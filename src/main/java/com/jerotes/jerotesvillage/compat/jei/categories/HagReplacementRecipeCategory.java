package com.jerotes.jerotesvillage.compat.jei.categories;

import com.jerotes.jerotesvillage.JerotesVillage;
import com.jerotes.jerotesvillage.init.JerotesVillageItems;
import com.jerotes.jerotesvillage.recipes.HagReplacementRecipe;
import mezz.jei.api.constants.VanillaTypes;
import mezz.jei.api.gui.builder.IRecipeLayoutBuilder;
import mezz.jei.api.gui.drawable.IDrawable;
import mezz.jei.api.gui.ingredient.IRecipeSlotsView;
import mezz.jei.api.helpers.IGuiHelper;
import mezz.jei.api.recipe.IFocusGroup;
import mezz.jei.api.recipe.RecipeIngredientRole;
import mezz.jei.api.recipe.RecipeType;
import mezz.jei.api.recipe.category.IRecipeCategory;
import net.minecraft.MethodsReturnNonnullByDefault;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.core.NonNullList;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.Ingredient;

import javax.annotation.ParametersAreNonnullByDefault;
import java.util.Arrays;

@ParametersAreNonnullByDefault
@MethodsReturnNonnullByDefault
public class HagReplacementRecipeCategory implements IRecipeCategory<HagReplacementRecipe> {
	public static final RecipeType<HagReplacementRecipe> HAG_REPLACEMENT = RecipeType.create(JerotesVillage.MODID, "hag_replacement", HagReplacementRecipe.class);

	private final Component title;
	private final IDrawable background;
	private final IDrawable icon;

	public HagReplacementRecipeCategory(IGuiHelper helper) {
		title = Component.translatable("gui.jerotesvillage.hag_replacement");
		ResourceLocation backgroundImage =
				new ResourceLocation(JerotesVillage.MODID, "textures/gui/container/hag_replacement.png");
		icon = helper.createDrawableIngredient(VanillaTypes.ITEM_STACK, new ItemStack(JerotesVillageItems.NEW_HAGS_CAULDRON.get()));
		background = helper.createDrawable(backgroundImage, 7, 9, 162, 72);
	}

	@Override
	public RecipeType<HagReplacementRecipe> getRecipeType() {
		return HAG_REPLACEMENT;
	}

	@Override
	public Component getTitle() {
		return this.title;
	}
//
//	public @Nullable IDrawable getBackground() {
//		return this.background;
//	}

	@Override
	public int getWidth() {
		return 162;
	}

	@Override
	public int getHeight() {
		return 72;
	}

	@Override
	public IDrawable getIcon() {
		return this.icon;
	}

	@Override
	public void setRecipe(IRecipeLayoutBuilder builder, HagReplacementRecipe recipe, IFocusGroup focusGroup) {
		NonNullList<Ingredient> base = recipe.getBase();
		NonNullList<Ingredient> result = recipe.getResult();

		for (Ingredient ingredient : base) {
			if (!ingredient.isEmpty()) {
				for (Ingredient value : base) {
					ItemStack[] itemStacks = value.getItems();
					builder.addSlot(RecipeIngredientRole.INPUT, 16 - 7, 28 - 9).addItemStacks(Arrays.asList(itemStacks));
				}
			}
		}
		for (Ingredient ingredient : result) {
			if (!ingredient.isEmpty()) {
				for (int row = 0; row < 4; ++row) {
					for (int column = 0; column < 6; ++column) {
						int inputIndex = row * 6 + column;
						if (inputIndex < result.size()) {
							ItemStack[] itemStacks = result.get(inputIndex).getItems();
							builder.addSlot(RecipeIngredientRole.OUTPUT,
									55 + column * 18, 1 + row * 18
							).addItemStacks(Arrays.asList(itemStacks));
						}
					}
				}
			}
		}
	}

	@Override
	public void draw(HagReplacementRecipe recipe, IRecipeSlotsView recipeSlotsView, GuiGraphics guiGraphics, double mouseX, double mouseY) {
		background.draw(guiGraphics, 0, 0);
	}
}
