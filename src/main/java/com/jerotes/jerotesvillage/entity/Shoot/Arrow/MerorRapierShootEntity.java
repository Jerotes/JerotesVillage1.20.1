package com.jerotes.jerotesvillage.entity.Shoot.Arrow;

import com.jerotes.jerotes.entity.Shoot.Arrow.BaseAbstractArrowEntity;
import com.jerotes.jerotes.init.JerotesDamageTypes;
import com.jerotes.jerotes.util.AttackFind;
import com.jerotes.jerotesvillage.init.JerotesVillageEntityType;
import com.jerotes.jerotesvillage.init.JerotesVillageSoundEvents;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.enchantment.EnchantmentHelper;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.EntityHitResult;

public class MerorRapierShootEntity extends BaseAbstractArrowEntity {
    private static final ItemStack DEFAULT_ARROW_STACK = new ItemStack(Items.AIR);
    private ItemStack arrowItem = new ItemStack(Items.AIR);

    public MerorRapierShootEntity(EntityType<? extends MerorRapierShootEntity> entityType, Level level) {
        super(entityType, level, DEFAULT_ARROW_STACK);
    }
    public MerorRapierShootEntity(Level level, LivingEntity livingEntity, ItemStack itemStack) {
        super(JerotesVillageEntityType.MEROR_RAPIER_SHOOT.get(), livingEntity, level, itemStack);
        this.arrowItem = itemStack.copy();
    }

    @Override
    protected ItemStack getPickupItem() {
        return arrowItem.copy();
    }

    @Override
    protected void defineSynchedData() {
        super.defineSynchedData();
    }

    public int life = 0;

    @Override
    public void tick() {
        super.tick();
        if (this.life >= 5) {
            this.discard();
        }
        else {
            this.life += 1;
        }
    }

    @Override
    protected void onHitEntity(EntityHitResult entityHitResult) {
        super.onHitEntity(entityHitResult);
        if (this.level().isClientSide()) {
            return;
        }
        Entity entity = entityHitResult.getEntity();
        Entity entity2 = this.getOwner();
        if (entity == entity2) {
            return;
        }
        float f = 8;
        if (entity2 instanceof LivingEntity livingEntity && livingEntity.getAttribute(Attributes.ATTACK_DAMAGE) != null) {
            f = (float) livingEntity.getAttributeValue(Attributes.ATTACK_DAMAGE);
            f += EnchantmentHelper.getDamageBonus(livingEntity.getMainHandItem(),livingEntity.getMobType());
        }
        if (entity2 instanceof LivingEntity livingEntity) {
            AttackFind.attackBegin(livingEntity, entity);
            boolean bl = AttackFind.attackAfter(livingEntity, entity, 1.0f, 0.2f, false, 0.0f);
            if (!bl) {
                DamageSource damageSource = AttackFind.findDamageType(this, JerotesDamageTypes.BYPASSES_COOLDOWN_SHOOT, this, livingEntity);
                entity.hurt(damageSource, f);
            }
        }
        else {
            DamageSource damageSource = AttackFind.findDamageType(this, JerotesDamageTypes.BYPASSES_COOLDOWN_SHOOT, this);
            entity.hurt(damageSource, f);
        }
        this.discard();
    }

    @Override
    protected void onHitBlock(BlockHitResult blockHitResult) {
        super.onHitBlock(blockHitResult);
        this.discard();
    }

    @Override
    protected SoundEvent getDefaultHitGroundSoundEvent() {
        return JerotesVillageSoundEvents.MEROR_TOOL_USE;
    }

    @Override
    public boolean isPickable() {
        return false;
    }

    @Override
    public void readAdditionalSaveData(CompoundTag compoundTag) {
        super.readAdditionalSaveData(compoundTag);
        this.life = compoundTag.getInt("Life");
        if (compoundTag.contains("Arrow", 10)) {
            this.arrowItem = ItemStack.of(compoundTag.getCompound("Arrow"));
        }
    }

    @Override
    public void addAdditionalSaveData(CompoundTag compoundTag) {
        super.addAdditionalSaveData(compoundTag);
        compoundTag.putInt("Life", this.life);
        compoundTag.put("Arrow", this.arrowItem.save(new CompoundTag()));
    }

    @Override
    protected float getWaterInertia() {
        return 0.99f;
    }
}
