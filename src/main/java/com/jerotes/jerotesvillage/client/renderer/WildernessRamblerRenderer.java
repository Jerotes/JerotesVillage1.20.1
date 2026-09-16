package com.jerotes.jerotesvillage.client.renderer;

import com.jerotes.jerotesvillage.JerotesVillage;
import com.jerotes.jerotesvillage.client.model.Modelrambler;
import com.jerotes.jerotesvillage.entity.Animal.WildernessRamblerEntity;
import com.mojang.blaze3d.vertex.PoseStack;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.entity.MobRenderer;
import net.minecraft.resources.ResourceLocation;

public class WildernessRamblerRenderer extends MobRenderer<WildernessRamblerEntity, Modelrambler<WildernessRamblerEntity>> {
	private static final ResourceLocation RAMBLER_LOCATION = new ResourceLocation(JerotesVillage.MODID, "textures/entity/wilderness_rambler.png");
	public WildernessRamblerRenderer(EntityRendererProvider.Context context) {
		super(context, new Modelrambler(context.bakeLayer(Modelrambler.LAYER_LOCATION)), 1.1f);
	}

	@Override
	protected void scale(WildernessRamblerEntity entity, PoseStack poseStack, float f) {
		poseStack.scale(1.5f, 1.5f, 1.5f);
		super.scale(entity, poseStack, f);
	}

	@Override
	public ResourceLocation getTextureLocation(WildernessRamblerEntity entity) {
		return RAMBLER_LOCATION;
	}
}

