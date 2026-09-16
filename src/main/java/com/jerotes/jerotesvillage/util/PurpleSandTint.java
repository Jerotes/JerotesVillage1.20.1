package com.jerotes.jerotesvillage.util;

import com.jerotes.jerotesvillage.config.OtherMainConfig;
import net.minecraft.core.BlockPos;
import net.minecraft.util.Mth;
import org.joml.SimplexNoise;
public class PurpleSandTint {
	private static int lerpColor(int a, int b, float t) {
		int ra = (a >> 16) & 0xFF, ga = (a >> 8) & 0xFF, ba = a & 0xFF;
		int rb = (b >> 16) & 0xFF, gb = (b >> 8) & 0xFF, bb = b & 0xFF;
		int r = ra + Math.round((rb - ra) * t);
		int g = ga + Math.round((gb - ga) * t);
		int bl = ba + Math.round((bb - ba) * t);
		return (r << 16) | (g << 8) | bl;
	}

	public static int getTint(BlockPos pos) {
		int base = !OtherMainConfig.PurpleSandEyeProtectionMode ? 0x703c9d : 0xcc98c8;
		if (pos == null) return base;

		float n = SimplexNoise.noise(pos.getX() * 0.02f, pos.getZ() * 0.02f);

		if (n > 0.0f) {
			float t = Mth.clamp(n / 1.0f, 0f, 1f);
			return lerpColor(base, 0xff9e95, t * 0.9f);
		} else {
			float t = Mth.clamp(-n / 1.0f, 0f, 1f);
			return lerpColor(base, 0xff90d9, t * 0.9f);
		}
	}
}