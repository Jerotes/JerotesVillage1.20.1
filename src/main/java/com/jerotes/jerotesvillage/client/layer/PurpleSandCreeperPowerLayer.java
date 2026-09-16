package com.jerotes.jerotesvillage.client.layer;

import com.jerotes.jerotesvillage.JerotesVillage;
import com.jerotes.jerotesvillage.entity.Monster.PurpleSandCreeperEntity;
import net.minecraft.client.model.CreeperModel;
import net.minecraft.client.model.EntityModel;
import net.minecraft.client.model.geom.EntityModelSet;
import net.minecraft.client.model.geom.ModelLayers;
import net.minecraft.client.renderer.entity.RenderLayerParent;
import net.minecraft.client.renderer.entity.layers.EnergySwirlLayer;
import net.minecraft.resources.ResourceLocation;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

@OnlyIn(Dist.CLIENT)
public class PurpleSandCreeperPowerLayer extends EnergySwirlLayer<PurpleSandCreeperEntity, CreeperModel<PurpleSandCreeperEntity>> {
    private static final ResourceLocation POWER_LOCATION = new ResourceLocation(JerotesVillage.MODID, "textures/entity/purple_sand_creeper_armor.png");
    private final CreeperModel<PurpleSandCreeperEntity> model;

    public PurpleSandCreeperPowerLayer(RenderLayerParent<PurpleSandCreeperEntity, CreeperModel<PurpleSandCreeperEntity>> renderLayerParent, EntityModelSet entityModelSet) {
        super(renderLayerParent);
        this.model = new CreeperModel(entityModelSet.bakeLayer(ModelLayers.CREEPER_ARMOR));
    }

    protected float xOffset(float f) {
        return f * 0.01F;
    }

    protected ResourceLocation getTextureLocation() {
        return POWER_LOCATION;
    }

    protected EntityModel<PurpleSandCreeperEntity> model() {
        return this.model;
    }
}
