package com.jerotes.jerotesvillage.entity.Animal;

import com.jerotes.jerotes.entity.Interface.ChangePoseAbout;
import com.jerotes.jerotes.entity.Interface.FactionEntity;
import com.jerotes.jerotes.entity.Interface.JerotesEntity;
import com.jerotes.jerotes.util.AttackFind;
import com.jerotes.jerotesvillage.entity.Boss.Biome.GemstoneMalignasaurEntity;
import com.jerotes.jerotes.entity.Interface.HasHomePosEntity;
import com.jerotes.jerotes.goal.JerotesBaseTamableAnimalGoHomeGoal;
import com.jerotes.jerotes.goal.*;
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
import net.minecraft.util.Mth;
import net.minecraft.world.DifficultyInstance;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.*;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.ServerLevelAccessor;

import javax.annotation.Nullable;
import java.util.UUID;

public abstract class BaseTamableAnimalEntity extends TamableAnimal implements JerotesEntity, ChangePoseAbout, HasHomePosEntity {
	protected static final EntityDataAccessor<Integer> DATA_OWNER_ID = SynchedEntityData.defineId(BaseTamableAnimalEntity.class, EntityDataSerializers.INT);
	public static final EntityDataAccessor<BlockPos> HOME_POS = SynchedEntityData.defineId(BaseTamableAnimalEntity.class, EntityDataSerializers.BLOCK_POS);
	private static final EntityDataAccessor<Boolean> IS_WANDER = SynchedEntityData.defineId(BaseTamableAnimalEntity.class, EntityDataSerializers.BOOLEAN);
	public static final EntityDataAccessor<Boolean> GOING_HOME = SynchedEntityData.defineId(BaseTamableAnimalEntity.class, EntityDataSerializers.BOOLEAN);
	private static final EntityDataAccessor<Integer> WATER_ANIM = SynchedEntityData.defineId(BaseTamableAnimalEntity.class, EntityDataSerializers.INT);

	public BaseTamableAnimalEntity(EntityType<? extends BaseTamableAnimalEntity> entityType, Level level) {
		super(entityType, level);
		setPersistenceRequired();
	}

	@Override
	protected void registerGoals() {
		if (!(this instanceof PrancedDolphinEntity) && !(this instanceof MalignasaurEntity)) {
			this.goalSelector.addGoal(0, new JerotesAnimalChangeSitWhenOrderedToGoal(this));
		}

		this.goalSelector.addGoal(3, new JerotesBaseTamableAnimalGoHomeGoal(this, 1.0f));
		if (!(this.isJerotesFlyingMob())) {
			this.goalSelector.addGoal(3, new JerotesAnimalChangeFollowOwnerGoal(this, 1.3, 5.0f, 1.0f, false));
		}
		else {
			this.goalSelector.addGoal(3, new JerotesAnimalChangeFollowOwnerGoal(this, 1.3, 5.0f, 1.0f, true));
		}
		this.goalSelector.addGoal(3, new JerotesAnimalChangeFollowMobOwnerGoal(this, 1.0f));
		this.targetSelector.addGoal(1, new JerotesAnimalChangeHelpMobOwnerGoal(this));
		this.targetSelector.addGoal(1, new JerotesAnimalChangeOwnerHurtByTargetGoal(this));
		this.targetSelector.addGoal(2, new JerotesAnimalChangeOwnerHurtTargetGoal(this));
	}

	public float thisTickRenderTime = 0;
	public float lastTickRenderTime = 6;
	public float waterAnimProgress = 0.0f;
	public float attackAnimProgress = 0.0f;
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
	public int getWaterAnim() {
		return this.getEntityData().get(WATER_ANIM);
	}
	public void setWaterAnim(int n) {
		this.getEntityData().set(WATER_ANIM, n);
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
		compoundTag.putInt("WaterAnim", this.getWaterAnim());
	}
	@Override
	public void readAdditionalSaveData(CompoundTag compoundTag) {
		super.readAdditionalSaveData(compoundTag);
		this.setWander(compoundTag.getBoolean("IsWander"));
		this.setGoingHome(compoundTag.getBoolean("IsGoingHome"));
		int n = compoundTag.getInt("HomePosX");
		int n2 = compoundTag.getInt("HomePosY");
		int n3 = compoundTag.getInt("HomePosZ");
		this.setWaterAnim(compoundTag.getInt("WaterAnim"));
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
		this.getEntityData().define(WATER_ANIM, 0);
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
		if (level().isClientSide()) {
			lastTickRenderTime = thisTickRenderTime;
			thisTickRenderTime = 0;
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
		if (this.getRandom().nextInt(900) == 1 && this.isAlive()) {
			this.heal(3.0f);
		}
		//停止战斗
		if (this.getTarget() != null && (!this.getTarget().isAlive() || this.getTarget() instanceof Player player && (player.isCreative() || player.isSpectator()))) {
			this.setTarget(null);
		}
		//
		//水
		if (this.isInWater() || (this instanceof GemstoneMalignasaurEntity gemstoneMalignasaur && gemstoneMalignasaur.isInRain())) {
			if (!this.level().isClientSide()) {
				this.setWaterAnim(Math.min(this.getWaterAnim() + 1, 40));
			}
		}
		else {
			if (!this.level().isClientSide()) {
				this.setWaterAnim(Math.max(this.getWaterAnim() - 1, 0));
			}
		}
	}

	@Override
	public boolean hurt(DamageSource damageSource, float amount) {
		if (isInvulnerableTo(damageSource)) {
			return super.hurt(damageSource, amount);
		}
		boolean bl = super.hurt(damageSource, amount);
		if (bl && !(this instanceof ThornyTortoiseEntity)) {
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
		return AttackFind.wantsToAttack(this, livingEntity, livingEntity2);
	}

	@Nullable
	@Override
	public SpawnGroupData finalizeSpawn(ServerLevelAccessor serverLevelAccessor, DifficultyInstance difficultyInstance, MobSpawnType mobSpawnType, @Nullable SpawnGroupData spawnGroupData, @Nullable CompoundTag compoundTag) {
		this.setHomePos(this.blockPosition());
		return super.finalizeSpawn(serverLevelAccessor, difficultyInstance, mobSpawnType, spawnGroupData, compoundTag);
	}
}

