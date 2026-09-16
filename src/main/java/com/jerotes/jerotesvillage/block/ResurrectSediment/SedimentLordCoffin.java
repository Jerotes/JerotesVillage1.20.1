package com.jerotes.jerotesvillage.block.ResurrectSediment;

import com.jerotes.jerotesvillage.entity.Boss.SedimentLordEntity;
import com.jerotes.jerotesvillage.entity.Monster.Resurrecter.ResurrecterEntity;
import com.jerotes.jerotesvillage.util.BossSpawn;
import com.jerotes.jerotesvillage.init.JerotesVillageBlocks;
import com.jerotes.jerotesvillage.init.JerotesVillageEntityType;
import com.jerotes.jerotesvillage.util.SpawnRules;
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
import net.minecraft.world.level.material.MapColor;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.Vec3;

import java.util.List;

public class SedimentLordCoffin extends Block {
    public SedimentLordCoffin() {
        super(Properties.of().mapColor(MapColor.COLOR_CYAN).sound(SoundType.METAL).strength(12f, 24f).requiresCorrectToolForDrops().randomTicks());
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
                this.spawnInfestationTrue(level, pos, players);
                return;
            }
            //复魂尸大量愤怒
            List<ResurrecterEntity> listResurrect = level.getEntitiesOfClass(ResurrecterEntity.class, AABB.ofSize(new Vec3(pos.getX(), pos.getY(), pos.getZ()), 12, 12, 12));
            listResurrect.removeIf(entity -> entity.angryTick < 500);
            if (listResurrect.size() > 6) {
                this.spawnInfestationTrue(level, pos, null);
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

    private boolean spawnInfestation(ServerLevel serverLevel, BlockPos blockPos, Player player) {
        if (SpawnRules.BossSpawnFind(JerotesVillageEntityType.SEDIMENT_LORD.get(), serverLevel, blockPos)) {
            SedimentLordEntity resurrecter = JerotesVillageEntityType.SEDIMENT_LORD.get().spawn(serverLevel, BlockPos.containing(blockPos.getX(), blockPos.getY(), blockPos.getZ()), MobSpawnType.MOB_SUMMONED);
            if (resurrecter != null) {
                List<ResurrecterEntity> listResurrecter = serverLevel.getEntitiesOfClass(ResurrecterEntity.class, resurrecter.getBoundingBox().inflate(32, 32, 32));
                for (ResurrecterEntity resurrecters : listResurrecter) {
                    if (resurrecters == null) continue;
                    if ((resurrecter.distanceTo(resurrecters)) > Double.MAX_VALUE) continue;
                    resurrecters.angryTick = 3600;
                }
                resurrecter.moveTo((double) blockPos.getX() + 0.5, blockPos.getY(), (double) blockPos.getZ() + 0.5, 0.0f, 0.0f);
                serverLevel.addFreshEntity(resurrecter);
                resurrecter.spawnAnim();
                resurrecter.startTick = 40;
                if (player != null) {
                    BossSpawn.SedimentLord(player);
                }
                else {
                    List<Player> listPlayer = serverLevel.getEntitiesOfClass(Player.class, resurrecter.getBoundingBox().inflate(32, 32, 32));
                    for (Player players : listPlayer) {
                        if (players == null) continue;
                        if ((resurrecter.distanceTo(players)) > Double.MAX_VALUE) continue;
                        BossSpawn.SedimentLordNoPlayer(players);
                    }
                }
                serverLevel.setBlock(blockPos, Blocks.AIR.defaultBlockState(), 2);
                return true;
            }
        }
        return false;
    }

    private void spawnInfestationTrue(ServerLevel serverLevel, BlockPos blockPos, Player player) {
        boolean bl = this.spawnInfestation(serverLevel, blockPos, player);
        if (bl) {
            serverLevel.setBlock(blockPos, JerotesVillageBlocks.SEDIMENT_LORD_EMPTY_COFFIN.get().defaultBlockState(), 2);
        }
    }

    @Override
    public void spawnAfterBreak(BlockState blockState, ServerLevel serverLevel, BlockPos blockPos, ItemStack itemStack, boolean bl) {
        super.spawnAfterBreak(blockState, serverLevel, blockPos, itemStack, bl);
        this.spawnInfestation(serverLevel, blockPos, null);
    }
}