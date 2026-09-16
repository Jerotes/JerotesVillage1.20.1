package com.jerotes.jerotesvillage.client.renderer;

import com.jerotes.jerotesvillage.JerotesVillage;
import com.jerotes.jerotesvillage.client.layer.WonderlinItemInHandLayer;
import com.jerotes.jerotesvillage.client.layer.WonderlinMerchantAddLayer;
import com.jerotes.jerotesvillage.client.model.Modelwonderlin;
import com.jerotes.jerotesvillage.client.model.Modelwonderlin_merchant_add;
import com.jerotes.jerotesvillage.entity.Neutral.Wonderlin.BaseWonderlinEntity;
import com.jerotes.jerotesvillage.entity.Neutral.Wonderlin.WonderlinMerchantEntity;
import com.jerotes.jerotesvillage.entity.Neutral.Wonderlin.WonderlinWarriorEntity;
import net.minecraft.client.model.HumanoidArmorModel;
import net.minecraft.client.model.geom.ModelLayers;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.entity.MobRenderer;
import net.minecraft.client.renderer.entity.layers.CustomHeadLayer;
import net.minecraft.client.renderer.entity.layers.ElytraLayer;
import net.minecraft.client.renderer.entity.layers.HumanoidArmorLayer;
import net.minecraft.resources.ResourceLocation;

public class WonderlinRenderer extends MobRenderer<BaseWonderlinEntity, Modelwonderlin<BaseWonderlinEntity>> {
	private static final ResourceLocation LOCATION = new ResourceLocation(JerotesVillage.MODID, "textures/entity/wonderlin.png");
	private static final ResourceLocation MERCHANT_LOCATION = new ResourceLocation(JerotesVillage.MODID, "textures/entity/wonderlin_merchant.png");
	private static final ResourceLocation WARRIOR_LOCATION = new ResourceLocation(JerotesVillage.MODID, "textures/entity/wonderlin_warrior.png");

	public WonderlinRenderer(EntityRendererProvider.Context context) {
		super(context, new Modelwonderlin(context.bakeLayer(Modelwonderlin.LAYER_LOCATION)), 0.3f);
		this.addLayer(new WonderlinMerchantAddLayer<>(this, new Modelwonderlin_merchant_add(context.bakeLayer(Modelwonderlin_merchant_add.LAYER_LOCATION)), MERCHANT_LOCATION));
		this.addLayer(new CustomHeadLayer<>(this, context.getModelSet(), context.getItemInHandRenderer()));
		this.addLayer(new ElytraLayer<>(this, context.getModelSet()));
		this.addLayer(new WonderlinItemInHandLayer<>(this, context.getItemInHandRenderer()));
		this.addLayer(new HumanoidArmorLayer<>(this, new HumanoidArmorModel(context.bakeLayer(ModelLayers.PLAYER_INNER_ARMOR)), new HumanoidArmorModel(context.bakeLayer(ModelLayers.PLAYER_OUTER_ARMOR)), context.getModelManager()));
	}

	@Override
	public ResourceLocation getTextureLocation(BaseWonderlinEntity entity) {
		if (entity instanceof WonderlinMerchantEntity) {
			return MERCHANT_LOCATION;
		}
		if (entity instanceof WonderlinWarriorEntity) {
			return WARRIOR_LOCATION;
		}
		return LOCATION;
	}
}

