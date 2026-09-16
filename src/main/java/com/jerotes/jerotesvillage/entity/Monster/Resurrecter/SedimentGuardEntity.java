package com.jerotes.jerotesvillage.entity.Monster.Resurrecter;

import com.jerotes.jerotesvillage.init.JerotesVillageItems;
import net.minecraft.util.RandomSource;
import net.minecraft.world.DifficultyInstance;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.ai.attributes.AttributeSupplier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.Level;

public class SedimentGuardEntity extends ResurrecterEntity {

	public SedimentGuardEntity(EntityType type, Level world) {
		super(type, world);
		xpReward = 10;
	}

	@Override
	protected void populateDefaultEquipmentSlots(RandomSource randomSource, DifficultyInstance difficultyInstance) {
		this.maybeWearArmor(EquipmentSlot.HEAD, new ItemStack(JerotesVillageItems.ANCIENT_SECOND_ROUNDER_HELMET.get()), randomSource);
		this.maybeWearArmor(EquipmentSlot.CHEST, new ItemStack(JerotesVillageItems.ANCIENT_SECOND_ROUNDER_CHESTPLATE.get()), randomSource);
		this.maybeWearArmor(EquipmentSlot.LEGS, new ItemStack(JerotesVillageItems.ANCIENT_SECOND_ROUNDER_LEGGINGS.get()), randomSource);
		this.maybeWearArmor(EquipmentSlot.FEET, new ItemStack(JerotesVillageItems.ANCIENT_SECOND_ROUNDER_BOOTS.get()), randomSource);
	}

	private void maybeWearArmor(EquipmentSlot equipmentSlot, ItemStack itemStack, RandomSource randomSource) {
		this.setItemSlot(equipmentSlot, itemStack);
	}

	public static AttributeSupplier.Builder createAttributes() {
		AttributeSupplier.Builder builder = Mob.createMobAttributes();
		builder = builder.add(Attributes.MOVEMENT_SPEED, 0.27);
		builder = builder.add(Attributes.MAX_HEALTH, 30);
		builder = builder.add(Attributes.ARMOR, 0);
		builder = builder.add(Attributes.ATTACK_DAMAGE, 5);
		builder = builder.add(Attributes.FOLLOW_RANGE, 35);
		return builder;
	}


	@Override
	public ItemStack createSpawnWeapon(float weaponRandom) {
		if (weaponRandom > 0.85) {
			return new ItemStack(JerotesVillageItems.ANCIENT_SECOND_ROUNDER_MACE.get());
		}
		if (weaponRandom > 0.4) {
			return new ItemStack(JerotesVillageItems.ANCIENT_SECOND_ROUNDER_SWORD.get());
		}
		if (weaponRandom > 0.2) {
			return new ItemStack(JerotesVillageItems.ANCIENT_SECOND_ROUNDER_WAR_AXE.get());
		}
		if (weaponRandom > 0.15) {
			return new ItemStack(JerotesVillageItems.ANCIENT_SECOND_ROUNDER_WARSPEAR.get());
		}
		return new ItemStack(JerotesVillageItems.ANCIENT_SECOND_ROUNDER_BOW.get());
	}

	@Override
	public ItemStack createSpawnOffhand(float offhandRandom) {
		if (offhandRandom > 0.85) {
			return new ItemStack(JerotesVillageItems.ANCIENT_SECOND_ROUNDER_SHIELD.get());
		}
		return new ItemStack(Items.AIR);
	}
}
