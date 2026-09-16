package com.jerotes.jerotesvillage.item;

import com.jerotes.jerotes.entity.Shoot.Arrow.BaseJavelinEntity;
import com.jerotes.jerotes.init.JerotesSoundEvents;
import com.jerotes.jerotes.item.Interface.ItemAnesthetized;
import com.jerotes.jerotes.item.Tool.ItemToolBaseThrowingSpearOfJavelin;
import com.jerotes.jerotes.item.Tool.ItemToolBaseThrowingSpearOfSpear;
import com.jerotes.jerotesvillage.entity.Shoot.Arrow.ThrownWildernessThrowingSpearEntity;
import com.jerotes.jerotesvillage.init.JerotesVillageItems;
import com.jerotes.jerotesvillage.init.JerotesVillageMobEffects;
import net.minecraft.ChatFormatting;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.MutableComponent;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.*;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.level.Level;

import java.util.List;

public class WildernessThrowingSpear {
	public static class WildernessThrowingSpearOfSpear extends ItemToolBaseThrowingSpearOfSpear implements ItemAnesthetized {
		public WildernessThrowingSpearOfSpear() {
			super(new Tier() {
					  @Override
					  public int getUses() {
						  return 400;
					  }
					  @Override
					  public float getSpeed() {
						  return 6f;
					  }
					  @Override
					  public float getAttackDamageBonus() {
						  return 11f;
					  }
					  @Override
					  public int getLevel() {
						  return 2;
					  }
					  @Override
					  public int getEnchantmentValue() {
						  return 12;
					  }
					  @Override
					  public Ingredient getRepairIngredient() {
						  return Ingredient.of(new ItemStack(Items.BONE));
					  }
				  }, new Properties().stacksTo(1).rarity(Rarity.COMMON), -1f, 1.1f - 4f, (1.0f / 1.1f),
					0.25f, 10, (int) (0.6f * 20.0f),
					Condition.ofAttackerSpeed((int) (2.5f * 20.0f), 8.0f),
					Condition.ofAttackerSpeed((int) (6.75f * 20.0f), 5.1f),
					Condition.ofRelativeSpeed((int) (11.25f * 20.0f), 4.6f),
					0.38f, 0.95f,
					JerotesSoundEvents.SPEAR_USE,
					JerotesSoundEvents.SPEAR_HIT,
					JerotesSoundEvents.SPEAR_ATTACK,
					JerotesSoundEvents.SPEAR_HIT,
					0.5f, 3f, 0.5f, 5f, 0.125f, 0.5f, true, false);
		}

		public Item getOtherMode() {
			return JerotesVillageItems.WILDERNESS_THROWING_SPEAR_OF_JAVELIN.get();
		}

		@Override
		public void attackUse(Entity self, Entity attackTo) {
			if (!attackTo.level().isClientSide && attackTo instanceof LivingEntity livingEntity) {
				livingEntity.addEffect(new MobEffectInstance(JerotesVillageMobEffects.WILDERNESS_GIANT_POISON.get(), 60, 0));
			}
		}

		@Override
		public boolean isValidRepairItem(ItemStack itemStack, ItemStack itemStack2) {
			return itemStack2.is(JerotesVillageItems.WILDERNESS_WOOD_CORE.get()) || super.isValidRepairItem(itemStack, itemStack2);
		}

		@Override
		public int getEnchantmentValue() {
			return 12;
		}

		@Override
		public int getAnesthetized() {
			return 30;
		}
		@Override
		public boolean onlyPlayer() {
			return false;
		}
		@Override
		public boolean onlyThrowing() {
			return false;
		}

		@Override
		public void appendHoverText(ItemStack itemstack, Level world, List<Component> list, TooltipFlag flag) {
			super.appendHoverText(itemstack, world, list, flag);
			list.add(Component.translatable("item.jerotes.anesthetized_only_melee", this.getAnesthetized()).withStyle(ChatFormatting.YELLOW));
			list.add(this.getDisplayName().withStyle(ChatFormatting.GRAY));
		}
		public MutableComponent getDisplayName() {
			return Component.translatable(this.getDescriptionId() + ".desc");
		}
	}
	public static class WildernessThrowingSpearOfJavelin extends ItemToolBaseThrowingSpearOfJavelin implements ItemAnesthetized {
		public WildernessThrowingSpearOfJavelin() {
			super(new Properties().stacksTo(1).rarity(Rarity.COMMON).durability(400), 11f, 1.1f, 20);
		}

		public Item getOtherMode() {
			return JerotesVillageItems.WILDERNESS_THROWING_SPEAR_OF_SPEAR.get();
		}

		@Override
		public BaseJavelinEntity JerotesThrownJavelin(LivingEntity livingEntity, ItemStack itemStack) {
			return new ThrownWildernessThrowingSpearEntity(livingEntity.level(), livingEntity, itemStack);
		}
		public float getThrowingDamage() {
			return 14.0f;
		}

		@Override
		public void attackUse(Entity self, Entity attackTo) {
			if (!attackTo.level().isClientSide && attackTo instanceof LivingEntity livingEntity) {
				livingEntity.addEffect(new MobEffectInstance(JerotesVillageMobEffects.WILDERNESS_GIANT_POISON.get(), 60, 0));
			}
		}

		@Override
		public boolean isValidRepairItem(ItemStack itemStack, ItemStack itemStack2) {
			return itemStack2.is(JerotesVillageItems.WILDERNESS_WOOD_CORE.get()) || super.isValidRepairItem(itemStack, itemStack2);
		}

		@Override
		public int getEnchantmentValue() {
			return 12;
		}

		@Override
		public int getAnesthetized() {
			return 30;
		}
		@Override
		public boolean onlyPlayer() {
			return false;
		}
		@Override
		public boolean onlyThrowing() {
			return false;
		}

		@Override
		public void appendHoverText(ItemStack itemstack, Level world, List<Component> list, TooltipFlag flag) {
			super.appendHoverText(itemstack, world, list, flag);
			list.add(Component.translatable("item.jerotes.anesthetized_melee_or_throw", this.getAnesthetized()).withStyle(ChatFormatting.YELLOW));
			list.add(this.getDisplayName().withStyle(ChatFormatting.GRAY));
		}
		public MutableComponent getDisplayName() {
			return Component.translatable(this.getDescriptionId() + ".desc");
		}

	}
}


