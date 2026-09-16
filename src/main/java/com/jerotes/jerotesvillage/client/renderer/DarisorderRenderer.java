package com.jerotes.jerotesvillage.client.renderer;

import com.jerotes.jerotesvillage.JerotesVillage;
import com.jerotes.jerotesvillage.client.layer.DarisorderGlowOtherBodyLayer;
import com.jerotes.jerotesvillage.client.model.Modeldarisorder;
import com.jerotes.jerotesvillage.entity.MagicSummoned.Darisorder.DarisorderEntity;
import net.minecraft.ChatFormatting;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.entity.MobRenderer;
import net.minecraft.resources.ResourceLocation;

public class DarisorderRenderer extends MobRenderer<DarisorderEntity, Modeldarisorder<DarisorderEntity>> {
	private static final ResourceLocation LOCATION = new ResourceLocation(JerotesVillage.MODID, "textures/entity/darisorder.png");
	private static final ResourceLocation ENDER_LOCATION = new ResourceLocation(JerotesVillage.MODID, "textures/entity/darisorder_darisender.png");
	public DarisorderRenderer(EntityRendererProvider.Context context) {
		super(context, new Modeldarisorder(context.bakeLayer(Modeldarisorder.LAYER_LOCATION)), 0f);
		this.addLayer(new DarisorderGlowOtherBodyLayer<>(this, new Modeldarisorder(context.bakeLayer(Modeldarisorder.LAYER_LOCATION))));
	}

	@Override
	protected float getFlipDegrees(DarisorderEntity t) {
		return 0.0f;
	}

	@Override
	public ResourceLocation getTextureLocation(DarisorderEntity entity) {
		String string = ChatFormatting.stripFormatting(entity.getName().getString());
		if ("Ender".equals(string) || "Enderman".equals(string) || "Darisender".equals(string)) {
			return ENDER_LOCATION;
		}
		return LOCATION;
	}
}

