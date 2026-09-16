package com.jerotes.jerotesvillage.client.renderer;

import com.jerotes.jerotes.client.layer.SpecialItemInHandLayer;
import com.jerotes.jerotesvillage.JerotesVillage;
import com.jerotes.jerotesvillage.client.layer.MerorOathguardMechGlowLayer;
import com.jerotes.jerotesvillage.client.model.Modelmeror_oathguard_mech;
import com.jerotes.jerotesvillage.entity.Neutral.MerorMachine.MerorOathguardMechEntity;
import com.mojang.blaze3d.vertex.PoseStack;
import net.minecraft.client.model.HumanoidArmorModel;
import net.minecraft.client.model.geom.ModelLayers;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.entity.MobRenderer;
import net.minecraft.client.renderer.entity.layers.CustomHeadLayer;
import net.minecraft.client.renderer.entity.layers.ElytraLayer;
import net.minecraft.client.renderer.entity.layers.HumanoidArmorLayer;
import net.minecraft.resources.ResourceLocation;

public class MerorOathguardMechRenderer extends MobRenderer<MerorOathguardMechEntity, Modelmeror_oathguard_mech<MerorOathguardMechEntity>> {
    private static final ResourceLocation LOCATION = new ResourceLocation(JerotesVillage.MODID, "textures/entity/meror_machine/meror_oathguard_mech.png");

    public MerorOathguardMechRenderer(EntityRendererProvider.Context context) {
        super(context, new Modelmeror_oathguard_mech(context.bakeLayer(Modelmeror_oathguard_mech.LAYER_LOCATION)), 0.65f);
        this.addLayer(new MerorOathguardMechGlowLayer(this, new Modelmeror_oathguard_mech(context.bakeLayer(Modelmeror_oathguard_mech.LAYER_LOCATION))));
        this.addLayer(new CustomHeadLayer(this, context.getModelSet(), 1.0F, 1.0F, 1.0F, context.getItemInHandRenderer()));
        this.addLayer(new ElytraLayer(this, context.getModelSet()));
        this.addLayer(new SpecialItemInHandLayer<>(this, context.getItemInHandRenderer()));
        this.addLayer(new HumanoidArmorLayer(this, new HumanoidArmorModel(context.bakeLayer(ModelLayers.PLAYER_INNER_ARMOR)), new HumanoidArmorModel(context.bakeLayer(ModelLayers.PLAYER_OUTER_ARMOR)), context.getModelManager()));
    }

    @Override
    protected void scale(MerorOathguardMechEntity entity, PoseStack poseStack, float f) {
        poseStack.scale(1.1f, 1.1f, 1.1f);
        super.scale(entity, poseStack, f);
    }

    @Override
    protected float getFlipDegrees(MerorOathguardMechEntity t) {
        return 0.0f;
    }

    @Override
    public ResourceLocation getTextureLocation(MerorOathguardMechEntity golem) {
        return LOCATION;
    }
}
