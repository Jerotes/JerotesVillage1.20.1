package com.jerotes.jerotesvillage.entity.Monster.Resurrecter;

import com.jerotes.jerotes.entity.Interface.EliteEntity;
import com.jerotes.jerotes.event.JerotesBossEvent;
import com.jerotes.jerotes.util.AttackFind;
import com.jerotes.jerotes.util.Main;
import com.jerotes.jerotesvillage.config.OtherMainConfig;
import com.jerotes.jerotesvillage.init.JerotesVillageGameRules;
import com.jerotes.jerotesvillage.init.JerotesVillageItems;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.chat.Component;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.util.RandomSource;
import net.minecraft.world.BossEvent;
import net.minecraft.world.DifficultyInstance;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.entity.*;
import net.minecraft.world.entity.ai.attributes.AttributeSupplier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.ServerLevelAccessor;

import javax.annotation.Nullable;
import java.util.List;

public class HeavyArmoredSedimentGuardEntity extends ResurrecterEntity implements EliteEntity {
	private final JerotesBossEvent bossEvent = new JerotesBossEvent(this, this.getUUID(), BossEvent.BossBarColor.BLUE, BossEvent.BossBarOverlay.NOTCHED_6, false);

	public HeavyArmoredSedimentGuardEntity(EntityType type, Level world) {
		super(type, world);
		xpReward = 20;
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
		this.maybeWearArmor(EquipmentSlot.HEAD, new ItemStack(JerotesVillageItems.ANCIENT_SECOND_ROUNDER_HEAVY_HELMET.get()), randomSource);
		this.maybeWearArmor(EquipmentSlot.CHEST, new ItemStack(JerotesVillageItems.ANCIENT_SECOND_ROUNDER_HEAVY_CHESTPLATE.get()), randomSource);
		this.maybeWearArmor(EquipmentSlot.LEGS, new ItemStack(JerotesVillageItems.ANCIENT_SECOND_ROUNDER_HEAVY_LEGGINGS.get()), randomSource);
		this.maybeWearArmor(EquipmentSlot.FEET, new ItemStack(JerotesVillageItems.ANCIENT_SECOND_ROUNDER_HEAVY_BOOTS.get()), randomSource);
	}

	private void maybeWearArmor(EquipmentSlot equipmentSlot, ItemStack itemStack, RandomSource randomSource) {
		this.setItemSlot(equipmentSlot, itemStack);
	}

	public static AttributeSupplier.Builder createAttributes() {
		AttributeSupplier.Builder builder = Mob.createMobAttributes();
		builder = builder.add(Attributes.MOVEMENT_SPEED, 0.24);
		builder = builder.add(Attributes.MAX_HEALTH, 40);
		builder = builder.add(Attributes.ARMOR, 5);
		builder = builder.add(Attributes.ATTACK_DAMAGE, 5);
		builder = builder.add(Attributes.FOLLOW_RANGE, 35);
		builder = builder.add(Attributes.KNOCKBACK_RESISTANCE, 0.5);
		return builder;
	}

	@Override
	public void setCustomName(@Nullable Component component) {
		super.setCustomName(component);
		this.bossEvent.setName(this.getDisplayName());
	}
	@Override
	public void addAdditionalSaveData(CompoundTag compoundTag) {
		super.addAdditionalSaveData(compoundTag);
	}
	@Override
	public void readAdditionalSaveData(CompoundTag compoundTag) {
		super.readAdditionalSaveData(compoundTag);
		if (this.hasCustomName()) {
			this.bossEvent.setName(this.getDisplayName());
		}
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
	}
	@Override
	protected float getSoundVolume() {
		return 2f;
	}

	@Override
	public boolean doHurtTarget(Entity entity) {
		this.level().broadcastEntityEvent(this, (byte) 4);
		boolean bl = super.doHurtTarget(entity);
		if (bl) {
			//群攻
			List<LivingEntity> list = this.level().getEntitiesOfClass(LivingEntity.class, entity.getBoundingBox().inflate(1.0, 0.25, 1.0));
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

	@Nullable
	@Override
	public SpawnGroupData finalizeSpawn(ServerLevelAccessor serverLevelAccessor, DifficultyInstance difficultyInstance, MobSpawnType mobSpawnType, @Nullable SpawnGroupData spawnGroupData, @Nullable CompoundTag compoundTag) {
		this.setShieldLevel(2);
		return super.finalizeSpawn(serverLevelAccessor, difficultyInstance, mobSpawnType, spawnGroupData, compoundTag);
	}

	@Override
	public ItemStack createSpawnWeapon(float weaponRandom) {
		if (weaponRandom > 0.65) {
			return new ItemStack(JerotesVillageItems.ANCIENT_SECOND_ROUNDER_GREATSWORD.get());
		}
		if (weaponRandom > 0.35) {
			return new ItemStack(JerotesVillageItems.ANCIENT_SECOND_ROUNDER_BATTLEAXE.get());
		}
		if (weaponRandom > 0.25) {
			return new ItemStack(JerotesVillageItems.ANCIENT_SECOND_ROUNDER_PIKE.get());
		}
		if (weaponRandom > 0.23) {
			return new ItemStack(JerotesVillageItems.ANCIENT_SECOND_ROUNDER_PINCER.get());
		}
		return new ItemStack(JerotesVillageItems.ANCIENT_SECOND_ROUNDER_WARHAMMER.get());
	}

	@Override
	public void tickDeath() {
		++this.deathTime;
		if (this.deathTime >= 20 && !this.level().isClientSide() && !this.isRemoved()) {
			this.level().broadcastEntityEvent(this, (byte)60);
			this.remove(RemovalReason.KILLED);
		}
	}
}
