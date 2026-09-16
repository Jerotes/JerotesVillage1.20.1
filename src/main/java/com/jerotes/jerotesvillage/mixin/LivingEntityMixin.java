package com.jerotes.jerotesvillage.mixin;

import com.jerotes.jerotes.util.AttackFind;
import com.jerotes.jerotesvillage.entity.Interface.GoastEntity;
import com.jerotes.jerotesvillage.event.WorldEvent;
import com.jerotes.jerotesvillage.init.JerotesVillageItems;
import com.jerotes.jerotesvillage.spell.OtherSpellFind;
import com.jerotes.jerotesvillage.util.OtherEntityFactionFind;
import net.minecraft.core.BlockPos;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.state.BlockState;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import javax.annotation.Nullable;

@Mixin(LivingEntity.class)
public abstract class LivingEntityMixin extends Entity {
    protected LivingEntityMixin(EntityType<? extends Entity> entityType, Level level) {
        super(entityType, level);
    }

    @Inject(method = "shouldDropExperience", at = @At("HEAD"), cancellable = true)
    public void shouldDropExperience(CallbackInfoReturnable<Boolean> cir) {
        if (WorldEvent.ZsieinModerateInfectedAbove(this.level())) {
            cir.setReturnValue(false);
            cir.cancel();
        }
    }

    @Inject(method = "checkFallDamage", at = @At("HEAD"))
    public void checkFallDamage(double d, boolean bl, BlockState blockState, BlockPos blockPos, CallbackInfo ci) {
        LivingEntity living = ((LivingEntity)(Object)this);
        if (!this.level().isClientSide && this.fallDistance > 1.5F && bl && !blockState.isAir() && this.isShiftKeyDown()) {
            boolean boot = living.getItemBySlot(EquipmentSlot.FEET).getItem() == JerotesVillageItems.MOUNTAIN_REALM_BOOTS.get();
            if (boot) {
                OtherSpellFind.EarthquakeKick(living, 6, 3, 0, 1800, false);
            }
        }
    }

    @Inject(method = "heal", at = @At("HEAD"), cancellable = true)
    public void heal(float f, CallbackInfo ci) {
        if (WorldEvent.ZsieinSevereInfectedAbove(this.level()) && !OtherEntityFactionFind.isZsiein(this.getType())) {
            ci.cancel();
        }
    }

    @Inject(method = "hurt", at = @At("HEAD"), cancellable = true)
    public void hurt(DamageSource damageSource, float p_21017_, CallbackInfoReturnable<Boolean> cir) {
        if (this instanceof GoastEntity goastEntity && goastEntity.isGoast() && AttackFind.isFireDamage(damageSource)) {
           cir.setReturnValue(false);
           cir.cancel();
        }
    }

    @Shadow
    public abstract ItemStack getItemBySlot(EquipmentSlot equipmentSlot);

    @Shadow public abstract boolean hasEffect(MobEffect p_21024_);

    @Shadow @Nullable public abstract MobEffectInstance getEffect(MobEffect p_21125_);

}