package com.jerotes.jerotesvillage.entity.Monster.WickedRidiculeCalls;

import com.jerotes.jerotes.entity.Interface.CanNotSkeletonSpiderEntity;
import com.jerotes.jerotes.entity.Interface.FactionEntity;
import com.jerotes.jerotes.entity.Interface.JerotesEntity;
import com.jerotes.jerotes.entity.Interface.WickedRidiculeCallsEntity;
import com.jerotes.jerotes.entity.Mob.HumanEntity;
import com.jerotes.jerotes.goal.JerotesHelpAlliesGoal;
import com.jerotes.jerotes.goal.JerotesHelpSameFactionGoal;
import com.jerotes.jerotes.init.JerotesMobEffects;
import com.jerotes.jerotes.util.EntityFactionFind;
import com.jerotes.jerotes.util.Main;
import com.jerotes.jerotesvillage.entity.Humanoid.SecondRounderEntity;
import com.jerotes.jerotesvillage.init.JerotesVillageEntityType;
import com.jerotes.jerotesvillage.init.JerotesVillageMobEffects;
import com.jerotes.jerotesvillage.network.JerotesVillageLevelData;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.syncher.EntityDataAccessor;
import net.minecraft.network.syncher.EntityDataSerializers;
import net.minecraft.network.syncher.SynchedEntityData;
import net.minecraft.util.RandomSource;
import net.minecraft.world.Difficulty;
import net.minecraft.world.DifficultyInstance;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.*;
import net.minecraft.world.entity.ai.attributes.AttributeSupplier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.ai.goal.target.NearestAttackableTargetGoal;
import net.minecraft.world.entity.monster.Spider;
import net.minecraft.world.entity.npc.AbstractVillager;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.ServerLevelAccessor;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.Vec3;
import org.joml.Vector3f;

import javax.annotation.Nullable;

public class SecondRounderSpiderEntity extends Spider implements JerotesEntity, WickedRidiculeCallsEntity, CanNotSkeletonSpiderEntity, FactionEntity {
	private static final EntityDataAccessor<Integer> COLOR = SynchedEntityData.defineId(SecondRounderSpiderEntity.class, EntityDataSerializers.INT);
	private static final EntityDataAccessor<Integer> PUSH_TICK = SynchedEntityData.defineId(SecondRounderSpiderEntity.class, EntityDataSerializers.INT);

	public SecondRounderSpiderEntity(EntityType<? extends SecondRounderSpiderEntity> entityType, Level level) {
		super(entityType, level);
		this.xpReward = 10;
	}

	@Override
	public String getFirstFactionTypeName() {
		return "wicked_ridicule_calls";
	}

	public static AttributeSupplier.Builder createAttributes() {
		AttributeSupplier.Builder builder = Mob.createMobAttributes();
		builder = builder.add(Attributes.MOVEMENT_SPEED, 0.34);
		builder = builder.add(Attributes.MAX_HEALTH, 28);
		builder = builder.add(Attributes.ARMOR, 2);
		builder = builder.add(Attributes.ATTACK_DAMAGE, 4);
		builder = builder.add(Attributes.FOLLOW_RANGE, 35);
		return builder;
	}

	@Override
	protected void registerGoals() {
		super.registerGoals();
		this.targetSelector.addGoal(1, new JerotesHelpSameFactionGoal(this, LivingEntity.class, false, false, livingEntity -> livingEntity instanceof LivingEntity));
		this.targetSelector.addGoal(1, new NearestAttackableTargetGoal<LivingEntity>(this, LivingEntity.class, 5, false, false, livingEntity -> EntityFactionFind.isHateFaction(this, livingEntity)));
		this.targetSelector.addGoal(1, new JerotesHelpAlliesGoal(this, LivingEntity.class, false, false, livingEntity -> livingEntity instanceof LivingEntity));
		this.targetSelector.addGoal(2, new NearestAttackableTargetGoal<SecondRounderEntity>(this, SecondRounderEntity.class, false));
		this.targetSelector.addGoal(2, new NearestAttackableTargetGoal<HumanEntity>(this, HumanEntity.class, false));
		this.targetSelector.addGoal(2, new NearestAttackableTargetGoal<AbstractVillager>(this, AbstractVillager.class, false));
	}

	//
	protected void positionRider(Entity entity, MoveFunction moveFunction) {
		Vec3 vec3 = this.getPassengerRidingPosition(entity);
		moveFunction.accept(entity, vec3.x, vec3.y + getMyRidingOffset(this), vec3.z);
	}
	public float getMyRidingOffset(Entity entity) {
		return this.ridingOffset(entity) * this.getScale();
	}
	protected float ridingOffset(Entity entity) {
		return -0.75F;
	}
	public Vec3 getPassengerRidingPosition(Entity entity) {
		return (new Vec3(this.getPassengerAttachmentPoint(entity, this.getDimensions(this.getPose()), this.getScale()).rotateY(-this.yBodyRot * ((float)Math.PI / 180F)))).add(this.position());
	}
	protected Vector3f getPassengerAttachmentPoint(Entity entity, EntityDimensions entityDimensions, float f) {
		return new Vector3f(0.0F, entityDimensions.height, 0.0F);
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

	public void setColor(int n) {
		this.getEntityData().set(COLOR, n);
	}
	public int getColor() {
		return this.getEntityData().get(COLOR);
	}
	public void setPushTick(int n) {
		this.getEntityData().set(PUSH_TICK, n);
	}
	public int getPushTick() {
		return this.getEntityData().get(PUSH_TICK);
	}
	@Override
	public void addAdditionalSaveData(CompoundTag compoundTag) {
		super.addAdditionalSaveData(compoundTag);
		compoundTag.putInt("Color", this.getColor());
		compoundTag.putInt("PushTick", this.getPushTick());
	}
	@Override
	public void readAdditionalSaveData(CompoundTag compoundTag) {
		super.readAdditionalSaveData(compoundTag);
		this.setColor(compoundTag.getInt("Color"));
		this.setPushTick(compoundTag.getInt("PushTick"));
	}
	@Override
	protected void defineSynchedData() {
		super.defineSynchedData();
		this.getEntityData().define(COLOR, 1);
		this.getEntityData().define(PUSH_TICK, 0);
	}
	@Override
	public void makeStuckInBlock(BlockState blockState, Vec3 vec3) {
		if (!blockState.is(Blocks.COBWEB)) {
			super.makeStuckInBlock(blockState, vec3);
		}
		this.resetFallDistance();
	}
	@Override
	protected void doPush(Entity entity) {
		if (!(entity instanceof WickedRidiculeCallsEntity || entity.isAlliedTo(this)) || entity.canBeCollidedWith() || this.getTarget() == null) {
			super.doPush(entity);
		}
		else {
			this.setPushTick(5);
		}
	}

	@Override
	public void aiStep() {
		super.aiStep();
		if (this.getPushTick() > 0) {
			if (!this.level().isClientSide()) {
				this.setPushTick(this.getPushTick() - 1);
			}
		}
		//停止战斗
		if (this.getTarget() != null && (!this.getTarget().isAlive() || this.getTarget() instanceof Player player && (player.isCreative() || player.isSpectator()))) {
			this.setTarget(null);
		}
		//
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

	@Override
	public boolean isInvulnerableTo(DamageSource damageSource) {
		return super.isInvulnerableTo(damageSource);
	}

	@Nullable
	@Override
	public SpawnGroupData finalizeSpawn(ServerLevelAccessor serverLevelAccessor, DifficultyInstance difficultyInstance, MobSpawnType mobSpawnType, @Nullable SpawnGroupData spawnGroupData, @Nullable CompoundTag compoundTag) {
		SpawnGroupData spawnGroupData2 = super.finalizeSpawn(serverLevelAccessor, difficultyInstance, mobSpawnType, spawnGroupData, compoundTag);
		Object object;
		RandomSource randomSource = serverLevelAccessor.getRandom();
		if (randomSource.nextInt(40) == 1 && (object = JerotesVillageEntityType.SECOND_ROUNDER_SKELETON.get().create(this.level())) != null) {
			((Entity)object).moveTo(this.getX(), this.getY(), this.getZ(), this.getYRot(), 0.0f);
			((SecondRounderSkeletonEntity)object).finalizeSpawn(serverLevelAccessor, difficultyInstance, mobSpawnType, null, null);
			if (!this.level().isClientSide()) {
				((Entity) object).startRiding(this);
			}
		}

		if (spawnGroupData2 == null) {
			spawnGroupData2 = new SpiderEffectsGroupData();
			if (serverLevelAccessor.getDifficulty() == Difficulty.HARD && randomSource.nextFloat() < 0.1F * difficultyInstance.getSpecialMultiplier()) {
				((SpiderEffectsGroupData)spawnGroupData2).setRandomEffect(randomSource);
			}
		}

		this.setColor(Main.randomReach(serverLevelAccessor.getRandom(), 0, 16777215));
		if (JerotesVillageLevelData.get(serverLevelAccessor) != null && JerotesVillageLevelData.get(serverLevelAccessor).WickedRidiculeNight) {
			this.setColor(0xff0000);
		}
		return spawnGroupData2;
	}


	public static class SpiderEffectsGroupData implements SpawnGroupData {
		@Nullable
		public MobEffect effect;

		public SpiderEffectsGroupData() {
		}

		public void setRandomEffect(RandomSource p_219119_) {
			int i = p_219119_.nextInt(9);
			if (i <= 1) {
				this.effect = JerotesMobEffects.MAGIC_ABSORPTION.get();
			} else if (i <= 2) {
				this.effect = JerotesMobEffects.TRUE_INVISIBLE.get();
			} else if (i <= 3) {
				this.effect = JerotesMobEffects.COUNTERSPELL.get();
			} else if (i <= 4) {
				this.effect = JerotesVillageMobEffects.RAMPANT.get();
			} else if (i <= 5) {
				this.effect = JerotesVillageMobEffects.ABUNDANT_COURAGE.get();
			} else if (i <= 6) {
				this.effect = MobEffects.MOVEMENT_SPEED;
			} else if (i <= 7) {
				this.effect = MobEffects.DAMAGE_BOOST;
			} else if (i <= 8) {
				this.effect = MobEffects.REGENERATION;
			} else if (i <= 9) {
				this.effect = MobEffects.INVISIBILITY;
			}
		}
	}
}