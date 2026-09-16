package com.jerotes.jerotesvillage.client.renderer;

import com.jerotes.jerotesvillage.JerotesVillage;
import com.jerotes.jerotesvillage.client.model.Modellittle_velvet;
import com.jerotes.jerotesvillage.entity.Animal.LittleVelvetEntity;
import com.mojang.blaze3d.vertex.PoseStack;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.entity.MobRenderer;
import net.minecraft.resources.ResourceLocation;

public class LittleVelvetRenderer extends MobRenderer<LittleVelvetEntity, Modellittle_velvet<LittleVelvetEntity>> {
	private static final ResourceLocation LOCATION = new ResourceLocation(JerotesVillage.MODID, "textures/entity/little_velvet.png");
	public LittleVelvetRenderer(EntityRendererProvider.Context context) {
		super(context, new Modellittle_velvet(context.bakeLayer(Modellittle_velvet.LAYER_LOCATION)), 0.25f);
	}

	@Override
	protected void scale(LittleVelvetEntity entity, PoseStack poseStack, float f) {
		poseStack.scale(0.7f, 0.7f, 0.7f);
		super.scale(entity, poseStack, f);
	}

	@Override
	public ResourceLocation getTextureLocation(LittleVelvetEntity entity) {
		return LOCATION;
	}
}

