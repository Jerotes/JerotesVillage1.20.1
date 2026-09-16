package com.jerotes.jerotesvillage.item;

import com.jerotes.jerotes.item.Interface.ItemModelArmor;
import com.jerotes.jerotesvillage.client.model.Modelwoodland_heart_guardian_mask;
import net.minecraft.client.Minecraft;
import net.minecraft.client.model.HumanoidModel;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.*;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraftforge.client.extensions.common.IClientItemExtensions;

import java.util.function.Consumer;


public abstract class WoodlandHeartGuardianMask extends ArmorItem implements ItemModelArmor {
	public WoodlandHeartGuardianMask(Type type, Properties properties) {
		super(new ArmorMaterial() {
			@Override
			public int getDurabilityForType(Type type) {
				return new int[]{13, 15, 16, 11}[type.getSlot().getIndex()] * 25;
			}

			@Override
			public int getDefenseForType(Type type) {
				return new int[]{3, 5, 6, 3}[type.getSlot().getIndex()];
			}

			@Override
			public int getEnchantmentValue() {
				return 45;
			}

			@Override
			public SoundEvent getEquipSound() {
				return SoundEvents.ARMOR_EQUIP_LEATHER;
			}

			@Override
			public Ingredient getRepairIngredient() {
				return Ingredient.of(new ItemStack(Items.BONE));
			}

			@Override
			public String getName() {
				return "woodland_heart_guardian_mask";
			}

			@Override
			public float getToughness() {
				return 3f;
			}

			@Override
			public float getKnockbackResistance() {
				return 0.15f;
			}
		}, type, properties);
	}

	@Override
	public void initializeClient(Consumer<IClientItemExtensions> consumer) {
		super.initializeClient(consumer);
		consumer.accept(new IClientItemExtensions() {
			public HumanoidModel<?> getHumanoidArmorModel(LivingEntity livingEntity, ItemStack itemStack, EquipmentSlot equipmentSlot, HumanoidModel<?> original) {
				Modelwoodland_heart_guardian_mask model = new Modelwoodland_heart_guardian_mask(Minecraft.getInstance().getEntityModels().bakeLayer(
						Modelwoodland_heart_guardian_mask.LAYER_LOCATION)).getArmor(livingEntity);
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

	public static class Helmet extends WoodlandHeartGuardianMask {
		public Helmet() {
			super(Type.HELMET, new Properties().fireResistant().rarity(Rarity.UNCOMMON));
		}

		@Override
		public String getArmorTexture(ItemStack stack, Entity entity, EquipmentSlot slot, String type) {
			return "jerotesvillage:textures/models/armor/woodland_heart_guardian_mask.png";
		}
	}
}
