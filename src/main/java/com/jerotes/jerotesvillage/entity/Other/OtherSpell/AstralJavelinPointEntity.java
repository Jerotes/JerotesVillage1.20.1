package com.jerotes.jerotesvillage.entity.Other.OtherSpell;

import com.jerotes.jerotes.JerotesWarehouse;
import com.jerotes.jerotes.entity.Other.OtherSpell.OtherSpellBaseEntity;
import com.jerotes.jerotes.entity.Shoot.Magic.MagicAbout;
import com.jerotes.jerotes.init.JerotesDamageTypes;
import com.jerotes.jerotes.util.AttackFind;
import com.jerotes.jerotes.util.Main;
import com.jerotes.jerotesvillage.init.JerotesVillageEntityType;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.syncher.EntityDataAccessor;
import net.minecraft.network.syncher.EntityDataSerializers;
import net.minecraft.network.syncher.SynchedEntityData;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.entity.*;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.Vec3;

import javax.annotation.Nullable;
import java.util.*;

public class AstralJavelinPointEntity extends OtherSpellBaseEntity implements TraceableEntity, OwnableEntity, MagicAbout {
    private static final EntityDataAccessor<Optional<UUID>> DATA_MAIN_UUID =
            SynchedEntityData.defineId(AstralJavelinPointEntity.class, EntityDataSerializers.OPTIONAL_UUID);
    private static final EntityDataAccessor<Integer> DATA_MAIN_ID =
            SynchedEntityData.defineId(AstralJavelinPointEntity.class, EntityDataSerializers.INT);

    private static final EntityDataAccessor<Optional<UUID>> DATA_TARGETPOINT_UUID =
            SynchedEntityData.defineId(AstralJavelinPointEntity.class, EntityDataSerializers.OPTIONAL_UUID);
    private static final EntityDataAccessor<Integer> DATA_TARGETPOINT_ID =
            SynchedEntityData.defineId(AstralJavelinPointEntity.class, EntityDataSerializers.INT);

    public AstralJavelinPointEntity(EntityType<? extends AstralJavelinPointEntity> entityType, Level level) {
        super(entityType, level);
        this.noCulling = true;
    }

    public AstralJavelinPointEntity(Level level, LivingEntity owner) {
        this(JerotesVillageEntityType.ASTRAL_JAVELIN_POINT.get(), level);
        this.setOwner(owner);
        this.noCulling = true;
    }

    @Override
    protected void defineSynchedData() {
        super.defineSynchedData();
        this.getEntityData().define(DATA_MAIN_UUID, Optional.empty());
        this.getEntityData().define(DATA_MAIN_ID, -1);
        this.getEntityData().define(DATA_TARGETPOINT_UUID, Optional.empty());
        this.getEntityData().define(DATA_TARGETPOINT_ID, -1);
    }

    @Override
    public Vec3 getLeashOffset(float partialTicks) {
        return new Vec3(0, this.getBbHeight(), 0);
    }
    @Override
    public Vec3 getRopeHoldPosition(float partialTicks) {
        return new Vec3(0, this.getBbHeight(), 0);
    }

    @Nullable
    public UUID getMainUUID() {
        return this.getEntityData().get(DATA_MAIN_UUID).orElse(null);
    }

    public void setMainUUID(@Nullable UUID uuid) {
        this.getEntityData().set(DATA_MAIN_UUID, Optional.ofNullable(uuid));
    }

    public int getMainId() {
        return this.getEntityData().get(DATA_MAIN_ID);
    }

    public void setMainId(int id) {
        this.getEntityData().set(DATA_MAIN_ID, id);
    }

    @Nullable
    public Entity getMain() {
        if (!this.level().isClientSide) {
            UUID uuid = this.getMainUUID();
            return uuid == null ? null : ((ServerLevel) this.level()).getEntity(uuid);
        } else {
            int id = this.getMainId();
            return id <= -1 ? null : this.level().getEntity(id);
        }
    }

    public void setMain(@Nullable Entity entity) {
        if (entity != null) {
            this.setMainUUID(entity.getUUID());
            if (!this.level().isClientSide) {
                this.setMainId(entity.getId());
            }
        } else {
            this.setMainUUID(null);
            this.setMainId(-1);
        }
    }

    @Nullable
    public UUID getTargetPointUUID() {
        return this.getEntityData().get(DATA_TARGETPOINT_UUID).orElse((UUID)null);
    }

    public void setTargetPointUUID(@Nullable UUID uuid) {
        this.getEntityData().set(DATA_TARGETPOINT_UUID, Optional.ofNullable(uuid));
    }

    public int getTargetPointId() {
        return this.getEntityData().get(DATA_TARGETPOINT_ID);
    }

    public void setTargetPointId(int id) {
        this.getEntityData().set(DATA_TARGETPOINT_ID, id);
    }

    @Nullable
    public Entity getTargetPoint() {
        if (!this.level().isClientSide) {
            UUID uuid = this.getTargetPointUUID();
            return uuid == null ? null : ((ServerLevel) this.level()).getEntity(uuid);
        } else {
            int id = this.getTargetPointId();
            return id <= -1 ? null : this.level().getEntity(id);
        }
    }

    public void setTargetPoint(@Nullable Entity entity) {
        if (entity != null) {
            this.setTargetPointUUID(entity.getUUID());
            if (!this.level().isClientSide) {
                this.setTargetPointId(entity.getId());
            }
        } else {
            this.setTargetPointUUID(null);
            this.setTargetPointId(-1);
        }
    }

    @Override
    protected void readAdditionalSaveData(CompoundTag compoundTag) {
        super.readAdditionalSaveData(compoundTag);

        if (compoundTag.hasUUID("Main")) {
            this.setMainUUID(compoundTag.getUUID("Main"));
        }
        if (compoundTag.hasUUID("TargetPoint")) {
            this.setTargetPointUUID(compoundTag.getUUID("TargetPoint"));
        }
    }

    @Override
    protected void addAdditionalSaveData(CompoundTag compoundTag) {
        super.addAdditionalSaveData(compoundTag);

        if (this.getMainUUID() != null) {
            compoundTag.putUUID("Main", this.getMainUUID());
        }
        if (this.getTargetPointUUID() != null) {
            compoundTag.putUUID("TargetPoint", this.getTargetPointUUID());
        }
    }

    public ResourceLocation getTextureLocation() {
        return new ResourceLocation(JerotesWarehouse.MODID, "textures/entity/beam/ray.png");
    }

    @Override
    public int getBaseSpellLevelDamage() {
        return 5;
    }

    @Override
    public int getMaxLife() {
        return 20 * 60 * 10;
    }

    @Override
    public void tick() {
        super.tick();
        if (this.getMain() != null && this.getMainId() == -1) {
            this.setMainId(this.getMain().getId());
        }
        if (this.getTargetPoint() != null && this.getTargetPointId() == -1) {
            this.setTargetPointId(this.getTargetPoint().getId());
        }
        if (this.level() instanceof ServerLevel) {
            if (this.getLifeTick() > 120) {
                this.discard();
            }
        }
        if (this.tickCount % 5 == 0 && this.getOwner() != null) {
            if (getTargetPoint() != null) {
                LivingEntity caster = this.getOwner();
                Vec3 targetPos = getTargetPoint().getPosition(1);
                List<Entity> hitEntities = findEntitiesOnRay(this.getPosition(1), targetPos, caster);
                if (!hitEntities.isEmpty()) {
                    for (Entity entity : hitEntities) {
                        if (entity == this) continue;
                        if (entity == this.getOwner()) continue;
                        if (!this.getOwner().hasLineOfSight(entity)) continue;
                        this.customHurt(entity);
                    }
                }
            }
        }
    }

    private List<Entity> findEntitiesOnRay(Vec3 start, Vec3 end, LivingEntity caster) {
        List<Entity> result = new ArrayList<>();
        HitResultContext hitContext = new HitResultContext(start, end);
        List<Entity> allEntities = this.level().getEntities(
                caster,
                new AABB(start, end).inflate(0.20),
                entity -> entity != caster && entity instanceof LivingEntity
        );
        for (Entity entity : allEntities) {
            AABB entityBox = entity.getBoundingBox().inflate(0.15f);
            Optional<Vec3> hitPoint = entityBox.clip(start, end);
            if (hitPoint.isPresent()) {
                result.add(entity);
                hitContext.addHit(entity, hitPoint.get());
            }
        }
        hitContext.sortByDistance();
        return hitContext.getEntities();
    }

    private static class HitResultContext {
        private final Vec3 start;
        private final Map<Entity, Vec3> hits = new HashMap<>();
        private final Map<Entity, Double> distances = new HashMap<>();

        public HitResultContext(Vec3 start, Vec3 end) {
            this.start = start;
        }

        public void addHit(Entity entity, Vec3 hitPoint) {
            hits.put(entity, hitPoint);
            distances.put(entity, start.distanceTo(hitPoint));
        }

        public void sortByDistance() {
        }
        public List<Entity> getEntities() {
            List<Entity> sorted = new ArrayList<>(hits.keySet());
            sorted.sort(Comparator.comparingDouble(e -> distances.get(e)));
            return sorted;
        }
    }

    protected void customHurt(Entity entity) {
        if (entity instanceof LivingEntity) {
            entity.invulnerableTime = 0;
            entity.hurt(AttackFind.findDamageType(this, JerotesDamageTypes.BYPASSES_COOLDOWN_FORCE, this, this.getOwner()),
                    0.05f * Main.rollDice(4 + (getSpellLevel() - 4), 10, this.random)
            );
        }
    }

    @Override
    public boolean shouldRenderAtSqrDistance(double d) {
        double d2 = this.getBoundingBox().getSize() * 4.0;
        if (Double.isNaN(d2)) {
            d2 = 4.0;
        }
        d2 *= 128.0;
        return d < d2 * d2;
    }

    public int beamLightI() {
        return 0xbe528d;
    }
    public int beamLightII() {
        return 0xc98a70;
    }
    public int beamLightIII() {
        return 0x7d2578;
    }
}