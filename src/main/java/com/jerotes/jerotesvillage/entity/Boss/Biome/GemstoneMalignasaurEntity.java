package com.jerotes.jerotesvillage.entity.Boss.Biome;

import com.jerotes.jerotes.client.sound.BossMusicPlayer;
import com.jerotes.jerotes.config.MainConfig;
import com.jerotes.jerotes.entity.Interface.*;
import com.jerotes.jerotes.event.JerotesBossEvent;
import com.jerotes.jerotes.init.JerotesMobEffects;
import com.jerotes.jerotes.init.JerotesParticleTypes;
import com.jerotes.jerotes.util.AttackFind;
import com.jerotes.jerotes.util.EntityAndItemFind;
import com.jerotes.jerotes.util.Main;
import com.jerotes.jerotesvillage.config.OtherMainConfig;
import com.jerotes.jerotesvillage.entity.Animal.MalialosaurEntity;
import com.jerotes.jerotesvillage.entity.Animal.MalignasaurEntity;
import com.jerotes.jerotesvillage.entity.Part.MalignasaurPart;
import com.jerotes.jerotesvillage.entity.Shoot.Magic.Breath.OceanCurrentBreathEntity;
import com.jerotes.jerotesvillage.entity.Shoot.Magic.MagicShoot.WhirlpoolEntity;
import com.jerotes.jerotesvillage.event.BossBarEvent;
import com.jerotes.jerotesvillage.goal.GemstoneMalignasaurTargetGoal;
import com.jerotes.jerotesvillage.goal.HighNearestAttackableTargetGoal;
import com.jerotes.jerotesvillage.init.JerotesVillageEntityType;
import com.jerotes.jerotesvillage.init.JerotesVillageMobEffects;
import com.jerotes.jerotesvillage.init.JerotesVillageParticleTypes;
import com.jerotes.jerotesvillage.init.JerotesVillageSoundEvents;
import com.jerotes.jerotesvillage.spell.OtherSpellList;
import com.jerotes.jerotesvillage.util.OtherParticlesUse;
import net.minecraft.core.BlockPos;
import net.minecraft.core.registries.Registries;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.chat.Component;
import net.minecraft.network.syncher.EntityDataAccessor;
import net.minecraft.network.syncher.EntityDataSerializers;
import net.minecraft.network.syncher.SynchedEntityData;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.tags.DamageTypeTags;
import net.minecraft.tags.TagKey;
import net.minecraft.util.Mth;
import net.minecraft.world.BossEvent;
import net.minecraft.world.Difficulty;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.damagesource.DamageTypes;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.*;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import net.minecraft.world.entity.ai.attributes.AttributeSupplier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.entity.vehicle.Boat;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.GameRules;
import net.minecraft.world.level.GameType;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.pathfinder.BlockPathTypes;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.Vec3;

import javax.annotation.Nullable;
import java.util.List;
import java.util.Objects;
import java.util.UUID;
import java.util.function.Predicate;

public class GemstoneMalignasaurEntity extends MalignasaurEntity implements BeastToughEntity, LoreUndyingEntity, ShockEntity, SpellUseEntity, BossEntity {
	private static final UUID ARMOR_MODIFIER_UUID = UUID.fromString("cb367b0d-1028-4334-b48c-fc8c63a5d579");
	private static final UUID MOVEMENT_SPEED_MODIFIER_UUID = UUID.fromString("64626148-1221-40de-b867-b91b01b88497");
	private static final UUID HUNGER_ATTACK_DAMAGE_MODIFIER_UUID = UUID.fromString("1efac5b3-4016-4253-ba7b-4d8fe849b2b9");
	private static final UUID HUNGER_MOVEMENT_SPEED_MODIFIER_UUID = UUID.fromString("82ad05ea-fb97-4db1-b3d3-1e4a19655f2c");
	private final JerotesBossEvent bossEvent = new JerotesBossEvent(this, this.getUUID(), BossEvent.BossBarColor.BLUE, false);
	private static final EntityDataAccessor<Integer> BEAST_TOUGH_TICK = SynchedEntityData.defineId(GemstoneMalignasaurEntity.class, EntityDataSerializers.INT);
	private static final EntityDataAccessor<Float> BEAST_TOUGH_DAMAGE = SynchedEntityData.defineId(GemstoneMalignasaurEntity.class, EntityDataSerializers.FLOAT);
	private static final EntityDataAccessor<Boolean> IS_BEAST_TOUGH = SynchedEntityData.defineId(GemstoneMalignasaurEntity.class, EntityDataSerializers.BOOLEAN);
	private static final EntityDataAccessor<Integer> BREAK_BEAST_TOUGH_TICK = SynchedEntityData.defineId(GemstoneMalignasaurEntity.class, EntityDataSerializers.INT);
	private static final EntityDataAccessor<Integer> BREAK_BEAST_TOUGH_COOLDOWN_TICK = SynchedEntityData.defineId(GemstoneMalignasaurEntity.class, EntityDataSerializers.INT);
	private static final EntityDataAccessor<Boolean> IS_BREAK_BEAST_TOUGH = SynchedEntityData.defineId(GemstoneMalignasaurEntity.class, EntityDataSerializers.BOOLEAN);
	private static final EntityDataAccessor<Boolean> GEMSTONE_ARMOR = SynchedEntityData.defineId(GemstoneMalignasaurEntity.class, EntityDataSerializers.BOOLEAN);
	private static final EntityDataAccessor<Integer> HUNGER_OF = SynchedEntityData.defineId(GemstoneMalignasaurEntity.class, EntityDataSerializers.INT);
	private static final EntityDataAccessor<Integer> SPELL_TICK = SynchedEntityData.defineId(GemstoneMalignasaurEntity.class, EntityDataSerializers.INT);
	private static final EntityDataAccessor<Integer> SHOOT_TICK = SynchedEntityData.defineId(GemstoneMalignasaurEntity.class, EntityDataSerializers.INT);
	private static final EntityDataAccessor<Integer> SHOOT_USE_TICK = SynchedEntityData.defineId(GemstoneMalignasaurEntity.class, EntityDataSerializers.INT);
	private static final EntityDataAccessor<Integer> ARMOR_TICK = SynchedEntityData.defineId(GemstoneMalignasaurEntity.class, EntityDataSerializers.INT);
	private static final EntityDataAccessor<Integer> CONTROL_TICK = SynchedEntityData.defineId(GemstoneMalignasaurEntity.class, EntityDataSerializers.INT);
	private static final EntityDataAccessor<Integer> CONTROL_USE_TICK = SynchedEntityData.defineId(GemstoneMalignasaurEntity.class, EntityDataSerializers.INT);
	private static final EntityDataAccessor<Integer> ANGRY_TICK = SynchedEntityData.defineId(GemstoneMalignasaurEntity.class, EntityDataSerializers.INT);
	private static final EntityDataAccessor<Integer> ANGRY_USE_TICK = SynchedEntityData.defineId(GemstoneMalignasaurEntity.class, EntityDataSerializers.INT);
	private static final EntityDataAccessor<Boolean> IS_LEGEND = SynchedEntityData.defineId(GemstoneMalignasaurEntity.class, EntityDataSerializers.BOOLEAN);
	private static final EntityDataAccessor<Integer> WATER_ANIM = SynchedEntityData.defineId(GemstoneMalignasaurEntity.class, EntityDataSerializers.INT);
	public static final Predicate<LivingEntity> HUNT = livingEntity -> {
		return !(livingEntity instanceof MalignasaurEntity) && !(livingEntity instanceof MalialosaurEntity) && !(livingEntity instanceof Player) && (livingEntity.isInWater());
	};

	public GemstoneMalignasaurEntity(EntityType<? extends GemstoneMalignasaurEntity> entityType, Level level) {
		super(entityType, level);
		setMaxUpStep(5.1f);
		this.xpReward = 400;
		this.setPathfindingMalus(BlockPathTypes.LEAVES, 4.0f);
		this.setPathfindingMalus(BlockPathTypes.FENCE, 4.0f);
		this.setPathfindingMalus(BlockPathTypes.BLOCKED, 4.0f);
		this.setPathfindingMalus(BlockPathTypes.DOOR_WOOD_CLOSED, 4.0f);
		this.setPathfindingMalus(BlockPathTypes.DOOR_IRON_CLOSED, 4.0f);
		this.setPathfindingMalus(BlockPathTypes.UNPASSABLE_RAIL, 0.0f);
		this.setPathfindingMalus(BlockPathTypes.WATER, 0.0f);
		if (OtherMainConfig.BossBaseAttributeCanUseConfig) {
			this.getAttribute(Attributes.MAX_HEALTH).setBaseValue(OtherMainConfig.GemstoneMalignasaurMaxHealth);
			this.setHealth(this.getMaxHealth());
			this.getAttribute(Attributes.ATTACK_DAMAGE).setBaseValue(OtherMainConfig.GemstoneMalignasaurMeleeDamage);
			this.getAttribute(Attributes.ARMOR).setBaseValue(OtherMainConfig.GemstoneMalignasaurArmor);
			this.getAttribute(Attributes.MOVEMENT_SPEED).setBaseValue(OtherMainConfig.GemstoneMalignasaurMovementSpeed);
			this.getAttribute(Attributes.ATTACK_KNOCKBACK).setBaseValue(OtherMainConfig.GemstoneMalignasaurAttackKnockback);
			this.getAttribute(Attributes.KNOCKBACK_RESISTANCE).setBaseValue(OtherMainConfig.GemstoneMalignasaurKnockbackResistance);
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
			return(float) OtherMainConfig.GemstoneMalignasaurMagicAttackPercentage;
		}
		return (float) OtherMainConfig.GemstoneMalignasaurAttackPercentage;
	}
	//限伤
	public boolean hasDamageCap() {
		return MainConfig.HasDamageCap.contains(this.getEncodeId()) || OtherMainConfig.BossHasDamageCap.contains(this.getEncodeId()) || this.isLegend();
	}
	public float DamageCap(DamageSource damageSource, Entity entity) {
		return (float) OtherMainConfig.GemstoneMalignasaurDamageCap;
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
		return (float)OtherMainConfig.GemstoneMalignasaurDamageCooldownTick * base;
	}

	@Override
	public int getExperienceReward() {
		return 400;
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
		if (this.start > 0) {
			this.bossEvent.setProgress(health1 / this.getMaxHealth());
			health1 += (this.getHealth() - health1) / 60;
		}
		else{
			this.bossEvent.setProgress(this.getHealth() / this.getMaxHealth());
		}
	}

	public static AttributeSupplier.Builder createAttributes() {
		AttributeSupplier.Builder builder = Mob.createMobAttributes();
		builder = builder.add(Attributes.MAX_HEALTH, 408);
		builder = builder.add(Attributes.FLYING_SPEED, 0.25);
		builder = builder.add(Attributes.ATTACK_DAMAGE, 18);
		builder = builder.add(Attributes.ARMOR, 4);
		builder = builder.add(Attributes.MOVEMENT_SPEED, 1.30);
		builder = builder.add(Attributes.ATTACK_KNOCKBACK, 2.8);
		builder = builder.add(Attributes.KNOCKBACK_RESISTANCE, 1.0);

		builder = builder.add(Attributes.FOLLOW_RANGE, 128);
		return builder;
	}

	@Override
	protected void registerGoals() {
		super.registerGoals();
		this.targetSelector.addGoal(2, new HighNearestAttackableTargetGoal<Player>(this, Player.class, true){
			@Override
			public boolean canUse() {
				if (GemstoneMalignasaurEntity.this.start > 0) {
					return false;
				}
				if (GemstoneMalignasaurEntity.this.level().getDifficulty() == Difficulty.PEACEFUL) {
					return false;
				}
				return super.canUse();
			}
		});
		this.targetSelector.addGoal(3, new GemstoneMalignasaurTargetGoal<>(this, Mob.class, true, HUNT));
	}

	@Override
	protected SoundEvent getAmbientSound() {
		return JerotesVillageSoundEvents.GEMSTONE_MALIGNASAUR_AMBIENT;
	}
	@Override
	protected SoundEvent getDeathSound() {
		return JerotesVillageSoundEvents.GEMSTONE_MALIGNASAUR_DEATH;
	}
	@Override
	protected SoundEvent getHurtSound(DamageSource damageSource) {
		return JerotesVillageSoundEvents.GEMSTONE_MALIGNASAUR_HURT;
	}
	@Override
	protected SoundEvent getSwimSound() {
		return JerotesVillageSoundEvents.GEMSTONE_MALIGNASAUR_SWIM;
	}
	@Override
	protected float getSoundVolume() {
		return 5.0f;
	}
	@Override
	protected void playStepSound(BlockPos blockPos, BlockState blockState) {
		this.playSound(JerotesVillageSoundEvents.GEMSTONE_MALIGNASAUR_WALK, 0.15f, 1.0f);
	}
	@Override
	public int getMaxAirSupply() {
		return 24000;
	}
	@Override
	public boolean isPushedByFluid() {
		return false;
	}
	@Override
	public boolean isPushable() {
		return false;
	}
	@Override
	public void makeStuckInBlock(BlockState blockState, Vec3 vec3) {
		this.resetFallDistance();
	}
	@Override
	public boolean canBeLeashed(Player player) {
		return false;
	}
	@Override
	public boolean removeWhenFarAway(double d) {
		return false;
	}
	@Override
	public boolean isOnFire() {
		if (this.isBeastTough()) {
			return false;
		}
		return super.isOnFire();
	}
	@Nullable
	@Override
	public GemstoneMalignasaurEntity getBreedOffspring(ServerLevel serverLevel, AgeableMob ageableMob) {
		return JerotesVillageEntityType.GEMSTONE_MALIGNASAUR.get().create(serverLevel);
	}
	@Override
	protected float getStandingEyeHeight(Pose pose, EntityDimensions entityDimensions) {
		float height = 1.8f;
		if (this.isBaby()) {
			return height / 2;
		}
		return height;
	}
	@Override
	public boolean isFood(ItemStack itemStack) {
		return itemStack.is(TagKey.create(Registries.ITEM, new ResourceLocation("jerotes", "aa_creative_claw")));
	}
	@Override
	public boolean isBaby() {
		return false;
	}
	public boolean isInRain() {
		BlockPos blockpos = this.blockPosition();
		return this.level().isRainingAt(blockpos) ||
				this.level().isRainingAt(BlockPos.containing((double)blockpos.getX(), this.getBoundingBox().maxY, (double)blockpos.getZ()));
	}
	@Override
	public void travel(Vec3 vec3) {
		if (this.isEffectiveAi() && (this.isInFluidType())) {
			this.moveRelative(this.getSpeed(), vec3);
			this.move(MoverType.SELF, this.getDeltaMovement());
			this.setDeltaMovement(this.getDeltaMovement().scale(0.90));
			if (this.getTarget() == null) {
				this.setDeltaMovement(this.getDeltaMovement().add(0.0, -0.3, 0.0));
			}
		}
		else {
			super.travel(vec3);
		}
	}
	@Override
	public boolean useShock() {
		return this.getHungerOf() < -3000;
	}
	@Override
	protected void doPush(Entity entity) {
		if (!this.onGround() && !this.isNoAi() && !this.isAggressive() && entity instanceof LivingEntity livingEntity  && this.canAttack(livingEntity) && this.canAttackType(livingEntity.getType()) && !this.isAlliedTo(livingEntity) && !(entity instanceof MalignasaurEntity) && !(entity instanceof ServerPlayer player && (player.gameMode.getGameModeForPlayer() == GameType.CREATIVE || player.gameMode.getGameModeForPlayer() == GameType.SPECTATOR)) && !entity.isSpectator()) {
			this.setTarget((LivingEntity)entity);
		}
		super.doPush(entity);
	}
	@Override
	public SoundEvent getBossMusic() {
		return JerotesVillageSoundEvents.GEMSTONE_MALIGNASAUR_MUSIC;
	}

	public int spellLevel = OtherMainConfig.GemstoneMalignasaurSpellNormalLevel;
	@Override
	public int getSpellLevel() {
		return this.spellLevel;
	}
	public int start = -101;
	private float health1 = 1;
	public void setSpellTick(int n){
		this.getEntityData().set(SPELL_TICK, n);
	}
	public int getSpellTick(){
		return this.getEntityData().get(SPELL_TICK);
	}
	public void setShootTick(int n){
		this.getEntityData().set(SHOOT_TICK, n);
	}
	public int getShootTick(){
		return this.getEntityData().get(SHOOT_TICK);
	}
	public void setShootUseTick(int n){
		this.getEntityData().set(SHOOT_USE_TICK, n);
	}
	public int getShootUseTick(){
		return this.getEntityData().get(SHOOT_USE_TICK);
	}
	public void setArmorTick(int n){
		this.getEntityData().set(ARMOR_TICK, n);
	}
	public int getArmorTick(){
		return this.getEntityData().get(ARMOR_TICK);
	}
	public void setControlTick(int n){
		this.getEntityData().set(CONTROL_TICK, n);
	}
	public int getControlTick(){
		return this.getEntityData().get(CONTROL_TICK);
	}
	public void setControlUseTick(int n){
		this.getEntityData().set(CONTROL_USE_TICK, n);
	}
	public int getControlUseTick(){
		return this.getEntityData().get(CONTROL_USE_TICK);
	}
	public void setAngryTick(int n){
		this.getEntityData().set(ANGRY_TICK, n);
	}
	public int getAngryTick(){
		return this.getEntityData().get(ANGRY_TICK);
	}
	public void setAngryUseTick(int n){
		this.getEntityData().set(ANGRY_USE_TICK, n);
	}
	public int getAngryUseTick(){
		return this.getEntityData().get(ANGRY_USE_TICK);
	}
	public void setHungerOf(int n){
		this.getEntityData().set(HUNGER_OF, n);
	}
	public int getHungerOf(){
		return this.getEntityData().get(HUNGER_OF);
	}
	public boolean specialAction() {
		return this.getJumpUseTick() > 0
				|| this.getAngryUseTick() > 0
				|| this.getControlUseTick() > 0
				|| this.getArmorTick() > 0
				|| this.getShootUseTick() > 0;
	}
	public boolean isArmor() {
		return this.getEntityData().get(GEMSTONE_ARMOR);
	}
	public void setArmor(boolean bl) {
		this.getEntityData().set(GEMSTONE_ARMOR, bl);
		Objects.requireNonNull(this.getAttribute(Attributes.ARMOR)).removeModifier(ARMOR_MODIFIER_UUID);
		Objects.requireNonNull(this.getAttribute(Attributes.MOVEMENT_SPEED)).removeModifier(MOVEMENT_SPEED_MODIFIER_UUID);
		if (bl) {
			double d = OtherMainConfig.GemstoneMalignasaurArmorGemstoneArmor - OtherMainConfig.GemstoneMalignasaurArmor;
			double d2 = OtherMainConfig.GemstoneMalignasaurMovementSpeedGemstoneArmor - OtherMainConfig.GemstoneMalignasaurMovementSpeed;
			Objects.requireNonNull(this.getAttribute(Attributes.ARMOR)).addTransientModifier(new AttributeModifier(ARMOR_MODIFIER_UUID, "Armor armor", d, AttributeModifier.Operation.ADDITION));
			Objects.requireNonNull(this.getAttribute(Attributes.MOVEMENT_SPEED)).addTransientModifier(new AttributeModifier(MOVEMENT_SPEED_MODIFIER_UUID, "Armor movement speed", d2, AttributeModifier.Operation.ADDITION));
		}
	}
	@Override
	public void setCustomName(@Nullable Component component) {
		super.setCustomName(component);
		this.bossEvent.setName(this.getDisplayName());
	}
	public void setBeastToughDamage(float f){
		this.getEntityData().set(BEAST_TOUGH_DAMAGE, f);
	}
	public float getBeastToughDamage(){
		return this.getEntityData().get(BEAST_TOUGH_DAMAGE);
	}
	@Override
	public boolean isBeastTough() {
		return this.getEntityData().get(IS_BEAST_TOUGH) && this.isAlive() && !this.isBreakBeastTough();
	}
	public void setBeastTough(boolean bl) {
		this.getEntityData().set(IS_BEAST_TOUGH, bl);
	}
	public void setBeastToughTick(int n){
		this.getEntityData().set(BEAST_TOUGH_TICK, n);
	}
	public int getBeastToughTick(){
		return this.getEntityData().get(BEAST_TOUGH_TICK);
	}
	@Override
	public boolean isBreakBeastTough() {
		return this.getEntityData().get(IS_BREAK_BEAST_TOUGH);
	}
	public void setBreakBeastTough(boolean bl) {
		this.getEntityData().set(IS_BREAK_BEAST_TOUGH, bl);
	}
	public void setBreakBeastToughTick(int n){
		this.getEntityData().set(BREAK_BEAST_TOUGH_TICK, n);
	}
	public int getBreakBeastToughTick(){
		return this.getEntityData().get(BREAK_BEAST_TOUGH_TICK);
	}
	public void setBreakBeastToughCooldownTick(int n){
		this.getEntityData().set(BREAK_BEAST_TOUGH_COOLDOWN_TICK, n);
	}
	public int getBreakBeastToughCooldownTick(){
		return this.getEntityData().get(BREAK_BEAST_TOUGH_COOLDOWN_TICK);
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
	public float getBeastToughBreakHurtCooldownMultiple() {
		return (float) OtherMainConfig.GemstoneMalignasaurBreakToughHurtCooldownMultiple;
	}
	public int getBaseBeastToughTick() {
		return (int) (OtherMainConfig.GemstoneMalignasaurToughHurtCooldown * 20);
	}
	public float getBreakBeastToughNeedBaseDamage() {
		return (int) (OtherMainConfig.GemstoneMalignasaurBreakToughNeedBaseDamage);
	}
	@Override
	public void addAdditionalSaveData(CompoundTag compoundTag) {
		super.addAdditionalSaveData(compoundTag);
		compoundTag.putBoolean("IsLegend", this.isLegend());
		compoundTag.putInt("SpellLevel", this.spellLevel);

		compoundTag.putInt("BeastToughTick", this.getBeastToughTick());
		compoundTag.putFloat("BeastToughDamage", this.getBeastToughDamage());
		compoundTag.putBoolean("IsBeastTough", this.isBeastTough());
		compoundTag.putInt("BreakBeastToughTick", this.getBreakBeastToughTick());
		compoundTag.putInt("BreakBeastToughCooldownTick", this.getBreakBeastToughCooldownTick());
		compoundTag.putBoolean("IsBreakBeastTough", this.isBreakBeastTough());
		
		compoundTag.putBoolean("IsArmor", this.isArmor());
		compoundTag.putInt("SpellTick", this.getSpellTick());
		compoundTag.putInt("ShootTick", this.getShootTick());
		compoundTag.putInt("ShootUseTick", this.getShootUseTick());
		compoundTag.putInt("HungerOf", this.getHungerOf());
		compoundTag.putInt("ArmorTick", this.getArmorTick());
		compoundTag.putInt("ControlTick", this.getControlTick());
		compoundTag.putInt("AngryTick", this.getAngryTick());
		compoundTag.putInt("ControlUseTick", this.getControlUseTick());
		compoundTag.putInt("AngryUseTick", this.getAngryUseTick());
		compoundTag.putInt("Start", this.start);
		compoundTag.putFloat("Health1", this.health1);
		this.addPersistentAngerSaveData(compoundTag);
	}
	@Override
	public void readAdditionalSaveData(CompoundTag compoundTag) {
		super.readAdditionalSaveData(compoundTag);
		if (this.hasCustomName()) {
			this.bossEvent.setName(this.getDisplayName());
		}
		this.setLegend(compoundTag.getBoolean("IsLegend"));
		this.spellLevel = compoundTag.getInt("SpellLevel");

		this.setBeastToughTick(compoundTag.getInt("BeastToughTick"));
		this.setBeastToughDamage(compoundTag.getFloat("BeastToughDamage"));
		this.setBeastTough(compoundTag.getBoolean("IsBeastTough"));
		this.setBreakBeastToughTick(compoundTag.getInt("BreakBeastToughTick"));
		this.setBreakBeastToughCooldownTick(compoundTag.getInt("BreakBeastToughCooldownTick"));
		this.setBreakBeastTough(compoundTag.getBoolean("IsBreakBeastTough"));
		
		if (compoundTag.contains("IsArmor")) {
			this.setArmor(compoundTag.getBoolean("IsArmor"));
		}
		this.setSpellTick(compoundTag.getInt("SpellTick"));
		this.setShootTick(compoundTag.getInt("ShootTick"));
		this.setShootUseTick(compoundTag.getInt("ShootUseTick"));
		this.setHungerOf(compoundTag.getInt("HungerOf"));
		this.setArmorTick(compoundTag.getInt("ArmorTick"));
		this.setControlTick(compoundTag.getInt("ControlTick"));
		this.setAngryTick(compoundTag.getInt("AngryTick"));
		this.setControlUseTick(compoundTag.getInt("ControlUseTick"));
		this.setAngryUseTick(compoundTag.getInt("AngryUseTick"));
		this.start = compoundTag.getInt("Start");
		this.health1 = compoundTag.getFloat("Health1");
		this.readPersistentAngerSaveData(this.level(), compoundTag);
		this.bossEvent.setId(this.getUUID());
	}
	@Override
	protected void defineSynchedData() {
		super.defineSynchedData();
		this.getEntityData().define(GEMSTONE_ARMOR, false);
		this.getEntityData().define(HUNGER_OF, 240);
		this.getEntityData().define(SPELL_TICK, 0);
		this.getEntityData().define(SHOOT_TICK, 0);
		this.getEntityData().define(SHOOT_USE_TICK, 0);
		this.getEntityData().define(ARMOR_TICK, -10);
		this.getEntityData().define(CONTROL_TICK, 0);
		this.getEntityData().define(ANGRY_TICK, 0);
		this.getEntityData().define(CONTROL_USE_TICK, 0);
		this.getEntityData().define(ANGRY_USE_TICK, 0);

		this.getEntityData().define(BEAST_TOUGH_TICK, 0);
		this.getEntityData().define(BEAST_TOUGH_DAMAGE, 0f);
		this.getEntityData().define(IS_BEAST_TOUGH, false);
		this.getEntityData().define(BREAK_BEAST_TOUGH_TICK, 0);
		this.getEntityData().define(BREAK_BEAST_TOUGH_COOLDOWN_TICK, 0);
		this.getEntityData().define(IS_BREAK_BEAST_TOUGH, false);
		
		this.getEntityData().define(WATER_ANIM, 0);
		this.getEntityData().define(IS_LEGEND, false);
	}
	@Override
	public void onSyncedDataUpdated(EntityDataAccessor<?> entityDataAccessor) {

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
		//技能-强韧
		if (!level().isClientSide()) {
			this.setBeastToughTick(Math.max(0, this.getBeastToughTick() - 1));
			this.setBreakBeastToughTick(Math.max(0, this.getBreakBeastToughTick() - 1));
			this.setBreakBeastToughCooldownTick(Math.max(0, this.getBreakBeastToughCooldownTick() - 1));
			this.setBeastTough(this.getBeastToughTick() > 0);
			this.setBreakBeastTough(this.getBreakBeastToughTick() > 0);
		}
		if (this.isBeastTough() && this.getRemainingFireTicks() > 0) {
			this.clearFire();
		}
	}

	@Override
	public void aiStep() {
		super.aiStep();
		this.bossEvent.update();
		if (!this.level().isClientSide()) {
			this.updatePersistentAnger((ServerLevel)this.level(), true);
		}
//		//晴雨切换
//		if (this.isInRain() && !this.isInWater() && !this.isRainNavigatorType) {
//			this.moveControl = new FlyingMoveControl(this, 10, true);
//			this.navigation = new FlyingPathNavigation(this, level());
//			this.isRainNavigatorType = true;
//		}
//		if ((!this.isInRain() || this.isInWater()) && this.isRainNavigatorType) {
//			this.moveControl = new GemstoneMalignasaurMoveControl(this, 85, 85, 0.02f, 0.15f, false);
//			this.navigation = new GroundPathNavigation(this, level());
//			this.isRainNavigatorType = false;
//		}

//		if (this.isInRain() && !this.isInWater()) {
//			if (this.level().isClientSide && this.tickCount % 3 == 0) {
//				this.level().addParticle(JerotesVillageParticleTypes.OCEAN_CURRENT_BREATH.get(),
//						this.getRandomX(0.8), this.getY(), this.getRandomZ(0.8),
//						0.0, -0.1f, 0.0);
//				this.level().addParticle(JerotesVillageParticleTypes.OCEAN_CURRENT_BREATH_FOG.get(),
//						this.getRandomX(0.8), this.getY(), this.getRandomZ(0.8),
//						0.0, -0.1f, 0.0);
//				if (this.head1 != null) {
//					this.level().addParticle(JerotesVillageParticleTypes.OCEAN_CURRENT_BREATH.get(),
//							head1.getRandomX(0.8), head1.getY(), head1.getRandomZ(0.8),
//							0.0, -0.1f, 0.0);
//					this.level().addParticle(JerotesVillageParticleTypes.OCEAN_CURRENT_BREATH_FOG.get(),
//							head1.getRandomX(0.8), head1.getY(), head1.getRandomZ(0.8),
//							0.0, -0.1f, 0.0);
//				}
//				if (this.tail1 != null) {
//					this.level().addParticle(JerotesVillageParticleTypes.OCEAN_CURRENT_BREATH.get(),
//							tail1.getRandomX(1.5), tail1.getY(), tail1.getRandomZ(1.5),
//							0.0, -0.1f, 0.0);
//					this.level().addParticle(JerotesVillageParticleTypes.OCEAN_CURRENT_BREATH_FOG.get(),
//							tail1.getRandomX(1.5), tail1.getY(), tail1.getRandomZ(1.5),
//							0.0, -0.1f, 0.0);
//				}
//			}
//		}
		//生命恢复
		{
			if (this.isInWaterOrRain() || this.isInWaterOrBubble()) {
				if (this.getRandom().nextInt(240) == 1 && this.isAlive()) {
					this.heal(3.0f);
				}
			}
			if (this.getRandom().nextInt(60) == 1 && this.isAlive()) {
				this.heal(3.0f);
			}
		}
		//出现召唤
		{
			if (this.start > 0) {
				this.start -= 1;
			} else if (this.start == 0) {
				if (!this.isSilent()) {
					this.playSound(JerotesVillageSoundEvents.GEMSTONE_MALIGNASAUR_AMBIENT, 10.0f, 1.0f / (this.getRandom().nextFloat() * 0.4f + 0.8f));
				}
				if (!this.level().isClientSide) {
					this.addEffect(new MobEffectInstance(MobEffects.GLOWING, 660, 1));
				}
				this.start = -1;
			} else if (this.start > -100) {
				this.start -= 1;
			}
			if (this.start <= -100) {
				this.start = -101;
			}
		}
		//饥饿猎杀
		{
			if (this.getHungerOf() > 0 || this.getHungerOf() > -20000) {
				if (!this.level().isClientSide()) {
					this.setHungerOf(this.getHungerOf() - 1);
				}
			}
			if (this.tickCount % 120 == 0) {
				Objects.requireNonNull(this.getAttribute(Attributes.ATTACK_DAMAGE)).removeModifier(HUNGER_ATTACK_DAMAGE_MODIFIER_UUID);
				Objects.requireNonNull(this.getAttribute(Attributes.MOVEMENT_SPEED)).removeModifier(HUNGER_MOVEMENT_SPEED_MODIFIER_UUID);
				if (this.getHungerOf() < 0) {
					//巨兽震撼
					if (this.isAlive() && !this.isNoAi() && this.tickCount % 120 == 0) {
						//给予buff
						this.addAback();
					}
					double d;
					double d2;
					//饥饿5
					if (this.getHungerOf() < -10000) {
						if (!this.level().isClientSide) {
							this.addEffect(new MobEffectInstance(MobEffects.HUNGER, 80, 4), this);
						}
						d = this.getAttributeBaseValue(Attributes.ATTACK_DAMAGE) * 1.25;
						d2 = this.getAttributeBaseValue(Attributes.MOVEMENT_SPEED) / 2;
					}
					//饥饿4
					else if (this.getHungerOf() < -6000) {
						if (!this.level().isClientSide) {
							this.addEffect(new MobEffectInstance(MobEffects.HUNGER, 80, 3), this);
						}
						d = this.getAttributeBaseValue(Attributes.ATTACK_DAMAGE);
						d2 = this.getAttributeBaseValue(Attributes.MOVEMENT_SPEED) / 3;
					}
					//饥饿3
					else if (this.getHungerOf() < -3000) {
						if (!this.level().isClientSide) {
							this.addEffect(new MobEffectInstance(MobEffects.HUNGER, 80, 2), this);
						}
						d = this.getAttributeBaseValue(Attributes.ATTACK_DAMAGE) / 3;
						d2 = this.getAttributeBaseValue(Attributes.MOVEMENT_SPEED) / 4;
					}
					//饥饿2
					else if (this.getHungerOf() < -1000) {
						if (!this.level().isClientSide) {
							this.addEffect(new MobEffectInstance(MobEffects.HUNGER, 80, 1), this);
						}
						d = this.getAttributeBaseValue(Attributes.ATTACK_DAMAGE) / 6;
						d2 = this.getAttributeBaseValue(Attributes.MOVEMENT_SPEED) / 5;
					}
					//饥饿1
					else {
						if (!this.level().isClientSide) {
							this.addEffect(new MobEffectInstance(MobEffects.HUNGER, 80, 0), this);
						}
						d = this.getAttributeBaseValue(Attributes.ATTACK_DAMAGE) / 10;
						d2 = this.getAttributeBaseValue(Attributes.MOVEMENT_SPEED) / 6;
					}
					Objects.requireNonNull(this.getAttribute(Attributes.ATTACK_DAMAGE)).addTransientModifier(new AttributeModifier(HUNGER_ATTACK_DAMAGE_MODIFIER_UUID, "Armor armor", d, AttributeModifier.Operation.ADDITION));
					Objects.requireNonNull(this.getAttribute(Attributes.MOVEMENT_SPEED)).addTransientModifier(new AttributeModifier(HUNGER_MOVEMENT_SPEED_MODIFIER_UUID, "Armor movement speed", d2, AttributeModifier.Operation.ADDITION));
				}
			}
		}
		//气泡
		{
			if (this.isInWaterOrRain() && this.isAlive()) {
				for (int i = 0; i < 2; ++i) {
					if (this.level().isClientSide()) {
						this.level().addParticle(JerotesVillageParticleTypes.GEMSTONE_BUBBLE.get(), this.getRandomX(0.8), this.getRandomY(), this.getRandomZ(0.8), 0.0, 0.0, 0.0);
						this.level().addParticle(JerotesVillageParticleTypes.GEMSTONE_BUBBLE_LAND.get(), this.getRandomX(0.8), this.getRandomY(), this.getRandomZ(0.8), 0.0, 0.0, 0.0);
					}
				}
			}
		}
		//宝石鳞甲
		{
			boolean health = this.getHealth() < this.getMaxHealth() / 2;
			if (health && this.getArmorTick() <= 0 && !this.isArmor() && this.isAlive()) {
				if (!this.isSilent()) {
					this.level().playSound(null, this.getX(), this.getY(), this.getZ(), JerotesVillageSoundEvents.GEMSTONE_MALIGNASAUR_ARMOR, this.getSoundSource(), 10.0f, 0.8f + this.random.nextFloat() * 0.4f);
				}
				if (!this.level().isRaining() && !this.level().isThundering() && this.level().getGameRules().getBoolean(GameRules.RULE_WEATHER_CYCLE)) {
					this.level().getLevelData().setRaining(true);
				}
				if (!this.level().isClientSide()) {
					this.setArmor(true);
					this.setArmorTick(20);
					this.setAnimTick(20);
					this.setAnimationState("armor");
				}
				if (this.level() instanceof ServerLevel serverLevel) {
					//技能-宝石鳞甲
					if (!this.isInvisible()) {
						serverLevel.sendParticles(JerotesVillageParticleTypes.GEMSTONE_SCALE_ARMOR_DISPLAY.get(), this.getX(), this.getBoundingBox().maxY + 0.5, this.getZ(), 0, 0.0, 0.0, 0.0, 0);
					}
				}
			}
			if (!health && this.getArmorTick() <= 0 && this.isArmor() && this.isAlive() && this.getTarget() == null) {
				if (!this.level().isClientSide()) {
					this.setArmor(false);
				}
			}
			if (this.getArmorTick() > 0 && this.isAlive()) {
				OtherParticlesUse.WaterSpiral(this);
			}
			if (!this.level().isClientSide()) {
				this.setArmorTick(Math.max(0, this.getArmorTick() - 1));
			}
		}
		//破船
		{
			if (this.tickCount % 60 == 0 && this.isAlive()) {
				List<Boat> lists = this.level().getEntitiesOfClass(Boat.class, this.getBoundingBox().inflate(15, 15, 15));
				for (Boat boat : lists) {
					if (this.isAlliedTo(boat) || this.distanceTo(boat) > 16.0)
						continue;
					this.spawnAtLocation(boat.getDropItem());
					boat.discard();
				}
			}
		}
		//技能
		{
			int spellLevel = this.getSpellLevel() - 1;
			if (this.level().getDifficulty() == Difficulty.NORMAL) {
				spellLevel = this.getSpellLevel();
			} else if (this.level().getDifficulty() == Difficulty.HARD) {
				spellLevel = this.getSpellLevel() + 1;
			}
			//吐息
			{
				if (this.getShootTick() >= (int)(OtherMainConfig.GemstoneMalignasaurBreathCooldown * 20) && spellNeed(0, 24, 1, true)) {
					if (this.level().getRandom().nextFloat() > 0.5f) {
						//技能-洋流吐息
						if (!this.level().isClientSide()) {
							this.setShootTick(0);
							this.setShootUseTick(20);
							this.setAnimTick(15);
							this.setSpellTick(30);
							this.setAnimationState("breath");
						}
					}
					else {
						//技能-漩涡
						if (!this.level().isClientSide()) {
							this.setShootTick(0);
							this.setShootUseTick(20);
							this.setAnimTick(20);
							this.setSpellTick(30);
							this.setAnimationState("whirlpool");
						}
					}
				}
				if (this.getShootUseTick() == 10 && this.isAlive()) {
					LivingEntity livingEntity = this;
					if (this.getTarget() != null) {
						livingEntity = this.getTarget();
					}
					if (this.getEntityData().get(ANIM_STATE) == this.getAnimationState("breath")) {
						//法术列表-洋流吐息
						OtherSpellList.OceanCurrentBreath(spellLevel, this, livingEntity).spellUse(this.getTarget() instanceof Player ? 1 : Main.randomReach(this.getRandom(), 0, 1));
					} else {
						//法术列表-漩涡
						OtherSpellList.Whirlpool(spellLevel, this, livingEntity).spellUse();
					}
				}
			}
			//窒息控制
			{
				if (this.getControlTick() >= (int)(OtherMainConfig.GemstoneMalignasaurControlCooldown * 20) && spellNeed(0, 48, 1, false)) {
					if (!this.level().isClientSide()) {
						this.setControlTick(0);
						this.setControlUseTick(18);
						this.setAnimTick(20);
						this.setSpellTick(25);
						this.setAnimationState("control");
					}
					if (!this.isSilent()) {
						this.level().playSound(null, this.getX(), this.getY(), this.getZ(), JerotesVillageSoundEvents.GEMSTONE_MALIGNASAUR_CONTROL, this.getSoundSource(), 10.0f, 0.8f + this.random.nextFloat() * 0.4f);
					}
				}
				if (this.getControlUseTick() == 10 && this.isAlive()) {
					//法术列表-窒息控制
					OtherSpellList.AsphyxiaControl(spellLevel, this, this).spellUse();
				}
			}
			//海龙威慑&恶鳞炸弹
			{
				if (this.getAngryTick() >= (int)(OtherMainConfig.GemstoneMalignasaurAngrySpellCooldown * 20) && spellNeed(0, 24, 1, true)) {
					//海龙威慑
					if (this.level().getRandom().nextFloat() > 0.5f) {
						if (!this.level().isClientSide()) {
							this.setAngryTick(0);
							this.setAngryUseTick(20);
							this.setAnimTick(40);
							this.setSpellTick(50);
							this.setAnimationState("deter");
						}
						if (this.level() instanceof ServerLevel serverLevel && this.head1 != null) {
							serverLevel.sendParticles(JerotesVillageParticleTypes.OCEAN_DRAGON_DETERRENCE.get(), this.head1.getRandomX(0.2), this.head1.getRandomY() + 3, this.head1.getRandomZ(0.2), 4, 0.0, 0.0, 0.0, 0.0);
						}
						if (!this.isSilent()) {
							this.level().playSound(null, this.getX(), this.getY(), this.getZ(), JerotesVillageSoundEvents.GEMSTONE_MALIGNASAUR_DETER, this.getSoundSource(), 10.0f, 0.8f + this.random.nextFloat() * 0.4f);
						}
					}
					//恶鳞炸弹
					else {
						if (!this.level().isClientSide()) {
							this.setAngryTick(0);
							this.setAngryUseTick(20);
							this.setAnimTick(20);
							this.setSpellTick(25);
							this.setAnimationState("bomb");
						}
					}
				}
				if (this.getAngryUseTick() == 15 && this.isAlive()) {
					if (this.getEntityData().get(ANIM_STATE) == this.getAnimationState("bomb")) {
						//法术列表-恶鳞炸弹
						OtherSpellList.MalignasaurBomb(spellLevel, this, this).spellUse();
					} else {
						//法术列表-窒息控制
						OtherSpellList.OceanDragonDeterrence(spellLevel, this, this).spellUse();
					}
				}
			}
			if (!this.level().isClientSide()) {
				this.setSpellTick(Math.max(0, this.getSpellTick() - 1));
				this.setShootTick(Math.min((int)(OtherMainConfig.GemstoneMalignasaurBreathCooldown * 20), this.getShootTick() + 1));
				this.setShootUseTick(Math.max(0, this.getShootUseTick() - 1));
				this.setControlTick(Math.min((int)(OtherMainConfig.GemstoneMalignasaurControlCooldown * 20), this.getControlTick() + 1));
				this.setControlUseTick(Math.max(0, this.getControlUseTick() - 1));
				this.setAngryTick(Math.min((int)(OtherMainConfig.GemstoneMalignasaurAngrySpellCooldown * 20), this.getAngryTick() + 1));
				this.setAngryUseTick(Math.max(0, this.getAngryUseTick() - 1));
			}
		}
	}


	public boolean addAback() {
		List<LivingEntity> list = this.level().getEntitiesOfClass(LivingEntity.class, this.getBoundingBox().inflate(32.0, 32.0, 32.0));
		double d = Double.MAX_VALUE;
		for (LivingEntity shock : list) {
			if (shock == null) continue;
			if (shock == this) continue;
			if ((this.distanceToSqr(shock)) > d) continue;
			if (AttackFind.FindCanNotAttack(this, shock)) continue;
			//震撼免疫
			if (EntityAndItemFind.isAbackAwayImmune(shock.getType())) continue;
			if (shock.getHealth() > this.getHealth() / 1.5f) continue;
			if (shock.hasEffect(JerotesVillageMobEffects.ABUNDANT_COURAGE.get())
					|| shock.hasEffect(JerotesVillageMobEffects.RAMPANT.get())) continue;
			if (!shock.level().isClientSide) {
				shock.addEffect(new MobEffectInstance(JerotesMobEffects.ABACK.get(), 240, 0), this);
				shock.addEffect(new MobEffectInstance(MobEffects.MOVEMENT_SLOWDOWN, 240, 0), this);
			}
		}
		if (this.level().isClientSide) {
			for (int i = 0; i < 24; ++i) {
				this.level().addParticle(JerotesVillageParticleTypes.ABACK.get(), this.getRandomX(0.5), this.getRandomY() + this.getBbHeight()/4, this.getRandomZ(0.5), 0.0, 0.02, 0.0);
			}
		}
		return true;
	}



	public boolean trueHurt(MalignasaurPart[] area) {
		if (!this.isSilent()) {
			if (this.getAttackUse() == 1) {
				this.level().playSound(null, this.getX(), this.getY(), this.getZ(), JerotesVillageSoundEvents.GEMSTONE_MALIGNASAUR_HEAD, this.getSoundSource(), 10.0f, 0.8f + this.random.nextFloat() * 0.4f);
			}
			else if (this.getAttackUse() == 2) {
				this.level().playSound(null, this.getX(), this.getY(), this.getZ(), JerotesVillageSoundEvents.GEMSTONE_MALIGNASAUR_ATTACK, this.getSoundSource(), 10.0f, 0.8f + this.random.nextFloat() * 0.4f);
			}
			else if (this.getAttackUse() == 3) {
				this.level().playSound(null, this.getX(), this.getY(), this.getZ(), JerotesVillageSoundEvents.GEMSTONE_MALIGNASAUR_TAIL, this.getSoundSource(), 10.0f, 0.8f + this.random.nextFloat() * 0.4f);
			}
			else if (this.getAttackUse() == 4) {
				this.level().playSound(null, this.getX(), this.getY(), this.getZ(), JerotesVillageSoundEvents.GEMSTONE_MALIGNASAUR_ATTACK, this.getSoundSource(), 10.0f, 0.8f + this.random.nextFloat() * 0.4f);
			}
		}
		int levels = 1;
		int levelsJump = OtherMainConfig.GemstoneMalignasaurJumpAttackLevel - 1;
		if (this.level().getDifficulty() == Difficulty.NORMAL) {
			levels = 2;
			levelsJump = OtherMainConfig.GemstoneMalignasaurJumpAttackLevel;
		} else if (this.level().getDifficulty() == Difficulty.HARD) {
			levels = 3;
			levelsJump = OtherMainConfig.GemstoneMalignasaurJumpAttackLevel + 1;
		}
		List<LivingEntity> list = this.level().getEntitiesOfClass(LivingEntity.class, getAttackBoundingBox().inflate(0.75, 0.75, 0.75));
		for (MalignasaurPart part : area) {
			List<LivingEntity> add = this.level().getEntitiesOfClass(LivingEntity.class, part.getBoundingBox().inflate(1.35, 1.35, 1.35));
			list.addAll(add);
		}
		for (LivingEntity hurt : list) {
			if (hurt == null) continue;
			if (AttackFind.FindCanNotAttack(this, hurt)) continue;
			if (!this.hasLineOfSight(hurt)) continue;
			if (hurt instanceof MalignasaurEntity malignasaur && malignasaur.getTarget() != this && this.getTarget() != malignasaur)
				continue;
			if (hurt instanceof MalialosaurEntity malialosaurEntity && malialosaurEntity.getTarget() != this && this.getTarget() != malialosaurEntity)
				continue;
			//视线
//			if (!Main.canSee(hurt, this) && this.getAttackUse() == 1) continue;
//			if (Main.canSee(hurt, this) && this.getAttackUse() == 3) continue;
			double d = 0;
			if (hurt.getAttribute(Attributes.KNOCKBACK_RESISTANCE) != null) {
				d = hurt.getAttributeValue(Attributes.KNOCKBACK_RESISTANCE);
			}
			double d3 = Math.max(0.3, 1.3 - d);
			AttackFind.attackBegin(this, hurt);
			boolean bl2 = false;
			if (this.getAttackUse() == 1) {
				bl2 = AttackFind.attackAfter(this, hurt, 1f, 0.5f, false, 0f);
			}
			else if (this.getAttackUse() == 2) {
				bl2 = AttackFind.attackAfter(this, hurt, 1f, 1.5f, false, 0f);
			}
			else if (this.getAttackUse() == 3) {
				bl2 = AttackFind.attackAfter(this, hurt, 1f, 1.0f, false, 0f);
			}
			else if (this.getAttackUse() == 4) {
				bl2 = AttackFind.attackAfter(this, hurt, 0f, 0.5f, true, levels * 8);
			}
			if (bl2) {
				//流血
				if (this.getAttackUse() == 1) {
					if (!hurt.level().isClientSide) {
						hurt.addEffect(new MobEffectInstance(JerotesMobEffects.BLEEDING.get(), 60, 0, false, false), this);
					}
				}
				//扫尾
				if (this.getAttackUse() == 3) {
					if ((Main.mobSizeSmall(hurt) || Main.mobSizeMedium(hurt) || Main.mobSizeLarge(hurt)) && !EntityAndItemFind.isNoSpecialKnockback(hurt.getType())) {
						hurt.setDeltaMovement(hurt.getDeltaMovement().add(-(this.getX() - hurt.getX()) * 0.2 * d3, -(this.getY() - hurt.getY()) * 0.2 * d3, -(this.getZ() - hurt.getZ()) * 0.2 * d3));
					}
					hurt.knockback(0.8, Mth.sin(this.getYRot() * 0.08f), -Mth.cos(this.getYRot() * 0.08f));
				}
				//跃击
				if (this.getJumpUseTick() > 0) {
					if (!hurt.level().isClientSide) {
						hurt.addEffect(new MobEffectInstance(MobEffects.WEAKNESS, 60, levelsJump - 1), this);
						hurt.addEffect(new MobEffectInstance(MobEffects.MOVEMENT_SLOWDOWN, 60, levelsJump - 1), this);
					}
					if (!this.level().isClientSide()) {
						this.setJumpUseTick(0);
					}
				}
			}
		}
		//震动
		if (this.getAttackUse() == 3 || this.getAttackUse() == 4) {
			List<LivingEntity> listShake = this.level().getEntitiesOfClass(LivingEntity.class, this.getBoundingBox().inflate(16.0, 16.0, 16.0));
			for (LivingEntity shake : listShake) {
				if (shake == null || shake == this || (this.distanceToSqr(shake)) > Double.MAX_VALUE)
					continue;
				if (!shake.level().isClientSide()) {
					shake.addEffect(new MobEffectInstance(JerotesMobEffects.QUAKE.get(), 20, 0, false, false), this);
				}
			}
		}
		//横扫效果
		Main.sweepAttack(this);

		if (this.level() instanceof ServerLevel serverLevel && (this.getAttackUse() == 3 || this.getAttackUse() == 4)) {
			if (tail1 != null) {
				AABB aabb = AABB.ofSize(new Vec3(tail1.getX(), tail1.getY() - 1, tail1.getZ()), Main.mobWidth(tail1)/2 + 2, 0, Main.mobWidth(tail1)/2 + 2);
				for (BlockPos blockPos : BlockPos.betweenClosed(Mth.floor(aabb.minX), Mth.floor(aabb.minY), Mth.floor(aabb.minZ), Mth.floor(aabb.maxX), Mth.floor(aabb.maxY), Mth.floor(aabb.maxZ))) {
					Main.spawnFallingBlockByPos(serverLevel, blockPos);
				}
			}
		}
		return true;
	}

	public boolean doHurtTargetHead(LivingEntity entity) {
		if (this.getAttackTick() > -(int)(OtherMainConfig.GemstoneMalignasaurMeleeAttackCooldown * 20) || this.specialAction()) {
			return false;
		}
		if (!this.level().isClientSide()) {
			this.setAttackTick(20);
			this.setAnimTick(20);
			this.setAttackUse(1);
			int attackRandom = this.getRandom().nextInt(30);
			if (attackRandom > 20) {
				this.setAnimationState("headAttack1");
			} else if (attackRandom < 10) {
				this.setAnimationState("headAttack2");
			} else {
				this.setAnimationState("headAttack3");
			}
		}
		return true;
	}
	@Override
	public boolean doHurtTarget(Entity entity) {
		if (this.isTrueManuallyControlCombatJerotes()) {
			return false;
		}
		if (this.getAttackTick() > -(int)(OtherMainConfig.GemstoneMalignasaurMeleeAttackCooldown * 20) || this.specialAction()) {
			return false;
		}
		if (!this.level().isClientSide()) {
			this.setAttackTick(20);
			this.setAnimTick(20);
			this.setAttackUse(2);
			int attackRandom = this.getRandom().nextInt(30);
			if (attackRandom > 20) {
				this.setAnimationState("attack1");
			} else if (attackRandom < 10) {
				this.setAnimationState("attack2");
			} else {
				this.setAnimationState("attack3");
			}
		}
		return true;
	}
	public boolean doHurtTargetTail(LivingEntity entity) {
		if (this.getAttackTick() > -(int)(OtherMainConfig.GemstoneMalignasaurMeleeAttackCooldown * 20) || this.specialAction()) {
			return false;
		}
		if (!this.level().isClientSide()) {
			this.setAttackTick(20);
			this.setAnimTick(20);
			this.setAttackUse(3);
			int attackRandom = this.getRandom().nextInt(30);
			if (attackRandom > 20) {
				this.setAnimationState("tailAttack1");
			} else if (attackRandom < 10) {
				this.setAnimationState("tailAttack2");
			} else {
				this.setAnimationState("tailAttack3");
			}
		}
		return true;
	}

	@Override
	public void awardKillScore(Entity entity, int score, DamageSource damageSource) {
		super.awardKillScore(entity, score, damageSource);
		if (entity instanceof LivingEntity livingEntity){
			if (!this.level().isClientSide()) {
				this.setHungerOf(this.getHungerOf() + (int) (livingEntity.getMaxHealth() * 40));
			}
		}
	}

	@Override
	public boolean isInvulnerableTo(DamageSource damageSource) {
		if (this.start > -100){
			return true;
		}
		if (AttackFind.MagicResistance(damageSource) && (damageSource.getEntity() == this
				|| (damageSource.getDirectEntity() instanceof OceanCurrentBreathEntity ocean && ocean.getOwner() == this)
				|| (damageSource.getDirectEntity() instanceof WhirlpoolEntity whirlpool && whirlpool.getOwner() == this)
				|| (damageSource.getDirectEntity() instanceof AreaEffectCloud area && area.getOwner() == this)))
			return true;
		if (damageSource.is(DamageTypeTags.IS_FALL)
				|| AttackFind.isWaterDamage(damageSource)
				|| damageSource.is(DamageTypes.DRY_OUT)
				|| damageSource.is(DamageTypes.CRAMMING)
				|| damageSource.is(DamageTypes.CACTUS)
				|| damageSource.is(DamageTypes.STARVE)
				|| damageSource.is(DamageTypes.THORNS)
				|| damageSource.is(DamageTypes.SWEET_BERRY_BUSH)
				|| damageSource.is(DamageTypes.HOT_FLOOR)
		)
			return true;
		if (!this.isArmor()) {
			if (this.getArmorTick() > 0 && !damageSource.is(DamageTypeTags.BYPASSES_INVULNERABILITY)) {
				return true;
			}
			if (damageSource.is(DamageTypes.CRAMMING)
			|| damageSource.is(DamageTypes.IN_WALL)
			|| damageSource.is(DamageTypes.THORNS))
				return true;
		}
		return super.isInvulnerableTo(damageSource);
	}
	@Override
	public boolean hurt(DamageSource damageSource, float amount) {
		if (isInvulnerableTo(damageSource)) {
			return super.hurt(damageSource, amount);
		}
		if (amount <= 4) {
			return false;
		}
		float amountTrue = amount;
		double amountArmor = amountTrue;

		if (this.getHungerOf() < -10000) {
			amountArmor = amountTrue/3;
		}
		//饥饿4
		else if (this.getHungerOf() < -6000) {
			amountArmor = amountTrue/2;
		}
		//饥饿3
		else if (this.getHungerOf() < -3000) {
			amountArmor = amountTrue/1.5f;
		}
		//饥饿2
		else if (this.getHungerOf() < -1000) {
			amountArmor = amountTrue/1.25f;
		}
		//饥饿1
		else if (this.getHungerOf() < 0) {
			amountArmor = amountTrue/1.05f;
		}

		if (!this.isArmor()) {
			if (this.getArmorTick() > 0 && !damageSource.is(DamageTypeTags.BYPASSES_INVULNERABILITY)) {
				return false;
			}
			if (this.getHealth() - amountArmor < this.getMaxHealth()/3 && !damageSource.is(DamageTypeTags.BYPASSES_INVULNERABILITY) && amountArmor <= OtherMainConfig.GemstoneMalignasaurBreakFakeDamageCap * 10) {
				if (this.getHealth() - this.getMaxHealth()/3 < amountArmor) {
					amountArmor = this.getHealth() - this.getMaxHealth()/3;
				}
			}
		}
		if (this.isArmor()) {
			if (damageSource.is(DamageTypes.MAGIC))
				amountArmor = amountTrue / 2;
			if (amountArmor <= this.getMaxHealth() * 3 && this.getHealth() > this.getMaxHealth()/10 && this.getHealth() - amountArmor < this.getMaxHealth()/15) {
				float amount1 = this.getHealth() - this.getMaxHealth()/15;
				 if (amount1 < amountArmor) {
					 amountArmor = amount1;
				 }
			}
			if (!damageSource.is(DamageTypeTags.BYPASSES_INVULNERABILITY) && amountArmor > OtherMainConfig.GemstoneMalignasaurFakeDamageCap && amountArmor <= OtherMainConfig.GemstoneMalignasaurBreakFakeDamageCap) {
				amountArmor = OtherMainConfig.GemstoneMalignasaurFakeDamageCap;
			}
		}
		//强韧期间
		if (this.isBeastTough() && !this.isBreakBeastTough()) {
			//破除强韧
			if (this.getBeastToughDamage() > this.getBreakBeastToughNeedBaseDamage() && this.getBreakBeastToughCooldownTick() <= 0 && amountArmor > this.getBeastToughDamage() * this.getBeastToughBreakHurtCooldownMultiple() && damageSource.getEntity() != null && EntityAndItemFind.isIgnoreSpecialHurtCooldown(damageSource.getEntity().getType())) {
				if (!this.level().isClientSide()) {
					this.setBreakBeastToughTick(this.getBaseBreakBeastToughTick());
					this.setBreakBeastToughCooldownTick(this.getBaseBreakBeastToughCooldownTick());
					if (!this.level().isClientSide()) {
						this.addEffect(new MobEffectInstance(JerotesMobEffects.PARALYZED.get(), this.getBaseBreakBeastToughHoldTick(), 0, false, false));
					}
					if (this.level() instanceof ServerLevel serverLevel) {
						if (!this.isInvisible()) {
							serverLevel.sendParticles(JerotesParticleTypes.BEAST_TOUGH_BREAK.get(), this.getX(), this.getBoundingBox().maxY + 0.5, this.getZ(), 0, 0.0, 0.0, 0.0, 0);
						}
					}
				}
			}
			//强韧抵挡
			else if (!damageSource.is(DamageTypeTags.BYPASSES_INVULNERABILITY) && amountArmor <= this.getBeastToughDamage() * this.getBeastToughBreakHurtCooldownMultiple()) {
				if (this.level() instanceof ServerLevel serverLevel) {
					if (!this.isInvisible()) {
						serverLevel.sendParticles(JerotesParticleTypes.BEAST_TOUGH_DISPLAY.get(), this.getX(), this.getBoundingBox().maxY + 0.5, this.getZ(), 0, 0.0, 0.0, 0.0, 0);
					}
				}
				return false;
			}
			//其他情况无视强韧
		}
		//其他情况设置强韧上限
		if (!this.level().isClientSide()) {
			this.setBeastToughDamage((float) amountArmor);
		}
		//设置强韧间隔
		double toughBase = this.getBaseBeastToughTick();
		float toughMultiple = 0.5f;
		if (this.level().getDifficulty() == Difficulty.NORMAL) {
			toughMultiple = 1f;
		} else if (this.level().getDifficulty() == Difficulty.HARD) {
			toughMultiple = 1.5f;
		}
		if (!this.level().isClientSide()) {
			this.setBeastToughTick((int) (toughBase * toughMultiple));
		}
		return super.hurt(damageSource, (float) amountArmor);
	}

	@Override
	public boolean canBeAffected(MobEffectInstance mobEffectInstance) {
		if (mobEffectInstance.getEffect() == JerotesMobEffects.ABACK.get()) {
			return false;
		}
		if (mobEffectInstance.getEffect() == MobEffects.LEVITATION) {
			return false;
		}
		if (mobEffectInstance.getEffect() == JerotesVillageMobEffects.MALIGNASAUR_ASPHYXIA.get()) {
			return false;
		}
		if (mobEffectInstance.getEffect() == JerotesVillageMobEffects.OCEAN_DRAGON_DETERRENCE.get()) {
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
		this.jumpAnimationState.stop();
		this.armorAnimationState.stop();
		this.controlAnimationState.stop();
		if (this.deathTime >= 40 && !this.level().isClientSide() && !this.isRemoved()) {
			this.remove(RemovalReason.CHANGED_DIMENSION);
		}
	}
}

