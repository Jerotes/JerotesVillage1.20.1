package com.jerotes.jerotesvillage.client.renderer;

import com.jerotes.jerotesvillage.JerotesVillage;
import com.jerotes.jerotesvillage.client.layer.CarvedIronGolemCrackinessLayer;
import com.jerotes.jerotesvillage.client.layer.CarvedIronGolemFlowerLayer;
import com.jerotes.jerotesvillage.client.layer.CarvedIronGolemNetheriteLayer;
import com.jerotes.jerotesvillage.client.layer.GlowOtherBodyLayer;
import com.jerotes.jerotesvillage.client.model.Modelcarved_iron_golem;
import com.jerotes.jerotesvillage.entity.Neutral.CarvedIronGolemEntity;
import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.math.Axis;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.entity.MobRenderer;
import net.minecraft.resources.ResourceLocation;

public class CarvedIronGolemRenderer extends MobRenderer<CarvedIronGolemEntity, Modelcarved_iron_golem<CarvedIronGolemEntity>> {
    private static final ResourceLocation GOLEM_LOCATION = new ResourceLocation(JerotesVillage.MODID, "textures/entity/carved_faction/carved_iron_golem.png");
    private static final ResourceLocation GLOW_LOCATION = new ResourceLocation(JerotesVillage.MODID, "textures/entity/carved_faction/carved_iron_golem_glow.png");
    private static final ResourceLocation NETHERIATE_LOCATION = new ResourceLocation(JerotesVillage.MODID, "textures/entity/carved_faction/carved_iron_golem_netherite.png");

    public CarvedIronGolemRenderer(EntityRendererProvider.Context context) {
        super(context, new Modelcarved_iron_golem(context.bakeLayer(Modelcarved_iron_golem.LAYER_LOCATION)), 0.7f);
        this.addLayer(new GlowOtherBodyLayer<>(this, new Modelcarved_iron_golem(context.bakeLayer(Modelcarved_iron_golem.LAYER_LOCATION)), GLOW_LOCATION));
        this.addLayer(new CarvedIronGolemCrackinessLayer(this));
        this.addLayer(new CarvedIronGolemFlowerLayer(this, context.getBlockRenderDispatcher()));
        this.addLayer(new CarvedIronGolemNetheriteLayer(this, new Modelcarved_iron_golem(context.bakeLayer(Modelcarved_iron_golem.LAYER_LOCATION)), NETHERIATE_LOCATION));
    }

    @Override
    public ResourceLocation getTextureLocation(CarvedIronGolemEntity ironGolem) {
        return GOLEM_LOCATION;
    }

    @Override
    protected void setupRotations(CarvedIronGolemEntity ironGolem, PoseStack poseStack, float f, float f2, float f3) {
        super.setupRotations(ironGolem, poseStack, f, f2, f3);
        if ((double)ironGolem.walkAnimation.speed() < 0.01) {
            return;
        }
        float f4 = 13.0f;
        float f5 = ironGolem.walkAnimation.position(f3) + 6.0f;
        float f6 = (Math.abs(f5 % 13.0f - 6.5f) - 3.25f) / 3.25f;
        poseStack.mulPose(Axis.ZP.rotationDegrees(3.5f * f6));
    }
}
