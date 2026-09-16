package com.jerotes.jerotesvillage.client.renderer;

import com.jerotes.jerotesvillage.JerotesVillage;
import com.jerotes.jerotesvillage.client.layer.FlamoarGlowOtherBodyLayer;
import com.jerotes.jerotesvillage.client.layer.FlamoarItemInHandLayer;
import com.jerotes.jerotesvillage.client.layer.FlamoarLadyGlowOtherBodyLayer;
import com.jerotes.jerotesvillage.client.model.Modelflamoar;
import com.jerotes.jerotesvillage.client.model.Modelflamoar_lady;
import com.jerotes.jerotesvillage.entity.Neutral.FlamoarEntity;
import com.mojang.blaze3d.vertex.PoseStack;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.entity.MobRenderer;
import net.minecraft.core.BlockPos;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.Mth;

public class FlamoarRenderer extends MobRenderer<FlamoarEntity, Modelflamoar<FlamoarEntity>> {
	private static final ResourceLocation LOCATION = new ResourceLocation(JerotesVillage.MODID, "textures/entity/flamoar/flamoar.png");
	private static final ResourceLocation LADY_LOCATION = new ResourceLocation(JerotesVillage.MODID, "textures/entity/flamoar/flamoar_lady.png");
	private final Modelflamoar<FlamoarEntity> lady;
	private final Modelflamoar<FlamoarEntity> normal;
	private int typeState = -1;

	public FlamoarRenderer(EntityRendererProvider.Context context) {
		super(context, new Modelflamoar(context.bakeLayer(Modelflamoar.LAYER_LOCATION)), 0f);
		//this.addLayer(new FlamoarLadyOtherBodyLayer<>(this, new Modelflamoar_lady(context.bakeLayer(Modelflamoar_lady.LAYER_LOCATION))));
		this.addLayer(new FlamoarGlowOtherBodyLayer<>(this, new Modelflamoar(context.bakeLayer(Modelflamoar.LAYER_LOCATION))));
		this.addLayer(new FlamoarLadyGlowOtherBodyLayer<>(this, new Modelflamoar_lady<>(context.bakeLayer(Modelflamoar_lady.LAYER_LOCATION))));
		this.addLayer(new FlamoarItemInHandLayer(this, context.getItemInHandRenderer()));
		this.lady = new Modelflamoar_lady<>(context.bakeLayer(Modelflamoar_lady.LAYER_LOCATION));
		this.normal = new Modelflamoar<>(context.bakeLayer(Modelflamoar.LAYER_LOCATION));
	}

	@Override
	public void render(FlamoarEntity t, float f, float f2, PoseStack poseStack, MultiBufferSource multiBufferSource, int n) {
		boolean isLady = t.isLady();
		int newTypeState = isLady ? 1 : 0;

		if (newTypeState != typeState) {
			this.model = isLady ? this.lady : this.normal;
			this.typeState = newTypeState;
		}

		t.thisTickRenderTime += 1;
		float attackAnim = t.getAttackAnim();
		float fs = t.lastTickRenderTime + 1;
		float smoothFactor = 1f / fs;
		t.attackAnimProgress = Mth.lerp(smoothFactor, t.attackAnimProgress, attackAnim);
		super.render(t, f, f2, poseStack, multiBufferSource, n);
	}

	@Override
	protected void scale(FlamoarEntity entity, PoseStack poseStack, float f) {
		poseStack.scale(1.35f, 1.35f, 1.35f);
		super.scale(entity, poseStack, f);
	}

	@Override
	protected float getFlipDegrees(FlamoarEntity t) {
		return 0.0f;
	}

	@Override
	protected int getBlockLightLevel(FlamoarEntity entity, BlockPos blockPos) {
		return 15;
	}

	@Override
	public ResourceLocation getTextureLocation(FlamoarEntity entity) {
		if (entity.isLady())
			return LADY_LOCATION;
		return LOCATION;
	}
}
