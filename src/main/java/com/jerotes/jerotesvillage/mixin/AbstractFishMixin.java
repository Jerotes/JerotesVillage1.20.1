package com.jerotes.jerotesvillage.mixin;

import com.jerotes.jerotesvillage.entity.Animal.Fish.BenthicFish.BrightGrassCarpEntity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.animal.AbstractFish;
import net.minecraft.world.entity.animal.Bucketable;
import net.minecraft.world.entity.animal.WaterAnimal;
import net.minecraft.world.level.Level;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(AbstractFish.class)
public abstract class AbstractFishMixin extends WaterAnimal implements Bucketable {

    protected AbstractFishMixin(EntityType<? extends WaterAnimal> entityType, Level level) {
        super(entityType, level);
    }

    @Inject(method = "aiStep", at = @At("HEAD"), cancellable = true)
    private void aiStep(CallbackInfo ci) {
        if (((AbstractFish)(Object)this) instanceof BrightGrassCarpEntity brightGrassCarpEntity && brightGrassCarpEntity.isLandNavigatorType) {
            super.aiStep();
            ci.cancel();
        }
    }
}