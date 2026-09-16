package com.jerotes.jerotesvillage.mixin;

import com.jerotes.jerotesvillage.init.JerotesVillageVillagerProfessions;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.sensing.NearestVisibleLivingEntitySensor;
import net.minecraft.world.entity.ai.sensing.VillagerHostilesSensor;
import net.minecraft.world.entity.npc.Villager;
import net.minecraft.world.entity.npc.VillagerDataHolder;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(VillagerHostilesSensor.class)
public abstract class VillagerHostilesSensorMixin extends NearestVisibleLivingEntitySensor {
    @Inject(method = "isMatchingEntity", at = @At(value = "HEAD"), cancellable = true)
    public void isMatchingEntity(LivingEntity livingEntity, LivingEntity livingEntity2, CallbackInfoReturnable<Boolean> cir) {
        if (livingEntity instanceof VillagerDataHolder villager && villager.getVillagerData().getProfession() == JerotesVillageVillagerProfessions.VILLAGER_CONSCRIPTION.get() && villager.getVillagerData().getLevel() > 0) {
            cir.setReturnValue(false);
            cir.cancel();
        }
    }
    @Inject(method = "isClose", at = @At(value = "HEAD"), cancellable = true)
    private void isClose(LivingEntity livingEntity, LivingEntity livingEntity2, CallbackInfoReturnable<Boolean> cir) {
        if (livingEntity instanceof Villager villager) {
            if (villager.getVillagerData().getProfession() == JerotesVillageVillagerProfessions.VILLAGER_CONSCRIPTION.get() && villager.getVillagerData().getLevel() > 0) {
                cir.setReturnValue(false);
                cir.cancel();
            }
        }
    }
    @Inject(method = "isHostile", at = @At(value = "HEAD"), cancellable = true)
    private void isHostile(LivingEntity livingEntity, CallbackInfoReturnable<Boolean> cir) {
        if (livingEntity instanceof Villager villager) {
            if (villager.getVillagerData().getProfession() == JerotesVillageVillagerProfessions.VILLAGER_CONSCRIPTION.get() && villager.getVillagerData().getLevel() > 0) {
                cir.setReturnValue(false);
                cir.cancel();
            }
        }
    }
}