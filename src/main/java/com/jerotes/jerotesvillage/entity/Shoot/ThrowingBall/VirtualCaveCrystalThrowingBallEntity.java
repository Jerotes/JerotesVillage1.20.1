package com.jerotes.jerotesvillage.entity.Shoot.ThrowingBall;

import com.jerotes.jerotes.init.JerotesDamageTypes;
import com.jerotes.jerotes.util.AttackFind;
import com.jerotes.jerotesvillage.init.JerotesVillageEntityType;
import com.jerotes.jerotesvillage.init.JerotesVillageItems;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.Level;

public class VirtualCaveCrystalThrowingBallEntity extends BaseThrowingBallEntity {
    public VirtualCaveCrystalThrowingBallEntity(EntityType<? extends VirtualCaveCrystalThrowingBallEntity> entityType, Level level) {
        super(entityType, level);
    }

    public VirtualCaveCrystalThrowingBallEntity(Level level, LivingEntity livingEntity) {
        super(JerotesVillageEntityType.VIRTUAL_CAVE_CRYSTAL_THROWING_BALL.get(), livingEntity, level);
    }

    public VirtualCaveCrystalThrowingBallEntity(Level level, double d, double d2, double d3) {
        super(JerotesVillageEntityType.VIRTUAL_CAVE_CRYSTAL_THROWING_BALL.get(), d, d2, d3, level);
    }

    @Override
    public float baseDamage() {
        return 2f;
    }
    @Override
    public float addDamage() {
        return 3f;
    }
    @Override
    public int maxBackCount() {
        return 0;
    }
    @Override
    public DamageSource damageSource(Entity entity) {
        return AttackFind.findDamageType(this, JerotesDamageTypes.BYPASSES_COOLDOWN_SHOOT, this, entity);
    }

    @Override
    protected Item getDefaultItem() {
        return JerotesVillageItems.VIRTUAL_CAVE_CRYSTAL_THROWING_BALL.get();
    }
}
