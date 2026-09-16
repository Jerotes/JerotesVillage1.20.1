package com.jerotes.jerotesvillage.entity.Shoot.ThrowingBall;

import com.jerotes.jerotesvillage.init.JerotesVillageEntityType;
import com.jerotes.jerotesvillage.init.JerotesVillageItems;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.Level;

public class VillagerMetalThrowingBallEntity extends BaseThrowingBallEntity {
    public VillagerMetalThrowingBallEntity(EntityType<? extends VillagerMetalThrowingBallEntity> entityType, Level level) {
        super(entityType, level);
    }

    public VillagerMetalThrowingBallEntity(Level level, LivingEntity livingEntity) {
        super(JerotesVillageEntityType.VILLAGER_METAL_THROWING_BALL.get(), livingEntity, level);
    }

    public VillagerMetalThrowingBallEntity(Level level, double d, double d2, double d3) {
        super(JerotesVillageEntityType.VILLAGER_METAL_THROWING_BALL.get(), d, d2, d3, level);
    }

    @Override
    public float baseDamage() {
        return 4f;
    }
    @Override
    public float addDamage() {
        return 8f;
    }
    @Override
    public int maxBackCount() {
        return 8;
    }

    @Override
    protected Item getDefaultItem() {
        return JerotesVillageItems.VILLAGER_METAL_THROWING_BALL.get();
    }
}
