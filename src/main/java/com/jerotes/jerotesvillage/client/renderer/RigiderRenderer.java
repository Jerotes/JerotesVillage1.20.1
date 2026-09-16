package com.jerotes.jerotesvillage.client.renderer;

import com.jerotes.jerotesvillage.JerotesVillage;
import com.jerotes.jerotesvillage.client.model.Modelrigider;
import com.jerotes.jerotesvillage.entity.Neutral.Zsiein.RigiderEntity;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.entity.MobRenderer;
import net.minecraft.resources.ResourceLocation;

public class RigiderRenderer extends MobRenderer<RigiderEntity, Modelrigider<RigiderEntity>> {
	private static final ResourceLocation LOCATION = new ResourceLocation(JerotesVillage.MODID, "textures/entity/rigider.png");
	public RigiderRenderer(EntityRendererProvider.Context context) {
		super(context, new Modelrigider(context.bakeLayer(Modelrigider.LAYER_LOCATION)), 1.1f);
	}

	@Override
	public ResourceLocation getTextureLocation(RigiderEntity entity) {
		return LOCATION;
	}
}

