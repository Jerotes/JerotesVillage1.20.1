package com.jerotes.jerotesvillage.mixin;

import com.jerotes.jerotesvillage.entity.Humanoid.WildernessGiant.WildernessGiantEntity;
import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import com.mojang.math.Axis;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.entity.ArrowRenderer;
import net.minecraft.client.renderer.entity.EntityRenderer;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.util.Mth;
import net.minecraft.world.entity.projectile.AbstractArrow;
import org.joml.Matrix3f;
import org.joml.Matrix4f;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(ArrowRenderer.class)
public abstract class ArrowRendererMixin<T extends AbstractArrow> extends EntityRenderer<T> {

    @Shadow public abstract void vertex(Matrix4f p_254392_, Matrix3f p_254011_, VertexConsumer p_253902_, int p_254058_, int p_254338_, int p_254196_, float p_254003_, float p_254165_, int p_253982_, int p_254037_, int p_254038_, int p_254271_);

    protected ArrowRendererMixin(EntityRendererProvider.Context p_174008_) {
        super(p_174008_);
    }

    @Inject(method = "render(Lnet/minecraft/world/entity/projectile/AbstractArrow;FFLcom/mojang/blaze3d/vertex/PoseStack;Lnet/minecraft/client/renderer/MultiBufferSource;I)V", at = @At("HEAD"), cancellable = true)
    public void getTextureLocation(T t, float p_113840_, float p_113841_, PoseStack p_113842_, MultiBufferSource p_113843_, int p_113844_, CallbackInfo ci) {
        if (t.getOwner() instanceof WildernessGiantEntity) {
            p_113842_.pushPose();
            p_113842_.mulPose(Axis.YP.rotationDegrees(Mth.lerp(p_113841_, t.yRotO, t.getYRot()) - 90.0F));
            p_113842_.mulPose(Axis.ZP.rotationDegrees(Mth.lerp(p_113841_, t.xRotO, t.getXRot())));
            float $$7 = 0.0F;
            float $$8 = 0.5F;
            float $$9 = 0.0F;
            float $$10 = 0.15625F;
            float $$11 = 0.0F;
            float $$12 = 0.15625F;
            float $$13 = 0.15625F;
            float $$14 = 0.3125F;
            float $$15 = 0.05625F;
            float $$16 = (float)t.shakeTime - p_113841_;
            if ($$16 > 0.0F) {
                float $$17 = -Mth.sin($$16 * 3.0F) * $$16;
                p_113842_.mulPose(Axis.ZP.rotationDegrees($$17));
            }

            p_113842_.mulPose(Axis.XP.rotationDegrees(45.0F));
            p_113842_.scale(0.1F, 0.1F, 0.1F);
            p_113842_.translate(-4.0F, 0.0F, 0.0F);
            VertexConsumer $$18 = p_113843_.getBuffer(RenderType.entityCutout(this.getTextureLocation(t)));
            PoseStack.Pose $$19 = p_113842_.last();
            Matrix4f $$20 = $$19.pose();
            Matrix3f $$21 = $$19.normal();
            this.vertex($$20, $$21, $$18, -7, -2, -2, 0.0F, 0.15625F, -1, 0, 0, p_113844_);
            this.vertex($$20, $$21, $$18, -7, -2, 2, 0.15625F, 0.15625F, -1, 0, 0, p_113844_);
            this.vertex($$20, $$21, $$18, -7, 2, 2, 0.15625F, 0.3125F, -1, 0, 0, p_113844_);
            this.vertex($$20, $$21, $$18, -7, 2, -2, 0.0F, 0.3125F, -1, 0, 0, p_113844_);
            this.vertex($$20, $$21, $$18, -7, 2, -2, 0.0F, 0.15625F, 1, 0, 0, p_113844_);
            this.vertex($$20, $$21, $$18, -7, 2, 2, 0.15625F, 0.15625F, 1, 0, 0, p_113844_);
            this.vertex($$20, $$21, $$18, -7, -2, 2, 0.15625F, 0.3125F, 1, 0, 0, p_113844_);
            this.vertex($$20, $$21, $$18, -7, -2, -2, 0.0F, 0.3125F, 1, 0, 0, p_113844_);

            for(int $$22 = 0; $$22 < 4; ++$$22) {
                p_113842_.mulPose(Axis.XP.rotationDegrees(90.0F));
                this.vertex($$20, $$21, $$18, -8, -2, 0, 0.0F, 0.0F, 0, 1, 0, p_113844_);
                this.vertex($$20, $$21, $$18, 8, -2, 0, 0.5F, 0.0F, 0, 1, 0, p_113844_);
                this.vertex($$20, $$21, $$18, 8, 2, 0, 0.5F, 0.15625F, 0, 1, 0, p_113844_);
                this.vertex($$20, $$21, $$18, -8, 2, 0, 0.0F, 0.15625F, 0, 1, 0, p_113844_);
            }

            p_113842_.popPose();
            super.render(t, p_113840_, p_113841_, p_113842_, p_113843_, p_113844_);
            ci.cancel();
        }
    }
}