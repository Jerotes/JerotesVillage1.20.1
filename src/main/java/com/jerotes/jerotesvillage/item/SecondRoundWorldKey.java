package com.jerotes.jerotesvillage.item;

import com.jerotes.jerotesvillage.block.Other.SecondRoundWorldPortalBlock;
import com.jerotes.jerotesvillage.init.JerotesVillageBlocks;
import com.jerotes.jerotesvillage.init.JerotesVillageDimensions;
import com.jerotes.jerotesvillage.init.JerotesVillageItems;
import net.minecraft.ChatFormatting;
import net.minecraft.core.BlockPos;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.MutableComponent;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.*;
import net.minecraft.world.item.context.UseOnContext;
import net.minecraft.world.level.Level;

import javax.annotation.Nullable;
import java.util.List;

public class SecondRoundWorldKey extends Item implements Vanishable {
	public SecondRoundWorldKey() {
		super(new Item.Properties().stacksTo(1).fireResistant().rarity(Rarity.UNCOMMON));
	}

	@Override
	public boolean isValidRepairItem(ItemStack itemStack, ItemStack itemStack2) {
		return itemStack2.is(JerotesVillageItems.VILLAGER_METAL_INGOT.get()) || super.isValidRepairItem(itemStack, itemStack2);
	}

	@Override
	public boolean hasCraftingRemainingItem(ItemStack stack) {
		return true;
	}

	@Override
	public ItemStack getCraftingRemainingItem(ItemStack itemstack) {
		return new ItemStack(this);
	}

	@Override
	public InteractionResult useOn(UseOnContext context) {
		BlockPos pos = context.getClickedPos().relative(context.getClickedFace());
		BlockPos posIn = context.getClickedPos();
		Player entity = context.getPlayer();
		ItemStack itemstack = context.getItemInHand();
		Level level = context.getLevel();
		int x = pos.getX();
		int y = pos.getY();
		int z = pos.getZ();

		if ((level.getBlockState(posIn)).getBlock() == JerotesVillageBlocks.VILLAGER_METAL_BLOCK.get()) {
			if (level instanceof ServerLevel serverLevel) {
				serverLevel.setBlock(posIn, JerotesVillageBlocks.SECOND_ROUND_WORLD_BLOCK.get().defaultBlockState(), 2);
			}
			ItemStack itemStack2 = new ItemStack(JerotesVillageItems.VILLAGER_METAL_INGOT.get(), 8);
			if (entity != null && !entity.getInventory().add(itemStack2)) {
				entity.drop(itemStack2, false);
			}
            level.playSound(null, posIn, SoundEvents.METAL_PLACE, SoundSource.NEUTRAL, 1.0f, 1.0f / (level.getRandom().nextFloat() * 0.4f + 1.2f) + 1 * 0.5f);
			return InteractionResult.SUCCESS;
		}
		else if (entity != null && entity.mayUseItemAt(pos, context.getClickedFace(), itemstack)) {
            if (level.isEmptyBlock(pos)) {
				if (entity.level().dimension() == Level.OVERWORLD || entity.level().dimension() == JerotesVillageDimensions.SECOND_ROUND_WORLD) {
					boolean bl = SecondRoundWorldPortalBlock.portalSpawn(level, pos);
					if (bl) {
						level.playSound(null, BlockPos.containing(x, y, z), SoundEvents.METAL_PLACE, SoundSource.NEUTRAL, 1.0f, 1.0f / (level.getRandom().nextFloat() * 0.4f + 1.2f) + 1 * 0.5f);
						return InteractionResult.SUCCESS;
					}
				}
            }
        }
        return super.useOn(context);
	}

	@Override
	public void appendHoverText(ItemStack itemStack, @Nullable Level level, List<Component> list, TooltipFlag tooltipFlag) {
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
