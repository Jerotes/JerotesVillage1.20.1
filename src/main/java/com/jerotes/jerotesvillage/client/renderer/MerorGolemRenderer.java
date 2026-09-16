package com.jerotes.jerotesvillage.client.renderer;

import com.jerotes.jerotesvillage.JerotesVillage;
import com.jerotes.jerotesvillage.client.layer.MerorGolemGlowLayer;
import com.jerotes.jerotesvillage.client.model.Modelredstone_golem;
import com.jerotes.jerotesvillage.entity.Neutral.MerorMachine.MerorGolemEntity;
import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.math.Axis;
import net.minecraft.ChatFormatting;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.entity.MobRenderer;
import net.minecraft.resources.ResourceLocation;

public class MerorGolemRenderer extends MobRenderer<MerorGolemEntity, Modelredstone_golem<MerorGolemEntity>> {
    private static final ResourceLocation LOCATION = new ResourceLocation(JerotesVillage.MODID, "textures/entity/meror_machine/meror_golem.png");
    private static final ResourceLocation REDSTONE_LOCATION = new ResourceLocation(JerotesVillage.MODID, "textures/entity/meror_machine/meror_golem_redstone_golem.png");
    private static final ResourceLocation CLASSIC_LOCATION = new ResourceLocation(JerotesVillage.MODID, "textures/entity/meror_machine/meror_golem_old.png");

    public MerorGolemRenderer(EntityRendererProvider.Context context) {
        super(context, new Modelredstone_golem(context.bakeLayer(Modelredstone_golem.LAYER_LOCATION)), 1.5f);
        this.addLayer(new MerorGolemGlowLayer<>(this, new Modelredstone_golem(context.bakeLayer(Modelredstone_golem.LAYER_LOCATION))));
    }

    @Override
    protected void scale(MerorGolemEntity entity, PoseStack poseStack, float f) {
        poseStack.scale(1.0f, 1.0f, 1.0f);
        super.scale(entity, poseStack, f);
    }

    @Override
    protected float getFlipDegrees(MerorGolemEntity golem) {
        return 0.0f;
    }

    @Override
    protected void setupRotations(MerorGolemEntity golem, PoseStack poseStack, float f, float f2, float f3) {
        super.setupRotations(golem, poseStack, f, f2, f3);
        if ((double)golem.walkAnimation.speed() < 0.01) {
            return;
        }
        float f4 = 13.0f;
        float f5 = golem.walkAnimation.position(f3) + 6.0f;
        float f6 = (Math.abs(f5 % 13.0f - 6.5f) - 3.25f) / 3.25f;
        poseStack.mulPose(Axis.ZP.rotationDegrees(2.5f * f6));
    }

    @Override
    public ResourceLocation getTextureLocation(MerorGolemEntity golem) {
        String string = ChatFormatting.stripFormatting(golem.getName().getString());
        if ("Redstone Golem".equals(string) || "红石傀儡".equals(string)) {
            return REDSTONE_LOCATION;
        }
        if ("Classic".equals(string) || "Old".equals(string) || "classic".equals(string) || "old".equals(string)) {
            return CLASSIC_LOCATION;
        }
        return LOCATION;
    }
}
