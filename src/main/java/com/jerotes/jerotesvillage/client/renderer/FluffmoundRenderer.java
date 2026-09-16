package com.jerotes.jerotesvillage.client.renderer;

import com.jerotes.jerotes.client.layer.ChestLayer;
import com.jerotes.jerotes.client.layer.HornLayer;
import com.jerotes.jerotes.client.layer.TameLayer;
import com.jerotes.jerotesvillage.JerotesVillage;
import com.jerotes.jerotesvillage.client.model.Modelfluffmound;
import com.jerotes.jerotesvillage.entity.Animal.FluffmoundEntity;
import com.mojang.blaze3d.vertex.PoseStack;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.entity.MobRenderer;
import net.minecraft.client.renderer.entity.layers.SaddleLayer;
import net.minecraft.resources.ResourceLocation;

public class FluffmoundRenderer extends MobRenderer<FluffmoundEntity, Modelfluffmound<FluffmoundEntity>> {
	private static final ResourceLocation LOCATION = new ResourceLocation(JerotesVillage.MODID, "textures/entity/fluffmound.png");
	private static final ResourceLocation TAME_LOCATION = new ResourceLocation(JerotesVillage.MODID, "textures/entity/fluffmound_tame.png");
	private static final ResourceLocation HORN_LOCATION = new ResourceLocation(JerotesVillage.MODID, "textures/entity/fluffmound_horn.png");
	private static final ResourceLocation SADDLE_LOCATION = new ResourceLocation(JerotesVillage.MODID, "textures/entity/fluffmound_saddle.png");
	private static final ResourceLocation CHEST_LOCATION = new ResourceLocation(JerotesVillage.MODID, "textures/entity/fluffmound_chest.png");
	public FluffmoundRenderer(EntityRendererProvider.Context context) {
		super(context, new Modelfluffmound(context.bakeLayer(Modelfluffmound.LAYER_LOCATION)), 1.8f);
		this.addLayer(new TameLayer(this, new Modelfluffmound(context.bakeLayer(Modelfluffmound.LAYER_LOCATION)), TAME_LOCATION));
		this.addLayer(new HornLayer(this, new Modelfluffmound(context.bakeLayer(Modelfluffmound.LAYER_LOCATION)), HORN_LOCATION));
		this.addLayer(new SaddleLayer(this, new Modelfluffmound(context.bakeLayer(Modelfluffmound.LAYER_LOCATION)), SADDLE_LOCATION));
			this.addLayer(new ChestLayer(this, new Modelfluffmound(context.bakeLayer(Modelfluffmound.LAYER_LOCATION)), CHEST_LOCATION));
		}

	@Override
	protected void scale(FluffmoundEntity entity, PoseStack poseStack, float f) {
		poseStack.scale(1.4f, 1.4f, 1.4f);
		super.scale(entity, poseStack, f);
	}

	@Override
	public ResourceLocation getTextureLocation(FluffmoundEntity entity) {
		return LOCATION;
	}
}

