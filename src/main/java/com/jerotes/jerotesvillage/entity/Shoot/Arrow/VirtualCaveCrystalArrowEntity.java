package com.jerotes.jerotesvillage.entity.Shoot.Arrow;

import com.jerotes.jerotes.entity.Shoot.Arrow.BaseArrowEntity;
import com.jerotes.jerotes.init.JerotesDamageTypes;
import com.jerotes.jerotes.util.AttackFind;
import com.jerotes.jerotesvillage.init.JerotesVillageEntityType;
import com.jerotes.jerotesvillage.init.JerotesVillageItems;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;

public class VirtualCaveCrystalArrowEntity extends BaseArrowEntity {
    public static final ItemStack item = new ItemStack(JerotesVillageItems.VIRTUAL_CAVE_CRYSTAL_ARROW.get());
    private static final EntityType<VirtualCaveCrystalArrowEntity> type = JerotesVillageEntityType.VIRTUAL_CAVE_CRYSTAL_ARROW.get();

    public VirtualCaveCrystalArrowEntity(EntityType<? extends VirtualCaveCrystalArrowEntity> entityType, Level level) {
        super(entityType, level, item, 2.5f);
    }
    public VirtualCaveCrystalArrowEntity(Level level, LivingEntity livingEntity, ItemStack itemStack) {
        super(level, livingEntity, itemStack, type, 2.5f);
    }
    public VirtualCaveCrystalArrowEntity(Level level, double d, double d2, double d3, ItemStack itemStack) {
        super(level, d, d2, d3, itemStack, type, 2.5f);
    }

    @Override
    public DamageSource getDamageSource(Entity entity) {
        DamageSource damageSource = AttackFind.findDamageType(this, JerotesDamageTypes.BYPASSES_COOLDOWN_SHOOT, this, this);
        if (this.getOwner() != null) {
            damageSource = AttackFind.findDamageType(this, JerotesDamageTypes.BYPASSES_COOLDOWN_SHOOT, this, this.getOwner());
        }
        return damageSource;
    }

    @Override
    public boolean canHurtEnderman() {
        return true;
    }
    @Override
    public boolean mustBreak() {
        return true;
    }
}


