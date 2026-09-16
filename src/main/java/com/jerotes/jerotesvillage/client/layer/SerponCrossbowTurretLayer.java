package com.jerotes.jerotesvillage.client.layer;

import com.jerotes.jerotes.init.JerotesRenderType;
import com.jerotes.jerotesvillage.JerotesVillage;
import com.jerotes.jerotesvillage.entity.Other.Machine.SerponCrossbowTurretEntity;
import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import net.minecraft.client.Minecraft;
import net.minecraft.client.model.EntityModel;
import net.minecraft.client.model.Model;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.entity.LivingEntityRenderer;
import net.minecraft.client.renderer.entity.RenderLayerParent;
import net.minecraft.client.renderer.entity.layers.RenderLayer;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;

public class SerponCrossbowTurretLayer<T extends LivingEntity, M extends EntityModel<T>> extends RenderLayer<T, M> {
    private final EntityModel<T> model;
    private final ResourceLocation textureLocation;
    private static final ResourceLocation USE_LOCATION = new ResourceLocation(JerotesVillage.MODID, "textures/entity/serpon_crossbow_turret_use.png");
    private static final ResourceLocation STRING_LOCATION = new ResourceLocation(JerotesVillage.MODID, "textures/entity/serpon_crossbow_turret_string.png");

    public SerponCrossbowTurretLayer(RenderLayerParent<T, M> renderLayerParent, M m, ResourceLocation resourceLocation) {
        super(renderLayerParent);
        this.model = m;
        this.textureLocation = resourceLocation;
    }

    @Override
    public void render(PoseStack poseStack, MultiBufferSource multiBufferSource, int n, T t, float f, float f2, float f3, float f4, float f5, float f6) {
        float f7 = (float) t.tickCount + f3;
        boolean bl;
        Minecraft minecraft = Minecraft.getInstance();
        boolean bl2 = bl = minecraft.shouldEntityAppearGlowing((Entity) t) && ((Entity) t).isInvisible();
        if (((Entity) t).isInvisible() && !bl) {
            return;
        }
        this.getParentModel().copyPropertiesTo(this.model);
        this.model.prepareMobModel(t, f, f2, f3);
        this.model.setupAnim(t, f, f2, f4, f5, f6);
        if (Minecraft.getInstance().player == t.getControllingPassenger()) {
            VertexConsumer vertexConsumer = multiBufferSource.getBuffer(JerotesRenderType.glow(STRING_LOCATION));
            ((Model) this.model).renderToBuffer(poseStack, vertexConsumer, n, LivingEntityRenderer.getOverlayCoords(t, 0.0f), 1.0f, 1.0f, 1.0f, 0.15f);

            VertexConsumer vertexConsumer2 = multiBufferSource.getBuffer(JerotesRenderType.entityCutoutNoCull(USE_LOCATION));
            ((Model) this.model).renderToBuffer(poseStack, vertexConsumer2, n, LivingEntityRenderer.getOverlayCoords(t, 0.0f), 1.0f, 1.0f, 1.0f, 0.15f);
        }
        else {
            VertexConsumer vertexConsumer = multiBufferSource.getBuffer(JerotesRenderType.entityCutoutNoCull(this.textureLocation));
            ((Model) this.model).renderToBuffer(poseStack, vertexConsumer, n, LivingEntityRenderer.getOverlayCoords(t, 0.0f), 1.0f, 1.0f, 1.0f, 0.15f);
        }
        if (t instanceof SerponCrossbowTurretEntity serponCrossbowTurret && (serponCrossbowTurret.getMultishotLevel() > 0 || serponCrossbowTurret.getQuickChargeLevel() > 0 || serponCrossbowTurret.getPiercingLevel() > 0)) {
            VertexConsumer vertexConsumer2 = multiBufferSource.getBuffer(JerotesRenderType.glint());
            ((Model) this.model).renderToBuffer(poseStack, vertexConsumer2, n, LivingEntityRenderer.getOverlayCoords(t, 0.0f), 1.0f, 1.0f, 1.0f, 0.15f);
        }
    }

    private float yOffset(float f) {
        return f * 0.05f;
    }
}

