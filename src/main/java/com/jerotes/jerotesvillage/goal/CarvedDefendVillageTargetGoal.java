package com.jerotes.jerotesvillage.goal;

import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.ai.goal.target.TargetGoal;
import net.minecraft.world.entity.ai.targeting.TargetingConditions;
import net.minecraft.world.entity.npc.Villager;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.phys.AABB;

import javax.annotation.Nullable;
import java.util.EnumSet;
import java.util.List;

public class CarvedDefendVillageTargetGoal extends TargetGoal {
    private final Mob mob;
    @Nullable
    private LivingEntity potentialTarget;
    private final TargetingConditions attackTargeting = TargetingConditions.forCombat().range(64.0);

    public CarvedDefendVillageTargetGoal(Mob mob) {
        super(mob, false, true);
        this.mob = mob;
        this.setFlags(EnumSet.of(Flag.TARGET));
    }

    @Override
    public boolean canUse() {
        AABB aABB = this.mob.getBoundingBox().inflate(10.0, 8.0, 10.0);
        List<Villager> list = this.mob.level().getNearbyEntities(Villager.class, this.attackTargeting, this.mob, aABB);
        List<Player> list2 = this.mob.level().getNearbyPlayers(this.attackTargeting, this.mob, aABB);
        for (LivingEntity livingEntity : list) {
            Villager villager = (Villager)livingEntity;
            for (Player player : list2) {
                int n = villager.getPlayerReputation(player);
                if (n > -100) continue;
                this.potentialTarget = player;
            }
        }
        if (this.potentialTarget == null) {
            return false;
        }
        return !(this.potentialTarget instanceof Player) || !this.potentialTarget.isSpectator() && !((Player)this.potentialTarget).isCreative();
    }

    @Override
    public void start() {
        this.mob.setTarget(this.potentialTarget);
        super.start();
    }
}

