package com.jerotes.jerotesvillage.client.renderer;

import com.jerotes.jerotesvillage.JerotesVillage;
import com.jerotes.jerotesvillage.block.EndlessOcean.GreyscalePrizeMachine;
import com.jerotes.jerotesvillage.block.HarmoniousWonderland.TyrantOfTheMountainRealmStatue;
import com.jerotes.jerotesvillage.block.HarmoniousWonderland.TyrantOfTheMountainRealmStatueBlockEntity;
import com.jerotes.jerotesvillage.client.model.Modeltyrant_of_the_mountain_realm_show;
import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import com.mojang.math.Axis;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.LevelRenderer;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.OutlineBufferSource;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.blockentity.BlockEntityRenderer;
import net.minecraft.client.renderer.blockentity.BlockEntityRendererProvider;
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.minecraft.core.Direction;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.player.Player;

public class TyrantOfTheMountainRealmStatueRenderer implements BlockEntityRenderer<TyrantOfTheMountainRealmStatueBlockEntity> {
    private static final ResourceLocation LOCATION = new ResourceLocation(JerotesVillage.MODID, "textures/entity/tyrant_of_the_mountain_realm_statue.png");
    private final Modeltyrant_of_the_mountain_realm_show<?> model;
    public TyrantOfTheMountainRealmStatueRenderer(BlockEntityRendererProvider.Context context) {
        this.model = new Modeltyrant_of_the_mountain_realm_show(context.bakeLayer(Modeltyrant_of_the_mountain_realm_show.LAYER_LOCATION));
    }

    @Override
    public void render(TyrantOfTheMountainRealmStatueBlockEntity tyrantOfTheMountainRealmStatueBlockEntity, float f, PoseStack poseStack, MultiBufferSource multiBufferSource, int n, int n2) {
        if (tyrantOfTheMountainRealmStatueBlockEntity.getBlockState().getValue(TyrantOfTheMountainRealmStatue.PART) != 0) {
            return;
        }
        poseStack.pushPose();
        poseStack.translate(0.5f, 2f, 0.5f);
        poseStack.scale(1.3f, 1.3f, 1.3f);
        poseStack.mulPose(Axis.ZP.rotationDegrees(180));
        float rot = 0;
        if (tyrantOfTheMountainRealmStatueBlockEntity.getBlockState().getValue(GreyscalePrizeMachine.FACING) == Direction.SOUTH) {
            rot = 180;
        }
        if (tyrantOfTheMountainRealmStatueBlockEntity.getBlockState().getValue(GreyscalePrizeMachine.FACING) == Direction.NORTH) {
            rot = 0;
        }
        if (tyrantOfTheMountainRealmStatueBlockEntity.getBlockState().getValue(GreyscalePrizeMachine.FACING) == Direction.EAST) {
            rot = 270;
        }
        if (tyrantOfTheMountainRealmStatueBlockEntity.getBlockState().getValue(GreyscalePrizeMachine.FACING) == Direction.WEST) {
            rot = 90;
        }
        poseStack.mulPose(Axis.YP.rotationDegrees(rot));
        VertexConsumer vertexConsumer = multiBufferSource.getBuffer(RenderType.entityCutoutNoCull(LOCATION));
        model.renderToBuffer(poseStack, vertexConsumer, n, OverlayTexture.NO_OVERLAY, 1, 1, 1, 1.0f);
        Player player = Minecraft.getInstance().player;
        LevelRenderer levelRenderer = Minecraft.getInstance().levelRenderer;
        if (player != null && levelRenderer != null && levelRenderer.shouldShowEntityOutlines()
                && TyrantOfTheMountainRealmStatue.getConvertResult(player.getMainHandItem()) != null) {
            OutlineBufferSource outlineBufferSource = Minecraft.getInstance().renderBuffers().outlineBufferSource();
            outlineBufferSource.setColor(255, 255, 255, 255);
            model.renderToBuffer(poseStack, outlineBufferSource.getBuffer(RenderType.entityCutoutNoCull(LOCATION)), n, OverlayTexture.NO_OVERLAY, 1, 1, 1, 1.0f);
        }
        poseStack.popPose();
    }
}
