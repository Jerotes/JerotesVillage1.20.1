package com.jerotes.jerotesvillage.entity.Monster.WickedRidiculeCalls;

import com.jerotes.jerotes.entity.Interface.FactionEntity;
import com.jerotes.jerotes.entity.Interface.JerotesEntity;
import com.jerotes.jerotes.entity.Interface.SpellUseEntity;
import com.jerotes.jerotes.entity.Interface.WickedRidiculeCallsEntity;
import com.jerotes.jerotes.entity.Mob.HumanEntity;
import com.jerotes.jerotes.goal.JerotesHelpAlliesGoal;
import com.jerotes.jerotes.goal.JerotesHelpSameFactionGoal;
import com.jerotes.jerotes.init.JerotesDamageTypes;
import com.jerotes.jerotes.util.AttackFind;
import com.jerotes.jerotes.util.EntityFactionFind;
import com.jerotes.jerotesvillage.entity.Humanoid.SecondRounderEntity;
import com.jerotes.jerotesvillage.goal.FlyingHideFromSunGoal;
import com.jerotes.jerotesvillage.goal.HighNearestAttackableTargetGoal;
import com.jerotes.jerotesvillage.goal.RushToOverlapMeleeGoal;
import com.jerotes.jerotesvillage.init.JerotesVillageMobEffects;
import com.jerotes.jerotesvillage.init.JerotesVillageSoundEvents;
import com.jerotes.jerotesvillage.network.JerotesVillageLevelData;
import com.jerotes.jerotesvillage.spell.OtherSpellList;
import net.minecraft.core.BlockPos;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.syncher.EntityDataAccessor;
import net.minecraft.network.syncher.EntityDataSerializers;
import net.minecraft.network.syncher.SynchedEntityData;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.tags.DamageTypeTags;
import net.minecraft.util.Mth;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.damagesource.DamageTypes;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.*;
import net.minecraft.world.entity.ai.attributes.AttributeSupplier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.ai.control.FlyingMoveControl;
import net.minecraft.world.entity.ai.goal.FloatGoal;
import net.minecraft.world.entity.ai.goal.LookAtPlayerGoal;
import net.minecraft.world.entity.ai.goal.RandomLookAroundGoal;
import net.minecraft.world.entity.ai.goal.RandomStrollGoal;
import net.minecraft.world.entity.ai.goal.target.HurtByTargetGoal;
import net.minecraft.world.entity.ai.navigation.FlyingPathNavigation;
import net.minecraft.world.entity.ai.navigation.PathNavigation;
import net.minecraft.world.entity.animal.IronGolem;
import net.minecraft.world.entity.monster.Monster;
import net.minecraft.world.entity.npc.AbstractVillager;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.entity.projectile.AbstractArrow;
import net.minecraft.world.item.TieredItem;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.pathfinder.BlockPathTypes;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.Vec3;
import net.minecraft.world.phys.shapes.BooleanOp;
import net.minecraft.world.phys.shapes.Shapes;
import net.minecraftforge.common.ForgeMod;
import net.minecraftforge.fluids.FluidType;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class PetitionerEntity extends Monster implements JerotesEntity, WickedRidiculeCallsEntity, FactionEntity, SpellUseEntity {
	private static final EntityDataAccessor<Integer> ANIM_STATE = SynchedEntityData.defineId(PetitionerEntity.class, EntityDataSerializers.INT);
	private static final EntityDataAccessor<Integer> ANIM_TICK = SynchedEntityData.defineId(PetitionerEntity.class, EntityDataSerializers.INT);
	private static final EntityDataAccessor<Integer> ATTACK_TICK = SynchedEntityData.defineId(PetitionerEntity.class, EntityDataSerializers.INT);
	private static final EntityDataAccessor<Integer> WAIL_TICK = SynchedEntityData.defineId(PetitionerEntity.class, EntityDataSerializers.INT);
	private static final EntityDataAccessor<Integer> WAIL_COOLDOWN = SynchedEntityData.defineId(PetitionerEntity.class, EntityDataSerializers.INT);
	private static final EntityDataAccessor<Integer> ATTACK_ANIM = SynchedEntityData.defineId(PetitionerEntity.class, EntityDataSerializers.INT);
	public AnimationState idleAnimationState = new AnimationState();
	public AnimationState wailAnimationState = new AnimationState();

	public PetitionerEntity(EntityType<? extends PetitionerEntity> entityType, Level level) {
		super(entityType, level);
		setMaxUpStep(1.1F);
		this.xpReward = 20;
		this.moveControl = new FlyingMoveControl(this, 90, true);
		this.setPathfindingMalus(BlockPathTypes.LAVA, -1.0F);
		this.setPathfindingMalus(BlockPathTypes.DAMAGE_FIRE, -1.0F);
		this.setPathfindingMalus(BlockPathTypes.DANGER_FIRE, -1.0F);
		this.setPathfindingMalus(BlockPathTypes.BLOCKED, 0.0f);
		this.setPathfindingMalus(BlockPathTypes.FENCE, 0.0f);
		this.setPathfindingMalus(BlockPathTypes.DOOR_WOOD_CLOSED, 0.0f);
		this.setPathfindingMalus(BlockPathTypes.DOOR_IRON_CLOSED, 0.0f);
		this.setPathfindingMalus(BlockPathTypes.UNPASSABLE_RAIL, 0.0f);
		this.setPathfindingMalus(BlockPathTypes.WATER, -1.0F);
	}

	@Override
	protected PathNavigation createNavigation(Level level) {
		return new FlyingPathNavigation(this, level);
	}

	@Override
	public String getFirstFactionTypeName() {
		return "wicked_ridicule_calls";
	}

	public static AttributeSupplier.Builder createAttributes() {
		AttributeSupplier.Builder builder = Mob.createMobAttributes();
		builder = builder.add(Attributes.MOVEMENT_SPEED, 0.33);
		builder = builder.add(Attributes.FLYING_SPEED, 1.0);
		builder = builder.add(Attributes.MAX_HEALTH, 30.0);
		builder = builder.add(Attributes.ATTACK_DAMAGE, 4.0);
		builder = builder.add(Attributes.KNOCKBACK_RESISTANCE, 1.0);
		builder = builder.add(Attributes.FOLLOW_RANGE, 35);
		return builder;
	}

	@Override
	protected void registerGoals() {
		this.goalSelector.addGoal(0, new FloatGoal(this));
		this.goalSelector.addGoal(0, new FlyingHideFromSunGoal(this, 1.0));
		this.goalSelector.addGoal(1, new RushToOverlapMeleeGoal(this, 2.0));
		this.goalSelector.addGoal(2, new LookAtPlayerGoal(this, Player.class, 3.0F, 1.0F));
		this.goalSelector.addGoal(3, new RandomLookAroundGoal(this));
		this.goalSelector.addGoal(4, new RandomStrollGoal(this, 0.6));
		this.targetSelector.addGoal(1, new HurtByTargetGoal(this));
		this.targetSelector.addGoal(1, new JerotesHelpSameFactionGoal(this, LivingEntity.class, false, false, livingEntity -> livingEntity instanceof LivingEntity));
		this.targetSelector.addGoal(1, new HighNearestAttackableTargetGoal<LivingEntity>(this, LivingEntity.class, 5, false, false, livingEntity -> EntityFactionFind.isHateFaction(this, livingEntity)));
		this.targetSelector.addGoal(1, new JerotesHelpAlliesGoal(this, LivingEntity.class, false, false, livingEntity -> livingEntity instanceof LivingEntity));
		this.targetSelector.addGoal(2, new HighNearestAttackableTargetGoal<Player>(this, Player.class, true));
		this.targetSelector.addGoal(2, new HighNearestAttackableTargetGoal<IronGolem>(this, IronGolem.class, true));
		this.targetSelector.addGoal(2, new HighNearestAttackableTargetGoal<SecondRounderEntity>(this, SecondRounderEntity.class, false));
		this.targetSelector.addGoal(2, new HighNearestAttackableTargetGoal<HumanEntity>(this, HumanEntity.class, false));
		this.targetSelector.addGoal(2, new HighNearestAttackableTargetGoal<AbstractVillager>(this, AbstractVillager.class, false));
	}

	@Override
	public MobType getMobType() {
		return MobType.UNDEAD;
	}
	@Override
	protected SoundEvent getAmbientSound() {
		return JerotesVillageSoundEvents.PETITIONER_AMBIENT;
	}
	@Override
	protected SoundEvent getHurtSound(DamageSource damageSource) {
		return JerotesVillageSoundEvents.PETITIONER_HURT;
	}
	@Override
	protected SoundEvent getDeathSound() {
		return JerotesVillageSoundEvents.PETITIONER_DEATH;
	}
	@Override
	public boolean canDrownInFluidType(FluidType type) {
		if (type == ForgeMod.WATER_TYPE.get()) {
			return false;
		}
		return super.canDrownInFluidType(type);
	}
	@Override
	protected float getStandingEyeHeight(Pose pose, net.minecraft.world.entity.EntityDimensions entityDimensions) {
		return 1.79F;
	}
	public AABB getAttackBoundingBox() {
		AABB aabb = this.getBoundingBox();
		AABB aabb1 = aabb.inflate(Math.sqrt(2.04F) - 0.6F, 0.0, Math.sqrt(2.04F) - 0.6F);
		return aabb1;
	}
	@Override
	public boolean isWithinMeleeAttackRange(net.minecraft.world.entity.LivingEntity livingEntity) {
		return this.getAttackBoundingBox().intersects(livingEntity.getBoundingBox());
	}
	@Override
	public void travel(Vec3 dir) {
		super.travel(dir);
		if (this.specialAction()) {
			this.setDeltaMovement(this.getDeltaMovement().multiply(0.05d, 1d, 0.05d));
		}
	}
	public boolean specialAction() {
		return this.getWailTick() > 0;
	}
	@Override
	public boolean isPushedByFluid() {
		return false;
	}
	@Override
	public void knockback(double d, double e, double f) {
	}

	@Override
	public boolean isPushable() {
		return false;
	}

	private int stuckTicks;
	public float thisTickRenderTime = 0;
	public float lastTickRenderTime = 6;
	public float attackAnimProgress = 0.0f;
	public int spellLevel = 1;
	public int wickedRidiculeNightNightSpellLevel = 2;
	@Override
	public int getSpellLevel() {
		if (JerotesVillageLevelData.get(this.level()) != null && JerotesVillageLevelData.get(this.level()).WickedRidiculeNight) {
			return Math.max(this.spellLevel, this.wickedRidiculeNightNightSpellLevel);
		}
		return this.spellLevel;
	}
	public void setAnimTick(int n) {
		this.getEntityData().set(ANIM_TICK, n);
	}
	public int getAnimTick() {
		return this.getEntityData().get(ANIM_TICK);
	}
	public void setAttackTick(int n) {
		this.getEntityData().set(ATTACK_TICK, n);
	}
	public int getAttackTick() {
		return this.getEntityData().get(ATTACK_TICK);
	}
	public void setWailTick(int n) {
		this.getEntityData().set(WAIL_TICK, n);
	}
	public int getWailTick() {
		return this.getEntityData().get(WAIL_TICK);
	}
	public void setWailCooldown(int n) {
		this.getEntityData().set(WAIL_COOLDOWN, n);
	}
	public int getWailCooldown() {
		return this.getEntityData().get(WAIL_COOLDOWN);
	}
	public void setAttackAnim(int n){
		this.getEntityData().set(ATTACK_ANIM, n);
	}
	public int getAttackAnim(){
		return this.getEntityData().get(ATTACK_ANIM);
	}
	public void setAnimationState(String input) {
		this.setAnimationState(0);
		this.setAnimationState(this.getAnimationState(input));
	}
	public void setAnimationState(int id) {
		this.entityData.set(ANIM_STATE, id);
	}
	public int getAnimationState(String animation) {
		if (Objects.equals(animation, "wail")) {
			return 1;
		}
		return 0;
	}
	public List<AnimationState> getAllAnimations() {
		List<AnimationState> list = new ArrayList<>();
		list.add(this.wailAnimationState);
		return list;
	}
	public void stopMostAnimation(AnimationState exception) {
		for (AnimationState state : this.getAllAnimations()) {
			if (state != exception) {
				state.stop();
			}
		}
	}
	public void stopAllAnimation() {
		for (AnimationState state : this.getAllAnimations()) {
			state.stop();
		}
	}
	@Override
	public void addAdditionalSaveData(CompoundTag compoundTag) {
		super.addAdditionalSaveData(compoundTag);
		compoundTag.putInt("SpellLevel", this.spellLevel);
		compoundTag.putInt("WickedRidiculeNightNightSpellLevel", this.wickedRidiculeNightNightSpellLevel);
		compoundTag.putInt("AttackAnim", this.getAttackAnim());
		compoundTag.putInt("WailTick", this.getWailTick());
		compoundTag.putInt("WailCooldown", this.getWailCooldown());
		compoundTag.putInt("AnimTick", this.getAnimTick());
		compoundTag.putInt("AttackTick", this.getAttackTick());
	}
	@Override
	public void readAdditionalSaveData(CompoundTag compoundTag) {
		super.readAdditionalSaveData(compoundTag);
		this.spellLevel = compoundTag.getInt("SpellLevel");
		this.wickedRidiculeNightNightSpellLevel = compoundTag.getInt("WickedRidiculeNightNightSpellLevel");
		this.setAttackAnim(compoundTag.getInt("AttackAnim"));
		this.setWailTick(compoundTag.getInt("WailTick"));
		this.setWailCooldown(compoundTag.getInt("WailCooldown"));
		this.setAnimTick(compoundTag.getInt("AnimTick"));
		this.setAttackTick(compoundTag.getInt("AttackTick"));
	}
	@Override
	protected void defineSynchedData() {
		super.defineSynchedData();
		this.getEntityData().define(ANIM_STATE, 0);
		this.getEntityData().define(ANIM_TICK, 0);
		this.getEntityData().define(ATTACK_TICK, 0);
		this.getEntityData().define(WAIL_TICK, 0);
		this.getEntityData().define(WAIL_COOLDOWN, 0);
		this.getEntityData().define(ATTACK_ANIM, 0);
	}
	@Override
	public void onSyncedDataUpdated(EntityDataAccessor<?> entityDataAccessor) {
		if (ANIM_STATE.equals(entityDataAccessor)) {
			if (this.level().isClientSide()) {
				switch (this.entityData.get(ANIM_STATE)) {
					case 0:
						this.stopAllAnimation();
						break;
					case 1:
						this.wailAnimationState.stop();
						this.wailAnimationState.start(this.tickCount);
						this.stopMostAnimation(this.wailAnimationState);
						break;
				}
			}
		}
		super.onSyncedDataUpdated(entityDataAccessor);
	}

	@Override
	public void addInWickedRidiculeNight(boolean bl) {
		if (!this.level().isClientSide()) {
			this.addEffect(new MobEffectInstance(JerotesVillageMobEffects.DEEP_BLESSING.get(), 5, 0), this);
			this.addEffect(new MobEffectInstance(JerotesVillageMobEffects.WICKED_RIDICULE_NIGHT.get(), 5, 0), this);
			this.addEffect(new MobEffectInstance(MobEffects.DAMAGE_BOOST, 5, 0), this);
			this.addEffect(new MobEffectInstance(MobEffects.DAMAGE_RESISTANCE, 5, 0), this);
		}
	}


	@Override
	public void tick() {
		this.noPhysics = true;
		super.tick();
		this.noPhysics = false;
		this.setNoGravity(true);
		if (!this.level().isClientSide()) {
			this.fixStuckInBlocks();
		}
		if (this.level().isClientSide()) {
			lastTickRenderTime = thisTickRenderTime;
			thisTickRenderTime = 0;
		}
	}

	private void fixStuckInBlocks() {
		if (this.level().noCollision(this, this.getBoundingBox())) {
			this.stuckTicks = 0;
			return;
		}
		this.stuckTicks++;
		for (int i = 0; i < 16; i++) {
			this.setPos(this.getX(), this.getY() + 0.125, this.getZ());
			if (this.level().noCollision(this, this.getBoundingBox())) {
				this.setDeltaMovement(this.getDeltaMovement().x, 0.15, this.getDeltaMovement().z);
				return;
			}
		}
		if (this.stuckTicks > 20) {
			this.stuckTicks = 0;
			double angle = this.random.nextDouble() * Math.PI * 2.0;
			this.setPos(this.getX() + Math.cos(angle) * 1.5, this.getY() + 1.5, this.getZ() + Math.sin(angle) * 1.5);
		}
	}
	@Override
	public void aiStep() {
		super.aiStep();
		if (this.getTarget() != null && (!this.getTarget().isAlive()
				|| this.getTarget() instanceof Player player && (player.isCreative() || player.isSpectator()))) {
			this.setTarget(null);
		}
		if (this.getAnimTick() == 0) {
			if (!this.level().isClientSide()) {
				this.setAnimationState(0);
			}
		}
		if (!this.level().isClientSide()) {
			this.setAnimTick(Math.max(-1, this.getAnimTick() - 1));
		}
		if (this.isAlive()) {
			this.idleAnimationState.startIfStopped(this.tickCount);
		}
		//诉冤者之嚎
		if (this.isAlive() && !this.isNoAi()) {
			if (this.getAttackTick() <= 0 && this.getWailTick() <= 0 && this.getWailCooldown() <= 0
					&& this.getTarget() != null && this.distanceTo(this.getTarget()) <= 8.0f
					&& this.random.nextInt(20) == 0) {
				if (!this.level().isClientSide()) {
					this.setWailTick(40);
					this.setWailCooldown(2400);
					this.setAnimTick(40);
					this.setAnimationState(0);
					this.setAnimationState("wail");
				}
			}
			if (this.getWailTick() == 20 && this.getTarget() != null) {
				OtherSpellList.WailOfThePetitioner(this.getSpellLevel(), this, this.getTarget()).spellUse();
			}
			if (!this.level().isClientSide()) {
				this.setWailTick(Math.max(0, this.getWailTick() - 1));
				this.setWailCooldown(Math.max(0, this.getWailCooldown() - 1));
			}
		}
		//
		Vec3 vec3 = this.getDeltaMovement();
		//上升
		if (this.getOnPos().getY() < this.level().getMinBuildHeight() && this.getTarget() == null || this.isInWallSelf()) {
			this.setDeltaMovement(this.getDeltaMovement().add(0.0, (0.3 - vec3.y) * 0.3, 0.0));
		}
 		//
		if (!this.level().isClientSide()) {
			this.setAttackTick(Math.max(0, this.getAttackTick() - 1));
		}
		if (this.getAttackTick() > 10) {
			if (!this.level().isClientSide()) {
				this.setAttackAnim(Math.min(this.getAttackAnim() + 1, 10));
			}
		}
		else {
			if (!this.level().isClientSide()) {
				this.setAttackAnim(Math.max(this.getAttackAnim() - 1, 0));
			}
		}
	}
	public boolean isInWallSelf() {
		float f = this.getBbWidth() * 0.8F;
		AABB aabb = AABB.ofSize(this.getEyePosition(), (double) f, 1.0E-6, (double) f);
		return BlockPos.betweenClosedStream(aabb).anyMatch((pos) -> {
			BlockState blockstate = this.level().getBlockState(pos);
			return !blockstate.isAir() && blockstate.isSuffocating(this.level(), pos) && Shapes.joinIsNotEmpty(blockstate.getCollisionShape(this.level(), pos).move((double) pos.getX(), (double) pos.getY(), (double) pos.getZ()), Shapes.create(aabb), BooleanOp.AND);
		});
	}
	public boolean RushAttack() {
		float f = this.getYRot();
		float f2 = this.getXRot();
		float f3 = -Mth.sin(f * 0.017453292f) * Mth.cos(f2 * 0.017453292f);
		float f4 = -Mth.sin(f2 * 0.017453292f);
		float f5 = Mth.cos(f * 0.017453292f) * Mth.cos(f2 * 0.017453292f);
		float f6 = Mth.sqrt(f3 * f3 + f4 * f4 + f5 * f5);
		float f7 = 0.015f;
		float f8 = f4 *= f7 / f6 * 2;
		this.push(f3 *= f7 / f6 * 2, f8, f5 *= f7 / f6 * 2);
		return true;
	}

	@Override
	public boolean doHurtTarget(Entity entity) {
		if (this.specialAction()) {
			return false;
		}
		if (this.getAttackTick() <= 0) {
			trueHurt();
			if (!this.level().isClientSide()) {
				this.setAttackTick(20);
			}
			return true;
		}
		return false;
	}
	public boolean trueHurt() {
		float damageMulti = 1.0F;
		float knockbackMulti = 1.0F;
		List<LivingEntity> list = this.level().getEntitiesOfClass(LivingEntity.class, this.getAttackBoundingBox());
		for (LivingEntity hurt : list) {
			if (hurt == null) continue;
			if (AttackFind.FindCanNotAttack(this, hurt)) continue;
			AttackFind.attackBegin(this, hurt);
			DamageSource damageSource = AttackFind.findDamageType(this, JerotesDamageTypes.BYPASSES_COOLDOWN_MELEE, this);
			AttackFind.attackAfterCustomDamage(this, hurt, damageSource, damageMulti, knockbackMulti, false, 0);
		}
		return true;
	}
	@Override
	public boolean isInvulnerableTo(DamageSource damageSource) {
		if (AttackFind.isWaterDamage(damageSource)
				|| damageSource.is(DamageTypeTags.IS_FALL)
				|| damageSource.is(DamageTypes.CACTUS)
				|| damageSource.is(DamageTypes.CRAMMING)
				|| damageSource.is(DamageTypes.SWEET_BERRY_BUSH))
			return true;
		return super.isInvulnerableTo(damageSource);
	}

	@Override
	public boolean hurt(DamageSource damagesource, float amount) {
		if (isInvulnerableTo(damagesource)) {
			return super.hurt(damagesource, amount);
		}
		if (damagesource.getEntity() instanceof LivingEntity livingEntity &&
				AttackFind.isMeleeDamage(damagesource) &&
				!livingEntity.canDisableShield() &&
				livingEntity.getItemInHand(InteractionHand.MAIN_HAND).getItem() instanceof TieredItem) {
			amount *= 0.5f;
		}
		if (damagesource.getDirectEntity() instanceof AbstractArrow) {
			amount *= 0.5f;
		}
		return super.hurt(damagesource, amount);
	}

	@Override
	public boolean causeFallDamage(float l, float d, DamageSource source) {
		return false;
	}
	@Override
	protected void checkFallDamage(double y, boolean onGroundIn, BlockState state, BlockPos pos) {
		this.resetFallDistance();
	}

	@Override
	public boolean isAlliedTo(Entity entity) {
		if (entity == null) {
			return false;
		}
		if (entity == this) {
			return true;
		}
		if (super.isAlliedTo(entity)) {
			return true;
		}
		if (entity instanceof LivingEntity livingEntity && EntityFactionFind.isFaction(this, livingEntity)) {
			return this.getTeam() == null && entity.getTeam() == null;
		}
		return false;
	}

	@Override
	public boolean canAttack(LivingEntity livingEntity) {
		if (((this.getTeam() == null && livingEntity.getTeam() == null) || this.getTeam() == livingEntity.getTeam()) &&
				EntityFactionFind.isFaction(this, livingEntity)
		) {
			return false;
		}
		return super.canAttack(livingEntity);
	}
}
