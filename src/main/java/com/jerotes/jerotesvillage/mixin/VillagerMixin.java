package com.jerotes.jerotesvillage.mixin;

import com.jerotes.jerotes.entity.Interface.SpellUseEntity;
import com.jerotes.jerotesvillage.event.VillagerEvent;
import com.jerotes.jerotesvillage.init.JerotesVillageItems;
import com.jerotes.jerotesvillage.init.JerotesVillageVillagerProfessions;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.npc.*;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(Villager.class)
public abstract class VillagerMixin extends AbstractVillager implements SpellUseEntity {
    public VillagerMixin(EntityType<? extends AbstractVillager> entityType, Level level) {
        super(entityType, level);
    }

    public int getSpellLevel() {
        if (getVillagerData().getProfession() == JerotesVillageVillagerProfessions.VILLAGER_MAGICIAN.get() && this instanceof VillagerDataHolder villagerDataHolder) {
            int spellLevel = 1;
            if (villagerDataHolder.getVillagerData().getLevel() >= 2) {
                spellLevel = 2;
            }
            if (villagerDataHolder.getVillagerData().getLevel() >= 4) {
                spellLevel = 3;
            }
            return spellLevel;
        }
        return 0;
    }
    @Shadow
    public abstract VillagerData getVillagerData();

    @Shadow protected abstract void setUnhappy();

    @Inject(method = "getAmbientSound", at = @At(value = "INVOKE", target = "Lnet/minecraft/world/entity/npc/Villager;isTrading()Z"), cancellable = true)
    public void getAmbientSound(CallbackInfoReturnable<SoundEvent> cir) {
        if (getVillagerData().getProfession() == JerotesVillageVillagerProfessions.VILLAGER_PIGLIN.get()) {
            cir.setReturnValue(SoundEvents.PIGLIN_AMBIENT);
        }
        else if (getVillagerData().getProfession() == JerotesVillageVillagerProfessions.VILLAGER_ILLAGER.get()) {
            cir.setReturnValue(SoundEvents.PILLAGER_AMBIENT);
        }
        else if (getVillagerData().getProfession() == JerotesVillageVillagerProfessions.VILLAGER_WITCH.get()) {
            cir.setReturnValue(SoundEvents.WITCH_AMBIENT);
        }
    }

    @Inject(method = "getHurtSound", at = @At(value = "HEAD"), cancellable = true)
    public void getHurtSound(CallbackInfoReturnable<SoundEvent> cir) {
        if (getVillagerData().getProfession() == JerotesVillageVillagerProfessions.VILLAGER_PIGLIN.get()) {
            cir.setReturnValue(SoundEvents.PIGLIN_HURT);
        }
        else if (getVillagerData().getProfession() == JerotesVillageVillagerProfessions.VILLAGER_ILLAGER.get()) {
            cir.setReturnValue(SoundEvents.PILLAGER_HURT);
        }
        else if (getVillagerData().getProfession() == JerotesVillageVillagerProfessions.VILLAGER_WITCH.get()) {
            cir.setReturnValue(SoundEvents.WITCH_HURT);
        }
    }

    @Inject(method = "getDeathSound", at = @At(value = "HEAD"), cancellable = true)
    public void getDeathSound(CallbackInfoReturnable<SoundEvent> cir) {
        if (getVillagerData().getProfession() == JerotesVillageVillagerProfessions.VILLAGER_PIGLIN.get()) {
            cir.setReturnValue(SoundEvents.PIGLIN_DEATH);
        }
        else if (getVillagerData().getProfession() == JerotesVillageVillagerProfessions.VILLAGER_ILLAGER.get()) {
            cir.setReturnValue(SoundEvents.PILLAGER_DEATH);
        }
        else if (getVillagerData().getProfession() == JerotesVillageVillagerProfessions.VILLAGER_WITCH.get()) {
            cir.setReturnValue(SoundEvents.WITCH_DEATH);
        }
    }

    @Inject(method = "setUnhappy", at = @At(value = "HEAD"), cancellable = true)
    public void setUnhappy(CallbackInfo ci) {
        if (getVillagerData().getProfession() == JerotesVillageVillagerProfessions.VILLAGER_PIGLIN.get()) {
            this.setUnhappyCounter(40);
            if (!this.level().isClientSide()) {
                this.playSound(SoundEvents.PIGLIN_ANGRY, this.getSoundVolume(), this.getVoicePitch());
            }
            ci.cancel();
        }
        else if (getVillagerData().getProfession() == JerotesVillageVillagerProfessions.VILLAGER_ILLAGER.get()) {
            this.setUnhappyCounter(40);
            if (!this.level().isClientSide()) {
                this.playSound(SoundEvents.PILLAGER_HURT, this.getSoundVolume(), this.getVoicePitch());
            }
            ci.cancel();
        }
        else if (getVillagerData().getProfession() == JerotesVillageVillagerProfessions.VILLAGER_WITCH.get()) {
            this.setUnhappyCounter(40);
            if (!this.level().isClientSide()) {
                this.playSound(SoundEvents.WITCH_HURT, this.getSoundVolume(), this.getVoicePitch());
            }
            ci.cancel();
        }
    }

    @Inject(method = "increaseMerchantCareer", at = @At(value = "TAIL"), cancellable = true)
    public void increaseMerchantCareer(CallbackInfo ci) {
        //应征官
        if (this.getVillagerData().getProfession() == JerotesVillageVillagerProfessions.VILLAGER_CONSCRIPTION.get() && this.getVillagerData().getLevel() > 0){
            VillagerEvent.Conscription(this, getVillagerData());
        }
    }
    @Inject(method = "mobInteract", at = @At(value = "HEAD"), cancellable = true)
    public void mobInteract(Player player, InteractionHand interactionHand, CallbackInfoReturnable<InteractionResult> cir) {
        ItemStack itemStack = player.getItemInHand(interactionHand);
        if (itemStack.is(JerotesVillageItems.VILLAGER_METAL_BASE.get()) && this.isAlive() && !this.isTrading() && !this.isSleeping()) {
            VillagerProfession armorer = VillagerProfession.ARMORER;
            VillagerProfession toolSmith = VillagerProfession.TOOLSMITH;
            VillagerProfession weaponSmith = VillagerProfession.WEAPONSMITH;
            VillagerProfession forger = JerotesVillageVillagerProfessions.VILLAGER_FORGER.get();
            boolean profession = this.getVillagerData().getProfession() == armorer || this.getVillagerData().getProfession() == toolSmith || this.getVillagerData().getProfession() == weaponSmith || this.getVillagerData().getProfession() == forger;
            if (!profession || this.isBaby()) {
                this.setUnhappy();
            }
            cir.setReturnValue(InteractionResult.PASS);
            cir.cancel();
        }

    }
}