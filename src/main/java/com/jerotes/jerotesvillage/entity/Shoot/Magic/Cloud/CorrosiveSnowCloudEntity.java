package com.jerotes.jerotesvillage.entity.Shoot.Magic.Cloud;

import com.jerotes.jerotes.entity.Other.SpellCloud.RainSpellCloudEntity;
import com.jerotes.jerotes.init.JerotesMobEffects;
import com.jerotes.jerotesvillage.init.JerotesVillageEntityType;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.level.Level;

public class CorrosiveSnowCloudEntity extends RainSpellCloudEntity {
    public CorrosiveSnowCloudEntity(EntityType<? extends CorrosiveSnowCloudEntity> entityType, Level level) {
        super(entityType, level);
    }

    public CorrosiveSnowCloudEntity(int spellLevelMainEffectTime, int spellLevelMainEffectLevel, Level level, double d, double d2, double d3) {
        this(JerotesVillageEntityType.CORROSIVE_SNOW_CLOUD.get(), level);
        this.setPos(d, d2, d3);
        this.spellLevelMainEffectTime = spellLevelMainEffectTime;
        this.spellLevelMainEffectLevel = spellLevelMainEffectLevel;
    }

    @Override
    public void addRainEffect(LivingEntity livingEntity, LivingEntity livingEntity1) {
        if (!livingEntity.level().isClientSide) {
            livingEntity.addEffect(new MobEffectInstance(JerotesMobEffects.CORROSIVE.get(), this.spellLevelMainEffectTime * 20, this.spellLevelMainEffectLevel-1), livingEntity1);
        }
    }
}