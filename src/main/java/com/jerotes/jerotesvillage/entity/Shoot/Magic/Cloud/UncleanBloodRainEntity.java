package com.jerotes.jerotesvillage.entity.Shoot.Magic.Cloud;

import com.jerotes.jerotes.entity.Other.SpellCloud.RainSpellCloudEntity;
import com.jerotes.jerotesvillage.init.JerotesVillageEntityType;
import com.jerotes.jerotesvillage.init.JerotesVillageMobEffects;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.level.Level;

public class UncleanBloodRainEntity extends RainSpellCloudEntity {
    public UncleanBloodRainEntity(EntityType<? extends UncleanBloodRainEntity> entityType, Level level) {
        super(entityType, level);
    }

    public UncleanBloodRainEntity(int spellLevelMainEffectTime, int spellLevelMainEffectLevel, Level level, double d, double d2, double d3) {
        this(JerotesVillageEntityType.UNCLEAN_BLOOD_RAIN.get(), level);
        this.setPos(d, d2, d3);
        this.spellLevelMainEffectTime = spellLevelMainEffectTime;
        this.spellLevelMainEffectLevel = spellLevelMainEffectLevel;
    }

    @Override
    public void addRainEffect(LivingEntity livingEntity, LivingEntity livingEntity1) {
        if (!livingEntity.level().isClientSide) {
            livingEntity.addEffect(new MobEffectInstance(JerotesVillageMobEffects.UNCLEAN_BODY.get(), this.spellLevelMainEffectTime * 20, this.spellLevelMainEffectLevel-1), livingEntity1);
        }
    }
}