package com.jerotes.jerotesvillage.block.Other;

import com.jerotes.jerotesvillage.init.JerotesVillageBlockEntityType;
import com.jerotes.jerotesvillage.inventory.JerotesChestMenu;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.core.NonNullList;
import net.minecraft.core.Vec3i;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.chat.Component;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.util.RandomSource;
import net.minecraft.world.Container;
import net.minecraft.world.ContainerHelper;
import net.minecraft.world.WorldlyContainer;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.enchantment.EnchantmentHelper;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.entity.*;
import net.minecraft.world.level.block.state.BlockState;

import javax.annotation.Nullable;
import java.util.stream.IntStream;

public class VillagerLetterBoxEntity extends RandomizableContainerBlockEntity implements WorldlyContainer {
    private NonNullList<ItemStack> items = NonNullList.withSize(9, ItemStack.EMPTY);
    private final ContainerOpenersCounter openersCounter = new ContainerOpenersCounter(){

        @Override
        protected void onOpen(Level level, BlockPos blockPos, BlockState blockState) {
            VillagerLetterBoxEntity.this.playSound(blockState, SoundEvents.METAL_PLACE);
        }

        @Override
        protected void onClose(Level level, BlockPos blockPos, BlockState blockState) {
            VillagerLetterBoxEntity.this.playSound(blockState, SoundEvents.METAL_PLACE);
        }

        @Override
        protected void openerCountChanged(Level level, BlockPos blockPos, BlockState blockState, int n, int n2) {
        }

        @Override
        protected boolean isOwnContainer(Player player) {
            if (player.containerMenu instanceof JerotesChestMenu) {
                Container container = ((JerotesChestMenu)player.containerMenu).getContainer();
                return container == VillagerLetterBoxEntity.this;
            } else {
                return false;
            }
        }
    };

    public VillagerLetterBoxEntity(BlockPos blockPos, BlockState blockState) {
        super(JerotesVillageBlockEntityType.VILLAGER_LETTER_BOX.get(), blockPos, blockState);
    }

    private int letterTickMax = 3000;
    public int letterTick = 0;
    @Override
    protected void saveAdditional(CompoundTag compoundTag) {
        super.saveAdditional(compoundTag);
        if (!this.trySaveLootTable(compoundTag)) {
            ContainerHelper.saveAllItems(compoundTag, this.items);
        }
        compoundTag.putInt("LetterTickMax", letterTickMax);
        compoundTag.putInt("LetterTick", letterTick);
    }
    @Override
    public void load(CompoundTag compoundTag) {
        super.load(compoundTag);
        this.items = NonNullList.withSize(this.getContainerSize(), ItemStack.EMPTY);
        if (!this.tryLoadLootTable(compoundTag)) {
            ContainerHelper.loadAllItems(compoundTag, this.items);
        }
        letterTickMax = compoundTag.getInt("LetterTickMax");
        letterTick = compoundTag.getInt("LetterTick");
    }


    public static void itemsTick(Level level, BlockPos blockPos, BlockState blockState, VillagerLetterBoxEntity villagerLetterBoxEntity) {
        villagerLetterBoxEntity.letterTick += 1;
        if (villagerLetterBoxEntity.letterTick >= 3000) {
            villagerLetterBoxEntity.letterTick = 0;
            NonNullList<ItemStack> nonNullList = villagerLetterBoxEntity.getItems();
            for (int i = 0; i < villagerLetterBoxEntity.getContainerSize(); ++i) {
                //数量
                int count = nonNullList.get(i).getCount();
                ItemStack itemStack = new ItemStack(Items.BOOK, count);
                if (Math.random() < (count * 0.01))  {
                    ItemStack itemStacks = new ItemStack(Items.BOOK, 1);
                    int enchant_levels = Math.min(32, count/2);
                    itemStack = EnchantmentHelper.enchantItem(RandomSource.create(), itemStacks, enchant_levels, false);
                }
                if (nonNullList.get(i).getItem() == Items.EMERALD) {
                    nonNullList.set(i, itemStack);
                    villagerLetterBoxEntity.setItems(nonNullList);
                    return;
                }
            }
        }
    }

    @Override
    public int getContainerSize() {
        return 9;
    }

    @Override
    protected NonNullList<ItemStack> getItems() {
        return this.items;
    }

    @Override
    protected void setItems(NonNullList<ItemStack> nonNullList) {
        this.items = nonNullList;
    }

    @Override
    protected Component getDefaultName() {
        return Component.translatable("block.jerotesvillage.villager_block_messenger");
    }

    @Override
    protected AbstractContainerMenu createMenu(int n, Inventory inventory) {
        return JerotesChestMenu.oneRow(n, inventory, this);
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

    @Override
    public int[] getSlotsForFace(Direction direction) {
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
}