package com.jerotes.jerotesvillage.entity.Neutral;

import com.jerotes.jerotes.entity.Interface.*;
import com.jerotes.jerotes.goal.*;
import com.jerotes.jerotes.init.JerotesDamageTypes;
import com.jerotes.jerotes.init.JerotesGameRules;
import com.jerotes.jerotes.init.JerotesMobEffects;
import com.jerotes.jerotes.util.AttackFind;
import com.jerotes.jerotes.entity.Interface.HasHomePosEntity;
import com.jerotes.jerotes.goal.JerotesBaseTamableAnimalGoHomeGoal;
import com.jerotes.jerotesvillage.init.JerotesVillageSoundEvents;
import net.minecraft.ChatFormatting;
import net.minecraft.core.BlockPos;
import net.minecraft.core.particles.ParticleOptions;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.chat.Component;
import net.minecraft.network.syncher.EntityDataAccessor;
import net.minecraft.network.syncher.EntityDataSerializers;
import net.minecraft.network.syncher.SynchedEntityData;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.MinecraftServer;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.server.players.OldUsersConverter;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.tags.DamageTypeTags;
import net.minecraft.tags.ItemTags;
import net.minecraft.util.Mth;
import net.minecraft.util.TimeUtil;
import net.minecraft.util.valueproviders.UniformInt;
import net.minecraft.world.DifficultyInstance;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.damagesource.DamageTypes;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.*;
import net.minecraft.world.entity.ai.attributes.AttributeSupplier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.ai.goal.FloatGoal;
import net.minecraft.world.entity.ai.goal.target.HurtByTargetGoal;
import net.minecraft.world.entity.ai.goal.target.NearestAttackableTargetGoal;
import net.minecraft.world.entity.ai.goal.target.ResetUniversalAngerTargetGoal;
import net.minecraft.world.entity.monster.RangedAttackMob;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.BowItem;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TridentItem;
import net.minecraft.world.level.GameRules;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.ServerLevelAccessor;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.scores.Team;
import net.minecraftforge.common.ForgeMod;
import net.minecraftforge.fluids.FluidType;

import javax.annotation.Nullable;
import java.util.Optional;
import java.util.UUID;

public class SpearMachineEntity extends PathfinderMob implements NeutralMob, UseSpearSpecialEntity, JerotesEntity, FactionEntity, ChangePoseAbout, HasHomePosEntity, RangedAttackMob, OwnableEntity, TameMobEntity, UseThrownJavelinEntity {
	protected static final EntityDataAccessor<Integer> DATA_OWNER_ID = SynchedEntityData.defineId(SpearMachineEntity.class, EntityDataSerializers.INT);
	public static final EntityDataAccessor<BlockPos> HOME_POS = SynchedEntityData.defineId(SpearMachineEntity.class, EntityDataSerializers.BLOCK_POS);
	private static final EntityDataAccessor<Boolean> IS_WANDER = SynchedEntityData.defineId(SpearMachineEntity.class, EntityDataSerializers.BOOLEAN);
	public static final EntityDataAccessor<Boolean> GOING_HOME = SynchedEntityData.defineId(SpearMachineEntity.class, EntityDataSerializers.BOOLEAN);
	protected static final EntityDataAccessor<Byte> DATA_FLAGS_ID = SynchedEntityData.defineId(SpearMachineEntity.class, EntityDataSerializers.BYTE);
	protected static final EntityDataAccessor<Optional<UUID>> DATA_OWNER_UUID_ID = SynchedEntityData.defineId(SpearMachineEntity.class, EntityDataSerializers.OPTIONAL_UUID);

	public SpearMachineEntity(EntityType<? extends SpearMachineEntity> type, Level world) {
		super(type, world);
		setMaxUpStep(1.6f);
		xpReward = 0;
		this.reassessTameGoals();
		setPersistenceRequired();
	}


	public float getJerotesSpearDamageMultiple() {
		return 2.5F;
	}

	public static AttributeSupplier.Builder createAttributes() {
		AttributeSupplier.Builder builder = Mob.createMobAttributes();
		builder = builder.add(Attributes.MOVEMENT_SPEED, 0.35);
		builder = builder.add(Attributes.MAX_HEALTH, 15);
		builder = builder.add(Attributes.ARMOR, 18);
		builder = builder.add(Attributes.ATTACK_DAMAGE, 7);
		builder = builder.add(Attributes.FOLLOW_RANGE, 24);
		builder = builder.add(Attributes.KNOCKBACK_RESISTANCE, 0.8);
		builder = builder.add(Attributes.ATTACK_KNOCKBACK, 0.7);
		return builder;
	}

	@Override
	protected void registerGoals() {
		this.goalSelector.addGoal(0, new FloatGoal(this));
		this.goalSelector.addGoal(1, new JerotesChangeSitWhenOrderedToGoal(this));
		this.goalSelector.addGoal(2, new JerotesRangedJavelinAttackGoal<>(this, 1.0, 30, 12.0F, 3));
		this.goalSelector.addGoal(2, new JerotesSpearUseGoal<>(this,  1.5, 1.0, 10.0f, 2.0f));
		this.goalSelector.addGoal(2, new JerotesPikeUseGoal(this,  1.0, true));
		this.goalSelector.addGoal(4, new JerotesBaseTamableAnimalGoHomeGoal(this, 1.0f));
		this.goalSelector.addGoal(3, new JerotesMeleeAttackGoal(SpearMachineEntity.this, 1.0, true));
		this.goalSelector.addGoal(4, new JerotesChangeFollowOwnerGoal(this, 1.3, 5.0f, 1.0f, false));
		this.goalSelector.addGoal(4, new JerotesChangeFollowMobOwnerGoal(this, 1.0f));
		this.targetSelector.addGoal(1, new JerotesChangeHelpMobOwnerGoal(this));
		this.targetSelector.addGoal(1, new JerotesChangeOwnerHurtByTargetGoal(this));
		this.targetSelector.addGoal(2, new JerotesChangeOwnerHurtTargetGoal(this));
		this.targetSelector.addGoal(2, new HurtByTargetGoal(this));
		this.targetSelector.addGoal(3, new NearestAttackableTargetGoal<Player>(this, Player.class, 10, true, false, this::isAngryAt));
		this.targetSelector.addGoal(4, new ResetUniversalAngerTargetGoal<SpearMachineEntity>(this, false));
	}

	@Override
	public void performRangedAttack(LivingEntity livingEntity, float f) {
		ItemStack handItem = this.getMainHandItem();
		if ((this.getMainHandItem().isEmpty() || InventoryEntity.isMeleeWeapon(this.getMainHandItem())) && !this.getOffhandItem().isEmpty()) {
			handItem = this.getOffhandItem();
		}
		if (InventoryEntity.isRangeJavelin(handItem)) {
			useTridentShoot(this, livingEntity);
		}
		if (JerotesGameRules.JEROTES_RANGE_CAN_BREAK != null && this.level().getLevelData().getGameRules().getBoolean(JerotesGameRules.JEROTES_RANGE_CAN_BREAK)) {
            if (handItem.getItem() instanceof BowItem || handItem.getItem() instanceof TridentItem) {
                handItem.hurtAndBreak(1, this, player -> player.broadcastBreakEvent((this.getMainHandItem().isEmpty() || InventoryEntity.isMeleeWeapon(this.getMainHandItem())) && !this.getOffhandItem().isEmpty() ? EquipmentSlot.OFFHAND : EquipmentSlot.MAINHAND));
            }
        }
	}

	@Override
	public SoundEvent getHurtSound(DamageSource ds) {
		return JerotesVillageSoundEvents.SPEAR_MACHINE_DEATH;
	}
	@Override
	public SoundEvent getDeathSound() {
		return JerotesVillageSoundEvents.SPEAR_MACHINE_DEATH;
	}
	@Override
	public void playStepSound(BlockPos pos, BlockState blockIn) {
		this.playSound(JerotesVillageSoundEvents.SPEAR_MACHINE_WALK, 0.15f, 1);
	}
	@Override
	public boolean removeWhenFarAway(double distanceToClosestPlayer) {
		return false;
	}
	@Override
	public boolean canDrownInFluidType(FluidType type) {
		if (type == ForgeMod.WATER_TYPE.get())
			return false;
		return super.canDrownInFluidType(type);
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
		return aabb1.inflate(1.0d, 1.0d, 1.0d);
	}
	@Override
	public boolean isWithinMeleeAttackRange(LivingEntity livingEntity) {
		return this.getAttackBoundingBox().intersects(livingEntity.getBoundingBox());
	}
	protected void reassessTameGoals() {
	}
	public void tame(Player player) {
		this.setTame(true);
		this.setOwnerUUID(player.getUUID());
	}

	private boolean orderedToSit;
	@Override
	public boolean isTame() {
		return (this.entityData.get(DATA_FLAGS_ID) & 4) != 0;
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
	public void setOwnerUUID(@Nullable UUID uuid) {
		this.entityData.set(DATA_OWNER_UUID_ID, Optional.ofNullable(uuid));
	}
	@Nullable
	@Override
	public UUID getOwnerUUID() {
		return this.entityData.get(DATA_OWNER_UUID_ID).orElse((UUID)null);
	}
	public void setTame(boolean bl) {
		byte bl1 = this.entityData.get(DATA_FLAGS_ID);
		if (bl) {
			this.entityData.set(DATA_FLAGS_ID, (byte)(bl1 | 4));
			this.getAttribute(Attributes.ATTACK_DAMAGE).setBaseValue(7);
		} else {
			this.entityData.set(DATA_FLAGS_ID, (byte)(bl1 & -5));
			this.getAttribute(Attributes.ATTACK_DAMAGE).setBaseValue(2);
		}
		this.reassessTameGoals();
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
	//1.20.4↑//
	//public PlayerTeam getTeam() {
	//1.20.1//
	public Team getTeam() {
		if (this.isTame()) {
			LivingEntity livingentity = this.getOwner();
			if (livingentity != null) {
				return livingentity.getTeam();
			}
		}
		return super.getTeam();
	}
	public void setOrderedToSit(boolean bl) {
		this.orderedToSit = bl;
	}
	@Override
	public void setInSittingPose(boolean bl) {
		byte b0 = this.entityData.get(DATA_FLAGS_ID);
		if (bl) {
			this.entityData.set(DATA_FLAGS_ID, (byte)(b0 | 1));
		} else {
			this.entityData.set(DATA_FLAGS_ID, (byte)(b0 & -2));
		}
	}
	@Override
	public void addAdditionalSaveData(CompoundTag compoundTag) {
		super.addAdditionalSaveData(compoundTag);
		compoundTag.putBoolean("IsWander", this.isWander());
		compoundTag.putBoolean("IsGoingHome", this.isGoingHome());
		compoundTag.putInt("HomePosX", this.getHomePos().getX());
		compoundTag.putInt("HomePosY", this.getHomePos().getY());
		compoundTag.putInt("HomePosZ", this.getHomePos().getZ());
		if (this.getOwnerUUID() != null) {
			compoundTag.putUUID("Owner", this.getOwnerUUID());
		}

		compoundTag.putBoolean("Sitting", this.orderedToSit);
		this.addPersistentAngerSaveData(compoundTag);
	}
	@Override
	public void readAdditionalSaveData(CompoundTag compoundTag) {
		super.readAdditionalSaveData(compoundTag);
		this.setWander(compoundTag.getBoolean("IsWander"));
		this.setGoingHome(compoundTag.getBoolean("IsGoingHome"));
		int n = compoundTag.getInt("HomePosX");
		int n2 = compoundTag.getInt("HomePosY");
		int n3 = compoundTag.getInt("HomePosZ");
		this.setHomePos(new BlockPos(n, n2, n3));
		UUID uuid;
		if (compoundTag.hasUUID("Owner")) {
			uuid = compoundTag.getUUID("Owner");
		} else {
			String string = compoundTag.getString("Owner");
			uuid = OldUsersConverter.convertMobOwnerIfNecessary(this.getServer(), string);
		}
		if (uuid != null) {
			try {
				this.setOwnerUUID(uuid);
				this.setTame(true);
			} catch (Throwable throwable) {
				this.setTame(false);
			}
		}
		this.orderedToSit = compoundTag.getBoolean("Sitting");
		this.setInSittingPose(this.orderedToSit);
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
	}

	public static boolean targetJavelinWeapon(ItemStack javelin) {
		return InventoryEntity.isRangeJavelin(javelin) || InventoryEntity.isSpear(javelin) || InventoryEntity.isPike(javelin) ||
				javelin.is(ItemTags.create(new ResourceLocation("forge:tools/tridents"))) ||
				javelin.is(ItemTags.create(new ResourceLocation("forge:tools/pikes"))) ||
				javelin.is(ItemTags.create(new ResourceLocation("forge:tools/spears")));
	}
	@Override
	public InteractionResult mobInteract(Player player, InteractionHand interactionHand) {
		ItemStack itemStack = player.getItemInHand(interactionHand);
		InteractionResult retval = InteractionResult.sidedSuccess(this.level().isClientSide());
		if (this.level().isClientSide()) {
			retval = (this.isTame() && this.isOwnedBy(player) || targetJavelinWeapon(itemStack)) ? InteractionResult.sidedSuccess(this.level().isClientSide()) : InteractionResult.PASS;
		}
		else {
			if (this.isTame()) {
				InteractionResult interactionResult;
				if (this.isOwnedBy(player)) {
					if (!player.isShiftKeyDown()) {
						if (targetJavelinWeapon(itemStack)) {
							this.swapItem(player, EquipmentSlot.MAINHAND, itemStack, interactionHand);
							int health = 7 + itemStack.getItem().getMaxDamage() / 100;
							this.getAttribute(Attributes.MAX_HEALTH).setBaseValue(health);
							this.handDropChances[EquipmentSlot.MAINHAND.getIndex()] = 2.0f;
							return InteractionResult.SUCCESS;
						}
						else if (this.getTarget() == null) {
							this.heal(4);
							this.level().broadcastEntityEvent(this, (byte) 11);
							return super.mobInteract(player, interactionHand);
						}
					}
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
			}
			else {
				if (targetJavelinWeapon(itemStack)) {
					if (!this.isTame()) {
						this.tame(player);
						this.jumping = false;
						this.navigation.stop();
						this.setTarget(null);
						setChangeType(1, player);
						this.level().broadcastEntityEvent(this, (byte) 7);
						return InteractionResult.SUCCESS;
					}
					else {
						this.level().broadcastEntityEvent(this, (byte) 6);
					}
					this.setPersistenceRequired();
					return InteractionResult.SUCCESS;
				}
				else {
					retval = super.mobInteract(player, interactionHand);
					if (retval == InteractionResult.SUCCESS || retval == InteractionResult.CONSUME)
						this.setPersistenceRequired();
				}
			}
		}
		return retval;
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

	protected void spawnTamingParticles(boolean bl) {
		ParticleOptions particleoptions = ParticleTypes.HEART;
		if (!bl) {
			particleoptions = ParticleTypes.SMOKE;
		}
		for(int i = 0; i < 7; ++i) {
			double d0 = this.random.nextGaussian() * 0.02D;
			double d1 = this.random.nextGaussian() * 0.02D;
			double d2 = this.random.nextGaussian() * 0.02D;
			this.level().addParticle(particleoptions, this.getRandomX(1.0D), this.getRandomY() + 0.5D, this.getRandomZ(1.0D), d0, d1, d2);
		}
	}

	protected void spawnHealParticles() {
		ParticleOptions particleoptions = ParticleTypes.HAPPY_VILLAGER;
		for(int i = 0; i < 7; ++i) {
			double d0 = this.random.nextGaussian() * 0.02D;
			double d1 = this.random.nextGaussian() * 0.02D;
			double d2 = this.random.nextGaussian() * 0.02D;
			this.level().addParticle(particleoptions, this.getRandomX(1.0D), this.getRandomY() + 0.5D, this.getRandomZ(1.0D), d0, d1, d2);
		}
	}

	private boolean swapItem(Player player, EquipmentSlot equipmentSlot, ItemStack itemStack, InteractionHand interactionHand) {
		ItemStack itemStack2 = this.getItemBySlot(equipmentSlot);
		if (player.getAbilities().instabuild && itemStack2.isEmpty() && !itemStack.isEmpty()) {
			this.setItemSlot(equipmentSlot, itemStack.copyWithCount(1));
			return true;
		}
		if (!itemStack.isEmpty() && itemStack.getCount() > 1) {
			if (!itemStack2.isEmpty()) {
				return false;
			}
			this.setItemSlot(equipmentSlot, itemStack.split(1));
			return true;
		}
		this.setItemSlot(equipmentSlot, itemStack);
		player.setItemInHand(interactionHand, itemStack2);
		return true;
	}

	@Override
	public void aiStep() {
		super.aiStep();
		this.updateSwingTime();
		this.updateNoActionTime();
		if (this.random.nextInt(900) == 1 && this.deathTime == 0) {
			this.heal(3.0f);
		}
		//停止战斗
		if (this.getTarget() != null && (!this.getTarget().isAlive() || this.getTarget() instanceof Player player && (player.isCreative() || player.isSpectator()))) {
			this.setTarget(null);
		}
		//
		if (!this.level().isClientSide) {
			this.updatePersistentAnger((ServerLevel) this.level(), true);
		}
		if (this.getOwner() != null && this.getOwnerId() == -1) {
			this.setOwnerId(this.getOwner().getId());
		}
		if (this.isWander() && this.isInSittingPose()) {
			if (!this.level().isClientSide) {
				this.setInSittingPose(false);
			}
		}
	}

	protected void updateNoActionTime() {
		float f = this.getLightLevelDependentMagicValue();
		if (f > 0.5f) {
			this.noActionTime += 2.5;
		}
	}

	@Override
	public boolean doHurtTarget(Entity entity) {
		boolean bl = super.doHurtTarget(entity);
		if (bl) {
			if (!this.isSilent()) {
				this.playSound(JerotesVillageSoundEvents.SPEAR_MACHINE_ATTACK, 1.0f, 1.0f);
			}
			if (JerotesGameRules.JEROTES_MELEE_CAN_BREAK != null && this.level().getLevelData().getGameRules().getBoolean(JerotesGameRules.JEROTES_MELEE_CAN_BREAK)) {
				ItemStack hand = this.getMainHandItem();
				hand.hurtAndBreak(1, this, player -> player.broadcastBreakEvent(EquipmentSlot.MAINHAND));
			}
		}
		return bl;
	}

	@Override
	public boolean isInvulnerableTo(DamageSource damageSource) {
		if (damageSource.is(DamageTypes.CACTUS)
				|| damageSource.is(DamageTypes.SWEET_BERRY_BUSH)
				|| damageSource.is(DamageTypes.THORNS)
				|| damageSource.is(DamageTypeTags.IS_FALL)
				|| AttackFind.isWaterDamage(damageSource)
				|| damageSource.is(DamageTypes.DRY_OUT)
				|| damageSource.is(DamageTypes.IN_WALL)
				|| AttackFind.isPsychicDamage(damageSource))
			return true;
		return super.isInvulnerableTo(damageSource);
	}
	@Override
	public boolean hurt(DamageSource damageSource, float amount) {
		if (isInvulnerableTo(damageSource)) {
			return super.hurt(damageSource, amount);
		}
		boolean bl = super.hurt(damageSource, amount);
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
	public boolean canBeAffected(MobEffectInstance mobEffectInstance) {
		if (mobEffectInstance.getEffect() == MobEffects.POISON) {
			return false;
		}
		if (mobEffectInstance.getEffect() == JerotesMobEffects.DEADLY_POISON.get()) {
			return false;
		}
		return super.canBeAffected(mobEffectInstance);
	}

	@Override
	public void handleEntityEvent(byte by) {
		if (by == 7) {
			this.spawnTamingParticles(true);
		} else if (by == 6) {
			this.spawnTamingParticles(false);
		} else if (by == 11) {
			this.spawnHealParticles();
		} else {
			super.handleEntityEvent(by);
		}
	}

	@Override
	public boolean isAlliedTo(Entity entity) {
		if (this.isTame()) {
			LivingEntity livingentity = this.getOwner();
			if (entity == livingentity) {
				return true;
			}
			if (livingentity != null) {
				return livingentity.isAlliedTo(entity);
			}
		}
		return super.isAlliedTo(entity);
	}

	@Override
	public boolean canAttack(LivingEntity livingEntity) {
		return !this.isOwnedBy(livingEntity) && super.canAttack(livingEntity);
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
		Component deathMessage = this.getCombatTracker().getDeathMessage();
		super.die(damageSource);
		if (this.dead)
			if (!this.level().isClientSide && this.level().getGameRules().getBoolean(GameRules.RULE_SHOWDEATHMESSAGES) && this.getOwner() instanceof ServerPlayer) {
				this.getOwner().sendSystemMessage(deathMessage);
			}
	}

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