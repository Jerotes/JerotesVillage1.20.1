package com.jerotes.jerotesvillage.mixin;

import com.jerotes.jerotesvillage.entity.Animal.IrishtailChickenEntity;
import com.jerotes.jerotesvillage.init.JerotesVillageEntityType;
import com.jerotes.jerotesvillage.init.JerotesVillageItems;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.animal.Chicken;
import net.minecraft.world.entity.projectile.ThrowableItemProjectile;
import net.minecraft.world.entity.projectile.ThrownEgg;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.HitResult;
import org.jetbrains.annotations.NotNull;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(ThrownEgg.class)
public abstract class ThrownEggMixin extends ThrowableItemProjectile {
    protected ThrownEggMixin(EntityType<? extends ThrowableItemProjectile> entityType, Level level) {
        super(entityType, level);
    }

    @Inject(method = "onHit", at = @At("HEAD"), cancellable = true)
    private void onHit(HitResult p_37488_, CallbackInfo ci) {
        if (getItem().getItem() == JerotesVillageItems.IRISHTAIL_CHICKEN_EGG.get() || IrishtailChickenEntity.isIrishtailChicken(getItem())) {
            if (!this.level().isClientSide) {
                if (this.random.nextInt(8) == 0) {
                    int n = 1;
                    if (this.random.nextInt(32) == 0) {
                        n = 4;
                    }
                    for(int n2 = 0; n2 < n; ++n2) {
                        Chicken chicken = JerotesVillageEntityType.IRISHTAIL_CHICKEN.get().create(this.level());
                        if (chicken != null) {
                            chicken.setAge(-24000);
                            chicken.moveTo(this.getX(), this.getY(), this.getZ(), this.getYRot(), 0.0F);
                            this.level().addFreshEntity(chicken);
                        }
                    }
                }
                this.level().broadcastEntityEvent(this, (byte)3);
                this.discard();
            }
            ci.cancel();
        }
    }
}