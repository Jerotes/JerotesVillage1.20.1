package com.jerotes.jerotesvillage.entity.Shoot.Arrow;

import com.jerotes.jerotes.entity.Shoot.Arrow.BaseAbstractArrowEntity;
import com.jerotes.jerotes.init.JerotesDamageTypes;
import com.jerotes.jerotes.util.AttackFind;
import com.jerotes.jerotesvillage.init.JerotesVillageEntityType;
import com.jerotes.jerotesvillage.init.JerotesVillageItems;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.syncher.EntityDataAccessor;
import net.minecraft.network.syncher.EntityDataSerializers;
import net.minecraft.network.syncher.SynchedEntityData;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.alchemy.PotionUtils;
import net.minecraft.world.item.enchantment.EnchantmentHelper;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.EntityHitResult;

import java.util.List;

public class GemstoneThrowingKnivesShootEntity extends BaseAbstractArrowEntity {
    private static final EntityDataAccessor<Boolean> ID_FOIL = SynchedEntityData.defineId(GemstoneThrowingKnivesShootEntity.class, EntityDataSerializers.BOOLEAN);
    private static final ItemStack DEFAULT_ARROW_STACK = new ItemStack(Items.AIR);
    private ItemStack arrowItem = new ItemStack(Items.AIR);

    public GemstoneThrowingKnivesShootEntity(EntityType<? extends GemstoneThrowingKnivesShootEntity> entityType, Level level) {
        super(entityType, level, DEFAULT_ARROW_STACK);
    }
    public GemstoneThrowingKnivesShootEntity(Level level, LivingEntity livingEntity, ItemStack itemStack) {
        super(JerotesVillageEntityType.GEMSTONE_THROWING_KNIVES_SHOOT.get(), livingEntity, level, itemStack);
        this.arrowItem = itemStack.copy();
        this.entityData.set(ID_FOIL, itemStack.hasFoil());
    }

    protected void doPostHurtEffects(LivingEntity livingEntity) {
        super.doPostHurtEffects(livingEntity);
        if (!this.level().isClientSide) {
            List<MobEffectInstance> list = PotionUtils.getMobEffects(this.getPickupItem());
            for (MobEffectInstance mobEffectInstance : list) {
                if (mobEffectInstance.getEffect().isInstantenous()) {
                    mobEffectInstance.getEffect().applyInstantenousEffect(this, this.getOwner() != null ? this.getOwner() : this, livingEntity, mobEffectInstance.getAmplifier(), 0.5);
                    continue;
                }
                MobEffect mobEffect = new MobEffectInstance(mobEffectInstance).getEffect();
                int amplifier = new MobEffectInstance(mobEffectInstance).getAmplifier();
                int duration = new MobEffectInstance(mobEffectInstance).getDuration() / 5;
                boolean bl = new MobEffectInstance(mobEffectInstance).isAmbient();
                boolean bl2 = new MobEffectInstance(mobEffectInstance).isVisible();
                if (this.getOwner() != null) {
                    livingEntity.addEffect(new MobEffectInstance(mobEffect, duration, amplifier, bl, bl2), this.getOwner());
                }
                else {
                    livingEntity.addEffect(new MobEffectInstance(mobEffect, duration, amplifier, bl, bl2));
                }
            }
        }
    }


    @Override
    protected ItemStack getPickupItem() {
       return arrowItem.copy();
    }

    @Override
    protected void defineSynchedData() {
        super.defineSynchedData();
        this.getEntityData().define(ID_FOIL, false);
    }

    @Override
    public void playerTouch(Player player) {
        if (this.level().isClientSide || !this.inGround && !this.isNoPhysics() || this.shakeTime > 0) {
            return;
        }
        if (player.getItemInHand(InteractionHand.MAIN_HAND).getItem() == JerotesVillageItems.GEMSTONE_THROWING_KNIVES.get() && player.getItemInHand(InteractionHand.MAIN_HAND).getDamageValue() >= 1){
            player.getItemInHand(InteractionHand.MAIN_HAND).hurtAndBreak(-1, player, player2 -> player2.broadcastBreakEvent(player.getUsedItemHand()));
            this.discard();
        }
    }

    public int life = 0;
    public int damage = 5;

    @Override
    public void tick() {
        super.tick();
        if (this.life >= 6000) {
            this.discard();
        }
        else {
            this.life += 1;
        }
    }

    @Override
    protected void onHitEntity(EntityHitResult entityHitResult) {
        Entity entity;
        Entity entity2 = entityHitResult.getEntity();
        float f = 2.0f;
        if (entity2 instanceof LivingEntity) {
            entity = (LivingEntity)entity2;
            f += EnchantmentHelper.getDamageBonus(this.arrowItem, ((LivingEntity)entity).getMobType());
        }
        DamageSource damageSource = this.damageSources().trident(this, (entity = this.getOwner()) == null ? this : entity);
        SoundEvent soundEvent = SoundEvents.TRIDENT_HIT;
        if (entity2.hurt(damageSource, f + (float)this.getDeltaMovement().length() * 2)) {
            if (entity2 instanceof LivingEntity) {
                LivingEntity livingEntity = (LivingEntity)entity2;
                if (entity instanceof LivingEntity) {
                    EnchantmentHelper.doPostHurtEffects(livingEntity, entity);
                    EnchantmentHelper.doPostDamageEffects((LivingEntity)entity, livingEntity);
                }
                this.doPostHurtEffects(livingEntity);
            }
        }
        if (damage > 0) {
            DamageSource damageSource2 = AttackFind.findDamageType(this, JerotesDamageTypes.BYPASSES_COOLDOWN_SHOOT, this, entity);
            entity2.hurt(damageSource2, damage);
            if (entity instanceof LivingEntity) {
                ((LivingEntity)entity).setLastHurtMob(entity2);
            }
            this.damage -= 1;
        }
        this.life += 300;
        this.setDeltaMovement(this.getDeltaMovement().multiply(-0.01, -0.1, -0.01));
        float f2 = 1.0f;
        this.playSound(soundEvent, f2, 1.0f);
    }

    protected SoundEvent getDefaultHitGroundSoundEvent() {
        return SoundEvents.PLAYER_ATTACK_STRONG;
    }

    @Override
    public boolean isPickable() {
        return false;
    }

    @Override
    public void readAdditionalSaveData(CompoundTag compoundTag) {
        super.readAdditionalSaveData(compoundTag);
        this.life = compoundTag.getInt("Life");
        this.damage = compoundTag.getInt("Damage");
        if (compoundTag.contains("Arrow", 10)) {
            this.arrowItem = ItemStack.of(compoundTag.getCompound("Arrow"));
        }
    }

    @Override
    public void addAdditionalSaveData(CompoundTag compoundTag) {
        super.addAdditionalSaveData(compoundTag);
        compoundTag.putInt("Life", this.life);
        compoundTag.putInt("Damage", this.damage);
        compoundTag.put("Arrow", this.arrowItem.save(new CompoundTag()));
    }

    @Override
    protected float getWaterInertia() {
        return 1.2f;
    }
}
