package com.jerotes.jerotesvillage.client.renderer;

import com.jerotes.jerotesvillage.JerotesVillage;
import com.jerotes.jerotesvillage.client.layer.GlowOtherBodyLayer;
import com.jerotes.jerotesvillage.client.layer.WickedRidiculeCallsEyesLayer;
import com.jerotes.jerotesvillage.client.model.Modelmocker;
import com.jerotes.jerotesvillage.entity.Monster.WickedRidiculeCalls.MockerEntity;
import com.mojang.blaze3d.vertex.PoseStack;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.entity.MobRenderer;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.Mth;
import org.jetbrains.annotations.NotNull;

public class MockerRenderer extends MobRenderer<MockerEntity, Modelmocker<MockerEntity>> {
    private static final ResourceLocation LOCATION = new ResourceLocation(JerotesVillage.MODID, "textures/entity/undead/mocker.png");
    private static final ResourceLocation GLOW_LOCATION = new ResourceLocation(JerotesVillage.MODID, "textures/entity/undead/mocker_glow.png");
    private static final ResourceLocation SPECIAL_GLOW_LOCATION = new ResourceLocation(JerotesVillage.MODID, "textures/entity/undead/mocker_wicked_ridicule_calls_eye.png");

    public MockerRenderer(EntityRendererProvider.Context context) {
        super(context, new Modelmocker<>(context.bakeLayer(Modelmocker.LAYER_LOCATION)), 0.65F);
        this.addLayer(new GlowOtherBodyLayer<>(this, new Modelmocker(context.bakeLayer(Modelmocker.LAYER_LOCATION)), GLOW_LOCATION));
        this.addLayer(new WickedRidiculeCallsEyesLayer<>(this, SPECIAL_GLOW_LOCATION));
    }

    @Override
    public void render(MockerEntity mockerEntity, float f, float f2, @NotNull PoseStack poseStack, @NotNull MultiBufferSource multiBufferSource, int n) {
        mockerEntity.thisTickRenderTime += 1;
        float smoothFactor = 1f / (mockerEntity.lastTickRenderTime + 1);
        mockerEntity.climbAnimProgress = Mth.lerp(smoothFactor, mockerEntity.climbAnimProgress, mockerEntity.getClimbAnim());
        mockerEntity.attackAnimProgress = Mth.lerp(smoothFactor, mockerEntity.attackAnimProgress, mockerEntity.getAttackAnim());
        super.render(mockerEntity, f, f2, poseStack, multiBufferSource, n);
    }

    @Override
    public ResourceLocation getTextureLocation(MockerEntity entity) {
        return LOCATION;
    }
}
