package com.jerotes.jerotesvillage.client.renderer;

import com.jerotes.jerotesvillage.JerotesVillage;
import com.jerotes.jerotesvillage.client.layer.SpearMachineItemInHandLayer;
import com.jerotes.jerotesvillage.client.model.Modelspear_machine;
import com.jerotes.jerotesvillage.entity.Neutral.SpearMachineEntity;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.entity.MobRenderer;
import net.minecraft.resources.ResourceLocation;

public class SpearMachineRenderer extends MobRenderer<SpearMachineEntity, Modelspear_machine<SpearMachineEntity>> {
	private static final ResourceLocation LOCATION = new ResourceLocation(JerotesVillage.MODID, "textures/entity/spear_machine.png");
	private static final ResourceLocation TAME_LOCATION = new ResourceLocation(JerotesVillage.MODID, "textures/entity/spear_machine_tame.png");
	public SpearMachineRenderer(EntityRendererProvider.Context context) {
		super(context, new Modelspear_machine(context.bakeLayer(Modelspear_machine.LAYER_LOCATION)), 0.5f);
		this.addLayer(new SpearMachineItemInHandLayer(this, context.getItemInHandRenderer()));
	}

	@Override
	protected float getFlipDegrees(SpearMachineEntity t) {
		return 0.0f;
	}

	@Override
	public ResourceLocation getTextureLocation(SpearMachineEntity entity) {
		if (entity.isTame()) {
			return TAME_LOCATION;
		}
		return LOCATION;
	}
}