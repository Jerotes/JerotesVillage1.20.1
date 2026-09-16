package com.jerotes.jerotesvillage.client.layer;

import com.jerotes.jerotesvillage.JerotesVillage;
import com.jerotes.jerotesvillage.config.OtherMainConfig;
import com.jerotes.jerotesvillage.entity.Boss.Biome.OldSecondRounderGolemEntity;
import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import net.minecraft.client.Minecraft;
import net.minecraft.client.model.EntityModel;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.entity.LivingEntityRenderer;
import net.minecraft.client.renderer.entity.RenderLayerParent;
import net.minecraft.client.renderer.entity.layers.RenderLayer;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.Entity;

public class SecondRounderGolemBreathLayer<T extends OldSecondRounderGolemEntity, M extends EntityModel<T>> extends RenderLayer<T, M> {
    private static final ResourceLocation BREATH_NONE = new ResourceLocation(JerotesVillage.MODID, "textures/entity/second_rounder_golem_breath_0.png");
    private static final ResourceLocation BREATH_LOW = new ResourceLocation(JerotesVillage.MODID, "textures/entity/second_rounder_golem_breath_1.png");
    private static final ResourceLocation BREATH_MEDIUM = new ResourceLocation(JerotesVillage.MODID, "textures/entity/second_rounder_golem_breath_2.png");
    private static final ResourceLocation BREATH_HIGH = new ResourceLocation(JerotesVillage.MODID, "textures/entity/second_rounder_golem_breath_3.png");
    private final M model;

    public SecondRounderGolemBreathLayer(RenderLayerParent<T, M> renderLayerParent, M m) {
        super(renderLayerParent);
        this.model = m;
    }

    @Override
    public void render(PoseStack poseStack, MultiBufferSource multiBufferSource, int n, T t, float f, float f2, float f3, float f4, float f5, float f6) {
        boolean bl;
        Minecraft minecraft = Minecraft.getInstance();
        boolean bl2 = bl = minecraft.shouldEntityAppearGlowing((Entity)t) && ((Entity)t).isInvisible();
        if (((Entity)t).isInvisible() && !bl) {
            return;
        }
        if (t.getBreathTick() <= 20 * OtherMainConfig.SecondRounderGolemBreathCooldown * 0.6){
            this.getParentModel().copyPropertiesTo(this.model);
            this.model.prepareMobModel(t, f, f2, f3);
            this.model.setupAnim(t, f, f2, f4, f5, f6);
            VertexConsumer vertexConsumer = multiBufferSource.getBuffer(RenderType.entityCutoutNoCull(BREATH_NONE));
            this.model.renderToBuffer(poseStack, vertexConsumer, n, LivingEntityRenderer.getOverlayCoords(t, 0.0f), 1.0f, 1.0f, 1.0f, 1.0f);
        }
        else if (t.getBreathTick() <= 20 * OtherMainConfig.SecondRounderGolemBreathCooldown * 0.8){
            this.getParentModel().copyPropertiesTo(this.model);
            this.model.prepareMobModel(t, f, f2, f3);
            this.model.setupAnim(t, f, f2, f4, f5, f6);
            VertexConsumer vertexConsumer = multiBufferSource.getBuffer(RenderType.entityCutoutNoCull(BREATH_LOW));
            this.model.renderToBuffer(poseStack, vertexConsumer, n, LivingEntityRenderer.getOverlayCoords(t, 0.0f), 1.0f, 1.0f, 1.0f, 1.0f);
        }
        else if ( t.getBreathTick() <= 20 * OtherMainConfig.SecondRounderGolemBreathCooldown * 0.95){
            this.getParentModel().copyPropertiesTo(this.model);
            this.model.prepareMobModel(t, f, f2, f3);
            this.model.setupAnim(t, f, f2, f4, f5, f6);
            VertexConsumer vertexConsumer = multiBufferSource.getBuffer(RenderType.entityCutoutNoCull(BREATH_MEDIUM));
            this.model.renderToBuffer(poseStack, vertexConsumer, n, LivingEntityRenderer.getOverlayCoords(t, 0.0f), 1.0f, 1.0f, 1.0f, 1.0f);
        }
        else if (t.getBreathTick() > 20 * OtherMainConfig.SecondRounderGolemBreathCooldown * 0.95){
            this.getParentModel().copyPropertiesTo(this.model);
            this.model.prepareMobModel(t, f, f2, f3);
            this.model.setupAnim(t, f, f2, f4, f5, f6);
            VertexConsumer vertexConsumer = multiBufferSource.getBuffer(RenderType.entityCutoutNoCull(BREATH_HIGH));
            this.model.renderToBuffer(poseStack, vertexConsumer, n, LivingEntityRenderer.getOverlayCoords(t, 0.0f), 1.0f, 1.0f, 1.0f, 1.0f);
        }
    }
}

