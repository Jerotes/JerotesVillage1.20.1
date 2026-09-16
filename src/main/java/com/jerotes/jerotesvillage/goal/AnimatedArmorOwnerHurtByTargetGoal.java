package com.jerotes.jerotesvillage.goal;

import com.jerotes.jerotes.goal.JerotesTameMobOwnerHurtByTargetGoal;
import com.jerotes.jerotesvillage.entity.MagicSummoned.SecondRounderAnimatedArmor.SecondRounderAnimatedArmorEntity;

public class AnimatedArmorOwnerHurtByTargetGoal extends JerotesTameMobOwnerHurtByTargetGoal {
    private final SecondRounderAnimatedArmorEntity tameMob;

    public AnimatedArmorOwnerHurtByTargetGoal(SecondRounderAnimatedArmorEntity tameMob) {
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
