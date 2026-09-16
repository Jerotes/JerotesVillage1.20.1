package com.jerotes.jerotesvillage.block.HarmoniousWonderland;

import com.jerotes.jerotesvillage.block.EndlessOcean.GreyscalePrizeMachine;
import com.jerotes.jerotesvillage.entity.Boss.TyrantOfTheMountainRealmEntity;
import com.jerotes.jerotesvillage.event.AdvancementEvent;
import com.jerotes.jerotesvillage.init.JerotesVillageBlockEntityType;
import com.jerotes.jerotesvillage.init.JerotesVillageBlocks;
import com.jerotes.jerotesvillage.init.JerotesVillageEntityType;
import com.jerotes.jerotesvillage.init.JerotesVillageItems;
import com.jerotes.jerotesvillage.init.JerotesVillageSoundEvents;
import com.jerotes.jerotesvillage.util.SpawnRules;
import com.jerotes.jerotesvillage.inventory.JerotesChestMenu;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.core.NonNullList;
import net.minecraft.core.particles.BlockParticleOption;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.chat.Component;
import net.minecraft.network.protocol.game.ClientboundBlockEntityDataPacket;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.sounds.SoundSource;
import net.minecraft.util.RandomSource;
import net.minecraft.world.ContainerHelper;
import net.minecraft.world.WorldlyContainer;
import net.minecraft.world.entity.MobSpawnType;
import net.minecraft.world.entity.item.ItemEntity;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityTicker;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.entity.RandomizableContainerBlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.AABB;
import net.minecraftforge.common.util.LazyOptional;
import net.minecraftforge.items.IItemHandler;
import net.minecraftforge.items.wrapper.SidedInvWrapper;

import javax.annotation.Nullable;
import java.util.stream.IntStream;

public class TyrantOfTheMountainRealmStatueBlockEntity extends RandomizableContainerBlockEntity implements WorldlyContainer {
	private NonNullList<ItemStack> stacks = NonNullList.<ItemStack>withSize(9, ItemStack.EMPTY);
	private final LazyOptional<? extends IItemHandler>[] handlers = SidedInvWrapper.create(this, Direction.values());

	public TyrantOfTheMountainRealmStatueBlockEntity(BlockPos position, BlockState state) {
		super(JerotesVillageBlockEntityType.TYRANT_OF_THE_MOUNTAIN_REALM_STATUE.get(), position, state);
	}

	@Override
	public AABB getRenderBoundingBox() {
		BlockPos blockPos = this.getBlockPos();
		return new AABB(blockPos.getX() - 0.5D, blockPos.getY(), blockPos.getZ() - 0.5D,
				blockPos.getX() + 1.5D, blockPos.getY() + TyrantOfTheMountainRealmStatue.PART_COUNT + 1.0D, blockPos.getZ() + 1.5D);
	}

	public void convert(Player player, ItemStack itemStack, Item item) {
		if (!(this.level instanceof ServerLevel serverLevel)) {
			return;
		}
		//击败镇山大王进度
		if (itemStack.is(JerotesVillageItems.CELESTIAL_COILVINE_BROKEN_VINE.get()) && player instanceof ServerPlayer serverPlayer) {
			AdvancementEvent.AdvancementGive(serverPlayer, "jerotesvillage:golden_armor_tyrant");
		}
		BlockPos blockPos = this.getBlockPos().relative(this.getBlockState().getValue(TyrantOfTheMountainRealmStatue.FACING));
		ItemEntity itemEntity = new ItemEntity(serverLevel, blockPos.getX() + 0.5D, blockPos.getY() + 0.5D, blockPos.getZ() + 0.5D, new ItemStack(item));
		itemEntity.setDefaultPickUpDelay();
		serverLevel.addFreshEntity(itemEntity);
		serverLevel.playSound(null, this.getBlockPos(), JerotesVillageSoundEvents.TYRANT_OF_THE_MOUNTAIN_REALM_GOLD, SoundSource.BLOCKS, 1.0f, 1.0f);
	}

	@Override
	public boolean hasCustomOutlineRendering(Player player) {
		return player != null && TyrantOfTheMountainRealmStatue.getConvertResult(player.getMainHandItem()) != null;
	}

	@Nullable
	protected static <T extends BlockEntity> BlockEntityTicker<T> createTableTicker(Level level, BlockEntityType<T> blockEntityType, BlockEntityType<? extends TyrantOfTheMountainRealmStatueBlockEntity> blockEntityType2) {
		return level.isClientSide ? null : createTickerHelper(blockEntityType, blockEntityType2, (arg_0, arg_1, arg_2, arg_3) -> TyrantOfTheMountainRealmStatueBlockEntity.serverTick(arg_0, arg_1, arg_2, arg_3));
	}

	protected final RandomSource random = RandomSource.create();
	@Nullable
	protected static <E extends BlockEntity, A extends BlockEntity> BlockEntityTicker<A> createTickerHelper(BlockEntityType<A> blockEntityType, BlockEntityType<E> blockEntityType2, BlockEntityTicker<? super E> blockEntityTicker) {
		return blockEntityType2 == blockEntityType ? (BlockEntityTicker<A>)blockEntityTicker : null;
	}
	public double getX(double p_20166_) {
		return (this.getBlockPos().getX() + 0.5f) + 2.25f * p_20166_;
	}
	public double getRandomX(double p_20209_) {
		return this.getX((2.0D * this.random.nextDouble() - 1.0D) * p_20209_);
	}
	public double getY(double p_20228_) {
		return (this.getBlockPos().getY()) + 4.6f * p_20228_;
	}
	public double getRandomY() {
		return this.getY(this.random.nextDouble());
	}
	public double getZ(double p_20247_) {
		return (this.getBlockPos().getZ() + 0.5f) + 2.25f * p_20247_;
	}
	public double getRandomZ(double p_20263_) {
		return this.getZ((2.0D * this.random.nextDouble() - 1.0D) * p_20263_);
	}

	public static void serverTick(Level level, BlockPos blockPos, BlockState blockState, TyrantOfTheMountainRealmStatueBlockEntity tyrantOfTheMountainRealmStatue) {

		if (tyrantOfTheMountainRealmStatue.getSummonTick() > 0) {
			if (!level.isClientSide()) {
				tyrantOfTheMountainRealmStatue.setSummonTick(tyrantOfTheMountainRealmStatue.getSummonTick() + 1);
				if (level instanceof ServerLevel serverLevel) {
					for (int i = 0; i < 64; ++i) {
						double findX = tyrantOfTheMountainRealmStatue.getRandomX(0.5f) ;
						double findY = tyrantOfTheMountainRealmStatue.getRandomY();
						double findZ = tyrantOfTheMountainRealmStatue.getRandomZ(0.5f);
						serverLevel.sendParticles(new BlockParticleOption(ParticleTypes.BLOCK, JerotesVillageBlocks.WONDERLAND_WOOD.get().defaultBlockState()),
								findX,
								findY,
								findZ,
								0, 0, 0.0, 0, 0.0);
					}
				}
			}
		}
		if (tyrantOfTheMountainRealmStatue.getSummonTick() >= 60) {
			tyrantOfTheMountainRealmStatue.summonBoss(level, blockPos, tyrantOfTheMountainRealmStatue);
		}
	}



	public void summonBoss(Level level, BlockPos blockPos, TyrantOfTheMountainRealmStatueBlockEntity tyrantOfTheMountainRealmStatue) {
		float rot = 0;
		if (tyrantOfTheMountainRealmStatue.getBlockState().getValue(GreyscalePrizeMachine.FACING) == Direction.SOUTH) {
			rot = 0;
		}
		if (tyrantOfTheMountainRealmStatue.getBlockState().getValue(GreyscalePrizeMachine.FACING) == Direction.NORTH) {
			rot = 180;
		}
		if (tyrantOfTheMountainRealmStatue.getBlockState().getValue(GreyscalePrizeMachine.FACING) == Direction.EAST) {
			rot = 90;
		}
		if (tyrantOfTheMountainRealmStatue.getBlockState().getValue(GreyscalePrizeMachine.FACING) == Direction.WEST) {
			rot = 270;
		}
		if (level instanceof ServerLevel serverLevel) {
			if (SpawnRules.BossSpawnFind(JerotesVillageEntityType.TYRANT_OF_THE_MOUNTAIN_REALM.get(), level, blockPos)) {
				TyrantOfTheMountainRealmEntity boss = JerotesVillageEntityType.TYRANT_OF_THE_MOUNTAIN_REALM.get().spawn(serverLevel, blockPos, MobSpawnType.EVENT);
				if (boss != null) {
					serverLevel.explode(boss, blockPos.getX() + 0.5, blockPos.getY() + 0.5, blockPos.getZ() + 0.5, 2, false, Level.ExplosionInteraction.NONE);
					boss.setYRot(rot);

					boss.setDeltaMovement(0, 0, 0);
					serverLevel.playSound(null, blockPos.getX() + 0.5, blockPos.getY() + 0.5, blockPos.getZ() + 0.5, JerotesVillageSoundEvents.TYRANT_OF_THE_MOUNTAIN_REALM_AMBIENT, SoundSource.NEUTRAL, 20.0f, 1.0f / (serverLevel.getRandom().nextFloat() * 0.4f + 1.2f) + 1 * 0.5f);
					if (!boss.level().isClientSide()) {
						boss.setUseSword(true);
					}
					if (!level.isClientSide()) {
						this.setSummonTick(0);
					}
					BlockPos base = blockPos;
					if (level.getBlockState(base).getBlock() instanceof TyrantOfTheMountainRealmStatue) {
						serverLevel.destroyBlock(base, true, null);
					}
				}
				else {
					if (!level.isClientSide()) {
						this.setSummonTick(0);
					}
				}
			}
			else {
				if (!level.isClientSide()) {
					this.setSummonTick(0);
				}
			}
		}
	}

	public int getSummonTick() {
		return this.summonTick;
	}
	public void setSummonTick(int n) {
		this.summonTick = n;
	}

	public int summonTick = 0;
	@Override
	public void load(CompoundTag compound) {
		super.load(compound);
		if (!this.tryLoadLootTable(compound))
			this.stacks = NonNullList.withSize(this.getContainerSize(), ItemStack.EMPTY);
		ContainerHelper.loadAllItems(compound, this.stacks);
		summonTick = compound.getInt("SummonTick");
	}
	@Override
	public void saveAdditional(CompoundTag compound) {
		super.saveAdditional(compound);
		if (!this.trySaveLootTable(compound)) {
			ContainerHelper.saveAllItems(compound, this.stacks);
		}
		compound.putInt("SummonTick", summonTick);
	}

	@Override
	public ClientboundBlockEntityDataPacket getUpdatePacket() {
		return ClientboundBlockEntityDataPacket.create(this);
	}

	@Override
	public CompoundTag getUpdateTag() {
		return this.saveWithFullMetadata();
	}

	@Override
	public int getContainerSize() {
		return stacks.size();
	}

	@Override
	public boolean isEmpty() {
		for (ItemStack itemstack : this.stacks)
			if (!itemstack.isEmpty())
				return false;
		return true;
	}

	@Override
	public Component getDefaultName() {
		return Component.translatable("block.jerotesvillage.tyrant_of_the_mountain_realm_statue");
	}

	@Override
	public int getMaxStackSize() {
		return 64;
	}

	@Override
	public AbstractContainerMenu createMenu(int id, Inventory inventory) {
		return JerotesChestMenu.threeRows(id, inventory);
	}

	@Override
	public Component getDisplayName() {
		return Component.literal("tyrant_of_the_mountain_realm_statue");
	}

	@Override
	protected NonNullList<ItemStack> getItems() {
		return this.stacks;
	}

	@Override
	protected void setItems(NonNullList<ItemStack> stacks) {
		this.stacks = stacks;
	}

	@Override
	public boolean canPlaceItem(int index, ItemStack stack) {
		return false;
	}

	@Override
	public int[] getSlotsForFace(Direction side) {
		return IntStream.range(0, this.getContainerSize()).toArray();
	}

	@Override
	public boolean canPlaceItemThroughFace(int index, ItemStack stack, @Nullable Direction direction) {
		return this.canPlaceItem(index, stack);
	}

	@Override
	public boolean canTakeItemThroughFace(int index, ItemStack stack, Direction direction) {
		return true;
	}

	@Override
	public void setRemoved() {
		super.setRemoved();
		for (LazyOptional<? extends IItemHandler> handler : handlers)
			handler.invalidate();
	}
}
