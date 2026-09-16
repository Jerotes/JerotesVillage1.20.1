package com.jerotes.jerotesvillage.client.layer;

import com.jerotes.jerotesvillage.client.model.Modelglow_sparrow;
import com.jerotes.jerotesvillage.init.JerotesVillageEntityType;
import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import net.minecraft.client.model.PlayerModel;
import net.minecraft.client.model.geom.EntityModelSet;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.entity.RenderLayerParent;
import net.minecraft.client.renderer.entity.layers.RenderLayer;
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.player.Player;
import com.jerotes.jerotesvillage.JerotesVillage;

public class GlowSparrowOnShoulderLayer<T extends Player> extends RenderLayer<T, PlayerModel<T>> {
    private final Modelglow_sparrow model;
    private static final ResourceLocation LOCATION = new ResourceLocation(JerotesVillage.MODID, "textures/entity/glow_sparrow.png");

    public GlowSparrowOnShoulderLayer(RenderLayerParent<T, PlayerModel<T>> renderLayerParent, EntityModelSet entityModelSet) {
        super(renderLayerParent);
        this.model = new Modelglow_sparrow(entityModelSet.bakeLayer(Modelglow_sparrow.LAYER_LOCATION));
    }

    @Override
    public void render(PoseStack poseStack, MultiBufferSource multiBufferSource, int n, T t, float f, float f2, float f3, float f4, float f5, float f6) {
        this.render(poseStack, multiBufferSource, n, t, f, f2, f5, f6, true);
        this.render(poseStack, multiBufferSource, n, t, f, f2, f5, f6, false);
    }

    private void render(PoseStack poseStack, MultiBufferSource multiBufferSource, int n, T t, float f, float f2, float f3, float f4, boolean bl) {
        CompoundTag compoundTag = bl ? t.getShoulderEntityLeft() : t.getShoulderEntityRight();
        EntityType.byString(compoundTag.getString("id")).filter(entityType -> entityType == JerotesVillageEntityType.GLOW_SPARROW.get()).ifPresent(entityType -> {
            poseStack.pushPose();
            poseStack.translate(bl ? 0.4f : -0.4f, t.isCrouching() ? -1.3F : -1.5F, 0.0f);
            VertexConsumer vertexConsumer = multiBufferSource.getBuffer(this.model.renderType(LOCATION));
            this.model.renderOnShoulder(poseStack, vertexConsumer, n, OverlayTexture.NO_OVERLAY, f, f2, f3, f4, t.tickCount);
            poseStack.popPose();
        });
    }


}

