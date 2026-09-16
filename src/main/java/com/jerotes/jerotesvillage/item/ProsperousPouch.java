package com.jerotes.jerotesvillage.item;

import net.minecraft.ChatFormatting;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.core.Holder;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.MutableComponent;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.stats.Stats;
import net.minecraft.tags.BiomeTags;
import net.minecraft.tags.BlockTags;
import net.minecraft.tags.ItemTags;
import net.minecraft.util.RandomSource;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.*;
import net.minecraft.world.item.context.UseOnContext;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.biome.Biome;
import net.minecraft.world.level.block.BaseCoralWallFanBlock;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.BonemealableBlock;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.gameevent.GameEvent;

import javax.annotation.Nullable;
import java.util.List;

public class ProsperousPouch extends Item implements Vanishable {
	public ProsperousPouch() {
		super(new Properties().stacksTo(1).rarity(Rarity.EPIC).durability(64));
	}

	@Override
	public InteractionResultHolder<ItemStack> use(Level level, Player player, InteractionHand interactionHand) {
		ItemStack itemStack = player.getItemInHand(interactionHand);
		if (player.getMainHandItem().getItem() instanceof ProsperousPouch && player.getOffhandItem().is(ItemTags.FLOWERS) && player.getMainHandItem().getDamageValue() > 0) {
			player.getMainHandItem().setDamageValue(0);
			player.awardStat(Stats.ITEM_USED.get(this));
			if (!player.getAbilities().instabuild) {
				player.getOffhandItem().shrink(1);
			}
			return InteractionResultHolder.sidedSuccess(itemStack, level.isClientSide());
		}
		else if (player.getOffhandItem().getItem() instanceof ProsperousPouch && player.getMainHandItem().is(ItemTags.FLOWERS) && player.getOffhandItem().getDamageValue() > 0) {
			player.getOffhandItem().setDamageValue(0);
			player.awardStat(Stats.ITEM_USED.get(this));
			if (!player.getAbilities().instabuild) {
				player.getMainHandItem().shrink(1);
			}
			return InteractionResultHolder.sidedSuccess(itemStack, level.isClientSide());
		}
		return super.use(level, player, interactionHand);
	}

	@Override
	public boolean isValidRepairItem(ItemStack itemStack, ItemStack itemStack2) {
		return itemStack2.is(ItemTags.FLOWERS) || super.isValidRepairItem(itemStack, itemStack2);
	}

	@Override
	public InteractionResult useOn(UseOnContext useOnContext) {
		ItemStack itemStack = useOnContext.getItemInHand();
		Level level = useOnContext.getLevel();
		Player player = useOnContext.getPlayer();
		BlockPos blockPos = useOnContext.getClickedPos();
		BlockPos blockPos2 = blockPos.relative(useOnContext.getClickedFace());

		if (player != null && player.getMainHandItem().getItem() instanceof ProsperousPouch && player.getOffhandItem().is(ItemTags.FLOWERS) && player.getMainHandItem().getDamageValue() > 0) {
			player.getMainHandItem().setDamageValue(0);
			player.awardStat(Stats.ITEM_USED.get(this));
			if (!player.getAbilities().instabuild) {
				player.getOffhandItem().shrink(1);
			}
		}
		else if (player != null && player.getOffhandItem().getItem() instanceof ProsperousPouch && player.getMainHandItem().is(ItemTags.FLOWERS) && player.getOffhandItem().getDamageValue() > 0) {
			player.getOffhandItem().setDamageValue(0);
			player.awardStat(Stats.ITEM_USED.get(this));
			if (!player.getAbilities().instabuild) {
				player.getMainHandItem().shrink(1);
			}
		}

		if (itemStack.getDamageValue() >= itemStack.getMaxDamage() - 1) {
			return super.useOn(useOnContext);
		}
		if (ProsperousPouch.growCrop(useOnContext.getItemInHand(), level, blockPos, player)) {
			if (!level.isClientSide) {
				useOnContext.getPlayer().gameEvent(GameEvent.ITEM_INTERACT_FINISH);
				level.levelEvent(1505, blockPos, 0);
			}
			return InteractionResult.sidedSuccess(level.isClientSide);
		}
		BlockState blockState = level.getBlockState(blockPos);
		boolean bl = blockState.isFaceSturdy(level, blockPos, useOnContext.getClickedFace());
		if (bl && ProsperousPouch.growWaterPlant(useOnContext.getItemInHand(), level, blockPos2, useOnContext.getClickedFace(), player)) {
			if (!level.isClientSide) {
				useOnContext.getPlayer().gameEvent(GameEvent.ITEM_INTERACT_FINISH);
				level.levelEvent(1505, blockPos2, 0);
			}
			return InteractionResult.sidedSuccess(level.isClientSide);
		}
		return InteractionResult.PASS;
	}

	public static boolean growCrop(ItemStack itemStack, Level level, BlockPos blockPos, Player player) {
		if (itemStack.getDamageValue() >= itemStack.getMaxDamage() - 1) {
			return false;
		}
		BonemealableBlock bonemealableBlock;
		BlockState blockState = level.getBlockState(blockPos);
		Block block = blockState.getBlock();
		//1.20.4↑//
		//if (block instanceof BonemealableBlock && (bonemealableBlock = (BonemealableBlock)((Object)block)).isValidBonemealTarget(level, blockPos, blockState))
		//1.20.1//
		if (block instanceof BonemealableBlock && (bonemealableBlock = (BonemealableBlock)((Object)block)).isValidBonemealTarget(level, blockPos, blockState, level.isClientSide))
		{

			if (level instanceof ServerLevel) {
				if (bonemealableBlock.isBonemealSuccess(level, level.random, blockPos, blockState)) {
					bonemealableBlock.performBonemeal((ServerLevel) level, level.random, blockPos, blockState);
				}
				if (player != null) {
					itemStack.hurtAndBreak(1, player, players -> players.broadcastBreakEvent(EquipmentSlot.MAINHAND));
				}
				else {
					itemStack.hurt(1, level.random, null);
				}
			}
			return true;
		}
		return false;
	}

	public static boolean growWaterPlant(ItemStack itemStack, Level level, BlockPos blockPos, @Nullable Direction direction, Player player) {
		if (itemStack.getDamageValue() >= itemStack.getMaxDamage() - 1) {
			return false;
		}
		if (!level.getBlockState(blockPos).is(Blocks.WATER) || level.getFluidState(blockPos).getAmount() != 8) {
			return false;
		}
		if (!(level instanceof ServerLevel)) {
			return true;
		}
		RandomSource randomSource = level.getRandom();
		block0 : for (int i = 0; i < 128; ++i) {
			BlockPos blockPos2 = blockPos;
			BlockState blockState = Blocks.SEAGRASS.defaultBlockState();
			for (int j = 0; j < i / 16; ++j) {
				if (level.getBlockState(blockPos2 = blockPos2.offset(randomSource.nextInt(3) - 1, (randomSource.nextInt(3) - 1) * randomSource.nextInt(3) / 2, randomSource.nextInt(3) - 1)).isCollisionShapeFullBlock(level, blockPos2)) continue block0;
			}
			Holder<Biome> holder2 = level.getBiome(blockPos2);
			if (holder2.is(BiomeTags.PRODUCES_CORALS_FROM_BONEMEAL)) {
				if (i == 0 && direction != null && direction.getAxis().isHorizontal()) {
					blockState = BuiltInRegistries.BLOCK.getTag(BlockTags.WALL_CORALS).flatMap(named -> named.getRandomElement(level.random)).map(holder -> ((Block)holder.value()).defaultBlockState()).orElse(blockState);
					if (blockState.hasProperty(BaseCoralWallFanBlock.FACING)) {
						blockState = (BlockState)blockState.setValue(BaseCoralWallFanBlock.FACING, direction);
					}
				} else if (randomSource.nextInt(4) == 0) {
					blockState = BuiltInRegistries.BLOCK.getTag(BlockTags.UNDERWATER_BONEMEALS).flatMap(named -> named.getRandomElement(level.random)).map(holder -> ((Block)holder.value()).defaultBlockState()).orElse(blockState);
				}
			}
			if (blockState.is(BlockTags.WALL_CORALS, blockStateBase -> blockStateBase.hasProperty(BaseCoralWallFanBlock.FACING))) {
				for (int j = 0; !blockState.canSurvive(level, blockPos2) && j < 4; ++j) {
					blockState = (BlockState)blockState.setValue(BaseCoralWallFanBlock.FACING, Direction.Plane.HORIZONTAL.getRandomDirection(randomSource));
				}
			}
			if (!blockState.canSurvive(level, blockPos2)) continue;
			BlockState blockState2 = level.getBlockState(blockPos2);
			if (blockState2.is(Blocks.WATER) && level.getFluidState(blockPos2).getAmount() == 8) {
				level.setBlock(blockPos2, blockState, 3);
				continue;
			}
			if (!blockState2.is(Blocks.SEAGRASS) || randomSource.nextInt(10) != 0) continue;
			((BonemealableBlock)((Object)Blocks.SEAGRASS)).performBonemeal((ServerLevel)level, randomSource, blockPos2, blockState2);
		}
		if (player != null) {
			itemStack.hurtAndBreak(1, player, players -> players.broadcastBreakEvent(EquipmentSlot.MAINHAND));
		}
		else {
			itemStack.hurt(1, level.random, null);
		}
		return true;
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
