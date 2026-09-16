package com.jerotes.jerotesvillage.client.layer;

import com.jerotes.jerotesvillage.client.model.Modelillager;
import com.jerotes.jerotesvillage.entity.Monster.IllagerFaction.TeleporterEntity;
import com.jerotes.jerotesvillage.init.JerotesVillageItems;
import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.math.Axis;
import net.minecraft.client.renderer.ItemInHandRenderer;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.entity.RenderLayerParent;
import net.minecraft.client.renderer.entity.layers.RenderLayer;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.ItemDisplayContext;
import net.minecraft.world.item.ItemStack;

public class TeleportStoneLayer<T extends TeleporterEntity, M extends Modelillager<T>> extends RenderLayer<T, M> {
    private final ItemInHandRenderer itemInHandRenderer;

    public TeleportStoneLayer(RenderLayerParent<T, M> renderLayerParent, ItemInHandRenderer itemInHandRenderer) {
        super(renderLayerParent);
        this.itemInHandRenderer = itemInHandRenderer;
    }

    @Override
    public void render(PoseStack poseStack, MultiBufferSource multiBufferSource, int n, T t, float f, float f2, float f3, float f4, float f5, float f6) {
        ItemStack itemStack = new ItemStack(JerotesVillageItems.SECOND_ROUND_WORLD_TELEPORT_STONE.get());
        if (!t.isCastingSpell()) {
            return;
        }
        poseStack.pushPose();
        poseStack.mulPose(Axis.XP.rotationDegrees(180.0f));
        poseStack.translate(0.0f, 1.0f, -0.0f);
        this.itemInHandRenderer.renderItem((LivingEntity)t, itemStack, ItemDisplayContext.GROUND, false, poseStack, multiBufferSource, n);
        poseStack.popPose();
    }
}

