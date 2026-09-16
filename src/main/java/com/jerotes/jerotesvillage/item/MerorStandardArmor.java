package com.jerotes.jerotesvillage.item;

import com.jerotes.jerotes.item.Interface.ItemModelArmor;
import com.jerotes.jerotesvillage.client.model.Modelmeror_standard_armor;
import com.jerotes.jerotesvillage.init.JerotesVillageItems;
import net.minecraft.ChatFormatting;
import net.minecraft.client.Minecraft;
import net.minecraft.client.model.HumanoidModel;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.MutableComponent;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.*;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.level.Level;
import net.minecraftforge.client.extensions.common.IClientItemExtensions;

import java.util.List;
import java.util.function.Consumer;

public abstract class MerorStandardArmor extends ArmorItem implements ItemModelArmor {
	public MerorStandardArmor(Type type, Properties properties) {
		super(new ArmorMaterial() {
			@Override
			public int getDurabilityForType(Type type) {
				return new int[]{13, 15, 16, 11}[type.getSlot().getIndex()] * 340;
			}

			@Override
			public int getDefenseForType(Type type) {
				return new int[]{6, 8, 10, 6}[type.getSlot().getIndex()];
			}

			@Override
			public int getEnchantmentValue() {
				return 25;
			}

			@Override
			public SoundEvent getEquipSound() {
				return SoundEvents.ARMOR_EQUIP_IRON;
			}

			@Override
			public Ingredient getRepairIngredient() {
				return Ingredient.of(new ItemStack(JerotesVillageItems.REFINE_MEROR_METAL_INGOT.get()));
			}

			@Override
			public String getName() {
				return "meror_standard_armor";
			}

			@Override
			public float getToughness() {
				return 4f;
			}

			@Override
			public float getKnockbackResistance() {
				return 0.2f;
			}
		}, type, properties.fireResistant().rarity(Rarity.EPIC));
	}

	@Override
	public void initializeClient(Consumer<IClientItemExtensions> consumer) {
		super.initializeClient(consumer);
		consumer.accept(new IClientItemExtensions() {
			public HumanoidModel<?> getHumanoidArmorModel(LivingEntity livingEntity, ItemStack itemStack, EquipmentSlot equipmentSlot, HumanoidModel<?> original) {
				Modelmeror_standard_armor model = new Modelmeror_standard_armor(Minecraft.getInstance().getEntityModels().bakeLayer(
						Modelmeror_standard_armor.LAYER_LOCATION)).getArmor(livingEntity);
				model.hat.visible = equipmentSlot == EquipmentSlot.HEAD;
				model.body.visible = equipmentSlot == EquipmentSlot.CHEST;
				model.rightArm.visible = equipmentSlot == EquipmentSlot.CHEST;
				model.leftArm.visible = equipmentSlot == EquipmentSlot.CHEST;
				model.rightLeg.visible = equipmentSlot == EquipmentSlot.FEET;
				model.leftLeg.visible = equipmentSlot == EquipmentSlot.FEET;
				model.young = original.young;
				model.crouching = original.crouching;
				model.riding = original.riding;
				model.rightArmPose = original.rightArmPose;
				model.leftArmPose = original.leftArmPose;
				return model;
			}
		});
	}

	public static class Helmet extends MerorStandardArmor {
		public Helmet() {
			super(Type.HELMET, new Properties());
		}

		@Override
		public void appendHoverText(ItemStack itemStack, Level level, List<Component> list, TooltipFlag tooltipFlag) {
			list.add(this.getDisplayName_0().withStyle(ChatFormatting.GRAY));
			list.add(this.getDisplayName_1().withStyle(ChatFormatting.GRAY));
			list.add(this.getDisplayName_2().withStyle(ChatFormatting.GRAY));
			list.add(this.getDisplayName_3().withStyle(ChatFormatting.GRAY));
			list.add(this.getDisplayName_4().withStyle(ChatFormatting.GRAY));
		}

		public MutableComponent getDisplayName_0() {
			return Component.translatable(this.getDescriptionId() + ".desc_0");
		}
		public MutableComponent getDisplayName_1() {
			return Component.translatable(this.getDescriptionId() + ".desc_1");
		}
		public MutableComponent getDisplayName_2() {
			return Component.translatable(this.getDescriptionId() + ".desc_2");
		}
		public MutableComponent getDisplayName_3() {
			return Component.translatable(this.getDescriptionId() + ".desc_3");
		}
		public MutableComponent getDisplayName_4() {
			return Component.translatable(this.getDescriptionId() + ".desc_4");
		}

		@Override
		public String getArmorTexture(ItemStack stack, Entity entity, EquipmentSlot slot, String type) {
			if (entity.isShiftKeyDown()){
				return "jerotesvillage:textures/models/armor/null_layer_1.png";
			}
			return "jerotesvillage:textures/models/armor/meror_standard_helmet.png";
		}
	}

	public static class Chestplate extends MerorStandardArmor {
		public Chestplate() {
			super(Type.CHESTPLATE, new Properties());
		}

		@Override
		public void appendHoverText(ItemStack itemStack, Level level, List<Component> list, TooltipFlag tooltipFlag) {
			list.add(this.getDisplayName_0().withStyle(ChatFormatting.GRAY));
			list.add(this.getDisplayName_1().withStyle(ChatFormatting.GRAY));
			list.add(this.getDisplayName_2().withStyle(ChatFormatting.GRAY));
			list.add(this.getDisplayName_3().withStyle(ChatFormatting.GRAY));
			list.add(this.getDisplayName_4().withStyle(ChatFormatting.GRAY));
		}

		public MutableComponent getDisplayName_0() {
			return Component.translatable(this.getDescriptionId() + ".desc_0");
		}
		public MutableComponent getDisplayName_1() {
			return Component.translatable(this.getDescriptionId() + ".desc_1");
		}
		public MutableComponent getDisplayName_2() {
			return Component.translatable(this.getDescriptionId() + ".desc_2");
		}
		public MutableComponent getDisplayName_3() {
			return Component.translatable(this.getDescriptionId() + ".desc_3");
		}
		public MutableComponent getDisplayName_4() {
			return Component.translatable(this.getDescriptionId() + ".desc_4");
		}

		@Override
		public String getArmorTexture(ItemStack stack, Entity entity, EquipmentSlot slot, String type) {
			if (entity.isShiftKeyDown()){
				return "jerotesvillage:textures/models/armor/null_layer_1.png";
			}
			return "jerotesvillage:textures/models/armor/meror_standard_chestplate.png";
		}
	}

	public static class Leggings extends MerorStandardArmor {
		public Leggings() {
			super(Type.LEGGINGS, new Properties());
		}

		@Override
		public void appendHoverText(ItemStack itemStack, Level level, List<Component> list, TooltipFlag tooltipFlag) {
			list.add(this.getDisplayName_0().withStyle(ChatFormatting.GRAY));
			list.add(this.getDisplayName_1().withStyle(ChatFormatting.GRAY));
			list.add(this.getDisplayName_2().withStyle(ChatFormatting.GRAY));
			list.add(this.getDisplayName_3().withStyle(ChatFormatting.GRAY));
		}

		public MutableComponent getDisplayName_0() {
			return Component.translatable(this.getDescriptionId() + ".desc_0");
		}
		public MutableComponent getDisplayName_1() {
			return Component.translatable(this.getDescriptionId() + ".desc_1");
		}
		public MutableComponent getDisplayName_2() {
			return Component.translatable(this.getDescriptionId() + ".desc_2");
		}
		public MutableComponent getDisplayName_3() {
			return Component.translatable(this.getDescriptionId() + ".desc_3");
		}

		@Override
		public String getArmorTexture(ItemStack stack, Entity entity, EquipmentSlot slot, String type) {
			if (entity.isShiftKeyDown()){
				return "jerotesvillage:textures/models/armor/null_layer_2.png";
			}
			return "jerotesvillage:textures/models/armor/meror_standard_leggings.png";
		}
	}

	public static class Boots extends MerorStandardArmor {
		public Boots() {
			super(Type.BOOTS, new Properties());
		}

		@Override
		public void appendHoverText(ItemStack itemStack, Level level, List<Component> list, TooltipFlag tooltipFlag) {
			list.add(this.getDisplayName_0().withStyle(ChatFormatting.GRAY));
			list.add(this.getDisplayName_1().withStyle(ChatFormatting.GRAY));
			list.add(this.getDisplayName_2().withStyle(ChatFormatting.GRAY));
		}

		public MutableComponent getDisplayName_0() {
			return Component.translatable(this.getDescriptionId() + ".desc_0");
		}
		public MutableComponent getDisplayName_1() {
			return Component.translatable(this.getDescriptionId() + ".desc_1");
		}
		public MutableComponent getDisplayName_2() {
			return Component.translatable(this.getDescriptionId() + ".desc_2");
		}

		@Override
		public String getArmorTexture(ItemStack stack, Entity entity, EquipmentSlot slot, String type) {
			if (entity.isShiftKeyDown()){
				return "jerotesvillage:textures/models/armor/null_layer_1.png";
			}
			return "jerotesvillage:textures/models/armor/meror_standard_boots.png";
		}
	}
}
