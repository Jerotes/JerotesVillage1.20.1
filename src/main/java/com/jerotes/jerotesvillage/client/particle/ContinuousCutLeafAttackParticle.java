package com.jerotes.jerotesvillage.client.particle;

import com.jerotes.jerotes.init.JerotesParticleRenderTypes;
import com.mojang.blaze3d.vertex.VertexConsumer;
import net.minecraft.client.Camera;
import net.minecraft.client.multiplayer.ClientLevel;
import net.minecraft.client.particle.*;
import net.minecraft.core.particles.SimpleParticleType;
import net.minecraft.util.Mth;
import net.minecraft.world.phys.Vec3;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import org.joml.Quaternionf;
import org.joml.Vector3f;

@OnlyIn(Dist.CLIENT)
public class ContinuousCutLeafAttackParticle extends TextureSheetParticle {
	private final SpriteSet sprites;
	private float baseScale;
	private float yaw;
	private float pitch;
	private final Quaternionf rotationQuaternion;

	protected ContinuousCutLeafAttackParticle(ClientLevel level, double x, double y, double z,
											  float yaw, float pitch, SpriteSet sprites) {
		super(level, x, y, z);
		this.sprites = sprites;
		this.setSpriteFromAge(sprites);
		this.yaw = yaw;
		this.pitch = pitch;

		this.rotationQuaternion = new Quaternionf()
				.rotationY(-yaw * ((float)Math.PI / 180f))
				.mul(new Quaternionf().rotationY(-Mth.HALF_PI))
				.mul(new Quaternionf().rotationX(Mth.HALF_PI));

		this.rCol = 1.0f;
		this.gCol = 1.0f;
		this.bCol = 1.0f;
		this.alpha = 0.9f;
		this.baseScale = 2.5f;
		this.lifetime = 25;
		this.hasPhysics = false;
	}

	@Override
	public void tick() {
		super.tick();
		this.setSpriteFromAge(this.sprites);

		float progress = (float)this.age / this.lifetime;
		float easeOut = 1 - (float)Math.pow(1 - progress, 4);

		// 平滑放大
		this.quadSize = (baseScale * (1 + easeOut * 1.5f));

		// 透明度衰减
		this.alpha = 0.9f * (1 - progress);
	}

	@Override
	public ParticleRenderType getRenderType() {
		return JerotesParticleRenderTypes.CUSTOM_TRANSLUCENT_SHOCKWAVE;
	}

	@Override
	public void render(VertexConsumer vertexConsumer, Camera camera, float partialTick) {
		this.alpha = 1.0f - Mth.clamp(((float)this.age + partialTick) / (float)this.lifetime, 0.0f, 1.0f);

		Vec3 cameraPos = camera.getPosition();
		float posX = (float)(Mth.lerp(partialTick, this.xo, this.x) - cameraPos.x());
		float posY = (float)(Mth.lerp(partialTick, this.yo, this.y) - cameraPos.y());
		float posZ = (float)(Mth.lerp(partialTick, this.zo, this.z) - cameraPos.z());

		// 四个顶点的局部坐标（在粒子局部坐标系中，初始为XY平面，法线为Z轴）
		Vector3f[] vertices = new Vector3f[]{
				new Vector3f(-1.0f, -1.0f, 0.0f),
				new Vector3f(-1.0f, 1.0f, 0.0f),
				new Vector3f(1.0f, 1.0f, 0.0f),
				new Vector3f(1.0f, -1.0f, 0.0f)
		};

		float size = this.getQuadSize(partialTick);
		int light = this.getLightColor(partialTick);

		// 使用预计算的旋转四元数
		Quaternionf rotation = new Quaternionf(this.rotationQuaternion);

		// 渲染第一个面（正面）
		renderQuad(vertexConsumer, vertices, rotation, posX, posY, posZ, size, light);

		// 渲染第二个面（背面），使用相反的旋转（绕法线旋转180度）
		Quaternionf backRotation = new Quaternionf(rotation);
		backRotation.mul(new Quaternionf().rotationZ((float)Math.PI)); // 绕Z轴旋转180度，这样背面就翻过来了
		renderQuad(vertexConsumer, vertices, backRotation, posX, posY, posZ, size, light);
	}

	private void renderQuad(VertexConsumer vertexConsumer, Vector3f[] vertices, Quaternionf rotation,
							float posX, float posY, float posZ, float size, int light) {
		// 复制顶点，避免修改原始数组
		Vector3f[] transformed = new Vector3f[4];
		for (int i = 0; i < 4; i++) {
			transformed[i] = new Vector3f(vertices[i]);
			transformed[i].rotate(rotation);
			transformed[i].mul(size);
			transformed[i].add(posX, posY, posZ);
		}

		// 渲染四边形（两个三角形）
		this.makeCornerVertex(vertexConsumer, transformed[0], this.getU1(), this.getV1(), light);
		this.makeCornerVertex(vertexConsumer, transformed[1], this.getU1(), this.getV0(), light);
		this.makeCornerVertex(vertexConsumer, transformed[2], this.getU0(), this.getV0(), light);

		this.makeCornerVertex(vertexConsumer, transformed[0], this.getU1(), this.getV1(), light);
		this.makeCornerVertex(vertexConsumer, transformed[2], this.getU0(), this.getV0(), light);
		this.makeCornerVertex(vertexConsumer, transformed[3], this.getU0(), this.getV1(), light);
	}

	private void makeCornerVertex(VertexConsumer vertexConsumer, Vector3f position, float u, float v, int light) {
		vertexConsumer.vertex(position.x(), position.y(), position.z())
				.uv(u, v)
				.color(this.rCol, this.gCol, this.bCol, this.alpha)
				.uv2(light)
				.endVertex();
	}

	// 新增：动态更新yaw的方法
	public void setYaw(float newYaw) {
		this.yaw = newYaw;
		this.rotationQuaternion.rotationY(-newYaw)
				.mul(new Quaternionf().rotationYXZ(0.0f, Mth.HALF_PI, 0.0f));
	}

	public float getYaw() {
		return this.yaw;
	}

	public static class Provider implements ParticleProvider<SimpleParticleType> {
		private final SpriteSet sprites;

		public Provider(SpriteSet sprites) {
			this.sprites = sprites;
		}

		@Override
		public Particle createParticle(SimpleParticleType type, ClientLevel level,
									   double x, double y, double z,
									   double xd, double yd, double zd) {
			// 使用xd作为yaw，yd作为pitch传递角度
			return new ContinuousCutLeafAttackParticle(level, x, y, z,
					(float)xd, (float)yd, this.sprites);
		}
	}
}