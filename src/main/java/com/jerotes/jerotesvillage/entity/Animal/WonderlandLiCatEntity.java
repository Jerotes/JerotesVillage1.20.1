package com.jerotes.jerotesvillage.entity.Animal;

import com.jerotes.jerotes.entity.Interface.ChangePoseAbout;
import com.jerotes.jerotes.entity.Interface.FactionEntity;
import com.jerotes.jerotes.entity.Interface.JerotesEntity;
import com.jerotes.jerotes.goal.JerotesBreedGoal;
import com.jerotes.jerotes.util.Main;
import com.jerotes.jerotesvillage.config.OtherMainConfig;
import com.jerotes.jerotes.entity.Interface.HasHomePosEntity;
import com.jerotes.jerotes.goal.JerotesBaseTamableAnimalGoHomeGoal;
import com.jerotes.jerotes.goal.*;
import com.jerotes.jerotesvillage.init.JerotesVillageBuiltInLootTable;
import com.jerotes.jerotesvillage.init.JerotesVillageEntityType;
import it.unimi.dsi.fastutil.objects.ObjectListIterator;
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
import net.minecraft.tags.BlockTags;
import net.minecraft.tags.ItemTags;
import net.minecraft.util.Mth;
import net.minecraft.util.RandomSource;
import net.minecraft.util.TimeUtil;
import net.minecraft.util.valueproviders.UniformInt;
import net.minecraft.world.DifficultyInstance;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.*;
import net.minecraft.world.entity.ai.attributes.AttributeSupplier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.ai.goal.*;
import net.minecraft.world.entity.ai.goal.target.HurtByTargetGoal;
import net.minecraft.world.entity.ai.goal.target.NearestAttackableTargetGoal;
import net.minecraft.world.entity.ai.goal.target.ResetUniversalAngerTargetGoal;
import net.minecraft.world.entity.animal.Cat;
import net.minecraft.world.entity.item.ItemEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.entity.projectile.AbstractArrow;
import net.minecraft.world.item.*;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.level.ItemLike;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.ServerLevelAccessor;
import net.minecraft.world.level.block.BedBlock;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.gameevent.GameEvent;
import net.minecraft.world.level.storage.loot.LootParams;
import net.minecraft.world.level.storage.loot.LootTable;
import net.minecraft.world.level.storage.loot.parameters.LootContextParamSets;
import net.minecraft.world.level.storage.loot.parameters.LootContextParams;
import net.minecraft.world.phys.AABB;

import javax.annotation.Nullable;
import java.util.Iterator;
import java.util.List;
import java.util.Objects;
import java.util.UUID;

public class WonderlandLiCatEntity extends Cat implements JerotesEntity, NeutralMob, ChangePoseAbout, HasHomePosEntity {
	protected static final EntityDataAccessor<Integer> DATA_OWNER_ID = SynchedEntityData.defineId(WonderlandLiCatEntity.class, EntityDataSerializers.INT);
	public static final EntityDataAccessor<BlockPos> HOME_POS = SynchedEntityData.defineId(WonderlandLiCatEntity.class, EntityDataSerializers.BLOCK_POS);
	private static final EntityDataAccessor<Boolean> IS_WANDER = SynchedEntityData.defineId(WonderlandLiCatEntity.class, EntityDataSerializers.BOOLEAN);
	public static final EntityDataAccessor<Boolean> GOING_HOME = SynchedEntityData.defineId(WonderlandLiCatEntity.class, EntityDataSerializers.BOOLEAN);
	private static final EntityDataAccessor<Integer> CAT_COLOR = SynchedEntityData.defineId(WonderlandLiCatEntity.class, EntityDataSerializers.INT);
	private static final EntityDataAccessor<Integer> CAT_WILD = SynchedEntityData.defineId(WonderlandLiCatEntity.class, EntityDataSerializers.INT);

	public WonderlandLiCatEntity(EntityType<? extends WonderlandLiCatEntity> entityType, Level level) {
		super(entityType, level);
		this.fixupDimensions();
		setPersistenceRequired();
	}

	public static AttributeSupplier.Builder createAttributes() {
		AttributeSupplier.Builder builder = Mob.createMobAttributes();
		builder = builder.add(Attributes.MAX_HEALTH, 10);
		builder = builder.add(Attributes.ATTACK_DAMAGE, 3);
		builder = builder.add(Attributes.MOVEMENT_SPEED, 0.3);
		return builder;
	}

	@Override
	public void MobInventoryAddTooltip(List<Component> tooltip, LivingEntity livingEntity) {
		tooltip.add(Component.translatable("message.jerotesvillage.mob_wild", this.getCatWild()).withStyle(ChatFormatting.AQUA));
	}
	@Override
	protected void registerGoals() {
		this.goalSelector.addGoal(1, new FloatGoal(this));
		this.goalSelector.addGoal(1, new PanicGoal(this, 1.5) {
			public boolean canUse() {
				if (WonderlandLiCatEntity.this.getCatWild() >= 5 && !WonderlandLiCatEntity.this.isBaby() || WonderlandLiCatEntity.this.getCatWild() >= 10) {
					return false;
				}
				return super.canUse();
			}
			public boolean canContinueToUse() {
				if (WonderlandLiCatEntity.this.getCatWild() >= 5 && !WonderlandLiCatEntity.this.isBaby() || WonderlandLiCatEntity.this.getCatWild() >= 10) {
					return false;
				}
				return super.canContinueToUse();
			}
		});
		this.goalSelector.addGoal(2, new JerotesAnimalChangeSitWhenOrderedToGoal(this));
		this.goalSelector.addGoal(3, new JerotesBaseTamableAnimalGoHomeGoal(this, 1.0f));
		this.goalSelector.addGoal(3, new JerotesAnimalChangeFollowOwnerGoal(this, 1.3, 5.0f, 1.0f, false));
		this.goalSelector.addGoal(3, new JerotesAnimalChangeFollowMobOwnerGoal(this, 1.0f));
		this.goalSelector.addGoal(3, new CatRelaxOnOwnerGoal(this));
		this.goalSelector.addGoal(4, new CatTemptGoal(this, 0.6, true));
		this.goalSelector.addGoal(5, new CatLieOnBedGoal(this, 1.1, 8));
		this.goalSelector.addGoal(7, new CatSitOnBlockGoal(this, 0.8));
		this.goalSelector.addGoal(8, new LeapAtTargetGoal(this, 0.3F));
		this.goalSelector.addGoal(9, new OcelotAttackGoal(this));
		this.goalSelector.addGoal(10, new JerotesBreedGoal(this, 0.8));
		this.goalSelector.addGoal(11, new WaterAvoidingRandomStrollGoal(this, 0.8, 1.0000001E-5F));
		this.goalSelector.addGoal(12, new LookAtPlayerGoal(this, Player.class, 10.0F));
		this.targetSelector.addGoal(1, new JerotesAnimalChangeHelpMobOwnerGoal(this) {
			public boolean canUse() {
				if ((WonderlandLiCatEntity.this.getCatWild() < 5 || WonderlandLiCatEntity.this.isBaby()) && WonderlandLiCatEntity.this.getCatWild() < 10) {
					return false;
				}
				return super.canUse();
			}
			public boolean canContinueToUse() {
				if ((WonderlandLiCatEntity.this.getCatWild() < 5 || WonderlandLiCatEntity.this.isBaby()) && WonderlandLiCatEntity.this.getCatWild() < 10) {
					return false;
				}
				return super.canContinueToUse();
			}
		});
		this.targetSelector.addGoal(1, new JerotesAnimalChangeOwnerHurtByTargetGoal(this) {
			public boolean canUse() {
				if ((WonderlandLiCatEntity.this.getCatWild() < 5 || WonderlandLiCatEntity.this.isBaby()) && WonderlandLiCatEntity.this.getCatWild() < 10) {
					return false;
				}
				return super.canUse();
			}
			public boolean canContinueToUse() {
				if ((WonderlandLiCatEntity.this.getCatWild() < 5 || WonderlandLiCatEntity.this.isBaby()) && WonderlandLiCatEntity.this.getCatWild() < 10) {
					return false;
				}
				return super.canContinueToUse();
			}
		});
		this.targetSelector.addGoal(2, new JerotesAnimalChangeOwnerHurtTargetGoal(this) {
			public boolean canUse() {
				if ((WonderlandLiCatEntity.this.getCatWild() < 5 || WonderlandLiCatEntity.this.isBaby()) && WonderlandLiCatEntity.this.getCatWild() < 10) {
					return false;
				}
				return super.canUse();
			}
			public boolean canContinueToUse() {
				if ((WonderlandLiCatEntity.this.getCatWild() < 5 || WonderlandLiCatEntity.this.isBaby()) && WonderlandLiCatEntity.this.getCatWild() < 10) {
					return false;
				}
				return super.canContinueToUse();
			}
		});
		this.targetSelector.addGoal(2, new HurtByTargetGoal(this, new Class[0]));
		this.targetSelector.addGoal(3, new NearestAttackableTargetGoal<Player>(this, Player.class, 10, true, false, this::isAngryAt));
		this.targetSelector.addGoal(4, new ResetUniversalAngerTargetGoal<WonderlandLiCatEntity>(this, false));
	}

	public void tick() {
		super.tick();
	}

	public int getCatColor() {
		return this.getEntityData().get(CAT_COLOR);
	}
	public void setCatColor(int n) {
		this.getEntityData().set(CAT_COLOR, n);
	}
	public int getCatWild() {
		return this.getEntityData().get(CAT_WILD);
	}
	public void setCatWild(int n, boolean bl) {
		int n2 = Mth.clamp(n, 1, OtherMainConfig.WonderlandLiCatMaxWild);
		this.getEntityData().set(CAT_WILD, n2);
		Objects.requireNonNull(this.getAttribute(Attributes.MAX_HEALTH)).setBaseValue(10 + getCatWild());
		if (bl) {
			this.setHealth((float) 10 + getCatWild());
		}
		Objects.requireNonNull(this.getAttribute(Attributes.ATTACK_DAMAGE)).setBaseValue(3 + (double) getCatWild() / 4);
		Objects.requireNonNull(this.getAttribute(Attributes.MOVEMENT_SPEED)).setBaseValue(0.3 + (double) getCatWild() * 0.0025);
		this.setMaxUpStep(0.5f + this.getCatWild() * 0.05f);
		this.reapplyPosition();
		this.refreshDimensions();
	}
	@Override
	public void refreshDimensions() {
		double d = this.getX();
		double d2 = this.getY();
		double d3 = this.getZ();
		super.refreshDimensions();
		this.setPos(d, d2, d3);
	}
	public boolean isFood(ItemStack itemStack) {
		return itemStack.is(ItemTags.FISHES);
	}
	@Override
	public EntityDimensions getDimensions(Pose pose) {
		return super.getDimensions(pose).scale((float) (1.0f + 0.05 * (float)this.getCatWild()));
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
		compoundTag.putInt("CatColor", this.getCatColor());
		compoundTag.putInt("CatWild", this.getCatWild());
		this.addPersistentAngerSaveData(compoundTag);
	}
	@Override
	public void readAdditionalSaveData(CompoundTag compoundTag) {
		this.setCatWild(compoundTag.getInt("CatWild"), false);
		super.readAdditionalSaveData(compoundTag);
		this.setCatColor(compoundTag.getInt("CatColor"));
		this.setWander(compoundTag.getBoolean("IsWander"));
		this.setGoingHome(compoundTag.getBoolean("IsGoingHome"));
		int n = compoundTag.getInt("HomePosX");
		int n2 = compoundTag.getInt("HomePosY");
		int n3 = compoundTag.getInt("HomePosZ");
		this.setHomePos(new BlockPos(n, n2, n3));
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
		this.getEntityData().define(CAT_COLOR, 1);
		this.getEntityData().define(CAT_WILD, 1);
	}
	@Override
	public void onSyncedDataUpdated(EntityDataAccessor<?> entityDataAccessor) {
		if (CAT_WILD.equals(entityDataAccessor)) {
			this.refreshDimensions();
		}
		super.onSyncedDataUpdated(entityDataAccessor);
	}

	@Nullable
	@Override
	public WonderlandLiCatEntity getBreedOffspring(ServerLevel serverLevel, AgeableMob ageableMob) {
		UUID uUID;
		WonderlandLiCatEntity cat = JerotesVillageEntityType.WONDERLAND_LI_CAT.get().create(serverLevel);
		if (cat != null && (uUID = this.getOwnerUUID()) != null) {
			cat.setOwnerUUID(uUID);
			cat.setTame(true);
			if (ageableMob instanceof WonderlandLiCatEntity cat2) {
				if (this.random.nextBoolean()) {
					cat.setVariant(this.getVariant());
					cat.setCatColor(this.getCatColor());
					cat.setCatWild(this.getCatWild() + Main.randomReach(this.getRandom(), -2, 2), true);
				} else {
					cat.setVariant(cat2.getVariant());
					cat.setCatColor(cat2.getCatColor());
					cat.setCatWild(cat2.getCatWild() + Main.randomReach(cat2.getRandom(), -2, 2), true);
				}
				if (this.random.nextBoolean()) {
					cat.setCollarColor(this.getCollarColor());
				} else {
					cat.setCollarColor(cat2.getCollarColor());
				}
			}
		}
		if (cat != null) {
			if (ageableMob instanceof WonderlandLiCatEntity cat2) {
				if (this.random.nextBoolean()) {
					cat.setVariant(this.getVariant());
					cat.setCatColor(this.getCatColor());
					cat.setCatWild(this.getCatWild() + Main.randomReach(this.getRandom(), -2, 2), true);
				} else {
					cat.setVariant(cat2.getVariant());
					cat.setCatColor(cat2.getCatColor());
					cat.setCatWild(cat2.getCatWild() + Main.randomReach(cat2.getRandom(), -2, 2), true);
				}
			}
		}
		return cat;
	}

	@Override
	public void aiStep() {
		super.aiStep();
		if (!this.level().isClientSide) {
			this.updatePersistentAnger((ServerLevel) this.level(), true);
		}
		if (this.random.nextInt(900) == 1 && this.deathTime == 0) {
			this.heal(2.0f);
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
			boolean bl = this.isOwnedBy(player) || this.isTame() || this.isFood(itemStack) && !this.isTame();
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
						this.usePlayerItem(player, interactionHand, itemStack);
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
		if (!this.isFood(itemStack))
			return super.mobInteract(player, interactionHand);
		if (!player.getAbilities().instabuild) {
			itemStack.shrink(1);
		}
		if (this.random.nextInt(3) == 1 && !net.minecraftforge.event.ForgeEventFactory.onAnimalTame(this, player)) {
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
	public boolean hurt(DamageSource damageSource, float amount) {
		if (isInvulnerableTo(damageSource)) {
			return super.hurt(damageSource, amount);
		}
		Entity entity = damageSource.getEntity();
		if (entity != null && !(entity instanceof Player) && !(entity instanceof AbstractArrow)) {
			amount = (amount + 1.0F) / 2.0F;
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

	@Nullable
	@Override
	public SpawnGroupData finalizeSpawn(ServerLevelAccessor serverLevelAccessor, DifficultyInstance difficultyInstance, MobSpawnType mobSpawnType, @Nullable SpawnGroupData spawnGroupData, @Nullable CompoundTag compoundTag) {
		this.setHomePos(this.blockPosition());
		RandomSource randomSourceSkin = serverLevelAccessor.getRandom();
		if (mobSpawnType != MobSpawnType.CONVERSION && mobSpawnType != MobSpawnType.BREEDING) {
			this.setCatColor(Main.randomReach(randomSourceSkin, 1, 8));
			this.setCatWild(Main.randomReach(randomSourceSkin, 1, 5), true);
		}
		return super.finalizeSpawn(serverLevelAccessor, difficultyInstance, mobSpawnType, spawnGroupData, compoundTag);
	}

	static class CatTemptGoal extends TemptGoal {
		@Nullable
		private Player selectedPlayer;
		private final Cat cat;

		public CatTemptGoal(Cat p_28219_, double p_28220_, boolean p_28222_) {
			super(p_28219_, p_28220_, Ingredient.of(new ItemLike[]{Items.COD, Items.SALMON}), p_28222_);
			this.cat = p_28219_;
		}

		private boolean shouldFollow(LivingEntity livingEntity) {
			return livingEntity.getMainHandItem().is(ItemTags.FISHES) || livingEntity.getMainHandItem().is(ItemTags.FISHES);
		}


		public void tick() {
			super.tick();
			if (this.selectedPlayer == null && this.mob.getRandom().nextInt(this.adjustedTickDelay(600)) == 0) {
				this.selectedPlayer = this.player;
			} else if (this.mob.getRandom().nextInt(this.adjustedTickDelay(500)) == 0) {
				this.selectedPlayer = null;
			}

		}

		protected boolean canScare() {
			return this.selectedPlayer != null && this.selectedPlayer.equals(this.player) ? false : super.canScare();
		}

		public boolean canUse() {
			return super.canUse() && !this.cat.isTame();
		}
	}

	static class CatRelaxOnOwnerGoal extends Goal {
		private final Cat cat;
		@Nullable
		private Player ownerPlayer;
		@Nullable
		private BlockPos goalPos;
		private int onBedTicks;

		public CatRelaxOnOwnerGoal(Cat p_28203_) {
			this.cat = p_28203_;
		}

		public boolean canUse() {
			if (!this.cat.isTame()) {
				return false;
			} else if (this.cat.isOrderedToSit()) {
				return false;
			} else {
				LivingEntity livingentity = this.cat.getOwner();
				if (livingentity instanceof Player) {
					this.ownerPlayer = (Player)livingentity;
					if (!livingentity.isSleeping()) {
						return false;
					}

					if (this.cat.distanceToSqr(this.ownerPlayer) > 100.0) {
						return false;
					}

					BlockPos blockpos = this.ownerPlayer.blockPosition();
					BlockState blockstate = this.cat.level().getBlockState(blockpos);
					if (blockstate.is(BlockTags.BEDS)) {
						this.goalPos = (BlockPos)blockstate.getOptionalValue(BedBlock.FACING).map((p_28209_) -> {
							return blockpos.relative(p_28209_.getOpposite());
						}).orElseGet(() -> {
							return new BlockPos(blockpos);
						});
						return !this.spaceIsOccupied();
					}
				}

				return false;
			}
		}

		private boolean spaceIsOccupied() {
			Iterator var1 = this.cat.level().getEntitiesOfClass(Cat.class, (new AABB(this.goalPos)).inflate(2.0)).iterator();

			Cat cat;
			do {
				do {
					if (!var1.hasNext()) {
						return false;
					}

					cat = (Cat)var1.next();
				} while(cat == this.cat);
			} while(!cat.isLying() && !cat.isRelaxStateOne());

			return true;
		}

		public boolean canContinueToUse() {
			return this.cat.isTame() && !this.cat.isOrderedToSit() && this.ownerPlayer != null && this.ownerPlayer.isSleeping() && this.goalPos != null && !this.spaceIsOccupied();
		}

		public void start() {
			if (this.goalPos != null) {
				this.cat.setInSittingPose(false);
				this.cat.getNavigation().moveTo((double)this.goalPos.getX(), (double)this.goalPos.getY(), (double)this.goalPos.getZ(), 1.100000023841858);
			}

		}

		public void stop() {
			this.cat.setLying(false);
			float f = this.cat.level().getTimeOfDay(1.0F);
			if (this.ownerPlayer.getSleepTimer() >= 100 && (double)f > 0.77 && (double)f < 0.8 && (double)this.cat.level().getRandom().nextFloat() < 0.7) {
				this.giveMorningGift();
			}

			this.onBedTicks = 0;
			this.cat.setRelaxStateOne(false);
			this.cat.getNavigation().stop();
		}

		private void giveMorningGift() {
			RandomSource randomsource = this.cat.getRandom();
			BlockPos.MutableBlockPos blockpos$mutableblockpos = new BlockPos.MutableBlockPos();
			blockpos$mutableblockpos.set(this.cat.isLeashed() ? this.cat.getLeashHolder().blockPosition() : this.cat.blockPosition());
			this.cat.randomTeleport((double)(blockpos$mutableblockpos.getX() + randomsource.nextInt(11) - 5), (double)(blockpos$mutableblockpos.getY() + randomsource.nextInt(5) - 2), (double)(blockpos$mutableblockpos.getZ() + randomsource.nextInt(11) - 5), false);
			blockpos$mutableblockpos.set(this.cat.blockPosition());
			LootTable loottable = this.cat.level().getServer().getLootData().getLootTable(JerotesVillageBuiltInLootTable.LI_CAT_MORNING_GIFT);
			LootParams lootparams = (new LootParams.Builder((ServerLevel)this.cat.level())).withParameter(LootContextParams.ORIGIN, this.cat.position()).withParameter(LootContextParams.THIS_ENTITY, this.cat).create(LootContextParamSets.GIFT);
			ObjectListIterator var5 = loottable.getRandomItems(lootparams).iterator();

			while(var5.hasNext()) {
				ItemStack itemstack = (ItemStack)var5.next();
				this.cat.level().addFreshEntity(new ItemEntity(this.cat.level(), (double)blockpos$mutableblockpos.getX() - (double) Mth.sin(this.cat.yBodyRot * 0.017453292F), (double)blockpos$mutableblockpos.getY(), (double)blockpos$mutableblockpos.getZ() + (double)Mth.cos(this.cat.yBodyRot * 0.017453292F), itemstack));
			}

		}

		public void tick() {
			if (this.ownerPlayer != null && this.goalPos != null) {
				this.cat.setInSittingPose(false);
				this.cat.getNavigation().moveTo((double)this.goalPos.getX(), (double)this.goalPos.getY(), (double)this.goalPos.getZ(), 1.100000023841858);
				if (this.cat.distanceToSqr(this.ownerPlayer) < 2.5) {
					++this.onBedTicks;
					if (this.onBedTicks > this.adjustedTickDelay(16)) {
						this.cat.setLying(true);
						this.cat.setRelaxStateOne(false);
					} else {
						this.cat.lookAt(this.ownerPlayer, 45.0F, 45.0F);
						this.cat.setRelaxStateOne(true);
					}
				} else {
					this.cat.setLying(false);
				}
			}

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
