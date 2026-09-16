package com.jerotes.jerotesvillage.client.renderer;

import com.jerotes.jerotes.entity.Shoot.Arrow.BaseArrowEntity;
import com.jerotes.jerotesvillage.JerotesVillage;
import com.jerotes.jerotesvillage.entity.Shoot.Arrow.*;
import net.minecraft.client.renderer.entity.ArrowRenderer;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.resources.ResourceLocation;

public class JerotesVillageArrowRenderer extends ArrowRenderer<BaseArrowEntity> {
    public static final ResourceLocation ARC_ARROW = new ResourceLocation(JerotesVillage.MODID, "textures/entity/projectiles/arc_arrow.png");
    public static final ResourceLocation ANCIENT_SECOND_ROUNDER_ARROW = new ResourceLocation(JerotesVillage.MODID, "textures/entity/projectiles/ancient_second_rounder_arrow.png");
    public static final ResourceLocation CARVED_VILLAGER_SPECTRAL_ARROW = new ResourceLocation(JerotesVillage.MODID, "textures/entity/projectiles/carved_villager_spectral_arrow.png");
    public static final ResourceLocation MALIGNASAUR_ARROW = new ResourceLocation(JerotesVillage.MODID, "textures/entity/projectiles/malignasaur_arrow.png");
    public static final ResourceLocation MEROR_METAL_ARROW = new ResourceLocation(JerotesVillage.MODID, "textures/entity/projectiles/meror_metal_arrow.png");
    public static final ResourceLocation VIRTUAL_CAVE_CRYSTAL_ARROW = new ResourceLocation(JerotesVillage.MODID, "textures/entity/projectiles/virtual_cave_crystal_arrow.png");
    public static final ResourceLocation MEROR_CHARGING_BOW_ARROW_1 = new ResourceLocation(JerotesVillage.MODID, "textures/entity/projectiles/meror_charging_bow_arrow_1.png");
    public static final ResourceLocation MEROR_CHARGING_BOW_ARROW_2 = new ResourceLocation(JerotesVillage.MODID, "textures/entity/projectiles/meror_charging_bow_arrow_2.png");
    public static final ResourceLocation MEROR_CHARGING_BOW_ARROW_3 = new ResourceLocation(JerotesVillage.MODID, "textures/entity/projectiles/meror_charging_bow_arrow_3.png");
    public static final ResourceLocation MEROR_CHARGING_BOW_ARROW_4 = new ResourceLocation(JerotesVillage.MODID, "textures/entity/projectiles/meror_charging_bow_arrow_4.png");
    public static final ResourceLocation MEROR_CHARGING_BOW_ARROW_5 = new ResourceLocation(JerotesVillage.MODID, "textures/entity/projectiles/meror_charging_bow_arrow_5.png");
    public JerotesVillageArrowRenderer(EntityRendererProvider.Context context) {
        super(context);
    }

    @Override
    public ResourceLocation getTextureLocation(BaseArrowEntity arrow) {
        if (arrow instanceof ArcArrowEntity)
            return ARC_ARROW;
        if (arrow instanceof AncientSecondRounderArrowEntity)
            return ANCIENT_SECOND_ROUNDER_ARROW;
        if (arrow instanceof CarvedVillagerSpectralArrowEntity)
            return CARVED_VILLAGER_SPECTRAL_ARROW;
        if (arrow instanceof MalignasaurArrowEntity)
            return MALIGNASAUR_ARROW;
        if (arrow instanceof MerorMetalArrowEntity)
            return MEROR_METAL_ARROW;
        if (arrow instanceof VirtualCaveCrystalArrowEntity)
            return VIRTUAL_CAVE_CRYSTAL_ARROW;
        if (arrow instanceof MerorChargingBowArrowIEntity)
            return MEROR_CHARGING_BOW_ARROW_1;
        if (arrow instanceof MerorChargingBowArrowIIEntity)
            return MEROR_CHARGING_BOW_ARROW_2;
        if (arrow instanceof MerorChargingBowArrowIIIEntity)
            return MEROR_CHARGING_BOW_ARROW_3;
        if (arrow instanceof MerorChargingBowArrowIVEntity)
            return MEROR_CHARGING_BOW_ARROW_4;
        if (arrow instanceof MerorChargingBowArrowVEntity)
            return MEROR_CHARGING_BOW_ARROW_5;
        return CARVED_VILLAGER_SPECTRAL_ARROW;
    }
}

