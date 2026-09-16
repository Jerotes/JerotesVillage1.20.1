package com.jerotes.jerotesvillage.client.renderer;

import com.jerotes.jerotesvillage.JerotesVillage;
import com.jerotes.jerotesvillage.client.layer.MerorChampionCoatingLayer;
import com.jerotes.jerotesvillage.client.layer.MerorChampionGlowLayer;
import com.jerotes.jerotesvillage.client.model.Modelmeror_champion;
import com.jerotes.jerotesvillage.entity.Neutral.MerorMachine.MerorChampionEntity;
import com.mojang.blaze3d.vertex.PoseStack;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.entity.MobRenderer;
import net.minecraft.resources.ResourceLocation;

public class MerorChampionRenderer extends MobRenderer<MerorChampionEntity, Modelmeror_champion<MerorChampionEntity>> {
	private static final ResourceLocation LOCATION = new ResourceLocation(JerotesVillage.MODID, "textures/entity/meror_machine/meror_champion/meror_champion.png");
	private static final ResourceLocation BASE_LOCATION = new ResourceLocation(JerotesVillage.MODID, "textures/entity/meror_machine/meror_champion/meror_champion_base.png");
	private static final ResourceLocation BASE_LIGHT_LOCATION = new ResourceLocation(JerotesVillage.MODID, "textures/entity/meror_machine/meror_champion/meror_champion_base_light.png");
	public MerorChampionRenderer(EntityRendererProvider.Context context) {
		super(context, new Modelmeror_champion<>(context.bakeLayer(Modelmeror_champion.LAYER_LOCATION)), 1.2f);
		this.addLayer(new MerorChampionCoatingLayer<>(this, new Modelmeror_champion(context.bakeLayer(Modelmeror_champion.LAYER_LOCATION)), BASE_LOCATION, BASE_LIGHT_LOCATION));
		this.addLayer(new MerorChampionGlowLayer<>(this, new Modelmeror_champion(context.bakeLayer(Modelmeror_champion.LAYER_LOCATION))));
	}

	@Override
	public void render(MerorChampionEntity entity, float entityYaw, float partialTicks, PoseStack poseStack, MultiBufferSource multiBufferSource, int packedLightIn) {

		super.render(entity, entityYaw, partialTicks, poseStack, multiBufferSource, packedLightIn);
	}

	@Override
	protected float getFlipDegrees(MerorChampionEntity entity) {
		return 0.0f;
	}

	@Override
	public ResourceLocation getTextureLocation(MerorChampionEntity entity) {;
		return LOCATION;
	}
}

