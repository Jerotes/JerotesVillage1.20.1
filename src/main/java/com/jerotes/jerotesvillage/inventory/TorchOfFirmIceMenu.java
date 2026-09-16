package com.jerotes.jerotesvillage.inventory;

import com.jerotes.jerotesvillage.block.BitterCold.TorchOfFirmIce;
import com.jerotes.jerotesvillage.block.BitterCold.TorchOfFirmIceEntity;
import com.jerotes.jerotesvillage.init.JerotesVillageMenus;
import net.minecraft.core.BlockPos;
import net.minecraft.world.Container;
import net.minecraft.world.SimpleContainer;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.inventory.ContainerData;
import net.minecraft.world.inventory.SimpleContainerData;
import net.minecraft.world.inventory.Slot;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;

public class TorchOfFirmIceMenu extends AbstractContainerMenu {
   private final Container container;
   private final ContainerData containerData;
   public final Level level;

   public TorchOfFirmIceMenu(int n, Inventory inventory) {
      this(n, inventory, new SimpleContainer(1), new SimpleContainerData(3));
   }

   public TorchOfFirmIceMenu(int n, Inventory inventory, Container container, ContainerData containerData) {
      super(JerotesVillageMenus.TORCH_OF_FIRM_ICE.get(), n);
      checkContainerSize(container, 1);
      checkContainerDataCount(containerData, 3);
      this.container = container;
      this.containerData = containerData;
      this.level = inventory.player.level();
      int x = this.containerData.get(0);
      int y = this.containerData.get(1);
      int z = this.containerData.get(2);
      this.addSlot(new TorchOfFirmIceMenu.PotionSlot(container, 0, 80, 46, level, new BlockPos(x, y, z)));

      for(int i = 0; i < 3; ++i) {
         for(int j = 0; j < 9; ++j) {
            this.addSlot(new Slot(inventory, j + i * 9 + 9, 8 + j * 18, 84 + i * 18));
         }
      }

      for(int k = 0; k < 9; ++k) {
         this.addSlot(new Slot(inventory, k, 8 + k * 18, 142));
      }

      this.addDataSlots(containerData);
   }

   public boolean stillValid(Player player) {
      if (level.getBlockState(getBlockPos()).getValue(TorchOfFirmIce.OPEN)) {
         return false;
      }
      return this.container.stillValid(player);
   }

   public ItemStack quickMoveStack(Player player, int slotIndex) {
      ItemStack originalStack = ItemStack.EMPTY;
      Slot slot = this.slots.get(slotIndex);

      if (slot != null && slot.hasItem()) {
         ItemStack slotStack = slot.getItem();
         originalStack = slotStack.copy();

         // 从容器槽位移到玩家背包
         if (slotIndex == 0) { // 假设0是容器槽位
            if (!this.moveItemStackTo(slotStack, 1, 37, true)) {
               return ItemStack.EMPTY;
            }
            slot.onQuickCraft(slotStack, originalStack);
         }
         // 从玩家背包移到容器槽位
         else {
            // 检查是否可以放入容器槽位
            if (this.slots.get(0).mayPlace(slotStack)) {
               // 尝试将物品移到容器槽位
               if (!this.moveItemStackTo(slotStack, 0, 1, false)) {
                  return ItemStack.EMPTY;
               }
            }
            // 如果是玩家背包上半部分 (1-27)
            else if (slotIndex >= 1 && slotIndex < 28) {
               // 尝试移到快捷栏 (28-36)
               if (!this.moveItemStackTo(slotStack, 28, 37, false)) {
                  return ItemStack.EMPTY;
               }
            }
            // 如果是快捷栏 (28-36)
            else if (slotIndex >= 28 && slotIndex < 37) {
               // 尝试移到背包上半部分 (1-27)
               if (!this.moveItemStackTo(slotStack, 1, 28, false)) {
                  return ItemStack.EMPTY;
               }
            }
            else {
               return ItemStack.EMPTY;
            }
         }

         if (slotStack.isEmpty()) {
            slot.set(ItemStack.EMPTY);
         } else {
            slot.setChanged();
         }

         if (slotStack.getCount() == originalStack.getCount()) {
            return ItemStack.EMPTY;
         }

         slot.onTake(player, slotStack);
      }

      return originalStack;
   }


   public BlockPos getBlockPos() {
      int x = this.containerData.get(0);
      int y = this.containerData.get(1);
      int z = this.containerData.get(2);
      return new BlockPos(x,y,z);
   }

   static class PotionSlot extends Slot {
      public final Level level;
      public final BlockPos blockPos;
      public PotionSlot(Container container, int n, int n2, int n3, Level level, BlockPos blockPos) {
         super(container, n, n2, n3);
         this.level = level;
         this.blockPos = blockPos;
      }

      public boolean mayPlace(ItemStack itemStack) {
         return super.mayPlace(itemStack);
      }

      @Override
      public int getMaxStackSize(ItemStack stack) {
         return 64;
      }
      @Override
      public int getMaxStackSize() {
         return 64;
      }

      @Override
      public void setChanged() {
         super.setChanged();
         if (level.getBlockEntity(blockPos) instanceof TorchOfFirmIceEntity torchOfFirmIce) {
            torchOfFirmIce.blockUpdated();
         }
      }
      @Override
      public void onTake(Player player, ItemStack itemStack) {
         super.onTake(player, itemStack);
      }
   }
}
