package com.jerotes.jerotesvillage.goal;

import com.jerotes.jerotes.goal.JerotesTameMobOwnerHurtTargetGoal;
import com.jerotes.jerotesvillage.entity.MagicSummoned.SecondRounderAnimatedArmor.SecondRounderAnimatedArmorEntity;

public class AnimatedArmorOwnerHurtTargetGoal extends JerotesTameMobOwnerHurtTargetGoal {
    private final SecondRounderAnimatedArmorEntity tameMob;

    public AnimatedArmorOwnerHurtTargetGoal(SecondRounderAnimatedArmorEntity tameMob) {
        super(tameMob);
        this.tameMob = tameMob;
    }

    public boolean canUse() {
        if (!(this.tameMob.isTame() && tameMob.getChangeType() != 1)) {
            return false;
        }
        else {
            return super.canUse();
        }
    }
}
