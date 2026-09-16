package com.jerotes.jerotesvillage.client.renderer;

import com.jerotes.jerotesvillage.JerotesVillage;
import com.jerotes.jerotesvillage.entity.Animal.PurpleSandRabbitEntity;
import com.mojang.blaze3d.vertex.PoseStack;
import net.minecraft.client.model.RabbitModel;
import net.minecraft.client.model.geom.ModelLayers;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.entity.MobRenderer;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.animal.Rabbit;

public class PurpleSandRabbitRenderer extends MobRenderer<PurpleSandRabbitEntity, RabbitModel<PurpleSandRabbitEntity>> {
	private static final ResourceLocation LOCATION = new ResourceLocation(JerotesVillage.MODID, "textures/entity/purple_sand_rabbit.png");
	private static final ResourceLocation KILLER_LOCATION = new ResourceLocation(JerotesVillage.MODID, "textures/entity/purple_sand_rabbit_killer.png");
	public PurpleSandRabbitRenderer(EntityRendererProvider.Context context) {
		super(context, new RabbitModel(context.bakeLayer(ModelLayers.RABBIT)), 0.45f);
	}

	@Override
	protected void scale(PurpleSandRabbitEntity entity, PoseStack poseStack, float f) {
		poseStack.scale(1.5f, 1.5f, 1.5f);
		if (entity.getVariant() == Rabbit.Variant.EVIL) {
			poseStack.scale(2f, 2f, 2f);
		}
		super.scale(entity, poseStack, f);
	}

	@Override
	public void render(PurpleSandRabbitEntity entity, float f, float f2, PoseStack poseStack, MultiBufferSource multiBufferSource, int n) {
		if (entity.getVariant() == Rabbit.Variant.EVIL) {
			this.shadowRadius = 0.9f;
		}
		else {
			this.shadowRadius = 0.45f;
		}
		super.render(entity, f, f2, poseStack, multiBufferSource, n);
	}

	@Override
	public ResourceLocation getTextureLocation(PurpleSandRabbitEntity entity) {
		if (entity.getVariant() == Rabbit.Variant.EVIL)
			return KILLER_LOCATION;
		return LOCATION;
	}

}

