package com.jerotes.jerotesvillage.item;

import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.ArmorItem;
import net.minecraft.world.item.ArmorMaterial;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.crafting.Ingredient;

public abstract class GlassesArmor extends ArmorItem {
	public GlassesArmor(ArmorMaterial armorMaterial, Type type, Properties properties) {
		super(armorMaterial, type, properties);
	}

	public static class Myopia extends GlassesArmor {
		public Myopia() {
			super(new ArmorMaterial() {
				@Override
				public int getDurabilityForType(Type type) {
					return new int[]{13, 15, 16, 11}[type.getSlot().getIndex()] * 40;
				}

				@Override
				public int getDefenseForType(Type type) {
					return new int[]{1, 2, 2, 1}[type.getSlot().getIndex()];
				}

				@Override
				public int getEnchantmentValue() {
					return 5;
				}

				@Override
				public SoundEvent getEquipSound() {
					return SoundEvents.ARMOR_EQUIP_LEATHER;
				}

				@Override
				public Ingredient getRepairIngredient() {
					return Ingredient.of(new ItemStack(Items.GLASS));
				}

				@Override
				public String getName() {
					return "myopia_glasses";
				}

				@Override
				public float getToughness() {
					return 0f;
				}

				@Override
				public float getKnockbackResistance() {
					return 0f;
				}
			}, Type.HELMET, new Properties());
		}

		@Override
		public String getArmorTexture(ItemStack stack, Entity entity, EquipmentSlot slot, String type) {
			return "jerotesvillage:textures/models/armor/myopia_glasses_layer_1.png";
		}
	}

	public static class Presbyopic extends GlassesArmor {
		public Presbyopic() {
			super(new ArmorMaterial() {
				@Override
				public int getDurabilityForType(Type type) {
					return new int[]{13, 15, 16, 11}[type.getSlot().getIndex()] * 40;
				}

				@Override
				public int getDefenseForType(Type type) {
					return new int[]{1, 2, 2, 1}[type.getSlot().getIndex()];
				}

				@Override
				public int getEnchantmentValue() {
					return 5;
				}

				@Override
				public SoundEvent getEquipSound() {
					return SoundEvents.ARMOR_EQUIP_LEATHER;
				}

				@Override
				public Ingredient getRepairIngredient() {
					return Ingredient.of(new ItemStack(Items.GLASS));
				}

				@Override
				public String getName() {
					return "presbyopic_glasses";
				}

				@Override
				public float getToughness() {
					return 0f;
				}

				@Override
				public float getKnockbackResistance() {
					return 0f;
				}
			}, Type.HELMET, new Properties());
		}

		@Override
		public String getArmorTexture(ItemStack stack, Entity entity, EquipmentSlot slot, String type) {
			return "jerotesvillage:textures/models/armor/presbyopic_glasses_layer_1.png";
		}
	}

	public static class Sun extends GlassesArmor {
		public Sun() {
			super(new ArmorMaterial() {
				@Override
				public int getDurabilityForType(Type type) {
					return new int[]{13, 15, 16, 11}[type.getSlot().getIndex()] * 40;
				}

				@Override
				public int getDefenseForType(Type type) {
					return new int[]{1, 2, 2, 1}[type.getSlot().getIndex()];
				}

				@Override
				public int getEnchantmentValue() {
					return 5;
				}

				@Override
				public SoundEvent getEquipSound() {
					return SoundEvents.ARMOR_EQUIP_LEATHER;
				}

				@Override
				public Ingredient getRepairIngredient() {
					return Ingredient.of(new ItemStack(Items.GLASS));
				}

				@Override
				public String getName() {
					return "sunglasses";
				}

				@Override
				public float getToughness() {
					return 0f;
				}

				@Override
				public float getKnockbackResistance() {
					return 0f;
				}
			}, Type.HELMET, new Properties());
		}

		@Override
		public String getArmorTexture(ItemStack stack, Entity entity, EquipmentSlot slot, String type) {
			return "jerotesvillage:textures/models/armor/sunglasses_layer_1.png";
		}
	}
}
