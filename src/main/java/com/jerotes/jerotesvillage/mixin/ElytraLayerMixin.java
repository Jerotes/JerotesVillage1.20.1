package com.jerotes.jerotesvillage.mixin;

import com.jerotes.jerotesvillage.entity.Humanoid.ChibaoEntity;
import com.jerotes.jerotesvillage.entity.Neutral.Wonderlin.WonderlinEntity;
import com.mojang.blaze3d.vertex.PoseStack;
import net.minecraft.client.model.EntityModel;
import net.minecraft.client.model.geom.EntityModelSet;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.entity.RenderLayerParent;
import net.minecraft.client.renderer.entity.layers.ElytraLayer;
import net.minecraft.client.renderer.entity.layers.RenderLayer;
import net.minecraft.world.entity.LivingEntity;
import org.jetbrains.annotations.NotNull;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(ElytraLayer.class)
public abstract class ElytraLayerMixin<T extends LivingEntity, M extends EntityModel<T>> extends RenderLayer<T, M> {
    public ElytraLayerMixin(RenderLayerParent<T, M> renderLayerParent, @NotNull EntityModelSet entityModelSet) {
        super(renderLayerParent);
    }

    @Inject(method = "render*", at = @At(value = "INVOKE", target = "Lcom/mojang/blaze3d/vertex/PoseStack;translate(FFF)V"), cancellable = true)
    public void render(PoseStack poseStack, MultiBufferSource multiBufferSource, int n, T t, float f, float f2, float f3, float f4, float f5, float f6, CallbackInfo callbackInfo) {
        if (t instanceof ChibaoEntity chibao) {
            poseStack.translate(0.0F, -0.55F, 0.25F);
            if (chibao.isBaby()) {
                poseStack.translate(0.0F, 0.325F, -0.125F);
            }
        }
        if (t instanceof WonderlinEntity wonderlin) {
            poseStack.translate(0.0F, 0.6F, -0.125F);
        }
    }
}