package com.jerotes.jerotesvillage.client.layer;

import com.jerotes.jerotesvillage.JerotesVillage;
import com.jerotes.jerotesvillage.client.model.Modelwoodland_heart_guardian;
import com.jerotes.jerotesvillage.entity.Neutral.WildernessGiantSkeleton.WoodlandHeartGuardianEntity;
import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.entity.LivingEntityRenderer;
import net.minecraft.client.renderer.entity.RenderLayerParent;
import net.minecraft.client.renderer.entity.layers.RenderLayer;
import net.minecraft.resources.ResourceLocation;

public class WoodlandHeartGuardianSweepLayer<T extends WoodlandHeartGuardianEntity, M extends Modelwoodland_heart_guardian<T>> extends RenderLayer<T, M> {
    private final Modelwoodland_heart_guardian<T> model;

    public WoodlandHeartGuardianSweepLayer(RenderLayerParent<T, M> renderLayerParent, M m) {
        super(renderLayerParent);
        this.model = m;
    }

    @Override
    public void render(PoseStack poseStack, MultiBufferSource multiBufferSource, int n, T t, float f, float f2, float f3, float f4, float f5, float f6) {
        Minecraft minecraft = Minecraft.getInstance();
        boolean bl = minecraft.shouldEntityAppearGlowing(t) && t.isInvisible();
        if (t.isInvisible() && !bl) {
            return;
        }
        if (t.getAttackTick() <= 0) {
            return;
        }
        this.getParentModel().copyPropertiesTo(this.model);
        this.model.prepareMobModel(t, f, f2, f3);
        this.model.setupAnim(t, f, f2, f4, f5, f6);
        int minTick = 9;
        int maxTick = 16;
        if (t.getAttackUse() == 2) {
            minTick = 10;
            maxTick = 17;
        }
        if (t.getAttackUse() == 3) {
            minTick = 11;
            maxTick = 18;
        }
        if (t.getAttackUse() == 5) {
            minTick = 16;
            maxTick = 23;
        }
        int ticks = 7 + (minTick - t.getAttackTick());
        if (t.getAttackTick() <= maxTick && t.getAttackTick() >= minTick) {
            VertexConsumer vertexConsumerSlash = multiBufferSource.getBuffer(RenderType.energySwirl(
                    new ResourceLocation(JerotesVillage.MODID, "textures/entity/woodland_heart_guardian/woodland_heart_guardian_sweep_" + ticks + ".png"),0,0));
            this.model.renderToBuffer(poseStack, vertexConsumerSlash, n, LivingEntityRenderer.getOverlayCoords(t, 0.0f), 1.0f, 1.0f, 1.0f, 1.0f);
        }
        int minTickAdd = 0;
        int maxTickAdd = 0;
        if (t.getAttackUse() == 3) {
            minTickAdd = 10;
            maxTickAdd = 17;
        }
        if (t.getAttackUse() == 4) {
            minTickAdd = 9;
            maxTickAdd = 16;
        }
        if (t.getAttackUse() == 5) {
            minTickAdd = 6;
            maxTickAdd = 13;
        }
        int ticksAdd = 7 + (minTickAdd - t.getAttackTick());
        if (t.getAttackTick() <= maxTickAdd && t.getAttackTick() >= minTickAdd) {
           VertexConsumer vertexConsumerSlashAdd = multiBufferSource.getBuffer(RenderType.energySwirl(
                    new ResourceLocation(JerotesVillage.MODID, "textures/entity/woodland_heart_guardian/woodland_heart_guardian_add_sweep_" + ticksAdd + ".png"),0,0));
            this.model.renderToBuffer(poseStack, vertexConsumerSlashAdd, n, LivingEntityRenderer.getOverlayCoords(t, 0.0f), 1.0f, 1.0f, 1.0f, 1.0f);
        }
    }
}