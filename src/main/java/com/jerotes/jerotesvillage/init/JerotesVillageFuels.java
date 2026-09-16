package com.jerotes.jerotesvillage.init;

import net.minecraft.world.item.ItemStack;
import net.minecraftforge.event.furnace.FurnaceFuelBurnTimeEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;


@Mod.EventBusSubscriber

public class JerotesVillageFuels {
	@SubscribeEvent
	public static void furnaceFuelBurnTimeEvent(FurnaceFuelBurnTimeEvent event) {
		ItemStack itemstack = event.getItemStack();
		if (itemstack.getItem() == JerotesVillageItems.DEEP_DUST.get().asItem())
			event.setBurnTime(800);
		else if (itemstack.getItem() == JerotesVillageBlocks.DEEP_DUST_BLOCK.get().asItem())
			event.setBurnTime(3200);
		else if (itemstack.getItem() == JerotesVillageBlocks.DROUGHT_FIRE_MUD.get().asItem())
			event.setBurnTime(800);
		else if (itemstack.getItem() == JerotesVillageItems.BITTER_COLD_FAT.get().asItem())
			event.setBurnTime(800);
		else if (itemstack.getItem() == JerotesVillageItems.GIANT_MONSTER_HAIR.get().asItem())
			event.setBurnTime(200);
		else if (itemstack.getItem() == JerotesVillageItems.SCALY_BEAR_HAIR.get().asItem())
			event.setBurnTime(200);
		else if (itemstack.getItem() == JerotesVillageItems.SPIRVE_STUB.get().asItem())
			event.setBurnTime(200);
		else if (itemstack.getItem() == JerotesVillageItems.SPIRVE_BROOM.get().asItem())
			event.setBurnTime(200);
		else if (itemstack.getItem() == JerotesVillageItems.SPIRVE_MOP.get().asItem())
			event.setBurnTime(200);
		else if (itemstack.getItem() == JerotesVillageItems.SPIRVE_INSECT_NET.get().asItem())
			event.setBurnTime(200);
		else if (itemstack.getItem() == JerotesVillageItems.SPIRVE_LID.get().asItem())
			event.setBurnTime(200);
		else if (itemstack.getItem() == JerotesVillageItems.SPIRVE_RULER.get().asItem())
			event.setBurnTime(200);
		else if (itemstack.getItem() == JerotesVillageItems.SPIRVE_WOODEN_BACK_SCRATCHER.get().asItem())
			event.setBurnTime(200);
		else if (itemstack.getItem() == JerotesVillageItems.MEROR_ENERGY_REGIME.get().asItem())
			event.setBurnTime(12096000);
		else if (itemstack.getItem() == JerotesVillageItems.SUSPICIOUS_BRANCH.get().asItem())
			event.setBurnTime(200);
		else if (itemstack.getItem() == JerotesVillageItems.RAMBLER_SKIN_WAND.get().asItem())
			event.setBurnTime(200);
		else if (itemstack.getItem() == JerotesVillageItems.WONDERLIN_FAKE_EARS.get().asItem())
			event.setBurnTime(200);
		else if (itemstack.getItem() == JerotesVillageItems.WONDERLIN_HANG_GLIDER.get().asItem())
			event.setBurnTime(200);
		else if (itemstack.getItem() == JerotesVillageItems.BRIGHT_GRASS_UMBRELLA.get().asItem())
			event.setBurnTime(200);
		else if (itemstack.getItem() == JerotesVillageItems.WILDERNESS_THORNS.get().asItem())
			event.setBurnTime(200);
		else if (itemstack.getItem() == JerotesVillageItems.WILDERNESS_WOOD_CORE.get().asItem())
			event.setBurnTime(200);
		else if (itemstack.getItem() == JerotesVillageItems.GREEN_HUMANOID_CANE_STEMS_BUNDLE.get().asItem())
			event.setBurnTime(1800);
		else if (itemstack.getItem() == JerotesVillageItems.GREEN_HUMANOID_CANE_STEMS.get().asItem())
			event.setBurnTime(200);
		else if (itemstack.getItem() == JerotesVillageItems.PURPLE_BLACK_DEAD_BUSH.get().asItem())
			event.setBurnTime(200);
		else if (itemstack.getItem() == JerotesVillageItems.PURPLE_SAND_SHORT_DRY_GRASS.get().asItem())
			event.setBurnTime(100);
		else if (itemstack.getItem() == JerotesVillageItems.PURPLE_SAND_TALL_DRY_GRASS.get().asItem())
			event.setBurnTime(100);
	}
}