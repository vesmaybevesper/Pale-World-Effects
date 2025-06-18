package vesper.paleworldfx.mixin;

import com.mojang.blaze3d.buffers.Std140Builder;
import net.minecraft.block.enums.CameraSubmersionType;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.network.ClientPlayerEntity;
import net.minecraft.client.render.Camera;
import net.minecraft.client.render.RenderTickCounter;
import net.minecraft.client.render.fog.FogData;
import net.minecraft.client.render.fog.FogRenderer;
import net.minecraft.client.world.ClientWorld;
import net.minecraft.entity.Entity;
import net.minecraft.registry.entry.RegistryEntry;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.Heightmap;
import net.minecraft.world.biome.Biome;
import net.minecraft.world.biome.BiomeKeys;
import org.joml.Vector4f;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;
import org.spongepowered.asm.mixin.injection.callback.LocalCapture;
import vesper.paleworldfx.Config;

import java.nio.ByteBuffer;

import static vesper.paleworldfx.Utils.FogStateManager.fogFade;

@Mixin(FogRenderer.class)
public abstract class FogRendererMixin {
    protected FogRendererMixin() {
    }

    @Shadow protected abstract Vector4f getFogColor(Camera camera, float tickProgress, ClientWorld world, int viewDistance, float skyDarkness, boolean thick);
	private static final float FADE_SPEED = 0.002f;
	private static float fogStart;
	private static float fogEnd;
	private static float fogAlphaBase;

    @Inject(method = "applyFog", at = @At(value = "INVOKE", target = "Lcom/mojang/blaze3d/systems/RenderSystem;getDevice()Lcom/mojang/blaze3d/systems/GpuDevice;", shift = At.Shift.BEFORE), locals = LocalCapture.CAPTURE_FAILHARD, cancellable = true)
	private void modifyFogSettings(Camera camera, int viewDistance, boolean thick, RenderTickCounter tickCounter, float skyDarkness, ClientWorld world, CallbackInfoReturnable<Vector4f> cir, float tickProgress, Vector4f color, float renderDistanceBlocks, CameraSubmersionType cameraSubmersionType, Entity entity, FogData fogData) {
	if (!(entity instanceof ClientPlayerEntity player)) return;


	BlockPos pos = player.getBlockPos();
	RegistryEntry<Biome> biome = world.getBiome(pos);
	int topY  = world.getTopY(Heightmap.Type.WORLD_SURFACE, pos.getX(), pos.getZ());

	if (biome.matchesKey(BiomeKeys.PALE_GARDEN)){
		if (player.getY() <= topY + 15 && player.getY() >= 15 && !player.isCreative() && !player.isSpectator()){
			fogFade = Math.min(fogFade + FADE_SPEED, 1f);
		} else if (fogFade > 0){
			fogFade = Math.min(fogFade - FADE_SPEED, 0);
		}else {return;}

		Config.horrorMode = Config.horrorModeSelect == Config.horrorVals.TRUE;
		float fogAlpha;
	}
			if (Config.horrorMode) {
				fogData.environmentalStart = (renderDistanceBlocks * 0.8F) + fogFade * (0.1F - (renderDistanceBlocks * 0.8F));
				fogData.environmentalEnd = (renderDistanceBlocks + fogFade * (8F - (renderDistanceBlocks)));
				fogAlphaBase = 0.99F;
			} else {
				fogData.environmentalStart = (renderDistanceBlocks * 0.8F) + fogFade * (Config.fogStart - (renderDistanceBlocks * 0.8F));
				fogData.environmentalEnd = (renderDistanceBlocks + fogFade * (Config.fogEnd - (renderDistanceBlocks)));
				fogAlphaBase = Config.fogTransparency;
			}
			fogData.skyEnd = fogData.environmentalEnd;
			fogData.cloudEnd = fogData.environmentalEnd;

			 color.x = color.x + fogFade * (0.8F - color.x);
			 color.y = color.y + fogFade * (0.8F - color.y);
			 color.z = color.z + fogFade * (0.85F - color.z);
			 color.w = color.w + fogFade * (fogAlphaBase - color.w);




		}
}

