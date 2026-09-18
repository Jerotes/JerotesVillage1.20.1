package com.jerotes.jerotesvillage.entity.Monster.IllagerFaction;

import com.jerotes.jerotes.entity.Interface.EliteEntity;
import com.jerotes.jerotes.entity.Interface.SpellUseEntity;
import com.jerotes.jerotes.entity.Mob.HumanEntity;
import com.jerotes.jerotes.event.JerotesBossEvent;
import com.jerotes.jerotes.goal.JerotesAttackAvoidEntityGoal;
import com.jerotes.jerotes.goal.JerotesFireRangeAttackGoal;
import com.jerotes.jerotes.goal.JerotesFlyingRandomStrollGoal;
import com.jerotes.jerotes.util.AttackFind;
import com.jerotes.jerotes.util.EntityFactionFind;
import com.jerotes.jerotes.util.Main;
import com.jerotes.jerotesvillage.config.OtherMainConfig;
import com.jerotes.jerotesvillage.goal.HighNearestAttackableTargetGoal;
import com.jerotes.jerotesvillage.goal.SerponOpenDoorGoal;
import com.jerotes.jerotesvillage.init.JerotesVillageGameRules;
import com.jerotes.jerotesvillage.init.JerotesVillageParticleTypes;
import com.jerotes.jerotesvillage.init.JerotesVillageSoundEvents;
import com.jerotes.jerotesvillage.spell.OtherSpellList;
import net.minecraft.ChatFormatting;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.chat.Component;
import net.minecraft.network.syncher.EntityDataAccessor;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.tags.DamageTypeTags;
import net.minecraft.util.Mth;
import net.minecraft.util.RandomSource;
import net.minecraft.world.BossEvent;
import net.minecraft.world.Difficulty;
import net.minecraft.world.DifficultyInstance;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.damagesource.DamageTypes;
import net.minecraft.world.entity.AnimationState;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.ai.attributes.AttributeSupplier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.ai.control.FlyingMoveControl;
import net.minecraft.world.entity.ai.control.MoveControl;
import net.minecraft.world.entity.ai.goal.FloatGoal;
import net.minecraft.world.entity.ai.goal.LookAtPlayerGoal;
import net.minecraft.world.entity.ai.goal.target.HurtByTargetGoal;
import net.minecraft.world.entity.ai.navigation.FlyingPathNavigation;
import net.minecraft.world.entity.ai.navigation.GroundPathNavigation;
import net.minecraft.world.entity.ai.navigation.PathNavigation;
import net.minecraft.world.entity.animal.IronGolem;
import net.minecraft.world.entity.monster.Monster;
import net.minecraft.world.entity.monster.RangedAttackMob;
import net.minecraft.world.entity.npc.AbstractVillager;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.entity.projectile.AbstractArrow;
import net.minecraft.world.entity.projectile.Projectile;
import net.minecraft.world.entity.projectile.ThrowableProjectile;
import net.minecraft.world.entity.raid.Raider;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.Vec3;

import javax.annotation.Nullable;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class GavilerEntity extends SpellIllagerEntity implements RangedAttackMob, SpellUseEntity, EliteEntity {
    public AnimationState idleAnimationState = new AnimationState();
    public AnimationState shootAnimationState = new AnimationState();
    public AnimationState upAnimationState = new AnimationState();
    public AnimationState downAnimationState = new AnimationState();
    public AnimationState deadAnimationState = new AnimationState();
    private final JerotesBossEvent bossEvent = new JerotesBossEvent(this, this.getUUID(), BossEvent.BossBarColor.RED, BossEvent.BossBarOverlay.NOTCHED_6, false);

    public GavilerEntity(EntityType<? extends GavilerEntity> entityType, Level level) {
        super(entityType, level);
        this.xpReward = 80;
        this.setCanPickUpLoot(false);
    }
    public boolean isJerotesFlyingMob() {
        return true;
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

    public static AttributeSupplier.Builder createAttributes() {
        AttributeSupplier.Builder builder = Monster.createMonsterAttributes();
        builder = builder.add(Attributes.MOVEMENT_SPEED, 0.35);
        builder = builder.add(Attributes.FLYING_SPEED, 1.25);
        builder = builder.add(Attributes.MAX_HEALTH, 80);
        builder = builder.add(Attributes.ARMOR, 3);
        builder = builder.add(Attributes.ATTACK_DAMAGE, 8);
        builder = builder.add(Attributes.FOLLOW_RANGE, 64);
        builder = builder.add(Attributes.KNOCKBACK_RESISTANCE, 0.8);
        return builder;
    }

    @Override
    protected void populateDefaultEquipmentSlots(RandomSource randomSource, DifficultyInstance difficultyInstance) {
    }

    @Override
    protected void registerGoals() {
        super.registerGoals();
        this.goalSelector.addGoal(0, new SerponOpenDoorGoal(this, true));
        this.goalSelector.addGoal(0, new FloatGoal(this));
        this.goalSelector.addGoal(1, new JerotesFireRangeAttackGoal(this, 1.25, 60, 12.0f));
        this.goalSelector.addGoal(2, new JerotesAttackAvoidEntityGoal<>(this, LivingEntity.class, 16.0f, 1.0, 1.2));
        this.goalSelector.addGoal(3, new JerotesFlyingRandomStrollGoal(this, 0.8, 10));
        this.goalSelector.addGoal(4, new LookAtPlayerGoal(this, Player.class, 3.0f, 1.0f));
        this.goalSelector.addGoal(4, new LookAtPlayerGoal(this, HumanEntity.class, 3.0f, 1.0f));
        this.goalSelector.addGoal(5, new LookAtPlayerGoal(this, Mob.class, 8.0f));
        this.targetSelector.addGoal(1, new HurtByTargetGoal(this, Raider.class).setAlertOthers(new Class[0]));
        this.targetSelector.addGoal(2, new HighNearestAttackableTargetGoal<Player>(this, Player.class, false){
            @Override
            public boolean canUse() {
                if (GavilerEntity.this.level().getDifficulty() == Difficulty.PEACEFUL) {
                    return false;
                }
                return super.canUse();
            }
        });
        this.targetSelector.addGoal(3, new HighNearestAttackableTargetGoal<AbstractVillager>(this, AbstractVillager.class, false));
        this.targetSelector.addGoal(3, new HighNearestAttackableTargetGoal<HumanEntity>(this, HumanEntity.class, false));
        this.targetSelector.addGoal(3, new HighNearestAttackableTargetGoal<IronGolem>(this, IronGolem.class, false));
    }

    @Override
    public void performRangedAttack(LivingEntity livingEntity, float f) {
        //增强
        int spellLevel = this.getSpellLevel();
        String string = ChatFormatting.stripFormatting(this.getName().getString());
        if ("Master".equals(string)) {
            spellLevel = this.getSpellLevel() + 1;
        }
        //技能-推动力场
        if (this.distanceTo(livingEntity) < 16 && !this.specialAction()) {
            if (!this.level().isClientSide()) {
                this.setSpellTick(20);
                this.setAnimTick(20);
                this.setAnimationState("shoot");
            }
            //技能列表-推动力场
            OtherSpellList.PushForce(spellLevel, this, livingEntity).spellUse();
        }
    }

    @Override
    protected SoundEvent getAmbientSound() {
        return JerotesVillageSoundEvents.GAVILER_AMBIENT;
    }
    @Override
    protected SoundEvent getDeathSound() {
        return JerotesVillageSoundEvents.GAVILER_DEATH;
    }
    @Override
    protected SoundEvent getHurtSound(DamageSource damageSource) {
        return JerotesVillageSoundEvents.GAVILER_HURT;
    }
    @Override
    public SoundEvent getCelebrateSound() {
        return JerotesVillageSoundEvents.GAVILER_CHEER;
    }
    @Override
    protected SoundEvent getCastingSoundEvent() {
        return JerotesVillageSoundEvents.GAVILER_SHOOT;
    }
    @Override
    public IllagerSpell customSpell() {
        return IllagerSpell.SUMMON_VEX;
    }
    @Override
    protected PathNavigation createNavigation(Level level) {
        return new GroundPathNavigation(this, level);
    }
    public boolean isLandNavigatorType = true;

    public int spellLevel = 4;
    @Override
    public int getSpellLevel() {
        return this.spellLevel;
    }
    public boolean specialAction() {
        return this.getSpellTick() > 0 || this.getSpellOneTick() > 0 || this.getSpellTwoTick() > 0;
    }
    //动画
    public int getAnimationState(String animation) {
        if (Objects.equals(animation, "shoot")){
            return 1;
        }
        else if (Objects.equals(animation, "up")){
            return 2;
        }
        else if (Objects.equals(animation, "down")){
            return 3;
        }
        else if (Objects.equals(animation, "dead")){
            return 4;
        }
        else {
            return 0;
        }
    }
    public List<AnimationState> getAllAnimations(){
        List<AnimationState> list = new ArrayList<>();
        list.add(this.shootAnimationState);
        list.add(this.upAnimationState);
        list.add(this.downAnimationState);
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
        this.spellLevel = compoundTag.getInt("SpellLevel");
        if (this.hasCustomName()) {
            this.bossEvent.setName(this.getDisplayName());
        }
        this.bossEvent.setId(this.getUUID());
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
                        this.shootAnimationState.startIfStopped(this.tickCount);
                        this.stopMostAnimation(this.shootAnimationState);
                        break;
                    case 2:
                        this.upAnimationState.startIfStopped(this.tickCount);
                        this.stopMostAnimation(this.upAnimationState);
                        break;
                    case 3:
                        this.downAnimationState.startIfStopped(this.tickCount);
                        this.stopMostAnimation(this.downAnimationState);
                        break;
                    case 4:
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
        //空陆切换
        boolean fly = !this.onGround() || this.horizontalCollision || this.isAggressive();
        if (fly && this.isLandNavigatorType) {
            this.moveControl = new FlyingMoveControl(this, 20, false);
            this.navigation = new FlyingPathNavigation(this, level());
            this.isLandNavigatorType = false;
        }
        if (!fly && !this.isLandNavigatorType) {
            this.moveControl = new MoveControl(this);
            this.navigation = new GroundPathNavigation(this, level());
            this.isLandNavigatorType = true;
        }
        //
        //起飞
        if (this.getNavigation().getPath() != null && !this.getNavigation().isDone()) {
            BlockState blockState = this.level().getBlockState(this.getNavigation().getPath().getTarget());
            if ((blockState.isAir() || this.getNavigation().getPath().getTarget().getY() > this.getY() + 1 || this.getTarget() != null && !this.getTarget().onGround() || this.getTarget() != null && this.getTarget().getY() > this.getY() + 1) && this.onGround() || this.horizontalCollision) {
                this.setDeltaMovement(this.getDeltaMovement().add(0, 0.5f, 0));
            }
        }
        //
        //站立
        if (this.isAlive()){
            this.idleAnimationState.startIfStopped((this.tickCount));
        }
        //摔落
        Vec3 vec3 = this.getDeltaMovement();
        if (!this.onGround() && vec3.y < 0.0) {
            this.setDeltaMovement(vec3.multiply(1.0, 0.6, 1.0));
        }
        if (!this.onGround()) {
            for (int i = 0; i < 3; ++i) {
                if (this.level().isClientSide) {
                    this.level().addParticle(JerotesVillageParticleTypes.PUSH_FORCE.get(), this.getRandomX(1.5), this.getY(), this.getRandomZ(1.5), 0.0, 0.0, 0.0);
                }
            }
        }
        //上升
        if (this.getTarget() != null &&
                ((this.getTarget().getEyeY() > this.getEyeY() && !this.getTarget().onGround()) || (this.getEyeY() < this.getTarget().getEyeY() + (this.getTarget().getBbHeight() + 0.5f) && this.getTarget().onGround()))) {
            this.setOnGround(false);
            this.setDeltaMovement(this.getDeltaMovement().add(0.0, (0.30000001192092896 - vec3.y) * 0.30000001192092896, 0.0));
            this.hasImpulse = true;
        }
        //下降
        if (this.getTarget() != null && (this.getTarget().getEyeY() + 6 < this.getEyeY())) {
            this.setDeltaMovement(this.getDeltaMovement().add(0.0, -(0.30000001192092896 - vec3.y) * 0.30000001192092896, 0.0));
            this.hasImpulse = true;
        }
        //技能-引力影响
        List<Mob> listRaider = this.level().getEntitiesOfClass(Mob.class, this.getBoundingBox().inflate(16.0, 16.0, 16.0));
        listRaider.removeIf(entity -> this.getTarget() == entity || entity == this || entity.getTarget() == this || !((EntityFactionFind.isRaider(entity) && this.getTeam() == null && entity.getTeam() == null) || (entity.isAlliedTo(this))));
        listRaider.removeIf(entity -> entity.getTarget() != null);
        for (LivingEntity raider : listRaider) {
            if (raider == null) continue;
            Vec3 vec32 = raider.getDeltaMovement();
            if (!raider.onGround() && raider.getDeltaMovement().y < 0.0) {
                raider.setDeltaMovement(vec32.multiply(1.0, 0.6, 1.0));
            }
            if (this.tickCount % 5 == 0) {
                raider.resetFallDistance();
            }
        }
        //技能-弹射牵引
        List<Projectile> listArrow = this.level().getEntitiesOfClass(Projectile.class, this.getBoundingBox().inflate(4.0, 4.0, 4.0));
        listArrow.removeIf(entity -> !(entity instanceof AbstractArrow || entity instanceof ThrowableProjectile));
        listArrow.removeIf(entity -> entity.getOwner() instanceof LivingEntity livingEntity && AttackFind.FindCanNotAttack(this, livingEntity));
        for (Projectile arrow : listArrow) {
            if (arrow == null) continue;
            if (!(Main.canSee(arrow, this) || arrow.getOwner() != null && Main.canSee(arrow.getOwner(), this))) continue;
            arrow.setDeltaMovement(arrow.getDeltaMovement().multiply(-1, -1, -1));
        }
        //靠近
        if (this.getTarget() != null && this.distanceTo(this.getTarget()) > 24 && !this.specialAction()) {
            this.getLookControl().setLookAt(this.getTarget(), 360.0f, 360.0f);
            this.lookAt(this.getTarget(), 360.0f, 360.0f);
            this.RushAttack();
        }
         //增强
        int spellLevel = this.getSpellLevel();
        String string = ChatFormatting.stripFormatting(this.getName().getString());
        if ("Master".equals(string)) {
            spellLevel = this.getSpellLevel() + 1;
        }
        //技能-漂浮力场
        if (this.getTarget() != null && !this.isNoAi() && this.getTarget().isAlive() && !this.specialAction() && this.hasLineOfSight(this.getTarget()) && this.getSpellOneTick() <= 0 && this.getSpellThreeTick() <= 0 && this.getRandom().nextInt(20) == 1) {
            if (this.getTarget().onGround() && this.distanceTo(this.getTarget()) <= spellLevel * 8) {
                if (!this.level().isClientSide()) {
                    this.setSpellTick(20);
                    this.setSpellOneTick(20);
                    this.setSpellThreeTick(120);
                    this.setAnimTick(20);
                    this.setAnimationState("up");
                }
                //法术列表-漂浮力场
                OtherSpellList.FloatingForce(spellLevel, this, this).spellUse();
            }
        }
        //技能-地吸力场
        if (this.getTarget() != null && !this.isNoAi() && this.getTarget().isAlive() && !this.specialAction() && this.hasLineOfSight(this.getTarget())  && this.getSpellTwoTick() <= 0 && this.getSpellThreeTick() <= 0 && this.getRandom().nextInt(20) == 1) {
            if (!this.getTarget().onGround() && this.distanceTo(this.getTarget()) <= spellLevel * 8) {
                if (!this.level().isClientSide()) {
                    this.setSpellTick(20);
                    this.setSpellTwoTick(20);
                    this.setSpellThreeTick(120);
                    this.setAnimTick(20);
                    this.setAnimationState("down");
                }
                //法术列表-漂浮力场
                OtherSpellList.GravityForce(spellLevel, this, this).spellUse();
            }
        }

    }

    public boolean RushAttack() {
        float f = this.getYRot();
        float f2 = this.getXRot();
        float f3 = -Mth.sin(f * 0.017453292f) * Mth.cos(f2 * 0.017453292f);
        float f4 = -Mth.sin(f2 * 0.017453292f);
        float f5 = Mth.cos(f * 0.017453292f) * Mth.cos(f2 * 0.017453292f);
        float f6 = Mth.sqrt(f3 * f3 + f4 * f4 + f5 * f5);
        float f7 = 0.03f;
        float f8 = f4 *= f7 / f6 * 2;
        this.push(f3 *= f7 / f6 * 2, f8, f5 *= f7 / f6 * 2);
        return true;
    }

    @Override
    public boolean isInvulnerableTo(DamageSource damageSource) {
        if (damageSource.is(DamageTypeTags.IS_FALL)
                || damageSource.is(DamageTypes.FALLING_ANVIL)
                || damageSource.is(DamageTypes.FALLING_BLOCK)
                || damageSource.is(DamageTypes.FALLING_STALACTITE)
                || damageSource.is(DamageTypes.FLY_INTO_WALL))
            return true;
        return super.isInvulnerableTo(damageSource);
    }
    @Override
    public boolean hurt(DamageSource damageSource, float amount) {
        if (isInvulnerableTo(damageSource)) {
            return super.hurt(damageSource, amount);
        }
        return super.hurt(damageSource, amount);
    }

    @Override
    public void tickDeath() {
        if(deathTime <= 0){
            if (!this.level().isClientSide()) {
                this.setAnimTick(20);
                this.setAnimationState("dead");
            }
        }
        this.idleAnimationState.stop();
        ++this.deathTime;
        if (this.deathTime >= 20 && !this.level().isClientSide() && !this.isRemoved()) {
            this.level().broadcastEntityEvent(this, (byte) 60);
            this.remove(RemovalReason.KILLED);
        }
    }
}

