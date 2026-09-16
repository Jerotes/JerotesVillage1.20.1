package com.jerotes.jerotesvillage.client.layer;

import com.jerotes.jerotesvillage.JerotesVillage;
import com.jerotes.jerotesvillage.entity.Neutral.MerorMachine.MerorCubeEntity;
import com.jerotes.jerotesvillage.init.JerotesVillageRenderType;
import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import net.minecraft.ChatFormatting;
import net.minecraft.client.Minecraft;
import net.minecraft.client.model.EntityModel;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.entity.LivingEntityRenderer;
import net.minecraft.client.renderer.entity.RenderLayerParent;
import net.minecraft.client.renderer.entity.layers.RenderLayer;
import net.minecraft.resources.ResourceLocation;

public class MerorCubeGlowLayer<T extends MerorCubeEntity, M extends EntityModel<T>> extends RenderLayer<T, M> {
    private final EntityModel<T> model;
    private static final ResourceLocation GLOW_LOCATION = new ResourceLocation(JerotesVillage.MODID, "textures/entity/meror_machine/meror_cube_glow.png");
    private static final ResourceLocation REDSTONE_GLOW_LOCATION = new ResourceLocation(JerotesVillage.MODID, "textures/entity/meror_machine/meror_cube_redstone_cube_glow.png");

    public MerorCubeGlowLayer(RenderLayerParent<T, M> renderLayerParent, M m) {
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
        if (!t.isAlive() && t.deathTime >= 15) {
            return;
        }
        if (t.isInSittingPose()) {
            return;
        }
        ResourceLocation location = GLOW_LOCATION;
        String string = ChatFormatting.stripFormatting(t.getName().getString());
        if ("Redstone Cube".equals(string) || "红石怪".equals(string) ) {
            location = REDSTONE_GLOW_LOCATION;
        }

        float levels;
        int ticks = t.tickCount % 40;
        if (ticks < 20) {
            levels = (ticks+1)/20f;
        }
        else {
            levels = ((40-(ticks+1))/20f);
        }
        float level = (levels / 2) + 0.5f;
        this.getParentModel().copyPropertiesTo(this.model);
        this.model.prepareMobModel(t, f, f2, f3);
        this.model.setupAnim(t, f, f2, f4, f5, f6);
        VertexConsumer vertexConsumer = multiBufferSource.getBuffer(RenderType.eyes(location));
        this.model.renderToBuffer(poseStack, vertexConsumer, n, LivingEntityRenderer.getOverlayCoords(t, 0.0f), 1.0f, 1.0f, 1.0f, level);
        VertexConsumer vertexConsumer2 = multiBufferSource.getBuffer(RenderType.entityTranslucent(location));
        this.model.renderToBuffer(poseStack, vertexConsumer2, n, LivingEntityRenderer.getOverlayCoords(t, 0.0f), 1.0f, 1.0f, 1.0f, level - 0.5f);
    }
}

