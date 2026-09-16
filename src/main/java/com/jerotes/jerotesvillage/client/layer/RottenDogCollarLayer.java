package com.jerotes.jerotesvillage.client.layer;

import com.jerotes.jerotesvillage.JerotesVillage;
import com.jerotes.jerotesvillage.client.model.Modelrotten_dog;
import com.jerotes.jerotesvillage.entity.Animal.RottenDogEntity;
import com.mojang.blaze3d.vertex.PoseStack;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.entity.RenderLayerParent;
import net.minecraft.client.renderer.entity.layers.RenderLayer;
import net.minecraft.resources.ResourceLocation;

public class RottenDogCollarLayer extends RenderLayer<RottenDogEntity, Modelrotten_dog<RottenDogEntity>> {
    private static final ResourceLocation WOLF_COLLAR_LOCATION = new ResourceLocation(JerotesVillage.MODID, "textures/entity/rotten_dog_collar.png");
    public RottenDogCollarLayer(RenderLayerParent<RottenDogEntity, Modelrotten_dog<RottenDogEntity>> renderLayerParent) {
        super(renderLayerParent);
    }

    @Override
    public void render(PoseStack poseStack, MultiBufferSource multiBufferSource, int n, RottenDogEntity wolf, float f, float f2, float f3, float f4, float f5, float f6) {
        if (!wolf.isTame() || wolf.isInvisible()) {
            return;
        }
        float[] arrf = wolf.getCollarColor().getTextureDiffuseColors();
        RottenDogCollarLayer.renderColoredCutoutModel(this.getParentModel(), WOLF_COLLAR_LOCATION, poseStack, multiBufferSource, n, wolf, arrf[0], arrf[1], arrf[2]);
    }
}

