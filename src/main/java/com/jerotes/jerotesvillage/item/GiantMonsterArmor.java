package com.jerotes.jerotesvillage.item;

import com.jerotes.jerotes.item.Interface.ItemModelArmor;
import com.jerotes.jerotesvillage.client.model.Modelhorned_armor;
import com.jerotes.jerotesvillage.config.OtherMainConfig;
import com.jerotes.jerotesvillage.init.JerotesVillageEntityType;
import com.jerotes.jerotesvillage.init.JerotesVillageItems;
import net.minecraft.ChatFormatting;
import net.minecraft.client.Minecraft;
import net.minecraft.client.model.HumanoidModel;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.MutableComponent;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.monster.AbstractIllager;
import net.minecraft.world.item.ArmorItem;
import net.minecraft.world.item.ArmorMaterial;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.level.Level;
import net.minecraftforge.client.extensions.common.IClientItemExtensions;

import javax.annotation.Nullable;
import java.util.List;
import java.util.function.Consumer;

public abstract class GiantMonsterArmor extends ArmorItem {
	public GiantMonsterArmor(ArmorMaterial armorMaterial, Type type, Properties properties) {
		super(armorMaterial, type, properties);
	}

	@Override
	public void appendHoverText(ItemStack itemStack, @Nullable Level level, List<Component> list, TooltipFlag tooltipFlag) {
		super.appendHoverText(itemStack, level, list, tooltipFlag);
		list.add(this.getDisplayName().withStyle(ChatFormatting.GRAY));
	}

	public MutableComponent getDisplayName() {
		return Component.translatable(this.getDescriptionId() + ".desc");
	}

	///
	public abstract static class BaseType extends GiantMonsterArmor {
		public BaseType(Type type, Properties properties) {
			super(new ArmorMaterial() {
				@Override
				public int getDurabilityForType(Type type) {
					return new int[]{13, 15, 16, 11}[type.getSlot().getIndex()] * 22;
				}

				@Override
				public int getDefenseForType(Type type) {
					return new int[]{3, 4, 5, 3}[type.getSlot().getIndex()];
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
					return Ingredient.of(new ItemStack(JerotesVillageItems.GIANT_MONSTER_HAIR.get()));
				}

				@Override
				public String getName() {
					return "giant_monster_armor";
				}

				@Override
				public float getToughness() {
					return 2f;
				}

				@Override
				public float getKnockbackResistance() {
					return 0.16f;
				}
			}, type, properties);
		}

	}
	public abstract static class HornType extends GiantMonsterArmor implements ItemModelArmor {
		public HornType(Type type, Properties properties) {
			super(new ArmorMaterial() {
				@Override
				public int getDurabilityForType(Type type) {
					return new int[]{13, 15, 16, 11}[type.getSlot().getIndex()] * 22;
				}

				@Override
				public int getDefenseForType(Type type) {
					return new int[]{3, 4, 5, 3}[type.getSlot().getIndex()];
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
					return Ingredient.of(new ItemStack(JerotesVillageItems.GIANT_MONSTER_HAIR.get()));
				}

				@Override
				public String getName() {
					return "giant_monster_horned_armor";
				}

				@Override
				public float getToughness() {
					return 2f;
				}

				@Override
				public float getKnockbackResistance() {
					return 0.16f;
				}
			}, type, properties);
		}

	}
	///

	public static class Helmet extends BaseType {
		public Helmet() {
			super(Type.HELMET, new Properties());
		}

		@Override
		public String getArmorTexture(ItemStack stack, Entity entity, EquipmentSlot slot, String type) {
			return "jerotesvillage:textures/models/armor/giant_monster_layer_1.png";
		}
	}

	public static class HornedHelmet extends HornType {
		public HornedHelmet() {
			super(Type.HELMET, new Properties());
		}

		@Override
		public void initializeClient(Consumer<IClientItemExtensions> consumer) {
			super.initializeClient(consumer);
			consumer.accept(new IClientItemExtensions() {
				public HumanoidModel<?> getHumanoidArmorModel(LivingEntity livingEntity, ItemStack itemStack, EquipmentSlot equipmentSlot, HumanoidModel<?> original) {
					Modelhorned_armor model = new Modelhorned_armor(Minecraft.getInstance().getEntityModels().bakeLayer(
							Modelhorned_armor.LAYER_LOCATION)).getArmor(livingEntity);
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
		public String getArmorTexture(ItemStack stack, Entity entity, EquipmentSlot slot, String type) {
			if (entity instanceof AbstractIllager && !(OtherMainConfig.CarvedVillagerHelmetHeightAsVillager && entity.getType() == JerotesVillageEntityType.CARVED_VILLAGER.get()) || entity.getType() == EntityType.ZOMBIE_VILLAGER || entity.getType() == EntityType.VILLAGER || entity.getType() == EntityType.WANDERING_TRADER)
				return "jerotesvillage:textures/models/armor/villager_giant_monster_horned_layer_1.png";
			return "jerotesvillage:textures/models/armor/giant_monster_horned_layer_1.png";
		}
	}

	public static class Chestplate extends BaseType {
		public Chestplate() {
			super(Type.CHESTPLATE, new Properties());
		}

		@Override
		public String getArmorTexture(ItemStack stack, Entity entity, EquipmentSlot slot, String type) {
			return "jerotesvillage:textures/models/armor/giant_monster_layer_1.png";
		}
	}

	public static class Leggings extends BaseType {
		public Leggings() {
			super(Type.LEGGINGS, new Properties());
		}

		@Override
		public String getArmorTexture(ItemStack stack, Entity entity, EquipmentSlot slot, String type) {
			return "jerotesvillage:textures/models/armor/giant_monster_layer_2.png";
		}
	}

	public static class Boots extends BaseType {
		public Boots() {
			super(Type.BOOTS, new Properties());
		}

		@Override
		public String getArmorTexture(ItemStack stack, Entity entity, EquipmentSlot slot, String type) {
			return "jerotesvillage:textures/models/armor/giant_monster_layer_1.png";
		}

		@Override
		public boolean canWalkOnPowderedSnow(ItemStack itemstack, LivingEntity entity) {
			return true;
		}
	}
}
