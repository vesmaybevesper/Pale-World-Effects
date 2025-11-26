package dev.vesper.paleworldfx.mixin;

import com.llamalad7.mixinextras.sugar.Local;
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
import org.joml.Vector4f;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;
import org.spongepowered.asm.mixin.injection.callback.LocalCapture;

@Mixin(FogRenderer.class)
public class FogRendererMixin {

    private static final float FADE_SPEED = 0.002f;

    @Inject(method = "setupFog", at = @At(value = "INVOKE", target = "Lcom/mojang/blaze3d/systems/RenderSystem;getDevice()Lcom/mojang/blaze3d/systems/GpuDevice;", shift = At.Shift.BEFORE), locals = LocalCapture.CAPTURE_FAILHARD, cancellable = true)
    private void afterLoadLevel(Camera camera, int i, DeltaTracker deltaTracker, float f, ClientLevel clientLevel, CallbackInfoReturnable<Vector4f> cir, float tickProgress, Vector4f color, float renderDistanceBlocks, @Local Entity entity, @Local FogData fogData) {
        if (!(entity instanceof Player player)) return;

        BlockPos pos = player.getOnPos();
        Holder<Biome> biome = clientLevel.getBiome(pos);
        int topY = clientLevel.getHeight(Heightmap.Types.WORLD_SURFACE, pos.getX(), pos.getZ());

        if (biome.is(Biomes.PALE_GARDEN)){
            if (player.getY()  <= topY + 15 && player.getY() >= 15 && !player.isCreative() && !player.isSpectator()){
                fogFade = Math.min(fogFade + FADE_SPEED, 1f);
            }
        }
    }
}