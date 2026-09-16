package com.jerotes.jerotesvillage.goal;

import com.jerotes.jerotesvillage.entity.Animal.ThornyTortoiseEntity;
import com.jerotes.jerotesvillage.init.JerotesVillageBlocks;
import net.minecraft.advancements.CriteriaTriggers;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.stats.Stats;
import net.minecraft.util.RandomSource;
import net.minecraft.world.entity.ExperienceOrb;
import net.minecraft.world.entity.ai.goal.BreedGoal;
import net.minecraft.world.level.GameRules;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.gameevent.GameEvent;

public class ThornyTortoiseBreedGoal extends BreedGoal {
    private final ThornyTortoiseEntity thornyTortoise;

    public ThornyTortoiseBreedGoal(ThornyTortoiseEntity thornyTortoise, double d) {
        super(thornyTortoise, d);
        this.thornyTortoise = thornyTortoise;
    }

    @Override
    public boolean canUse() {
        return super.canUse();
    }

    @Override
    protected void breed() {
        ServerPlayer serverPlayer = this.animal.getLoveCause();
        if (serverPlayer == null && this.partner.getLoveCause() != null) {
            serverPlayer = this.partner.getLoveCause();
        }
        if (serverPlayer != null) {
            serverPlayer.awardStat(Stats.ANIMALS_BRED);
            CriteriaTriggers.BRED_ANIMALS.trigger(serverPlayer, this.animal, this.partner, null);
        }
        this.animal.setAge(6000);
        this.partner.setAge(6000);
        this.animal.resetLove();
        this.partner.resetLove();
        RandomSource randomSource = this.animal.getRandom();
        if (this.level.getGameRules().getBoolean(GameRules.RULE_DOMOBLOOT)) {
            this.level.addFreshEntity(new ExperienceOrb(this.level, this.animal.getX(), this.animal.getY(), this.animal.getZ(), randomSource.nextInt(7) + 1));
        }
        BlockPos blockPos = this.thornyTortoise.blockPosition();
        Level level = this.thornyTortoise.level();
        for (Direction direction : Direction.Plane.HORIZONTAL) {
            level.playSound(null, blockPos, SoundEvents.TURTLE_LAY_EGG, SoundSource.BLOCKS, 0.3f, 0.9f + level.random.nextFloat() * 0.2f);
            BlockState blockState = (BlockState) JerotesVillageBlocks.THORNY_TORTOISE_EGG.get().defaultBlockState();
            level.setBlock(blockPos, blockState, 3);
            level.gameEvent(GameEvent.BLOCK_PLACE, blockPos, GameEvent.Context.of(this.thornyTortoise, blockState));
            this.thornyTortoise.setInLoveTime(600);
        }
    }
}

