package dev.vesper.palegardenfx.mixin;

import dev.kikugie.fletching_table.annotation.MixinEnvironment;
import dev.vesper.eveningstarlib.common.ESLModChecks;
import dev.vesper.eveningstarlib.common.aurora.Aurora;
import dev.vesper.palegardenfx.common.FogCode;
import dev.vesper.palegardenfx.common.Config;
import net.minecraft.client.Camera;
import net.minecraft.client.DeltaTracker;
import net.minecraft.client.Minecraft;
import net.minecraft.client.PreferredGraphicsApi;
import net.minecraft.client.multiplayer.ClientLevel;
import net.minecraft.client.renderer.fog.FogData;
import net.minecraft.client.renderer.fog.FogRenderer;
import net.minecraft.client.renderer.fog.environment.FogEnvironment;
import net.minecraft.network.chat.Component;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.material.FogType;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;
import org.spongepowered.asm.mixin.injection.callback.LocalCapture;

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

	@Inject(method = "setupFog", at = @At(value = "INVOKE", target = "Lnet/minecraft/client/renderer/fog/environment/FogEnvironment;setupFog(Lnet/minecraft/client/renderer/fog/FogData;Lnet/minecraft/client/Camera;Lnet/minecraft/client/multiplayer/ClientLevel;FLnet/minecraft/client/DeltaTracker;)V", shift = At.Shift.BEFORE), locals = LocalCapture.CAPTURE_FAILHARD)
	private static void onFogStart(Camera camera, int renderDistanceInChunks, DeltaTracker deltaTracker, float darkenWorldAmount, ClientLevel level, CallbackInfoReturnable<FogData> cir, float partialTickTime, float renderDistanceInBlocks, FogType fogType, Entity entity, FogData fog, Iterator var11, FogEnvironment fogEnvironment) {
		capturedEntity = entity;
		renderBlocks = renderDistanceInChunks * 16;
	}

	// if fogType is set to off we do nothing and vanilla fog takes over
	@Inject(method = "updateBuffer(Lnet/minecraft/client/renderer/fog/FogData;)V", at = @At("HEAD"))
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
					//this type is intended for a future custom fog shader option so it shouldn't do anything RN
					// Requires Vulkan cause that's the API I wrote the uploader for, probably won't port it to OpenGL unless Mojang really drag their feet on going Vulkan
					// Regardless, if you are reading this, this code does nothing as the uploader never fires in ESL lol. I'll delete this comment when its go time
					//? if >=26.2 {
					if (Minecraft.getInstance().options.preferredGraphicsBackend().equals(PreferredGraphicsApi.VULKAN)) {
						Aurora.setUniform("passedChecks", true);
					} else {
						assert Minecraft.getInstance().player != null;
						Minecraft.getInstance().player.sendSystemMessage(Component.literal("You have the Shader Fog Type selected but are on OpenGL, that type only works on Vulkan"));
					}
					//?}
				}
			}
		}
	}
}
