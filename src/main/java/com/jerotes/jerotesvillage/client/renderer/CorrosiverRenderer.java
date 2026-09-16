package com.jerotes.jerotesvillage.client.renderer;

import com.jerotes.jerotesvillage.JerotesVillage;
import com.jerotes.jerotesvillage.client.layer.CorrosiverAngryLayer;
import com.jerotes.jerotesvillage.client.layer.CorrosiverWindLayer;
import com.jerotes.jerotesvillage.client.layer.BeastToughHurtLayer;
import com.jerotes.jerotesvillage.client.model.Modelcorrosiver;
import com.jerotes.jerotesvillage.entity.Boss.Biome.CorrosiverEntity;
import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.math.Axis;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.entity.MobRenderer;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.Mth;

public class CorrosiverRenderer extends MobRenderer<CorrosiverEntity, Modelcorrosiver<CorrosiverEntity>> {
	private static final ResourceLocation LOCATION = new ResourceLocation(JerotesVillage.MODID, "textures/entity/corrosiver/corrosiver.png");
	private static final ResourceLocation ANGRY_LOCATION = new ResourceLocation(JerotesVillage.MODID, "textures/entity/corrosiver/corrosiver_angry.png");
	private static final ResourceLocation START_LOCATION = new ResourceLocation(JerotesVillage.MODID, "textures/entity/corrosiver/corrosiver_wind.png");
	private static final ResourceLocation HURT_LOCATION = new ResourceLocation(JerotesVillage.MODID, "textures/entity/corrosiver/corrosiver_hurt.png");
	public CorrosiverRenderer(EntityRendererProvider.Context context) {
		super(context, new Modelcorrosiver<>(context.bakeLayer(Modelcorrosiver.LAYER_LOCATION)), 1.3f);
		this.addLayer(new CorrosiverAngryLayer<>(this, new Modelcorrosiver(context.bakeLayer(Modelcorrosiver.LAYER_LOCATION)), ANGRY_LOCATION));
		this.addLayer(new CorrosiverWindLayer(this, new Modelcorrosiver(context.bakeLayer(Modelcorrosiver.LAYER_LOCATION)), START_LOCATION));
		this.addLayer(new BeastToughHurtLayer(this, new Modelcorrosiver(context.bakeLayer(Modelcorrosiver.LAYER_LOCATION)), HURT_LOCATION));
	}

	@Override
	public void render(CorrosiverEntity entity, float f, float f2, PoseStack poseStack, MultiBufferSource multiBufferSource, int n) {
		entity.thisTickRenderTime += 1;
		float windAnim = entity.getWindAnim();
		float fs = entity.lastTickRenderTime + 1;
		float smoothFactor = 1f / fs;
		entity.windAnimProgress = Mth.lerp(smoothFactor, entity.windAnimProgress, windAnim);
		float angryAnim = entity.getAngryAnim();
		entity.angryAnimProgress = Mth.lerp(smoothFactor, entity.angryAnimProgress, angryAnim);
		super.render(entity, f, f2, poseStack, multiBufferSource, n);
	}

	@Override
	protected void setupRotations(CorrosiverEntity entity, PoseStack poseStack, float f, float f2, float f3) {
		super.setupRotations(entity, poseStack, f, f2, f3);
		if ((double)entity.walkAnimation.speed() < 0.01) {
			return;
		}
		float f4 = 13.0f;
		float f5 = entity.walkAnimation.position(f3) + 6.0f;
		float f6 = (Math.abs(f5 % 13.0f - 6.5f) - 3.25f) / 3.25f;
		poseStack.mulPose(Axis.ZP.rotationDegrees(0.5f * f6));
	}

	@Override
	protected float getFlipDegrees(CorrosiverEntity t) {
		return 0.0f;
	}

	@Override
	public ResourceLocation getTextureLocation(CorrosiverEntity entity) {
		return LOCATION;
	}
}

