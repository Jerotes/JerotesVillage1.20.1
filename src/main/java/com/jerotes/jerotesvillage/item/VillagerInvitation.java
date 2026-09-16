package com.jerotes.jerotesvillage.item;

import com.jerotes.jerotes.init.JerotesSoundEvents;
import com.jerotes.jerotes.util.Main;
import net.minecraft.ChatFormatting;
import net.minecraft.core.BlockPos;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.MutableComponent;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.MobSpawnType;
import net.minecraft.world.entity.npc.Villager;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.*;
import net.minecraft.world.level.Level;

import javax.annotation.Nullable;
import java.util.List;

public class VillagerInvitation extends Item {
	public VillagerInvitation() {
			super(new Properties().stacksTo(1).rarity(Rarity.UNCOMMON));
	}

	@Override
	public UseAnim getUseAnimation(ItemStack itemStack) {
		return UseAnim.BRUSH;
	}

	@Override
	public int getUseDuration(ItemStack itemStack) {
		return 72000;
	}

	@Override
	public InteractionResultHolder<ItemStack> use(Level level, Player player, InteractionHand interactionHand) {
		ItemStack itemStack = player.getItemInHand(interactionHand);
		player.startUsingItem(interactionHand);
		return InteractionResultHolder.consume(itemStack);
	}

	@Override
	public void releaseUsing(ItemStack itemStack, Level level, LivingEntity livingEntity, int n) {
		if (!(livingEntity instanceof Player player)) {
			return;
		}
		int n2 = this.getUseDuration(itemStack) - n;
		if (n2 < 20) {
			return;
		}
		if (level instanceof ServerLevel serverLevel) {
			BlockPos blockPos = Main.findSpawnPositionNearFillOnBlock(player, 4);
			Villager villager;
			if (blockPos != null) {
				villager = EntityType.VILLAGER.spawn(serverLevel, blockPos, MobSpawnType.EVENT);
				if (villager != null) {
					villager.setDeltaMovement(0, 0, 0);
					player.getCooldowns().addCooldown(this, 120);
					if (!player.getAbilities().instabuild) {
						itemStack.shrink(1);
					}
					for (int i = 0; i < 60; ++i) {
						serverLevel.sendParticles(ParticleTypes.HAPPY_VILLAGER, villager.getRandomX(1.5), villager.getRandomY(), villager.getRandomZ(1.5), 0, 0.0, 0.0, 0.0, 0.0);
					}
					if (!villager.isSilent()) {
						serverLevel.playSound(null, villager.getX(), villager.getY(), villager.getZ(), JerotesSoundEvents.TELEPORT, villager.getSoundSource(), 5.0f, 1.0F);
					}
				}
			}
		}
	}

	@Override
	public void appendHoverText(ItemStack itemStack, @Nullable Level level, List<Component> list, TooltipFlag tooltipFlag) {
		super.appendHoverText(itemStack, level, list, tooltipFlag);
		list.add(this.getDisplayName().withStyle(ChatFormatting.GRAY));
	}

	public MutableComponent getDisplayName() {
		return Component.translatable(this.getDescriptionId() + ".desc");
	}

}
