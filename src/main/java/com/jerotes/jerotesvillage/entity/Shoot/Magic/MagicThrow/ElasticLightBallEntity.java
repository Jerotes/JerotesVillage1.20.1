package com.jerotes.jerotesvillage.entity.Shoot.Magic.MagicThrow;

import com.jerotes.jerotes.entity.Shoot.Magic.MagicAboutThrowEntity;
import com.jerotes.jerotes.init.JerotesDamageTypes;
import com.jerotes.jerotes.util.AttackFind;
import com.jerotes.jerotes.util.Main;
import com.jerotes.jerotesvillage.init.JerotesVillageEntityType;
import com.jerotes.jerotesvillage.init.JerotesVillageItems;
import net.minecraft.ChatFormatting;
import net.minecraft.core.BlockPos;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.EntityHitResult;
import net.minecraft.world.phys.HitResult;

public class ElasticLightBallEntity extends MagicAboutThrowEntity {
    public ElasticLightBallEntity(EntityType<? extends ElasticLightBallEntity> entityType, Level level) {
        super(entityType, level);
    }

    public ElasticLightBallEntity(int spellLevelDamage, int spellLevelMainEffectTime, int spellLevelMainEffectLevel, Level level, LivingEntity livingEntity) {
        super(JerotesVillageEntityType.ELASTIC_LIGHT_BALL.get(), livingEntity, level);
        this.spellLevelDamage = spellLevelDamage;
        this.spellLevelMainEffectTime = spellLevelMainEffectTime;
        this.spellLevelMainEffectLevel = spellLevelMainEffectLevel;
    }

    public ElasticLightBallEntity(int spellLevelDamage, int spellLevelMainEffectTime, int spellLevelMainEffectLevel, Level level, double d, double d2, double d3) {
        super(JerotesVillageEntityType.ELASTIC_LIGHT_BALL.get(), d, d2, d3, level);
        this.spellLevelDamage = spellLevelDamage;
        this.spellLevelMainEffectTime = spellLevelMainEffectTime;
        this.spellLevelMainEffectLevel = spellLevelMainEffectLevel;
    }

    @Override
    protected void onHitEntity(EntityHitResult entityHitResult) {
        super.onHitEntity(entityHitResult);
        if (this.level().isClientSide) {
            return;
        }
        Entity entity = entityHitResult.getEntity();

        if (entity instanceof LivingEntity livingEntity) {
            DamageSource damageSource = AttackFind.findDamageType(this, JerotesDamageTypes.RADIANT, this, this.getOwner());
            boolean bl = livingEntity.hurt(damageSource,
                    Main.rollDice(4 + (getSpellLevel() - 3), 4, livingEntity.getRandom())
            );
            if (bl) {
                livingEntity.addEffect(new MobEffectInstance(MobEffects.GLOWING, 20 * spellLevelMainEffectTime, spellLevelMainEffectLevel-1), this.getEffectSource());
                if (this.getOwner() != null) {
                    String string = ChatFormatting.stripFormatting(this.getOwner().getName().getString());
                    if ("Soul Lampologer".equals(string) || "Soullampologer".equals(string) || "Lampologer".equals(string)) {
                        livingEntity.addEffect(new MobEffectInstance(MobEffects.WITHER, 20 * spellLevelMainEffectTime, spellLevelMainEffectLevel - 1), this.getEffectSource());
                    }
                }
            }
            this.playSound(SoundEvents.STONE_BREAK, 1.0f, 1.0f);
        }
    }

    @Override
    protected void onHit(HitResult hitResult) {
        super.onHit(hitResult);
        if (!this.level().isClientSide) {
            this.level().broadcastEntityEvent(this, (byte)3);
            BlockPos blockPos = this.getOnPos();
            BlockState blockState = this.level().getBlockState(blockPos);
            this.setDeltaMovement(this.getDeltaMovement().multiply(0.85, -0.75, 0.85));
        }
    }

    @Override
    public int getMaxLife() {
        return 120;
    }

    @Override
    protected Item getDefaultItem() {
        return JerotesVillageItems.GLOW_SHINE.get();
    }

    @Override
    public boolean canCollideWith(Entity entity) {
        return this.isAlive();
    }

    @Override
    public boolean canBeCollidedWith() {
        return this.isAlive();
    }

    @Override
    protected float getGravity() {
        return 0.05F;
    }
}
