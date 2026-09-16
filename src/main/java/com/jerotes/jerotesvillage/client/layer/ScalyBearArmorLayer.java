package com.jerotes.jerotesvillage.client.layer;

import com.jerotes.jerotes.item.Interface.ItemBaseGiantBeastArmor;
import com.jerotes.jerotes.item.ItemGiantBeastArmor;
import com.jerotes.jerotes.util.Color;
import com.jerotes.jerotes.util.EntityAndItemFind;
import com.jerotes.jerotesvillage.JerotesVillage;
import com.jerotes.jerotesvillage.client.model.Modelscaly_bear;
import com.jerotes.jerotesvillage.client.model.Modelscaly_bear_armor;
import com.jerotes.jerotesvillage.entity.Animal.ScalyBearEntity;
import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import net.minecraft.client.model.geom.EntityModelSet;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.entity.RenderLayerParent;
import net.minecraft.client.renderer.entity.layers.RenderLayer;
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.TagKey;
import net.minecraft.util.Mth;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.item.DyeableLeatherItem;
import net.minecraft.world.item.ItemStack;

public class ScalyBearArmorLayer extends RenderLayer<ScalyBearEntity, Modelscaly_bear<ScalyBearEntity>> {
    private static final ResourceLocation LOCATION = new ResourceLocation(JerotesVillage.MODID, "textures/entity/scaly_bear/scaly_bear_armor/scaly_bear_armor.png");
    private final Modelscaly_bear<ScalyBearEntity> model;

    public ScalyBearArmorLayer(RenderLayerParent<ScalyBearEntity, Modelscaly_bear<ScalyBearEntity>> renderLayerParent, EntityModelSet entityModelSet) {
        super(renderLayerParent);
        this.model = new Modelscaly_bear_armor(entityModelSet.bakeLayer(Modelscaly_bear_armor.LAYER_LOCATION));
    }

    @Override
    public void render(PoseStack poseStack, MultiBufferSource multiBufferSource, int n, ScalyBearEntity scalyBear, float f, float f2, float f3, float f4, float f5, float f6) {
        if (EntityAndItemFind.isTrueInvisible(scalyBear)) {
            return;
        }
        float f7;
        float f8;
        float f9;
        ItemStack itemStack = scalyBear.getItemBySlot(EquipmentSlot.CHEST);
        if (!(itemStack.getItem() instanceof ItemBaseGiantBeastArmor itemGiantBeastArmor)) {
            return;
        }
        this.getParentModel().copyPropertiesTo(this.model);
        this.model.prepareMobModel(scalyBear, f, f2, f3);
        this.model.setupAnim(scalyBear, f, f2, f4, f5, f6);

        if (itemStack.is(TagKey.create(Registries.ITEM, new ResourceLocation(JerotesVillage.MODID, "scaly_bear_special_armor")))) {
            ResourceLocation LOCATIONS = new ResourceLocation(JerotesVillage.MODID, "textures/entity/scaly_bear/scaly_bear_armor/" + itemGiantBeastArmor.getTextureString() + ".png");
            if (itemGiantBeastArmor instanceof DyeableLeatherItem) {
                int n2 = ((DyeableLeatherItem)itemGiantBeastArmor).getColor(itemStack);
                f9 = (float)(n2 >> 16 & 0xFF) / 255.0f;
                f7 = (float)(n2 >> 8 & 0xFF) / 255.0f;
                f8 = (float)(n2 & 0xFF) / 255.0f;
                VertexConsumer vertexConsumer = multiBufferSource.getBuffer(RenderType.entityCutoutNoCull(LOCATIONS));
                this.model.renderToBuffer(poseStack, vertexConsumer, n, OverlayTexture.NO_OVERLAY, f9, f7, f8, 1.0f);
            }
            else {
                f9 = 1.0f;
                f7 = 1.0f;
                f8 = 1.0f;
                VertexConsumer vertexConsumer = multiBufferSource.getBuffer(RenderType.entityCutoutNoCull(LOCATIONS));
                this.model.renderToBuffer(poseStack, vertexConsumer, n, OverlayTexture.NO_OVERLAY, f9, f7, f8, 1.0f);
            }
            return;
        }


        if (itemGiantBeastArmor instanceof DyeableLeatherItem) {
            int n2 = ((DyeableLeatherItem)itemGiantBeastArmor).getColor(itemStack);
            f9 = (float)(n2 >> 16 & 0xFF) / 255.0f;
            f7 = (float)(n2 >> 8 & 0xFF) / 255.0f;
            f8 = (float)(n2 & 0xFF) / 255.0f;
            VertexConsumer vertexConsumer = multiBufferSource.getBuffer(RenderType.entityCutoutNoCull(LOCATION));
            this.model.renderToBuffer(poseStack, vertexConsumer, n, OverlayTexture.NO_OVERLAY, f9, f7, f8, 1.0f);
        }
        else if (itemGiantBeastArmor instanceof ItemGiantBeastArmor itemGiantBeastArmor1) {
            int n2 = itemGiantBeastArmor1.getColor();
            for (int ns = 1; ns < 7; ++ns) {
                int[] arrf = Color.colorIntToRGBInt(n2);
                if (arrf == null)
                    return;
                //-1 0 1 2
                int colorLightOrDark = ns - 4;
                colorLightOrDark = colorLightOrDark < 0 ? (int) (colorLightOrDark * 1.1f) : colorLightOrDark > 0 ? (int) (colorLightOrDark * 1.1f) : colorLightOrDark;
                int addColor = 30 * colorLightOrDark;

                int rAddColor = (int) (addColor * 1.1f);
                float r = Mth.clamp((arrf[0] - rAddColor), 0, 255) / 255f;

                int gAddColor = (int) (addColor * 0.9f);
                float g = Mth.clamp((arrf[1] - gAddColor), 0, 255) / 255f;

                int bAddColor = (int) (addColor * 1f);
                float b = Mth.clamp((arrf[2] - bAddColor), 0, 255) / 255f;

                VertexConsumer vertexConsumer1 = multiBufferSource.getBuffer(RenderType.entityCutoutNoCull(new ResourceLocation(JerotesVillage.MODID, "textures/entity/scaly_bear/scaly_bear_armor/scaly_bear_armor_"+ ns +".png")));
                this.model.renderToBuffer(poseStack, vertexConsumer1, n, OverlayTexture.NO_OVERLAY, r, g, b, 1.0f);
            }
        }
        else {
            f9 = 1.0f;
            f7 = 1.0f;
            f8 = 1.0f;
            VertexConsumer vertexConsumer = multiBufferSource.getBuffer(RenderType.entityCutoutNoCull(LOCATION));
            this.model.renderToBuffer(poseStack, vertexConsumer, n, OverlayTexture.NO_OVERLAY, f9, f7, f8, 1.0f);
        }
    }
}

