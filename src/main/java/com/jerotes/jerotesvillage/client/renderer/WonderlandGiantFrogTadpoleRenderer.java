package com.jerotes.jerotesvillage.client.renderer;

import com.jerotes.jerotes.client.layer.TameLayer;
import com.jerotes.jerotesvillage.JerotesVillage;
import com.jerotes.jerotesvillage.client.model.Modelwonderland_giant_frog_tadpole;
import com.jerotes.jerotesvillage.entity.Animal.WonderlandGiantFrogTadpoleEntity;
import com.mojang.blaze3d.vertex.PoseStack;
import net.minecraft.client.model.geom.ModelLayers;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.entity.MobRenderer;
import net.minecraft.resources.ResourceLocation;

public class WonderlandGiantFrogTadpoleRenderer extends MobRenderer<WonderlandGiantFrogTadpoleEntity, Modelwonderland_giant_frog_tadpole<WonderlandGiantFrogTadpoleEntity>> {
	private static final ResourceLocation LOCATION = new ResourceLocation(JerotesVillage.MODID, "textures/entity/wonderland_giant_frog/wonderland_fiant_frog_tadpole.png");
	private static final ResourceLocation TAME_LOCATION = new ResourceLocation(JerotesVillage.MODID, "textures/entity/wonderland_giant_frog/wonderland_fiant_frog_tadpole_tame.png");
	public WonderlandGiantFrogTadpoleRenderer(EntityRendererProvider.Context context) {
		super(context, new Modelwonderland_giant_frog_tadpole(context.bakeLayer(Modelwonderland_giant_frog_tadpole.LAYER_LOCATION)), 0.5f);
		this.addLayer(new TameLayer(this, new Modelwonderland_giant_frog_tadpole(context.bakeLayer(ModelLayers.TADPOLE)), TAME_LOCATION));
	}

	@Override
	protected void scale(WonderlandGiantFrogTadpoleEntity entity, PoseStack poseStack, float f) {
		poseStack.scale(3f, 3f, 3f);
		super.scale(entity, poseStack, f);
	}

	@Override
	public ResourceLocation getTextureLocation(WonderlandGiantFrogTadpoleEntity entity) {
		return LOCATION;
	}
}

