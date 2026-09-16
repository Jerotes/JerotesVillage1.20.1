package com.jerotes.jerotesvillage.client.renderer;

import com.jerotes.jerotesvillage.JerotesVillage;
import com.jerotes.jerotesvillage.client.model.Modeliron_teeth;
import com.jerotes.jerotesvillage.entity.MagicSummoned.IronTeeth.FuryIronTeethEntity;
import com.jerotes.jerotesvillage.entity.MagicSummoned.IronTeeth.IronTeethEntity;
import com.jerotes.jerotesvillage.entity.MagicSummoned.SecondRounderAnimatedArmor.FurySecondRounderAnimatedArmorEntity;
import com.jerotes.jerotesvillage.entity.MagicSummoned.SecondRounderAnimatedArmor.SecondRounderAnimatedArmorEntity;
import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.math.Axis;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.entity.MobRenderer;
import net.minecraft.resources.ResourceLocation;

public class IronTeethRenderer extends MobRenderer<IronTeethEntity, Modeliron_teeth<IronTeethEntity>> {
	private static final ResourceLocation LOCATION = new ResourceLocation(JerotesVillage.MODID, "textures/entity/iron_teeth.png");
	private static final ResourceLocation FURY_LOCATION = new ResourceLocation(JerotesVillage.MODID, "textures/entity/fury_iron_teeth.png");
	public IronTeethRenderer(EntityRendererProvider.Context context) {
		super(context, new Modeliron_teeth(context.bakeLayer(Modeliron_teeth.LAYER_LOCATION)), 0.4f);
	}

	@Override
	protected void scale(IronTeethEntity entity, PoseStack poseStack, float f) {
		poseStack.scale(0.75f, 0.75f, 0.75f);
		super.scale(entity, poseStack, f);
	}

	@Override
	protected void setupRotations(IronTeethEntity entity, PoseStack poseStack, float f, float f2, float f3) {
		super.setupRotations(entity, poseStack, f, f2, f3);
		if ((double)entity.walkAnimation.speed() < 0.01) {
			return;
		}
		float f4 = 13.0f;
		float f5 = entity.walkAnimation.position(f3) + 6.0f;
		float f6 = (Math.abs(f5 % 13.0f - 6.5f) - 3.25f) / 3.25f;
		poseStack.mulPose(Axis.ZP.rotationDegrees(6.5f * f6));
	}

	@Override
	public ResourceLocation getTextureLocation(IronTeethEntity entity) {
		if (entity instanceof FuryIronTeethEntity) {
			return FURY_LOCATION;
		}
		return LOCATION;
	}
}

