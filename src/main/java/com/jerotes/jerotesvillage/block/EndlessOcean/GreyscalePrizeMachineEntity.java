package com.jerotes.jerotesvillage.block.EndlessOcean;

import com.jerotes.jerotesvillage.block.Other.TabletPressMachine;
import com.jerotes.jerotesvillage.block.Other.VillagerLetterBox;
import com.jerotes.jerotesvillage.entity.Animal.Fish.BenthicFish.GreyscaleEntity;
import com.jerotes.jerotesvillage.init.JerotesVillageBlockEntityType;
import com.jerotes.jerotesvillage.init.JerotesVillageEntityType;
import com.jerotes.jerotesvillage.item.Greyscale;
import com.jerotes.jerotesvillage.inventory.JerotesChestMenu;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.core.NonNullList;
import net.minecraft.core.Vec3i;
import net.minecraft.core.particles.ParticleOptions;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.chat.Component;
import net.minecraft.network.protocol.game.ClientboundBlockEntityDataPacket;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.util.RandomSource;
import net.minecraft.world.Container;
import net.minecraft.world.ContainerHelper;
import net.minecraft.world.WorldlyContainer;
import net.minecraft.world.entity.MobSpawnType;
import net.minecraft.world.entity.item.ItemEntity;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.entity.*;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.storage.loot.LootParams;
import net.minecraft.world.level.storage.loot.parameters.LootContextParamSets;
import net.minecraft.world.level.storage.loot.parameters.LootContextParams;
import net.minecraft.world.phys.Vec3;
import net.minecraftforge.common.util.LazyOptional;
import net.minecraftforge.items.IItemHandler;
import net.minecraftforge.items.wrapper.SidedInvWrapper;

import javax.annotation.Nullable;
import java.util.stream.IntStream;

public class GreyscalePrizeMachineEntity extends RandomizableContainerBlockEntity implements WorldlyContainer {
	private NonNullList<ItemStack> stacks = NonNullList.withSize(1, ItemStack.EMPTY);
	private final ContainerOpenersCounter openersCounter = new ContainerOpenersCounter(){

		@Override
		protected void onOpen(Level level, BlockPos blockPos, BlockState blockState) {
			GreyscalePrizeMachineEntity.this.playSound(blockState, SoundEvents.METAL_PLACE);
		}

		@Override
		protected void onClose(Level level, BlockPos blockPos, BlockState blockState) {
			GreyscalePrizeMachineEntity.this.playSound(blockState, SoundEvents.METAL_PLACE);
		}

		@Override
		protected void openerCountChanged(Level level, BlockPos blockPos, BlockState blockState, int n, int n2) {
		}

		@Override
		protected boolean isOwnContainer(Player player) {
			if (player.containerMenu instanceof JerotesChestMenu) {
				Container container = ((JerotesChestMenu)player.containerMenu).getContainer();
				return container == GreyscalePrizeMachineEntity.this;
			} else {
				return false;
			}
		}
	};


	private final LazyOptional<? extends IItemHandler>[] handlers = SidedInvWrapper.create(this, Direction.values());

	public GreyscalePrizeMachineEntity(BlockPos position, BlockState state) {
		super(JerotesVillageBlockEntityType.GREYSCALE_PRIZE_MACHINE.get(), position, state);
	}

	@Nullable
	protected static <T extends BlockEntity> BlockEntityTicker<T> createTableTicker(Level level, BlockEntityType<T> blockEntityType, BlockEntityType<? extends GreyscalePrizeMachineEntity> blockEntityType2) {
		return createTickerHelper(blockEntityType, blockEntityType2, (arg_0, arg_1, arg_2, arg_3) -> GreyscalePrizeMachineEntity.tick(arg_0, arg_1, arg_2, arg_3));
	}

	@Nullable
	protected static <E extends BlockEntity, A extends BlockEntity> BlockEntityTicker<A> createTickerHelper(BlockEntityType<A> blockEntityType, BlockEntityType<E> blockEntityType2, BlockEntityTicker<? super E> blockEntityTicker) {
		return blockEntityType2 == blockEntityType ? (BlockEntityTicker<A>)blockEntityTicker : null;
	}

	public static void tick(Level level, BlockPos blockPos, BlockState blockState, GreyscalePrizeMachineEntity greyscalePrizeMachineEntity) {
		if (isOpen(blockState)) {
			greyscalePrizeMachineEntity.open = Math.min(greyscalePrizeMachineEntity.open + 1, 160);
			greyscalePrizeMachineEntity.blockUpdated();
		}
		else {
			greyscalePrizeMachineEntity.open = 0;
		}

		double luck = (greyscalePrizeMachineEntity.fishLevel * 2 + 5) / 30f;
		if (greyscalePrizeMachineEntity.level instanceof ServerLevel) {
			if (greyscalePrizeMachineEntity.open <= 40) {
				greyscalePrizeMachineEntity.itemStack1 = greyscalePrizeMachineEntity.gift();
			}
			if (greyscalePrizeMachineEntity.open <= 80) {
				if (RandomSource.create().nextFloat() < luck && greyscalePrizeMachineEntity.open > 40) {
					greyscalePrizeMachineEntity.itemStack2 = greyscalePrizeMachineEntity.itemStack1;
				} else {
					greyscalePrizeMachineEntity.itemStack2 = greyscalePrizeMachineEntity.gift();
				}
			}
			if (greyscalePrizeMachineEntity.open <= 120) {
				if (RandomSource.create().nextFloat() < luck / 2f && greyscalePrizeMachineEntity.open > 80 &&
						greyscalePrizeMachineEntity.itemStack1 == greyscalePrizeMachineEntity.itemStack2) {
					greyscalePrizeMachineEntity.itemStack3 = greyscalePrizeMachineEntity.itemStack1;
				} else {
					greyscalePrizeMachineEntity.itemStack3 = greyscalePrizeMachineEntity.gift();
				}
			}
			if (greyscalePrizeMachineEntity.open == 40 || greyscalePrizeMachineEntity.open == 80 || greyscalePrizeMachineEntity.open == 120) {
				greyscalePrizeMachineEntity.playSound(blockState, SoundEvents.NOTE_BLOCK_BASS.get());
			}


			if (greyscalePrizeMachineEntity.open == 135) {
				//生成物品
				if (areItemsEqual(greyscalePrizeMachineEntity.itemStack1, greyscalePrizeMachineEntity.itemStack2) &&
						areItemsEqual(greyscalePrizeMachineEntity.itemStack2, greyscalePrizeMachineEntity.itemStack3)) {
					greyscalePrizeMachineEntity.drop(greyscalePrizeMachineEntity.itemStack1);
					greyscalePrizeMachineEntity.drop(greyscalePrizeMachineEntity.itemStack1);
					greyscalePrizeMachineEntity.drop(greyscalePrizeMachineEntity.itemStack1);
					greyscalePrizeMachineEntity.addParticles(ParticleTypes.HAPPY_VILLAGER);
					greyscalePrizeMachineEntity.playSound(blockState, SoundEvents.NOTE_BLOCK_BELL.get());
				}
				else if (areItemsEqual(greyscalePrizeMachineEntity.itemStack1, greyscalePrizeMachineEntity.itemStack2)) {
					greyscalePrizeMachineEntity.drop(greyscalePrizeMachineEntity.itemStack1);
					greyscalePrizeMachineEntity.addParticles(ParticleTypes.ELECTRIC_SPARK);
					greyscalePrizeMachineEntity.playSound(blockState, SoundEvents.NOTE_BLOCK_FLUTE.get());
				}
				else if (areItemsEqual(greyscalePrizeMachineEntity.itemStack1, greyscalePrizeMachineEntity.itemStack3)) {
					greyscalePrizeMachineEntity.drop(greyscalePrizeMachineEntity.itemStack1);
					greyscalePrizeMachineEntity.addParticles(ParticleTypes.ELECTRIC_SPARK);
					greyscalePrizeMachineEntity.playSound(blockState, SoundEvents.NOTE_BLOCK_FLUTE.get());
				}
				else if (areItemsEqual(greyscalePrizeMachineEntity.itemStack2, greyscalePrizeMachineEntity.itemStack3)) {
					greyscalePrizeMachineEntity.drop(greyscalePrizeMachineEntity.itemStack2);
					greyscalePrizeMachineEntity.addParticles(ParticleTypes.ELECTRIC_SPARK);
					greyscalePrizeMachineEntity.playSound(blockState, SoundEvents.NOTE_BLOCK_FLUTE.get());
				}
				else {
					greyscalePrizeMachineEntity.addParticles(ParticleTypes.SMOKE);
					greyscalePrizeMachineEntity.playSound(blockState, SoundEvents.NOTE_BLOCK_COW_BELL.get());
				}
			}

			if (greyscalePrizeMachineEntity.open >= 140) {
				greyscalePrizeMachineEntity.fishLevel = 0;
				greyscalePrizeMachineEntity.itemStack1 = Items.AIR.getDefaultInstance();
				greyscalePrizeMachineEntity.itemStack2 = Items.AIR.getDefaultInstance();
				greyscalePrizeMachineEntity.itemStack3 = Items.AIR.getDefaultInstance();
				if (isOpen(blockState) && blockState.getBlock() instanceof GreyscalePrizeMachine greyscalePrizeMachine) {
					greyscalePrizeMachine.setOpen(null, level, blockState, blockPos, false);
				}
				greyscalePrizeMachineEntity.open = 0;
			}
		}
	}
	private static boolean areItemsEqual(ItemStack stack1, ItemStack stack2) {
		return ItemStack.isSameItem(stack1, stack2);
	}
	public void addParticles(ParticleOptions particleTypes) {
		if (level != null && level instanceof ServerLevel serverLevel) {
			for (int i = 0; i < 32; ++i) {
				double d0 = (double) worldPosition.getX() + 0.5D + (RandomSource.create().nextFloat() - 0.5f) / 2;
				double d1 = (double) worldPosition.getY() + 1.05D;
				double d2 = (double) worldPosition.getZ() + 0.5D + (RandomSource.create().nextFloat() - 0.5f) / 2;
				serverLevel.sendParticles(particleTypes, d0, d1, d2, 0, 0.0D, 0.0D, 0.0D, 0);
			}
		}
	}
	public void drop(ItemStack result) {
		if (level != null) {
			if (result.getItem() instanceof Greyscale greyscale) {
				if (level instanceof ServerLevel serverLevel) {
					Vec3 vec3;
					if (level.getBlockState(this.worldPosition).getValue(TabletPressMachine.FACING) == Direction.NORTH) {
						vec3 = new Vec3(this.worldPosition.getX() + 0.5, this.worldPosition.getY() + 0.5, this.worldPosition.getZ() - 0.5);
					} else if (level.getBlockState(this.worldPosition).getValue(TabletPressMachine.FACING) == Direction.SOUTH) {
						vec3 = new Vec3(this.worldPosition.getX() + 0.5, this.worldPosition.getY() + 0.5, this.worldPosition.getZ() + 1.5);
					} else if (level.getBlockState(this.worldPosition).getValue(TabletPressMachine.FACING) == Direction.WEST) {
						vec3 = new Vec3(this.worldPosition.getX() - 0.5, this.worldPosition.getY() + 0.5, this.worldPosition.getZ() + 0.5);
					} else {
						vec3 = new Vec3(this.worldPosition.getX() + 1.5, this.worldPosition.getY() + 0.5, this.worldPosition.getZ() + 0.5);
					}
					GreyscaleEntity mob = JerotesVillageEntityType.GREYSCALE.get().spawn(serverLevel, BlockPos.containing(worldPosition.getX(), worldPosition.getY(), worldPosition.getZ()), MobSpawnType.EVENT);
					if (mob != null) {
						mob.moveTo(vec3);
						mob.setSize(greyscale.getFishSize(result));
					}
				}
			} else {
				ItemEntity drop;
				if (level.getBlockState(this.worldPosition).getValue(TabletPressMachine.FACING) == Direction.NORTH) {
					drop = new ItemEntity(level, this.worldPosition.getX() + 0.5, this.worldPosition.getY() + 0.5, this.worldPosition.getZ() - 0.5, result);
				} else if (level.getBlockState(this.worldPosition).getValue(TabletPressMachine.FACING) == Direction.SOUTH) {
					drop = new ItemEntity(level, this.worldPosition.getX() + 0.5, this.worldPosition.getY() + 0.5, this.worldPosition.getZ() + 1.5, result);
				} else if (level.getBlockState(this.worldPosition).getValue(TabletPressMachine.FACING) == Direction.WEST) {
					drop = new ItemEntity(level, this.worldPosition.getX() - 0.5, this.worldPosition.getY() + 0.5, this.worldPosition.getZ() + 0.5, result);
				} else {
					drop = new ItemEntity(level, this.worldPosition.getX() + 1.5, this.worldPosition.getY() + 0.5, this.worldPosition.getZ() + 0.5, result);
				}
				drop.setDeltaMovement(0, 0, 0);
				level.addFreshEntity(drop);
			}
		}
	}
	public ItemStack gift() {
		Level level = this.level;
		if (level instanceof ServerLevel serverLevel) {
			BlockPos blockPos = this.worldPosition;
			for (ItemStack drop : level.getServer().getLootData().getLootTable(new ResourceLocation(this.giftItem)).
					getRandomItems(new LootParams.Builder(serverLevel).
							withParameter(LootContextParams.ORIGIN, Vec3.atCenterOf(blockPos)).
							withParameter(LootContextParams.BLOCK_STATE, serverLevel.
									getBlockState(blockPos)).withOptionalParameter(LootContextParams.BLOCK_ENTITY, serverLevel.getBlockEntity(blockPos)).
							create(LootContextParamSets.EMPTY))) {
				return drop;
			}
		}
		return null;
	}

	public void blockUpdated() {
		if (level != null) {
			if (!this.level.isClientSide()) {
				this.setChanged();
				this.level.sendBlockUpdated(this.worldPosition, this.getBlockState(), this.getBlockState(), Block.UPDATE_ALL);
			}
		}
	}

	public String giftItem = "jerotesvillage:gameplay/jerotesvillage_other/greyscale_prize_machine";
	public int open = 0;
	public double fishLevel = 0;
	public int getOpen() {
		return open;
	}
	public static boolean isOpen(BlockState blockState) {
		return blockState.getValue(GreyscalePrizeMachine.OPEN);
	}
	public ItemStack itemStack1 = Items.AIR.getDefaultInstance();
	public ItemStack itemStack2 = Items.AIR.getDefaultInstance();
	public ItemStack itemStack3 = Items.AIR.getDefaultInstance();
	@Override
	public void saveAdditional(CompoundTag compound) {
		super.saveAdditional(compound);
		ContainerHelper.saveAllItems(compound, this.stacks);
		compound.putInt("Open", open);
		compound.putDouble("FishLevel", fishLevel);
		compound.putString("GiftItem", this.giftItem);
		if (itemStack1 != null)
			compound.put("itemStack1", itemStack1.save(new CompoundTag()));
		if (itemStack2 != null)
			compound.put("itemStack2", itemStack2.save(new CompoundTag()));
		if (itemStack3 != null)
			compound.put("itemStack3", itemStack3.save(new CompoundTag()));
	}
	@Override
	public void load(CompoundTag compound) {
		super.load(compound);
		this.stacks = NonNullList.withSize(this.getContainerSize(), ItemStack.EMPTY);
		ContainerHelper.loadAllItems(compound, this.stacks);
		open = compound.getInt("Open");
		fishLevel = compound.getDouble("FishLevel");
		this.giftItem = compound.getString("GiftItem");
		if (itemStack1 != null)
			itemStack1 = ItemStack.of(compound.getCompound("itemStack1"));
		if (itemStack2 != null)
			itemStack2 = ItemStack.of(compound.getCompound("itemStack2"));
		if (itemStack3 != null)
			itemStack3 = ItemStack.of(compound.getCompound("itemStack3"));
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
		return Component.translatable("block.jerotesvillage.greyscale_prize_machine");
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
		return Component.translatable("block.jerotesvillage.greyscale_prize_machine");
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
