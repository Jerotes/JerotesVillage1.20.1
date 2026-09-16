package com.jerotes.jerotesvillage.world.region;

import net.minecraft.util.Mth;

public final class ValueNoise2D {

	private final long salt;

	public ValueNoise2D(long salt) {
		this.salt = salt;
	}

	private static long hash(int x, int z, long salt) {
		long h = salt;
		h = h * 0x9E3779B97F4A7C15L + x;
		h ^= (h >>> 29);
		h = h * 0xBF58476D1CE4E5B9L + z;
		h ^= (h >>> 32);
		h *= 0x94D049BB133111EBL;
		h ^= (h >>> 31);
		return h;
	}

	private double gridValue(int gx, int gz) {
		long h = hash(gx, gz, salt);
		return (((h >>> 40) & 0xFFFFFF) / (double) 0xFFFFFF) * 2.0 - 1.0;
	}

	private static double smooth(double t) {
		return t * t * t * (t * (t * 6 - 15) + 10);
	}

	public double sample(double x, double z, double cellSize) {
		double gx = x / cellSize, gz = z / cellSize;
		int x0 = (int) Math.floor(gx), z0 = (int) Math.floor(gz);
		double tx = smooth(gx - x0), tz = smooth(gz - z0);
		double v00 = gridValue(x0, z0), v10 = gridValue(x0 + 1, z0);
		double v01 = gridValue(x0, z0 + 1), v11 = gridValue(x0 + 1, z0 + 1);
		double a = Mth.lerp(tx, v00, v10);
		double b = Mth.lerp(tx, v01, v11);
		return Mth.lerp(tz, a, b);
	}

	public double fbm(double x, double z, double baseCellSize, int octaves) {
		double sum = 0, amp = 1, freq = 1, ampSum = 0;
		for (int i = 0; i < octaves; i++) {
			sum += sample(x * freq, z * freq, baseCellSize) * amp;
			ampSum += amp;
			amp *= 0.5;
			freq *= 2.0;
		}
		return sum / ampSum;
	}
}
