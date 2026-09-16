package com.jerotes.jerotesvillage.client.layer;

import com.jerotes.jerotesvillage.JerotesVillage;
import com.jerotes.jerotesvillage.entity.Animal.BigWoolGemsbokEntity;
import com.jerotes.jerotesvillage.entity.Monster.WickedRidiculeCalls.SecondRounderSpiderEntity;
import com.mojang.blaze3d.vertex.PoseStack;
import net.minecraft.client.Minecraft;
import net.minecraft.client.model.SpiderModel;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.entity.RenderLayerParent;
import net.minecraft.client.renderer.entity.layers.RenderLayer;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.item.DyeColor;

public class SpiderRandomColorLayer<T extends SecondRounderSpiderEntity, M extends SpiderModel<T>> extends RenderLayer<T, M> {
   private static final ResourceLocation LOCATION = new ResourceLocation(JerotesVillage.MODID, "textures/entity/second_rounder_spider_color.png");
   private static final ResourceLocation PUSH_LOCATION = new ResourceLocation(JerotesVillage.MODID, "textures/entity/second_rounder_spider_color_push.png");
   private final SpiderModel<T> model;

   public SpiderRandomColorLayer(RenderLayerParent<T, M> renderLayerParent, M m) {
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
      ResourceLocation resourceLocation = LOCATION;
      if (t.getPushTick() > 0) {
         resourceLocation = PUSH_LOCATION;
      }
      int color = t.getColor();
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
         SpiderRandomColorLayer.coloredCutoutModelCopyLayerRender(this.getParentModel(), this.model, resourceLocation, poseStack, multiBufferSource, n, t, f, f2, f4, f5, f6, f3, r, g, b);
      } else {
         float d = 1;
         float r = (float) (color >> 16 & 255) / 255.0F * d;
         float g = (float) (color >> 8 & 255) / 255.0F * d;
         float b = (float) (color & 255) / 255.0F * d;
         float a = (float) (color >> 24 & 255) / 255.0F;
         SpiderRandomColorLayer.coloredCutoutModelCopyLayerRender(this.getParentModel(), this.model, resourceLocation, poseStack, multiBufferSource, n, t, f, f2, f4, f5, f6, f3, r, g, b);
      }
   }
}