package com.jerotes.jerotesvillage.init;

import com.google.common.base.Supplier;
import com.google.common.base.Suppliers;
import com.google.common.collect.ImmutableList;
import com.jerotes.jerotesvillage.JerotesVillage;
import com.jerotes.jerotesvillage.recipes.TabletPressingCraftingBookCategory;
import com.jerotes.jerotesvillage.recipes.TabletPressingRecipe;
import net.minecraft.client.RecipeBookCategories;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.client.event.RegisterRecipeBookCategoriesEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

@Mod.EventBusSubscriber(modid = JerotesVillage.MODID, bus = Mod.EventBusSubscriber.Bus.MOD, value = Dist.CLIENT)
public class JerotesVillageRecipeBookCategories {
	public static final Supplier<RecipeBookCategories> TABLET_PRESSING_SEARCH = Suppliers.memoize(() -> RecipeBookCategories.create("tablet_pressing_search", new ItemStack(Items.COMPASS)));
	public static final Supplier<RecipeBookCategories> TABLET_PRESSING_TABLET = Suppliers.memoize(() -> RecipeBookCategories.create("tablet_pressing_tablet", new ItemStack(Items.POTATO)));
	public static final Supplier<RecipeBookCategories> TABLET_PRESSING_MISC = Suppliers.memoize(() -> RecipeBookCategories.create("tablet_pressing_misc", new ItemStack(Items.BOOK)));

	@SubscribeEvent
	public static void onRegisterRecipeBookCategories(RegisterRecipeBookCategoriesEvent event) {
		event.registerBookCategories(JerotesVillage.TABLET_PRESS_MACHINE,
				ImmutableList.of(TABLET_PRESSING_SEARCH.get(), TABLET_PRESSING_TABLET.get(), TABLET_PRESSING_MISC.get()));
		event.registerAggregateCategory(TABLET_PRESSING_SEARCH.get(),
				ImmutableList.of(TABLET_PRESSING_TABLET.get(), TABLET_PRESSING_MISC.get()));
		event.registerRecipeCategoryFinder(JerotesVillageRecipeType.TABLET_PRESSING.get(), recipe -> {
			if (recipe instanceof TabletPressingRecipe tabletPressingRecipe) {
				TabletPressingCraftingBookCategory category = tabletPressingRecipe.category();
				if (category != null) {
					return switch (category) {
						case TABLET -> TABLET_PRESSING_TABLET.get();
						case MISC -> TABLET_PRESSING_MISC.get();
					};
				}
			}
			return TABLET_PRESSING_MISC.get();
		});
	}

}