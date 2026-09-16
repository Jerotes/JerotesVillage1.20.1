package com.jerotes.jerotesvillage.mixin;

import com.jerotes.jerotesvillage.init.JerotesVillageVillagerProfessions;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.behavior.VillagerPanicTrigger;
import net.minecraft.world.entity.npc.Villager;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(VillagerPanicTrigger.class)
public abstract class VillagerPanicTriggerMixin {
    @Inject(method = "canStillUse*", at = @At(value = "HEAD"), cancellable = true)
    public void canStillUse(ServerLevel serverLevel, Villager villager, long L, CallbackInfoReturnable<Boolean> cir) {
        if (villager.getVillagerData().getProfession() == JerotesVillageVillagerProfessions.VILLAGER_CONSCRIPTION.get() && villager.getVillagerData().getLevel() > 0) {
            cir.setReturnValue(false);
            cir.cancel();
        }
    }

    @Inject(method = "start*", at = @At(value = "HEAD"), cancellable = true)
    public void start(ServerLevel serverLevel, Villager villager, long p_24696_, CallbackInfo ci) {
        if (villager.getVillagerData().getProfession() == JerotesVillageVillagerProfessions.VILLAGER_CONSCRIPTION.get() && villager.getVillagerData().getLevel() > 0) {
            ci.cancel();
        }
    }
    @Inject(method = "isHurt", at = @At(value = "HEAD"), cancellable = true)
    private static void isHurt(LivingEntity livingEntity, CallbackInfoReturnable<Boolean> cir) {
        if (livingEntity instanceof Villager villager) {
            if (villager.getVillagerData().getProfession() == JerotesVillageVillagerProfessions.VILLAGER_CONSCRIPTION.get() && villager.getVillagerData().getLevel() > 0) {
                cir.setReturnValue(false);
                cir.cancel();
            }
        }
    }
    @Inject(method = "hasHostile", at = @At(value = "HEAD"), cancellable = true)
    private static void hasHostile(LivingEntity livingEntity, CallbackInfoReturnable<Boolean> cir) {
        if (livingEntity instanceof Villager villager) {
            if (villager.getVillagerData().getProfession() == JerotesVillageVillagerProfessions.VILLAGER_CONSCRIPTION.get() && villager.getVillagerData().getLevel() > 0) {
                cir.setReturnValue(false);
                cir.cancel();
            }
        }
    }
}