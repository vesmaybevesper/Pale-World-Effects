package vesper.paleworldfx.mixin;

import net.minecraft.client.MinecraftClient;
import net.minecraft.client.network.ClientPlayerEntity;
import net.minecraft.client.render.BackgroundRenderer;
import net.minecraft.client.render.Camera;
import net.minecraft.client.render.Fog;
import net.minecraft.client.render.FogShape;
import net.minecraft.client.world.ClientWorld;
import net.minecraft.registry.entry.RegistryEntry;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.Heightmap;
import net.minecraft.world.biome.Biome;
import net.minecraft.world.biome.BiomeKeys;
import org.joml.Vector4f;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;
import vesper.paleworldfx.Config;


import static vesper.paleworldfx.FogStateManager.fogFade;

@Mixin(BackgroundRenderer.class)
public class BackgroundRendererMixin {
	private static final float FADE_SPEED = 0.002f;
	private static float fogStart;
	private static float fogEnd;
	private static float fogAlphaBase;

	@Inject(method = "applyFog", at = @At("TAIL"), cancellable = true)
	private static void modifyFogSettings(Camera camera, BackgroundRenderer.FogType fogType, Vector4f color, float viewDistance, boolean thickenFog, float tickDelta, CallbackInfoReturnable<Fog> cir) {
		Fog PALE_GARDEN_FOG;

		assert MinecraftClient.getInstance().player != null;
		ClientWorld world = MinecraftClient.getInstance().world;
		ClientPlayerEntity player = MinecraftClient.getInstance().player;
		if (world == null || camera == null) {
			return;
		}
		BlockPos pos = MinecraftClient.getInstance().player.getBlockPos();
		RegistryEntry<Biome> biome = world.getBiome(pos);

		if (biome.matchesKey(BiomeKeys.PALE_GARDEN) && player.getY() <= (world.getTopY(Heightmap.Type.WORLD_SURFACE, pos.getX(), pos.getZ())) + 15
				&& !(player.getY() < (world.getTopY(Heightmap.Type.WORLD_SURFACE, pos.getX(), pos.getZ())) - 15)
				&& !player.isCreative() && !player.isSpectator()) {
			fogFade = Math.min(fogFade + FADE_SPEED, 1.0F);
		} else if (fogFade > 0) {
			fogFade = Math.max(fogFade - FADE_SPEED, 0);
		} else {
			return;
		}

		Config.horrorMode = Config.horrorModeSelect == Config.horrorVals.TRUE;

		if (Config.horrorMode) {
			fogStart = (viewDistance * 0.8F) + fogFade * (0.1F - (viewDistance * 0.8F));
			fogEnd = (viewDistance) + fogFade * (8F - (viewDistance));
			fogAlphaBase = 0.99F;
		} else {
			fogStart = (viewDistance * 0.8F) + fogFade * (Config.fogStart - (viewDistance * 0.8F));
			fogEnd = (viewDistance) + fogFade * (Config.fogEnd - (viewDistance));
			fogAlphaBase = Config.fogTransparency ;
		}

		float fogRed = color.x + fogFade * (0.8F - color.x);
		float fogGreen = color.y + fogFade * (0.8F - color.y);
		float fogBlue = color.z + fogFade * (0.85F - color.z);
		float fogAlpha = color.w + fogFade * (fogAlphaBase - color.w);

		PALE_GARDEN_FOG = new Fog(fogStart, fogEnd, FogShape.CYLINDER, fogRed, fogGreen, fogBlue, fogAlpha);
		cir.setReturnValue(PALE_GARDEN_FOG);
	}
}