package com.jerotes.jerotesvillage.entity.Humanoid.Meror;

import com.jerotes.jerotesvillage.goal.MerorPanicGoal;
import com.jerotes.jerotesvillage.init.JerotesVillageItems;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.util.RandomSource;
import net.minecraft.world.DifficultyInstance;
import net.minecraft.world.entity.*;
import net.minecraft.world.entity.ai.attributes.AttributeSupplier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.ShieldItem;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.ServerLevelAccessor;

import javax.annotation.Nullable;

public class MerorAdventurerEntity extends MerorEntity {

	public MerorAdventurerEntity(EntityType type, Level world) {
		super(type, world);
		xpReward = 20;
	}

	private void maybeWearArmor(EquipmentSlot equipmentSlot, ItemStack itemStack, RandomSource randomSource) {
		this.setItemSlot(equipmentSlot, itemStack);
	}

	public static AttributeSupplier.Builder createAttributes() {
		AttributeSupplier.Builder builder = Mob.createMobAttributes();
		builder = builder.add(Attributes.MOVEMENT_SPEED, 0.37);
		builder = builder.add(Attributes.MAX_HEALTH, 40);
		builder = builder.add(Attributes.ARMOR, 0);
		builder = builder.add(Attributes.ATTACK_DAMAGE, 4);
		builder = builder.add(Attributes.FOLLOW_RANGE, 48);
		builder = builder.add(Attributes.ATTACK_KNOCKBACK, 0);
		builder = builder.add(Attributes.KNOCKBACK_RESISTANCE, 0);
		return builder;
	}

	@Override
	protected void populateDefaultEquipmentSlots(RandomSource randomSource, DifficultyInstance difficultyInstance) {
		this.maybeWearArmor(EquipmentSlot.HEAD, new ItemStack(JerotesVillageItems.MEROR_METAL_HELMET.get()), randomSource);
		this.maybeWearArmor(EquipmentSlot.CHEST, new ItemStack(JerotesVillageItems.MEROR_METAL_CHESTPLATE.get()), randomSource);
		this.maybeWearArmor(EquipmentSlot.LEGS, new ItemStack(JerotesVillageItems.MEROR_METAL_LEGGINGS.get()), randomSource);
		this.maybeWearArmor(EquipmentSlot.FEET, new ItemStack(JerotesVillageItems.MEROR_METAL_BOOTS.get()), randomSource);
	}

	@Override
	protected void registerGoals() {
		super.registerGoals();
		this.getNavigation().getNodeEvaluator().setCanOpenDoors(true);
		this.goalSelector.addGoal(0, new MerorPanicGoal(this, 1.6));
	}

	@Nullable
	@Override
	public SpawnGroupData finalizeSpawn(ServerLevelAccessor serverLevelAccessor, DifficultyInstance difficultyInstance, MobSpawnType mobSpawnType, @Nullable SpawnGroupData spawnGroupData, @Nullable CompoundTag compoundTag) {
		this.setShieldLevel(3);
		return super.finalizeSpawn(serverLevelAccessor, difficultyInstance, mobSpawnType, spawnGroupData, compoundTag);
	}
	@Override
	public ItemStack createSpawnWeapon(float weaponRandom) {
		if (weaponRandom > 0.75) {
			return new ItemStack(JerotesVillageItems.MEROR_METAL_AXE.get());
		}
		if (weaponRandom > 0.65) {
			return new ItemStack(JerotesVillageItems.MEROR_METAL_BOW.get());
		}
		return new ItemStack(JerotesVillageItems.MEROR_METAL_SWORD.get());
	}

	@Override
	public ItemStack createSpawnOffhand(float offhandRandom, float weaponRandom) {
		if (weaponRandom > 0.75) {
			return new ItemStack(Items.MAP);
		}
		if (weaponRandom > 0.65) {
			return new ItemStack(JerotesVillageItems.MEROR_METAL_ARROW.get());
		}
		return new ItemStack(JerotesVillageItems.MEROR_METAL_SHIELD.get());
	}
}
