package com.jerotes.jerotesvillage.goal;

import com.jerotes.jerotesvillage.entity.Animal.WonderlandGiantFrogEntity;
import net.minecraft.advancements.CriteriaTriggers;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.stats.Stats;
import net.minecraft.util.RandomSource;
import net.minecraft.world.entity.ExperienceOrb;
import net.minecraft.world.entity.ai.goal.BreedGoal;
import net.minecraft.world.level.GameRules;

public class WonderlandGiantFrogBreedGoal extends BreedGoal {
    private final WonderlandGiantFrogEntity wonderlandGiantFrog;

    public WonderlandGiantFrogBreedGoal(WonderlandGiantFrogEntity wonderlandGiantFrog, double d) {
        super(wonderlandGiantFrog, d);
        this.wonderlandGiantFrog = wonderlandGiantFrog;
    }

    @Override
    public boolean canUse() {
        return super.canUse() && !this.wonderlandGiantFrog.hasEgg();
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
        this.wonderlandGiantFrog.setHasEgg(true);
        this.animal.setAge(6000);
        this.partner.setAge(6000);
        this.animal.resetLove();
        this.partner.resetLove();
        RandomSource randomSource = this.animal.getRandom();
        if (this.level.getGameRules().getBoolean(GameRules.RULE_DOMOBLOOT)) {
            this.level.addFreshEntity(new ExperienceOrb(this.level, this.animal.getX(), this.animal.getY(), this.animal.getZ(), randomSource.nextInt(7) + 1));
        }
    }
}

