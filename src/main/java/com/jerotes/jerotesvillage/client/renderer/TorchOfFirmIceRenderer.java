package com.jerotes.jerotesvillage.client.renderer;

import com.jerotes.jerotesvillage.JerotesVillage;
import com.jerotes.jerotesvillage.block.BitterCold.TorchOfFirmIce;
import com.jerotes.jerotesvillage.block.BitterCold.TorchOfFirmIceEntity;
import com.jerotes.jerotesvillage.client.model.Modeltorch_of_firm_ice;
import com.jerotes.jerotesvillage.init.JerotesVillageBlocks;
import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import com.mojang.math.Axis;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.blockentity.BlockEntityRenderer;
import net.minecraft.client.renderer.blockentity.BlockEntityRendererProvider;
import net.minecraft.client.renderer.entity.ItemRenderer;
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.minecraft.core.Direction;
import net.minecraft.core.NonNullList;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.Mth;
import net.minecraft.world.item.ItemDisplayContext;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.ChestBlock;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.properties.DoubleBlockHalf;

public class TorchOfFirmIceRenderer implements BlockEntityRenderer<TorchOfFirmIceEntity> {
    private final Modeltorch_of_firm_ice<?> model;
    private final ItemRenderer itemRenderer;
    public TorchOfFirmIceRenderer(BlockEntityRendererProvider.Context context) {
        this.model = new Modeltorch_of_firm_ice(context.bakeLayer(Modeltorch_of_firm_ice.LAYER_LOCATION));
        this.itemRenderer = context.getItemRenderer();
    }

    @Override
    public void render(TorchOfFirmIceEntity torchOfFirmIceEntity, float f, PoseStack poseStack, MultiBufferSource multiBufferSource, int n, int n2) {
        Level level = ((BlockEntity)torchOfFirmIceEntity).getLevel();
        boolean bl = level != null;
        BlockState blockState = bl ? ((BlockEntity)torchOfFirmIceEntity).getBlockState() : (BlockState) JerotesVillageBlocks.TORCH_OF_FIRM_ICE.get().defaultBlockState().setValue(TorchOfFirmIce.FACING, Direction.SOUTH);

        float openAbout = Mth.clamp(torchOfFirmIceEntity.open, 0 ,40) / 40f;

        if (torchOfFirmIceEntity.getBlockState().getValue(TorchOfFirmIce.HALF) != DoubleBlockHalf.UPPER) {
            //物品
            NonNullList<ItemStack> nonNullList = torchOfFirmIceEntity.getItems();
            int n3 = (int)torchOfFirmIceEntity.getBlockPos().asLong();
            for (int i = 0; i < nonNullList.size(); ++i) {
                ItemStack itemStack = nonNullList.get(i);
                if (itemStack == ItemStack.EMPTY) continue;
                poseStack.pushPose();
                poseStack.translate(0.5f, 1.5 * openAbout + 0.5f, 0.5f);
                poseStack.mulPose(Axis.YP.rotationDegrees(torchOfFirmIceEntity.tickCount * 3.6f));
                poseStack.scale(0.5f, 0.5f, 0.5f);
                this.itemRenderer.renderStatic(itemStack, ItemDisplayContext.FIXED, n, n2, poseStack, multiBufferSource, torchOfFirmIceEntity.getLevel(), n3 + i);
                poseStack.popPose();
            }
            //冰
            poseStack.pushPose();
            poseStack.translate(0.5f, 1.5 * openAbout + 0.5f, 0.5f);
            poseStack.popPose();
            //底座
            poseStack.pushPose();
            poseStack.translate(0.5f, 1.5f, 0.5f);
            poseStack.mulPose(Axis.ZP.rotationDegrees(180));
            float f2 = blockState.getValue(ChestBlock.FACING).toYRot();
            poseStack.mulPose(Axis.YP.rotationDegrees(-f2));
            ResourceLocation resourceLocation = new ResourceLocation(JerotesVillage.MODID, "textures/entity/torch_of_firm_ice_by_fallensnow21.png");
            VertexConsumer vertexConsumer = multiBufferSource.getBuffer(RenderType.entityCutoutNoCull(resourceLocation));
            model.head.y = -2f + 24 * (1 - openAbout);
            model.medium.yScale = openAbout;
            model.renderToBuffer(poseStack, vertexConsumer, n, OverlayTexture.NO_OVERLAY, 1, 1, 1, 1.0f);
            poseStack.popPose();
        }
        else {
            poseStack.pushPose();
            poseStack.translate(0.5f, 0.5f, 0.5f);
            poseStack.mulPose(Axis.ZP.rotationDegrees(180));
            float f2 = blockState.getValue(ChestBlock.FACING).toYRot();
            poseStack.mulPose(Axis.YP.rotationDegrees(-f2));
            ResourceLocation resourceLocation = new ResourceLocation(JerotesVillage.MODID, "textures/entity/null.png");
            VertexConsumer vertexConsumer = multiBufferSource.getBuffer(RenderType.entityCutoutNoCull(resourceLocation));
            model.head.y = -2f + 24 * (1 - openAbout);
            model.medium.yScale = openAbout;
            model.renderToBuffer(poseStack, vertexConsumer, n, OverlayTexture.NO_OVERLAY, 1, 1, 1, 1.0f);
            poseStack.popPose();
        }
    }
}

