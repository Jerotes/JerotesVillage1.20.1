package com.jerotes.jerotesvillage.client.renderer;

import com.jerotes.jerotesvillage.JerotesVillage;
import com.jerotes.jerotesvillage.client.model.Modelsandstone_beast;
import com.jerotes.jerotesvillage.entity.Animal.SandstoneBeastEntity;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.entity.MobRenderer;
import net.minecraft.resources.ResourceLocation;

public class SandstoneBeastRenderer extends MobRenderer<SandstoneBeastEntity, Modelsandstone_beast<SandstoneBeastEntity>> {
	private static final ResourceLocation LOCATION = new ResourceLocation(JerotesVillage.MODID, "textures/entity/sandstone_beast.png");
	private static final ResourceLocation STONE_LOCATION = new ResourceLocation(JerotesVillage.MODID, "textures/entity/sandstone_beast_stone.png");
	public SandstoneBeastRenderer(EntityRendererProvider.Context context) {
		super(context, new Modelsandstone_beast(context.bakeLayer(Modelsandstone_beast.LAYER_LOCATION)), 1.2f);
	}

	@Override
	public ResourceLocation getTextureLocation(SandstoneBeastEntity entity) {
		if (entity.isStone() && !entity.isBaby()) {
			return STONE_LOCATION;
		}
		return LOCATION;
	}
}

