package com.jerotes.jerotesvillage.world.region;

import net.minecraft.util.Mth;

public final class VoronoiRegion {

	private final int cellSize;
	private final float jitter;
	private final long salt;

	// 粗扭曲：负责大范围弧线弯曲
	private static final ValueNoise2D WARP_X = new ValueNoise2D(9001L);
	private static final ValueNoise2D WARP_Z = new ValueNoise2D(9002L);
	// 细扭曲：负责边缘细碎锯齿，波长短、幅度小，单独看不会导致折叠
	private static final ValueNoise2D WARP_X2 = new ValueNoise2D(9003L);
	private static final ValueNoise2D WARP_Z2 = new ValueNoise2D(9004L);

	public VoronoiRegion(int cellSize, float jitter, long salt) {
		this.cellSize = cellSize;
		this.jitter = Mth.clamp(jitter, 0f, 0.5f);
		this.salt = salt;
	}

	public int cellSize() { return cellSize; }

	private double[] cellPoint(int cx, int cz) {
		long h = hash(cx, cz, salt);
		double jx = ((h >>> 32) & 0xFFFF) / 65535.0 - 0.5;
		double jz = (h & 0xFFFF) / 65535.0 - 0.5;
		double px = (cx + 0.5 + jx * 2 * jitter) * cellSize;
		double pz = (cz + 0.5 + jz * 2 * jitter) * cellSize;
		return new double[]{px, pz};
	}

	public double[] seedPointOf(int cx, int cz) {
		return cellPoint(cx, cz);
	}

	private static long hash(long x, long z, long salt) {
		long h = salt;
		h = h * 0x9E3779B97F4A7C15L + x;
		h ^= (h >>> 29);
		h = h * 0xBF58476D1CE4E5B9L + z;
		h ^= (h >>> 32);
		h *= 0x94D049BB133111EBL;
		h ^= (h >>> 31);
		return h;
	}

	public record Sample(int cx, int cz, int cx2, int cz2, double f1, double f2) {}

	public Sample sample(double worldX, double worldZ) {
		// 粗扭曲：负责大范围弧线，比例 0.20/0.55≈0.36，留足安全余量不会折叠
		double coarseStrength = cellSize * 0.20;
		double coarseScale = cellSize * 0.55;
		// 细扭曲：负责边缘细碎锯齿，波长短、幅度更小，比例同样留有余量
		double fineStrength = cellSize * 0.055;
		double fineScale = cellSize * 0.16;

		double wx = worldX
				+ WARP_X.sample(worldX, worldZ, coarseScale) * coarseStrength
				+ WARP_X2.sample(worldX, worldZ, fineScale) * fineStrength;
		double wz = worldZ
				+ WARP_Z.sample(worldX, worldZ, coarseScale) * coarseStrength
				+ WARP_Z2.sample(worldX, worldZ, fineScale) * fineStrength;

		int baseCx = (int) Math.floorDiv((long) Math.floor(wx), cellSize);
		int baseCz = (int) Math.floorDiv((long) Math.floor(wz), cellSize);

		int c1x = baseCx, c1z = baseCz, c2x = baseCx, c2z = baseCz;
		double best1 = Double.MAX_VALUE, best2 = Double.MAX_VALUE;

		for (int dx = -1; dx <= 1; dx++) {
			for (int dz = -1; dz <= 1; dz++) {
				int cx = baseCx + dx, cz = baseCz + dz;
				double[] p = cellPoint(cx, cz);
				double ddx = p[0] - wx, ddz = p[1] - wz;
				double dist = ddx * ddx + ddz * ddz;
				if (dist < best1) {
					best2 = best1; c2x = c1x; c2z = c1z;
					best1 = dist; c1x = cx; c1z = cz;
				} else if (dist < best2) {
					best2 = dist; c2x = cx; c2z = cz;
				}
			}
		}
		return new Sample(c1x, c1z, c2x, c2z, Math.sqrt(best1), Math.sqrt(best2));
	}

	public long cellSeed(int cx, int cz, long extraSalt) {
		return hash(cx, cz, WorldSeedHolder.get() ^ 1001L ^ extraSalt);
	}

	public static double unitFloat(long h) {
		return ((h >>> 40) & 0xFFFFFF) / (double) 0xFFFFFF;
	}
}
