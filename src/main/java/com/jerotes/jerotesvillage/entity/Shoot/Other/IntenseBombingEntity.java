package com.jerotes.jerotesvillage.entity.Shoot.Other;

import com.jerotes.jerotes.util.Main;
import com.jerotes.jerotesvillage.init.JerotesVillageEntityType;
import com.jerotes.jerotesvillage.init.JerotesVillageItems;
import net.minecraft.core.BlockPos;
import net.minecraft.core.particles.ParticleOptions;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.entity.projectile.Fireball;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.GameRules;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.BaseFireBlock;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.EntityHitResult;

public class IntenseBombingEntity extends Fireball {
    private int spellLevelDamage = 1;
    private int spellLevelFireTime = 1;
    private float spellLevelExplode = 1;
    @Override
    public void addAdditionalSaveData(CompoundTag compoundTag) {
        super.addAdditionalSaveData(compoundTag);
        compoundTag.putInt("Life", this.life);
        compoundTag.putInt("SpellLevelDamage", this.spellLevelDamage);
        compoundTag.putInt("SpellLevelFireTime", this.spellLevelFireTime);
        compoundTag.putFloat("SpellLevelExplode", this.spellLevelExplode);
    }
    @Override
    public void readAdditionalSaveData(CompoundTag compoundTag) {
        super.readAdditionalSaveData(compoundTag);
        this.life = compoundTag.getInt("Life");
        this.spellLevelDamage = compoundTag.getInt("SpellLevelDamage");
        this.spellLevelFireTime = compoundTag.getInt("SpellLevelFireTime");
        this.spellLevelExplode = compoundTag.getFloat("SpellLevelExplode");
    }

    public IntenseBombingEntity(EntityType<? extends IntenseBombingEntity> entityType, Level level) {
        super(entityType, level);
    }

    public IntenseBombingEntity(EntityType<? extends IntenseBombingEntity> entityType, double d, double d2, double d3, double d4, double d5, double d6, Level level) {
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

    public IntenseBombingEntity(int spellLevelDamage, int spellLevelFireTime, float spellLevelExplode, Level level, LivingEntity livingEntity, double d, double d2, double d3) {
        super(JerotesVillageEntityType.INTENSE_BOMBING.get(), livingEntity, d, d2, d3, level);
        this.spellLevelDamage = spellLevelDamage;
        this.spellLevelFireTime = spellLevelFireTime;
        this.spellLevelExplode = spellLevelExplode;
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
        if (entity instanceof LivingEntity) {
            Entity entity2 = this.getOwner();
            entity.setSecondsOnFire(spellLevelFireTime);
            entity.hurt(this.damageSources().fireball(this, entity2),
                    Main.rollDice(spellLevelDamage, 8, this.random)
            );
            boolean bl = this.level().getGameRules().getBoolean(GameRules.RULE_MOBGRIEFING);
            if (bl) {
                this.level().explode(entity, this.getX(), this.getY(), this.getZ(), spellLevelExplode, bl, Level.ExplosionInteraction.MOB);
            }
            else {
                this.level().explode(entity, this.getX(), this.getY(), this.getZ(), spellLevelExplode, Level.ExplosionInteraction.NONE);
            }
            this.discard();
        }
    }


    @Override
    protected void onHitBlock(BlockHitResult blockHitResult) {
        super.onHitBlock(blockHitResult);
        if (this.level().isClientSide) {
            return;
        }
        Entity entity = this.getOwner();
        if (entity instanceof LivingEntity) {
            if (!(entity instanceof Mob) || this.level().getGameRules().getBoolean(GameRules.RULE_MOBGRIEFING)) {
                BlockPos blockPos = blockHitResult.getBlockPos().relative(blockHitResult.getDirection());
                if (this.level().isEmptyBlock(blockPos)) {
                    this.level().setBlockAndUpdate(blockPos, BaseFireBlock.getState(this.level(), blockPos));
                }
            }
            boolean bl = this.level().getGameRules().getBoolean(GameRules.RULE_MOBGRIEFING);
            if (bl) {
                this.level().explode(entity, this.getX(), this.getY(), this.getZ(), spellLevelExplode, bl, Level.ExplosionInteraction.MOB);
            }
            else {
                this.level().explode(entity, this.getX(), this.getY(), this.getZ(), spellLevelExplode, Level.ExplosionInteraction.NONE);
            }
            this.discard();
        }
    }

    @Override
    public void playerTouch(Player player) {
        if (this.ownedBy(player) || this.getOwner() == null) {
            super.playerTouch(player);
        }
    }

    public int life = 0;

    @Override
    public void tick() {
        super.tick();
        if (this.life >= 60) {
            Entity entity = this.getOwner();
            boolean bl = this.level().getGameRules().getBoolean(GameRules.RULE_MOBGRIEFING);
            if (bl) {
                this.level().explode(entity, this.getX(), this.getY(), this.getZ(), spellLevelExplode, bl, Level.ExplosionInteraction.MOB);
            }
            else {
                this.level().explode(entity, this.getX(), this.getY(), this.getZ(), spellLevelExplode, Level.ExplosionInteraction.NONE);
            }
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
        return ParticleTypes.FLAME;
    }

    @Override
    protected boolean shouldBurn() {
        return false;
    }


    @Override
    public ItemStack getItem() {
        ItemStack itemStack = this.getItemRaw();
        return itemStack.isEmpty() ? new ItemStack(JerotesVillageItems.INTENSE_BOMBING.get()) : itemStack;
    }

    @Override
    protected float getInertia() {
        return 0.95f;
    }

    //@Override
    protected float getLiquidInertia() {
        return 0.8f;
    }
}
