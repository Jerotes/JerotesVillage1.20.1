package com.jerotes.jerotesvillage.entity.Shoot.Magic.MagicShoot;

import com.jerotes.jerotes.entity.Shoot.Magic.MagicAboutEntity;
import com.jerotes.jerotes.init.JerotesDamageTypes;
import com.jerotes.jerotes.util.AttackFind;
import com.jerotes.jerotes.util.Main;
import com.jerotes.jerotesvillage.init.JerotesVillageEntityType;
import com.jerotes.jerotesvillage.init.JerotesVillageItems;
import com.jerotes.jerotesvillage.init.JerotesVillageParticleTypes;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.damagesource.DamageTypes;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;

import java.util.List;

public class SerponSiroccoEntity extends MagicAboutEntity {
    public SerponSiroccoEntity(EntityType<? extends SerponSiroccoEntity> entityType, Level level) {
        super(entityType, level);
    }

    public SerponSiroccoEntity(EntityType<? extends SerponSiroccoEntity> entityType, double d, double d2, double d3, double d4, double d5, double d6, Level level) {
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

    public boolean isOnFire() {
        return false;
    }

    public SerponSiroccoEntity(int spellLevelDamage, Level level, LivingEntity livingEntity, double d, double d2, double d3) {
        super(JerotesVillageEntityType.SERPON_SIROCCO.get(), livingEntity, d, d2, d3, level);
        this.spellLevelDamage = spellLevelDamage;
    }

    @Override
    public void tick() {
        super.tick();
        for (int i = 0; i < 3; ++i) {
            for (int i2 = 0; i2 < 2; ++i2) {
                this.level().addParticle(JerotesVillageParticleTypes.SERPON_SIROCCO.get(), this.getRandomX(0.75f), this.getY((this.random.nextDouble()) * 0.5) + 0.5f, this.getRandomZ(0.75f), 0.0d, 0.0d, 0.0d);
                this.level().addParticle(JerotesVillageParticleTypes.SERPON_SIROCCO.get(), this.getRandomX(0.25f), this.getY((2.0D * this.random.nextDouble() - 1.0D) * 0.5), this.getRandomZ(0.25f), 0.0d, 0.0d, 0.0d);
            }
            this.level().addParticle(ParticleTypes.FLAME, this.getRandomX(0.75f), this.getY((this.random.nextDouble()) * 0.5) + 0.5f, this.getRandomZ(0.75f), 0.0d, 0.0d, 0.0d);
            this.level().addParticle(ParticleTypes.FLAME, this.getRandomX(0.25f), this.getY((2.0D * this.random.nextDouble() - 1.0D) * 0.5), this.getRandomZ(0.25f), 0.0d, 0.0d, 0.0d);
        }

        List<LivingEntity> list = this.level().getEntitiesOfClass(LivingEntity.class, this.getBoundingBox().inflate(0.5f));
        if (!list.isEmpty()) {
            for (LivingEntity attack : list) {
                if (attack == null) continue;
                if (attack == this.getLastHurt() && !this.canNotHurtLastHurt() || getHurtUUIDs().contains(attack.getUUID())) continue;
                if (!(this.getOwner() instanceof LivingEntity livingEntityOwner)) continue;
                if (AttackFind.FindCanNotAttack(livingEntityOwner, attack)) continue;
                this.setLastHurt(attack);
                this.addHurtUUID(attack.getUUID());
                Entity entity2 = this.getOwner();
                DamageSource damageSource = AttackFind.findDamageType(this, JerotesDamageTypes.FIRE_MAGIC, this, entity2);
                DamageSource damageSource2 = AttackFind.findDamageType(this, DamageTypes.EXPLOSION, this, entity2);
                DamageSource damageSource3 = AttackFind.findDamageType(this, DamageTypes.FLY_INTO_WALL, this, entity2);
                boolean bl = attack.hurt(damageSource,
                        Main.rollDice(spellLevelDamage + 1, 6, this.random)
                );
                if (!bl) {
                    boolean bl2 = attack.hurt(damageSource2,
                            Main.rollDice(spellLevelDamage + 1, 6, this.random)
                    );
                    if (!bl2) {
                        attack.hurt(damageSource3,
                                Main.rollDice(spellLevelDamage + 1, 6, this.random)
                        );
                    }
                }
                attack.setDeltaMovement((attack.getX() - this.getX()) / 3, (attack.getY() - this.getY()) / 3, (attack.getZ() - this.getZ()) / 3);
                if (!this.isSilent()) {
                    this.playSound(SoundEvents.PLAYER_ATTACK_KNOCKBACK, 10.0f, 1.0f);
                }
            }
        }
    }

    @Override
    public int getMaxLife() {
        return 40;
    }

    @Override
    public ItemStack getItem() {
        ItemStack itemStack = this.getItemRaw();
        return itemStack.isEmpty() ? new ItemStack(JerotesVillageItems.SERPON_SIROCCO.get()) : itemStack;
    }

    @Override
    protected float getInertia() {
        return 1.0f;
    }

    //@Override
    protected float getLiquidInertia() {
        return 1.0f;
    }
}
