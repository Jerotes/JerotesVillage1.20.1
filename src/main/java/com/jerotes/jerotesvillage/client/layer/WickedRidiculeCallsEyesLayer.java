package com.jerotes.jerotesvillage.client.layer;

import com.jerotes.jerotesvillage.init.JerotesVillageMobEffects;
import com.mojang.blaze3d.vertex.PoseStack;
import net.minecraft.client.Minecraft;
import net.minecraft.client.model.EntityModel;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.entity.RenderLayerParent;
import net.minecraft.client.renderer.entity.layers.EyesLayer;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

@OnlyIn(Dist.CLIENT)
public class WickedRidiculeCallsEyesLayer<T extends LivingEntity, M extends EntityModel<T>> extends EyesLayer<T, M> {
    private final ResourceLocation textureLocation;

    public WickedRidiculeCallsEyesLayer(RenderLayerParent<T, M> renderLayerParent, ResourceLocation resourceLocation) {
        super(renderLayerParent);
        this.textureLocation = resourceLocation;
    }
    @Override
    public void render(PoseStack poseStack, MultiBufferSource multiBufferSource, int n, T t, float f, float f2, float f3, float f4, float f5, float f6) {
        Minecraft minecraft = Minecraft.getInstance();
        Player player = minecraft.player;
        if (player == null || !player.hasEffect(JerotesVillageMobEffects.WICKED_RIDICULE_NIGHT.get())) {
            return;
        }
        super.render(poseStack, multiBufferSource, n, t, f, f2, f3, f4, f5, f6);
    }

    public RenderType renderType() {
        return RenderType.eyes(textureLocation);
    }
}

