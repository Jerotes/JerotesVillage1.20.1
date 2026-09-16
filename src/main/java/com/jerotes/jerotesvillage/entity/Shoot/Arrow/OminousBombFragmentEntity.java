package com.jerotes.jerotesvillage.entity.Shoot.Arrow;

import com.jerotes.jerotes.init.JerotesDamageTypes;
import com.jerotes.jerotes.util.AttackFind;
import com.jerotes.jerotesvillage.init.JerotesVillageEntityType;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.Level;
import com.jerotes.jerotes.entity.Shoot.Arrow.*;
import net.minecraft.world.phys.HitResult;

public class OminousBombFragmentEntity extends BaseArrowEntity {
    public static final ItemStack item = new ItemStack(Items.AIR);
    private static final EntityType<OminousBombFragmentEntity> type = JerotesVillageEntityType.OMINOUS_BOMB_FRAGMENT.get();

    public OminousBombFragmentEntity(EntityType<? extends OminousBombFragmentEntity> entityType, Level level) {
        super(entityType, level, item, 4.0);
    }
    public OminousBombFragmentEntity(Level level, LivingEntity livingEntity, ItemStack itemStack) {
        super(level, livingEntity, itemStack, type, 4.0);
    }
    public OminousBombFragmentEntity(Level level, double d, double d2, double d3, ItemStack itemStack) {
        super(level, d, d2, d3, itemStack, type, 4.0);
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
    public boolean mustBreak() {
        return true;
    }

    @Override
    protected void onHit(HitResult hitResult) {
        super.onHit(hitResult);
        this.discard();
    }
}
