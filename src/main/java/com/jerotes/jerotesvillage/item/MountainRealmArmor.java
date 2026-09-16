package com.jerotes.jerotesvillage.item;

import com.jerotes.jerotes.item.Interface.ItemModelArmor;
import com.jerotes.jerotes.util.Main;
import com.jerotes.jerotesvillage.client.model.Modelmountain_realm_armor;
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

public abstract class MountainRealmArmor extends ArmorItem implements ItemModelArmor {
	public MountainRealmArmor(Type type, Properties properties) {
		super(new ArmorMaterial() {
			@Override
			public int getDurabilityForType(Type type) {
				return new int[]{13, 15, 16, 11}[type.getSlot().getIndex()] * 45;
			}

			@Override
			public int getDefenseForType(Type type) {
				return new int[]{6, 8, 9, 5}[type.getSlot().getIndex()];
			}

			@Override
			public int getEnchantmentValue() {
				return 22;
			}

			@Override
			public SoundEvent getEquipSound() {
				return SoundEvents.ARMOR_EQUIP_CHAIN;
			}

			@Override
			public Ingredient getRepairIngredient() {
				return Ingredient.of(new ItemStack(Items.GOLD_INGOT));
			}

			@Override
			public String getName() {
				return "mountain_realm_armor";
			}

			@Override
			public float getToughness() {
				return 3.5f;
			}

			@Override
			public float getKnockbackResistance() {
				return 0.225f;
			}
		}, type, properties.rarity(Rarity.EPIC));
	}

	@Override
	public void initializeClient(Consumer<IClientItemExtensions> consumer) {
		super.initializeClient(consumer);
		consumer.accept(new IClientItemExtensions() {
			public HumanoidModel<?> getHumanoidArmorModel(LivingEntity livingEntity, ItemStack itemStack, EquipmentSlot equipmentSlot, HumanoidModel<?> original) {
				Modelmountain_realm_armor model = new Modelmountain_realm_armor(Minecraft.getInstance().getEntityModels().bakeLayer(
						Modelmountain_realm_armor.LAYER_LOCATION)).getArmor(livingEntity);
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

	@Override
	public boolean makesPiglinsNeutral(ItemStack itemstack, LivingEntity entity) {
		return true;
	}

	public static class Helmet extends MountainRealmArmor {
		public Helmet() {
			super(Type.HELMET, new Properties());
		}

		@Override
		public String getArmorTexture(ItemStack stack, Entity entity, EquipmentSlot slot, String type) {
			return "jerotesvillage:textures/models/armor/mountain_realm_helmet.png";
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
	}

	public static class Chestplate extends MountainRealmArmor {
		public Chestplate() {
			super(Type.CHESTPLATE, new Properties());
		}

		@Override
		public String getArmorTexture(ItemStack stack, Entity entity, EquipmentSlot slot, String type) {
			if (Main.getJerotesPersistentData(entity).getDouble("jerotesvillage_mountain_realm_flag_spear") > 0
					|| entity instanceof LivingEntity living && (living.getMainHandItem().getItem() instanceof MountainRealmFlagSpear.MountainRealmFlagSpearOfJavelin
					|| living.getOffhandItem().getItem() instanceof MountainRealmFlagSpear.MountainRealmFlagSpearOfJavelin)
					|| entity instanceof LivingEntity livings && (livings.getMainHandItem().getItem() instanceof MountainRealmFlagSpear.MountainRealmFlagSpearOfSpear
					|| livings.getOffhandItem().getItem() instanceof MountainRealmFlagSpear.MountainRealmFlagSpearOfSpear)) {
				return "jerotesvillage:textures/models/armor/mountain_realm_chestplate_has_flag.png";
			}
			return "jerotesvillage:textures/models/armor/mountain_realm_chestplate.png";
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
	}

	public static class Leggings extends MountainRealmArmor {
		public Leggings() {
			super(Type.LEGGINGS, new Properties());
		}

		@Override
		public String getArmorTexture(ItemStack stack, Entity entity, EquipmentSlot slot, String type) {
			return "jerotesvillage:textures/models/armor/mountain_realm_leggings.png";
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
	}

	public static class Boots extends MountainRealmArmor {
		public Boots() {
			super(Type.BOOTS, new Properties());
		}

		@Override
		public String getArmorTexture(ItemStack stack, Entity entity, EquipmentSlot slot, String type) {
			return "jerotesvillage:textures/models/armor/mountain_realm_boots.png";
		}

		@Override
		public boolean canWalkOnPowderedSnow(ItemStack itemstack, LivingEntity entity) {
			return true;
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
	}
}
