package dev.vesper.palegardenfx.mixin;

import com.llamalad7.mixinextras.sugar.Local;
import dev.vesper.eveningstarlib.common.ESLModChecks;
import dev.vesper.palegardenfx.common.Config;
import net.minecraft.client.Camera;
import net.minecraft.client.DeltaTracker;
import net.minecraft.client.Minecraft;
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
import org.jetbrains.annotations.NotNull;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;
import org.spongepowered.asm.mixin.injection.callback.LocalCapture;

import static dev.vesper.palegardenfx.common.FogStateManager.fogFade;

@Mixin(FogRenderer.class)
public class FogRendererMixin {

    private static float fogAlphaBase;
    private static Entity entity;
    private static float renderDistanceBlocks;

    @Inject(method = "setupFog", at = @At(value = "INVOKE", target = "Lnet/minecraft/client/renderer/fog/environment/FogEnvironment;setupFog(Lnet/minecraft/client/renderer/fog/FogData;Lnet/minecraft/client/Camera;Lnet/minecraft/client/multiplayer/ClientLevel;FLnet/minecraft/client/DeltaTracker;)V", shift = At.Shift.BEFORE), locals = LocalCapture.CAPTURE_FAILHARD)
    private static void onFogStart(Camera camera, int renderDistanceInChunks, DeltaTracker deltaTracker, float darkenWorldAmount, ClientLevel level, CallbackInfoReturnable<FogData> cir, @Local(name = "entity") Entity localEntity) {
        entity = localEntity;
        renderDistanceBlocks = renderDistanceInChunks * 16;
    }

    @Inject(method = "updateBuffer(Lnet/minecraft/client/renderer/fog/FogData;)V", at = @At("HEAD"), cancellable = true)
    private void updateBuffer(FogData fog, CallbackInfo ci) {
        if (entity instanceof Player player) {
            if (!ESLModChecks.isShaders()) {
                if (Config.fogType == Config.FogType.VANILLA){
                    BlockPos pos = player.getOnPos();
                    assert Minecraft.getInstance().level != null;
                    Holder<@NotNull Biome> biome = Minecraft.getInstance().level.getBiome(pos);
                    if (!biome.is(Biomes.PALE_GARDEN)) {return;}

                    int topY = Minecraft.getInstance().level.getHeight(Heightmap.Types.WORLD_SURFACE, pos.getX(), pos.getZ());

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
                        fog.environmentalStart = renderDistanceBlocks * 0.8F + fogFade * (0.1F - renderDistanceBlocks * 0.8F);
                        fog.environmentalEnd = renderDistanceBlocks + fogFade * (8.0F - renderDistanceBlocks);
                        fogAlphaBase = 0.99F;
                    } else {
                        fog.environmentalStart = renderDistanceBlocks * 0.8F + fogFade * (Config.fogStart - renderDistanceBlocks * 0.8F);
                        fog.environmentalEnd = renderDistanceBlocks + fogFade * (Config.fogEnd - renderDistanceBlocks);
                        fogAlphaBase = Config.fogTransparency;
                    }

                    fog.skyEnd = fog.environmentalEnd;
                    fog.cloudEnd = fog.environmentalEnd;
                    fog.color.x += fogFade * (0.8F - fog.color.x);
                    fog.color.y += fogFade * (0.8F - fog.color.y);
                    fog.color.z += fogFade * (0.85F - fog.color.z);
                    fog.color.w += fogFade * (fogAlphaBase - fog.color.w);
                } else if (Config.fogType == Config.FogType.SHADER) {
                    ci.cancel();
                }
            }
        }
    }
}