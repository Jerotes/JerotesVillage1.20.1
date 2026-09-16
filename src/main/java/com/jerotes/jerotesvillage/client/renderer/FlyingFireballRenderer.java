package com.jerotes.jerotesvillage.client.renderer;

import com.jerotes.jerotesvillage.JerotesVillage;
import com.jerotes.jerotesvillage.client.layer.FlyingFireballFireLayer;
import com.jerotes.jerotesvillage.client.model.Modelflying_fireball;
import com.jerotes.jerotesvillage.entity.MagicSummoned.FlyingFireball.FlyingFireballEntity;
import com.jerotes.jerotesvillage.client.layer.FlyingFireballOuterLayer;
import com.jerotes.jerotesvillage.entity.MagicSummoned.FlyingFireball.FuryFlyingFireballEntity;
import com.mojang.blaze3d.vertex.PoseStack;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.entity.MobRenderer;
import net.minecraft.core.BlockPos;
import net.minecraft.resources.ResourceLocation;

public class FlyingFireballRenderer extends MobRenderer<FlyingFireballEntity, Modelflying_fireball<FlyingFireballEntity>> {
	private static final ResourceLocation LOCATION = new ResourceLocation(JerotesVillage.MODID, "textures/entity/flying_fireball.png");
	private static final ResourceLocation FURY_LOCATION = new ResourceLocation(JerotesVillage.MODID, "textures/entity/fury_flying_fireball.png");
	private static final ResourceLocation OUT_LOCATION = new ResourceLocation(JerotesVillage.MODID, "textures/entity/flying_fireball_out.png");
	private static final ResourceLocation FIRE_LOCATION = new ResourceLocation(JerotesVillage.MODID, "textures/entity/flying_fireball_fire.png");
	public FlyingFireballRenderer(EntityRendererProvider.Context context) {
		super(context, new Modelflying_fireball(context.bakeLayer(Modelflying_fireball.LAYER_LOCATION)), 0f);
		this.addLayer(new FlyingFireballOuterLayer(this, new Modelflying_fireball(context.bakeLayer(Modelflying_fireball.LAYER_LOCATION)), OUT_LOCATION));
		this.addLayer(new FlyingFireballFireLayer(this, new Modelflying_fireball(context.bakeLayer(Modelflying_fireball.LAYER_LOCATION)), FIRE_LOCATION));
	}

	@Override
	protected void scale(FlyingFireballEntity entity, PoseStack poseStack, float f) {
		poseStack.scale(0.5f, 0.5f, 0.5f);
		super.scale(entity, poseStack, f);
	}
	@Override
	protected int getBlockLightLevel(FlyingFireballEntity entity, BlockPos blockPos) {
		return 15;
	}
	@Override
	public ResourceLocation getTextureLocation(FlyingFireballEntity entity) {
		if (entity instanceof FuryFlyingFireballEntity) {
			return FURY_LOCATION;
		}
		return LOCATION;
	}
}