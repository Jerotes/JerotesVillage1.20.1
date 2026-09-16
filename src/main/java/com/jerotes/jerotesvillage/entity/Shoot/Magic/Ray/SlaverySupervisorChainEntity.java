package com.jerotes.jerotesvillage.entity.Shoot.Magic.Ray;

import com.jerotes.jerotes.entity.Shoot.Magic.Ray.BaseRayEntity;
import com.jerotes.jerotes.util.EntityAndItemFind;
import com.jerotes.jerotes.util.Main;
import com.jerotes.jerotesvillage.init.JerotesVillageEntityType;
import com.jerotes.jerotesvillage.init.JerotesVillageItems;
import net.minecraft.core.particles.BlockParticleOption;
import net.minecraft.core.particles.ParticleOptions;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.Rarity;
import net.minecraft.world.level.GameRules;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.phys.HitResult;

public class SlaverySupervisorChainEntity extends BaseRayEntity {
    public SlaverySupervisorChainEntity(EntityType<? extends SlaverySupervisorChainEntity> entityType, Level level) {
        super(entityType, level);
    }

    public SlaverySupervisorChainEntity(EntityType<? extends SlaverySupervisorChainEntity> entityType, double d, double d2, double d3, double d4, double d5, double d6, Level level) {
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

    public SlaverySupervisorChainEntity(int spellLevelDamage, Level level, LivingEntity livingEntity, double d, double d2, double d3) {
        super(JerotesVillageEntityType.SLAVERY_SUPERVISOR_CHAIN.get(), livingEntity, d, d2, d3, level);
        this.spellLevelDamage = spellLevelDamage;
        this.summonTod = d;
        this.summonTod2 = d2;
        this.summonTod3 = d3;
    }

    public boolean showHitParticle() {
        return false;
    }
    protected void hitEntity(Entity entity) {
        super.hitEntity(entity);
        if (!this.isUseful())
            return;
        this.setUseful(false);
        if (this.level().isClientSide) {
            return;
        }
        if (entity instanceof LivingEntity livingEntity && (this.getOwner() instanceof LivingEntity livingEntity2)) {
            double damage = spellLevelDamage;
            if (livingEntity2.getAttribute(Attributes.ATTACK_DAMAGE) != null) {
                damage = livingEntity2.getAttributeValue(Attributes.ATTACK_DAMAGE) / 5 * spellLevelDamage;
            }
            boolean bl = livingEntity.hurt(this.damageSources().thrown(this, livingEntity2), (float) damage);
            if (bl) {
                if (livingEntity.getHealth() <= livingEntity2.getHealth() * 2) {
                    if ((Main.mobSizeSmall(livingEntity) || Main.mobSizeMedium(livingEntity) || Main.mobSizeLarge(livingEntity))) {
                        //牵扯
                        double d = 0;
                        if (livingEntity.getAttribute(Attributes.KNOCKBACK_RESISTANCE) != null) {
                            d = livingEntity.getAttributeValue(Attributes.KNOCKBACK_RESISTANCE);
                        }
                        double d3 = Math.max(0.25, 1.25 - d);
                        entity.setDeltaMovement((this.getX() - entity.getX()) * 0.6 * d3, (this.getY() - entity.getY()) * 0.5 * d3, (this.getZ() - entity.getZ()) * 0.6 * d3);
                    }
                     //缴械
                    boolean bl2 = this.level().getGameRules().getBoolean(GameRules.RULE_MOBGRIEFING) || this.getOwner() instanceof Player player;
                    boolean bl3 = this.level().getGameRules().getBoolean(GameRules.RULE_KEEPINVENTORY);
                    if (bl2 && !EntityAndItemFind.isUnableDisarm(entity)) {
                        if (livingEntity.getMainHandItem().getItem().getRarity(livingEntity.getMainHandItem()) != Rarity.EPIC) {
                            if (!(livingEntity instanceof Player player && (bl3 || player.getAbilities().instabuild))) {
                                entity.spawnAtLocation(livingEntity.getMainHandItem());
                                entity.setItemSlot(EquipmentSlot.MAINHAND, new ItemStack(Items.AIR.asItem()));
                            }
                        }
                    }
                }
            }
            this.playSound(SoundEvents.CHAIN_BREAK, 10.0f, 1.0f);
        }
    }

    @Override
    protected void onHit(HitResult hitResult) {
        super.onHit(hitResult);
        if (!this.isUseful())
            return;
        this.setUseful(false);
    }
    protected void afterHasLineOfSight() {
        super.afterHasLineOfSight();
        if (!this.isUseful())
            return;
        this.setUseful(false);
    }

    public BaseRayEntity getRay() {
        return new SlaverySupervisorChainEntity(spellLevelDamage, this.level(), (LivingEntity) this.getOwner(), summonTod, summonTod2, summonTod3);
    }

    @Override
    public int getMaxLife() {
        return 40;
    }

    @Override
    protected ParticleOptions getTrailParticle() {
        return new BlockParticleOption(ParticleTypes.BLOCK, Blocks.CHAIN.defaultBlockState());
    }
    public boolean showParticle() {
        return true;
    }
    public boolean showBeam() {
        return false;
    }

    @Override
    public ItemStack getItem() {
        ItemStack itemStack = this.getItemRaw();
        if (this.isUseful()) {
            return itemStack.isEmpty() ? new ItemStack(JerotesVillageItems.SLAVERY_SUPERVISOR_CHAIN.get()) : itemStack;
        }
        return itemStack.isEmpty() ? new ItemStack(Items.AIR) : itemStack;
    }

    @Override
    protected float getInertia() {
        return 1.0f;
    }

    //@Override
    protected float getLiquidInertia() {
        return 1.0f;
    }


    public int beamLightI() {
        return 0x322727;
    }
    public int beamLightII() {
        return 0x51444e;
    }
}
