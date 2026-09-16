package com.jerotes.jerotesvillage.entity.Neutral.WildernessGiantSkeleton;

import com.jerotes.jerotes.entity.Interface.BreakShieldEntity;
import com.jerotes.jerotes.entity.Interface.EliteEntity;
import com.jerotes.jerotes.event.JerotesBossEvent;
import com.jerotes.jerotes.init.JerotesGameRules;
import com.jerotes.jerotes.util.AttackFind;
import com.jerotes.jerotes.util.Main;
import com.jerotes.jerotesvillage.config.OtherMainConfig;
import com.jerotes.jerotesvillage.init.JerotesVillageGameRules;
import com.jerotes.jerotesvillage.init.JerotesVillageItems;
import com.jerotes.jerotesvillage.init.JerotesVillageSoundEvents;
import net.minecraft.ChatFormatting;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.chat.Component;
import net.minecraft.network.syncher.EntityDataAccessor;
import net.minecraft.network.syncher.EntityDataSerializers;
import net.minecraft.network.syncher.SynchedEntityData;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.util.Mth;
import net.minecraft.util.RandomSource;
import net.minecraft.world.BossEvent;
import net.minecraft.world.DifficultyInstance;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.*;
import net.minecraft.world.entity.ai.attributes.AttributeSupplier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.ServerLevelAccessor;

import javax.annotation.Nullable;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class WoodlandHeartGuardianEntity extends WildernessGiantSkeletonEntity implements EliteEntity, BreakShieldEntity {
	private final JerotesBossEvent bossEvent = new JerotesBossEvent(this, this.getUUID(), BossEvent.BossBarColor.YELLOW, BossEvent.BossBarOverlay.NOTCHED_6, false);
	private static final EntityDataAccessor<Integer> ANIM_STATE = SynchedEntityData.defineId(WoodlandHeartGuardianEntity.class, EntityDataSerializers.INT);
	private static final EntityDataAccessor<Integer> ANIM_TICK = SynchedEntityData.defineId(WoodlandHeartGuardianEntity.class, EntityDataSerializers.INT);
	private static final EntityDataAccessor<Integer> ATTACK_ANIM = SynchedEntityData.defineId(WoodlandHeartGuardianEntity.class, EntityDataSerializers.INT);
	private static final EntityDataAccessor<Integer> ATTACK_TICK = SynchedEntityData.defineId(WoodlandHeartGuardianEntity.class, EntityDataSerializers.INT);
	private static final EntityDataAccessor<Integer> ATTACK_USE = SynchedEntityData.defineId(WoodlandHeartGuardianEntity.class, EntityDataSerializers.INT);
	private static final EntityDataAccessor<Boolean> IS_SMALL = SynchedEntityData.defineId(WoodlandHeartGuardianEntity.class, EntityDataSerializers.BOOLEAN);
	public AnimationState idleAnimationState = new AnimationState();
	public AnimationState left1AnimationState = new AnimationState();
	public AnimationState right1AnimationState = new AnimationState();
	public AnimationState left2AnimationState = new AnimationState();
	public AnimationState right2AnimationState = new AnimationState();
	public AnimationState left3AnimationState = new AnimationState();
	public AnimationState right3AnimationState = new AnimationState();
	public AnimationState cutAnimationState = new AnimationState();
	public AnimationState rollAnimationState = new AnimationState();
	public AnimationState deadAnimationState = new AnimationState();

	public WoodlandHeartGuardianEntity(EntityType type, Level world) {
		super(type, world);
		xpReward = 40;
		this.armorDropChances[EquipmentSlot.HEAD.getIndex()] = 2f;
		this.fixupDimensions();
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
		this.maybeWearArmor(EquipmentSlot.HEAD, new ItemStack(JerotesVillageItems.WOODLAND_HEART_GUARDIAN_MASK.get()), randomSource);
		this.maybeWearArmor(EquipmentSlot.CHEST, new ItemStack(JerotesVillageItems.WILDERNESS_GIANT_CHESTPLATE.get()), randomSource);
		this.maybeWearArmor(EquipmentSlot.LEGS, new ItemStack(JerotesVillageItems.WILDERNESS_GIANT_LEGGINGS.get()), randomSource);
		this.maybeWearArmor(EquipmentSlot.FEET, new ItemStack(JerotesVillageItems.WILDERNESS_GIANT_BOOTS.get()), randomSource);
	}

	private void maybeWearArmor(EquipmentSlot equipmentSlot, ItemStack itemStack, RandomSource randomSource) {
		this.setItemSlot(equipmentSlot, itemStack);
	}

	public static AttributeSupplier.Builder createAttributes() {
		AttributeSupplier.Builder builder = Mob.createMobAttributes();
		builder = builder.add(Attributes.MOVEMENT_SPEED, 0.25);
		builder = builder.add(Attributes.MAX_HEALTH, 135);
		builder = builder.add(Attributes.ARMOR, 4);
		builder = builder.add(Attributes.ATTACK_DAMAGE, 11);
		builder = builder.add(Attributes.FOLLOW_RANGE, 64);
		builder = builder.add(Attributes.KNOCKBACK_RESISTANCE, 0.5);
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
	protected float getSoundVolume() {
		return 2f;
	}
	@Override
	public int getShieldBreakStrength() {
		if (this.getAttackUse() == 2)
			return 100;
		return 0;
	}
	@Override
	public boolean canDisableShield() {
		return this.getAttackUse() == 2;
	}

	@Override
	protected void blockedByShield(LivingEntity livingEntity) {
		if (this.getAttackUse() == 2) {
			if (livingEntity instanceof Player player) {
				player.disableShield(true);
			}
		}
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
	public boolean isSmall() {
		return this.getEntityData().get(IS_SMALL);
	}
	public void setSmall(boolean bl) {
		this.getEntityData().set(IS_SMALL, bl);
		this.refreshDimensions();
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
		if (Objects.equals(animation, "left1")){
			return 1;
		}
		else if (Objects.equals(animation, "right1")){
			return 2;
		}
		else if (Objects.equals(animation, "left2")){
			return 3;
		}
		else if (Objects.equals(animation, "right2")){
			return 4;
		}
		else if (Objects.equals(animation, "left3")){
			return 5;
		}
		else if (Objects.equals(animation, "right3")){
			return 6;
		}
		else if (Objects.equals(animation, "cut")){
			return 7;
		}
		else if (Objects.equals(animation, "roll")){
			return 8;
		}
		else if (Objects.equals(animation, "dead")){
			return 9;
		}
		else {
			return 0;
		}
	}
	public List<AnimationState> getAllAnimations(){
		List<AnimationState> list = new ArrayList<>();
		list.add(this.left1AnimationState);
		list.add(this.right1AnimationState);
		list.add(this.left2AnimationState);
		list.add(this.right2AnimationState);
		list.add(this.left3AnimationState);
		list.add(this.right3AnimationState);
		list.add(this.cutAnimationState);
		list.add(this.rollAnimationState);
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
		String string = ChatFormatting.stripFormatting(component.getString());
		super.setCustomName(component);
		this.bossEvent.setName(this.getDisplayName());
		if (!this.level().isClientSide()) {
			this.setSmall("Skyrim".equals(string));
		}
		this.refreshDimensions();
	}
	@Override
	public void addAdditionalSaveData(CompoundTag compoundTag) {
		super.addAdditionalSaveData(compoundTag);
		compoundTag.putInt("AnimTick", this.getAnimTick());
		compoundTag.putInt("AttackAnim", this.getAttackAnim());
		compoundTag.putInt("AttackTick", this.getAttackTick());
		compoundTag.putInt("AttackUse", this.getAttackUse());
		compoundTag.putBoolean("IsSmall", this.isSmall());
		this.refreshDimensions();
	}
	@Override
	public void readAdditionalSaveData(CompoundTag compoundTag) {
		super.readAdditionalSaveData(compoundTag);
		this.setAnimTick(compoundTag.getInt("AnimTick"));
		this.setAttackAnim(compoundTag.getInt("AttackAnim"));
		this.setAttackTick(compoundTag.getInt("AttackTick"));
		this.setAttackUse(compoundTag.getInt("AttackUse"));
		this.bossEvent.setId(this.getUUID());
		if (this.getCustomName() != null && "Skyrim".equals(this.getCustomName().getString())) {
			this.setSmall(true);
		}
		this.setSmall(compoundTag.getBoolean("IsSmall"));
		if (this.hasCustomName()) {
			this.bossEvent.setName(this.getDisplayName());
		}
		refreshDimensions();
	}
	@Override
	public EntityDimensions getDimensions(Pose pose) {
		if (isSmall()) {
			return super.getDimensions(pose).scale(0.6f/1.05f, 1.8f/3.4f);
		}
		return super.getDimensions(pose);
	}
	@Override
	protected float getStandingEyeHeight(Pose pose, EntityDimensions entityDimensions) {
		if (isSmall()) {
			return this.isBaby() ? 0.86f : 1.62f;
		}
		return this.isBaby() ? 1.63f : 3.05f;
	}
	@Override
	protected void defineSynchedData() {
		super.defineSynchedData();
		this.getEntityData().define(ANIM_STATE, 0);
		this.getEntityData().define(ANIM_TICK, 0);
		this.getEntityData().define(ATTACK_ANIM, 0);
		this.getEntityData().define(ATTACK_TICK, 0);
		this.getEntityData().define(ATTACK_USE, 0);
		this.getEntityData().define(IS_SMALL, false);
	}
	@Override
	public void onSyncedDataUpdated(EntityDataAccessor<?> entityDataAccessor) {
		if (IS_SMALL.equals(entityDataAccessor)) {
			this.refreshDimensions();
		}
		if (ANIM_STATE.equals(entityDataAccessor)) {
			if (this.level().isClientSide()) {
				switch (this.entityData.get(ANIM_STATE)){
					case 0:
						this.stopAllAnimation();
						break;
					case 1:
						this.left1AnimationState.startIfStopped(this.tickCount);
						this.stopMostAnimation(this.left1AnimationState);
						break;
					case 2:
						this.right1AnimationState.startIfStopped(this.tickCount);
						this.stopMostAnimation(this.right1AnimationState);
						break;
					case 3:
						this.left2AnimationState.startIfStopped(this.tickCount);
						this.stopMostAnimation(this.left2AnimationState);
						break;
					case 4:
						this.right2AnimationState.startIfStopped(this.tickCount);
						this.stopMostAnimation(this.right2AnimationState);
						break;
					case 5:
						this.left3AnimationState.startIfStopped(this.tickCount);
						this.stopMostAnimation(this.left3AnimationState);
						break;
					case 6:
						this.right3AnimationState.startIfStopped(this.tickCount);
						this.stopMostAnimation(this.right3AnimationState);
						break;
					case 7:
						this.cutAnimationState.startIfStopped(this.tickCount);
						this.stopMostAnimation(this.cutAnimationState);
						break;
					case 8:
						this.rollAnimationState.startIfStopped(this.tickCount);
						this.stopMostAnimation(this.rollAnimationState);
						break;
					case 9:
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
			if (this.getAttackTick() == 20 || this.getAttackTick() == 10) {
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
		if (this.getAttackTick() > 20){
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
			boolean onlyLeft = this.getMainHandItem().isEmpty() && !this.getOffhandItem().isEmpty();
			boolean onlyRight = !this.getMainHandItem().isEmpty() && this.getOffhandItem().isEmpty();
			//1-轻击
			if (attackRandom > 90) {
				this.setAnimTick(20);
				this.setAttackTick(25);
				this.setAttackUse(1);
				if (onlyRight) {
					this.setAnimationState("right1");
				}
				else {
					this.setAnimationState("left1");
				}
			} else if (attackRandom > 80) {
				this.setAnimTick(20);
				this.setAttackTick(25);
				this.setAttackUse(1);
				if (onlyLeft) {
					this.setAnimationState("left1");
				}
				else {
					this.setAnimationState("right1");
				}
			}
			//2-重击
			else if (attackRandom > 70) {
				this.setAnimTick(20);
				this.setAttackTick(25);
				this.setAttackUse(2);
				if (onlyRight) {
					this.setAnimationState("right2");
				}
				else {
					this.setAnimationState("left2");
				}
			}
			else if (attackRandom > 60) {
				this.setAnimTick(20);
				this.setAttackTick(25);
				this.setAttackUse(2);
				if (onlyLeft) {
					this.setAnimationState("left2");
				}
				else {
					this.setAnimationState("right2");
				}
			}
			//3-重击
			else if (attackRandom > 50) {
				this.setAnimTick(20);
				this.setAttackTick(25);
				this.setAttackUse(3);
				if (onlyRight) {
					this.setAnimationState("right3");
				}
				else {
					this.setAnimationState("left3");
				}
			}
			else if (attackRandom > 40) {
				this.setAnimTick(20);
				this.setAttackTick(25);
				this.setAttackUse(3);
				if (onlyLeft) {
					this.setAnimationState("left3");
				}
				else {
					this.setAnimationState("right3");
				}
			}
			//4-切割
			else if (attackRandom > 25) {
				this.setAnimTick(20);
				this.setAttackTick(25);
				this.setAttackUse(4);
				this.setAnimationState("cut");
			}
			//5-风车
			else {
				this.setAnimTick(25);
				this.setAttackTick(30);
				this.setAttackUse(5);
				this.setAnimationState("roll");
			}
		}
		this.getNavigation().stop();
		this.setDeltaMovement(this.getDeltaMovement().multiply(0,1,0));
		return true;
	}
	public boolean trueHurt() {
		//1-轻击
		//2-重击
		//3-平移
		//4-切割
		//5-风车
		if (!this.isSilent()) {
			if (this.getAttackUse() == 1) {
				this.playSound(JerotesVillageSoundEvents.WOODLAND_HEART_GUARDIAN_ATTACK, 2.0f, 1.0f);
				this.playSound(SoundEvents.PLAYER_ATTACK_CRIT, 2.0f, 1.0f);
			}
			if (this.getAttackUse() == 2) {
				this.playSound(JerotesVillageSoundEvents.WOODLAND_HEART_GUARDIAN_HEAVY_ATTACK, 2.0f, 1.0f);
				this.playSound(SoundEvents.PLAYER_ATTACK_KNOCKBACK, 2.0f, 1.0f);
			}
			if (this.getAttackUse() == 3) {
				this.playSound(JerotesVillageSoundEvents.WOODLAND_HEART_GUARDIAN_ATTACK, 2.0f, 1.0f);
				this.playSound(SoundEvents.PLAYER_ATTACK_SWEEP, 2.0f, 1.0f);
			}
			if (this.getAttackUse() == 4) {
				this.playSound(JerotesVillageSoundEvents.WOODLAND_HEART_GUARDIAN_ATTACK, 2.0f, 1.0f);
				this.playSound(SoundEvents.PLAYER_ATTACK_STRONG, 2.0f, 1.0f);
			}
			if (this.getAttackUse() == 5) {
				this.playSound(JerotesVillageSoundEvents.WOODLAND_HEART_GUARDIAN_HEAVY_ATTACK, 2.0f, 1.0f);
				this.playSound(SoundEvents.PLAYER_ATTACK_CRIT, 2.0f, 1.0f);
			}
		}
		float damageMulti = 1.0f;
		float knockbackMulti = 1.0f;
		float reach = 1.35f;
		if (this.getAttackUse() == 2) {
			damageMulti = 1.5f;
			knockbackMulti = 1.75f;
			reach = 1.5f;
		}
		if (this.getAttackUse() == 3) {
			damageMulti = 1.25f;
			knockbackMulti = 1.25f;
			reach = 1.5f;
		}
		if (this.getAttackUse() == 4) {
			damageMulti = 1.75f;
			knockbackMulti = 1.25f;
			reach = 1.5f;
		}
		if (this.getAttackUse() == 5) {
			damageMulti = 2f;
			knockbackMulti = 2f;
			reach = 1.5f;
		}
		List<LivingEntity> list = this.level().getEntitiesOfClass(LivingEntity.class, this.getAttackBoundingBox().inflate(reach));
		for (LivingEntity hurt : list) {
			if (hurt == null) continue;
			if ((this.distanceToSqr(hurt)) > Double.MAX_VALUE) continue;
			if (AttackFind.FindCanNotAttack(this, hurt)) continue;
			if (!this.hasLineOfSight(hurt)) continue;
			if (!Main.canSee(hurt, this) && this.getAttackUse() != 3 && this.getAttackUse() != 4 && this.getAttackUse() != 5) continue;
			AttackFind.attackBegin(this, hurt);
			AttackFind.attackAfter(this, hurt, damageMulti, knockbackMulti, false, 0f);
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
		if (JerotesGameRules.JEROTES_MELEE_CAN_BREAK != null && this.level().getLevelData().getGameRules().getBoolean(JerotesGameRules.JEROTES_MELEE_CAN_BREAK)) {
			ItemStack hand = this.getMainHandItem();
			hand.hurtAndBreak(1, this, player -> player.broadcastBreakEvent(EquipmentSlot.MAINHAND));
		}
		return true;
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
		this.setShieldLevel(2);
		return super.finalizeSpawn(serverLevelAccessor, difficultyInstance, mobSpawnType, spawnGroupData, compoundTag);
	}

	@Override
	public ItemStack createSpawnWeapon(float weaponRandom) {
		return new ItemStack(JerotesVillageItems.WILDERNESS_SWORD.get());
	}
	@Override
	public ItemStack createSpawnOffhand(float offhandRandom) {
		return new ItemStack(JerotesVillageItems.WILDERNESS_SWORD.get());
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
			this.level().broadcastEntityEvent(this, (byte)60);
			this.remove(RemovalReason.KILLED);
		}
	}
}
