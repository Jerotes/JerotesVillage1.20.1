package com.jerotes.jerotesvillage.client.layer;

import com.jerotes.jerotesvillage.JerotesVillage;
import com.jerotes.jerotesvillage.client.model.Modelchromasnap_bass;
import com.jerotes.jerotesvillage.entity.Animal.BigWoolGemsbokEntity;
import com.jerotes.jerotesvillage.entity.Animal.Fish.BenthicFish.ChromasnapBassEntity;
import com.mojang.blaze3d.vertex.PoseStack;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.entity.RenderLayerParent;
import net.minecraft.client.renderer.entity.layers.RenderLayer;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.item.DyeColor;

public class ChromasnapBassRandomColorLayer<T extends ChromasnapBassEntity, M extends Modelchromasnap_bass<T>> extends RenderLayer<T, M> {
   private static final ResourceLocation LOCATION_1 = new ResourceLocation(JerotesVillage.MODID, "textures/entity/fish/chromasnap_bass_color_1.png");
   private static final ResourceLocation LOCATION_2 = new ResourceLocation(JerotesVillage.MODID, "textures/entity/fish/chromasnap_bass_color_2.png");
   private static final ResourceLocation LOCATION_3 = new ResourceLocation(JerotesVillage.MODID, "textures/entity/fish/chromasnap_bass_color_3.png");
   private static final ResourceLocation LOCATION_4 = new ResourceLocation(JerotesVillage.MODID, "textures/entity/fish/chromasnap_bass_color_4.png");
   private final Modelchromasnap_bass<T> model;

   public ChromasnapBassRandomColorLayer(RenderLayerParent<T, M> renderLayerParent, M m) {
      super(renderLayerParent);
      this.model = m;
   }

   @Override
   public void render(PoseStack poseStack, MultiBufferSource multiBufferSource, int n, T t, float f, float f2, float f3, float f4, float f5, float f6) {
      boolean bl;
      Minecraft minecraft = Minecraft.getInstance();
      boolean bl2 = bl = minecraft.shouldEntityAppearGlowing((Entity) t) && ((Entity) t).isInvisible();
      if (t.isInvisible() && !bl) {
         return;
      }
      ResourceLocation resourceLocation = LOCATION_1;
      if (t.hasCustomName() && "jeb_".equals(t.getName().getString())) {
         int n2 = 25;
         int n3 = t.tickCount / 25 + t.getId();
         int n4 = DyeColor.values().length;
         int n5 = n3 % n4;
         int n6 = (n3 + 1) % n4;
         float f10 = ((float) (t.tickCount % 25) + f3) / 25.0f;
         float[] arrf = BigWoolGemsbokEntity.getColorArray(DyeColor.byId(n5));
         float[] arrf2 = BigWoolGemsbokEntity.getColorArray(DyeColor.byId(n6));
         float r = arrf[0] * (1.0f - f10) + arrf2[0] * f10;
         float g = arrf[1] * (1.0f - f10) + arrf2[1] * f10;
         float b = arrf[2] * (1.0f - f10) + arrf2[2] * f10;
         ChromasnapBassRandomColorLayer.coloredCutoutModelCopyLayerRender(this.getParentModel(), this.model, LOCATION_1, poseStack, multiBufferSource, n, t, f, f2, f4, f5, f6, f3, r, g, b);
         ChromasnapBassRandomColorLayer.coloredCutoutModelCopyLayerRender(this.getParentModel(), this.model, LOCATION_2, poseStack, multiBufferSource, n, t, f, f2, f4, f5, f6, f3, r, g, b);
         ChromasnapBassRandomColorLayer.coloredCutoutModelCopyLayerRender(this.getParentModel(), this.model, LOCATION_3, poseStack, multiBufferSource, n, t, f, f2, f4, f5, f6, f3, r, g, b);
         ChromasnapBassRandomColorLayer.coloredCutoutModelCopyLayerRender(this.getParentModel(), this.model, LOCATION_4, poseStack, multiBufferSource, n, t, f, f2, f4, f5, f6, f3, r, g, b);
      }
      else {
         {
            int color = t.getColor1();
            float d = 1;
            float r = (float) (color >> 16 & 255) / 255.0F * d;
            float g = (float) (color >> 8 & 255) / 255.0F * d;
            float b = (float) (color & 255) / 255.0F * d;
            float a = (float) (color >> 24 & 255) / 255.0F;
            ChromasnapBassRandomColorLayer.coloredCutoutModelCopyLayerRender(this.getParentModel(), this.model, LOCATION_1, poseStack, multiBufferSource, n, t, f, f2, f4, f5, f6, f3, r, g, b);
         }
         {
            int color = t.getColor2();
            float d = 1;
            float r = (float) (color >> 16 & 255) / 255.0F * d;
            float g = (float) (color >> 8 & 255) / 255.0F * d;
            float b = (float) (color & 255) / 255.0F * d;
            float a = (float) (color >> 24 & 255) / 255.0F;
            ChromasnapBassRandomColorLayer.coloredCutoutModelCopyLayerRender(this.getParentModel(), this.model, LOCATION_2, poseStack, multiBufferSource, n, t, f, f2, f4, f5, f6, f3, r, g, b);
         }
         {
            int color = t.getColor3();
            float d = 1;
            float r = (float) (color >> 16 & 255) / 255.0F * d;
            float g = (float) (color >> 8 & 255) / 255.0F * d;
            float b = (float) (color & 255) / 255.0F * d;
            float a = (float) (color >> 24 & 255) / 255.0F;
            ChromasnapBassRandomColorLayer.coloredCutoutModelCopyLayerRender(this.getParentModel(), this.model, LOCATION_3, poseStack, multiBufferSource, n, t, f, f2, f4, f5, f6, f3, r, g, b);
         }
         {
            int color = t.getColor4();
            float d = 1;
            float r = (float) (color >> 16 & 255) / 255.0F * d;
            float g = (float) (color >> 8 & 255) / 255.0F * d;
            float b = (float) (color & 255) / 255.0F * d;
            float a = (float) (color >> 24 & 255) / 255.0F;
            ChromasnapBassRandomColorLayer.coloredCutoutModelCopyLayerRender(this.getParentModel(), this.model, LOCATION_4, poseStack, multiBufferSource, n, t, f, f2, f4, f5, f6, f3, r, g, b);
         }
      }
   }
}