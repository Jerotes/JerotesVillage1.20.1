package com.jerotes.jerotesvillage.client.renderer;

import com.jerotes.jerotesvillage.JerotesVillage;
import com.jerotes.jerotesvillage.client.model.Modelsolish;
import com.jerotes.jerotesvillage.entity.Animal.Fish.SolishEntity;
import com.mojang.blaze3d.vertex.PoseStack;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.entity.MobRenderer;
import net.minecraft.resources.ResourceLocation;

public class SolishRenderer extends MobRenderer<SolishEntity, Modelsolish<SolishEntity>> {
	private static final ResourceLocation LOCATION = new ResourceLocation(JerotesVillage.MODID, "textures/entity/solish.png");
	public SolishRenderer(EntityRendererProvider.Context context) {
		super(context, new Modelsolish(context.bakeLayer(Modelsolish.LAYER_LOCATION)), 1.2f);
	}

	@Override
	protected void scale(SolishEntity entity, PoseStack poseStack, float f) {
		poseStack.scale(1.25f, 1.25f, 1.25f);
		super.scale(entity, poseStack, f);
	}

	@Override
	public ResourceLocation getTextureLocation(SolishEntity entity) {
		return LOCATION;
	}

}

