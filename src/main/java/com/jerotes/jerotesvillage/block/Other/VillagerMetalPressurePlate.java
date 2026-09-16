package com.jerotes.jerotesvillage.block.Other;

import com.jerotes.jerotesvillage.block.BaseBlock.ABaseBlock;
import com.jerotes.jerotesvillage.init.JerotesVillageBlockSetTypes;
import com.jerotes.jerotesvillage.init.JerotesVillageBlocks;
import net.minecraft.ChatFormatting;
import net.minecraft.core.BlockPos;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.MutableComponent;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.npc.AbstractVillager;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.properties.NoteBlockInstrument;
import net.minecraft.world.level.material.PushReaction;

import javax.annotation.Nullable;
import java.util.List;

public class VillagerMetalPressurePlate extends ABaseBlock.ABasePressurePlateBlock {
    public VillagerMetalPressurePlate() {
        super(JerotesVillageBlockSetTypes.VILLAGER_METAL, Properties.of().mapColor(JerotesVillageBlocks.VILLAGER_METAL_BLOCK.get().defaultMapColor()).forceSolidOn().instrument(NoteBlockInstrument.BASEDRUM).requiresCorrectToolForDrops().noCollission().strength(1.0f).pushReaction(PushReaction.DESTROY));
    }

    @Override
    public void entityInside(BlockState blockState, Level level, BlockPos blockPos, Entity entity) {
        if (entity instanceof AbstractVillager) {
            super.entityInside(blockState, level, blockPos, entity);
        }
    }

    @Override
    public void appendHoverText(ItemStack itemStack, @Nullable BlockGetter blockGetter, List<Component> list, TooltipFlag tooltipFlag) {
        list.add(this.getDisplayName().withStyle(ChatFormatting.GRAY));
    }

    public MutableComponent getDisplayName() {
        return Component.translatable(this.getDescriptionId() + ".desc");
    }

}

