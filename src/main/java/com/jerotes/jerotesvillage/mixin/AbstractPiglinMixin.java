package com.jerotes.jerotesvillage.mixin;

import com.jerotes.jerotesvillage.init.JerotesVillageBiomes;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.monster.Monster;
import net.minecraft.world.entity.monster.piglin.AbstractPiglin;
import net.minecraft.world.level.Level;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(AbstractPiglin.class)
public abstract class AbstractPiglinMixin extends Monster {
    protected AbstractPiglinMixin(EntityType<? extends Monster> entityType, Level level) {
        super(entityType, level);
    }
    @Inject(method = "isImmuneToZombification", at = @At("HEAD"), cancellable = true)
    private void isImmuneToZombification(CallbackInfoReturnable<Boolean> cir) {
        if (this.level().getBiome(this.getOnPos()).is(JerotesVillageBiomes.DROUGHT_FIRE)) {
            cir.setReturnValue(true);
            cir.cancel();
        }
    }
}