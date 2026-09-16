package com.jerotes.jerotesvillage.client.renderer;

import com.jerotes.jerotesvillage.JerotesVillage;
import com.jerotes.jerotesvillage.client.model.Modelcleaning_robot;
import com.jerotes.jerotesvillage.entity.Neutral.CleaningRobotEntity;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.entity.MobRenderer;
import net.minecraft.core.BlockPos;
import net.minecraft.resources.ResourceLocation;

public class CleaningRobotRenderer extends MobRenderer<CleaningRobotEntity, Modelcleaning_robot<CleaningRobotEntity>> {
	private static final ResourceLocation ON_LOCATION = new ResourceLocation(JerotesVillage.MODID, "textures/entity/cleaning_robot_on.png");
	private static final ResourceLocation LOCATION = new ResourceLocation(JerotesVillage.MODID, "textures/entity/cleaning_robot.png");
	public CleaningRobotRenderer(EntityRendererProvider.Context context) {
		super(context, new Modelcleaning_robot(context.bakeLayer(Modelcleaning_robot.LAYER_LOCATION)), 0.75f);
	}

	@Override
	protected int getBlockLightLevel(CleaningRobotEntity entity, BlockPos blockPos) {
		if  (entity.isOn()) {
			return 15;
		}
		return 0;
	}

	@Override
	protected float getFlipDegrees(CleaningRobotEntity t) {
		return 0.0f;
	}

	@Override
	public ResourceLocation getTextureLocation(CleaningRobotEntity entity) {
		if  (entity.isOn()) {
			return ON_LOCATION;
		}
		return LOCATION;
	}

}

