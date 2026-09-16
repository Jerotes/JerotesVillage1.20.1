package com.jerotes.jerotesvillage.client.renderer;

import com.jerotes.jerotes.client.layer.OtherBodyLayer;
import com.jerotes.jerotesvillage.JerotesVillage;
import com.jerotes.jerotesvillage.client.model.Modelbright_land_beast;
import com.jerotes.jerotesvillage.entity.Other.Other.BossShowEntity;
import com.mojang.blaze3d.vertex.PoseStack;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.entity.LivingEntityRenderer;
import net.minecraft.core.BlockPos;
import net.minecraft.resources.ResourceLocation;

public class BossShowEntityBrightLandBeastRenderer extends LivingEntityRenderer<BossShowEntity, Modelbright_land_beast<BossShowEntity>> {
	private static final ResourceLocation LOCATION = new ResourceLocation(JerotesVillage.MODID, "textures/entity/bright_land_beast.png");
	private static final ResourceLocation OTHER_LOCATION = new ResourceLocation(JerotesVillage.MODID, "textures/entity/bright_land_beast_other.png");
	public BossShowEntityBrightLandBeastRenderer(EntityRendererProvider.Context context) {
		super(context, new Modelbright_land_beast(context.bakeLayer(Modelbright_land_beast.LAYER_LOCATION)), 0.5f);
		this.addLayer(new OtherBodyLayer(this, new Modelbright_land_beast(context.bakeLayer(Modelbright_land_beast.LAYER_LOCATION)), OTHER_LOCATION));
	}

	@Override
	protected void scale(BossShowEntity entity, PoseStack poseStack, float f) {
		poseStack.scale(0.4f, 0.4f, 0.4f);
		super.scale(entity, poseStack, f);
	}
	@Override
	protected int getBlockLightLevel(BossShowEntity entity, BlockPos blockPos) {
		return 9;
	}

	@Override
	public ResourceLocation getTextureLocation(BossShowEntity entity) {
		return LOCATION;
	}
}
