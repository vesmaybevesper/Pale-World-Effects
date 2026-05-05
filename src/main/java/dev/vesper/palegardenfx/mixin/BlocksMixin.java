package dev.vesper.palegardenfx.mixin;

import com.llamalad7.mixinextras.injector.ModifyExpressionValue;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockBehaviour;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Slice;

import static dev.vesper.palegardenfx.common.Config.horrorMode;

@Mixin(Blocks.class)
public class BlocksMixin {
    //? <26.2 {
    /*@ModifyExpressionValue(
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
                if (horrorMode) {
                    return original.lightLevel((blockstate) -> 3);
                } else {
                    return original.lightLevel((blockstate) -> 5);
                }
    }

@ModifyExpressionValue(
        method = {"<clinit>"},
        at = {@At(
                value = "INVOKE",
                target = "Lnet/minecraft/world/level/block/Blocks;flowerPotProperties()Lnet/minecraft/world/level/block/state/BlockBehaviour$Properties;"
        )},
        slice = {@Slice(
                from = @At(
                        value = "CONSTANT",
                        args = {"stringValue=potted_open_eyeblossom"}
                )
        )}
)
private static BlockBehaviour.Properties pottedOpenEyeblossom(BlockBehaviour.Properties original) {
    if (horrorMode) {
        return original.lightLevel((blockstate) -> 3);
    } else {
        return original.lightLevel((blockstate) -> 5);
    }
}
*///?} >=26.2{
@ModifyExpressionValue(
        method = {"<clinit>"},
        at = {@At(
                value = "INVOKE",
                target = "Lnet/minecraft/world/level/block/state/BlockBehaviour$Properties;of()Lnet/minecraft/world/level/block/state/BlockBehaviour$Properties;"
        )},
        slice = {@Slice(
                from = @At(
                        value = "CONSTANT",
                        args = {"BlockItemIds.OPEN_EYEBLOSSOM"}
                )
        )}
)
private static BlockBehaviour.Properties openEyeblossom(BlockBehaviour.Properties original) {
    if (horrorMode) {
        return original.lightLevel((blockstate) -> 3);
    } else {
        return original.lightLevel((blockstate) -> 5);
    }
}

    @ModifyExpressionValue(
            method = {"<clinit>"},
            at = {@At(
                    value = "INVOKE",
                    target = "Lnet/minecraft/world/level/block/Blocks;flowerPotProperties()Lnet/minecraft/world/level/block/state/BlockBehaviour$Properties;"
            )},
            slice = {@Slice(
                    from = @At(
                            value = "CONSTANT",
                            args = {"BlockIds.POTTED_OPEN_EYEBLOSSOM"}
                    )
            )}
    )
    private static BlockBehaviour.Properties pottedOpenEyeblossom(BlockBehaviour.Properties original) {
        if (horrorMode) {
            return original.lightLevel((blockstate) -> 3);
        } else {
            return original.lightLevel((blockstate) -> 5);
        }
    }
    //?}
}
