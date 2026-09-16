package com.jerotes.jerotesvillage.client.renderer;

import com.jerotes.jerotesvillage.JerotesVillage;
import com.jerotes.jerotesvillage.client.layer.SedimentItemInHandLayer;
import com.jerotes.jerotesvillage.client.layer.SerponGlowBodyLayer;
import com.jerotes.jerotesvillage.client.model.Modelserpon_fire_apostle;
import com.jerotes.jerotesvillage.entity.Humanoid.Serpon.SerponFireApostleEntity;
import com.mojang.blaze3d.vertex.PoseStack;
import net.minecraft.client.model.HumanoidArmorModel;
import net.minecraft.client.model.geom.ModelLayers;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.entity.MobRenderer;
import net.minecraft.client.renderer.entity.layers.CustomHeadLayer;
import net.minecraft.client.renderer.entity.layers.ElytraLayer;
import net.minecraft.client.renderer.entity.layers.HumanoidArmorLayer;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.Mth;

public class SerponFireApostleRenderer extends MobRenderer<SerponFireApostleEntity, Modelserpon_fire_apostle<SerponFireApostleEntity>> {
	private static final ResourceLocation SERPON_FIRE_APOSTLE_LOCATION = new ResourceLocation(JerotesVillage.MODID, "textures/entity/serpon/serpon_fire_apostle.png");
	public SerponFireApostleRenderer(EntityRendererProvider.Context context) {
		super(context, new Modelserpon_fire_apostle(context.bakeLayer(Modelserpon_fire_apostle.LAYER_LOCATION)), 0.5f);
		this.addLayer(new SerponGlowBodyLayer<>(this, new Modelserpon_fire_apostle(context.bakeLayer(Modelserpon_fire_apostle.LAYER_LOCATION))));
		this.addLayer(new CustomHeadLayer(this, context.getModelSet(), 1.0F, 1.0F, 1.0F, context.getItemInHandRenderer()));
		this.addLayer(new ElytraLayer(this, context.getModelSet()));
		this.addLayer(new SedimentItemInHandLayer<>(this, context.getItemInHandRenderer()));
		this.addLayer(new HumanoidArmorLayer(this, new HumanoidArmorModel(context.bakeLayer(ModelLayers.PLAYER_INNER_ARMOR)), new HumanoidArmorModel(context.bakeLayer(ModelLayers.PLAYER_OUTER_ARMOR)), context.getModelManager()));
	}

	@Override
	public void render(SerponFireApostleEntity t, float f, float f2, PoseStack poseStack, MultiBufferSource multiBufferSource, int n) {
		t.thisTickRenderTime += 1;
		float attackAnim = t.getAttackAnim();
		float fs = t.lastTickRenderTime + 1;
		float smoothFactor = 1f / fs;
		t.attackAnimProgress = Mth.lerp(smoothFactor, t.attackAnimProgress, attackAnim);
		super.render(t, f, f2, poseStack, multiBufferSource, n);
	}

	@Override
	protected float getFlipDegrees(SerponFireApostleEntity entity) {
		return 0.0f;
	}

	@Override
	public ResourceLocation getTextureLocation(SerponFireApostleEntity entity) {
		return SERPON_FIRE_APOSTLE_LOCATION;
	}
}
