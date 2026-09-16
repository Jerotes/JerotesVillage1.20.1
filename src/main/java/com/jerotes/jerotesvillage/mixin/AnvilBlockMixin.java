package com.jerotes.jerotesvillage.mixin;

import com.jerotes.jerotesvillage.init.JerotesVillageBlocks;
import net.minecraft.core.Direction;
import net.minecraft.world.level.block.AnvilBlock;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.properties.DirectionProperty;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(AnvilBlock.class)
public abstract class AnvilBlockMixin {
    @Shadow @Final public static DirectionProperty FACING;

    @Inject(method = "damage", at = @At(value = "HEAD"), cancellable = true)
    private static void damage(BlockState blockState, CallbackInfoReturnable<BlockState> cir) {
        if (blockState.is(JerotesVillageBlocks.VILLAGER_BLOCK_FORGER.get())) {
            cir.setReturnValue(
                    (BlockState) JerotesVillageBlocks.VILLAGER_BLOCK_FORGER.get().defaultBlockState().setValue(FACING, (Direction)blockState.getValue(FACING)));
            cir.cancel();
        }
    }
}