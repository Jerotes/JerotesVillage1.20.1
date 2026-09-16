package com.jerotes.jerotesvillage.entity.Other.OtherSpell;

import com.jerotes.jerotes.entity.Interface.LightningAbsorptionEntity;
import com.jerotes.jerotes.entity.Interface.UseShieldEntity;
import com.jerotes.jerotes.entity.Other.Other.InvisibleLightningBolt;
import com.jerotes.jerotes.entity.Other.OtherSpell.OtherSpellBaseEntity;
import com.jerotes.jerotes.entity.Shoot.Magic.MagicAbout;
import com.jerotes.jerotes.init.JerotesDamageTypes;
import com.jerotes.jerotes.init.JerotesEntityType;
import com.jerotes.jerotes.init.JerotesMobEffects;
import com.jerotes.jerotes.init.JerotesParticleTypes;
import com.jerotes.jerotes.spell.SpellFind;
import com.jerotes.jerotes.util.AttackFind;
import com.jerotes.jerotes.util.Main;
import com.jerotes.jerotesvillage.init.JerotesVillageEntityType;
import net.minecraft.advancements.CriteriaTriggers;
import net.minecraft.core.BlockPos;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.*;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ShieldItem;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.LightningRodBlock;
import net.minecraft.world.level.block.WeatheringCopper;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.gameevent.GameEvent;
import net.minecraft.world.phys.Vec3;

import java.util.List;
import java.util.Objects;
import java.util.Optional;

public class ChaoticDischargeEntity extends OtherSpellBaseEntity implements TraceableEntity, OwnableEntity, MagicAbout {
    public ChaoticDischargeEntity(EntityType<? extends ChaoticDischargeEntity> entityType, Level level) {
        super(entityType, level);
        this.seed = this.random.nextLong();
        this.noCulling = true;
    }

    public ChaoticDischargeEntity(Level p_36926_, LivingEntity p_36932_) {
        this(JerotesVillageEntityType.CHAOTIC_DISCHARGE.get(), p_36926_);
        this.setOwnerUUID(p_36932_.getUUID());
        this.seed = this.random.nextLong();
        this.noCulling = true;
    }

    public long seed;
    private boolean hasStruck = false;
    private int strikeTick = 10;
    public int getBaseSpellLevelDamage() {
        return 3;
    }
    public int getMaxStart() {
        return 40;
    }
    public int getMaxLife() {
        return 40;
    }
    public SoundSource getSoundSource() {
        return SoundSource.WEATHER;
    }

    @Override
    public void tick() {
        if (this.getLifeTick() == 0) {
            if (this.level().isClientSide()) {
                this.level().playLocalSound(this.getX(), this.getY(), this.getZ(),
                        SoundEvents.LIGHTNING_BOLT_THUNDER, SoundSource.WEATHER,
                        10.0F, 0.8F + this.random.nextFloat() * 0.2F, false);
                this.level().playLocalSound(this.getX(), this.getY(), this.getZ(),
                        SoundEvents.LIGHTNING_BOLT_IMPACT, SoundSource.WEATHER,
                        2.0F, 0.5F + this.random.nextFloat() * 0.2F, false);
            }
            else {
                this.powerLightningRod();
                clearCopperOnLightningStrike(this.level(), this.getStrikePosition());
                this.gameEvent(GameEvent.LIGHTNING_STRIKE);
            }
        }
        super.tick();
        //攻击
        if (this.getLifeTick() == 20) {
            List<Entity> list = this.level().getEntitiesOfClass(Entity.class, this.getBoundingBox().inflate(1.75,1.75,1.75));
            double radius = 2.25;
            double radiusSq = radius * radius;
            list.removeIf(livingEntity -> livingEntity.distanceToSqr(this) > radiusSq);
            if (this.level() instanceof ServerLevel serverLevel) {
                InvisibleLightningBolt fakeBolt = new InvisibleLightningBolt(JerotesEntityType.INVISIBLE_LIGHTNING_BOLT.get(), serverLevel);
                fakeBolt.setDamage(0);
                fakeBolt.setPos(this.getX(), this.getY(), this.getZ());
                fakeBolt.setVisualOnly(true);
                serverLevel.addFreshEntity(fakeBolt);
                for(Entity entity : list) {
                    if (!AttackFind.FindCanNotAttack(this.getOwner(), entity)) {
                        if (!net.minecraftforge.event.ForgeEventFactory.onEntityStruckByLightning(entity, fakeBolt)) {
                            entity.thunderHit(serverLevel, fakeBolt);
                        }
                    }
                }
                if (this.getOwner() instanceof ServerPlayer serverPlayer) {
                    CriteriaTriggers.CHANNELED_LIGHTNING.trigger(serverPlayer, list);
                }
                for (Entity entity : list) {
                    if (entity instanceof LivingEntity livingEntity) {
                        if (AttackFind.FindCanNotAttack(this.getOwner(), entity)) {
                            if (!entity.level().isClientSide()) {
                                if (livingEntity.hasEffect(JerotesMobEffects.LIGHTNING_ABSORPTION.get()) || livingEntity instanceof LightningAbsorptionEntity) {
                                    double AbsorptionPercentage = Math.max(
                                            (entity instanceof LightningAbsorptionEntity absorptionEntity) ? absorptionEntity.LightningAbsorptionPercentage() : 0,
                                            (livingEntity.hasEffect(JerotesMobEffects.LIGHTNING_ABSORPTION.get())) ? (Objects.requireNonNull(livingEntity.getEffect(JerotesMobEffects.LIGHTNING_ABSORPTION.get())).getAmplifier() + 1) * 20 : 0);
                                    SpellFind.LightningAbsorption(livingEntity, Main.rollDice(6, 6, getRandom()), AbsorptionPercentage, 0.25f);
                                }
                            }
                        }
                        else {
                            this.dealDamageTo(livingEntity);
                        }
                    }
                }
            }
        }
        if (this.life <= 40 && this.life > 20 && this.getRandom().nextInt(6) == 1) {
            this.seed = this.random.nextLong();
        }
//        if (this.life <= 25 && this.life > 20) {
//            if (!(this.level() instanceof ServerLevel)) {
//                this.level().setSkyFlashTime(2);
//            }
//        }
    }

    public boolean dealDamageTo(LivingEntity livingEntity) {
        LivingEntity livingEntity2 = this.getOwner();
        if (!livingEntity.isAlive() || livingEntity.isInvulnerable() || livingEntity == livingEntity2) {
            return false;
        }
        if (AttackFind.FindCanNotAttack(this.getOwner(), livingEntity))
            return false;
        if (livingEntity.getUseItem().getItem() instanceof ShieldItem) {
            if (livingEntity instanceof Player player) {
                player.disableShield(true);
            } else if (livingEntity instanceof UseShieldEntity useShieldEntity) {
                useShieldEntity.disableShield();
            }
        }
        DamageSource damageSource = AttackFind.findDamageType(this, JerotesDamageTypes.LIGHTNING_MAGIC, this);
        if (livingEntity2 != null) {
            damageSource = AttackFind.findDamageType(this, JerotesDamageTypes.LIGHTNING_MAGIC, this, livingEntity2);
        }
        boolean bl = livingEntity.hurt(damageSource,
                Main.rollDice(6, 6, getRandom())
        );
        if (bl) {
            if (this.level() instanceof ServerLevel serverLevel) {
                for (int i = 0; i < 24; ++i) {
                    serverLevel.sendParticles(JerotesParticleTypes.SHOCK.get(), livingEntity.getRandomX(0.5), livingEntity.getRandomY(), livingEntity.getRandomZ(0.5), 0, 0.0, 0.0, 0.0, 0.0);
                }
            }
        }
        return true;
    }

    private void powerLightningRod() {
        BlockPos blockpos = this.getStrikePosition();
        BlockState blockstate = this.level().getBlockState(blockpos);
        if (blockstate.is(Blocks.LIGHTNING_ROD)) {
            ((LightningRodBlock)blockstate.getBlock()).onLightningStrike(blockstate, this.level(), blockpos);
        }
    }
    private BlockPos getStrikePosition() {
        Vec3 vec3 = this.position();
        return BlockPos.containing(vec3.x, vec3.y - 1.0E-6D, vec3.z);
    }
    private static void clearCopperOnLightningStrike(Level p_147151_, BlockPos p_147152_) {
        BlockState blockstate = p_147151_.getBlockState(p_147152_);
        BlockPos blockpos;
        BlockState blockstate1;
        if (blockstate.is(Blocks.LIGHTNING_ROD)) {
            blockpos = p_147152_.relative(blockstate.getValue(LightningRodBlock.FACING).getOpposite());
            blockstate1 = p_147151_.getBlockState(blockpos);
        } else {
            blockpos = p_147152_;
            blockstate1 = blockstate;
        }

        if (blockstate1.getBlock() instanceof WeatheringCopper) {
            p_147151_.setBlockAndUpdate(blockpos, WeatheringCopper.getFirst(p_147151_.getBlockState(blockpos)));
            BlockPos.MutableBlockPos blockpos$mutableblockpos = p_147152_.mutable();
            int i = p_147151_.random.nextInt(3) + 3;

            for(int j = 0; j < i; ++j) {
                int k = p_147151_.random.nextInt(8) + 1;
                randomWalkCleaningCopper(p_147151_, blockpos, blockpos$mutableblockpos, k);
            }

        }
    }
    private static void randomWalkCleaningCopper(Level p_147146_, BlockPos p_147147_, BlockPos.MutableBlockPos p_147148_, int p_147149_) {
        p_147148_.set(p_147147_);
        for(int i = 0; i < p_147149_; ++i) {
            Optional<BlockPos> optional = randomStepCleaningCopper(p_147146_, p_147148_);
            if (!optional.isPresent()) {
                break;
            }

            p_147148_.set(optional.get());
        }
    }
    private static Optional<BlockPos> randomStepCleaningCopper(Level p_147154_, BlockPos p_147155_) {
        for(BlockPos blockpos : BlockPos.randomInCube(p_147154_.random, 10, p_147155_, 1)) {
            BlockState blockstate = p_147154_.getBlockState(blockpos);
            if (blockstate.getBlock() instanceof WeatheringCopper) {
                WeatheringCopper.getPrevious(blockstate).ifPresent((p_147144_) -> {
                    p_147154_.setBlockAndUpdate(blockpos, p_147144_);
                });
                p_147154_.levelEvent(3002, blockpos, -1);
                return Optional.of(blockpos);
            }
        }

        return Optional.empty();
    }

    public boolean shouldRenderAtSqrDistance(double p_20869_) {
        double d0 = 64.0D * getViewScale();
        return p_20869_ < d0 * d0;
    }
}

