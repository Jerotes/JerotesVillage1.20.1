package com.jerotes.jerotesvillage.block.DroughtFire;

import com.jerotes.jerotesvillage.entity.Other.Other.PrimedSerponClassicalBombEntity;
import com.jerotes.jerotesvillage.event.AdvancementEvent;
import com.jerotes.jerotesvillage.init.JerotesVillageItems;
import net.minecraft.core.BlockPos;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.stats.Stats;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.entity.projectile.Projectile;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.Explosion;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.*;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraft.world.level.block.state.properties.BooleanProperty;
import net.minecraft.world.level.block.state.properties.NoteBlockInstrument;
import net.minecraft.world.level.gameevent.GameEvent;
import net.minecraft.world.level.material.MapColor;
import net.minecraft.world.phys.BlockHitResult;

import javax.annotation.Nullable;

public class SerponClassicalBomb extends Block {
	public static final BooleanProperty UNSTABLE = BlockStateProperties.UNSTABLE;
	
	public SerponClassicalBomb() {
		super(Properties.of().mapColor(MapColor.COLOR_ORANGE).instrument(NoteBlockInstrument.BELL).requiresCorrectToolForDrops().strength(5.0f, 6.0f).sound(SoundType.METAL));
	}

	public void onCaughtFire(BlockState state, Level world, BlockPos pos, @Nullable net.minecraft.core.Direction face, @Nullable LivingEntity igniter) {
		explode(world, pos, igniter);
	}

	public void onPlace(BlockState blockState, Level level, BlockPos blockPos, BlockState blockState2, boolean bl) {
		if (!blockState2.is(blockState.getBlock())) {
			if (level.hasNeighborSignal(blockPos)) {
				onCaughtFire(blockState, level, blockPos, null, null);
				level.removeBlock(blockPos, false);
			}
		}
	}

	public void neighborChanged(BlockState blockState, Level level, BlockPos blockPos, Block block, BlockPos blockPos2, boolean bl) {
		if (level.hasNeighborSignal(blockPos)) {
			onCaughtFire(blockState, level, blockPos, null, null);
			level.removeBlock(blockPos, false);
		}

	}

	public void wasExploded(Level level, BlockPos blockPos, Explosion p_57443_) {
		if (!level.isClientSide) {
			PrimedSerponClassicalBombEntity primedSerponClassicalBomb = new PrimedSerponClassicalBombEntity(level, (double)blockPos.getX() + 0.5D, (double)blockPos.getY(), (double)blockPos.getZ() + 0.5D, p_57443_.getIndirectSourceEntity());
			level.addFreshEntity(primedSerponClassicalBomb);
		}
	}

	@Deprecated //Forge: Prefer using IForgeBlock#catchFire
	public static void explode(Level level, BlockPos blockPos) {
		explode(level, blockPos, (LivingEntity)null);
	}

	@Deprecated //Forge: Prefer using IForgeBlock#catchFire
	private static void explode(Level level, BlockPos blockPos, @Nullable LivingEntity livingEntity) {
		if (!level.isClientSide) {
			PrimedSerponClassicalBombEntity primedSerponClassicalBomb = new PrimedSerponClassicalBombEntity(level, (double)blockPos.getX() + 0.5D, (double)blockPos.getY(), (double)blockPos.getZ() + 0.5D, livingEntity);
			level.addFreshEntity(primedSerponClassicalBomb);
			level.playSound((Player)null, primedSerponClassicalBomb.getX(), primedSerponClassicalBomb.getY(), primedSerponClassicalBomb.getZ(), SoundEvents.TNT_PRIMED, SoundSource.BLOCKS, 1.0F, 1.0F);
			level.gameEvent(livingEntity, GameEvent.PRIME_FUSE, blockPos);
		}
	}

	public InteractionResult use(BlockState blockState, Level level, BlockPos blockPos, Player player, InteractionHand interactionHand, BlockHitResult blockHitResult) {
		ItemStack itemstack = player.getItemInHand(interactionHand);
		if (!itemstack.is(Items.FLINT_AND_STEEL) && !itemstack.is(Items.FIRE_CHARGE) && !itemstack.is(JerotesVillageItems.PIGLIN_GOLDEN_FIRE.get())) {
			return super.use(blockState, level, blockPos, player, interactionHand, blockHitResult);
		}
		else {
			onCaughtFire(blockState, level, blockPos, blockHitResult.getDirection(), player);
			if (player instanceof ServerPlayer serverPlayer) {
				AdvancementEvent.AdvancementGive(serverPlayer, "jerotesvillage:a_million_times_hotter_than_tnt");
			}
			level.setBlock(blockPos, Blocks.AIR.defaultBlockState(), 11);
			Item item = itemstack.getItem();
			if (!player.isCreative()) {
				if (itemstack.is(Items.FLINT_AND_STEEL) || itemstack.is(JerotesVillageItems.PIGLIN_GOLDEN_FIRE.get())) {
					itemstack.hurtAndBreak(1, player, (player1) -> {
						player1.broadcastBreakEvent(interactionHand);
					});
				} else {
					itemstack.shrink(1);
				}
			}

			player.awardStat(Stats.ITEM_USED.get(item));
			return InteractionResult.sidedSuccess(level.isClientSide);
		}
	}

	public void onProjectileHit(Level level, BlockState blockState, BlockHitResult blockHitResult, Projectile projectile) {
		if (!level.isClientSide) {
			BlockPos blockpos = blockHitResult.getBlockPos();
			Entity entity = projectile.getOwner();
			if (projectile.isOnFire() && projectile.mayInteract(level, blockpos)) {
				onCaughtFire(blockState, level, blockpos, null, entity instanceof LivingEntity ? (LivingEntity)entity : null);
				level.removeBlock(blockpos, false);
			}
		}

	}

	public boolean dropFromExplosion(Explosion explosion) {
		return false;
	}

	protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> stateBuilder) {
		stateBuilder.add(UNSTABLE);
	}
}
