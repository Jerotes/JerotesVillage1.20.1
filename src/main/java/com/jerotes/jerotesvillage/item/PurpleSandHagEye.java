package com.jerotes.jerotesvillage.item;

import com.jerotes.jerotes.init.JerotesMobEffectTags;
import com.jerotes.jerotes.init.JerotesMobEffects;
import com.jerotes.jerotes.util.Main;
import com.jerotes.jerotesvillage.init.JerotesVillageItems;
import com.jerotes.jerotesvillage.init.JerotesVillageSoundEvents;
import net.minecraft.ChatFormatting;
import net.minecraft.core.registries.Registries;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.MutableComponent;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.sounds.SoundSource;
import net.minecraft.stats.Stats;
import net.minecraft.tags.TagKey;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Rarity;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.level.Level;

import javax.annotation.Nullable;
import java.util.List;

public class PurpleSandHagEye extends BaseHagEye {
	public PurpleSandHagEye() {
		super(new Properties().stacksTo(1).rarity(Rarity.RARE).durability(480));
	}

	@Override
	public EquipmentSlot getEquipmentSlot() {
		return EquipmentSlot.HEAD;
	}

	@Override
	public boolean isValidRepairItem(ItemStack itemStack, ItemStack itemStack2) {
		return itemStack2.is(JerotesVillageItems.HAG_EYE.get()) || super.isValidRepairItem(itemStack, itemStack2);
	}

	@Override
	public InteractionResultHolder<ItemStack> use(Level level, Player player, InteractionHand interactionHand) {
		ItemStack itemStack = player.getItemInHand(interactionHand);
		if (itemStack.getDamageValue() >= itemStack.getMaxDamage() - 1) {
			if (!player.level().isClientSide) {
				player.addEffect(new MobEffectInstance(MobEffects.BLINDNESS, 24000, 0), player);
			}
			player.hurt(player.damageSources().genericKill(), 666 * Main.randomReach(player.getRandom(), 1, 60));
			itemStack.hurtAndBreak(1, player, e -> e.broadcastBreakEvent(player.getUsedItemHand()));
			return InteractionResultHolder.consume(itemStack);
		}

		player.startUsingItem(interactionHand);
		if (!level.isClientSide) {
			player.removeEffect(MobEffects.BLINDNESS);
			player.removeEffect(MobEffects.DARKNESS);
		}
		level.playSound(null, player.getX(), player.getY(), player.getZ(), JerotesVillageSoundEvents.PURPLE_SAND_HAG_AMBIENT, SoundSource.NEUTRAL, 1.0f, 1.0f / (level.getRandom().nextFloat() * 0.4f + 1.2f) + 1 * 0.5f);
		if (!player.getAbilities().instabuild) {
			itemStack.hurtAndBreak(1, player, e -> e.broadcastBreakEvent(player.getUsedItemHand()));
		}
		return InteractionResultHolder.consume(itemStack);
	}

	@Override
	public void onUseTick(Level level, LivingEntity livingEntity, ItemStack itemStack, int count) {
		if (livingEntity instanceof Player player) {
			if (Main.getTargetedEntity(player, 32) != null &&
					Main.getTargetedEntity(player, 32) instanceof LivingEntity livingEntity2) {
				if (livingEntity2.getType().is(TagKey.create(Registries.ENTITY_TYPE, new ResourceLocation("jerotesvillage:purple_sand_hag_eye_can_not_see")))) {
					player.displayClientMessage(Component.translatable("message.jerotesvillage.purple_sand_hag_eye_can_not_see").withStyle(ChatFormatting.RED), true);
					if (!player.level().isClientSide) {
						player.addEffect(new MobEffectInstance(MobEffects.BLINDNESS, 240, 0), player);
					}
					super.onUseTick(level, livingEntity, itemStack, count);
					return;
				}
				if (!livingEntity.level().isClientSide) {
					livingEntity2.removeEffect(MobEffects.INVISIBILITY);
					livingEntity2.removeEffect(JerotesMobEffects.CLOAKING.get());
					livingEntity2.removeEffect(JerotesMobEffects.TRUE_INVISIBLE.get());
					livingEntity2.level().registryAccess().registryOrThrow(Registries.MOB_EFFECT).getTagOrEmpty(JerotesMobEffectTags.TRUE_INVISIBLE).forEach(effect -> {
						if (livingEntity2.hasEffect(effect.get())) {
							livingEntity2.removeEffect(effect.get());
						}
					});
				}
			}
		}
		if (!livingEntity.level().isClientSide) {
			livingEntity.addEffect(new MobEffectInstance(MobEffects.NIGHT_VISION, 5, 0, false, false), livingEntity);
		}
		super.onUseTick(level, livingEntity, itemStack, count);
	}

	@Override
	public void releaseUsing(ItemStack itemStack, Level level, LivingEntity livingEntity, int n) {
		if (!(livingEntity instanceof Player player)) {
			return;
		}
		if (Main.getTargetedEntity(player, 32) != null &&
				Main.getTargetedEntity(player, 32) instanceof LivingEntity livingEntity2) {
			if (player instanceof ServerPlayer serverPlayer) {
				if (livingEntity2.getType().is(TagKey.create(Registries.ENTITY_TYPE, new ResourceLocation("jerotesvillage:purple_sand_hag_eye_can_not_see")))) {
					}
				else {
					Main.openMobInventoryGui(serverPlayer, livingEntity2, false, false, false, false, false, false, false, false);
				}
			}
		}
		player.awardStat(Stats.ITEM_USED.get(this));
	}

	@Override
	public void appendHoverText(ItemStack itemStack, @Nullable Level level, List<Component> list, TooltipFlag tooltipFlag) {
		list.add(this.getDisplayName_0().withStyle(ChatFormatting.GRAY));
		list.add(this.getDisplayName_1().withStyle(ChatFormatting.GRAY));
		list.add(this.getDisplayName_2().withStyle(ChatFormatting.GRAY));
		super.appendHoverText(itemStack, level, list, tooltipFlag);
	}

	public MutableComponent getDisplayName_0() {
		return Component.translatable(this.getDescriptionId() + ".desc_0");
	}
	public MutableComponent getDisplayName_1() {
		return Component.translatable(this.getDescriptionId() + ".desc_1");
	}
	public MutableComponent getDisplayName_2() {
		return Component.translatable(this.getDescriptionId() + ".desc_2");
	}
}
