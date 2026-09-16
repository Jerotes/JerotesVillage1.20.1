package com.jerotes.jerotesvillage.entity.Shoot.Magic.Breath;

import com.jerotes.jerotes.entity.Shoot.Magic.Breath.BaseBreathEntity;
import com.jerotes.jerotes.init.JerotesDamageTypes;
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
import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.vehicle.Boat;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.EntityHitResult;

import java.util.List;

public class OceanCurrentBreathEntity extends BaseBreathEntity {
    public OceanCurrentBreathEntity(EntityType<? extends OceanCurrentBreathEntity> entityType, Level level) {
        super(entityType, level);
    }

    public OceanCurrentBreathEntity(EntityType<? extends OceanCurrentBreathEntity> entityType, double d, double d2, double d3, double d4, double d5, double d6, Level level) {
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

    public OceanCurrentBreathEntity(int spellLevelDamage, int spellLevelMainEffectTime, int spellLevelMainEffectLevel, Level level, LivingEntity livingEntity, double d, double d2, double d3) {
        super(JerotesVillageEntityType.OCEAN_CURRENT_BREATH.get(), livingEntity, d, d2, d3, level);
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
            //水类伤害
            float damageScale = 1;
            if (livingEntity.isSensitiveToWater()) {
                damageScale *= 3;
            }
            boolean bl = false;
            boolean bl2 = false;
            DamageSource damageSource = AttackFind.findDamageType(this, JerotesDamageTypes.DROWN_MAGIC, this, this.getOwner());
            if (livingEntity.hasEffect(MobEffects.WATER_BREATHING) || livingEntity.hasEffect(MobEffects.CONDUIT_POWER)) {
                damageScale /= 3;
            }

            if (!EntityAndItemFind.savingSpellThrow(livingEntity)) {
                bl = livingEntity.hurt(damageSource,
                        Main.rollDice(spellLevelDamage * 2, 6, this.random) * damageScale
                );
            }
            else {
                bl2 = livingEntity.hurt(damageSource,
                        Main.rollDice(spellLevelDamage * 2, 6, this.random) * damageScale / 2
                );
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
            this.Cloud(blockHitResult.getLocation().x, blockHitResult.getLocation().y, blockHitResult.getLocation().z);
            this.discard();
        }
    }

    @Override
    public void tick() {
        super.tick();
        List<Boat> list = this.level().getEntitiesOfClass(Boat.class, this.getBoundingBox().inflate(2, 2, 2));
        for (Boat boat : list) {
            if (this.isAlliedTo(boat) || this.distanceTo(boat) > 16.0)
                continue;
            this.spawnAtLocation(boat.getDropItem());
            boat.discard();
        }
    }

    @Override
    public boolean hasCloud() {
        return true;
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
        return JerotesVillageParticleTypes.OCEAN_CURRENT_BREATH_FOG.get();
    }
    @Override
    public MobEffect getCloudEffect() {
        return JerotesVillageMobEffects.MALIGNASAUR_ASPHYXIA.get();
    }

    @Override
    public int getMaxLife() {
        return 30;
    }
    @Override
    public float getMaxReach() {
        return 27.0f;
    }

    @Override
    protected ParticleOptions getTrailParticle() {
        return JerotesVillageParticleTypes.OCEAN_CURRENT_BREATH.get();
    }

    @Override
    public ItemStack getItem() {
        ItemStack itemStack = this.getItemRaw();
        return itemStack.isEmpty() ? new ItemStack(JerotesVillageItems.OCEAN_CURRENT_BREATH.get()) : itemStack;
    }

    @Override
    protected float getInertia() {
        return 0.95f;
    }

    //@Override
    protected float getLiquidInertia() {
        return 1.1f;
    }
}