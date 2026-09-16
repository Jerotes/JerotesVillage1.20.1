package com.jerotes.jerotesvillage.entity.Animal;

import com.jerotes.jerotes.entity.Interface.CanBeIllagerFactionEntity;
import com.jerotes.jerotes.entity.Interface.ChangePoseAbout;
import com.jerotes.jerotes.entity.Interface.FactionEntity;
import com.jerotes.jerotes.entity.Interface.JerotesEntity;
import com.jerotes.jerotes.goal.JerotesBreedGoal;
import com.jerotes.jerotes.goal.JerotesIllagerFactionEntityHelpIllagerGoal;
import com.jerotes.jerotes.goal.JerotesMeleeAttackGoal;
import com.jerotes.jerotes.util.AttackFind;
import com.jerotes.jerotes.entity.Interface.HasHomePosEntity;
import com.jerotes.jerotes.goal.JerotesBaseTamableAnimalGoHomeGoal;
import com.jerotes.jerotes.goal.*;
import com.jerotes.jerotesvillage.init.JerotesVillageBiomes;
import com.jerotes.jerotesvillage.init.JerotesVillageEntityType;
import net.minecraft.ChatFormatting;
import net.minecraft.core.BlockPos;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.chat.Component;
import net.minecraft.network.syncher.EntityDataAccessor;
import net.minecraft.network.syncher.EntityDataSerializers;
import net.minecraft.network.syncher.SynchedEntityData;
import net.minecraft.server.MinecraftServer;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.tags.BiomeTags;
import net.minecraft.tags.DamageTypeTags;
import net.minecraft.util.Mth;
import net.minecraft.world.DifficultyInstance;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.damagesource.DamageTypes;
import net.minecraft.world.entity.*;
import net.minecraft.world.entity.ai.attributes.AttributeSupplier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.ai.goal.*;
import net.minecraft.world.entity.ai.goal.target.HurtByTargetGoal;
import net.minecraft.world.entity.ai.goal.target.NearestAttackableTargetGoal;
import net.minecraft.world.entity.ai.goal.target.ResetUniversalAngerTargetGoal;
import net.minecraft.world.entity.animal.Wolf;
import net.minecraft.world.entity.animal.horse.Llama;
import net.minecraft.world.entity.monster.AbstractIllager;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.entity.projectile.AbstractArrow;
import net.minecraft.world.item.*;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.ServerLevelAccessor;
import net.minecraft.world.level.gameevent.GameEvent;

import javax.annotation.Nullable;
import java.util.UUID;

public class WildernessWolfEntity extends Wolf implements JerotesEntity, CanBeIllagerFactionEntity, ChangePoseAbout, HasHomePosEntity {
	protected static final EntityDataAccessor<Integer> DATA_OWNER_ID = SynchedEntityData.defineId(WildernessWolfEntity.class, EntityDataSerializers.INT);
	public static final EntityDataAccessor<BlockPos> HOME_POS = SynchedEntityData.defineId(WildernessWolfEntity.class, EntityDataSerializers.BLOCK_POS);
	private static final EntityDataAccessor<Boolean> IS_WANDER = SynchedEntityData.defineId(WildernessWolfEntity.class, EntityDataSerializers.BOOLEAN);
	public static final EntityDataAccessor<Boolean> GOING_HOME = SynchedEntityData.defineId(WildernessWolfEntity.class, EntityDataSerializers.BOOLEAN);
	private static final EntityDataAccessor<Boolean> IS_ILLAGER_FACTION = SynchedEntityData.defineId(WildernessWolfEntity.class, EntityDataSerializers.BOOLEAN);
	private static final EntityDataAccessor<Boolean> IS_SNOW = SynchedEntityData.defineId(WildernessWolfEntity.class, EntityDataSerializers.BOOLEAN);

	public WildernessWolfEntity(EntityType<? extends WildernessWolfEntity> entityType, Level level) {
		super(entityType, level);
		setPersistenceRequired();
		this.fixupDimensions();
	}

	public static AttributeSupplier.Builder createAttributes() {
		AttributeSupplier.Builder builder = Mob.createMobAttributes();
		builder = builder.add(Attributes.MAX_HEALTH, 30);
		builder = builder.add(Attributes.ATTACK_DAMAGE, 5);
		builder = builder.add(Attributes.MOVEMENT_SPEED, 0.32);
		return builder;
	}
	@Override
	protected void registerGoals() {
		this.goalSelector.addGoal(1, new FloatGoal(this));
		this.goalSelector.addGoal(1, new WolfPanicGoal(1.5));
		this.goalSelector.addGoal(2, new JerotesAnimalChangeSitWhenOrderedToGoal(this));
		this.goalSelector.addGoal(3, new WolfAvoidEntityGoal<>(this, Llama.class, 24.0F, 1.5, 1.5));
		this.goalSelector.addGoal(4, new LeapAtTargetGoal(this, 0.4F));
		this.goalSelector.addGoal(5, new JerotesMeleeAttackGoal(this, 1.0, true));
		this.goalSelector.addGoal(6, new JerotesBaseTamableAnimalGoHomeGoal(this, 1.0f));
		this.goalSelector.addGoal(6, new JerotesAnimalChangeFollowOwnerGoal(this, 1.3, 5.0f, 1.0f, false));
		this.goalSelector.addGoal(6, new JerotesAnimalChangeFollowMobOwnerGoal(this, 1.0f));
		this.goalSelector.addGoal(7, new JerotesBreedGoal(this, 1.0));
		this.goalSelector.addGoal(8, new WaterAvoidingRandomStrollGoal(this, 1.0));
		this.goalSelector.addGoal(9, new BegGoal(this, 8.0F));
		this.goalSelector.addGoal(10, new LookAtPlayerGoal(this, Player.class, 8.0F));
		this.goalSelector.addGoal(10, new RandomLookAroundGoal(this));
		this.targetSelector.addGoal(1, new JerotesIllagerFactionEntityHelpIllagerGoal(this, LivingEntity.class, false, false, livingEntity -> livingEntity instanceof LivingEntity));
		this.targetSelector.addGoal(1, new JerotesAnimalChangeHelpMobOwnerGoal(this));
		this.targetSelector.addGoal(1, new JerotesAnimalChangeOwnerHurtByTargetGoal(this));
		this.targetSelector.addGoal(2, new JerotesAnimalChangeOwnerHurtTargetGoal(this));
		this.targetSelector.addGoal(3, (new HurtByTargetGoal(this, new Class[0])).setAlertOthers(new Class[0]));
		this.targetSelector.addGoal(4, new NearestAttackableTargetGoal<Player>(this, Player.class, 10, true, false, this::isAngryAt));
		this.targetSelector.addGoal(8, new ResetUniversalAngerTargetGoal<>(this, true));
	}
	class WolfPanicGoal extends PanicGoal {
		public WolfPanicGoal(double p_203124_) {
			super(WildernessWolfEntity.this, p_203124_);
		}

		protected boolean shouldPanic() {
			return this.mob.isFreezing() || this.mob.isOnFire();
		}
	}

	class WolfAvoidEntityGoal<T extends LivingEntity> extends AvoidEntityGoal<T> {
		private final Wolf wolf;

		public WolfAvoidEntityGoal(Wolf p_30454_, Class<T> p_30455_, float p_30456_, double p_30457_, double p_30458_) {
			super(p_30454_, p_30455_, p_30456_, p_30457_, p_30458_);
			this.wolf = p_30454_;
		}

		public boolean canUse() {
			if (super.canUse() && this.toAvoid instanceof Llama) {
				return !this.wolf.isTame() && this.avoidLlama((Llama)this.toAvoid);
			} else {
				return false;
			}
		}

		private boolean avoidLlama(Llama p_30461_) {
			return p_30461_.getStrength() >= WildernessWolfEntity.this.random.nextInt(5);
		}

		public void start() {
			WildernessWolfEntity.this.setTarget((LivingEntity)null);
			super.start();
		}

		public void tick() {
			WildernessWolfEntity.this.setTarget((LivingEntity)null);
			super.tick();
		}
	}

	@Nullable
	@Override
	public WildernessWolfEntity getBreedOffspring(ServerLevel serverLevel, AgeableMob ageableMob) {
		UUID uUID;
		WildernessWolfEntity wolf = JerotesVillageEntityType.WILDERNESS_WOLF.get().create(serverLevel);
		if (wolf != null && (uUID = this.getOwnerUUID()) != null) {
			wolf.setOwnerUUID(uUID);
			wolf.setTame(true);
			if (ageableMob instanceof WildernessWolfEntity wildernessWolf) {
				if (this.random.nextBoolean()) {
					wolf.setSnow(this.isSnow());
				} else {
					wolf.setSnow(wildernessWolf.isSnow());
				}
			}
			else {
				wolf.setSnow(this.isSnow());
			}
		}
		else if (wolf != null) {
			if (ageableMob instanceof WildernessWolfEntity wildernessWolf) {
				if (this.random.nextBoolean()) {
					wolf.setSnow(this.isSnow());
				} else {
					wolf.setSnow(wildernessWolf.isSnow());
				}
			}
			else {
				wolf.setSnow(this.isSnow());
			}
		}
		return wolf;
	}

	@Override
	public boolean isIllagerFaction() {
		return this.getEntityData().get(IS_ILLAGER_FACTION);
	}
	@Override
	public void setIllagerFaction(boolean bl) {
		this.getEntityData().set(IS_ILLAGER_FACTION, bl);
	}
	public boolean isSnow() {
		return this.getEntityData().get(IS_SNOW);
	}
	public void setSnow(boolean bl) {
		this.getEntityData().set(IS_SNOW, bl);
		this.refreshDimensions();
	}
	//体型相关
	@Override
	public EntityDimensions getDimensions(Pose pose) {
		float scale = 1f;
		if (this.isSnow())
			scale = 0.85f;
		return super.getDimensions(pose).scale(scale);
	}
    @Override
	public void setTame(boolean bl) {
		super.setTame(bl);
		if (bl) {
			this.setIllagerFaction(false);
			this.getAttribute(Attributes.MAX_HEALTH).setBaseValue(40);
			this.getAttribute(Attributes.ATTACK_DAMAGE).setBaseValue(7.0);
			this.setHealth(40.0f);
		} else {
			this.getAttribute(Attributes.MAX_HEALTH).setBaseValue(30);
			this.getAttribute(Attributes.ATTACK_DAMAGE).setBaseValue(5.0);
		}
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
		compoundTag.putBoolean("IsIllagerFaction", this.isIllagerFaction());
		compoundTag.putBoolean("IsSnow", this.isSnow());
	}
	@Override
	public void readAdditionalSaveData(CompoundTag compoundTag) {
		super.readAdditionalSaveData(compoundTag);
		this.setIllagerFaction(compoundTag.getBoolean("IsIllagerFaction"));
		this.setSnow(compoundTag.getBoolean("IsSnow"));
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
		this.getEntityData().define(IS_ILLAGER_FACTION, false);
		this.getEntityData().define(IS_SNOW, false);
		this.getEntityData().define(IS_WANDER, false);
		this.getEntityData().define(DATA_OWNER_ID, -1);
		this.getEntityData().define(HOME_POS, BlockPos.ZERO);
		this.getEntityData().define(GOING_HOME, false);
	}
	@Override
	public void onSyncedDataUpdated(EntityDataAccessor<?> entityDataAccessor) {
		if (IS_ILLAGER_FACTION.equals(entityDataAccessor)) {
			this.refreshDimensions();
		}
		if (IS_SNOW.equals(entityDataAccessor)) {
			this.refreshDimensions();
		}
		super.onSyncedDataUpdated(entityDataAccessor);
	}

	@Override
	public void aiStep() {
		super.aiStep();
		if (this.random.nextInt(900) == 1 && this.deathTime == 0) {
			this.heal(3.0f);
		}
		//停止战斗
		if (this.getTarget() != null && (!this.getTarget().isAlive() || this.getTarget() instanceof Player player && (player.isCreative() || player.isSpectator()))){
			this.setTarget(null);
		}
		//
		if (this.getOwner() != null && this.getOwnerId() == -1) {
			this.setOwnerId(this.getOwner().getId());
		}

		if (this.isWander() && this.isInSittingPose()) {
			if (!this.level().isClientSide) {
				this.setInSittingPose(false);
			}
		}
	}

	@Override
	public InteractionResult mobInteract(Player player, InteractionHand interactionHand) {
		ItemStack itemStack = player.getItemInHand(interactionHand);
		Item item = itemStack.getItem();
		if (this.level().isClientSide) {
			boolean bl = this.isOwnedBy(player) || this.isTame() || this.isFood(itemStack) && !this.isTame() && !this.isAngry();
			boolean bl2 = this.isTame() && !player.isShiftKeyDown() && this.isBaby();
			if (!bl2) {
				return bl ? InteractionResult.CONSUME : InteractionResult.PASS;
			}
		}
		if (this.isTame()) {
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
		if (!itemStack.is(Items.BONE) || this.isAngry())
			return super.mobInteract(player, interactionHand);
		if (!player.getAbilities().instabuild) {
			itemStack.shrink(1);
		}
		if (this.random.nextInt(6) == 1 && !net.minecraftforge.event.ForgeEventFactory.onAnimalTame(this, player)) {
			this.tame(player);
			this.navigation.stop();
			this.setTarget(null);
			setChangeType(1, player);
			((Level)this.level()).broadcastEntityEvent(this, (byte)7);
		}
		else {
			((Level)this.level()).broadcastEntityEvent(this, (byte)6);
		}
		return InteractionResult.SUCCESS;
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
		Entity entity = damageSource.getEntity();
		if (entity != null && !(entity instanceof Player) && !(entity instanceof AbstractArrow)) {
			amount = (amount + 1.0F) / 2.0F;
		}

		int freezeAmount = 2;
		if (AttackFind.isColdDamage(damageSource) && this.isSnow())
			amount = amount / freezeAmount;

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
	public boolean wantsToAttack(LivingEntity livingEntity, LivingEntity livingEntity2) {
		//恶魂和苦力怕
		return AttackFind.wantsToAttack(this, livingEntity, livingEntity2);
	}

	public boolean canAttack(LivingEntity livingEntity) {
		if (this.isIllagerFaction() && livingEntity instanceof AbstractIllager && ((this.getTeam() == null && livingEntity.getTeam() == null) || this.getTeam() == livingEntity.getTeam())) {
			return false;
		}
		return super.canAttack(livingEntity);
	}

	@Nullable
	@Override
	public SpawnGroupData finalizeSpawn(ServerLevelAccessor serverLevelAccessor, DifficultyInstance difficultyInstance, MobSpawnType mobSpawnType, @Nullable SpawnGroupData spawnGroupData, @Nullable CompoundTag compoundTag) {
		this.setHomePos(this.blockPosition());

		if (mobSpawnType != MobSpawnType.CONVERSION && mobSpawnType != MobSpawnType.BREEDING) {
			//苦寒之地
			if (serverLevelAccessor.getBiome(this.blockPosition()).is(BiomeTags.SPAWNS_SNOW_FOXES)) {
				this.setSnow(true);
			}
			else if (serverLevelAccessor.getBiome(this.blockPosition()).is(JerotesVillageBiomes.WILDERNESS_WOODLAND)) {
				this.setSnow(false);
			}
			else {
				this.setSnow(serverLevelAccessor.getRandom().nextFloat() > 0.9f);
			}
		}
		return super.finalizeSpawn(serverLevelAccessor, difficultyInstance, mobSpawnType, spawnGroupData, compoundTag);
	}
}