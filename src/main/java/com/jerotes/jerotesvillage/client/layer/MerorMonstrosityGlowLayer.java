package com.jerotes.jerotesvillage.client.layer;

import com.jerotes.jerotesvillage.JerotesVillage;
import com.jerotes.jerotesvillage.config.OtherMainConfig;
import com.jerotes.jerotesvillage.entity.Neutral.MerorMachine.MerorMonstrosityEntity;
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

public class MerorMonstrosityGlowLayer<T extends MerorMonstrosityEntity, M extends EntityModel<T>> extends RenderLayer<T, M> {
    private final EntityModel<T> model;
    private static final ResourceLocation GLOW_1_LOCATION = new ResourceLocation(JerotesVillage.MODID, "textures/entity/meror_machine/meror_monstrosity_glow_1.png");
    private static final ResourceLocation GLOW_2_LOCATION = new ResourceLocation(JerotesVillage.MODID, "textures/entity/meror_machine/meror_monstrosity_glow_2.png");
    private static final ResourceLocation REDSTONE_GLOW_1_LOCATION = new ResourceLocation(JerotesVillage.MODID, "textures/entity/meror_machine/meror_monstrosity_redstone_monstrosity_glow_1.png");
    private static final ResourceLocation REDSTONE_GLOW_2_LOCATION = new ResourceLocation(JerotesVillage.MODID, "textures/entity/meror_machine/meror_monstrosity_redstone_monstrosity_glow_2.png");
    private static final ResourceLocation CLASSIC_GLOW_1_LOCATION = new ResourceLocation(JerotesVillage.MODID, "textures/entity/meror_machine/meror_monstrosity_old_glow_1.png");
    private static final ResourceLocation CLASSIC_GLOW_2_LOCATION = new ResourceLocation(JerotesVillage.MODID, "textures/entity/meror_machine/meror_monstrosity_old_glow_2.png");

    public MerorMonstrosityGlowLayer(RenderLayerParent<T, M> renderLayerParent, M m) {
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
        if (!t.isTame() && t.getStandTick() <= 10) {
            return;
        }
        ResourceLocation location1 = GLOW_1_LOCATION;
        ResourceLocation location2 = GLOW_2_LOCATION;
        String string = ChatFormatting.stripFormatting(t.getName().getString());
        if ("Redstone Monstrosity".equals(string) || "红石巨兽".equals(string)) {
            location1 = REDSTONE_GLOW_1_LOCATION;
            location2 = REDSTONE_GLOW_2_LOCATION;
        }
        else if ("Classic".equals(string) || "Old".equals(string) || "classic".equals(string) || "old".equals(string)) {
            location1 = CLASSIC_GLOW_1_LOCATION;
            location2 = CLASSIC_GLOW_2_LOCATION;
        }

        if (OtherMainConfig.MerorMonstrositySpecialGlow){
            float levels;
            int ticks = t.tickCount % 80;
            if (ticks < 40) {
                levels = (ticks + 1) / 40f;
            } else {
                levels = ((80 - (ticks + 1)) / 40f);
            }
            float level = (levels / 2) + 0.5f;

            ResourceLocation location3 = location2;;
            boolean send = t.getSendUseTick() > 12 && t.getSendUseTick() <= 70;
            boolean shoot = t.getShootUseTick() > 8 && t.getShootUseTick() <= 25;
            if (!t.isAlive() || !send && !shoot) {
                location3 = location1;
            }
            else {
                level = getLevel(t, send);
            }
            this.getParentModel().copyPropertiesTo(this.model);
            this.model.prepareMobModel(t, f, f2, f3);
            this.model.setupAnim(t, f, f2, f4, f5, f6);
            VertexConsumer vertexConsumer = multiBufferSource.getBuffer(RenderType.eyes(location3));
            this.model.renderToBuffer(poseStack, vertexConsumer, n, LivingEntityRenderer.getOverlayCoords(t, 0.0f), 1.0f, 1.0f, 1.0f, level);
            VertexConsumer vertexConsumer2 = multiBufferSource.getBuffer(RenderType.entityTranslucent(location1));
            this.model.renderToBuffer(poseStack, vertexConsumer2, n, LivingEntityRenderer.getOverlayCoords(t, 0.0f), 1.0f, 1.0f, 1.0f, level - 0.5f);
        }
    }

    private static <T extends MerorMonstrosityEntity> float getLevel(T t, boolean send) {
        float level = 1;
        if (send) {
            if (t.getSendUseTick() <= 17 && t.getSendUseTick() > 12) {
                level = 1 - (17 - t.getSendUseTick()) / 5f;
            }
            else if (t.getSendUseTick() <= 70 && t.getSendUseTick() > 55) {
                level = (70 - t.getSendUseTick()) / 15f;
            }
        }
        else {
            if (t.getShootUseTick() <= 15 && t.getShootUseTick() > 8) {
                level = 1 - (15 - t.getShootUseTick()) / 7f;
            }
            else if (t.getShootUseTick() <= 25 && t.getShootUseTick() > 17) {
                level = (25 - t.getShootUseTick()) / 8f;
            }
        }
        return -(level / 2f) + 1f;
    }
}

