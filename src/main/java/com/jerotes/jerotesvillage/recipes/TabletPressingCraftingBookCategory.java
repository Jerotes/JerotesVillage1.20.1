package com.jerotes.jerotesvillage.recipes;

import net.minecraft.util.StringRepresentable;
import net.minecraft.world.item.crafting.SmeltingRecipe;

public enum TabletPressingCraftingBookCategory implements StringRepresentable {
    TABLET("tablet"),
    MISC("misc");

    public static final StringRepresentable.EnumCodec<TabletPressingCraftingBookCategory> CODEC = StringRepresentable.fromEnum(TabletPressingCraftingBookCategory::values);

    private final String bookName;

    TabletPressingCraftingBookCategory(String bookName) {
        this.bookName = bookName;
    }

    public String getSerializedName() {
        return this.bookName;
    }
}