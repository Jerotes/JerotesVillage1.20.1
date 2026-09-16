package com.jerotes.jerotesvillage.item;

import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.*;
import net.minecraft.world.item.crafting.Ingredient;

public abstract class SlaverySupervisorArmor extends ArmorItem {
	public SlaverySupervisorArmor(Type type, Properties properties) {
		super(new ArmorMaterial() {
			@Override
			public int getDurabilityForType(Type type) {
				return new int[]{13, 15, 16, 11}[type.getSlot().getIndex()] * 40;
			}

			@Override
			public int getDefenseForType(Type type) {
				return new int[]{3, 6, 8, 3}[type.getSlot().getIndex()];
			}

			@Override
			public int getEnchantmentValue() {
				return 17;
			}

			@Override
			public SoundEvent getEquipSound() {
				return SoundEvents.ARMOR_EQUIP_NETHERITE;
			}

			@Override
			public Ingredient getRepairIngredient() {
				return Ingredient.of(new ItemStack(Items.NETHERITE_INGOT));
			}

			@Override
			public String getName() {
				return "slavery_supervisor_armor";
			}

			@Override
			public float getToughness() {
				return 3f;
			}

			@Override
			public float getKnockbackResistance() {
				return 0.1f;
			}
		}, type, properties.fireResistant().rarity(Rarity.UNCOMMON));
	}

	@Override
	public boolean makesPiglinsNeutral(ItemStack itemstack, LivingEntity entity) {
		return true;
	}

	public static class Helmet extends SlaverySupervisorArmor {
		public Helmet() {
			super(Type.HELMET, new Properties());
		}

		@Override
		public String getArmorTexture(ItemStack stack, Entity entity, EquipmentSlot slot, String type) {
			return "jerotesvillage:textures/models/armor/slavery_supervisor_layer_1.png";
		}
	}

	public static class Chestplate extends SlaverySupervisorArmor {
		public Chestplate() {
			super(Type.CHESTPLATE, new Properties());
		}

		@Override
		public String getArmorTexture(ItemStack stack, Entity entity, EquipmentSlot slot, String type) {
			return "jerotesvillage:textures/models/armor/slavery_supervisor_layer_1.png";
		}
	}

	public static class Leggings extends SlaverySupervisorArmor {
		public Leggings() {
			super(Type.LEGGINGS, new Properties());
		}

		@Override
		public String getArmorTexture(ItemStack stack, Entity entity, EquipmentSlot slot, String type) {
			return "jerotesvillage:textures/models/armor/slavery_supervisor_layer_2.png";
		}
	}

	public static class Boots extends SlaverySupervisorArmor {
		public Boots() {
			super(Type.BOOTS, new Properties());
		}

		@Override
		public String getArmorTexture(ItemStack stack, Entity entity, EquipmentSlot slot, String type) {
			return "jerotesvillage:textures/models/armor/slavery_supervisor_layer_1.png";
		}
	}
}
