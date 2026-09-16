package com.jerotes.jerotesvillage.entity.Monster;

import com.jerotes.jerotes.entity.Interface.FactionEntity;
import com.jerotes.jerotes.entity.Interface.JerotesEntity;
import com.jerotes.jerotes.entity.Mob.HumanEntity;
import com.jerotes.jerotes.entity.Interface.SpellUseEntity;
import com.jerotes.jerotes.goal.JerotesAttackAvoidEntityGoal;
import com.jerotes.jerotesvillage.goal.BulgeFloatGoal;
import com.jerotes.jerotesvillage.goal.HighNearestAttackableTargetGoal;
import com.jerotes.jerotesvillage.goal.RuinsBreezeAttackGoal;
import com.jerotes.jerotesvillage.init.JerotesVillageEntityType;
import com.jerotes.jerotesvillage.init.JerotesVillageSoundEvents;
import com.jerotes.jerotesvillage.spell.OtherSpellList;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.core.particles.BlockParticleOption;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.core.registries.Registries;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.syncher.EntityDataAccessor;
import net.minecraft.network.syncher.EntityDataSerializers;
import net.minecraft.network.syncher.SynchedEntityData;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.tags.DamageTypeTags;
import net.minecraft.tags.FluidTags;
import net.minecraft.tags.TagKey;
import net.minecraft.util.Mth;
import net.minecraft.util.RandomSource;
import net.minecraft.world.Difficulty;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.damagesource.DamageTypes;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.*;
import net.minecraft.world.entity.ai.attributes.AttributeSupplier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.ai.goal.LookAtPlayerGoal;
import net.minecraft.world.entity.ai.goal.RandomLookAroundGoal;
import net.minecraft.world.entity.ai.goal.WaterAvoidingRandomStrollGoal;
import net.minecraft.world.entity.ai.goal.target.HurtByTargetGoal;
import net.minecraft.world.entity.monster.Monster;
import net.minecraft.world.entity.monster.RangedAttackMob;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.ClipContext;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.RenderShape;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.pathfinder.BlockPathTypes;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.HitResult;
import net.minecraft.world.phys.Vec3;
//1.20.4↑//
//import net.minecraft.world.entity.monster.breeze.Breeze;

import javax.annotation.Nullable;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

//1.20.4↑//
//extends Breeze
public class RuinsBreeze extends Monster implements RangedAttackMob, JerotesEntity, FactionEntity, SpellUseEntity {

    //1.20.4↑//
//    protected Brain<?> makeBrain(Dynamic<?> p_311857_) {
//        return this.brainProvider().makeBrain(p_311857_);
//    }
//    protected Brain.Provider<Breeze> brainProvider() {
//        return Brain.provider(ImmutableList.of(), ImmutableList.of());
//    }
//    @Override
//    public boolean canAttackType(EntityType<?> entityType) {
//        return true;
//    }

    private static final EntityDataAccessor<Integer> ANIM_STATE = SynchedEntityData.defineId(RuinsBreeze.class, EntityDataSerializers.INT);
    private static final EntityDataAccessor<Integer> ANIM_TICK = SynchedEntityData.defineId(RuinsBreeze.class, EntityDataSerializers.INT);
    private static final EntityDataAccessor<Integer> JUMP_TICK = SynchedEntityData.defineId(RuinsBreeze.class, EntityDataSerializers.INT);
    private static final EntityDataAccessor<Integer> SHOOT_TICK = SynchedEntityData.defineId(RuinsBreeze.class, EntityDataSerializers.INT);

    public AnimationState longJumpAnimationState = new AnimationState();
    public AnimationState shootAnimationState = new AnimationState();

    public RuinsBreeze(EntityType<? extends Monster> entityType, Level level) {
        super(entityType, level);

        //1.20.4↑//
        //this.setPathfindingMalus(BlockPathTypes.DANGER_TRAPDOOR, -1.0f);

        this.setPathfindingMalus(BlockPathTypes.DAMAGE_FIRE, -1.0f);
    }

    public static AttributeSupplier.Builder createAttributes() {
        AttributeSupplier.Builder builder = Mob.createMobAttributes();
        builder = builder.add(Attributes.MOVEMENT_SPEED, 0.6);
        builder = builder.add(Attributes.MAX_HEALTH, 35.0);
        builder = builder.add(Attributes.ATTACK_DAMAGE, 2.0);
        builder = builder.add(Attributes.FOLLOW_RANGE, 48.0);
        return builder;
    }

    @Override
    protected void registerGoals() {
        this.goalSelector.addGoal(0, new BulgeFloatGoal(this, 0.15f));
        this.goalSelector.addGoal(1, new RuinsBreezeAttackGoal(this, 1.0f, 30, 12.0F));
        this.goalSelector.addGoal(1, new JerotesAttackAvoidEntityGoal<>(this, LivingEntity.class, 8.0f, 1.0, 0.6));
        this.goalSelector.addGoal(2, new WaterAvoidingRandomStrollGoal(this, 0.6f, 0.0f));
        this.goalSelector.addGoal(3, new LookAtPlayerGoal(this, Player.class, 8.0f));
        this.goalSelector.addGoal(3, new LookAtPlayerGoal(this, HumanEntity.class, 8.0f));
        this.goalSelector.addGoal(4, new RandomLookAroundGoal(this));
        this.targetSelector.addGoal(1, new HurtByTargetGoal(this, new Class[0]).setAlertOthers(new Class[0]));
        this.targetSelector.addGoal(2, new HighNearestAttackableTargetGoal<Player>(this, Player.class, true){
            @Override
            public boolean canUse() {
                if (RuinsBreeze.this.level().getDifficulty() == Difficulty.PEACEFUL) {
                    return false;
                }
                return super.canUse();
            }
            @Override
            protected double getFollowDistance() {
                return super.getFollowDistance() * 0.25;
            }
        });
    }

    public void performRangedAttack(LivingEntity livingEntity, float f) {
        if (this.getShootTick() > 0) {
            return;
        }
        if (!this.level().isClientSide()) {
            this.setShootTick(60);
            this.setAnimTick(23);
            this.setAnimationState("shoot");
        }
        this.getNavigation().stop();
        this.playSound(JerotesVillageSoundEvents.RUINS_BREEZE_INHALE, 1.0f, 1.0f);
    }

    public boolean canAttack(LivingEntity livingEntity) {
        return livingEntity.getType() != JerotesVillageEntityType.RUINS_BREEZE.get() && super.canAttack(livingEntity);
    }

    public int spellLevel = 1;
    private int jumpTrailStartedTick = 0;
    @Override
    public int getSpellLevel() {
        return this.spellLevel;
    }
    public void setShootTick(int n){
        this.getEntityData().set(SHOOT_TICK, n);
    }
    public int getShootTick(){
        return this.getEntityData().get(SHOOT_TICK);
    }
    public void setJumpTick(int n){
        this.getEntityData().set(JUMP_TICK, n);
    }
    public int getJumpTick(){
        return this.getEntityData().get(JUMP_TICK);
    }
    //动画
    public void setAnimTick(int n){
        this.getEntityData().set(ANIM_TICK, n);
    }
    public int getAnimTick(){
        return this.getEntityData().get(ANIM_TICK);
    }
    public void setAnimationState(String input) {
        this.setAnimationState(this.getAnimationState(input));
    }
    public void setAnimationState(int id) {
        this.entityData.set(ANIM_STATE, id);
    }
    public int getAnimationState(String animation) {
        if (Objects.equals(animation, "longJump")){
            return 1;
        }
        else if (Objects.equals(animation, "shoot")){
            return 2;
        }
        else {
            return 0;
        }
    }
    public List<AnimationState> getAllAnimations(){
        List<AnimationState> list = new ArrayList<>();
        list.add(this.longJumpAnimationState);
        list.add(this.shootAnimationState);
        return list;
    }
    public void stopMostAnimation(AnimationState exception){
        for (AnimationState state : this.getAllAnimations()){
            if (state != exception) {
                state.stop();
            }
        }
    }
    public void stopAllAnimation(){
        for (AnimationState state : this.getAllAnimations()){
            state.stop();
        }
    }
    //
    @Override
    public void addAdditionalSaveData(CompoundTag compoundTag) {
        super.addAdditionalSaveData(compoundTag);
        compoundTag.putInt("AnimTick", this.getAnimTick());
        compoundTag.putInt("SpellLevel", this.spellLevel);
        compoundTag.putInt("ShootTick", this.getShootTick());
        compoundTag.putInt("JumpTick", this.getJumpTick());
    }
    @Override
    public void readAdditionalSaveData(CompoundTag compoundTag) {
        super.readAdditionalSaveData(compoundTag);
        this.setAnimTick(compoundTag.getInt("AnimTick"));
        this.spellLevel = compoundTag.getInt("SpellLevel");
        this.setShootTick(compoundTag.getInt("ShootTick"));
        this.setJumpTick(compoundTag.getInt("JumpTick"));
    }
    @Override
    protected void defineSynchedData() {
        super.defineSynchedData();
        this.getEntityData().define(ANIM_STATE, 0);
        this.getEntityData().define(ANIM_TICK, 0);
        this.getEntityData().define(JUMP_TICK, 0);
        this.getEntityData().define(SHOOT_TICK, 0);
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
                        this.longJumpAnimationState.startIfStopped(this.tickCount);
                        this.stopMostAnimation(this.longJumpAnimationState);
                        break;
                    case 2:
                        this.shootAnimationState.startIfStopped(this.tickCount);
                        this.stopMostAnimation(this.shootAnimationState);
                        break;
                }
            }
        }
        super.onSyncedDataUpdated(entityDataAccessor);
    }

    @Override
    public void aiStep() {
        super.aiStep();
        //停止战斗
        if (this.getTarget() != null && (!this.getTarget().isAlive() || this.getTarget() instanceof Player player && (player.isCreative() || player.isSpectator()))) {
            this.setTarget(null);
        }
        //
        //清除动画
        if (!this.level().isClientSide()) {
            this.setAnimTick(Math.max(-1, this.getAnimTick() - 1));
        }
        if (this.getAnimTick() == 0) {
            if (!this.level().isClientSide()) {
                this.setAnimationState(0);
            }
        }

        if (!this.level().isClientSide()) {
            this.setShootTick(Math.max(0, this.getShootTick() - 1));
        }
        this.resetJumpTrail().emitGroundParticles(1 + this.getRandom().nextInt(1));
        if (!this.getNavigation().isDone() && this.onGround()) {
            this.emitGroundParticles(20);
            if (this.tickCount % 40 == 0) {
                this.playSound(JerotesVillageSoundEvents.RUINS_BREEZE_SLIDE);
            }
        }
        //跳跃
        if (this.getJumpTick() > 5) {
            if (!this.level().isClientSide()) {
                this.setJumpTick(this.getJumpTick() - 1);
            }
            this.emitJumpTrailParticles();
        }
        if (this.getJumpTick() <= 5 && this.getJumpTick() > 0 && this.onGround()) {
            if (!this.level().isClientSide()) {
                this.setJumpTick(0);
            }
            if (this.getTarget() != null) {
                this.getLookControl().setLookAt(this.getTarget(), 360.0f, 360.0f);
                this.lookAt(this.getTarget(), 360.0f, 360.0f);
            }
            this.playSound(JerotesVillageSoundEvents.RUINS_BREEZE_LAND, 1.0f, 1.0f);
            this.setDeltaMovement(0,this.getDeltaMovement().y,0);
        }
        if (this.getJumpTick() <= 0 && this.getJumpTick() > -40) {
            if (!this.level().isClientSide()) {
                this.setJumpTick(this.getJumpTick() - 1);
            }
            this.emitJumpTrailParticles();
        }

        if (this.tickCount % 80 == 0 && this.getRandom().nextFloat() > 0.75f) {
            this.playSound(JerotesVillageSoundEvents.RUINS_BREEZE_WHIRL, 1.0f, 1.0f);
        }
        //发射
        if (this.getShootTick() == 50) {
            if (this.getTarget() != null && this.isAlive()) {
                //法术列表-遗迹风弹
                OtherSpellList.RuinsWindCharge(this.getSpellLevel(), this, this.getTarget()).spellUse();
            }
        }
        if (this.getTarget() != null && this.getTarget().isAlive() && this.isAlive()) {
            if (this.getShootTick() > 30 && this.getShootTick() <= 40) {
                //跳跃
                if (canJump(this.level(), this) && this.getRandom().nextFloat() > 0.25f && !(!hasLineOfSight(this, this.getTarget().getOnPos().getCenter()) && !hasLineOfSight(this, this.getTarget().getOnPos().above(4).getCenter())) && !this.isPassenger() && !this.isInWater() && this.getEffect(MobEffects.LEVITATION) == null) {
                    if (!this.level().isClientSide()) {
                        this.setJumpTick(10);
                        this.setAnimTick(23);
                        this.setAnimationState("longJump");
                    }
                    this.playSound(JerotesVillageSoundEvents.RUINS_BREEZE_JUMP, 1.0f, 1.0f);
                    this.jumpToTarget(this.getTarget());
                }
            }

        }
    }

    public void onAboveBubbleCol(boolean bl) {
    }

    public void onInsideBubbleColumn(boolean bl) {
        this.resetFallDistance();
    }

    public boolean jumpToTarget(LivingEntity target) {
        this.getLookControl().setLookAt(target, 360.0f, 360.0f);
        this.lookAt(target, 360.0f, 360.0f);
        final double smoothX = Mth.clamp(Math.abs(target.getX() - this.getX()), 0, 1);
        final double smoothZ = Mth.clamp(Math.abs(target.getZ() - this.getZ()), 0, 1);
        final double d0 = (target.getX() - this.getX()) * 0.3 * smoothX;
        final double d2 = (target.getZ() - this.getZ()) * 0.3 * smoothZ;
        final float up = 0.5f + this.getRandom().nextFloat() * 0.8f;
        this.setDeltaMovement(this.getDeltaMovement().add(d0 * 0.75, up, d2 * 0.75));
        return true;
    }

    protected boolean canJump(Level level, RuinsBreeze breeze) {
        if (!breeze.onGround() && !breeze.isInWater()) {
            return false;
        }
        LivingEntity livingEntity = breeze.getTarget();
        if (livingEntity == null) {
            return false;
        }
        if (tooCloseForJump(breeze, livingEntity)) {
            return false;
        }
        if (!canJumpFromCurrentPosition(level, breeze)) {
            return false;
        }
        BlockPos blockPos = snapToSurface(breeze, randomPointBehindTarget(livingEntity, breeze.getRandom()));
        if (blockPos == null) {
            return false;
        }
        if (!hasLineOfSight(breeze, blockPos.getCenter()) && !hasLineOfSight(breeze, blockPos.above(4).getCenter())) {
            return false;
        }
        return true;
    }
    @Nullable
    private static BlockPos snapToSurface(LivingEntity livingEntity, Vec3 vec3) {
        ClipContext clipContext = new ClipContext(vec3, vec3.relative(Direction.DOWN, 10.0), ClipContext.Block.COLLIDER, ClipContext.Fluid.NONE, livingEntity);
        BlockHitResult blockHitResult = livingEntity.level().clip(clipContext);
        if (((HitResult)blockHitResult).getType() == HitResult.Type.BLOCK) {
            return BlockPos.containing(blockHitResult.getLocation()).above();
        }
        ClipContext clipContext2 = new ClipContext(vec3, vec3.relative(Direction.UP, 10.0), ClipContext.Block.COLLIDER, ClipContext.Fluid.NONE, livingEntity);
        BlockHitResult blockHitResult2 = livingEntity.level().clip(clipContext2);
        if (((HitResult)blockHitResult2).getType() == HitResult.Type.BLOCK) {
            return BlockPos.containing(blockHitResult.getLocation()).above();
        }
        return null;
    }
    private static Vec3 randomPointBehindTarget(LivingEntity livingEntity, RandomSource randomSource) {
        int n = 90;
        float f = livingEntity.yHeadRot + 180.0f + (float)randomSource.nextGaussian() * 90.0f / 2.0f;
        float f2 = Mth.lerp(randomSource.nextFloat(), 4.0f, 8.0f);
        Vec3 vec3 = Vec3.directionFromRotation(0.0f, f).scale(f2);
        return livingEntity.position().add(vec3);
    }
    public static boolean hasLineOfSight(RuinsBreeze breeze, Vec3 vec3) {
        Vec3 vec32 = new Vec3(breeze.getX(), breeze.getY(), breeze.getZ());
        if (vec3.distanceTo(vec32) > 50.0) {
            return false;
        }
        return breeze.level().clip(new ClipContext(vec32, vec3, ClipContext.Block.COLLIDER, ClipContext.Fluid.NONE, breeze)).getType() == HitResult.Type.MISS;
    }
    private static boolean outOfAggroRange(RuinsBreeze breeze, LivingEntity livingEntity) {
        return !livingEntity.closerThan(breeze, 24.0);
    }
    private static boolean tooCloseForJump(RuinsBreeze breeze, LivingEntity livingEntity) {
        return livingEntity.distanceTo(breeze) - 4.0f <= 0.0f;
    }
    private static boolean canJumpFromCurrentPosition(Level level, RuinsBreeze breeze) {
        BlockPos blockPos = breeze.blockPosition();
        for (int i = 1; i <= 4; ++i) {
            BlockPos blockPos2 = blockPos.relative(Direction.UP, i);
            if (level.getBlockState(blockPos2).isAir() || level.getFluidState(blockPos2).is(FluidTags.WATER)) continue;
            return false;
        }
        return true;
    }

    public RuinsBreeze resetJumpTrail() {
        this.jumpTrailStartedTick = 0;
        return this;
    }
    public void emitJumpTrailParticles() {
        if (++this.jumpTrailStartedTick <= 5) {
            BlockState $$0 = this.level().getBlockState(this.blockPosition().below());
            Vec3 $$1 = this.getDeltaMovement();
            Vec3 $$2 = this.position().add($$1).add(0.0, 0.10000000149011612, 0.0);

            for(int $$3 = 0; $$3 < 3; ++$$3) {
                this.level().addParticle(new BlockParticleOption(ParticleTypes.BLOCK, $$0), $$2.x, $$2.y, $$2.z, 0.0, 0.0, 0.0);
            }

        }
    }
    public void emitGroundParticles(int p_310885_) {
        Vec3 $$1 = this.getBoundingBox().getCenter();
        Vec3 $$2 = new Vec3($$1.x, this.position().y, $$1.z);
        BlockState $$3 = this.level().getBlockState(this.blockPosition().below());
        if ($$3.getRenderShape() != RenderShape.INVISIBLE) {
            for(int $$4 = 0; $$4 < p_310885_; ++$$4) {
                this.level().addParticle(new BlockParticleOption(ParticleTypes.BLOCK, $$3), $$2.x, $$2.y, $$2.z, 0.0, 0.0, 0.0);
            }
        }
    }

    @Override
    protected SoundEvent getAmbientSound() {
        return this.onGround() ? JerotesVillageSoundEvents.RUINS_BREEZE_AMBIENT : JerotesVillageSoundEvents.RUINS_BREEZE_AMBIENT_AIR;
    }
    @Override
    protected SoundEvent getDeathSound() {
        return JerotesVillageSoundEvents.RUINS_BREEZE_DEATH;
    }
    @Override
    protected SoundEvent getHurtSound(DamageSource damageSource) {
        return JerotesVillageSoundEvents.RUINS_BREEZE_HURT;
    }

    public int getMaxHeadYRot() {
        return 30;
    }

    public int getHeadRotSpeed() {
        return 25;
    }

    public boolean isInvulnerableTo(DamageSource damageSource) {
        if (damageSource.is(DamageTypes.FLY_INTO_WALL)
                || damageSource.is(DamageTypeTags.IS_FALL))
            return true;
        return damageSource.is(TagKey.create(Registries.DAMAGE_TYPE, new ResourceLocation("breeze_immune_to"))) || damageSource.getEntity() instanceof RuinsBreeze || super.isInvulnerableTo(damageSource);
    }

    @Override
    public boolean hurt(DamageSource damageSource, float amount) {
        if (isInvulnerableTo(damageSource)) {
            return super.hurt(damageSource, amount);
        }
        if (damageSource.is(TagKey.create(Registries.DAMAGE_TYPE, new ResourceLocation("breeze_immune_to")))) {
            this.playSound(JerotesVillageSoundEvents.RUINS_BREEZE_DEFLECT, 1.0f, 1.0f);
            return false;
        }
        if (damageSource.is(DamageTypes.TRIDENT) || damageSource.is(DamageTypes.ARROW)) {
            this.playSound(JerotesVillageSoundEvents.RUINS_BREEZE_DEFLECT, 1.0f, 1.0f);
            return false;
        }
        return super.hurt(damageSource, amount);
    }

    public double getFluidJumpThreshold() {
        return this.getEyeHeight();
    }

    public boolean causeFallDamage(float f, float f2, DamageSource damageSource) {
        if (f > 3.0F) {
            this.playSound(JerotesVillageSoundEvents.RUINS_BREEZE_LAND, 1.0F, 1.0F);
        }
        return super.causeFallDamage(f, f2, damageSource);
    }

    protected Entity.MovementEmission getMovementEmission() {
        return MovementEmission.EVENTS;
    }
}

