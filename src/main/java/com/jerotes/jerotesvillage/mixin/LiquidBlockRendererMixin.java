package com.jerotes.jerotesvillage.mixin;

import com.jerotes.jerotesvillage.config.OtherMainConfig;
import com.jerotes.jerotesvillage.event.WorldEvent;
import com.jerotes.jerotesvillage.init.JerotesVillageMobEffects;
import com.mojang.blaze3d.vertex.VertexConsumer;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.LevelRenderer;
import net.minecraft.client.renderer.block.LiquidBlockRenderer;
import net.minecraft.client.renderer.texture.TextureAtlasSprite;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.tags.FluidTags;
import net.minecraft.util.Mth;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.BlockAndTintGetter;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.material.Fluid;
import net.minecraft.world.level.material.FluidState;
import net.minecraft.world.phys.Vec3;
import net.minecraft.world.phys.shapes.Shapes;
import net.minecraft.world.phys.shapes.VoxelShape;
import net.minecraftforge.client.ForgeHooksClient;
import org.jetbrains.annotations.NotNull;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(LiquidBlockRenderer.class)
public abstract class LiquidBlockRendererMixin {
    @Shadow
    private TextureAtlasSprite waterOverlay;

    @Unique
    private int jerotes_$getLightColor(BlockAndTintGetter p_110946_, BlockPos p_110947_) {
        int i = LevelRenderer.getLightColor(p_110946_, p_110947_);
        int j = LevelRenderer.getLightColor(p_110946_, p_110947_.above());
        int k = i & 255;
        int l = j & 255;
        int i1 = i >> 16 & 255;
        int j1 = j >> 16 & 255;
        return (Math.max(k, l)) | (Math.max(i1, j1)) << 16;
    }

    @Unique
    private void jerotes_$vertex(@NotNull VertexConsumer p_110985_, double p_110986_, double p_110987_, double p_110988_, float p_110989_, float p_110990_, float p_110991_, float alpha, float p_110992_, float p_110993_, int p_110994_) {
        p_110985_.vertex(p_110986_, p_110987_, p_110988_).color(p_110989_, p_110990_, p_110991_, alpha).uv(p_110992_, p_110993_).uv2(p_110994_).normal(0.0F, 1.0F, 0.0F).endVertex();
    }


    @Unique
    private float jerotes_$getHeight(BlockAndTintGetter p_203161_, Fluid p_203162_, BlockPos p_203163_, BlockState p_203164_, FluidState p_203165_) {
        if (p_203162_.isSame(p_203165_.getType())) {
            BlockState blockstate = p_203161_.getBlockState(p_203163_.above());
            return p_203162_.isSame(blockstate.getFluidState().getType()) ? 1.0F : p_203165_.getOwnHeight();
        } else {
            return !p_203164_.isSolid() ? 0.0F : -1.0F;
        }
    }

    @Unique
    private float jerotes_$calculateAverageHeight(BlockAndTintGetter p_203150_, Fluid p_203151_, float p_203152_, float p_203153_, float p_203154_, BlockPos p_203155_) {
        if (!(p_203154_ >= 1.0F) && !(p_203153_ >= 1.0F)) {
            float[] afloat = new float[2];
            if (p_203154_ > 0.0F || p_203153_ > 0.0F) {
                float f = this.jerotes_$getHeight(p_203150_, p_203151_, p_203155_);
                if (f >= 1.0F) {
                    return 1.0F;
                }

                this.jerotes_$addWeightedHeight(afloat, f);
            }

            this.jerotes_$addWeightedHeight(afloat, p_203152_);
            this.jerotes_$addWeightedHeight(afloat, p_203154_);
            this.jerotes_$addWeightedHeight(afloat, p_203153_);
            return afloat[0] / afloat[1];
        } else {
            return 1.0F;
        }
    }

    @Unique
    private void jerotes_$addWeightedHeight(float[] p_203189_, float p_203190_) {
        if (p_203190_ >= 0.8F) {
            p_203189_[0] += p_203190_ * 10.0F;
            p_203189_[1] += 10.0F;
        } else if (p_203190_ >= 0.0F) {
            p_203189_[0] += p_203190_;
            p_203189_[1]++;
        }

    }

    @Unique
    private float jerotes_$getHeight(BlockAndTintGetter p_203157_, Fluid p_203158_, BlockPos p_203159_) {
        BlockState blockstate = p_203157_.getBlockState(p_203159_);
        return this.jerotes_$getHeight(p_203157_, p_203158_, p_203159_, blockstate, blockstate.getFluidState());
    }

    @Unique
    private static boolean jerotes_$isNeighborSameFluid(FluidState p_203186_, FluidState p_203187_) {
        return p_203187_.getType().isSame(p_203186_.getType());
    }

    @Unique
    private static boolean jerotes_$isFaceOccludedByState(BlockGetter p_110979_, Direction p_110980_, float p_110981_, BlockPos p_110982_, BlockState p_110983_) {
        if (p_110983_.canOcclude()) {
            VoxelShape voxelshape = Shapes.box(0.0, 0.0, 0.0, 1.0, p_110981_, 1.0);
            VoxelShape voxelshape1 = p_110983_.getOcclusionShape(p_110979_, p_110982_);
            return Shapes.blockOccudes(voxelshape, voxelshape1, p_110980_);
        } else {
            return false;
        }
    }

    @Unique
    private static boolean jerotes_$isFaceOccludedByNeighbor(BlockGetter p_203180_, BlockPos p_203181_, Direction p_203182_, float p_203183_, BlockState p_203184_) {
        return !jerotes_$isFaceOccludedByState(p_203180_, p_203182_, p_203183_, p_203181_.relative(p_203182_), p_203184_);
    }


    @Inject(method = "tesselate", at = @At("HEAD"), cancellable = true)
    public void tesselate(BlockAndTintGetter p_234370_, BlockPos p_234371_, VertexConsumer p_234372_, BlockState p_234373_, FluidState p_234374_, CallbackInfo ci) {
        Minecraft minecraft = Minecraft.getInstance();
        Player player = minecraft.player;
        if (player != null && minecraft.level != null) {
            boolean wicked = OtherMainConfig.WickedRidiculeNightAboutDimension.contains(minecraft.level.dimension().location().toString()) && player.hasEffect(JerotesVillageMobEffects.WICKED_RIDICULE_NIGHT.get()) && OtherMainConfig.WickedRidiculeNightLiquidColor;
            boolean zsiein = WorldEvent.ZsieinExtremelyInfectedAbove(minecraft.level);
            if (wicked || zsiein) {
                p_234374_.is(FluidTags.LAVA);
                TextureAtlasSprite[] atextureatlassprite = ForgeHooksClient.getFluidSprites(p_234370_, p_234371_, p_234374_);
                int i = OtherMainConfig.WickedRidiculeNightLiquidColorChoose;
                if (zsiein) {
                    i = 0x534ba1;
                }
                float alpha = 1.0F;
                float f = (float) (i >> 16 & 255) / 255.0F;
                float f1 = (float) (i >> 8 & 255) / 255.0F;
                float f2 = (float) (i & 255) / 255.0F;
                BlockState blockstate = p_234370_.getBlockState(p_234371_.relative(Direction.DOWN));
                FluidState fluidstate = blockstate.getFluidState();
                BlockState blockstate1 = p_234370_.getBlockState(p_234371_.relative(Direction.UP));
                FluidState fluidstate1 = blockstate1.getFluidState();
                BlockState blockstate2 = p_234370_.getBlockState(p_234371_.relative(Direction.NORTH));
                FluidState fluidstate2 = blockstate2.getFluidState();
                BlockState blockstate3 = p_234370_.getBlockState(p_234371_.relative(Direction.SOUTH));
                FluidState fluidstate3 = blockstate3.getFluidState();
                BlockState blockstate4 = p_234370_.getBlockState(p_234371_.relative(Direction.WEST));
                FluidState fluidstate4 = blockstate4.getFluidState();
                BlockState blockstate5 = p_234370_.getBlockState(p_234371_.relative(Direction.EAST));
                FluidState fluidstate5 = blockstate5.getFluidState();
                boolean flag1 = !jerotes_$isNeighborSameFluid(p_234374_, fluidstate1);
                boolean flag2 = LiquidBlockRenderer.shouldRenderFace(p_234370_, p_234371_, p_234374_, p_234373_, Direction.DOWN, fluidstate) && jerotes_$isFaceOccludedByNeighbor(p_234370_, p_234371_, Direction.DOWN, 0.8888889F, blockstate);
                boolean flag3 = LiquidBlockRenderer.shouldRenderFace(p_234370_, p_234371_, p_234374_, p_234373_, Direction.NORTH, fluidstate2);
                boolean flag4 = LiquidBlockRenderer.shouldRenderFace(p_234370_, p_234371_, p_234374_, p_234373_, Direction.SOUTH, fluidstate3);
                boolean flag5 = LiquidBlockRenderer.shouldRenderFace(p_234370_, p_234371_, p_234374_, p_234373_, Direction.WEST, fluidstate4);
                boolean flag6 = LiquidBlockRenderer.shouldRenderFace(p_234370_, p_234371_, p_234374_, p_234373_, Direction.EAST, fluidstate5);
                if (flag1 || flag2 || flag6 || flag5 || flag3 || flag4) {
                    float f3 = p_234370_.getShade(Direction.DOWN, true);
                    float f4 = p_234370_.getShade(Direction.UP, true);
                    float f5 = p_234370_.getShade(Direction.NORTH, true);
                    float f6 = p_234370_.getShade(Direction.WEST, true);
                    Fluid fluid = p_234374_.getType();
                    float f11 = this.jerotes_$getHeight(p_234370_, fluid, p_234371_, p_234373_, p_234374_);
                    float f7;
                    float f8;
                    float f9;
                    float f10;
                    if (f11 >= 1.0F) {
                        f7 = 1.0F;
                        f8 = 1.0F;
                        f9 = 1.0F;
                        f10 = 1.0F;
                    } else {
                        float f12 = this.jerotes_$getHeight(p_234370_, fluid, p_234371_.north(), blockstate2, fluidstate2);
                        float f13 = this.jerotes_$getHeight(p_234370_, fluid, p_234371_.south(), blockstate3, fluidstate3);
                        float f14 = this.jerotes_$getHeight(p_234370_, fluid, p_234371_.east(), blockstate5, fluidstate5);
                        float f15 = this.jerotes_$getHeight(p_234370_, fluid, p_234371_.west(), blockstate4, fluidstate4);
                        f7 = this.jerotes_$calculateAverageHeight(p_234370_, fluid, f11, f12, f14, p_234371_.relative(Direction.NORTH).relative(Direction.EAST));
                        f8 = this.jerotes_$calculateAverageHeight(p_234370_, fluid, f11, f12, f15, p_234371_.relative(Direction.NORTH).relative(Direction.WEST));
                        f9 = this.jerotes_$calculateAverageHeight(p_234370_, fluid, f11, f13, f14, p_234371_.relative(Direction.SOUTH).relative(Direction.EAST));
                        f10 = this.jerotes_$calculateAverageHeight(p_234370_, fluid, f11, f13, f15, p_234371_.relative(Direction.SOUTH).relative(Direction.WEST));
                    }
                    double d1 = p_234371_.getX() & 15;
                    double d2 = p_234371_.getY() & 15;
                    double d0 = p_234371_.getZ() & 15;
                    float f17 = flag2 ? 0.001F : 0.0F;
                    float f18;
                    float f19;
                    float f44;
                    float f45;
                    float f22;
                    float f23;
                    float f24;
                    if (flag1 && jerotes_$isFaceOccludedByNeighbor(p_234370_, p_234371_, Direction.UP, Math.min(Math.min(f8, f10), Math.min(f9, f7)), blockstate1)) {
                        f8 -= 0.001F;
                        f10 -= 0.001F;
                        f9 -= 0.001F;
                        f7 -= 0.001F;
                        Vec3 vec3 = p_234374_.getFlow(p_234370_, p_234371_);
                        float f25;
                        TextureAtlasSprite textureatlassprite;
                        float f50;
                        float f51;
                        float f52;
                        if (vec3.x == 0.0 && vec3.z == 0.0) {
                            textureatlassprite = atextureatlassprite[0];
                            f18 = textureatlassprite.getU(0.0F);
                            f22 = textureatlassprite.getV(0.0F);
                            f19 = f18;
                            f23 = textureatlassprite.getV(1.0F);
                            f44 = textureatlassprite.getU(1.0F);
                            f24 = f23;
                            f45 = f44;
                            f25 = f22;
                        } else {
                            textureatlassprite = atextureatlassprite[1];
                            f50 = (float) Mth.atan2(vec3.z, vec3.x) - 1.5707964F;
                            f51 = Mth.sin(f50) * 0.25F;
                            float f28 = Mth.cos(f50) * 0.25F;
                            f18 = textureatlassprite.getU(0.5F + (-f28 - f51));
                            f22 = textureatlassprite.getV(0.5F - f28 + f51);
                            f19 = textureatlassprite.getU(0.5F - f28 + f51);
                            f23 = textureatlassprite.getV(0.5F + f28 + f51);
                            f44 = textureatlassprite.getU(0.5F + f28 + f51);
                            f24 = textureatlassprite.getV(0.5F + (f28 - f51));
                            f45 = textureatlassprite.getU(0.5F + (f28 - f51));
                            f25 = textureatlassprite.getV(0.5F + (-f28 - f51));
                        }
                        float f49 = (f18 + f19 + f44 + f45) / 4.0F;
                        f50 = (f22 + f23 + f24 + f25) / 4.0F;
                        f51 = atextureatlassprite[0].uvShrinkRatio();
                        f18 = Mth.lerp(f51, f18, f49);
                        f19 = Mth.lerp(f51, f19, f49);
                        f44 = Mth.lerp(f51, f44, f49);
                        f45 = Mth.lerp(f51, f45, f49);
                        f22 = Mth.lerp(f51, f22, f50);
                        f23 = Mth.lerp(f51, f23, f50);
                        f24 = Mth.lerp(f51, f24, f50);
                        f25 = Mth.lerp(f51, f25, f50);
                        int l = this.jerotes_$getLightColor(p_234370_, p_234371_);
                        f52 = f4 * f;
                        float f30 = f4 * f1;
                        float f31 = f4 * f2;
                        this.jerotes_$vertex(p_234372_, d1 + 0.0, d2 + (double) f8, d0 + 0.0, f52, f30, f31, alpha, f18, f22, l);
                        this.jerotes_$vertex(p_234372_, d1 + 0.0, d2 + (double) f10, d0 + 1.0, f52, f30, f31, alpha, f19, f23, l);
                        this.jerotes_$vertex(p_234372_, d1 + 1.0, d2 + (double) f9, d0 + 1.0, f52, f30, f31, alpha, f44, f24, l);
                        this.jerotes_$vertex(p_234372_, d1 + 1.0, d2 + (double) f7, d0 + 0.0, f52, f30, f31, alpha, f45, f25, l);
                        if (p_234374_.shouldRenderBackwardUpFace(p_234370_, p_234371_.above())) {
                            this.jerotes_$vertex(p_234372_, d1 + 0.0, d2 + (double) f8, d0 + 0.0, f52, f30, f31, alpha, f18, f22, l);
                            this.jerotes_$vertex(p_234372_, d1 + 1.0, d2 + (double) f7, d0 + 0.0, f52, f30, f31, alpha, f45, f25, l);
                            this.jerotes_$vertex(p_234372_, d1 + 1.0, d2 + (double) f9, d0 + 1.0, f52, f30, f31, alpha, f44, f24, l);
                            this.jerotes_$vertex(p_234372_, d1 + 0.0, d2 + (double) f10, d0 + 1.0, f52, f30, f31, alpha, f19, f23, l);
                        }
                    }
                    if (flag2) {
                        float f40 = atextureatlassprite[0].getU0();
                        f18 = atextureatlassprite[0].getU1();
                        f19 = atextureatlassprite[0].getV0();
                        f44 = atextureatlassprite[0].getV1();
                        int k = this.jerotes_$getLightColor(p_234370_, p_234371_.below());
                        f22 = f3 * f;
                        f23 = f3 * f1;
                        f24 = f3 * f2;
                        this.jerotes_$vertex(p_234372_, d1, d2 + (double) f17, d0 + 1.0, f22, f23, f24, alpha, f40, f44, k);
                        this.jerotes_$vertex(p_234372_, d1, d2 + (double) f17, d0, f22, f23, f24, alpha, f40, f19, k);
                        this.jerotes_$vertex(p_234372_, d1 + 1.0, d2 + (double) f17, d0, f22, f23, f24, alpha, f18, f19, k);
                        this.jerotes_$vertex(p_234372_, d1 + 1.0, d2 + (double) f17, d0 + 1.0, f22, f23, f24, alpha, f18, f44, k);
                    }

                    int j = this.jerotes_$getLightColor(p_234370_, p_234371_);

                    for (Direction direction : Direction.Plane.HORIZONTAL) {
                        double d3;
                        double d4;
                        double d5;
                        double d6;
                        boolean flag7;
                        switch (direction) {
                            case NORTH:
                                f44 = f8;
                                f45 = f7;
                                d3 = d1;
                                d5 = d1 + 1.0;
                                d4 = d0 + 0.0010000000474974513;
                                d6 = d0 + 0.0010000000474974513;
                                flag7 = flag3;
                                break;
                            case SOUTH:
                                f44 = f9;
                                f45 = f10;
                                d3 = d1 + 1.0;
                                d5 = d1;
                                d4 = d0 + 1.0 - 0.0010000000474974513;
                                d6 = d0 + 1.0 - 0.0010000000474974513;
                                flag7 = flag4;
                                break;
                            case WEST:
                                f44 = f10;
                                f45 = f8;
                                d3 = d1 + 0.0010000000474974513;
                                d5 = d1 + 0.0010000000474974513;
                                d4 = d0 + 1.0;
                                d6 = d0;
                                flag7 = flag5;
                                break;
                            default:
                                f44 = f7;
                                f45 = f9;
                                d3 = d1 + 1.0 - 0.0010000000474974513;
                                d5 = d1 + 1.0 - 0.0010000000474974513;
                                d4 = d0;
                                d6 = d0 + 1.0;
                                flag7 = flag6;
                        }

                        if (flag7 && jerotes_$isFaceOccludedByNeighbor(p_234370_, p_234371_, direction, Math.max(f44, f45), p_234370_.getBlockState(p_234371_.relative(direction)))) {
                            BlockPos blockpos = p_234371_.relative(direction);
                            TextureAtlasSprite textureatlassprite2 = atextureatlassprite[1];
                            if (atextureatlassprite[2] != null && p_234370_.getBlockState(blockpos).shouldDisplayFluidOverlay(p_234370_, blockpos, p_234374_)) {
                                textureatlassprite2 = atextureatlassprite[2];
                            }

                            float f53 = textureatlassprite2.getU(0.0F);
                            float f32 = textureatlassprite2.getU(0.5F);
                            float f33 = textureatlassprite2.getV((1.0F - f44) * 0.5F);
                            float f34 = textureatlassprite2.getV((1.0F - f45) * 0.5F);
                            float f35 = textureatlassprite2.getV(0.5F);
                            float f36 = direction.getAxis() == Direction.Axis.Z ? f5 : f6;
                            float f37 = f4 * f36 * f;
                            float f38 = f4 * f36 * f1;
                            float f39 = f4 * f36 * f2;
                            this.jerotes_$vertex(p_234372_, d3, d2 + (double) f44, d4, f37, f38, f39, alpha, f53, f33, j);
                            this.jerotes_$vertex(p_234372_, d5, d2 + (double) f45, d6, f37, f38, f39, alpha, f32, f34, j);
                            this.jerotes_$vertex(p_234372_, d5, d2 + (double) f17, d6, f37, f38, f39, alpha, f32, f35, j);
                            this.jerotes_$vertex(p_234372_, d3, d2 + (double) f17, d4, f37, f38, f39, alpha, f53, f35, j);
                            if (textureatlassprite2 != this.waterOverlay) {
                                this.jerotes_$vertex(p_234372_, d3, d2 + (double) f17, d4, f37, f38, f39, alpha, f53, f35, j);
                                this.jerotes_$vertex(p_234372_, d5, d2 + (double) f17, d6, f37, f38, f39, alpha, f32, f35, j);
                                this.jerotes_$vertex(p_234372_, d5, d2 + (double) f45, d6, f37, f38, f39, alpha, f32, f34, j);
                                this.jerotes_$vertex(p_234372_, d3, d2 + (double) f44, d4, f37, f38, f39, alpha, f53, f33, j);
                            }
                        }
                    }
                }
                ci.cancel();
            }
        }
    }
}