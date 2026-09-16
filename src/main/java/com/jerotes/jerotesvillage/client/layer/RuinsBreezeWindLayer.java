package com.jerotes.jerotesvillage.client.layer;

import com.jerotes.jerotes.init.JerotesRenderType;
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

public class RuinsBreezeWindLayer extends RenderLayer<RuinsBreeze, Modelruins_breeze<RuinsBreeze>> {
    private static final float TOP_PART_ALPHA = 1.0f;
    private static final float MIDDLE_PART_ALPHA = 1.0f;
    private static final float BOTTOM_PART_ALPHA = 1.0f;
    private final ResourceLocation textureLoc;
    private final Modelruins_breeze<RuinsBreeze> model;

    public RuinsBreezeWindLayer(RenderLayerParent<RuinsBreeze, Modelruins_breeze<RuinsBreeze>> renderLayerParent, EntityModelSet entityModelSet, ResourceLocation resourceLocation) {
        super(renderLayerParent);
        this.model = new Modelruins_breeze(entityModelSet.bakeLayer(Modelruins_breeze.BREEZE_WIND_LAYER_LOCATION));
        this.textureLoc = resourceLocation;
    }

    @Override
    public void render(PoseStack poseStack, MultiBufferSource multiBufferSource, int n, RuinsBreeze breeze, float f, float f2, float f3, float f4, float f5, float f6) {
        float f7 = (float)breeze.tickCount + f3;
        this.model.prepareMobModel(breeze, f, f2, f3);
        this.getParentModel().copyPropertiesTo(this.model);
        VertexConsumer vertexConsumer = multiBufferSource.getBuffer(JerotesRenderType.flow(this.getTextureLocation(breeze), this.xOffset(f7) % 1.0f, 0.0f));
        this.model.setupAnim(breeze, f, f2, f4, f5, f6);
        this.model.windTop().skipDraw = true;
        this.model.windMiddle().skipDraw = true;
        this.model.windBottom().skipDraw = false;
        this.model.root().render(poseStack, vertexConsumer, n, OverlayTexture.NO_OVERLAY, 1.0f, 1.0f, 1.0f, 1.0f);
        this.model.windTop().skipDraw = true;
        this.model.windMiddle().skipDraw = false;
        this.model.windBottom().skipDraw = true;
        this.model.root().render(poseStack, vertexConsumer, n, OverlayTexture.NO_OVERLAY, 1.0f, 1.0f, 1.0f, 1.0f);
        this.model.windTop().skipDraw = false;
        this.model.windMiddle().skipDraw = true;
        this.model.windBottom().skipDraw = true;
        this.model.root().render(poseStack, vertexConsumer, n, OverlayTexture.NO_OVERLAY, 1.0f, 1.0f, 1.0f, 1.0f);
    }

    private float xOffset(float f) {
        return f * 0.02f;
    }

    @Override
    protected ResourceLocation getTextureLocation(RuinsBreeze breeze) {
        return this.textureLoc;
    }
}

