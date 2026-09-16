package com.jerotes.jerotesvillage.entity.Shoot.Other;

import com.jerotes.jerotes.util.AttackFind;
import com.jerotes.jerotes.util.Main;
import com.jerotes.jerotesvillage.entity.Boss.Biome.OldSecondRounderGolemEntity;
import com.jerotes.jerotesvillage.event.WorldEvent;
import com.jerotes.jerotesvillage.init.JerotesVillageEntityType;
import com.jerotes.jerotesvillage.init.JerotesVillageItems;
import com.jerotes.jerotesvillage.init.JerotesVillageParticleTypes;
import com.jerotes.jerotesvillage.init.JerotesVillageSoundEvents;
import com.jerotes.jerotesvillage.util.OtherEntityFactionFind;
import net.minecraft.core.particles.ParticleOptions;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.ListTag;
import net.minecraft.network.protocol.Packet;
import net.minecraft.network.protocol.game.ClientGamePacketListener;
import net.minecraft.network.protocol.game.ClientboundAddEntityPacket;
import net.minecraft.network.syncher.EntityDataAccessor;
import net.minecraft.network.syncher.EntityDataSerializers;
import net.minecraft.network.syncher.SynchedEntityData;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.world.Difficulty;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.entity.projectile.ItemSupplier;
import net.minecraft.world.entity.projectile.Projectile;
import net.minecraft.world.entity.projectile.ProjectileUtil;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.enchantment.EnchantmentHelper;
import net.minecraft.world.level.ClipContext;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.EntityHitResult;
import net.minecraft.world.phys.HitResult;
import net.minecraft.world.phys.Vec3;

import java.util.List;

public class ZsieinSonicEntity extends Projectile implements ItemSupplier {
    private static final EntityDataAccessor<ItemStack> DATA_ITEM_STACK = SynchedEntityData.defineId(ZsieinSonicEntity.class, EntityDataSerializers.ITEM_STACK);
    public double xPower;
    public double yPower;
    public double zPower;
    public float strength;
    @Override
    public void addAdditionalSaveData(CompoundTag compoundTag) {
        super.addAdditionalSaveData(compoundTag);
        ItemStack itemStack = this.getItemRaw();
        if (!itemStack.isEmpty()) {
            compoundTag.put("Item", itemStack.save(new CompoundTag()));
        }
        compoundTag.put("power", this.newDoubleList(this.xPower, this.yPower, this.zPower));
        compoundTag.putFloat("Strength", this.strength);
    }

    @Override
    public void readAdditionalSaveData(CompoundTag compoundTag) {
        ListTag listTag;
        super.readAdditionalSaveData(compoundTag);
        ItemStack itemStack = ItemStack.of(compoundTag.getCompound("Item"));
        this.setItem(itemStack);
        if (compoundTag.contains("power", 9) && (listTag = compoundTag.getList("power", 6)).size() == 3) {
            this.xPower = listTag.getDouble(0);
            this.yPower = listTag.getDouble(1);
            this.zPower = listTag.getDouble(2);
        }
        this.strength = compoundTag.getFloat("Strength");
    }

    public ZsieinSonicEntity(EntityType<? extends ZsieinSonicEntity> entityType, Level level) {
        super(entityType, level);
    }

    public ZsieinSonicEntity(EntityType<? extends ZsieinSonicEntity> entityType, double d, double d2, double d3, Level level) {
        this(entityType, level);
        this.setPos(d, d2, d3);
    }

    public ZsieinSonicEntity(EntityType<? extends ZsieinSonicEntity> entityType, double d, double d2, double d3, double d4, double d5, double d6, Level level) {
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

    public ZsieinSonicEntity(EntityType<? extends ZsieinSonicEntity> entityType, LivingEntity livingEntity, double d, double d2, double d3, Level level) {
        this(entityType, livingEntity.getX(), livingEntity.getY(), livingEntity.getZ(), d, d2, d3, level);
        this.setOwner(livingEntity);
        this.setRot(livingEntity.getYRot(), livingEntity.getXRot());
    }

    public ZsieinSonicEntity(float strength, Level level, LivingEntity livingEntity, double d, double d2, double d3) {
        this(JerotesVillageEntityType.ZSIEIN_SONIC.get(), livingEntity, d, d2, d3, level);
        this.strength = strength;
    }

    @Override
    public boolean shouldRenderAtSqrDistance(double d) {
        return d < 16384.0;
    }

    protected ClipContext.Block getClipType() {
        return ClipContext.Block.COLLIDER;
    }

    protected void onHitEntity(EntityHitResult entityHitResult) {
        super.onHitEntity(entityHitResult);
    }

    @Override
    protected void onHit(HitResult hitResult) {
        super.onHit(hitResult);
    }

    @Override
    public void playerTouch(Player player) {
        if (this.ownedBy(player) || this.getOwner() == null) {
            super.playerTouch(player);
        }
    }

    private boolean doHurtAll(Entity entity) {
        if (this.getOwner() == null) {
            return false;
        }
        if (this.getOwner() instanceof LivingEntity owner && owner.getAttribute(Attributes.ATTACK_DAMAGE) != null) {
            if (this.level().getDifficulty() != Difficulty.HARD && creativePlayer(entity)) {
                return false;
            }
            float f = (float)owner.getAttributeValue(Attributes.ATTACK_DAMAGE) + EnchantmentHelper.getDamageBonus(owner.getMainHandItem(), ((LivingEntity)entity).getMobType());
            float f1 = (float)owner.getAttributeValue(Attributes.ATTACK_KNOCKBACK) + EnchantmentHelper.getKnockbackBonus(owner);
            int i = EnchantmentHelper.getFireAspect(owner);
            if (i > 0) {
                entity.setSecondsOnFire(i * 4);
            }
            if (entity instanceof OldSecondRounderGolemEntity secondRounderGolem) {
                if (!secondRounderGolem.isSilent()) {
                    secondRounderGolem.level().playSound(null, secondRounderGolem.getX(), secondRounderGolem.getY(), secondRounderGolem.getZ(), JerotesVillageSoundEvents.SECOND_ROUNDER_GOLEM_BLOCK, secondRounderGolem.getSoundSource(), 10.0f, 0.8f + this.random.nextFloat() * 0.4f);
                }
                secondRounderGolem.level().broadcastEntityEvent(secondRounderGolem, (byte) 12);
                this.discard();
            }
            if (entity instanceof LivingEntity livingEntity) {
                float health = livingEntity.getHealth();
                float maxHealth = livingEntity.getMaxHealth();
                //百分比大小
                float percentage;
                percentage = Math.max(this.strength/2, 5);
                percentage = Math.min(percentage, 100);
                //此次伤害
                float trueDamage = f + (maxHealth * (percentage / 100));
                //攻击
                if ((entity instanceof OldSecondRounderGolemEntity || entity instanceof Player) && !WorldEvent.ZsieinExtremelyInfectedAbove(entity.level())) {
                    entity.hurt(this.damageSources().mobAttack(owner) , f);
                }
                else {
                    entity.hurt(this.damageSources().mobAttack(owner) , f * 3);
                    //修改血量
                    if (!this.level().isClientSide) {
                        if (livingEntity.getHealth() - trueDamage < 0) {
                            if (livingEntity.getHealth() > 0) {
                                livingEntity.setHealth(0);
                            }
                        } else {
                            livingEntity.setHealth(health - trueDamage);
                        }
                    }
                }
                if (f1 > 0.0F) {
                    livingEntity.setDeltaMovement((livingEntity.getX() - this.getX())/2, (livingEntity.getY() - this.getY())/2, (livingEntity.getZ() - this.getZ())/2);
                    this.setDeltaMovement(this.getDeltaMovement().multiply(0.6D, 1.0D, 0.6D));
                }
            }
            if (!(entity instanceof Player) && !entity.level().isClientSide()) {
                Main.getJerotesPersistentData(entity).putDouble("jerotesvillage_variant_zsiein_discard", Main.getJerotesPersistentData(entity).getDouble("jerotesvillage_variant_zsiein_discard") + 200);
            }
            return true;
        }
        return false;
    }

    protected boolean creativePlayer(Entity entity) {
        return (entity instanceof Player player && (player.getAbilities().instabuild || player.isSpectator()));
    }

    @Override
    public void tick() {
        if (this.getOwner() == null || !this.getOwner().isAlive() || this.distanceTo(this.getOwner()) > 64) {
            this.discard();
        }
        List<LivingEntity> list = this.level().getEntitiesOfClass(LivingEntity.class, this.getBoundingBox().inflate(3, 3, 3));
        for (LivingEntity attack : list) {
            if (attack == null) continue;
            if (!(this.getOwner() instanceof LivingEntity livingEntityOwner)) continue;
            if ((this.distanceToSqr(attack)) > Double.MAX_VALUE) continue;
            if (AttackFind.FindCanNotAttack(livingEntityOwner, attack)) continue;
            if (attack instanceof Mob zsiein && OtherEntityFactionFind.isZsiein(zsiein.getType()) && zsiein.getTarget() != this.getOwner())
                continue;
            if (this.level().getDifficulty() != Difficulty.HARD && creativePlayer(attack))
                continue;
            this.doHurtAll(attack);
            if (!this.isSilent()) {
                this.playSound(SoundEvents.PLAYER_ATTACK_KNOCKBACK, 10.0f, 1.0f);
            }
        }
        HitResult hitResult;
        float f;
        Entity entity = this.getOwner();
        if (!this.level().isClientSide && (entity != null && entity.isRemoved() || !this.level().hasChunkAt(this.blockPosition()))) {
            return;
        }
        super.tick();
        if (this.shouldBurn()) {
            this.setSecondsOnFire(1);
        }
        if ((hitResult = ProjectileUtil.getHitResultOnMoveVector(this, this::canHitEntity)).getType() != HitResult.Type.MISS) {
            this.onHit(hitResult);
        }
        this.checkInsideBlocks();
        Vec3 vec3 = this.getDeltaMovement();
        double d = this.getX() + vec3.x;
        double d2 = this.getY() + vec3.y;
        double d3 = this.getZ() + vec3.z;
        ProjectileUtil.rotateTowardsMovement(this, 0.2f);
        if (this.isInWater()) {
            for (int i = 0; i < 4; ++i) {
                float f2 = 0.25f;
                this.level().addParticle(ParticleTypes.BUBBLE, d - vec3.x * 0.25, d2 - vec3.y * 0.25, d3 - vec3.z * 0.25, vec3.x, vec3.y, vec3.z);
            }
            f = this.getLiquidInertia();
        } else {
            f = this.getInertia();
        }
        this.setDeltaMovement(vec3.add(this.xPower, this.yPower, this.zPower).scale(f));
        ParticleOptions particleOptions = this.getTrailParticle();
        if (particleOptions != null) {
            this.level().addParticle(particleOptions, d, d2 + 0.5, d3, 0.0, 0.0, 0.0);
        }
        this.setPos(d, d2, d3);
    }

    @Override
    protected boolean canHitEntity(Entity entity) {
        return super.canHitEntity(entity) && !entity.noPhysics;
    }

    @Override
    public boolean isPickable() {
        return false;
    }

    @Override
    public boolean hurt(DamageSource damageSource, float f) {
        return false;
    }

    protected ParticleOptions getTrailParticle() {
        return JerotesVillageParticleTypes.ZSIEIN_SONIC.get();
    }

    protected boolean shouldBurn() {
        return false;
    }

    protected float getInertia() {
        return 1.0f;
    }

    protected float getLiquidInertia() {
        return 1.0f;
    }

    public void setItem(ItemStack itemStack) {
        if (!itemStack.is(Items.AIR) || itemStack.hasTag()) {
            this.getEntityData().set(DATA_ITEM_STACK, itemStack.copyWithCount(1));
        }
    }

    protected ItemStack getItemRaw() {
        return this.getEntityData().get(DATA_ITEM_STACK);
    }

    @Override
    public ItemStack getItem() {
        ItemStack itemStack = this.getItemRaw();
        return itemStack.isEmpty() ? new ItemStack(JerotesVillageItems.ZSIEIN_SNOIC.get()) : itemStack;
    }

    @Override
    protected void defineSynchedData() {
        this.getEntityData().define(DATA_ITEM_STACK, ItemStack.EMPTY);
    }

    @Override
    public float getPickRadius() {
        return 1.0f;
    }

    @Override
    public float getLightLevelDependentMagicValue() {
        return 1.0f;
    }

    @Override
    public Packet<ClientGamePacketListener> getAddEntityPacket() {
        Entity entity = this.getOwner();
        int n = entity == null ? 0 : entity.getId();
        return new ClientboundAddEntityPacket(this.getId(), this.getUUID(), this.getX(), this.getY(), this.getZ(), this.getXRot(), this.getYRot(), this.getType(), n, new Vec3(this.xPower, this.yPower, this.zPower), 0.0);
    }

    @Override
    public void recreateFromPacket(ClientboundAddEntityPacket clientboundAddEntityPacket) {
        super.recreateFromPacket(clientboundAddEntityPacket);
        double d = clientboundAddEntityPacket.getXa();
        double d2 = clientboundAddEntityPacket.getYa();
        double d3 = clientboundAddEntityPacket.getZa();
        double d4 = Math.sqrt(d * d + d2 * d2 + d3 * d3);
        if (d4 != 0.0) {
            this.xPower = d / d4 * 0.1;
            this.yPower = d2 / d4 * 0.1;
            this.zPower = d3 / d4 * 0.1;
        }
    }
}
