package com.jerotes.jerotesvillage.client.renderer;

import com.jerotes.jerotesvillage.JerotesVillage;
import com.jerotes.jerotesvillage.client.model.Modelbright_mouse;
import com.jerotes.jerotesvillage.entity.Animal.BrightMouseEntity;
import com.mojang.blaze3d.vertex.PoseStack;
import net.minecraft.ChatFormatting;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.entity.MobRenderer;
import net.minecraft.resources.ResourceLocation;

public class BrightMouseRenderer extends MobRenderer<BrightMouseEntity, Modelbright_mouse<BrightMouseEntity>> {
	private static final ResourceLocation LOCATION = new ResourceLocation(JerotesVillage.MODID, "textures/entity/bright_mouse.png");
	public BrightMouseRenderer(EntityRendererProvider.Context context) {
		super(context, new Modelbright_mouse(context.bakeLayer(Modelbright_mouse.LAYER_LOCATION)), 0.25f);
	}

	@Override
	protected void scale(BrightMouseEntity entity, PoseStack poseStack, float f) {
		poseStack.scale(0.7f, 0.7f, 0.7f);
		super.scale(entity, poseStack, f);
	}

	@Override
	public ResourceLocation getTextureLocation(BrightMouseEntity entity) {
		String string = ChatFormatting.stripFormatting(entity.getName().getString());
		if ("Jerry".equals(string)) {
			return new ResourceLocation(JerotesVillage.MODID, "textures/entity/bright_mouse_jerry.png");
		}
		if ("HuangFeng".equals(string) || "黄风大圣".equals(string)) {
			return new ResourceLocation(JerotesVillage.MODID, "textures/entity/bright_mouse_huangfeng.png");
		}
		return LOCATION;
	}
}

