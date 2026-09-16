package com.jerotes.jerotesvillage.mixin;

import com.jerotes.jerotesvillage.entity.Animal.PurpleSandRabbitEntity;
import net.minecraft.world.entity.PathfinderMob;
import net.minecraft.world.entity.ai.goal.Goal;
import net.minecraft.world.entity.ai.goal.PanicGoal;
import net.minecraft.world.entity.animal.Rabbit;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(PanicGoal.class)
public abstract class PanicGoalMixin extends Goal {
    @Shadow @Final protected PathfinderMob mob;

    @Inject(method = "shouldPanic", at = @At(value = "HEAD"), cancellable = true)
    public void shouldPanic(CallbackInfoReturnable<Boolean> cir) {
        if (this.mob instanceof PurpleSandRabbitEntity purpleSandRabbitEntity && purpleSandRabbitEntity.getVariant() == Rabbit.Variant.EVIL) {
            cir.setReturnValue(false);
            cir.cancel();
        }
    }
}