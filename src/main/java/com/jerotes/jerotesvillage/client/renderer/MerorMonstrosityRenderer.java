package com.jerotes.jerotesvillage.client.renderer;

import com.jerotes.jerotesvillage.JerotesVillage;
import com.jerotes.jerotesvillage.client.layer.MerorMonstrosityGlowLayer;
import com.jerotes.jerotesvillage.client.model.Modelredstone_monstrosity;
import com.jerotes.jerotesvillage.entity.Neutral.MerorMachine.MerorMonstrosityEntity;
import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.math.Axis;
import net.minecraft.ChatFormatting;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.entity.MobRenderer;
import net.minecraft.resources.ResourceLocation;

public class MerorMonstrosityRenderer extends MobRenderer<MerorMonstrosityEntity, Modelredstone_monstrosity<MerorMonstrosityEntity>> {
    private static final ResourceLocation LOCATION = new ResourceLocation(JerotesVillage.MODID, "textures/entity/meror_machine/meror_monstrosity.png");
    private static final ResourceLocation REDSTONE_LOCATION = new ResourceLocation(JerotesVillage.MODID, "textures/entity/meror_machine/meror_monstrosity_redstone_monstrosity.png");
    private static final ResourceLocation CLASSIC_LOCATION = new ResourceLocation(JerotesVillage.MODID, "textures/entity/meror_machine/meror_monstrosity_old.png");

    public MerorMonstrosityRenderer(EntityRendererProvider.Context context) {
        super(context, new Modelredstone_monstrosity(context.bakeLayer(Modelredstone_monstrosity.LAYER_LOCATION)), 3.0f);
        this.addLayer(new MerorMonstrosityGlowLayer<>(this, new Modelredstone_monstrosity(context.bakeLayer(Modelredstone_monstrosity.LAYER_LOCATION))));
    }

        @Override
    protected void scale(MerorMonstrosityEntity entity, PoseStack poseStack, float f) {
        poseStack.scale(1.0f, 1.0f, 1.0f);
        super.scale(entity, poseStack, f);
    }

    @Override
    protected float getFlipDegrees(MerorMonstrosityEntity t) {
        return 0.0f;
    }

    @Override
    protected void setupRotations(MerorMonstrosityEntity golem, PoseStack poseStack, float f, float f2, float f3) {
        super.setupRotations(golem, poseStack, f, f2, f3);
        if ((double)golem.walkAnimation.speed() < 0.01) {
            return;
        }
        float f4 = 13.0f;
        float f5 = golem.walkAnimation.position(f3) + 6.0f;
        float f6 = (Math.abs(f5 % 13.0f - 6.5f) - 3.25f) / 3.25f;
        poseStack.mulPose(Axis.ZP.rotationDegrees(1.5f * f6));
    }

    @Override
    public ResourceLocation getTextureLocation(MerorMonstrosityEntity golem) {
        String string = ChatFormatting.stripFormatting(golem.getName().getString());
        if ("Redstone Monstrosity".equals(string) || "红石巨兽".equals(string) ) {
            return REDSTONE_LOCATION;
        }
        if ("Classic".equals(string) || "Old".equals(string) || "classic".equals(string) || "old".equals(string)) {
            return CLASSIC_LOCATION;
        }
        return LOCATION;
    }
}
