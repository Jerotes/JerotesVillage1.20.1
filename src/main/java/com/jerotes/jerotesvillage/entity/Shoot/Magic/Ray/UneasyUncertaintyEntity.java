package com.jerotes.jerotesvillage.entity.Shoot.Magic.Ray;

import com.jerotes.jerotes.entity.Shoot.Magic.Ray.BaseRayEntity;
import com.jerotes.jerotes.init.JerotesDamageTypes;
import com.jerotes.jerotes.init.JerotesParticleTypes;
import com.jerotes.jerotes.init.JerotesSoundEvents;
import com.jerotes.jerotes.util.AttackFind;
import com.jerotes.jerotes.util.EntityAndItemFind;
import com.jerotes.jerotes.util.Main;
import com.jerotes.jerotesvillage.init.JerotesVillageEntityType;
import net.minecraft.core.particles.ParticleOptions;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.Rarity;
import net.minecraft.world.level.GameRules;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.HitResult;

public class UneasyUncertaintyEntity extends BaseRayEntity {
    public UneasyUncertaintyEntity(EntityType<? extends UneasyUncertaintyEntity> entityType, Level level) {
        super(entityType, level);
    }

    public UneasyUncertaintyEntity(EntityType<? extends UneasyUncertaintyEntity> entityType, double d, double d2, double d3, double d4, double d5, double d6, Level level) {
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

    public UneasyUncertaintyEntity(int spellLevelDamage, Level level, LivingEntity livingEntity, double d, double d2, double d3) {
        super(JerotesVillageEntityType.UNEASY_UNCERTAINTY.get(), livingEntity, d, d2, d3, level);
        this.spellLevelDamage = spellLevelDamage;
        this.summonTod = d;
        this.summonTod2 = d2;
        this.summonTod3 = d3;
    }

    protected void hitEntity(Entity entity) {
        super.hitEntity(entity);
        if (!this.isUseful())
            return;
        this.setUseful(false);
        if (this.level().isClientSide) {
            return;
        }
        if (entity instanceof LivingEntity livingEntity) {
            Entity entity2 = this.getOwner();
            DamageSource damageSource = AttackFind.findDamageType(livingEntity, JerotesDamageTypes.PSYCHIC, this, entity2);
            if (!EntityAndItemFind.savingSpellThrow(livingEntity)) {
                boolean bl = livingEntity.hurt(damageSource,
                        Main.rollDice(6 + (spellLevelDamage - 4), 6, livingEntity.getRandom())
                );
                if (bl) {
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
            } else {
                livingEntity.hurt(damageSource,
                        Main.rollDice(6 + (spellLevelDamage - 4), 6, livingEntity.getRandom()) / 2f
                );
            }
            this.playSound(JerotesSoundEvents.SPELL, 1.0f, 1.0f);
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
        return new UneasyUncertaintyEntity(this.spellLevelDamage, this.level(), (LivingEntity) this.getOwner(), summonTod, summonTod2, summonTod3);
    }

    @Override
    public int getMaxLife() {
        return 40;
    }

    @Override
    protected ParticleOptions getTrailParticle() {
        return JerotesParticleTypes.GUST.get();
    }

    public int getMaxReach() {
        return 36;
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

    public boolean showHitParticle() {
        return false;
    }

    //@Override
    protected float getLiquidInertia() {
        return 1.0f;
    }
    public int beamLightI() {
        return 0x39ad72;
    }
    public int beamLightII() {
        return 0x7cffa8;
    }

    public int beamLightIII() {
        return 0xff5ca8;
    }
    public int beamLightIV() {
        return 0xffcd4a;
    }
    public int beamLightV() {
        return 0x120a18;
    }
}
