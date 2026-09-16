package com.jerotes.jerotesvillage.alchemy.effect;

import com.jerotes.jerotes.alchemy.effect.AAAAlchemyEffect;
import com.jerotes.jerotes.init.JerotesMobEffects;
import com.jerotes.jerotesvillage.init.JerotesVillageMobEffects;
import net.minecraft.world.effect.MobEffect;

public class WildernessGiantPoisonAlchemyEffect extends AAAAlchemyEffect {
    public WildernessGiantPoisonAlchemyEffect(int level, int time) {
        super(level, time);
    }

    public MobEffect getMobEffect() {
        return JerotesVillageMobEffects.WILDERNESS_GIANT_POISON.get();
    }

    public String getName() {
        return "jerotesvillage_wilderness_giant_poison";
    }
}