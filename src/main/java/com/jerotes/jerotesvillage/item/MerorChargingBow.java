package com.jerotes.jerotesvillage.item;

import com.jerotes.jerotes.entity.Interface.UseBowEntity;
import com.jerotes.jerotes.item.Tool.ItemToolBaseBow;
import com.jerotes.jerotesvillage.entity.Shoot.Arrow.*;
import com.jerotes.jerotesvillage.init.JerotesVillageItems;
import net.minecraft.ChatFormatting;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.MutableComponent;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.stats.Stats;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.entity.projectile.AbstractArrow;
import net.minecraft.world.item.*;
import net.minecraft.world.item.enchantment.EnchantmentHelper;
import net.minecraft.world.item.enchantment.Enchantments;
import net.minecraft.world.level.Level;

import java.util.List;

public class MerorChargingBow extends ItemToolBaseBow {
	public MerorChargingBow() {
		super(new Properties().stacksTo(1).durability(18800 * 3).fireResistant().rarity(Rarity.EPIC));
	}

	public int getEnchantmentValue() {
		return 28;
	}
	public float getArrowSpeed(float f) {
		return f * 1.5f * 3.0F;
	}
	public float getArrowInaccuracy() {
		return 0f;
	}

	public boolean useBaseShootArrow() {
		return true;
	}
	@Override
	public AbstractArrow customBaseShootArrow(LivingEntity livingEntity, ItemStack itemStack) {
		if (livingEntity instanceof UseBowEntity useBowEntity) {
			if (useBowEntity.getBowLevel() >= 20)
				return new MerorChargingBowArrowVEntity(livingEntity.level(), livingEntity, itemStack);
			if (useBowEntity.getBowLevel() >= 19)
				return new MerorChargingBowArrowIVEntity(livingEntity.level(), livingEntity, itemStack);
			if (useBowEntity.getBowLevel() >= 18)
				return new MerorChargingBowArrowIIIEntity(livingEntity.level(), livingEntity, itemStack);
			if (useBowEntity.getBowLevel() >= 17)
				return new MerorChargingBowArrowIIEntity(livingEntity.level(), livingEntity, itemStack);
		}
		return new MerorChargingBowArrowIEntity(livingEntity.level(), livingEntity, itemStack);
	}
	@Override
	public void releaseUsing(ItemStack itemStack, Level level, LivingEntity livingEntity, int n) {
		if (livingEntity instanceof Player player) {
			boolean flag = player.getAbilities().instabuild || EnchantmentHelper.getItemEnchantmentLevel(Enchantments.INFINITY_ARROWS, itemStack) > 0;
			ItemStack itemStack2 = player.getProjectile(itemStack);
			int i = this.getUseDuration(itemStack) - n;
			i = net.minecraftforge.event.ForgeEventFactory.onArrowLoose(itemStack, level, player, i, !itemStack2.isEmpty() || flag);
			if (i < 0) return;
			if (!itemStack2.isEmpty() || flag) {
				if (itemStack2.isEmpty()) {
					itemStack2 = new ItemStack(Items.ARROW);
				}
				float f = getPowerForTime(i);
				if (!((double)f < 0.1D)) {
					boolean flag1 = player.getAbilities().instabuild || (itemStack2.getItem() instanceof ArrowItem && ((ArrowItem)itemStack2.getItem()).isInfinite(itemStack2, itemStack, player));
					if (!level.isClientSide) {
						int im = EnchantmentHelper.getItemEnchantmentLevel(Enchantments.MULTISHOT, itemStack);
						int jm = im == 0 ? 1 : 3;
						for (int km = 0; km < jm; ++km) {
							AbstractArrow abstractarrow;
							if (i < 60) {
								ArrowItem arrowitem = (ArrowItem) (itemStack2.getItem() instanceof ArrowItem ? itemStack2.getItem() : Items.ARROW);
								abstractarrow = arrowitem.createArrow(level, itemStack2, player);
								abstractarrow = customArrow(abstractarrow);
							} else if (i < 100) {
								abstractarrow = new MerorChargingBowArrowIEntity(level, player, itemStack2);
								abstractarrow.shootFromRotation(player, (player.getXRot() - ((jm - 1f) * 5f) / 2f + i * 5f), player.getYRot(), 0.0F, getArrowSpeed(f), getArrowInaccuracy());
							} else if (i < 140) {
								abstractarrow = new MerorChargingBowArrowIIEntity(level, player, itemStack2);
								abstractarrow.shootFromRotation(player, (player.getXRot() - ((jm - 1f) * 5f) / 2f + i * 5f), player.getYRot(), 0.0F, getArrowSpeed(f), getArrowInaccuracy());
							} else if (i < 180) {
								abstractarrow = new MerorChargingBowArrowIIIEntity(level, player, itemStack2);
								abstractarrow.shootFromRotation(player, (player.getXRot() - ((jm - 1f) * 5f) / 2f + i * 5f), player.getYRot(), 0.0F, getArrowSpeed(f), getArrowInaccuracy());
							} else if (i < 220) {
								abstractarrow = new MerorChargingBowArrowIVEntity(level, player, itemStack2);
								abstractarrow.shootFromRotation(player, (player.getXRot() - ((jm - 1f) * 5f) / 2f + i * 5f), player.getYRot(), 0.0F, getArrowSpeed(f), getArrowInaccuracy());
							} else {
								abstractarrow = new MerorChargingBowArrowVEntity(level, player, itemStack2);
								abstractarrow.shootFromRotation(player, (player.getXRot() - ((jm - 1f) * 5f) / 2f + i * 5f), player.getYRot(), 0.0F, getArrowSpeed(f), getArrowInaccuracy());
							}
							abstractarrow.shootFromRotation(player, (player.getXRot() - ((jm - 1f) * 5f) / 2f + km * 5f), player.getYRot(), 0.0F, getArrowSpeed(f), getArrowInaccuracy());
							if (f == 1.0F) {
								abstractarrow.setCritArrow(true);
							}


							//常规附魔
							int j = EnchantmentHelper.getItemEnchantmentLevel(Enchantments.POWER_ARROWS, itemStack);
							if (j > 0) {
								abstractarrow.setBaseDamage(abstractarrow.getBaseDamage() + (double) j * 0.5D + 0.5D);
							}
							int k = EnchantmentHelper.getItemEnchantmentLevel(Enchantments.PUNCH_ARROWS, itemStack);
							if (k > 0) {
								abstractarrow.setKnockback(k);
							}
							if (EnchantmentHelper.getItemEnchantmentLevel(Enchantments.FLAMING_ARROWS, itemStack) > 0) {
								abstractarrow.setSecondsOnFire(100);
							}

							//其他情况的附魔
							int np = EnchantmentHelper.getTagEnchantmentLevel(Enchantments.PIERCING, itemStack);
							if (np > 0) {
								abstractarrow.setPierceLevel((byte) np);
							}

							itemStack.hurtAndBreak(1, player, (player2) -> {
								player2.broadcastBreakEvent(player.getUsedItemHand());
							});
							if (flag1 || km != 0 || player.getAbilities().instabuild && (itemStack2.getItem() instanceof ArrowItem)) {
								abstractarrow.pickup = AbstractArrow.Pickup.CREATIVE_ONLY;
							}
							if (i >= 60) {
								abstractarrow.pickup = AbstractArrow.Pickup.DISALLOWED;
							}
							level.addFreshEntity(abstractarrow);
						}
					}
					level.playSound((Player)null, player.getX(), player.getY(), player.getZ(), SoundEvents.ARROW_SHOOT, SoundSource.PLAYERS, 1.0F, 1.0F / (level.getRandom().nextFloat() * 0.4F + 1.2F) + f * 0.5F);
					if (!flag1 && !player.getAbilities().instabuild && i < 60) {
						itemStack2.shrink(1);
						if (itemStack2.isEmpty()) {
							player.getInventory().removeItem(itemStack2);
						}
					}
					player.awardStat(Stats.ITEM_USED.get(this));
				}
			}
		}
	}

	@Override
	public boolean isValidRepairItem(ItemStack itemStack, ItemStack itemStack2) {
		return itemStack2.is(JerotesVillageItems.REFINE_MEROR_METAL_INGOT.get()) || super.isValidRepairItem(itemStack, itemStack2);
	}

	@Override
	public int getDefaultProjectileRange() {
		return 30;
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

