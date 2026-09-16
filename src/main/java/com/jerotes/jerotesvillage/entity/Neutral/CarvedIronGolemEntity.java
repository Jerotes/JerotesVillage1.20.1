package com.jerotes.jerotesvillage.entity.Neutral;

import com.google.common.collect.Lists;
import com.jerotes.jerotes.entity.Interface.CarvedEntity;
import com.jerotes.jerotes.entity.Interface.FactionEntity;
import com.jerotes.jerotes.entity.Interface.JerotesEntity;
import com.jerotes.jerotes.entity.Mob.HumanEntity;
import com.jerotes.jerotes.goal.JerotesHelpAlliesGoal;
import com.jerotes.jerotes.goal.JerotesHelpCarvedGoal;
import com.jerotes.jerotes.goal.JerotesMeleeAttackGoal;
import com.jerotes.jerotes.init.JerotesDamageTypes;
import com.jerotes.jerotes.util.AttackFind;
import com.jerotes.jerotes.util.EntityFactionFind;
import com.jerotes.jerotes.util.Main;
import com.jerotes.jerotesvillage.config.OtherMainConfig;
import com.jerotes.jerotesvillage.entity.Animal.CarvedHoundEntity;
import com.jerotes.jerotesvillage.entity.Animal.CarvedLlamaEntity;
import com.jerotes.jerotesvillage.entity.Monster.IllagerFaction.DefectorEntity;
import com.jerotes.jerotesvillage.event.RelationshipEvent;
import com.jerotes.jerotesvillage.goal.CarvedIronGolemMeleeAttackGoal;
import com.jerotes.jerotesvillage.goal.HelpVillagerGoal;
import com.jerotes.jerotesvillage.item.CarvedFlag;
import net.minecraft.core.particles.BlockParticleOption;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.ListTag;
import net.minecraft.nbt.NbtUtils;
import net.minecraft.nbt.Tag;
import net.minecraft.network.syncher.EntityDataAccessor;
import net.minecraft.network.syncher.EntityDataSerializers;
import net.minecraft.network.syncher.SynchedEntityData;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.tags.DamageTypeTags;
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
import net.minecraft.world.entity.ai.goal.target.HurtByTargetGoal;
import net.minecraft.world.entity.ai.goal.target.NearestAttackableTargetGoal;
import net.minecraft.world.entity.ai.goal.target.ResetUniversalAngerTargetGoal;
import net.minecraft.world.entity.animal.IronGolem;
import net.minecraft.world.entity.monster.Creeper;
import net.minecraft.world.entity.monster.Enemy;
import net.minecraft.world.entity.monster.Zombie;
import net.minecraft.world.entity.npc.AbstractVillager;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.entity.raid.Raider;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.level.GameRules;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.Vec3;
import net.minecraftforge.common.ForgeMod;
import net.minecraftforge.fluids.FluidType;

import javax.annotation.Nullable;
import java.util.*;

public class CarvedIronGolemEntity extends IronGolem implements JerotesEntity, CarvedEntity, FactionEntity {
	private static final EntityDataAccessor<Integer> ANIM_STATE = SynchedEntityData.defineId(CarvedIronGolemEntity.class, EntityDataSerializers.INT);
	private static final EntityDataAccessor<Integer> ANIM_TICK = SynchedEntityData.defineId(CarvedIronGolemEntity.class, EntityDataSerializers.INT);
	private static final EntityDataAccessor<Integer> NETHERITE_TICK = SynchedEntityData.defineId(CarvedIronGolemEntity.class, EntityDataSerializers.INT);
	private static final EntityDataAccessor<Integer> DATA_TYPE_ID = SynchedEntityData.defineId(CarvedIronGolemEntity.class, EntityDataSerializers.INT);
	private static final EntityDataAccessor<Byte> DATA_FLAGS_ID = SynchedEntityData.defineId(CarvedIronGolemEntity.class, EntityDataSerializers.BYTE);
	private static final EntityDataAccessor<Optional<UUID>> DATA_TRUSTED_ID_0 = SynchedEntityData.defineId(CarvedIronGolemEntity.class, EntityDataSerializers.OPTIONAL_UUID);
	private static final EntityDataAccessor<Optional<UUID>> DATA_TRUSTED_ID_1 = SynchedEntityData.defineId(CarvedIronGolemEntity.class, EntityDataSerializers.OPTIONAL_UUID);
	public AnimationState attack1AnimationState = new AnimationState();
	public AnimationState attack2AnimationState = new AnimationState();
	public AnimationState attack3AnimationState = new AnimationState();
	public AnimationState idleAnimationState = new AnimationState();

	public CarvedIronGolemEntity(EntityType<? extends CarvedIronGolemEntity> entityType, Level level) {
		super(entityType, level);
		this.setMaxUpStep(1.0f);
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
		builder = builder.add(Attributes.MOVEMENT_SPEED, 0.27);
		builder = builder.add(Attributes.MAX_HEALTH, 120);
		builder = builder.add(Attributes.KNOCKBACK_RESISTANCE, 1.0);
		builder = builder.add(Attributes.ARMOR, 17);
		builder = builder.add(Attributes.ATTACK_DAMAGE, 17);
		builder = builder.add(Attributes.FOLLOW_RANGE, 48);
		return builder;
	}




	@Override
	protected void registerGoals() {
		this.goalSelector.addGoal(0, new CarvedIronGolemMeleeAttackGoal(this, 1.05, true));
		this.goalSelector.addGoal(1, new JerotesMeleeAttackGoal(this, 1.05, true));
		this.goalSelector.addGoal(2, new MoveTowardsTargetGoal(this, 0.9, 32.0f));
		this.goalSelector.addGoal(3, new MoveBackToVillageGoal(this, 0.6, false));
		this.goalSelector.addGoal(4, new GolemRandomStrollInVillageGoal(this, 0.6));
		this.goalSelector.addGoal(5, new OfferFlowerGoal(this));
		this.goalSelector.addGoal(6, new LookAtPlayerGoal(this, Player.class, 6.0f));
		this.goalSelector.addGoal(6, new LookAtPlayerGoal(this, HumanEntity.class, 6.0f));
		this.goalSelector.addGoal(6, new LookAtPlayerGoal(this, AbstractVillager.class, 6.0f));
		this.goalSelector.addGoal(6, new LookAtPlayerGoal(this, IronGolem.class, 6.0f));
		this.goalSelector.addGoal(6, new LookAtPlayerGoal(this, CarvedAllayEntity.class, 6.0f));
		this.goalSelector.addGoal(6, new LookAtPlayerGoal(this, CarvedHoundEntity.class, 6.0f));
		this.goalSelector.addGoal(9, new LookAtPlayerGoal(this, CarvedLlamaEntity.class, 6.0f));
		this.goalSelector.addGoal(7, new RandomLookAroundGoal(this));
		this.targetSelector.addGoal(1, new HurtByTargetGoal(this, CarvedEntity.class));
		this.targetSelector.addGoal(1, new JerotesHelpCarvedGoal(this));
		this.targetSelector.addGoal(1, new HelpVillagerGoal(this));
		this.targetSelector.addGoal(1, new JerotesHelpAlliesGoal(this, LivingEntity.class, false, false, livingEntity -> livingEntity instanceof LivingEntity));
		if (OtherMainConfig.CarvedObligation) {
			this.targetSelector.addGoal(2, new NearestAttackableTargetGoal<Mob>(this, Mob.class, 5, false, false, mob -> (mob instanceof Enemy || OtherMainConfig.CarvedGonnaActiveAttackList.contains(mob.getEncodeId())) && !OtherMainConfig.CarvedGonnaActiveAttackList.contains(mob.getEncodeId()) && !(mob instanceof Creeper && !OtherMainConfig.CarvedActiveAttackCreeper)));
		}
		else {
			this.targetSelector.addGoal(2, new NearestAttackableTargetGoal<Mob>(this, Mob.class, 5, false, false, mob -> OtherMainConfig.CarvedGonnaActiveAttackList.contains(mob.getEncodeId()) && !OtherMainConfig.CarvedGonnaActiveAttackList.contains(mob.getEncodeId()) && !(mob instanceof Creeper && !OtherMainConfig.CarvedActiveAttackCreeper)));
			this.targetSelector.addGoal(2, new NearestAttackableTargetGoal<Raider>(this, Raider.class, 5, false, false, (mob) -> !(mob instanceof NeutralMob) && !OtherMainConfig.CarvedNeverGonnaActiveAttack.contains(mob.getEncodeId())));
			this.targetSelector.addGoal(2, new NearestAttackableTargetGoal<Zombie>(this, Zombie.class, 5, false, false, (mob) -> !(mob instanceof NeutralMob) && !OtherMainConfig.CarvedNeverGonnaActiveAttack.contains(mob.getEncodeId())));
		}
		this.targetSelector.addGoal(2, new NearestAttackableTargetGoal<Player>(this, Player.class, 10, true, false, livingEntity -> livingEntity instanceof Player player && RelationshipEvent.LessCopperCarvedCompanyRelationship(player, -500) && !this.trusts(player.getUUID())));
		this.targetSelector.addGoal(3, new NearestAttackableTargetGoal<Player>(this, Player.class, 10, true, false, this::isAngryAt));
		this.targetSelector.addGoal(3, new ResetUniversalAngerTargetGoal<CarvedIronGolemEntity>(this, true));
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
		return aabb1.inflate(0.5d, 0.5d, 0.5d);
	}
	@Override
	public boolean isWithinMeleeAttackRange(LivingEntity livingEntity) {
		return this.getAttackBoundingBox().intersects(livingEntity.getBoundingBox());
	}

	public boolean isEmerald(ItemStack stack) {
		return Ingredient.of(new ItemStack(Items.EMERALD)).test(stack);
	}
	public Crackiness getCrackiness() {
		return Crackiness.byFraction(this.getHealth() / this.getMaxHealth());
	}
	@Override
	public boolean canDrownInFluidType(FluidType type) {
		if (type == ForgeMod.WATER_TYPE.get())
			return false;
		return super.canDrownInFluidType(type);
	}

	public int getNetheriteTick() {
		return this.getEntityData().get(NETHERITE_TICK);
	}
	public void setNetheriteTick(int n) {
		this.getEntityData().set(NETHERITE_TICK, n);
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
		if (Objects.equals(animation, "attack1")){
			return 1;
		}
		else if (Objects.equals(animation, "attack2")){
			return 2;
		}
		else if (Objects.equals(animation, "attack3")){
			return 3;
		}
		else {
			return 0;
		}
	}
	public List<AnimationState> getAllAnimations(){
		List<AnimationState> list = new ArrayList<>();
		list.add(this.attack1AnimationState);
		list.add(this.attack2AnimationState);
		list.add(this.attack3AnimationState);
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
	private int attackAnimationTick;
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
		compoundTag.putInt("NetheriteTick", this.getNetheriteTick());
		compoundTag.putInt("AnimTick", this.getAnimTick());
	}
	@Override
	public void readAdditionalSaveData(CompoundTag compoundTag) {
		super.readAdditionalSaveData(compoundTag);
		ListTag listTag = compoundTag.getList("Trusted", 11);
		for (Tag tag : listTag) {
			this.addTrustedUUID(NbtUtils.loadUUID(tag));
		}
		if (compoundTag.contains("NetheriteTick")) {
			this.setNetheriteTick(compoundTag.getInt("NetheriteTick"));
		}
		this.setAnimTick(compoundTag.getInt("AnimTick"));
	}
	@Override
	protected void defineSynchedData() {
		super.defineSynchedData();
		this.getEntityData().define(ANIM_STATE, 0);
		this.getEntityData().define(ANIM_TICK, 0);
		this.getEntityData().define(DATA_TRUSTED_ID_0, Optional.empty());
		this.getEntityData().define(DATA_TRUSTED_ID_1, Optional.empty());
		this.getEntityData().define(DATA_TYPE_ID, 0);
		this.getEntityData().define(DATA_FLAGS_ID, (byte)0);
		this.getEntityData().define(NETHERITE_TICK, 0);
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
						this.attack1AnimationState.startIfStopped(this.tickCount);
						this.stopMostAnimation(this.attack1AnimationState);
						break;
					case 2:
						this.attack2AnimationState.startIfStopped(this.tickCount);
						this.stopMostAnimation(this.attack2AnimationState);
						break;
					case 3:
						this.attack3AnimationState.startIfStopped(this.tickCount);
						this.stopMostAnimation(this.attack3AnimationState);
						break;
				}
			}
		}
		super.onSyncedDataUpdated(entityDataAccessor);
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
		((CarvedIronGolemEntity)this).addTrustedUUID(player.getUUID());
	}
	@Override
	public boolean trusts(UUID uUID) {
		return this.getTrustedUUIDs().contains(uUID);
	}

	@Override
	public InteractionResult mobInteract(Player player, InteractionHand interactionHand) {
		ItemStack itemstack = player.getItemInHand(interactionHand);
		if (itemstack.getItem() instanceof CarvedFlag) {
			float f1 = 1.0f + (this.random.nextFloat() - this.random.nextFloat()) * 0.2f;
			if (!this.isSilent()) {
				this.playSound(SoundEvents.IRON_GOLEM_REPAIR, 1.0f, f1);
			}
			return InteractionResult.PASS;
		}
		if (itemstack.getItem() == Items.NETHERITE_BLOCK) {
			if (!this.level().isClientSide()) {
				this.setNetheriteTick(12000);
			}
			player.swing(interactionHand);
			float f = (float)this.getY();
			Vec3 vec3 = this.getDeltaMovement();
			for (int i = 0; i < 10; ++i) {
				float f2 = (this.random.nextFloat() * 2.0f - 1.0f) * this.getBbWidth() * 0.5f;
				float f3 = (this.random.nextFloat() * 2.0f - 1.0f) * this.getBbWidth() * 0.5f;
				this.level().addParticle(new BlockParticleOption(ParticleTypes.BLOCK, Blocks.NETHERITE_BLOCK.defaultBlockState()), this.getX() + (double)f2, f + 2f, this.getZ() + (double)f3, vec3.x, vec3.y, vec3.z);
			}
			if (!player.getAbilities().instabuild) {
				itemstack.shrink(1);
			}
			if (!this.isSilent()) {
				this.playSound(SoundEvents.IRON_GOLEM_REPAIR, 1.0f, 1);
			}
			return InteractionResult.PASS;
		}
		if (!this.isAggressive() && this.isEmerald(itemstack)) {
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
				this.playSound(SoundEvents.IRON_GOLEM_REPAIR, 1.0f, f1);
			}
			if (!player.getAbilities().instabuild) {
				itemstack.shrink(1);
			}
			float f = (float)this.getY();
			Vec3 vec3 = this.getDeltaMovement();
			for (int i = 0; i < 10; ++i) {
				float f2 = (this.random.nextFloat() * 2.0f - 1.0f) * this.getBbWidth() * 0.5f;
				float f3 = (this.random.nextFloat() * 2.0f - 1.0f) * this.getBbWidth() * 0.5f;
				((Level)this.level()).addParticle(ParticleTypes.HAPPY_VILLAGER, this.getX() + (double)f2, f + 2f, this.getZ() + (double)f3, vec3.x, vec3.y, vec3.z);
			}
			if (!(this.trusts(player.getUUID()))) {
				RelationshipEvent.AddCopperCarvedCompanyRelationship(player, 5);
			}
			this.addTrustedUUID(player.getUUID());
			return InteractionResult.SUCCESS;
		}
		return super.mobInteract(player, interactionHand);
	}

	@Override
	public void aiStep() {
		super.aiStep();
		if (this.attackAnimationTick > 0) {
			--this.attackAnimationTick;
		}
		if (!this.level().isClientSide()) {
			this.updatePersistentAnger((ServerLevel)this.level(), true);
		}
		if (this.getRandom().nextInt(900) == 1 && this.isAlive()) {
			this.heal(3.0f);
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
		//站立动画
		if (this.isAlive()) {
			this.idleAnimationState.startIfStopped(this.tickCount);
		}
		//停止战斗
		if (this.getTarget() != null && (!this.getTarget().isAlive() || this.getTarget() instanceof Player player && (player.isCreative() || player.isSpectator()))){
			this.setTarget(null);
		}
		//
		if (this.getNetheriteTick() > 0) {
			this.clearFire();
			if (!this.level().isClientSide()) {
				this.setNetheriteTick(this.getNetheriteTick() - 1);
			}
		}
	}

	@Override
	public boolean doHurtTarget(Entity entity) {
		attackAnimation();
		this.attackAnimationTick = 10;
		this.level().broadcastEntityEvent(this, (byte)4);
		AttackFind.attackBegin(this, entity);
		if (!this.isSilent()) {
			this.playSound(SoundEvents.IRON_GOLEM_ATTACK, 1.0F, 1.0F);
			this.playSound(SoundEvents.PLAYER_ATTACK_CRIT, 1.0F, 1.0F);
		}
		if (this.getNetheriteTick() > 0) {
			DamageSource damageSource = AttackFind.findDamageType(this, JerotesDamageTypes.BYPASSES_COOLDOWN_MELEE, this);
            return AttackFind.attackAfterCustomDamage(this, entity, damageSource, 0.3f, 0.5f, false, 0f);
		}
		float f = this.getAttackDamage();
		float f2 = (int)f > 0 ? f / 2.0F + (float)this.random.nextInt((int)f) : f;

		boolean bl = AttackFind.attackAfter(this, entity, 1f, 1f, true, f2);
		if (bl) {
			//群攻
			List<LivingEntity> list = this.level().getEntitiesOfClass(LivingEntity.class, entity.getBoundingBox().inflate(0.5, 0.2, 0.5));
			for (LivingEntity hurt : list) {
				if (hurt == null) continue;
				if (AttackFind.FindCanNotAttack(this, hurt, entity)) continue;
				if (AttackFind.FindCanNotAttack(this, hurt)) continue;
				if (!this.hasLineOfSight(hurt)) continue;
				if (hurt instanceof CarvedIronGolemEntity carvedIronGolem && carvedIronGolem.getTarget() != this && this.getTarget() != carvedIronGolem)
					continue;
				AttackFind.attackBegin(this, hurt);
				boolean bl2 = AttackFind.attackAfter(this, hurt, 1f, 1f, true, f2*0.5f);
			}
			//横扫效果
			Main.sweepAttack(this);
		}
        return bl;
	}
	public void attackAnimation() {
		if (!this.level().isClientSide()) {
			int attackRandom = this.getRandom().nextInt(30);
			if (attackRandom < 10) {
				this.setAnimTick(15);
				this.setAnimationState("attack1");
			} else if (attackRandom < 20) {
				this.setAnimTick(15);
				this.setAnimationState("attack2");
			} else {
				this.setAnimTick(15);
				this.setAnimationState("attack3");
			}
		}
	}
	private float getAttackDamage() {
		return (float)this.getAttributeValue(Attributes.ATTACK_DAMAGE);
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
	public boolean isInvulnerableTo(DamageSource damageSource) {
		if (damageSource.is(DamageTypeTags.IS_FALL)
				|| damageSource.is(DamageTypes.CACTUS)
				|| damageSource.is(DamageTypes.SWEET_BERRY_BUSH)
				|| damageSource.is(DamageTypes.DROWN)
				|| damageSource.is(DamageTypes.FALLING_ANVIL)
				|| AttackFind.isFireDamage(damageSource) && this.getNetheriteTick() > 0)
			return true;
		return super.isInvulnerableTo(damageSource);
	}
	@Override
	public boolean hurt(DamageSource damageSource, float f) {
		if (isInvulnerableTo(damageSource)) {
			return super.hurt(damageSource, f);
		}
		boolean bl = super.hurt(damageSource, f);
		if (bl) {
			if (damageSource.getEntity() instanceof Player player && !this.trusts(player.getUUID()) && this.getTarget() == null) {
				RelationshipEvent.AddCopperCarvedCompanyRelationship(player, -2);
			}
			float damage = f;
			if (this.getNetheriteTick() > 0) {
				float f2 = (float) this.getNetheriteTick() / 12000 / 4;
				if (f2 > 0.25f) {
					f2 = 0.25f;
				}
				f -= f * f2 * 4;
				if (!this.level().isClientSide()) {
					this.setNetheriteTick(this.getNetheriteTick() - (int) damage * 20);
				}
			}
		}
		return bl;
	}

	public void handleEntityEvent(byte p_28844_) {
		if (p_28844_ == 4) {
			this.attackAnimationTick = 10;
			this.playSound(SoundEvents.IRON_GOLEM_ATTACK, 1.0F, 1.0F);
		} else {
			super.handleEntityEvent(p_28844_);
		}
	}
	public int getAttackAnimationTick() {
		return this.attackAnimationTick;
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
					player.sendSystemMessage(this.getCombatTracker().getDeathMessage());
				}
			}
		}
		super.die(damageSource);
	}
}
