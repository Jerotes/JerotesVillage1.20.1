package com.jerotes.jerotesvillage.mixin;

import com.jerotes.jerotesvillage.entity.Interface.IRottenDog;
import com.jerotes.jerotesvillage.init.JerotesVillageEntityType;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.TamableAnimal;
import net.minecraft.world.entity.animal.Wolf;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(Wolf.class)
public abstract class WolfMixin extends TamableAnimal {
    protected WolfMixin(EntityType<? extends TamableAnimal> entityType, Level level) {
        super(entityType, level);
    }

    @Inject(method = "tick", at = @At(value = "INVOKE", target = "Lnet/minecraft/world/entity/animal/Wolf;playSound(Lnet/minecraft/sounds/SoundEvent;FF)V"))
    public void tick(CallbackInfo ci) {
        if (this instanceof IRottenDog rottenDog) {
            rottenDog.RottenDogShake();
        }
    }

    @Inject(method = "mobInteract", at = @At("HEAD"), cancellable = true)
    public void mobInteract(Player player, InteractionHand interactionHand, CallbackInfoReturnable<InteractionResult> cir) {
        if (this.getType() == JerotesVillageEntityType.CARVED_HOUND.get()
                || this.getType() == JerotesVillageEntityType.WILDERNESS_WOLF.get()
                || this.getType() == JerotesVillageEntityType.ROTTEN_DOG.get()
                || this.getType() == JerotesVillageEntityType.WONDERLAND_YELLOW_DOG.get()) {
            cir.setReturnValue(super.mobInteract(player, interactionHand));
            cir.cancel();
        }
    }

    @Inject(method = "hurt", at = @At("HEAD"), cancellable = true)
    public void hurt(DamageSource damageSource, float f, CallbackInfoReturnable<Boolean> cir) {
        if (this.getType() == JerotesVillageEntityType.CARVED_HOUND.get()
                || this.getType() == JerotesVillageEntityType.WILDERNESS_WOLF.get()
                || this.getType() == JerotesVillageEntityType.ROTTEN_DOG.get()
                || this.getType() == JerotesVillageEntityType.WONDERLAND_YELLOW_DOG.get()) {
            cir.setReturnValue(super.hurt(damageSource, f));
            cir.cancel();
        }
    }
}