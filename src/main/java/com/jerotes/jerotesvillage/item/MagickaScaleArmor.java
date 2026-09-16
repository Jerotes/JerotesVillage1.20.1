package com.jerotes.jerotesvillage.item;

import com.jerotes.jerotes.item.Interface.ItemModelArmor;
import com.jerotes.jerotesvillage.client.model.Modelmagicka_scale_armor;
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

public abstract class MagickaScaleArmor extends ArmorItem implements ItemModelArmor {
	public MagickaScaleArmor(Type type, Properties properties) {
		super(new ArmorMaterial() {
			@Override
			public int getDurabilityForType(Type type) {
				return new int[]{13, 15, 16, 11}[type.getSlot().getIndex()] * 95;
			}

			@Override
			public int getDefenseForType(Type type) {
				return new int[]{6, 7, 9, 5}[type.getSlot().getIndex()];
			}

			@Override
			public int getEnchantmentValue() {
				return 28;
			}

			@Override
			public SoundEvent getEquipSound() {
				return SoundEvents.ARMOR_EQUIP_CHAIN;
			}

			@Override
			public Ingredient getRepairIngredient() {
				return Ingredient.of(new ItemStack(JerotesVillageItems.FROST_YETI_HAIR.get()),new ItemStack(JerotesVillageItems.LIGHTNING_WORM_CHITIN.get()), new ItemStack(JerotesVillageItems.FIRE_SECRETOR_ADHESIVE.get()), new ItemStack(JerotesVillageItems.MARINE_REPTILES_FINE_SCALE.get()));
			}

			@Override
			public String getName() {
				return "magicka_scale_armor";
			}

			@Override
			public float getToughness() {
				return 3.5f;
			}

			@Override
			public float getKnockbackResistance() {
				return 0.18f;
			}
		}, type, properties.fireResistant().rarity(Rarity.RARE));
	}

	@Override
	public void initializeClient(Consumer<IClientItemExtensions> consumer) {
		super.initializeClient(consumer);
		consumer.accept(new IClientItemExtensions() {
			public HumanoidModel<?> getHumanoidArmorModel(LivingEntity livingEntity, ItemStack itemStack, EquipmentSlot equipmentSlot, HumanoidModel<?> original) {
				Modelmagicka_scale_armor model = new Modelmagicka_scale_armor(Minecraft.getInstance().getEntityModels().bakeLayer(
						Modelmagicka_scale_armor.LAYER_LOCATION)).getArmor(livingEntity);
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

	public static class Helmet extends MagickaScaleArmor {
		public Helmet() {
			super(Type.HELMET, new Properties());
		}
		
		@Override
		public void appendHoverText(ItemStack itemStack, Level level, List<Component> list, TooltipFlag tooltipFlag) {
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
			return "jerotesvillage:textures/models/armor/magicka_scale_helmet.png";
		}
	}

	public static class Chestplate extends MagickaScaleArmor {
		public Chestplate() {
			super(Type.CHESTPLATE, new Properties());
		}

		@Override
		public void appendHoverText(ItemStack itemStack, Level level, List<Component> list, TooltipFlag tooltipFlag) {
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
			return "jerotesvillage:textures/models/armor/magicka_scale_chestplate.png";
		}
	}

	public static class Leggings extends MagickaScaleArmor {
		public Leggings() {
			super(Type.LEGGINGS, new Properties());
		}

		@Override
		public void appendHoverText(ItemStack itemStack, Level level, List<Component> list, TooltipFlag tooltipFlag) {
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
			return "jerotesvillage:textures/models/armor/magicka_scale_leggings.png";
		}
	}

	public static class Boots extends MagickaScaleArmor {
		public Boots() {
			super(Type.BOOTS, new Properties());
		}

		@Override
		public void appendHoverText(ItemStack itemStack, Level level, List<Component> list, TooltipFlag tooltipFlag) {
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
			return "jerotesvillage:textures/models/armor/magicka_scale_boots.png";
		}

		@Override
		public boolean canWalkOnPowderedSnow(ItemStack itemstack, LivingEntity entity) {
			return true;
		}
	}
}
