package com.jerotes.jerotesvillage.client.renderer;

import com.jerotes.jerotesvillage.JerotesVillage;
import com.jerotes.jerotesvillage.client.layer.BossShowSerponCombatExecuterItemInHandLayer;
import com.jerotes.jerotesvillage.client.model.Modelserpon_combat_executer;
import com.jerotes.jerotesvillage.entity.Other.Other.BossShowEntity;
import com.mojang.blaze3d.vertex.PoseStack;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.entity.LivingEntityRenderer;
import net.minecraft.core.BlockPos;
import net.minecraft.resources.ResourceLocation;

public class BossShowEntitySerponCombatExecuterRenderer extends LivingEntityRenderer<BossShowEntity, Modelserpon_combat_executer<BossShowEntity>> {
	private static final ResourceLocation LOCATION = new ResourceLocation(JerotesVillage.MODID, "textures/entity/serpon/serpon_combat_executer.png");
	public BossShowEntitySerponCombatExecuterRenderer(EntityRendererProvider.Context context) {
		super(context, new Modelserpon_combat_executer(context.bakeLayer(Modelserpon_combat_executer.LAYER_LOCATION)), 0.5f);
		this.addLayer(new BossShowSerponCombatExecuterItemInHandLayer(this, context.getItemInHandRenderer()));
	}

	@Override
	protected int getBlockLightLevel(BossShowEntity entity, BlockPos blockPos) {
		return 9;
	}

	@Override
	protected void scale(BossShowEntity entity, PoseStack poseStack, float f) {
		poseStack.scale(1.05f, 1.05f, 1.05f);
		super.scale(entity, poseStack, f);
	}

	@Override
	public ResourceLocation getTextureLocation(BossShowEntity entity) {
		return LOCATION;
	}
}
