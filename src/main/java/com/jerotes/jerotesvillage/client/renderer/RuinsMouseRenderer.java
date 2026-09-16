package com.jerotes.jerotesvillage.client.renderer;

import com.jerotes.jerotesvillage.JerotesVillage;
import com.jerotes.jerotesvillage.client.model.Modelruins_mouse;
import com.jerotes.jerotesvillage.entity.Animal.RuinsMouseEntity;
import com.mojang.blaze3d.vertex.PoseStack;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.entity.MobRenderer;
import net.minecraft.resources.ResourceLocation;

public class RuinsMouseRenderer extends MobRenderer<RuinsMouseEntity, Modelruins_mouse<RuinsMouseEntity>> {
	private static final ResourceLocation LOCATION = new ResourceLocation(JerotesVillage.MODID, "textures/entity/ruins_mouse.png");
	private static final ResourceLocation DUST_LOCATION = new ResourceLocation(JerotesVillage.MODID, "textures/entity/ruins_mouse_dust.png");
	public RuinsMouseRenderer(EntityRendererProvider.Context context) {
		super(context, new Modelruins_mouse(context.bakeLayer(Modelruins_mouse.LAYER_LOCATION)), 0.25f);
	}

	@Override
	protected void scale(RuinsMouseEntity entity, PoseStack poseStack, float f) {
		poseStack.scale(0.7f, 0.7f, 0.7f);
		super.scale(entity, poseStack, f);
	}

	@Override
	public ResourceLocation getTextureLocation(RuinsMouseEntity entity) {
		if (entity.getDustTick() > 1200) {
			return DUST_LOCATION;
		}
		return LOCATION;
	}
}

