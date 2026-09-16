package com.jerotes.jerotesvillage.alchemy.effect;

import com.jerotes.jerotes.alchemy.effect.AAAAlchemyEffect;
import com.jerotes.jerotesvillage.init.JerotesVillageMobEffects;
import net.minecraft.world.effect.MobEffect;

public class FragranceAlchemyEffect extends AAAAlchemyEffect {
    public FragranceAlchemyEffect(int level, int time) {
        super(level, time);
    }

    public MobEffect getMobEffect() {
        return JerotesVillageMobEffects.FRAGRANCE.get();
    }

    public String getName() {
        return "jerotesvillage_fragrance";
    }
}