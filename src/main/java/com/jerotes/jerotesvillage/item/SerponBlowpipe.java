package com.jerotes.jerotesvillage.item;

import com.google.common.collect.ImmutableMultimap;
import com.google.common.collect.Multimap;
import com.jerotes.jerotes.item.Tool.ItemToolBaseBow;
import com.jerotes.jerotesvillage.entity.Shoot.Arrow.SerponDartEntity;
import com.jerotes.jerotesvillage.entity.Shoot.Other.SerponFireChargeEntity;
import com.jerotes.jerotesvillage.init.JerotesVillageEntityType;
import com.jerotes.jerotesvillage.init.JerotesVillageItems;
import net.minecraft.ChatFormatting;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.MutableComponent;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.stats.Stats;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.attributes.Attribute;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.entity.projectile.AbstractArrow;
import net.minecraft.world.entity.projectile.ProjectileUtil;
import net.minecraft.world.item.*;
import net.minecraft.world.item.enchantment.EnchantmentHelper;
import net.minecraft.world.item.enchantment.Enchantments;
import net.minecraft.world.level.Level;

import java.util.List;
import java.util.function.Predicate;

public class SerponBlowpipe extends ItemToolBaseBow {
	public static final Predicate<ItemStack> FIRE_CHARGE = itemStack -> itemStack.is(Items.FIRE_CHARGE);
	public static final Predicate<ItemStack> FIRE_CHARGE_OR_DART = FIRE_CHARGE.or(itemStack -> itemStack.is(JerotesVillageItems.SERPON_DART.get()));
	public SerponBlowpipe() {
		super(new Item.Properties().stacksTo(1).fireResistant().rarity(Rarity.COMMON).durability(1600 * 3));
	}

	public boolean useBaseShootArrow() {
		return true;
	}
	@Override
	public AbstractArrow customBaseShootArrow(LivingEntity livingEntity, ItemStack itemStack) {
		SerponDartEntity serponDartEntity = new SerponDartEntity(livingEntity.level(), livingEntity, itemStack);
		serponDartEntity.setSecondsOnFire(100);
		return serponDartEntity;
	}
	public boolean specialShootBullet(LivingEntity livingEntity, LivingEntity livingEntityTarget, float f, int n, int n2) {
		ItemStack itemStackBow = livingEntity.getMainHandItem();
		ItemStack itemStackArrow = livingEntity.getProjectile(livingEntity.getItemInHand(ProjectileUtil.getWeaponHoldingHand(livingEntity, itemStackBow.getItem())));
		if (itemStackArrow.is(Items.FIRE_CHARGE)) {
			SerponFireChargeEntity fire = new SerponFireChargeEntity(JerotesVillageEntityType.SERPON_FIRE_CHARGE.get(), livingEntity.level());
			fire.setOwner(livingEntity);
			fire.setPos(livingEntity.getX(), livingEntity.getEyeY(), livingEntity.getZ());
			fire.shoot(livingEntityTarget.getX() - livingEntity.getX(), livingEntityTarget.getY(0.7) - livingEntity.getY(0.7), livingEntityTarget.getZ() - livingEntity.getZ(), 4f, 0f);
			livingEntity.level().addFreshEntity(fire);
			livingEntity.level().playSound(null, livingEntity.getX(), livingEntity.getY(), livingEntity.getZ(), SoundEvents.BLAZE_SHOOT, SoundSource.NEUTRAL, 1.0f, 1.0f / (livingEntity.level().getRandom().nextFloat() * 0.4f + 1.2f) + f * 0.5f);
			return true;
		}
		return false;
	}
	@Override
	public boolean isValidRepairItem(ItemStack itemStack, ItemStack itemStack2) {
		return itemStack2.is(JerotesVillageItems.SERPON_ALLOY_INGOT.get()) || super.isValidRepairItem(itemStack, itemStack2);
	}

	@Override
	public Multimap<Attribute, AttributeModifier> getDefaultAttributeModifiers(EquipmentSlot equipmentSlot) {
		if (equipmentSlot == EquipmentSlot.MAINHAND) {
			ImmutableMultimap.Builder<Attribute, AttributeModifier> builder = ImmutableMultimap.builder();
			builder.putAll(super.getDefaultAttributeModifiers(equipmentSlot));
			builder.put(Attributes.ATTACK_DAMAGE, new AttributeModifier(BASE_ATTACK_DAMAGE_UUID, "Item modifier", 3d, AttributeModifier.Operation.ADDITION));
			builder.put(Attributes.ATTACK_SPEED, new AttributeModifier(BASE_ATTACK_SPEED_UUID, "Item modifier", -2.4, AttributeModifier.Operation.ADDITION));
			return builder.build();
		}
		return super.getDefaultAttributeModifiers(equipmentSlot);
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
				boolean bl2 = itemStack2.is(Items.FIRE_CHARGE);
				if (bl2) {
					if (itemStack2.isEmpty()) {
						itemStack2 = new ItemStack(Items.FIRE_CHARGE);
					}
					float f = getPowerForTime(i);
					if (!((double) f < 0.1D)) {
						boolean flag1 = player.getAbilities().instabuild || EnchantmentHelper.getItemEnchantmentLevel(Enchantments.INFINITY_ARROWS, itemStack) > 0;
						if (!level.isClientSide) {
							SerponFireChargeEntity fire = new SerponFireChargeEntity(JerotesVillageEntityType.SERPON_FIRE_CHARGE.get(), level);
							fire.setOwner(livingEntity);
							fire.setPos(livingEntity.getX(), livingEntity.getEyeY(), livingEntity.getZ());
							fire.shootFromRotation(livingEntity, livingEntity.getXRot(), livingEntity.getYRot(), 1.0f, 1.5f, 1.0f);
							itemStack.hurtAndBreak(1, player, (player2) -> {
								player2.broadcastBreakEvent(player.getUsedItemHand());
							});
							level.addFreshEntity(fire);
						}
						level.playSound(null, player.getX(), player.getY(), player.getZ(), SoundEvents.BLAZE_SHOOT, SoundSource.NEUTRAL, 1.0f, 1.0f / (level.getRandom().nextFloat() * 0.4f + 1.2f) + f * 0.5f);
						if (!flag1 && !player.getAbilities().instabuild) {
							itemStack2.shrink(1);
							if (itemStack2.isEmpty()) {
								player.getInventory().removeItem(itemStack2);
							}
						}
						player.awardStat(Stats.ITEM_USED.get(this));
					}
				}
				else {
					if (itemStack2.isEmpty()) {
						itemStack2 = new ItemStack(JerotesVillageItems.SERPON_DART.get());
					}
					float f = getPowerForTime(i);
					if (!((double) f < 0.1D)) {
						boolean flag1 = player.getAbilities().instabuild || EnchantmentHelper.getItemEnchantmentLevel(Enchantments.INFINITY_ARROWS, itemStack) > 0;
						if (!level.isClientSide) {
							AbstractArrow serponDartEntity = new SerponDartEntity(level, player, itemStack2.copyWithCount(1));
							serponDartEntity = customArrow(serponDartEntity);
							serponDartEntity.shootFromRotation(player, player.getXRot(), player.getYRot(), 0.0F, f * 3.0F, 1.0F);
							if (f == 1.0F) {
								serponDartEntity.setCritArrow(true);
							}
							int j = EnchantmentHelper.getItemEnchantmentLevel(Enchantments.POWER_ARROWS, itemStack);
							if (j > 0) {
								serponDartEntity.setBaseDamage(serponDartEntity.getBaseDamage() + (double) j * 0.5D + 0.5D);
							}
							int k = EnchantmentHelper.getItemEnchantmentLevel(Enchantments.PUNCH_ARROWS, itemStack);
							if (k > 0) {
								serponDartEntity.setKnockback(k);
							}
							if (EnchantmentHelper.getItemEnchantmentLevel(Enchantments.FLAMING_ARROWS, itemStack) >= 0) {
								serponDartEntity.setSecondsOnFire(100);
							}
							itemStack.hurtAndBreak(1, player, (player2) -> {
								player2.broadcastBreakEvent(player.getUsedItemHand());
							});
							if (flag1 || player.getAbilities().instabuild && (itemStack2.getItem() instanceof ArrowItem)) {
								serponDartEntity.pickup = AbstractArrow.Pickup.CREATIVE_ONLY;
							}
							level.addFreshEntity(serponDartEntity);
						}
						level.playSound(null, player.getX(), player.getY(), player.getZ(), SoundEvents.BLAZE_SHOOT, SoundSource.NEUTRAL, 1.0f, 1.0f / (level.getRandom().nextFloat() * 0.4f + 1.2f) + f * 0.5f);
						if (!flag1 && !player.getAbilities().instabuild) {
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
	}

	@Override
	public UseAnim getUseAnimation(ItemStack itemStack) {
		return UseAnim.TOOT_HORN;
	}

	@Override
	public Predicate<ItemStack> getAllSupportedProjectiles() {
		return FIRE_CHARGE_OR_DART;
	}

	@Override
	public int getDefaultProjectileRange() {
		return 22;
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
