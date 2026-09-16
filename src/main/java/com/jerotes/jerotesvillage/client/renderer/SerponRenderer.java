package com.jerotes.jerotesvillage.client.renderer;

import com.jerotes.jerotes.entity.Interface.EliteEntity;
import com.jerotes.jerotesvillage.JerotesVillage;
import com.jerotes.jerotesvillage.client.layer.SerponGlowBodyLayer;
import com.jerotes.jerotesvillage.client.layer.SerponOtherBodyLayer;
import com.jerotes.jerotesvillage.client.model.Modelhumanoid;
import com.jerotes.jerotesvillage.client.model.Modelserpon_other;
import com.jerotes.jerotesvillage.entity.Humanoid.Serpon.*;
import net.minecraft.client.model.HumanoidArmorModel;
import net.minecraft.client.model.geom.ModelLayers;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.entity.HumanoidMobRenderer;
import net.minecraft.client.renderer.entity.layers.HumanoidArmorLayer;
import net.minecraft.resources.ResourceLocation;

public class SerponRenderer extends HumanoidMobRenderer<SerponEntity, Modelhumanoid<SerponEntity>> {
	private static final ResourceLocation SERPON_LOCATION = new ResourceLocation(JerotesVillage.MODID, "textures/entity/serpon/serpon.png");
	private static final ResourceLocation SERPON_RIOTER_LOCATION = new ResourceLocation(JerotesVillage.MODID, "textures/entity/serpon/serpon_rioter.png");
	private static final ResourceLocation SERPON_PIONEER_LOCATION = new ResourceLocation(JerotesVillage.MODID, "textures/entity/serpon/serpon_pioneer.png");
	private static final ResourceLocation SERPON_COLONIAL_SOLDIER_LOCATION = new ResourceLocation(JerotesVillage.MODID, "textures/entity/serpon/serpon_colonial_soldier.png");
	private static final ResourceLocation SERPON_FLAME_WIZARD_LOCATION = new ResourceLocation(JerotesVillage.MODID, "textures/entity/serpon/serpon_flame_wizard.png");
	private static final ResourceLocation SERPON_INTRUDER_LOCATION = new ResourceLocation(JerotesVillage.MODID, "textures/entity/serpon/serpon_intruder.png");
	public SerponRenderer(EntityRendererProvider.Context context) {
		super(context, new Modelhumanoid(context.bakeLayer(Modelhumanoid.LAYER_LOCATION)), 0.55f);
		this.addLayer(new SerponGlowBodyLayer<>(this, new Modelhumanoid(context.bakeLayer(Modelhumanoid.LAYER_LOCATION))));
		this.addLayer(new SerponOtherBodyLayer<>(this, new Modelserpon_other(context.bakeLayer(Modelserpon_other.LAYER_LOCATION))));
		this.addLayer(new HumanoidArmorLayer(this, new HumanoidArmorModel(context.bakeLayer(ModelLayers.PLAYER_INNER_ARMOR)), new HumanoidArmorModel(context.bakeLayer(ModelLayers.PLAYER_OUTER_ARMOR)), context.getModelManager()));
	}

	@Override
	protected float getFlipDegrees(SerponEntity entity) {
		return 0.0f;
	}

	@Override
	protected boolean isShaking(SerponEntity entity) {
		return (entity.deathTime > 0 && entity.deathTime <= 20 && entity instanceof EliteEntity) || super.isShaking(entity);
	}

	@Override
	public ResourceLocation getTextureLocation(SerponEntity entity) {
		if (entity instanceof SerponRioterEntity) {
			return SERPON_RIOTER_LOCATION;
		}
		else if (entity instanceof SerponPioneerEntity) {
			return SERPON_PIONEER_LOCATION;
		}
		else if (entity instanceof SerponColonialSoldierEntity) {
			return SERPON_COLONIAL_SOLDIER_LOCATION;
		}
		else if (entity instanceof SerponFlameWizardEntity) {
			return SERPON_FLAME_WIZARD_LOCATION;
		}
		else if (entity instanceof SerponIntruderEntity) {
			return SERPON_INTRUDER_LOCATION;
		}
		return SERPON_LOCATION;
	}
}
