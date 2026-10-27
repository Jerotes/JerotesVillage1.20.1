package com.jerotes.jerotesvillage.entity.Boss;

import com.jerotes.jerotes.client.sound.BossMusicPlayer;
import com.jerotes.jerotes.config.MainConfig;
import com.jerotes.jerotes.entity.Interface.BossEntity;
import com.jerotes.jerotes.entity.Interface.InventoryEntity;
import com.jerotes.jerotes.entity.Interface.SpellUseEntity;
import com.jerotes.jerotes.event.JerotesBossEvent;
import com.jerotes.jerotes.init.JerotesGameRules;
import com.jerotes.jerotes.util.AttackFind;
import com.jerotes.jerotes.util.EntityAndItemFind;
import com.jerotes.jerotes.util.Main;
import com.jerotes.jerotesvillage.config.OtherMainConfig;
import com.jerotes.jerotesvillage.entity.Neutral.WildernessGiantSkeleton.WildernessGiantSkeletonEntity;
import com.jerotes.jerotesvillage.event.BossBarEvent;
import com.jerotes.jerotesvillage.init.JerotesVillageItems;
import com.jerotes.jerotesvillage.init.JerotesVillageSoundEvents;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.chat.Component;
import net.minecraft.network.syncher.EntityDataAccessor;
import net.minecraft.network.syncher.EntityDataSerializers;
import net.minecraft.network.syncher.SynchedEntityData;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.tags.DamageTypeTags;
import net.minecraft.util.RandomSource;
import net.minecraft.world.BossEvent;
import net.minecraft.world.DifficultyInstance;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.damagesource.DamageTypes;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.*;
import net.minecraft.world.entity.ai.attributes.AttributeSupplier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.entity.projectile.AbstractArrow;
import net.minecraft.world.item.*;
import net.minecraft.world.item.enchantment.Enchantments;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.ServerLevelAccessor;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.Vec3;

import javax.annotation.Nullable;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class EagleBowEntity extends WildernessGiantSkeletonEntity implements BossEntity, SpellUseEntity {
	public AnimationState startAnimationState = new AnimationState();
	public AnimationState idleAnimationState = new AnimationState();
	public AnimationState shootSlow1AnimationState = new AnimationState();
	public AnimationState shootSlow2AnimationState = new AnimationState();
	public AnimationState shootSlow3AnimationState = new AnimationState();
	public AnimationState shootSlow4AnimationState = new AnimationState();

	public AnimationState attack1AnimationState = new AnimationState();
	public AnimationState attack2AnimationState = new AnimationState();
	public AnimationState meleeAttack1AnimationState = new AnimationState();
	public AnimationState meleeAttack2AnimationState = new AnimationState();
	public AnimationState leadAnimationState = new AnimationState();
	public AnimationState deadAnimationState = new AnimationState();
	private static final EntityDataAccessor<Boolean> IS_LEGEND = SynchedEntityData.defineId(EagleBowEntity.class, EntityDataSerializers.BOOLEAN);
	private static final EntityDataAccessor<Integer> ANIM_STATE = SynchedEntityData.defineId(EagleBowEntity.class, EntityDataSerializers.INT);
	private static final EntityDataAccessor<Integer> ANIM_TICK = SynchedEntityData.defineId(EagleBowEntity.class, EntityDataSerializers.INT);
	private static final EntityDataAccessor<Integer> ATTACK_ANIM = SynchedEntityData.defineId(EagleBowEntity.class, EntityDataSerializers.INT);
	private static final EntityDataAccessor<Integer> SHOOT_ANIM = SynchedEntityData.defineId(EagleBowEntity.class, EntityDataSerializers.INT);
	private static final EntityDataAccessor<Integer> ATTACK_TICK = SynchedEntityData.defineId(EagleBowEntity.class, EntityDataSerializers.INT);
	private static final EntityDataAccessor<Integer> ATTACK_USE = SynchedEntityData.defineId(EagleBowEntity.class, EntityDataSerializers.INT);
	private static final EntityDataAccessor<Integer> SHOOT_TICK = SynchedEntityData.defineId(EagleBowEntity.class, EntityDataSerializers.INT);
	public static final EntityDataAccessor<Integer> SHADOW_ANIM = SynchedEntityData.defineId(EagleBowEntity.class, EntityDataSerializers.INT);
	private final JerotesBossEvent bossEvent = new JerotesBossEvent(this, this.getUUID(), BossEvent.BossBarColor.YELLOW, false);
	public static final int MAX_SHADOW_HISTORY = 12;
	public static final int SHADOW_COOLDOWN = 10;

	public EagleBowEntity(EntityType<? extends EagleBowEntity> entityType, Level level) {
		super(entityType, level);
		setMaxUpStep(1.1f);
		xpReward = 300;
		this.setCanPickUpLoot(true);
		this.armorDropChances[EquipmentSlot.HEAD.getIndex()] = (float) OtherMainConfig.EagleBowDropEquipmentChance;
		this.armorDropChances[EquipmentSlot.CHEST.getIndex()] = (float) OtherMainConfig.EagleBowDropEquipmentChance;
		this.armorDropChances[EquipmentSlot.LEGS.getIndex()] = (float) OtherMainConfig.EagleBowDropEquipmentChance;
		this.armorDropChances[EquipmentSlot.FEET.getIndex()] = (float) OtherMainConfig.EagleBowDropEquipmentChance;
		this.handDropChances[EquipmentSlot.MAINHAND.getIndex()] = (float) OtherMainConfig.EagleBowDropEquipmentChance;
		this.handDropChances[EquipmentSlot.OFFHAND.getIndex()] = (float) OtherMainConfig.EagleBowDropEquipmentChance;
		if (OtherMainConfig.BossBaseAttributeCanUseConfig) {
			this.getAttribute(Attributes.MAX_HEALTH).setBaseValue(OtherMainConfig.EagleBowMaxHealth);
			this.setHealth(this.getMaxHealth());
			this.getAttribute(Attributes.ATTACK_DAMAGE).setBaseValue(OtherMainConfig.EagleBowMeleeDamage);
			this.getAttribute(Attributes.ARMOR).setBaseValue(OtherMainConfig.EagleBowArmor);
			this.getAttribute(Attributes.MOVEMENT_SPEED).setBaseValue(OtherMainConfig.EagleBowMovementSpeed);
			this.getAttribute(Attributes.ATTACK_KNOCKBACK).setBaseValue(OtherMainConfig.EagleBowAttackKnockback);
			this.getAttribute(Attributes.KNOCKBACK_RESISTANCE).setBaseValue(OtherMainConfig.EagleBowKnockbackResistance);
		}
		if (level.isClientSide) {
			BossBarEvent.BOSSES.add(this);
		}
		for (int i = 0; i < 20; i++) {
			listRenderer1.add(0f);
			listRenderer2.add(0f);
			listRenderer3.add(0);
			listX.add(0d);
			listY.add(0d);
			listZ.add(0d);
			listXRot.add(0f);
			listYRot.add(0f);
			listXRotO.add(0f);
			listYRotO.add(0f);
			listAttackTime.add(0f);
			listYBodyRot.add(0f);
			listYHeadRot.add(0f);
			listYBodyRotO.add(0f);
			listYHeadRotO.add(0f);
			listWalkSpeed.add(0f);
			listWalkPosition.add(0f);
			listBob.add(0f);
			listRiding.add(false);
			listRidingLivingEntity.add(false);
			listAgeInTicks.add(0f);
		}
	}
	//百分比
	public boolean hasPercentageDamage() {
		return MainConfig.HasPercentageDamage.contains(this.getEncodeId())
				|| OtherMainConfig.BossHasPercentageDamage.contains(this.getEncodeId()) || this.isLegend();
	}
	public float PercentageDamage(DamageSource damageSource) {
		if (AttackFind.MagicResistance(damageSource)) {
			return(float) OtherMainConfig.EagleBowMagicAttackPercentage;
		}
		return (float) OtherMainConfig.EagleBowAttackPercentage;
	}
	//限伤
	public boolean hasDamageCap() {
		return MainConfig.HasDamageCap.contains(this.getEncodeId()) || OtherMainConfig.BossHasDamageCap.contains(this.getEncodeId()) || this.isLegend();
	}
	public float DamageCap(DamageSource damageSource, Entity entity) {
		return (float) OtherMainConfig.EagleBowDamageCap;
	}
	//间隔
	public boolean hasDamageCooldownTick() {
		return MainConfig.HasDamageCooldownTick.contains(this.getEncodeId()) || OtherMainConfig.BossHasDamageCooldownTick.contains(this.getEncodeId()) || this.isLegend();
	}
	public float DamageCooldownTick(DamageSource damageSource, Entity entity) {
		float base = 1.0F;
		if (damageSource.is(DamageTypeTags.BYPASSES_COOLDOWN)) {
			base *= 0.5F;
		}
		return (float)OtherMainConfig.EagleBowDamageCooldownTick * base;
	}

	@Override
	public boolean wantsToPickUp(ItemStack itemStack) {
		if (itemStack.canEquip(EquipmentSlot.HEAD,this)) {
			return false;
		}
		if (itemStack.canEquip(EquipmentSlot.CHEST,this)) {
			return false;
		}
		if (itemStack.canEquip(EquipmentSlot.LEGS,this)) {
			return false;
		}
		if (itemStack.canEquip(EquipmentSlot.FEET,this)) {
			return false;
		}
		return super.wantsToPickUp(itemStack);
	}

	@Override
	protected boolean canReplaceCurrentItem(ItemStack newItem, ItemStack oldItem) {
		if (newItem.getItem() instanceof BowItem) {
			return false;
		}
		if (newItem.getItem() instanceof CrossbowItem) {
			return false;
		}
		if (newItem.getItem() instanceof ShieldItem) {
			return false;
		}
		return super.canReplaceCurrentItem(newItem, oldItem);
	}

	@Override
	public void startSeenByPlayer(ServerPlayer serverPlayer) {
		super.startSeenByPlayer(serverPlayer);
		this.bossEvent.addPlayer(serverPlayer);
	}

	@Override
	public void stopSeenByPlayer(ServerPlayer serverPlayer) {
		super.stopSeenByPlayer(serverPlayer);
		this.bossEvent.removePlayer(serverPlayer);
	}

	@Override
	public void customServerAiStep() {
		super.customServerAiStep();
		this.bossEvent.setProgress(this.getHealth() / this.getMaxHealth());
	}

	@Override
	protected void populateDefaultEquipmentSlots(RandomSource randomSource, DifficultyInstance difficultyInstance) {
		this.maybeWearArmor(EquipmentSlot.HEAD, new ItemStack(JerotesVillageItems.WILDERNESS_HUNTER_HELMET.get()), randomSource);
		this.maybeWearArmor(EquipmentSlot.CHEST, new ItemStack(JerotesVillageItems.WILDERNESS_HUNTER_CHESTPLATE.get()), randomSource);
		this.maybeWearArmor(EquipmentSlot.LEGS, new ItemStack(JerotesVillageItems.WILDERNESS_HUNTER_LEGGINGS.get()), randomSource);
		this.maybeWearArmor(EquipmentSlot.FEET, new ItemStack(JerotesVillageItems.WILDERNESS_HUNTER_BOOTS.get()), randomSource);
	}

	private void maybeWearArmor(EquipmentSlot equipmentSlot, ItemStack itemStack, RandomSource randomSource) {
		this.setItemSlot(equipmentSlot, itemStack);
	}

	public static AttributeSupplier.Builder createAttributes() {
		AttributeSupplier.Builder builder = Mob.createMobAttributes();
		builder = builder.add(Attributes.MAX_HEALTH, 200);
		builder = builder.add(Attributes.ATTACK_DAMAGE, 11.0);
		builder = builder.add(Attributes.ARMOR, 5);
		builder = builder.add(Attributes.MOVEMENT_SPEED, 0.25);
		builder = builder.add(Attributes.ATTACK_KNOCKBACK, 0.25);
		builder = builder.add(Attributes.KNOCKBACK_RESISTANCE, 0.6);

		builder = builder.add(Attributes.FOLLOW_RANGE, 128);
		return builder;
	}


	@Override
	public void performRangedAttack(LivingEntity livingEntity, float f) {
		ItemStack handItem = this.getMainHandItem();
		if ((this.getMainHandItem().isEmpty() || InventoryEntity.isMeleeWeapon(this.getMainHandItem())) && !this.getOffhandItem().isEmpty()) {
			handItem = this.getOffhandItem();
		}
		if (InventoryEntity.isBow(handItem)) {
			int n = this.getBowLevel();
			useBowShoot(this, livingEntity, f, n, 20);
		}
		if (JerotesGameRules.JEROTES_RANGE_CAN_BREAK != null && this.level().getLevelData().getGameRules().getBoolean(JerotesGameRules.JEROTES_RANGE_CAN_BREAK)) {
			if (handItem.getItem() instanceof BowItem || handItem.getItem() instanceof TridentItem) {
				handItem.hurtAndBreak(1, this, player -> player.broadcastBreakEvent((this.getMainHandItem().isEmpty() || InventoryEntity.isMeleeWeapon(this.getMainHandItem())) && !this.getOffhandItem().isEmpty() ? EquipmentSlot.OFFHAND : EquipmentSlot.MAINHAND));
			}
		}
	}

//	@Override
//	protected SoundEvent getAmbientSound() {
//		return JerotesVillageSoundEvents.EAGLE_BOW_AMBIENT;
//	}
//	@Override
//	protected SoundEvent getDeathSound() {
//		return JerotesVillageSoundEvents.EAGLE_BOW_DEATH;
//	}
//	@Override
//	protected SoundEvent getHurtSound(DamageSource damageSource) {
//		return JerotesVillageSoundEvents.EAGLE_BOW_HURT;
//	}
	@Override
	public boolean isLeftHanded() {
		return false;
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
		return aabb1.inflate(0.75d, 0.75d, 0.75d);
	}
	@Override
	public boolean isWithinMeleeAttackRange(LivingEntity livingEntity) {
		return this.getAttackBoundingBox().intersects(livingEntity.getBoundingBox());
	}
	@Override
	protected float getSoundVolume() {
		return 5.0f;
	}
	@Override
	public void travel(Vec3 vec3) {
		super.travel(vec3);
		if (this.specialAction()) {
			this.setDeltaMovement(this.getDeltaMovement().multiply(0.05d, 1d, 0.05d));
		}
	}
	@Override
	public SoundEvent getBossMusic() {
		return JerotesVillageSoundEvents.BOSS_MUSIC_1;
	}

	public float thisTickRenderTime = 0;
	public float lastTickRenderTime = 6;
	public float attackAnimProgress = 0.0f;
	public float shadowAnimProgress = 0.0f;
	public float shootAnimProgress = 0.0f;
	public int spellLevel = 3;
	@Override
	public int getSpellLevel() {
		return this.spellLevel;
	}
	public int startTick;
	public double leadTick;
	public int specialBlockTick;
	public int renderTick = 0;
	public List<Float> listRenderer1 = new ArrayList<>();
	public List<Float> listRenderer2 = new ArrayList<>();
	public List<Integer> listRenderer3 = new ArrayList<>();
	public List<Double> listX = new ArrayList<>();
	public List<Double> listY = new ArrayList<>();
	public List<Double> listZ = new ArrayList<>();
	public List<Float> listXRot = new ArrayList<>();
	public List<Float> listXRotO = new ArrayList<>();
	public List<Float> listYRot = new ArrayList<>();
	public List<Float> listYRotO = new ArrayList<>();
	public List<Float> listAttackTime = new ArrayList<>();
	public List<Float> listYBodyRot = new ArrayList<>();
	public List<Float> listYHeadRot = new ArrayList<>();
	public List<Float> listYBodyRotO = new ArrayList<>();
	public List<Float> listYHeadRotO = new ArrayList<>();
	public List<Float> listWalkSpeed = new ArrayList<>();
	public List<Float> listWalkPosition = new ArrayList<>();
	public List<Float> listBob = new ArrayList<>();
	public List<Boolean> listRiding = new ArrayList<>();
	public List<Boolean> listRidingLivingEntity = new ArrayList<>();
	public List<Float> listAgeInTicks = new ArrayList<>();
	public boolean specialAction() {
		return false;
	}
	@Override
	public void setCustomName(@Nullable Component component) {
		super.setCustomName(component);
		this.bossEvent.setName(this.getDisplayName());
	}
	//是否传奇
	public boolean isLegend() {
		return this.getEntityData().get(IS_LEGEND);
	}
	public void setLegend(boolean bl) {
		this.getEntityData().set(IS_LEGEND, bl);
	}
	public boolean isLegendary() {
		return this.isLegend() || hasPercentageDamage() && hasDamageCap() && hasDamageCooldownTick();
	}

	public int getAttackAnim() {
		return this.getEntityData().get(ATTACK_ANIM);
	}
	public void setAttackAnim(int n) {
		this.getEntityData().set(ATTACK_ANIM, n);
	}
	public int getShadowAnim() {
		return this.getEntityData().get(SHADOW_ANIM);
	}
	public void setShadowAnim(int n) {
		this.getEntityData().set(SHADOW_ANIM, n);
	}
	public int getShootAnim() {
		return this.getEntityData().get(SHOOT_ANIM);
	}
	public void setShootAnim(int n) {
		this.getEntityData().set(SHOOT_ANIM, n);
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
	public void setShootTick(int n){
		this.getEntityData().set(SHOOT_TICK, n);
	}
	public int getShootTick(){
		return this.getEntityData().get(SHOOT_TICK);
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
		if (Objects.equals(animation, "lead")){
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
		list.add(this.leadAnimationState);
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
		compoundTag.putBoolean("IsLegend", this.isLegend());
		compoundTag.putInt("StartTick", this.startTick);
		compoundTag.putDouble("LeadTick", this.leadTick);
		compoundTag.putInt("SpecialBlockTick", this.specialBlockTick);
		compoundTag.putInt("AnimTick", this.getAnimTick());
		compoundTag.putInt("AttackAnim", this.getAttackAnim());
		compoundTag.putInt("ShootAnim", this.getShootAnim());
		compoundTag.putInt("AttackTick", this.getAttackTick());
		compoundTag.putInt("AttackUse", this.getAttackUse());
		compoundTag.putInt("ShootTick", this.getShootTick());
		compoundTag.putInt("ShadowAnim", this.getShadowAnim());
	}
	@Override
	public void readAdditionalSaveData(CompoundTag compoundTag) {
		super.readAdditionalSaveData(compoundTag);
		if (this.hasCustomName()) {
			this.bossEvent.setName(this.getDisplayName());
		}
		this.setLegend(compoundTag.getBoolean("IsLegend"));
		this.startTick = compoundTag.getInt("StartTick");
		this.leadTick = compoundTag.getDouble("LeadTick");
		this.specialBlockTick = compoundTag.getInt("SpecialBlockTick");
		this.setAnimTick(compoundTag.getInt("AnimTick"));
		this.setAttackAnim(compoundTag.getInt("AttackAnim"));
		this.setShootAnim(compoundTag.getInt("ShootAnim"));
		this.setAttackTick(compoundTag.getInt("AttackTick"));
		this.setAttackUse(compoundTag.getInt("AttackUse"));
		this.setShootTick(compoundTag.getInt("ShootTick"));
		this.setShadowAnim(compoundTag.getInt("ShadowAnim"));
		this.bossEvent.setId(this.getUUID());
	}
	@Override
	protected void defineSynchedData() {
		super.defineSynchedData();
		this.getEntityData().define(IS_LEGEND, false);
		this.getEntityData().define(ANIM_STATE, 0);
		this.getEntityData().define(ANIM_TICK, 0);
		this.getEntityData().define(ATTACK_ANIM, 0);
		this.getEntityData().define(SHOOT_TICK, 0);
		this.getEntityData().define(SHADOW_ANIM, 0);
		this.getEntityData().define(SHOOT_ANIM, 0);
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
					case 5:
						this.leadAnimationState.startIfStopped(this.tickCount);
						this.stopMostAnimation(this.leadAnimationState);
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
		if (!level().isClientSide && getBossMusic() != null) {
			if (!isSilent() && getTarget() instanceof Player player && player.isAlive()) {
				this.level().broadcastEntityEvent(this, (byte)67);
			}
			else {
				this.level().broadcastEntityEvent(this, (byte)68);
			}
		}
		if (level().isClientSide()) {
			lastTickRenderTime = thisTickRenderTime;
			thisTickRenderTime = 0;
		}
	}

	@Override
	public void aiStep() {
		super.aiStep();
		this.bossEvent.update();
		if (this.specialAction()){
			this.getNavigation().stop();
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
		if (!this.level().isClientSide()) {
			this.setShadowAnim(Math.max(this.getShadowAnim() - 1, 0));
		}
		//近战攻击
		if (!this.level().isClientSide()) {
			this.setAttackTick(Math.max(0, this.getAttackTick() - 1));
		}
		if (this.getAttackTick() == 10 && this.isAlive() && !this.getMainHandItem().isEmpty()) {
			this.trueHurt(this.getMainHandItem().getItem() instanceof BowItem);
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
		if (this.isUsingItem() && this.getUseItem().getItem() instanceof BowItem || this.getAttackTick() > 0) {
			if (!this.level().isClientSide()) {
				this.setShootAnim(Math.min(this.getShootAnim() + 2, 10));
			}
		}
		else {
			if (!this.level().isClientSide()) {
				this.setShootAnim(Math.max(this.getShootAnim() - 1, 0));
			}
		}
		if (this.getShootTick() > 10){
			if (this.getTarget() != null) {
				this.getLookControl().setLookAt(this.getTarget(), 360f, 360f);
			}
		}
	}

	@Override
	public boolean doHurtTarget(Entity entity) {
		if (this.getAttackTick() > 0) {
			return false;
		}
		if (this.specialAction()) {
			return false;
		}
		if (!this.level().isClientSide()) {
			this.level().broadcastEntityEvent(this, (byte) 102);
			if (this.getMainHandItem().isEmpty()) {
				this.setAttackTick(15);
				return super.doHurtTarget(entity);
			} else {
				this.setAttackTick(15);
			}
		}
		return false;
	}
	public boolean trueHurt(boolean bls) {
		if (!this.isSilent()) {
			this.playSound(SoundEvents.PLAYER_ATTACK_SWEEP, 5.0f, 1.0f);
		}
		List<LivingEntity> list = this.level().getEntitiesOfClass(LivingEntity.class, this.getAttackBoundingBox().inflate(0.5));
		for (LivingEntity hurt : list) {
			if (hurt == null) continue;
			if ((this.distanceToSqr(hurt)) > Double.MAX_VALUE) continue;
			if (AttackFind.FindCanNotAttack(this, hurt)) continue;
			if (!this.hasLineOfSight(hurt)) continue;
			if (!Main.canSee(hurt, this)) continue;
			AttackFind.attackBegin(this, hurt);
			boolean bl = AttackFind.attackAfter(this, hurt, 1.0f, bls ? 2.0f : 1.0f, false, 0f);
			if (bl && bls) {
				if ((Main.mobSizeSmall(hurt) || Main.mobSizeMedium(hurt) || Main.mobSizeLarge(hurt)) &&
						!EntityAndItemFind.isNoSpecialKnockback(hurt.getType())) {
					float knockbackMulti = 2.0f;
					double d;
					if (hurt.getAttribute(Attributes.KNOCKBACK_RESISTANCE) != null) {
						d = hurt.getAttributeValue(Attributes.KNOCKBACK_RESISTANCE);
					} else {
						d = 0.0;
					}
					double d2 = Math.max(0.3, 1.3 - d);
					if (!EntityAndItemFind.isNoSpecialKnockback(hurt.getType()) && (Main.mobSizeSmall(hurt) || Main.mobSizeMedium(hurt) || Main.mobSizeLarge(hurt))) {
						hurt.setOnGround(false);
						hurt.setDeltaMovement(hurt.getDeltaMovement().add(
								-((this.getX() - hurt.getX()) * d2 / 3) * (0.7 + knockbackMulti / 3),
								-((this.getX() - hurt.getX()) * d2 / 8) * (0.7 + knockbackMulti / 3) + 0.2f,
								-((this.getZ() - hurt.getZ()) * d2 / 3) * (0.7 + knockbackMulti / 3)
						));
					}
				}
			}
			else if (bls) {
				if ((Main.mobSizeSmall(hurt) || Main.mobSizeMedium(hurt) || Main.mobSizeLarge(hurt)) &&
						!EntityAndItemFind.isNoSpecialKnockback(hurt.getType())) {
					float knockbackMulti = 1.0f;
					double d;
					if (hurt.getAttribute(Attributes.KNOCKBACK_RESISTANCE) != null) {
						d = hurt.getAttributeValue(Attributes.KNOCKBACK_RESISTANCE);
					} else {
						d = 0.0;
					}
					double d2 = Math.max(0.3, 1.3 - d);
					if (!EntityAndItemFind.isNoSpecialKnockback(hurt.getType()) && (Main.mobSizeSmall(hurt) || Main.mobSizeMedium(hurt) || Main.mobSizeLarge(hurt))) {
						hurt.setOnGround(false);
						hurt.setDeltaMovement(hurt.getDeltaMovement().add(
								-((this.getX() - hurt.getX()) * d2 / 3) * (0.7 + knockbackMulti / 3),
								-((this.getX() - hurt.getX()) * d2 / 8) * (0.7 + knockbackMulti / 3) + 0.2f,
								-((this.getZ() - hurt.getZ()) * d2 / 3) * (0.7 + knockbackMulti / 3)
						));
					}
				}
			}
		}
		//横扫效果
		Main.sweepAttack(this);
		if (JerotesGameRules.JEROTES_MELEE_CAN_BREAK != null && this.level().getLevelData().getGameRules().getBoolean(JerotesGameRules.JEROTES_MELEE_CAN_BREAK)) {
			ItemStack hand = this.getMainHandItem();
			hand.hurtAndBreak(1, this, player -> player.broadcastBreakEvent(EquipmentSlot.MAINHAND));
		}
		//随机移动
		this.setDeltaMovement(this.getDeltaMovement().add(
				(this.getRandom().nextFloat() - 0.5f) * 0.5f,0,(this.getRandom().nextFloat() - 0.5f) * 0.5f)
		);
		return true;
	}

	@Override
	public boolean isInvulnerableTo(DamageSource damageSource) {
		if (damageSource.is(DamageTypes.CACTUS)
		|| damageSource.is(DamageTypes.SWEET_BERRY_BUSH))
			return true;
		return super.isInvulnerableTo(damageSource);
	}
	@Override
	public boolean hurt(DamageSource damageSource, float amount) {
		if (isInvulnerableTo(damageSource)) {
			return super.hurt(damageSource, amount);
		}
		if (damageSource.getEntity() instanceof LivingEntity livingEntity &&
				AttackFind.isMeleeDamage(damageSource) &&
				!livingEntity.canDisableShield() &&
				livingEntity.getItemInHand(InteractionHand.MAIN_HAND).getItem() instanceof TieredItem) {
			amount *= 0.75f;
		}
		if (damageSource.getDirectEntity() instanceof AbstractArrow) {
			amount *= 0.75f;
		}
		return super.hurt(damageSource, amount);
	}

	@Override
	public boolean canBeAffected(MobEffectInstance mobEffectInstance) {
		if (mobEffectInstance.getEffect() == MobEffects.LEVITATION) {
			return false;
		}
		return super.canBeAffected(mobEffectInstance);
	}

	@Override
	public ItemStack createSpawnWeapon(float weaponRandom) {
		ItemStack itemStack = new ItemStack(JerotesVillageItems.WILDERNESS_BOW.get());
		itemStack.enchant(Enchantments.POWER_ARROWS, 3);
		return itemStack;
	}


	@Nullable
	@Override
	public SpawnGroupData finalizeSpawn(ServerLevelAccessor serverLevelAccessor, DifficultyInstance difficultyInstance, MobSpawnType mobSpawnType, @Nullable SpawnGroupData spawnGroupData, @Nullable CompoundTag compoundTag) {
		super.finalizeSpawn(serverLevelAccessor, difficultyInstance, mobSpawnType, spawnGroupData, compoundTag);
		this.setShieldLevel(3);
		this.setBowLevel(20);
		return spawnGroupData;
	}

	@Override
	public void handleEntityEvent(byte by) {
		if (by == 101) {
			float f = this.getRandom().nextFloat();
			if (f > 0.6f) {
				this.shootSlow1AnimationState.start(this.tickCount);
			}
			else if (f > 0.5f) {
				this.shootSlow2AnimationState.start(this.tickCount);
			}
			else if (f > 0.4f) {
				this.shootSlow3AnimationState.start(this.tickCount);
			}
			else {
				this.shootSlow4AnimationState.start(this.tickCount);
			}
		}
		else if (by == 102) {
			int attackRandom = Main.randomReach(this.getRandom(), 1, 30);
			if (this.getMainHandItem().isEmpty()) {
				if (attackRandom <= 30) {
					this.attack1AnimationState.start(this.tickCount);
				} else {
					this.attack2AnimationState.start(this.tickCount);
				}
			} else {
				if (attackRandom >= 15) {
					this.meleeAttack1AnimationState.start(this.tickCount);
				} else {
					this.meleeAttack2AnimationState.start(this.tickCount);
				}
			}
		}
		else if (by == 67) {
			BossMusicPlayer.playBossMusic(this);
		}
		else if (by == 68) {
			BossMusicPlayer.stopBossMusic(this);
		}
		else {
			super.handleEntityEvent(by);
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
		this.startAnimationState.stop();
		this.shootSlow1AnimationState.stop();
		this.shootSlow2AnimationState.stop();
		this.shootSlow3AnimationState.stop();
		this.shootSlow4AnimationState.stop();
		if (this.deathTime >= 40 && !this.level().isClientSide() && !this.isRemoved()) {
			this.level().broadcastEntityEvent(this, (byte)60);
			this.remove(RemovalReason.KILLED);
		}
	}
}
