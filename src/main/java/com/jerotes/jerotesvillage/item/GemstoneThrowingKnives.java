package com.jerotes.jerotesvillage.item;

import com.jerotes.jerotes.item.Tool.ItemToolBaseDagger;
import com.jerotes.jerotesvillage.entity.Shoot.Arrow.GemstoneThrowingKnivesShootEntity;
import com.jerotes.jerotesvillage.init.JerotesVillageItems;
import net.minecraft.ChatFormatting;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.MutableComponent;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.stats.Stats;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.entity.projectile.AbstractArrow;
import net.minecraft.world.item.*;
import net.minecraft.world.item.alchemy.PotionUtils;
import net.minecraft.world.item.alchemy.Potions;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.level.Level;

import java.util.List;

public class GemstoneThrowingKnives extends ItemToolBaseDagger {
	public GemstoneThrowingKnives() {
		super(new Tier() {
			public int getUses() {
				return 108;
			}

			public float getSpeed() {
				return 12f;
			}

			public float getAttackDamageBonus() {
				return 5f;
			}

			public int getLevel() {
				return 1;
			}

			public int getEnchantmentValue() {
				return 28;
			}

			public Ingredient getRepairIngredient() {
				return Ingredient.of(new ItemStack(JerotesVillageItems.OCEAN_GEMSTONE.get()));
			}

		}, -1, 2.5f - 4f, new Properties().rarity(Rarity.COMMON));
	}


	@Override
	public InteractionResultHolder<ItemStack> use(Level level, Player player, InteractionHand interactionHand) {
		ItemStack itemStack = player.getItemInHand(interactionHand);
		if (usePotion(level, player, interactionHand)) {
			return InteractionResultHolder.sidedSuccess(itemStack, level.isClientSide());
		}
		if (itemStack.getDamageValue() >= itemStack.getMaxDamage() - 1) {
			return InteractionResultHolder.fail(itemStack);
		}
		player.startUsingItem(interactionHand);
		return InteractionResultHolder.consume(itemStack);
	}

	@Override
	public void releaseUsing(ItemStack itemstack, Level level, LivingEntity livingEntity, int n) {
		Player player2 = (Player) livingEntity;
		int n2 = this.getUseDuration(itemstack) - n;
		if (n2 < 10) {
			return;
		}
		//第三发
		if (level instanceof ServerLevel projectileLevel) {
			if (n2 >= 40) {
				shootThrowingKnives(itemstack, level, player2, projectileLevel, 4, 0);
			}
			//第二发
			if (n2 >= 20) {
				shootThrowingKnives(itemstack, level, player2, projectileLevel, 3, 1);
			}
			//第一发
			shootThrowingKnives(itemstack, level, player2, projectileLevel, 2, 2);
		}
	}
	public ItemStack getDefaultInstance() {
		return PotionUtils.setPotion(super.getDefaultInstance(), Potions.POISON);
	}

	private void shootThrowingKnives(ItemStack itemstack, Level level, Player player2, ServerLevel projectileLevel, float f, float f2) {
		AbstractArrow shoot1 = new GemstoneThrowingKnivesShootEntity(projectileLevel, player2, itemstack);
		shoot1.setOwner(player2);
		shoot1.setPos(player2.getX(), player2.getEyeY() - 0.1, player2.getZ());
		shoot1.shootFromRotation(player2, player2.getXRot() + 3, player2.getYRot(), 5.0f, 3f, 1.0f);
		shoot1.shoot(player2.getLookAngle().x, player2.getLookAngle().y, player2.getLookAngle().z, f, f2);
		projectileLevel.addFreshEntity(shoot1);
		if (!player2.getAbilities().instabuild) {
			itemstack.hurtAndBreak(1, player2, e -> e.broadcastBreakEvent(player2.getUsedItemHand()));
		}
		level.playSound(null, player2, SoundEvents.TRIDENT_THROW, SoundSource.PLAYERS, 1.0f, 1.0f);
		player2.awardStat(Stats.ITEM_USED.get(this));
		PotionUtils.setPotion(itemstack, Potions.WATER);
	}


	@Override
	public UseAnim getUseAnimation(ItemStack itemStack) {
		return UseAnim.SPEAR;
	}

	@Override
	public int getUseDuration(ItemStack itemStack) {
		return 72000;
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
