package com.jerotes.jerotesvillage.item;

import com.jerotes.jerotes.init.JerotesSoundEvents;
import com.jerotes.jerotes.util.Main;
import com.jerotes.jerotesvillage.entity.Animal.RamblerEntity;
import com.jerotes.jerotesvillage.init.JerotesVillageItems;
import net.minecraft.ChatFormatting;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.network.chat.Component;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.stats.Stats;
import net.minecraft.util.Mth;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.*;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.Vec3;

import javax.annotation.Nullable;
import java.util.List;

public class RamblerSkinWand extends Item implements Vanishable {
	public RamblerSkinWand() {
		super(new Properties().stacksTo(1).rarity(Rarity.EPIC).durability(600));
	}

	@Override
	public boolean isValidRepairItem(ItemStack itemStack, ItemStack itemStack2) {
		return itemStack2.is(JerotesVillageItems.WONDERLAND_FRUIT.get()) || super.isValidRepairItem(itemStack, itemStack2);
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
	public void releaseUsing(ItemStack itemStack, Level level, LivingEntity livingEntity, int n) {
		if (!(livingEntity instanceof Player player)) {
			return;
		}
		int n2 = this.getUseDuration(itemStack) - n;
		if (!((float)n2 < 20)) {
			if (Main.getTargetedEntity(player, 18) != null &&
					Main.getTargetedEntity(player, 18) instanceof RamblerEntity target) {
				target.spawnAtLocation(JerotesVillageItems.RAMBLER_SKIN.get());
				if (level instanceof ServerLevel serverLevel) {
					Vec3 vec3 = livingEntity.getPosition(0.75f);
					Vec3 vec32 = target.getPosition(0.5f).subtract(vec3);
					Vec3 vec33 = vec32.normalize();
					for (int i = 1; i < Mth.floor(vec32.length()) * 6 + 1; ++i) {
						Vec3 vec34 = vec3.add(vec33.scale(i / 6f));
						serverLevel.sendParticles(ParticleTypes.PORTAL, vec34.x, vec34.y, vec34.z, 1, 0.0, 0.0, 0.0, 0.0);
					}
				}
				livingEntity.playSound( JerotesSoundEvents.SPELL, 5.0f, 1.0f);
				if (!player.getAbilities().instabuild) {
					player.getCooldowns().addCooldown(this, 60);
				}
				itemStack.hurtAndBreak(60, player, livingEntitys -> livingEntitys.broadcastBreakEvent(EquipmentSlot.MAINHAND));
				player.awardStat(Stats.ITEM_USED.get(this));
			}
		}
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
	public void appendHoverText(ItemStack itemStack, @Nullable Level level, List<Component> list, TooltipFlag tooltipFlag) {
		super.appendHoverText(itemStack, level, list, tooltipFlag);
		list.add(Component.translatable(this.getDescriptionId() + ".desc").withStyle(ChatFormatting.GRAY));
	}
}
