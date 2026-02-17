package dev.vesper.palegardenfx.mixin;

import com.llamalad7.mixinextras.sugar.Local;
import dev.vesper.eveningstarlib.common.ESLModChecks;
import dev.vesper.palegardenfx.common.Config;
import dev.vesper.palegardenfx.common.FogStateManager;
import net.minecraft.client.Camera;
import net.minecraft.client.DeltaTracker;
import net.minecraft.client.Minecraft;
import net.minecraft.client.multiplayer.ClientLevel;
//? 1.21.4 {
/*import net.minecraft.client.renderer.FogParameters;
import com.mojang.blaze3d.shaders.FogShape;
import net.minecraft.client.renderer.FogRenderer;
*///?}
//? >1.21.4 {
import net.minecraft.client.renderer.fog.FogData;
import net.minecraft.client.renderer.fog.FogRenderer;
//?}
import net.minecraft.core.BlockPos;
import net.minecraft.core.Holder;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.biome.Biome;
import net.minecraft.world.level.biome.Biomes;
import net.minecraft.world.level.levelgen.Heightmap;
import net.minecraft.world.level.material.FogType;
import org.jetbrains.annotations.NotNull;
import org.joml.Vector4f;
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
//? 1.21.11 {
    @Inject(method = "setupFog", at = @At(value = "INVOKE", target = "Lcom/mojang/blaze3d/systems/RenderSystem;getDevice()Lcom/mojang/blaze3d/systems/GpuDevice;", shift = At.Shift.BEFORE), locals = LocalCapture.CAPTURE_FAILHARD, cancellable = true)
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
    }
    //?}
//? < 1.21.11 && !1.21.4 {
    /*@Inject(method = "setupFog", at = @At(value = "INVOKE", target = "Lcom/mojang/blaze3d/systems/RenderSystem;getDevice()Lcom/mojang/blaze3d/systems/GpuDevice;", shift = At.Shift.BEFORE), locals = LocalCapture.CAPTURE_FAILHARD, cancellable = true)
    private void afterLoadLevel(Camera camera, int i, boolean bl, DeltaTracker deltaTracker, float f, ClientLevel world, CallbackInfoReturnable<Vector4f> cir, float g, Vector4f color, float renderDistanceBlocks, FogType fogType, Entity entity, FogData fogData, float j) {
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
    }
    *///?}
    //? 1.21.4 {
/*@Inject(method = "setupFog", at = @At(value = "TAIL"), cancellable = true)
private static void afterLoadLevel(Camera camera, FogRenderer.FogMode fogMode, Vector4f color, float viewDistance, boolean bl, float g, CallbackInfoReturnable<FogParameters> cir) {
    Entity entity = Minecraft.getInstance().cameraEntity;
    ClientLevel world = Minecraft.getInstance().level;
    if (entity instanceof Player player) {
        if (!ESLModChecks.isShaders()) {
            BlockPos pos = player.getOnPos();
            assert world != null;
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
                fogStart = viewDistance * 0.8F + FogStateManager.fogFade * (0.1F - viewDistance * 0.8F);
                fogEnd = viewDistance + FogStateManager.fogFade * (8.0F - viewDistance);
                fogAlphaBase = 0.99F;
            } else {
                fogStart = viewDistance * 0.8F + FogStateManager.fogFade * (Config.fogStart - viewDistance * 0.8F);
                fogEnd = viewDistance + FogStateManager.fogFade * (Config.fogEnd - viewDistance);
                fogAlphaBase = Config.fogTransparency;
            }

            float fogRed = color.x + FogStateManager.fogFade * (0.8F - color.x);
            float fogGreen = color.y + FogStateManager.fogFade * (0.8F - color.y);
            float fogBlue = color.z + FogStateManager.fogFade * (0.85F - color.z);
            float fogAlpha = color.w + FogStateManager.fogFade * (fogAlphaBase - color.w);
            FogParameters PALE_GARDEN_FOG = new FogParameters(fogStart, fogEnd, FogShape.SPHERE, fogRed, fogGreen, fogBlue, fogAlpha);
            cir.setReturnValue(PALE_GARDEN_FOG);
        }

    }
}
    *///?}
}