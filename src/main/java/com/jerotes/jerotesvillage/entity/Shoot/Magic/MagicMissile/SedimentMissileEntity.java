package com.jerotes.jerotesvillage.entity.Shoot.Magic.MagicMissile;

import com.jerotes.jerotes.entity.Shoot.Magic.MagicMissile.BaseMagicMissileEntity;
import com.jerotes.jerotes.init.JerotesDamageTypes;
import com.jerotes.jerotes.init.JerotesSoundEvents;
import com.jerotes.jerotes.util.AttackFind;
import com.jerotes.jerotes.util.Main;
import com.jerotes.jerotesvillage.JerotesVillage;
import com.jerotes.jerotesvillage.init.JerotesVillageEntityType;
import com.jerotes.jerotesvillage.init.JerotesVillageItems;
import com.jerotes.jerotesvillage.init.JerotesVillageParticleTypes;
import net.minecraft.core.particles.ParticleOptions;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.EntityHitResult;

public class SedimentMissileEntity extends BaseMagicMissileEntity {
    public SedimentMissileEntity(EntityType<? extends SedimentMissileEntity> entityType, Level level) {
        super(entityType, level);
    }

    public SedimentMissileEntity(EntityType<? extends SedimentMissileEntity> entityType, double d, double d2, double d3, double d4, double d5, double d6, Level level) {
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

    public SedimentMissileEntity(int spellLevelDamage, Level level, LivingEntity livingEntity, double d, double d2, double d3) {
        super(JerotesVillageEntityType.SEDIMENT_MISSILE.get(), livingEntity, d, d2, d3, level);
        this.spellLevelDamage = spellLevelDamage;
    }

    protected void onHitEntity(EntityHitResult entityHitResult) {
        super.onHitEntity(entityHitResult);
        if (this.level().isClientSide) {
            return;
        }
        Entity entity = entityHitResult.getEntity();
        if (this.getOwner() instanceof LivingEntity && entity != this.getTarget() && this.getTarget() != null) {
            return;
        }
        if (entity instanceof LivingEntity livingEntity) {
            DamageSource damageSource = AttackFind.findDamageType(this, JerotesDamageTypes.NECROTIC, this, this.getOwner());
            livingEntity.hurt(damageSource,
                    Main.rollDice(1, 4, livingEntity.getRandom()) + 1
            );
            this.playSound(JerotesSoundEvents.SPELL, 1.0f, 1.0f);
            this.discard();
        }
    }
    @Override
    protected void onHitBlock(BlockHitResult blockHitResult) {
        super.onHitBlock(blockHitResult);
        if (this.getTarget() == null)
            this.discard();
    }

    @Override
    public int getMaxLife() {
        return 60;
    }
    public int getMaxReach() {
        return 36;
    }

    protected boolean shouldTail() {
        return false;
    }

    @Override
    protected ParticleOptions getTrailParticle() {
        return JerotesVillageParticleTypes.SEDIMENT_MISSILE.get();
    }
    @Override
    public ItemStack getItem() {
        ItemStack itemStack = this.getItemRaw();
        return itemStack.isEmpty() ? new ItemStack(JerotesVillageItems.SEDIMENT_MISSILE.get()) : itemStack;
    }
    public int beamLightI() {
        return 0x69a7cc;
    }
    public int beamLightII() {
        return 0x89c7cc;
    }
    public int beamLightIII() {
        return 0x005f98;
    }
    public ResourceLocation TextureLocation() {
        return new ResourceLocation(JerotesVillage.MODID, "textures/entity/projectiles/sediment_missile.png");
    }
}
