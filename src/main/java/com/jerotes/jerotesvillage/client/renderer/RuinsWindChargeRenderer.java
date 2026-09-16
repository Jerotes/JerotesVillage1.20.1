package com.jerotes.jerotesvillage.client.renderer;

import com.jerotes.jerotes.init.JerotesRenderType;
import com.jerotes.jerotesvillage.JerotesVillage;
import com.jerotes.jerotesvillage.client.model.Modelruins_wind_charge;
import com.jerotes.jerotesvillage.entity.Shoot.Magic.MagicShoot.RuinsWindChargeEntity;
import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.entity.EntityRenderer;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.minecraft.resources.ResourceLocation;

public class RuinsWindChargeRenderer extends EntityRenderer<RuinsWindChargeEntity> {
    private static final ResourceLocation TEXTURE_LOCATION = new ResourceLocation(JerotesVillage.MODID, "textures/entity/ruins_wind_charge.png");
    private final Modelruins_wind_charge model;

    public RuinsWindChargeRenderer(EntityRendererProvider.Context context) {
        super(context);
        this.model = new Modelruins_wind_charge(context.bakeLayer(Modelruins_wind_charge.LAYER_LOCATION));
    }

    @Override
    public void render(RuinsWindChargeEntity windCharge, float f, float f2, PoseStack poseStack, MultiBufferSource multiBufferSource, int n) {
        float f3 = (float)windCharge.tickCount + f2;
        VertexConsumer vertexConsumer = multiBufferSource.getBuffer(JerotesRenderType.flow(TEXTURE_LOCATION, this.xOffset(f3) % 1.0f, 0.0f));
        this.model.setupAnim(windCharge, 0.0f, 0.0f, f3, 0.0f, 0.0f);
        this.model.renderToBuffer(poseStack, vertexConsumer, n, OverlayTexture.NO_OVERLAY, 1.0f, 1.0f, 1.0f, 0.5f);
        super.render(windCharge, f, f2, poseStack, multiBufferSource, n);
    }

    protected float xOffset(float f) {
        return f * 0.03f;
    }

    @Override
    public ResourceLocation getTextureLocation(RuinsWindChargeEntity windCharge) {
        return TEXTURE_LOCATION;
    }
}

