package com.jerotes.jerotesvillage.mixin;

import com.jerotes.jerotesvillage.entity.Animal.IrishtailChickenEntity;
import com.jerotes.jerotesvillage.item.WonderlinFakeEars;
import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import net.minecraft.client.renderer.block.model.BakedQuad;
import net.minecraft.client.renderer.entity.ItemRenderer;
import net.minecraft.world.item.EggItem;
import net.minecraft.world.item.ItemStack;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import java.util.List;

@Mixin(ItemRenderer.class)
public abstract class ItemRendererMixin {

    @Inject(method = "renderQuadList", at = @At("HEAD"), cancellable = true)
    public void renderQuadList(PoseStack poseStack, VertexConsumer vertexConsumer, List<BakedQuad> list, ItemStack itemStack, int n, int n2, CallbackInfo ci) {
        if (itemStack.getItem() instanceof WonderlinFakeEars wonderlinFakeEars) {
            boolean bl = !itemStack.isEmpty();
            PoseStack.Pose pose = poseStack.last();
            for (BakedQuad bakedQuad : list) {
                int n3 = wonderlinFakeEars.getColor(itemStack);
                float f = (float) (n3 >> 16 & 0xFF) / 255.0f;
                float f2 = (float) (n3 >> 8 & 0xFF) / 255.0f;
                float f3 = (float) (n3 & 0xFF) / 255.0f;
                vertexConsumer.putBulkData(pose, bakedQuad, f, f2, f3, n, n2);
            }
            ci.cancel();
        }
        if (itemStack.getItem() instanceof EggItem && IrishtailChickenEntity.isIrishtailChicken(itemStack)) {
            boolean bl = !itemStack.isEmpty();
            PoseStack.Pose pose = poseStack.last();
            for (BakedQuad bakedQuad : list) {
                int n3 = 0xffaa6d;
                float f = (float) (n3 >> 16 & 0xFF) / 255.0f;
                float f2 = (float) (n3 >> 8 & 0xFF) / 255.0f;
                float f3 = (float) (n3 & 0xFF) / 255.0f;
                vertexConsumer.putBulkData(pose, bakedQuad, f, f2, f3, n, n2);
            }
            ci.cancel();
        }
    }
}