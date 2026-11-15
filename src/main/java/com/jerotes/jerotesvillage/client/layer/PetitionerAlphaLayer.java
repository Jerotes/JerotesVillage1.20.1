package com.jerotes.jerotesvillage.client.layer;

import com.jerotes.jerotesvillage.client.model.Modelpetitioner;
import com.jerotes.jerotesvillage.client.renderer.PetitionerRenderer;
import com.jerotes.jerotesvillage.entity.Monster.WickedRidiculeCalls.PetitionerEntity;
import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.entity.RenderLayerParent;
import net.minecraft.client.renderer.entity.layers.RenderLayer;
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.minecraft.resources.ResourceLocation;
import org.jetbrains.annotations.NotNull;

public class PetitionerAlphaLayer extends RenderLayer<PetitionerEntity, Modelpetitioner<PetitionerEntity>> {
    private final ResourceLocation texture;

    public PetitionerAlphaLayer(RenderLayerParent<PetitionerEntity, Modelpetitioner<PetitionerEntity>> parent, ResourceLocation texture) {
        super(parent);
        this.texture = texture;
    }

    @Override
    public void render(@NotNull PoseStack poseStack, @NotNull MultiBufferSource multiBufferSource, int light,
                       @NotNull PetitionerEntity entity, float limbSwing, float limbSwingAmount,
                       float partialTicks, float ageInTicks, float netHeadYaw, float headPitch) {
        float alpha = PetitionerRenderer.alphaOf(entity);
        if (alpha >= 0.999F) {
            return;
        }
        VertexConsumer vertexConsumer = multiBufferSource.getBuffer(RenderType.entityTranslucent(this.texture));
        this.getParentModel().renderToBuffer(poseStack, vertexConsumer, light, OverlayTexture.NO_OVERLAY,
                1.0F, 1.0F, 1.0F, alpha);
    }
}
