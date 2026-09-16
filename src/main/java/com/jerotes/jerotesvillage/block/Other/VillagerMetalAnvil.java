package com.jerotes.jerotesvillage.block.Other;

import com.jerotes.jerotesvillage.inventory.AnvilMenuInterface;
import net.minecraft.ChatFormatting;
import net.minecraft.core.BlockPos;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.MutableComponent;
import net.minecraft.world.MenuProvider;
import net.minecraft.world.SimpleMenuProvider;
import net.minecraft.world.inventory.AnvilMenu;
import net.minecraft.world.inventory.ContainerLevelAccess;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.AnvilBlock;
import net.minecraft.world.level.block.SoundType;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.material.MapColor;
import net.minecraft.world.level.material.PushReaction;

import javax.annotation.Nullable;
import java.util.List;

public class VillagerMetalAnvil extends AnvilBlock {
    private static final Component CONTAINER_TITLE = Component.translatable("container.repair");
    public VillagerMetalAnvil() {
        super(Properties.of().mapColor(MapColor.COLOR_BROWN).strength(6f, 1200.0f).sound(SoundType.ANVIL).pushReaction(PushReaction.BLOCK).requiresCorrectToolForDrops());
    }

    @Nullable
    public MenuProvider getMenuProvider(BlockState p_48821_, Level p_48822_, BlockPos p_48823_) {
        return new SimpleMenuProvider((p_48785_, p_48786_, p_48787_) -> {
            AnvilMenu anvilMenu = new AnvilMenu(p_48785_, p_48786_, ContainerLevelAccess.create(p_48822_, p_48823_));
            if (anvilMenu instanceof AnvilMenuInterface anvilMenuInterface) {
                anvilMenuInterface.setVillagerMetal(true);
            }
            return anvilMenu;
        }, CONTAINER_TITLE);
    }

    @Override
    public void appendHoverText(ItemStack itemStack, @Nullable BlockGetter blockGetter, List<Component> list, TooltipFlag tooltipFlag) {
        list.add(this.getDisplayName_0().withStyle(ChatFormatting.GRAY));
        list.add(this.getDisplayName_1().withStyle(ChatFormatting.GRAY));
        super.appendHoverText(itemStack, blockGetter, list, tooltipFlag);
    }

    public MutableComponent getDisplayName_0() {
        return Component.translatable(this.getDescriptionId() + ".desc_0");
    }
    public MutableComponent getDisplayName_1() {
        return Component.translatable(this.getDescriptionId() + ".desc_1");
    }
}