package com.jerotes.jerotesvillage.entity.Monster.IllagerFaction;

import com.jerotes.jerotes.entity.Interface.EliteEntity;
import com.jerotes.jerotes.entity.Interface.SpellUseEntity;
import com.jerotes.jerotes.entity.Mob.HumanEntity;
import com.jerotes.jerotes.event.JerotesBossEvent;
import com.jerotes.jerotes.util.AttackFind;
import com.jerotes.jerotes.util.EntityFactionFind;
import com.jerotes.jerotesvillage.config.OtherMainConfig;
import com.jerotes.jerotesvillage.entity.Boss.OminousBannerProjectionEntity;
import com.jerotes.jerotesvillage.entity.Other.MagicAboutMob.UncleanTentacleEntity;
import com.jerotes.jerotesvillage.init.JerotesVillageGameRules;
import com.jerotes.jerotesvillage.init.JerotesVillageMobEffects;
import com.jerotes.jerotesvillage.init.JerotesVillageParticleTypes;
import com.jerotes.jerotesvillage.init.JerotesVillageSoundEvents;
import com.jerotes.jerotesvillage.spell.OtherSpellList;
import net.minecraft.ChatFormatting;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.chat.Component;
import net.minecraft.network.syncher.EntityDataAccessor;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.tags.DamageTypeTags;
import net.minecraft.util.RandomSource;
import net.minecraft.world.BossEvent;
import net.minecraft.world.Difficulty;
import net.minecraft.world.DifficultyInstance;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.AnimationState;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.ai.attributes.AttributeSupplier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.ai.goal.*;
import net.minecraft.world.entity.ai.goal.target.HurtByTargetGoal;
import net.minecraft.world.entity.ai.goal.target.NearestAttackableTargetGoal;
import net.minecraft.world.entity.animal.IronGolem;
import net.minecraft.world.entity.monster.Monster;
import net.minecraft.world.entity.monster.RangedAttackMob;
import net.minecraft.world.entity.npc.AbstractVillager;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.entity.raid.Raider;
import net.minecraft.world.level.Level;
import net.minecraftforge.common.ForgeMod;
import net.minecraftforge.fluids.FluidType;

import javax.annotation.Nullable;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class NecromancyWarlockEntity extends SpellIllagerEntity implements RangedAttackMob, SpellUseEntity, EliteEntity {
    public AnimationState idleAnimationState = new AnimationState();
    public AnimationState gearAnimationState = new AnimationState();
    public AnimationState screamAnimationState = new AnimationState();
    public AnimationState whisperAnimationState = new AnimationState();
    public AnimationState rainAnimationState = new AnimationState();
    public AnimationState fogAnimationState = new AnimationState();
    public AnimationState summonAnimationState = new AnimationState();
    public AnimationState deadAnimationState = new AnimationState();
    private final JerotesBossEvent bossEvent = new JerotesBossEvent(this, this.getUUID(), BossEvent.BossBarColor.RED, BossEvent.BossBarOverlay.NOTCHED_6, false);

    public NecromancyWarlockEntity(EntityType<? extends NecromancyWarlockEntity> entityType, Level level) {
        super(entityType, level);
        this.xpReward = 80;
        this.setCanPickUpLoot(false);
    }

    @Override
    public void startSeenByPlayer(ServerPlayer serverPlayer) {
        super.startSeenByPlayer(serverPlayer);
        if (JerotesVillageGameRules.JEROTES_VILLAGE_SOME_ELITE_HAS_BOSS_BAR != null && this.level().getLevelData().getGameRules().getBoolean(JerotesVillageGameRules.JEROTES_VILLAGE_SOME_ELITE_HAS_BOSS_BAR) && OtherMainConfig.EliteCanHasBossBar.contains(this.getEncodeId()))
            this.bossEvent.addPlayer(serverPlayer);
    }

    @Override
    public void stopSeenByPlayer(ServerPlayer serverPlayer) {
        super.stopSeenByPlayer(serverPlayer);
        if (JerotesVillageGameRules.JEROTES_VILLAGE_SOME_ELITE_HAS_BOSS_BAR != null && this.level().getLevelData().getGameRules().getBoolean(JerotesVillageGameRules.JEROTES_VILLAGE_SOME_ELITE_HAS_BOSS_BAR) && OtherMainConfig.EliteCanHasBossBar.contains(this.getEncodeId()))
            this.bossEvent.removePlayer(serverPlayer);
    }

    @Override
    public void customServerAiStep() {
        super.customServerAiStep();
        if (JerotesVillageGameRules.JEROTES_VILLAGE_SOME_ELITE_HAS_BOSS_BAR != null && this.level().getLevelData().getGameRules().getBoolean(JerotesVillageGameRules.JEROTES_VILLAGE_SOME_ELITE_HAS_BOSS_BAR) && OtherMainConfig.EliteCanHasBossBar.contains(this.getEncodeId()))
            this.bossEvent.setProgress(this.getHealth() / this.getMaxHealth());
    }

    @Override
    protected void populateDefaultEquipmentSlots(RandomSource randomSource, DifficultyInstance difficultyInstance) {
    }

    public static AttributeSupplier.Builder createAttributes() {
        AttributeSupplier.Builder builder = Monster.createMonsterAttributes();
        builder = builder.add(Attributes.MOVEMENT_SPEED, 0.35);
        builder = builder.add(Attributes.MAX_HEALTH, 95);
        builder = builder.add(Attributes.ARMOR, 8);
        builder = builder.add(Attributes.ATTACK_DAMAGE, 4);
        builder = builder.add(Attributes.FOLLOW_RANGE, 64);
        return builder;
    }

    @Override
    protected void registerGoals() {
        super.registerGoals();
        this.goalSelector.addGoal(0, new OpenDoorGoal(this, true));
        this.goalSelector.addGoal(0, new FloatGoal(this));
        this.goalSelector.addGoal(1, new RangedAttackGoal(this, 0.4, 120, 12.0F));
        this.goalSelector.addGoal(3, new RandomStrollGoal(this, 0.6));
        this.goalSelector.addGoal(4, new LookAtPlayerGoal(this, Player.class, 3.0f, 1.0f));
        this.goalSelector.addGoal(4, new LookAtPlayerGoal(this, HumanEntity.class, 3.0f, 1.0f));
        this.goalSelector.addGoal(5, new LookAtPlayerGoal(this, Mob.class, 8.0f));
        this.targetSelector.addGoal(1, new HurtByTargetGoal(this, Raider.class).setAlertOthers(new Class[0]));
        this.targetSelector.addGoal(2, new NearestAttackableTargetGoal<Player>(this, Player.class, false){
            @Override
            public boolean canUse() {
                if (NecromancyWarlockEntity.this.level().getDifficulty() == Difficulty.PEACEFUL) {
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
    public void performRangedAttack(LivingEntity livingEntity, float f) {
        if (this.getSpellTick() > 0) {
            return;
        }
        List<Mob> listRaider = this.level().getEntitiesOfClass(Mob.class, this.getBoundingBox().inflate(32.0, 32.0, 32.0));
        listRaider.removeIf(entity -> this.getTarget() == entity || entity == this || entity.getTarget() == this || !((EntityFactionFind.isRaider(entity) && this.getTeam() == null && entity.getTeam() == null) || (entity.isAlliedTo(this))));
        listRaider.removeIf(entity -> entity instanceof NecromancyWarlockEntity);
        //增强
        int spellLevel = this.getSpellLevel();
        String string = ChatFormatting.stripFormatting(this.getName().getString());
        if ("Master".equals(string)) {
            spellLevel = this.getSpellLevel() + 1;
        }

        List<UncleanTentacleEntity> listTentacle = this.level().getEntitiesOfClass(UncleanTentacleEntity.class, this.getBoundingBox().inflate(64.0, 64.0, 64.0));
        listRaider.removeIf(entity -> this.getTarget() == entity || entity.getTarget() == this || !((this.getTeam() == null && entity.getTeam() == null) || (entity.isAlliedTo(this))));
        List<NecromancyWarlockEntity> ListWarlock = this.level().getEntitiesOfClass(NecromancyWarlockEntity.class, this.getBoundingBox().inflate(64.0, 64.0, 64.0));
        ListWarlock.removeIf(entity -> this.getTarget() == entity || entity.getTarget() == this || !((this.getTeam() == null && entity.getTeam() == null) || (entity.isAlliedTo(this))));

        //技能-邪祟召唤
        if (this.random.nextFloat() < 0.8f && listRaider.isEmpty() && listTentacle.size() <= ListWarlock.size() * 4){
            if (!this.level().isClientSide()) {
                this.setSpellTick(40);
                this.setAnimTick(20);
                this.setAnimationState("summon");
            }
            //法术列表-邪祟召唤
            OtherSpellList.EvilSummoning(spellLevel, this, livingEntity).spellUse();
        }
        //技能-不祥齿轮
        else if (this.random.nextFloat() < 0.2f && this.distanceTo(livingEntity) < 12 && livingEntity.onGround()) {
            if (!this.level().isClientSide()) {
                this.setSpellTick(90);
                this.setSpellOneTick(90);
                this.setAnimTick(90);
                this.setAnimationState("gear");
            }
            if (!this.isSilent()) {
                this.level().playSound(null, this.getX(), this.getY(), this.getZ(), JerotesVillageSoundEvents.NECROMANCY_WARLOCK_GEAR, this.getSoundSource(), 5.0f, 0.8f + this.getRandom().nextFloat() * 0.4f);
            }
        }
        //技能-血腥尖啸
        else if (this.random.nextFloat() < 0.2f){
            if (!this.level().isClientSide()) {
                this.setSpellTick(20);
                this.setAnimTick(20);
                this.setAnimationState("scream");
            }
            //法术列表-血腥尖啸
            OtherSpellList.BloodyScream(spellLevel, this, livingEntity).spellUse();
        }
        //技能-神汉低语
        else if (this.random.nextFloat() < 0.15f && !(livingEntity.hasEffect(MobEffects.DARKNESS))){
            if (!this.level().isClientSide()) {
                this.setSpellTick(25);
                this.setAnimTick(25);
                this.setAnimationState("whisper");
            }
            //法术列表-血腥尖啸
            OtherSpellList.WarlockWhisper(spellLevel, this, livingEntity).spellUse();
        }
        //技能-不洁血雨
        else if (this.random.nextFloat() < 0.4f){
            if (!this.level().isClientSide()) {
                this.setSpellTick(15);
                this.setSpellTwoTick(15);
                this.setAnimTick(15);
                this.setAnimationState("rain");
            }
        }
        //技能-邪祟召唤
        else if (this.random.nextFloat() < 0.2f && listTentacle.size() <= ListWarlock.size() * 4){
            if (!this.level().isClientSide()) {
                this.setSpellTick(40);
                this.setAnimTick(20);
                this.setAnimationState("summon");
            }
            //法术列表-邪祟召唤
            OtherSpellList.EvilSummoning(spellLevel, this, livingEntity).spellUse();
        }
        //技能-不洁血雾
        else {
            if (!this.level().isClientSide()) {
                this.setSpellTick(15);
                this.setSpellThreeTick(15);
                this.setAnimTick(15);
                this.setAnimationState("fog");
            }
        }

    }

    @Override
    protected SoundEvent getAmbientSound() {
        return JerotesVillageSoundEvents.NECROMANCY_WARLOCK_AMBIENT;
    }
    @Override
    protected SoundEvent getDeathSound() {
        return JerotesVillageSoundEvents.NECROMANCY_WARLOCK_DEATH;
    }
    @Override
    protected SoundEvent getHurtSound(DamageSource damageSource) {
        return JerotesVillageSoundEvents.NECROMANCY_WARLOCK_HURT;
    }
    @Override
    public SoundEvent getCelebrateSound() {
        return JerotesVillageSoundEvents.NECROMANCY_WARLOCK_CHEER;
    }
    @Override
    protected SoundEvent getCastingSoundEvent() {
        return JerotesVillageSoundEvents.NECROMANCY_WARLOCK_GEAR;
    }
    @Override
    public IllagerSpell customSpell() {
        return IllagerSpell.SUMMON_VEX;
    }
    @Override
    protected float getSoundVolume() {
        return 2.0f;
    }
    @Override
    public boolean canDrownInFluidType(FluidType type) {
        if (type == ForgeMod.WATER_TYPE.get())
            return false;
        return super.canDrownInFluidType(type);
    }

    public int spellLevel = 4;
    @Override
    public int getSpellLevel() {
        return this.spellLevel;
    }
    //动画
    public int getAnimationState(String animation) {
        if (Objects.equals(animation, "gear")){
            return 1;
        }
        else if (Objects.equals(animation, "scream")){
            return 2;
        }
        else if (Objects.equals(animation, "whisper")){
            return 3;
        }
        else if (Objects.equals(animation, "rain")){
            return 4;
        }
        else if (Objects.equals(animation, "fog")){
            return 5;
        }
        else if (Objects.equals(animation, "summon")){
            return 6;
        }
        else if (Objects.equals(animation, "dead")){
            return 7;
        }
        else {
            return 0;
        }
    }
    public List<AnimationState> getAllAnimations(){
        List<AnimationState> list = new ArrayList<>();
        list.add(this.gearAnimationState);
        list.add(this.screamAnimationState);
        list.add(this.whisperAnimationState);
        list.add(this.rainAnimationState);
        list.add(this.fogAnimationState);
        list.add(this.summonAnimationState);
        list.add(this.deadAnimationState);
        return list;
    }
    //
    @Override
    public void setCustomName(@Nullable Component component) {
        super.setCustomName(component);
        this.bossEvent.setName(this.getDisplayName());
    }
    @Override
    public void addAdditionalSaveData(CompoundTag compoundTag) {
        super.addAdditionalSaveData(compoundTag);
        compoundTag.putInt("SpellLevel", this.spellLevel);
    }
    @Override
    public void readAdditionalSaveData(CompoundTag compoundTag) {
        super.readAdditionalSaveData(compoundTag);
        if (this.hasCustomName()) {
            this.bossEvent.setName(this.getDisplayName());
        }
        this.bossEvent.setId(this.getUUID());
        this.spellLevel = compoundTag.getInt("SpellLevel");
    }

    @Override
    public void onSyncedDataUpdated(EntityDataAccessor<?> entityDataAccessor) {
        if (ANIM_STATE.equals(entityDataAccessor)) {
            if (this.level().isClientSide()) {
                switch (this.entityData.get(ANIM_STATE)){
                    case 0:
                        this.stopAllAnimation();
                        break;
                    case 1:
                        this.gearAnimationState.startIfStopped(this.tickCount);
                        this.stopMostAnimation(this.gearAnimationState);
                        break;
                    case 2:
                        this.screamAnimationState.startIfStopped(this.tickCount);
                        this.stopMostAnimation(this.screamAnimationState);
                        break;
                    case 3:
                        this.whisperAnimationState.startIfStopped(this.tickCount);
                        this.stopMostAnimation(this.whisperAnimationState);
                        break;
                    case 4:
                        this.rainAnimationState.startIfStopped(this.tickCount);
                        this.stopMostAnimation(this.rainAnimationState);
                        break;
                    case 5:
                        this.fogAnimationState.startIfStopped(this.tickCount);
                        this.stopMostAnimation(this.fogAnimationState);
                        break;
                    case 6:
                        this.summonAnimationState.startIfStopped(this.tickCount);
                        this.stopMostAnimation(this.summonAnimationState);
                        break;
                    case 7:
                        this.deadAnimationState.startIfStopped(this.tickCount);
                        this.stopMostAnimation(this.deadAnimationState);
                        break;
                }
            }
        }
        super.onSyncedDataUpdated(entityDataAccessor);
    }

    @Override
    public void aiStep() {
        super.aiStep();
        if (JerotesVillageGameRules.JEROTES_VILLAGE_SOME_ELITE_HAS_BOSS_BAR != null && this.level().getLevelData().getGameRules().getBoolean(JerotesVillageGameRules.JEROTES_VILLAGE_SOME_ELITE_HAS_BOSS_BAR) && OtherMainConfig.EliteCanHasBossBar.contains(this.getEncodeId())) {
            this.bossEvent.update();
            if (OtherMainConfig.EliteBossBarOnlyCombat) {
                this.bossEvent.setVisible(this.getTarget() != null);
            }
        }
        //站立
        if (this.isAlive()){
            this.idleAnimationState.startIfStopped((this.tickCount));
        }
        //增强
        int spellLevel = this.getSpellLevel();
        String string = ChatFormatting.stripFormatting(this.getName().getString());
        if ("Master".equals(string)) {
            spellLevel = this.getSpellLevel() + 1;
        }
        if (this.isAlive()) {
            //技能-不祥齿轮
            if (this.getSpellOneTick() > 0 && this.getSpellOneTick() % 10 == 0) {
                //法术列表-不祥齿轮
                OtherSpellList.OminousGear(spellLevel, this, this.getTarget()).spellUse();
            }
            //技能-不洁血雨
            if (this.getSpellTwoTick() == 5 && this.getTarget() != null) {
                //法术列表-不洁血雨
                OtherSpellList.UncleanBloodRain(spellLevel, this, this.getTarget()).spellUse();
            }
            //技能-不洁血雾
            if (this.getSpellThreeTick() == 5 && this.getTarget() != null) {
                //法术列表-不洁血雨
                OtherSpellList.UncleanBloodFog(spellLevel, this, this.getTarget()).spellUse();
            }
            //清除不洁
            if (this.tickCount % 5 == 0) {
                List<Mob> listRaider = this.level().getEntitiesOfClass(Mob.class, this.getBoundingBox().inflate(32.0, 32.0, 32.0));

                listRaider.stream()
                        .filter(entity -> AttackFind.FindCanNotAttack(this, entity))
                        .filter(entity -> !(entity instanceof NecromancyWarlockEntity))
                        .forEach(raider -> {
                            if (raider.hasEffect(JerotesVillageMobEffects.UNCLEAN_BODY.get())) {
                                raider.removeEffect(JerotesVillageMobEffects.UNCLEAN_BODY.get());
                            }
                            if (raider.hasEffect(MobEffects.CONFUSION)) {
                                raider.removeEffect(MobEffects.CONFUSION);
                            }
                        });
            }
        }
    }

    @Override
    public boolean hurt(DamageSource damageSource, float amount) {
        if (isInvulnerableTo(damageSource)) {
            return super.hurt(damageSource, amount);
        }
        if (!damageSource.is(DamageTypeTags.BYPASSES_INVULNERABILITY) && amount <= this.getMaxHealth() * 20 && !this.isNoAi()) {
            //技能-不祥天选
            List<Mob> listRaider = this.level().getEntitiesOfClass(Mob.class, this.getBoundingBox().inflate(32.0, 32.0, 32.0));
            listRaider.removeIf(entity -> this.getTarget() == entity || entity == this || entity.getTarget() == this || !((EntityFactionFind.isRaider(entity) && this.getTeam() == null && entity.getTeam() == null) || (entity.isAlliedTo(this))));
            listRaider.removeIf(entity -> entity instanceof NecromancyWarlockEntity || entity instanceof OminousBannerProjectionEntity);
            if (!listRaider.isEmpty() && this.level().getRandom().nextFloat() < 0.5f + (listRaider.size() * 0.05f) && !damageSource.is(DamageTypeTags.BYPASSES_INVULNERABILITY)) {
                for (LivingEntity hurt : listRaider) {
                    if (hurt == null) continue;
                    if (!hurt.isAlive()) continue;
                    //原地粒子
                    if (this.level() instanceof ServerLevel serverLevel) {
                        for (int i = 0; i < 24; ++i) {
                            serverLevel.sendParticles(ParticleTypes.DAMAGE_INDICATOR, this.getRandomX(0.5f), this.getRandomY(), this.getRandomZ(0.5f), 0, 0, 0.0, 0, 0.0);
                        }
                    }
                    this.teleportTo(hurt.getX(), hurt.getY(), hurt.getZ());
                    //瞬移后粒子
                    if (this.level() instanceof ServerLevel serverLevel) {
                        for (int i = 0; i < 24; ++i) {
                            serverLevel.sendParticles(ParticleTypes.DAMAGE_INDICATOR, this.getRandomX(0.5f), this.getRandomY(), this.getRandomZ(0.5f), 0, 0, 0.0, 0, 0.0);
                        }
                    }
                    if (this.level() instanceof ServerLevel serverLevel) {
                        if (!this.isInvisible()) {
                            serverLevel.sendParticles(JerotesVillageParticleTypes.OMINOUS_SELECTION_DISPLAY.get(), this.getX(), this.getBoundingBox().maxY + 0.5, this.getZ(), 0, 0.0, 0.0, 0.0, 0);
                        }
                    }
                    if (!this.isSilent() && this.random.nextFloat() < 0.5f) {
                        this.level().playSound(null, this.getX(), this.getY(), this.getZ(), JerotesVillageSoundEvents.NECROMANCY_WARLOCK_SELECTION, this.getSoundSource(), 5.0f, 0.8f + this.random.nextFloat() * 0.4f);
                    }
                    hurt.hurt(damageSource, amount * 0.8f);
                    return false;
                }
            }
        }
        float damage = 1;
        return super.hurt(damageSource, amount * damage);
    }

    @Override
    public boolean canBeAffected(MobEffectInstance mobEffectInstance) {
        if (mobEffectInstance.getEffect() == JerotesVillageMobEffects.UNCLEAN_BODY.get()) {
            return false;
        }
        if (mobEffectInstance.getEffect() == MobEffects.CONFUSION) {
            return false;
        }
        return super.canBeAffected(mobEffectInstance);
    }

    @Override
    public void tickDeath() {
        if(deathTime <= 0){
            if (!this.level().isClientSide()) {
                this.setAnimTick(20);
                this.setAnimationState("dead");
            }
        }
        ++this.deathTime;
        if (this.deathTime >= 20 && !this.level().isClientSide() && !this.isRemoved()) {
            this.level().broadcastEntityEvent(this, (byte) 60);
            this.remove(RemovalReason.KILLED);
        }
    }
}

