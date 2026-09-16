package com.jerotes.jerotesvillage.compat.jei.categories;

import com.jerotes.jerotesvillage.JerotesVillage;
import com.jerotes.jerotesvillage.init.JerotesVillageItems;
import com.jerotes.jerotesvillage.recipes.TabletPressingRecipe;
import mezz.jei.api.constants.VanillaTypes;
import mezz.jei.api.gui.builder.IRecipeLayoutBuilder;
import mezz.jei.api.gui.drawable.IDrawable;
import mezz.jei.api.gui.drawable.IDrawableAnimated;
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

import javax.annotation.Nullable;
import javax.annotation.ParametersAreNonnullByDefault;
import java.util.Arrays;

@ParametersAreNonnullByDefault
@MethodsReturnNonnullByDefault
public class TabletPressingRecipeCategory implements IRecipeCategory<TabletPressingRecipe> {
	public static final RecipeType<TabletPressingRecipe> TABLET_PRESSING = RecipeType.create(JerotesVillage.MODID, "tablet_pressing", TabletPressingRecipe.class);

	protected final IDrawableAnimated arrow;
	private final Component title;
	private final IDrawable background;
	private final IDrawable icon;

	public TabletPressingRecipeCategory(IGuiHelper helper) {
		title = Component.translatable("gui.jerotesvillage.tablet_pressing");
		ResourceLocation backgroundImage =
				new ResourceLocation(JerotesVillage.MODID, "textures/gui/container/tablet_press_machine.png");
		ResourceLocation arrowImage =
				new ResourceLocation(JerotesVillage.MODID, "textures/gui/sprites/container/tablet_press_machine/tablet_press_machine_progress_other.png");
		icon = helper.createDrawableIngredient(VanillaTypes.ITEM_STACK, new ItemStack(JerotesVillageItems.VILLAGER_BLOCK_PHYSICIAN.get()));
		background = helper.createDrawable(backgroundImage, 55, 16, 64, 59);
		arrow = helper.drawableBuilder(arrowImage, 0, 0, 64, 32)
				.buildAnimated(200, IDrawableAnimated.StartDirection.TOP, false);
	}

	@Override
	public RecipeType<TabletPressingRecipe> getRecipeType() {
		return TABLET_PRESSING;
	}

	@Override
	public Component getTitle() {
		return this.title;
	}

	//@Override
//	public @Nullable IDrawable getBackground() {
//		return this.background;
//	}

	@Override
	public int getWidth() {
		return 64;
	}

	@Override
	public int getHeight() {
		return 59;
	}

	@Override
	public IDrawable getIcon() {
		return this.icon;
	}

	@Override
	public void setRecipe(IRecipeLayoutBuilder builder, TabletPressingRecipe recipe, IFocusGroup focusGroup) {
		ItemStack result = recipe.getResult();
		NonNullList<Ingredient> base = recipe.getBase();
		NonNullList<Ingredient> addition = recipe.getAddition();

		for (Ingredient ingredient : addition) {
			if (!ingredient.isEmpty()) {
				for (Ingredient value : addition) {
					ItemStack[] itemStacks = value.getItems();
					builder.addSlot(RecipeIngredientRole.INPUT, 79 - 55, 17 - 16).addItemStacks(Arrays.asList(itemStacks));
				}
			}
		}
		for (Ingredient ingredient : base) {
			if (!ingredient.isEmpty()) {
				for (Ingredient value : base) {
					ItemStack[] itemStacks = value.getItems();
					builder.addSlot(RecipeIngredientRole.CATALYST, 56 - 55, 58 - 16).addItemStacks(Arrays.asList(itemStacks));
				}
			}
		}


		builder.addSlot(RecipeIngredientRole.OUTPUT, 79 - 55, 58 - 16).addItemStack(result);
	}

	@Override
	public void draw(TabletPressingRecipe recipe, IRecipeSlotsView recipeSlotsView, GuiGraphics guiGraphics, double mouseX, double mouseY) {
		background.draw(guiGraphics, 0, 0);
		arrow.draw(guiGraphics, 0, 10);
	}
}
