package com.jerotes.jerotesvillage.item;

import com.jerotes.jerotesvillage.init.*;
import com.jerotes.jerotes.item.Tool.*;
import net.minecraft.ChatFormatting;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.MutableComponent;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundSource;
import net.minecraft.util.Mth;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.*;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.level.Level;

import java.util.List;

public class MerorChainsaw extends ItemToolBaseChainsaw {
	public MerorChainsaw() {
		super(new Tier() {
			public int getUses() {
				return 18800;
			}

			public float getSpeed() {
				return 18f;
			}

			public float getAttackDamageBonus() {
				return 14f;
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
		}, -1, 1.4f - 4f, new Properties().fireResistant().rarity(Rarity.EPIC), 0.3f, 0.1f, 40, 1.5f, 3.0f);
	}

	@Override
	public InteractionResultHolder<ItemStack> use(Level level, Player player, InteractionHand interactionHand) {
		ItemStack itemStack = player.getItemInHand(interactionHand);
		if (interactionHand == InteractionHand.OFF_HAND) {
			return InteractionResultHolder.fail(itemStack);
		}
		if (player.isShiftKeyDown()) {
			player.getCooldowns().addCooldown(this, 600);
			itemStack.hurtAndBreak(2, player, livingEntitys -> livingEntitys.broadcastBreakEvent(EquipmentSlot.MAINHAND));
			level.playSound(null, player.getX(), player.getY(), player.getZ(), JerotesVillageSoundEvents.MEROR_TOOL_USE, SoundSource.NEUTRAL, 1.5f, 0.4f / (level.getRandom().nextFloat() * 0.4f + 0.8f));
			return InteractionResultHolder.sidedSuccess(itemStack, level.isClientSide());
		}
		return super.use(level, player, interactionHand);
	}
	@Override
	public void onUseTick(Level level, LivingEntity livingEntity, ItemStack itemStack, int count) {
		super.onUseTick(level, livingEntity, itemStack, count);
		double d3 = -Mth.sin(livingEntity.getYRot() * ((float)Math.PI / 180F));
		double d4 = Mth.cos(livingEntity.getYRot() * ((float)Math.PI / 180F));
		if (livingEntity.level() instanceof ServerLevel serverLevel) {
			serverLevel.sendParticles(JerotesVillageParticleTypes.MEROR.get(),  livingEntity.getRandomX(0.5), livingEntity.getRandomY(), livingEntity.getRandomZ(0.5), 4, d3, 0.0, d4, 0.0);
		}
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
