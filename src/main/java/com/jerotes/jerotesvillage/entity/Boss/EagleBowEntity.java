package com.jerotes.jerotesvillage.entity.Boss;

import com.jerotes.jerotes.client.sound.BossMusicPlayer;
import com.jerotes.jerotes.config.MainConfig;
import com.jerotes.jerotes.entity.Interface.BossEntity;
import com.jerotes.jerotes.entity.Interface.SpellUseEntity;
import com.jerotes.jerotes.event.JerotesBossEvent;
import com.jerotes.jerotes.init.JerotesGameRules;
import com.jerotes.jerotes.util.AttackFind;
import com.jerotes.jerotes.util.EntityAndItemFind;
import com.jerotes.jerotes.util.Main;
import com.jerotes.jerotesvillage.config.OtherMainConfig;
import com.jerotes.jerotesvillage.entity.Monster.Resurrecter.ResurrecterEntity;
import com.jerotes.jerotesvillage.event.BossBarEvent;
import com.jerotes.jerotesvillage.init.JerotesVillageItems;
import com.jerotes.jerotesvillage.init.JerotesVillageSoundEvents;
import com.jerotes.jerotesvillage.spell.OtherSpellFind;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.chat.Component;
import net.minecraft.network.syncher.EntityDataAccessor;
import net.minecraft.network.syncher.EntityDataSerializers;
import net.minecraft.network.syncher.SynchedEntityData;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.tags.DamageTypeTags;
import net.minecraft.util.RandomSource;
import net.minecraft.world.BossEvent;
import net.minecraft.world.DifficultyInstance;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.damagesource.DamageTypes;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.*;
import net.minecraft.world.entity.ai.attributes.AttributeSupplier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.BowItem;
import net.minecraft.world.item.CrossbowItem;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.ShieldItem;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.Vec3;

import javax.annotation.Nullable;
import java.util.List;

public class EagleBowEntity extends ResurrecterEntity implements BossEntity, SpellUseEntity {
	public AnimationState startAnimationState = new AnimationState();
	public AnimationState idleAnimationState = new AnimationState();
	public AnimationState attack1AnimationState = new AnimationState();
	public AnimationState attack2AnimationState = new AnimationState();
	public AnimationState swordAttack1AnimationState = new AnimationState();
	public AnimationState swordAttack2AnimationState = new AnimationState();
	public AnimationState swordAttack3AnimationState = new AnimationState();
	public AnimationState swordAttack4AnimationState = new AnimationState();
	public AnimationState leadAnimationState = new AnimationState();
	public AnimationState block1AnimationState = new AnimationState();
	public AnimationState block2AnimationState = new AnimationState();
	public AnimationState block3AnimationState = new AnimationState();
	public AnimationState shieldAttackAnimationState = new AnimationState();
	public AnimationState dead1AnimationState = new AnimationState();
	public AnimationState dead2AnimationState = new AnimationState();
	private static final EntityDataAccessor<Boolean> IS_LEGEND = SynchedEntityData.defineId(EagleBowEntity.class, EntityDataSerializers.BOOLEAN);
	private final JerotesBossEvent bossEvent = new JerotesBossEvent(this, this.getUUID(), BossEvent.BossBarColor.BLUE, false);

	public EagleBowEntity(EntityType<? extends EagleBowEntity> entityType, Level level) {
		super(entityType, level);
		setMaxUpStep(1.1f);
		xpReward = 300;
		this.setCanPickUpLoot(true);
		this.armorDropChances[EquipmentSlot.HEAD.getIndex()] = (float) OtherMainConfig.SedimentLordDropEquipmentChance;
		this.armorDropChances[EquipmentSlot.CHEST.getIndex()] = (float) OtherMainConfig.SedimentLordDropEquipmentChance;
		this.armorDropChances[EquipmentSlot.LEGS.getIndex()] = (float) OtherMainConfig.SedimentLordDropEquipmentChance;
		this.armorDropChances[EquipmentSlot.FEET.getIndex()] = (float) OtherMainConfig.SedimentLordDropEquipmentChance;
		this.handDropChances[EquipmentSlot.MAINHAND.getIndex()] = (float) OtherMainConfig.SedimentLordDropEquipmentChance;
		this.handDropChances[EquipmentSlot.OFFHAND.getIndex()] = (float) OtherMainConfig.SedimentLordDropEquipmentChance;
		if (OtherMainConfig.BossBaseAttributeCanUseConfig) {
			this.getAttribute(Attributes.MAX_HEALTH).setBaseValue(OtherMainConfig.SedimentLordMaxHealth);
			this.setHealth(this.getMaxHealth());
			this.getAttribute(Attributes.ATTACK_DAMAGE).setBaseValue(OtherMainConfig.SedimentLordMeleeDamage);
			this.getAttribute(Attributes.ARMOR).setBaseValue(OtherMainConfig.SedimentLordArmor);
			this.getAttribute(Attributes.MOVEMENT_SPEED).setBaseValue(OtherMainConfig.SedimentLordMovementSpeed);
			this.getAttribute(Attributes.ATTACK_KNOCKBACK).setBaseValue(OtherMainConfig.SedimentLordAttackKnockback);
			this.getAttribute(Attributes.KNOCKBACK_RESISTANCE).setBaseValue(OtherMainConfig.SedimentLordKnockbackResistance);
		}
		if (level.isClientSide) {
			BossBarEvent.BOSSES.add(this);
		}
	}
	//百分比
	public boolean hasPercentageDamage() {
		return MainConfig.HasPercentageDamage.contains(this.getEncodeId())
				|| OtherMainConfig.BossHasPercentageDamage.contains(this.getEncodeId()) || this.isLegend();
	}
	public float PercentageDamage(DamageSource damageSource) {
		if (AttackFind.MagicResistance(damageSource)) {
			return(float) OtherMainConfig.SedimentLordMagicAttackPercentage;
		}
		return (float) OtherMainConfig.SedimentLordAttackPercentage;
	}
	//限伤
	public boolean hasDamageCap() {
		return MainConfig.HasDamageCap.contains(this.getEncodeId()) || OtherMainConfig.BossHasDamageCap.contains(this.getEncodeId()) || this.isLegend();
	}
	public float DamageCap(DamageSource damageSource, Entity entity) {
		return (float) OtherMainConfig.SedimentLordDamageCap;
	}
	//间隔
	public boolean hasDamageCooldownTick() {
		return MainConfig.HasDamageCooldownTick.contains(this.getEncodeId()) || OtherMainConfig.BossHasDamageCooldownTick.contains(this.getEncodeId()) || this.isLegend();
	}
	public float DamageCooldownTick(DamageSource damageSource, Entity entity) {
		float base = 1.0F;
		if (damageSource.is(DamageTypeTags.BYPASSES_COOLDOWN)) {
			base *= 0.5F;
		}
		return (float)OtherMainConfig.SedimentLordDamageCooldownTick * base;
	}

	@Override
	public boolean wantsToPickUp(ItemStack itemStack) {
		if (itemStack.canEquip(EquipmentSlot.HEAD,this)) {
			return false;
		}
		if (itemStack.canEquip(EquipmentSlot.CHEST,this)) {
			return false;
		}
		if (itemStack.canEquip(EquipmentSlot.LEGS,this)) {
			return false;
		}
		if (itemStack.canEquip(EquipmentSlot.FEET,this)) {
			return false;
		}
		return super.wantsToPickUp(itemStack);
	}

	@Override
	protected boolean canReplaceCurrentItem(ItemStack newItem, ItemStack oldItem) {
		if (newItem.getItem() instanceof BowItem) {
			return false;
		}
		if (newItem.getItem() instanceof CrossbowItem) {
			return false;
		}
		if (newItem.getItem() instanceof ShieldItem) {
			return false;
		}
		return super.canReplaceCurrentItem(newItem, oldItem);
	}

	@Override
	public void startSeenByPlayer(ServerPlayer serverPlayer) {
		super.startSeenByPlayer(serverPlayer);
		this.bossEvent.addPlayer(serverPlayer);
	}

	@Override
	public void stopSeenByPlayer(ServerPlayer serverPlayer) {
		super.stopSeenByPlayer(serverPlayer);
		this.bossEvent.removePlayer(serverPlayer);
	}

	@Override
	public void customServerAiStep() {
		super.customServerAiStep();
		this.bossEvent.setProgress(this.getHealth() / this.getMaxHealth());
	}

	@Override
	protected void populateDefaultEquipmentSlots(RandomSource randomSource, DifficultyInstance difficultyInstance) {
		this.maybeWearArmor(EquipmentSlot.HEAD, new ItemStack(JerotesVillageItems.ANCIENT_SECOND_ROUNDER_NOBLE_HELMET.get()), randomSource);
		this.maybeWearArmor(EquipmentSlot.CHEST, new ItemStack(JerotesVillageItems.ANCIENT_SECOND_ROUNDER_NOBLE_CHESTPLATE.get()), randomSource);
		this.maybeWearArmor(EquipmentSlot.LEGS, new ItemStack(JerotesVillageItems.ANCIENT_SECOND_ROUNDER_NOBLE_LEGGINGS.get()), randomSource);
		this.maybeWearArmor(EquipmentSlot.FEET, new ItemStack(JerotesVillageItems.ANCIENT_SECOND_ROUNDER_NOBLE_BOOTS.get()), randomSource);
	}

	private void maybeWearArmor(EquipmentSlot equipmentSlot, ItemStack itemStack, RandomSource randomSource) {
		this.setItemSlot(equipmentSlot, itemStack);
	}

	public static AttributeSupplier.Builder createAttributes() {
		AttributeSupplier.Builder builder = Mob.createMobAttributes();
		builder = builder.add(Attributes.MAX_HEALTH, 170);
		builder = builder.add(Attributes.ATTACK_DAMAGE, 6);
		builder = builder.add(Attributes.ARMOR, 3);
		builder = builder.add(Attributes.MOVEMENT_SPEED, 0.20);
		builder = builder.add(Attributes.ATTACK_KNOCKBACK, 0.4);
		builder = builder.add(Attributes.KNOCKBACK_RESISTANCE, 0.5);

		builder = builder.add(Attributes.FOLLOW_RANGE, 128);
		return builder;
	}

	@Override
	protected SoundEvent getAmbientSound() {
		return JerotesVillageSoundEvents.SEDIMENT_LORD_AMBIENT;
	}
	@Override
	protected SoundEvent getDeathSound() {
		return JerotesVillageSoundEvents.SEDIMENT_LORD_DEATH;
	}
	@Override
	protected SoundEvent getHurtSound(DamageSource damageSource) {
		return JerotesVillageSoundEvents.SEDIMENT_LORD_HURT;
	}
	@Override
	public boolean isLeftHanded() {
		return false;
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
		if (!this.getMainHandItem().isEmpty())
			return aabb1.inflate(0.85d, 0.85d, 0.85d);
		return aabb1.inflate(0.5d, 0.5d, 0.5d);
	}
	@Override
	public boolean isWithinMeleeAttackRange(LivingEntity livingEntity) {
		return this.getAttackBoundingBox().intersects(livingEntity.getBoundingBox());
	}
	@Override
	protected float getSoundVolume() {
		return 5.0f;
	}
	@Override
	public void travel(Vec3 vec3) {
		super.travel(vec3);
		if (this.specialAction()) {
			this.setDeltaMovement(this.getDeltaMovement().multiply(0.05d, 1d, 0.05d));
		}
	}
	@Override
	public SoundEvent getBossMusic() {
		return JerotesVillageSoundEvents.BOSS_MUSIC_1;
	}

	public int spellLevel = 4;
	@Override
	public int getSpellLevel() {
		return this.spellLevel;
	}
	public int startTick;
	public double leadTick;
	public int specialBlockTick;
	public int shieldAttackTick = -20;
	public boolean specialAction() {
		return this.leadTick > (OtherMainConfig.SedimentLordLeadCooldown * 20 - 20) || this.shieldAttackTick > 0 || this.startTick > 0;
	}
	@Override
	public void setCustomName(@Nullable Component component) {
		super.setCustomName(component);
		this.bossEvent.setName(this.getDisplayName());
	}
	//是否传奇
	public boolean isLegend() {
		return this.getEntityData().get(IS_LEGEND);
	}
	public void setLegend(boolean bl) {
		this.getEntityData().set(IS_LEGEND, bl);
	}
	public boolean isLegendary() {
		return this.isLegend() || hasPercentageDamage() && hasDamageCap() && hasDamageCooldownTick();
	}
	@Override
	public void addAdditionalSaveData(CompoundTag compoundTag) {
		super.addAdditionalSaveData(compoundTag);
		compoundTag.putBoolean("IsLegend", this.isLegend());
		compoundTag.putInt("StartTick", this.startTick);
		compoundTag.putDouble("LeadTick", this.leadTick);
		compoundTag.putInt("SpecialBlockTick", this.specialBlockTick);
		compoundTag.putInt("ShieldAttackTick", this.shieldAttackTick);
	}
	@Override
	public void readAdditionalSaveData(CompoundTag compoundTag) {
		super.readAdditionalSaveData(compoundTag);
		if (this.hasCustomName()) {
			this.bossEvent.setName(this.getDisplayName());
		}
		this.setLegend(compoundTag.getBoolean("IsLegend"));
		this.startTick = compoundTag.getInt("StartTick");
		this.leadTick = compoundTag.getDouble("LeadTick");
		this.specialBlockTick = compoundTag.getInt("SpecialBlockTick");
		this.shieldAttackTick = compoundTag.getInt("ShieldAttackTick");
		this.bossEvent.setId(this.getUUID());
	}
	@Override
	protected void defineSynchedData() {
		super.defineSynchedData();
		this.getEntityData().define(IS_LEGEND, false);
	}

	@Override
	public void tick() {
		super.tick();
		if (!level().isClientSide && getBossMusic() != null) {
			if (!isSilent() && getTarget() instanceof Player player && player.isAlive()) {
				this.level().broadcastEntityEvent(this, (byte)67);
			}
			else {
				this.level().broadcastEntityEvent(this, (byte)68);
			}
		}
	}

	@Override
	public void aiStep() {
		super.aiStep();
		this.bossEvent.update();
		if (this.specialAction()){
			this.getNavigation().stop();
		}
		//停止战斗
		if (this.getTarget() != null && (!this.getTarget().isAlive() || this.getTarget() instanceof Player player && (player.isCreative() || player.isSpectator()))) {
			this.setTarget(null);
		}
		List<EagleBowEntity> listLord = this.level().getEntitiesOfClass(EagleBowEntity.class, this.getBoundingBox().inflate(64.0, 64.0, 64.0));
		listLord.removeIf(entity -> this.getTarget() == entity || entity.getTarget() == this || !((this.getTeam() == null && entity.getTeam() == null) || (entity.isAlliedTo(this))));
		List<ResurrecterEntity> listResurrect = this.level().getEntitiesOfClass(ResurrecterEntity.class, this.getBoundingBox().inflate(64.0, 64.0, 64.0));
		listResurrect.removeIf(entity -> this.getTarget() == entity || entity.getTarget() == this || !((this.getTeam() == null && entity.getTeam() == null) || (entity.isAlliedTo(this))));
		if (this.leadTick > 1) {
			this.leadTick -= 1;
		}
		else if (this.getTarget() != null) {
			if (this.leadTick > -30) {
				this.leadTick -= 1;
			}
			if (this.leadTick <= -30 && listResurrect.size() < listLord.size() * 16 && this.random.nextInt(1, 120) == 1 && this.distanceTo(this.getTarget()) < 32 && this.deathTime == 0) {
				this.block1AnimationState.stop();
				this.block2AnimationState.stop();
				this.block3AnimationState.stop();
				this.leadTick = OtherMainConfig.SedimentLordLeadCooldown * 20;
				OtherSpellFind.SedimentOrder(this, this.getTarget(), this.getSpellLevel(), this.getSpellLevel() + 2, 4, 32);
				if (!this.isSilent()) {
					this.level().playSound(null, this.getX(), this.getY(), this.getZ(), JerotesVillageSoundEvents.SEDIMENT_LORD_LEAD, this.getSoundSource(), 10.0f, 1.0f);
				}
			}
		}
		if (this.specialBlockTick > 0){
			this.specialBlockTick -= 1;
		}
		if (this.shieldAttackTick > -20){
			this.shieldAttackTick -= 1;
		}
		if (this.shieldAttackTick == 55 || this.shieldAttackTick == 50 || this.shieldAttackTick == 45 || this.shieldAttackTick == 40
		|| this.shieldAttackTick == 35 || this.shieldAttackTick == 30 || this.shieldAttackTick == 25 || this.shieldAttackTick == 20
		|| this.shieldAttackTick == 15 || this.shieldAttackTick == 10 || this.shieldAttackTick == 5) {
			this.shieldAttack();
		}
		if (this.shieldAttackTick <= 0) {
			this.shieldAttackAnimationState.stop();
		}
		if (this.isAlive()) {
			this.idleAnimationState.startIfStopped(this.tickCount);
		}
	}

	@Override
	public boolean doHurtTarget(Entity entity) {
		if (!this.specialAction()) {
			this.level().broadcastEntityEvent(this, (byte) 4);
			boolean bl = super.doHurtTarget(entity);
			if (bl) {
				if (!this.isSilent()) {
					this.level().playSound(null, this.getX(), this.getY(), this.getZ(), SoundEvents.PLAYER_ATTACK_SWEEP, this.getSoundSource(), 1.0f, 1.0f);
				}
				//群攻
				List<LivingEntity> list = this.level().getEntitiesOfClass(LivingEntity.class, entity.getBoundingBox().inflate(1.5, 1.0, 1.5));
				if (this.onGround() && !this.getItemInHand(InteractionHand.MAIN_HAND).isEmpty()) {
					for (LivingEntity hurt : list) {
						if (hurt == null) continue;
						if (AttackFind.FindCanNotAttack(this, hurt, entity)) continue;
						if (AttackFind.FindCanNotAttack(this, hurt)) continue;
						if (!this.hasLineOfSight(hurt)) continue;
						AttackFind.attackBegin(this, hurt);
						AttackFind.attackAfter(this, hurt, 0.5f, 0.5f, false, 0f);
					}
					//横扫效果
					Main.sweepAttack(this);
				}
			}
			return bl;
		}
		return false;
	}

	public boolean shieldAttack() {
		this.swing(InteractionHand.MAIN_HAND);
		if (!this.isSilent()) {
			this.level().playSound(null, this.getX(), this.getY(), this.getZ(), SoundEvents.PLAYER_ATTACK_SWEEP, this.getSoundSource(), 10.0f, 1.0f);
		}
		List<LivingEntity> list = this.level().getEntitiesOfClass(LivingEntity.class, this.getBoundingBox().inflate(2, 2, 2));
		for (LivingEntity hurt : list) {
			if (hurt == null) continue;
			if (AttackFind.FindCanNotAttack(this, hurt)) continue;
			AttackFind.attackBegin(this, hurt);
			AttackFind.attackAfter(this, hurt, 1f, 0.5f, false, 0f);
		}
		//横扫效果
		Main.sweepAttack(this);
		return true;
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
		if (damageSource.is(DamageTypeTags.IS_FALL))
			return super.hurt(damageSource, amount/3);
		if (!this.isNoAi() && this.isAggressive() && isDamageSourceBlocks(damageSource) && !this.getMainHandItem().isEmpty() && this.isAngryTick() && this.deathTime == 0 && shieldAttackTick <= 0) {
			if (specialBlockTick <= 0) {
				this.leadAnimationState.stop();
				specialBlockTick = 5;
				int attackDamage = (int) this.getAttributeValue(Attributes.ATTACK_DAMAGE) * 10;
				if (attackDamage < 2) {
					attackDamage = 2;
				}
				if (this.random.nextInt(1, attackDamage) > amount * 3.5) {
					if (!this.isSilent()) {
						this.level().playSound(null, this.getX(), this.getY(), this.getZ(), JerotesVillageSoundEvents.SEDIMENT_LORD_BLOCK, this.getSoundSource(), 10.0f, 1.0f);
					}
					if (JerotesGameRules.JEROTES_MELEE_CAN_BREAK != null && this.level().getLevelData().getGameRules().getBoolean(JerotesGameRules.JEROTES_MELEE_CAN_BREAK)) {
						ItemStack hand = this.getMainHandItem();
						hand.hurtAndBreak((int) amount/3, this, player -> player.broadcastBreakEvent(EquipmentSlot.MAINHAND));
					}
					return false;
				}
			}
		}
		this.shieldAttackTick = 0;
		this.shieldAttackAnimationState.stop();
		this.level().broadcastEntityEvent(this, (byte) 14);
		if (damageSource.is(DamageTypeTags.BYPASSES_ARMOR))
			return super.hurt(damageSource, amount/2);
		return super.hurt(damageSource, amount);
	}

	@Override
	protected void blockedByShield(LivingEntity livingEntity) {
		if (this.shieldAttackTick <= -20) {
			this.shieldAttackTick = 60;
			this.level().broadcastEntityEvent(this, (byte) 13);
		}
	}

	@Override
	public boolean canBeAffected(MobEffectInstance mobEffectInstance) {
		if (mobEffectInstance.getEffect() == MobEffects.LEVITATION) {
			return false;
		}
		return super.canBeAffected(mobEffectInstance);
	}

	@Override
	public void handleEntityEvent(byte by) {
		if (by == 4) {
			int attackRandom = this.random.nextInt(30);
			this.block1AnimationState.stop();
			this.block2AnimationState.stop();
			this.block3AnimationState.stop();
			if (this.getMainHandItem().isEmpty()) {
				if (attackRandom < 20) {
					this.attack1AnimationState.start(this.tickCount);
				} else {
					this.attack2AnimationState.start(this.tickCount);
				}
			} else {
				if (attackRandom > 20) {
					this.swordAttack1AnimationState.start(this.tickCount);
				} else if (attackRandom > 10) {
					this.swordAttack2AnimationState.start(this.tickCount);
				} else if (attackRandom > 5) {
					this.swordAttack3AnimationState.start(this.tickCount);
				} else {
					this.swordAttack4AnimationState.start(this.tickCount);
				}
			}
		}
		else if (by == 11) {
			this.leadTick = OtherMainConfig.SedimentLordLeadCooldown * 20;
			this.leadAnimationState.start(this.tickCount);
		}
		else if (by == 12) {
			this.specialBlockTick = 5;
			int blockRandom = this.random.nextInt(30);
			if (blockRandom > 20) {
				this.block1AnimationState.start(this.tickCount);
			} else if (blockRandom > 10) {
				this.block2AnimationState.start(this.tickCount);
			} else {
				this.block3AnimationState.start(this.tickCount);
			}
		}
		else if (by == 13) {
			this.shieldAttackTick = 60;
			this.shieldAttackAnimationState.start(this.tickCount);
		}
		else if (by == 14) {
			this.shieldAttackTick = 0;
		}
		else if (by == 15) {
			this.startAnimationState.start(this.tickCount);
		}
		else if (by == 67) {
			BossMusicPlayer.playBossMusic(this);
		}
		else if (by == 68) {
			BossMusicPlayer.stopBossMusic(this);
		}
		else {
			super.handleEntityEvent(by);
		}
	}

	@Override
	public ItemStack createSpawnWeapon(float weaponRandom) {
		if (weaponRandom > 0.65) {
			return new ItemStack(JerotesVillageItems.ANCIENT_SECOND_ROUNDER_GREATSWORD.get());
		}
		if (weaponRandom > 0.35) {
			return new ItemStack(JerotesVillageItems.ANCIENT_SECOND_ROUNDER_BATTLEAXE.get());
		}
		return new ItemStack(JerotesVillageItems.ANCIENT_SECOND_ROUNDER_WARHAMMER.get());
	}

	@Override
	public void tickDeath() {
		if(deathTime <= 0) {
			if (this.getMainHandItem().isEmpty()) {
				this.dead1AnimationState.start(this.tickCount);
			}
			else {
				this.dead2AnimationState.start(this.tickCount);
			}
		}
		++this.deathTime;
		this.startAnimationState.stop();
		this.attack1AnimationState.stop();
		this.attack2AnimationState.stop();
		this.swordAttack1AnimationState.stop();
		this.swordAttack2AnimationState.stop();
		this.swordAttack3AnimationState.stop();
		this.swordAttack4AnimationState.stop();
		this.leadAnimationState.stop();
		this.block1AnimationState.stop();
		this.block2AnimationState.stop();
		this.block3AnimationState.stop();
		this.idleAnimationState.stop();
		if (this.deathTime >= 40 && !this.level().isClientSide() && !this.isRemoved()) {
			this.level().broadcastEntityEvent(this, (byte)60);
			this.remove(RemovalReason.KILLED);
		}
	}
}
