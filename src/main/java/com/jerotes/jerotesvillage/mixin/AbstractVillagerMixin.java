package com.jerotes.jerotesvillage.mixin;

import com.jerotes.jerotesvillage.init.JerotesVillageVillagerProfessions;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.world.entity.AgeableMob;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.npc.AbstractVillager;
import net.minecraft.world.entity.npc.VillagerDataHolder;
import net.minecraft.world.level.Level;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(AbstractVillager.class)
public abstract class AbstractVillagerMixin extends AgeableMob {
    protected AbstractVillagerMixin(EntityType<? extends AgeableMob> entityType, Level level) {
        super(entityType, level);
    }
    @Inject(method = "getNotifyTradeSound", at = @At("HEAD"), cancellable = true)
    public void getNotifyTradeSound(CallbackInfoReturnable<SoundEvent> cir) {
        if (this instanceof VillagerDataHolder villagerDataHolder && villagerDataHolder.getVillagerData().getProfession() == JerotesVillageVillagerProfessions.VILLAGER_PIGLIN.get()) {
            cir.setReturnValue(SoundEvents.PIGLIN_CELEBRATE);
        }
        else if (this instanceof VillagerDataHolder villagerDataHolder && villagerDataHolder.getVillagerData().getProfession() == JerotesVillageVillagerProfessions.VILLAGER_ILLAGER.get()) {
            cir.setReturnValue(SoundEvents.PILLAGER_CELEBRATE);
        }
        else if (this instanceof VillagerDataHolder villagerDataHolder && villagerDataHolder.getVillagerData().getProfession() == JerotesVillageVillagerProfessions.VILLAGER_WITCH.get()) {
            cir.setReturnValue(SoundEvents.WITCH_CELEBRATE);
        }
    }

    @Inject(method = "getTradeUpdatedSound", at = @At("HEAD"), cancellable = true)
    protected void getTradeUpdatedSound(boolean p_35323_, CallbackInfoReturnable<SoundEvent> cir) {
        if (this instanceof VillagerDataHolder villagerDataHolder && villagerDataHolder.getVillagerData().getProfession() == JerotesVillageVillagerProfessions.VILLAGER_PIGLIN.get()) {
            cir.setReturnValue(p_35323_ ? SoundEvents.PIGLIN_CELEBRATE : SoundEvents.PIGLIN_ANGRY);
        }
        else if (this instanceof VillagerDataHolder villagerDataHolder && villagerDataHolder.getVillagerData().getProfession() == JerotesVillageVillagerProfessions.VILLAGER_ILLAGER.get()) {
            cir.setReturnValue(p_35323_ ? SoundEvents.PILLAGER_CELEBRATE : SoundEvents.PILLAGER_HURT);
        }
        else if (this instanceof VillagerDataHolder villagerDataHolder && villagerDataHolder.getVillagerData().getProfession() == JerotesVillageVillagerProfessions.VILLAGER_WITCH.get()) {
            cir.setReturnValue(p_35323_ ? SoundEvents.WITCH_CELEBRATE : SoundEvents.WITCH_HURT);
        }
    }

    @Inject(method = "playCelebrateSound", at = @At("HEAD"), cancellable = true)
    public void playCelebrateSound(CallbackInfo ci) {
        if (this instanceof VillagerDataHolder villagerDataHolder && villagerDataHolder.getVillagerData().getProfession() == JerotesVillageVillagerProfessions.VILLAGER_PIGLIN.get()) {
            this.playSound(SoundEvents.PIGLIN_CELEBRATE, this.getSoundVolume(), this.getVoicePitch());
            ci.cancel();
        }
        else if (this instanceof VillagerDataHolder villagerDataHolder && villagerDataHolder.getVillagerData().getProfession() == JerotesVillageVillagerProfessions.VILLAGER_ILLAGER.get()) {
            this.playSound(SoundEvents.PILLAGER_CELEBRATE, this.getSoundVolume(), this.getVoicePitch());
            ci.cancel();
        }
        else if (this instanceof VillagerDataHolder villagerDataHolder && villagerDataHolder.getVillagerData().getProfession() == JerotesVillageVillagerProfessions.VILLAGER_WITCH.get()) {
            this.playSound(SoundEvents.WITCH_CELEBRATE, this.getSoundVolume(), this.getVoicePitch());
            ci.cancel();
        }

    }
}