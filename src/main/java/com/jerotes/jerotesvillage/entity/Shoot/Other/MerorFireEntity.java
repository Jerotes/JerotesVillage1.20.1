package com.jerotes.jerotesvillage.entity.Shoot.Other;

import com.jerotes.jerotes.init.JerotesDamageTypes;
import com.jerotes.jerotes.util.AttackFind;
import com.jerotes.jerotes.util.Main;
import com.jerotes.jerotesvillage.init.JerotesVillageEntityType;
import com.jerotes.jerotesvillage.init.JerotesVillageParticleTypes;
import net.minecraft.core.BlockPos;
import net.minecraft.core.particles.ParticleOptions;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.entity.projectile.Fireball;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.GameRules;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.BaseFireBlock;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.EntityHitResult;

public class MerorFireEntity extends Fireball {
    public static final float SPLASH_RANGE = 4.0f;

    public MerorFireEntity(EntityType<? extends MerorFireEntity> entityType, Level level) {
        super(entityType, level);
    }


    public MerorFireEntity(EntityType<? extends MerorFireEntity> entityType, double d, double d2, double d3, double d4, double d5, double d6, Level level) {
        this(entityType, level);
        this.moveTo(d, d2, d3, this.getYRot(), this.getXRot());
        this.reapplyPosition();
        double d7 = Math.sqrt(d4 * d4 + d5 * d5 + d6 * d6);
        if (d7 != 0.0) {
            this.xPower = d4 / d7 * 0.1;
            this.yPower = d5 / d7 * 0.1;
            this.zPower = d6 / d7 * 0.1;
        }
    }

    public MerorFireEntity(Level level, LivingEntity livingEntity, double d, double d2, double d3) {
        super(JerotesVillageEntityType.MEROR_FIRE.get(), livingEntity, d, d2, d3, level);
    }

    protected void onHitEntity(EntityHitResult entityHitResult) {
        if (entityHitResult.getEntity() == this.getOwner()) {
            return;
        }
        super.onHitEntity(entityHitResult);
        if (this.level().isClientSide) {
            return;
        }
        Entity entity = entityHitResult.getEntity();
        Entity entity2 = this.getOwner();
        DamageSource damageSource = AttackFind.findDamageType(this, JerotesDamageTypes.BYPASSES_COOLDOWN_FIRE, this, this.getOwner());
        DamageSource damageSource2 = AttackFind.findDamageType(this, JerotesDamageTypes.BYPASSES_COOLDOWN_SHOOT, this, this.getOwner());
        if (!entity.hurt(damageSource, 7.0f)) {
            entity.hurt(damageSource2, 5.0f);
        } {
            entity.setSecondsOnFire(8);
        }
        this.doEnchantDamageEffects((LivingEntity)entity2, entity);
        this.level().broadcastEntityEvent(this, (byte)3);
    }


    @Override
    protected void onHitBlock(BlockHitResult blockHitResult) {
        super.onHitBlock(blockHitResult);
        if (this.level().isClientSide) {
            return;
        }
        if ((this.level().getBlockState(BlockPos.containing(blockHitResult.getLocation().x, blockHitResult.getLocation().y, blockHitResult.getLocation().z))).getBlock() == Blocks.WATER) {
            return;
        }
        BlockPos blockPos = blockHitResult.getBlockPos().relative(blockHitResult.getDirection());
        BlockPos blockPos2 = blockHitResult.getBlockPos();
        Main.igniteBlock(this.level(), blockPos, this.getOwner());
        Main.igniteBlock(this.level(), blockPos2, this.getOwner());
        Main.placeFire(this.level(), blockPos, this.getOwner());
        Main.placeFire(this.level(), blockPos2, this.getOwner());
    }

    @Override
    public void playerTouch(Player player) {
        if (this.ownedBy(player) || this.getOwner() == null) {
            super.playerTouch(player);
        }
    }

    public int life = 0;
    @Override
    public void readAdditionalSaveData(CompoundTag compoundTag) {
        super.readAdditionalSaveData(compoundTag);
        this.life = compoundTag.getInt("Life");
    }

    @Override
    public void addAdditionalSaveData(CompoundTag compoundTag) {
        super.addAdditionalSaveData(compoundTag);
        compoundTag.putInt("Life", this.life);
    }

    @Override
    public void tick() {
        super.tick();
        if (this.life >= 14) {
            this.discard();
        }
        else {
            this.life += 1;
        }
    }


    @Override
    public boolean isPickable() {
        return false;
    }

    @Override
    public boolean hurt(DamageSource damageSource, float f) {
        return false;
    }

    @Override
    protected ParticleOptions getTrailParticle() {
        return JerotesVillageParticleTypes.MEROR_FIRE.get();
    }

    @Override
    protected boolean shouldBurn() {
        return false;
    }


    @Override
    public ItemStack getItem() {
        ItemStack itemStack = this.getItemRaw();
        return itemStack.isEmpty() ? new ItemStack(Items.AIR) : itemStack;
    }

    @Override
    protected float getInertia() {
        return 1.0f;
    }

    //@Override
    protected float getLiquidInertia() {
        return 0.9f;
    }
}
