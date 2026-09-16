package com.jerotes.jerotesvillage.goal;

import com.jerotes.jerotesvillage.entity.Neutral.Zsiein.ZsieinEntity;
import com.mojang.datafixers.DataFixUtils;
import net.minecraft.world.entity.ai.goal.Goal;

import java.util.List;
import java.util.function.Predicate;

public class ZsieinFollowFlockLeaderGoal extends Goal {
   private static final int INTERVAL_TICKS = 200;
   private final ZsieinEntity mob;
   private int timeToRecalcPath;
   private int nextStartTick;

   public ZsieinFollowFlockLeaderGoal(ZsieinEntity zsiein) {
      this.mob = zsiein;
      this.nextStartTick = this.nextStartTick(zsiein);
   }

   protected int nextStartTick(ZsieinEntity p_25252_) {
      return reducedTickDelay(200 + p_25252_.getRandom().nextInt(200) % 20);
   }

   public boolean canUse() {
      if (this.mob.hasFollowers()) {
         return false;
      }
      if (this.mob.isFollower()) {
         return true;
      }
      if (this.nextStartTick > 0) {
         --this.nextStartTick;
         return false;
      }
      this.nextStartTick = this.nextStartTick(this.mob);
      Predicate<ZsieinEntity> predicate = ZsieinEntity -> ZsieinEntity.canBeFollowed() || !ZsieinEntity.isFollower();
      List<ZsieinEntity> list = this.mob.level().getEntitiesOfClass(ZsieinEntity.class, this.mob.getBoundingBox().inflate(64.0, 64.0, 64.0), predicate);
      ZsieinEntity ZsieinEntity2 = (ZsieinEntity)DataFixUtils.orElse(list.stream().filter(ZsieinEntity::canBeFollowed).findAny(), (Object)this.mob);
      ZsieinEntity2.addFollowers(list.stream().filter(ZsieinEntity -> !ZsieinEntity.isFollower()));
      return this.mob.isFollower();
   }

   public boolean canContinueToUse() {
      return this.mob.isFollower() && this.mob.inRangeOfLeader() && this.mob.getTarget() == null
//              && !this.mob.isTame()
              ;
   }

   public void start() {
      this.timeToRecalcPath = 0;
   }

   public void stop() {
      this.mob.stopFollowing();
   }

   public void tick() {
      if (--this.timeToRecalcPath <= 0) {
         this.timeToRecalcPath = this.adjustedTickDelay(10);
         this.mob.pathToLeader();
      }
   }
}