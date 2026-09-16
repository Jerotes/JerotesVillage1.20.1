package com.jerotes.jerotesvillage.client.layer;

import com.jerotes.jerotesvillage.entity.Boss.TyrantOfTheMountainRealmEntity;
import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import net.minecraft.client.Minecraft;
import net.minecraft.client.model.EntityModel;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.entity.LivingEntityRenderer;
import net.minecraft.client.renderer.entity.RenderLayerParent;
import net.minecraft.client.renderer.entity.layers.RenderLayer;
import net.minecraft.resources.ResourceLocation;

public class TyrantOfTheMountainRealmRushBodyLayer<T extends TyrantOfTheMountainRealmEntity, M extends EntityModel<T>> extends RenderLayer<T, M> {
    private final EntityModel<T> model;
    private final ResourceLocation textureLocation;

    public TyrantOfTheMountainRealmRushBodyLayer(RenderLayerParent<T, M> renderLayerParent, M m, ResourceLocation resourceLocation) {
        super(renderLayerParent);
        this.model = m;
        this.textureLocation = resourceLocation;
    }

    @Override
    public void render(PoseStack poseStack, MultiBufferSource multiBufferSource, int n, T t, float f, float f2, float f3, float f4, float f5, float f6) {
        boolean bl;
        Minecraft minecraft = Minecraft.getInstance();
        boolean bl2 = bl = minecraft.shouldEntityAppearGlowing(t) && t.isInvisible();
        if (t.isInvisible() && !bl) {
            return;
        }
        if (!(t.isAlive() && t.getRushUseTick() > 20 && t.getRushUseTick() <= 120)) {
            return;
        }
        if (t.getRushUseTick() <= 60) {
            for (int i = 1; i < 6; ++i) {
                poseStack.pushPose();
                float tick;
                poseStack.translate(0f, 0f, 0.25f * 0.1f);
                this.getParentModel().copyPropertiesTo(this.model);
                this.model.prepareMobModel(t, f, f2, f3);
                this.model.setupAnim(t, f, f2, f4, f5, f6);
                VertexConsumer vertexConsumer = multiBufferSource.getBuffer(RenderType.energySwirl(this.textureLocation, 0, 0));
                this.model.renderToBuffer(poseStack, vertexConsumer, n, LivingEntityRenderer.getOverlayCoords(t, 0.0f), 1.0f, 1.0f, 1.0f, 1.0f);
                poseStack.popPose();
            }
        }
        else {
            for (int i = 1; i < 6; ++i) {
                poseStack.pushPose();
                float tick;
                if (t.getRushUseTick() > 90) {
                    tick = 121 - t.getRushUseTick();
                } else {
                    tick = t.getRushUseTick() - 60;
                }
                poseStack.translate(0f, 0f, 0.25f * i * (tick / 10));
                this.getParentModel().copyPropertiesTo(this.model);
                this.model.prepareMobModel(t, f, f2, f3);
                this.model.setupAnim(t, f, f2, f4, f5, f6);
                VertexConsumer vertexConsumer = multiBufferSource.getBuffer(RenderType.energySwirl(this.textureLocation, 0, 0));
                this.model.renderToBuffer(poseStack, vertexConsumer, n, LivingEntityRenderer.getOverlayCoords(t, 0.0f), 1.0f, 1.0f, 1.0f, 1.0f);
                poseStack.popPose();
            }
        }
    }
}

