package com.jerotes.jerotesvillage.entity.Shoot.Magic.Breath;

import com.jerotes.jerotes.entity.Shoot.Magic.Breath.BaseBreathEntity;
import com.jerotes.jerotes.init.JerotesParticleTypes;
import com.jerotes.jerotes.init.JerotesSoundEvents;
import com.jerotes.jerotes.util.AttackFind;
import com.jerotes.jerotes.util.EntityAndItemFind;
import com.jerotes.jerotes.util.Main;
import com.jerotes.jerotesvillage.init.JerotesVillageEntityType;
import com.jerotes.jerotesvillage.init.JerotesVillageItems;
import com.jerotes.jerotesvillage.init.JerotesVillageMobEffects;
import com.jerotes.jerotesvillage.init.JerotesVillageParticleTypes;
import net.minecraft.core.BlockPos;
import net.minecraft.core.particles.ParticleOptions;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.damagesource.DamageTypes;
import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.EntityHitResult;

public class BloodyScreamEntity extends BaseBreathEntity {
    public BloodyScreamEntity(EntityType<? extends BloodyScreamEntity> entityType, Level level) {
        super(entityType, level);
    }

    public BloodyScreamEntity(EntityType<? extends BloodyScreamEntity> entityType, double d, double d2, double d3, double d4, double d5, double d6, Level level) {
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

    public BloodyScreamEntity(int spellLevelDamage, int spellLevelMainEffectTime, int spellLevelMainEffectLevel, Level level, LivingEntity livingEntity, double d, double d2, double d3) {
        super(JerotesVillageEntityType.BLOODY_SCREAM.get(), livingEntity, d, d2, d3, level);
        this.spellLevelDamage = spellLevelDamage;
        this.spellLevelMainEffectTime = spellLevelMainEffectTime;
        this.spellLevelMainEffectLevel = spellLevelMainEffectLevel;
    }

    protected void onHitEntity(EntityHitResult entityHitResult) {
        super.onHitEntity(entityHitResult);
        if (this.level().isClientSide) {
            return;
        }
        Entity entity = entityHitResult.getEntity();

        if (entity instanceof LivingEntity livingEntity) {
            boolean bl = false;
            boolean bl2 = false;
            DamageSource damageSource = AttackFind.findDamageType(this, DamageTypes.INDIRECT_MAGIC, this, this.getOwner());
            if (!EntityAndItemFind.savingSpellThrow(livingEntity)) {
                bl = livingEntity.hurt(damageSource,
                        Main.rollDice(spellLevelDamage * 2, 6, this.random)
                );
                if (bl) {
                    livingEntity.addEffect(new MobEffectInstance(JerotesVillageMobEffects.UNCLEAN_BODY.get(), 20 * spellLevelMainEffectTime, spellLevelMainEffectLevel - 1), this.getEffectSource());
                }
            }
            else {
                bl2 = livingEntity.hurt(damageSource, Main.rollDice(spellLevelDamage * 2, 6, this.random) / 2);
            }
            if (bl || bl2) {
                this.playSound(JerotesSoundEvents.SPELL, 1.0f, 1.0f);
            }
        }
    }

    @Override
    protected void onHitBlock(BlockHitResult blockHitResult) {
        super.onHitBlock(blockHitResult);
        if (this.level().getBlockState(BlockPos.containing(blockHitResult.getLocation().x, blockHitResult.getLocation().y, blockHitResult.getLocation().z)).getBlock() == Blocks.WATER) {
            return;
        }
        if (!this.level().isClientSide) {
            this.discard();
        }
    }

    @Override
    public boolean hasCloud() {
        return false;
    }
    @Override
    public float getCloudRadius() {
        return 1f;
    }
    @Override
    public float getCloudEffectTimeMultiple() {
        return 1f;
    }
    @Override
    public ParticleOptions getCloudParticle() {
        return JerotesParticleTypes.NULL.get();
    }
    @Override
    public MobEffect getCloudEffect() {
        return JerotesVillageMobEffects.UNCLEAN_BODY.get();
    }

    @Override
    public int getMaxLife() {
        return 30;
    }
    @Override
    public float getMaxReach() {
        return 9.0f;
    }

    @Override
    protected ParticleOptions getTrailParticle() {
        return JerotesVillageParticleTypes.BLOODY_SCREAM.get();
    }

    @Override
    public ItemStack getItem() {
        ItemStack itemStack = this.getItemRaw();
        return itemStack.isEmpty() ? new ItemStack(JerotesVillageItems.BLOODY_SCREAM.get()) : itemStack;
    }

    @Override
    protected float getInertia() {
        return 0.9f;
    }

    //@Override
    protected float getLiquidInertia() {
        return 0.5f;
    }
}