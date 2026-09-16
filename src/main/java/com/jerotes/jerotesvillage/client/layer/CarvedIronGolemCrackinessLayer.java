package com.jerotes.jerotesvillage.client.layer;

import com.google.common.collect.ImmutableMap;
import com.jerotes.jerotesvillage.client.model.Modelcarved_iron_golem;
import com.jerotes.jerotesvillage.entity.Neutral.CarvedIronGolemEntity;
import com.mojang.blaze3d.vertex.PoseStack;
import net.minecraft.client.model.IronGolemModel;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.entity.RenderLayerParent;
import net.minecraft.client.renderer.entity.layers.RenderLayer;
import net.minecraft.resources.ResourceLocation;

public class CarvedIronGolemCrackinessLayer extends RenderLayer<CarvedIronGolemEntity, Modelcarved_iron_golem<CarvedIronGolemEntity>> {
    private static final ImmutableMap<Object, Object> resourceLocations = ImmutableMap.of((Object)((Object)CarvedIronGolemEntity.Crackiness.LOW), (Object)new ResourceLocation("textures/entity/iron_golem/iron_golem_crackiness_low.png"), (Object)((Object)CarvedIronGolemEntity.Crackiness.MEDIUM), (Object)new ResourceLocation("textures/entity/iron_golem/iron_golem_crackiness_medium.png"), (Object)((Object)CarvedIronGolemEntity.Crackiness.HIGH), (Object)new ResourceLocation("textures/entity/iron_golem/iron_golem_crackiness_high.png"));

    public CarvedIronGolemCrackinessLayer(RenderLayerParent<CarvedIronGolemEntity, Modelcarved_iron_golem<CarvedIronGolemEntity>> renderLayerParent) {
        super(renderLayerParent);
    }

    @Override
    public void render(PoseStack poseStack, MultiBufferSource multiBufferSource, int n, CarvedIronGolemEntity ironGolem, float f, float f2, float f3, float f4, float f5, float f6) {
        if (ironGolem.isInvisible()) {
            return;
        }
        CarvedIronGolemEntity.Crackiness crackiness = ironGolem.getCrackiness();
        if (crackiness == CarvedIronGolemEntity.Crackiness.NONE) {
            return;
        }
        Object resourceLocation = resourceLocations.get((Object)crackiness);
        CarvedIronGolemCrackinessLayer.renderColoredCutoutModel(this.getParentModel(), (ResourceLocation) resourceLocation, poseStack, multiBufferSource, n, ironGolem, 1.0f, 1.0f, 1.0f);
    }
}

