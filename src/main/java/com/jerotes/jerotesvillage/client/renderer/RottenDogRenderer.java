package com.jerotes.jerotesvillage.client.renderer;

import com.jerotes.jerotesvillage.JerotesVillage;
import com.jerotes.jerotesvillage.client.layer.RottenDogCollarLayer;
import com.jerotes.jerotesvillage.client.model.Modelrotten_dog;
import com.jerotes.jerotesvillage.entity.Animal.RottenDogEntity;
import com.mojang.blaze3d.vertex.PoseStack;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.entity.MobRenderer;
import net.minecraft.resources.ResourceLocation;

public class RottenDogRenderer extends MobRenderer<RottenDogEntity, Modelrotten_dog<RottenDogEntity>> {
    private static final ResourceLocation WOLF_LOCATION = new ResourceLocation(JerotesVillage.MODID, "textures/entity/rotten_dog.png");
    private static final ResourceLocation WOLF_TAME_LOCATION = new ResourceLocation(JerotesVillage.MODID, "textures/entity/rotten_dog_tame.png");
    private static final ResourceLocation WOLF_ANGRY_LOCATION = new ResourceLocation(JerotesVillage.MODID, "textures/entity/rotten_dog_angry.png");
    private static final ResourceLocation WOLF_TAME_ANGRY_LOCATION = new ResourceLocation(JerotesVillage.MODID, "textures/entity/rotten_dog_tame_angry.png");

    public RottenDogRenderer(EntityRendererProvider.Context context) {
        super(context, new Modelrotten_dog(context.bakeLayer(Modelrotten_dog.LAYER_LOCATION)), 0.8f);
        this.addLayer(new RottenDogCollarLayer(this));
    }

    @Override
    public void render(RottenDogEntity wolf, float f, float f2, PoseStack poseStack, MultiBufferSource multiBufferSource, int n) {
        if (wolf.isWet()) {
            float f3 = wolf.getWetShade(f2);
            this.model.setColor(f3, f3, f3);
        }
        super.render(wolf, f, f2, poseStack, multiBufferSource, n);
        if (wolf.isWet()) {
            this.model.setColor(1.0f, 1.0f, 1.0f);
        }
    }

    @Override
    protected void scale(RottenDogEntity entity, PoseStack poseStack, float f) {
        poseStack.scale(1.3f, 1.3f, 1.3f);
        super.scale(entity, poseStack, f);
    }

    @Override
    public ResourceLocation getTextureLocation(RottenDogEntity wolf) {
        if (wolf.isTame() && wolf.isAngry()) {
            return WOLF_TAME_ANGRY_LOCATION;
        }
        if (wolf.isTame()) {
            return WOLF_TAME_LOCATION;
        }
        if (wolf.isAngry()) {
            return WOLF_ANGRY_LOCATION;
        }
        return WOLF_LOCATION;
    }
}



