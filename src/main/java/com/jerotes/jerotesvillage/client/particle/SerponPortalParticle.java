package com.jerotes.jerotesvillage.client.particle;

import net.minecraft.client.multiplayer.ClientLevel;
import net.minecraft.client.particle.*;
import net.minecraft.core.particles.SimpleParticleType;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

@OnlyIn(Dist.CLIENT)
public class SerponPortalParticle extends TextureSheetParticle {
	public static DisplayParticleProvider provider(SpriteSet spriteSet) {
		return new DisplayParticleProvider(spriteSet);
	}

	public static class DisplayParticleProvider implements ParticleProvider<SimpleParticleType> {
			private final SpriteSet spriteSet;

		public DisplayParticleProvider(SpriteSet spriteSet) {
			this.spriteSet = spriteSet;
		}

		public Particle createParticle(SimpleParticleType typeIn, ClientLevel worldIn, double x, double y, double z, double xSpeed, double ySpeed, double zSpeed) {
			return new SerponPortalParticle(worldIn, x, y, z, xSpeed, ySpeed, zSpeed, this.spriteSet);
		}
	}

	private final SpriteSet spriteSet;

	protected SerponPortalParticle(ClientLevel world, double x, double y, double z, double vx, double vy, double vz, SpriteSet spriteSet) {
		super(world, x, y, z);
		this.spriteSet = spriteSet;
		this.setSize(0.3f, 0.3f);
		this.quadSize *= 15;
		this.lifetime = 16;
		this.gravity = 0f;
		this.hasPhysics = false;
		this.xd = vx * 2;
		this.yd = vy * 2;
		this.zd = vz * 2;
		this.pickSprite(spriteSet);
	}

	@Override
	public ParticleRenderType getRenderType() {
		return ParticleRenderType.PARTICLE_SHEET_OPAQUE;
	}


	@Override
	public int getLightColor(float f) {
		return 240;
	}

	@Override
	public void tick() {
		super.tick();
		this.setSpriteFromAge(this.spriteSet);
	}

}
