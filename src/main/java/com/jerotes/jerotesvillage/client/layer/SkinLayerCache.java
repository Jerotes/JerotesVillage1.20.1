package com.jerotes.jerotesvillage.client.layer;

import com.jerotes.jerotes.util.Color;
import com.jerotes.jerotesvillage.JerotesVillage;
import net.minecraft.resources.ResourceLocation;

import java.util.HashMap;
import java.util.Map;

public final class SkinLayerCache {
	private static final Map<String, ResourceLocation> TEXTURES = new HashMap<>();
	private static final Map<String, int[]> COLORS = new HashMap<>();

	private SkinLayerCache() {
	}

	public static ResourceLocation tex(String path) {
		ResourceLocation location = TEXTURES.get(path);
		if (location == null) {
			location = new ResourceLocation(JerotesVillage.MODID, path);
			TEXTURES.put(path, location);
		}
		return location;
	}

	public static int[] colorRgb(String color) {
		int[] rgb = COLORS.get(color);
		if (rgb == null) {
			rgb = Color.colorStringToRGBInt(color);
			COLORS.put(color, rgb);
		}
		return rgb;
	}
}
