package com.jerotes.jerotesvillage.item;

import com.jerotes.jerotesvillage.client.model.Modelexplorer_armor;
import net.minecraft.client.Minecraft;
import net.minecraft.client.model.HumanoidModel;
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
import net.minecraftforge.client.extensions.common.IClientItemExtensions;

import java.util.function.Consumer;

public abstract class ExplorerArmor extends ArmorItem {
	public ExplorerArmor(Type type, Properties properties) {
		super(new ArmorMaterial() {
			@Override
			public int getDurabilityForType(Type type) {
				return new int[]{13, 15, 16, 11}[type.getSlot().getIndex()] * 22;
			}

			@Override
			public int getDefenseForType(Type type) {
				return new int[]{3, 5, 7, 2}[type.getSlot().getIndex()];
			}

			@Override
			public int getEnchantmentValue() {
				return 15;
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
				return "explorer_armor";
			}

			@Override
			public float getToughness() {
				return 1f;
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
				Modelexplorer_armor model = new Modelexplorer_armor(Minecraft.getInstance().getEntityModels().bakeLayer(
						Modelexplorer_armor.LAYER_LOCATION)).getArmor(livingEntity);
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

	public static class Helmet extends ExplorerArmor {
		public Helmet() {
			super(Type.HELMET, new Properties());
		}

		@Override
		public String getArmorTexture(ItemStack stack, Entity entity, EquipmentSlot slot, String type) {
			return "jerotesvillage:textures/models/armor/explorer_helmet.png";
		}
	}

	public static class Chestplate extends ExplorerArmor {
		public Chestplate() {
			super(Type.CHESTPLATE, new Properties());
		}

		@Override
		public String getArmorTexture(ItemStack stack, Entity entity, EquipmentSlot slot, String type) {
			return "jerotesvillage:textures/models/armor/explorer_chestplate.png";
		}
	}

	public static class Leggings extends ExplorerArmor {
		public Leggings() {
			super(Type.LEGGINGS, new Properties());
		}

		@Override
		public String getArmorTexture(ItemStack stack, Entity entity, EquipmentSlot slot, String type) {
			return "jerotesvillage:textures/models/armor/explorer_leggings.png";
		}
	}

	public static class Boots extends ExplorerArmor {
		public Boots() {
			super(Type.BOOTS, new Properties());
		}

		@Override
		public String getArmorTexture(ItemStack stack, Entity entity, EquipmentSlot slot, String type) {
			return "jerotesvillage:textures/models/armor/explorer_boots.png";
		}

		@Override
		public boolean canWalkOnPowderedSnow(ItemStack itemstack, LivingEntity entity) {
			return true;
		}
	}
}
