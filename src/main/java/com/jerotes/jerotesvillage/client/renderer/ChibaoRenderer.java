package com.jerotes.jerotesvillage.client.renderer;

import com.jerotes.jerotesvillage.JerotesVillage;
import com.jerotes.jerotesvillage.client.layer.ChibaoHeadLayer;
import com.jerotes.jerotesvillage.client.layer.ChibaoItemInHandLayer;
import com.jerotes.jerotesvillage.client.model.Modelchibao;
import com.jerotes.jerotesvillage.entity.Humanoid.ChibaoEntity;
import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.math.Axis;
import net.minecraft.client.model.HumanoidArmorModel;
import net.minecraft.client.model.geom.ModelLayers;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.entity.MobRenderer;
import net.minecraft.client.renderer.entity.layers.ElytraLayer;
import net.minecraft.client.renderer.entity.layers.HumanoidArmorLayer;
import net.minecraft.resources.ResourceLocation;

public class ChibaoRenderer extends MobRenderer<ChibaoEntity, Modelchibao<ChibaoEntity>> {
    private static final ResourceLocation LOCATION = new ResourceLocation(JerotesVillage.MODID, "textures/entity/chibao.png");
    public ChibaoRenderer(EntityRendererProvider.Context context) {
        super(context, new Modelchibao(context.bakeLayer(Modelchibao.LAYER_LOCATION)), 0.80f);
        this.addLayer(new ChibaoHeadLayer<>(this, context.getModelSet(), context.getItemInHandRenderer()));
        this.addLayer(new ElytraLayer(this, context.getModelSet()));
        this.addLayer(new ChibaoItemInHandLayer<>(this, context.getItemInHandRenderer()));
        this.addLayer(new HumanoidArmorLayer<>(this, new HumanoidArmorModel(context.bakeLayer(ModelLayers.PLAYER_INNER_ARMOR)), new HumanoidArmorModel(context.bakeLayer(ModelLayers.PLAYER_OUTER_ARMOR)), context.getModelManager()));
    }

    @Override
    protected void scale(ChibaoEntity entity, PoseStack poseStack, float f) {
        super.scale(entity, poseStack, f);
    }

    @Override
    public ResourceLocation getTextureLocation(ChibaoEntity witch) {
        return LOCATION;
    }

    @Override
    protected void setupRotations(ChibaoEntity entity, PoseStack poseStack, float f, float f2, float f3) {
        super.setupRotations(entity, poseStack, f, f2, f3);
        if ((double)entity.walkAnimation.speed() < 0.01) {
            return;
        }
        float f5 = entity.walkAnimation.position(f3) + 6.0f;
        float f6 = (Math.abs(f5 % 13.0f - 6.5f) - 3.25f) / 3.25f;
        poseStack.mulPose(Axis.ZP.rotationDegrees(6.5f * f6));
    }
}
