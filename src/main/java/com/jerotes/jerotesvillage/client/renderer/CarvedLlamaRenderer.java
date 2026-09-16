package com.jerotes.jerotesvillage.client.renderer;

import com.jerotes.jerotesvillage.JerotesVillage;
import com.jerotes.jerotesvillage.client.layer.CarvedLlamaDecorLayer;
import com.jerotes.jerotesvillage.entity.Animal.CarvedLlamaEntity;
import com.mojang.blaze3d.vertex.PoseStack;
import net.minecraft.client.model.geom.ModelLayers;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.entity.LlamaRenderer;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.animal.horse.Llama;

public class CarvedLlamaRenderer extends LlamaRenderer {
    private static final ResourceLocation LOCATION = new ResourceLocation(JerotesVillage.MODID, "textures/entity/carved_llama.png");

    public CarvedLlamaRenderer(EntityRendererProvider.Context context) {
        super(context, ModelLayers.TRADER_LLAMA);
        this.addLayer(new CarvedLlamaDecorLayer(this, context.getModelSet()));
    }

    @Override
    public void render(Llama llama, float f, float f2, PoseStack poseStack, MultiBufferSource multiBufferSource, int n) {
        if (llama instanceof CarvedLlamaEntity carvedLlama && carvedLlama.isInSittingPose()) {
            poseStack.translate(0.0f, -0.25f, 0.0f);
        }
        super.render(llama, f, f2, poseStack, multiBufferSource, n);
    }

    @Override
    public ResourceLocation getTextureLocation(Llama llama) {
        return LOCATION;
    }
}
