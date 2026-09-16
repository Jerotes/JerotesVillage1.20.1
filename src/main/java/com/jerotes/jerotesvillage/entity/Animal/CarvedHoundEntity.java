package com.jerotes.jerotesvillage.entity.Animal;

import com.google.common.collect.Lists;
import com.jerotes.jerotes.entity.Interface.*;
import com.jerotes.jerotes.entity.Mob.HumanEntity;
import com.jerotes.jerotes.goal.*;
import com.jerotes.jerotes.util.AttackFind;
import com.jerotes.jerotes.util.EntityFactionFind;
import com.jerotes.jerotesvillage.config.OtherMainConfig;
import com.jerotes.jerotesvillage.entity.Monster.IllagerFaction.DefectorEntity;
import com.jerotes.jerotesvillage.entity.Neutral.CarvedAllayEntity;
import com.jerotes.jerotesvillage.event.RelationshipEvent;
import com.jerotes.jerotesvillage.goal.HelpVillagerGoal;
import com.jerotes.jerotesvillage.init.JerotesVillageEntityType;
import com.jerotes.jerotesvillage.item.CarvedFlag;
import net.minecraft.ChatFormatting;
import net.minecraft.core.BlockPos;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.ListTag;
import net.minecraft.nbt.NbtUtils;
import net.minecraft.nbt.Tag;
import net.minecraft.network.chat.Component;
import net.minecraft.network.syncher.EntityDataAccessor;
import net.minecraft.network.syncher.EntityDataSerializers;
import net.minecraft.network.syncher.SynchedEntityData;
import net.minecraft.server.MinecraftServer;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.util.Mth;
import net.minecraft.world.DifficultyInstance;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.*;
import net.minecraft.world.entity.ai.attributes.AttributeSupplier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.ai.goal.*;
import net.minecraft.world.entity.ai.goal.target.HurtByTargetGoal;
import net.minecraft.world.entity.ai.goal.target.NearestAttackableTargetGoal;
import net.minecraft.world.entity.ai.goal.target.ResetUniversalAngerTargetGoal;
import net.minecraft.world.entity.animal.IronGolem;
import net.minecraft.world.entity.animal.Wolf;
import net.minecraft.world.entity.monster.Creeper;
import net.minecraft.world.entity.monster.Enemy;
import net.minecraft.world.entity.monster.Zombie;
import net.minecraft.world.entity.npc.AbstractVillager;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.entity.projectile.AbstractArrow;
import net.minecraft.world.entity.raid.Raider;
import net.minecraft.world.item.*;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.level.GameRules;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.ServerLevelAccessor;
import net.minecraft.world.level.gameevent.GameEvent;
import net.minecraft.world.phys.Vec3;

import javax.annotation.Nullable;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.function.Predicate;

public class CarvedHoundEntity extends Wolf implements JerotesEntity, FactionEntity, CarvedEntity, ChangePoseAbout, HasHomePosEntity {
	protected static final EntityDataAccessor<Integer> DATA_OWNER_ID = SynchedEntityData.defineId(CarvedHoundEntity.class, EntityDataSerializers.INT);
	public static final EntityDataAccessor<BlockPos> HOME_POS = SynchedEntityData.defineId(CarvedHoundEntity.class, EntityDataSerializers.BLOCK_POS);
	private static final EntityDataAccessor<Boolean> IS_WANDER = SynchedEntityData.defineId(CarvedHoundEntity.class, EntityDataSerializers.BOOLEAN);
	public static final EntityDataAccessor<Boolean> GOING_HOME = SynchedEntityData.defineId(CarvedHoundEntity.class, EntityDataSerializers.BOOLEAN);
	private static final EntityDataAccessor<Integer> DATA_TYPE_ID = SynchedEntityData.defineId(CarvedHoundEntity.class, EntityDataSerializers.INT);
	private static final EntityDataAccessor<Byte> DATA_FLAGS_ID = SynchedEntityData.defineId(CarvedHoundEntity.class, EntityDataSerializers.BYTE);
	private static final EntityDataAccessor<Optional<UUID>> DATA_TRUSTED_ID_0 = SynchedEntityData.defineId(CarvedHoundEntity.class, EntityDataSerializers.OPTIONAL_UUID);
	private static final EntityDataAccessor<Optional<UUID>> DATA_TRUSTED_ID_1 = SynchedEntityData.defineId(CarvedHoundEntity.class, EntityDataSerializers.OPTIONAL_UUID);
	private static final Predicate<Entity> TRUSTED_TARGET_SELECTOR = entity -> {
		if (entity instanceof LivingEntity) {
			LivingEntity livingEntity = (LivingEntity)entity;
			return livingEntity.getLastHurtMob() != null && livingEntity.getLastHurtMobTimestamp() < livingEntity.tickCount + 600;
		}
		return false;
	};

	public CarvedHoundEntity(EntityType<? extends CarvedHoundEntity> entityType, Level level) {
		super(entityType, level);
		setPersistenceRequired();
	}

	public static AttributeSupplier.Builder createAttributes() {
		AttributeSupplier.Builder builder = Mob.createMobAttributes();
		builder = builder.add(Attributes.MOVEMENT_SPEED, 0.33);
		builder = builder.add(Attributes.MAX_HEALTH, 26);
		builder = builder.add(Attributes.KNOCKBACK_RESISTANCE, 0.4);
		builder = builder.add(Attributes.ARMOR, 12);
		builder = builder.add(Attributes.ATTACK_DAMAGE, 6);
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
		this.goalSelector.addGoal(0, new FloatGoal(this));
		this.goalSelector.addGoal(1, new JerotesNoOwnerAndTameSitWhenOrderedToGoal(this){
			public boolean canUse() {
				if (CarvedHoundEntity.this.isWander()) {
					return false;
				}
				else {
					return super.canUse();
				}
			}

			public boolean canContinueToUse() {
				if (CarvedHoundEntity.this.isWander()) {
					return false;
				}
				else {
					return super.canContinueToUse();
				}
			}
		});
		this.goalSelector.addGoal(4, new LeapAtTargetGoal(this, 0.4f));
		this.goalSelector.addGoal(5, new JerotesMeleeAttackGoal(this, 1.0, true));
		this.goalSelector.addGoal(6, new JerotesBaseTamableAnimalGoHomeGoal(this, 1.0f));
		this.goalSelector.addGoal(6, new JerotesAnimalChangeFollowOwnerGoal(this, 1.3, 5.0f, 1.0f, false));
		this.goalSelector.addGoal(6, new JerotesAnimalChangeFollowMobOwnerGoal(this, 1.0f));
		this.goalSelector.addGoal(7, new JerotesBreedGoal(this, 1.0));
		this.goalSelector.addGoal(8, new BegGoal(this, 8.0f));
		this.goalSelector.addGoal(9, new MoveBackToVillageGoal(this, 0.6, false));
		this.goalSelector.addGoal(10, new GolemRandomStrollInVillageGoal(this, 0.6));
		this.goalSelector.addGoal(11, new LookAtPlayerGoal(this, Player.class, 8.0f));
		this.goalSelector.addGoal(11, new LookAtPlayerGoal(this, HumanEntity.class, 8.0f));
		this.goalSelector.addGoal(11, new LookAtPlayerGoal(this, AbstractVillager.class, 6.0f));
		this.goalSelector.addGoal(11, new LookAtPlayerGoal(this, IronGolem.class, 6.0f));
		this.goalSelector.addGoal(11, new LookAtPlayerGoal(this, CarvedAllayEntity.class, 6.0f));
		this.goalSelector.addGoal(11, new LookAtPlayerGoal(this, CarvedHoundEntity.class, 6.0f));
		this.goalSelector.addGoal(11, new LookAtPlayerGoal(this, CarvedLlamaEntity.class, 6.0f));
		this.goalSelector.addGoal(12, new RandomLookAroundGoal(this));
		this.targetSelector.addGoal(1, new HurtByTargetGoal(this, CarvedEntity.class));
		this.targetSelector.addGoal(1, new JerotesHelpCarvedGoal(this));
		this.targetSelector.addGoal(1, new HelpVillagerGoal(this));
		this.targetSelector.addGoal(1, new JerotesHelpAlliesGoal(this, LivingEntity.class, false, false, livingEntity -> livingEntity instanceof LivingEntity));
		this.targetSelector.addGoal(1, new JerotesAnimalChangeHelpMobOwnerGoal(this));
		this.targetSelector.addGoal(1, new JerotesAnimalChangeOwnerHurtByTargetGoal(this));
		this.targetSelector.addGoal(2, new JerotesAnimalChangeOwnerHurtTargetGoal(this));
		if (OtherMainConfig.CarvedObligation) {
			this.targetSelector.addGoal(2, new NearestAttackableTargetGoal<Mob>(this, Mob.class, 5, false, false, mob -> (mob instanceof Enemy || OtherMainConfig.CarvedGonnaActiveAttackList.contains(mob.getEncodeId())) && !OtherMainConfig.CarvedGonnaActiveAttackList.contains(mob.getEncodeId()) && this.getOwner() == null && !(mob instanceof Creeper && !OtherMainConfig.CarvedActiveAttackCreeper)));
		}
		else {
			this.targetSelector.addGoal(2, new NearestAttackableTargetGoal<Mob>(this, Mob.class, 5, false, false, mob -> OtherMainConfig.CarvedGonnaActiveAttackList.contains(mob.getEncodeId()) && !OtherMainConfig.CarvedGonnaActiveAttackList.contains(mob.getEncodeId()) && this.getOwner() == null && !(mob instanceof Creeper && !OtherMainConfig.CarvedActiveAttackCreeper)));
			this.targetSelector.addGoal(2, new NearestAttackableTargetGoal<Raider>(this, Raider.class, 5, false, false, (mob) -> !(mob instanceof NeutralMob) && this.getOwner() == null && !OtherMainConfig.CarvedNeverGonnaActiveAttack.contains(mob.getEncodeId())));
			this.targetSelector.addGoal(2, new NearestAttackableTargetGoal<Zombie>(this, Zombie.class, 5, false, false, (mob) -> !(mob instanceof NeutralMob) && this.getOwner() == null && !OtherMainConfig.CarvedNeverGonnaActiveAttack.contains(mob.getEncodeId())));
		}
		this.targetSelector.addGoal(2, new NearestAttackableTargetGoal<Player>(this, Player.class, 10, true, false, livingEntity -> livingEntity instanceof Player player && RelationshipEvent.LessCopperCarvedCompanyRelationship(player, -500) && !this.trusts(player.getUUID())));
		this.targetSelector.addGoal(3, new NearestAttackableTargetGoal<Player>(this, Player.class, 10, true, false, this::isAngryAt));
		this.targetSelector.addGoal(3, new ResetUniversalAngerTargetGoal<CarvedHoundEntity>(this, true));
	}

	@Nullable
	@Override
	public CarvedHoundEntity getBreedOffspring(ServerLevel serverLevel, AgeableMob ageableMob) {
		UUID uUID;
		CarvedHoundEntity wolf = JerotesVillageEntityType.CARVED_HOUND.get().create(serverLevel);
		if (wolf != null && (uUID = this.getOwnerUUID()) != null) {
			wolf.setOwnerUUID(uUID);
			wolf.addTrustedUUID(uUID);
			wolf.setTame(true);
		}
		return wolf;
	}
	@Override
	public void setTame(boolean bl) {
		super.setTame(bl);
		if (bl) {
			this.getAttribute(Attributes.MAX_HEALTH).setBaseValue(40);
			this.getAttribute(Attributes.ATTACK_DAMAGE).setBaseValue(8);
			this.setHealth(40.0f);
		} else {
			this.getAttribute(Attributes.MAX_HEALTH).setBaseValue(26);
			this.getAttribute(Attributes.ATTACK_DAMAGE).setBaseValue(6);
		}
	}

	@Override
	public boolean removeWhenFarAway(double d) {
		return false;
	}
	@Override
	public boolean isTame() {
		if ((this.entityData.get(DATA_FLAGS_ID) & 4) != 0) {
			return (this.entityData.get(DATA_FLAGS_ID) & 4) != 0;
		}
		return true;
	}
	public boolean isEmerald(ItemStack stack) {
		return Ingredient.of(new ItemStack(Items.EMERALD)).test(stack);
	}
	public void setHomePos(BlockPos blockPos) {
		this.entityData.set(HOME_POS, blockPos);
	}
	public BlockPos getHomePos() {
		return this.entityData.get(HOME_POS);
	}
	public boolean isGoingHome() {
		return this.entityData.get(GOING_HOME);
	}
	public void setGoingHome(boolean bl) {
		this.entityData.set(GOING_HOME, bl);
	}
	@Nullable
	@Override
	public LivingEntity getOwner() {
		if (!this.level().isClientSide){
			UUID uuid = this.getOwnerUUID();
			return uuid == null ? null : getLivingEntityByUUID(this.level(), uuid);
		} else {
			int id = this.getOwnerId();
			return id <= -1 ? null : this.level().getEntity(this.getOwnerId()) instanceof LivingEntity living && living != this ? living : null;
		}
	}
	public int getOwnerId(){
		return this.entityData.get(DATA_OWNER_ID);
	}
	public void setOwnerId(int id){
		this.entityData.set(DATA_OWNER_ID, id);
	}
	public static LivingEntity getLivingEntityByUUID(Level level, UUID uuid) {
		return getLivingEntityByUUID(level.getServer(), uuid);
	}

	public static LivingEntity getLivingEntityByUUID(MinecraftServer server, UUID uuid){
		if (uuid != null && server != null) {
			for (ServerLevel world : server.getAllLevels()) {
				Entity entity = world.getEntity(uuid);
				if (entity instanceof LivingEntity livingEntity){
					return livingEntity;
				}
			}
		}
		return null;
	}
	public boolean isWander() {
		return this.getEntityData().get(IS_WANDER);
	}
	public void setWander(boolean bl) {
		this.getEntityData().set(IS_WANDER, bl);
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
		compoundTag.putBoolean("IsWander", this.isWander());
		compoundTag.putBoolean("IsGoingHome", this.isGoingHome());
		compoundTag.putInt("HomePosX", this.getHomePos().getX());
		compoundTag.putInt("HomePosY", this.getHomePos().getY());
		compoundTag.putInt("HomePosZ", this.getHomePos().getZ());
	}
	@Override
	public void readAdditionalSaveData(CompoundTag compoundTag) {
		super.readAdditionalSaveData(compoundTag);
		ListTag listTag = compoundTag.getList("Trusted", 11);
		for (Tag tag : listTag) {
			this.addTrustedUUID(NbtUtils.loadUUID(tag));
		}
		this.setWander(compoundTag.getBoolean("IsWander"));
		this.setGoingHome(compoundTag.getBoolean("IsGoingHome"));
		int n = compoundTag.getInt("HomePosX");
		int n2 = compoundTag.getInt("HomePosY");
		int n3 = compoundTag.getInt("HomePosZ");
		this.setHomePos(new BlockPos(n, n2, n3));
	}
	@Override
	public boolean canChangeDimensions() {
		return super.canChangeDimensions() && this.getChangeType() != 3;
	}
	@Override
	protected void defineSynchedData() {
		super.defineSynchedData();
		this.getEntityData().define(IS_WANDER, false);
		this.getEntityData().define(DATA_OWNER_ID, -1);
		this.getEntityData().define(HOME_POS, BlockPos.ZERO);
		this.getEntityData().define(GOING_HOME, false);
		this.getEntityData().define(DATA_TRUSTED_ID_0, Optional.empty());
		this.getEntityData().define(DATA_TRUSTED_ID_1, Optional.empty());
		this.getEntityData().define(DATA_TYPE_ID, 0);
		this.getEntityData().define(DATA_FLAGS_ID, (byte)0);
	}
	List<UUID> getTrustedUUIDs() {
		ArrayList arrayList = Lists.newArrayList();
		arrayList.add(this.entityData.get(DATA_TRUSTED_ID_0).orElse(null));
		arrayList.add(this.entityData.get(DATA_TRUSTED_ID_1).orElse(null));
		return arrayList;
	}
	void addTrustedUUID(@Nullable UUID uUID) {
		if (this.entityData.get(DATA_TRUSTED_ID_0).isPresent()) {
			this.entityData.set(DATA_TRUSTED_ID_1, Optional.ofNullable(uUID));
		} else {
			this.entityData.set(DATA_TRUSTED_ID_0, Optional.ofNullable(uUID));
		}
	}
	@Override
	protected void onOffspringSpawnedFromEgg(Player player, Mob mob) {
		((CarvedHoundEntity)this).addTrustedUUID(player.getUUID());
	}
	@Override
	public boolean trusts(UUID uUID) {
		return this.getTrustedUUIDs().contains(uUID);
	}

	@Override
	public InteractionResult mobInteract(Player player, InteractionHand interactionHand) {
		ItemStack itemStack = player.getItemInHand(interactionHand);
		Item item = itemStack.getItem();
		if (itemStack.getItem() instanceof CarvedFlag) {
			float f1 = 1.0f + (this.random.nextFloat() - this.random.nextFloat()) * 0.2f;
			if (!this.isSilent()) {
				this.playSound(SoundEvents.WOLF_SHAKE, 1.0f, f1);
			}
			return InteractionResult.PASS;
		}
		if (!this.isAggressive() && this.isEmerald(itemStack)) {
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
				this.playSound(SoundEvents.WOLF_SHAKE, 1.0f, f1);
			}
			if (!player.getAbilities().instabuild) {
				itemStack.shrink(1);
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
		if (this.level().isClientSide) {
			boolean bl = this.isOwnedBy(player) || this.isTame() || this.isFood(itemStack) && !this.isTame() && !this.isAngry();
			boolean bl2 = this.isTame() && !player.isShiftKeyDown() && this.isBaby();
			if (!bl2) {
				return bl ? InteractionResult.CONSUME : InteractionResult.PASS;
			}
		}
		if (this.getOwner() == null) {
			if (itemStack.is(Items.BONE) && !this.isAngry()) {
				if (!player.getAbilities().instabuild) {
					itemStack.shrink(1);
				}
				if (this.random.nextInt(3) == 0 && !net.minecraftforge.event.ForgeEventFactory.onAnimalTame(this, player)) {
					this.tame(player);
					this.navigation.stop();
					this.setTarget((LivingEntity) null);
					setChangeType(1, player);
					this.level().broadcastEntityEvent(this, (byte) 7);
				} else {
					this.level().broadcastEntityEvent(this, (byte) 6);
				}
				return InteractionResult.SUCCESS;
			}
		}
		else {
			InteractionResult interactionResult;
			if (!player.isShiftKeyDown()) {
				if (this.isFood(itemStack)) {
					if (this.getHealth() < this.getMaxHealth()) {
						this.heal((float)itemStack.getFoodProperties(this).getNutrition());
						if (!player.getAbilities().instabuild) {
							itemStack.shrink(1);
						}
						this.gameEvent(GameEvent.EAT, this);
						return InteractionResult.SUCCESS;
					}
					return super.mobInteract(player, interactionHand);
				}
			}
			if (item instanceof DyeItem dyeItem) {
				if (this.isOwnedBy(player)) {
					DyeColor dyecolor = dyeItem.getDyeColor();
					if (dyecolor != this.getCollarColor()) {
						this.setCollarColor(dyecolor);
						if (!player.getAbilities().instabuild) {
							itemStack.shrink(1);
						}
						return InteractionResult.SUCCESS;
					}
					return super.mobInteract(player, interactionHand);
				}
			}
			//坐下
			if ((interactionResult = super.mobInteract(player, interactionHand)).consumesAction() && !this.isBaby() || !this.isOwnedBy(player)) return interactionResult;
			if (!this.isVehicle() && player == this.getOwner()) {
				int pose = this.getChangeType() + 1;
				if (pose > 4) {
					pose = 1;
				}
				this.setChangeType(pose, player);
			}
			return InteractionResult.SUCCESS;
		}
		return super.mobInteract(player, interactionHand);
	}
	public int getChangeType() {
		return (this.isOrderedToSit() ? 1 : 2) + (this.isWander() ? 2 : 0);
	}
	public void setChangeType(int n) {
		n = Mth.clamp(n, 1, 4);
		switch (n) {
			case 1:
				this.setOrderedToSit(true);
				if (!this.level().isClientSide()) {
					this.setWander(false);
				}
				this.jumping = false;
				this.navigation.stop();
				this.setTarget(null);
				break;
			case 2:
				this.setOrderedToSit(false);
				if (!this.level().isClientSide()) {
					this.setWander(false);
				}
				this.jumping = false;
				this.navigation.stop();
				this.setTarget(null);
				break;
			case 3:
				this.setOrderedToSit(true);
				if (!this.level().isClientSide()) {
					this.setWander(true);
					this.setHomePos(this.blockPosition());
				}
				this.jumping = false;
				this.navigation.stop();
				this.setTarget(null);
				break;
			case 4:
				this.setOrderedToSit(false);
				if (!this.level().isClientSide()) {
					this.setWander(true);
				}
				this.jumping = false;
				this.navigation.stop();
				this.setTarget(null);
				break;
		}
	}
	public void setChangeType(int n, Player player) {
		this.setChangeType(n);
		if (!this.level().isClientSide() && player instanceof ServerPlayer serverPlayer) {
			serverPlayer.sendSystemMessage(Component.translatable("talk.jerotes.pose_" + n, this.getDisplayName()).withStyle(ChatFormatting.WHITE));
		}
	}

	@Override
	public void tick() {
		super.tick();
		if (this.getOwner() != null && !(this.trusts(this.getOwnerUUID()))) {
			RelationshipEvent.AddCopperCarvedCompanyRelationship((Player)this.getOwner(), 5);
			this.addTrustedUUID(this.getOwnerUUID());
		}
	}

	@Override
	public void aiStep() {
		super.aiStep();
		if (this.getOwner() != null && this.getOwnerId() == -1) {
			this.setOwnerId(this.getOwner().getId());
		}
		if (this.isWander() && this.isInSittingPose()) {
			if (!this.level().isClientSide) {
				this.setInSittingPose(false);
			}
		}
		//停止战斗
		if (this.getTarget() != null && (!this.getTarget().isAlive() || this.getTarget() instanceof Player player && (player.isCreative() || player.isSpectator()))){
			this.setTarget(null);
		}
		//
		if (this.random.nextInt(900) == 1 && this.deathTime == 0) {
			this.heal(3.0f);
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
		Entity entity = damageSource.getEntity();
		if (entity != null && !(entity instanceof Player) && !(entity instanceof AbstractArrow)) {
			f = (f + 1.0F) / 2.0F;
		}

		boolean bl = super.hurt(damageSource, f);
		if (bl) {
			//取消坐下
			if (!this.level().isClientSide()) {
				if (this.getChangeType() == 1) {
					if (this.getOwner() instanceof Player player) {
						this.setChangeType(2, player);
					}
					else {
						this.setChangeType(2);
					}
				}
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
	public boolean wantsToAttack(LivingEntity livingEntity, LivingEntity livingEntity2) {
		return AttackFind.wantsToAttack(this, livingEntity, livingEntity2);
	}
	@Nullable
	@Override
	public SpawnGroupData finalizeSpawn(ServerLevelAccessor serverLevelAccessor, DifficultyInstance difficultyInstance, MobSpawnType mobSpawnType, @Nullable SpawnGroupData spawnGroupData, @Nullable CompoundTag compoundTag) {
		this.setHomePos(this.blockPosition());
		return super.finalizeSpawn(serverLevelAccessor, difficultyInstance, mobSpawnType, spawnGroupData, compoundTag);
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
					if (player == this.getOwner()) continue;
					player.sendSystemMessage(this.getCombatTracker().getDeathMessage());
				}
			}
		}
		super.die(damageSource);
	}
}