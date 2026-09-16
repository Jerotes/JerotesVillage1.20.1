package com.jerotes.jerotesvillage.client.layer;

import com.jerotes.jerotesvillage.JerotesVillage;
import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import net.minecraft.client.model.LlamaModel;
import net.minecraft.client.model.geom.EntityModelSet;
import net.minecraft.client.model.geom.ModelLayers;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.entity.RenderLayerParent;
import net.minecraft.client.renderer.entity.layers.RenderLayer;
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.animal.horse.Llama;
import net.minecraft.world.item.DyeColor;

public class CarvedLlamaDecorLayer extends RenderLayer<Llama, LlamaModel<Llama>> {
    private static final ResourceLocation LLAMA = new ResourceLocation(JerotesVillage.MODID, "textures/entity/carved_llama_decor.png");
    private final LlamaModel<Llama> model;

    public CarvedLlamaDecorLayer(RenderLayerParent<Llama, LlamaModel<Llama>> renderLayerParent, EntityModelSet entityModelSet) {
        super(renderLayerParent);
        this.model = new LlamaModel(entityModelSet.bakeLayer(ModelLayers.LLAMA_DECOR));
    }

    @Override
    public void render(PoseStack poseStack, MultiBufferSource multiBufferSource, int n, Llama llama, float f, float f2, float f3, float f4, float f5, float f6) {
        ResourceLocation resourceLocation;
        DyeColor dyeColor = llama.getSwag();
        if (dyeColor != null) {
            return;
        }  else {
            resourceLocation = LLAMA;
        }
        this.getParentModel().copyPropertiesTo(this.model);
        this.model.setupAnim(llama, f, f2, f4, f5, f6);
        VertexConsumer vertexConsumer = multiBufferSource.getBuffer(RenderType.entityCutoutNoCull(resourceLocation));
        this.model.renderToBuffer(poseStack, vertexConsumer, n, OverlayTexture.NO_OVERLAY, 1.0f, 1.0f, 1.0f, 1.0f);
    }
}