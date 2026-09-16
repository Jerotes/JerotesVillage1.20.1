package com.jerotes.jerotesvillage.block.Other;

import net.minecraft.ChatFormatting;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.MutableComponent;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.SoundType;
import net.minecraft.world.level.material.MapColor;
import net.minecraft.world.level.material.PushReaction;

import javax.annotation.Nullable;
import java.util.List;

public class SecondRounderAnchorCore extends Block {
    public SecondRounderAnchorCore() {
        super(Properties.of().mapColor(MapColor.COLOR_BROWN).requiresCorrectToolForDrops().strength(10f, 10000f).sound(SoundType.LODESTONE).pushReaction(PushReaction.BLOCK));
    }

    @Override
    public void appendHoverText(ItemStack itemStack, @Nullable BlockGetter blockGetter, List<Component> list, TooltipFlag tooltipFlag) {
        list.add(this.getDisplayName().withStyle(ChatFormatting.GRAY));
    }

    public MutableComponent getDisplayName() {
        return Component.translatable(this.getDescriptionId() + ".desc");
    }
}