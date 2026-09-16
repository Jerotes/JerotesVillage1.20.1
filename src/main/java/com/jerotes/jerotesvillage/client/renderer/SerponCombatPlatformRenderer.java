package com.jerotes.jerotesvillage.client.renderer;

import com.jerotes.jerotesvillage.JerotesVillage;
import com.jerotes.jerotesvillage.client.model.Modelserpon_combat_platform;
import com.jerotes.jerotesvillage.entity.Other.Machine.SerponCombatPlatformEntity;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.entity.MobRenderer;
import net.minecraft.core.BlockPos;
import net.minecraft.resources.ResourceLocation;

public class SerponCombatPlatformRenderer extends MobRenderer<SerponCombatPlatformEntity, Modelserpon_combat_platform<SerponCombatPlatformEntity>> {
	private static final ResourceLocation LOCATION = new ResourceLocation(JerotesVillage.MODID, "textures/entity/serpon_combat_platform.png");
	public SerponCombatPlatformRenderer(EntityRendererProvider.Context context) {
		super(context, new Modelserpon_combat_platform(context.bakeLayer(Modelserpon_combat_platform.LAYER_LOCATION)), 0.5f);
	}

	@Override
	protected int getBlockLightLevel(SerponCombatPlatformEntity entity, BlockPos blockPos) {
		return 6;
	}

	@Override
	protected float getFlipDegrees(SerponCombatPlatformEntity entity) {
		return 0.0f;
	}

	@Override
	public ResourceLocation getTextureLocation(SerponCombatPlatformEntity entity) {
		return LOCATION;
	}
}