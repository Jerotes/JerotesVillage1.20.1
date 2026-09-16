package com.jerotes.jerotesvillage.client.layer;

import com.jerotes.jerotesvillage.JerotesVillage;
import com.jerotes.jerotesvillage.config.OtherMainConfig;
import com.jerotes.jerotesvillage.entity.Humanoid.Qoaiku.QoaikuEntity;
import com.jerotes.jerotesvillage.entity.Humanoid.Qoaiku.QoaikuSeekerEntity;
import com.jerotes.jerotesvillage.event.WeatherEvent;
import com.jerotes.jerotesvillage.init.JerotesVillageRenderType;
import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import net.minecraft.ChatFormatting;
import net.minecraft.client.Minecraft;
import net.minecraft.client.model.EntityModel;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.entity.LivingEntityRenderer;
import net.minecraft.client.renderer.entity.RenderLayerParent;
import net.minecraft.client.renderer.entity.layers.RenderLayer;
import net.minecraft.resources.ResourceLocation;

public class QoaikuOtherBodyLayer<T extends QoaikuEntity, M extends EntityModel<T>> extends RenderLayer<T, M> {
    private static final ResourceLocation QOAIKU_LOCATION = new ResourceLocation(JerotesVillage.MODID, "textures/entity/qoaiku/qoaiku_other.png");
    private static final ResourceLocation QOAIKU_SEEKER_LOCATION = new ResourceLocation(JerotesVillage.MODID, "textures/entity/qoaiku/qoaiku_seeker_other.png");
    private final EntityModel<T> model;
    public QoaikuOtherBodyLayer(RenderLayerParent<T, M> renderLayerParent, M m) {
        super(renderLayerParent);
        this.model = m;
    }

    @Override
    public void render(PoseStack poseStack, MultiBufferSource multiBufferSource, int n, T t, float f, float f2, float f3, float f4, float f5, float f6) {
        Minecraft minecraft = Minecraft.getInstance();
        boolean bl = minecraft.shouldEntityAppearGlowing(t) && t.isInvisible();
        String string = ChatFormatting.stripFormatting(t.getName().getString());
        if ((WeatherEvent.isNewYearsDay() && OtherMainConfig.SpecialDay) || "Old Lore".equals(string)) {
            return;
        }
        if (!t.isInvisible() || bl) {
            ResourceLocation resourceLocation = QOAIKU_LOCATION;
            if (t instanceof QoaikuSeekerEntity) {
                resourceLocation = QOAIKU_SEEKER_LOCATION;
            }
            this.getParentModel().copyPropertiesTo(this.model);
            this.model.prepareMobModel(t, f, f2, f3);
            this.model.setupAnim(t, f, f2, f4, f5, f6);
            VertexConsumer vertexConsumer = multiBufferSource.getBuffer(JerotesVillageRenderType.entityTranslucent(resourceLocation));
            this.model.renderToBuffer(poseStack, vertexConsumer, n, LivingEntityRenderer.getOverlayCoords(t, 0.0f), 1.0f, 1.0f, 1.0f, 1.0f);
        }
    }

    private float xOffset(float f) {
        return f * 0.08f;
    }
}

