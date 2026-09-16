package com.jerotes.jerotesvillage.client.layer;

import com.jerotes.jerotesvillage.JerotesVillage;
import com.jerotes.jerotesvillage.client.model.Modelhumanoid;
import com.jerotes.jerotesvillage.entity.Humanoid.CarvedVillagerEntity;
import com.mojang.blaze3d.vertex.PoseStack;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.entity.RenderLayerParent;
import net.minecraft.client.renderer.entity.layers.EyesLayer;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.LivingEntity;

public class CarvedEnderEyesLayer<T extends LivingEntity> extends EyesLayer<T, Modelhumanoid<T>> {
    private static final RenderType ENDERMAN_EYES = RenderType.eyes(new ResourceLocation(JerotesVillage.MODID, "textures/entity/carved_villager_enderman_eye.png"));

    public CarvedEnderEyesLayer(RenderLayerParent<T, Modelhumanoid<T>> renderLayerParent) {
        super(renderLayerParent);
    }

    @Override
    public void render(PoseStack poseStack, MultiBufferSource multiBufferSource, int n, T villager, float f, float f2, float f3, float f4, float f5, float f6) {
        if (!(villager instanceof CarvedVillagerEntity carvedVillager && carvedVillager.isEndermanRace())) {
            return;
        }
        super.render(poseStack, multiBufferSource, n, villager, f, f2, f3, f4, f5, f6);
    }
    @Override
    public RenderType renderType() {
        return ENDERMAN_EYES;
    }
}

