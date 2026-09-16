package com.jerotes.jerotesvillage.client.renderer;

import com.jerotes.jerotesvillage.JerotesVillage;
import com.jerotes.jerotes.client.layer.*;
import com.jerotes.jerotesvillage.client.model.Modelbright_land_beast;
import com.jerotes.jerotesvillage.entity.Animal.ArchnosedHornbeastEntity;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.entity.MobRenderer;
import net.minecraft.client.renderer.entity.layers.SaddleLayer;
import net.minecraft.resources.ResourceLocation;

public class ArchnosedHornbeastRenderer extends MobRenderer<ArchnosedHornbeastEntity, Modelbright_land_beast<ArchnosedHornbeastEntity>> {
	private static final ResourceLocation LOCATION = new ResourceLocation(JerotesVillage.MODID, "textures/entity/archnosed_hornbeast.png");
	private static final ResourceLocation TAME_LOCATION = new ResourceLocation(JerotesVillage.MODID, "textures/entity/archnosed_hornbeast_tame.png");
	private static final ResourceLocation SADDLE_LOCATION = new ResourceLocation(JerotesVillage.MODID, "textures/entity/archnosed_hornbeast_saddle.png");
	private static final ResourceLocation CHEST_LOCATION = new ResourceLocation(JerotesVillage.MODID, "textures/entity/archnosed_hornbeast_chest.png");
	public ArchnosedHornbeastRenderer(EntityRendererProvider.Context context) {
		super(context, new Modelbright_land_beast(context.bakeLayer(Modelbright_land_beast.LAYER_LOCATION)), 4.5f);
		this.addLayer(new TameLayer(this, new Modelbright_land_beast(context.bakeLayer(Modelbright_land_beast.LAYER_LOCATION)), TAME_LOCATION));
		this.addLayer(new SaddleLayer(this, new Modelbright_land_beast(context.bakeLayer(Modelbright_land_beast.LAYER_LOCATION)), SADDLE_LOCATION));
		this.addLayer(new ChestLayer(this, new Modelbright_land_beast(context.bakeLayer(Modelbright_land_beast.LAYER_LOCATION)), CHEST_LOCATION));
	}

	@Override
	public ResourceLocation getTextureLocation(ArchnosedHornbeastEntity entity) {
		return LOCATION;
	}
}