package com.jerotes.jerotesvillage.client.layer;

import com.jerotes.jerotes.init.JerotesRenderType;
import com.jerotes.jerotes.util.EntityAndItemFind;
import com.jerotes.jerotesvillage.client.model.Modelruins_breeze;
import com.jerotes.jerotesvillage.entity.Monster.RuinsBreeze;
import com.jerotes.jerotesvillage.init.JerotesVillageRenderType;
import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import net.minecraft.client.model.geom.EntityModelSet;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.entity.RenderLayerParent;
import net.minecraft.client.renderer.entity.layers.RenderLayer;
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.minecraft.resources.ResourceLocation;

public class RuinsBreezeEyesLayer extends RenderLayer<RuinsBreeze, Modelruins_breeze<RuinsBreeze>> {
    private final ResourceLocation textureLoc;
    private final Modelruins_breeze<RuinsBreeze> model;

    public RuinsBreezeEyesLayer(RenderLayerParent<RuinsBreeze, Modelruins_breeze<RuinsBreeze>> renderLayerParent, EntityModelSet entityModelSet, ResourceLocation resourceLocation) {
        super(renderLayerParent);
        this.model = new Modelruins_breeze(entityModelSet.bakeLayer(Modelruins_breeze.BREEZE_EYES_LAYER_LOCATION));
        this.textureLoc = resourceLocation;
    }

    @Override
    public void render(PoseStack poseStack, MultiBufferSource multiBufferSource, int n, RuinsBreeze breeze, float f, float f2, float f3, float f4, float f5, float f6) {
        if (EntityAndItemFind.isTrueInvisible(breeze)) {
            return;
        }
        this.model.prepareMobModel(breeze, f, f2, f3);
        this.getParentModel().copyPropertiesTo(this.model);
        VertexConsumer vertexConsumer = multiBufferSource.getBuffer(JerotesRenderType.glow(this.textureLoc));
        this.model.setupAnim(breeze, f, f2, f4, f5, f6);
        this.model.root().render(poseStack, vertexConsumer, n, OverlayTexture.NO_OVERLAY, 1.0f, 1.0f, 1.0f, 1.0f);
    }

    @Override
    protected ResourceLocation getTextureLocation(RuinsBreeze breeze) {
        return this.textureLoc;
    }
}

