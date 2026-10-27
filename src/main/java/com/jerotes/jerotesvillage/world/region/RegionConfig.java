package com.jerotes.jerotesvillage.world.region;

public final class RegionConfig {
	public static int CONTINENT_CELL_SIZE = 1600;
	public static float CONTINENT_JITTER = 0.4f;
	public static double OCEAN_BIAS = 0.38;

	public static double COAST_WIDTH = 220.0;
	public static double COAST_EASE_POWER = 2.2;
	// 陆地群系之间的混合带：从 32 放宽到 160。群系偏置差最大可达 0.62（仙境盆地 -0.3 对草原 +0.32），
	// 32 格走完这个落差就是垂直墙；160 格才能摊成缓坡。
	public static double LAND_BORDER_BLEND = 160.0;

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
	// 原来是 35，但核心区从 y=33 起就是 1.0，导致 y=34 返回 0、y=35 返回 1.0 的硬跳变，
	// 同时让 TAPER_FADE_DOWN 变成死代码。压到世界底部以下，交给淡出分支收尾。
	public static int TAPER_HARD_FLOOR = -64;

	public static int OCEAN_STRONG_BELOW_SEA = 10;
	public static double OCEAN_FADE_DOWN = 15;

	public static double OCEAN_FLOOD_BIAS = 2.0;

	private RegionConfig() {}
}
