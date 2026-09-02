package dev.vesper.palegardenfx.mixin;

import com.llamalad7.mixinextras.sugar.Local;
import dev.kikugie.fletching_table.annotation.MixinEnvironment;
import dev.vesper.eveningstarlib.common.ESLModChecks;
import dev.vesper.palegardenfx.common.FogCode;
import dev.vesper.palegardenfx.common.Config;
import net.minecraft.client.Camera;
import net.minecraft.client.DeltaTracker;
import net.minecraft.client.multiplayer.ClientLevel;
import net.minecraft.client.renderer.fog.FogData;
import net.minecraft.client.renderer.fog.FogRenderer;
import net.minecraft.client.renderer.fog.environment.FogEnvironment;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.material.FogType;
import org.joml.Vector4f;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;
import org.spongepowered.asm.mixin.injection.callback.LocalCapture;

import java.nio.ByteBuffer;
import java.util.Iterator;

@Mixin(FogRenderer.class)
@MixinEnvironment(type = MixinEnvironment.Env.CLIENT)
public class FogRendererMixin {

	@Unique
	private static float fogAlphaBase;
	@Unique
	private static Entity capturedEntity;
	@Unique
	private static float renderBlocks;
	@Unique
	private static Vector4f color;
	@Unique
	private static FogData capturedFog;

	@Inject(method = "setupFog", at = @At(value = "INVOKE", target = "Lnet/minecraft/client/renderer/fog/environment/FogEnvironment;setupFog(Lnet/minecraft/client/renderer/fog/FogData;Lnet/minecraft/client/Camera;Lnet/minecraft/client/multiplayer/ClientLevel;FLnet/minecraft/client/DeltaTracker;)V", shift = At.Shift.BEFORE), locals = LocalCapture.CAPTURE_FAILHARD)
	// the worlds longest version replace annotation
	//~ if <26.1.2 'Camera camera, int renderDistanceInChunks, DeltaTracker deltaTracker, float darkenWorldAmount, ClientLevel level, CallbackInfoReturnable<FogData> cir, float partialTickTime, float renderDistanceInBlocks, FogType fogType, Entity entity, FogData fog, Iterator var11, FogEnvironment fogEnvironment' -> 'Camera camera, int i, DeltaTracker deltaTracker, float f, ClientLevel clientLevel, CallbackInfoReturnable<Vector4f> cir, float g, Vector4f color, float h, FogType fogType, Entity entity, FogData fog, Iterator var12, FogEnvironment fogEnvironment'
	private static void onFogStart(Camera camera, int renderDistanceInChunks, DeltaTracker deltaTracker, float darkenWorldAmount, ClientLevel level, CallbackInfoReturnable<FogData> cir, float partialTickTime, float renderDistanceInBlocks, FogType fogType, Entity entity, FogData fog, Iterator var11, FogEnvironment fogEnvironment) {
		capturedEntity = entity;
		//~ if <26.1.2 'renderDistanceInChunks' -> 'i'
		renderBlocks = renderDistanceInChunks * 16;

		//? 1.21.11{
		/*if (capturedEntity instanceof Player player) {
			if (Config.fogType == Config.FogType.VANILLA) {
				if (Config.gamemodeFog){
					if (!player.isCreative() && !player.isSpectator()){
						FogCode.setFogBuffer(h, fog, fogAlphaBase, player, color);
					}
				} else {
					FogCode.setFogBuffer(h, fog, fogAlphaBase, player, color);
				}
			} else if (Config.fogType == Config.FogType.SHADER) {
				// If set to use a shader just cancel the whole thing, this type is intended for a future custom fog shader option but rn we can treat it as a "hey im using iris" option I guess
			}
		}
		*///?}
	}

	// if fogType is set to off we do nothing and vanilla fog takes over
	//? if >=26.1.2 {
	@Inject(method = "updateBuffer(Lnet/minecraft/client/renderer/fog/FogData;)V", at = @At("HEAD"), cancellable = true)
	private void updateBuffer(FogData fog, CallbackInfo ci) {
		if (!ESLModChecks.isShaders()) {
			if (capturedEntity instanceof Player player) {
				if (Config.fogType == Config.FogType.VANILLA) {
					if (Config.gamemodeFog){
						if (!player.isCreative() && !player.isSpectator()){
							FogCode.setFogBuffer(renderBlocks, fog, fogAlphaBase, player);
						}
					} else {
						FogCode.setFogBuffer(renderBlocks, fog, fogAlphaBase, player);
					}
				} else if (Config.fogType == Config.FogType.SHADER) {
					// If set to use a shader just cancel the whole thing, this type is intended for a future custom fog shader option but rn we can treat it as a "hey im using iris" option I guess
					ci.cancel();
				}
			}
		}
	}
	//?}
}
