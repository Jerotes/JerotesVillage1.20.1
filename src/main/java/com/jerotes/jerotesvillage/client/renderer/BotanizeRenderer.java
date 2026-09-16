package com.jerotes.jerotesvillage.client.renderer;

import com.jerotes.jerotesvillage.JerotesVillage;
import com.jerotes.jerotesvillage.client.layer.BotanizeItemInHandLayer;
import com.jerotes.jerotesvillage.client.model.Modelbotanize;
import com.jerotes.jerotesvillage.entity.MagicSummoned.Botanize.BotanizeEntity;
import com.mojang.blaze3d.vertex.PoseStack;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.entity.MobRenderer;
import net.minecraft.core.BlockPos;
import net.minecraft.resources.ResourceLocation;

public class BotanizeRenderer extends MobRenderer<BotanizeEntity, Modelbotanize<BotanizeEntity>> {
	private static final ResourceLocation LOCATION = new ResourceLocation(JerotesVillage.MODID, "textures/entity/botanize.png");
	public BotanizeRenderer(EntityRendererProvider.Context context) {
		super(context, new Modelbotanize(context.bakeLayer(Modelbotanize.LAYER_LOCATION)), 0.3f);
		this.addLayer(new BotanizeItemInHandLayer(this, context.getItemInHandRenderer()));
	}

	@Override
	protected void scale(BotanizeEntity entity, PoseStack poseStack, float f) {
		poseStack.scale(0.8f, 0.8f, 0.8f);
		super.scale(entity, poseStack, f);
	}

	@Override
	protected int getBlockLightLevel(BotanizeEntity entity, BlockPos blockPos) {
		return 9;
	}

	@Override
	public ResourceLocation getTextureLocation(BotanizeEntity entity) {
		return LOCATION;
	}
}

