package com.jerotes.jerotesvillage.item;

import com.jerotes.jerotes.item.Tool.ItemToolBaseSword;
import com.jerotes.jerotes.util.Main;
import com.jerotes.jerotesvillage.entity.Shoot.Arrow.MerorRapierShootEntity;
import com.jerotes.jerotesvillage.init.JerotesVillageItems;
import com.jerotes.jerotesvillage.init.JerotesVillageMobEffects;
import com.jerotes.jerotesvillage.init.JerotesVillageSoundEvents;
import net.minecraft.ChatFormatting;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.MutableComponent;
import net.minecraft.sounds.SoundSource;
import net.minecraft.stats.Stats;
import net.minecraft.util.Mth;
import net.minecraft.util.RandomSource;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.entity.projectile.AbstractArrow;
import net.minecraft.world.item.*;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.item.enchantment.Enchantments;
import net.minecraft.world.level.Level;

import java.util.List;

public class MerorRapier extends ItemToolBaseSword {
	public MerorRapier() {
		super(new Tier() {
			public int getUses() {
				return 18800;
			}

			public float getSpeed() {
				return 18f;
			}

			public float getAttackDamageBonus() {
				return 10.5f;
			}

			public int getLevel() {
				return 5;
			}

			public int getEnchantmentValue() {
				return 28;
			}

			public Ingredient getRepairIngredient() {
				return Ingredient.of(new ItemStack(JerotesVillageItems.REFINE_MEROR_METAL_INGOT.get()));
			}
		}, -1, 6f - 4f, new Properties().fireResistant().rarity(Rarity.EPIC));
	}

	@Override
	public UseAnim getUseAnimation(ItemStack itemStack) {
		return UseAnim.BOW;
	}

	@Override
	public int getUseDuration(ItemStack itemStack) {
		return 72000;
	}

	@Override
	public boolean onEntitySwing(ItemStack itemstack, LivingEntity entity) {
		boolean retval = super.onEntitySwing(itemstack, entity);

		if (!entity.level().isClientSide) {
			if (Mth.nextDouble(RandomSource.create(), 1, 100) < 70) {
				return retval;
			}
			Level projectileLevel = entity.level();
			MerorRapierShootEntity shoot0 = new MerorRapierShootEntity(projectileLevel, entity, new ItemStack(Items.AIR));
			shoot0.setPos(entity.getX(), entity.getEyeY() - 0.1, entity.getZ());
			shoot0.shootFromRotation(entity, entity.getXRot() + 3, entity.getYRot(), 5.0f, 3f, 1.0f);
			shoot0.setOwner(entity);
			shoot0.pickup = AbstractArrow.Pickup.DISALLOWED;
			projectileLevel.addFreshEntity(shoot0);
			projectileLevel.playSound(null, entity, JerotesVillageSoundEvents.MEROR_TOOL_USE, SoundSource.NEUTRAL, 1.0f, 1.0f);
			if (Mth.nextDouble(RandomSource.create(), 1, 100) < 45) {
				return retval;
			}
			MerorRapierShootEntity shoot1 = new MerorRapierShootEntity(projectileLevel, entity, new ItemStack(Items.AIR));
			shoot1.setPos(entity.getX() + 0.6, entity.getEyeY() - 0.1, entity.getZ() - 0.6);
			shoot1.shootFromRotation(entity, entity.getXRot() + 3, entity.getYRot(), 5.0f, 3f, 1.0f);
			shoot1.setOwner(entity);
			shoot1.pickup = AbstractArrow.Pickup.DISALLOWED;
			projectileLevel.addFreshEntity(shoot1);
			projectileLevel.playSound(null, entity, JerotesVillageSoundEvents.MEROR_TOOL_USE, SoundSource.NEUTRAL, 1.0f, 1.0f);
			if (Mth.nextDouble(RandomSource.create(), 1, 100) < 10) {
				return retval;
			}
			MerorRapierShootEntity shoot2 = new MerorRapierShootEntity(projectileLevel, entity, new ItemStack(Items.AIR));
			shoot2.setPos(entity.getX() - 0.6, entity.getEyeY() - 0.1, entity.getZ() + 0.6);
			shoot2.shootFromRotation(entity, entity.getXRot() + 3, entity.getYRot(), 5.0f, 3f, 1.0f);
			shoot2.setOwner(entity);
			shoot2.pickup = AbstractArrow.Pickup.DISALLOWED;
			projectileLevel.addFreshEntity(shoot2);
			projectileLevel.playSound(null, entity, JerotesVillageSoundEvents.MEROR_TOOL_USE, SoundSource.NEUTRAL, 1.0f, 1.0f);

		}
		return retval;
	}


	@Override
	public void releaseUsing(ItemStack itemStack, Level level, LivingEntity livingEntity, int n) {
		int n1 = this.getUseDuration(itemStack) - n;
		if (n1 < 10) {
			return;
		}
		if (!livingEntity.onGround() && !livingEntity.isInWater() && !livingEntity.isFallFlying() && !livingEntity.hasEffect(JerotesVillageMobEffects.MEROR_STIMULIN.get()) && !(livingEntity instanceof Player player && player.getAbilities().instabuild)) {
			return;
		}
		float n2 = Math.min(itemStack.getEnchantmentLevel(Enchantments.SHARPNESS), 5);
		if (n2 < 0) {
			n2 = 0;
		}
		float f = livingEntity.getYRot();
		float f2 = livingEntity.getXRot();
		float f3 = -Mth.sin(f * 0.017453292f) * Mth.cos(f2 * 0.017453292f);
		float f4 = -Mth.sin(f2 * 0.017453292f);
		float f5 = Mth.cos(f * 0.017453292f) * Mth.cos(f2 * 0.017453292f);
		float f6 = Mth.sqrt(f3 * f3 + f4 * f4 + f5 * f5);
		float f7 = 2f + n2 / 5;
		if (!livingEntity.isInWater()) {
			livingEntity.push(f3 *= f7 / f6, f4 *= f7 / f6, f5 *= f7 / f6);
		}
		else {
			livingEntity.push(f3 *= f7 / f6 * 2, f4 *= f7 / f6 * 2, f5 *= f7 / f6 * 2);
		}
		if ((livingEntity.isFallFlying() || livingEntity.isInWater()) && livingEntity instanceof Player player2) {
			player2.startAutoSpinAttack((int) ((n2) * 3 + 7));
		}
		if (!livingEntity.level().isClientSide()) {
			Main.getJerotesPersistentData(livingEntity).putDouble("jerotesvillage_start_meror_rapier_attack", (n2) * 3 + 5);
		}
		level.playSound(null, livingEntity, JerotesVillageSoundEvents.MEROR_TOOL_USE, SoundSource.NEUTRAL, 1.0f, 1.0f);
		if (livingEntity instanceof Player player2) {
			player2.awardStat(Stats.ITEM_USED.get(this));
		}
	}

	@Override
	public InteractionResultHolder<ItemStack> use(Level level, Player player, InteractionHand interactionHand) {
		ItemStack itemStack = player.getItemInHand(interactionHand);
		if (itemStack.getDamageValue() >= itemStack.getMaxDamage() - 1) {
			return InteractionResultHolder.fail(itemStack);
		}
		player.startUsingItem(interactionHand);
		return InteractionResultHolder.consume(itemStack);
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
