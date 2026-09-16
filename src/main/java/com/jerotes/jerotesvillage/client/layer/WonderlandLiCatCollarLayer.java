package com.jerotes.jerotesvillage.client.layer;

import com.jerotes.jerotesvillage.entity.Animal.WonderlandLiCatEntity;
import com.mojang.blaze3d.vertex.PoseStack;
import net.minecraft.client.model.CatModel;
import net.minecraft.client.model.geom.EntityModelSet;
import net.minecraft.client.model.geom.ModelLayers;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.entity.RenderLayerParent;
import net.minecraft.client.renderer.entity.layers.RenderLayer;
import net.minecraft.resources.ResourceLocation;

public class WonderlandLiCatCollarLayer extends RenderLayer<WonderlandLiCatEntity, CatModel<WonderlandLiCatEntity>> {
    private static final ResourceLocation CAT_COLLAR_LOCATION = new ResourceLocation("textures/entity/cat/cat_collar.png");
    private final CatModel<WonderlandLiCatEntity> catModel;

    public WonderlandLiCatCollarLayer(RenderLayerParent<WonderlandLiCatEntity, CatModel<WonderlandLiCatEntity>> renderLayerParent, EntityModelSet entityModelSet) {
        super(renderLayerParent);
        this.catModel = new CatModel(entityModelSet.bakeLayer(ModelLayers.CAT_COLLAR));
    }

    @Override
    public void render(PoseStack poseStack, MultiBufferSource multiBufferSource, int n, WonderlandLiCatEntity cat, float f, float f2, float f3, float f4, float f5, float f6) {
        if (!cat.isTame()) {
            return;
        }
        float[] arrf = cat.getCollarColor().getTextureDiffuseColors();
        WonderlandLiCatCollarLayer.coloredCutoutModelCopyLayerRender(this.getParentModel(), this.catModel, CAT_COLLAR_LOCATION, poseStack, multiBufferSource, n, cat, f, f2, f4, f5, f6, f3, arrf[0], arrf[1], arrf[2]);
    }
}

