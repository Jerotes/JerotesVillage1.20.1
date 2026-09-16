package com.jerotes.jerotesvillage.client.renderer;

import com.jerotes.jerotesvillage.JerotesVillage;
import com.jerotes.jerotesvillage.client.layer.BotanizeItemInHandLayer;
import com.jerotes.jerotesvillage.client.model.Modelbotanize;
import com.jerotes.jerotesvillage.entity.MagicSummoned.Botanize.CoilvineBotanizeEntity;
import com.mojang.blaze3d.vertex.PoseStack;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.entity.MobRenderer;
import net.minecraft.core.BlockPos;
import net.minecraft.resources.ResourceLocation;

public class CoilvineBotanizeRenderer extends MobRenderer<CoilvineBotanizeEntity, Modelbotanize<CoilvineBotanizeEntity>> {
	private static final ResourceLocation LOCATION = new ResourceLocation(JerotesVillage.MODID, "textures/entity/coilvine_botanize.png");
	public CoilvineBotanizeRenderer(EntityRendererProvider.Context context) {
		super(context, new Modelbotanize(context.bakeLayer(Modelbotanize.LAYER_LOCATION)), 0.45f);
		this.addLayer(new BotanizeItemInHandLayer(this, context.getItemInHandRenderer()));
	}

	@Override
	protected void scale(CoilvineBotanizeEntity entity, PoseStack poseStack, float f) {
		poseStack.scale(1.2f, 1.2f, 1.2f);
		super.scale(entity, poseStack, f);
	}

	@Override
	protected int getBlockLightLevel(CoilvineBotanizeEntity entity, BlockPos blockPos) {
		return 9;
	}

	@Override
	public ResourceLocation getTextureLocation(CoilvineBotanizeEntity entity) {
		return LOCATION;
	}
}

