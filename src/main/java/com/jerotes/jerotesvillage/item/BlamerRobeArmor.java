package com.jerotes.jerotesvillage.item;

import com.jerotes.jerotes.item.Interface.ItemModelArmor;
import com.jerotes.jerotesvillage.client.model.Modelblamer_robe;
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
import net.minecraft.world.item.ArmorItem;
import net.minecraft.world.item.ArmorMaterial;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.level.Level;
import net.minecraftforge.client.extensions.common.IClientItemExtensions;

import java.util.List;
import java.util.function.Consumer;

public abstract class BlamerRobeArmor extends ArmorItem implements ItemModelArmor {
	public BlamerRobeArmor(Type type, Properties properties) {
		super(new ArmorMaterial() {
			@Override
			public int getDurabilityForType(Type type) {
				return new int[]{13, 15, 16, 11}[type.getSlot().getIndex()] * 35;
			}

			@Override
			public int getDefenseForType(Type type) {
				return new int[]{1, 2, 2, 1}[type.getSlot().getIndex()];
			}

			@Override
			public int getEnchantmentValue() {
				return 20;
			}

			@Override
			public SoundEvent getEquipSound() {
				return SoundEvents.ARMOR_EQUIP_LEATHER;
			}

			@Override
			public Ingredient getRepairIngredient() {
				return Ingredient.of(new ItemStack(JerotesVillageItems.BLAMER_SOUL.get()));
			}

			@Override
			public String getName() {
				return "blamer_robe";
			}

			@Override
			public float getToughness() {
				return 0f;
			}

			@Override
			public float getKnockbackResistance() {
				return 0f;
			}
		}, type, properties);
	}

	@Override
	public void initializeClient(Consumer<IClientItemExtensions> consumer) {
		super.initializeClient(consumer);
		consumer.accept(new IClientItemExtensions() {
			public HumanoidModel<?> getHumanoidArmorModel(LivingEntity livingEntity, ItemStack itemStack, EquipmentSlot equipmentSlot, HumanoidModel<?> original) {
				Modelblamer_robe model = new Modelblamer_robe(Minecraft.getInstance().getEntityModels().bakeLayer(
						Modelblamer_robe.LAYER_LOCATION)).getArmor(livingEntity);
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

	public static class Chestplate extends BlamerRobeArmor {
		public Chestplate() {
			super(Type.CHESTPLATE, new Properties());
		}

		@Override
		public void appendHoverText(ItemStack itemStack, Level level, List<Component> list, TooltipFlag tooltipFlag) {
			list.add(this.getDisplayName().withStyle(ChatFormatting.GRAY));
		}

		public MutableComponent getDisplayName() {
			return Component.translatable(this.getDescriptionId() + ".desc");
		}

		@Override
		public String getArmorTexture(ItemStack stack, Entity entity, EquipmentSlot slot, String type) {
			return "jerotesvillage:textures/models/armor/blamer_robe_layer_1.png";
		}
	}
}
