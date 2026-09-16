package com.jerotes.jerotesvillage.client.renderer;

import com.jerotes.jerotesvillage.JerotesVillage;
import com.jerotes.jerotesvillage.client.model.Modelabyssaler;
import com.jerotes.jerotesvillage.entity.Animal.Fish.AbyssalerEntity;
import com.mojang.blaze3d.vertex.PoseStack;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.entity.MobRenderer;
import net.minecraft.resources.ResourceLocation;

public class AbyssalerRenderer extends MobRenderer<AbyssalerEntity, Modelabyssaler<AbyssalerEntity>> {
	private static final ResourceLocation LOCATION = new ResourceLocation(JerotesVillage.MODID, "textures/entity/fish/abyssaler.png");
	public AbyssalerRenderer(EntityRendererProvider.Context context) {
		super(context, new Modelabyssaler(context.bakeLayer(Modelabyssaler.LAYER_LOCATION)), 0.6f);
	}

	@Override
	protected void scale(AbyssalerEntity entity, PoseStack poseStack, float f) {
		poseStack.scale(1.4f, 1.4f, 1.4f);
		super.scale(entity, poseStack, f);
	}

	@Override
	public ResourceLocation getTextureLocation(AbyssalerEntity entity) {
		return LOCATION;
	}

}

