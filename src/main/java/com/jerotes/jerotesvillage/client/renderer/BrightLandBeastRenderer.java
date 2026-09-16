package com.jerotes.jerotesvillage.client.renderer;

import com.jerotes.jerotes.client.layer.ChestLayer;
import com.jerotes.jerotes.client.layer.TameLayer;
import com.jerotes.jerotesvillage.JerotesVillage;
import com.jerotes.jerotesvillage.client.layer.BrightLandBeastOtherBodyLayer;
import com.jerotes.jerotesvillage.client.layer.BeastToughHurtLayer;
import com.jerotes.jerotesvillage.client.model.Modelbright_land_beast;
import com.jerotes.jerotesvillage.entity.Boss.Biome.BrightLandBeastEntity;
import com.mojang.blaze3d.vertex.PoseStack;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.entity.MobRenderer;
import net.minecraft.client.renderer.entity.layers.SaddleLayer;
import net.minecraft.core.BlockPos;
import net.minecraft.resources.ResourceLocation;

public class BrightLandBeastRenderer extends MobRenderer<BrightLandBeastEntity, Modelbright_land_beast<BrightLandBeastEntity>> {
	private static final ResourceLocation LOCATION = new ResourceLocation(JerotesVillage.MODID, "textures/entity/bright_land_beast.png");
	private static final ResourceLocation TAME_LOCATION = new ResourceLocation(JerotesVillage.MODID, "textures/entity/archnosed_hornbeast_tame.png");
	private static final ResourceLocation HURT_LOCATION = new ResourceLocation(JerotesVillage.MODID, "textures/entity/bright_land_beast_hurt.png");
	private static final ResourceLocation SADDLE_LOCATION = new ResourceLocation(JerotesVillage.MODID, "textures/entity/archnosed_hornbeast_saddle.png");
	private static final ResourceLocation CHEST_LOCATION = new ResourceLocation(JerotesVillage.MODID, "textures/entity/archnosed_hornbeast_chest.png");
	public BrightLandBeastRenderer(EntityRendererProvider.Context context) {
		super(context, new Modelbright_land_beast(context.bakeLayer(Modelbright_land_beast.LAYER_LOCATION)), 5.625f);
		this.addLayer(new BrightLandBeastOtherBodyLayer(this, new Modelbright_land_beast(context.bakeLayer(Modelbright_land_beast.LAYER_LOCATION))));
		this.addLayer(new BeastToughHurtLayer(this, new Modelbright_land_beast(context.bakeLayer(Modelbright_land_beast.LAYER_LOCATION)), HURT_LOCATION));
		this.addLayer(new TameLayer(this, new Modelbright_land_beast(context.bakeLayer(Modelbright_land_beast.LAYER_LOCATION)), TAME_LOCATION));
		this.addLayer(new SaddleLayer(this, new Modelbright_land_beast(context.bakeLayer(Modelbright_land_beast.LAYER_LOCATION)), SADDLE_LOCATION));
		this.addLayer(new ChestLayer(this, new Modelbright_land_beast(context.bakeLayer(Modelbright_land_beast.LAYER_LOCATION)), CHEST_LOCATION));
	}

	@Override
	protected void scale(BrightLandBeastEntity entity, PoseStack poseStack, float f) {
		poseStack.scale(1.25f, 1.25f, 1.25f);
		super.scale(entity, poseStack, f);
	}

	@Override
	protected float getFlipDegrees(BrightLandBeastEntity t) {
		return 0.0f;
	}

	@Override
	protected int getBlockLightLevel(BrightLandBeastEntity entity, BlockPos blockPos) {
		if (entity.getHealth() > entity.getMaxHealth()/4) {
			return 5;
		}
		return super.getBlockLightLevel(entity, blockPos);
	}

	@Override
	public ResourceLocation getTextureLocation(BrightLandBeastEntity entity) {
		return LOCATION;
	}
}