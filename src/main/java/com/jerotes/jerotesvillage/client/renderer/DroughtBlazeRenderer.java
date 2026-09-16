package com.jerotes.jerotesvillage.client.renderer;

import com.jerotes.jerotesvillage.JerotesVillage;
import com.jerotes.jerotesvillage.entity.MagicSummoned.DroughtBlaze.DroughtBlazeEntity;
import net.minecraft.client.model.BlazeModel;
import net.minecraft.client.model.geom.ModelLayers;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.entity.MobRenderer;
import net.minecraft.core.BlockPos;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.monster.Enemy;

public class DroughtBlazeRenderer extends MobRenderer<DroughtBlazeEntity, BlazeModel<DroughtBlazeEntity>> {
	private static final ResourceLocation LOCATION = new ResourceLocation(JerotesVillage.MODID, "textures/entity/elemental/drought_blaze.png");
	private static final ResourceLocation FURY_LOCATION = new ResourceLocation(JerotesVillage.MODID, "textures/entity/elemental/fury_drought_blaze.png");
	public DroughtBlazeRenderer(EntityRendererProvider.Context context) {
		super(context, new BlazeModel(context.bakeLayer(ModelLayers.BLAZE)), 0.5F);
	}
	@Override
	protected int getBlockLightLevel(DroughtBlazeEntity entity, BlockPos blockPos) {
		return 15;
	}
	@Override
	public ResourceLocation getTextureLocation(DroughtBlazeEntity entity) {
		if (entity instanceof Enemy) {
			return FURY_LOCATION;
		}
		return LOCATION;
	}
}