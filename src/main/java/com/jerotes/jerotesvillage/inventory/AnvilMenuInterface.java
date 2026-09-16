package com.jerotes.jerotesvillage.inventory;

public interface AnvilMenuInterface {
    default boolean isVillagerMetal() {
        return false;
    }
    default void setVillagerMetal(boolean bl) {
    }
}