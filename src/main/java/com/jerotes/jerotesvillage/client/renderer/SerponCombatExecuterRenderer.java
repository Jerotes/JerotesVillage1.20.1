package com.jerotes.jerotesvillage.client.renderer;

import com.jerotes.jerotesvillage.JerotesVillage;
import com.jerotes.jerotesvillage.client.layer.SedimentItemInHandLayer;
import com.jerotes.jerotesvillage.client.layer.SerponCombatExecuterOtherBodyLayer;
import com.jerotes.jerotesvillage.client.layer.SerponCombatExecuterPortaBaselLayer;
import com.jerotes.jerotesvillage.client.layer.SerponCombatExecuterPortalLayer;
import com.jerotes.jerotesvillage.client.model.Modelserpon_combat_executer;
import com.jerotes.jerotesvillage.client.model.Modelserpon_combat_executer_portal;
import com.jerotes.jerotesvillage.entity.Boss.Biome.SerponCombatExecuterEntity;
import com.mojang.blaze3d.vertex.PoseStack;
import net.minecraft.client.model.HumanoidArmorModel;
import net.minecraft.client.model.geom.ModelLayers;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.entity.MobRenderer;
import net.minecraft.client.renderer.entity.layers.CustomHeadLayer;
import net.minecraft.client.renderer.entity.layers.ElytraLayer;
import net.minecraft.client.renderer.entity.layers.HumanoidArmorLayer;
import net.minecraft.core.BlockPos;
import net.minecraft.resources.ResourceLocation;

public class SerponCombatExecuterRenderer extends MobRenderer<SerponCombatExecuterEntity, Modelserpon_combat_executer<SerponCombatExecuterEntity>> {
	private static final ResourceLocation LOCATION = new ResourceLocation(JerotesVillage.MODID, "textures/entity/serpon/serpon_combat_executer.png");
	private static final ResourceLocation NULL_LOCATION = new ResourceLocation(JerotesVillage.MODID, "textures/entity/null.png");
	private static final ResourceLocation PORTAL_LOCATION = new ResourceLocation(JerotesVillage.MODID, "textures/entity/serpon/serpon_combat_executer_portal.png");
	private static final ResourceLocation PORTAL_BASE_LOCATION = new ResourceLocation(JerotesVillage.MODID, "textures/entity/serpon/serpon_combat_executer_portal_base.png");

	public SerponCombatExecuterRenderer(EntityRendererProvider.Context context) {
		super(context, new Modelserpon_combat_executer(context.bakeLayer(Modelserpon_combat_executer.LAYER_LOCATION)), 0.5f);
		this.addLayer(new SerponCombatExecuterPortaBaselLayer(this, new Modelserpon_combat_executer_portal(context.bakeLayer(Modelserpon_combat_executer_portal.LAYER_LOCATION)), PORTAL_BASE_LOCATION));
		this.addLayer(new SerponCombatExecuterPortalLayer(this, new Modelserpon_combat_executer_portal(context.bakeLayer(Modelserpon_combat_executer_portal.LAYER_LOCATION)), PORTAL_LOCATION));
		this.addLayer(new SerponCombatExecuterOtherBodyLayer(this, new Modelserpon_combat_executer(context.bakeLayer(Modelserpon_combat_executer.LAYER_LOCATION))));
		this.addLayer(new CustomHeadLayer(this, context.getModelSet(), 1.0F, 1.0F, 1.0F, context.getItemInHandRenderer()));
		this.addLayer(new ElytraLayer(this, context.getModelSet()));
		this.addLayer(new SedimentItemInHandLayer<>(this, context.getItemInHandRenderer()));
		this.addLayer(new HumanoidArmorLayer(this, new HumanoidArmorModel(context.bakeLayer(ModelLayers.PLAYER_INNER_ARMOR)), new HumanoidArmorModel(context.bakeLayer(ModelLayers.PLAYER_OUTER_ARMOR)), context.getModelManager()));
	}

	@Override
	protected void scale(SerponCombatExecuterEntity entity, PoseStack poseStack, float f) {
		if (!entity.isTrueCome() && entity.deathTime > 1) {
			poseStack.scale(1.05f / entity.deathTime, 1.05f / entity.deathTime, 1.05f / entity.deathTime);
			super.scale(entity, poseStack, f);
			return;
		}
		poseStack.scale(1.05f, 1.05f, 1.05f);
		super.scale(entity, poseStack, f);
	}

	@Override
	protected int getBlockLightLevel(SerponCombatExecuterEntity entity, BlockPos blockPos) {
		if (!entity.isTrueCome()) {
			return 15;
		}
		return 9;
	}

	@Override
	protected float getFlipDegrees(SerponCombatExecuterEntity entity) {
		return 0.0f;
	}

	@Override
	public ResourceLocation getTextureLocation(SerponCombatExecuterEntity entity) {
		if (!entity.isTrueCome()) {
			return NULL_LOCATION;
		}
		return LOCATION;
	}
}
