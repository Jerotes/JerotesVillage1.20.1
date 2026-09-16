package com.jerotes.jerotesvillage.entity.Animal;

import com.jerotes.jerotes.JerotesWarehouse;
import com.jerotes.jerotes.entity.Interface.CatchEntity;
import com.jerotes.jerotes.entity.Interface.FactionEntity;
import com.jerotes.jerotes.entity.Interface.JerotesEntity;
import com.jerotes.jerotes.entity.Mob.HumanEntity;
import com.jerotes.jerotes.goal.JerotesAnimalMeleeAttackGoal;
import com.jerotes.jerotes.goal.JerotesBreedGoal;
import com.jerotes.jerotes.goal.JerotesFollowParentGoal;
import com.jerotes.jerotes.init.JerotesItems;
import com.jerotes.jerotes.util.Main;
import com.jerotes.jerotesvillage.init.JerotesVillageEntityType;
import com.jerotes.jerotesvillage.init.JerotesVillageItems;
import com.jerotes.jerotesvillage.init.JerotesVillageMobEffects;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.syncher.EntityDataAccessor;
import net.minecraft.network.syncher.EntityDataSerializers;
import net.minecraft.network.syncher.SynchedEntityData;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.tags.ItemTags;
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
import net.minecraft.world.entity.ai.goal.*;
import net.minecraft.world.entity.ai.goal.target.NearestAttackableTargetGoal;
import net.minecraft.world.entity.ai.navigation.PathNavigation;
import net.minecraft.world.entity.ai.navigation.WallClimberNavigation;
import net.minecraft.world.entity.animal.Animal;
import net.minecraft.world.entity.monster.Endermite;
import net.minecraft.world.entity.monster.Silverfish;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.ServerLevelAccessor;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.gameevent.GameEvent;
import net.minecraft.world.phys.Vec3;

import javax.annotation.Nullable;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;


public class DownySpiderEntity extends Animal implements JerotesEntity, CatchEntity {
	private static final EntityDataAccessor<Boolean> DATA_COLOR_A_ID = SynchedEntityData.defineId(DownySpiderEntity.class, EntityDataSerializers.BOOLEAN);
	private static final EntityDataAccessor<Boolean> DATA_COLOR_B_ID = SynchedEntityData.defineId(DownySpiderEntity.class, EntityDataSerializers.BOOLEAN);
	private static final EntityDataAccessor<Boolean> FROM_CATCH = SynchedEntityData.defineId(DownySpiderEntity.class, EntityDataSerializers.BOOLEAN);
	private static final EntityDataAccessor<Integer> ANIM_STATE = SynchedEntityData.defineId(DownySpiderEntity.class, EntityDataSerializers.INT);
	private static final EntityDataAccessor<Integer> ANIM_TICK = SynchedEntityData.defineId(DownySpiderEntity.class, EntityDataSerializers.INT);
	public AnimationState attackAnimationState = new AnimationState();
	public AnimationState climbAnimationState = new AnimationState();
	public int webTime = this.random.nextInt(6000) + 6000;
	private static final Ingredient FOOD_ITEMS = Ingredient.of(
			ItemTags.create(new ResourceLocation(JerotesWarehouse.MODID, "animal_foods/fruit_foods"))
	);
	private static final EntityDataAccessor<Byte> DATA_FLAGS_ID = SynchedEntityData.defineId(DownySpiderEntity.class, EntityDataSerializers.BYTE);

	public DownySpiderEntity(EntityType<? extends DownySpiderEntity> entityType, Level level) {
		super(entityType, level);
	}

	@Override
	protected void registerGoals() {
		this.goalSelector.addGoal(1, new FloatGoal(this));
		this.goalSelector.addGoal(1, new JerotesAnimalMeleeAttackGoal(this, 1.3, true));
		this.goalSelector.addGoal(2, new PanicGoal(this, 1.3));
		this.goalSelector.addGoal(2, new JerotesBreedGoal(this, 1.1));
		this.goalSelector.addGoal(3, new TemptGoal(this, 1.1, FOOD_ITEMS, false));
		this.goalSelector.addGoal(4, new JerotesFollowParentGoal(this, 1.1));
		this.goalSelector.addGoal(5, new WaterAvoidingRandomStrollGoal(this, 0.8));
		this.goalSelector.addGoal(6, new LookAtPlayerGoal(this, Player.class, 8.0f));
		this.goalSelector.addGoal(6, new LookAtPlayerGoal(this, HumanEntity.class, 8.0f));
		this.goalSelector.addGoal(6, new RandomLookAroundGoal(this));
		this.targetSelector.addGoal(1, new NearestAttackableTargetGoal<Silverfish>(this, Silverfish.class, false));
		this.targetSelector.addGoal(1, new NearestAttackableTargetGoal<Endermite>(this, Endermite.class, false));
	}

	@Override
	protected PathNavigation createNavigation(Level level) {
		return new WallClimberNavigation(this, level);
	}

	public static AttributeSupplier.Builder createAttributes() {
		AttributeSupplier.Builder builder = Mob.createMobAttributes();
		builder = builder.add(Attributes.MOVEMENT_SPEED, 0.26);
		builder = builder.add(Attributes.MAX_HEALTH, 4);
		builder = builder.add(Attributes.ATTACK_DAMAGE, 3);
		builder = builder.add(Attributes.FOLLOW_RANGE, 20);
		return builder;
	}

	@Override
	protected float getStandingEyeHeight(Pose pose, EntityDimensions entityDimensions) {
		float height = 0.2f;
		if (this.isBaby()) {
			return height / 2;
		}
		return height;
	}
	@Override
	public MobType getMobType() {
		return MobType.ARTHROPOD;
	}
	@Override
	protected float getSoundVolume() {
		return 0.1f;
	}
	@Override
	protected int calculateFallDamage(float f, float f2) {
		return super.calculateFallDamage(f, f2) - 10;
	}
	@Override
	public boolean isFood(ItemStack itemStack) {
		return FOOD_ITEMS.test(itemStack);
	}
	@Override
	protected SoundEvent getHurtSound(DamageSource damageSource) {
		return SoundEvents.SPIDER_HURT;
	}
	@Override
	public int getMaxHeadYRot() {
		return 15;
	}
	@Override
	public int getMaxHeadXRot() {
		return 15;
	}
	@Override
	protected SoundEvent getDeathSound() {
		return SoundEvents.SPIDER_DEATH;
	}
	@Override
	public boolean onClimbable() {
		return this.isClimbing();
	}
	@Override
	public void makeStuckInBlock(BlockState blockState, Vec3 vec3) {
		if (!blockState.is(Blocks.COBWEB)) {
			super.makeStuckInBlock(blockState, vec3);
		} else {
			this.resetFallDistance();
		}
	}
	public boolean isClimbing() {
		return (this.entityData.get(DATA_FLAGS_ID) & 1) != 0;
	}
	public void setClimbing(boolean bl) {
		byte by = this.entityData.get(DATA_FLAGS_ID);
		by = bl ? (byte)(by | 1) : (byte)(by & 0xFFFFFFFE);
		this.entityData.set(DATA_FLAGS_ID, by);
	}
	@Override
	public ItemStack getCatchItemStack() {
		return new ItemStack(JerotesVillageItems.CAPTURED_DOWNY_SPIDER.get());
	}

	@Override
	public SoundEvent getPickupSound() {
		return SoundEvents.SPIDER_AMBIENT;
	}

	@Override
	public boolean requiresCustomPersistence() {
		return super.requiresCustomPersistence() || this.fromCatch();
	}
	@Override
	public boolean removeWhenFarAway(double d) {
		return !this.fromCatch() && !this.hasCustomName() && super.removeWhenFarAway(d);
	}

	@Override
	public boolean fromCatch() {
		return this.entityData.get(FROM_CATCH);
	}
	@Override
	public void setFromCatch(boolean bl) {
		this.entityData.set(FROM_CATCH, bl);
	}
	public boolean isColorA() {
		return this.getEntityData().get(DATA_COLOR_A_ID);
	}
	public boolean isColorB() {
		return this.getEntityData().get(DATA_COLOR_B_ID);
	}
	public void setColorA(boolean bl) {
		this.getEntityData().set(DATA_COLOR_A_ID, bl);
	}
	public void setColorB(boolean bl) {
		this.getEntityData().set(DATA_COLOR_B_ID, bl);
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
		if (Objects.equals(animation, "attack")){
			return 1;
		}
		else {
			return 0;
		}
	}
	public List<AnimationState> getAllAnimations(){
		List<AnimationState> list = new ArrayList<>();
		list.add(this.attackAnimationState);
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
	@Override
	public void addAdditionalSaveData(CompoundTag compoundTag) {
		super.addAdditionalSaveData(compoundTag);
		compoundTag.putInt("AnimTick", this.getAnimTick());
		compoundTag.putBoolean("FromCatch", this.fromCatch());
		compoundTag.putBoolean("IsColorA", this.isColorA());
		compoundTag.putBoolean("IsColorB", this.isColorB());
		compoundTag.putInt("WebTime", this.webTime);
	}
	@Override
	public void readAdditionalSaveData(CompoundTag compoundTag) {
		super.readAdditionalSaveData(compoundTag);
		this.setAnimTick(compoundTag.getInt("AnimTick"));
		this.setFromCatch(compoundTag.getBoolean("FromCatch"));
		if (compoundTag.contains("IsColorA")) {
			this.setColorA(compoundTag.getBoolean("IsColorA"));
		}
		if (compoundTag.contains("IsColorB")) {
			this.setColorB(compoundTag.getBoolean("IsColorB"));
		}
		if (compoundTag.contains("WebTime")) {
			this.webTime = compoundTag.getInt("WebTime");
		}
	}
	@Override
	protected void defineSynchedData() {
		super.defineSynchedData();
		this.getEntityData().define(ANIM_STATE, 0);
		this.getEntityData().define(ANIM_TICK, 0);
		this.getEntityData().define(FROM_CATCH, false);
		this.getEntityData().define(DATA_FLAGS_ID, (byte)0);
		this.getEntityData().define(DATA_COLOR_A_ID, false);
		this.getEntityData().define(DATA_COLOR_B_ID, false);
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
						this.attackAnimationState.startIfStopped(this.tickCount);
						this.stopMostAnimation(this.attackAnimationState);
						break;
				}
			}
		}
		super.onSyncedDataUpdated(entityDataAccessor);
	}
	@Override
	public void saveToCatchTag(ItemStack itemStack) {
		CatchEntity.saveDefaultDataToCatchTag(this, itemStack);
		CompoundTag compoundTag = itemStack.getOrCreateTag();
		this.addAdditionalSaveData(compoundTag);
		itemStack.save(compoundTag);
	}
	@Override
	public void loadFromCatchTag(CompoundTag compoundTag) {
		CatchEntity.loadDefaultDataFromCatchTag(this, compoundTag);
		this.readAdditionalSaveData(compoundTag);
	}
	
	@Override
	public InteractionResult mobInteract(Player player, InteractionHand interactionHand) {
		return CatchEntity.catchMobPickup(player, interactionHand, this).orElse(super.mobInteract(player, interactionHand));
	}

	@Override
	public boolean doHurtTarget(Entity entity) {
		if (!this.level().isClientSide()) {
			this.setAnimTick(7);
			this.setAnimationState("attack");
		}
		boolean bl = super.doHurtTarget(entity);
		if (bl) {
			if (entity instanceof LivingEntity livingEntity) {
				if (!livingEntity.level().isClientSide) {
					livingEntity.addEffect(new MobEffectInstance(MobEffects.POISON, 20, 0), this);
					livingEntity.addEffect(new MobEffectInstance(MobEffects.WEAKNESS, 20, 0), this);
				}
			}
		}
		return bl;
	}

	@Nullable
	@Override
	public DownySpiderEntity getBreedOffspring(ServerLevel serverLevel, AgeableMob ageableMob) {
		DownySpiderEntity downySpider = JerotesVillageEntityType.DOWNY_SPIDER.get().create(serverLevel);
		if (downySpider != null && ageableMob instanceof DownySpiderEntity downySpider2) {
			if (this.random.nextBoolean()) {
				downySpider.setColorA(this.isColorA());
				downySpider.setColorB(this.isColorB());
			}
			else {
				downySpider.setColorA(downySpider2.isColorA());
				downySpider.setColorB(downySpider2.isColorB());
			}
		}
		else if (downySpider != null) {
			downySpider.setColorA(this.isColorA());
			downySpider.setColorB(this.isColorB());
		}
		return downySpider;
	}

	@Override
	public void spawnChildFromBreeding(ServerLevel serverLevel, Animal animal) {
		for (int i = 0; i < Main.randomReach(this.getRandom(), 1, 4); ++i) {
			AgeableMob ageableMob = this.getBreedOffspring(serverLevel, animal);
			if (ageableMob == null) {
				return;
			}
			ageableMob.setBaby(true);
			ageableMob.moveTo(this.getX(), this.getY(), this.getZ(), 0.0f, 0.0f);
			this.finalizeSpawnChildFromBreeding(serverLevel, animal, ageableMob);
			serverLevel.addFreshEntityWithPassengers(ageableMob);
		}
	}

	@Override
	public void tick() {
		super.tick();
		if (!this.level().isClientSide) {
			this.setClimbing(this.horizontalCollision);
		}
	}

	@Override
	public void aiStep() {
		super.aiStep();
		//停止战斗
		if (this.getTarget() != null && (!this.getTarget().isAlive() || this.getTarget() instanceof Player player && (player.isCreative() || player.isSpectator()))){
			this.setTarget(null);
		}
		//
		if (this.isClimbing()) {
			this.climbAnimationState.start(this.tickCount);
		}
		else {
			this.climbAnimationState.stop();
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

		if (!this.level().isClientSide && this.isAlive() && --this.webTime <= 0) {
			this.playSound(SoundEvents.SPIDER_AMBIENT, 1.0f, (this.random.nextFloat() - this.random.nextFloat()) * 0.2f + 1.0f);
			if (this.random.nextFloat() > 0.99) {
				this.spawnAtLocation(JerotesItems.HIGH_STRENGTH_STRING.get());
			}
			else if (this.random.nextFloat() > 0.90) {
				this.spawnAtLocation(Items.COBWEB);
			}
			else {
				this.spawnAtLocation(Items.STRING);
			}
			this.gameEvent(GameEvent.ENTITY_PLACE);
			this.webTime = this.random.nextInt(6000) + 6000;
		}
	}

	@Override
	public boolean canBeAffected(MobEffectInstance mobEffectInstance) {
		if (mobEffectInstance.getEffect() == MobEffects.POISON) {
			return false;
		}
		if (mobEffectInstance.getEffect() == JerotesVillageMobEffects.FISHING_NET_CONFINEMET.get()) {
			return false;
		}
		return super.canBeAffected(mobEffectInstance);
	}
	
	@Nullable
	@Override
	public SpawnGroupData finalizeSpawn(ServerLevelAccessor serverLevelAccessor, DifficultyInstance difficultyInstance, MobSpawnType mobSpawnType, @Nullable SpawnGroupData spawnGroupData, @Nullable CompoundTag compoundTag) {
		RandomSource randomSourceSkin = serverLevelAccessor.getRandom();
		if (mobSpawnType != MobSpawnType.CONVERSION && mobSpawnType != MobSpawnType.BREEDING) {
			if (randomSourceSkin.nextFloat() < 0.25f) {
				this.setColorA(true);
			}
			if (randomSourceSkin.nextFloat() < 0.3f) {
				this.setColorB(true);
			}
		}
		return super.finalizeSpawn(serverLevelAccessor, difficultyInstance, mobSpawnType, spawnGroupData, compoundTag);
	}
}

