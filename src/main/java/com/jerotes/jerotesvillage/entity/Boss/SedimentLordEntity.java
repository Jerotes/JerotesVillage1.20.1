package com.jerotes.jerotesvillage.entity.Boss;

import com.jerotes.jerotes.client.sound.BossMusicPlayer;
import com.jerotes.jerotes.config.MainConfig;
import com.jerotes.jerotes.entity.Interface.BossEntity;
import com.jerotes.jerotes.entity.Interface.SpellUseEntity;
import com.jerotes.jerotes.entity.Interface.UseShieldEntity;
import com.jerotes.jerotes.event.JerotesBossEvent;
import com.jerotes.jerotes.init.JerotesGameRules;
import com.jerotes.jerotes.init.JerotesMobEffects;
import com.jerotes.jerotes.init.JerotesParticleTypes;
import com.jerotes.jerotes.item.Interface.ItemSpecialEffect;
import com.jerotes.jerotes.item.Interface.ItemTwoHanded;
import com.jerotes.jerotes.util.AttackFind;
import com.jerotes.jerotes.util.EntityAndItemFind;
import com.jerotes.jerotes.util.Main;
import com.jerotes.jerotesvillage.config.OtherMainConfig;
import com.jerotes.jerotesvillage.entity.Monster.Resurrecter.ResurrecterEntity;
import com.jerotes.jerotesvillage.entity.Shoot.Arrow.AncientSecondRounderArrowEntity;
import com.jerotes.jerotesvillage.event.BossBarEvent;
import com.jerotes.jerotesvillage.init.JerotesVillageItems;
import com.jerotes.jerotesvillage.init.JerotesVillageParticleTypes;
import com.jerotes.jerotesvillage.init.JerotesVillageSoundEvents;
import com.jerotes.jerotesvillage.spell.OtherSpellFind;
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
import net.minecraft.util.Mth;
import net.minecraft.util.RandomSource;
import net.minecraft.world.BossEvent;
import net.minecraft.world.Difficulty;
import net.minecraft.world.DifficultyInstance;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.damagesource.DamageTypes;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.*;
import net.minecraft.world.entity.ai.attributes.AttributeSupplier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.ai.util.LandRandomPos;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.*;
import net.minecraft.world.level.GameRules;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.ServerLevelAccessor;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.Vec3;

import javax.annotation.Nullable;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class SedimentLordEntity extends ResurrecterEntity implements BossEntity, SpellUseEntity {
	public AnimationState startAnimationState = new AnimationState();
	public AnimationState idleAnimationState = new AnimationState();
	public AnimationState attack1AnimationState = new AnimationState();
	public AnimationState attack2AnimationState = new AnimationState();
	public AnimationState swordAttack1AnimationState = new AnimationState();
	public AnimationState swordAttack2AnimationState = new AnimationState();
	public AnimationState swordAttack3AnimationState = new AnimationState();
	public AnimationState swordAttack4AnimationState = new AnimationState();
	public AnimationState leadAnimationState = new AnimationState();
	public AnimationState block1AnimationState = new AnimationState();
	public AnimationState block2AnimationState = new AnimationState();
	public AnimationState block3AnimationState = new AnimationState();
	public AnimationState shieldAttackAnimationState = new AnimationState();
	public AnimationState dead1AnimationState = new AnimationState();
	public AnimationState dead2AnimationState = new AnimationState();
	public AnimationState pushingAttackAnimationState = new AnimationState();
	public AnimationState tripAttackAnimationState = new AnimationState();
	public AnimationState precisionAttackAnimationState = new AnimationState();
	public AnimationState sweepingAttackAnimationState = new AnimationState();
	public AnimationState menacingAttackAnimationState = new AnimationState();
	public AnimationState maneuveringAttackAnimationState = new AnimationState();
	public AnimationState lungingAttackAnimationState = new AnimationState();
	public AnimationState goadingAttackAnimationState = new AnimationState();
	public AnimationState feintingAttackAnimationState = new AnimationState();
	public AnimationState disarmingAttackAnimationState = new AnimationState();
	public AnimationState distractingStrikeAnimationState = new AnimationState();
	private final JerotesBossEvent bossEvent = new JerotesBossEvent(this, this.getUUID(), BossEvent.BossBarColor.BLUE, false);
	private static final EntityDataAccessor<Integer> ANIM_STATE = SynchedEntityData.defineId(SedimentLordEntity.class, EntityDataSerializers.INT);
	private static final EntityDataAccessor<Integer> ANIM_TICK = SynchedEntityData.defineId(SedimentLordEntity.class, EntityDataSerializers.INT);
	private static final EntityDataAccessor<Integer> ATTACK_WALK_ANIM = SynchedEntityData.defineId(SedimentLordEntity.class, EntityDataSerializers.INT);
	private static final EntityDataAccessor<Integer> ACTION_SURGE = SynchedEntityData.defineId(SedimentLordEntity.class, EntityDataSerializers.INT);
	private static final EntityDataAccessor<Integer> ATTACK_TICK = SynchedEntityData.defineId(SedimentLordEntity.class, EntityDataSerializers.INT);
	private static final EntityDataAccessor<Integer> SPECIAL_ATTACK_COOLDOWN = SynchedEntityData.defineId(SedimentLordEntity.class, EntityDataSerializers.INT);
	private static final EntityDataAccessor<Integer> SPECIAL_ATTACK_TICK = SynchedEntityData.defineId(SedimentLordEntity.class, EntityDataSerializers.INT);
	private static final EntityDataAccessor<Integer> SPECIAL_ATTACK_USE = SynchedEntityData.defineId(SedimentLordEntity.class, EntityDataSerializers.INT);
	private static final EntityDataAccessor<Integer> LEAD_TICK = SynchedEntityData.defineId(SedimentLordEntity.class, EntityDataSerializers.INT);
	private static final EntityDataAccessor<Integer> LEAD_USE_TICK = SynchedEntityData.defineId(SedimentLordEntity.class, EntityDataSerializers.INT);
	private static final EntityDataAccessor<Integer> SHIELD_ATTACK_TICK = SynchedEntityData.defineId(SedimentLordEntity.class, EntityDataSerializers.INT);
	private static final EntityDataAccessor<Integer> SPECIAL_BLOCK_TICK = SynchedEntityData.defineId(SedimentLordEntity.class, EntityDataSerializers.INT);
	private static final EntityDataAccessor<Boolean> IS_LEGEND = SynchedEntityData.defineId(SedimentLordEntity.class, EntityDataSerializers.BOOLEAN);

	public SedimentLordEntity(EntityType<? extends SedimentLordEntity> entityType, Level level) {
		super(entityType, level);
		setMaxUpStep(1.1f);
		xpReward = 300;
		this.setCanPickUpLoot(true);
		this.armorDropChances[EquipmentSlot.HEAD.getIndex()] = (float) OtherMainConfig.SedimentLordDropEquipmentChance;
		this.armorDropChances[EquipmentSlot.CHEST.getIndex()] = (float) OtherMainConfig.SedimentLordDropEquipmentChance;
		this.armorDropChances[EquipmentSlot.LEGS.getIndex()] = (float) OtherMainConfig.SedimentLordDropEquipmentChance;
		this.armorDropChances[EquipmentSlot.FEET.getIndex()] = (float) OtherMainConfig.SedimentLordDropEquipmentChance;
		this.handDropChances[EquipmentSlot.MAINHAND.getIndex()] = (float) OtherMainConfig.SedimentLordDropEquipmentChance;
		this.handDropChances[EquipmentSlot.OFFHAND.getIndex()] = (float) OtherMainConfig.SedimentLordDropEquipmentChance;
		if (OtherMainConfig.BossBaseAttributeCanUseConfig) {
			this.getAttribute(Attributes.MAX_HEALTH).setBaseValue(OtherMainConfig.SedimentLordMaxHealth);
			this.setHealth(this.getMaxHealth());
			this.getAttribute(Attributes.ATTACK_DAMAGE).setBaseValue(OtherMainConfig.SedimentLordMeleeDamage);
			this.getAttribute(Attributes.ARMOR).setBaseValue(OtherMainConfig.SedimentLordArmor);
			this.getAttribute(Attributes.MOVEMENT_SPEED).setBaseValue(OtherMainConfig.SedimentLordMovementSpeed);
			this.getAttribute(Attributes.ATTACK_KNOCKBACK).setBaseValue(OtherMainConfig.SedimentLordAttackKnockback);
			this.getAttribute(Attributes.KNOCKBACK_RESISTANCE).setBaseValue(OtherMainConfig.SedimentLordKnockbackResistance);
		}
		if (level.isClientSide) {
			BossBarEvent.BOSSES.add(this);
		}
	}
	//百分比
	public boolean hasPercentageDamage() {
		return MainConfig.HasPercentageDamage.contains(this.getEncodeId())
				|| OtherMainConfig.BossHasPercentageDamage.contains(this.getEncodeId()) || this.isLegend();
	}
	public float PercentageDamage(DamageSource damageSource) {
		if (AttackFind.MagicResistance(damageSource)) {
			return(float) OtherMainConfig.SedimentLordMagicAttackPercentage;
		}
		return (float) OtherMainConfig.SedimentLordAttackPercentage;
	}
	//限伤
	public boolean hasDamageCap() {
		return MainConfig.HasDamageCap.contains(this.getEncodeId()) || OtherMainConfig.BossHasDamageCap.contains(this.getEncodeId()) || this.isLegend();
	}
	public float DamageCap(DamageSource damageSource, Entity entity) {
		return (float) OtherMainConfig.SedimentLordDamageCap;
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
		return (float)OtherMainConfig.SedimentLordDamageCooldownTick * base;
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
		this.maybeWearArmor(EquipmentSlot.HEAD, new ItemStack(JerotesVillageItems.ANCIENT_SECOND_ROUNDER_NOBLE_HELMET.get()), randomSource);
		this.maybeWearArmor(EquipmentSlot.CHEST, new ItemStack(JerotesVillageItems.ANCIENT_SECOND_ROUNDER_NOBLE_CHESTPLATE.get()), randomSource);
		this.maybeWearArmor(EquipmentSlot.LEGS, new ItemStack(JerotesVillageItems.ANCIENT_SECOND_ROUNDER_NOBLE_LEGGINGS.get()), randomSource);
		this.maybeWearArmor(EquipmentSlot.FEET, new ItemStack(JerotesVillageItems.ANCIENT_SECOND_ROUNDER_NOBLE_BOOTS.get()), randomSource);
	}

	private void maybeWearArmor(EquipmentSlot equipmentSlot, ItemStack itemStack, RandomSource randomSource) {
		this.setItemSlot(equipmentSlot, itemStack);
	}

	public static AttributeSupplier.Builder createAttributes() {
		AttributeSupplier.Builder builder = Mob.createMobAttributes();
		builder = builder.add(Attributes.MAX_HEALTH, 170);
		builder = builder.add(Attributes.ATTACK_DAMAGE, 6);
		builder = builder.add(Attributes.ARMOR, 3);
		builder = builder.add(Attributes.MOVEMENT_SPEED, 0.20);
		builder = builder.add(Attributes.ATTACK_KNOCKBACK, 0.4);
		builder = builder.add(Attributes.KNOCKBACK_RESISTANCE, 0.5);

		builder = builder.add(Attributes.FOLLOW_RANGE, 128);
		return builder;
	}

	@Override
	protected SoundEvent getAmbientSound() {
		return JerotesVillageSoundEvents.SEDIMENT_LORD_AMBIENT;
	}
	@Override
	protected SoundEvent getDeathSound() {
		return JerotesVillageSoundEvents.SEDIMENT_LORD_DEATH;
	}
	@Override
	protected SoundEvent getHurtSound(DamageSource damageSource) {
		return JerotesVillageSoundEvents.SEDIMENT_LORD_HURT;
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
			return aabb1.inflate(0.75d, 0.75d, 0.75d);
		return aabb1.inflate(0.5d, 0.5d, 0.5d);
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
	public boolean isBlocking() {
		if (this.getSpecialBlockTick() > 4) {
			return true;
		}
		return super.isBlocking();
	}
	@Override
	public SoundEvent getBossMusic() {
		return JerotesVillageSoundEvents.BOSS_MUSIC_1;
	}

	public int spellLevel = 3;
	@Override
	public int getSpellLevel() {
		return this.spellLevel;
	}
	public int getActionSurge() {
		return this.getEntityData().get(ACTION_SURGE);
	}
	public void setActionSurge(int n) {
		this.getEntityData().set(ACTION_SURGE, n);
	}
	public void setAttackTick(int n){
		this.getEntityData().set(ATTACK_TICK, n);
	}
	public int getAttackTick(){
		return this.getEntityData().get(ATTACK_TICK);
	}
	public void setSpecialAttackCooldown(int n){
		this.getEntityData().set(SPECIAL_ATTACK_COOLDOWN, n);
	}
	public int getSpecialAttackCooldown(){
		return this.getEntityData().get(SPECIAL_ATTACK_COOLDOWN);
	}
	public void setSpecialAttackTick(int n){
		this.getEntityData().set(SPECIAL_ATTACK_TICK, n);
	}
	public int getSpecialAttackTick(){
		return this.getEntityData().get(SPECIAL_ATTACK_TICK);
	}
	public void setSpecialAttackUse(int n){
		this.getEntityData().set(SPECIAL_ATTACK_USE, n);
	}
	public int getSpecialAttackUse(){
		return this.getEntityData().get(SPECIAL_ATTACK_USE);
	}
	public void setLeadTick(int n){
		this.getEntityData().set(LEAD_TICK, n);
	}
	public int getLeadTick(){
		return this.getEntityData().get(LEAD_TICK);
	}
	public void setLeadUseTick(int n){
		this.getEntityData().set(LEAD_USE_TICK, n);
	}
	public int getLeadUseTick(){
		return this.getEntityData().get(LEAD_USE_TICK);
	}
	public void setShieldAttackTick(int n){
		this.getEntityData().set(SHIELD_ATTACK_TICK, n);
	}
	public int getShieldAttackTick(){
		return this.getEntityData().get(SHIELD_ATTACK_TICK);
	}
	public void setSpecialBlockTick(int n){
		this.getEntityData().set(SPECIAL_BLOCK_TICK, n);
	}
	public int getSpecialBlockTick(){
		return this.getEntityData().get(SPECIAL_BLOCK_TICK);
	}
	public int startTick;
	public boolean specialAction() {
		return this.getLeadUseTick() > 0 ||
				this.getShieldAttackTick() > 0 ||
				this.getSpecialAttackTick() > 0 && this.getSpecialAttackUse() != 7 ||
				this.startTick > 0;
	}
	@Override
	public void setCustomName(@Nullable Component component) {
		super.setCustomName(component);
		this.bossEvent.setName(this.getDisplayName());
	}
	public void setAttackWalkAnim(int n){
		this.getEntityData().set(ATTACK_WALK_ANIM, n);
	}
	public int getAttackWalkAnim(){
		return this.getEntityData().get(ATTACK_WALK_ANIM);
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
		if (Objects.equals(animation, "start")){
			return 1;
		}
		else if (Objects.equals(animation, "attack1")){
			return 2;
		}
		else if (Objects.equals(animation, "attack2")){
			return 3;
		}
		else if (Objects.equals(animation, "swordAttack1")){
			return 4;
		}
		else if (Objects.equals(animation, "swordAttack2")){
			return 5;
		}
		else if (Objects.equals(animation, "swordAttack3")){
			return 6;
		}
		else if (Objects.equals(animation, "swordAttack4")){
			return 7;
		}
		else if (Objects.equals(animation, "lead")){
			return 8;
		}
		else if (Objects.equals(animation, "block1")){
			return 9;
		}
		else if (Objects.equals(animation, "block2")){
			return 10;
		}
		else if (Objects.equals(animation, "block3")){
			return 11;
		}
		else if (Objects.equals(animation, "shieldAttack")){
			return 12;
		}
		else if (Objects.equals(animation, "dead1")){
			return 13;
		}
		else if (Objects.equals(animation, "dead2")){
			return 14;
		}
		else if (Objects.equals(animation, "pushingAttack")){
			return 15;
		}
		else if (Objects.equals(animation, "tripAttack")){
			return 16;
		}
		else if (Objects.equals(animation, "precisionAttack")){
			return 17;
		}
		else if (Objects.equals(animation, "sweepingAttack")){
			return 18;
		}
		else if (Objects.equals(animation, "menacingAttack")){
			return 19;
		}
		else if (Objects.equals(animation, "maneuveringAttack")){
			return 20;
		}
		else if (Objects.equals(animation, "lungingAttack")){
			return 21;
		}
		else if (Objects.equals(animation, "goadingAttack")){
			return 22;
		}
		else if (Objects.equals(animation, "feintingAttack")){
			return 23;
		}
		else if (Objects.equals(animation, "disarmingAttack")){
			return 24;
		}
		else if (Objects.equals(animation, "distractingStrike")){
			return 25;
		}
		else {
			return 0;
		}
	}
	public List<AnimationState> getAllAnimations(){
		List<AnimationState> list = new ArrayList<>();
		list.add(this.startAnimationState);
		list.add(this.attack1AnimationState);
		list.add(this.attack2AnimationState);
		list.add(this.swordAttack1AnimationState);
		list.add(this.swordAttack2AnimationState);
		list.add(this.swordAttack3AnimationState);
		list.add(this.swordAttack4AnimationState);
		list.add(this.leadAnimationState);
		list.add(this.block1AnimationState);
		list.add(this.block2AnimationState);
		list.add(this.block3AnimationState);
		list.add(this.shieldAttackAnimationState);
		list.add(this.dead1AnimationState);
		list.add(this.dead2AnimationState);
		list.add(this.pushingAttackAnimationState);
		list.add(this.tripAttackAnimationState);
		list.add(this.precisionAttackAnimationState);
		list.add(this.sweepingAttackAnimationState);
		list.add(this.menacingAttackAnimationState);
		list.add(this.maneuveringAttackAnimationState);
		list.add(this.lungingAttackAnimationState);
		list.add(this.goadingAttackAnimationState);
		list.add(this.feintingAttackAnimationState);
		list.add(this.disarmingAttackAnimationState);
		list.add(this.distractingStrikeAnimationState);
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
	@Override
	public void addAdditionalSaveData(CompoundTag compoundTag) {
		super.addAdditionalSaveData(compoundTag);
		compoundTag.putBoolean("IsLegend", this.isLegend());
		compoundTag.putInt("AnimTick", this.getAnimTick());
		compoundTag.putInt("AttackWalkAnim", this.getAttackWalkAnim());
		compoundTag.putInt("ActionSurge", this.getActionSurge());
		compoundTag.putInt("SpellLevel", this.spellLevel);
		compoundTag.putInt("StartTick", this.startTick);
		compoundTag.putInt("AttackTick", this.getAttackTick());
		compoundTag.putInt("SpecialAttackCooldown", this.getSpecialAttackCooldown());
		compoundTag.putInt("SpecialAttackTick", this.getSpecialAttackTick());
		compoundTag.putInt("SpecialAttackUse", this.getSpecialAttackUse());
		compoundTag.putInt("LeadTick", this.getLeadTick());
		compoundTag.putInt("LeadUseTick", this.getLeadUseTick());
		compoundTag.putInt("SpecialBlockTick", this.getSpecialBlockTick());
		compoundTag.putInt("ShieldAttackTick", this.getShieldAttackTick());
	}
	@Override
	public void readAdditionalSaveData(CompoundTag compoundTag) {
		super.readAdditionalSaveData(compoundTag);
		if (this.hasCustomName()) {
			this.bossEvent.setName(this.getDisplayName());
		}
		this.setLegend(compoundTag.getBoolean("IsLegend"));
		this.spellLevel = compoundTag.getInt("SpellLevel");
		this.startTick = compoundTag.getInt("StartTick");
		this.setAnimTick(compoundTag.getInt("AnimTick"));
		this.setActionSurge(compoundTag.getInt("ActionSurge"));
		this.setAttackTick(compoundTag.getInt("AttackTick"));
		this.setAttackWalkAnim(compoundTag.getInt("AttackWalkAnim"));
		this.setSpecialAttackCooldown(compoundTag.getInt("SpecialAttackCooldown"));
		this.setSpecialAttackTick(compoundTag.getInt("SpecialAttackTick"));
		this.setSpecialAttackUse(compoundTag.getInt("SpecialAttackUse"));
		this.setLeadTick(compoundTag.getInt("LeadTick"));
		this.setLeadUseTick(compoundTag.getInt("LeadUseTick"));
		this.setSpecialBlockTick(compoundTag.getInt("SpecialBlockTick"));
		this.setShieldAttackTick(compoundTag.getInt("ShieldAttackTick"));
		this.bossEvent.setId(this.getUUID());
	}
	@Override
	protected void defineSynchedData() {
		super.defineSynchedData();
		this.getEntityData().define(ANIM_STATE, 0);
		this.getEntityData().define(ANIM_TICK, 0);
		this.getEntityData().define(ATTACK_WALK_ANIM, 0);
		this.getEntityData().define(ACTION_SURGE, 0);
		this.getEntityData().define(ATTACK_TICK, 0);
		this.getEntityData().define(SPECIAL_ATTACK_COOLDOWN, 0);
		this.getEntityData().define(SPECIAL_ATTACK_TICK, 0);
		this.getEntityData().define(SPECIAL_ATTACK_USE, 0);
		this.getEntityData().define(LEAD_TICK, (int) (OtherMainConfig.SedimentLordLeadCooldown * 18));
		this.getEntityData().define(LEAD_USE_TICK, 0);
		this.getEntityData().define(SPECIAL_BLOCK_TICK, 0);
		this.getEntityData().define(SHIELD_ATTACK_TICK, -20);
		this.getEntityData().define(IS_LEGEND, false);
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
						this.startAnimationState.startIfStopped(this.tickCount);
						this.stopMostAnimation(this.startAnimationState);
						break;
					case 2:
						this.attack1AnimationState.startIfStopped(this.tickCount);
						this.stopMostAnimation(this.attack1AnimationState);
						break;
					case 3:
						this.attack2AnimationState.startIfStopped(this.tickCount);
						this.stopMostAnimation(this.attack2AnimationState);
						break;
					case 4:
						this.swordAttack1AnimationState.startIfStopped(this.tickCount);
						this.stopMostAnimation(this.swordAttack1AnimationState);
						break;
					case 5:
						this.swordAttack2AnimationState.startIfStopped(this.tickCount);
						this.stopMostAnimation(this.swordAttack2AnimationState);
						break;
					case 6:
						this.swordAttack3AnimationState.startIfStopped(this.tickCount);
						this.stopMostAnimation(this.swordAttack3AnimationState);
						break;
					case 7:
						this.swordAttack4AnimationState.startIfStopped(this.tickCount);
						this.stopMostAnimation(this.swordAttack4AnimationState);
						break;
					case 8:
						this.leadAnimationState.startIfStopped(this.tickCount);
						this.stopMostAnimation(this.leadAnimationState);
						break;
					case 9:
						this.block1AnimationState.startIfStopped(this.tickCount);
						break;
					case 10:
						this.block2AnimationState.startIfStopped(this.tickCount);
						break;
					case 11:
						this.block3AnimationState.startIfStopped(this.tickCount);
						break;
					case 12:
						this.shieldAttackAnimationState.startIfStopped(this.tickCount);
						this.stopMostAnimation(this.shieldAttackAnimationState);
						break;
					case 13:
						this.dead1AnimationState.startIfStopped(this.tickCount);
						this.stopMostAnimation(this.dead1AnimationState);
						break;
					case 14:
						this.dead2AnimationState.startIfStopped(this.tickCount);
						this.stopMostAnimation(this.dead2AnimationState);
						break;
					case 15:
						this.pushingAttackAnimationState.startIfStopped(this.tickCount);
						this.stopMostAnimation(this.pushingAttackAnimationState);
						break;
					case 16:
						this.tripAttackAnimationState.startIfStopped(this.tickCount);
						this.stopMostAnimation(this.tripAttackAnimationState);
						break;
					case 17:
						this.precisionAttackAnimationState.startIfStopped(this.tickCount);
						this.stopMostAnimation(this.precisionAttackAnimationState);
						break;
					case 18:
						this.sweepingAttackAnimationState.startIfStopped(this.tickCount);
						this.stopMostAnimation(this.sweepingAttackAnimationState);
						break;
					case 19:
						this.menacingAttackAnimationState.startIfStopped(this.tickCount);
						this.stopMostAnimation(this.menacingAttackAnimationState);
						break;
					case 20:
						this.maneuveringAttackAnimationState.startIfStopped(this.tickCount);
						this.stopMostAnimation(this.maneuveringAttackAnimationState);
						break;
					case 21:
						this.lungingAttackAnimationState.startIfStopped(this.tickCount);
						this.stopMostAnimation(this.lungingAttackAnimationState);
						break;
					case 22:
						this.goadingAttackAnimationState.startIfStopped(this.tickCount);
						this.stopMostAnimation(this.goadingAttackAnimationState);
						break;
					case 23:
						this.feintingAttackAnimationState.startIfStopped(this.tickCount);
						this.stopMostAnimation(this.feintingAttackAnimationState);
						break;
					case 24:
						this.disarmingAttackAnimationState.startIfStopped(this.tickCount);
						this.stopMostAnimation(this.disarmingAttackAnimationState);
						break;
					case 25:
						this.distractingStrikeAnimationState.startIfStopped(this.tickCount);
						this.stopMostAnimation(this.distractingStrikeAnimationState);
						break;
				}
			}
		}
		super.onSyncedDataUpdated(entityDataAccessor);
	}

	@Override
	public void tick() {
		super.tick();
		if (!level().isClientSide() && getBossMusic() != null) {
			if (!isSilent() && getTarget() instanceof Player player && player.isAlive()) {
				this.level().broadcastEntityEvent(this, (byte)67);
			}
			else {
				this.level().broadcastEntityEvent(this, (byte)68);
			}
		}
	}

	@Override
	public void aiStep() {
		super.aiStep();
		this.bossEvent.update();
		if (this.specialAction()){
			this.getNavigation().stop();
		}
		if (this.startTick > 0) {
			if (this.startTick == 40) {
				if (!this.level().isClientSide()) {
					this.setAnimTick(40);
					this.setAnimationState("start");
				}
			}
			this.startTick -= 1;
		}
		//近战攻击
		if (!this.level().isClientSide()) {
			this.setAttackTick(Math.max(0, this.getAttackTick() - 1));
		}
		if (this.getAttackTick() == 10 && this.isAlive() && this.getSpecialAttackTick() <= 0 && !this.getMainHandItem().isEmpty()) {
			this.trueHurt();
		}
		if (this.getSpecialAttackTick() == 10 && this.isAlive()) {
			this.trueHurtSpecial(this.getSpecialAttackUse());
		}
		if (this.getAttackTick() > 18 || this.getSpecialAttackTick() > 15) {
			if (this.getTarget() != null) {
				this.getLookControl().setLookAt(this.getTarget(), 360f, 360f);
				this.lookAt(this.getTarget(), 360f, 360f);
			}
		}
		//清除动画
		if (!this.level().isClientSide()) {
			this.setAnimTick(Math.max(-1, this.getAnimTick() - 1));
		}
		if (this.getAnimTick() == 0) {
			if (!this.level().isClientSide()) {
				this.setAnimationState(0);
			}
		}
		//攻击行走
		if (this.isAggressive() && !this.getMainHandItem().isEmpty() && !this.specialAction() && this.getAttackTick() <= 0 && this.getSpecialAttackTick() <= 0) {
			if (!this.level().isClientSide()) {
				this.setAttackWalkAnim(Math.min(this.getAttackWalkAnim() + 1, 10));
			}
		}
		else {
			if (!this.level().isClientSide()) {
				this.setAttackWalkAnim(Math.max(this.getAttackWalkAnim() - 1, 0));
			}
		}
		//站立动画
		if (this.isAlive()) {
			this.idleAnimationState.startIfStopped(this.tickCount);
		}

		//战技
		if (this.isAlive() && !this.isNoAi()) {
			//沉降号令
			{
				if (this.getLeadTick() >= (int) (OtherMainConfig.SedimentLordLeadCooldown * 20) &&
						spellNeed(2, 32, 1)) {
					List<SedimentLordEntity> listLord = this.level().getEntitiesOfClass(SedimentLordEntity.class, this.getBoundingBox().inflate(64.0, 64.0, 64.0));
					listLord.removeIf(entity -> this.getTarget() == entity || entity.getTarget() == this || !((this.getTeam() == null && entity.getTeam() == null) || (entity.isAlliedTo(this))));
					List<ResurrecterEntity> listResurrect = this.level().getEntitiesOfClass(ResurrecterEntity.class, this.getBoundingBox().inflate(64.0, 64.0, 64.0));
					listResurrect.removeIf(entity -> this.getTarget() == entity || entity.getTarget() == this || !((this.getTeam() == null && entity.getTeam() == null) || (entity.isAlliedTo(this))));
					if (listResurrect.size() < listLord.size() * 8) {
						if (!this.level().isClientSide()) {
							this.setLeadUseTick(25);
							if (this.getTarget() != null) {
								this.getLookControl().setLookAt(this.getTarget(), 360f, 360f);
								this.lookAt(this.getTarget(), 360.0f, 360.0f);
							}
						}
					}
				}
				if (this.getLeadUseTick() == 25) {
					if (!this.level().isClientSide()) {
						this.setLeadTick(0);
						this.setAnimTick(25);
						this.setAnimationState("lead");
						if (this.getTarget() != null) {
							this.getLookControl().setLookAt(this.getTarget(), 360f, 360f);
							this.lookAt(this.getTarget(), 360.0f, 360.0f);
						}
					}
				}
				if (this.getLeadUseTick() == 15) {
					OtherSpellFind.SedimentOrder(this, this.getTarget(), 4, 4 + 2, 4, 32);
					if (!this.isSilent()) {
						this.level().playSound(null, this.getX(), this.getY(), this.getZ(), JerotesVillageSoundEvents.SEDIMENT_LORD_LEAD, this.getSoundSource(), 10.0f, 1.0f);
					}
				}
			}
			//破盾连击
			{
				if (this.getShieldAttackTick() < 60 && this.getShieldAttackTick() > 0 && this.getShieldAttackTick() % 5 == 0) {
					this.shieldAttack();
				}
			}
			//战技
			{
				//突刺攻击
				if (spellNeed(3, 6, 6) && this.getTarget() != null && this.hasLineOfSight(this.getTarget()) && this.getAttackTick() <= 0 && this.getActionSurge() <= 0 && this.getSpecialAttackCooldown() <= 0) {
					if (!this.level().isClientSide()) {
						this.setAttackTick(20);
						this.setSpecialAttackCooldown(120);
						this.setSpecialAttackTick(20);
						this.setAnimTick(20);
						this.setSpecialAttackUse(7);
						this.setAnimationState("lungingAttack");
						if (this.level() instanceof ServerLevel serverLevel) {
							if (!this.isInvisible()) {
								serverLevel.sendParticles(JerotesVillageParticleTypes.LUNGING_ATTACK_DISPLAY.get(), this.getX(), this.getBoundingBox().maxY + 0.5, this.getZ(), 0, 0.0, 0.0, 0.0, 0);
							}
						}
					}
				}
				if (this.getSpecialAttackTick() <= 13 && this.getSpecialAttackTick() > 10 && this.getSpecialAttackUse() == 7) {
					this.RushAttack();
				}
				if (this.getSpecialAttackTick() > 0 && this.getSpecialAttackUse() == 7) {
					//粒子
					if (this.level() instanceof ServerLevel serverLevel) {
						for (int i = 0; i < 20; ++i) {
							serverLevel.sendParticles(ParticleTypes.PORTAL,
									this.getRandomX(0.25) - this.getDeltaMovement().x, this.getRandomY() - 0.5f -  this.getDeltaMovement().y, this.getRandomZ(0.25) - this.getDeltaMovement().z, 0,
									this.getDeltaMovement().x, this.getDeltaMovement().y, this.getDeltaMovement().z, -0.5f);
						}
					}
				}
			}
			//计算
			if (!this.level().isClientSide()) {
				this.setSpecialAttackTick(Math.max(0, this.getSpecialAttackTick() - 1));
				this.setSpecialAttackCooldown(Math.max(0, this.getSpecialAttackCooldown() - 1));
				this.setLeadTick(Math.min((int) (OtherMainConfig.SedimentLordLeadCooldown * 20), this.getLeadTick() + 1));
				this.setLeadUseTick(Math.max(0, this.getLeadUseTick() - 1));
				this.setSpecialBlockTick(Math.max(0, this.getSpecialBlockTick() - 1));
				this.setShieldAttackTick(Math.max(-20, this.getShieldAttackTick() - 1));
			}
		}
	}
	//法术前提
	public boolean spellNeed(float min, float max, int time) {
		return !this.specialAction() && this.isAlive() && !this.isNoAi() && this.getAttackTick() <= 0 && this.getTarget() != null && this.getTarget().isAlive()
				&& this.getTarget().distanceTo(this) > min && this.getTarget().distanceTo(this) < max
				&& this.getRandom().nextInt(time * 20) == 1;
	}

	public boolean RushAttack() {
		if (this.getTarget() != null) {
			this.getLookControl().setLookAt(this.getTarget(), 360f, 360f);
			this.lookAt(this.getTarget(), 360.0f, 360.0f);
		}
		float f = this.getYRot();
		float f2 = this.getXRot();
		float f3 = -Mth.sin(f * 0.017453292f) * Mth.cos(f2 * 0.017453292f);
		float f4 = -Mth.sin(f2 * 0.017453292f);
		float f5 = Mth.cos(f * 0.017453292f) * Mth.cos(f2 * 0.017453292f);
		float f6 = Mth.sqrt(f3 * f3 + f4 * f4 + f5 * f5);
		float f7 = 0.35f;
		float f8 = f4 *= f7 / f6 * 2;
		this.setDeltaMovement(this.getDeltaMovement().add(f3 *= f7 / f6 * 2, f8, f5 *= f7 / f6 * 2));
		return true;
	}
	@Override
	public boolean doHurtTarget(Entity entity) {
		if (this.specialAction() || this.getAttackTick() > 0 && this.getActionSurge() <= 0 || this.getAttackTick() > 5) {
			return false;
		}
		if (!this.level().isClientSide()) {
			this.setAttackTick(20);
			int attackRandom = Main.randomReach(this.getRandom(), 1, 40);
			//战技攻击
			if (this.getActionSurge() <= 0 && this.getSpecialAttackCooldown() <= 0
					&& this.getRandom().nextFloat() > 0.5f
			) {
				this.setSpecialAttackCooldown(120);
				this.setSpecialAttackTick(20);
				this.setAnimTick(20);
				if (attackRandom > 36) {
					this.setSpecialAttackUse(1);
					this.setAnimationState("pushingAttack");
					if (this.level() instanceof ServerLevel serverLevel) {
						if (!this.isInvisible()) {
							serverLevel.sendParticles(JerotesVillageParticleTypes.PUSHING_ATTACK_DISPLAY.get(), this.getX(), this.getBoundingBox().maxY + 0.5, this.getZ(), 0, 0.0, 0.0, 0.0, 0);
						}
					}
				}
				else if (attackRandom > 32) {
					this.setSpecialAttackUse(2);
					this.setAnimationState("tripAttack");
					if (this.level() instanceof ServerLevel serverLevel) {
						if (!this.isInvisible()) {
							serverLevel.sendParticles(JerotesVillageParticleTypes.TRIP_ATTACK_DISPLAY.get(), this.getX(), this.getBoundingBox().maxY + 0.5, this.getZ(), 0, 0.0, 0.0, 0.0, 0);
						}
					}
				}
				else if (attackRandom > 28) {
					this.setSpecialAttackUse(3);
					this.setAnimationState("precisionAttack");
					if (this.level() instanceof ServerLevel serverLevel) {
						if (!this.isInvisible()) {
							serverLevel.sendParticles(JerotesVillageParticleTypes.PRECISION_ATTACK_DISPLAY.get(), this.getX(), this.getBoundingBox().maxY + 0.5, this.getZ(), 0, 0.0, 0.0, 0.0, 0);
						}
					}
				}
				else if (attackRandom > 24) {
					this.setSpecialAttackUse(4);
					this.setAnimationState("sweepingAttack");
					if (this.level() instanceof ServerLevel serverLevel) {
						if (!this.isInvisible()) {
							serverLevel.sendParticles(JerotesVillageParticleTypes.SWEEPING_ATTACK_DISPLAY.get(), this.getX(), this.getBoundingBox().maxY + 0.5, this.getZ(), 0, 0.0, 0.0, 0.0, 0);
						}
					}
				}
				else if (attackRandom > 20) {
					this.setSpecialAttackUse(5);
					this.setAnimationState("menacingAttack");
					if (this.level() instanceof ServerLevel serverLevel) {
						if (!this.isInvisible()) {
							serverLevel.sendParticles(JerotesVillageParticleTypes.MENACING_ATTACK_DISPLAY.get(), this.getX(), this.getBoundingBox().maxY + 0.5, this.getZ(), 0, 0.0, 0.0, 0.0, 0);
						}
					}
				}
				else if (attackRandom > 16) {
					this.setSpecialAttackUse(6);
					this.setAnimationState("maneuveringAttack");
					if (this.level() instanceof ServerLevel serverLevel) {
						if (!this.isInvisible()) {
							serverLevel.sendParticles(JerotesVillageParticleTypes.MANEUVERING_ATTACK_DISPLAY.get(), this.getX(), this.getBoundingBox().maxY + 0.5, this.getZ(), 0, 0.0, 0.0, 0.0, 0);
						}
					}
				}
				else if (attackRandom > 12) {
					this.setSpecialAttackTick(25);
					this.setAnimTick(25);
					this.setSpecialAttackUse(8);
					this.setAnimationState("goadingAttack");
					if (this.level() instanceof ServerLevel serverLevel) {
						if (!this.isInvisible()) {
							serverLevel.sendParticles(JerotesVillageParticleTypes.GOADING_ATTACK_DISPLAY.get(), this.getX(), this.getBoundingBox().maxY + 0.5, this.getZ(), 0, 0.0, 0.0, 0.0, 0);
						}
					}
				}
				else if (attackRandom > 8) {
					this.setSpecialAttackTick(25);
					this.setAnimTick(25);
					this.setSpecialAttackUse(9);
					this.setAnimationState("feintingAttack");
					if (this.level() instanceof ServerLevel serverLevel) {
						if (!this.isInvisible()) {
							serverLevel.sendParticles(JerotesVillageParticleTypes.FEINTING_ATTACK_DISPLAY.get(), this.getX(), this.getBoundingBox().maxY + 0.5, this.getZ(), 0, 0.0, 0.0, 0.0, 0);
						}
					}
				}
				else if (attackRandom > 4) {
					this.setSpecialAttackUse(10);
					this.setAnimationState("disarmingAttack");
					if (this.level() instanceof ServerLevel serverLevel) {
						if (!this.isInvisible()) {
							serverLevel.sendParticles(JerotesVillageParticleTypes.DISARMING_ATTACK_DISPLAY.get(), this.getX(), this.getBoundingBox().maxY + 0.5, this.getZ(), 0, 0.0, 0.0, 0.0, 0);
						}
					}
				}
				else {
					this.setSpecialAttackUse(11);
					this.setAnimationState("distractingStrike");
					if (this.level() instanceof ServerLevel serverLevel) {
						if (!this.isInvisible()) {
							serverLevel.sendParticles(JerotesVillageParticleTypes.DISTRACTING_STRIKE_DISPLAY.get(), this.getX(), this.getBoundingBox().maxY + 0.5, this.getZ(), 0, 0.0, 0.0, 0.0, 0);
						}
					}
				}
			}
			//普通攻击
			else {
				//动作如潮
				float actionRandom = this.getRandom().nextFloat();
				if (this.getActionSurge() <= 0) {
					if (actionRandom <= 0.25f) {
						this.setActionSurge(1);
						if (!this.isSilent()) {
							this.playSound(JerotesVillageSoundEvents.SEDIMENT_LORD_ACTION, 5.0f, 1.0f);
						}
						if (this.level() instanceof ServerLevel serverLevel) {
							if (!this.isInvisible()) {
								serverLevel.sendParticles(JerotesParticleTypes.ACTION_SURGE_DISPLAY.get(), this.getX(), this.getBoundingBox().maxY + 0.5, this.getZ(), 0, 0.0, 0.0, 0.0, 0);
							}
						}
					}
					else {
						this.setActionSurge(0);
					}
				}
				else if (this.getActionSurge() == 1) {
					if (actionRandom <= 0.75f) {
						this.setActionSurge(2);
						if (!this.isSilent()) {
							this.playSound(JerotesVillageSoundEvents.SEDIMENT_LORD_ACTION, 5.0f, 1.0f);
						}
						if (this.level() instanceof ServerLevel serverLevel) {
							if (!this.isInvisible()) {
								serverLevel.sendParticles(JerotesParticleTypes.ACTION_SURGE_DISPLAY.get(), this.getX(), this.getBoundingBox().maxY + 0.5, this.getZ(), 0, 0.0, 0.0, 0.0, 0);
							}
						}
					}
					else {
						this.setActionSurge(0);
					}
				}
				else if (this.getActionSurge() == 2) {
					if (actionRandom <= 0.25f) {
						this.setActionSurge(3);
						if (!this.isSilent()) {
							this.playSound(JerotesVillageSoundEvents.SEDIMENT_LORD_ACTION, 5.0f, 1.0f);
						}
						if (this.level() instanceof ServerLevel serverLevel) {
							if (!this.isInvisible()) {
								serverLevel.sendParticles(JerotesParticleTypes.ACTION_SURGE_DISPLAY.get(), this.getX(), this.getBoundingBox().maxY + 0.5, this.getZ(), 0, 0.0, 0.0, 0.0, 0);
							}
						}
					}
					else {
						this.setActionSurge(0);
					}
				}
				else if (this.getActionSurge() < 10) {
					if (actionRandom <= 0.1f) {
						this.setActionSurge(this.getActionSurge() + 1);
						if (!this.isSilent()) {
							this.playSound(JerotesVillageSoundEvents.SEDIMENT_LORD_ACTION, 5.0f, 1.0f);
						}
						if (this.level() instanceof ServerLevel serverLevel) {
							if (!this.isInvisible()) {
								serverLevel.sendParticles(JerotesParticleTypes.ACTION_SURGE_DISPLAY.get(), this.getX(), this.getBoundingBox().maxY + 0.5, this.getZ(), 0, 0.0, 0.0, 0.0, 0);
							}
						}
					}
					else {
						this.setActionSurge(0);
					}
				}
				else {
					this.setActionSurge(0);
				}
				if (this.getMainHandItem().isEmpty()) {
					this.setAnimTick(5);
					if (attackRandom <= 30) {
						this.setAnimationState("attack1");
					} else {
						this.setAnimationState("attack2");
					}
					return super.doHurtTarget(entity);
				} else {
					this.setAnimTick(20);
					if (attackRandom >= 30) {
						this.setAnimationState("swordAttack1");
					} else if (attackRandom >= 20) {
						this.setAnimationState("swordAttack2");
					} else if (attackRandom >= 10) {
						this.setAnimationState("swordAttack3");
					} else {
						this.setAnimationState("swordAttack4");
					}
				}
			}
		}
		return true;
	}
	public boolean trueHurt() {
		if (!this.isSilent()) {
			this.playSound(JerotesVillageSoundEvents.SEDIMENT_LORD_ATTACK, 5.0f, 1.0f);
		}
		List<LivingEntity> list = this.level().getEntitiesOfClass(LivingEntity.class, this.getAttackBoundingBox().inflate(0.5));
		for (LivingEntity hurt : list) {
			if (hurt == null) continue;
			if ((this.distanceToSqr(hurt)) > Double.MAX_VALUE) continue;
			if (AttackFind.FindCanNotAttack(this, hurt)) continue;
			if (!this.hasLineOfSight(hurt)) continue;
			if (!Main.canSee(hurt, this)) continue;
			AttackFind.attackBegin(this, hurt);
			AttackFind.attackAfter(this, hurt, 1.0f, 1.0f, false, 0f);
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

	//战技
	public boolean trueHurtSpecial(int n) {
		//1-推撞攻击
		//2-摔绊攻击
		//3-精准攻击
		//4-横扫攻击
		//5-恐吓攻击
		//6-灵动攻击
		//7-突刺攻击
		//8-挑衅攻击
		//9-诡诈攻击
		//10-缴械攻击
		//11-扰乱打击
		if (!this.isSilent()) {
			this.playSound(JerotesVillageSoundEvents.SEDIMENT_LORD_ATTACK, 5.0f, 1.0f);
		}
		if (!this.isSilent()) {
			if (n == 1) {
				this.playSound(JerotesVillageSoundEvents.SEDIMENT_LORD_PUSHING, 5.0f, 1.0f);
			}
			else if (n == 2) {
				this.playSound(JerotesVillageSoundEvents.SEDIMENT_LORD_TRIP, 5.0f, 1.0f);
			}
			else if (n == 3) {
				this.playSound(JerotesVillageSoundEvents.SEDIMENT_LORD_PRECISION, 5.0f, 1.0f);
			}
			else if (n == 4) {
				this.playSound(JerotesVillageSoundEvents.SEDIMENT_LORD_SWEEPING, 5.0f, 1.0f);
			}
			else if (n == 5) {
				this.playSound(JerotesVillageSoundEvents.SEDIMENT_LORD_MENACING, 5.0f, 1.0f);
			}
			else if (n == 6) {
				this.playSound(JerotesVillageSoundEvents.SEDIMENT_LORD_MANEUVERING, 5.0f, 1.0f);
			}
			else if (n == 7) {
				this.playSound(JerotesVillageSoundEvents.SEDIMENT_LORD_LUNGING, 5.0f, 1.0f);
			}
			else if (n == 8) {
				this.playSound(JerotesVillageSoundEvents.SEDIMENT_LORD_GOADING, 5.0f, 1.0f);
			}
			else if (n == 9) {
				this.playSound(JerotesVillageSoundEvents.SEDIMENT_LORD_FEINTING, 5.0f, 1.0f);
			}
			else if (n == 10) {
				this.playSound(JerotesVillageSoundEvents.SEDIMENT_LORD_DISARMING, 5.0f, 1.0f);
			}
			else if (n == 11) {
				this.playSound(JerotesVillageSoundEvents.SEDIMENT_LORD_DISTRACTING, 5.0f, 1.0f);
			}
		}
		int spellLevel = this.getSpellLevel() - 1;
		if (this.level().getDifficulty() == Difficulty.NORMAL) {
			spellLevel = this.getSpellLevel();
		} else if (this.level().getDifficulty() == Difficulty.HARD) {
			spellLevel = this.getSpellLevel() + 1;
		}
		float damageMulti = 1.25f;
		float knockbackMulti = 1.25f;
		float reach = 0.75f;
		if (n == 1) {
			damageMulti = 1.5f;
			knockbackMulti = 2.0f;
		}
		else if (n == 2) {
			knockbackMulti = 0.05f;
		}
		else if (n == 3) {
			damageMulti = 2.0f;
			reach = 1.0f;
		}
		else if (n == 4) {
			reach = 1.5f;
		}
		boolean success = false;
		List<LivingEntity> list = this.level().getEntitiesOfClass(LivingEntity.class, this.getAttackBoundingBox().inflate(reach));
		for (LivingEntity hurt : list) {
			if (hurt == null) continue;
			if ((this.distanceToSqr(hurt)) > Double.MAX_VALUE) continue;
			if (AttackFind.FindCanNotAttack(this, hurt)) continue;
			if (!this.hasLineOfSight(hurt)) continue;
			if (!Main.canSee(hurt, this)) continue;
			AttackFind.attackBegin(this, hurt);
			boolean bl = AttackFind.attackAfter(this, hurt, damageMulti, knockbackMulti, false, 0f);
			if (bl) {
				success = true;
				//推撞
				if (n == 1) {
					if ((Main.mobSizeSmall(hurt) || Main.mobSizeMedium(hurt) || Main.mobSizeLarge(hurt))
							&& !EntityAndItemFind.isNoSpecialKnockback(hurt.getType())) {
						double d = 0.0;
						if (hurt.getAttribute(Attributes.KNOCKBACK_RESISTANCE) != null) {
							d = Math.max(hurt.getAttributeValue(Attributes.KNOCKBACK_RESISTANCE), 1.0);
						}
						double d3 = Math.max(0, 1 - d) + 0.5;
						hurt.setOnGround(false);
						hurt.setDeltaMovement(hurt.getDeltaMovement().add(
								-((this.getX() - hurt.getX()) * d3 / 1.5) * (0.7 + knockbackMulti / 3),
								-((this.getY() - hurt.getY()) * d3 / 1.5) * (0.7 + knockbackMulti / 3),
								-((this.getZ() - hurt.getZ()) * d3 / 1.5) * (0.7 + knockbackMulti / 3)
						));
					}
				}
				//摔绊
				if (n == 2) {
					if ((Main.mobSizeSmall(hurt) || Main.mobSizeMedium(hurt) || Main.mobSizeLarge(hurt))
							&& !EntityAndItemFind.isNoSpecialKnockback(hurt.getType())) {
						if (!hurt.level().isClientSide()) {
							hurt.addEffect(new MobEffectInstance(JerotesMobEffects.PRONE.get(), spellLevel * 20, 0, false, false), this);
						}
					}
				}
				//恐吓
				if (n == 5) {
					if (!hurt.level().isClientSide()) {
						hurt.addEffect(new MobEffectInstance(JerotesMobEffects.ABACK.get(), spellLevel * 40, spellLevel / 2));
						if (!EntityAndItemFind.isAbackAwayImmune(hurt.getType()) && hurt.getHealth() < this.getHealth() * 5 && hurt instanceof PathfinderMob mob) {
							mob.getNavigation().stop();
							if (mob.getNavigation().isDone()) {
								Vec3 vec3 = LandRandomPos.getPosAway(mob, 16, 7, this.position());
								if (vec3 != null) {
									mob.getNavigation().moveTo(mob.getNavigation().createPath(vec3.x, vec3.y, vec3.z, 0), 1.2f);
								}
							}
						}
					}
				}
				//挑衅
				if (n == 9) {
					if (hurt instanceof Mob mob && mob.getTarget() != this) {
						if (!hurt.level().isClientSide()) {
							hurt.addEffect(new MobEffectInstance(MobEffects.WEAKNESS, spellLevel * 20, spellLevel / 2));
						}
					}
				}
				//缴械
				if (n == 10) {
					boolean bl2 = this.level().getGameRules().getBoolean(GameRules.RULE_MOBGRIEFING);
					boolean bl3 = this.level().getGameRules().getBoolean(GameRules.RULE_KEEPINVENTORY);
					if (bl2 && !EntityAndItemFind.isUnableDisarm(hurt)) {
						if (hurt.getMainHandItem().getItem().getRarity(hurt.getMainHandItem()) != Rarity.EPIC) {
							if (!(hurt instanceof Player player && (bl3 || player.getAbilities().instabuild))) {
								hurt.spawnAtLocation(hurt.getMainHandItem());
								hurt.setItemSlot(EquipmentSlot.MAINHAND, new ItemStack(Items.AIR.asItem()));
							}
						}
					}
				}
			}
		}
		//全局
		if (success) {
			//灵动
			if (n == 6) {
				List<LivingEntity> listTeam = this.level().getEntitiesOfClass(LivingEntity.class, this.getBoundingBox().inflate(32, 32, 32));
				for (LivingEntity team : listTeam) {
					if (team == null) continue;
					if (!AttackFind.SameFactionAvoidDamage(this, team, false)) continue;
					if (!team.level().isClientSide()) {
						team.addEffect(new MobEffectInstance(MobEffects.MOVEMENT_SPEED, spellLevel * 20, spellLevel / 2));
					}
					break;
				}
			}
			//扰乱
			if (n == 11) {
				List<LivingEntity> listTeam = this.level().getEntitiesOfClass(LivingEntity.class, this.getBoundingBox().inflate(16, 16, 16));
				for (LivingEntity team : listTeam) {
					if (team == null) continue;
					if (!AttackFind.SameFactionAvoidDamage(this, team, false)) continue;
					if (!team.level().isClientSide()) {
						team.addEffect(new MobEffectInstance(MobEffects.DAMAGE_BOOST, spellLevel * 20, spellLevel / 2));
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
		//灵巧步法
		this.setDeltaMovement(this.getDeltaMovement().add(
				(this.getRandom().nextFloat() - 0.5f) * 0.5f,0,(this.getRandom().nextFloat() - 0.5f) * 0.5f)
		);
		return true;
	}

	public boolean shieldAttack() {
		this.swing(InteractionHand.MAIN_HAND);
		if (!this.isSilent()) {
			this.level().playSound(null, this.getX(), this.getY(), this.getZ(), SoundEvents.PLAYER_ATTACK_CRIT, this.getSoundSource(), 10.0f, 1.0f);
		}
		List<LivingEntity> list = this.level().getEntitiesOfClass(LivingEntity.class, this.getBoundingBox().inflate(2, 2, 2));
		for (LivingEntity hurt : list) {
			if (hurt == null) continue;
			if (AttackFind.FindCanNotAttack(this, hurt)) continue;
			AttackFind.attackBegin(this, hurt);
			AttackFind.attackAfter(this, hurt, 1f, 0.5f, false, 0f);
			//破盾
			if (hurt.getUseItem().getItem() instanceof ShieldItem) {
				if (hurt instanceof Player player) {
					player.disableShield(true);
				} else if (hurt instanceof UseShieldEntity useShieldEntity) {
					useShieldEntity.disableShield();
				}
			}
			//横扫粒子
			double d3 = -Mth.sin(this.getYRot() * 0.017453292f);
			double d4 = Mth.cos(this.getYRot() * 0.017453292f);
			if (this.level() instanceof ServerLevel serverLevel) {
				for (int i = 0; i < 15; ++i) {
					double xs = this.getX() + d3 + (this.getRandom().nextFloat() - 0.5f) * 2 - (this.getX() - hurt.getX()) / 2;
					double ys = this.getY(0.5) + this.getRandom().nextFloat() - 0.5f - (this.getY() - hurt.getY()) / 2;
					double zs = this.getZ() + d4 + (this.getRandom().nextFloat() - 0.5f) * 2 - (this.getZ() - hurt.getZ()) / 2;
					serverLevel.sendParticles(ParticleTypes.SWEEP_ATTACK, xs, ys, zs, 0, d3, 0.0, d4, 0.0);
				}
			}
		}
		//横扫效果
		Main.sweepAttack(this);
		return true;
	}

	@Override
	public boolean isInvulnerableTo(DamageSource damageSource) {
		if (damageSource.getEntity() instanceof AncientSecondRounderArrowEntity || damageSource.getDirectEntity() instanceof AncientSecondRounderArrowEntity)
			return true;
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
		if (damageSource.is(DamageTypeTags.IS_FALL))
			amount /= 3;
		if (isDamageSourceBlocks(damageSource) && this.getSpecialBlockTick() > 0) {
			amount /= 2;
		}
		if (!this.isNoAi() &&
				this.getTarget() != null &&
				!this.isInvulnerableTo(damageSource) &&
				isDamageSourceBlocks(damageSource) &&
				!this.getMainHandItem().isEmpty() &&
				!(this.getOffhandItem().getItem() instanceof ShieldItem) &&
				this.isAngryTick() &&
				this.isAlive() &&
				!this.specialAction()) {
			if (this.getSpecialBlockTick() <= 0) {
				if (!this.level().isClientSide()) {
					this.level().broadcastEntityEvent(this, (byte) 101);
				}
				if (this.getOffhandItem().isEmpty() &&
						this.getMainHandItem().getItem() instanceof ItemTwoHanded itemTwoHanded && itemTwoHanded.canBlock()) {
					if (this.getMainHandItem().getItem() instanceof ItemSpecialEffect specialEffect) {
						specialEffect.blockUse(this, damageSource.getEntity(), damageSource);
					}
				}
				int attackDamage = (int) this.getAttributeValue(Attributes.ATTACK_DAMAGE) * 10;
				if (attackDamage < 2) {
					attackDamage = 2;
				}
				if (this.getRandom().nextInt(1, attackDamage) > amount * 3.5) {
					if (!this.level().isClientSide()) {
						this.setSpecialBlockTick(5);
					}
					if (!this.isSilent()) {
						this.level().playSound(null, this.getX(), this.getY(), this.getZ(), JerotesVillageSoundEvents.SEDIMENT_LORD_BLOCK, this.getSoundSource(), 10.0f, 1.0f);
					}
					if (JerotesGameRules.JEROTES_MELEE_CAN_BREAK != null && this.level().getLevelData().getGameRules().getBoolean(JerotesGameRules.JEROTES_MELEE_CAN_BREAK)) {
						ItemStack hand = this.getMainHandItem();
						hand.hurtAndBreak((int) amount/3, this, player -> player.broadcastBreakEvent(EquipmentSlot.MAINHAND));
					}
					return false;
				}
			}
		}
		if (this.getShieldAttackTick() > 0) {
			if (!this.level().isClientSide()) {
				this.setShieldAttackTick(0);
				this.shieldAttackAnimationState.stop();
				this.stopAllAnimation();
			}
		}
		if (damageSource.is(DamageTypeTags.BYPASSES_ARMOR) && !damageSource.is(DamageTypeTags.BYPASSES_INVULNERABILITY)) {
			amount /= 2;
		}
		return super.hurt(damageSource, amount);
	}

	@Override
	protected void blockedByShield(LivingEntity livingEntity) {
		if (this.getShieldAttackTick() <= -20) {
			if (!this.level().isClientSide()) {
				this.setShieldAttackTick(60);
				this.setAnimTick(70);
				this.setAnimationState("shieldAttack");
			}
			if (this.level() instanceof ServerLevel serverLevel) {
				if (!this.isInvisible()) {
					serverLevel.sendParticles(JerotesVillageParticleTypes.SHIELD_PIERCING_COMBO_DISPLAY.get(), this.getX(), this.getBoundingBox().maxY + 0.5, this.getZ(), 0, 0.0, 0.0, 0.0, 0);
				}
			}
			if (!this.isSilent()) {
				this.level().playSound(null, this.getX(), this.getY(), this.getZ(), JerotesVillageSoundEvents.SEDIMENT_LORD_SPECIAL, this.getSoundSource(), 10.0f, 1.0f);
			}
		}
	}

	@Override
	public boolean canBeAffected(MobEffectInstance mobEffectInstance) {
		if (mobEffectInstance.getEffect() == MobEffects.LEVITATION) {
			return false;
		}
		return super.canBeAffected(mobEffectInstance);
	}


	@Override
	public void handleEntityEvent(byte by) {
		if (by == 67) {
			BossMusicPlayer.playBossMusic(this);
		}
		else if (by == 68) {
			BossMusicPlayer.stopBossMusic(this);
		}
		else if (by == 101) {
			int blockRandom = this.getRandom().nextInt(30);
			if (blockRandom > 20) {
				this.block1AnimationState.start(this.tickCount);
			} else if (blockRandom > 10) {
				this.block2AnimationState.start(this.tickCount);
			} else {
				this.block3AnimationState.start(this.tickCount);
			}
		}
		else {
			super.handleEntityEvent(by);
		}
	}

	@Override
	public ItemStack createSpawnWeapon(float weaponRandom) {
		if (weaponRandom > 0.65) {
			return new ItemStack(JerotesVillageItems.ANCIENT_SECOND_ROUNDER_GREATSWORD.get());
		}
		if (weaponRandom > 0.35) {
			return new ItemStack(JerotesVillageItems.ANCIENT_SECOND_ROUNDER_BATTLEAXE.get());
		}
		return new ItemStack(JerotesVillageItems.ANCIENT_SECOND_ROUNDER_WARHAMMER.get());
	}

	@Nullable
	@Override
	public SpawnGroupData finalizeSpawn(ServerLevelAccessor serverLevelAccessor, DifficultyInstance difficultyInstance, MobSpawnType mobSpawnType, @Nullable SpawnGroupData spawnGroupData, @Nullable CompoundTag compoundTag) {
		this.setShieldLevel(3);
		return super.finalizeSpawn(serverLevelAccessor, difficultyInstance, mobSpawnType, spawnGroupData, compoundTag);
	}

	@Override
	public void tickDeath() {
		if(deathTime <= 0) {
			if (this.getMainHandItem().isEmpty()) {
				if (!this.level().isClientSide()) {
					this.setAnimTick(40);
					this.setAnimationState("dead1");
				}
			}
			else {
				if (!this.level().isClientSide()) {
					this.setAnimTick(40);
					this.setAnimationState("dead2");
				}
			}
		}
		++this.deathTime;
		if (this.deathTime >= 40 && !this.level().isClientSide() && !this.isRemoved()) {
			this.level().broadcastEntityEvent(this, (byte)60);
			this.remove(RemovalReason.KILLED);
		}
	}
}
