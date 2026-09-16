package com.jerotes.jerotesvillage.client.layer;

import com.jerotes.jerotes.util.EntityAndItemFind;
import com.jerotes.jerotesvillage.JerotesVillage;
import com.jerotes.jerotesvillage.client.model.Modelglow_sparrow;
import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import net.minecraft.client.model.PlayerModel;
import net.minecraft.client.model.geom.EntityModelSet;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.entity.RenderLayerParent;
import net.minecraft.client.renderer.entity.layers.RenderLayer;
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.player.Player;

public class WonderlinFakeEarsLayer<T extends Player> extends RenderLayer<T, PlayerModel<T>> {
    private final Modelglow_sparrow model;
    private static final ResourceLocation LOCATION = new ResourceLocation(JerotesVillage.MODID, "textures/item/wonderlin_fake_ears_model_color.png");

    public WonderlinFakeEarsLayer(RenderLayerParent<T, PlayerModel<T>> renderLayerParent, EntityModelSet entityModelSet) {
        super(renderLayerParent);
        this.model = new Modelglow_sparrow(entityModelSet.bakeLayer(Modelglow_sparrow.LAYER_LOCATION));
    }

    @Override
    public void render(PoseStack poseStack, MultiBufferSource multiBufferSource, int n, T t, float f, float f2, float f3, float f4, float f5, float f6) {
        if (EntityAndItemFind.isTrueInvisible(t)) {
            return;
        }
        this.render(poseStack, multiBufferSource, n, t, f, f2, f5, f6);
    }

    private void render(PoseStack poseStack, MultiBufferSource multiBufferSource, int n, T t, float f, float f2, float f3, float f4) {
        poseStack.pushPose();
        VertexConsumer vertexConsumer = multiBufferSource.getBuffer(this.model.renderType(LOCATION));
        this.model.renderOnShoulder(poseStack, vertexConsumer, n, OverlayTexture.NO_OVERLAY, f, f2, f3, f4, t.tickCount);
        poseStack.popPose();
    }
}

