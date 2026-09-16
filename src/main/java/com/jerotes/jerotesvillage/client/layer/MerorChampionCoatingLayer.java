package com.jerotes.jerotesvillage.client.layer;

import com.jerotes.jerotesvillage.JerotesVillage;
import com.jerotes.jerotesvillage.entity.Animal.BigWoolGemsbokEntity;
import com.jerotes.jerotesvillage.entity.Neutral.MerorMachine.MerorChampionEntity;
import com.jerotes.jerotesvillage.init.JerotesVillageItems;
import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import net.minecraft.client.Minecraft;
import net.minecraft.client.model.EntityModel;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.entity.LivingEntityRenderer;
import net.minecraft.client.renderer.entity.RenderLayerParent;
import net.minecraft.client.renderer.entity.layers.RenderLayer;
import net.minecraft.core.RegistryAccess;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.DyeColor;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.armortrim.TrimMaterial;

public class MerorChampionCoatingLayer<T extends MerorChampionEntity, M extends EntityModel<T>> extends RenderLayer<T, M> {
    private final EntityModel<T> model;
    private final ResourceLocation resourceLocation;
    private final ResourceLocation resourceLocation2;

    public MerorChampionCoatingLayer(RenderLayerParent<T, M> parent, M model, ResourceLocation resourceLocation,  ResourceLocation resourceLocation2) {
        super(parent);
        this.model = model;
        this.resourceLocation = resourceLocation;
        this.resourceLocation2 = resourceLocation2;
    }

    @Override
    public void render(PoseStack poseStack, MultiBufferSource multiBufferSource, int n, T entity, float f, float f2, float f3, float f4, float f5, float f6) {

        Minecraft mc = Minecraft.getInstance();
        if (entity.isInvisible() && !(mc.shouldEntityAppearGlowing(entity) && entity.isInvisible())) {
            return;
        }
        if (!entity.isPerfect()) {
            this.getParentModel().copyPropertiesTo(this.model);
            this.model.prepareMobModel(entity, f, f2, f3);
            this.model.setupAnim(entity, f, f2, f4, f5, f6);
            VertexConsumer vertexConsumer = multiBufferSource.getBuffer(RenderType.entityCutoutNoCull(new ResourceLocation(JerotesVillage.MODID, "textures/entity/meror_machine/meror_champion/meror_champion_break.png")));
            this.model.renderToBuffer(poseStack, vertexConsumer, n, LivingEntityRenderer.getOverlayCoords(entity, 0.0f), 1.0f, 1.0f, 1.0f, 1.0f);
        }

        ItemStack coating = entity.getCoating();
        if (entity.hasCustomName() && "jeb_".equals(entity.getName().getString())) {
            int n2 = 25;
            int n3 = entity.tickCount / 25 + entity.getId();
            int n4 = DyeColor.values().length;
            int n5 = n3 % n4;
            int n6 = (n3 + 1) % n4;
            float f10 = ((float) (entity.tickCount % 25) + f3) / 25.0f;
            float[] arrf = BigWoolGemsbokEntity.getColorArray(DyeColor.byId(n5));
            float[] arrf2 = BigWoolGemsbokEntity.getColorArray(DyeColor.byId(n6));
            float r = arrf[0] * (1.0f - f10) + arrf2[0] * f10;
            float g = arrf[1] * (1.0f - f10) + arrf2[1] * f10;
            float b = arrf[2] * (1.0f - f10) + arrf2[2] * f10;
            coloredCutoutModelCopyLayerRender(this.getParentModel(), this.model,
                    resourceLocation, poseStack, multiBufferSource, n, entity, f, f2, f4, f5, f6, f3, r, g, b);
            coloredCutoutModelCopyLayerRender(this.getParentModel(), this.model,
                    resourceLocation2, poseStack, multiBufferSource, n, entity, f, f2, f4, f5, f6, f3, r, g, b);
        }
        else if (coating.is(Items.LEATHER)) {
            ResourceLocation resourceLocations = new ResourceLocation(JerotesVillage.MODID, "textures/entity/meror_machine/meror_champion/meror_champion_leather.png");
            coloredCutoutModelCopyLayerRender(this.getParentModel(), this.model,
                    resourceLocations, poseStack, multiBufferSource, n, entity, f, f2, f4, f5, f6, f3, 1.0f, 1.0f, 1.0f);
        }
        else if (coating.is(Items.BROWN_MUSHROOM)) {
            ResourceLocation resourceLocations = new ResourceLocation(JerotesVillage.MODID, "textures/entity/meror_machine/meror_champion/meror_champion_brown_mushroom.png");
            coloredCutoutModelCopyLayerRender(this.getParentModel(), this.model,
                    resourceLocations, poseStack, multiBufferSource, n, entity, f, f2, f4, f5, f6, f3, 1.0f, 1.0f, 1.0f);
        }
        else if (coating.is(Items.RED_MUSHROOM)) {
            ResourceLocation resourceLocations = new ResourceLocation(JerotesVillage.MODID, "textures/entity/meror_machine/meror_champion/meror_champion_red_mushroom.png");
            coloredCutoutModelCopyLayerRender(this.getParentModel(), this.model,
                    resourceLocations, poseStack, multiBufferSource, n, entity, f, f2, f4, f5, f6, f3, 1.0f, 1.0f, 1.0f);
        }
        else if (coating.is(Items.DANDELION) || coating.is(Items.SUNFLOWER)) {
            ResourceLocation resourceLocations = new ResourceLocation(JerotesVillage.MODID, "textures/entity/meror_machine/meror_champion/meror_champion_dandelion.png");
            coloredCutoutModelCopyLayerRender(this.getParentModel(), this.model,
                    resourceLocations, poseStack, multiBufferSource, n, entity, f, f2, f4, f5, f6, f3, 1.0f, 1.0f, 1.0f);
        }
        else if (coating.is(Items.PINK_TULIP)) {
            ResourceLocation resourceLocations = new ResourceLocation(JerotesVillage.MODID, "textures/entity/meror_machine/meror_champion/meror_champion_pink_tulip.png");
            coloredCutoutModelCopyLayerRender(this.getParentModel(), this.model,
                    resourceLocations, poseStack, multiBufferSource, n, entity, f, f2, f4, f5, f6, f3, 1.0f, 1.0f, 1.0f);
        }
        else if (coating.is(JerotesVillageItems.WILDERNESS_DIRT.get())) {
            ResourceLocation resourceLocations = new ResourceLocation(JerotesVillage.MODID, "textures/entity/meror_machine/meror_champion/meror_champion_wilderness_dirt.png");
            coloredCutoutModelCopyLayerRender(this.getParentModel(), this.model,
                    resourceLocations, poseStack, multiBufferSource, n, entity, f, f2, f4, f5, f6, f3, 1.0f, 1.0f, 1.0f);
        }
        else {
            if (coating == null || coating.isEmpty()) return;

            TrimMaterial material = findTrimMaterialByIngredient(entity.level().registryAccess(), coating);
            if (material == null) return;
            float itemModelIndex = material.itemModelIndex();
            int color = 0xffffff;
            int color2 = 0xffffff;
            if (itemModelIndex == 0.1f) {
                color = 0xe3dbc4;
                color2 = 0xf2efed;
            } else if (itemModelIndex == 0.2f) {
                color = 0x9daaaa;
                color2 = 0xc5d2d4;
            } else if (itemModelIndex == 0.3f) {
                color = 0x312e31;
                color2 = 0x5a575a;
            } else if (itemModelIndex == 0.4f) {
                color = 0x971607;
                color2 = 0xe62008;
            } else if (itemModelIndex == 0.5f) {
                color = 0x9a472c;
                color2 = 0xe3826c;
            } else if (itemModelIndex == 0.6f) {
                color = 0xdeb12d;
                color2 = 0xfffd90;
            } else if (itemModelIndex == 0.7f) {
                color = 0x11a036;
                color2 = 0x82f6ad;
            } else if (itemModelIndex == 0.8f) {
                color = 0x2cbaa8;
                color2 = 0xcbfff5;
            } else if (itemModelIndex == 0.9f) {
                color = 0x21497b;
                color2 = 0x416e97;
            } else if (itemModelIndex == 1.0f) {
                color = 0x6c49aa;
                color2 = 0xc98ff3;
            }
            float d = 1;
            float r = (float) (color >> 16 & 255) / 255.0F * d;
            float g = (float) (color >> 8 & 255) / 255.0F * d;
            float b = (float) (color & 255) / 255.0F * d;
            float a = (float) (color >> 24 & 255) / 255.0F;
            float r2 = (float) (color2 >> 16 & 255) / 255.0F * d;
            float g2 = (float) (color2 >> 8 & 255) / 255.0F * d;
            float b2 = (float) (color2 & 255) / 255.0F * d;
            float a2 = (float) (color2 >> 24 & 255) / 255.0F;
            coloredCutoutModelCopyLayerRender(this.getParentModel(), this.model,
                    resourceLocation, poseStack, multiBufferSource, n, entity, f, f2, f4, f5, f6, f3, r, g, b);
            coloredCutoutModelCopyLayerRender(this.getParentModel(), this.model,
                    resourceLocation2, poseStack, multiBufferSource, n, entity, f, f2, f4, f5, f6, f3, r2, g2, b2);
        }
    }

    private TrimMaterial findTrimMaterialByIngredient(RegistryAccess registryAccess, ItemStack stack) {
        var trimMaterialRegistry = registryAccess.registryOrThrow(Registries.TRIM_MATERIAL);
        for (var holder : trimMaterialRegistry) {
            TrimMaterial material = holder;
            if (holder.ingredient().get() == stack.getItem())
                return material;
        }
        return null;
    }
}
