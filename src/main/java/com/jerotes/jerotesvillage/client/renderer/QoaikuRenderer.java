package com.jerotes.jerotesvillage.client.renderer;

import com.jerotes.jerotesvillage.JerotesVillage;
import com.jerotes.jerotesvillage.client.layer.QoaikuLoreBodyLayer;
import com.jerotes.jerotesvillage.client.layer.QoaikuOtherBodyLayer;
import com.jerotes.jerotesvillage.client.model.Modelhumanoid;
import com.jerotes.jerotesvillage.client.model.Modelqoaiku_old_lore;
import com.jerotes.jerotesvillage.client.model.Modelqoaiku_other;
import com.jerotes.jerotesvillage.config.OtherMainConfig;
import com.jerotes.jerotesvillage.entity.Humanoid.Qoaiku.QoaikuEntity;
import com.jerotes.jerotesvillage.entity.Humanoid.Qoaiku.QoaikuSeekerEntity;
import com.jerotes.jerotesvillage.event.WeatherEvent;
import com.mojang.blaze3d.vertex.PoseStack;
import net.minecraft.ChatFormatting;
import net.minecraft.client.model.HumanoidArmorModel;
import net.minecraft.client.model.geom.ModelLayers;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.entity.HumanoidMobRenderer;
import net.minecraft.client.renderer.entity.layers.HumanoidArmorLayer;
import net.minecraft.resources.ResourceLocation;

public class QoaikuRenderer extends HumanoidMobRenderer<QoaikuEntity, Modelhumanoid<QoaikuEntity>> {
	private static final ResourceLocation QOAIKU_LOCATION = new ResourceLocation(JerotesVillage.MODID, "textures/entity/qoaiku/qoaiku.png");
	private static final ResourceLocation NULL_LOCATION = new ResourceLocation(JerotesVillage.MODID, "textures/entity/null.png");
	private static final ResourceLocation QOAIKU_SEEKER_LOCATION = new ResourceLocation(JerotesVillage.MODID, "textures/entity/qoaiku/qoaiku_seeker.png");
	public QoaikuRenderer(EntityRendererProvider.Context context) {
		super(context, new Modelhumanoid(context.bakeLayer(Modelhumanoid.LAYER_LOCATION)), 0.5f);
		this.addLayer(new QoaikuOtherBodyLayer<>(this, new Modelqoaiku_other(context.bakeLayer(Modelqoaiku_other.LAYER_LOCATION))));
		this.addLayer(new QoaikuLoreBodyLayer<>(this, new Modelqoaiku_old_lore<>(context.bakeLayer(Modelqoaiku_old_lore.LAYER_LOCATION))));
		this.addLayer(new HumanoidArmorLayer(this, new HumanoidArmorModel(context.bakeLayer(ModelLayers.PLAYER_INNER_ARMOR)), new HumanoidArmorModel(context.bakeLayer(ModelLayers.PLAYER_OUTER_ARMOR)), context.getModelManager()));
	}

	@Override
	protected void scale(QoaikuEntity entity, PoseStack poseStack, float f) {
		poseStack.scale(0.9f, 0.9f, 0.9f);
		super.scale(entity, poseStack, f);
	}

	@Override
	public ResourceLocation getTextureLocation(QoaikuEntity entity) {
		if (WeatherEvent.isNewYearsDay() && OtherMainConfig.SpecialDay) {
			return NULL_LOCATION;
		}
		String string = ChatFormatting.stripFormatting(entity.getName().getString());
		if ("Old Lore".equals(string)) {
			return NULL_LOCATION;
		}

		if (entity instanceof QoaikuSeekerEntity) {
			return QOAIKU_SEEKER_LOCATION;
		}
		return QOAIKU_LOCATION;
	}
}
