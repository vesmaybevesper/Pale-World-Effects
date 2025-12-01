package dev.vesper.paleworldfx.mixin;

import com.llamalad7.mixinextras.sugar.Local;
//? fabric {
import dev.vesper.eveningstarlib.fabric.ESLModChecks;
//?}
//? neoforge {
/*import dev.vesper.eveningstarlib.neoforge.ESLModChecks;
*///?}
import dev.vesper.paleworldfx.common.Config;
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
import org.jetbrains.annotations.NotNull;
import org.joml.Vector4f;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;
import org.spongepowered.asm.mixin.injection.callback.LocalCapture;

import static dev.vesper.paleworldfx.common.FogStateManager.fogFade;

@Mixin(FogRenderer.class)
public class FogRendererMixin {

    private static final float FADE_SPEED = 0.002f;
    private static float fogAlphaBase;

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
}