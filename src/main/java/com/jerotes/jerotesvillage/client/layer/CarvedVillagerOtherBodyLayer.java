package com.jerotes.jerotesvillage.client.layer;

import com.jerotes.jerotesvillage.JerotesVillage;
import com.jerotes.jerotesvillage.client.model.Modelcarved_villager_enderman_other;
import com.jerotes.jerotesvillage.client.model.Modelcarved_villager_other;
import com.jerotes.jerotesvillage.client.model.Modelcarved_villager_piglin_other;
import com.jerotes.jerotesvillage.config.OtherMainConfig;
import com.jerotes.jerotesvillage.entity.Humanoid.CarvedVillagerEntity;
import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import net.minecraft.ChatFormatting;
import net.minecraft.client.Minecraft;
import net.minecraft.client.model.EntityModel;
import net.minecraft.client.model.Model;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.entity.LivingEntityRenderer;
import net.minecraft.client.renderer.entity.RenderLayerParent;
import net.minecraft.client.renderer.entity.layers.RenderLayer;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;

import java.util.Locale;

public class CarvedVillagerOtherBodyLayer<T extends LivingEntity, M extends EntityModel<T>> extends RenderLayer<T, M> {
    private static final ResourceLocation VILLAGER_LOCATION = new ResourceLocation(JerotesVillage.MODID, "textures/entity/carved_villager.png");
    private static final ResourceLocation ILLAGER_LOCATION = new ResourceLocation(JerotesVillage.MODID, "textures/entity/carved_villager_illager.png");
    private static final ResourceLocation PIGLIN_LOCATION = new ResourceLocation(JerotesVillage.MODID, "textures/entity/carved_villager_piglin_head.png");
    private static final ResourceLocation ENDERMAN_LOCATION = new ResourceLocation(JerotesVillage.MODID, "textures/entity/carved_villager_enderman_other.png");
    private final EntityRendererProvider.Context context;

    public CarvedVillagerOtherBodyLayer(RenderLayerParent<T, M> renderLayerParent, EntityRendererProvider.Context context) {
        super(renderLayerParent);
        this.context = context;
    }

    public static String sanitizeString(String input) {
        if (input == null || input.isEmpty()) {
            return input;
        }
        String lowerCase = input.toLowerCase(Locale.ROOT);
        String sanitized = lowerCase.replaceAll("[^a-z0-9_./-]", "_");
        sanitized = sanitized.replaceAll(" ", "_");
        return sanitized;
    }

    @Override
    public void render(PoseStack poseStack, MultiBufferSource multiBufferSource, int n, T t, float f, float f2, float f3, float f4, float f5, float f6) {
        boolean bl;
        Minecraft minecraft = Minecraft.getInstance();
        boolean bl2 = bl = minecraft.shouldEntityAppearGlowing((Entity)t) && ((Entity)t).isInvisible();
        if (((Entity)t).isInvisible() && !bl) {
            return;
        }
        if (!(t instanceof CarvedVillagerEntity carvedVillager)) {
            return;
        }
        if (carvedVillager.isSkin()) {
            return;
        }
        String string = ChatFormatting.stripFormatting(carvedVillager.getName().getString());
        if (carvedVillager.getCustomName() != null) {
            if (string != null && !string.isEmpty() && OtherMainConfig.CarvedVillagerCustomNameWide.contains(string)) {
                return;
            }
            if (string != null && !string.isEmpty() && OtherMainConfig.CarvedVillagerCustomNameSlim.contains(string)) {
                return;
            }
        }
        if (carvedVillager.isEndermanRace()) {
            if (!carvedVillager.isBaby()) {
                EntityModel<T> model = new Modelcarved_villager_enderman_other(this.context.bakeLayer(Modelcarved_villager_enderman_other.LAYER_LOCATION));
                this.getParentModel().copyPropertiesTo(model);
                model.prepareMobModel(t, f, f2, f3);
                model.setupAnim(t, f, f2, f4, f5, f6);
                VertexConsumer vertexConsumer = multiBufferSource.getBuffer(RenderType.entityCutoutNoCull(ENDERMAN_LOCATION));
                ((Model) model).renderToBuffer(poseStack, vertexConsumer, n, LivingEntityRenderer.getOverlayCoords(t, 0.0f), 1.0f, 1.0f, 1.0f, 1.0f);
            }
        }
        else if (carvedVillager.isIllagerRace()) {
            EntityModel<T> model = new Modelcarved_villager_other(this.context.bakeLayer(Modelcarved_villager_other.LAYER_LOCATION));
            this.getParentModel().copyPropertiesTo(model);
            model.prepareMobModel(t, f, f2, f3);
            model.setupAnim(t, f, f2, f4, f5, f6);
            VertexConsumer vertexConsumer = multiBufferSource.getBuffer(RenderType.entityCutoutNoCull(ILLAGER_LOCATION));
            ((Model)model).renderToBuffer(poseStack, vertexConsumer, n, LivingEntityRenderer.getOverlayCoords(t, 0.0f), 1.0f, 1.0f, 1.0f, 1.0f);
        }
        else if (carvedVillager.isPiglinRace()) {
            EntityModel<T> model = new Modelcarved_villager_piglin_other(this.context.bakeLayer(Modelcarved_villager_piglin_other.LAYER_LOCATION));
            this.getParentModel().copyPropertiesTo(model);
            model.prepareMobModel(t, f, f2, f3);
            model.setupAnim(t, f, f2, f4, f5, f6);
            VertexConsumer vertexConsumer = multiBufferSource.getBuffer(RenderType.entityCutoutNoCull(PIGLIN_LOCATION));
            ((Model)model).renderToBuffer(poseStack, vertexConsumer, n, LivingEntityRenderer.getOverlayCoords(t, 0.0f), 1.0f, 1.0f, 1.0f, 1.0f);
        }
        else {
            EntityModel<T> model = new Modelcarved_villager_other(this.context.bakeLayer(Modelcarved_villager_other.LAYER_LOCATION));
            this.getParentModel().copyPropertiesTo(model);
            model.prepareMobModel(t, f, f2, f3);
            model.setupAnim(t, f, f2, f4, f5, f6);
            VertexConsumer vertexConsumer = multiBufferSource.getBuffer(RenderType.entityCutoutNoCull(VILLAGER_LOCATION));
            ((Model)model).renderToBuffer(poseStack, vertexConsumer, n, LivingEntityRenderer.getOverlayCoords(t, 0.0f), 1.0f, 1.0f, 1.0f, 1.0f);
        }
    }
}

