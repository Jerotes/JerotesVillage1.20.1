package com.jerotes.jerotesvillage.client.layer;

import com.jerotes.jerotesvillage.JerotesVillage;
import com.jerotes.jerotesvillage.entity.Animal.SoftSpiralSheepEntity;
import com.mojang.blaze3d.vertex.PoseStack;
import net.minecraft.client.Minecraft;
import net.minecraft.client.model.EntityModel;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.entity.RenderLayerParent;
import net.minecraft.client.renderer.entity.layers.RenderLayer;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.animal.Sheep;
import net.minecraft.world.item.DyeColor;

public class SoftSpiralSheepBaseHairLayer<T extends SoftSpiralSheepEntity, M extends EntityModel<T>> extends RenderLayer<T, M> {
    private final EntityModel<T> model;
    private static final ResourceLocation BASE_HAIR_LOCATION = new ResourceLocation(JerotesVillage.MODID, "textures/entity/soft_spiral_sheep_base_hair.png");
    private static final ResourceLocation BABY_BASE_HAIR_LOCATION = new ResourceLocation(JerotesVillage.MODID, "textures/entity/soft_spiral_sheep_baby_base_hair.png");

    public SoftSpiralSheepBaseHairLayer(RenderLayerParent<T, M> renderLayerParent, M m) {
        super(renderLayerParent);
        this.model = m;
    }

    @Override
    public void render(PoseStack poseStack, MultiBufferSource multiBufferSource, int n, T t, float f, float f2, float f3, float f4, float f5, float f6) {
        boolean bl;
        float f7;
        float f8;
        float f9;
        Minecraft minecraft = Minecraft.getInstance();
        boolean bl2 = bl = minecraft.shouldEntityAppearGlowing((Entity)t) && ((Entity)t).isInvisible();
        if (t.isInvisible() && !bl) {
            return;
        }
        ResourceLocation resourceLocation;
        resourceLocation = BASE_HAIR_LOCATION;
        if (t.isBaby()) {
            resourceLocation = BABY_BASE_HAIR_LOCATION;
        }
        if (t.hasCustomName() && "jeb_".equals(t.getName().getString())) {
            int n2 = 25;
            int n3 = t.tickCount / 25 + t.getId();
            int n4 = DyeColor.values().length;
            int n5 = n3 % n4;
            int n6 = (n3 + 1) % n4;
            float f10 = ((float)(t.tickCount % 25) + f3) / 25.0f;
            float[] arrf = Sheep.getColorArray(DyeColor.byId(n5));
            float[] arrf2 = Sheep.getColorArray(DyeColor.byId(n6));
            f8 = arrf[0] * (1.0f - f10) + arrf2[0] * f10;
            f7 = arrf[1] * (1.0f - f10) + arrf2[1] * f10;
            f9 = arrf[2] * (1.0f - f10) + arrf2[2] * f10;
        } else {
            float[] arrf = Sheep.getColorArray(t.getColor());
            f8 = arrf[0];
            f7 = arrf[1];
            f9 = arrf[2];
        }
        BigWoolGemsbokWoolLayer.coloredCutoutModelCopyLayerRender(this.getParentModel(), this.model, resourceLocation, poseStack, multiBufferSource, n, t, f, f2, f4, f5, f6, f3, f8, f7, f9);
    }
}