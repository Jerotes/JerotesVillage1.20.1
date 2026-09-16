package com.jerotes.jerotesvillage.client.layer;

import com.jerotes.jerotesvillage.entity.Animal.WildernessWolfEntity;
import com.mojang.blaze3d.vertex.PoseStack;
import net.minecraft.client.model.WolfModel;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.entity.RenderLayerParent;
import net.minecraft.client.renderer.entity.layers.RenderLayer;
import net.minecraft.resources.ResourceLocation;

public class WildernessWolfCollarLayer extends RenderLayer<WildernessWolfEntity, WolfModel<WildernessWolfEntity>> {
    private static final ResourceLocation WOLF_COLLAR_LOCATION = new ResourceLocation("textures/entity/wolf/wolf_collar.png");
    public WildernessWolfCollarLayer(RenderLayerParent<WildernessWolfEntity, WolfModel<WildernessWolfEntity>> renderLayerParent) {
        super(renderLayerParent);
    }

    @Override
    public void render(PoseStack poseStack, MultiBufferSource multiBufferSource, int n, WildernessWolfEntity wolf, float f, float f2, float f3, float f4, float f5, float f6) {
        if (!wolf.isTame() || wolf.isInvisible()) {
            return;
        }
        float[] arrf = wolf.getCollarColor().getTextureDiffuseColors();
        WildernessWolfCollarLayer.renderColoredCutoutModel(this.getParentModel(), WOLF_COLLAR_LOCATION, poseStack, multiBufferSource, n, wolf, arrf[0], arrf[1], arrf[2]);
    }
}

