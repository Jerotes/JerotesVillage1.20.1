package com.jerotes.jerotesvillage.client.renderer;

import com.jerotes.jerotesvillage.JerotesVillage;
import com.jerotes.jerotesvillage.client.layer.PetitionerAlphaLayer;
import com.jerotes.jerotesvillage.client.layer.WickedRidiculeCallsEyesLayer;
import com.jerotes.jerotesvillage.client.model.Modelpetitioner;
import com.jerotes.jerotesvillage.entity.Monster.WickedRidiculeCalls.PetitionerEntity;
import com.mojang.blaze3d.vertex.PoseStack;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.entity.MobRenderer;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.Mth;
import org.jetbrains.annotations.NotNull;

public class PetitionerRenderer extends MobRenderer<PetitionerEntity, Modelpetitioner<PetitionerEntity>> {
    private static final ResourceLocation LOCATION = new ResourceLocation(JerotesVillage.MODID, "textures/entity/undead/petitioner.png");
    private static final ResourceLocation WAIL_LOCATION = new ResourceLocation(JerotesVillage.MODID, "textures/entity/undead/petitioner_wail.png");
    private static final ResourceLocation EYE_LOCATION = new ResourceLocation(JerotesVillage.MODID, "textures/entity/undead/petitioner_wicked_ridicule_calls_eye.png");

    public PetitionerRenderer(EntityRendererProvider.Context context) {
        super(context, new Modelpetitioner<>(context.bakeLayer(Modelpetitioner.LAYER_LOCATION)), 0f);
        this.addLayer(new WickedRidiculeCallsEyesLayer<>(this, EYE_LOCATION));
        this.addLayer(new PetitionerAlphaLayer(this, LOCATION));
    }


    public static float alphaOf(PetitionerEntity entity) {
        float t = Mth.clamp(entity.attackAnimProgress / 10.0F, 0.0F, 1.0F);
        return Mth.lerp(t, 1.0F, 0.0F);
    }

    @Override
    protected RenderType getRenderType(PetitionerEntity entity, boolean visible, boolean translucent, boolean glowing) {
        if (alphaOf(entity) < 0.999F) {
            return null;
        }
        return super.getRenderType(entity, visible, translucent, glowing);
    }

    @Override
    public void render(PetitionerEntity petitionerEntity, float f, float f2, @NotNull PoseStack poseStack, @NotNull MultiBufferSource multiBufferSource, int n) {
        petitionerEntity.thisTickRenderTime += 1;
        float smoothFactor = 1f / (petitionerEntity.lastTickRenderTime + 1);
        petitionerEntity.attackAnimProgress = Mth.lerp(smoothFactor, petitionerEntity.attackAnimProgress, petitionerEntity.getAttackAnim());
        super.render(petitionerEntity, f, f2, poseStack, multiBufferSource, n);
    }

    @Override
    public ResourceLocation getTextureLocation(PetitionerEntity entity) {
        int wailTick = entity.getWailTick();
        if (wailTick >= 10 && wailTick <= 30) {
            return WAIL_LOCATION;
        }
        return LOCATION;
    }
}
