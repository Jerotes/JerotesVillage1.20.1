package com.jerotes.jerotesvillage.entity.Monster.IllagerFaction;

import com.jerotes.jerotes.entity.Interface.InventoryEntity;
import com.jerotes.jerotes.entity.Mob.HumanEntity;
import com.jerotes.jerotes.goal.JerotesAttackAvoidEntityGoal;
import com.jerotes.jerotes.util.EntityFactionFind;
import com.jerotes.jerotesvillage.init.JerotesVillageSoundEvents;
import com.jerotes.jerotesvillage.spell.OtherSpellFind;
import com.jerotes.jerotesvillage.entity.Boss.OminousBannerProjectionEntity;
import com.jerotes.jerotesvillage.entity.Other.MagicAboutMob.BitterColdAltarEntity;
import com.jerotes.jerotes.entity.Interface.SpellUseEntity;
import com.jerotes.jerotesvillage.init.JerotesVillageEntityType;
import com.jerotes.jerotesvillage.init.JerotesVillageParticleTypes;
import com.jerotes.jerotes.init.JerotesSoundEvents;
import net.minecraft.core.BlockPos;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.syncher.EntityDataAccessor;
import net.minecraft.network.syncher.EntityDataSerializers;
import net.minecraft.network.syncher.SynchedEntityData;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.util.RandomSource;
import net.minecraft.world.Difficulty;
import net.minecraft.world.DifficultyInstance;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.*;
import net.minecraft.world.entity.ai.attributes.AttributeSupplier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.ai.goal.FloatGoal;
import net.minecraft.world.entity.ai.goal.LookAtPlayerGoal;
import net.minecraft.world.entity.ai.goal.OpenDoorGoal;
import net.minecraft.world.entity.ai.goal.RandomStrollGoal;
import net.minecraft.world.entity.ai.goal.target.HurtByTargetGoal;
import net.minecraft.world.entity.ai.goal.target.NearestAttackableTargetGoal;
import net.minecraft.world.entity.animal.IronGolem;
import net.minecraft.world.entity.monster.Monster;
import net.minecraft.world.entity.monster.RangedAttackMob;
import net.minecraft.world.entity.monster.SpellcasterIllager;
import net.minecraft.world.entity.npc.AbstractVillager;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.entity.raid.Raider;
import net.minecraft.world.level.Level;
import net.minecraft.world.scores.PlayerTeam;

import javax.annotation.Nullable;
import java.util.List;
import java.util.UUID;

public class TeleporterEntity extends SpellIllagerEntity implements SpellUseEntity {
    private static final EntityDataAccessor<Integer> TELEPORT_USE = SynchedEntityData.defineId(TeleporterEntity.class, EntityDataSerializers.INT);

    public TeleporterEntity(EntityType<? extends TeleporterEntity> entityType, Level level) {
        super(entityType, level);
        this.xpReward = 25;
        this.setCanPickUpLoot(false);
    }

    @Override
    protected void populateDefaultEquipmentSlots(RandomSource randomSource, DifficultyInstance difficultyInstance) {
    }

    public static AttributeSupplier.Builder createAttributes() {
        AttributeSupplier.Builder builder = Monster.createMonsterAttributes();
        builder = builder.add(Attributes.MOVEMENT_SPEED, 0.34);
        builder = builder.add(Attributes.MAX_HEALTH, 48);
        builder = builder.add(Attributes.ARMOR, 2);
        builder = builder.add(Attributes.ATTACK_DAMAGE, 2);
        builder = builder.add(Attributes.FOLLOW_RANGE, 32);
        return builder;
    }

    @Override
    protected void registerGoals() {
        super.registerGoals();
        this.goalSelector.addGoal(0, new OpenDoorGoal(this, true));
        this.goalSelector.addGoal(0, new FloatGoal(this));
        this.goalSelector.addGoal(1, new JerotesAttackAvoidEntityGoal<LivingEntity>(this, LivingEntity.class, 8.0f, 0.6, 1.0));
        this.goalSelector.addGoal(2, new RandomStrollGoal(this, 0.6));
        this.goalSelector.addGoal(3, new LookAtPlayerGoal(this, Player.class, 3.0f, 1.0f));
        this.goalSelector.addGoal(3, new LookAtPlayerGoal(this, HumanEntity.class, 3.0f, 1.0f));
        this.goalSelector.addGoal(4, new LookAtPlayerGoal(this, Mob.class, 8.0f));
        this.targetSelector.addGoal(1, new HurtByTargetGoal(this, Raider.class).setAlertOthers(new Class[0]));
        this.targetSelector.addGoal(2, new NearestAttackableTargetGoal<Player>(this, Player.class, false){
            @Override
            public boolean canUse() {
                if (TeleporterEntity.this.level().getDifficulty() == Difficulty.PEACEFUL) {
                    return false;
                }
                return super.canUse();
            }
        });
        this.targetSelector.addGoal(3, new NearestAttackableTargetGoal<AbstractVillager>(this, AbstractVillager.class, false));
        this.targetSelector.addGoal(3, new NearestAttackableTargetGoal<HumanEntity>(this, HumanEntity.class, false));
        this.targetSelector.addGoal(3, new NearestAttackableTargetGoal<IronGolem>(this, IronGolem.class, false));
    }

    @Override
    protected SoundEvent getAmbientSound() {
        return JerotesVillageSoundEvents.TELEPORTER_AMBIENT;
    }
    @Override
    protected SoundEvent getDeathSound() {
        return JerotesVillageSoundEvents.TELEPORTER_DEATH;
    }
    @Override
    protected SoundEvent getHurtSound(DamageSource damageSource) {
        return JerotesVillageSoundEvents.TELEPORTER_HURT;
    }
    @Override
    public SoundEvent getCelebrateSound() {
        return JerotesVillageSoundEvents.TELEPORTER_CHEER;
    }
    @Override
    protected SoundEvent getCastingSoundEvent() {
        return JerotesVillageSoundEvents.TELEPORTER_TELEPORT;
    }
    @Override
    public IllagerSpell customSpell() {
        return IllagerSpell.FANGS;
    }

    public int spellLevel = 1;
    @Override
    public int getSpellLevel() {
        return this.spellLevel;
    }
    @Nullable
    private LivingEntity teamFind;
    @Nullable
    private UUID teamFindUUID;
    @Nullable
    public LivingEntity getTeamFind() {
        Entity entity;
        if (this.teamFind == null && this.teamFindUUID != null && this.level() instanceof ServerLevel && (entity = ((ServerLevel)this.level()).getEntity(this.teamFindUUID)) instanceof LivingEntity) {
            this.teamFind = (LivingEntity)entity;
        }
        return this.teamFind;
    }
    public void setTeamFind(@Nullable LivingEntity livingEntity) {
        this.teamFind = livingEntity;
        this.teamFindUUID = livingEntity == null ? null : livingEntity.getUUID();
    }
    public void setTeleportUse(int n){
        this.getEntityData().set(TELEPORT_USE, n);
    }
    public int getTeleportUse(){
        return this.getEntityData().get(TELEPORT_USE);
    }
    @Override
    public void addAdditionalSaveData(CompoundTag compoundTag) {
        super.addAdditionalSaveData(compoundTag);
        compoundTag.putInt("TeleportUse", this.getTeleportUse());
        if (this.teamFindUUID != null) {
            compoundTag.putUUID("TeamFind", this.teamFindUUID);
        }
        compoundTag.putInt("SpellLevel", this.spellLevel);
        this.writeInventoryToTag(compoundTag);
    }
    @Override
    public void readAdditionalSaveData(CompoundTag compoundTag) {
        super.readAdditionalSaveData(compoundTag);
        this.spellLevel = compoundTag.getInt("SpellLevel");
        this.setTeleportUse(compoundTag.getInt("TeleportUse"));
        if (compoundTag.hasUUID("TeamFind")) {
            this.teamFindUUID = compoundTag.getUUID("TeamFind");
        }
        this.readInventoryFromTag(compoundTag);
    }
    @Override
    protected void defineSynchedData() {
        super.defineSynchedData();
        this.getEntityData().define(TELEPORT_USE, 0);
    }

    @Override
    public void aiStep() {
        super.aiStep();
        //传送类型
        if (this.getSpellTick() <= 0){

                //1-传送自身
                if (this.getTeleportUse() == 1 && this.getTarget() != null) {
                    OtherSpellFind.TeleportStoneTeleport(this, this, this.getTarget(), this.getSpellLevel() * 16, false, false);
                    if (!this.isSilent()) {
                        this.level().playSound(null, this.getX(), this.getY(), this.getZ(), JerotesVillageSoundEvents.TELEPORTER_TELEPORT, this.getSoundSource(), 5.0f, 0.8f + this.random.nextFloat() * 0.4f);
                    }
                }
                //2-传送目标
                else if (this.getTeleportUse() == 2 && this.getTarget() != null && this.getTarget().getHealth() < this.getHealth() * 5) {
                    OtherSpellFind.TeleportStoneTeleport(this, this.getTarget(), this, this.getSpellLevel() * 12, false, false);
                    if (!this.isSilent()) {
                        this.level().playSound(null, this.getX(), this.getY(), this.getZ(), JerotesVillageSoundEvents.TELEPORTER_TELEPORT, this.getSoundSource(), 5.0f, 0.8f + this.random.nextFloat() * 0.4f);
                        this.level().playSound(null, this.getTarget().getX(), this.getTarget().getY(), this.getTarget().getZ(), JerotesSoundEvents.TELEPORT, this.getTarget().getSoundSource(), 5.0f, 0.8f + this.random.nextFloat() * 0.4f);
                    }
                }
                //3-位置交换
                else if (this.getTeleportUse() == 3 && this.getTarget() != null && this.getTarget().getHealth() < this.getHealth() * 5) {
                    OtherSpellFind.TeleportStoneTeleport(this, this.getTarget(), this.getTarget(), this.getSpellLevel() * 16, true, true);
                    if (!this.isSilent()) {
                        this.level().playSound(null, this.getX(), this.getY(), this.getZ(), JerotesVillageSoundEvents.TELEPORTER_TELEPORT, this.getSoundSource(), 5.0f, 0.8f + this.random.nextFloat() * 0.4f);
                        this.level().playSound(null, this.getTarget().getX(), this.getTarget().getY(), this.getTarget().getZ(), JerotesSoundEvents.TELEPORT, this.getTarget().getSoundSource(), 5.0f, 0.8f + this.random.nextFloat() * 0.4f);
                    }
                }
                //4-传送队友
                else if (this.getTeleportUse() == 4 && this.getTarget() != null && this.getTeamFind() != null && this.getTeamFind().getHealth() < this.getHealth() * 5) {
                    boolean near = true;
                    float distances = 2f;
                    if (this.getTeamFind() instanceof RangedAttackMob && !InventoryEntity.isMeleeWeapon(this.getTeamFind().getMainHandItem()) || this.getTeamFind() instanceof SpellcasterIllager || this.getTeamFind().getHealth() < this.getTeamFind().getMaxHealth() / 5 || this.getTeamFind() instanceof Mob mob && mob.getTarget() != null && mob.getTarget() != this.getTarget() && this.getRandom().nextFloat() > 0.95f) {
                        near = false;
                        distances = this.getSpellLevel() * 12;
                    }
                    if (this.getTeamFind() instanceof Mob mob && mob.getTarget() != null) {
                        OtherSpellFind.TeleportStoneTeleport(this, this.getTeamFind(), mob.getTarget(), distances, false, near);
                    }
                    else {
                        OtherSpellFind.TeleportStoneTeleport(this, this.getTeamFind(), this.getTarget(), distances, false, near);
                    }
                    if (!this.isSilent()) {
                        this.level().playSound(null, this.getX(), this.getY(), this.getZ(), JerotesVillageSoundEvents.TELEPORTER_TELEPORT, this.getSoundSource(), 5.0f, 0.8f + this.random.nextFloat() * 0.4f);
                        if (this.getTeamFind() != null) {
                            this.level().playSound(null, this.getTeamFind().getX(), this.getTeamFind().getY(), this.getTeamFind().getZ(), JerotesSoundEvents.TELEPORT, this.getTeamFind().getSoundSource(), 5.0f, 0.8f + this.random.nextFloat() * 0.4f);
                        }
                    }
                }
                //5-将死逃离
                else if (this.getTeleportUse() == 5) {
                    if (this.level() instanceof ServerLevel serverLevel) {
                        PlayerTeam teams = (PlayerTeam) this.getTeam();
                        float f = this.getRandom().nextFloat();
                        Mob illager;
                        if (f < 0.25f) {
                            illager = JerotesVillageEntityType.EXPLORER.get().spawn(serverLevel, BlockPos.containing(this.getX(), this.getY(), this.getZ()), MobSpawnType.MOB_SUMMONED);
                        }
                        else if (f < 0.5f) {
                            illager = JerotesVillageEntityType.EXECUTIONER.get().spawn(serverLevel, BlockPos.containing(this.getX(), this.getY(), this.getZ()), MobSpawnType.MOB_SUMMONED);
                        }
                        else if (f < 0.75f) {
                            illager = JerotesVillageEntityType.JAVELIN_THROWER.get().spawn(serverLevel, BlockPos.containing(this.getX(), this.getY(), this.getZ()), MobSpawnType.MOB_SUMMONED);
                        }
                        else {
                            illager = JerotesVillageEntityType.DEFECTOR.get().spawn(serverLevel, BlockPos.containing(this.getX(), this.getY(), this.getZ()), MobSpawnType.MOB_SUMMONED);
                        }
                        if (illager != null) {
                            if (teams != null) {
                                serverLevel.getScoreboard().addPlayerToTeam(illager.getStringUUID(), teams);
                            }
                            if (this.getTarget() != null) {
                                illager.setTarget(this.getTarget());
                            }
                            serverLevel.sendParticles(JerotesVillageParticleTypes.TARGET.get(), illager.getX(), illager.getY() + 0.1, illager.getZ(), 0, 0.0, 0.0, 0.0, 0.0);
                        }
                        if (!this.isInvisible()) {
                            serverLevel.sendParticles(JerotesVillageParticleTypes.TELEPORT_STONE_TELEPORT_DISPLAY.get(), this.getX(), this.getBoundingBox().maxY + 0.5, this.getZ(), 0, 0.0, 0.0, 0.0, 0.0);
                        }
                    }
                    if (!this.isSilent()) {
                        this.level().playSound(null, this.getX(), this.getY(), this.getZ(), JerotesVillageSoundEvents.TELEPORTER_AWAY, this.getSoundSource(), 5.0f, 0.8f + this.random.nextFloat() * 0.4f);
                    }
                    this.remove(RemovalReason.CHANGED_DIMENSION);
                }
            if (!this.level().isClientSide()) {
                this.setTeleportUse(0);
                this.setTeamFind(null);
            }
        }
        //传送粒子
        if (this.getSpellTick() > 0){
            if (this.level() instanceof ServerLevel serverLevel) {
                if ((this.getTeleportUse() == 2 || this.getTeleportUse() == 3) && this.getTarget() != null) {
                    this.getLookControl().setLookAt(this.getTarget(), 360.0f, 360.0f);
                    this.lookAt(this.getTarget(), 360.0f, 360.0f);
                    for (int i = 0; i < 32; ++i) {
                        serverLevel.sendParticles(ParticleTypes.PORTAL, this.getTarget().getRandomX(0.5), this.getTarget().getRandomY(), this.getTarget().getRandomZ(0.5), 0, this.getTarget().getRandom().nextGaussian(), 0.0, this.getTarget().getRandom().nextGaussian(), 0);
                    }
                }
                //4-传送队友
                if (this.getTeleportUse() == 4 && this.getTeamFind() != null) {
                    this.getLookControl().setLookAt(this.getTeamFind(), 360.0f, 360.0f);
                    this.lookAt(this.getTeamFind(), 360.0f, 360.0f);
                    for (int i = 0; i < 32; ++i) {
                        serverLevel.sendParticles(ParticleTypes.PORTAL, this.getTeamFind().getRandomX(0.5), this.getTeamFind().getRandomY(), this.getTeamFind().getRandomZ(0.5), 0, this.getTeamFind().getRandom().nextGaussian(), 0.0, this.getTeamFind().getRandom().nextGaussian(), 0);
                    }
                }
            }
            //粒子
            for (int i = 0; i < 32; ++i) {
                this.level().addParticle(ParticleTypes.PORTAL, this.getX(), this.getY() + this.getRandom().nextDouble() * 2.0, this.getZ(), this.getRandom().nextGaussian(), 0.0, this.getRandom().nextGaussian());
            }
            //buff
            if (!this.level().isClientSide) {
                this.addEffect(new MobEffectInstance(MobEffects.MOVEMENT_SLOWDOWN, 5, 0));
                this.addEffect(new MobEffectInstance(MobEffects.WEAKNESS, 5, 0));
                this.addEffect(new MobEffectInstance(MobEffects.HUNGER, 5, 0));
                this.addEffect(new MobEffectInstance(MobEffects.DARKNESS, 5, 0));
            }
        }
        if (!this.isCastingSpell() && this.getSpellOneTick() <= 0 && !this.isNoAi()) {
            //队伍
            List<LivingEntity> listRaider = this.level().getEntitiesOfClass(LivingEntity.class, this.getBoundingBox().inflate(32.0, 32.0, 32.0));
            listRaider.removeIf(entity -> this.getTarget() == entity || entity == this || entity instanceof Mob mob && mob.getTarget() == this || !(EntityFactionFind.isRaider(entity) || (entity.isAlliedTo(this))));
            listRaider.removeIf(entity -> entity.getHealth() >= this.getHealth() * 5);
            listRaider.removeIf(entity -> entity instanceof OminousBannerProjectionEntity);
            listRaider.removeIf(entity -> entity instanceof BitterColdAltarEntity);
            //5-将死逃离
            if (this.getHealth() < this.getMaxHealth() / 3 && this.getTarget() != null || listRaider.isEmpty()) {
                if (!this.level().isClientSide()) {
                    this.setSpellTick(120);
                    this.setTeleportUse(5);
                    this.setSpellOneTick(120);
                }
                this.level().broadcastEntityEvent(this, (byte) 16);
            }
            //1-传送自身
            else if (this.getTarget() != null && (this.distanceTo(this.getTarget()) < 4 || this.getHealth() < this.getMaxHealth() && this.distanceTo(this.getTarget()) < 6)  && this.getRandom().nextFloat() < 0.8f) {
                if (!this.level().isClientSide()) {
                    this.setSpellTick(60);
                    this.setTeleportUse(1);
                    this.setSpellOneTick(120);
                }
                this.level().broadcastEntityEvent(this, (byte) 12);
            }
            //4-传送队友
            else if (this.getTarget() != null && this.getRandom().nextFloat() < 0.65f && !listRaider.isEmpty() && this.getTeamFind() == null) {
                if (!this.level().isClientSide()) {
                    this.setSpellTick(60);
                    this.setTeleportUse(4);
                    for (LivingEntity livingEntityEffect : listRaider) {
                        if (this.getRandom().nextFloat() <= 1f / listRaider.size()) {
                            this.setTeamFind(livingEntityEffect);
                        }
                    }
                    if (this.getTeamFind() == null) {
                        for (LivingEntity livingEntityEffect : listRaider) {
                            this.setTeamFind(livingEntityEffect);
                            break;
                        }
                    }
                    this.setSpellOneTick(120);
                }
                this.level().broadcastEntityEvent(this, (byte) 15);
            }
            //2-传送目标
            else if (this.getTarget() != null && this.getRandom().nextFloat() < 0.6f && (this.distanceTo(this.getTarget()) < 18) && this.getTarget().getHealth() < this.getHealth() * 5) {
                if (!this.level().isClientSide()) {
                    this.setSpellTick(60);
                    this.setTeleportUse(2);
                    this.setSpellOneTick(120);
                }
                this.level().broadcastEntityEvent(this, (byte) 13);
            }
            //3-位置交换
            else if (this.getTarget() != null && this.getTarget().getHealth() < this.getHealth() * 5) {
                if (!this.level().isClientSide()) {
                    this.setSpellTick(60);
                    this.setTeleportUse(3);
                    this.setSpellOneTick(120);
                }
                this.level().broadcastEntityEvent(this, (byte) 14);
            }
        }
    }

    @Override
    public boolean hurt(DamageSource damageSource, float amount) {
        if (isInvulnerableTo(damageSource)) {
            return super.hurt(damageSource, amount);
        }
        if (this.isCastingSpell()) {
            if (!this.level().isClientSide()) {
                this.setSpellTick(0);
                this.setTeleportUse(0);
                this.setTeamFind(null);
            }
            this.level().broadcastEntityEvent(this, (byte) 17);
            return super.hurt(damageSource, amount * 2.5f);
        }
        return super.hurt(damageSource, amount);
    }
}