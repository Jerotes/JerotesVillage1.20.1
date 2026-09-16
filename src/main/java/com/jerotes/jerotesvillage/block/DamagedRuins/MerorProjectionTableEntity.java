package com.jerotes.jerotesvillage.block.DamagedRuins;

import com.jerotes.jerotesvillage.entity.Other.Other.BossShowEntity;
import com.jerotes.jerotesvillage.init.JerotesVillageBlockEntityType;
import com.jerotes.jerotesvillage.init.JerotesVillageSoundEvents;
import com.jerotes.jerotesvillage.item.ItemBossDrop;
import com.jerotes.jerotesvillage.inventory.JerotesChestMenu;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.core.NonNullList;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.chat.Component;
import net.minecraft.network.protocol.game.ClientboundBlockEntityDataPacket;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.ContainerHelper;
import net.minecraft.world.WorldlyContainer;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.item.ItemEntity;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.entity.RandomizableContainerBlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraftforge.common.util.LazyOptional;
import net.minecraftforge.items.IItemHandler;
import net.minecraftforge.items.wrapper.SidedInvWrapper;

import javax.annotation.Nullable;
import java.util.UUID;
import java.util.stream.IntStream;

public class MerorProjectionTableEntity extends RandomizableContainerBlockEntity implements WorldlyContainer {
	public static void dropItemStackSelf(Level level, double p_18994_, double p_18995_, double p_18996_, ItemStack p_18997_) {
		if (!p_18997_.isEmpty()) {
			ItemEntity drop = new ItemEntity(level, p_18994_, p_18995_, p_18996_, p_18997_);
			drop.setPickUpDelay(10);
			drop.setUnlimitedLifetime();
			level.addFreshEntity(drop);
		}
	}
	private NonNullList<ItemStack> stacks = NonNullList.<ItemStack>withSize(1, ItemStack.EMPTY);
	private final LazyOptional<? extends IItemHandler>[] handlers = SidedInvWrapper.create(this, Direction.values());

	public MerorProjectionTableEntity(BlockPos position, BlockState state) {
		super(JerotesVillageBlockEntityType.MEROR_PROJECTION_TABLE.get(), position, state);
	}

	public static void summonBoss(BlockPos pos, MerorProjectionTableEntity merorProjectionTable, ItemStack itemStack, float facing, Level level) {
		if (merorProjectionTable == null) {
			return;
		}
		if (merorProjectionTable.summonBossed) {
			if (level instanceof ServerLevel serverLevel && serverLevel.getEntity(merorProjectionTable.summonBoss) instanceof LivingEntity livingEntity) {
				livingEntity.discard();
			}
			merorProjectionTable.summonBossed = false;
			merorProjectionTable.summonBoss = null;
			return;
		}
		BossShowEntity shows;
		if (itemStack.getItem() instanceof ItemBossDrop itemBossDrop) {
			shows = new BossShowEntity(itemBossDrop.getBossEntity().get(), level);
			shows.setBlockAboutX(pos.getX());
			shows.setBlockAboutY(pos.getY());
			shows.setBlockAboutZ(pos.getZ());
			shows.facing = facing;
			shows.setPosRaw(shows.getBlockAboutX() + 0.5, shows.getBlockAboutY() + 1/16f + 0.05f, shows.getBlockAboutZ() + 0.5);
			shows.setYRot(facing);
			shows.setYHeadRot(facing);
			shows.setYBodyRot(facing);
			level.addFreshEntity(shows);
			level.playSound(null, shows.getX(), shows.getY(), shows.getZ(), JerotesVillageSoundEvents.MEROR_TOOL_USE, SoundSource.BLOCKS, 0.75f, 0.8f);
			merorProjectionTable.summonBossed = true;
			merorProjectionTable.summonBoss = shows.getUUID();
		}
	}


	public boolean summonBossed = false;
	public UUID summonBoss;
	@Override
	public void load(CompoundTag compound) {
		super.load(compound);
		if (!this.tryLoadLootTable(compound))
			this.stacks = NonNullList.withSize(this.getContainerSize(), ItemStack.EMPTY);
		ContainerHelper.loadAllItems(compound, this.stacks);
		summonBossed = compound.getBoolean("SummonBossed");
		if (compound.get("SummonBoss") != null)
			summonBoss = compound.getUUID("SummonBoss");
	}
	@Override
	public void saveAdditional(CompoundTag compound) {
		super.saveAdditional(compound);
		if (!this.trySaveLootTable(compound)) {
			ContainerHelper.saveAllItems(compound, this.stacks);
		}
		compound.putBoolean("SummonBossed", summonBossed);
		if (summonBoss != null)
			compound.putUUID("SummonBoss", summonBoss);
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
		return Component.translatable("block.jerotesvillage.meror_projection_table");
	}

	@Override
	public int getMaxStackSize() {
		return 1;
	}

	@Override
	public AbstractContainerMenu createMenu(int id, Inventory inventory) {
		return JerotesChestMenu.threeRows(id, inventory);
	}

	@Override
	public Component getDisplayName() {
		return Component.literal("meror_projection_table");
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
		return stack.getItem() instanceof ItemBossDrop;
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
