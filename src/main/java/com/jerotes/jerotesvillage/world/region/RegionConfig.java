package com.jerotes.jerotesvillage.world.region;

public final class RegionConfig {
	public static int CONTINENT_CELL_SIZE = 1600;
	public static float CONTINENT_JITTER = 0.4f;
	public static double OCEAN_BIAS = 0.38;

	public static double COAST_WIDTH = 220.0;
	public static double COAST_EASE_POWER = 2.2;
	public static double LAND_BORDER_BLEND = 32.0;

	public static int LAND_CELL_SIZE = 700;
	public static float LAND_JITTER = 0.42f;

	public static int CLUSTER_CELL_SIZE = 3000;
	public static float CLUSTER_JITTER = 0.35f;
	public static double CLUSTER_DOMINANCE = 0.78;
	public static int PATCH_CELL_SIZE = 1500;
	public static float PATCH_JITTER = 0.4f;

	public static int CAVE_CELL_SIZE = 900;
	public static float CAVE_JITTER = 0.4f;
	public static int CAVE_Y_THRESHOLD = -48;

	public static double OCEAN_DENSITY_BIAS = -1.6;
	public static double OCEAN_DENSITY_FLOOR = -2.6;
	public static double LAND_DENSITY_BIAS = 0.32;
	public static double WONDERLAND_MOUNTAIN_BIAS = 0.55;
	public static double WONDERLAND_BASIN_BIAS = -0.3;
	public static double DROUGHT_FIRE_PLATEAU_BIAS = 0.3;

	public static int SEA_LEVEL = 63;
	public static double TAPER_CORE_HALF_WIDTH = 30;
	public static double TAPER_FADE_DOWN = 20;
	public static double TAPER_FADE_UP = 70;
	public static int TAPER_HARD_FLOOR = 35;

	public static int OCEAN_STRONG_BELOW_SEA = 10;
	public static double OCEAN_FADE_DOWN = 15;

	public static double OCEAN_FLOOD_BIAS = 2.0;

	private RegionConfig() {}
}