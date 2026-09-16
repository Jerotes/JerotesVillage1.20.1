package com.jerotes.jerotesvillage.client.renderer;

import com.jerotes.jerotesvillage.JerotesVillage;
import com.jerotes.jerotesvillage.client.layer.GlowOtherBodyLayer;
import com.jerotes.jerotesvillage.client.model.Modelgemstone_malignasaur;
import com.jerotes.jerotesvillage.entity.Other.Other.BossShowEntity;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.entity.LivingEntityRenderer;
import net.minecraft.core.BlockPos;
import net.minecraft.resources.ResourceLocation;

public class BossShowEntityGemstoneMalignasaurRenderer extends LivingEntityRenderer<BossShowEntity, Modelgemstone_malignasaur<BossShowEntity>> {
	private static final ResourceLocation LOCATION = new ResourceLocation(JerotesVillage.MODID, "textures/entity/gemstone_malignasaur_armor.png");
	private static final ResourceLocation GLOW_LOCATION = new ResourceLocation(JerotesVillage.MODID, "textures/entity/gemstone_malignasaur_glow.png");
	public BossShowEntityGemstoneMalignasaurRenderer(EntityRendererProvider.Context context) {
		super(context, new Modelgemstone_malignasaur(context.bakeLayer(Modelgemstone_malignasaur.LAYER_LOCATION)), 0.5f);
		this.addLayer(new GlowOtherBodyLayer<>(this, new Modelgemstone_malignasaur(context.bakeLayer(Modelgemstone_malignasaur.LAYER_LOCATION)), GLOW_LOCATION));
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
