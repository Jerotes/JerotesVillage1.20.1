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
import net.minecraft.core.particles.SimpleParticleType;
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
import net.minecraft.server.players.OldUsersConverter;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.util.Mth;
import net.minecraft.util.TimeUtil;
import net.minecraft.util.valueproviders.UniformInt;
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
import net.minecraft.world.entity.animal.horse.Llama;
import net.minecraft.world.entity.monster.Creeper;
import net.minecraft.world.entity.monster.Enemy;
import net.minecraft.world.entity.monster.Zombie;
import net.minecraft.world.entity.npc.AbstractVillager;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.entity.projectile.LlamaSpit;
import net.minecraft.world.entity.raid.Raider;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.level.GameRules;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.ServerLevelAccessor;
import net.minecraft.world.level.gameevent.GameEvent;
import net.minecraft.world.phys.Vec3;
import net.minecraft.world.scores.Team;

import javax.annotation.Nullable;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.function.Predicate;

public class CarvedLlamaEntity extends Llama implements CarvedEntity, ChangePoseAbout, HasHomePosEntity, OwnableEntity, NeutralMob, JerotesEntity, FactionEntity, TameMobEntity {
	protected static final EntityDataAccessor<Integer> DATA_OWNER_ID = SynchedEntityData.defineId(CarvedLlamaEntity.class, EntityDataSerializers.INT);
	public static final EntityDataAccessor<BlockPos> HOME_POS = SynchedEntityData.defineId(CarvedLlamaEntity.class, EntityDataSerializers.BLOCK_POS);
	private static final EntityDataAccessor<Boolean> IS_WANDER = SynchedEntityData.defineId(CarvedLlamaEntity.class, EntityDataSerializers.BOOLEAN);
	public static final EntityDataAccessor<Boolean> GOING_HOME = SynchedEntityData.defineId(CarvedLlamaEntity.class, EntityDataSerializers.BOOLEAN);
	protected static final EntityDataAccessor<Optional<UUID>> DATA_OWNER_UUID_ID = SynchedEntityData.defineId(CarvedLlamaEntity.class, EntityDataSerializers.OPTIONAL_UUID);
	protected static final EntityDataAccessor<Byte> DATA_FLAGS_ID = SynchedEntityData.defineId(CarvedLlamaEntity.class, EntityDataSerializers.BYTE);
	private static final EntityDataAccessor<Integer> DATA_TYPE_ID = SynchedEntityData.defineId(CarvedLlamaEntity.class, EntityDataSerializers.INT);
	private static final EntityDataAccessor<Optional<UUID>> DATA_TRUSTED_ID_0 = SynchedEntityData.defineId(CarvedLlamaEntity.class, EntityDataSerializers.OPTIONAL_UUID);
	private static final EntityDataAccessor<Optional<UUID>> DATA_TRUSTED_ID_1 = SynchedEntityData.defineId(CarvedLlamaEntity.class, EntityDataSerializers.OPTIONAL_UUID);
	private static final Predicate<Entity> TRUSTED_TARGET_SELECTOR = entity -> {
		if (entity instanceof LivingEntity) {
			LivingEntity livingEntity = (LivingEntity)entity;
			return livingEntity.getLastHurtMob() != null && livingEntity.getLastHurtMobTimestamp() < livingEntity.tickCount + 600;
		}
		return false;
	};

	public CarvedLlamaEntity(EntityType entityType, Level level) {
		super(entityType, level);
		this.reassessTameGoals();
		setPersistenceRequired();
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

	public static AttributeSupplier.Builder createAttributes() {
		AttributeSupplier.Builder builder = Mob.createMobAttributes();
		builder = builder.add(Attributes.MOVEMENT_SPEED, 0.2);
		builder = builder.add(Attributes.JUMP_STRENGTH, 0.6);
		builder = builder.add(Attributes.MAX_HEALTH, 25);
		builder = builder.add(Attributes.ARMOR, 12);
		builder = builder.add(Attributes.ATTACK_DAMAGE, 3);
		builder = builder.add(Attributes.FOLLOW_RANGE, 48);
		builder = builder.add(Attributes.KNOCKBACK_RESISTANCE, 0.4);
		return builder;
	}

	@Override
	protected void registerGoals() {
		this.goalSelector.addGoal(0, new FloatGoal(this));
		this.goalSelector.addGoal(1, new JerotesChangeSitWhenOrderedToGoal(this));
		this.goalSelector.addGoal(2, new RunAroundLikeCrazyGoal(this, 1.2));
		this.goalSelector.addGoal(2, new LlamaFollowCaravanGoal(this, 2.0999999046325684));
		this.goalSelector.addGoal(3, new JerotesBaseTamableAnimalGoHomeGoal(this, 1.0f));
		this.goalSelector.addGoal(3, new RangedAttackGoal(this, 1.25, 10, 20.0f));
		this.goalSelector.addGoal(3, new JerotesChangeFollowOwnerGoal(this, 1.3, 5.0f, 1.0f, false));
		this.goalSelector.addGoal(3, new JerotesChangeFollowMobOwnerGoal(this, 1.0f));
		this.goalSelector.addGoal(5, new JerotesBreedGoal(this, 1.0));
		this.goalSelector.addGoal(7, new MoveBackToVillageGoal(this, 0.6, false));
		this.goalSelector.addGoal(8, new GolemRandomStrollInVillageGoal(this, 0.6));
		this.goalSelector.addGoal(9, new LookAtPlayerGoal(this, Player.class, 8.0f));
		this.goalSelector.addGoal(9, new LookAtPlayerGoal(this, HumanEntity.class, 8.0f));
		this.goalSelector.addGoal(9, new LookAtPlayerGoal(this, AbstractVillager.class, 6.0f));
		this.goalSelector.addGoal(9, new LookAtPlayerGoal(this, IronGolem.class, 6.0f));
		this.goalSelector.addGoal(9, new LookAtPlayerGoal(this, CarvedAllayEntity.class, 6.0f));
		this.goalSelector.addGoal(9, new LookAtPlayerGoal(this, CarvedHoundEntity.class, 6.0f));
		this.goalSelector.addGoal(9, new LookAtPlayerGoal(this, CarvedLlamaEntity.class, 6.0f));
		this.goalSelector.addGoal(10, new RandomLookAroundGoal(this));
		this.targetSelector.addGoal(1, new HurtByTargetGoal(this, CarvedEntity.class));
		this.targetSelector.addGoal(1, new JerotesHelpCarvedGoal(this));
		this.targetSelector.addGoal(1, new HelpVillagerGoal(this));
		this.targetSelector.addGoal(1, new JerotesHelpAlliesGoal(this, LivingEntity.class, false, false, livingEntity -> livingEntity instanceof LivingEntity));
		this.targetSelector.addGoal(1, new JerotesChangeHelpMobOwnerGoal(this));
		this.targetSelector.addGoal(1, new JerotesChangeOwnerHurtByTargetGoal(this));
		this.targetSelector.addGoal(2, new JerotesChangeOwnerHurtTargetGoal(this));
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
		this.targetSelector.addGoal(3, new ResetUniversalAngerTargetGoal<CarvedLlamaEntity>(this, true));
	}

	public void performRangedAttack(LivingEntity livingEntity, float f) {
		super.performRangedAttack(livingEntity, f);
		this.spit(livingEntity);
	}
	private void spit(LivingEntity livingEntity) {
		LlamaSpit llamaspit = new LlamaSpit(this.level(), this);
		for (int i = 0; i < 4; ++i) {
			double d0 = livingEntity.getX() - this.getX();
			double d1 = livingEntity.getY(0.3333333333333333) - llamaspit.getY();
			double d2 = livingEntity.getZ() - this.getZ();
			double d3 = Math.sqrt(d0 * d0 + d2 * d2) * 0.20000000298023224;
			llamaspit.shoot(d0, d1 + d3 , d2, 1.5F, 20f);
		}
		this.level().addFreshEntity(llamaspit);
	}

	protected void reassessTameGoals() {
	}
	@Nullable
	@Override
	protected Llama makeNewLlama() {
		return JerotesVillageEntityType.CARVED_LLAMA.get().create(this.level());
	}

	private int sitTick = 0;
	private boolean orderedToSit;
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
	@Override
	public boolean isInSittingPose() {
		return (this.entityData.get(DATA_FLAGS_ID) & 1) != 0;
	}
	public boolean isOwnedBy(LivingEntity livingEntity) {
		return livingEntity == this.getOwner();
	}
	@Override
	public boolean isOrderedToSit() {
		return this.orderedToSit;
	}
	@Nullable
	@Override
	public UUID getOwnerUUID() {
		return this.entityData.get(DATA_OWNER_UUID_ID).orElse((UUID)null);
	}
	public void setOrderedToSit(boolean bl) {
		this.orderedToSit = bl;
	}
	public void setTame(boolean bl) {
		byte by = this.entityData.get(DATA_FLAGS_ID);
		if (bl) {
			this.entityData.set(DATA_FLAGS_ID, (byte)(by | 4));
		} else {
			this.entityData.set(DATA_FLAGS_ID, (byte)(by & 0xFFFFFFFB));
		}
		this.reassessTameGoals();
		this.setFlag(2, bl);
	}
	@Override
	public void setTamed(boolean bl) {
		this.setFlag(2, bl);
		this.setTame(bl);
	}
	@Override
	//1.20.4↑//
	//public PlayerTeam getTeam() {
	//1.20.1//
	public Team getTeam() {
		LivingEntity livingEntity;
		if (this.isTame() && (livingEntity = this.getOwner()) != null) {
			return livingEntity.getTeam();
		}
		return super.getTeam();
	}
	@Override
	public void setInSittingPose(boolean bl) {
		byte by = this.entityData.get(DATA_FLAGS_ID);
		if (bl) {
			this.entityData.set(DATA_FLAGS_ID, (byte)(by | 1));
		} else {
			this.entityData.set(DATA_FLAGS_ID, (byte)(by & -2));
		}
	}
	public boolean isEmerald(ItemStack stack) {
		return Ingredient.of(new ItemStack(Items.EMERALD)).test(stack);
	}
	@Override
	public boolean isSaddled() {
		return this.isAlive() && !this.isBaby() && this.getOwner() != null;
	}
	@Override
	public void setOwnerUUID(@Nullable UUID uUID) {
		this.entityData.set(DATA_OWNER_UUID_ID, Optional.ofNullable(uUID));
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
		compoundTag.putBoolean("IsWander", this.isWander());
		compoundTag.putBoolean("IsGoingHome", this.isGoingHome());
		compoundTag.putInt("HomePosX", this.getHomePos().getX());
		compoundTag.putInt("HomePosY", this.getHomePos().getY());
		compoundTag.putInt("HomePosZ", this.getHomePos().getZ());
		compoundTag.putInt("SitTick", this.sitTick);
		if (this.getOwnerUUID() != null) {
			compoundTag.putUUID("Owner", this.getOwnerUUID());
		}
		compoundTag.putBoolean("Sitting", this.orderedToSit);
		List<UUID> list = this.getTrustedUUIDs();
		ListTag listTag = new ListTag();
		for (UUID uUID : list) {
			if (uUID == null) continue;
			listTag.add(NbtUtils.createUUID(uUID));
		}
		compoundTag.put("Trusted", listTag);
		this.addPersistentAngerSaveData(compoundTag);
	}
	@Override
	public void readAdditionalSaveData(CompoundTag compoundTag) {
		UUID uUID;
		ItemStack itemStack;
		super.readAdditionalSaveData(compoundTag);
		this.setWander(compoundTag.getBoolean("IsWander"));
		this.setGoingHome(compoundTag.getBoolean("IsGoingHome"));
		int n = compoundTag.getInt("HomePosX");
		int n2 = compoundTag.getInt("HomePosY");
		int n3 = compoundTag.getInt("HomePosZ");
		this.setHomePos(new BlockPos(n, n2, n3));
		this.sitTick = compoundTag.getInt("SitTick");
		if (compoundTag.hasUUID("Owner")) {
			uUID = compoundTag.getUUID("Owner");
		} else {
			String string = compoundTag.getString("Owner");
			uUID = OldUsersConverter.convertMobOwnerIfNecessary(this.getServer(), string);
		}
		if (uUID != null) {
			try {
				this.setOwnerUUID(uUID);
				this.setTame(true);
			}
			catch (Throwable throwable) {
				this.setTame(false);
			}
		}
		this.orderedToSit = compoundTag.getBoolean("Sitting");
		this.setInSittingPose(this.orderedToSit);
		ListTag listTag = compoundTag.getList("Trusted", 11);
		for (Tag tag : listTag) {
			this.addTrustedUUID(NbtUtils.loadUUID(tag));
		}
		this.readPersistentAngerSaveData(this.level(), compoundTag);
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
		this.getEntityData().define(DATA_FLAGS_ID, (byte)0);
		this.getEntityData().define(DATA_OWNER_UUID_ID, Optional.empty());
		this.getEntityData().define(DATA_TRUSTED_ID_0, Optional.empty());
		this.getEntityData().define(DATA_TRUSTED_ID_1, Optional.empty());
		this.getEntityData().define(DATA_TYPE_ID, 0);
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
		((CarvedLlamaEntity)this).addTrustedUUID(player.getUUID());
	}
	@Override
	public boolean trusts(UUID uUID) {
		return this.getTrustedUUIDs().contains(uUID);
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
		if (!this.level().isClientSide) {
			this.updatePersistentAnger((ServerLevel)this.level(), true);
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
	public InteractionResult mobInteract(Player player, InteractionHand interactionHand) {
		ItemStack itemStack = player.getItemInHand(interactionHand);
		if (itemStack.getItem() instanceof CarvedFlag) {
			float f1 = 1.0f + (this.random.nextFloat() - this.random.nextFloat()) * 0.2f;
			if (!this.isSilent()) {
				this.playSound(SoundEvents.LLAMA_EAT, 1.0f, f1);
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
				this.playSound(SoundEvents.LLAMA_EAT, 1.0f, f1);
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
		if (!itemStack.isEmpty() && !this.isFood(itemStack) && !this.isTamed()) {
			this.makeMad();
		}
		if (this.isTame()) {
			InteractionResult interactionResult;
			if (!player.isShiftKeyDown() && !this.isOrderedToSit()) {
				//喂食
				if (this.isFood(itemStack)) {
					if (!this.isSilent()) {
						this.playSound(getEatingSound(), 0.15f, 1.0f);
					}
					if (this.getHealth() < this.getMaxHealth()) {
						this.heal(4f);
						if (!player.getAbilities().instabuild) {
							itemStack.shrink(1);
						}
						this.gameEvent(GameEvent.EAT, this);
						return InteractionResult.SUCCESS;
					}
					//常规繁殖
					int i = this.getAge();
					if (!this.level().isClientSide && i == 0 && this.canFallInLove()) {
						this.setInLove(player);
						if (!player.getAbilities().instabuild) {
							itemStack.shrink(1);
						}
					}
					if (this.isBaby()) {
						this.usePlayerItem(player, interactionHand, itemStack);
						this.ageUp(getSpeedUpSecondsWhenFeeding(-i), true);
					}
					return InteractionResult.SUCCESS;
				}
				return super.mobInteract(player, interactionHand);
			}
			if (!this.isVehicle() && player == this.getOwner()) {
				int pose = this.getChangeType() + 1;
				if (pose > 4) {
					pose = 1;
				}
				this.setChangeType(pose, player);
			}
			return InteractionResult.SUCCESS;
		}
		if (this.canWearArmor() && this.isArmor(itemStack) && !this.isWearingArmor()) {
			return InteractionResult.sidedSuccess(((Level)this.level()).isClientSide);
		}
		if (!this.isFood(itemStack) || this.isAngry())
			return super.mobInteract(player, interactionHand);
		if (!this.isSilent()) {
			this.playSound(getEatingSound(), 0.15f, 1.0f);
		}
		return this.fedFood(player, itemStack);
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
	protected void spawnTamingParticles(boolean bl) {
		SimpleParticleType simpleParticleType = ParticleTypes.HEART;
		if (!bl) {
			simpleParticleType = ParticleTypes.SMOKE;
		}
		for (int i = 0; i < 7; ++i) {
			double d = this.random.nextGaussian() * 0.02;
			double d2 = this.random.nextGaussian() * 0.02;
			double d3 = this.random.nextGaussian() * 0.02;
			((Level)this.level()).addParticle(simpleParticleType, this.getRandomX(1.0), this.getRandomY() + 0.5, this.getRandomZ(1.0), d, d2, d3);
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
	public boolean hurt(DamageSource damageSource, float amount) {
		if (isInvulnerableTo(damageSource)) {
			return super.hurt(damageSource, amount);
		}
		boolean bl = super.hurt(damageSource, amount);
		if (bl) {
			if (damageSource.getEntity() instanceof Player player && !this.trusts(player.getUUID()) && this.getTarget() == null) {
				RelationshipEvent.AddCopperCarvedCompanyRelationship(player, -2);
			}
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
	public void handleEntityEvent(byte by) {
		if (by == 7) {
			this.spawnTamingParticles(true);
		} else if (by == 6) {
			this.spawnTamingParticles(false);
		} else {
			super.handleEntityEvent(by);
		}
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
		if (!this.level().isClientSide && this.level().getGameRules().getBoolean(GameRules.RULE_SHOWDEATHMESSAGES) && this.getOwner() instanceof ServerPlayer) {
			this.getOwner().sendSystemMessage(this.getCombatTracker().getDeathMessage());
		}
		super.die(damageSource);
	}

	@Nullable
	private static final UniformInt PERSISTENT_ANGER_TIME = TimeUtil.rangeOfSeconds(20, 39);
	private int remainingPersistentAngerTime;
	private UUID persistentAngerTarget;

	@Override
	public void startPersistentAngerTimer() {
		this.setRemainingPersistentAngerTime(PERSISTENT_ANGER_TIME.sample(this.random));
	}

	@Override
	public void setRemainingPersistentAngerTime(int n) {
		this.remainingPersistentAngerTime = n;
	}

	@Override
	public int getRemainingPersistentAngerTime() {
		return this.remainingPersistentAngerTime;
	}

	@Override
	public void setPersistentAngerTarget(UUID uUID) {
		this.persistentAngerTarget = uUID;
	}

	@Override
	public UUID getPersistentAngerTarget() {
		return this.persistentAngerTarget;
	}
}




