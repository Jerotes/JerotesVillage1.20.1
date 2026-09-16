package com.jerotes.jerotesvillage.entity.Monster.Elite;

import com.jerotes.jerotes.entity.Interface.EliteEntity;
import com.jerotes.jerotes.entity.Interface.FireAbsorptionEntity;
import com.jerotes.jerotes.entity.Interface.FactionEntity;
import com.jerotes.jerotes.entity.Interface.JerotesEntity;
import com.jerotes.jerotes.entity.Interface.SpellUseEntity;
import com.jerotes.jerotes.entity.Mob.HumanEntity;
import com.jerotes.jerotes.event.JerotesBossEvent;
import com.jerotes.jerotes.goal.JerotesAttackAvoidEntityGoal;
import com.jerotes.jerotes.goal.JerotesFireRangeAttackGoal;
import com.jerotes.jerotes.util.AttackFind;
import com.jerotes.jerotes.util.EntityAndItemFind;
import com.jerotes.jerotes.util.Main;
import com.jerotes.jerotesvillage.config.OtherMainConfig;
import com.jerotes.jerotesvillage.control.FireSecretorMoveControl;
import com.jerotes.jerotesvillage.entity.Humanoid.SecondRounderEntity;
import com.jerotes.jerotesvillage.entity.Shoot.Magic.MagicShoot.SecreteFireOilBombEntity;
import com.jerotes.jerotesvillage.init.JerotesVillageGameRules;
import com.jerotes.jerotesvillage.init.JerotesVillageItems;
import com.jerotes.jerotesvillage.init.JerotesVillageParticleTypes;
import com.jerotes.jerotesvillage.init.JerotesVillageSoundEvents;
import com.jerotes.jerotesvillage.spell.OtherSpellList;
import net.minecraft.core.BlockPos;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.chat.Component;
import net.minecraft.network.syncher.EntityDataAccessor;
import net.minecraft.network.syncher.EntityDataSerializers;
import net.minecraft.network.syncher.SynchedEntityData;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.tags.DamageTypeTags;
import net.minecraft.util.RandomSource;
import net.minecraft.world.BossEvent;
import net.minecraft.world.Difficulty;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.damagesource.DamageTypes;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.AnimationState;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.ai.attributes.AttributeSupplier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.ai.goal.WaterAvoidingRandomFlyingGoal;
import net.minecraft.world.entity.ai.goal.WaterAvoidingRandomStrollGoal;
import net.minecraft.world.entity.ai.goal.target.HurtByTargetGoal;
import net.minecraft.world.entity.ai.goal.target.NearestAttackableTargetGoal;
import net.minecraft.world.entity.ai.navigation.FlyingPathNavigation;
import net.minecraft.world.entity.ai.navigation.PathNavigation;
import net.minecraft.world.entity.animal.IronGolem;
import net.minecraft.world.entity.item.ItemEntity;
import net.minecraft.world.entity.monster.Monster;
import net.minecraft.world.entity.monster.RangedAttackMob;
import net.minecraft.world.entity.npc.AbstractVillager;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.pathfinder.BlockPathTypes;

import javax.annotation.Nullable;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class FireSecretorEntity extends Monster implements RangedAttackMob, JerotesEntity, FactionEntity, FireAbsorptionEntity, SpellUseEntity, EliteEntity {
	public AnimationState fireAnimationState = new AnimationState();
	public AnimationState smokeAnimationState = new AnimationState();
	public AnimationState idleAnimationState = new AnimationState();
	public AnimationState deadAnimationState = new AnimationState();
	private final JerotesBossEvent bossEvent = new JerotesBossEvent(this, this.getUUID(), BossEvent.BossBarColor.RED, BossEvent.BossBarOverlay.NOTCHED_10, false);
	private static final EntityDataAccessor<Integer> ANIM_STATE = SynchedEntityData.defineId(FireSecretorEntity.class, EntityDataSerializers.INT);
	private static final EntityDataAccessor<Integer> ANIM_TICK = SynchedEntityData.defineId(FireSecretorEntity.class, EntityDataSerializers.INT);

	public FireSecretorEntity(EntityType<? extends FireSecretorEntity> entityType, Level level) {
		super(entityType, level);
		this.setMaxUpStep(1.1f);
		this.xpReward = 50;
		this.moveControl = new FireSecretorMoveControl(this, 10, false);
		this.setPathfindingMalus(BlockPathTypes.WATER, -1.0f);
		this.setPathfindingMalus(BlockPathTypes.LAVA, 8.0f);
		this.setPathfindingMalus(BlockPathTypes.DAMAGE_FIRE, 0.0f);
		this.setPathfindingMalus(BlockPathTypes.DANGER_FIRE, 0.0f);
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
	protected PathNavigation createNavigation(Level world) {
		return new FlyingPathNavigation(this, world);
	}

	@Override
	protected void registerGoals() {
		this.goalSelector.addGoal(0, new JerotesAttackAvoidEntityGoal<>(this, LivingEntity.class, 8.0f, 1.0, 1.2));
		this.goalSelector.addGoal(1, new JerotesFireRangeAttackGoal(this, 1.25, 60, 20.0f));
		this.goalSelector.addGoal(2, new WaterAvoidingRandomFlyingGoal(this, 1.0));
		this.goalSelector.addGoal(3, new WaterAvoidingRandomStrollGoal(this, 1.0, 0.0f));
		this.targetSelector.addGoal(1, new HurtByTargetGoal(this, new Class[0]));
		this.targetSelector.addGoal(2, new NearestAttackableTargetGoal<Player>(this, Player.class, true){
			@Override
			public boolean canUse() {
				if (FireSecretorEntity.this.level().getDifficulty() == Difficulty.PEACEFUL) {
					return false;
				}
				return super.canUse();
			}
			@Override
			protected double getFollowDistance() {
				return super.getFollowDistance() * 0.5;
			}
		});
		this.targetSelector.addGoal(2, new NearestAttackableTargetGoal<SecondRounderEntity>(this, SecondRounderEntity.class, true){
			@Override
			protected double getFollowDistance() {
				return super.getFollowDistance() * 0.5;
			}
		});
		this.targetSelector.addGoal(2, new NearestAttackableTargetGoal<HumanEntity>(this, HumanEntity.class, false){
			@Override
			protected double getFollowDistance() {
				return super.getFollowDistance() * 0.5;
			}
		});
		this.targetSelector.addGoal(2, new NearestAttackableTargetGoal<AbstractVillager>(this, AbstractVillager.class, true){
			@Override
			protected double getFollowDistance() {
				return super.getFollowDistance() * 0.5;
			}
		});
		this.targetSelector.addGoal(2, new NearestAttackableTargetGoal<IronGolem>(this, IronGolem.class, true){
			@Override
			protected double getFollowDistance() {
				return super.getFollowDistance() * 0.5;
			}
		});
	}

	@Override
	public void performRangedAttack(LivingEntity livingEntity, float v) {
		if (Main.randomReach(this.getRandom(), 1, 16) > 2 || this.distanceTo(livingEntity) <= 8) {
			//技能-泌火油弹
			if (!this.level().isClientSide()) {
				this.setAnimTick(15);
				this.setAnimationState("fire");
			}
			//法术列表-泌火油弹
			OtherSpellList.SecreteFireOilBomb(this.getSpellLevel(), this, livingEntity).spellUse();
		}
		else {
			//技能-浓烟飞弹
			int spellLevel = this.getSpellLevel();
			if (!this.level().isClientSide()) {
				this.setAnimTick(15);
				this.setAnimationState("smoke");
			}
			//法术列表-浓烟飞弹
			OtherSpellList.SmokeMissile(this.getSpellLevel(), this, livingEntity).spellUse();
		}
	}

	@Override
	public boolean removeWhenFarAway(double d) {
		return false;
	}
	@Override
	protected boolean shouldDespawnInPeaceful() {
		return false;
	}
	@Override
	public double FireAbsorptionPercentage() {
		return 80;
	}

	public static AttributeSupplier.Builder createAttributes() {
		AttributeSupplier.Builder builder = Mob.createMobAttributes();
		builder = builder.add(Attributes.MOVEMENT_SPEED, 0.35);
		builder = builder.add(Attributes.MAX_HEALTH, 102);
		builder = builder.add(Attributes.ATTACK_DAMAGE, 4);
		builder = builder.add(Attributes.FOLLOW_RANGE, 64);
		builder = builder.add(Attributes.ARMOR, 12);
		builder = builder.add(Attributes.KNOCKBACK_RESISTANCE, 0.3);
		return builder;
	}

	@Override
	public boolean isOnFire() {
		return false;
	}

	@Override
	protected SoundEvent getAmbientSound() {
		return JerotesVillageSoundEvents.FIRE_SECRETOR_AMBIENT;
	}

	@Override
	protected SoundEvent getHurtSound(DamageSource damageSource) {
		return JerotesVillageSoundEvents.FIRE_SECRETOR_HURT;
	}

	@Override
	protected SoundEvent getDeathSound() {
		return JerotesVillageSoundEvents.FIRE_SECRETOR_DEATH;
	}

	@Override
	protected float getSoundVolume() {
		return 2.0f;
	}

	@Override
	public boolean isInvulnerableTo(DamageSource damageSource) {
		if (damageSource.is(DamageTypes.CACTUS)
				|| damageSource.is(DamageTypes.SWEET_BERRY_BUSH)
				|| damageSource.is(DamageTypeTags.IS_FALL)
				|| AttackFind.isFireDamage(damageSource)
		)
			return true;
		if (damageSource.getEntity() == this || (damageSource.getEntity() != null && damageSource.getEntity() instanceof SecreteFireOilBombEntity secreteFireOilBomb && secreteFireOilBomb.getOwner() == this))
			return true;
		return super.isInvulnerableTo(damageSource);
	}
	@Override
	public boolean hurt(DamageSource damageSource, float amount) {
		if (isInvulnerableTo(damageSource)) {
			return super.hurt(damageSource, amount);
		}
		this.fallTick = 15;
		if (damageSource.is(DamageTypes.EXPLOSION))
			return super.hurt(damageSource, amount/3);
		return super.hurt(damageSource, amount);
	}

	@Override
	public boolean canBeAffected(MobEffectInstance mobEffectInstance) {
		if (mobEffectInstance.getEffect() == MobEffects.BLINDNESS) {
			return false;
		}
		return super.canBeAffected(mobEffectInstance);
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
		if (this.random.nextInt(900) == 1 && this.deathTime == 0) {
			this.heal(3.0f);
		}
		//停止战斗
		if (this.getTarget() != null && (!this.getTarget().isAlive() || this.getTarget() instanceof Player player && (player.isCreative() || player.isSpectator()))){
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
		this.clearFire();
		if (this.isAlive()) {
			this.idleAnimationState.startIfStopped(this.tickCount);
		}
		if (this.fallTick > 0) {
			this.setNoGravity(false);
			this.fallTick -= 1;
		}
		if (!this.onGround() && this.getDeltaMovement().y < 0.0) {
			this.setDeltaMovement(this.getDeltaMovement().multiply(1.0, 0.8, 1.0));
		}
		if (this.level().isClientSide) {
			if (this.random.nextInt(800) == 1 && !this.isSilent()) {
				this.level().playLocalSound(this.getX() + 0.5, this.getY() + 0.5, this.getZ() + 0.5, JerotesVillageSoundEvents.FIRE_SECRETOR_BURN, this.getSoundSource(), 1.0f + this.random.nextFloat(), this.random.nextFloat() * 0.7f + 0.3f, false);
			}
			for (int i = 0; i < 2; ++i) {
				this.level().addParticle(ParticleTypes.FLAME, this.getRandomX(0.5), this.getRandomY(), this.getRandomZ(0.5), 0.0, 0.0, 0.0);
				}
			this.level().addParticle(ParticleTypes.LARGE_SMOKE, this.getRandomX(0.5), this.getRandomY() + 0.5, this.getRandomZ(0.5), 0.0, 0.0, 0.0);
		}
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
	public boolean isSensitiveToWater() {
		return true;
	}

	@Override
	public void setCustomName(@Nullable Component component) {
		super.setCustomName(component);
		this.bossEvent.setName(this.getDisplayName());
	}
	public int spellLevel = 3;
	@Override
	public int getSpellLevel() {
		return this.spellLevel;
	}
	public int fallTick;
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
		if (Objects.equals(animation, "fire")){
			return 1;
		}
		else if (Objects.equals(animation, "smoke")){
			return 2;
		}
		else if (Objects.equals(animation, "dead")){
			return 3;
		}
		else {
			return 0;
		}
	}
	public List<AnimationState> getAllAnimations(){
		List<AnimationState> list = new ArrayList<>();
		list.add(this.fireAnimationState);
		list.add(this.smokeAnimationState);
		list.add(this.deadAnimationState);
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
		compoundTag.putInt("SpellLevel", this.spellLevel);
		compoundTag.putInt("FallTick", this.fallTick);
		compoundTag.putInt("AnimTick", this.getAnimTick());
	}
	@Override
	public void readAdditionalSaveData(CompoundTag compoundTag) {
		super.readAdditionalSaveData(compoundTag);
		if (this.hasCustomName()) {
			this.bossEvent.setName(this.getDisplayName());
		}
		this.spellLevel = compoundTag.getInt("SpellLevel");
		this.fallTick = compoundTag.getInt("FallTick");
		this.setAnimTick(compoundTag.getInt("AnimTick"));
		this.bossEvent.setId(this.getUUID());
	}
	@Override
	protected void defineSynchedData() {
		super.defineSynchedData();
		this.getEntityData().define(ANIM_STATE, 0);
		this.getEntityData().define(ANIM_TICK, 0);
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
						this.fireAnimationState.startIfStopped(this.tickCount);
						this.stopMostAnimation(this.fireAnimationState);
						break;
					case 2:
						this.smokeAnimationState.startIfStopped(this.tickCount);
						this.stopMostAnimation(this.smokeAnimationState);
						break;
					case 3:
						this.deadAnimationState.startIfStopped(this.tickCount);
						this.stopMostAnimation(this.deadAnimationState);
						break;
				}
			}
		}
		super.onSyncedDataUpdated(entityDataAccessor);
	}

	@Nullable
	public ItemEntity spawnAtLocation(ItemStack itemStack, float f) {
		if (itemStack.isEmpty()) {
			return null;
		} else if (this.level().isClientSide) {
			return null;
		} else {
			ItemEntity itementity = new ItemEntity(this.level(), this.getX(), this.getY() + (double)f, this.getZ(), itemStack);
			itementity.setDefaultPickUpDelay();
			if (itementity.getItem().getItem() == JerotesVillageItems.FIRE_SECRETOR_ADHESIVE.get()) {
				itementity.setGlowingTag(true);
			}
			if (captureDrops() != null) captureDrops().add(itementity);
			else {
				this.level().addFreshEntity(itementity);
			}
			return itementity;
		}
	}

	@Override
	public void tickDeath() {
		if(deathTime <= 0){
			if (!this.level().isClientSide()) {
				this.setAnimTick(40);
				this.setAnimationState("dead");
			}
		}
		++this.deathTime;

		//技能-燃油爆炸
		if (this.deathTime == 5) {
			if (this.level() instanceof ServerLevel serverLevel) {
				if (!this.isInvisible()) {
					serverLevel.sendParticles(JerotesVillageParticleTypes.FUEL_EXPLOSION_DISPLAY.get(), this.getX(), this.getBoundingBox().maxY + 0.5, this.getZ(), 0, 0.0, 0.0, 0.0, 0);
				}
			}
			if (!this.isSilent()) {
				this.playSound(JerotesVillageSoundEvents.FIRE_SECRETOR_BOMB, 1.0f, 1.0f / (this.getRandom().nextFloat() * 0.4f + 0.8f));
			}
			List<Player> list = this.level().getEntitiesOfClass(Player.class, this.getBoundingBox().inflate(6.0, 6.0, 6.0));
			for (Player hurt : list) {
				if (AttackFind.FindCanNotAttack(this, hurt)) continue;
				double d = 0.0;
				if (hurt.getAttribute(Attributes.KNOCKBACK_RESISTANCE) != null) {
					d = Math.max(hurt.getAttributeValue(Attributes.KNOCKBACK_RESISTANCE), 1.0);
				}
				double d2 = Math.max(0, 1 - d) + 0.2f;
				if (!EntityAndItemFind.isNoSpecialKnockback(hurt.getType())) {
					hurt.setDeltaMovement(hurt.getDeltaMovement().add(-(this.getX() - hurt.getX()) * 0.6 * (d2 + 0.3), -(this.getY() - hurt.getY()) * 0.6 * (d2 + 0.35) + 0.2f, -(this.getZ() - hurt.getZ()) * 0.6 * (d2 + 0.3)));
				}
			}
		}

		if (!this.level().isClientSide()) {
			if (this.deathTime == 35) {
				this.level().playSound(null, this.getX(), this.getY(), this.getZ(), SoundEvents.GENERIC_EXPLODE, this.getSoundSource(), 10.0F, 0.5F);
				if (this.level() instanceof ServerLevel serverLevel) {
					for (int i = 0; i < 120; ++i) {
						RandomSource random = RandomSource.create();
						double d = random.nextGaussian() * 0.02;
						double d2 = random.nextGaussian() * 0.02;
						double d3 = random.nextGaussian() * 0.02;
						float x = (float) this.getX((2.0 * random.nextDouble() - 1.0) * ((6.0 + random.nextDouble() * 2)));
						float y = (float) this.getY((2.0 * random.nextDouble() - 1.0) * ((3.0 + random.nextDouble() * 2)));
						float z = (float) this.getZ((2.0 * random.nextDouble() - 1.0) * ((6.0 + random.nextDouble() * 2)));
						serverLevel.sendParticles(ParticleTypes.LAVA, x, y, z, 0, d, d2, d3, 0);
						serverLevel.sendParticles(ParticleTypes.EXPLOSION, x, y, z, 0, d, d2, d3, 0);
					}
				}
				List<LivingEntity> list = this.level().getEntitiesOfClass(LivingEntity.class, this.getBoundingBox().inflate(8.0, 8.0, 8.0));
				for (LivingEntity hurt : list) {
					if (AttackFind.FindCanNotAttack(this, hurt)) continue;
					if (!this.hasLineOfSight(hurt)) continue;
					DamageSource damageSources = AttackFind.findDamageType(this, DamageTypes.EXPLOSION, this);
					hurt.hurt(damageSources, this.getMaxHealth() / 3 / (Math.max(1, (this.distanceTo(hurt)))));
				}
			}
		}
		if (this.deathTime >= 40 && !this.level().isClientSide() && !this.isRemoved()) {
			this.level().broadcastEntityEvent(this, (byte) 60);
			this.remove(RemovalReason.KILLED);
		}
	}
}

