package com.jerotes.jerotesvillage.client.layer;

import com.jerotes.jerotesvillage.JerotesVillage;
import com.jerotes.jerotesvillage.entity.Animal.BigWoolGemsbokEntity;
import com.jerotes.jerotesvillage.entity.Monster.PurpleSandCreeperEntity;
import com.jerotes.jerotesvillage.init.JerotesVillageBiomes;
import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import net.minecraft.client.Minecraft;
import net.minecraft.client.model.CreeperModel;
import net.minecraft.client.model.EntityModel;
import net.minecraft.client.renderer.BiomeColors;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.entity.RenderLayerParent;
import net.minecraft.client.renderer.entity.layers.RenderLayer;
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.minecraft.core.BlockPos;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.Mth;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.DyeColor;
import net.minecraft.world.level.BlockAndTintGetter;

public class PurpleSandCreeperColorLayer<T extends PurpleSandCreeperEntity, M extends CreeperModel<T>> extends RenderLayer<T, M> {
   private static final ResourceLocation LOCATION = new ResourceLocation(JerotesVillage.MODID, "textures/entity/purple_sand_creeper_color.png");
   private final CreeperModel<T> model;

   public PurpleSandCreeperColorLayer(RenderLayerParent<T, M> renderLayerParent, M m) {
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
      BlockAndTintGetter blockAndTintGetter = t.level();
      if (t.level().getBiome(BlockPos.containing(t.getX(), t.getY(), t.getZ())).is(JerotesVillageBiomes.PURPLE_DESERT)) {
         return;
      }
      int color = BiomeColors.getAverageFoliageColor(blockAndTintGetter, t.getOnPos());
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
         PurpleSandCreeperColorLayer.coloredCutoutModelCopyLayerRender(this.getParentModel(), this.model, resourceLocation, poseStack, multiBufferSource, n, t, f, f2, f4, f5, f6, f3, r, g, b);
      } else {
         float d = 1;
         float r = (float) (color >> 16 & 255) / 255.0F * d;
         float g = (float) (color >> 8 & 255) / 255.0F * d;
         float b = (float) (color & 255) / 255.0F * d;
         float a = (float) (color >> 24 & 255) / 255.0F;
         PurpleSandCreeperColorLayer.coloredCutoutModelCopyLayerRender(this.getParentModel(), this.model, resourceLocation, poseStack, multiBufferSource, n, t, f, f2, f4, f5, f6, f3, r, g, b);
      }
   }


   protected static <T extends LivingEntity> void coloredCutoutModelCopyLayerRender(EntityModel<T> p_117360_, EntityModel<T> p_117361_, ResourceLocation p_117362_, PoseStack p_117363_, MultiBufferSource p_117364_, int p_117365_, T p_117366_, float p_117367_, float p_117368_, float p_117369_, float p_117370_, float p_117371_, float p_117372_, float p_117373_, float p_117374_, float p_117375_) {
      if (!p_117366_.isInvisible()) {
         p_117360_.copyPropertiesTo(p_117361_);
         p_117361_.prepareMobModel(p_117366_, p_117367_, p_117368_, p_117372_);
         p_117361_.setupAnim(p_117366_, p_117367_, p_117368_, p_117369_, p_117370_, p_117371_);
         renderColoredCutoutModel(p_117361_, p_117362_, p_117363_, p_117364_, p_117365_, p_117366_, p_117373_, p_117374_, p_117375_, p_117372_);
      }

   }

   protected static <T extends LivingEntity> void renderColoredCutoutModel(EntityModel<T> p_117377_, ResourceLocation p_117378_, PoseStack p_117379_, MultiBufferSource p_117380_, int p_117381_, T p_117382_, float p_117383_, float p_117384_, float p_117385_, float p_117386_) {
      VertexConsumer $$9 = p_117380_.getBuffer(RenderType.entityCutoutNoCull(p_117378_));
      int i = getOverlayCoords(p_117382_, getWhiteOverlayProgress((PurpleSandCreeperEntity) p_117382_, p_117386_));
      p_117377_.renderToBuffer(p_117379_, $$9, p_117381_, i, p_117383_, p_117384_, p_117385_, 1.0F);
   }

   public static int getOverlayCoords(LivingEntity p_115339_, float p_115340_) {
      return OverlayTexture.pack(OverlayTexture.u(p_115340_), OverlayTexture.v(p_115339_.hurtTime > 0 || p_115339_.deathTime > 0));
   }

   protected static float getWhiteOverlayProgress(PurpleSandCreeperEntity creeper, float f) {
      float f2 = creeper.getSwelling(f);
      if ((int)(f2 * 10.0f) % 2 == 0) {
         return 0.0f;
      }
      return Mth.clamp(f2, 0.5f, 1.0f);
   }
}