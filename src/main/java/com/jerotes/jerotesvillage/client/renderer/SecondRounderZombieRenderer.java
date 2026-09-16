package com.jerotes.jerotesvillage.client.renderer;

import com.jerotes.jerotes.client.layer.TruesightLayer;
import com.jerotes.jerotesvillage.JerotesVillage;
import com.jerotes.jerotesvillage.client.layer.SkinEntityBodyLayer;
import com.jerotes.jerotesvillage.client.layer.WickedRidiculeCallsEyesLayer;
import com.jerotes.jerotesvillage.config.OtherMainConfig;
import com.jerotes.jerotesvillage.entity.Monster.WickedRidiculeCalls.SecondRounderZombieEntity;
import com.mojang.blaze3d.vertex.PoseStack;
import net.minecraft.client.model.ZombieModel;
import net.minecraft.client.model.geom.ModelLayers;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.entity.ZombieRenderer;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.monster.Zombie;

public class SecondRounderZombieRenderer extends ZombieRenderer {
    private static final ResourceLocation LOCATION = new ResourceLocation(JerotesVillage.MODID, "textures/entity/second_rounder_zombie.png");
    private static final ResourceLocation NULL_LOCATION = new ResourceLocation(JerotesVillage.MODID, "textures/entity/null.png");
    private static final ResourceLocation WICKED_RIDICULE_CALLS_LOCATION = new ResourceLocation(JerotesVillage.MODID, "textures/entity/wicked_ridicule_calls_eye.png");
    public SecondRounderZombieRenderer(EntityRendererProvider.Context context) {
        super(context, ModelLayers.ZOMBIE, ModelLayers.ZOMBIE_INNER_ARMOR, ModelLayers.ZOMBIE_OUTER_ARMOR);
        this.addLayer(new SkinEntityBodyLayer(this, new ZombieModel(context.bakeLayer(ModelLayers.ZOMBIE)), "second_rounder_zombie"));
        this.addLayer(new WickedRidiculeCallsEyesLayer(this, WICKED_RIDICULE_CALLS_LOCATION));
        this.addLayer(new TruesightLayer<>(this));
    }

    @Override
    protected void scale(Zombie entity, PoseStack poseStack, float f) {
        if (((SecondRounderZombieEntity)entity).isGiant()) {
            poseStack.scale(1.75f, 1.75f, 1.75f);
        }
        else {
            poseStack.scale(1.05f, 1.05f, 1.05f);
        }
        super.scale(entity, poseStack, f);
    }

    @Override
    public void render(Zombie entity, float f, float f2, PoseStack poseStack, MultiBufferSource multiBufferSource, int n) {
        if (((SecondRounderZombieEntity)entity).isGiant()) {
            this.shadowRadius = 0.90f;
        }
        else {
            this.shadowRadius = 0.53f;
        }
        super.render(entity, f, f2, poseStack, multiBufferSource, n);
    }


    @Override
    protected boolean isShaking(Zombie zombie) {
        return super.isShaking(zombie) || ((SecondRounderZombieEntity)zombie).isConverting();
    }
    @Override
    public ResourceLocation getTextureLocation(Zombie entity) {
        return OtherMainConfig.RandomSkinMobHasUnderTexture ? LOCATION : NULL_LOCATION;
    }
}