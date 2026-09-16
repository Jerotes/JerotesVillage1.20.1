package com.jerotes.jerotesvillage.entity.Monster;

import com.jerotes.jerotes.entity.Interface.FactionEntity;
import com.jerotes.jerotes.entity.Interface.JerotesEntity;
import com.jerotes.jerotes.entity.Interface.MagicSpellLivingEntity;
import com.jerotes.jerotes.entity.Interface.SpellUseEntity;
import com.jerotes.jerotes.entity.Mob.HumanEntity;
import com.jerotes.jerotes.util.EntityAndItemFind;
import com.jerotes.jerotesvillage.entity.Humanoid.SecondRounderEntity;
import com.jerotes.jerotesvillage.goal.EyeDiscRangedAttackGoal;
import com.jerotes.jerotesvillage.goal.HighNearestAttackableTargetGoal;
import com.jerotes.jerotesvillage.init.JerotesVillageParticleTypes;
import com.jerotes.jerotesvillage.init.JerotesVillageSoundEvents;
import com.jerotes.jerotesvillage.spell.OtherSpellList;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.syncher.EntityDataAccessor;
import net.minecraft.network.syncher.EntityDataSerializers;
import net.minecraft.network.syncher.SynchedEntityData;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.tags.DamageTypeTags;
import net.minecraft.util.RandomSource;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.*;
import net.minecraft.world.entity.ai.attributes.AttributeSupplier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.ai.control.FlyingMoveControl;
import net.minecraft.world.entity.ai.goal.LookAtPlayerGoal;
import net.minecraft.world.entity.ai.goal.RandomStrollGoal;
import net.minecraft.world.entity.ai.goal.WaterAvoidingRandomStrollGoal;
import net.minecraft.world.entity.ai.goal.target.HurtByTargetGoal;
import net.minecraft.world.entity.ai.navigation.FlyingPathNavigation;
import net.minecraft.world.entity.ai.navigation.PathNavigation;
import net.minecraft.world.entity.animal.IronGolem;
import net.minecraft.world.entity.monster.Monster;
import net.minecraft.world.entity.monster.RangedAttackMob;
import net.minecraft.world.entity.npc.AbstractVillager;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.Vec3;

public class EyeDiscEntity extends Monster implements RangedAttackMob, JerotesEntity, FactionEntity, SpellUseEntity {
	private static final EntityDataAccessor<Boolean> BLIND = SynchedEntityData.defineId(EyeDiscEntity.class, EntityDataSerializers.BOOLEAN);
	private static final EntityDataAccessor<Integer> SPELL_LEVEL_OF = SynchedEntityData.defineId(EyeDiscEntity.class, EntityDataSerializers.INT);
	private static final EntityDataAccessor<Integer> SHOOT_TICK = SynchedEntityData.defineId(EyeDiscEntity.class, EntityDataSerializers.INT);

	public EyeDiscEntity(EntityType<? extends EyeDiscEntity> entityType, Level level) {
		super(entityType, level);
		setMaxUpStep(2.6f);
		this.xpReward = 20;
		this.moveControl = new FlyingMoveControl(this, 3, true);
	}
	public boolean isJerotesFlyingMob() {
		return true;
	}

	@Override
	protected PathNavigation createNavigation(Level world) {
		return new FlyingPathNavigation(this, world);
	}

	@Override
	protected void registerGoals() {
		this.goalSelector.addGoal(1, new EyeDiscRangedAttackGoal(this, 1.5, 2, 36.0f));
		this.goalSelector.addGoal(2, new LookAtPlayerGoal(this, LivingEntity.class, 3.0f, 1.0f));
		this.goalSelector.addGoal(2, new RandomStrollGoal(this, 0.8, 20) {
			@Override
			protected Vec3 getPosition() {
				RandomSource random = EyeDiscEntity.this.getRandom();
				double dir_x = EyeDiscEntity.this.getX() + ((random.nextFloat() * 2 - 1) * 16);
				double dir_y = EyeDiscEntity.this.getY() + ((random.nextFloat() * 2 - 1) * 16);
				double dir_z = EyeDiscEntity.this.getZ() + ((random.nextFloat() * 2 - 1) * 16);
				return new Vec3(dir_x, dir_y, dir_z);
			}
		});
		this.goalSelector.addGoal(7, new WaterAvoidingRandomStrollGoal(this, 1.0, 0.0f));
		this.targetSelector.addGoal(1, new HurtByTargetGoal(this, new Class[0]));
		this.targetSelector.addGoal(2, new HighNearestAttackableTargetGoal<Player>(this, Player.class, false));
		this.targetSelector.addGoal(2, new HighNearestAttackableTargetGoal<SecondRounderEntity>(this, SecondRounderEntity.class, false));
		this.targetSelector.addGoal(2, new HighNearestAttackableTargetGoal<HumanEntity>(this, HumanEntity.class, false));
		this.targetSelector.addGoal(2, new HighNearestAttackableTargetGoal<AbstractVillager>(this, AbstractVillager.class, false));
		this.targetSelector.addGoal(2, new HighNearestAttackableTargetGoal<IronGolem>(this, IronGolem.class, false));
	}


	public void performRangedAttack(LivingEntity livingEntity, float f) {
		//眼盘射线
		this.setSpellLevelOf(this.getSpellLevel());
		if (!this.level().isClientSide()) {
			this.setShootTick(60 + this.getSpellLevel() * 50);
		}
		//法术列表-眼盘射线
		OtherSpellList.EyeDiscShoot(this.getSpellLevel(), this, livingEntity).spellUse();
	}

	public static AttributeSupplier.Builder createAttributes() {
		AttributeSupplier.Builder builder = Mob.createMobAttributes();
		builder = builder.add(Attributes.MOVEMENT_SPEED, 0.15);
		builder = builder.add(Attributes.FLYING_SPEED, 1.15);
		builder = builder.add(Attributes.MAX_HEALTH, 32);
		builder = builder.add(Attributes.ATTACK_DAMAGE, 4);
		builder = builder.add(Attributes.FOLLOW_RANGE, 48);
		return builder;
	}

	@Override
	protected SoundEvent getHurtSound(DamageSource damageSource) {
		return JerotesVillageSoundEvents.EYE_DISC_HURT;
	}

	@Override
	protected SoundEvent getDeathSound() {
		return JerotesVillageSoundEvents.EYE_DISC_DEATH;
	}

	@Override
	protected float getSoundVolume() {
		return 1f;
	}

	@Override
	protected float getStandingEyeHeight(Pose pose, EntityDimensions entityDimensions) {
		return 1.125f;
	}

	@Override
	public boolean isInvulnerableTo(DamageSource damageSource) {
		if (damageSource.is(DamageTypeTags.IS_FALL))
			return true;
		return super.isInvulnerableTo(damageSource);
	}
	@Override
	public boolean hurt(DamageSource damageSource, float amount) {
		if (isInvulnerableTo(damageSource)) {
			return super.hurt(damageSource, amount);
		}
		if (EntityAndItemFind.canSelfResistMagic(damageSource, amount, this, getSpellLevel() + 2) && !this.isBlind())
			amount /= 3;
		return super.hurt(damageSource, amount);
	}

	public int spellLevel = 2;
	@Override
	public int getSpellLevel() {
		return this.spellLevel;
	}
	public boolean isBlind() {
		return this.getEntityData().get(BLIND);
	}
	public void setBlind(boolean bl) {
		this.getEntityData().set(BLIND, bl);
	}
	public int getSpellLevelOf() {
		return this.getEntityData().get(SPELL_LEVEL_OF);
	}
	public void setSpellLevelOf(int n) {
		this.getEntityData().set(SPELL_LEVEL_OF, n);
	}
	public int getShootTick() {
		return this.getEntityData().get(SHOOT_TICK);
	}
	public void setShootTick(int n) {
		this.getEntityData().set(SHOOT_TICK, n);
	}
	@Override
	public void addAdditionalSaveData(CompoundTag compoundTag) {
		super.addAdditionalSaveData(compoundTag);
		compoundTag.putInt("SpellLevel", this.spellLevel);
		compoundTag.putInt("ShootTick", this.getShootTick());
		compoundTag.putBoolean("IsBlind", this.isBlind());
		compoundTag.putInt("SpellLevelOf", this.getSpellLevelOf());
	}

	@Override
	public void readAdditionalSaveData(CompoundTag compoundTag) {
		super.readAdditionalSaveData(compoundTag);
		this.spellLevel = compoundTag.getInt("SpellLevel");
		this.setShootTick(compoundTag.getInt("ShootTick"));
		this.setBlind(compoundTag.getBoolean("IsBlind"));
		this.setSpellLevelOf(compoundTag.getInt("SpellLevelOf"));
	}
	@Override
	protected void defineSynchedData() {
		super.defineSynchedData();
		this.getEntityData().define(BLIND, false);
		this.getEntityData().define(SPELL_LEVEL_OF, 2);
		this.getEntityData().define(SHOOT_TICK, 0);
	}
	@Override
	public void onSyncedDataUpdated(EntityDataAccessor<?> entityDataAccessor) {
		if (BLIND.equals(entityDataAccessor)) {
			this.refreshDimensions();
		}
		super.onSyncedDataUpdated(entityDataAccessor);
	}

	public void aiStep() {
		super.aiStep();
		if (this.level().isClientSide) {
			for (int i = 0; i < 13; ++i) {
				this.level().addParticle(JerotesVillageParticleTypes.EYE_DISC.get(), this.getRandomX(1), this.getRandomY(), this.getRandomZ(1), 0.0, 0.0, 0.0);
			}
		}
		//停止战斗
		if (this.getTarget() != null && (!this.getTarget().isAlive() || this.getTarget() instanceof Player player && (player.isCreative() || player.isSpectator()))) {
			this.setTarget(null);
		}
		//

		if (this.getTarget() != null) {
			if (this instanceof MagicSpellLivingEntity magicSpellLivingEntity) {
				if (magicSpellLivingEntity.getCastingTickJerotes() > 0) {
					this.getLookControl().setLookAt(this.getTarget(), 360.0f, 360.0f);
					this.lookAt(this.getTarget(), 360.0f, 360.0f);
				}
			}
		}
		if (!this.level().isClientSide()) {
			this.setShootTick(Math.max(0, this.getShootTick() - 1));
			if (this.getTarget() == null && !this.isAggressive()) {
				if (this instanceof MagicSpellLivingEntity magicSpellLivingEntity) {
					magicSpellLivingEntity.setCastingTickJerotes(0);
					this.setShootTick(magicSpellLivingEntity.getCastingTickJerotes());
				}
			}
		}
		if (this.hasEffect(MobEffects.BLINDNESS) && !this.isBlind()) {
			this.setBlind(true);
		}
		if (!(this.hasEffect(MobEffects.BLINDNESS)) && this.isBlind()) {
			this.setBlind(false);
		}
	}
}

