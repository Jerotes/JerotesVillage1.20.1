package com.jerotes.jerotesvillage.entity.Humanoid.Serpon;

import com.jerotes.jerotesvillage.goal.SerponAttackTargetPlayerGoal;
import com.jerotes.jerotesvillage.init.JerotesVillageItems;
import net.minecraft.util.RandomSource;
import net.minecraft.world.DifficultyInstance;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.ai.attributes.AttributeSupplier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.Level;

public class SerponColonialSoldierEntity extends SerponEntity {

	public SerponColonialSoldierEntity(EntityType<? extends SerponColonialSoldierEntity> entityType, Level level) {
		super(entityType, level);
		this.xpReward = 20;
	}

	@Override
	protected void populateDefaultEquipmentSlots(RandomSource randomSource, DifficultyInstance difficultyInstance) {
		this.maybeWearArmor(EquipmentSlot.HEAD, new ItemStack(JerotesVillageItems.SERPON_HELMET.get()), randomSource);
		this.maybeWearArmor(EquipmentSlot.CHEST, new ItemStack(JerotesVillageItems.SERPON_CHESTPLATE.get()), randomSource);
		this.maybeWearArmor(EquipmentSlot.LEGS, new ItemStack(JerotesVillageItems.SERPON_LEGGINGS.get()), randomSource);
		this.maybeWearArmor(EquipmentSlot.FEET, new ItemStack(JerotesVillageItems.SERPON_BOOTS.get()), randomSource);
	}

	private void maybeWearArmor(EquipmentSlot equipmentSlot, ItemStack itemStack, RandomSource randomSource) {
		if (randomSource.nextFloat() < 0.5f) {
			this.setItemSlot(equipmentSlot, itemStack);
		}
	}

	public static AttributeSupplier.Builder createAttributes() {
		AttributeSupplier.Builder builder = Mob.createMobAttributes();
		builder = builder.add(Attributes.MOVEMENT_SPEED, 0.35);
		builder = builder.add(Attributes.FLYING_SPEED, 0.5);
		builder = builder.add(Attributes.MAX_HEALTH, 40);
		builder = builder.add(Attributes.ATTACK_KNOCKBACK, 0.1);
		builder = builder.add(Attributes.ATTACK_DAMAGE, 5);
		builder = builder.add(Attributes.FOLLOW_RANGE, 48);
		return builder;
	}

	@Override
	protected void registerGoals() {
		super.registerGoals();
		this.targetSelector.addGoal(2, new SerponAttackTargetPlayerGoal<Player>(this, Player.class, true));
	}

	@Override
	public ItemStack createSpawnWeapon(float weaponRandom) {
		if (weaponRandom > 0.75) {
			return new ItemStack(JerotesVillageItems.SERPON_SWORD.get());
		}
		if (weaponRandom > 0.6) {
			return new ItemStack(JerotesVillageItems.SERPON_BLOWPIPE.get());
		}
		if (weaponRandom > 0.3) {
			return new ItemStack(JerotesVillageItems.SERPON_DAGGER.get());
		}
		return new ItemStack(JerotesVillageItems.SERPON_JAVELIN.get());
	}

	@Override
	public ItemStack createSpawnOffhand(float offhandRandom) {
		if (offhandRandom > 0.4) {
			return new ItemStack(Items.AIR);
		}
		return new ItemStack(JerotesVillageItems.SERPON_SPIKED_SHIELD.get());
	}
}

