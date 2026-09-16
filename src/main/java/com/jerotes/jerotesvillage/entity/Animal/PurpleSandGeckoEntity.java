package com.jerotes.jerotesvillage.entity.Animal;

import com.jerotes.jerotes.entity.Interface.JerotesEntity;
import com.jerotes.jerotes.goal.JerotesBreedGoal;
import com.jerotes.jerotes.goal.JerotesFollowParentGoal;
import com.jerotes.jerotes.util.AttackFind;
import com.jerotes.jerotes.util.EntityAndItemFind;
import com.jerotes.jerotesvillage.entity.Other.Other.PurpleSandGeckoTailEntity;
import com.jerotes.jerotesvillage.init.JerotesVillageEntityType;
import com.jerotes.jerotesvillage.init.JerotesVillageItems;
import com.jerotes.jerotesvillage.init.JerotesVillageSoundEvents;
import net.minecraft.core.BlockPos;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.syncher.EntityDataAccessor;
import net.minecraft.network.syncher.EntityDataSerializers;
import net.minecraft.network.syncher.SynchedEntityData;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.*;
import net.minecraft.world.entity.ai.attributes.AttributeSupplier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.ai.goal.*;
import net.minecraft.world.entity.ai.navigation.PathNavigation;
import net.minecraft.world.entity.ai.navigation.WallClimberNavigation;
import net.minecraft.world.entity.animal.Animal;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.entity.projectile.Projectile;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.level.Level;
import net.minecraft.world.scores.PlayerTeam;

import javax.annotation.Nullable;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class PurpleSandGeckoEntity extends Animal implements JerotesEntity {
	public AnimationState wallAnimationState = new AnimationState();
	public AnimationState hurtAnimationState = new AnimationState();
	private static final EntityDataAccessor<Integer> ANIM_STATE = SynchedEntityData.defineId(PurpleSandGeckoEntity.class, EntityDataSerializers.INT);
	private static final EntityDataAccessor<Integer> ANIM_TICK = SynchedEntityData.defineId(PurpleSandGeckoEntity.class, EntityDataSerializers.INT);
	private static final EntityDataAccessor<Integer> TAIL_TICK = SynchedEntityData.defineId(PurpleSandGeckoEntity.class, EntityDataSerializers.INT);
	private static final EntityDataAccessor<Byte> DATA_FLAGS_ID = SynchedEntityData.defineId(PurpleSandGeckoEntity.class, EntityDataSerializers.BYTE);
	private static final EntityDataAccessor<Boolean> NO_TAIL = SynchedEntityData.defineId(PurpleSandGeckoEntity.class, EntityDataSerializers.BOOLEAN);
	private static final Ingredient FOOD_ITEMS = Ingredient.of(Items.SPIDER_EYE, Items.FERMENTED_SPIDER_EYE);
	private static final Ingredient LUCK_ITEMS = Ingredient.of(Items.SWEET_BERRIES, Items.GLOW_BERRIES, JerotesVillageItems.SECOND_ROUND_NIGRUM.get(), JerotesVillageItems.COOKED_SECOND_ROUND_NIGRUM.get());

	public PurpleSandGeckoEntity(EntityType<? extends PurpleSandGeckoEntity> type, Level world) {
		super(type, world);
	}

	@Override
	protected void registerGoals() {
		this.goalSelector.addGoal(0, new FloatGoal(this));
		this.goalSelector.addGoal(1, new PanicGoal(this, 1.5));
		this.goalSelector.addGoal(2, new JerotesBreedGoal(this, 1.0));
		this.goalSelector.addGoal(3, new TemptGoal(this, 1.1, FOOD_ITEMS, false));
		this.goalSelector.addGoal(4, new JerotesFollowParentGoal(this, 1.1));
		this.goalSelector.addGoal(5, new WaterAvoidingRandomStrollGoal(this, 1.0));
		this.goalSelector.addGoal(6, new LookAtPlayerGoal(this, Player.class, 4.0f));
		this.goalSelector.addGoal(7, new RandomLookAroundGoal(this));
	}

	@Override
	protected PathNavigation createNavigation(Level level) {
		return new WallClimberNavigation(this, level);
	}

	public boolean isNoTail() {
		return this.getEntityData().get(NO_TAIL);
	}
	public void setNoTail(boolean bl) {
		this.getEntityData().set(NO_TAIL, bl);
	}
	public void setTailTick(int n){
		this.getEntityData().set(TAIL_TICK, n);
	}
	public int getTailTick(){
		return this.getEntityData().get(TAIL_TICK);
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
		if (Objects.equals(animation, "hurt")){
			return 1;
		} else {
			return 0;
		}
	}
	public List<AnimationState> getAllAnimations(){
		List<AnimationState> list = new ArrayList<>();
		list.add(this.hurtAnimationState);
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
		compoundTag.putBoolean("IsNoTail", this.isNoTail());
		compoundTag.putInt("AnimTick", this.getAnimTick());
		compoundTag.putInt("TailTick", this.getTailTick());
	}
	@Override
	public void readAdditionalSaveData(CompoundTag compoundTag) {
		super.readAdditionalSaveData(compoundTag);
		if (compoundTag.contains("IsNoTail")) {
			this.setNoTail(compoundTag.getBoolean("IsNoTail"));
		}
		this.setAnimTick(compoundTag.getInt("AnimTick"));
		this.setTailTick(compoundTag.getInt("TailTick"));
	}
	@Override
	protected void defineSynchedData() {
		super.defineSynchedData();
		this.getEntityData().define(ANIM_STATE, 0);
		this.getEntityData().define(ANIM_TICK, 0);
		this.getEntityData().define(DATA_FLAGS_ID, (byte)0);
		this.getEntityData().define(NO_TAIL, false);
		this.getEntityData().define(TAIL_TICK, 0);
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
						this.hurtAnimationState.startIfStopped(this.tickCount);
						this.stopMostAnimation(this.hurtAnimationState);
						break;
				}
			}
		}
		super.onSyncedDataUpdated(entityDataAccessor);
	}

	@Override
	public void tick() {
		super.tick();
		if (!this.level().isClientSide) {
			this.setClimbing(this.horizontalCollision);
		}
		if (this.isNoTail()) {
			if (!this.level().isClientSide()) {
				this.setTailTick(Math.min(72000, this.getTailTick() + 1));
			}
			if (this.getTailTick() >= 72000) {
				if (!this.level().isClientSide()) {
					this.setNoTail(false);
					this.setTailTick(0);
				}
				for (int i = 0; i < 7; ++i) {
					double d = this.random.nextGaussian() * 0.02;
					double d2 = this.random.nextGaussian() * 0.02;
					double d3 = this.random.nextGaussian() * 0.02;
					this.level().addParticle(ParticleTypes.TOTEM_OF_UNDYING, this.getRandomX(1.0), this.getRandomY() + 0.5, this.getRandomZ(1.0), d, d2, d3);
				}
			}
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
			this.wallAnimationState.start(this.tickCount);
		}
		else {
			this.wallAnimationState.stop();
		}
		if (this.random.nextInt(900) == 1 && this.deathTime == 0) {
			this.heal(2.0f);
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
	}

	@Override
	protected int calculateFallDamage(float f, float f2) {
		return super.calculateFallDamage(f, f2) - 12;
	}

	@Override
	public boolean hurt(DamageSource damageSource, float amount) {
		if (isInvulnerableTo(damageSource)) {
			return super.hurt(damageSource, amount);
		}
		if (!this.level().isClientSide) {
			if (damageSource.getEntity() != null && damageSource.getEntity() instanceof LivingEntity livingEntity) {
				livingEntity.removeEffect(MobEffects.LUCK);
				if (!livingEntity.level().isClientSide) {
					livingEntity.addEffect(new MobEffectInstance(MobEffects.UNLUCK, 6000, 0), this);
				}
			}
			if (damageSource.getEntity() != null && damageSource.getEntity() instanceof Projectile projectile && projectile.getOwner() != null && projectile.getOwner() instanceof LivingEntity livingEntity) {
				livingEntity.removeEffect(MobEffects.LUCK);
				if (!livingEntity.level().isClientSide) {
					livingEntity.addEffect(new MobEffectInstance(MobEffects.UNLUCK, 6000, 0), this);
				}
			}
		}
		if (AttackFind.isMeleeDamage(damageSource) ) {
			if (!this.isNoTail()) {
				if (!this.level().isClientSide()) {
					this.setAnimTick(20);
					this.setAnimationState("hurt");
				}
				if (this.random.nextFloat() > 0.3 && this.isAlive()) {
					if (!this.level().isClientSide()) {
						this.setNoTail(true);
					}
					if (this.level() instanceof ServerLevel serverLevel) {
					PurpleSandGeckoTailEntity tail = JerotesVillageEntityType.PURPLE_SAND_GECKO_TAIL.get().spawn(serverLevel, BlockPos.containing(this.getX(), this.getY(), this.getZ()), MobSpawnType.MOB_SUMMONED);
					PlayerTeam teams = (PlayerTeam) this.getTeam();
					if (tail != null) {
						if (teams != null) {
							serverLevel.getScoreboard().addPlayerToTeam(tail.getStringUUID(), teams);
						}
						if (this.isBaby()) {
							tail.setBaby(true);
						}
						List<LivingEntity> list = this.level().getEntitiesOfClass(LivingEntity.class, this.getBoundingBox().inflate(1.5, 1.3, 1.5));
						for (LivingEntity entity : list) {
							if (entity == null) continue;
							if (entity == this || this.distanceTo(entity) > 32.0)
								continue;
							if (entity instanceof Mob mob && mob.getTarget() == this) {
								mob.setTarget(tail);
							}
							if (entity.getLastHurtMob() == this) {
								entity.setLastHurtMob(tail);
							}
						}
					}
					}
					if (!this.level().isClientSide) {
						this.addEffect(new MobEffectInstance(MobEffects.MOVEMENT_SPEED, 1200, 0), this);
					}
					this.setAnimationState(0);
					if (!this.level().isClientSide()) {
						this.setTailTick(0);
					}
				}
			}
		}
		return super.hurt(damageSource, amount);
	}

	@Override
	public boolean onClimbable() {
		return this.isClimbing();
	}

	public boolean isClimbing() {
		return (this.entityData.get(DATA_FLAGS_ID) & 1) != 0;
	}

	public void setClimbing(boolean bl) {
		byte by = this.entityData.get(DATA_FLAGS_ID);
		by = bl ? (byte)(by | 1) : (byte)(by & 0xFFFFFFFE);
		this.entityData.set(DATA_FLAGS_ID, by);
	}

	public static AttributeSupplier.Builder createAttributes() {
		AttributeSupplier.Builder builder = Mob.createMobAttributes();
		builder = builder.add(Attributes.MOVEMENT_SPEED, 0.35);
		builder = builder.add(Attributes.MAX_HEALTH, 6);
		builder = builder.add(Attributes.ATTACK_DAMAGE, 3);
		builder = builder.add(Attributes.FOLLOW_RANGE, 16);
		return builder;
	}

	@Override
	protected SoundEvent getAmbientSound() {
		return JerotesVillageSoundEvents.PURPLE_SAND_GECKO_AMBIENT;
	}

	@Override
	protected SoundEvent getHurtSound(DamageSource damageSource) {
		return JerotesVillageSoundEvents.PURPLE_SAND_GECKO_HURT;
	}

	@Override
	protected SoundEvent getDeathSound() {
		return JerotesVillageSoundEvents.PURPLE_SAND_GECKO_DEATH;
	}


	@Override
	protected float getSoundVolume() {
		return 0.4f;
	}

	@Override
	public boolean isFood(ItemStack itemStack) {
		return FOOD_ITEMS.test(itemStack);
	}

	public boolean isLuck(ItemStack itemStack) {
		return LUCK_ITEMS.test(itemStack);
	}

	@Nullable
	@Override
	public PurpleSandGeckoEntity getBreedOffspring(ServerLevel serverLevel, AgeableMob ageableMob) {
		return JerotesVillageEntityType.PURPLE_SAND_GECKO.get().create(serverLevel);
	}

	@Override
	public InteractionResult mobInteract(Player player, InteractionHand interactionHand) {
		ItemStack itemStack = player.getItemInHand(interactionHand);
		if (this.isLuck(itemStack)) {
			player.playSound(JerotesVillageSoundEvents.PURPLE_SAND_GECKO_AMBIENT, 2.0f, 1.0f);
			if (!player.level().isClientSide) {
				player.addEffect(new MobEffectInstance(MobEffects.LUCK, 6000, 0), this);
			}
			for (int i = 0; i < 7; ++i) {
				double d = this.random.nextGaussian() * 0.02;
				double d2 = this.random.nextGaussian() * 0.02;
				double d3 = this.random.nextGaussian() * 0.02;
				this.level().addParticle(ParticleTypes.GLOW, this.getRandomX(1.0), this.getRandomY() + 0.5, this.getRandomZ(1.0), d, d2, d3);
			}
			if (this.isNoTail()) {
				if (!this.level().isClientSide()) {
					this.setTailTick(this.getTailTick() + 7200);
				}
			}
			if (!player.getAbilities().instabuild) {
				itemStack.shrink(1);
			}
			return InteractionResult.sidedSuccess(this.level().isClientSide);
		}
		return super.mobInteract(player, interactionHand);
	}

	@Override
	protected float getStandingEyeHeight(Pose pose, EntityDimensions entityDimensions) {
		float height = 0.3f;
		if (this.isBaby()) {
			return height / 2;
		}
		return height;
	}
}




