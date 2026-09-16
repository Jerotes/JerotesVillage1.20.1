package com.jerotes.jerotesvillage.world.region;

public final class WorldSeedHolder {
	private static volatile long seed = 0L;
	public static void set(long s) { seed = s; }
	public static long get() { return seed; }
}