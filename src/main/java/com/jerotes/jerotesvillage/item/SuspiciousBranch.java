package com.jerotes.jerotesvillage.item;

import com.jerotes.jerotes.init.JerotesSoundEvents;
import net.minecraft.ChatFormatting;
import net.minecraft.core.BlockPos;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.MutableComponent;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundSource;
import net.minecraft.util.Mth;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.*;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.BrushableBlock;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.Vec3;

import javax.annotation.Nullable;
import java.util.List;

public class SuspiciousBranch extends Item implements Vanishable {
	public SuspiciousBranch() {
		super(new Properties().stacksTo(1).rarity(Rarity.UNCOMMON).durability(512));
	}

	@Override
	public UseAnim getUseAnimation(ItemStack itemStack) {
		return UseAnim.TOOT_HORN;
	}
	@Override
	public int getUseDuration(ItemStack itemStack) {
		return 72000;
	}

	@Override
	public InteractionResultHolder<ItemStack> use(Level level, Player player, InteractionHand interactionHand) {
		ItemStack itemStack = player.getItemInHand(interactionHand);

		if (level instanceof ServerLevel serverLevel) {
			double reach = 32;
			int count = 0;
			double nearest = reach * 20;
			AABB aabb = player.getBoundingBox().inflate(reach);

			for (BlockPos blockPos : BlockPos.betweenClosed(Mth.floor(aabb.minX), Mth.floor(aabb.minY), Mth.floor(aabb.minZ), Mth.floor(aabb.maxX), Mth.floor(aabb.maxY), Mth.floor(aabb.maxZ))) {
				BlockState blockState = serverLevel.getBlockState(blockPos);
				boolean bl = blockState.getBlock() instanceof BrushableBlock;
				if (bl) {
					count += 1;
					Vec3 vec3 = blockPos.getCenter();
					if (vec3.distanceTo(player.getEyePosition()) < nearest) {
						nearest = (int)vec3.distanceTo(player.getEyePosition());
					}
				}
			}
			if (nearest > reach) {
				player.sendSystemMessage(Component.translatable("message.jerotesvillage.suspicious_branch_get", reach, count).withStyle(ChatFormatting.LIGHT_PURPLE));
			}
			else {
				player.sendSystemMessage(Component.translatable("message.jerotesvillage.suspicious_branch_get", reach, count).append(Component.translatable("message.jerotesvillage.suspicious_branch_get_has", nearest)).withStyle(ChatFormatting.LIGHT_PURPLE));
			}
		}

		level.playSound(null, player.getX(), player.getY(), player.getZ(), JerotesSoundEvents.TELEPORT, SoundSource.NEUTRAL, 1.0f, 1.0f);
		if (!player.getAbilities().instabuild) {
			player.getCooldowns().addCooldown(this, 120);
		}
		itemStack.hurtAndBreak(1, player, (player2) -> {
			player2.broadcastBreakEvent(EquipmentSlot.MAINHAND);
		});
		return InteractionResultHolder.consume(itemStack);
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
