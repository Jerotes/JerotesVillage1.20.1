package com.jerotes.jerotesvillage.block.Other;

import com.jerotes.jerotesvillage.block.BaseBlock.ABaseBlock;
import com.jerotes.jerotesvillage.init.JerotesVillageBlockSetTypes;
import com.jerotes.jerotesvillage.init.JerotesVillageBlocks;
import net.minecraft.ChatFormatting;
import net.minecraft.core.BlockPos;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.MutableComponent;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.npc.AbstractVillager;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.material.PushReaction;
import net.minecraft.world.level.pathfinder.BlockPathTypes;

import javax.annotation.Nullable;
import java.util.List;

public class VillagerMetalDoor extends ABaseBlock.ABaseDoorBlock {
    public VillagerMetalDoor() {
        super(JerotesVillageBlockSetTypes.VILLAGER_METAL, Properties.of().mapColor(JerotesVillageBlocks.VILLAGER_METAL_BLOCK.get().defaultMapColor()).requiresCorrectToolForDrops().strength(6f, 9f).noOcclusion().pushReaction(PushReaction.DESTROY));
    }

    @Override
    public void setOpen(@Nullable Entity entity, Level level, BlockState blockState, BlockPos blockPos, boolean bl) {
        if (entity != null && !(entity instanceof AbstractVillager)) {
            return;
        }
        super.setOpen(entity, level, blockState, blockPos, bl);
    }

    @Override
    public void appendHoverText(ItemStack itemStack, @Nullable BlockGetter blockGetter, List<Component> list, TooltipFlag tooltipFlag) {
        list.add(this.getDisplayName().withStyle(ChatFormatting.GRAY));
    }

    @Override
    public BlockPathTypes getBlockPathType(BlockState state, BlockGetter level, BlockPos pos, @org.jetbrains.annotations.Nullable Mob mob) {
        return BlockPathTypes.DOOR_WOOD_CLOSED;
    }

    public MutableComponent getDisplayName() {
        return Component.translatable(this.getDescriptionId() + ".desc");
    }
}