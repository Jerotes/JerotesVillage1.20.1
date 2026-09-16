package com.jerotes.jerotesvillage.spell;

import com.jerotes.jerotesvillage.JerotesVillage;
import net.minecraft.world.entity.LivingEntity;
import net.minecraftforge.event.entity.living.LivingDeathEvent;
import net.minecraftforge.event.entity.living.LivingEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

@Mod.EventBusSubscriber(modid = JerotesVillage.MODID)
public class OtherSpellFindUseEvent {
    @SubscribeEvent
    public static void SpellTick(LivingEvent.LivingTickEvent event) {
        LivingEntity caster = event.getEntity();
        if (caster == null)
            return;
    }
    @SubscribeEvent
    public static void CasterDeath(LivingDeathEvent event) {
        LivingEntity caster = event.getEntity();
        if (caster == null)
            return;
        OtherSpellType.stops(caster, 10, true);
    }
}