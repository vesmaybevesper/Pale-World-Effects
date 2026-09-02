package dev.vesper.palegardenfx.common;

import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.fog.FogData;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Holder;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.biome.Biome;
import net.minecraft.world.level.biome.Biomes;
import net.minecraft.world.level.levelgen.Heightmap;
import org.joml.Vector4f;

import static dev.vesper.palegardenfx.common.util.FogStateManager.fogFade;

public class FogCode {

	//~ if 1.21.11 'float renderBlocks, FogData fog, float fogAlphaBase, Player player' -> 'float renderBlocks, FogData fog, float fogAlphaBase, Player player, Vector4f color'
	public static void setFogBuffer(float renderBlocks, FogData fog, float fogAlphaBase, Player player) {
		if (Config.fogType == Config.FogType.VANILLA) {
			BlockPos pos = player.getOnPos();
			assert Minecraft.getInstance().level != null;
			Holder<Biome> biome = Minecraft.getInstance().level.getBiome(pos);
			if (!biome.is(Biomes.PALE_GARDEN)) {
				if (fogFade != 0.0f) fogFade = Math.min(fogFade - 0.002F, 0.0F);
				return;
			}

			int topY = Minecraft.getInstance().level.getHeight(Heightmap.Types.WORLD_SURFACE, pos.getX(), pos.getZ());

			if (biome.is(Biomes.PALE_GARDEN)) {
				if (player.getY() <= (double) (topY + 15) && player.getY() >= 15.0D) {
					fogFade = Math.min(fogFade + 0.002F, 1.0F);
				} else {
					if (!(fogFade > 0.0F)) {
						return;
					}
					fogFade = Math.min(fogFade - 0.002F, 0.0F);
				}
			}

			if (Config.horrorMode) {
				fog.environmentalStart = renderBlocks * 0.8F + fogFade * (0.1F - renderBlocks * 0.8F);
				fog.environmentalEnd = renderBlocks + fogFade * (8.0F - renderBlocks);
				fogAlphaBase = 0.99F;
			} else {
				fog.environmentalStart = renderBlocks * 0.8F + fogFade * (Config.fogStart - renderBlocks * 0.8F);
				fog.environmentalEnd = renderBlocks + fogFade * (Config.fogEnd - renderBlocks);
				fogAlphaBase = Config.fogTransparency;
			}

			fog.skyEnd = fog.environmentalEnd;
			fog.cloudEnd = fog.environmentalEnd;
			//~ if 1.21.11 'fog.color' -> 'color' {
			fog.color.x += fogFade * (0.8F - fog.color.x);
			fog.color.y += fogFade * (0.8F - fog.color.y);
			fog.color.z += fogFade * (0.85F - fog.color.z);
			fog.color.w += fogFade * (fogAlphaBase - fog.color.w);
			//~}
		}
	}
}
