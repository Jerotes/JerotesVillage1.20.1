package com.jerotes.jerotesvillage.client;

import com.jerotes.jerotesvillage.JerotesVillage;
import com.jerotes.jerotesvillage.init.JerotesVillageMobEffects;
import com.mojang.blaze3d.platform.GlStateManager;
import com.mojang.blaze3d.systems.RenderSystem;
import com.mojang.blaze3d.vertex.*;
import com.mojang.math.Axis;
import net.minecraft.client.Camera;
import net.minecraft.client.Minecraft;
import net.minecraft.client.multiplayer.ClientLevel;
import net.minecraft.client.renderer.*;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.Mth;
import net.minecraft.util.RandomSource;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.phys.Vec3;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import org.joml.Matrix4f;

@OnlyIn(Dist.CLIENT)
public class SkyRenderer {
    private static final ResourceLocation SUN_LOCATION = new ResourceLocation(JerotesVillage.MODID, "textures/environment/second_round_world_sun.png");
    private static final ResourceLocation MOON_LOCATION = new ResourceLocation(JerotesVillage.MODID, "textures/environment/second_round_world_moon.png");
    private static final ResourceLocation RED_MOON_LOCATION = new ResourceLocation(JerotesVillage.MODID, "textures/environment/red_second_round_world_moon.png");
    private static VertexBuffer starBuffer;
    private static VertexBuffer darkBuffer;
    private static VertexBuffer skyBuffer;

    public static boolean renderSky(ClientLevel level, PoseStack stack, Matrix4f matrix, float partialTicks, Camera camera, Runnable runnable) {
        Minecraft minecraft = Minecraft.getInstance();
        LevelRenderer levelRenderer = minecraft.levelRenderer;
        runnable.run();
        Vec3 vec3 = level.getSkyColor(minecraft.gameRenderer.getMainCamera().getPosition(), partialTicks);
        float f = (float)vec3.x;
        float f1 = (float)vec3.y;
        float f2 = (float)vec3.z;
        FogRenderer.levelFogColor();
        BufferBuilder bufferbuilder = Tesselator.getInstance().getBuilder();
        RenderSystem.depthMask(false);
        RenderSystem.setShaderColor(f, f1, f2, 1.0F);
        ShaderInstance shaderinstance = RenderSystem.getShader();
        //skyBuffer.bind();
        //skyBuffer.drawWithShader(stack.last().pose(), matrix, shaderinstance);
        VertexBuffer.unbind();
        RenderSystem.enableBlend();
        float[] arrf = level.effects().getSunriseColor(level.getTimeOfDay(f), f);
        if (arrf != null) {
            RenderSystem.setShader(GameRenderer::getPositionColorShader);
            RenderSystem.setShaderColor(1.0f, 1.0f, 1.0f, 1.0f);
            stack.pushPose();
            stack.mulPose(Axis.XP.rotationDegrees(90.0F));
            float f3 = Mth.sin(level.getSunAngle(partialTicks)) < 0.0F ? 180.0F : 0.0F;
            stack.mulPose(Axis.ZP.rotationDegrees(f3));
            stack.mulPose(Axis.ZP.rotationDegrees(90.0F));
            float f4 = arrf[0];
            float f5 = arrf[1];
            float f6 = arrf[2];
            Matrix4f matrix4f = stack.last().pose();
            bufferbuilder.begin(VertexFormat.Mode.TRIANGLE_FAN, DefaultVertexFormat.POSITION_COLOR);
            bufferbuilder.vertex(matrix4f, 0.0F, 100.0F, 0.0F).color(f4, f5, f6, arrf[3]).endVertex();
            for(int j = 0; j <= 16; ++j) {
                float f7 = (float)j * 6.2831855f / 16.0F;
                float f8 = Mth.sin(f7);
                float f9 = Mth.cos(f7);
                bufferbuilder.vertex(matrix4f, f8 * 120.0F, f9 * 120.0F, -f9 * 40.0F * arrf[3]).color(arrf[0], arrf[1], arrf[2], 0.0F).endVertex();
            }
            BufferUploader.drawWithShader(bufferbuilder.end());
            stack.popPose();
        }

        RenderSystem.blendFuncSeparate(GlStateManager.SourceFactor.SRC_ALPHA, GlStateManager.DestFactor.ONE, GlStateManager.SourceFactor.ONE, GlStateManager.DestFactor.ZERO);
        stack.pushPose();
        float f10 = 1.0F - level.getRainLevel(partialTicks);
        RenderSystem.setShaderColor(1.0F, 1.0F, 1.0F, f10);
        stack.mulPose(Axis.YP.rotationDegrees(-90.0F));
        stack.mulPose(Axis.XP.rotationDegrees(level.getTimeOfDay(f) * 360.0f));
        Matrix4f matrix4f1 = stack.last().pose();
        float f11 = 30.0F;
        RenderSystem.setShader(GameRenderer::getPositionTexShader);
        RenderSystem.setShaderTexture(0, SUN_LOCATION);
        bufferbuilder.begin(VertexFormat.Mode.QUADS, DefaultVertexFormat.POSITION_TEX);
        bufferbuilder.vertex(matrix4f1, -f11, 100.0F, -f11).uv(0.0F, 0.0F).endVertex();
        bufferbuilder.vertex(matrix4f1, f11, 100.0F, -f11).uv(1.0F, 0.0F).endVertex();
        bufferbuilder.vertex(matrix4f1, f11, 100.0F, f11).uv(1.0F, 1.0F).endVertex();
        bufferbuilder.vertex(matrix4f1, -f11, 100.0F, f11).uv(0.0F, 1.0F).endVertex();
        BufferUploader.drawWithShader(bufferbuilder.end());
        f11 = 20.0f;
        if (camera.getEntity() instanceof LivingEntity livingEntity && livingEntity.hasEffect(JerotesVillageMobEffects.WICKED_RIDICULE_NIGHT.get())) {
            RenderSystem.setShaderTexture(0, RED_MOON_LOCATION);
        }
        else {
            RenderSystem.setShaderTexture(0, MOON_LOCATION);
        }
        int n2 = level.getMoonPhase();
        int n3 = n2 % 4;
        int n = n2 / 4 % 2;
        float f12 = (float)(n3 + 0) / 4.0f;
        float f14 = (float)(n + 0) / 2.0f;
        float f15 = (float)(n3 + 1) / 4.0f;
        float f16 = (float)(n + 1) / 2.0f;
        bufferbuilder.begin(VertexFormat.Mode.QUADS, DefaultVertexFormat.POSITION_TEX);
        bufferbuilder.vertex(matrix4f1, -f11, -100.0f, f11).uv(f15, f16).endVertex();
        bufferbuilder.vertex(matrix4f1, f11, -100.0f, f11).uv(f12, f16).endVertex();
        bufferbuilder.vertex(matrix4f1, f11, -100.0f, -f11).uv(f12, f14).endVertex();
        bufferbuilder.vertex(matrix4f1, -f11, -100.0f, -f11).uv(f15, f14).endVertex();
        BufferUploader.drawWithShader(bufferbuilder.end());
        float f13 = level.getStarBrightness(f) * f2;
        if (f13 > 0.0f) {
            RenderSystem.setShaderColor(f13, f13, f13, f13);
            FogRenderer.setupNoFog();
            starBuffer.bind();
            starBuffer.drawWithShader(stack.last().pose(), matrix, GameRenderer.getPositionShader());
            VertexBuffer.unbind();
            runnable.run();
        }
        RenderSystem.setShaderColor(1.0f, 1.0f, 1.0f, 1.0f);
        RenderSystem.disableBlend();
        RenderSystem.defaultBlendFunc();
        stack.popPose();
        RenderSystem.setShaderColor(0.0F, 0.0F, 0.0F, 1.0F);
        double d0 = minecraft.player.getEyePosition(partialTicks).y - level.getLevelData().getHorizonHeight(level);
        if (d0 < 0.0D) {
            stack.pushPose();
            stack.translate(0.0F, 12.0F, 0.0F);
            darkBuffer.bind();
            darkBuffer.drawWithShader(stack.last().pose(), matrix, shaderinstance);
            VertexBuffer.unbind();
            stack.popPose();
        }

        RenderSystem.setShaderColor(1.0F, 1.0F, 1.0F, 1.0F);
        RenderSystem.depthMask(true);
        return true;
    }

    public static void createStars() {
        Tesselator tesselator = Tesselator.getInstance();
        BufferBuilder bufferbuilder = tesselator.getBuilder();
        RenderSystem.setShader(GameRenderer::getPositionShader);
        if (starBuffer != null) {
            starBuffer.close();
        }

        starBuffer = new VertexBuffer(VertexBuffer.Usage.STATIC);
        BufferBuilder.RenderedBuffer bufferbuilder$renderedbuffer = drawStars(bufferbuilder);
        starBuffer.bind();
        starBuffer.upload(bufferbuilder$renderedbuffer);
        VertexBuffer.unbind();
    }

    public static void createDarkSky() {
        Tesselator tesselator = Tesselator.getInstance();
        BufferBuilder bufferBuilder = tesselator.getBuilder();
        if (darkBuffer != null) {
            darkBuffer.close();
        }
        darkBuffer = new VertexBuffer(VertexBuffer.Usage.STATIC);
        BufferBuilder.RenderedBuffer renderedBuffer = SkyRenderer.buildSkyDisc(bufferBuilder, -16.0f);
        darkBuffer.bind();
        darkBuffer.upload(renderedBuffer);
        VertexBuffer.unbind();
    }

    public static void createLightSky() {
        Tesselator tesselator = Tesselator.getInstance();
        BufferBuilder bufferBuilder = tesselator.getBuilder();
        if (skyBuffer != null) {
            skyBuffer.close();
        }
        skyBuffer = new VertexBuffer(VertexBuffer.Usage.STATIC);
        BufferBuilder.RenderedBuffer renderedBuffer = SkyRenderer.buildSkyDisc(bufferBuilder, 16.0f);
        skyBuffer.bind();
        skyBuffer.upload(renderedBuffer);
        VertexBuffer.unbind();
    }

    private static BufferBuilder.RenderedBuffer buildSkyDisc(BufferBuilder bufferBuilder, float f) {
        float f2 = Math.signum(f) * 512.0f;
        float f3 = 512.0f;
        RenderSystem.setShader(GameRenderer::getPositionShader);
        bufferBuilder.begin(VertexFormat.Mode.TRIANGLE_FAN, DefaultVertexFormat.POSITION);
        bufferBuilder.vertex(0.0, f, 0.0).endVertex();
        for (int i = -180; i <= 180; i += 45) {
            bufferBuilder.vertex(f2 * Mth.cos((float)i * 0.017453292f), f, 512.0f * Mth.sin((float)i * 0.017453292f)).endVertex();
        }
        return bufferBuilder.end();
    }

    private static BufferBuilder.RenderedBuffer drawStars(BufferBuilder bufferBuilder) {
        RandomSource randomSource = RandomSource.create(10842L);
        bufferBuilder.begin(VertexFormat.Mode.QUADS, DefaultVertexFormat.POSITION);
        for (int i = 0; i < 1500; ++i) {
            double d = randomSource.nextFloat() * 2.0f - 1.0f;
            double d2 = randomSource.nextFloat() * 2.0f - 1.0f;
            double d3 = randomSource.nextFloat() * 2.0f - 1.0f;
            double d4 = 0.15f + randomSource.nextFloat() * 0.1f;
            double d5 = d * d + d2 * d2 + d3 * d3;
            if (!(d5 < 1.0) || !(d5 > 0.01)) continue;
            d5 = 1.0 / Math.sqrt(d5);
            double d6 = (d *= d5) * 100.0;
            double d7 = (d2 *= d5) * 100.0;
            double d8 = (d3 *= d5) * 100.0;
            double d9 = Math.atan2(d, d3);
            double d10 = Math.sin(d9);
            double d11 = Math.cos(d9);
            double d12 = Math.atan2(Math.sqrt(d * d + d3 * d3), d2);
            double d13 = Math.sin(d12);
            double d14 = Math.cos(d12);
            double d15 = randomSource.nextDouble() * 3.141592653589793 * 2.0;
            double d16 = Math.sin(d15);
            double d17 = Math.cos(d15);
            for (int j = 0; j < 4; ++j) {
                double d18;
                double d19 = 0.0;
                double d20 = (double)((j & 2) - 1) * d4;
                double d21 = (double)((j + 1 & 2) - 1) * d4;
                double d22 = 0.0;
                double d23 = d20 * d17 - d21 * d16;
                double d24 = d18 = d21 * d17 + d20 * d16;
                double d25 = d23 * d13 + 0.0 * d14;
                double d26 = 0.0 * d13 - d23 * d14;
                double d27 = d26 * d10 - d24 * d11;
                double d28 = d25;
                double d29 = d24 * d10 + d26 * d11;
                bufferBuilder.vertex(d6 + d27, d7 + d28, d8 + d29).endVertex();
            }
        }
        return bufferBuilder.end();
    }
}