package com.jerotes.jerotesvillage.entity.Shoot.Magic.MagicSpray;

import com.jerotes.jerotes.entity.Shoot.Magic.MagicSpray.BaseMagicSprayEntity;
import com.jerotes.jerotes.init.JerotesDamageTypes;
import com.jerotes.jerotes.util.AttackFind;
import com.jerotes.jerotes.util.Main;
import com.jerotes.jerotesvillage.init.JerotesVillageEntityType;
import com.jerotes.jerotesvillage.init.JerotesVillageItems;
import net.minecraft.core.BlockPos;
import net.minecraft.core.particles.ParticleOptions;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.GameRules;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.BaseFireBlock;
import net.minecraft.world.level.block.state.BlockState;

public class SerponFlamesEntity extends BaseMagicSprayEntity {
    public SerponFlamesEntity(EntityType<? extends SerponFlamesEntity> entityType, Level level) {
        super(entityType, level);
    }

    public SerponFlamesEntity(EntityType<? extends SerponFlamesEntity> entityType, double d, double d2, double d3, double d4, double d5, double d6, Level level) {
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

    public SerponFlamesEntity(int spellLevelDamage, int spellLevelFireTime, Level level, LivingEntity livingEntity, double d, double d2, double d3) {
        super(JerotesVillageEntityType.SERPON_FLAMES.get(), livingEntity, d, d2, d3, level);
        this.spellLevelDamage = spellLevelDamage;
        this.spellLevelFireTime = spellLevelFireTime;
    }

    public void useMagicTo(LivingEntity livingEntity) {
        super.useMagicTo(livingEntity);
        if (this.level().isClientSide) {
            return;
        }
        Entity entity2 = this.getOwner();
        if (entity2 instanceof LivingEntity livingEntity2) {
            DamageSource damageSource = AttackFind.findDamageType(this, JerotesDamageTypes.BYPASSES_COOLDOWN_FIRE, this, this.getOwner());
            if (!livingEntity.hurt(damageSource,
                    Main.rollDice(6 + (spellLevelDamage - 3), 6, livingEntity2.getRandom()) / 30
            )) {
                livingEntity.hurt(this.damageSources().indirectMagic(this, entity2),
                        Main.rollDice(6 + (spellLevelDamage - 3), 6, livingEntity2.getRandom()) / 30
                );
            } else {
                livingEntity.setSecondsOnFire(spellLevelFireTime);
            }
        }
    }
    @Override
    protected void handleBlock(BlockPos blockPos, BlockState state) {
        if (this.level().isClientSide()) {
            return;
        }
        Main.placeFireOnFlammable(this.level(), blockPos, this.getOwner());
        Main.igniteBlock(this.level(), blockPos, this.getOwner());
    }

    @Override
    protected ParticleOptions getTrailParticle() {
        return ParticleTypes.FLAME;
    }

    @Override
    public ItemStack getItem() {
        ItemStack itemStack = this.getItemRaw();
        return itemStack.isEmpty() ? new ItemStack(JerotesVillageItems.SERPON_FLAMES.get()) : itemStack;
    }
    public int beamLightI() {
        return 0xff8114;
    }
    public int beamLightII() {
        return 0xffe2cb;
    }
    public int beamLightIII() {
        return 0xff1c14;
    }
}
