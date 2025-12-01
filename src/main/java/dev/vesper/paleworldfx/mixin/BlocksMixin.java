package dev.vesper.paleworldfx.mixin;

import com.llamalad7.mixinextras.injector.ModifyExpressionValue;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.Minecraft;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockBehaviour;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Slice;

import static dev.vesper.paleworldfx.common.Config.horrorMode;

//? fabric {
@Environment(EnvType.CLIENT)
//?}
@Mixin(Blocks.class)
public class BlocksMixin {
    @ModifyExpressionValue(
            method = {"<clinit>"},
            at = {@At(
                    value = "INVOKE",
                    target = "Lnet/minecraft/world/level/block/state/BlockBehaviour$Properties;of()Lnet/minecraft/world/level/block/state/BlockBehaviour$Properties;"
            )},
            slice = {@Slice(
                    from = @At(
                            value = "CONSTANT",
                            args = {"stringValue=open_eyeblossom"}
                    )
            )}
    )
    private static BlockBehaviour.Properties openEyeblossom(BlockBehaviour.Properties original) {
        assert Minecraft.getInstance().level != null;
        if (Minecraft.getInstance().level.isClientSide()) {
            if (horrorMode) {
                return original.lightLevel((blockstate) -> {
                    return 3;
                });
            } else {
                return original.lightLevel((blockstate) -> {
                    return 5;
                });
            }
        } else {
            return original;
        }
    }

    @ModifyExpressionValue(
            method = {"<clinit>"},
            at = {@At(
                    value = "INVOKE",
                    target = "Lnet/minecraft/world/level/block/state/BlockBehaviour$Properties;of()Lnet/minecraft/world/level/block/state/BlockBehaviour$Properties;"
            )},
            slice = {@Slice(
                    from = @At(
                            value = "CONSTANT",
                            args = {"stringValue=potted_open_eyeblossom"}
                    )
            )}
    )
    private static BlockBehaviour.Properties pottedOpenEyeblossom(BlockBehaviour.Properties original) {
        assert Minecraft.getInstance().level != null;
        if (Minecraft.getInstance().level.isClientSide()) {
            if (horrorMode) {
                return original.lightLevel((blockstate) -> {return 3;});
            } else {
                return original.lightLevel((blockstate) -> {return 5;});
            }
        } else {
            return original;
        }
    }
}
