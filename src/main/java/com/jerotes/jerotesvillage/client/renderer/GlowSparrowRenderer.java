package com.jerotes.jerotesvillage.client.renderer;

import com.jerotes.jerotesvillage.JerotesVillage;
import com.jerotes.jerotes.client.layer.*;
import com.jerotes.jerotesvillage.client.model.Modelglow_sparrow;
import com.jerotes.jerotesvillage.entity.Animal.GlowSparrowEntity;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.entity.MobRenderer;
import net.minecraft.core.BlockPos;
import net.minecraft.resources.ResourceLocation;

public class GlowSparrowRenderer extends MobRenderer<GlowSparrowEntity, Modelglow_sparrow<GlowSparrowEntity>> {
	private static final ResourceLocation LOCATION = new ResourceLocation(JerotesVillage.MODID, "textures/entity/glow_sparrow.png");
	private static final ResourceLocation TAME_LOCATION = new ResourceLocation(JerotesVillage.MODID, "textures/entity/glow_sparrow_tame.png");
	public GlowSparrowRenderer(EntityRendererProvider.Context context) {
		super(context, new Modelglow_sparrow(context.bakeLayer(Modelglow_sparrow.LAYER_LOCATION)), 0.3f);
		this.addLayer(new TameLayer(this, new Modelglow_sparrow(context.bakeLayer(Modelglow_sparrow.LAYER_LOCATION)), TAME_LOCATION));
	}

	@Override
	protected int getBlockLightLevel(GlowSparrowEntity entity, BlockPos blockPos) {
		return 15;
	}

	@Override
	public ResourceLocation getTextureLocation(GlowSparrowEntity entity) {
		return LOCATION;
	}

}

