package com.jerotes.jerotesvillage.client.renderer;

import com.jerotes.jerotesvillage.JerotesVillage;
import com.jerotes.jerotesvillage.entity.Animal.WonderlandLiCatEntity;
import com.mojang.blaze3d.vertex.PoseStack;
import net.minecraft.client.model.CatModel;
import net.minecraft.client.model.geom.ModelLayers;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.entity.MobRenderer;
import net.minecraft.client.renderer.entity.layers.CatCollarLayer;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.animal.Cat;

public class WonderlandLiCatRenderer extends MobRenderer<Cat, CatModel<Cat>> {
    private static final ResourceLocation BROWN_LOCATION = new ResourceLocation(JerotesVillage.MODID, "textures/entity/wonderland_li_cat/brown.png");
    private static final ResourceLocation ORANGE_LOCATION = new ResourceLocation(JerotesVillage.MODID, "textures/entity/wonderland_li_cat/orange.png");
    private static final ResourceLocation BLACK_LOCATION = new ResourceLocation(JerotesVillage.MODID, "textures/entity/wonderland_li_cat/black.png");
    private static final ResourceLocation WHITE_LOCATION = new ResourceLocation(JerotesVillage.MODID, "textures/entity/wonderland_li_cat/white.png");
    private static final ResourceLocation CALICO_LOCATION = new ResourceLocation(JerotesVillage.MODID, "textures/entity/wonderland_li_cat/calico.png");
    private static final ResourceLocation COW_LOCATION = new ResourceLocation(JerotesVillage.MODID, "textures/entity/wonderland_li_cat/cow.png");
    private static final ResourceLocation TORTOISESHELL_LOCATION = new ResourceLocation(JerotesVillage.MODID, "textures/entity/wonderland_li_cat/tortoiseshell.png");
    private static final ResourceLocation YELLOW_LOCATION = new ResourceLocation(JerotesVillage.MODID, "textures/entity/wonderland_li_cat/yellow.png");

    public WonderlandLiCatRenderer(EntityRendererProvider.Context context) {
        super(context, new CatModel(context.bakeLayer(ModelLayers.CAT)), 0.4F);
        this.addLayer(new CatCollarLayer(this, context.getModelSet()));
    }

    @Override
    protected void scale(Cat cat, PoseStack poseStack, float f) {
        if (cat instanceof WonderlandLiCatEntity wonderlandLiCatEntity) {
            poseStack.scale(0.8f + wonderlandLiCatEntity.getCatWild() * 0.05f, 0.8f + wonderlandLiCatEntity.getCatWild() * 0.05f, 0.8f + wonderlandLiCatEntity.getCatWild() * 0.05f);
        }
        super.scale(cat, poseStack, f);
    }

    @Override
    public void render(Cat cat, float f, float f2, PoseStack poseStack, MultiBufferSource multiBufferSource, int n) {
        if (cat instanceof WonderlandLiCatEntity wonderlandLiCatEntity) {
            this.shadowRadius = 0.4f + wonderlandLiCatEntity.getCatWild() * 0.025f;
        }
        super.render(cat, f, f2, poseStack, multiBufferSource, n);
    }

    @Override
    public ResourceLocation getTextureLocation(Cat cat) {
        if (cat instanceof WonderlandLiCatEntity wonderlandLiCatEntity) {
            if (wonderlandLiCatEntity.getCatColor() == 1) {
                return BROWN_LOCATION;
            } else if (wonderlandLiCatEntity.getCatColor() == 2) {
                return BLACK_LOCATION;
            } else if (wonderlandLiCatEntity.getCatColor() == 3) {
                return ORANGE_LOCATION;
            } else if (wonderlandLiCatEntity.getCatColor() == 4) {
                return CALICO_LOCATION;
            } else if (wonderlandLiCatEntity.getCatColor() == 5) {
                return COW_LOCATION;
            } else if (wonderlandLiCatEntity.getCatColor() == 6) {
                return TORTOISESHELL_LOCATION;
            } else if (wonderlandLiCatEntity.getCatColor() == 7) {
                return YELLOW_LOCATION;
            } else {
                return WHITE_LOCATION;
            }
        }
        return BROWN_LOCATION;
    }
}