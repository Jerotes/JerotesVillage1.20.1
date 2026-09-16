package com.jerotes.jerotesvillage.event;

import com.jerotes.jerotesvillage.client.JerotesDimensionSpecialEffects;
import com.jerotes.jerotesvillage.client.SkyRenderer;
import net.minecraft.client.renderer.DimensionSpecialEffects;
import net.minecraft.resources.ResourceLocation;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.client.event.RegisterDimensionSpecialEffectsEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

@Mod.EventBusSubscriber
public class DimensionEvent {

	@Mod.EventBusSubscriber(bus = Mod.EventBusSubscriber.Bus.MOD)
	public static class DimensionSpecialEffectsHandler {
		@SubscribeEvent
		@OnlyIn(Dist.CLIENT)
		public static void registerDimensionSpecialEffects(RegisterDimensionSpecialEffectsEvent event) {
			SkyRenderer.createDarkSky();
			SkyRenderer.createLightSky();
			SkyRenderer.createStars();
			DimensionSpecialEffects customEffect = new JerotesDimensionSpecialEffects(160.0F, false, DimensionSpecialEffects.SkyType.NONE, false, false);
			event.register(new ResourceLocation("jerotesvillage:second_round_world"), customEffect);
		}
	}
}
