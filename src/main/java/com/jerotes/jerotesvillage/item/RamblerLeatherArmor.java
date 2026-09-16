package com.jerotes.jerotesvillage.item;

import com.jerotes.jerotesvillage.init.JerotesVillageItems;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.ArmorItem;
import net.minecraft.world.item.ArmorMaterial;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.Ingredient;

public abstract class RamblerLeatherArmor extends ArmorItem {
	public RamblerLeatherArmor(ArmorItem.Type type, Properties properties) {
		super(new ArmorMaterial() {
			@Override
			public int getDurabilityForType(ArmorItem.Type type) {
				return new int[]{13, 15, 16, 11}[type.getSlot().getIndex()] * 25;
			}

			@Override
			public int getDefenseForType(ArmorItem.Type type) {
				return new int[]{3, 5, 7, 3}[type.getSlot().getIndex()];
			}

			@Override
			public int getEnchantmentValue() {
				return 15;
			}

			@Override
			public SoundEvent getEquipSound() {
				return SoundEvents.ARMOR_EQUIP_LEATHER;
			}

			@Override
			public Ingredient getRepairIngredient() {
				return Ingredient.of(new ItemStack(JerotesVillageItems.RAMBLER_LEATHER.get()));
			}

			@Override
			public String getName() {
				return "rambler_leather_armor";
			}

			@Override
			public float getToughness() {
				return 3f;
			}

			@Override
			public float getKnockbackResistance() {
				return 0.20f;
			}
		}, type, properties);
	}

	public static class Helmet extends RamblerLeatherArmor {
		public Helmet() {
			super(ArmorItem.Type.HELMET, new Properties());
		}

		@Override
		public String getArmorTexture(ItemStack stack, Entity entity, EquipmentSlot slot, String type) {
			return "jerotesvillage:textures/models/armor/rambler_leather_layer_1.png";
		}
	}

	public static class Chestplate extends RamblerLeatherArmor {
		public Chestplate() {
			super(ArmorItem.Type.CHESTPLATE, new Properties());
		}

		@Override
		public String getArmorTexture(ItemStack stack, Entity entity, EquipmentSlot slot, String type) {
			return "jerotesvillage:textures/models/armor/rambler_leather_layer_1.png";
		}
	}

	public static class Leggings extends RamblerLeatherArmor {
		public Leggings() {
			super(ArmorItem.Type.LEGGINGS, new Properties());
		}

		@Override
		public String getArmorTexture(ItemStack stack, Entity entity, EquipmentSlot slot, String type) {
			return "jerotesvillage:textures/models/armor/rambler_leather_layer_2.png";
		}
	}

	public static class Boots extends RamblerLeatherArmor {
		public Boots() {
			super(ArmorItem.Type.BOOTS, new Properties());
		}

		@Override
		public String getArmorTexture(ItemStack stack, Entity entity, EquipmentSlot slot, String type) {
			return "jerotesvillage:textures/models/armor/rambler_leather_layer_1.png";
		}

		@Override
		public boolean canWalkOnPowderedSnow(ItemStack itemstack, LivingEntity entity) {
			return true;
		}
	}
}
