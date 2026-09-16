package com.jerotes.jerotesvillage.entity.Neutral;

import com.google.common.collect.Lists;
import com.jerotes.jerotes.entity.Interface.CarvedEntity;
import com.jerotes.jerotes.entity.Interface.FactionEntity;
import com.jerotes.jerotes.entity.Interface.JerotesEntity;
import com.jerotes.jerotes.entity.Mob.HumanEntity;
import com.jerotes.jerotes.goal.JerotesAttackAvoidEntityGoal;
import com.jerotes.jerotes.goal.JerotesHelpAlliesGoal;
import com.jerotes.jerotes.goal.JerotesHelpCarvedGoal;
import com.jerotes.jerotes.util.EntityFactionFind;
import com.jerotes.jerotesvillage.config.OtherMainConfig;
import com.jerotes.jerotesvillage.entity.Animal.CarvedHoundEntity;
import com.jerotes.jerotesvillage.entity.Animal.CarvedLlamaEntity;
import com.jerotes.jerotesvillage.entity.Monster.IllagerFaction.DefectorEntity;
import com.jerotes.jerotesvillage.entity.Shoot.Other.CarvedAllayAmethystEntity;
import com.jerotes.jerotesvillage.event.RelationshipEvent;
import com.jerotes.jerotesvillage.goal.CarvedAllayRangedAttackGoal;
import com.jerotes.jerotesvillage.goal.HelpVillagerGoal;
import com.jerotes.jerotesvillage.init.JerotesVillagePotions;
import com.jerotes.jerotesvillage.item.CarvedFlag;
import com.jerotes.jerotesvillage.util.OtherEntityFactionFind;
import net.minecraft.ChatFormatting;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.ListTag;
import net.minecraft.nbt.NbtUtils;
import net.minecraft.nbt.Tag;
import net.minecraft.network.chat.Component;
import net.minecraft.network.syncher.EntityDataAccessor;
import net.minecraft.network.syncher.EntityDataSerializers;
import net.minecraft.network.syncher.SynchedEntityData;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.util.RandomSource;
import net.minecraft.world.DifficultyInstance;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.*;
import net.minecraft.world.entity.ai.attributes.AttributeSupplier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.ai.behavior.BehaviorUtils;
import net.minecraft.world.entity.ai.goal.GolemRandomStrollInVillageGoal;
import net.minecraft.world.entity.ai.goal.LookAtPlayerGoal;
import net.minecraft.world.entity.ai.goal.MoveBackToVillageGoal;
import net.minecraft.world.entity.ai.goal.RandomLookAroundGoal;
import net.minecraft.world.entity.ai.goal.target.HurtByTargetGoal;
import net.minecraft.world.entity.ai.memory.MemoryModuleType;
import net.minecraft.world.entity.animal.IronGolem;
import net.minecraft.world.entity.animal.allay.Allay;
import net.minecraft.world.entity.monster.RangedAttackMob;
import net.minecraft.world.entity.npc.AbstractVillager;
import net.minecraft.world.entity.npc.Villager;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.entity.projectile.ThrownPotion;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.alchemy.Potion;
import net.minecraft.world.item.alchemy.PotionUtils;
import net.minecraft.world.item.alchemy.Potions;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.level.GameRules;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.ServerLevelAccessor;
import net.minecraft.world.phys.Vec3;

import javax.annotation.Nullable;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

public class CarvedAllayEntity extends Allay implements JerotesEntity, CarvedEntity, RangedAttackMob, FactionEntity {
	private static final EntityDataAccessor<Boolean> VEX = SynchedEntityData.defineId(CarvedAllayEntity.class, EntityDataSerializers.BOOLEAN);
	private static final EntityDataAccessor<Integer> DATA_TYPE_ID = SynchedEntityData.defineId(CarvedAllayEntity.class, EntityDataSerializers.INT);
	private static final EntityDataAccessor<Integer> HEAL_TICK = SynchedEntityData.defineId(CarvedAllayEntity.class, EntityDataSerializers.INT);
	private static final EntityDataAccessor<Byte> DATA_FLAGS_ID = SynchedEntityData.defineId(CarvedAllayEntity.class, EntityDataSerializers.BYTE);
	private static final EntityDataAccessor<Optional<UUID>> DATA_TRUSTED_ID_0 = SynchedEntityData.defineId(CarvedAllayEntity.class, EntityDataSerializers.OPTIONAL_UUID);
	private static final EntityDataAccessor<Optional<UUID>> DATA_TRUSTED_ID_1 = SynchedEntityData.defineId(CarvedAllayEntity.class, EntityDataSerializers.OPTIONAL_UUID);

	public CarvedAllayEntity(EntityType<? extends CarvedAllayEntity> entityType, Level level) {
		super(entityType, level);
	}
	public boolean isJerotesFlyingMob() {
		return true;
	}

	public static AttributeSupplier.Builder createAttributes() {
		AttributeSupplier.Builder builder = Mob.createMobAttributes();
		builder = builder.add(Attributes.MOVEMENT_SPEED, 0.12);
		builder = builder.add(Attributes.FLYING_SPEED, 0.12);
		builder = builder.add(Attributes.MAX_HEALTH, 20);
		builder = builder.add(Attributes.ARMOR, 4);
		builder = builder.add(Attributes.ATTACK_DAMAGE, 2);
		builder = builder.add(Attributes.FOLLOW_RANGE, 48);
		return builder;
	}


	@Override
	public String getFirstFactionTypeName() {
		return "copper_carved_company";
	}
	@Override
	public List<String> getFactionTypeUntilTame() {
		List<String> list = new ArrayList<>();
		list.add(getFirstFactionTypeName());
		list.add("villager");
		return list;
	}
	@Override
	protected void registerGoals() {
		this.goalSelector.addGoal(2, new CarvedAllayRangedAttackGoal(this, 1.25, 60, 18.0f));
		this.goalSelector.addGoal(3, new JerotesAttackAvoidEntityGoal<LivingEntity>(this, LivingEntity.class, 8.0f, 1.0, 1.2));
		this.goalSelector.addGoal(3, new MoveBackToVillageGoal(this, 0.6, false));
		this.goalSelector.addGoal(4, new GolemRandomStrollInVillageGoal(this, 0.6));
		this.goalSelector.addGoal(6, new LookAtPlayerGoal(this, Player.class, 6.0f));
		this.goalSelector.addGoal(6, new LookAtPlayerGoal(this, HumanEntity.class, 6.0f));
		this.goalSelector.addGoal(6, new LookAtPlayerGoal(this, AbstractVillager.class, 6.0f));
		this.goalSelector.addGoal(6, new LookAtPlayerGoal(this, IronGolem.class, 6.0f));
		this.goalSelector.addGoal(6, new LookAtPlayerGoal(this, CarvedAllayEntity.class, 6.0f));
		this.goalSelector.addGoal(6, new LookAtPlayerGoal(this, CarvedHoundEntity.class, 6.0f));
		this.goalSelector.addGoal(9, new LookAtPlayerGoal(this, CarvedLlamaEntity.class, 6.0f));
		this.goalSelector.addGoal(7, new RandomLookAroundGoal(this));
		this.targetSelector.addGoal(1, new HurtByTargetGoal(this));
		this.targetSelector.addGoal(1, new JerotesHelpCarvedGoal(this));
		this.targetSelector.addGoal(1, new HelpVillagerGoal(this));
		this.targetSelector.addGoal(1, new JerotesHelpAlliesGoal(this, LivingEntity.class, false, false, livingEntity -> livingEntity instanceof LivingEntity));
	}

	@Override
	public void performRangedAttack(LivingEntity livingEntity, float f) {
		CarvedAllayAmethystEntity allayAmethyst;
		allayAmethyst = new CarvedAllayAmethystEntity(this.level(), this);
		double d = livingEntity.getX() - this.getX();
		double d2 = livingEntity.getY(0.3333333333333333) - allayAmethyst.getY();
		double d3 = livingEntity.getZ() - this.getZ();
		double d4 = Math.sqrt(d * d + d3 * d3);
		allayAmethyst.shoot(d, d2 + d4 * 0.2, d3, 1, 0);

		allayAmethyst.setOwner(this);
		this.level().addFreshEntity(allayAmethyst);
		if (!this.isSilent()) {
			this.playSound(SoundEvents.ALLAY_THROW, 1.0f, 1.0f / (this.getRandom().nextFloat() * 0.4f + 0.8f));
		}
		this.setTarget(null);
		this.setAggressive(false);
	}

	public void rangeHealThrow(LivingEntity livingEntity) {
		ThrownPotion thrownPotion;
		thrownPotion = new ThrownPotion(this.level(), this);
		Potion potion = Potions.STRONG_HEALING;
		if (livingEntity.getMobType() == MobType.UNDEAD) {
			potion = Potions.STRONG_HARMING;
		}
		if (livingEntity.getType() == EntityType.VILLAGER || livingEntity.getType() == EntityType.WANDERING_TRADER || EntityFactionFind.isCarved(livingEntity.getType())  || livingEntity instanceof Player player && RelationshipEvent.MoreCopperCarvedCompanyRelationship(player, 500)) {
			potion = JerotesVillagePotions.CARVED_VILLAGE_ARROW.get();
		}
		thrownPotion.setItem(PotionUtils.setPotion(new ItemStack(Items.SPLASH_POTION), potion));
		double d = livingEntity.getX() - this.getX();
		double d2 = livingEntity.getY(0.3333333333333333) - this.getY();
		double d3 = livingEntity.getZ() - this.getZ();
		double d4 = Math.sqrt(d * d + d3 * d3);
		thrownPotion.shoot(d, d2 + d4 * 0.2, d3, 1, 0);

		thrownPotion.setOwner(this);
		this.level().addFreshEntity(thrownPotion);
		if (!this.isSilent()) {
			this.playSound(SoundEvents.ALLAY_THROW, 1.0f, 1.0f / (this.getRandom().nextFloat() * 0.4f + 0.8f));
		}
	}

	protected SoundEvent getAmbientSound() {
		if (this.isVexRace()) {
			return SoundEvents.VEX_AMBIENT;
		}
		return this.hasItemInSlot(EquipmentSlot.MAINHAND) ? SoundEvents.ALLAY_AMBIENT_WITH_ITEM : SoundEvents.ALLAY_AMBIENT_WITHOUT_ITEM;
	}
	protected SoundEvent getHurtSound(DamageSource p_218369_) {
		if (this.isVexRace()) {
			return SoundEvents.VEX_HURT;
		}
		return SoundEvents.ALLAY_HURT;
	}
	protected SoundEvent getDeathSound() {
		if (this.isVexRace()) {
			return SoundEvents.VEX_DEATH;
		}
		return SoundEvents.ALLAY_DEATH;
	}
	public boolean isEmerald(ItemStack stack) {
		return Ingredient.of(new ItemStack(Items.EMERALD)).test(stack);
	}


	public int getHealTick() {
		return this.getEntityData().get(HEAL_TICK);
	}
	public void setHealTick(int n) {
		this.getEntityData().set(HEAL_TICK, n);
	}
	@Override
	protected Component getTypeName() {
		if (this.isVexRace())
			return Component.translatable("entity.jerotesvillage.carved_allay.vex");
		return Component.translatable(this.getType().getDescriptionId());
	}
	@Override
	public void setCustomName(Component component) {
		String string = ChatFormatting.stripFormatting(component.getString());
		super.setCustomName(component);
		if ("Vex".equals(string)) {
			this.setVexRace(true);
		}
		else if ("Allay".equals(string)) {
			this.setVexRace(false);
		}
		else {
		}
	}
	public boolean isVexRace() {
		return this.getEntityData().get(VEX);
	}
	public void setVexRace(boolean bl) {
		this.getEntityData().set(VEX, bl);
	}
	@Override
	public void addAdditionalSaveData(CompoundTag compoundTag) {
		super.addAdditionalSaveData(compoundTag);
		List<UUID> list = this.getTrustedUUIDs();
		ListTag listTag = new ListTag();
		for (UUID uUID : list) {
			if (uUID == null) continue;
			listTag.add(NbtUtils.createUUID(uUID));
		}
		compoundTag.put("Trusted", listTag);
		compoundTag.putInt("HealTick", this.getHealTick());
		compoundTag.putBoolean("IsVex", this.isVexRace());
	}
	@Override
	public void readAdditionalSaveData(CompoundTag compoundTag) {
		super.readAdditionalSaveData(compoundTag);
		ListTag listTag = compoundTag.getList("Trusted", 11);
		for (Tag tag : listTag) {
			this.addTrustedUUID(NbtUtils.loadUUID(tag));
		}
		this.setHealTick(compoundTag.getInt("HealTick"));
		this.setVexRace(compoundTag.getBoolean("IsVex"));
	}
	@Override
	protected void defineSynchedData() {
		super.defineSynchedData();
		this.getEntityData().define(DATA_TRUSTED_ID_0, Optional.empty());
		this.getEntityData().define(DATA_TRUSTED_ID_1, Optional.empty());
		this.getEntityData().define(DATA_TYPE_ID, 0);
		this.getEntityData().define(HEAL_TICK, 0);
		this.getEntityData().define(DATA_FLAGS_ID, (byte)0);
		this.getEntityData().define(VEX, false);
	}
	@Override
	public void onSyncedDataUpdated(EntityDataAccessor<?> entityDataAccessor) {
		if (VEX.equals(entityDataAccessor)) {
			this.refreshDimensions();
		}
		super.onSyncedDataUpdated(entityDataAccessor);
	}
	List<UUID> getTrustedUUIDs() {
		ArrayList arrayList = Lists.newArrayList();
		arrayList.add(this.entityData.get(DATA_TRUSTED_ID_0).orElse(null));
		arrayList.add(this.entityData.get(DATA_TRUSTED_ID_1).orElse(null));
		return arrayList;
	}
	public void addTrustedUUID(@Nullable UUID uUID) {
		if (this.entityData.get(DATA_TRUSTED_ID_0).isPresent()) {
			this.entityData.set(DATA_TRUSTED_ID_1, Optional.ofNullable(uUID));
		} else {
			this.entityData.set(DATA_TRUSTED_ID_0, Optional.ofNullable(uUID));
		}
	}
	@Override
	protected void onOffspringSpawnedFromEgg(Player player, Mob mob) {
		((CarvedAllayEntity)this).addTrustedUUID(player.getUUID());
	}
	@Override
	public boolean trusts(UUID uUID) {
		return this.getTrustedUUIDs().contains(uUID);
	}

	@Override
	public InteractionResult mobInteract(Player player, InteractionHand interactionHand) {
		ItemStack itemstack = player.getItemInHand(interactionHand);
		if (itemstack.getItem() instanceof CarvedFlag) {
			float f1 = 1.0f + (this.random.nextFloat() - this.random.nextFloat()) * 0.2f;
			if (!this.isSilent()) {
				this.playSound(SoundEvents.ALLAY_ITEM_GIVEN, 1.0f, f1);
			}
			return InteractionResult.PASS;
		}
		if (!this.isAggressive() && this.isEmerald(itemstack)) {
			if (!this.level().isClientSide) {
				this.addEffect(new MobEffectInstance(MobEffects.GLOWING, 12000, 0));
				this.addEffect(new MobEffectInstance(MobEffects.JUMP, 12000, 0));
				this.addEffect(new MobEffectInstance(MobEffects.NIGHT_VISION, 12000, 0));
				this.addEffect(new MobEffectInstance(MobEffects.WATER_BREATHING, 12000, 0));
				this.addEffect(new MobEffectInstance(MobEffects.FIRE_RESISTANCE, 12000, 0));
				this.addEffect(new MobEffectInstance(MobEffects.LUCK, 12000, 0));
				this.addEffect(new MobEffectInstance(MobEffects.DAMAGE_BOOST, 12000, 1));
				this.addEffect(new MobEffectInstance(MobEffects.DAMAGE_RESISTANCE, 12000, 1));
				this.addEffect(new MobEffectInstance(MobEffects.MOVEMENT_SPEED, 12000, 1));
				this.addEffect(new MobEffectInstance(MobEffects.HEALTH_BOOST, 12000, 2));
				this.addEffect(new MobEffectInstance(MobEffects.HERO_OF_THE_VILLAGE, 12000, 4));
				this.addEffect(new MobEffectInstance(MobEffects.REGENERATION, 660, 1));
				this.addEffect(new MobEffectInstance(MobEffects.HEAL, 1, 3));
			}
			float f1 = 1.0f + (this.random.nextFloat() - this.random.nextFloat()) * 0.2f;
			if (!this.isSilent()) {
				this.playSound(SoundEvents.ALLAY_ITEM_GIVEN, 1.0f, f1);
			}
			if (!player.getAbilities().instabuild) {
				itemstack.shrink(1);
			}
			float f = (float)this.getY();
			Vec3 vec3 = this.getDeltaMovement();
			for (int i = 0; i < 10; ++i) {
				float f2 = (this.random.nextFloat() * 2.0f - 1.0f) * this.getBbWidth() * 0.5f;
				float f3 = (this.random.nextFloat() * 2.0f - 1.0f) * this.getBbWidth() * 0.5f;
				((Level)this.level()).addParticle(ParticleTypes.HAPPY_VILLAGER, this.getX() + (double)f2, f + 1f, this.getZ() + (double)f3, vec3.x, vec3.y, vec3.z);
			}
			if (!(this.trusts(player.getUUID()))) {
				RelationshipEvent.AddCopperCarvedCompanyRelationship(player, 5);
			}
			this.addTrustedUUID(player.getUUID());
			return InteractionResult.SUCCESS;
		}

		ItemStack itemStack = player.getItemInHand(interactionHand);
		ItemStack itemStack2 = this.getItemInHand(InteractionHand.MAIN_HAND);
		if (this.isDancing() && itemstack.getItem() == Items.AMETHYST_SHARD) {
			if (itemStack2.isEmpty() && !itemStack.isEmpty()) {
				ItemStack itemStack3 = itemStack.copyWithCount(1);
				this.setItemInHand(InteractionHand.MAIN_HAND, itemStack3);
				this.removeInteractionItem(player, itemStack);
				this.level().playSound(player, this, SoundEvents.ALLAY_ITEM_GIVEN, SoundSource.NEUTRAL, 2.0f, 1.0f);
				this.getBrain().setMemory(MemoryModuleType.LIKED_PLAYER, player.getUUID());
				return InteractionResult.SUCCESS;
			}
			if (!itemStack2.isEmpty() && interactionHand == InteractionHand.MAIN_HAND && itemStack.isEmpty()) {
				this.setItemSlot(EquipmentSlot.MAINHAND, ItemStack.EMPTY);
				this.level().playSound(player, this, SoundEvents.ALLAY_ITEM_TAKEN, SoundSource.NEUTRAL, 2.0f, 1.0f);
				this.swing(InteractionHand.MAIN_HAND);
				for (ItemStack itemStack4 : this.getInventory().removeAllItems()) {
					BehaviorUtils.throwItem(this, itemStack4, this.position());
				}
				this.getBrain().eraseMemory(MemoryModuleType.LIKED_PLAYER);
				player.addItem(itemStack2);
				return InteractionResult.SUCCESS;
			}
		}
		return super.mobInteract(player, interactionHand);
	}


	private void removeInteractionItem(Player player, ItemStack itemStack) {
		if (!player.getAbilities().instabuild) {
			itemStack.shrink(1);
		}
	}

	@Override
	public void aiStep() {
		super.aiStep();
		if (this.random.nextInt(900) == 1 && this.deathTime == 0) {
			this.heal(3.0f);
		}
		//停止战斗
		if (this.getTarget() != null && (!this.getTarget().isAlive() || this.getTarget() instanceof Player player && (player.isCreative() || player.isSpectator()))) {
			this.setTarget(null);
		}
		//
		if (!this.level().isClientSide()) {
			this.setHealTick(Math.max(0, this.getHealTick() - 1));
		}
		List<LivingEntity> list = this.level().getEntitiesOfClass(LivingEntity.class, this.getBoundingBox().inflate(32.0, 32.0, 32.0));
		double d = Double.MAX_VALUE;
		for (LivingEntity carved : list) {
			if (carved == null) continue;
			if ((this.distanceToSqr(carved)) > d) continue;
			if (carved == this) continue;
			if (carved instanceof Mob mob && mob.getTarget() == this) continue;
			if (this.getTarget() == carved) continue;
			if (this.getHealTick() > 0) continue;
			if (carved.getHealth() >= carved.getMaxHealth()) continue;
			if (OtherEntityFactionFind.isCarved(carved.getType()) || carved instanceof Villager || this.trusts(carved.getUUID()) || carved instanceof Player player && RelationshipEvent.MoreCopperCarvedCompanyRelationship(player, 500) || carved.isAlliedTo(this)) {
				if (this.distanceTo(carved) <= 8) {
					this.rangeHealThrow(carved);
					if (!this.level().isClientSide()) {
						this.setHealTick(80);
					}
				}
			}
		}
	}

	@Override
	public void awardKillScore(Entity entity, int score, DamageSource damageSource) {
		super.awardKillScore(entity, score, damageSource);
		if (!this.level().isClientSide) {
			this.addEffect(new MobEffectInstance(MobEffects.DAMAGE_BOOST, 660, 0));
			this.addEffect(new MobEffectInstance(MobEffects.REGENERATION, 660, 0));
			this.addEffect(new MobEffectInstance(MobEffects.DAMAGE_RESISTANCE, 660, 0));
			this.addEffect(new MobEffectInstance(MobEffects.MOVEMENT_SPEED, 660, 0));
		}
	}

	@Override
	public boolean hurt(DamageSource damageSource, float f) {
		if (isInvulnerableTo(damageSource)) {
			return super.hurt(damageSource, f);
		}
		boolean bl = super.hurt(damageSource, f);
		if (bl) {
			if (damageSource.getEntity() instanceof Player player && !this.trusts(player.getUUID()) && this.getTarget() == null) {
				RelationshipEvent.AddCopperCarvedCompanyRelationship(player, -2);
			}
		}
		return bl;
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
		if (this.trusts(entity.getUUID())) {
			return true;
		}
		if (entity instanceof Player player && RelationshipEvent.MoreCopperCarvedCompanyRelationship(player, 500)) {
			return this.getTeam() == null && entity.getTeam() == null;
		}
		if (entity instanceof LivingEntity livingEntity && EntityFactionFind.isFaction(this, livingEntity)) {
			return this.getTeam() == null && entity.getTeam() == null;
		}
		return false;
	}

	@Override
	public boolean canAttack(LivingEntity livingEntity) {
		if (this.trusts(livingEntity.getUUID())) {
			return false;
		}
		if (((this.getTeam() == null && livingEntity.getTeam() == null) || this.getTeam() == livingEntity.getTeam()) &&
				EntityFactionFind.isFaction(this, livingEntity)
		) {
			return false;
		}
		if (OtherMainConfig.CarvedCanNotAttackList.contains(livingEntity.getEncodeId())) {
			return false;
		}
		if (livingEntity instanceof DefectorEntity defector && defector.getTarget() != this && !defector.isMustEnemy()) {
			return false;
		}
		if (livingEntity instanceof Player player && RelationshipEvent.MoreCopperCarvedCompanyRelationship(player, 500)) {
			return false;
		}
		return super.canAttack(livingEntity);
	}

	@Override
	public void die(DamageSource damageSource) {
		if (damageSource.getEntity() instanceof Player player) {
			RelationshipEvent.AddCopperCarvedCompanyRelationship(player, -10);
		}
		if (!this.level().isClientSide() && this.level().getGameRules().getBoolean(GameRules.RULE_SHOWDEATHMESSAGES) && !this.getTrustedUUIDs().isEmpty()) {
			List<UUID> list = this.getTrustedUUIDs();
			for (UUID uUID : list) {
				if (uUID == null) continue;
				if (!(this.level() instanceof ServerLevel)) continue;
				if (this.level() instanceof ServerLevel serverLevel && serverLevel.getEntity(uUID) instanceof Player player) {
					player.sendSystemMessage(this.getCombatTracker().getDeathMessage());
				}
			}
		}
		super.die(damageSource);
	}

	@Nullable
	@Override
	public SpawnGroupData finalizeSpawn(ServerLevelAccessor serverLevelAccessor, DifficultyInstance difficultyInstance, MobSpawnType mobSpawnType, @Nullable SpawnGroupData spawnGroupData, @Nullable CompoundTag compoundTag) {
		RandomSource randomSource = serverLevelAccessor.getRandom();
		if (mobSpawnType != MobSpawnType.CONVERSION) {
			RandomSource randomSourceSkin = serverLevelAccessor.getRandom();
			if (randomSourceSkin.nextFloat() < 0.05f) {
				this.setVexRace(true);
			}
			else {
				this.setVexRace(false);
			}
			float weaponRandom = this.random.nextFloat();
			this.populateDefaultEquipmentEnchantments(randomSource, difficultyInstance);
		}
		return super.finalizeSpawn(serverLevelAccessor, difficultyInstance, mobSpawnType, spawnGroupData, compoundTag);
	}
}
