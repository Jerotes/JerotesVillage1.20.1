package com.jerotes.jerotesvillage.entity.Humanoid.Serpon;

import com.jerotes.jerotes.entity.Interface.EliteEntity;
import com.jerotes.jerotes.entity.Interface.InventoryEntity;
import com.jerotes.jerotes.entity.Interface.UseSpearSpecialEntity;
import com.jerotes.jerotes.util.AttackFind;
import com.jerotes.jerotes.util.Main;
import com.jerotes.jerotesvillage.config.OtherMainConfig;
import com.jerotes.jerotes.event.JerotesBossEvent;
import com.jerotes.jerotesvillage.init.JerotesVillageGameRules;
import com.jerotes.jerotesvillage.init.JerotesVillageItems;
import com.jerotes.jerotesvillage.init.JerotesVillageParticleTypes;
import com.jerotes.jerotesvillage.init.JerotesVillageSoundEvents;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.chat.Component;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.sounds.SoundSource;
import net.minecraft.util.Mth;
import net.minecraft.util.RandomSource;
import net.minecraft.world.BossEvent;
import net.minecraft.world.DifficultyInstance;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.*;
import net.minecraft.world.entity.ai.attributes.AttributeSupplier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.ShieldItem;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.ServerLevelAccessor;
import net.minecraft.world.phys.Vec3;

import javax.annotation.Nullable;
import java.util.List;

public class SerponIntruderEntity extends SerponEntity implements EliteEntity, UseSpearSpecialEntity {
	private final JerotesBossEvent bossEvent = new JerotesBossEvent(this, this.getUUID(), BossEvent.BossBarColor.YELLOW, BossEvent.BossBarOverlay.NOTCHED_6, false);
	public SerponIntruderEntity(EntityType<? extends SerponIntruderEntity> entityType, Level level) {
		super(entityType, level);
		this.xpReward = 80;
	}

	public float getJerotesSpearDamageMultiple() {
		return 2.0F;
	}
	@Override
	public void startSeenByPlayer(ServerPlayer serverPlayer) {
		super.startSeenByPlayer(serverPlayer);
		if (JerotesVillageGameRules.JEROTES_VILLAGE_SOME_ELITE_HAS_BOSS_BAR != null && this.level().getLevelData().getGameRules().getBoolean(JerotesVillageGameRules.JEROTES_VILLAGE_SOME_ELITE_HAS_BOSS_BAR) && OtherMainConfig.EliteCanHasBossBar.contains(this.getEncodeId()))
			this.bossEvent.addPlayer(serverPlayer);
	}

	@Override
	public void stopSeenByPlayer(ServerPlayer serverPlayer) {
		super.stopSeenByPlayer(serverPlayer);
		if (JerotesVillageGameRules.JEROTES_VILLAGE_SOME_ELITE_HAS_BOSS_BAR != null && this.level().getLevelData().getGameRules().getBoolean(JerotesVillageGameRules.JEROTES_VILLAGE_SOME_ELITE_HAS_BOSS_BAR) && OtherMainConfig.EliteCanHasBossBar.contains(this.getEncodeId()))
			this.bossEvent.removePlayer(serverPlayer);
	}

	@Override
	public void customServerAiStep() {
		super.customServerAiStep();
		if (JerotesVillageGameRules.JEROTES_VILLAGE_SOME_ELITE_HAS_BOSS_BAR != null && this.level().getLevelData().getGameRules().getBoolean(JerotesVillageGameRules.JEROTES_VILLAGE_SOME_ELITE_HAS_BOSS_BAR) && OtherMainConfig.EliteCanHasBossBar.contains(this.getEncodeId()))
			this.bossEvent.setProgress(this.getHealth() / this.getMaxHealth());
	}

	@Override
	protected void populateDefaultEquipmentSlots(RandomSource randomSource, DifficultyInstance difficultyInstance) {
		this.maybeWearArmor(EquipmentSlot.HEAD, new ItemStack(JerotesVillageItems.SERPON_POWERHOUSE_HELMET.get()), randomSource);
		this.maybeWearArmor(EquipmentSlot.CHEST, new ItemStack(JerotesVillageItems.SERPON_POWERHOUSE_CHESTPLATE.get()), randomSource);
		this.maybeWearArmor(EquipmentSlot.LEGS, new ItemStack(JerotesVillageItems.SERPON_POWERHOUSE_LEGGINGS.get()), randomSource);
		this.maybeWearArmor(EquipmentSlot.FEET, new ItemStack(JerotesVillageItems.SERPON_POWERHOUSE_BOOTS.get()), randomSource);
	}

	private void maybeWearArmor(EquipmentSlot equipmentSlot, ItemStack itemStack, RandomSource randomSource) {
		this.setItemSlot(equipmentSlot, itemStack);
	}

	public static AttributeSupplier.Builder createAttributes() {
		AttributeSupplier.Builder builder = Mob.createMobAttributes();
		builder = builder.add(Attributes.MOVEMENT_SPEED, 0.37);
		builder = builder.add(Attributes.FLYING_SPEED, 0.5);
		builder = builder.add(Attributes.MAX_HEALTH, 55);
		builder = builder.add(Attributes.ATTACK_KNOCKBACK, 0.1);
		builder = builder.add(Attributes.ATTACK_DAMAGE, 7);
		builder = builder.add(Attributes.FOLLOW_RANGE, 64);
		builder = builder.add(Attributes.ARMOR, 2);
		return builder;
	}

	@Override
	protected void doPush(Entity entity) {
		if (this.rushTick > 0 && entity == this.getTarget() && !InventoryEntity.isSpear(this, this.getMainHandItem())) {
			this.doHurtTarget(entity);
			this.rushTick = 0;
		}
		super.doPush(entity);
	}

	@Override
	protected float getSoundVolume() {
		return 2.0f;
	}
	public int rushTick;
	public int rushCooldown;
	@Override
	public void setCustomName(@Nullable Component component) {
		super.setCustomName(component);
		this.bossEvent.setName(this.getDisplayName());
	}
	@Override
	public void addAdditionalSaveData(CompoundTag compoundTag) {
		super.addAdditionalSaveData(compoundTag);
		compoundTag.putInt("RushTick", this.rushTick);
	}
	@Override
	public void readAdditionalSaveData(CompoundTag compoundTag) {
		super.readAdditionalSaveData(compoundTag);
		if (this.hasCustomName()) {
			this.bossEvent.setName(this.getDisplayName());
		}
		this.rushTick = compoundTag.getInt("RushTick");
		this.bossEvent.setId(this.getUUID());
	}

	@Override
	public void aiStep() {
		super.aiStep();
		if (JerotesVillageGameRules.JEROTES_VILLAGE_SOME_ELITE_HAS_BOSS_BAR != null && this.level().getLevelData().getGameRules().getBoolean(JerotesVillageGameRules.JEROTES_VILLAGE_SOME_ELITE_HAS_BOSS_BAR) && OtherMainConfig.EliteCanHasBossBar.contains(this.getEncodeId())) {
			this.bossEvent.update();
			if (OtherMainConfig.EliteBossBarOnlyCombat) {
				this.bossEvent.setVisible(this.getTarget() != null);
			}
		}
		if (this.rushCooldown > 0) {
			this.rushCooldown -= 1;
		}
		if (this.rushTick > 0){
			this.rushTick -= 1;
			if (this.getTarget() != null) {
				if (!this.level().isClientSide) {
					this.removeEffect(MobEffects.WEAKNESS);
					this.removeEffect(MobEffects.MOVEMENT_SLOWDOWN);
					this.addEffect(new MobEffectInstance(MobEffects.DAMAGE_RESISTANCE, 20, 0));
				}
				this.getLookControl().setLookAt(this.getTarget(), 360.0f, 360.0f);
				this.lookAt(this.getTarget(), 360.0f, 360.0f);
				this.RushAttack();
			}
		}
		if (this.getTarget() != null && this.distanceTo(this.getTarget()) < 64 && this.rushTick <= 0 && rushCooldown <= 0 && Main.canSee(this.getTarget(), this) &&
				(this.getRandom().nextInt(60) == 1 || this.getRandom().nextInt(20) == 1 && this.isUsingItem() && InventoryEntity.isSpear(this, this.getUseItem()) && !(InventoryEntity.isSpear(this, this.getMainHandItem()) && this.getUseItem() != this.getMainHandItem()))
		) {
			this.rushTick = 40;
			this.rushCooldown = 120;
			if (!this.isSilent()) {
				this.level().playSound(null, this, JerotesVillageSoundEvents.SERPON_DASH, SoundSource.NEUTRAL, 1.0f, 1.0f);
			}
		}
	}

	@Override
	public boolean doHurtTarget(Entity entity) {
		this.level().broadcastEntityEvent(this, (byte) 4);
		boolean bl = super.doHurtTarget(entity);
		if (bl) {
			//群攻
			List<LivingEntity> list = this.level().getEntitiesOfClass(LivingEntity.class, entity.getBoundingBox().inflate(1.5, 1.0, 1.5));
			if (!this.getItemInHand(InteractionHand.MAIN_HAND).isEmpty()) {
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

	public boolean RushAttack() {
		if (this.level() instanceof ServerLevel serverLevel) {
			for (int i = 0; i < 5; ++i) {
				Vec3 vec3 = this.getDeltaMovement();
				serverLevel.sendParticles(ParticleTypes.SMOKE, this.getRandomX(0.5), this.getRandomY(), this.getRandomZ(0.5), 0, vec3.x, vec3.y - 0.1, vec3.z, 0);
				serverLevel.sendParticles(ParticleTypes.FLAME, this.getRandomX(0.5), this.getRandomY(), this.getRandomZ(0.5), 0, vec3.x, vec3.y - 0.1, vec3.z, 0);
			}
		}
		float f = this.getYRot();
		float f2 = this.getXRot();
		float f3 = -Mth.sin(f * 0.017453292f) * Mth.cos(f2 * 0.017453292f);
		float f4 = -Mth.sin(f2 * 0.017453292f);
		float f5 = Mth.cos(f * 0.017453292f) * Mth.cos(f2 * 0.017453292f);
		float f6 = Mth.sqrt(f3 * f3 + f4 * f4 + f5 * f5);
		float f7 = 0.1f;
		this.push(f3 *= f7 / f6 * 2, f4 *= f7 / f6 * 1, f5 *= f7 / f6 * 2);
		return true;
	}

	@Override
	public void disableShield() {
		if (this.shieldCoolDown < 100) {
			this.shieldCoolDown = 100;
		}
		this.shieldCanUse = 0;
		if (this.getUseItem().getItem() instanceof ShieldItem) {
			this.stopUsingItem();
		}
	}

	@Override
	public void disableShieldTry() {
		if (this.random.nextFloat() < 0.05) {
			if (this.shieldCoolDown < 100) {
				this.shieldCoolDown = 100;
			}
			this.shieldCanUse = 0;
			if (this.getUseItem().getItem() instanceof ShieldItem) {
				this.stopUsingItem();
			}
		}
	}


	@Nullable
	@Override
	public SpawnGroupData finalizeSpawn(ServerLevelAccessor serverLevelAccessor, DifficultyInstance difficultyInstance, MobSpawnType mobSpawnType, @Nullable SpawnGroupData spawnGroupData, @Nullable CompoundTag compoundTag) {
		this.setShieldLevel(3);
		return super.finalizeSpawn(serverLevelAccessor, difficultyInstance, mobSpawnType, spawnGroupData, compoundTag);
	}
	@Override
	public ItemStack createSpawnWeapon(float weaponRandom) {
		if (weaponRandom > 0.5f)
			return new ItemStack(JerotesVillageItems.SERPON_SPEAR.get());
		return new ItemStack(JerotesVillageItems.SERPON_GLAIVE.get());
	}

	@Override
	public void tickDeath() {
		++this.deathTime;
		if (this.deathTime >= 20 && !this.level().isClientSide() && !this.isRemoved()) {
			if (this.level() instanceof ServerLevel serverLevel) {
				serverLevel.sendParticles(JerotesVillageParticleTypes.SERPON_PORTAL.get(), this.getX(), this.getY(0.5), this.getZ(), 0, 0.0, 0.0, 0.0, 0.0);
			}
			this.remove(RemovalReason.CHANGED_DIMENSION);
		}
	}
}

