package com.jerotes.jerotesvillage.client.layer;

import com.jerotes.jerotesvillage.JerotesVillage;
import com.jerotes.jerotesvillage.entity.Humanoid.Serpon.*;
import com.jerotes.jerotesvillage.init.JerotesVillageRenderType;
import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import net.minecraft.client.Minecraft;
import net.minecraft.client.model.EntityModel;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.entity.LivingEntityRenderer;
import net.minecraft.client.renderer.entity.RenderLayerParent;
import net.minecraft.client.renderer.entity.layers.RenderLayer;
import net.minecraft.resources.ResourceLocation;

public class SerponOtherBodyLayer<T extends SerponEntity, M extends EntityModel<T>> extends RenderLayer<T, M> {
    private static final ResourceLocation SERPON_LOCATION = new ResourceLocation(JerotesVillage.MODID, "textures/entity/serpon/serpon_other.png");
    private static final ResourceLocation SERPON_RIOTER_LOCATION = new ResourceLocation(JerotesVillage.MODID, "textures/entity/serpon/serpon_rioter_other.png");
    private static final ResourceLocation SERPON_PIONEER_LOCATION = new ResourceLocation(JerotesVillage.MODID, "textures/entity/serpon/serpon_pioneer_other.png");
    private static final ResourceLocation SERPON_COLONIAL_SOLDIER_LOCATION = new ResourceLocation(JerotesVillage.MODID, "textures/entity/serpon/serpon_colonial_soldier_other.png");
    private static final ResourceLocation SERPON_FIRE_APOSTLE_LOCATION = new ResourceLocation(JerotesVillage.MODID, "textures/entity/serpon/serpon_fire_apostle_other.png");
    private static final ResourceLocation SERPON_FLAME_WIZARD_LOCATION = new ResourceLocation(JerotesVillage.MODID, "textures/entity/serpon/serpon_flame_wizard_other.png");
    private static final ResourceLocation SERPON_INTRUDER_LOCATION = new ResourceLocation(JerotesVillage.MODID, "textures/entity/serpon/serpon_intruder_other.png");
    private final EntityModel<T> model;
    public SerponOtherBodyLayer(RenderLayerParent<T, M> renderLayerParent, M m) {
        super(renderLayerParent);
        this.model = m;
    }

    @Override
    public void render(PoseStack poseStack, MultiBufferSource multiBufferSource, int n, T t, float f, float f2, float f3, float f4, float f5, float f6) {
        Minecraft minecraft = Minecraft.getInstance();
        boolean bl = minecraft.shouldEntityAppearGlowing(t) && t.isInvisible();
        if (!t.isInvisible() || bl) {
            ResourceLocation resourceLocation = SERPON_LOCATION;
            if (t instanceof SerponRioterEntity) {
                resourceLocation = SERPON_RIOTER_LOCATION;
            } else if (t instanceof SerponPioneerEntity) {
                resourceLocation = SERPON_PIONEER_LOCATION;
            } else if (t instanceof SerponColonialSoldierEntity) {
                resourceLocation = SERPON_COLONIAL_SOLDIER_LOCATION;
            } else if (t instanceof SerponFireApostleEntity) {
                resourceLocation = SERPON_FIRE_APOSTLE_LOCATION;
            } else if (t instanceof SerponFlameWizardEntity) {
                resourceLocation = SERPON_FLAME_WIZARD_LOCATION;
            } else if (t instanceof SerponIntruderEntity) {
                resourceLocation = SERPON_INTRUDER_LOCATION;
            }
            this.getParentModel().copyPropertiesTo(this.model);
            this.model.prepareMobModel(t, f, f2, f3);
            this.model.setupAnim(t, f, f2, f4, f5, f6);
            VertexConsumer vertexConsumer = multiBufferSource.getBuffer(JerotesVillageRenderType.entityTranslucent(resourceLocation));
            this.model.renderToBuffer(poseStack, vertexConsumer, n, LivingEntityRenderer.getOverlayCoords(t, 0.0f), 1.0f, 1.0f, 1.0f, 1.0f);
        }
    }

    private float xOffset(float f) {
        return f * 0.08f;
    }
}

