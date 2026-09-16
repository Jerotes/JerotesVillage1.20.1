package com.jerotes.jerotesvillage.entity.Monster;

import com.jerotes.jerotes.entity.Interface.FactionEntity;
import com.jerotes.jerotes.entity.Interface.JerotesEntity;
import com.jerotes.jerotes.entity.Mob.HumanEntity;
import com.jerotes.jerotes.goal.JerotesMeleeAttackGoal;
import com.jerotes.jerotes.init.JerotesDamageTypes;
import com.jerotes.jerotes.init.JerotesMobEffects;
import com.jerotes.jerotes.util.AttackFind;
import com.jerotes.jerotes.util.EntityAndItemFind;
import com.jerotes.jerotesvillage.init.JerotesVillageBlocks;
import com.jerotes.jerotesvillage.init.JerotesVillageSoundEvents;
import net.minecraft.core.BlockPos;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.syncher.EntityDataAccessor;
import net.minecraft.network.syncher.EntityDataSerializers;
import net.minecraft.network.syncher.SynchedEntityData;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.tags.DamageTypeTags;
import net.minecraft.util.Mth;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.damagesource.DamageTypes;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.entity.*;
import net.minecraft.world.entity.ai.attributes.AttributeSupplier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.ai.goal.FloatGoal;
import net.minecraft.world.entity.ai.goal.LookAtPlayerGoal;
import net.minecraft.world.entity.ai.goal.RandomLookAroundGoal;
import net.minecraft.world.entity.ai.goal.RandomStrollGoal;
import net.minecraft.world.entity.ai.goal.target.HurtByTargetGoal;
import net.minecraft.world.entity.ai.goal.target.NearestAttackableTargetGoal;
import net.minecraft.world.entity.animal.IronGolem;
import net.minecraft.world.entity.monster.Monster;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.GameRules;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.gameevent.GameEvent;

public class SliderEntity extends Monster implements JerotesEntity {
	private static final EntityDataAccessor<Integer> ATTACK_TICK = SynchedEntityData.defineId(SliderEntity.class, EntityDataSerializers.INT);

	public SliderEntity(EntityType<? extends SliderEntity> entityType, Level level) {
		super(entityType, level);
		setMaxUpStep(3.1f);
		this.xpReward = 20;
	}

	@Override
	protected void registerGoals() {
		this.goalSelector.addGoal(0, new FloatGoal(this));
		this.goalSelector.addGoal(1, new JerotesMeleeAttackGoal(this, 1.0, false));
		this.goalSelector.addGoal(2, new LookAtPlayerGoal(this, Player.class, 3.0f, 1.0f));
		this.goalSelector.addGoal(2, new LookAtPlayerGoal(this, HumanEntity.class, 3.0f, 1.0f));
		this.goalSelector.addGoal(3, new RandomLookAroundGoal(this));
		this.goalSelector.addGoal(4, new RandomStrollGoal(this, 0.6));
		this.targetSelector.addGoal(1, new HurtByTargetGoal(this, new Class[0]));
		this.targetSelector.addGoal(2, new NearestAttackableTargetGoal<Player>(this, Player.class, true));
		this.targetSelector.addGoal(2, new NearestAttackableTargetGoal<IronGolem>(this, IronGolem.class, true));
	}

	public static AttributeSupplier.Builder createAttributes() {
		AttributeSupplier.Builder builder = Mob.createMobAttributes();
		builder = builder.add(Attributes.MOVEMENT_SPEED, 0.25);
		builder = builder.add(Attributes.MAX_HEALTH, 18);
		builder = builder.add(Attributes.ARMOR, 2);
		builder = builder.add(Attributes.ATTACK_DAMAGE, 3);
		builder = builder.add(Attributes.FOLLOW_RANGE, 24);
		builder = builder.add(Attributes.KNOCKBACK_RESISTANCE, 0.95);
		return builder;
	}

	@Override
	protected void playStepSound(BlockPos blockPos, BlockState blockState) {
		this.playSound(JerotesVillageSoundEvents.SLIDER_AMBIENT, 0.15f, 1.0f);
	}
	@Override
	protected SoundEvent getAmbientSound() {
		return JerotesVillageSoundEvents.SLIDER_AMBIENT;
	}
	@Override
	protected SoundEvent getHurtSound(DamageSource damageSource) {
		return JerotesVillageSoundEvents.SLIDER_AMBIENT;
	}
	@Override
	protected SoundEvent getDeathSound() {
		return JerotesVillageSoundEvents.SLIDER_AMBIENT;
	}

	@Override
	public boolean isInvulnerableTo(DamageSource damageSource) {
		if (damageSource.is(DamageTypes.CACTUS)
				|| damageSource.is(DamageTypes.SWEET_BERRY_BUSH)
				|| AttackFind.isBleedDamage(damageSource)
				|| damageSource.is(DamageTypeTags.IS_FALL))
			return true;
		return super.isInvulnerableTo(damageSource);
	}
	@Override
	public boolean hurt(DamageSource damageSource, float amount) {
		if (isInvulnerableTo(damageSource)) {
			return super.hurt(damageSource, amount);
		}
		if (!AttackFind.MagicResistance(damageSource) && !damageSource.is(DamageTypeTags.BYPASSES_INVULNERABILITY)) {
			//光滑
			if (amount < this.getAttributeValue(Attributes.MAX_HEALTH) * 0.75f) {
				if (this.random.nextFloat() < 0.75) {
					return false;
				}
			}
			else if (amount < this.getAttributeValue(Attributes.MAX_HEALTH) * 1.5f) {
				if (this.random.nextFloat() < 0.5) {
					return false;
				}
			}
		}
		return super.hurt(damageSource, amount);
	}

	@Override
	protected float getStandingEyeHeight(Pose pose, EntityDimensions entityDimensions) {
		return 0.4f;
	}

	public void setAttackTick(int n){
		this.getEntityData().set(ATTACK_TICK, n);
	}
	public int getAttackTick(){
		return this.getEntityData().get(ATTACK_TICK);
	}
	@Override
	public void addAdditionalSaveData(CompoundTag compoundTag) {
		super.addAdditionalSaveData(compoundTag);
		compoundTag.putInt("AttackTick", this.getAttackTick());
	}
	@Override
	public void readAdditionalSaveData(CompoundTag compoundTag) {
		super.readAdditionalSaveData(compoundTag);
		this.setAttackTick(compoundTag.getInt("AttackTick"));
	}
	@Override
	protected void defineSynchedData() {
		super.defineSynchedData();
		this.getEntityData().define(ATTACK_TICK, 0);
	}

	public void aiStep() {
		super.aiStep();
		//停止战斗
		if (this.getTarget() != null && (!this.getTarget().isAlive() || this.getTarget() instanceof Player player && (player.isCreative() || player.isSpectator()))) {
			this.setTarget(null);
		}
		//
		if (!this.level().isClientSide()) {
			this.setAttackTick(Math.max(-10, this.getAttackTick() - 1));
			//痕迹
			if (this.level().getGameRules().getBoolean(GameRules.RULE_MOBGRIEFING)) {
				BlockState blockState = JerotesVillageBlocks.SLIDER_MARK.get().defaultBlockState();
				for (int i = 0; i < 4; ++i) {
					int n = Mth.floor(this.getX() + (double)((float)(i % 2 * 2 - 1) * 0.25f));
					int n2 = Mth.floor(this.getY());
					int n3 = Mth.floor(this.getZ() + (double)((float)(i / 2 % 2 * 2 - 1) * 0.25f));
					BlockPos blockPos = new BlockPos(n, n2, n3);
					if (!this.level().getBlockState(blockPos).isAir() || !blockState.canSurvive(this.level(), blockPos)) continue;
					this.level().setBlockAndUpdate(blockPos, blockState);
					this.level().gameEvent(GameEvent.BLOCK_PLACE, blockPos, GameEvent.Context.of(this, blockState));
				}
			}
		}
	}

	@Override
	public boolean doHurtTarget(Entity entity) {
		if (this.getAttackTick() > -10) {
			return false;
		}
		if (!this.level().isClientSide()) {
			this.setAttackTick(10);
		}
		boolean bl = super.doHurtTarget(entity);
		if (bl) {
			if (!this.isSilent()) {
				this.playSound(JerotesVillageSoundEvents.SLIDER_AMBIENT, 1.0f, 1.0f);
			}
			if (entity instanceof LivingEntity livingEntity) {
				if (!livingEntity.level().isClientSide) {
					livingEntity.addEffect(new MobEffectInstance(JerotesMobEffects.CORROSIVE.get(), 60, 0), this);
				}
			}
		}
		return bl;
	}
}

