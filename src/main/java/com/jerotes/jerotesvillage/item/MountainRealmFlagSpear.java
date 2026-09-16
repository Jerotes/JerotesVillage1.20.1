package com.jerotes.jerotesvillage.item;

import com.jerotes.jerotes.entity.Shoot.Arrow.BaseJavelinEntity;
import com.jerotes.jerotes.init.JerotesSoundEvents;
import com.jerotes.jerotes.item.Tool.ItemToolBaseThrowingSpearOfJavelin;
import com.jerotes.jerotes.item.Tool.ItemToolBaseThrowingSpearOfSpear;
import com.jerotes.jerotes.util.Main;
import com.jerotes.jerotesvillage.entity.Shoot.Arrow.ThrownMountainRealmFlagSpearEntity;
import com.jerotes.jerotesvillage.init.JerotesVillageItems;
import net.minecraft.ChatFormatting;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.MutableComponent;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.*;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.level.Level;

import java.util.List;

public class MountainRealmFlagSpear {
	public static class MountainRealmFlagSpearOfSpear extends ItemToolBaseThrowingSpearOfSpear implements Equipable {
		public MountainRealmFlagSpearOfSpear() {
			super(new Tier() {
					  @Override
					  public int getUses() {
						  return 2000;
					  }
					  @Override
					  public float getSpeed() {
						  return 10f;
					  }
					  @Override
					  public float getAttackDamageBonus() {
						  return 14f;
					  }
					  @Override
					  public int getLevel() {
						  return 4;
					  }
					  @Override
					  public int getEnchantmentValue() {
						  return 22;
					  }
					  @Override
					  public Ingredient getRepairIngredient() {
						  return Ingredient.of(new ItemStack(Items.BONE));
					  }
				  }, new Properties().stacksTo(1).rarity(Rarity.EPIC), -1f, 1.1f - 4f, (1.0f / 1.1f),
					0.25f, 10, (int) (0.6f * 20.0f),
					Condition.ofAttackerSpeed((int) (2.5f * 20.0f), 7.0f),
					Condition.ofAttackerSpeed((int) (5.5f * 20.0f), 5.1f),
					Condition.ofRelativeSpeed((int) (8.75f * 20.0f), 4.6f),
					0.38f, 1.6f,
					JerotesSoundEvents.SPEAR_USE,
					JerotesSoundEvents.SPEAR_HIT,
					JerotesSoundEvents.SPEAR_ATTACK,
					JerotesSoundEvents.SPEAR_HIT,
					0.5f, 3f, 0.5f, 5f, 0.125f, 0.5f, true, false);
		}
		@Override
		public void inventoryTick(ItemStack itemstack, Level world, Entity entity, int slot, boolean selected) {
			super.inventoryTick(itemstack, world, entity, slot, selected);
			Main.getJerotesPersistentData(entity).putDouble("jerotesvillage_mountain_realm_flag_spear", 120);
		}

		public Item getOtherMode() {
			return JerotesVillageItems.MOUNTAIN_REALM_FLAG_SPEAR_OF_JAVELIN.get();
		}

		@Override
		public boolean isValidRepairItem(ItemStack itemStack, ItemStack itemStack2) {
			return itemStack2.is(Items.GOLD_INGOT) || super.isValidRepairItem(itemStack, itemStack2);
		}

		@Override
		public int getEnchantmentValue() {
			return 22;
		}

		@Override
		public EquipmentSlot getEquipmentSlot() {
			return EquipmentSlot.HEAD;
		}

		@Override
		public void appendHoverText(ItemStack itemstack, Level world, List<Component> list, TooltipFlag flag) {
			super.appendHoverText(itemstack, world, list, flag);
			list.add(this.getDisplayName().withStyle(ChatFormatting.GRAY));
		}
		public MutableComponent getDisplayName() {
			return Component.translatable(this.getDescriptionId() + ".desc");
		}
	}

	public static class MountainRealmFlagSpearOfJavelin extends ItemToolBaseThrowingSpearOfJavelin implements Equipable {
		public MountainRealmFlagSpearOfJavelin() {
			super(new Properties().stacksTo(1).rarity(Rarity.EPIC).durability(2000), 14f, 1.1f);
		}
		@Override
		public void inventoryTick(ItemStack itemstack, Level world, Entity entity, int slot, boolean selected) {
			super.inventoryTick(itemstack, world, entity, slot, selected);
			Main.getJerotesPersistentData(entity).putDouble("jerotesvillage_mountain_realm_flag_spear", 120);
		}

		public Item getOtherMode() {
			return JerotesVillageItems.MOUNTAIN_REALM_FLAG_SPEAR_OF_SPEAR.get();
		}

		@Override
		public BaseJavelinEntity JerotesThrownJavelin(LivingEntity livingEntity, ItemStack itemStack) {
			return new ThrownMountainRealmFlagSpearEntity(livingEntity.level(), livingEntity, itemStack);
		}
		public float getThrowingDamage() {
			return 15.0f;
		}

		@Override
		public boolean isValidRepairItem(ItemStack itemStack, ItemStack itemStack2) {
			return itemStack2.is(Items.GOLD_INGOT) || super.isValidRepairItem(itemStack, itemStack2);
		}

		@Override
		public int getEnchantmentValue() {
			return 22;
		}

		@Override
		public EquipmentSlot getEquipmentSlot() {
			return EquipmentSlot.HEAD;
		}

		@Override
		public void appendHoverText(ItemStack itemStack, Level level, List<Component> list, TooltipFlag tooltipFlag) {
			super.appendHoverText(itemStack, level, list, tooltipFlag);
			list.add(this.getDisplayName().withStyle(ChatFormatting.GRAY));
		}

		public MutableComponent getDisplayName() {
			return Component.translatable(this.getDescriptionId() + ".desc");
		}

	}
}


