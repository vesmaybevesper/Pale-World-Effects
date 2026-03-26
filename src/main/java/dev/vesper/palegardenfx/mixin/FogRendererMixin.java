package dev.vesper.palegardenfx.mixin;

import com.llamalad7.mixinextras.sugar.Local;
import dev.vesper.eveningstarlib.common.ESLModChecks;
import dev.vesper.palegardenfx.common.Config;
import net.minecraft.client.Camera;
import net.minecraft.client.DeltaTracker;
import net.minecraft.client.multiplayer.ClientLevel;
import net.minecraft.client.renderer.fog.FogData;
import net.minecraft.client.renderer.fog.FogRenderer;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Holder;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.biome.Biome;
import net.minecraft.world.level.biome.Biomes;
import net.minecraft.world.level.levelgen.Heightmap;
//? neoforge{
/*import net.neoforged.fml.ModList;
*///?}
import org.jetbrains.annotations.NotNull;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;
import org.spongepowered.asm.mixin.injection.callback.LocalCapture;

import static dev.vesper.palegardenfx.common.FogStateManager.fogFade;

@Mixin(FogRenderer.class)
public class FogRendererMixin {

    private static final float FADE_SPEED = 0.002f;
    private static float fogAlphaBase;
    private static float fogStart;
    private static float fogEnd;

    /*@Inject(method = "setupFog", at = @At(value = "INVOKE", target = "Lcom/mojang/blaze3d/systems/RenderSystem;getDevice()Lcom/mojang/blaze3d/systems/GpuDevice;", shift = At.Shift.BEFORE), locals = LocalCapture.CAPTURE_FAILHARD, cancellable = true)
    private void afterLoadLevel(Camera camera, int i, DeltaTracker deltaTracker, float f, ClientLevel world, CallbackInfoReturnable<Vector4f> cir, float tickProgress, Vector4f color, float renderDistanceBlocks, @Local Entity entity, @Local FogData fogData) {
        if (entity instanceof Player player) {
            if (!ESLModChecks.isShaders()) {
                BlockPos pos = player.getOnPos();
                Holder<@NotNull Biome> biome = world.getBiome(pos);
                if (!biome.is(Biomes.PALE_GARDEN)) {
                    return;
                }

                int topY = world.getHeight(Heightmap.Types.WORLD_SURFACE, pos.getX(), pos.getZ());
                if (biome.is(Biomes.PALE_GARDEN)) {
                    if (player.getY() <= (double)(topY + 15) && player.getY() >= 15.0D && !player.isCreative() && !player.isSpectator()) {
                        fogFade = Math.min(fogFade + 0.002F, 1.0F);
                    } else {
                        if (!(fogFade > 0.0F)) {
                            return;
                        }
                        fogFade = Math.min(fogFade - 0.002F, 0.0F);
                    }
                }

                if (Config.horrorMode) {
                    fogData.environmentalStart = renderDistanceBlocks * 0.8F + fogFade * (0.1F - renderDistanceBlocks * 0.8F);
                    fogData.environmentalEnd = renderDistanceBlocks + fogFade * (8.0F - renderDistanceBlocks);
                    fogAlphaBase = 0.99F;
                } else {
                    fogData.environmentalStart = renderDistanceBlocks * 0.8F + fogFade * (Config.fogStart - renderDistanceBlocks * 0.8F);
                    fogData.environmentalEnd = renderDistanceBlocks + fogFade * (Config.fogEnd - renderDistanceBlocks);
                    fogAlphaBase = Config.fogTransparency;
                }

                fogData.skyEnd = fogData.environmentalEnd;
                fogData.cloudEnd = fogData.environmentalEnd;
                color.x += fogFade * (0.8F - color.x);
                color.y += fogFade * (0.8F - color.y);
                color.z += fogFade * (0.85F - color.z);
                color.w += fogFade * (fogAlphaBase - color.w);
            }

        }
    }*/

    @Inject(method = "setupFog", at = @At(value = "INVOKE", target = "Lnet/minecraft/client/renderer/fog/environment/FogEnvironment;setupFog(Lnet/minecraft/client/renderer/fog/FogData;Lnet/minecraft/client/Camera;Lnet/minecraft/client/multiplayer/ClientLevel;FLnet/minecraft/client/DeltaTracker;)V", shift = At.Shift.BEFORE), locals = LocalCapture.CAPTURE_FAILHARD, cancellable = true)
    private static  void onFogStart(Camera camera, int renderDistanceInChunks, DeltaTracker deltaTracker, float darkenWorldAmount, ClientLevel level, CallbackInfoReturnable<FogData> cir, @Local(name = "entity") Entity entity, @Local(name = "fog") FogData fogData) {
        if (entity instanceof Player player) {
            //? fabric{
            if (!ESLModChecks.isShaders()) {
                //?}
                //? neoforge{
                /*if (!ModList.get().isLoaded("iris")) {
                *///?}
                BlockPos pos = player.getOnPos();
                Holder<@NotNull Biome> biome = level.getBiome(pos);
                if (!biome.is(Biomes.PALE_GARDEN)) {
                    return;
                }

                float renderDistanceBlocks = renderDistanceInChunks * 16;

                int topY = level.getHeight(Heightmap.Types.WORLD_SURFACE, pos.getX(), pos.getZ());
                if (biome.is(Biomes.PALE_GARDEN)) {
                    if (player.getY() <= (double)(topY + 15) && player.getY() >= 15.0D && !player.isCreative() && !player.isSpectator()) {
                        fogFade = Math.min(fogFade + 0.002F, 1.0F);
                    } else {
                        if (!(fogFade > 0.0F)) {
                            return;
                        }
                        fogFade = Math.min(fogFade - 0.002F, 0.0F);
                    }
                }

                if (Config.horrorMode) {
                    fogData.environmentalStart = renderDistanceBlocks * 0.8F + fogFade * (0.1F - renderDistanceBlocks * 0.8F);
                    fogData.environmentalEnd = renderDistanceBlocks + fogFade * (8.0F - renderDistanceBlocks);
                    fogAlphaBase = 0.99F;
                } else {
                    fogData.environmentalStart = renderDistanceBlocks * 0.8F + fogFade * (Config.fogStart - renderDistanceBlocks * 0.8F);
                    fogData.environmentalEnd = renderDistanceBlocks + fogFade * (Config.fogEnd - renderDistanceBlocks);
                    fogAlphaBase = Config.fogTransparency;
                }

                fogData.skyEnd = fogData.environmentalEnd;
                fogData.cloudEnd = fogData.environmentalEnd;
                fogData.color.x += fogFade * (0.8F - fogData.color.x);
                fogData.color.y += fogFade * (0.8F - fogData.color.y);
                fogData.color.z += fogFade * (0.85F - fogData.color.z);
                fogData.color.w += fogFade * (fogAlphaBase - fogData.color.w);
            }
        }
    }
}