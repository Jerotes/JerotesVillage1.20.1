package com.jerotes.jerotesvillage.client.renderer;

import com.jerotes.jerotesvillage.JerotesVillage;
import com.jerotes.jerotesvillage.client.layer.FirepowerPourerChainsawLayer;
import com.jerotes.jerotesvillage.client.layer.FirepowerPourerGunLayer;
import com.jerotes.jerotesvillage.client.model.Modelfirepower_pourer;
import com.jerotes.jerotesvillage.entity.Monster.IllagerFaction.FirepowerPourerEntity;
import net.minecraft.ChatFormatting;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.entity.MobRenderer;
import net.minecraft.client.renderer.entity.layers.CustomHeadLayer;
import net.minecraft.core.BlockPos;
import net.minecraft.resources.ResourceLocation;

public class FirepowerPourerRenderer extends MobRenderer<FirepowerPourerEntity, Modelfirepower_pourer<FirepowerPourerEntity>> {
	private static final ResourceLocation LOCATION = new ResourceLocation(JerotesVillage.MODID, "textures/entity/firepower_pourer.png");
	private static final ResourceLocation BREAK_1_LOCATION = new ResourceLocation(JerotesVillage.MODID, "textures/entity/firepower_pourer_break_1.png");
	private static final ResourceLocation BREAK_2_LOCATION = new ResourceLocation(JerotesVillage.MODID, "textures/entity/firepower_pourer_break_2.png");
	private static final ResourceLocation BREAK_3_LOCATION = new ResourceLocation(JerotesVillage.MODID, "textures/entity/firepower_pourer_break_3.png");
	private static final ResourceLocation HELL_MARCHER_LOCATION = new ResourceLocation(JerotesVillage.MODID, "textures/entity/firepower_pourer_hell_marcher.png");
	public FirepowerPourerRenderer(EntityRendererProvider.Context context) {
		super(context, new Modelfirepower_pourer(context.bakeLayer(Modelfirepower_pourer.LAYER_LOCATION)), 1.2f);
		this.addLayer(new FirepowerPourerChainsawLayer(this, new Modelfirepower_pourer(context.bakeLayer(Modelfirepower_pourer.LAYER_LOCATION))));
		this.addLayer(new FirepowerPourerGunLayer(this, new Modelfirepower_pourer(context.bakeLayer(Modelfirepower_pourer.LAYER_LOCATION))));
		this.addLayer(new CustomHeadLayer<FirepowerPourerEntity, Modelfirepower_pourer<FirepowerPourerEntity>>(this, context.getModelSet(), context.getItemInHandRenderer()));
	}

	@Override
	protected int getBlockLightLevel(FirepowerPourerEntity entity, BlockPos blockPos) {
		String string = ChatFormatting.stripFormatting(entity.getName().getString());
		if ("Hell Marcher".equals(string) || "HellMarcher".equals(string) || "Hellmarcher".equals(string) || "地狱行军者".equals(string)) {
			return 15;
		}
		return 9;
	}

	@Override
	protected float getFlipDegrees(FirepowerPourerEntity entity) {
		return 0.0f;
	}

	@Override
	public ResourceLocation getTextureLocation(FirepowerPourerEntity entity) {
		String string = ChatFormatting.stripFormatting(entity.getName().getString());
		if ("Hell Marcher".equals(string) || "HellMarcher".equals(string) || "Hellmarcher".equals(string) || "地狱行军者".equals(string)) {
			return HELL_MARCHER_LOCATION;
		}
		if (entity.getHealth() <= entity.getMaxHealth() / 4)
			return BREAK_3_LOCATION;
		if (entity.getHealth() <= entity.getMaxHealth() * 3 / 4)
			return BREAK_2_LOCATION;
		if (entity.getHealth() <= entity.getMaxHealth() / 2)
			return BREAK_1_LOCATION;
		return LOCATION;
	}
}

