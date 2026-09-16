package com.jerotes.jerotesvillage.entity.Boss.Biome;

import com.jerotes.jerotes.client.sound.BossMusicPlayer;
import com.jerotes.jerotes.config.MainConfig;
import com.jerotes.jerotes.entity.Interface.BossEntity;
import com.jerotes.jerotes.entity.Interface.FireAbsorptionEntity;
import com.jerotes.jerotes.entity.Interface.LoreUndyingEntity;
import com.jerotes.jerotes.entity.Interface.SpellUseEntity;
import com.jerotes.jerotes.event.JerotesBossEvent;
import com.jerotes.jerotes.init.JerotesDamageTypes;
import com.jerotes.jerotes.init.JerotesGameRules;
import com.jerotes.jerotes.init.JerotesMobEffects;
import com.jerotes.jerotes.item.Interface.ItemSpecialEffect;
import com.jerotes.jerotes.item.Interface.ItemTwoHanded;
import com.jerotes.jerotes.util.AttackFind;
import com.jerotes.jerotes.util.EntityAndItemFind;
import com.jerotes.jerotes.util.Main;
import com.jerotes.jerotesvillage.JerotesVillage;
import com.jerotes.jerotesvillage.config.OtherMainConfig;
import com.jerotes.jerotesvillage.control.NoRotationControl;
import com.jerotes.jerotesvillage.entity.Humanoid.Serpon.SerponColonialSoldierEntity;
import com.jerotes.jerotesvillage.entity.Humanoid.Serpon.SerponEntity;
import com.jerotes.jerotesvillage.entity.Other.Machine.SerponChariotEntity;
import com.jerotes.jerotesvillage.entity.Other.Machine.SerponCombatPlatformEntity;
import com.jerotes.jerotesvillage.event.BossBarEvent;
import com.jerotes.jerotesvillage.goal.SerponAttackTargetPlayerGoal;
import com.jerotes.jerotesvillage.init.*;
import com.jerotes.jerotesvillage.network.JerotesVillageLevelData;
import com.jerotes.jerotesvillage.spell.OtherSpellFind;
import com.jerotes.jerotesvillage.spell.OtherSpellList;
import com.jerotes.jerotesvillage.util.Other;
import com.jerotes.jerotesvillage.util.OtherEntityFactionFind;
import net.minecraft.core.BlockPos;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.core.registries.Registries;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.chat.Component;
import net.minecraft.network.syncher.EntityDataAccessor;
import net.minecraft.network.syncher.EntityDataSerializers;
import net.minecraft.network.syncher.SynchedEntityData;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundSource;
import net.minecraft.tags.BiomeTags;
import net.minecraft.tags.BlockTags;
import net.minecraft.tags.DamageTypeTags;
import net.minecraft.tags.FluidTags;
import net.minecraft.util.Mth;
import net.minecraft.util.RandomSource;
import net.minecraft.world.*;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.damagesource.DamageTypes;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.*;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import net.minecraft.world.entity.ai.attributes.AttributeSupplier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.ai.control.BodyRotationControl;
import net.minecraft.world.entity.ai.goal.Goal;
import net.minecraft.world.entity.monster.Enemy;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.BowItem;
import net.minecraft.world.item.CrossbowItem;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.ShieldItem;
import net.minecraft.world.item.armortrim.ArmorTrim;
import net.minecraft.world.level.GameRules;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.ServerLevelAccessor;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.gameevent.GameEvent;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.Vec3;
import net.minecraft.world.scores.PlayerTeam;
import org.jetbrains.annotations.NotNull;

import javax.annotation.Nullable;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.UUID;

public class SerponCombatExecuterEntity extends SerponEntity implements BossEntity, FireAbsorptionEntity, SpellUseEntity, LoreUndyingEntity {
	private static final UUID ARMOR_MODIFIER_UUID = UUID.fromString("d8d07b34-c419-425c-8cc6-da38d77fd547");
	private static final UUID ATTACK_DAMAGE_MODIFIER_UUID = UUID.fromString("f07e31dd-a4f8-4ff6-a428-3c5ad54f93fa");
	private static final UUID KNOCKBACK_RESISTANCE_MODIFIER_UUID = UUID.fromString("bccc23e7-90ae-4c19-8d45-a0cd65213ea0");
	private static final EntityDataAccessor<Integer> ANIM_STATE = SynchedEntityData.defineId(SerponCombatExecuterEntity.class, EntityDataSerializers.INT);
	private static final EntityDataAccessor<Integer> ANIM_TICK = SynchedEntityData.defineId(SerponCombatExecuterEntity.class, EntityDataSerializers.INT);
	private static final EntityDataAccessor<Boolean> IS_TRUE_COME = SynchedEntityData.defineId(SerponCombatExecuterEntity.class, EntityDataSerializers.BOOLEAN);
	private static final EntityDataAccessor<Boolean> IS_SUPPLEMENT = SynchedEntityData.defineId(SerponCombatExecuterEntity.class, EntityDataSerializers.BOOLEAN);
	private static final EntityDataAccessor<Boolean> IS_FURY = SynchedEntityData.defineId(SerponCombatExecuterEntity.class, EntityDataSerializers.BOOLEAN);
	private static final EntityDataAccessor<Integer> SPELL_CHANCE = SynchedEntityData.defineId(SerponCombatExecuterEntity.class, EntityDataSerializers.INT);
	private static final EntityDataAccessor<Integer> ATTACK_TICK = SynchedEntityData.defineId(SerponCombatExecuterEntity.class, EntityDataSerializers.INT);
	private static final EntityDataAccessor<Integer> USE_SPELL_TICK = SynchedEntityData.defineId(SerponCombatExecuterEntity.class, EntityDataSerializers.INT);
	private static final EntityDataAccessor<Integer> WIND_ATTACK_TICK = SynchedEntityData.defineId(SerponCombatExecuterEntity.class, EntityDataSerializers.INT);
	private static final EntityDataAccessor<Integer> FLAME_ATTACK_TICK = SynchedEntityData.defineId(SerponCombatExecuterEntity.class, EntityDataSerializers.INT);
	private static final EntityDataAccessor<Integer> CURSE_TICK = SynchedEntityData.defineId(SerponCombatExecuterEntity.class, EntityDataSerializers.INT);
	private static final EntityDataAccessor<Integer> IS_CURSE_TICK = SynchedEntityData.defineId(SerponCombatExecuterEntity.class, EntityDataSerializers.INT);
	private static final EntityDataAccessor<Integer> LAST_TICK = SynchedEntityData.defineId(SerponCombatExecuterEntity.class, EntityDataSerializers.INT);
	private static final EntityDataAccessor<Boolean> IS_LAST = SynchedEntityData.defineId(SerponCombatExecuterEntity.class, EntityDataSerializers.BOOLEAN);
	private static final EntityDataAccessor<Integer> SPECIAL_BLOCK_TICK = SynchedEntityData.defineId(SerponCombatExecuterEntity.class, EntityDataSerializers.INT);
	private static final EntityDataAccessor<Integer> SPECIAL_MELEE_CHANCE = SynchedEntityData.defineId(SerponCombatExecuterEntity.class, EntityDataSerializers.INT);
	private static final EntityDataAccessor<Boolean> IS_LEGEND = SynchedEntityData.defineId(SerponCombatExecuterEntity.class, EntityDataSerializers.BOOLEAN);
	public AnimationState idleAnimationState = new AnimationState();
	public AnimationState attack1AnimationState = new AnimationState();
	public AnimationState attack2AnimationState = new AnimationState();
	public AnimationState swordAttack1AnimationState = new AnimationState();
	public AnimationState swordAttack2AnimationState = new AnimationState();
	public AnimationState swordAttack3AnimationState = new AnimationState();
	public AnimationState swordAttack4AnimationState = new AnimationState();
	public AnimationState windAttackAnimationState = new AnimationState();
	public AnimationState flameAttackAnimationState = new AnimationState();
	public AnimationState block1AnimationState = new AnimationState();
	public AnimationState block2AnimationState = new AnimationState();
	public AnimationState block3AnimationState = new AnimationState();
	public AnimationState pikeSpellAnimationState = new AnimationState();
	public AnimationState hotWindSpellAnimationState = new AnimationState();
	public AnimationState meteorSpellAnimationState = new AnimationState();
	public AnimationState pushAnimationState = new AnimationState();
	public AnimationState deadAnimationState = new AnimationState();
	private final JerotesBossEvent bossEvent = new JerotesBossEvent(this, this.getUUID(), BossEvent.BossBarColor.YELLOW, false);

	public SerponCombatExecuterEntity(EntityType<? extends SerponCombatExecuterEntity> entityType, Level level) {
		super(entityType, level);
		this.xpReward = 700;
		this.setCanPickUpLoot(true);
		this.armorDropChances[EquipmentSlot.HEAD.getIndex()] = (float) OtherMainConfig.SerponCombatExecuterDropEquipmentChance;
		this.armorDropChances[EquipmentSlot.CHEST.getIndex()] = (float) OtherMainConfig.SerponCombatExecuterDropEquipmentChance;
		this.armorDropChances[EquipmentSlot.LEGS.getIndex()] = (float) OtherMainConfig.SerponCombatExecuterDropEquipmentChance;
		this.armorDropChances[EquipmentSlot.FEET.getIndex()] = (float) OtherMainConfig.SerponCombatExecuterDropEquipmentChance;
		this.handDropChances[EquipmentSlot.MAINHAND.getIndex()] = (float) OtherMainConfig.SerponCombatExecuterDropEquipmentChance;
		this.handDropChances[EquipmentSlot.OFFHAND.getIndex()] = (float) OtherMainConfig.SerponCombatExecuterDropEquipmentChance;
		if (OtherMainConfig.BossBaseAttributeCanUseConfig) {
			this.getAttribute(Attributes.MAX_HEALTH).setBaseValue(OtherMainConfig.SerponCombatExecuterMaxHealth);
			this.setHealth(this.getMaxHealth());
			this.getAttribute(Attributes.ATTACK_DAMAGE).setBaseValue(OtherMainConfig.SerponCombatExecuterMeleeDamage);
			this.getAttribute(Attributes.ARMOR).setBaseValue(OtherMainConfig.SerponCombatExecuterArmor);
			this.getAttribute(Attributes.MOVEMENT_SPEED).setBaseValue(OtherMainConfig.SerponCombatExecuterMovementSpeed);
			this.getAttribute(Attributes.FLYING_SPEED).setBaseValue(OtherMainConfig.SerponCombatExecuterFlyingSpeed);
			this.getAttribute(Attributes.ATTACK_KNOCKBACK).setBaseValue(OtherMainConfig.SerponCombatExecuterAttackKnockback);
			this.getAttribute(Attributes.KNOCKBACK_RESISTANCE).setBaseValue(OtherMainConfig.SerponCombatExecuterKnockbackResistance);
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
			return(float) OtherMainConfig.SerponCombatExecuterMagicAttackPercentage;
		}
		return (float) OtherMainConfig.SerponCombatExecuterAttackPercentage;
	}
	//限伤
	public boolean hasDamageCap() {
		return MainConfig.HasDamageCap.contains(this.getEncodeId()) || OtherMainConfig.BossHasDamageCap.contains(this.getEncodeId()) || this.isLegend();
	}
	public float DamageCap(DamageSource damageSource, Entity entity) {
		return (float) OtherMainConfig.SerponCombatExecuterDamageCap;
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
		return (float)OtherMainConfig.SerponCombatExecuterDamageCooldownTick * base;
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
		if (itemStack.canEquip(EquipmentSlot.OFFHAND,this)) {
			return false;
		}
		if (!this.getMainHandItem().isEmpty()) {
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
		if (this.isTrueCome()) {
			this.bossEvent.setProgress(this.getHealth() / this.getMaxHealth());
		}
		else if (this.isSupplement()) {
			this.bossEvent.setProgress((float) this.supplementTick);
		}
		else {
			this.bossEvent.setProgress(Mth.clamp(this.getHealthOfLivingSerpon() / this.summonMaxHealth, 0.0f, 1.0f));
		}
	}

	public float getHealthOfLivingSerpon() {
		float f = (float) (this.summonAmount / this.summonAmountMax);
		List<Mob> listTeam = this.level().getEntitiesOfClass(Mob.class, this.getBoundingBox().inflate(64.0, 64.0, 64.0));
		listTeam.removeIf(entity -> entity instanceof SerponCombatExecuterEntity || !OtherEntityFactionFind.isFactionSerponCombatTeam(entity) || !(entity instanceof NeutralMob || entity instanceof Enemy));
		listTeam.removeIf(entity -> (entity.getTeam() != null || this.getTeam() != null) && !this.isAlliedTo(entity));
		return f + listTeam.size();
	}

	@Override
	protected void populateDefaultEquipmentSlots(RandomSource randomSource, DifficultyInstance difficultyInstance) {
		ItemStack head = new ItemStack(JerotesVillageItems.SERPON_POWERHOUSE_HELMET.get());
		ItemStack chest = new ItemStack(JerotesVillageItems.SERPON_POWERHOUSE_CHESTPLATE.get());
		ItemStack legs = new ItemStack(JerotesVillageItems.SERPON_POWERHOUSE_LEGGINGS.get());
		ItemStack feet = new ItemStack(JerotesVillageItems.SERPON_POWERHOUSE_BOOTS.get());
		ArmorTrim armorTrim = new ArmorTrim(this.level().registryAccess().registryOrThrow(Registries.TRIM_MATERIAL).getHolder(ResourceKey.create(Registries.TRIM_MATERIAL, new ResourceLocation(JerotesVillage.MODID, "fire_secretor_adhesive"))).get(), this.level().registryAccess().registryOrThrow(Registries.TRIM_PATTERN).getHolder(ResourceKey.create(Registries.TRIM_PATTERN, new ResourceLocation(JerotesVillage.MODID, "serpent_dominater"))).get());
		ArmorTrim.setTrim(this.level().registryAccess(), head, armorTrim);
		ArmorTrim.setTrim(this.level().registryAccess(), chest, armorTrim);
		ArmorTrim.setTrim(this.level().registryAccess(), legs, armorTrim);
		ArmorTrim.setTrim(this.level().registryAccess(), feet, armorTrim);
		this.maybeWearArmor(EquipmentSlot.HEAD, head, randomSource);
		this.maybeWearArmor(EquipmentSlot.CHEST, chest, randomSource);
		this.maybeWearArmor(EquipmentSlot.LEGS, legs, randomSource);
		this.maybeWearArmor(EquipmentSlot.FEET, feet, randomSource);
	}

	private void maybeWearArmor(EquipmentSlot equipmentSlot, ItemStack itemStack, RandomSource randomSource) {
		this.setItemSlot(equipmentSlot, itemStack);
	}

	public static AttributeSupplier.Builder createAttributes() {
		AttributeSupplier.Builder builder = Mob.createMobAttributes();
		builder = builder.add(Attributes.MAX_HEALTH, 270);
		builder = builder.add(Attributes.ATTACK_DAMAGE, 8);
		builder = builder.add(Attributes.ARMOR, 5);
		builder = builder.add(Attributes.MOVEMENT_SPEED, 0.4);
		builder = builder.add(Attributes.FLYING_SPEED, 0.5);
		builder = builder.add(Attributes.ATTACK_KNOCKBACK, 0.5);
		builder = builder.add(Attributes.KNOCKBACK_RESISTANCE, 0.8);

		builder = builder.add(Attributes.FOLLOW_RANGE, 128);
		return builder;
	}

	@Override
	protected void registerGoals() {
		super.registerGoals();
		this.targetSelector.addGoal(2, new SerponAttackTargetPlayerGoal<Player>(this, Player.class, true));
	}

	@Override
	protected SoundEvent getAmbientSound() {
		if (!this.isTrueCome()) {
			return null;
		}
		return JerotesVillageSoundEvents.SERPON_COMBAT_EXECUTER_AMBIENT;
	}
	@Override
	public int getAmbientSoundInterval() {
		return 200;
	}
	@Override
	protected SoundEvent getDeathSound() {
		if (!this.isTrueCome()) {
			return null;
		}
		return JerotesVillageSoundEvents.SERPON_COMBAT_EXECUTER_DEATH;
	}
	@Override
	protected SoundEvent getHurtSound(DamageSource damageSource) {
		if (!this.isTrueCome()) {
			return null;
		}
		return JerotesVillageSoundEvents.SERPON_COMBAT_EXECUTER_HURT;
	}
	public float serponStrengthBase() {
		float base = 1.0f;
		//群系
		if (this.level().getBiome(BlockPos.containing(this.getX(), this.getY(), this.getZ())).is(JerotesVillageBiomeTypeTags.SERPON_FIND) && JerotesVillageLevelData.get(this.level()) != null && !JerotesVillageLevelData.get(this.level()).SerponAway) {
			base += 0.5f;
		}
		else if (this.level().getBiome(BlockPos.containing(this.getX(), this.getY(), this.getZ())).is(BiomeTags.SNOW_GOLEM_MELTS)) {
			base += 0.25f;
		}
		else if (JerotesVillageLevelData.get(this.level()) != null && this.level().getBiome(BlockPos.containing(this.getX(), this.getY(), this.getZ())).is(BiomeTags.SPAWNS_SNOW_FOXES)) {
			base -= 0.5f;
		}
		//驱逐
		if (JerotesVillageLevelData.get(this.level()) != null && JerotesVillageLevelData.get(this.level()).SerponAway) {
			base -= 0.5f;
		}
		//火焰
		if (this.level().getBlockState(this.getOnPos().above()).is(BlockTags.FIRE) || this.level().getFluidState(this.getOnPos().above()).is(FluidTags.LAVA)) {
			base += 0.25f;
		}
		//蛇龙人数量
		List<Mob> listTeam = this.level().getEntitiesOfClass(Mob.class, this.getBoundingBox().inflate(64.0, 64.0, 64.0));
		listTeam.removeIf(entity -> entity instanceof SerponCombatExecuterEntity || !OtherEntityFactionFind.isFactionSerponCombatTeam(entity) || !(entity instanceof NeutralMob || entity instanceof Enemy));
		listTeam.removeIf(entity -> (entity.getTeam() != null || this.getTeam() != null) && !this.isAlliedTo(entity));
		if (!listTeam.isEmpty()) {
			base += Math.min(1.0f, listTeam.size()/12f);
		}
		//日间
		if (this.level().isDay()) {
			base += 0.25f;
		}
		//夜晚
		else if (this.level().isNight()) {
			base += 0.15f;
		}
		//阳光下
		if (this.isSunBurnTick()) {
			base += 0.25f;
		}
		//目标阳光下
		if (this.getTarget() != null && this.getTarget() instanceof Mob mob && otherIsSunBurnTick(mob)) {
			base += 0.25f;
		}
		//蛇龙凭证
		if (this.getTarget() != null && this.getTarget().hasEffect(JerotesVillageMobEffects.SERPON_CREDENTIAL.get())) {
			base -= 0.25f;
		}
		if (this.hasEffect(JerotesVillageMobEffects.SERPON_CREDENTIAL.get())) {
			base += 0.5f;
		}
		//卸甲之怒
		if (this.isFury()) {
			base += 0.25f;
		}
		return Math.min(Math.max(base, 0.25f), 5f);
	}
	protected boolean otherIsSunBurnTick(Mob mob) {
		if (mob.level().isDay() && !mob.level().isClientSide) {
			float f = mob.getLightLevelDependentMagicValue();
			BlockPos blockpos = BlockPos.containing(mob.getX(), mob.getEyeY(), mob.getZ());
			boolean flag = mob.isInWaterRainOrBubble() || mob.isInPowderSnow || mob.wasInPowderSnow;
			if (f > 0.5F && mob.getRandom().nextFloat() * 30.0F < (f - 0.4F) * 2.0F && !flag && mob.level().canSeeSky(blockpos)) {
				return true;
			}
		}
		return false;
	}
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
		if (!this.getMainHandItem().isEmpty())
			return aabb1.inflate(1.6d, 1.6d, 1.6d);
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
	public void knockback(double d, double d2, double d3) {
		super.knockback(0, 0, 0);
	}
	@Override
	public void push(double d, double d2, double d3) {
		super.push(0, 0, 0);
	}
	@Override
	public void makeStuckInBlock(BlockState blockState, Vec3 vec3) {
		this.resetFallDistance();
	}
	@Override
	public void travel(Vec3 vec3) {
		super.travel(vec3);
		if (this.specialAction() && !(this.getUseSpellTick() < 0 && this.getUseSpellTick() > -2)) {
			this.setDeltaMovement(this.getDeltaMovement().multiply(0.05d, 1d, 0.05d));
		}
	}
	@Override
	public void setDeltaMovement(Vec3 vec3) {
		if (!this.isTrueCome()) {
			super.setDeltaMovement(new Vec3(0, 0, 0));
		}
		else {
			super.setDeltaMovement(vec3);
		}
	}
	@Override
	public boolean isBlocking() {
		if (this.getSpecialBlockTick() > 4) {
			return true;
		}
		if (isFury() && this.getUseSpellTick() > 1) {
			return true;
		}
		return super.isBlocking();
	}
	@Override
	public boolean isDamageSourceBlocked(DamageSource damageSource) {
		if (isFury() && this.getSpecialBlockTick() < 0 && this.getRandom().nextFloat() > 0.75f) {
			return true;
		}
		return super.isDamageSourceBlocked(damageSource);
	}
	@Override
	protected @NotNull BodyRotationControl createBodyControl() {
		if (!this.isTrueCome()) {
			return new NoRotationControl(this);
		}
		return super.createBodyControl();
	}
	@Override
	public boolean canBeSeenAsEnemy() {
		if (!this.isTrueCome()) {
			return false;
		}
		return super.canBeSeenAsEnemy();
	}
	public Component getDisplayName() {
		if (!this.isTrueCome()) {
			return Component.translatable("entity.jerotesvillage.serpon_combat_team");
		}
		return PlayerTeam.formatNameForTeam(this.getTeam(), this.getName()).withStyle((p_185975_) -> {
			return p_185975_.withHoverEvent(this.createHoverEvent()).withInsertion(this.getStringUUID());
		});
	}
	@Override
	public boolean isPushable() {
		if (!this.isTrueCome())
			return false;
		return super.isPushable();
	}
	@Override
	public boolean isPushedByFluid() {
		if (!this.isTrueCome())
			return false;
		return super.isPushedByFluid();
	}
	@Override
	public SoundEvent getBossMusic() {
		return JerotesVillageSoundEvents.SERPON_COMBAT_EXECUTER_MUSIC;
	}
	@Override
	public boolean canChangeDimensions() {
		return super.canChangeDimensions() && this.isTrueCome();
	}
	@Override
	public double FireAbsorptionPercentage() {
		return OtherMainConfig.SerponCombatExecuterFireAbsorptionPercentage;
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
	public void lookAt(Entity entity, float f, float f2) {
		if (this.getIsCurseTick() <= 0) {
			super.lookAt(entity,f,f2);
		}
	}
	@Override
	public void setXRot(float f) {
		if (this.getIsCurseTick() <= 0) {
			super.setXRot(f);
		}
	}
	@Override
	public void setYRot(float f) {
		if (this.getIsCurseTick() <= 0) {
			super.setYRot(f);
		}
	}
	@Override
	public void setYBodyRot(float f) {
		if (this.getIsCurseTick() <= 0) {
			super.setYBodyRot(f);
		}
	}

	private double blockDestroyTick;
	public int spellLevel = OtherMainConfig.SerponCombatExecuterSpellNormalLevel;
	@Override
	public int getSpellLevel() {
		return this.spellLevel;
	}
	//是否真身前来
	public boolean isTrueCome() {
		return this.getEntityData().get(IS_TRUE_COME);
	}
	public void setTrueCome(boolean bl) {
		this.getEntityData().set(IS_TRUE_COME, bl);
		this.noPhysics = false;
		if (bl) {
			this.bossEvent.setName(Objects.requireNonNull(this.getDisplayName()));
			this.bossEvent.setProgress(this.getHealth() / this.getMaxHealth());
		}
	}
	//是否间隔
	public boolean isSupplement() {
		return this.getEntityData().get(IS_SUPPLEMENT);
	}
	public void setSupplement(boolean bl) {
		this.getEntityData().set(IS_SUPPLEMENT, bl);
	}
	//恢复时间
	public double supplementTick;
	//波次生物数量计算
	public double summonAmountMax = OtherMainConfig.SerponCombatExecuterRoundTimeMin * 20;
	public double summonAmountStopMax = OtherMainConfig.SerponCombatExecuterRoundTimeMax * 20;
	public double summonAmount = this.summonAmountMax;
	//此波次最大生命值
	public float summonMaxHealth = 1;
	//波次
	public int round = 1;
	public int roundSummons;
	public boolean specialAction() {
		return !isTrueCome() || this.getUseSpellTick() < 0 || this.getFlameAttackTick() > 0 || this.getWindAttackTick() > 0 || this.getIsCurseTick() > 0;
	}
	public void setAttackTick(int n){
		this.getEntityData().set(ATTACK_TICK, n);
	}
	public int getAttackTick(){
		return this.getEntityData().get(ATTACK_TICK);
	}
	public void setUseSpellTick(int n){
		this.getEntityData().set(USE_SPELL_TICK, n);
	}
	public int getUseSpellTick(){
		return this.getEntityData().get(USE_SPELL_TICK);
	}
	public void setSpellChance(int n){
		this.getEntityData().set(SPELL_CHANCE, n);
	}
	public int getSpellChance(){
		return this.getEntityData().get(SPELL_CHANCE);
	}
	public void setWindAttackTick(int n){
		this.getEntityData().set(WIND_ATTACK_TICK, n);
	}
	public int getWindAttackTick(){
		return this.getEntityData().get(WIND_ATTACK_TICK);
	}
	public void setFlameAttackTick(int n){
		this.getEntityData().set(FLAME_ATTACK_TICK, n);
	}
	public int getFlameAttackTick(){
		return this.getEntityData().get(FLAME_ATTACK_TICK);
	}
	public void setCurseTick(int n){
		this.getEntityData().set(CURSE_TICK, n);
	}
	public int getCurseTick(){
		return this.getEntityData().get(CURSE_TICK);
	}
	public void setIsCurseTick(int n){
		this.getEntityData().set(IS_CURSE_TICK, n);
	}
	public int getIsCurseTick(){
		return this.getEntityData().get(IS_CURSE_TICK);
	}
	public void setLastTick(int n){
		this.getEntityData().set(LAST_TICK, n);
	}
	public int getLastTick(){
		return this.getEntityData().get(LAST_TICK);
	}
	public void setSpecialBlockTick(int n){
		this.getEntityData().set(SPECIAL_BLOCK_TICK, n);
	}
	public int getSpecialBlockTick(){
		return this.getEntityData().get(SPECIAL_BLOCK_TICK);
	}
	public void setSpecialMeleeChance(int n){
		this.getEntityData().set(SPECIAL_MELEE_CHANCE, n);
	}
	public int getSpecialMeleeChance(){
		return this.getEntityData().get(SPECIAL_MELEE_CHANCE);
	}
	public boolean isLast() {
		return this.getEntityData().get(IS_LAST);
	}
	public void setLast(boolean bl) {
		this.getEntityData().set(IS_LAST, bl);
	}
	public boolean isFury() {
		return this.getEntityData().get(IS_FURY);
	}
	public void setFury(boolean bl) {
		this.getEntityData().set(IS_FURY, bl);
		Objects.requireNonNull(this.getAttribute(Attributes.ARMOR)).removeModifier(ARMOR_MODIFIER_UUID);
		Objects.requireNonNull(this.getAttribute(Attributes.ATTACK_DAMAGE)).removeModifier(ATTACK_DAMAGE_MODIFIER_UUID);
		Objects.requireNonNull(this.getAttribute(Attributes.KNOCKBACK_RESISTANCE)).removeModifier(KNOCKBACK_RESISTANCE_MODIFIER_UUID);
		if (bl) {
			ItemStack head = this.getItemBySlot(EquipmentSlot.HEAD);
			ItemStack chest = this.getItemBySlot(EquipmentSlot.CHEST);
			ItemStack legs = this.getItemBySlot(EquipmentSlot.LEGS);
			ItemStack feet = this.getItemBySlot(EquipmentSlot.FEET);
			head.hurtAndBreak(2400, this, player -> player.broadcastBreakEvent(EquipmentSlot.HEAD));
			chest.hurtAndBreak(2400, this, player -> player.broadcastBreakEvent(EquipmentSlot.CHEST));
			legs.hurtAndBreak(2400, this, player -> player.broadcastBreakEvent(EquipmentSlot.LEGS));
			feet.hurtAndBreak(2400, this, player -> player.broadcastBreakEvent(EquipmentSlot.FEET));

			if (!this.isSilent()) {
				this.level().playSound(null, this.getX(), this.getY(), this.getZ(), JerotesVillageSoundEvents.SERPON_COMBAT_EXECUTER_FURY, this.getSoundSource(), 1.0f, 1.0f);
			}

			double d = OtherMainConfig.SerponCombatExecuterArmorFury - OtherMainConfig.SerponCombatExecuterArmor;
			double d2 = OtherMainConfig.SerponCombatExecuterMeleeDamageFury - OtherMainConfig.SerponCombatExecuterMeleeDamage;
			double d3 = OtherMainConfig.SerponCombatExecuterKnockbackResistanceFury - OtherMainConfig.SerponCombatExecuterKnockbackResistance;
			Objects.requireNonNull(this.getAttribute(Attributes.ARMOR)).addTransientModifier(new AttributeModifier(ARMOR_MODIFIER_UUID, "Fury armor", d, AttributeModifier.Operation.ADDITION));
			Objects.requireNonNull(this.getAttribute(Attributes.ATTACK_DAMAGE)).addTransientModifier(new AttributeModifier(ATTACK_DAMAGE_MODIFIER_UUID, "Fury movement speed", d2, AttributeModifier.Operation.ADDITION));
			Objects.requireNonNull(this.getAttribute(Attributes.KNOCKBACK_RESISTANCE)).addTransientModifier(new AttributeModifier(KNOCKBACK_RESISTANCE_MODIFIER_UUID, "Fury knockback resistance", d3, AttributeModifier.Operation.ADDITION));
		}
	}
	@Override
	public void setCustomName(@Nullable Component component) {
		super.setCustomName(component);
		this.bossEvent.setName(Objects.requireNonNull(this.getDisplayName()));
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
		else if (Objects.equals(animation, "swordAttack1")){
			return 3;
		}
		else if (Objects.equals(animation, "swordAttack2")){
			return 4;
		}
		else if (Objects.equals(animation, "swordAttack3")){
			return 5;
		}
		else if (Objects.equals(animation, "swordAttack4")){
			return 6;
		}
		else if (Objects.equals(animation, "windAttack")){
			return 7;
		}
		else if (Objects.equals(animation, "flameAttack")){
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
		else if (Objects.equals(animation, "pikeSpell")){
			return 12;
		}
		else if (Objects.equals(animation, "hotWindSpell")){
			return 13;
		}
		else if (Objects.equals(animation, "meteorSpell")){
			return 14;
		}
		else if (Objects.equals(animation, "push")){
			return 15;
		}
		else if (Objects.equals(animation, "dead")){
			return 16;
		}
		else {
			return 0;
		}
	}
	public List<AnimationState> getAllAnimations(){
		List<AnimationState> list = new ArrayList<>();
		list.add(attack1AnimationState);
		list.add(attack2AnimationState);
		list.add(swordAttack1AnimationState);
		list.add(swordAttack2AnimationState);
		list.add(swordAttack3AnimationState);
		list.add(swordAttack4AnimationState);
		list.add(windAttackAnimationState);
		list.add(flameAttackAnimationState);
		list.add(block1AnimationState);
		list.add(block2AnimationState);
		list.add(block3AnimationState);
		list.add(pikeSpellAnimationState);
		list.add(hotWindSpellAnimationState);
		list.add(meteorSpellAnimationState);
		list.add(pushAnimationState);
		list.add(deadAnimationState);
		return list;
	}
	public void stopMostAnimation(AnimationState exception){
		for (AnimationState state : this.getAllAnimations()){
			if (state != exception && state != pushAnimationState) {
				state.stop();
			}
		}
	}
	public void stopAllAnimation(){
		for (AnimationState state : this.getAllAnimations()){
			if (state != pushAnimationState) {
				state.stop();
			}
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
		compoundTag.putInt("SpellLevel", this.spellLevel);
		compoundTag.putBoolean("IsTrueCome", this.isTrueCome());
		compoundTag.putBoolean("IsSupplement", this.isSupplement());
		compoundTag.putBoolean("IsFury", this.isFury());
		compoundTag.putBoolean("IsLast", this.isLast());
		compoundTag.putDouble("SupplementTick", this.supplementTick);
		compoundTag.putDouble("SummonAmount", this.summonAmount);
		compoundTag.putDouble("SummonAmountMax", this.summonAmountMax);
		compoundTag.putDouble("SummonAmountStopMax", this.summonAmountStopMax);
		compoundTag.putFloat("SummonMaxHealth", this.summonMaxHealth);
		compoundTag.putInt("Round", this.round);
		compoundTag.putInt("RoundSummons", this.roundSummons);
		compoundTag.putInt("SpecialBlockTick", this.getSpecialBlockTick());
		compoundTag.putInt("AttackTick", this.getAttackTick());
		compoundTag.putInt("FlameAttackTick", this.getFlameAttackTick());
		compoundTag.putInt("WindAttackTick", this.getWindAttackTick());
		compoundTag.putInt("CurseTick", this.getCurseTick());
		compoundTag.putInt("IsCurseTick", this.getIsCurseTick());
		compoundTag.putInt("LastTick", this.getLastTick());
		compoundTag.putInt("SpecialMeleeChance", this.getSpecialMeleeChance());
		compoundTag.putInt("SpellTick", this.getUseSpellTick());
		compoundTag.putInt("SpellChance", this.getSpellChance());
		compoundTag.putDouble("BlockDestroyTick", this.blockDestroyTick);
	}
	@Override
	public void readAdditionalSaveData(CompoundTag compoundTag) {
		super.readAdditionalSaveData(compoundTag);
		if (this.hasCustomName()) {
			this.bossEvent.setName(Objects.requireNonNull(this.getDisplayName()));
		}
		this.setLegend(compoundTag.getBoolean("IsLegend"));
		this.setAnimTick(compoundTag.getInt("AnimTick"));
		this.spellLevel = compoundTag.getInt("SpellLevel");
		if (compoundTag.contains("IsTrueCome")) {
			this.setTrueCome(compoundTag.getBoolean("IsTrueCome"));
		}
		if (compoundTag.contains("IsSupplement")) {
			this.setSupplement(compoundTag.getBoolean("IsSupplement"));
		}
		if (compoundTag.contains("IsFury")) {
			this.setFury(compoundTag.getBoolean("IsFury"));
		}
		this.setLast(compoundTag.getBoolean("IsLast"));
		this.supplementTick = compoundTag.getDouble("SupplementTick");
		this.summonAmount = compoundTag.getDouble("SummonAmount");
		this.summonAmountMax = compoundTag.getDouble("SummonAmountMax");
		this.summonAmountStopMax = compoundTag.getDouble("SummonAmountStopMax");
		this.summonMaxHealth = compoundTag.getFloat("SummonMaxHealth");
		this.round = compoundTag.getInt("Round");
		this.roundSummons = compoundTag.getInt("RoundSummons");
		this.setSpecialBlockTick(compoundTag.getInt("SpecialBlockTick"));
		this.setAttackTick(compoundTag.getInt("AttackTick"));
		this.setFlameAttackTick(compoundTag.getInt("FlameAttackTick"));
		this.setWindAttackTick(compoundTag.getInt("WindAttackTick"));
		this.setCurseTick(compoundTag.getInt("CurseTick"));
		this.setIsCurseTick(compoundTag.getInt("IsCurseTick"));
		this.setLastTick(compoundTag.getInt("LastTick"));
		this.setSpecialMeleeChance(compoundTag.getInt("SpecialMeleeChance"));
		this.setUseSpellTick(compoundTag.getInt("SpellTick"));
		this.setSpellChance(compoundTag.getInt("SpellChance"));
		this.blockDestroyTick = compoundTag.getDouble("BlockDestroyTick");
		this.bossEvent.setId(this.getUUID());
	}
	protected void defineSynchedData() {
		super.defineSynchedData();
		this.getEntityData().define(ANIM_STATE, 0);
		this.getEntityData().define(ANIM_TICK, 0);
		this.getEntityData().define(IS_TRUE_COME, true);
		this.getEntityData().define(IS_SUPPLEMENT, true);
		this.getEntityData().define(IS_FURY, false);
		this.getEntityData().define(IS_LAST, false);
		this.getEntityData().define(SPELL_CHANCE, 0);
		this.getEntityData().define(ATTACK_TICK, 0);
		this.getEntityData().define(USE_SPELL_TICK, 0);
		this.getEntityData().define(WIND_ATTACK_TICK, 0);
		this.getEntityData().define(FLAME_ATTACK_TICK, 0);
		this.getEntityData().define(CURSE_TICK, 400);
		this.getEntityData().define(IS_CURSE_TICK, 0);
		this.getEntityData().define(LAST_TICK, 0);
		this.getEntityData().define(SPECIAL_BLOCK_TICK, 0);
		this.getEntityData().define(SPECIAL_MELEE_CHANCE, 0);
		this.getEntityData().define(IS_LEGEND, false);
	}
	@Override
	public void onSyncedDataUpdated(EntityDataAccessor<?> entityDataAccessor) {
		if (IS_TRUE_COME.equals(entityDataAccessor)) {
			this.refreshDimensions();
		}
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
						this.swordAttack1AnimationState.startIfStopped(this.tickCount);
						this.stopMostAnimation(this.swordAttack1AnimationState);
						break;
					case 4:
						this.swordAttack2AnimationState.startIfStopped(this.tickCount);
						this.stopMostAnimation(this.swordAttack2AnimationState);
						break;
					case 5:
						this.swordAttack3AnimationState.startIfStopped(this.tickCount);
						this.stopMostAnimation(this.swordAttack3AnimationState);
						break;
					case 6:
						this.swordAttack4AnimationState.startIfStopped(this.tickCount);
						this.stopMostAnimation(this.swordAttack4AnimationState);
						break;
					case 7:
						this.windAttackAnimationState.startIfStopped(this.tickCount);
						this.stopMostAnimation(this.windAttackAnimationState);
						break;
					case 8:
						this.flameAttackAnimationState.startIfStopped(this.tickCount);
						this.stopMostAnimation(this.flameAttackAnimationState);
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
						this.pikeSpellAnimationState.startIfStopped(this.tickCount);
						this.stopMostAnimation(this.pikeSpellAnimationState);
						break;
					case 13:
						this.hotWindSpellAnimationState.startIfStopped(this.tickCount);
						this.stopMostAnimation(this.hotWindSpellAnimationState);
						break;
					case 14:
						this.meteorSpellAnimationState.startIfStopped(this.tickCount);
						this.stopMostAnimation(this.meteorSpellAnimationState);
						break;
					case 15:
						this.pushAnimationState.startIfStopped(this.tickCount);
						break;
					case 16:
						this.deadAnimationState.startIfStopped(this.tickCount);
						this.stopMostAnimation(this.deadAnimationState);
						break;
				}
			}
		}
		super.onSyncedDataUpdated(entityDataAccessor);
	}

	@Override
	protected InteractionResult mobInteract(Player player, InteractionHand interactionHand) {
		if (!this.isTrueCome()) {
			if (player.getItemInHand(interactionHand).getItem() == JerotesVillageItems.SERPON_CREDENTIAL.get() && this.round < 8 && !player.getCooldowns().isOnCooldown(JerotesVillageItems.SERPON_CREDENTIAL.get())) {
				player.getCooldowns().addCooldown(JerotesVillageItems.SERPON_CREDENTIAL.get(), 18 * 20);
				this.round += 1;
				this.supplementTick = 0;
			}
			return InteractionResult.sidedSuccess(this.level().isClientSide);
		}
		else {
			return super.mobInteract(player, interactionHand);
		}
	}

	@Override
	public void tick() {
		this.fly = 500;
		List<Mob> listTeam = this.level().getEntitiesOfClass(Mob.class, this.getBoundingBox().inflate(64.0, 64.0, 64.0));
		listTeam.removeIf(entity -> entity instanceof SerponCombatExecuterEntity || !OtherEntityFactionFind.isFactionSerponCombatTeam(entity) || !(entity instanceof NeutralMob || entity instanceof Enemy));
		listTeam.removeIf(entity -> (entity.getTeam() != null || this.getTeam() != null) && !this.isAlliedTo(entity));
		for (Mob serpon : listTeam) {
			if (!(serpon instanceof SerponEntity serpon1)) continue;
			serpon1.fly = 500;
		}

		//召唤与否
		if (!this.isTrueCome() && this.isAlive()) {
			this.noPhysics = true;
			super.tick();
			this.noPhysics = false;
			this.setNoGravity(true);
			this.getNavigation().stop();
			if (!this.level().isClientSide) {
				this.goalSelector.setControlFlag(Goal.Flag.MOVE, false);
				this.goalSelector.setControlFlag(Goal.Flag.JUMP, false);
				this.goalSelector.setControlFlag(Goal.Flag.LOOK, false);
				this.addEffect(new MobEffectInstance(MobEffects.INVISIBILITY, 10, 10, true, false));
			}
			int time1 = (int) Math.max(((this.summonAmount) / 20), 0);
            int time2 = (int) ((this.summonAmount + (this.summonAmountStopMax - this.summonAmountMax)) / 20);
			this.bossEvent.setName(Component.translatable("entity.jerotesvillage.serpon_combat_team").append(Component.literal(" ")).append(Component.translatable("entity.jerotesvillage.serpon_combat_team_round", String.valueOf(this.round))).append(Component.literal(" ")).append(Component.translatable("entity.jerotesvillage.serpon_combat_team_time", time1, time2)).append(Component.literal(" ")).append(Component.translatable("entity.jerotesvillage.serpon_combat_team_count", listTeam.size())));
			if (this.level().isClientSide) {
				for (int i = 0; i < 5; ++i) {
					this.level().addParticle(ParticleTypes.FLAME, this.getRandomX(4.0), this.getY((2.0D * this.random.nextDouble() - 1.0D) * 2.0), this.getRandomZ(4.0), 0.0, 0.0, 0.0);
				}
			}
			//准备累计
			if (this.supplementTick < 1) {
				this.supplementTick += 0.01;
				if (!this.level().isClientSide()) {
					this.setSupplement(true);
				}
				this.summonMaxHealth = 1;
				this.summonAmount = this.summonAmountMax;
				this.roundSummons = 0;
			}
			else {
				if (!this.level().isClientSide()) {
					this.setSupplement(false);
				}
			}
			//生成数量
			if (!this.isSupplement()) {
				if (this.summonAmount > (this.summonAmountMax - this.summonAmountStopMax)) {
					this.summonAmount -= 1;
					if (this.summonAmount <= 0 && this.getHealthOfLivingSerpon() <= 0) {
						this.round += 1;
						this.supplementTick = 0;
						if (!this.level().isClientSide()) {
							this.setSupplement(true);
						}
					}
				}
				//修改
				else {
					if (round < 8) {
						this.round += 1;
						this.supplementTick = 0;
						if (!this.level().isClientSide()) {
							this.setSupplement(true);
						}
					}
				}
			}
			if (this.round > 8 || this.round < 1) {
				if (!this.level().isClientSide()) {
					this.setTrueCome(true);
				}
				this.bossEvent.setName(Objects.requireNonNull(this.getDisplayName()));
				this.bossEvent.setProgress(this.getHealth() / this.getMaxHealth());
				if (this.level() instanceof ServerLevel serverLevel) {
					for (int counts = 0; counts < 360; ++counts) {
						serverLevel.sendParticles(ParticleTypes.CAMPFIRE_SIGNAL_SMOKE, this.getRandomX(1), this.getRandomY(), this.getRandomZ(1), 4, 0, 0, 0, 0);
					}
				}
			}
			//第一波
			if (round == 1) {
				if (this.summonAmount < this.summonAmountMax * 0.95 && roundSummons == 0) {
					//蛇龙先锋
					summonEntity(JerotesVillageEntityType.SERPON_PIONEER.get(), 2, 12);
					this.roundSummons += 1;
				}
				if (this.summonAmount < this.summonAmountMax * 0.75 && roundSummons == 1) {
					//蛇龙先锋
					summonEntity(JerotesVillageEntityType.SERPON_PIONEER.get(), 3, 12);
					this.roundSummons += 1;
				}
				if (this.summonAmount < this.summonAmountMax * 0.5 && roundSummons == 2) {
					//蛇龙先锋
					summonEntity(JerotesVillageEntityType.SERPON_PIONEER.get(), 3, 12);
					this.roundSummons += 1;
				}
				if (this.summonAmount < this.summonAmountMax * 0.25 && roundSummons == 3) {
					//蛇龙先锋
					summonEntity(JerotesVillageEntityType.SERPON_PIONEER.get(), 4, 12);
					this.roundSummons += 1;
				}
			}
			//第二波
			else if (round == 2) {
				if (this.summonAmount < this.summonAmountMax * 0.95 && roundSummons == 0) {
					//蛇龙殖民兵
					summonEntity(JerotesVillageEntityType.SERPON_COLONIAL_SOLDIER.get(), 3, 12);
					this.roundSummons += 1;
				}
				if (this.summonAmount < this.summonAmountMax * 0.75 && roundSummons == 1) {
					//蛇龙殖民兵
					summonEntity(JerotesVillageEntityType.SERPON_COLONIAL_SOLDIER.get(), 3, 12);
					this.roundSummons += 1;
				}
				if (this.summonAmount < this.summonAmountMax * 0.5 && roundSummons == 2) {
					//蛇龙殖民兵
					summonEntity(JerotesVillageEntityType.SERPON_COLONIAL_SOLDIER.get(), 4, 12);
					this.roundSummons += 1;
				}
				if (this.summonAmount < this.summonAmountMax * 0.25 && roundSummons == 3) {
					//蛇龙殖民兵
					summonEntity(JerotesVillageEntityType.SERPON_COLONIAL_SOLDIER.get(), 5, 12);
					this.roundSummons += 1;
				}
			}
			//第三波
			else if (round == 3) {
				if (this.summonAmount < this.summonAmountMax * 0.95 && roundSummons == 0) {
					//蛇龙殖民兵
					summonEntity(JerotesVillageEntityType.SERPON_COLONIAL_SOLDIER.get(), 3, 12);
					this.roundSummons += 1;
				}
				if (this.summonAmount < this.summonAmountMax * 0.75 && roundSummons == 1) {
					//蛇龙殖民兵
					summonEntity(JerotesVillageEntityType.SERPON_COLONIAL_SOLDIER.get(), 3, 12);
					//战蟒
					summonEntity(JerotesVillageEntityType.WAR_PYTHON.get(), 1, 12);
					this.roundSummons += 1;
				}
				if (this.summonAmount < this.summonAmountMax * 0.5 && roundSummons == 2) {
					//蛇龙殖民兵
					summonEntity(JerotesVillageEntityType.SERPON_COLONIAL_SOLDIER.get(), 4, 12);
					this.roundSummons += 1;
				}
				if (this.summonAmount < this.summonAmountMax * 0.25 && roundSummons == 3) {
					//蛇龙殖民兵
					summonEntity(JerotesVillageEntityType.SERPON_COLONIAL_SOLDIER.get(), 5, 12);
					//战蟒
					summonEntity(JerotesVillageEntityType.WAR_PYTHON.get(), 2, 12);
					this.roundSummons += 1;
				}
			}
			//第四波
			else if (round == 4) {
				if (this.summonAmount < this.summonAmountMax * 0.95 && roundSummons == 0) {
					//蛇龙暴动者
					summonEntity(JerotesVillageEntityType.SERPON_RIOTER.get(), 2, 12);
					this.roundSummons += 1;
				}
				if (this.summonAmount < this.summonAmountMax * 0.75 && roundSummons == 1) {
					//蛇龙暴动者
					summonEntity(JerotesVillageEntityType.SERPON_RIOTER.get(), 1, 12);
					//战蟒
					summonEntity(JerotesVillageEntityType.WAR_PYTHON.get(), 1, 12);
					//蛇龙火焰巫师
					summonEntity(JerotesVillageEntityType.SERPON_FLAME_WIZARD.get(), 1, 12);
					this.roundSummons += 1;
				}
				if (this.summonAmount < this.summonAmountMax * 0.5 && roundSummons == 2) {
					//蛇龙暴动者
					summonEntity(JerotesVillageEntityType.SERPON_RIOTER.get(), 2, 12);
					//战蟒
					summonEntity(JerotesVillageEntityType.WAR_PYTHON.get(), 2, 12);
					this.roundSummons += 1;
				}
				if (this.summonAmount < this.summonAmountMax * 0.25 && roundSummons == 3) {
					//蛇龙暴动者
					summonEntity(JerotesVillageEntityType.SERPON_RIOTER.get(), 1, 12);
					//战蟒
					summonEntity(JerotesVillageEntityType.WAR_PYTHON.get(), 2, 12);
					//蛇龙火焰巫师
					summonEntity(JerotesVillageEntityType.SERPON_FLAME_WIZARD.get(), 1, 12);
					this.roundSummons += 1;
				}
			}
			//第五波
			else if (round == 5) {
				if (this.summonAmount < this.summonAmountMax * 0.95 && roundSummons == 0) {
					//蛇龙暴动者
					summonEntity(JerotesVillageEntityType.SERPON_RIOTER.get(), 2, 12);
					//蛇龙火焰巫师
					summonEntity(JerotesVillageEntityType.SERPON_FLAME_WIZARD.get(), 2, 12);
					this.roundSummons += 1;
				}
				if (this.summonAmount < this.summonAmountMax * 0.75 && roundSummons == 1) {
					//蛇龙暴动者
					summonEntity(JerotesVillageEntityType.SERPON_RIOTER.get(), 2, 12);
					//蛇龙战车
					summonEntity(JerotesVillageEntityType.SERPON_CHARIOT.get(), 1, 12);
					this.roundSummons += 1;
				}
				if (this.summonAmount < this.summonAmountMax * 0.5 && roundSummons == 2) {
					//蛇龙暴动者
					summonEntity(JerotesVillageEntityType.SERPON_RIOTER.get(), 3, 12);
					//战蟒
					summonEntity(JerotesVillageEntityType.WAR_PYTHON.get(), 2, 12);
					//蛇龙火焰巫师
					summonEntity(JerotesVillageEntityType.SERPON_FLAME_WIZARD.get(), 2, 12);
					this.roundSummons += 1;
				}
				if (this.summonAmount < this.summonAmountMax * 0.25 && roundSummons == 3) {
					//蛇龙暴动者
					summonEntity(JerotesVillageEntityType.SERPON_RIOTER.get(), 3, 12);
					//蛇龙火焰巫师
					summonEntity(JerotesVillageEntityType.SERPON_FLAME_WIZARD.get(), 2, 12);
					//蛇龙战车
					summonEntity(JerotesVillageEntityType.SERPON_CHARIOT.get(), 1, 12);
					this.roundSummons += 1;
				}
			}
			//第六波
			else if (round == 6) {
				if (this.summonAmount < this.summonAmountMax * 0.95 && roundSummons == 0) {
					//蛇龙突进者
					summonEntity(JerotesVillageEntityType.SERPON_INTRUDER.get(), 1, 12);
					//火灵
					summonEntity(JerotesVillageEntityType.FLAMOAR.get(), 1, 12);
					//蛇龙战车
					summonEntity(JerotesVillageEntityType.SERPON_CHARIOT.get(), 1, 12);
					this.roundSummons += 1;
				}
				if (this.summonAmount < this.summonAmountMax * 0.75 && roundSummons == 1) {
					//蛇龙突进者
					summonEntity(JerotesVillageEntityType.SERPON_INTRUDER.get(), 1, 12);
					//皇家战蟒
					summonEntity(JerotesVillageEntityType.ROYAL_WAR_PYTHON.get(), 2, 12);
					//火灵
					summonEntity(JerotesVillageEntityType.FLAMOAR.get(), 1, 12);
					this.roundSummons += 1;
				}
				if (this.summonAmount < this.summonAmountMax * 0.5 && roundSummons == 2) {
					//蛇龙突进者
					summonEntity(JerotesVillageEntityType.SERPON_INTRUDER.get(), 1, 12);
					//蛇龙火焰使徒
					summonEntity(JerotesVillageEntityType.SERPON_FIRE_APOSTLE.get(), 1, 12);
					//蛇龙战车
					summonEntity(JerotesVillageEntityType.SERPON_CHARIOT.get(), 1, 12);
					this.roundSummons += 1;
				}
				if (this.summonAmount < this.summonAmountMax * 0.25 && roundSummons == 3) {
					//蛇龙突进者
					summonEntity(JerotesVillageEntityType.SERPON_INTRUDER.get(), 2, 12);
					//皇家战蟒
					summonEntity(JerotesVillageEntityType.ROYAL_WAR_PYTHON.get(), 3, 12);
					//火灵
					summonEntity(JerotesVillageEntityType.FLAMOAR.get(), 3, 12);
					this.roundSummons += 1;
				}
			}
			//第七波
			else if (round == 7) {
				if (this.summonAmount < this.summonAmountMax * 0.95 && roundSummons == 0) {
					//蛇龙突进者
					summonEntity(JerotesVillageEntityType.SERPON_INTRUDER.get(), 2, 12);
					//皇家战蟒
					summonEntity(JerotesVillageEntityType.ROYAL_WAR_PYTHON.get(), 1, 12);
					//蛇龙战车
					summonEntity(JerotesVillageEntityType.SERPON_CHARIOT.get(), 1, 12);
					this.roundSummons += 1;
				}
				if (this.summonAmount < this.summonAmountMax * 0.75 && roundSummons == 1) {
					//蛇龙突进者
					summonEntity(JerotesVillageEntityType.SERPON_INTRUDER.get(), 1, 12);
					//蛇龙战斗平台
					summonEntity(JerotesVillageEntityType.SERPON_COMBAT_PLATFORM.get(), 1, 12);
					this.roundSummons += 1;
				}
				if (this.summonAmount < this.summonAmountMax * 0.5 && roundSummons == 2) {
					//皇家战蟒
					summonEntity(JerotesVillageEntityType.ROYAL_WAR_PYTHON.get(), 2, 12);
					//蛇龙战斗平台
					summonEntity(JerotesVillageEntityType.SERPON_COMBAT_PLATFORM.get(), 1, 12);
					this.roundSummons += 1;
				}
				if (this.summonAmount < this.summonAmountMax * 0.25 && roundSummons == 3) {
					//蛇龙火焰使徒
					summonEntity(JerotesVillageEntityType.SERPON_FIRE_APOSTLE.get(), 2, 12);
					//蛇龙战斗平台
					summonEntity(JerotesVillageEntityType.SERPON_COMBAT_PLATFORM.get(), 1, 12);
					this.roundSummons += 1;
				}
			}
			//第八波
			else if (round == 8) {
				if (this.summonAmount < this.summonAmountMax * 0.95 && roundSummons == 0) {
					//蛇龙突进者
					summonEntity(JerotesVillageEntityType.SERPON_INTRUDER.get(), 2, 12);
					//火灵
					summonEntity(JerotesVillageEntityType.FLAMOAR.get(), 2, 12);
					this.roundSummons += 1;
				}
				if (this.summonAmount < this.summonAmountMax * 0.75 && roundSummons == 1) {
					//蛇龙突进者
					summonEntity(JerotesVillageEntityType.SERPON_INTRUDER.get(), 1, 12);
					//蛇龙战斗平台
					summonEntity(JerotesVillageEntityType.SERPON_COMBAT_PLATFORM.get(), 1, 12);
					this.roundSummons += 1;
				}
				if (this.summonAmount < this.summonAmountMax * 0.5 && roundSummons == 2) {
					//火灵
					summonEntity(JerotesVillageEntityType.FLAMOAR.get(), 2, 12);
					//蛇龙战斗平台
					summonEntity(JerotesVillageEntityType.SERPON_COMBAT_PLATFORM.get(), 1, 12);
					this.roundSummons += 1;
				}
				if (this.summonAmount < this.summonAmountMax * 0.25 && roundSummons == 3) {
					//蛇龙火焰使徒
					summonEntity(JerotesVillageEntityType.SERPON_FIRE_APOSTLE.get(), 3, 12);
					//火灵
					summonEntity(JerotesVillageEntityType.FLAMOAR.get(), 3, 12);
					this.roundSummons += 1;
					this.level().explode(this, this.getX(), this.getY(), this.getZ(), 5.0f, false, Level.ExplosionInteraction.MOB);
					if (!this.level().isClientSide()) {
						this.setTrueCome(true);
					}
					this.round += 1;
					this.bossEvent.setName(Objects.requireNonNull(this.getDisplayName()));
					this.bossEvent.setProgress(this.getHealth() / this.getMaxHealth());
					if (this.level() instanceof ServerLevel serverLevel) {
						for (int counts = 0; counts < 360; ++counts) {
							serverLevel.sendParticles(ParticleTypes.CAMPFIRE_SIGNAL_SMOKE, this.getRandomX(1), this.getRandomY(), this.getRandomZ(1), 4, 0, 0, 0, 0);
						}
					}
					if (this.level().isClientSide) {
						for (int i = 0; i < 64; ++i) {
							this.level().addParticle(ParticleTypes.FLAME, this.getRandomX(4.0), this.getY((2.0D * this.random.nextDouble() - 1.0D) * 2.0), this.getRandomZ(4.0), 0.0, 0.0, 0.0);
						}
					}
				}
			}
		}
		else {
			super.tick();
		}
		if (!level().isClientSide && getBossMusic() != null) {
			if (!isSilent() && getTarget() instanceof Player player && player.isAlive()) {
				this.level().broadcastEntityEvent(this, (byte)67);
			}
			else {
				this.level().broadcastEntityEvent(this, (byte)68);
			}
		}
	}

	public void summonEntity(EntityType entityType, int count, int summonDistance) {
		if (this.level() instanceof ServerLevel serverLevel) {
			for (int countss = 0; countss < Other.getRaidBossCount(this); ++countss) {
				for (int i = 0; i < count; ++i) {
					BlockPos summonPos = Main.findSpawnPositionNearFill(this, summonDistance);
					if (summonPos.getY() < this.getY()) {
						summonPos = this.getOnPos().above();
					}
					Entity serpon = entityType.spawn(serverLevel, BlockPos.containing(summonPos.getX(), summonPos.getY(), summonPos.getZ()), MobSpawnType.MOB_SUMMONED);
					PlayerTeam teams = (PlayerTeam) this.getTeam();
					if (teams != null && serpon != null) {
						serverLevel.getScoreboard().addPlayerToTeam(serpon.getStringUUID(), teams);
					}

					if (entityType != JerotesVillageEntityType.SERPON_CHARIOT.get() && entityType != JerotesVillageEntityType.SERPON_COMBAT_PLATFORM.get()) {
						if (serpon instanceof Mob mobSerpon) {
							for (int counts = 0; counts < 12; ++counts) {
								serverLevel.sendParticles(ParticleTypes.FLAME, mobSerpon.getRandomX(0.5), mobSerpon.getRandomY(), mobSerpon.getRandomZ(0.5), 4, 0, 0, 0, 0);
							}
							if (this.getTarget() != null) {
								mobSerpon.setTarget(this.getTarget());
							}
						}
						this.summonMaxHealth += 1;
					}
					else if (entityType == JerotesVillageEntityType.SERPON_CHARIOT.get()) {
						if (serpon instanceof SerponChariotEntity serponChariot) {
							for (int counts = 0; counts < 12; ++counts) {
								serverLevel.sendParticles(ParticleTypes.FLAME, serponChariot.getRandomX(0.5), serponChariot.getRandomY(), serponChariot.getRandomZ(0.5), 4, 0, 0, 0, 0);
							}
							serponChariot.setTarget(this.getTarget());
							for (int i2 = 0; i2 < 3; ++i2) {
								SerponColonialSoldierEntity serponColonialSoldier = JerotesVillageEntityType.SERPON_COLONIAL_SOLDIER.get().spawn(serverLevel, BlockPos.containing(summonPos.getX(), summonPos.getY(), summonPos.getZ()), MobSpawnType.MOB_SUMMONED);
								if (serponColonialSoldier != null) {
									if (i2 == 0) {
										serponColonialSoldier.setItemSlot(EquipmentSlot.MAINHAND, new ItemStack(JerotesVillageItems.SERPON_SWORD.get()));
									} else {
										serponColonialSoldier.setItemSlot(EquipmentSlot.MAINHAND, new ItemStack(JerotesVillageItems.SERPON_BLOWPIPE.get()));
									}
									if (!this.level().isClientSide) {
										serponColonialSoldier.startRiding(serponChariot);
										serponColonialSoldier.setPrepare(this.isPrepare(), true);
									}
									if (teams != null) {
										serverLevel.getScoreboard().addPlayerToTeam(serponColonialSoldier.getStringUUID(), teams);
									}
								}
							}
						}
						this.summonMaxHealth += 3;
					} else if (entityType == JerotesVillageEntityType.SERPON_COMBAT_PLATFORM.get()) {
						if (serpon instanceof SerponCombatPlatformEntity serponCombatPlatform) {
							for (int counts = 0; counts < 12; ++counts) {
								serverLevel.sendParticles(ParticleTypes.FLAME, serponCombatPlatform.getRandomX(0.5), serponCombatPlatform.getRandomY(), serponCombatPlatform.getRandomZ(0.5), 4, 0, 0, 0, 0);
							}
							serponCombatPlatform.setTarget(this.getTarget());
							for (int i2 = 0; i2 < 5; ++i2) {
								SerponEntity serpon2;
								if (i2 == 0 || i2 == 1 || i2 == 2) {
									serpon2 = JerotesVillageEntityType.SERPON_RIOTER.get().spawn(serverLevel, BlockPos.containing(summonPos.getX(), summonPos.getY(), summonPos.getZ()), MobSpawnType.MOB_SUMMONED);
									if (serpon2 != null) {
										if (!this.level().isClientSide) {
											serpon2.startRiding(serponCombatPlatform);
											serpon2.setPrepare(this.isPrepare(), true);
										}
										if (teams != null) {
											serverLevel.getScoreboard().addPlayerToTeam(serpon2.getStringUUID(), teams);
										}
									}
								} else {
									serpon2 = JerotesVillageEntityType.SERPON_FLAME_WIZARD.get().spawn(serverLevel, BlockPos.containing(summonPos.getX(), summonPos.getY(), summonPos.getZ()), MobSpawnType.MOB_SUMMONED);
									if (serpon2 != null) {
										if (!this.level().isClientSide) {
											serpon2.startRiding(serponCombatPlatform);
											serpon2.setPrepare(this.isPrepare(), true);
										}
										if (teams != null) {
											serverLevel.getScoreboard().addPlayerToTeam(serpon2.getStringUUID(), teams);
										}
									}
								}
							}
						}
						this.summonMaxHealth += 5;
					}
					if (serpon instanceof Mob mob) {
						if (serpon instanceof SerponEntity serponEntity) {
							if (!this.level().isClientSide) {
								serponEntity.setPrepare(this.isPrepare(), true);
							}
						}
						if (!mob.isInvisible()) {
							serverLevel.sendParticles(JerotesVillageParticleTypes.SERPON_PORTAL.get(), mob.getX(), mob.getY(0.5), mob.getZ(), 0, 0.0, 0.0, 0.0, 0.0);
						}
					}
				}
				serverLevel.gameEvent(GameEvent.ENTITY_PLACE, new BlockPos((int) this.getX(), (int) this.getY(), (int) this.getZ()), GameEvent.Context.of(this));
			}
		}
	}

	@Override
	public void aiStep() {
		super.aiStep();
		this.bossEvent.update();
		if (this.isTrueCome()) {
			//特殊近战
			if (this.getFlameAttackTick() > 0) {
				if (this.level().isClientSide()) {
					for (int i = 0; i < 5; ++i) {
						this.level().addParticle(ParticleTypes.FLAME, this.getRandomX(4.0), this.getY((2.0D * this.random.nextDouble() - 1.0D) * 2.0), this.getRandomZ(4.0), 0.0, 0.0, 0.0);
					}
				}
				for (int i = 0; i < 24; i++) {
					double angle = (Math.PI * 2 * i) / 24;
					double radius = 5;
					double offsetX = Math.cos(angle) * radius;
					double offsetZ = Math.sin(angle) * radius;
					this.level().addParticle(ParticleTypes.FLAME, this.getX() + offsetX, this.getY(), this.getZ() + offsetZ, 0, 0.12, 0);
				}
			}
			if (this.getFlameAttackTick() == 10) {
				this.FlameAttack();
			}
			if (this.getWindAttackTick() > 0) {
				if (this.level().isClientSide()) {
					for (int i = 0; i < 5; ++i) {
						this.level().addParticle(ParticleTypes.FLAME, this.getRandomX(4.0), this.getY((2.0D * this.random.nextDouble() - 1.0D) * 2.0), this.getRandomZ(4.0), 0.0, 0.0, 0.0);
					}
				}
			}
			if (this.getWindAttackTick() == 10) {
				this.WindAttack();
			}
			//冲刺
			if (this.getTarget() != null && !this.specialAction() && this.distanceTo(this.getTarget()) > 12 && Main.randomReach(this.getRandom(), 1, 40) == 1) {
				this.RushAttack(true);
			}
			//近战攻击
			if (!this.level().isClientSide()) {
				this.setAttackTick(Math.max(0, this.getAttackTick() - 1));
			}
			if (this.getAttackTick() == 10 && this.isAlive() && !this.getMainHandItem().isEmpty()) {
				this.trueHurt();
			}
			if (this.getAttackTick() > 18) {
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
			//站立动画
			if (this.isAlive()) {
				this.idleAnimationState.startIfStopped(this.tickCount);
			}
			//粒子
			if (this.getSpellChance() == 1) {
				Vec3 playerPos = this.position().add(0, this.getY(0.5) - this.getY(), 0);
				double radius = 3.0;
				int particleCount = 40;
				for (int i = 0; i < particleCount; i++) {
					double angle = this.getRandom().nextDouble() * Math.PI * 2;
					double inclination = this.getRandom().nextDouble() * Math.PI;
					double offsetX = radius * Math.sin(inclination) * Math.cos(angle);
					double offsetY = radius * Math.cos(inclination);
					double offsetZ = radius * Math.sin(inclination) * Math.sin(angle);
					Vec3 particlePos = playerPos.add(offsetX, offsetY, offsetZ);
					Vec3 direction = playerPos.subtract(particlePos).normalize();
					double speed = 0.15;
					Vec3 velocity = direction.scale(speed);
					this.level().addParticle(ParticleTypes.FLAME, particlePos.x, particlePos.y, particlePos.z, velocity.x, velocity.y, velocity.z);
				}
			}
			//破坏方块
			if (this.blockDestroyTick > 0) {
				this.blockDestroyTick -= 1;
			}
			if (this.blockDestroyTick <= 0 && (this.horizontalCollision || this.getTarget() != null && this.getTarget().getY() > this.getY() && this.verticalCollision) && this.level().getGameRules().getBoolean(GameRules.RULE_MOBGRIEFING)) {
				boolean canBlockDestroy = this.level().getGameRules().getBoolean(GameRules.RULE_MOBGRIEFING);
				boolean blockDestroy = Main.BlockDestroy(this, 5f);
				if (blockDestroy) {
					this.blockDestroyTick = 30;
				}
				if (!this.level().isClientSide()) {
					if (!canBlockDestroy && this.onGround()) {
						this.jumpFromGround();
					}
				}
			}
			//特殊技能
			{
				int spellLevel = this.getSpellLevel() - 1;
				if (this.level().getDifficulty() == Difficulty.NORMAL) {
					spellLevel = this.getSpellLevel();
				} else if (this.level().getDifficulty() == Difficulty.HARD) {
					spellLevel = this.getSpellLevel() + 1;
				}
				//基础技能
				{
					if (this.getUseSpellTick() < 0 && this.getUseSpellTick() > -60) {
						if (this.getTarget() != null && !this.isNoAi()) {
							//技能-蛇龙热风术
							if (this.getSpellChance() == 1) {
								if (this.getUseSpellTick() == -20 || this.getUseSpellTick() == -30 || this.getUseSpellTick() == -40 || this.getUseSpellTick() == -50) {
									//法术列表-蛇龙火球
									OtherSpellList.SerponSirocco(this.getSpellLevel(), this, this.getTarget()).spellUse();
								}
							}
							//技能-蛇龙刺枪之雨
							else if (this.getSpellChance() == 2) {
								if (this.getUseSpellTick() == -20 || this.getUseSpellTick() == -30 || this.getUseSpellTick() == -40 || this.getUseSpellTick() == -50) {
									OtherSpellFind.RainofSerponPike(this, this.getTarget(), spellLevel * 4, spellLevel * 8, 4, 12);
									if (!this.isSilent()) {
										this.level().playSound(null, this.getX(), this.getY(), this.getZ(), JerotesVillageSoundEvents.SERPON_COMBAT_EXECUTER_JAVELIN, this.getSoundSource(), 10.0f, 0.8f + this.random.nextFloat() * 0.4f);
									}
								}
							}
							//技能-瑟潘珀斯陨石术
							else if (this.getSpellChance() == 3) {
								if (this.getUseSpellTick() == -30 || this.getUseSpellTick() == -40) {
									//法术列表-瑟潘珀斯陨石术
									OtherSpellList.SerpenperthMeteor(this.getSpellLevel(), this, this.getTarget()).spellUse();
								}
							}
						}
					}
					if (this.getUseSpellTick() <= -60) {
						if (!this.level().isClientSide()) {
							this.setUseSpellTick((int) (OtherMainConfig.SerponCombatExecuterSpellCooldown * 20));
							this.setSpellChance(0);
						}
					}
					if (this.getUseSpellTick() == 0 && this.getTarget() != null && this.distanceTo(this.getTarget()) < 64 && Main.randomReach(this.getRandom(), 1, 120) == 1) {
						this.RushAttack(false);
						if (!this.level().isClientSide()) {
							this.setUseSpellTick(this.getUseSpellTick() - 1);
						}
						int spellChance = Main.randomReach(this.getRandom(), 1, 100);
						//技能-蛇龙热风术
						if (spellChance > 60) {
							if (!this.level().isClientSide()) {
								this.setSpellChance(1);
								this.setAnimTick(60);
								this.setAnimationState("hotWindSpell");
							}
						}
						//技能-蛇龙刺枪之雨
						else if (spellChance > 20) {
							if (!this.level().isClientSide()) {
								this.setSpellChance(2);
								this.setAnimTick(60);
								this.setAnimationState("pikeSpell");
							}
							if (!this.isSilent()) {
								this.level().playSound(null, this.getX(), this.getY(), this.getZ(), JerotesVillageSoundEvents.SERPON_COMBAT_EXECUTER_JAVELIN, this.getSoundSource(), 10.0f, 0.8f + this.random.nextFloat() * 0.4f);
							}
						}
						//技能-瑟潘珀斯陨石术
						else {
							if (!this.level().isClientSide()) {
								this.setSpellChance(3);
								this.setAnimTick(60);
								this.setAnimationState("meteorSpell");
							}
						}
					}
				}
				//技能-卸甲之怒
				{
					if (this.isTrueCome() && this.getHealth() <= this.getMaxHealth() / 2 && this.isAlive() && !this.isFury()) {
						if (!this.level().isClientSide()) {
							this.setFury(true);
						}
						if (this.level() instanceof ServerLevel serverLevel) {
							if (!this.isInvisible()) {
								serverLevel.sendParticles(JerotesVillageParticleTypes.STRIPPED_FURY_DISPLAY.get(), this.getX(), this.getBoundingBox().maxY + 0.5, this.getZ(), 0, 0.0, 0.0, 0.0, 0);
							}
						}
					}
				}
				//蛇龙诅咒
				{
					if (this.getTarget() instanceof Player player && player.isAlive() && this.getCurseTick() >= 800) {
						this.setXRot(45);
						if (!this.level().isClientSide()) {
							this.setIsCurseTick(60);
							this.setCurseTick(0);
						}
					}
					if (this.getIsCurseTick() > 0) {
						if (!this.level().isClientSide()) {
							this.goalSelector.setControlFlag(Goal.Flag.MOVE, false);
							this.goalSelector.setControlFlag(Goal.Flag.JUMP, false);
							this.goalSelector.setControlFlag(Goal.Flag.LOOK, false);
						}
						this.getNavigation().stop();
						if (this.level() instanceof ServerLevel serverLevel) {
							for (int i = 0; i < 4; i++) {
								double angle = (Math.PI * 2 * (getIsCurseTick() + i * 10)) / 60;
								double radius = 1.45;
								double offsetX = Math.cos(angle) * radius;
								double offsetZ = Math.sin(angle) * radius;
								serverLevel.sendParticles(ParticleTypes.FLAME, this.getX() + offsetX, this.getY(1.1f), this.getZ() + offsetZ, 0, 0, 0.12, 0, 1);
								serverLevel.sendParticles(ParticleTypes.ENCHANT, this.getX() + offsetX * 1.25f, this.getY(1.1f), this.getZ() + offsetZ * 1.25f, 0, 0, 0.15, 0, 1);
							}
							for (int i = 0; i < 8; i++) {
								double angle = (Math.PI * 2 * (getIsCurseTick() + i * 5)) / 60;
								double radius = 4;
								double offsetX = Math.cos(angle) * radius;
								double offsetZ = Math.sin(angle) * radius;
								serverLevel.sendParticles(ParticleTypes.FLAME, this.getX() + offsetX, this.getY(1.1f), this.getZ() + offsetZ, 0, 0, 0.12, 0, 1);
								serverLevel.sendParticles(ParticleTypes.ENCHANT, this.getX() + offsetX * 1.25f, this.getY(1.1f), this.getZ() + offsetZ * 1.25f, 0, 0, 0.15, 0, 1);
							}

							Vec3 playerPos = this.position().add(0, this.getY(0.5) - this.getY(), 0);
							double radius = 3.0;
							int particleCount = 60;
							for (int i = 0; i < particleCount; i++) {
								double angle = this.getRandom().nextDouble() * Math.PI * 2;
								double inclination = this.getRandom().nextDouble() * Math.PI;
								double offsetX = radius * Math.sin(inclination) * Math.cos(angle);
								double offsetY = radius * Math.cos(inclination);
								double offsetZ = radius * Math.sin(inclination) * Math.sin(angle);
								Vec3 particlePos = playerPos.add(offsetX, offsetY, offsetZ);
								Vec3 direction = playerPos.subtract(particlePos).normalize();
								double speed = 0.15;
								Vec3 velocity = direction.scale(speed);
								serverLevel.sendParticles(ParticleTypes.ENCHANTED_HIT, particlePos.x, particlePos.y, particlePos.z, 0, velocity.x, velocity.y, velocity.z, 1);
							}
						}
					}
				}
				//最后一击
				{
					if (this.getLastTick() > 0) {
						if (this.level() instanceof ServerLevel serverLevel) {
							for (int i = 0; i < 3; i++) {
								double angle = (Math.PI * 2 * i) / 3;
								double radius = 0.45;
								double offsetX = Math.cos(angle) * radius;
								double offsetZ = Math.sin(angle) * radius;
								serverLevel.sendParticles(ParticleTypes.FLAME, this.getX() + offsetX, this.getY(0.65f), this.getZ() + offsetZ, 0, 0, 0.12, 0, 1);
							}
						}
					}
				}
				if (!this.level().isClientSide()) {
					this.setSpecialBlockTick(Math.max(0, this.getSpecialBlockTick() - 1));
					this.setFlameAttackTick(Math.max(0, this.getFlameAttackTick() - 1));
					this.setWindAttackTick(Math.max(0, this.getWindAttackTick() - 1));
					if (this.getUseSpellTick() > 0 || (this.getUseSpellTick() < 0 && this.getUseSpellTick() > -60)) {
						this.setUseSpellTick(this.getUseSpellTick() - 1);
					}
					if (this.isFury()) {
						this.setCurseTick(Math.min(800, this.getCurseTick() + 1));
					}
					this.setIsCurseTick(Math.max(0, this.getIsCurseTick() - 1));
					this.setLastTick(Math.max(0, this.getLastTick() - 1));
				}
			}
		}
	}

	@Override
	public boolean doHurtTarget(Entity entity) {
		if (!this.isTrueCome()) {
			return false;
		}
		if (!this.specialAction() && this.getAttackTick() <= 0) {
			if (this.getSpecialMeleeChance() > 0 || this.getRandom().nextFloat() >= OtherMainConfig.SerponCombatExecuterFlameSlashChance + OtherMainConfig.SerponCombatExecuterWhirlwindSlashChance) {
				//常规攻击
				if (!this.level().isClientSide()) {
					this.setAttackTick(20);
					if (this.getSpecialMeleeChance() > 0) {
						this.setSpecialMeleeChance(this.getSpecialMeleeChance() - 1);
					}
					int attackRandom = this.getRandom().nextInt(40);
					if (this.getMainHandItem().isEmpty()) {
						this.setAnimTick(5);
						if (attackRandom < 30) {
							this.setAnimationState("attack1");
						} else {
							this.setAnimationState("attack2");
						}
					}
					else {
						this.setAnimTick(20);
						if (attackRandom > 30) {
							this.setAnimationState("swordAttack1");
						} else if (attackRandom > 20) {
							this.setAnimationState("swordAttack2");
						} else if (attackRandom > 10) {
							this.setAnimationState("swordAttack3");
						} else {
							this.setAnimationState("swordAttack4");
						}
					}
				}
				return true;
			}
			else {
				//技能-火焰斩
				if (this.getRandom().nextFloat() < OtherMainConfig.SerponCombatExecuterFlameSlashChance / (OtherMainConfig.SerponCombatExecuterFlameSlashChance + OtherMainConfig.SerponCombatExecuterWhirlwindSlashChance)) {
					if (!this.level().isClientSide()) {
						this.setAttackTick(0);
						this.setFlameAttackTick(25);
						this.setSpecialMeleeChance(3);
						this.setAnimTick(15);
						this.setAnimationState("flameAttack");
					}
					if (this.level() instanceof ServerLevel serverLevel) {
						if (!this.isInvisible()) {
							serverLevel.sendParticles(JerotesVillageParticleTypes.FLAME_SLASH_DISPLAY.get(), this.getX(), this.getBoundingBox().maxY + 0.5, this.getZ(), 0, 0.0, 0.0, 0.0, 0);
						}
					}
				}
				//技能-旋风斩
				else {
					if (!this.level().isClientSide()) {
						this.setAttackTick(0);
						this.setWindAttackTick(25);
						this.setSpecialMeleeChance(3);
						this.setAnimTick(15);
						this.setAnimationState("windAttack");
					}
					if (this.level() instanceof ServerLevel serverLevel) {
						if (!this.isInvisible()) {
							serverLevel.sendParticles(JerotesVillageParticleTypes.WHIRLWIND_SLASH_DISPLAY.get(), this.getX(), this.getBoundingBox().maxY + 0.5, this.getZ(), 0, 0.0, 0.0, 0.0, 0);
						}
					}
				}
				return true;
			}
		}
		return false;
	}
	public boolean trueHurt() {
		if (!this.isSilent()) {
			this.playSound(JerotesVillageSoundEvents.SERPON_COMBAT_EXECUTER_ATTACK, 5.0f, 1.0f);
		}
		List<LivingEntity> list = this.level().getEntitiesOfClass(LivingEntity.class, this.getAttackBoundingBox().inflate(0.5));
		for (LivingEntity hurt : list) {
			if (hurt == null) continue;
			if ((this.distanceToSqr(hurt)) > Double.MAX_VALUE) continue;
			if (AttackFind.FindCanNotAttack(this, hurt)) continue;
			if (!this.hasLineOfSight(hurt)) continue;
			if (!Main.canSee(hurt, this)) continue;
			AttackFind.attackBegin(this, hurt);
			boolean bl = AttackFind.attackAfter(this, hurt, Math.min(1, 0.5f + serponStrengthBase()/1.5f), 0.5f, false, 0f);
			if (bl) {
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
		//横扫效果
		Main.sweepAttack(this);
		if (JerotesGameRules.JEROTES_MELEE_CAN_BREAK != null && this.level().getLevelData().getGameRules().getBoolean(JerotesGameRules.JEROTES_MELEE_CAN_BREAK)) {
			ItemStack hand = this.getMainHandItem();
			hand.hurtAndBreak(1, this, player -> player.broadcastBreakEvent(EquipmentSlot.MAINHAND));
		}
		return true;
	}
	public boolean FlameAttack() {
		if (!this.isSilent()) {
			this.playSound(JerotesVillageSoundEvents.SERPON_COMBAT_EXECUTER_ATTACK, 5.0f, 1.0f);
		}
		//技能-火焰斩
		if (!this.isSilent()) {
			this.level().playSound(null, this.getX(), this.getY(), this.getZ(), JerotesVillageSoundEvents.SERPON_COMBAT_EXECUTER_FLAME, this.getSoundSource(), 10.0f, 1.0f);
		}
		List<LivingEntity> list = this.level().getEntitiesOfClass(LivingEntity.class, this.getAttackBoundingBox().inflate(0.75));
		for (LivingEntity attack : list) {
			if (AttackFind.FindCanNotAttack(this, attack)) continue;
			if (!this.hasLineOfSight(attack)) continue;
			if (attack instanceof Mob serpon && OtherEntityFactionFind.isFactionSerponCombatTeam(serpon) && serpon.getTarget() != this && this.getTarget() != serpon)
				continue;
			DamageSource damageSource = AttackFind.findDamageType(this, JerotesDamageTypes.FIRE_MELEE, this);
			AttackFind.attackBegin(this, attack);
			boolean bl2 = AttackFind.attackAfterCustomDamage(this, attack, damageSource, 2.5f * (0.5f + serponStrengthBase()/1.5f), 1.0f, false, 0f);
			if (bl2) {
				attack.setSecondsOnFire(32);
				attack.knockback(2.0, Mth.sin(this.getYRot() * 0.0175f), -Mth.cos(this.getYRot() * 0.0175f));
			}
			else {
				AttackFind.attackAfter(this, attack, 2f * serponStrengthBase(), 1.0f, false, 0f);
			}
			//横扫效果
			Main.sweepAttack(this);
			//火焰粒子
			double d = -Mth.sin(this.getYRot() * 0.017453292f);
			double d2 = Mth.cos(this.getYRot() * 0.017453292f);
			if (this.level() instanceof ServerLevel serverLevel) {
				for (int i = 0; i < 15; ++i) {
					double xs = this.getX() + d + (this.getRandom().nextFloat() - 0.5f) * 2 - (this.getX() - attack.getX()) / 2;
					double ys = this.getY(0.5) + this.getRandom().nextFloat() - 0.5f - (this.getY() - attack.getY()) / 2;
					double zs = this.getZ() + d2 + (this.getRandom().nextFloat() - 0.5f) * 2 - (this.getZ() - attack.getZ()) / 2;
					serverLevel.sendParticles(ParticleTypes.FLAME, xs, ys, zs, 0, d, 0.0, d2, 0.0);
				}
			}
			//生成火焰
			AABB aabb = AABB.ofSize(new Vec3(attack.getX(), attack.getY(), attack.getZ()), 4, 3, 4);
			if (this.level() instanceof ServerLevel serverLevel && this.level().getGameRules().getBoolean(GameRules.RULE_MOBGRIEFING)) {
				for (BlockPos blockPos : BlockPos.betweenClosed(Mth.floor(aabb.minX), Mth.floor(aabb.minY), Mth.floor(aabb.minZ), Mth.floor(aabb.maxX), Mth.floor(aabb.maxY), Mth.floor(aabb.maxZ))) {
					BlockState blockState = serverLevel.getBlockState(blockPos);
					if (blockState.isAir() && !serverLevel.getBlockState(blockPos.below()).isAir()) {
						serverLevel.setBlock(blockPos, Blocks.FIRE.defaultBlockState(), 2);
					}
				}
			}
		}
		Main.sweepAttack(this);
		return true;
	}

	public boolean WindAttack() {
		if (!this.isSilent()) {
			this.playSound(JerotesVillageSoundEvents.SERPON_COMBAT_EXECUTER_ATTACK, 5.0f, 1.0f);
		}
		//技能-旋风斩
		if (!this.isSilent()) {
			this.level().playSound(null, this.getX(), this.getY(), this.getZ(), JerotesVillageSoundEvents.SERPON_COMBAT_EXECUTER_WIND, this.getSoundSource(), 10.0f, 1.0f);
		}
		List<LivingEntity> list = this.level().getEntitiesOfClass(LivingEntity.class, this.getAttackBoundingBox().inflate(0.75));
		for (LivingEntity attack : list) {
			if (AttackFind.FindCanNotAttack(this, attack)) continue;
			if (!this.hasLineOfSight(attack)) continue;
			if (attack instanceof Mob serpon && OtherEntityFactionFind.isFactionSerponCombatTeam(serpon) && serpon.getTarget() != this && this.getTarget() != serpon)
				continue;
			attack.knockback(5.0, Mth.sin(this.getYRot() * 0.0175f), -Mth.cos(this.getYRot() * 0.0175f));
			DamageSource damageSource = AttackFind.findDamageType(this, DamageTypes.FALL, this);
			AttackFind.attackBegin(this, attack);
			boolean bl2 = AttackFind.attackAfterCustomDamage(this, attack, damageSource, 2.25f * (0.5f + serponStrengthBase()/1.5f), 3.0f, false, 0f);
			if (bl2) {
				double d = 0.0;
				if (attack.getAttribute(Attributes.KNOCKBACK_RESISTANCE) != null) {
					d = Math.max(attack.getAttributeValue(Attributes.KNOCKBACK_RESISTANCE), 1.0);
				}
				double d2 = Math.max(0, 1 - d) + 1.0;
				if (!EntityAndItemFind.isNoSpecialKnockback(attack.getType())) {
					attack.setDeltaMovement(attack.getDeltaMovement().add(-(this.getX() - attack.getX()) * 1.4 * (d2 + 0.3), -(this.getY() - attack.getY()) * 1.4 * (d2 + 0.35), -(this.getZ() - attack.getZ()) * 1.4 * (d2 + 0.3)));
				}
			}
			else {
				AttackFind.attackAfter(this, attack, 2f * serponStrengthBase(), 3.0f, false, 0f);
			}
			//横扫效果
			Main.sweepAttack(this);
			//横扫粒子
			double d3 = -Mth.sin(this.getYRot() * 0.017453292f);
			double d4 = Mth.cos(this.getYRot() * 0.017453292f);
			if (this.level() instanceof ServerLevel serverLevel) {
				for (int i = 0; i < 15; ++i) {
					double xs = this.getX() + d3 + (this.getRandom().nextFloat() - 0.5f) * 2 - (this.getX() - attack.getX()) / 2;
					double ys = this.getY(0.5) + this.getRandom().nextFloat() - 0.5f - (this.getY() - attack.getY()) / 2;
					double zs = this.getZ() + d4 + (this.getRandom().nextFloat() - 0.5f) * 2 - (this.getZ() - attack.getZ()) / 2;
					serverLevel.sendParticles(ParticleTypes.SWEEP_ATTACK, xs, ys, zs, 0, d3, 0.0, d4, 0.0);
				}
			}
			//下落方块
			AABB aabb = AABB.ofSize(new Vec3(this.getX(), this.getY(), this.getZ()), 7, 6, 7);
			if (this.level() instanceof ServerLevel serverLevel) {
				for (BlockPos blockPos : BlockPos.betweenClosed(Mth.floor(aabb.minX), Mth.floor(aabb.minY), Mth.floor(aabb.minZ), Mth.floor(aabb.maxX), Mth.floor(aabb.maxY), Mth.floor(aabb.maxZ))) {
					Main.spawnFallingBlockByPos(serverLevel, blockPos);
				}
			}
		}
		Main.sweepAttack(this);
		return true;
	}

	@Override
	public boolean isInvulnerableTo(DamageSource damageSource) {
		if (damageSource.is(DamageTypes.THORNS)
				|| damageSource.is(DamageTypes.CACTUS)
				|| damageSource.is(DamageTypeTags.IS_EXPLOSION) && this.isFury()
				|| damageSource.is(DamageTypes.SWEET_BERRY_BUSH)
				|| damageSource.is(DamageTypes.IN_WALL))
			return true;
		return super.isInvulnerableTo(damageSource);
	}
	@Override
	public boolean hurt(DamageSource damageSource, float amount) {
		if (isInvulnerableTo(damageSource)) {
			return super.hurt(damageSource, amount);
		}
		if (amount < this.getMaxHealth() * 20 && amount >= this.getHealth() && !this.isLast()) {
			if (!this.level().isClientSide()) {
				this.setLast(true);
				this.setLastTick(160);
			}
			return false;
		}
		if (amount < this.getMaxHealth() * 20 && this.getLastTick() > 0) {
			return false;
		}
		if (!this.isTrueCome()) {
			if (amount < 32000) {
				if (damageSource.getEntity() instanceof Player) {
					this.summonAmount -= amount;
				}
				return false;
			}
			return super.hurt(damageSource, amount);
		}
		boolean normalUse = this.isAggressive() && isDamageSourceBlocks(damageSource);
		boolean furyUse = !damageSource.is(DamageTypeTags.BYPASSES_INVULNERABILITY) && this.isFury();
		if (!this.isNoAi() && this.getIsCurseTick() <= 0 &&
				!this.isInvulnerableTo(damageSource) &&
				this.isTrueCome() && (normalUse || furyUse) && !this.getMainHandItem().isEmpty() && !this.specialAction() && this.isAlive()) {
			if (this.getSpecialBlockTick() <= 0) {
				if (!this.level().isClientSide()) {
					this.level().broadcastEntityEvent(this, (byte) 101);
				}
				if (this.getOffhandItem().isEmpty() &&
						this.getMainHandItem().getItem() instanceof ItemTwoHanded itemTwoHanded && itemTwoHanded.canBlock()) {
					if (this.getMainHandItem().getItem() instanceof ItemSpecialEffect specialEffect && !this.getMainHandItem().is(JerotesVillageItems.GLAIVE_OF_SERPENT_DOMINATER.get())) {
						specialEffect.blockUse(this, damageSource.getEntity(), damageSource);
					}
				}
				float chance = Math.max(1.5f - (serponStrengthBase()/3 - 1), 0.2f);
				if (this.isFury()) {
					chance = Math.max(0.35f - (serponStrengthBase()/3 - 1), 0.1f);
				}
				int attackDamage = (int) this.getAttributeValue(Attributes.ATTACK_DAMAGE) * 10;
				if (attackDamage < 2) {
					attackDamage = 2;
				}
				if (Main.randomReach(this.getRandom(), 0, attackDamage) > chance * amount) {
					if (!this.level().isClientSide()) {
						if (!this.isFury()) {
							this.setSpecialBlockTick(5);
						}
						else {
							this.setSpecialBlockTick(2);
						}
					}
					if (!this.isSilent()) {
						this.level().playSound(null, this.getX(), this.getY(), this.getZ(), JerotesVillageSoundEvents.SERPON_COMBAT_EXECUTER_BLOCK, this.getSoundSource(), 10.0f, 1.0f);
					}
					//反击
					if (this.getTarget() != null && this.getAttackTick() <= 0) {
						if (this.getAttackBoundingBox().intersects(this.getTarget().getBoundingBox())) {
							this.doHurtTarget(this.getTarget());
						}
						else {
							this.getLookControl().setLookAt(this.getTarget(), 360.0f, 360.0f);
							this.lookAt(this.getTarget(), 360.0f, 360.0f);
							this.RushAttack(true);
						}
					}
					//耐久度降低
					if (JerotesGameRules.JEROTES_MELEE_CAN_BREAK != null && this.level().getLevelData().getGameRules().getBoolean(JerotesGameRules.JEROTES_MELEE_CAN_BREAK)) {
						ItemStack hand = this.getMainHandItem();
						if (hand.getItem() != JerotesVillageItems.GLAIVE_OF_SERPENT_DOMINATER.get()) {
							hand.hurtAndBreak((int) amount / 3, this, player -> player.broadcastBreakEvent(EquipmentSlot.MAINHAND));
						}
					}
					return false;
				}
			}
		}
		if (this.isInvulnerableTo(damageSource)) {
			return super.hurt(damageSource, amount);
		}
		if (EntityAndItemFind.canSelfResistMagic(damageSource, amount, this) && this.isFury())
			return super.hurt(damageSource, amount / 3 / serponStrengthBase());
		return super.hurt(damageSource, amount / serponStrengthBase());
	}

	public boolean RushAttack(boolean bl) {
		if (this.level() instanceof ServerLevel serverLevel) {
			for (int i = 0; i < 5; ++i) {
				Vec3 vec3 = this.getDeltaMovement();
				serverLevel.sendParticles(ParticleTypes.SMOKE, this.getRandomX(0.5), this.getRandomY(), this.getRandomZ(0.5), 0, vec3.x, vec3.y - 0.1, vec3.z, 0);
				serverLevel.sendParticles(ParticleTypes.FLAME, this.getRandomX(0.5), this.getRandomY(), this.getRandomZ(0.5), 0, vec3.x, vec3.y - 0.1, vec3.z, 0);
			}
		}
		float f = this.getYRot();
		float f2 = this.getXRot();
		float f3 = -Mth.sin(f * 0.017453292f) * Mth.cos(f2 * 0.017453292f);
		float f4 = -Mth.sin(f2 * 0.017453292f);
		float f5 = Mth.cos(f * 0.017453292f) * Mth.cos(f2 * 0.017453292f);
		float f6 = Mth.sqrt(f3 * f3 + f4 * f4 + f5 * f5);
		float f7 = 0.5f + ((serponStrengthBase() - 1) * 0.15f);
		if (!bl) {
			this.setDeltaMovement(-(f3 *= f7 / f6 * 2), -(f4 *= f7 / f6 * 2), -(f5 *= f7 / f6 * 2));
		}
		else {
			this.setDeltaMovement(f3 *= f7 / f6 * 2, f4 *= f7 / f6 * 2, f5 *= f7 / f6 * 2);
		}
		if (!this.level().isClientSide()) {
			this.setAnimTick(20);
			this.setAnimationState("push");
		}
		if (!this.isSilent()) {
			this.level().playSound(null, this, JerotesVillageSoundEvents.SERPON_COMBAT_EXECUTER_DASH, SoundSource.NEUTRAL, 1.0f, 1.0f);
		}
		return true;
	}

	@Override
	public boolean canBeAffected(MobEffectInstance mobEffectInstance) {
		if (mobEffectInstance.getEffect() == JerotesMobEffects.ABACK.get()) {
			return false;
		}
		if (!this.isTrueCome() && mobEffectInstance.getEffect() != MobEffects.INVISIBILITY) {
			return false;
		}
		if (mobEffectInstance.getEffect() == MobEffects.POISON) {
			return false;
		}
		if (mobEffectInstance.getEffect() == JerotesMobEffects.DEADLY_POISON.get()) {
			return false;
		}
		if (mobEffectInstance.getEffect() == MobEffects.WEAKNESS) {
			return false;
		}
		if (mobEffectInstance.getEffect() == JerotesMobEffects.CORROSIVE.get()) {
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

	@Nullable
	@Override
	public SpawnGroupData finalizeSpawn(ServerLevelAccessor serverLevelAccessor, DifficultyInstance difficultyInstance, MobSpawnType mobSpawnType, @Nullable SpawnGroupData spawnGroupData, @Nullable CompoundTag compoundTag) {
		//this.setTrueCome(true);
		//this.setSupplement(true);
		//this.setFury(false);
		this.setShieldLevel(5);
		return super.finalizeSpawn(serverLevelAccessor, difficultyInstance, mobSpawnType, spawnGroupData, compoundTag);
	}

	@Override
	public ItemStack createSpawnWeapon(float weaponRandom) {
		return new ItemStack(JerotesVillageItems.GLAIVE_OF_SERPENT_DOMINATER.get());
	}

	@Override
	public void tickDeath() {
		if (this.getLastTick() > 0)
			return;
		if(deathTime <= 0) {
			if (!this.level().isClientSide()) {
				this.setAnimTick(40);
				this.setAnimationState("dead");
			}
		}
		++this.deathTime;
		if (this.deathTime >= 40 && !this.level().isClientSide() && !this.isRemoved()) {
			if (this.level() instanceof ServerLevel serverLevel) {
				serverLevel.sendParticles(JerotesVillageParticleTypes.SERPON_PORTAL.get(), this.getX(), this.getY(0.5), this.getZ(), 0, 0.0, 0.0, 0.0, 0.0);
			}
			this.remove(RemovalReason.CHANGED_DIMENSION);
		}
	}

	@Override
	protected void dropAllDeathLoot(DamageSource damageSource) {
		if (this.isTrueCome()) {
			super.dropAllDeathLoot(damageSource);
		}
	}
}