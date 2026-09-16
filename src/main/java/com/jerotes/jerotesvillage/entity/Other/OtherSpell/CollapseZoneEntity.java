package com.jerotes.jerotesvillage.entity.Other.OtherSpell;

import com.jerotes.jerotes.entity.Other.OtherSpell.OtherSpellBaseEntity;
import com.jerotes.jerotesvillage.init.JerotesVillageEntityType;
import com.jerotes.jerotesvillage.util.Other;
import net.minecraft.core.BlockPos;
import net.minecraft.core.particles.BlockParticleOption;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.state.BlockState;

import java.util.ArrayList;
import java.util.List;

public class CollapseZoneEntity extends OtherSpellBaseEntity {

    public CollapseZoneEntity(EntityType<? extends CollapseZoneEntity> entityType, Level level) {
        super(entityType, level);
        this.noCulling = true;
    }

    public CollapseZoneEntity(Level level, LivingEntity owner) {
        this(JerotesVillageEntityType.COLLAPSE_ZONE.get(), level);
        this.setOwnerUUID(owner.getUUID());
        this.noCulling = true;
    }

    public int getBaseSpellLevelDamage() {
        return 6;
    }

    public int getMaxStart() {
        return 40;
    }

    public int getMaxLife() {
        return 600;
    }

    @Override
    public void tick() {
        super.tick();
        if (this.level() instanceof ServerLevel serverLevel) {
            int t = this.getLifeTick();
            if (t >= 50 && (t - 50) % 120 == 0) {
                this.collapseWave(serverLevel);
            }
            if (t >= 20) {
                this.collapseParticles(serverLevel);
            }
        }
    }

    private void collapseParticles(ServerLevel serverLevel) {
        BlockPos center = this.blockPosition();
        int start = this.getLifeTick() % 49;
        for (int i = 0; i < 4; ++i) {
            int idx = (start + i * 12) % 49;
            int dx = idx % 7 - 3;
            int dz = idx / 7 - 3;
            for (int dy = 1; dy >= -3; --dy) {
                BlockPos blockPos = center.offset(dx, dy, dz);
                if (this.canCollapse(serverLevel, blockPos)) {
                    serverLevel.sendParticles(new BlockParticleOption(ParticleTypes.BLOCK, serverLevel.getBlockState(blockPos)),
                            blockPos.getX() + this.getRandom().nextDouble(), blockPos.getY() + 1.05, blockPos.getZ() + this.getRandom().nextDouble(),
                            0, 0.0, 0.12, 0.0, 1.0);
                    break;
                }
            }
        }
    }

    private void collapseWave(ServerLevel serverLevel) {
        BlockPos center = this.blockPosition();
        List<BlockPos> candidates = new ArrayList<>();
        for (int dx = -3; dx <= 3; ++dx) {
            for (int dz = -3; dz <= 3; ++dz) {
                for (int dy = 1; dy >= -3; --dy) {
                    BlockPos blockPos = center.offset(dx, dy, dz);
                    if (this.canCollapse(serverLevel, blockPos)) {
                        candidates.add(blockPos);
                        break;
                    }
                }
            }
        }
        LivingEntity owner = this.getOwner();
        for (BlockPos blockPos : candidates) {
            if (owner != null) {
                Other.spawnEarthquakeFallingBlockByPos(serverLevel, blockPos, owner);
            } else {
                Other.spawnEarthquakeFallingBlockByPos(serverLevel, blockPos);
            }
        }
    }

    private boolean canCollapse(ServerLevel level, BlockPos blockPos) {
        BlockState block = level.getBlockState(blockPos);
        if (block.isAir() || !block.isRedstoneConductor(level, blockPos) || block.hasBlockEntity()) {
            return false;
        }
        return !level.getBlockState(blockPos.above()).blocksMotion();
    }

    public boolean shouldRenderAtSqrDistance(double d) {
        double d0 = 64.0D * getViewScale();
        return d < d0 * d0;
    }
}
