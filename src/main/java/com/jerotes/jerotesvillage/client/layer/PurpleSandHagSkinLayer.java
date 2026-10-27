package com.jerotes.jerotesvillage.client.layer;

import com.jerotes.jerotesvillage.JerotesVillage;
import com.jerotes.jerotes.util.Color;
import com.mojang.blaze3d.vertex.PoseStack;
import net.minecraft.client.Minecraft;
import net.minecraft.client.model.EntityModel;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.entity.RenderLayerParent;
import net.minecraft.client.renderer.entity.layers.RenderLayer;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.LivingEntity;

import static com.jerotes.jerotesvillage.client.layer.SkinLayerCache.colorRgb;
import static com.jerotes.jerotesvillage.client.layer.SkinLayerCache.tex;

public class PurpleSandHagSkinLayer<T extends LivingEntity, M extends EntityModel<T>> extends RenderLayer<T, M> {
    private final EntityModel<T> model;

    public PurpleSandHagSkinLayer(RenderLayerParent<T, M> renderLayerParent, M m) {
        super(renderLayerParent);
        this.model = m;
    }

    @Override
    public void render(PoseStack poseStack, MultiBufferSource multiBufferSource, int n, T t, float f, float f2, float f3, float f4, float f5, float f6) {
        boolean bl;
        Minecraft minecraft = Minecraft.getInstance();
        boolean bl2 = bl = minecraft.shouldEntityAppearGlowing(t) && t.isInvisible();
        if (t.isInvisible() && !bl) {
            return;
        }
        this.getParentModel().copyPropertiesTo(this.model);
        this.model.prepareMobModel(t, f, f2, f3);
        this.model.setupAnim(t, f, f2, f4, f5, f6);
        String SkinColorHave = "null";
        //皮肤
        ResourceLocation bodyLocation = tex("textures/entity/skin/hag/female/base.png");
        //眼睛
        ResourceLocation eyeBaseLocation = tex("textures/entity/skin/hag/female/eye_base.png");
        ResourceLocation leftEyeLocation = tex("textures/entity/skin/hag/female/eye/eye_left.png");
        ResourceLocation rightEyeLocation = tex("textures/entity/skin/hag/female/eye/eye_right.png");
        //头发
        ResourceLocation hairLocation = tex("textures/entity/skin/hag/female/hair/hair_16.png");
        //附件
        ResourceLocation addLocation_1 = tex("textures/entity/skin/hag/female/add/add_1.png");
        ResourceLocation addBaseLocation_1 = tex("textures/entity/skin/hag/female/add/add_base_1.png");
        //上衣
        ResourceLocation jacketLocation = tex("textures/entity/skin/hag/female/clothes/jacket_1.png");
        ResourceLocation jacketBaseLocation = tex("textures/entity/skin/hag/female/clothes/jacket_base_1.png");
        //裤子
        ResourceLocation pantsLocation = tex("textures/entity/skin/hag/female/clothes/pants_1.png");
        ResourceLocation pantsBaseLocation = tex("textures/entity/skin/hag/female/clothes/pants_base_1.png");
        //鞋子
        ResourceLocation shoesLocation = tex("textures/entity/skin/hag/female/clothes/shoes_1.png");
        ResourceLocation shoesBaseLocation = tex("textures/entity/skin/hag/female/clothes/shoes_base_1.png");
        //帽子
        ResourceLocation hatLocation = tex("textures/entity/skin/hag/female/clothes/hat_11.png");
        ResourceLocation hatBaseLocation = tex("textures/entity/skin/hag/female/clothes/hat_base_11.png");
        //外套
        ResourceLocation overcoatLocation = tex("textures/entity/skin/hag/female/clothes/overcoat_3.png");
        ResourceLocation overcoatBaseLocation = tex("textures/entity/skin/hag/female/clothes/overcoat_base_3.png");

        //皮肤
        if (true) {
            String SkinColor = "898444";
            int[] arrf = colorRgb(SkinColor);
            float r = arrf[0] / 255f;
            float g = arrf[1] / 255f;
            float b = arrf[2] / 255f;
            SkinEntityBodyLayer.coloredCutoutModelCopyLayerRender(this.getParentModel(), this.model, bodyLocation, poseStack, multiBufferSource, n, t, f, f2, f4, f5, f6, f3, r, g, b);
            SkinColorHave = SkinColor;
        }
        //眼睛
        if (true) {
            //眼睛基础
            String EyeBaseColor = "eeb900";
            int[] arrf = colorRgb(EyeBaseColor);
            float r = arrf[0] / 255f;
            float g = arrf[1] / 255f;
            float b = arrf[2] / 255f;
            SkinEntityBodyLayer.coloredCutoutModelCopyLayerRender(this.getParentModel(), this.model, eyeBaseLocation, poseStack, multiBufferSource, n, t, f, f2, f4, f5, f6, f3, r, g, b);
            //眼睛具体
            if (true) {
                String LeftEyeColor = "8d6d00";
                String RightEyeColor = LeftEyeColor;
                int[] left_arrf = colorRgb(LeftEyeColor);
                float left_r = left_arrf[0] / 255f;
                float left_g = left_arrf[1] / 255f;
                float left_b = left_arrf[2] / 255f;
                int[] right_arrf = colorRgb(RightEyeColor);
                float right_r = right_arrf[0] / 255f;
                float right_g = right_arrf[1] / 255f;
                float right_b = right_arrf[2] / 255f;
                SkinEntityBodyLayer.coloredCutoutModelCopyLayerRender(this.getParentModel(), this.model, leftEyeLocation, poseStack, multiBufferSource, n, t, f, f2, f4, f5, f6, f3, left_r, left_g, left_b);
                SkinEntityBodyLayer.coloredCutoutModelCopyLayerRender(this.getParentModel(), this.model, rightEyeLocation, poseStack, multiBufferSource, n, t, f, f2, f4, f5, f6, f3, right_r, right_g, right_b);
            }
        }
        //头发
        if (true) {
            String HairColor;
            //发色
            HairColor = "ffffff";
            int[] arrf = colorRgb(HairColor);
            float r = arrf[0] / 255f;
            float g = arrf[1] / 255f;
            float b = arrf[2] / 255f;
            SkinEntityBodyLayer.coloredCutoutModelCopyLayerRender(this.getParentModel(), this.model, hairLocation, poseStack, multiBufferSource, n, t, f, f2, f4, f5, f6, f3, r, g, b);
        }
        //附加
        if (true) {
            //附加_1
            //附加基础
            int[] arrf_base_1 = colorRgb("ffffff");
            float r_base_1 = arrf_base_1[0] / 255f;
            float g_base_1 = arrf_base_1[1] / 255f;
            float b_base_1 = arrf_base_1[2] / 255f;
            SkinEntityBodyLayer.coloredCutoutModelCopyLayerRender(this.getParentModel(), this.model, addBaseLocation_1, poseStack, multiBufferSource, n, t, f, f2, f4, f5, f6, f3, r_base_1, g_base_1, b_base_1);
            int[] arrf_1 = colorRgb(SkinColorHave);
            float r_1 = arrf_1[0] / 255f;
            float g_1 = arrf_1[1] / 255f;
            float b_1 = arrf_1[2] / 255f;
            SkinEntityBodyLayer.coloredCutoutModelCopyLayerRender(this.getParentModel(), this.model, addLocation_1, poseStack, multiBufferSource, n, t, f, f2, f4, f5, f6, f3, r_1, g_1, b_1);
        }
        //上衣
        if (true) {
            String ClothesColor;
            ClothesColor = "d0d0d0";
            int[] arrf_base_1 = colorRgb("ffffff");
            float r_base_1 = arrf_base_1[0] / 255f;
            float g_base_1 = arrf_base_1[1] / 255f;
            float b_base_1 = arrf_base_1[2] / 255f;
            SkinEntityBodyLayer.coloredCutoutModelCopyLayerRender(this.getParentModel(), this.model, jacketBaseLocation, poseStack, multiBufferSource, n, t, f, f2, f4, f5, f6, f3, r_base_1, g_base_1, b_base_1);
            int[] arrf_1 = colorRgb(ClothesColor);
            float r_1 = arrf_1[0] / 255f;
            float g_1 = arrf_1[1] / 255f;
            float b_1 = arrf_1[2] / 255f;
            SkinEntityBodyLayer.coloredCutoutModelCopyLayerRender(this.getParentModel(), this.model, jacketLocation, poseStack, multiBufferSource, n, t, f, f2, f4, f5, f6, f3, r_1, g_1, b_1);
        }
        //裤子
        if (true) {
            String ClothesColor;
            ClothesColor = "d0d0d0";
            int[] arrf_base_1 = colorRgb("ffffff");
            float r_base_1 = arrf_base_1[0] / 255f;
            float g_base_1 = arrf_base_1[1] / 255f;
            float b_base_1 = arrf_base_1[2] / 255f;
            SkinEntityBodyLayer.coloredCutoutModelCopyLayerRender(this.getParentModel(), this.model, pantsBaseLocation, poseStack, multiBufferSource, n, t, f, f2, f4, f5, f6, f3, r_base_1, g_base_1, b_base_1);
            int[] arrf_1 = colorRgb(ClothesColor);
            float r_1 = arrf_1[0] / 255f;
            float g_1 = arrf_1[1] / 255f;
            float b_1 = arrf_1[2] / 255f;
            SkinEntityBodyLayer.coloredCutoutModelCopyLayerRender(this.getParentModel(), this.model, pantsLocation, poseStack, multiBufferSource, n, t, f, f2, f4, f5, f6, f3, r_1, g_1, b_1);
        }
        //鞋子
        if (true) {
            String ClothesColor;
            ClothesColor = "d0d0d0";
            int[] arrf_base_1 = colorRgb("ffffff");
            float r_base_1 = arrf_base_1[0] / 255f;
            float g_base_1 = arrf_base_1[1] / 255f;
            float b_base_1 = arrf_base_1[2] / 255f;
            SkinEntityBodyLayer.coloredCutoutModelCopyLayerRender(this.getParentModel(), this.model, shoesBaseLocation, poseStack, multiBufferSource, n, t, f, f2, f4, f5, f6, f3, r_base_1, g_base_1, b_base_1);
            int[] arrf_1 = colorRgb(ClothesColor);
            float r_1 = arrf_1[0] / 255f;
            float g_1 = arrf_1[1] / 255f;
            float b_1 = arrf_1[2] / 255f;
            SkinEntityBodyLayer.coloredCutoutModelCopyLayerRender(this.getParentModel(), this.model, shoesLocation, poseStack, multiBufferSource, n, t, f, f2, f4, f5, f6, f3, r_1, g_1, b_1);
        }
        //帽子
        if (true) {
            String ClothesColor;
            ClothesColor = "d0d0d0";
            int[] arrf_base_1 = colorRgb("ffffff");
            float r_base_1 = arrf_base_1[0] / 255f;
            float g_base_1 = arrf_base_1[1] / 255f;
            float b_base_1 = arrf_base_1[2] / 255f;
            SkinEntityBodyLayer.coloredCutoutModelCopyLayerRender(this.getParentModel(), this.model, hatBaseLocation, poseStack, multiBufferSource, n, t, f, f2, f4, f5, f6, f3, r_base_1, g_base_1, b_base_1);
            int[] arrf_1 = colorRgb(ClothesColor);
            float r_1 = arrf_1[0] / 255f;
            float g_1 = arrf_1[1] / 255f;
            float b_1 = arrf_1[2] / 255f;
            SkinEntityBodyLayer.coloredCutoutModelCopyLayerRender(this.getParentModel(), this.model, hatLocation, poseStack, multiBufferSource, n, t, f, f2, f4, f5, f6, f3, r_1, g_1, b_1);
        }
        //大衣
        if (true) {
            String ClothesColor;
            ClothesColor = "d0d0d0";
            int[] arrf_base_1 = colorRgb("ffffff");
            float r_base_1 = arrf_base_1[0] / 255f;
            float g_base_1 = arrf_base_1[1] / 255f;
            float b_base_1 = arrf_base_1[2] / 255f;
            SkinEntityBodyLayer.coloredCutoutModelCopyLayerRender(this.getParentModel(), this.model, overcoatBaseLocation, poseStack, multiBufferSource, n, t, f, f2, f4, f5, f6, f3, r_base_1, g_base_1, b_base_1);
            int[] arrf_1 = colorRgb(ClothesColor);
            float r_1 = arrf_1[0] / 255f;
            float g_1 = arrf_1[1] / 255f;
            float b_1 = arrf_1[2] / 255f;
            SkinEntityBodyLayer.coloredCutoutModelCopyLayerRender(this.getParentModel(), this.model, overcoatLocation, poseStack, multiBufferSource, n, t, f, f2, f4, f5, f6, f3, r_1, g_1, b_1);
        }
    }
}

