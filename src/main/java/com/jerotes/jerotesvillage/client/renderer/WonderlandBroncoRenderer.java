package com.jerotes.jerotesvillage.client.renderer;

import com.jerotes.jerotes.client.layer.ReinLayer;
import com.jerotes.jerotes.client.layer.TameLayer;
import com.jerotes.jerotesvillage.JerotesVillage;
import com.jerotes.jerotesvillage.client.layer.WonderlandBroncoArmorLayer;
import com.jerotes.jerotesvillage.client.model.Modelwonderland_bronco;
import com.jerotes.jerotesvillage.entity.Animal.WonderlandBroncoEntity;
import com.mojang.blaze3d.vertex.PoseStack;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.entity.MobRenderer;
import net.minecraft.client.renderer.entity.layers.SaddleLayer;
import net.minecraft.resources.ResourceLocation;

public class WonderlandBroncoRenderer extends MobRenderer<WonderlandBroncoEntity, Modelwonderland_bronco<WonderlandBroncoEntity>> {
	private static final ResourceLocation LOCATION = new ResourceLocation(JerotesVillage.MODID, "textures/entity/wonderland_bronco.png");
	private static final ResourceLocation TAME_LOCATION = new ResourceLocation(JerotesVillage.MODID, "textures/entity/wonderland_bronco_tame.png");
	private static final ResourceLocation REIN_LOCATION = new ResourceLocation(JerotesVillage.MODID, "textures/entity/wonderland_bronco_rein.png");
	public WonderlandBroncoRenderer(EntityRendererProvider.Context context) {
		super(context, new Modelwonderland_bronco(context.bakeLayer(Modelwonderland_bronco.LAYER_LOCATION)), 1.0f);
		this.addLayer(new TameLayer(this, new Modelwonderland_bronco(context.bakeLayer(Modelwonderland_bronco.LAYER_LOCATION)), TAME_LOCATION));
		this.addLayer(new SaddleLayer(this, new Modelwonderland_bronco(context.bakeLayer(Modelwonderland_bronco.LAYER_LOCATION)), new ResourceLocation(JerotesVillage.MODID, "textures/entity/wonderland_bronco_saddle.png")));
		this.addLayer(new ReinLayer(this, new Modelwonderland_bronco(context.bakeLayer(Modelwonderland_bronco.LAYER_LOCATION)), REIN_LOCATION));
		this.addLayer(new WonderlandBroncoArmorLayer(this, context.getModelSet()));
	}

	@Override
	protected void scale(WonderlandBroncoEntity entity, PoseStack poseStack, float f) {
		poseStack.scale(1.7f, 1.7f, 1.7f);
		super.scale(entity, poseStack, f);
	}

	@Override
	public ResourceLocation getTextureLocation(WonderlandBroncoEntity entity) {
		return LOCATION;
	}
}

