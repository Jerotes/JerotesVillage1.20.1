package com.jerotes.jerotesvillage.entity.Humanoid.Qoaiku;

import com.jerotes.jerotesvillage.init.JerotesVillageItems;
import net.minecraft.util.RandomSource;
import net.minecraft.world.DifficultyInstance;
import net.minecraft.world.entity.*;
import net.minecraft.world.entity.ai.attributes.AttributeSupplier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.item.*;
import net.minecraft.world.level.Level;


public class QoaikuSeekerEntity extends QoaikuEntity {

	public QoaikuSeekerEntity(EntityType<? extends QoaikuSeekerEntity> entityType, Level level) {
		super(entityType, level);
		this.xpReward = 20;
	}

	@Override
	protected void populateDefaultEquipmentSlots(RandomSource randomSource, DifficultyInstance difficultyInstance) {
		this.maybeWearArmor(EquipmentSlot.HEAD, new ItemStack(JerotesVillageItems.QOAIKU_HELMET.get()), randomSource);
		this.maybeWearArmor(EquipmentSlot.CHEST, new ItemStack(JerotesVillageItems.QOAIKU_CHESTPLATE.get()), randomSource);
		this.maybeWearArmor(EquipmentSlot.LEGS, new ItemStack(JerotesVillageItems.QOAIKU_LEGGINGS.get()), randomSource);
		this.maybeWearArmor(EquipmentSlot.FEET, new ItemStack(JerotesVillageItems.QOAIKU_BOOTS.get()), randomSource);
	}

	private void maybeWearArmor(EquipmentSlot equipmentSlot, ItemStack itemStack, RandomSource randomSource) {
		if (randomSource.nextFloat() < 0.9f) {
			this.setItemSlot(equipmentSlot, itemStack);
		}
	}

	public static AttributeSupplier.Builder createAttributes() {
		AttributeSupplier.Builder builder = Mob.createMobAttributes();
		builder = builder.add(Attributes.MOVEMENT_SPEED, 0.32);
		builder = builder.add(Attributes.MAX_HEALTH, 30);
		builder = builder.add(Attributes.ATTACK_KNOCKBACK, 0);
		builder = builder.add(Attributes.ATTACK_DAMAGE, 4);
		builder = builder.add(Attributes.FOLLOW_RANGE, 48);
		return builder;
	}

	@Override
	public ItemStack createSpawnWeapon(float weaponRandom) {
		if (weaponRandom > 0.85) {
			return new ItemStack(JerotesVillageItems.QOAIKU_SPIKE.get());
		}
		if (weaponRandom > 0.5) {
			return new ItemStack(JerotesVillageItems.QOAIKU_KNIFE.get());
		}
		if (weaponRandom > 0.25) {
			return new ItemStack(JerotesVillageItems.QOAIKU_DAGGER.get());
		}
		return new ItemStack(JerotesVillageItems.QOAIKU_TRIDENT.get());
	}

	@Override
	public ItemStack createSpawnOffhand(float offhandRandom) {
		if (offhandRandom > 0.3) {
			return new ItemStack(Items.AIR);
		}
		return new ItemStack(JerotesVillageItems.QOAIKU_ROUND_SHIELD.get());
	}
}

