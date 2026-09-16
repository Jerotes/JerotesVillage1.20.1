package com.jerotes.jerotesvillage.mixin;

import com.jerotes.jerotesvillage.JerotesVillage;
import com.jerotes.jerotesvillage.event.RelationshipEvent;
import com.jerotes.jerotesvillage.init.JerotesVillageVillagerProfessions;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.world.DifficultyInstance;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.MobSpawnType;
import net.minecraft.world.entity.SpawnGroupData;
import net.minecraft.world.entity.monster.Zombie;
import net.minecraft.world.entity.monster.ZombieVillager;
import net.minecraft.world.entity.npc.VillagerData;
import net.minecraft.world.entity.npc.VillagerProfession;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.ServerLevelAccessor;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import javax.annotation.Nullable;
import java.util.UUID;

@Mixin(ZombieVillager.class)
public abstract class ZombieVillagerMixin extends Zombie {
    public ZombieVillagerMixin(EntityType<? extends Zombie> entityType, Level level) {
        super(entityType, level);
    }

    @Shadow
    public abstract VillagerData getVillagerData();

    @Shadow public abstract void setVillagerData(VillagerData p_34376_);

    @Shadow @Nullable private UUID conversionStarter;

    @Inject(method = "getAmbientSound", at = @At(value = "HEAD"), cancellable = true)
    public void getAmbientSound(CallbackInfoReturnable<SoundEvent> cir) {
        if (getVillagerData().getProfession() == JerotesVillageVillagerProfessions.VILLAGER_PIGLIN.get()) {
            cir.setReturnValue(SoundEvents.ZOMBIFIED_PIGLIN_AMBIENT);
        }
    }

    @Inject(method = "getHurtSound", at = @At(value = "HEAD"), cancellable = true)
    public void getHurtSound(CallbackInfoReturnable<SoundEvent> cir) {
        if (getVillagerData().getProfession() == JerotesVillageVillagerProfessions.VILLAGER_PIGLIN.get()) {
            cir.setReturnValue(SoundEvents.ZOMBIFIED_PIGLIN_HURT);
        }
    }

    @Inject(method = "getDeathSound", at = @At(value = "HEAD"), cancellable = true)
    public void getDeathSound(CallbackInfoReturnable<SoundEvent> cir) {
        if (getVillagerData().getProfession() == JerotesVillageVillagerProfessions.VILLAGER_PIGLIN.get()) {
            cir.setReturnValue(SoundEvents.ZOMBIFIED_PIGLIN_DEATH);
        }
    }


    @Inject(method = "finalizeSpawn", at = @At(value = "HEAD"))
    public void finalizeSpawn(ServerLevelAccessor serverLevelAccessor, DifficultyInstance difficultyInstance, MobSpawnType mobSpawnType, SpawnGroupData spawnGroupData, CompoundTag compoundTag, CallbackInfoReturnable<SpawnGroupData> cir) {
        if (mobSpawnType != MobSpawnType.CONVERSION) {
            if (getVillagerData().getProfession().toString().contains(JerotesVillage.MODID)) {
                setVillagerData(this.getVillagerData().setProfession(VillagerProfession.NONE));
            }
        }
    }


    @Inject(method = "finishConversion", at = @At(value = "HEAD"))
    public void finishConversion(ServerLevel serverLevel, CallbackInfo ci) {
        if (this.conversionStarter != null && (serverLevel.getPlayerByUUID(this.conversionStarter)) instanceof ServerPlayer serverPlayer) {
            RelationshipEvent.AddCopperCarvedCompanyRelationship(serverPlayer, 10);
        }
    }
}