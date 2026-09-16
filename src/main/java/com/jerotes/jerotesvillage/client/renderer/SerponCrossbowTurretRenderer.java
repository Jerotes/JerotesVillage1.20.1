package com.jerotes.jerotesvillage.client.renderer;

import com.jerotes.jerotesvillage.JerotesVillage;
import com.jerotes.jerotesvillage.client.layer.SerponCrossbowTurretLayer;
import com.jerotes.jerotesvillage.client.model.Modelserpon_crossbow_turret;
import com.jerotes.jerotesvillage.entity.Other.Machine.SerponCrossbowTurretEntity;
import com.mojang.blaze3d.vertex.PoseStack;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.entity.MobRenderer;
import net.minecraft.core.BlockPos;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.Mth;

public class SerponCrossbowTurretRenderer extends MobRenderer<SerponCrossbowTurretEntity, Modelserpon_crossbow_turret<SerponCrossbowTurretEntity>> {
	private static final ResourceLocation LOCATION = new ResourceLocation(JerotesVillage.MODID, "textures/entity/serpon_crossbow_turret.png");
	private static final ResourceLocation NULL_LOCATION = new ResourceLocation(JerotesVillage.MODID, "textures/entity/null.png");
	public SerponCrossbowTurretRenderer(EntityRendererProvider.Context context) {
		super(context, new Modelserpon_crossbow_turret<>(context.bakeLayer(Modelserpon_crossbow_turret.LAYER_LOCATION)), 0f);
		this.addLayer(new SerponCrossbowTurretLayer<>(this, new Modelserpon_crossbow_turret(context.bakeLayer(Modelserpon_crossbow_turret.LAYER_LOCATION)), LOCATION));
	}

	@Override
	public void render(SerponCrossbowTurretEntity t, float f, float f2, PoseStack poseStack, MultiBufferSource multiBufferSource, int n) {
		t.thisTickRenderTime += 1;
		float loadAnim = t.getLoadTick();
		float fs = t.lastTickRenderTime + 1;
		float smoothFactor = 1f / fs;
		t.attackAnimProgress = Mth.lerp(smoothFactor, t.attackAnimProgress, loadAnim);
		super.render(t, f, f2, poseStack, multiBufferSource, n);
	}

	@Override
	protected int getBlockLightLevel(SerponCrossbowTurretEntity entity, BlockPos blockPos) {
		return 6;
	}

	@Override
	protected float getFlipDegrees(SerponCrossbowTurretEntity entity) {
		return 0.0f;
	}

	@Override
	public ResourceLocation getTextureLocation(SerponCrossbowTurretEntity entity) {
		return NULL_LOCATION;
	}
}