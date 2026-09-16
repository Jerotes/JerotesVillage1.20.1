package com.jerotes.jerotesvillage.client.renderer;

import com.jerotes.jerotes.client.layer.TameLayer;
import com.jerotes.jerotesvillage.JerotesVillage;
import com.jerotes.jerotesvillage.client.model.Modelgemstone_malignasaur;
import com.jerotes.jerotesvillage.entity.Animal.MalignasaurEntity;
import com.mojang.blaze3d.vertex.PoseStack;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.entity.MobRenderer;
import net.minecraft.client.renderer.entity.layers.SaddleLayer;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.Mth;

public class MalignasaurRenderer extends MobRenderer<MalignasaurEntity, Modelgemstone_malignasaur<MalignasaurEntity>> {
	private static final ResourceLocation LOCATION = new ResourceLocation(JerotesVillage.MODID, "textures/entity/malignasaur.png");
	private static final ResourceLocation TAME_LOCATION = new ResourceLocation(JerotesVillage.MODID, "textures/entity/malignasaur_tame.png");
	private static final ResourceLocation SADDLE_LOCATION = new ResourceLocation(JerotesVillage.MODID, "textures/entity/malignasaur_saddle.png");
	public MalignasaurRenderer(EntityRendererProvider.Context context) {
		super(context, new Modelgemstone_malignasaur(context.bakeLayer(Modelgemstone_malignasaur.LAYER_LOCATION)), 3.375f);
		this.addLayer(new TameLayer(this, new Modelgemstone_malignasaur(context.bakeLayer(Modelgemstone_malignasaur.LAYER_LOCATION)), TAME_LOCATION));
		this.addLayer(new SaddleLayer(this, new Modelgemstone_malignasaur(context.bakeLayer(Modelgemstone_malignasaur.LAYER_LOCATION)), SADDLE_LOCATION));
	}

	@Override
	public void render(MalignasaurEntity t, float f, float f2, PoseStack poseStack, MultiBufferSource multiBufferSource, int n) {
		t.thisTickRenderTime += 1;
		float waterAnim = t.getWaterAnim();
		float fs = t.lastTickRenderTime + 1;
		float smoothFactor = 1f / fs;
		t.waterAnimProgress = Mth.lerp(smoothFactor, t.waterAnimProgress, waterAnim);
		super.render(t, f, f2, poseStack, multiBufferSource, n);
	}

	@Override
	protected void scale(MalignasaurEntity entity, PoseStack poseStack, float f) {
		poseStack.scale(1.5f, 1.5f, 1.5f);
		super.scale(entity, poseStack, f);
	}

	@Override
	public ResourceLocation getTextureLocation(MalignasaurEntity entity) {
		return LOCATION;
	}
}
