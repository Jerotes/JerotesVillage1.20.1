package com.jerotes.jerotesvillage.client.renderer;

import com.jerotes.jerotesvillage.JerotesVillage;
import com.jerotes.jerotesvillage.client.layer.PurpleSandHagSkinLayer;
import com.jerotes.jerotesvillage.client.model.Modelhag;
import com.jerotes.jerotesvillage.entity.Other.Other.BossShowEntity;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.entity.LivingEntityRenderer;
import net.minecraft.core.BlockPos;
import net.minecraft.resources.ResourceLocation;

public class BossShowEntityPurpleSandHagRenderer extends LivingEntityRenderer<BossShowEntity, Modelhag<BossShowEntity>> {
	private static final ResourceLocation LOCATION = new ResourceLocation(JerotesVillage.MODID, "textures/entity/hag.png");
	public BossShowEntityPurpleSandHagRenderer(EntityRendererProvider.Context context) {
		super(context, new Modelhag(context.bakeLayer(Modelhag.LAYER_LOCATION)), 0.5f);
		this.addLayer(new PurpleSandHagSkinLayer<>(this, new Modelhag(context.bakeLayer(Modelhag.LAYER_LOCATION))));
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