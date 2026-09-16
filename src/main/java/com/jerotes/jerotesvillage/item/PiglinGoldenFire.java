package com.jerotes.jerotesvillage.item;

import com.jerotes.jerotes.item.Interface.ItemSpecialEffect;
import com.jerotes.jerotesvillage.block.DroughtFire.PiglinSecondRoundWorldPortalBlock;
import com.jerotes.jerotesvillage.init.JerotesVillageDimensions;
import net.minecraft.ChatFormatting;
import net.minecraft.advancements.CriteriaTriggers;
import net.minecraft.core.BlockPos;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.MutableComponent;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.stats.Stats;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.*;
import net.minecraft.world.item.context.UseOnContext;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.*;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraft.world.level.gameevent.GameEvent;

import javax.annotation.Nullable;
import java.util.List;

public class PiglinGoldenFire extends Item implements ItemSpecialEffect, Vanishable {
	public PiglinGoldenFire() {
		super(new Properties().stacksTo(1).fireResistant().rarity(Rarity.UNCOMMON).durability(32));
	}

	@Override
	public boolean isValidRepairItem(ItemStack itemStack, ItemStack itemStack2) {
		return itemStack2.is(Items.GOLD_INGOT) || super.isValidRepairItem(itemStack, itemStack2);
	}

	@Override
	public void attackUse(Entity self, Entity attackTo) {
		attackTo.setSecondsOnFire(5);
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

	@Override
	public InteractionResult useOn(UseOnContext context) {
		BlockPos pos = context.getClickedPos().relative(context.getClickedFace());
		Player entity = context.getPlayer();
		ItemStack itemstack = context.getItemInHand();
		Level level = context.getLevel();
		BlockPos blockPos = context.getClickedPos();
		BlockState blockState = level.getBlockState(blockPos);
		int x = pos.getX();
		int y = pos.getY();
		int z = pos.getZ();

		if (entity != null && entity.mayUseItemAt(pos, context.getClickedFace(), itemstack)) {
            if (level.isEmptyBlock(pos)) {
				if (entity.level().dimension() == Level.NETHER || entity.level().dimension() == JerotesVillageDimensions.SECOND_ROUND_WORLD) {
					boolean bl = PiglinSecondRoundWorldPortalBlock.portalSpawn(level, pos);
					if (bl) {
						if (!entity.getAbilities().instabuild) {
							itemstack.hurtAndBreak(1, entity, e -> e.broadcastBreakEvent(entity.getUsedItemHand()));
						}
						level.playSound(null, BlockPos.containing(x, y, z), SoundEvents.FIRECHARGE_USE, SoundSource.NEUTRAL, 1.0f, 1.0f / (level.getRandom().nextFloat() * 0.4f + 1.2f) + 1 * 0.5f);
						return InteractionResult.SUCCESS;
					}
				}
            }
			if (level.getBlockState(blockPos).getBlock() instanceof TntBlock tntBlock && level.getBlockState(blockPos).is(Blocks.TNT)) {
				tntBlock.onCaughtFire(level.getBlockState(blockPos), level, blockPos, context.getClickedFace(), entity);
				level.setBlock(blockPos, Blocks.AIR.defaultBlockState(), 11);
				Item item = itemstack.getItem();
				if (!entity.getAbilities().instabuild) {
					itemstack.hurtAndBreak(1, entity, e -> e.broadcastBreakEvent(entity.getUsedItemHand()));
				};
				entity.awardStat(Stats.ITEM_USED.get(item));
				return InteractionResult.sidedSuccess(level.isClientSide);
			}
			else if (!CampfireBlock.canLight(blockState) && !CandleBlock.canLight(blockState) && !CandleCakeBlock.canLight(blockState)) {
				BlockPos blockPos1 = blockPos.relative(context.getClickedFace());
				if (BaseFireBlock.canBePlacedAt(level, blockPos1, context.getHorizontalDirection())) {
					level.playSound(entity, blockPos1, SoundEvents.FIRECHARGE_USE, SoundSource.BLOCKS, 1.0F, level.getRandom().nextFloat() * 0.4F + 0.8F);
					BlockState blockState1 = BaseFireBlock.getState(level, blockPos1);
					level.setBlock(blockPos1, blockState1, 11);
					level.gameEvent(entity, GameEvent.BLOCK_PLACE, blockPos);
					ItemStack itemstacks = context.getItemInHand();
					if (entity instanceof ServerPlayer) {
						CriteriaTriggers.PLACED_BLOCK.trigger((ServerPlayer)entity, blockPos1, itemstacks);
						itemstacks.hurtAndBreak(1, entity, (p_41300_) -> {
							p_41300_.broadcastBreakEvent(context.getHand());
						});
					}

					return InteractionResult.sidedSuccess(level.isClientSide());
				} else {
					return InteractionResult.FAIL;
				}
			}
			else {
				level.playSound(entity, blockPos, SoundEvents.FIRECHARGE_USE, SoundSource.BLOCKS, 1.0F, level.getRandom().nextFloat() * 0.4F + 0.8F);
				level.setBlock(blockPos, blockState.setValue(BlockStateProperties.LIT, Boolean.valueOf(true)), 11);
				level.gameEvent(entity, GameEvent.BLOCK_CHANGE, blockPos);
                context.getItemInHand().hurtAndBreak(1, entity, (players) -> {
                    players.broadcastBreakEvent(context.getHand());
                });

                return InteractionResult.sidedSuccess(level.isClientSide());
			}
        }
		return super.useOn(context);
	}

}
