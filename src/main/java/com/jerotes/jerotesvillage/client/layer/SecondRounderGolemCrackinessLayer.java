package com.jerotes.jerotesvillage.client.layer;

import com.jerotes.jerotesvillage.JerotesVillage;
import com.google.common.collect.ImmutableMap;
import com.jerotes.jerotesvillage.client.model.Modelsecond_rounder_golem;
import com.jerotes.jerotesvillage.entity.Boss.Biome.OldSecondRounderGolemEntity;
import com.mojang.blaze3d.vertex.PoseStack;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.entity.RenderLayerParent;
import net.minecraft.client.renderer.entity.layers.RenderLayer;
import net.minecraft.resources.ResourceLocation;

public class SecondRounderGolemCrackinessLayer extends RenderLayer<OldSecondRounderGolemEntity, Modelsecond_rounder_golem<OldSecondRounderGolemEntity>> {
    private static final ImmutableMap<Object, Object> resourceLocations = ImmutableMap.of((Object)((Object) OldSecondRounderGolemEntity.Crackiness.LOW), (Object)new ResourceLocation(JerotesVillage.MODID, "textures/entity/second_rounder_golem_crack_1.png"), (Object)((Object) OldSecondRounderGolemEntity.Crackiness.MEDIUM), (Object)new ResourceLocation(JerotesVillage.MODID, "textures/entity/second_rounder_golem_crack_2.png"), (Object)((Object) OldSecondRounderGolemEntity.Crackiness.HIGH), (Object)new ResourceLocation(JerotesVillage.MODID, "textures/entity/second_rounder_golem_crack_3.png"));

    public SecondRounderGolemCrackinessLayer(RenderLayerParent<OldSecondRounderGolemEntity, Modelsecond_rounder_golem<OldSecondRounderGolemEntity>> renderLayerParent) {
        super(renderLayerParent);
    }

    @Override
    public void render(PoseStack poseStack, MultiBufferSource multiBufferSource, int n, OldSecondRounderGolemEntity golem, float f, float f2, float f3, float f4, float f5, float f6) {
        if (golem.isInvisible()) {
            return;
        }
        OldSecondRounderGolemEntity.Crackiness crackiness = golem.getCrackiness();
        if (crackiness == OldSecondRounderGolemEntity.Crackiness.NONE) {
            return;
        }
        Object resourceLocation = resourceLocations.get((Object)crackiness);
        SecondRounderGolemCrackinessLayer.renderColoredCutoutModel(this.getParentModel(), (ResourceLocation) resourceLocation, poseStack, multiBufferSource, n, golem, 1.0f, 1.0f, 1.0f);
    }
}

