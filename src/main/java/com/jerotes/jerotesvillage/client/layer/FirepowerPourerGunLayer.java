package com.jerotes.jerotesvillage.client.layer;

import com.jerotes.jerotesvillage.JerotesVillage;
import com.jerotes.jerotesvillage.entity.Monster.IllagerFaction.FirepowerPourerEntity;
import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import net.minecraft.ChatFormatting;
import net.minecraft.client.Minecraft;
import net.minecraft.client.model.EntityModel;
import net.minecraft.client.model.Model;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.entity.RenderLayerParent;
import net.minecraft.client.renderer.entity.layers.RenderLayer;
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.minecraft.resources.ResourceLocation;

public class FirepowerPourerGunLayer<T extends FirepowerPourerEntity, M extends EntityModel<T>> extends RenderLayer<T, M> {
    private static final ResourceLocation GUN_LOCATION = new ResourceLocation(JerotesVillage.MODID, "textures/entity/firepower_pourer_gun.png");
    private static final ResourceLocation HELL_MARCHER_GUN_LOCATION = new ResourceLocation(JerotesVillage.MODID, "textures/entity/firepower_pourer_hell_marcher_gun.png");
    private final M model;

    public FirepowerPourerGunLayer(RenderLayerParent<T, M> renderLayerParent, M m) {
        super(renderLayerParent);
        this.model = m;
    }

    @Override
    public void render(PoseStack poseStack, MultiBufferSource multiBufferSource, int n, T t, float f, float f2, float f3, float f4, float f5, float f6) {
        boolean bl;
        Minecraft minecraft = Minecraft.getInstance();
        boolean bl2 = bl = minecraft.shouldEntityAppearGlowing(t) && (t).isInvisible();
        if ((t).isInvisible() && !bl) {
            return;
        }
        if (t.getGunHealth() <= 0) {
            return;
        }
        ResourceLocation resourceLocation = GUN_LOCATION;
        String string = ChatFormatting.stripFormatting(t.getName().getString());
        if ("Hell Marcher".equals(string) || "HellMarcher".equals(string) || "Hellmarcher".equals(string) || "地狱行军者".equals(string)) {
            resourceLocation = HELL_MARCHER_GUN_LOCATION;
        }
        this.getParentModel().copyPropertiesTo(this.model);
        this.model.prepareMobModel(t, f, f2, f3);
        this.model.setupAnim(t, f, f2, f4, f5, f6);
        VertexConsumer vertexConsumer = multiBufferSource.getBuffer(RenderType.entityCutoutNoCull(resourceLocation));
        ((Model)this.model).renderToBuffer(poseStack, vertexConsumer, n, OverlayTexture.NO_OVERLAY, 1.0f, 1.0f, 1.0f, 1.0f);
    }
}

