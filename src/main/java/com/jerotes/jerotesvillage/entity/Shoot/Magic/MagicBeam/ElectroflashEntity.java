package com.jerotes.jerotesvillage.entity.Shoot.Magic.MagicBeam;

import com.jerotes.jerotes.config.MainConfig;
import com.jerotes.jerotes.entity.Shoot.Magic.MagicBeam.BaseMagicBeamEntity;
import com.jerotes.jerotes.init.JerotesDamageTypes;
import com.jerotes.jerotes.util.AttackFind;
import com.jerotes.jerotes.util.Main;
import com.jerotes.jerotesvillage.JerotesVillage;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.level.Level;

public class ElectroflashEntity extends BaseMagicBeamEntity {
    public ElectroflashEntity(EntityType<? extends ElectroflashEntity> entityType, Level level) {
        super(entityType, level);
    }
    public ElectroflashEntity(EntityType<? extends ElectroflashEntity> entityType, Level level, int spellLevelDamage) {
        super(entityType, level, spellLevelDamage);
    }

    public void customHurtMagic(Entity entity) {
        entity.hurt(AttackFind.findDamageType(this, JerotesDamageTypes.BYPASSES_COOLDOWN_LIGHTNING, this, this.getOwner()),
                Main.rollDice(spellLevelDamage, 6, this.random) / 30 / 5 * (float) (MainConfig.BeamDamageMultipler));
    }
    public int getMaxTick() {
        return 240;
    }

    public ResourceLocation getBeamLocation() {
        return new ResourceLocation(JerotesVillage.MODID, "textures/entity/beam/electroflash.png");
    }
}