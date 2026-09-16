package com.jerotes.jerotesvillage.entity.Shoot.Arrow;

import com.jerotes.jerotes.entity.Shoot.Arrow.BaseAbstractArrowEntity;
import com.jerotes.jerotesvillage.init.JerotesVillageEntityType;
import com.jerotes.jerotesvillage.init.JerotesVillageParticleTypes;
import com.jerotes.jerotesvillage.init.JerotesVillageSoundEvents;
import com.jerotes.jerotesvillage.item.MerorScythe;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.item.ItemEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.EntityHitResult;

public class MerorScytheShootEntity extends BaseAbstractArrowEntity {
    private static final ItemStack DEFAULT_ARROW_STACK = new ItemStack(Items.AIR);
    private ItemStack arrowItem = new ItemStack(Items.AIR);

    public MerorScytheShootEntity(EntityType<? extends MerorScytheShootEntity> entityType, Level level) {
        super(entityType, level, DEFAULT_ARROW_STACK);
    }
    public MerorScytheShootEntity(Level level, LivingEntity livingEntity, ItemStack itemStack) {
        super(JerotesVillageEntityType.MEROR_SCYTHE_SHOOT.get(), livingEntity, level, itemStack);
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

    @Override
    public void tick() {
        super.tick();
        if (this.level().isClientSide && !this.inGround) {
            this.level().addParticle(JerotesVillageParticleTypes.MEROR_SCYTHE_SHOOT.get(), this.getX(), this.getY(), this.getZ(), 0.0, 0.0, 0.0);
        }
        if (this.getOwner() == null || (this.getOwner() instanceof Player player && (!player.isUsingItem() || !(player.getUseItem().getItem() instanceof MerorScythe)))) {
            this.discard();
        }
    }

    @Override
    protected boolean canHitEntity(Entity entity) {
        return super.canHitEntity(entity) || entity.isAlive() || entity instanceof ItemEntity;
    }

    @Override
    protected void onHitEntity(EntityHitResult entityHitResult) {
        if (this.level().isClientSide) {
            return;
        }
        if (this.getOwner() == entityHitResult.getEntity()) {
            return;
        }
        if (entityHitResult.getEntity() instanceof LivingEntity truesScythe && this.getOwner() != null){
            double d = 0;
            if (truesScythe.getAttribute(Attributes.KNOCKBACK_RESISTANCE) != null) {
                d = truesScythe.getAttributeValue(Attributes.KNOCKBACK_RESISTANCE);
            }
            Entity owner = this.getOwner();
            double d3 = Math.max(0.25, 1.25 - d);
            truesScythe.setOnGround(false);
            truesScythe.setDeltaMovement(truesScythe.getDeltaMovement().add((owner.getX() - truesScythe.getX()) * 0.4 * d3, 0, 0));
            truesScythe.setDeltaMovement(truesScythe.getDeltaMovement().add(0, (owner.getY() - truesScythe.getY()) * 0.325 * d3, 0));
            truesScythe.setDeltaMovement(truesScythe.getDeltaMovement().add(0, 0, (owner.getZ() - truesScythe.getZ()) * 0.4 * d3));
            if (truesScythe.distanceTo(owner) <= 2) {
                truesScythe.setDeltaMovement(0d, 0d, 0d);
            }
        }
        if (entityHitResult.getEntity() instanceof ItemEntity truesScythe && this.getOwner() != null){
            Entity owner = this.getOwner();
            truesScythe.moveTo(owner.getX(), owner.getY(), owner.getZ());
        }
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
        if (compoundTag.contains("Arrow", 10)) {
            this.arrowItem = ItemStack.of(compoundTag.getCompound("Arrow"));
        }
    }

    @Override
    public void addAdditionalSaveData(CompoundTag compoundTag) {
        super.addAdditionalSaveData(compoundTag);
        compoundTag.put("Arrow", this.arrowItem.save(new CompoundTag()));
    }

    @Override
    protected float getWaterInertia() {
        return 1.0f;
    }
}
