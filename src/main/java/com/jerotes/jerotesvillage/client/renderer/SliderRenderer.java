package com.jerotes.jerotesvillage.client.renderer;

import com.jerotes.jerotesvillage.JerotesVillage;
import com.jerotes.jerotesvillage.client.layer.SliderBodyLayer;
import com.jerotes.jerotesvillage.client.model.Modelslider;
import com.jerotes.jerotesvillage.entity.Monster.SliderEntity;
import com.mojang.blaze3d.vertex.PoseStack;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.entity.MobRenderer;
import net.minecraft.core.BlockPos;
import net.minecraft.resources.ResourceLocation;

public class SliderRenderer extends MobRenderer<SliderEntity, Modelslider<SliderEntity>> {
	private static final ResourceLocation LOCATION = new ResourceLocation(JerotesVillage.MODID, "textures/entity/slider.png");
	private static final ResourceLocation BODY_LOCATION = new ResourceLocation(JerotesVillage.MODID, "textures/entity/slider_body.png");
	public SliderRenderer(EntityRendererProvider.Context context) {
		super(context, new Modelslider(context.bakeLayer(Modelslider.LAYER_LOCATION)), 0.3f);
		this.addLayer(new SliderBodyLayer(this, new Modelslider(context.bakeLayer(Modelslider.LAYER_LOCATION)), BODY_LOCATION));
	}

	@Override
	protected void scale(SliderEntity entity, PoseStack poseStack, float f) {
		poseStack.scale(1.2f, 1.2f, 1.2f);
		super.scale(entity, poseStack, f);
	}

	@Override
	protected int getBlockLightLevel(SliderEntity entity, BlockPos blockPos) {
		return 6;
	}

	@Override
	public ResourceLocation getTextureLocation(SliderEntity entity) {
		return LOCATION;
	}
}

