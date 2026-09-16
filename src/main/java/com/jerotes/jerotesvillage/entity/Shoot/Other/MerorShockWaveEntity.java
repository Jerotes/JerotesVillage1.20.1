package com.jerotes.jerotesvillage.entity.Shoot.Other;

import com.jerotes.jerotes.init.JerotesDamageTypes;
import com.jerotes.jerotes.util.AttackFind;
import com.jerotes.jerotesvillage.init.JerotesVillageParticleTypes;
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
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.entity.projectile.ItemSupplier;
import net.minecraft.world.entity.projectile.Projectile;
import net.minecraft.world.entity.projectile.ProjectileUtil;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.ClipContext;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.EntityHitResult;
import net.minecraft.world.phys.HitResult;
import net.minecraft.world.phys.Vec3;

public class MerorShockWaveEntity extends Projectile implements ItemSupplier {
    private static final EntityDataAccessor<ItemStack> DATA_ITEM_STACK = SynchedEntityData.defineId(MerorShockWaveEntity.class, EntityDataSerializers.ITEM_STACK);
    public double xPower;
    public double yPower;
    public double zPower;

    public MerorShockWaveEntity(EntityType<? extends MerorShockWaveEntity> entityType, Level level) {
        super(entityType, level);
    }

    public MerorShockWaveEntity(EntityType<? extends MerorShockWaveEntity> entityType, double d, double d2, double d3, double d4, double d5, double d6, Level level) {
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

    protected MerorShockWaveEntity(EntityType<? extends MerorShockWaveEntity> entityType, double d, double d2, double d3, Level level) {
        this(entityType, level);
        this.setPos(d, d2, d3);
    }

    public MerorShockWaveEntity(EntityType<? extends MerorShockWaveEntity> entityType, LivingEntity livingEntity, double d, double d2, double d3, Level level) {
        this(entityType, livingEntity.getX(), livingEntity.getY(), livingEntity.getZ(), d, d2, d3, level);
        this.setOwner(livingEntity);
        this.setRot(livingEntity.getYRot(), livingEntity.getXRot());
    }

    @Override
    public boolean shouldRenderAtSqrDistance(double d) {
        double d2 = this.getBoundingBox().getSize() * 4.0;
        if (Double.isNaN(d2)) {
            d2 = 4.0;
        }
        return d < (d2 *= 64.0) * d2;
    }

    protected ClipContext.Block getClipType() {
        return ClipContext.Block.COLLIDER;
    }

    protected void onHitEntity(EntityHitResult entityHitResult) {
        super.onHitEntity(entityHitResult);
        if (this.level().isClientSide) {
            return;
        }
        Entity entity = entityHitResult.getEntity();
        Entity entity2 = this.getOwner();
        this.playSound(SoundEvents.GENERIC_EXPLODE, 1.0f, 1.0f);
        DamageSource damageSource = AttackFind.findDamageType(this, JerotesDamageTypes.BYPASSES_COOLDOWN_THUNDER, this, this.getOwner());
        DamageSource damageSource2 = AttackFind.findDamageType(this, JerotesDamageTypes.BYPASSES_COOLDOWN_SHOOT, this, this.getOwner());
        if (!entity.hurt(damageSource, 12.0f) && entity2 instanceof LivingEntity livingEntity) {
            entity.hurt(damageSource2, 8.0f);
        }
        this.doEnchantDamageEffects((LivingEntity) entity2, entity);
        if (!(this.getOwner() instanceof LivingEntity livingEntity && AttackFind.FindCanNotAttack(livingEntity, entity))) {
            entity.setDeltaMovement(this.getDeltaMovement().x / 2, this.getDeltaMovement().y / 2, this.getDeltaMovement().z / 2);
        }
    }

    @Override
    protected void onHit(HitResult hitResult) {
        super.onHit(hitResult);
        if (hitResult.getType() == HitResult.Type.ENTITY && this.ownedBy(((EntityHitResult)hitResult).getEntity())) {
            return;
        }
        if (!this.level().isClientSide) {
            this.life += 4;
        }
    }

    @Override
    public void playerTouch(Player player) {
        if (this.ownedBy(player) || this.getOwner() == null) {
            super.playerTouch(player);
        }
    }

    public int life = 0;

    @Override
    public void tick() {
        if (this.life >= 30) {
            this.discard();
        }
        else {
            this.life += 1;
        }
        HitResult hitResult;
        float f;
        Entity entity = this.getOwner();
        if (!this.level().isClientSide && (entity != null && entity.isRemoved() || !this.level().hasChunkAt(this.blockPosition()))) {
            this.life += 20;
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
        double d = this.getX() + vec3.x * 1;
        double d2 = this.getY() + vec3.y * 1;
        double d3 = this.getZ() + vec3.z * 1;
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
        this.setPos(d, d2, d3);
    }

    public void TrailParticle(float f) {
        Vec3 vec3 = this.getDeltaMovement();
        double d = this.getX() + vec3.x * f;
        double d2 = this.getY() + vec3.y * f;
        double d3 = this.getZ() + vec3.z * f;
        ParticleOptions particleOptions = this.getTrailParticle();
        if (particleOptions != null) {
            this.level().addParticle(particleOptions, d, d2 + 0.5, d3, 0.0, 0.0, 0.0);
        }
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
        return JerotesVillageParticleTypes.MEROR_SHOCK_WAVE.get();
    }

    protected boolean shouldBurn() {
        return false;
    }

    protected float getInertia() {
        return 0.999f;
    }

    protected float getLiquidInertia() {
        return 0.99f;
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
        return itemStack.isEmpty() ? new ItemStack(Items.AIR) : itemStack;
    }

    @Override
    protected void defineSynchedData() {
        this.getEntityData().define(DATA_ITEM_STACK, ItemStack.EMPTY);
    }

    @Override
    public void addAdditionalSaveData(CompoundTag compoundTag) {
        super.addAdditionalSaveData(compoundTag);
        compoundTag.putInt("Life", this.life);
        ItemStack itemStack = this.getItemRaw();
        if (!itemStack.isEmpty()) {
            compoundTag.put("Item", itemStack.save(new CompoundTag()));
        }
        compoundTag.put("power", this.newDoubleList(this.xPower, this.yPower, this.zPower));
    }

    @Override
    public void readAdditionalSaveData(CompoundTag compoundTag) {
        ListTag listTag;
        super.readAdditionalSaveData(compoundTag);
        this.life = compoundTag.getInt("Life");
        ItemStack itemStack = ItemStack.of(compoundTag.getCompound("Item"));
        this.setItem(itemStack);
        if (compoundTag.contains("power", 9) && (listTag = compoundTag.getList("power", 6)).size() == 3) {
            this.xPower = listTag.getDouble(0);
            this.yPower = listTag.getDouble(1);
            this.zPower = listTag.getDouble(2);
        }
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

    protected float getWaterInertia() {
        return 0.99f;
    }
}
