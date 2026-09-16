package com.jerotes.jerotesvillage.client.renderer;

import com.jerotes.jerotesvillage.JerotesVillage;
import com.jerotes.jerotesvillage.client.model.Modelpurple_sand_gecko;
import com.jerotes.jerotesvillage.entity.Animal.PurpleSandGeckoEntity;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.entity.MobRenderer;
import net.minecraft.resources.ResourceLocation;

public class PurpleSandGeckoRenderer extends MobRenderer<PurpleSandGeckoEntity, Modelpurple_sand_gecko<PurpleSandGeckoEntity>> {
	private static final ResourceLocation LOCATION = new ResourceLocation(JerotesVillage.MODID, "textures/entity/purple_sand_gecko.png");
	private static final ResourceLocation NO_TAIL_LOCATION = new ResourceLocation(JerotesVillage.MODID, "textures/entity/purple_sand_gecko_no_tail.png");
	public PurpleSandGeckoRenderer(EntityRendererProvider.Context context) {
		super(context, new Modelpurple_sand_gecko(context.bakeLayer(Modelpurple_sand_gecko.LAYER_LOCATION)), 0.3f);
	}

	@Override
	public ResourceLocation getTextureLocation(PurpleSandGeckoEntity entity) {
		if (entity.isNoTail()) {
			return NO_TAIL_LOCATION;
		}
		return LOCATION;
	}

}

