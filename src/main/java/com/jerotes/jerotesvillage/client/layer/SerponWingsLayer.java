package com.jerotes.jerotesvillage.client.layer;

import com.jerotes.jerotesvillage.JerotesVillage;
import com.jerotes.jerotesvillage.client.model.Modelserpon_other;
import com.jerotes.jerotesvillage.init.JerotesVillageMobEffects;
import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import net.minecraft.client.Minecraft;
import net.minecraft.client.model.EntityModel;
import net.minecraft.client.model.Model;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.entity.LivingEntityRenderer;
import net.minecraft.client.renderer.entity.RenderLayerParent;
import net.minecraft.client.renderer.entity.layers.RenderLayer;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.player.Player;

import java.util.Objects;

public class SerponWingsLayer<T extends Player, M extends EntityModel<T>> extends RenderLayer<T, M> {
    private final EntityRendererProvider.Context context;
    private static final ResourceLocation LOCATION = new ResourceLocation(JerotesVillage.MODID, "textures/entity/player_serpon_other.png");

    public SerponWingsLayer(RenderLayerParent<T, M> renderLayerParent, EntityRendererProvider.Context context) {
        super(renderLayerParent);
        this.context = context;
    }

    @Override
    public void render(PoseStack poseStack, MultiBufferSource multiBufferSource, int n, T t, float f, float f2, float f3, float f4, float f5, float f6) {
        boolean bl;
        Minecraft minecraft = Minecraft.getInstance();
        boolean bl2 = bl = minecraft.shouldEntityAppearGlowing((Entity)t) && ((Entity)t).isInvisible();
        if (((Entity)t).isInvisible() && !bl) {
            return;
        }
        if (!t.hasEffect(JerotesVillageMobEffects.SERPON_CREDENTIAL.get()) || t.hasEffect(JerotesVillageMobEffects.SERPON_CREDENTIAL.get()) && Objects.requireNonNull(t.getEffect(JerotesVillageMobEffects.SERPON_CREDENTIAL.get())).getAmplifier() <= 0) {
            return;
        }
        EntityModel<T> model = new Modelserpon_other(this.context.bakeLayer(Modelserpon_other.LAYER_LOCATION));
        this.getParentModel().copyPropertiesTo(model);
        model.prepareMobModel(t, f, f2, f3);
        model.setupAnim(t, f, f2, f4, f5, f6);
        VertexConsumer vertexConsumer = multiBufferSource.getBuffer(RenderType.entityCutoutNoCull(LOCATION));
        ((Model)model).renderToBuffer(poseStack, vertexConsumer, n, LivingEntityRenderer.getOverlayCoords(t, 0.0f), 1.0f, 1.0f, 1.0f, 1.0f);
    }
}