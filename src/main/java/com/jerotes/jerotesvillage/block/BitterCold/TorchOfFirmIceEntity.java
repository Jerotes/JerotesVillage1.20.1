package com.jerotes.jerotesvillage.block.BitterCold;

import com.jerotes.jerotesvillage.block.Other.VillagerLetterBox;
import com.jerotes.jerotesvillage.init.JerotesVillageBlockEntityType;
import com.jerotes.jerotesvillage.inventory.JerotesChestMenu;
import com.jerotes.jerotesvillage.inventory.TorchOfFirmIceMenu;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.core.NonNullList;
import net.minecraft.core.Vec3i;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.chat.Component;
import net.minecraft.network.protocol.game.ClientboundBlockEntityDataPacket;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.Container;
import net.minecraft.world.ContainerHelper;
import net.minecraft.world.WorldlyContainer;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.inventory.ContainerData;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.entity.*;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.properties.DoubleBlockHalf;
import net.minecraft.world.phys.AABB;
import net.minecraftforge.common.util.LazyOptional;
import net.minecraftforge.items.IItemHandler;
import net.minecraftforge.items.wrapper.SidedInvWrapper;

import javax.annotation.Nullable;
import java.util.stream.IntStream;

public class TorchOfFirmIceEntity extends RandomizableContainerBlockEntity implements WorldlyContainer {
	private NonNullList<ItemStack> stacks = NonNullList.withSize(1, ItemStack.EMPTY);
	private final ContainerOpenersCounter openersCounter = new ContainerOpenersCounter(){

		@Override
		protected void onOpen(Level level, BlockPos blockPos, BlockState blockState) {
			TorchOfFirmIceEntity.this.playSound(blockState, SoundEvents.METAL_PLACE);
		}

		@Override
		protected void onClose(Level level, BlockPos blockPos, BlockState blockState) {
			TorchOfFirmIceEntity.this.playSound(blockState, SoundEvents.METAL_PLACE);
		}

		@Override
		protected void openerCountChanged(Level level, BlockPos blockPos, BlockState blockState, int n, int n2) {
		}

		@Override
		protected boolean isOwnContainer(Player player) {
			if (player.containerMenu instanceof JerotesChestMenu) {
				Container container = ((JerotesChestMenu)player.containerMenu).getContainer();
				return container == TorchOfFirmIceEntity.this;
			} else {
				return false;
			}
		}
	};
	protected final ContainerData dataAccess = new ContainerData() {
		@Override
		public int get(int i) {
            return switch (i) {
                case 0 -> TorchOfFirmIceEntity.this.getBlockPos().getX();
                case 1 -> TorchOfFirmIceEntity.this.getBlockPos().getY();
                case 2 -> TorchOfFirmIceEntity.this.getBlockPos().getZ();
                default -> 0;
            };
        }

		@Override
		public void set(int n, int n2) {
		}

		public int getCount() {
			return 3;
		}
	};


	private final LazyOptional<? extends IItemHandler>[] handlers = SidedInvWrapper.create(this, Direction.values());

	public static boolean up = false;
	public TorchOfFirmIceEntity(BlockPos position, BlockState state, boolean bl) {
		super(JerotesVillageBlockEntityType.TORCH_OF_FIRM_ICE.get(), position, state);
		up = bl;
	}
	public TorchOfFirmIceEntity(BlockPos position, BlockState state) {
		super(JerotesVillageBlockEntityType.TORCH_OF_FIRM_ICE.get(), position, state);
		up = false;
	}

	@Override
	public AABB getRenderBoundingBox() {
		BlockPos blockPos = this.getBlockPos();
		if (this.getBlockState().getValue(TorchOfFirmIce.HALF) == DoubleBlockHalf.UPPER) {
			blockPos = blockPos.below();
		}
		return new AABB(blockPos.getX() - 0.5D, blockPos.getY(), blockPos.getZ() - 0.5D,
				blockPos.getX() + 1.5D, blockPos.getY() + 3.0D, blockPos.getZ() + 1.5D);
	}

	@Nullable
	protected static <T extends BlockEntity> BlockEntityTicker<T> createTableTicker(Level level, BlockEntityType<T> blockEntityType, BlockEntityType<? extends TorchOfFirmIceEntity> blockEntityType2) {
		return createTickerHelper(blockEntityType, blockEntityType2, (arg_0, arg_1, arg_2, arg_3) -> TorchOfFirmIceEntity.tick(arg_0, arg_1, arg_2, arg_3));
	}

	@Nullable
	protected static <E extends BlockEntity, A extends BlockEntity> BlockEntityTicker<A> createTickerHelper(BlockEntityType<A> blockEntityType, BlockEntityType<E> blockEntityType2, BlockEntityTicker<? super E> blockEntityTicker) {
		return blockEntityType2 == blockEntityType ? (BlockEntityTicker<A>)blockEntityTicker : null;
	}

	public static void tick(Level level, BlockPos blockPos, BlockState blockState, TorchOfFirmIceEntity torchOfFirmIceEntity) {
		if (blockState.getValue(TorchOfFirmIce.HALF) == DoubleBlockHalf.UPPER) {
			return;
		}

		//40为完全未激活 0为完全激活
		if (!isOpen(blockState)) {
			torchOfFirmIceEntity.open = Math.min(torchOfFirmIceEntity.open + 1, 40);
		}
		else {
			torchOfFirmIceEntity.open = Math.max(torchOfFirmIceEntity.open - 1, 0);
		}

		if (!isOpen(blockState) || torchOfFirmIceEntity.open > 0) {
			torchOfFirmIceEntity.canUseCooldown = 1200;
		}
		else {
			torchOfFirmIceEntity.canUseCooldown = Math.max(torchOfFirmIceEntity.canUseCooldown - 1, 0);
		}

		if (isOpen(blockState) && torchOfFirmIceEntity.canUseCooldown <= 0 && blockState.getBlock() instanceof TorchOfFirmIce torchOfFirmIce) {
			torchOfFirmIce.setOpen(null, level, blockState, blockPos, false);
		}

		torchOfFirmIceEntity.tickCount += 1;
		if (torchOfFirmIceEntity.tickCount >= 100) {
			torchOfFirmIceEntity.tickCount = 0;
		}
	}

	public void blockUpdated() {
		if (!this.level.isClientSide()) {
			this.setChanged();
			this.level.sendBlockUpdated(this.worldPosition, this.getBlockState(), this.getBlockState(), Block.UPDATE_ALL);
		}
	}

	public int open = 0;
	public int tickCount = 0;
	public int canUseCooldown = 0;
	public int getOpen() {
		return open;
	}
	public static boolean isOpen(BlockState blockState) {
		return blockState.getValue(TorchOfFirmIce.OPEN);
	}
	@Override
	public void load(CompoundTag compound) {
		super.load(compound);
		this.stacks = NonNullList.withSize(this.getContainerSize(), ItemStack.EMPTY);
		ContainerHelper.loadAllItems(compound, this.stacks);
		open = compound.getInt("Open");
		canUseCooldown = compound.getInt("CanUseCooldown");
	}
	@Override
	public void saveAdditional(CompoundTag compound) {
		super.saveAdditional(compound);
		ContainerHelper.saveAllItems(compound, this.stacks);
		compound.putInt("Open", open);
		compound.putInt("CanUseCooldown", canUseCooldown);
	}

	public void shrink(int n) {
		this.stacks.get(0).shrink(n);
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
		return 1;
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
		return Component.translatable("block.jerotesvillage.torch_of_firm_ice");
	}
	@Override
	public int getMaxStackSize() {
		return 64;
	}
	@Override
	public AbstractContainerMenu createMenu(int id, Inventory inventory) {
		return new TorchOfFirmIceMenu(id, inventory, this, this.dataAccess);
	}
	@Override
	public Component getDisplayName() {
		return Component.translatable("block.jerotesvillage.torch_of_firm_ice");
	}
	@Override
	public NonNullList<ItemStack> getItems() {
		return this.stacks;
	}
	@Override
	protected void setItems(NonNullList<ItemStack> stacks) {
		this.stacks = stacks;
	}
	@Override
	public boolean canPlaceItem(int index, ItemStack stack) {
		return !up;
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

	@Override
	public void startOpen(Player player) {
		if (!this.remove && !player.isSpectator()) {
			this.openersCounter.incrementOpeners(player, this.getLevel(), this.getBlockPos(), this.getBlockState());
		}
	}

	@Override
	public void stopOpen(Player player) {
		if (!this.remove && !player.isSpectator()) {
			this.openersCounter.decrementOpeners(player, this.getLevel(), this.getBlockPos(), this.getBlockState());
		}
	}

	public void recheckOpen() {
		if (!this.remove) {
			this.openersCounter.recheckOpeners(this.getLevel(), this.getBlockPos(), this.getBlockState());
		}
	}

	void playSound(BlockState blockState, SoundEvent soundEvent) {
		Vec3i vec3i = blockState.getValue(VillagerLetterBox.FACING).getNormal();
		double d = (double)this.worldPosition.getX() + 0.5 + (double)vec3i.getX() / 2.0;
		double d2 = (double)this.worldPosition.getY() + 0.5 + (double)vec3i.getY() / 2.0;
		double d3 = (double)this.worldPosition.getZ() + 0.5 + (double)vec3i.getZ() / 2.0;
		this.level.playSound(null, d, d2, d3, soundEvent, SoundSource.BLOCKS, 0.5f, this.level.random.nextFloat() * 0.1f + 0.9f);
	}
}
