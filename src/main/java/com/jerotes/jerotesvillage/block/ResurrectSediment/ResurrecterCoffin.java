package com.jerotes.jerotesvillage.block.ResurrectSediment;

import com.jerotes.jerotesvillage.config.OtherMainConfig;
import com.jerotes.jerotesvillage.entity.Boss.SedimentLordEntity;
import com.jerotes.jerotesvillage.entity.Monster.Resurrecter.ResurrecterEntity;
import com.jerotes.jerotesvillage.init.JerotesVillageBlocks;
import com.jerotes.jerotesvillage.init.JerotesVillageEntityType;
import net.minecraft.core.BlockPos;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.util.RandomSource;
import net.minecraft.world.entity.MobSpawnType;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.SoundType;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraft.world.level.block.state.properties.IntegerProperty;
import net.minecraft.world.level.material.MapColor;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.Vec3;

import java.util.List;

public class ResurrecterCoffin extends Block {
    public static final IntegerProperty LEVEL = BlockStateProperties.AGE_15;

    public ResurrecterCoffin() {
        super(Properties.of().mapColor(MapColor.COLOR_CYAN).sound(SoundType.METAL).strength(6f, 9f).requiresCorrectToolForDrops().randomTicks());
        this.registerDefaultState(this.stateDefinition.any().setValue(LEVEL, 0));
    }

    @Override
    protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> builder) {
        builder.add(LEVEL);
    }

    public int getLevel(BlockState blockState) {
        return blockState.getValue(LEVEL);
    }

    @Override
    public void randomTick(BlockState blockState, ServerLevel level, BlockPos pos, RandomSource randomSource) {
        super.randomTick(blockState, level, pos, randomSource);
        level.scheduleTick(pos, this, 1);
    }

    @Override
    public void tick(BlockState blockState, ServerLevel level, BlockPos pos, RandomSource randomSource) {
        super.tick(blockState, level, pos, randomSource);
        level.scheduleTick(pos, this, 1);
        BlockState blockState1 = level.getBlockState(pos.above());
        if (!blockState1.isRedstoneConductor(level, pos)) {
            //玩家非潜行附近
            List<Player> listPlayer = level.getEntitiesOfClass(Player.class, AABB.ofSize(new Vec3(pos.getX(), pos.getY(), pos.getZ()), 12, 12, 12));
            for (Player players : listPlayer) {
                if (players == null) continue;
                if (players.isShiftKeyDown()) continue;
                if (players.isInvisible()) continue;
                if (players.getAbilities().instabuild) continue;
                this.spawnInfestationTrue(level, blockState, pos);
                return;
            }
            //复魂尸愤怒
            List<ResurrecterEntity> listResurrect = level.getEntitiesOfClass(ResurrecterEntity.class, AABB.ofSize(new Vec3(pos.getX(), pos.getY(), pos.getZ()), 12, 12, 12));
            listResurrect.removeIf(entity -> entity.angryTick < 500);
            if (!listResurrect.isEmpty()) {
                this.spawnInfestationTrue(level, blockState, pos);
                return;
            }
            //沉降领主愤怒
            List<SedimentLordEntity> listLord = level.getEntitiesOfClass(SedimentLordEntity.class, AABB.ofSize(new Vec3(pos.getX(), pos.getY(), pos.getZ()), 32, 32, 32));
            listLord.removeIf(entity -> entity.angryTick < 500);
            if (!listLord.isEmpty()) {
                this.spawnInfestationTrue(level, blockState, pos);
            }
        }
    }

    @Override
    public void onPlace(BlockState blockstate, Level world, BlockPos pos, BlockState oldState, boolean moving) {
        super.onPlace(blockstate, world, pos, oldState, moving);
        world.scheduleTick(pos, this, 1);
    }

    @Override
    public void neighborChanged(BlockState blockState, Level level, BlockPos blockPos, Block block, BlockPos blockPos2, boolean bl) {
        super.neighborChanged(blockState, level, blockPos, block, blockPos2, bl);
        level.scheduleTick(blockPos, this, 1);
    }

    private boolean spawnInfestation(ServerLevel serverLevel, BlockState blockState,  BlockPos blockPos) {
        ResurrecterEntity resurrecter;
        //等级
        int level = this.getLevel(blockState);
        if (level > 10) {
            level = 10;
        }
        else if (level < 0) {
            level = 0;
        }
        //最高占比
        int max = level;
        //最低占比
        int min = 10 - level;
        //沉降守卫
        double guardTen = OtherMainConfig.ResurrectCoffinSedimentGuardWeightAfterTenResurrections;
        double guardZero = OtherMainConfig.ResurrectCoffinSedimentGuardWeight;
        double guard = (max * guardTen + min * guardZero) / 10;
        //沉降定灵人
        double soulStabilizerTen = OtherMainConfig.ResurrectCoffinSedimentSoulStabilizerWeightAfterTenResurrections;
        double soulStabilizerZero = OtherMainConfig.ResurrectCoffinSedimentSoulStabilizerWeight;
        double soulStabilizer = (max * soulStabilizerTen + min * soulStabilizerZero) / 10;
        //重装沉降守卫
        double heavyGuardTen = OtherMainConfig.ResurrectCoffinHeavyArmoredSedimentGuardWeightAfterTenResurrections;
        double heavyGuardZero = OtherMainConfig.ResurrectCoffinHeavyArmoredSedimentGuardWeight;
        double heavyGuard = (max * heavyGuardTen + min * heavyGuardZero) / 10;
        //最大
        double maxDouble = guard + soulStabilizer + heavyGuard;
        //几率
        if (serverLevel.random.nextFloat() < heavyGuard / maxDouble) {
            resurrecter = JerotesVillageEntityType.HEAVY_ARMORED_SEDIMENT_GUARD.get().spawn(serverLevel, BlockPos.containing(blockPos.getX(), blockPos.getY(), blockPos.getZ()), MobSpawnType.MOB_SUMMONED);
        } else if (serverLevel.random.nextFloat() > 1 - (soulStabilizer / maxDouble)) {
            resurrecter = JerotesVillageEntityType.SEDIMENT_SOUL_STABILIZER.get().spawn(serverLevel, BlockPos.containing(blockPos.getX(), blockPos.getY(), blockPos.getZ()), MobSpawnType.MOB_SUMMONED);
        } else {
            resurrecter = JerotesVillageEntityType.SEDIMENT_GUARD.get().spawn(serverLevel, BlockPos.containing(blockPos.getX(), blockPos.getY(), blockPos.getZ()), MobSpawnType.MOB_SUMMONED);
        }
        if (resurrecter != null) {
            List<ResurrecterEntity> list = serverLevel.getEntitiesOfClass(ResurrecterEntity.class, resurrecter.getBoundingBox().inflate(32, 32, 32));
            for (ResurrecterEntity resurrecters : list) {
                if (resurrecters == null) continue;
                if ((resurrecter.distanceTo(resurrecters)) > Double.MAX_VALUE) continue;
                resurrecters.angryTick = 3600;
            }
            resurrecter.moveTo((double) blockPos.getX() + 0.5, blockPos.getY(), (double) blockPos.getZ() + 0.5, 0.0f, 0.0f);
            serverLevel.addFreshEntity(resurrecter);
            resurrecter.spawnAnim();
            serverLevel.setBlock(blockPos, Blocks.AIR.defaultBlockState(), 2);
            return true;
        }
        return false;
    }

    private void spawnInfestationTrue(ServerLevel serverLevel, BlockState blockState, BlockPos blockPos) {
        boolean bl = this.spawnInfestation(serverLevel, blockState, blockPos);
        if (bl) {
            int level = this.getLevel(blockState);
            if (this.getLevel(blockState) < 10) {
                level = this.getLevel(blockState) + 1;
            }
            serverLevel.setBlock(blockPos, JerotesVillageBlocks.RESURRECTER_EMPTY_COFFIN.get().defaultBlockState().setValue(LEVEL, level), 2);
        }
    }

    @Override
    public void spawnAfterBreak(BlockState blockState, ServerLevel serverLevel, BlockPos blockPos, ItemStack itemStack, boolean bl) {
        super.spawnAfterBreak(blockState, serverLevel, blockPos, itemStack, bl);
        this.spawnInfestation(serverLevel, blockState, blockPos);
    }
}