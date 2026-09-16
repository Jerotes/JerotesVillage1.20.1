package com.jerotes.jerotesvillage.client.renderer;

import com.jerotes.jerotesvillage.block.EndlessOcean.GreyscalePrizeMachine;
import com.jerotes.jerotesvillage.block.EndlessOcean.GreyscalePrizeMachineEntity;
import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.math.Axis;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.blockentity.BlockEntityRenderer;
import net.minecraft.client.renderer.blockentity.BlockEntityRendererProvider;
import net.minecraft.client.renderer.entity.ItemRenderer;
import net.minecraft.core.Direction;
import net.minecraft.world.item.ItemDisplayContext;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.entity.BlockEntity;

public class GreyscalePrizeMachineRenderer implements BlockEntityRenderer<GreyscalePrizeMachineEntity> {
    private final ItemRenderer itemRenderer;
    public GreyscalePrizeMachineRenderer(BlockEntityRendererProvider.Context context) {
        this.itemRenderer = context.getItemRenderer();
    }

    @Override
    public void render(GreyscalePrizeMachineEntity greyscalePrizeMachine, float f, PoseStack poseStack, MultiBufferSource multiBufferSource, int n, int n2) {
        Level level = ((BlockEntity) greyscalePrizeMachine).getLevel();
        if (!greyscalePrizeMachine.getBlockState().getValue(GreyscalePrizeMachine.OPEN))
            return;
        //物品
        int n3 = (int) greyscalePrizeMachine.getBlockPos().asLong();
        ItemStack itemStack1 = greyscalePrizeMachine.itemStack1;
        ItemStack itemStack2 = greyscalePrizeMachine.itemStack2;
        ItemStack itemStack3 = greyscalePrizeMachine.itemStack3;
        float rot = 0;
        if (greyscalePrizeMachine.getBlockState().getValue(GreyscalePrizeMachine.FACING) == Direction.SOUTH) {
            rot = 0;
        }
        if (greyscalePrizeMachine.getBlockState().getValue(GreyscalePrizeMachine.FACING) == Direction.NORTH) {
            rot = 180;
        }
        if (greyscalePrizeMachine.getBlockState().getValue(GreyscalePrizeMachine.FACING) == Direction.EAST) {
            rot = 90;
        }
        if (greyscalePrizeMachine.getBlockState().getValue(GreyscalePrizeMachine.FACING) == Direction.WEST) {
            rot = 270;
        }
        if (itemStack1 != ItemStack.EMPTY) {
            poseStack.pushPose();
            poseStack.translate(0.5f, 0f, 0.5f);
            poseStack.mulPose(Axis.YP.rotationDegrees(rot));
            poseStack.translate(0 - 0.275F,0.775f, 0.325f);
            poseStack.scale(0.25f, 0.25f, 0.25f);
            this.itemRenderer.renderStatic(itemStack1, ItemDisplayContext.FIXED, n, n2,
                    poseStack, multiBufferSource, greyscalePrizeMachine.getLevel(), n3 + 1);
            poseStack.popPose();
        }
        if (itemStack1 != ItemStack.EMPTY) {
            poseStack.pushPose();
            poseStack.translate(0.5f, 0f, 0.5f);
            poseStack.mulPose(Axis.YP.rotationDegrees(rot));
            poseStack.translate(0,0.775f, 0.325f);
            poseStack.scale(0.25f, 0.25f, 0.25f);
            this.itemRenderer.renderStatic(itemStack2, ItemDisplayContext.FIXED, n, n2,
                    poseStack, multiBufferSource, greyscalePrizeMachine.getLevel(), n3 + 2);
            poseStack.popPose();
        }
        if (itemStack1 != ItemStack.EMPTY) {
            poseStack.pushPose();
            poseStack.translate(0.5f, 0f, 0.5f);
            poseStack.mulPose(Axis.YP.rotationDegrees(rot));
            poseStack.translate(0 + 0.275F,0.775f, 0.325f);
            poseStack.scale(0.25f, 0.25f, 0.25f);
            this.itemRenderer.renderStatic(itemStack3, ItemDisplayContext.FIXED, n, n2,
                    poseStack, multiBufferSource, greyscalePrizeMachine.getLevel(), n3 + 3);
            poseStack.popPose();
        }
    }
}

