package com.jerotes.jerotesvillage.item;

import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.item.ArmorItem;
import net.minecraft.world.item.ArmorMaterial;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.crafting.Ingredient;

public abstract class AncientSecondRounderArmor extends ArmorItem {
	public AncientSecondRounderArmor(Type type, Properties properties) {
		super(new ArmorMaterial() {
			@Override
			public int getDurabilityForType(Type type) {
				return new int[]{13, 15, 16, 11}[type.getSlot().getIndex()] * 18;
			}

			@Override
			public int getDefenseForType(Type type) {
				return new int[]{2, 5, 6, 2}[type.getSlot().getIndex()];
			}

			@Override
			public int getEnchantmentValue() {
				return 12;
			}

			@Override
			public SoundEvent getEquipSound() {
				return SoundEvents.ARMOR_EQUIP_IRON;
			}

			@Override
			public Ingredient getRepairIngredient() {
				return Ingredient.of(new ItemStack(Items.IRON_INGOT));
			}

			@Override
			public String getName() {
				return "ancient_second_rounder_armor";
			}

			@Override
			public float getToughness() {
				return 2f;
			}

			@Override
			public float getKnockbackResistance() {
				return 0f;
			}
		}, type, properties);
	}

	public static class Helmet extends AncientSecondRounderArmor {
		public Helmet() {
			super(Type.HELMET, new Properties());
		}

		@Override
		public String getArmorTexture(ItemStack stack, Entity entity, EquipmentSlot slot, String type) {
			if (stack.getDamageValue() > stack.getMaxDamage()/2) {
				return "jerotesvillage:textures/models/armor/ancient_second_rounder_old_layer_1.png";
			}
			return "jerotesvillage:textures/models/armor/ancient_second_rounder_layer_1.png";
		}
	}

	public static class Chestplate extends AncientSecondRounderArmor {
		public Chestplate() {
			super(Type.CHESTPLATE, new Properties());
		}

		@Override
		public String getArmorTexture(ItemStack stack, Entity entity, EquipmentSlot slot, String type) {
			if (stack.getDamageValue() > stack.getMaxDamage()/2) {
				return "jerotesvillage:textures/models/armor/ancient_second_rounder_old_layer_1.png";
			}
			return "jerotesvillage:textures/models/armor/ancient_second_rounder_layer_1.png";
		}
	}

	public static class Leggings extends AncientSecondRounderArmor {
		public Leggings() {
			super(Type.LEGGINGS, new Properties());
		}

		@Override
		public String getArmorTexture(ItemStack stack, Entity entity, EquipmentSlot slot, String type) {
			if (stack.getDamageValue() > stack.getMaxDamage()/2) {
				return "jerotesvillage:textures/models/armor/ancient_second_rounder_old_layer_2.png";
			}
			return "jerotesvillage:textures/models/armor/ancient_second_rounder_layer_2.png";
		}
	}

	public static class Boots extends AncientSecondRounderArmor {
		public Boots() {
			super(Type.BOOTS, new Properties());
		}

		@Override
		public String getArmorTexture(ItemStack stack, Entity entity, EquipmentSlot slot, String type) {
			if (stack.getDamageValue() > stack.getMaxDamage()/2) {
				return "jerotesvillage:textures/models/armor/ancient_second_rounder_old_layer_1.png";
			}
			return "jerotesvillage:textures/models/armor/ancient_second_rounder_layer_1.png";
		}
	}
}
