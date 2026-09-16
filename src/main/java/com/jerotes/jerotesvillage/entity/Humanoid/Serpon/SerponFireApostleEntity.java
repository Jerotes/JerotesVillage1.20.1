package com.jerotes.jerotesvillage.entity.Humanoid.Serpon;

import com.jerotes.jerotes.entity.Interface.EliteEntity;
import com.jerotes.jerotes.entity.Interface.FireAbsorptionEntity;
import com.jerotes.jerotes.event.JerotesBossEvent;
import com.jerotes.jerotes.init.JerotesGameRules;
import com.jerotes.jerotes.util.AttackFind;
import com.jerotes.jerotes.util.EntityAndItemFind;
import com.jerotes.jerotes.util.Main;
import com.jerotes.jerotesvillage.config.OtherMainConfig;
import com.jerotes.jerotesvillage.init.JerotesVillageGameRules;
import com.jerotes.jerotesvillage.init.JerotesVillageItems;
import com.jerotes.jerotesvillage.init.JerotesVillageParticleTypes;
import com.jerotes.jerotesvillage.init.JerotesVillageSoundEvents;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.chat.Component;
import net.minecraft.network.syncher.EntityDataAccessor;
import net.minecraft.network.syncher.EntityDataSerializers;
import net.minecraft.network.syncher.SynchedEntityData;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.util.Mth;
import net.minecraft.util.RandomSource;
import net.minecraft.world.BossEvent;
import net.minecraft.world.DifficultyInstance;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.damagesource.DamageTypes;
import net.minecraft.world.entity.*;
import net.minecraft.world.entity.ai.attributes.AttributeSupplier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.ShieldItem;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.ServerLevelAccessor;
import net.minecraft.world.phys.AABB;

import javax.annotation.Nullable;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;


public class SerponFireApostleEntity extends SerponEntity implements FireAbsorptionEntity, EliteEntity {
	private final JerotesBossEvent bossEvent = new JerotesBossEvent(this, this.getUUID(), BossEvent.BossBarColor.YELLOW, BossEvent.BossBarOverlay.NOTCHED_6, false);
	private static final EntityDataAccessor<Integer> ANIM_STATE = SynchedEntityData.defineId(SerponFireApostleEntity.class, EntityDataSerializers.INT);
	private static final EntityDataAccessor<Integer> ANIM_TICK = SynchedEntityData.defineId(SerponFireApostleEntity.class, EntityDataSerializers.INT);
	private static final EntityDataAccessor<Integer> ATTACK_ANIM = SynchedEntityData.defineId(SerponFireApostleEntity.class, EntityDataSerializers.INT);
	private static final EntityDataAccessor<Integer> ATTACK_TICK = SynchedEntityData.defineId(SerponFireApostleEntity.class, EntityDataSerializers.INT);
	private static final EntityDataAccessor<Integer> ATTACK_USE = SynchedEntityData.defineId(SerponFireApostleEntity.class, EntityDataSerializers.INT);
	public AnimationState idleAnimationState = new AnimationState();
	public AnimationState attack1AnimationState = new AnimationState();
	public AnimationState attack2AnimationState = new AnimationState();
	public AnimationState attack3AnimationState = new AnimationState();
	public AnimationState attack4AnimationState = new AnimationState();
	public AnimationState attack5AnimationState = new AnimationState();
	public AnimationState deadAnimationState = new AnimationState();

	public SerponFireApostleEntity(EntityType<? extends SerponFireApostleEntity> entityType, Level level) {
		super(entityType, level);
		this.xpReward = 100;
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
		this.maybeWearArmor(EquipmentSlot.HEAD, new ItemStack(JerotesVillageItems.FIRE_APOSTLE_HELMET.get()), randomSource);
		this.maybeWearArmor(EquipmentSlot.CHEST, new ItemStack(JerotesVillageItems.FIRE_APOSTLE_CHESTPLATE.get()), randomSource);
		this.maybeWearArmor(EquipmentSlot.LEGS, new ItemStack(JerotesVillageItems.FIRE_APOSTLE_LEGGINGS.get()), randomSource);
		this.maybeWearArmor(EquipmentSlot.FEET, new ItemStack(JerotesVillageItems.FIRE_APOSTLE_BOOTS.get()), randomSource);
	}

	private void maybeWearArmor(EquipmentSlot equipmentSlot, ItemStack itemStack, RandomSource randomSource) {
		this.setItemSlot(equipmentSlot, itemStack);
	}

	public static AttributeSupplier.Builder createAttributes() {
		AttributeSupplier.Builder builder = Mob.createMobAttributes();
		builder = builder.add(Attributes.MOVEMENT_SPEED, 0.35);
		builder = builder.add(Attributes.FLYING_SPEED, 0.55);
		builder = builder.add(Attributes.MAX_HEALTH, 70);
		builder = builder.add(Attributes.ATTACK_KNOCKBACK, 0.1);
		builder = builder.add(Attributes.KNOCKBACK_RESISTANCE	, 1);
		builder = builder.add(Attributes.ATTACK_DAMAGE, 8);
		builder = builder.add(Attributes.FOLLOW_RANGE, 64);
		builder = builder.add(Attributes.ARMOR, 5);
		return builder;
	}

	@Override
	public boolean isLeftHanded() {
		return false;
	}
	@Override
	public boolean canUseCrossbow() {
		return false;
	}
	@Override
	public boolean canUseThrow() {
		return false;
	}
	@Override
	public boolean canUseRangeJavelin() {
		return false;
	}
	@Override
	public boolean canUseBow() {
		return false;
	}
	@Override
	public double FireAbsorptionPercentage() {
		return 60;
	}
	@Override
	protected float getSoundVolume() {
		return 2.0f;
	}
	@Override
	public void knockback(double d, double d2, double d3) {
		super.knockback(0, 0, 0);
	}
	@Override
	public void push(double d, double d2, double d3) {
		super.push(0, 0, 0);
	}
	public AABB getAttackBoundingBox() {
		Entity entity = this.getVehicle();
		AABB aabb;
		if (entity != null) {
			AABB aabb1 = entity.getBoundingBox();
			AABB aabb2 = this.getBoundingBox();
			aabb = new AABB(Math.min(aabb2.minX, aabb1.minX), aabb2.minY, Math.min(aabb2.minZ, aabb1.minZ), Math.max(aabb2.maxX, aabb1.maxX), aabb2.maxY, Math.max(aabb2.maxZ, aabb1.maxZ));
		} else {
			aabb = this.getBoundingBox();
		}
		AABB aabb1 = aabb.inflate(Math.sqrt((double)2.04F) - (double)0.6F, 0.0D, Math.sqrt((double)2.04F) - (double)0.6F);
		if (!this.getMainHandItem().isEmpty())
			return aabb1.inflate(0.85d, 0.85d, 0.85d);
		return aabb1.inflate(0.5d, 0.5d, 0.5d);
	}
	@Override
	public boolean isWithinMeleeAttackRange(LivingEntity livingEntity) {
		return this.getAttackBoundingBox().intersects(livingEntity.getBoundingBox());
	}

	public float thisTickRenderTime = 0;
	public float lastTickRenderTime = 6;
	public float attackAnimProgress = 0.0f;
	public int getAttackAnim() {
		return this.getEntityData().get(ATTACK_ANIM);
	}
	public void setAttackAnim(int n) {
		this.getEntityData().set(ATTACK_ANIM, n);
	}
	public int getAttackTick() {
		return this.getEntityData().get(ATTACK_TICK);
	}
	public void setAttackTick(int n) {
		this.getEntityData().set(ATTACK_TICK, n);
	}
	public int getAttackUse() {
		return this.getEntityData().get(ATTACK_USE);
	}
	public void setAttackUse(int n) {
		this.getEntityData().set(ATTACK_USE, n);
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
		if (Objects.equals(animation, "attack1")){
			return 1;
		}
		else if (Objects.equals(animation, "attack2")){
			return 2;
		}
		else if (Objects.equals(animation, "attack3")){
			return 3;
		}
		else if (Objects.equals(animation, "attack4")){
			return 4;
		}
		else if (Objects.equals(animation, "attack5")){
			return 5;
		}
		else if (Objects.equals(animation, "dead")){
			return 6;
		}
		else {
			return 0;
		}
	}
	public List<AnimationState> getAllAnimations(){
		List<AnimationState> list = new ArrayList<>();
		list.add(this.attack1AnimationState);
		list.add(this.attack2AnimationState);
		list.add(this.attack3AnimationState);
		list.add(this.attack4AnimationState);
		list.add(this.attack5AnimationState);
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
	public void setCustomName(@Nullable Component component) {
		super.setCustomName(component);
		this.bossEvent.setName(this.getDisplayName());
	}
	@Override
	public void addAdditionalSaveData(CompoundTag compoundTag) {
		super.addAdditionalSaveData(compoundTag);
		compoundTag.putInt("AnimTick", this.getAnimTick());
		compoundTag.putInt("AttackUse", this.getAttackUse());
		compoundTag.putInt("AttackTick", this.getAttackTick());
		compoundTag.putInt("AttackAnim", this.getAttackAnim());
	}
	@Override
	public void readAdditionalSaveData(CompoundTag compoundTag) {
		super.readAdditionalSaveData(compoundTag);
		if (this.hasCustomName()) {
			this.bossEvent.setName(this.getDisplayName());
		}
		this.setAnimTick(compoundTag.getInt("AnimTick"));
		this.setAttackUse(compoundTag.getInt("AttackUse"));
		this.setAttackTick(compoundTag.getInt("AttackTick"));
		this.setAttackAnim(compoundTag.getInt("AttackAnim"));
		this.bossEvent.setId(this.getUUID());
	}

	@Override
	protected void defineSynchedData() {
		super.defineSynchedData();
		this.getEntityData().define(ANIM_STATE, 0);
		this.getEntityData().define(ANIM_TICK, 0);
		this.getEntityData().define(ATTACK_ANIM, 0);
		this.getEntityData().define(ATTACK_TICK, 0);
		this.getEntityData().define(ATTACK_USE, 0);
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
						this.attack1AnimationState.startIfStopped(this.tickCount);
						this.stopMostAnimation(this.attack1AnimationState);
						break;
					case 2:
						this.attack2AnimationState.startIfStopped(this.tickCount);
						this.stopMostAnimation(this.attack2AnimationState);
						break;
					case 3:
						this.attack3AnimationState.startIfStopped(this.tickCount);
						this.stopMostAnimation(this.attack3AnimationState);
						break;
					case 4:
						this.attack4AnimationState.startIfStopped(this.tickCount);
						this.stopMostAnimation(this.attack4AnimationState);
						break;
					case 5:
						this.attack5AnimationState.startIfStopped(this.tickCount);
						this.stopMostAnimation(this.attack5AnimationState);
						break;
					case 6:
						this.deadAnimationState.startIfStopped(this.tickCount);
						this.stopMostAnimation(this.deadAnimationState);
						break;
				}
			}
		}
		super.onSyncedDataUpdated(entityDataAccessor);
	}

	@Override
	public void tick() {
		super.tick();
		if (level().isClientSide()) {
			lastTickRenderTime = thisTickRenderTime;
			thisTickRenderTime = 0;
		}
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
		if (this.isAlive()) {
			this.idleAnimationState.startIfStopped(this.tickCount);
		}
		//近战攻击
		if (!this.level().isClientSide()) {
			this.setAttackTick(Math.max(0, this.getAttackTick() - 1));
		}
		if (this.getAttackUse() != 5 && this.getAttackTick() == 15 && this.isAlive()) {
			this.trueHurt();
		}
		if (this.getAttackUse() == 5 && this.isAlive()) {
			if (this.getAttackTick() == 13) {
				this.trueHurt();
			}
		}
		if (this.getAttackTick() > 0){
			this.getNavigation().stop();
		}
		//战斗
		if (this.getTarget() != null && this.getAttackTick() <= 5) {
			if (!this.level().isClientSide()) {
				this.setAttackAnim(Math.min(this.getAttackAnim() + 1, 10));
			}
		}
		else {
			if (!this.level().isClientSide()) {
				this.setAttackAnim(Math.max(this.getAttackAnim() - 2, 0));
			}
		}
		if (this.getAttackTick() > 15){
			if (this.getTarget() != null) {
				this.lookAt(this.getTarget(), 360f, 360f);
				this.getLookControl().setLookAt(this.getTarget(), 360f, 360f);
			}
		}
		//位移
		{
			if (this.getAttackUse() == 2 && this.getAttackTick() <= 20 && this.getAttackTick() > 15) {
				float f = this.getYRot();
				float f2 = this.getXRot();
				float f3 = -Mth.sin(f * 0.017453292f) * Mth.cos(f2 * 0.017453292f);
				float f4 = -Mth.sin(f2 * 0.017453292f);
				float f5 = Mth.cos(f * 0.017453292f) * Mth.cos(f2 * 0.017453292f);
				float f6 = Mth.sqrt(f3 * f3 + f4 * f4 + f5 * f5);
				float f7 = 0.15f;
				if (this.getAttackTick() > 18) {
					f7 = -0.05f;
				}
				float f8 = f4 *= f7 / f6 * 2;
				if (!this.onGround()) {
					f8 = 0;
				}
				this.setDeltaMovement(this.getDeltaMovement().add(f3 *= f7 / f6 * 2, f8, f5 *= f7 / f6 * 2));
			}
		}
	}


	@Override
	public boolean doHurtTarget(Entity entity) {
		if (this.getAttackTick() > 0) {
			return false;
		}
		if (!this.level().isClientSide()) {
			int attackRandom = this.getRandom().nextInt(100);
			//1-横扫
			if (attackRandom > 85) {
				this.setAnimTick(20);
				this.setAttackTick(25);
				this.setAttackUse(1);
				this.setAnimationState("attack1");
			}
			//2-突击
			else if (attackRandom > 70) {
				this.setAnimTick(20);
				this.setAttackTick(25);
				this.setAttackUse(2);
				this.setAnimationState("attack2");
			}
			//3-劈砍
			else if (attackRandom > 40) {
				this.setAnimTick(20);
				this.setAttackTick(25);
				this.setAttackUse(3);
				this.setAnimationState("attack3");
			}
			//4-刺击
			else if (attackRandom > 15) {
				this.setAnimTick(20);
				this.setAttackTick(25);
				this.setAttackUse(4);
				this.setAnimationState("attack4");
			}
			//5-风车
			else {
				this.setAnimTick(20);
				this.setAttackTick(25);
				this.setAttackUse(5);
				this.setAnimationState("attack5");
			}
		}
		this.getNavigation().stop();
		this.setDeltaMovement(this.getDeltaMovement().multiply(0,1,0));
		return true;
	}

	public boolean trueHurt() {
		//1-横扫
		//2-突击
		//3-劈砍
		//4-刺击
		//5-风车
		if (!this.isSilent()) {
			this.playSound(JerotesVillageSoundEvents.SERPON_ATTACK, 2.0f, 1.0f);
			if (this.getAttackUse() == 1) {
				this.playSound(SoundEvents.PLAYER_ATTACK_SWEEP, 2.0f, 1.0f);
			}
			if (this.getAttackUse() == 2) {
				this.playSound(SoundEvents.PLAYER_ATTACK_KNOCKBACK, 2.0f, 1.0f);
			}
			if (this.getAttackUse() == 3) {
				this.playSound(SoundEvents.PLAYER_ATTACK_STRONG, 2.0f, 1.0f);
			}
			if (this.getAttackUse() == 4) {
				this.playSound(SoundEvents.PLAYER_ATTACK_KNOCKBACK, 2.0f, 1.0f);
			}
			if (this.getAttackUse() == 5) {
				this.playSound(SoundEvents.PLAYER_ATTACK_CRIT, 2.0f, 1.0f);
			}
		}
		float damageMulti = 1.0f;
		float knockbackMulti = 1.0f;
		float reach = 1.35f;
		//1-横扫
		//2-突击
		//3-劈砍
		//4-刺击
		//5-风车
		if (this.getAttackUse() == 1) {
			reach = 1.5f;
		}
		if (this.getAttackUse() == 2) {
			knockbackMulti = 1.5f;
		}
		if (this.getAttackUse() == 3) {
			damageMulti = 1.25f;
		}
		if (this.getAttackUse() == 4) {
			knockbackMulti = 1.15f;
			reach = 1.5f;
		}
		if (this.getAttackUse() == 5) {
			damageMulti = 1.25f;
			reach = 1.5f;
		}
		List<LivingEntity> list = this.level().getEntitiesOfClass(LivingEntity.class, this.getAttackBoundingBox().inflate(reach));
		for (LivingEntity hurt : list) {
			if (hurt == null) continue;
			if ((this.distanceToSqr(hurt)) > Double.MAX_VALUE) continue;
			if (AttackFind.FindCanNotAttack(this, hurt)) continue;
			if (!this.hasLineOfSight(hurt)) continue;
			if (!Main.canSee(hurt, this) && this.getAttackUse() != 3 && this.getAttackUse() != 4 && this.getAttackUse() != 5) continue;
			DamageSource damageSource = AttackFind.findDamageType(this, DamageTypes.MOB_ATTACK_NO_AGGRO, this);
			AttackFind.attackBegin(this, hurt);
			boolean bl = AttackFind.attackAfter(this, hurt, damageMulti, knockbackMulti, false, 0f);
			AttackFind.attackAfterCustomDamage(this, hurt, damageSource, damageMulti, knockbackMulti, false, 0f);
			if (bl) {
				//着火
				hurt.setSecondsOnFire(16);
				//火焰粒子
				double d = -Mth.sin(this.getYRot() * 0.017453292f);
				double d2 = Mth.cos(this.getYRot() * 0.017453292f);
				if (this.level() instanceof ServerLevel serverLevel) {
					for (int i = 0; i < 15; ++i) {
						double xs = this.getX() + d + (this.getRandom().nextFloat() - 0.5f) * 2 - (this.getX() - hurt.getX())/2;
						double ys = this.getY(0.5) + this.getRandom().nextFloat() - 0.5f - (this.getY() - hurt.getY())/2;
						double zs = this.getZ() + d2 + (this.getRandom().nextFloat() - 0.5f) * 2 - (this.getZ() - hurt.getZ())/2;
						serverLevel.sendParticles(ParticleTypes.FLAME, xs, ys, zs, 0, d, 0.0, d2, 0.0);
					}
				}
			}
		}
		//位移
		{
			if (this.getAttackUse() == 2) {
				float f = this.getYRot();
				float f2 = this.getXRot();
				float f3 = -Mth.sin(f * 0.017453292f) * Mth.cos(f2 * 0.017453292f);
				float f4 = -Mth.sin(f2 * 0.017453292f);
				float f5 = Mth.cos(f * 0.017453292f) * Mth.cos(f2 * 0.017453292f);
				float f6 = Mth.sqrt(f3 * f3 + f4 * f4 + f5 * f5);
				float f7 = 0.5f;
				float f8 = f4 *= f7 / f6 * 2;
				if (!this.onGround()) {
					f8 = 0;
				}
				this.setDeltaMovement(this.getDeltaMovement().add(f3 *= f7 / f6 * 2, f8, f5 *= f7 / f6 * 2));
			}
		}
		//横扫效果
		Main.sweepAttack(this);
		if (JerotesGameRules.JEROTES_MELEE_CAN_BREAK != null && this.level().getLevelData().getGameRules().getBoolean(JerotesGameRules.JEROTES_MELEE_CAN_BREAK)) {
			ItemStack hand = this.getMainHandItem();
			hand.hurtAndBreak(1, this, player -> player.broadcastBreakEvent(EquipmentSlot.MAINHAND));
		}
		return true;
	}

	@Override
	public void disableShield() {
		if (this.random.nextFloat() < 0.66) {
			if (this.shieldCoolDown < 100) {
				this.shieldCoolDown = 100;
			}
			this.shieldCanUse = 0;
			if (this.getUseItem().getItem() instanceof ShieldItem) {
				this.stopUsingItem();
			}
		}
	}

	@Override
	public void disableShieldTry() {
		if (this.random.nextFloat() < 0.05) {
			if (this.shieldCoolDown < 100) {
				this.shieldCoolDown = 100;
			}
			this.shieldCanUse = 0;
			if (this.getUseItem().getItem() instanceof ShieldItem) {
				this.stopUsingItem();
			}
		}
	}

	@Override
	public boolean hurt(DamageSource damageSource, float amount) {
		if (isInvulnerableTo(damageSource)) {
			return super.hurt(damageSource, amount);
		}
		return super.hurt(damageSource, amount);
	}

	@Nullable
	@Override
	public SpawnGroupData finalizeSpawn(ServerLevelAccessor serverLevelAccessor, DifficultyInstance difficultyInstance, MobSpawnType mobSpawnType, @Nullable SpawnGroupData spawnGroupData, @Nullable CompoundTag compoundTag) {
		this.setShieldLevel(3);
		return super.finalizeSpawn(serverLevelAccessor, difficultyInstance, mobSpawnType, spawnGroupData, compoundTag);
	}
	@Override
	public ItemStack createSpawnWeapon(float weaponRandom) {
		return new ItemStack(JerotesVillageItems.FIRE_APOSTLE_SWORD.get());
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
			if (this.level() instanceof ServerLevel serverLevel) {
				serverLevel.sendParticles(JerotesVillageParticleTypes.SERPON_PORTAL.get(), this.getX(), this.getY(0.5), this.getZ(), 0, 0.0, 0.0, 0.0, 0.0);
			}
			this.remove(RemovalReason.CHANGED_DIMENSION);
		}
	}
}

