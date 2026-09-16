package com.jerotes.jerotesvillage.client.renderer;

import com.jerotes.jerotesvillage.JerotesVillage;
import com.jerotes.jerotesvillage.client.layer.MerorMinistrosityGlowLayer;
import com.jerotes.jerotesvillage.client.model.Modelredstone_ministrosity;
import com.jerotes.jerotesvillage.entity.Neutral.MerorMachine.MerorMinistrosityEntity;
import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.math.Axis;
import net.minecraft.ChatFormatting;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.entity.MobRenderer;
import net.minecraft.resources.ResourceLocation;

public class MerorMinistrosityRenderer extends MobRenderer<MerorMinistrosityEntity, Modelredstone_ministrosity<MerorMinistrosityEntity>> {
    private static final ResourceLocation LOCATION = new ResourceLocation(JerotesVillage.MODID, "textures/entity/meror_machine/meror_ministrosity.png");
    private static final ResourceLocation REDSTONE_LOCATION = new ResourceLocation(JerotesVillage.MODID, "textures/entity/meror_machine/meror_ministrosity_redstone_ministrosity.png");

    public MerorMinistrosityRenderer(EntityRendererProvider.Context context) {
        super(context, new Modelredstone_ministrosity(context.bakeLayer(Modelredstone_ministrosity.LAYER_LOCATION)), 0.35f);
        this.addLayer(new MerorMinistrosityGlowLayer<>(this, new Modelredstone_ministrosity(context.bakeLayer(Modelredstone_ministrosity.LAYER_LOCATION))));
    }

    @Override
    protected void scale(MerorMinistrosityEntity entity, PoseStack poseStack, float f) {
        poseStack.scale(1.0f, 1.0f, 1.0f);
        super.scale(entity, poseStack, f);
    }

    @Override
    protected float getFlipDegrees(MerorMinistrosityEntity t) {
        return 0.0f;
    }

    @Override
    protected void setupRotations(MerorMinistrosityEntity golem, PoseStack poseStack, float f, float f2, float f3) {
        super.setupRotations(golem, poseStack, f, f2, f3);
        if ((double)golem.walkAnimation.speed() < 0.01) {
            return;
        }
        float f4 = 13.0f;
        float f5 = golem.walkAnimation.position(f3) + 6.0f;
        float f6 = (Math.abs(f5 % 13.0f - 6.5f) - 3.25f) / 3.25f;
        poseStack.mulPose(Axis.ZP.rotationDegrees(0.4f * f6));
    }

    @Override
    public ResourceLocation getTextureLocation(MerorMinistrosityEntity golem) {
        String string = ChatFormatting.stripFormatting(golem.getName().getString());
        if ("Redstone Ministrosity".equals(string) || "红石小兽".equals(string) ) {
            return REDSTONE_LOCATION;
        }
        return LOCATION;
    }
}
