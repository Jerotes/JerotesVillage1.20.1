package com.jerotes.jerotesvillage.client.renderer;

import com.jerotes.jerotesvillage.JerotesVillage;
import com.jerotes.jerotesvillage.client.model.Modelpurple_sand_gecko_tail;
import com.jerotes.jerotesvillage.entity.Other.Other.PurpleSandGeckoTailEntity;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.entity.MobRenderer;
import net.minecraft.resources.ResourceLocation;

public class PurpleSandGeckoTailRenderer extends MobRenderer<PurpleSandGeckoTailEntity, Modelpurple_sand_gecko_tail<PurpleSandGeckoTailEntity>> {
	private static final ResourceLocation LOCATION = new ResourceLocation(JerotesVillage.MODID, "textures/entity/purple_sand_gecko_tail.png");
	public PurpleSandGeckoTailRenderer(EntityRendererProvider.Context context) {
		super(context, new Modelpurple_sand_gecko_tail(context.bakeLayer(Modelpurple_sand_gecko_tail.LAYER_LOCATION)), 0.3f);
	}

	@Override
	public ResourceLocation getTextureLocation(PurpleSandGeckoTailEntity entity) {
		return LOCATION;
	}
}

