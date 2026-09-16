package com.jerotes.jerotesvillage.entity.MagicSummoned.Botanize;

import com.jerotes.jerotes.config.MainConfig;
import com.jerotes.jerotes.entity.MagicSummoned.MagicSummonedEntity;
import com.jerotes.jerotes.entity.Mob.HumanEntity;
import com.jerotes.jerotes.goal.JerotesHelpAlliesGoal;
import com.jerotes.jerotes.goal.JerotesHelpSameFactionGoal;
import com.jerotes.jerotes.init.JerotesMobEffects;
import com.jerotes.jerotes.util.AttackFind;
import com.jerotes.jerotes.util.EntityFactionFind;
import com.jerotes.jerotesvillage.entity.Boss.Biome.CelestialCoilvineEntity;
import com.jerotes.jerotesvillage.event.AdvancementEvent;
import com.jerotes.jerotesvillage.goal.BotanizeFindAltarGoal;
import com.jerotes.jerotesvillage.init.JerotesVillageEntityType;
import com.jerotes.jerotesvillage.init.JerotesVillageItems;
import com.jerotes.jerotesvillage.init.JerotesVillageMobEffects;
import com.jerotes.jerotesvillage.init.JerotesVillageSoundEvents;
import net.minecraft.ChatFormatting;
import net.minecraft.core.BlockPos;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.chat.Component;
import net.minecraft.network.syncher.EntityDataAccessor;
import net.minecraft.network.syncher.EntityDataSerializers;
import net.minecraft.network.syncher.SynchedEntityData;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.tags.BlockTags;
import net.minecraft.tags.ItemTags;
import net.minecraft.util.Mth;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.damagesource.DamageTypes;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.*;
import net.minecraft.world.entity.ai.attributes.AttributeSupplier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.ai.goal.*;
import net.minecraft.world.entity.ai.goal.target.NearestAttackableTargetGoal;
import net.minecraft.world.entity.ai.navigation.GroundPathNavigation;
import net.minecraft.world.entity.ai.util.GoalUtils;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.entity.projectile.AbstractArrow;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.enchantment.Enchantments;
import net.minecraft.world.level.GameRules;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.*;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.gameevent.GameEvent;
import net.minecraft.world.level.storage.loot.LootParams;
import net.minecraft.world.level.storage.loot.parameters.LootContextParamSets;
import net.minecraft.world.level.storage.loot.parameters.LootContextParams;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.Vec3;
import net.minecraft.world.scores.PlayerTeam;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class BotanizeEntity extends MagicSummonedEntity {
	public AnimationState idleAnimationState = new AnimationState();
	public AnimationState tiltAnimationState = new AnimationState();
	public AnimationState growAnimationState = new AnimationState();
	public AnimationState giveAnimationState = new AnimationState();
	public AnimationState shootAnimationState = new AnimationState();
	private static final EntityDataAccessor<Integer> ANIM_STATE = SynchedEntityData.defineId(BotanizeEntity.class, EntityDataSerializers.INT);
	private static final EntityDataAccessor<Integer> ANIM_TICK = SynchedEntityData.defineId(BotanizeEntity.class, EntityDataSerializers.INT);
	private static final EntityDataAccessor<Integer> TILT_TICK = SynchedEntityData.defineId(BotanizeEntity.class, EntityDataSerializers.INT);
	private static final EntityDataAccessor<Integer> GROW_TICK = SynchedEntityData.defineId(BotanizeEntity.class, EntityDataSerializers.INT);
	private static final EntityDataAccessor<Integer> GIVE_TICK = SynchedEntityData.defineId(BotanizeEntity.class, EntityDataSerializers.INT);

	public BotanizeEntity(EntityType<? extends BotanizeEntity> type, Level world) {
		super(type, world);
		this.xpReward = 0;
		setPersistenceRequired();
		this.applyOpenDoorsAbility();
	}

	@Override
	public String getFirstFactionTypeName() {
		return "botanize_village";
	}

	public static AttributeSupplier.Builder createAttributes() {
		AttributeSupplier.Builder builder = Mob.createMobAttributes();
		builder = builder.add(Attributes.MOVEMENT_SPEED, 0.25);
		builder = builder.add(Attributes.MAX_HEALTH, 16);
		builder = builder.add(Attributes.ATTACK_DAMAGE, 2);
		builder = builder.add(Attributes.FOLLOW_RANGE, 48);
		return builder;
	}

	private void applyOpenDoorsAbility() {
		if (GoalUtils.hasGroundPathNavigation(this)) {
			((GroundPathNavigation)this.getNavigation()).setCanOpenDoors(true);
		}
	}

	@Override
	protected void registerGoals() {
		super.registerGoals();
		this.getNavigation().getNodeEvaluator().setCanOpenDoors(true);
		this.goalSelector.addGoal(0, new OpenDoorGoal(this, true));
		this.goalSelector.addGoal(0, new FloatGoal(this));;
		this.goalSelector.addGoal(1, new BotanizeFindAltarGoal(this));
		if (!(this instanceof CoilvineBotanizeEntity)) {
			this.goalSelector.addGoal(2, new PanicGoal(this, 1.4));
		}
		this.goalSelector.addGoal(3, new WaterAvoidingRandomStrollGoal(this, 1.0));
		this.goalSelector.addGoal(4, new LookAtPlayerGoal(this, Player.class, 6.0f));
		this.goalSelector.addGoal(4, new LookAtPlayerGoal(this, HumanEntity.class, 6.0f));
		this.goalSelector.addGoal(4, new LookAtPlayerGoal(this, CelestialCoilvineEntity.class, 6.0f));
		this.goalSelector.addGoal(5, new LookAtPlayerGoal(this, LivingEntity.class, 6.0f));
		this.goalSelector.addGoal(5, new RandomLookAroundGoal(this));
		this.targetSelector.addGoal(1, new JerotesHelpSameFactionGoal(this, LivingEntity.class, false, false, livingEntity -> livingEntity instanceof LivingEntity));
		this.targetSelector.addGoal(1, new NearestAttackableTargetGoal<>(this, LivingEntity.class, 5, false, false, livingEntity -> EntityFactionFind.isHateFaction(this, livingEntity)));
		this.targetSelector.addGoal(1, new JerotesHelpAlliesGoal(this, LivingEntity.class, false, false, livingEntity -> livingEntity instanceof LivingEntity));
	}

	@Override
	public boolean removeWhenFarAway(double distanceToClosestPlayer) {
		return false;
	}
	@Override
	public boolean isPushedByFluid() {
		return false;
	}

	public void setTiltTick(int n){
		this.getEntityData().set(TILT_TICK, n);
	}
	public int getTiltTick(){
		return this.getEntityData().get(TILT_TICK);
	}
	public void setGiveTick(int n){
		this.getEntityData().set(GIVE_TICK, n);
	}
	public int getGiveTick(){
		return this.getEntityData().get(GIVE_TICK);
	}
	public void setGrowTick(int n){
		this.getEntityData().set(GROW_TICK, n);
	}
	public int getGrowTick(){
		return this.getEntityData().get(GROW_TICK);
	}
	public String giftItem = "jerotesvillage:gameplay/jerotesvillage_other/botanize_gift";
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
		if (Objects.equals(animation, "tilt")){
			return 1;
		}
		else if (Objects.equals(animation, "grow")){
			return 2;
		}
		else if (Objects.equals(animation, "give")){
			return 3;
		}
		else if (Objects.equals(animation, "shoot")){
			return 4;
		}
		else {
			return 0;
		}
	}
	public List<AnimationState> getAllAnimations(){
		List<AnimationState> list = new ArrayList<>();
		list.add(this.tiltAnimationState);
		list.add(this.growAnimationState);
		list.add(this.giveAnimationState);
		list.add(this.shootAnimationState);
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
		compoundTag.putInt("TiltTick", this.getTiltTick());
		compoundTag.putInt("GrowTick", this.getGrowTick());
		compoundTag.putInt("GiveTick", this.getGiveTick());
		compoundTag.putString("GiftItem", this.giftItem);
	}
	@Override
	public void readAdditionalSaveData(CompoundTag compoundTag) {
		super.readAdditionalSaveData(compoundTag);
		this.setAnimTick(compoundTag.getInt("AnimTick"));
		this.setTiltTick(compoundTag.getInt("TiltTick"));
		this.setGrowTick(compoundTag.getInt("GrowTick"));
		this.setGiveTick(compoundTag.getInt("GiveTick"));
		this.giftItem = compoundTag.getString("GiftItem");
	}
	@Override
	protected void defineSynchedData() {
		super.defineSynchedData();
		this.getEntityData().define(ANIM_STATE, 0);
		this.getEntityData().define(ANIM_TICK, 0);
		this.getEntityData().define(TILT_TICK, 0);
		this.getEntityData().define(GROW_TICK, 0);
		this.getEntityData().define(GIVE_TICK, 0);
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
						this.tiltAnimationState.startIfStopped(this.tickCount);
						this.stopMostAnimation(this.tiltAnimationState);
						break;
					case 2:
						this.growAnimationState.startIfStopped(this.tickCount);
						this.stopMostAnimation(this.growAnimationState);
						break;
					case 3:
						this.giveAnimationState.startIfStopped(this.tickCount);
						this.stopMostAnimation(this.giveAnimationState);
						break;
					case 4:
						this.shootAnimationState.startIfStopped(this.tickCount);
						this.stopMostAnimation(this.shootAnimationState);
						break;
				}
			}
		}
		super.onSyncedDataUpdated(entityDataAccessor);
	}

	@Override
	protected InteractionResult mobInteract(Player player, InteractionHand interactionHand) {
		ItemStack itemStack = player.getItemInHand(interactionHand);
		if (!this.isTame()) {
			if (!this.isAggressive() && this.getTarget() == null && MainConfig.MobSayQuestionMark) {
				if (!this.level().isClientSide() && player instanceof ServerPlayer serverPlayer && interactionHand == InteractionHand.MAIN_HAND) {
					serverPlayer.sendSystemMessage(Component.translatable("talk.jerotesvillage.normal", this.getDisplayName()).withStyle(ChatFormatting.WHITE));
				}
			}
		}
		if (!this.isAggressive() && this.getTarget() == null && !(this instanceof CoilvineBotanizeEntity) && !player.isShiftKeyDown()) {
			if (this.getTiltTick() <= 0 && this.getGiveTick() <= 0) {
				if (!this.level().isClientSide()) {
					this.setTiltTick(60);
					this.setAnimTick(60);
					this.setAnimationState("tilt");
				}
				return InteractionResult.SUCCESS;
			}
			if (getTiltTick() > 0 && this.getGiveTick() <= 0 && !itemStack.isEmpty()) {
				if (itemStack.is(ItemTags.FLOWERS)) {
					if (player instanceof ServerPlayer serverPlayer) {
						AdvancementEvent.AdvancementGive(serverPlayer, "jerotesvillage:beautiful_world");
					}
				}
				if (!this.level().isClientSide()) {
					this.setTiltTick(0);
					this.setGiveTick(70);
					this.setAnimTick(70);
					this.setAnimationState("give");
				}
				this.swapItem(player, itemStack, interactionHand);
				return InteractionResult.SUCCESS;
			}
		}
		return super.mobInteract(player, interactionHand);
	}

	private boolean swapItem(Player player, ItemStack itemStack, InteractionHand interactionHand) {
		ItemStack itemStack2 = this.getItemBySlot(EquipmentSlot.MAINHAND);
		if (player.getAbilities().instabuild && itemStack2.isEmpty() && !itemStack.isEmpty()) {
			this.setItemSlot(EquipmentSlot.MAINHAND, itemStack.copyWithCount(1));
			return true;
		}
		if (!itemStack.isEmpty() && itemStack.getCount() > 1) {
			if (!itemStack2.isEmpty()) {
				return false;
			}
			this.setItemSlot(EquipmentSlot.MAINHAND, itemStack.split(1));
			return true;
		}
		this.setItemSlot(EquipmentSlot.MAINHAND, itemStack);
		player.setItemInHand(interactionHand, itemStack2);
		this.handDropChances[EquipmentSlot.MAINHAND.getIndex()] = 2.0f;
		return true;
	}

	@Override
	public void aiStep() {
		super.aiStep();
		if (this.random.nextInt(300) == 1 && this.isAlive()) {
			this.heal(6.0f);
		}
		//停止战斗
		if (this.getTarget() != null && (!this.getTarget().isAlive() || this.getTarget() instanceof Player player && (player.isCreative() || player.isSpectator()))) {
			this.setTarget(null);
		}
		//
		if (this.isAlive()) {
			this.idleAnimationState.startIfStopped(this.tickCount);
		}
		Vec3 vec3 = this.getDeltaMovement();
		if (!this.onGround() && vec3.y < 0.0) {
			this.setDeltaMovement(vec3.multiply(1.0, 0.6, 1.0));
		}
		if (this.level().isClientSide) {
			if (this.tickCount % 3 == 0) {
				this.level().addParticle(ParticleTypes.CHERRY_LEAVES, this.getRandomX(0.5), this.getRandomY(), this.getRandomZ(0.5), 0.0, 0.0, 0.0);
			}
		}

		if (!this.level().isClientSide()) {
			this.setTiltTick(Math.max(0, this.getTiltTick() - 1));
			this.setGiveTick(Math.max(0, this.getGiveTick() - 1));
			this.setGrowTick(Math.max(0, this.getGrowTick() - 1));
		}
		//给予物品
		if (this.getTiltTick() > 0 || this.getGiveTick() > 0) {
			this.setDeltaMovement(this.getDeltaMovement().multiply(0.05d, 1d, 0.05d));
			this.getNavigation().stop();
		}
		//丢出物品
		if (this.getGiveTick() == 5) {
			this.giveGift();
		}
		//催熟
		if (this.random.nextInt(600) == 1 && this.getGrowTick() <= 0 && this.isAlive()) {
			if (!this.level().isClientSide()) {
				if (this.level().getGameRules().getBoolean(GameRules.RULE_MOBGRIEFING)) {
					AABB aABB = this.getBoundingBox().inflate(1);
					for (BlockPos blockPos : BlockPos.betweenClosed(Mth.floor(aABB.minX), Mth.floor(aABB.minY), Mth.floor(aABB.minZ), Mth.floor(aABB.maxX), Mth.floor(aABB.maxY), Mth.floor(aABB.maxZ))) {
						BlockState blockState = this.level().getBlockState(blockPos);
						Block block = blockState.getBlock();
						BlockState blockState1 = null;
						if (blockState.is(BlockTags.BEE_GROWABLES)) {
							if (block instanceof CropBlock) {
								CropBlock cropblock = (CropBlock)block;
								if (!cropblock.isMaxAge(blockState)) {
									blockState1 = cropblock.getStateForAge(cropblock.getAge(blockState) + 1);
								}
							} else if (block instanceof StemBlock) {
								int k = blockState.getValue(StemBlock.AGE);
								if (k < 7) {
									blockState1 = blockState.setValue(StemBlock.AGE, Integer.valueOf(k + 1));
								}
							} else if (blockState.is(Blocks.SWEET_BERRY_BUSH)) {
								int j = blockState.getValue(SweetBerryBushBlock.AGE);
								if (j < 3) {
									blockState1 = blockState.setValue(SweetBerryBushBlock.AGE, Integer.valueOf(j + 1));
								}
							} else if (blockState.is(Blocks.CAVE_VINES) || blockState.is(Blocks.CAVE_VINES_PLANT)) {
								((BonemealableBlock)blockState.getBlock()).performBonemeal((ServerLevel) this.level(), this.random, blockPos, blockState);
							}
							if (blockState1 != null) {
								this.level().levelEvent(2005, blockPos, 0);
								this.level().setBlockAndUpdate(blockPos, blockState1);
								this.setGrowTick(120);
							}
						}
					}
				}
			}
		}
		if (this.getGrowTick() == 115) {
			this.setAnimTick(20);
			this.setAnimationState("grow");
		}
	}


	public void giveGift() {
		//物品
		if (this.level() instanceof ServerLevel) {
			ItemStack getItems = this.getItemBySlot(EquipmentSlot.MAINHAND);
			if (getItems.is(JerotesVillageItems.WONDERLAND_SAPLING.get())) {
				this.cloneFriend();
				return;
			}
			if (getItems.is(ItemTags.FLOWERS)) {
				BlockPos blockPos = new BlockPos((int) this.getX(), (int) (this.getEyeY()), (int) this.getZ());
				for (ItemStack drop : this.level().getServer().getLootData().getLootTable(new ResourceLocation(this.giftItem)).getRandomItems(new LootParams.Builder((ServerLevel) this.level()).withParameter(LootContextParams.ORIGIN, Vec3.atCenterOf(blockPos)).withParameter(LootContextParams.BLOCK_STATE, this.level().getBlockState(blockPos)).withOptionalParameter(LootContextParams.BLOCK_ENTITY, this.level().getBlockEntity(blockPos)).create(LootContextParamSets.EMPTY))) {
					getItems = drop;
				}
			}
			this.spawnAtLocation(getItems);
			this.setItemSlot(EquipmentSlot.MAINHAND, new ItemStack(Items.AIR));
			this.playSound(JerotesVillageSoundEvents.BOTANIZE_GIFT, 1.0f, 1.0f);
		}
	}

	public void cloneFriend() {
		if (this.level() instanceof ServerLevel serverLevel) {
			PlayerTeam teams = (PlayerTeam) this.getTeam();
			BotanizeEntity botanize;
			if (serverLevel.getBlockState(this.getOnPos().below()).isAir()) {
				botanize = JerotesVillageEntityType.BOTANIZE.get().spawn(serverLevel, BlockPos.containing(this.getX(), this.getY(), this.getZ()), MobSpawnType.MOB_SUMMONED);
			}
			else {
				botanize = JerotesVillageEntityType.BOTANIZE.get().spawn(serverLevel, this.getOnPos().above(), MobSpawnType.MOB_SUMMONED);
			}
			if (botanize != null) {
				if (teams != null) {
					serverLevel.getScoreboard().addPlayerToTeam(botanize.getStringUUID(), teams);
				}
				for (int i = 0; i < 30; ++i) {
					serverLevel.sendParticles(ParticleTypes.CHERRY_LEAVES, this.getRandomX(1), this.getRandomY(), this.getRandomX(1), 0, 0.0, 0.0, 0.0, 0.0);
				}
			}
			serverLevel.gameEvent(GameEvent.ENTITY_PLACE, new BlockPos((int) this.getX(), (int) this.getY(), (int) this.getZ()), GameEvent.Context.of(this));
			this.setItemSlot(EquipmentSlot.MAINHAND, new ItemStack(Items.AIR));
			this.playSound(JerotesVillageSoundEvents.BOTANIZE_GIFT, 1.0f, 1.0f);
		}
	}

	@Override
	public SoundEvent getAmbientSound() {
		return JerotesVillageSoundEvents.BOTANIZE_AMBIENT;
	}
	@Override
	public SoundEvent getHurtSound(DamageSource damageSource) {
		return JerotesVillageSoundEvents.BOTANIZE_HURT;
	}
	@Override
	public SoundEvent getDeathSound() {
		return JerotesVillageSoundEvents.BOTANIZE_DEATH;
	}

	@Override
	public boolean canBeAffected(MobEffectInstance mobEffectInstance) {
		if (mobEffectInstance.getEffect() == MobEffects.POISON) {
			return false;
		}
		if (mobEffectInstance.getEffect() == JerotesMobEffects.DEADLY_POISON.get()) {
			return false;
		}
		if (mobEffectInstance.getEffect() == JerotesVillageMobEffects.DAZZLING_LIGHT.get()) {
			return false;
		}
		return super.canBeAffected(mobEffectInstance);
	}

	@Override
	public boolean isInvulnerableTo(DamageSource damageSource) {
		if (damageSource.is(DamageTypes.CACTUS)
				|| damageSource.is(DamageTypes.SWEET_BERRY_BUSH)
		)
			return true;
		return super.isInvulnerableTo(damageSource);
	}
	@Override
	public boolean hurt(DamageSource damageSource, float amount) {
		if (isInvulnerableTo(damageSource)) {
			return super.hurt(damageSource, amount);
		}
		if (this.getOwner() instanceof CelestialCoilvineEntity celestialCoilvineEntity) {
			if (AttackFind.isLightningDamage(damageSource) || AttackFind.isColdDamage(damageSource) || AttackFind.isFireDamage(damageSource))
				amount *= 2f;
			if (damageSource.getDirectEntity() != null && damageSource.getDirectEntity() instanceof AbstractArrow arrow && arrow.isOnFire() ||
					AttackFind.isFireDamage(damageSource))
				amount *= 1.5f;
			else if (AttackFind.isMeleeDamage(damageSource) && damageSource.getEntity() instanceof LivingEntity livingEntity) {
				ItemStack handItem = livingEntity.getMainHandItem();
				if (!AttackFind.MeleeDamageFromMainHandNotOffHand(livingEntity))
					handItem = livingEntity.getOffhandItem();
				if (handItem.getEnchantmentLevel(Enchantments.FIRE_ASPECT) > 0) {
					amount *= 1.5f;
				}
			}
		}
		else {
			if (AttackFind.isLightningDamage(damageSource) || AttackFind.isColdDamage(damageSource) || AttackFind.isFireDamage(damageSource))
				amount *= 1.25f;
		}
		boolean bl = super.hurt(damageSource, amount);
		if (bl) {
			if (this.getGiveTick() > 5) {
				this.giveGift();
				this.setAnimTick(70);
			}
		}
		return bl;
	}
}

