package com.jerotes.jerotesvillage.client.renderer;

import com.jerotes.jerotesvillage.JerotesVillage;
import com.jerotes.jerotesvillage.client.model.Modelprimordier;
import com.jerotes.jerotesvillage.entity.Monster.Elite.PrimordierEntity;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.entity.MobRenderer;
import net.minecraft.resources.ResourceLocation;

public class PrimordierRenderer extends MobRenderer<PrimordierEntity, Modelprimordier<PrimordierEntity>> {
	private static final ResourceLocation LOCATION = new ResourceLocation(JerotesVillage.MODID, "textures/entity/primordier.png");
	public PrimordierRenderer(EntityRendererProvider.Context context) {
		super(context, new Modelprimordier<>(context.bakeLayer(Modelprimordier.LAYER_LOCATION)), 2.5f);
	}

	@Override
	protected float getFlipDegrees(PrimordierEntity t) {
		return 0.0f;
	}


	@Override
	public ResourceLocation getTextureLocation(PrimordierEntity entity) {
		return LOCATION;
	}
}

