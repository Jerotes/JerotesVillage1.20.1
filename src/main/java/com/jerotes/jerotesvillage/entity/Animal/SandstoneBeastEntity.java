package com.jerotes.jerotesvillage.entity.Animal;

import com.jerotes.jerotes.entity.Interface.FactionEntity;
import com.jerotes.jerotes.entity.Interface.JerotesEntity;
import com.jerotes.jerotes.goal.JerotesAnimalMeleeAttackGoal;
import com.jerotes.jerotes.goal.JerotesAnimalPanicGoal;
import com.jerotes.jerotes.goal.JerotesBreedGoal;
import com.jerotes.jerotes.goal.JerotesFollowParentGoal;
import com.jerotes.jerotes.util.AttackFind;
import com.jerotes.jerotes.util.EntityAndItemFind;
import com.jerotes.jerotes.util.Main;
import com.jerotes.jerotesvillage.event.AdvancementEvent;
import com.jerotes.jerotesvillage.init.JerotesVillageEntityType;
import com.jerotes.jerotesvillage.init.JerotesVillageItems;
import com.jerotes.jerotesvillage.init.JerotesVillageSoundEvents;
import net.minecraft.core.BlockPos;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.syncher.EntityDataAccessor;
import net.minecraft.network.syncher.EntityDataSerializers;
import net.minecraft.network.syncher.SynchedEntityData;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.tags.DamageTypeTags;
import net.minecraft.tags.ItemTags;
import net.minecraft.util.TimeUtil;
import net.minecraft.util.valueproviders.UniformInt;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.damagesource.DamageTypes;
import net.minecraft.world.entity.*;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import net.minecraft.world.entity.ai.attributes.AttributeSupplier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.ai.goal.*;
import net.minecraft.world.entity.ai.goal.target.HurtByTargetGoal;
import net.minecraft.world.entity.ai.goal.target.NearestAttackableTargetGoal;
import net.minecraft.world.entity.ai.goal.target.ResetUniversalAngerTargetGoal;
import net.minecraft.world.entity.animal.Animal;
import net.minecraft.world.entity.item.ItemEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.PickaxeItem;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.item.enchantment.Enchantments;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.storage.loot.LootParams;
import net.minecraft.world.level.storage.loot.parameters.LootContextParamSets;
import net.minecraft.world.level.storage.loot.parameters.LootContextParams;
import net.minecraft.world.phys.Vec3;

import javax.annotation.Nullable;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.UUID;

public class SandstoneBeastEntity extends Animal implements JerotesEntity, NeutralMob {
	public AnimationState idleAnimationState = new AnimationState();
	public AnimationState attackAnimationState = new AnimationState();
	private static final Ingredient FOOD_ITEMS = Ingredient.of(Items.DIAMOND);
	private static final EntityDataAccessor<Boolean> STONE = SynchedEntityData.defineId(SandstoneBeastEntity.class, EntityDataSerializers.BOOLEAN);
	private static final EntityDataAccessor<Integer> ANIM_STATE = SynchedEntityData.defineId(SandstoneBeastEntity.class, EntityDataSerializers.INT);
	private static final EntityDataAccessor<Integer> ANIM_TICK = SynchedEntityData.defineId(SandstoneBeastEntity.class, EntityDataSerializers.INT);
	private static final UUID MOVEMENT_SPEED_MODIFIER_UUID = UUID.fromString("c2ae70b1-7c42-43c8-a279-7cae22727cac");

	public SandstoneBeastEntity(EntityType<? extends SandstoneBeastEntity> type, Level world) {
		super(type, world);
	}

	@Override
	protected void registerGoals() {
		this.goalSelector.addGoal(0, new SandstoneBeastFloatGoal());
		this.goalSelector.addGoal(1, new JerotesAnimalMeleeAttackGoal(this, 1.4, true));
		this.goalSelector.addGoal(1, new JerotesAnimalPanicGoal(this, 1.4));
		this.goalSelector.addGoal(2, new JerotesBreedGoal(this, 1.0));
		this.goalSelector.addGoal(3, new TemptGoal(this, 1.1, FOOD_ITEMS, false));
		this.goalSelector.addGoal(4, new JerotesFollowParentGoal(this, 1.1));
		this.goalSelector.addGoal(5, new WaterAvoidingRandomStrollGoal(this, 1.0));
		this.goalSelector.addGoal(6, new LookAtPlayerGoal(this, Player.class, 6.0f));
		this.goalSelector.addGoal(7, new RandomLookAroundGoal(this));
		this.targetSelector.addGoal(1, new HurtByTargetGoal(this, new Class[0]));
		this.targetSelector.addGoal(3, new NearestAttackableTargetGoal<Player>(this, Player.class, 10, true, false, this::isAngryAt));
		this.targetSelector.addGoal(4, new ResetUniversalAngerTargetGoal<SandstoneBeastEntity>(this, false));
	}
	class SandstoneBeastFloatGoal extends FloatGoal {
		public SandstoneBeastFloatGoal() {
			super(SandstoneBeastEntity.this);
		}
		public boolean canUse() {
			return super.canUse() && !SandstoneBeastEntity.this.isStone();
		}
	}
	public boolean OwnerCanOrderAttack() {
		return !this.isBaby();
	}

	public static AttributeSupplier.Builder createAttributes() {
		AttributeSupplier.Builder builder = Mob.createMobAttributes();
		builder = builder.add(Attributes.MOVEMENT_SPEED, 0.22);
		builder = builder.add(Attributes.MAX_HEALTH, 40);
		builder = builder.add(Attributes.ARMOR, 4);
		builder = builder.add(Attributes.ATTACK_KNOCKBACK, 0.8);
		builder = builder.add(Attributes.ATTACK_DAMAGE, 6);
		builder = builder.add(Attributes.FOLLOW_RANGE, 20);
		builder = builder.add(Attributes.KNOCKBACK_RESISTANCE, 0.85);
		return builder;
	}

	@Override
	protected SoundEvent getAmbientSound() {
		return JerotesVillageSoundEvents.SANDSTONE_BEAST_AMBIENT;
	}

	@Override
	public int getAmbientSoundInterval() {
		return 400;
	}

	@Override
	protected SoundEvent getHurtSound(DamageSource damageSource) {
		return JerotesVillageSoundEvents.SANDSTONE_BEAST_HURT;
	}

	@Override
	protected SoundEvent getDeathSound() {
		return JerotesVillageSoundEvents.SANDSTONE_BEAST_DEATH;
	}

	@Override
	protected void playStepSound(BlockPos blockPos, BlockState blockState) {
		this.playSound(JerotesVillageSoundEvents.SANDSTONE_BEAST_WALK, 0.15f, 1.0f);
	}

	@Override
	protected float getSoundVolume() {
		return 0.6f;
	}

	@Override
	public boolean isFood(ItemStack itemStack) {
		return FOOD_ITEMS.test(itemStack);
	}

	@Nullable
	@Override
	public SandstoneBeastEntity getBreedOffspring(ServerLevel serverLevel, AgeableMob ageableMob) {
		return JerotesVillageEntityType.SANDSTONE_BEAST.get().create(serverLevel);
	}

	@Override
	public void setBaby(boolean bl) {
		this.setAge(bl ? -42000 : 0);
	}

	@Override
	public boolean doHurtTarget(Entity entity) {
		if (!this.level().isClientSide()) {
			this.setAnimTick(10);
			this.setAnimationState("attack");
		}
		boolean bl = super.doHurtTarget(entity);
		if (bl) {
			if (!this.isSilent()) {
				this.playSound(JerotesVillageSoundEvents.SANDSTONE_BEAST_ATTACK, 1.0f, 1.0f);
			}
		}
		return bl;
	}

	@Override
	public void aiStep() {
		super.aiStep();
		//清除动画
		if (!this.level().isClientSide()) {
			this.setAnimTick(Math.max(-1, this.getAnimTick() - 1));
		}
		if (this.getAnimTick() == 0) {
			if (!this.level().isClientSide()) {
				this.setAnimationState(0);
			}
		}
		if (this.isAlive()) {
			this.idleAnimationState.startIfStopped(this.tickCount);
		}
		if (!this.isStone()){
			if (this.stoneTick > 0) {
				this.stoneTick -= 1;
			}
			if (this.stoneTick <= 0 && !this.isBaby()) {
				if (!this.level().isClientSide) {
					if (this.level() instanceof ServerLevel projectileLevel) {
						for (int i = 0; i < 12; ++i) {
							projectileLevel.sendParticles(ParticleTypes.CRIT, this.getRandomX(1.0), this.getRandomY() + 0.5, this.getRandomZ(1.0), 1, 0.0, 0.0, 0.0, 0.0);
						}
					}
					this.level().playSound(null, this, SoundEvents.STONE_PLACE, this.getSoundSource(), 5.0f, 1.0f);
					this.setStone(true);
				}
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
	}

	@Override
	public boolean isPushedByFluid() {
		return false;
	}
	@Override
	public boolean isPushable() {
		return false;
	}
	@Override
	public boolean canBeCollidedWith() {
		return this.isStone() && this.isAlive() || super.canBeCollidedWith();
	}

	private int stoneTick;
	public String stoneDropItem = "jerotesvillage:gameplay/jerotesvillage_other/sandstone_beast_stone_drop";
	public boolean isStone() {
		return this.getEntityData().get(STONE);
	}
	public void setStone(boolean bl) {
		this.getEntityData().set(STONE, bl);
		Objects.requireNonNull(this.getAttribute(Attributes.MOVEMENT_SPEED)).removeModifier(MOVEMENT_SPEED_MODIFIER_UUID);
		if (bl) {
			Objects.requireNonNull(this.getAttribute(Attributes.MOVEMENT_SPEED)).addTransientModifier(
					new AttributeModifier(MOVEMENT_SPEED_MODIFIER_UUID, "Armor movement speed", -0.06, AttributeModifier.Operation.ADDITION));
		}
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
		compoundTag.putBoolean("IsStone", this.isStone());
		compoundTag.putInt("StoneTick", this.stoneTick);
		compoundTag.putString("StoneDropItem", this.stoneDropItem);
		this.addPersistentAngerSaveData(compoundTag);
	}
	@Override
	public void readAdditionalSaveData(CompoundTag compoundTag) {
		super.readAdditionalSaveData(compoundTag);
		this.setAnimTick(compoundTag.getInt("AnimTick"));
		this.setStone(compoundTag.getBoolean("IsStone"));
		this.stoneTick = compoundTag.getInt("StoneTick");
		this.stoneDropItem = compoundTag.getString("StoneDropItem");
		this.readPersistentAngerSaveData(this.level(), compoundTag);
	}
	@Override
	protected void defineSynchedData() {
		super.defineSynchedData();
		this.getEntityData().define(ANIM_STATE, 0);
		this.getEntityData().define(ANIM_TICK, 0);
		this.getEntityData().define(STONE, false);
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
	public InteractionResult mobInteract(Player player, InteractionHand interactionHand) {
		ItemStack itemStack = player.getItemInHand(interactionHand);
		if (itemStack.is(ItemTags.PICKAXES)) {
			if (this.isStone() && !this.isBaby()) {
				if (player instanceof ServerPlayer serverPlayer) {
					AdvancementEvent.AdvancementGive(serverPlayer, "jerotesvillage:the_walking_vein");
				}
				if (!this.level().isClientSide) {
					player.swing(interactionHand);
					this.level().playSound(null, this, SoundEvents.STONE_BREAK, this.getSoundSource(), 5.0f, 1.0f);
					int n = 1;
					if (itemStack.getItem() instanceof PickaxeItem pickaxeItem) {
					 n = (int) pickaxeItem.getTier().getSpeed() + pickaxeItem.getEnchantmentLevel(itemStack, Enchantments.BLOCK_EFFICIENCY);
					}
					if (Main.randomReach(this.getRandom(), 1, 12) < n) {
						this.drops();
						itemStack.hurtAndBreak(1, player, livingEntitys -> player.broadcastBreakEvent(interactionHand));
					}
					return InteractionResult.SUCCESS;
				}
			}
		}
		return super.mobInteract(player, interactionHand);
	}

	public void drops() {
		this.setStone(false);
		int reward = net.minecraftforge.event.ForgeEventFactory.getExperienceDrop(this, this.lastHurtByPlayer, this.getExperienceReward());
		ExperienceOrb.award((ServerLevel) this.level(), this.position(), reward);
		this.stoneTick = 72000;
		int n = 1 + Main.randomReach(this.getRandom(), 1, 5);
		if (this.level() instanceof ServerLevel) {
			for (int i = 0; i < n; ++i) {
				BlockPos blockPos = new BlockPos((int) this.getX(), (int) (this.getEyeY() + 0.2), (int) this.getZ());
				for (ItemStack drop : this.level().getServer().getLootData().getLootTable(new ResourceLocation(this.stoneDropItem)).getRandomItems(new LootParams.Builder((ServerLevel) this.level()).withParameter(LootContextParams.ORIGIN, Vec3.atCenterOf(blockPos)).withParameter(LootContextParams.BLOCK_STATE, this.level().getBlockState(blockPos)).withOptionalParameter(LootContextParams.BLOCK_ENTITY, this.level().getBlockEntity(blockPos)).create(LootContextParamSets.EMPTY))) {
					ItemEntity itemEntity = this.spawnAtLocation(drop, 1);
					if (itemEntity == null) continue;
					itemEntity.setDeltaMovement(itemEntity.getDeltaMovement().add((this.random.nextFloat() - this.random.nextFloat()) * 0.1f, this.random.nextFloat() * 0.2f, (this.random.nextFloat() - this.random.nextFloat()) * 0.1f));
				}
			}
		}
	}

	public ItemStack dropItemStack(float dropItem) {
		if (dropItem > 0.85) {
			return new ItemStack(JerotesVillageItems.PURPLE_SAND.get());
		}
		if (dropItem > 0.80) {
			return new ItemStack(Items.RAW_IRON);
		}
		if (dropItem > 0.75) {
			return new ItemStack(Items.RAW_COPPER);
		}
		if (dropItem > 0.745) {
			return new ItemStack(Items.RAW_GOLD);
		}
		if (dropItem > 0.65) {
			return new ItemStack(Items.COAL);
		}
		if (dropItem > 0.50) {
			return new ItemStack(Items.FLINT);
		}
		if (dropItem > 0.4975) {
			return new ItemStack(Items.DIAMOND);
		}
		if (dropItem > 0.495) {
			return new ItemStack(Items.EMERALD);
		}
		if (dropItem > 0.46) {
			return new ItemStack(Items.LAPIS_LAZULI);
		}
		if (dropItem > 0.43) {
			return new ItemStack(Items.REDSTONE);
		}
		if (dropItem > 0.40) {
			return new ItemStack(Items.QUARTZ);
		}
		if (dropItem > 0.38) {
			return new ItemStack(Items.AMETHYST_SHARD);
		}
		return new ItemStack(JerotesVillageItems.PURPLE_SANDSTONE.get());
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
		if (this.isStone()) {
			if (AttackFind.isMeleeDamage(damageSource) && damageSource.getEntity() instanceof LivingEntity livingEntity) {
				ItemStack handItem = livingEntity.getMainHandItem();
				if (!AttackFind.MeleeDamageFromMainHandNotOffHand(livingEntity))
					handItem = livingEntity.getOffhandItem();
				if (handItem.getItem() instanceof PickaxeItem)
					return super.hurt(damageSource, amount);
				if (damageSource.is(DamageTypeTags.IS_FALL))
					amount *= 0.25f;
				if (AttackFind.isFireDamage(damageSource))
					amount *= 0.25f;
				if (!damageSource.is(DamageTypeTags.BYPASSES_ARMOR))
					amount *= 0.5f;
			}
		}
		return super.hurt(damageSource, amount);
	}

	@Nullable
	private UUID persistentAngerTarget;
	private static final UniformInt PERSISTENT_ANGER_TIME = TimeUtil.rangeOfSeconds(20, 39);
	private int remainingPersistentAngerTime;

	@Override
	public int getRemainingPersistentAngerTime() {
		return this.remainingPersistentAngerTime;
	}

	@Override
	public void setRemainingPersistentAngerTime(int n) {
		this.remainingPersistentAngerTime = n;
	}


	@Override
	public void startPersistentAngerTimer() {
		this.setRemainingPersistentAngerTime(PERSISTENT_ANGER_TIME.sample(this.random));
	}

	@Nullable
	@Override
	public UUID getPersistentAngerTarget() {
		return this.persistentAngerTarget;
	}

	@Override
	public void setPersistentAngerTarget(@Nullable UUID uUID) {
		this.persistentAngerTarget = uUID;
	}
}




