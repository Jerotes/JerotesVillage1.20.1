package com.jerotes.jerotesvillage.client.layer;

import com.jerotes.jerotesvillage.JerotesVillage;
import com.jerotes.jerotesvillage.entity.Boss.Biome.BrightLandBeastEntity;
import com.jerotes.jerotesvillage.init.JerotesVillageRenderType;
import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import net.minecraft.client.model.EntityModel;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.entity.LivingEntityRenderer;
import net.minecraft.client.renderer.entity.RenderLayerParent;
import net.minecraft.client.renderer.entity.layers.RenderLayer;
import net.minecraft.resources.ResourceLocation;

public class BrightLandBeastOtherBodyLayer<T extends BrightLandBeastEntity, M extends EntityModel<T>> extends RenderLayer<T, M> {
    private static final ResourceLocation OTHER_LOCATION = new ResourceLocation(JerotesVillage.MODID, "textures/entity/bright_land_beast_other.png");
    private static final ResourceLocation OTHER_BREAK_LOCATION = new ResourceLocation(JerotesVillage.MODID, "textures/entity/bright_land_beast_other_break.png");
    private static final ResourceLocation OTHER_LOW_LOCATION = new ResourceLocation(JerotesVillage.MODID, "textures/entity/bright_land_beast_other_low.png");
    private final EntityModel<T> model;
    public BrightLandBeastOtherBodyLayer(RenderLayerParent<T, M> renderLayerParent, M m) {
        super(renderLayerParent);
        this.model = m;
    }

    @Override
    public void render(PoseStack poseStack, MultiBufferSource multiBufferSource, int n, T t, float f, float f2, float f3, float f4, float f5, float f6) {
        if (t.isInvisible() || !t.isAlive()) {
            return;
        }
        if (t.getHealth() <= t.getMaxHealth()/4) {
            return;
        }
        ResourceLocation resourceLocation;
        if (t.getHealth() > t.getMaxHealth()/2) {
            resourceLocation = OTHER_LOCATION;
        }
        else if (t.getHealth() > t.getMaxHealth()/3) {
            resourceLocation = OTHER_BREAK_LOCATION;
        }
        else {
            resourceLocation = OTHER_LOW_LOCATION;
        }
        this.getParentModel().copyPropertiesTo(this.model);
        this.model.prepareMobModel(t, f, f2, f3);
        this.model.setupAnim(t, f, f2, f4, f5, f6);
        VertexConsumer vertexConsumer = multiBufferSource.getBuffer(JerotesVillageRenderType.entityTranslucent(resourceLocation));
        this.model.renderToBuffer(poseStack, vertexConsumer, n, LivingEntityRenderer.getOverlayCoords(t, 0.0f), 1.0f, 1.0f, 1.0f, 1.0f);
    }

    private float xOffset(float f) {
        return f * 0.08f;
    }
}

