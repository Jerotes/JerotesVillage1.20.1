package com.jerotes.jerotesvillage.item;

import com.jerotes.jerotesvillage.init.JerotesVillageItems;
import net.minecraft.ChatFormatting;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.MutableComponent;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.item.*;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.level.Level;

import java.util.List;

public abstract class MerorBountyHunterArmor extends ArmorItem {
	public MerorBountyHunterArmor(Type type, Properties properties) {
		super(new ArmorMaterial() {
			@Override
			public int getDurabilityForType(Type type) {
				return new int[]{13, 15, 16, 11}[type.getSlot().getIndex()] * 90;
			}

			@Override
			public int getDefenseForType(Type type) {
				return new int[]{3, 6, 8, 3}[type.getSlot().getIndex()];
			}

			@Override
			public int getEnchantmentValue() {
				return 25;
			}

			@Override
			public SoundEvent getEquipSound() {
				return SoundEvents.ARMOR_EQUIP_GENERIC;
			}

			@Override
			public Ingredient getRepairIngredient() {
				return Ingredient.of(new ItemStack(JerotesVillageItems.MEROR_METAL_INGOT.get()));
			}

			@Override
			public String getName() {
				return "meror_bounty_hunter";
			}

			@Override
			public float getToughness() {
				return 2f;
			}

			@Override
			public float getKnockbackResistance() {
				return 0f;
			}
		}, type, properties.fireResistant().rarity(Rarity.RARE));
	}

	public static class Helmet extends MerorBountyHunterArmor {
		public Helmet() {
			super(Type.HELMET, new Properties());
		}

		@Override
		public void appendHoverText(ItemStack itemStack, Level level, List<Component> list, TooltipFlag tooltipFlag) {
			super.appendHoverText(itemStack, level, list, tooltipFlag);
			list.add(this.getDisplayName_0().withStyle(ChatFormatting.GRAY));
			list.add(this.getDisplayName_1().withStyle(ChatFormatting.GRAY));
		}

		public MutableComponent getDisplayName_0() {
			return Component.translatable(this.getDescriptionId() + ".desc_0");
		}
		public MutableComponent getDisplayName_1() {
			return Component.translatable(this.getDescriptionId() + ".desc_1");
		}

		@Override
		public String getArmorTexture(ItemStack stack, Entity entity, EquipmentSlot slot, String type) {
			return "jerotesvillage:textures/models/armor/meror_bounty_hunter_layer_1.png";
		}
	}

	public static class Chestplate extends MerorBountyHunterArmor {
		public Chestplate() {
			super(Type.CHESTPLATE, new Properties());
		}

		@Override
		public String getArmorTexture(ItemStack stack, Entity entity, EquipmentSlot slot, String type) {
			return "jerotesvillage:textures/models/armor/meror_bounty_hunter_layer_1.png";
		}
	}

	public static class Leggings extends MerorBountyHunterArmor {
		public Leggings() {
			super(Type.LEGGINGS, new Properties());
		}

		@Override
		public String getArmorTexture(ItemStack stack, Entity entity, EquipmentSlot slot, String type) {
			return "jerotesvillage:textures/models/armor/meror_bounty_hunter_layer_2.png";
		}
	}

	public static class Boots extends MerorBountyHunterArmor {
		public Boots() {
			super(Type.BOOTS, new Properties());
		}

		@Override
		public String getArmorTexture(ItemStack stack, Entity entity, EquipmentSlot slot, String type) {
			return "jerotesvillage:textures/models/armor/meror_bounty_hunter_layer_1.png";
		}
	}
}
