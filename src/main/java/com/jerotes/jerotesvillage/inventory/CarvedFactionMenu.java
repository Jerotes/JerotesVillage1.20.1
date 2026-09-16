package com.jerotes.jerotesvillage.inventory;


import com.jerotes.jerotesvillage.init.JerotesVillageMenus;
import com.jerotes.jerotesvillage.network.JerotesVillagePlayerData;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.inventory.ContainerData;
import net.minecraft.world.inventory.SimpleContainerData;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraftforge.items.ItemStackHandler;

public class CarvedFactionMenu extends AbstractContainerMenu {
    public final Player entity;
    public final Level world;
    public final ItemStackHandler internal;
    public final ContainerData data;

    public CarvedFactionMenu(int id, Inventory inv) {
        super(JerotesVillageMenus.CARVED_FACTION.get(), id);
        this.entity = inv.player;
        this.world = inv.player.level();
        this.internal = new ItemStackHandler(0);

        this.data = new SimpleContainerData(2);

        if (!world.isClientSide) {
            JerotesVillagePlayerData.PlayerVariables vars = entity.getCapability(com.jerotes.jerotesvillage.network.JerotesVillagePlayerData.CAPABILITY, null)
                    .orElse(new JerotesVillagePlayerData.PlayerVariables());
            this.data.set(0, vars.HaveCopperCarvedCompanyRelationship ? 1 : 0);
            this.data.set(1, vars.CopperCarvedCompanyRelationship);
        }

        this.addDataSlots(this.data);
    }

    public boolean hasCopperCavedCompany() {
        return this.data.get(0) == 1;
    }

    public int getCopperCavedCompanyInt() {
        return this.data.get(1);
    }

    @Override
    public ItemStack quickMoveStack(Player player, int i) {
        return null;
    }

    @Override
    public boolean stillValid(Player player) {
        return true;
    }
}