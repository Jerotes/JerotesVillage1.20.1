package com.jerotes.jerotesvillage.recipes.brewing;

import com.jerotes.jerotesvillage.init.JerotesVillageItems;
import com.jerotes.jerotesvillage.item.AloeVeraPulp;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraftforge.common.brewing.BrewingRecipe;

import javax.annotation.Nonnull;

public class AloeVeraPulpBrewingRecipe extends BrewingRecipe {
	private final Ingredient input;
	private final Ingredient ingredient;
	private final ItemStack output;

	public AloeVeraPulpBrewingRecipe() {
		this(Ingredient.of(JerotesVillageItems.ALOE_VERA_PULP.get()), Ingredient.of(JerotesVillageItems.SECOND_ROUND_ALOE_LEAF.get()), new ItemStack(JerotesVillageItems.ALOE_VERA_PULP.get()));
	}

	public AloeVeraPulpBrewingRecipe(Ingredient input, Ingredient ingredient, ItemStack output) {
		super(input, ingredient, output);
		this.input = input;
		this.ingredient = ingredient;
		this.output = output;
	}


	@Override
	public boolean isInput(@Nonnull ItemStack itemStack) {
		ItemStack[] matchingStacks = input.getItems();
		if (matchingStacks.length == 0) {
			return itemStack.isEmpty();
		}
		else {
			for (ItemStack itemStack1 : matchingStacks) {
				if (ItemStack.isSameItem(itemStack, itemStack1)) {
					if (itemStack.getItem() instanceof AloeVeraPulp aloeVeraPulp && aloeVeraPulp.getExtractLevel(itemStack) < 6) {
						return true;
					}
				}
			}
			return false;
		}
	}

	@Override
	public ItemStack getOutput(ItemStack input, ItemStack ingredient) {
		ItemStack outPutTrue = new ItemStack(JerotesVillageItems.ALOE_VERA_PULP.get());
		if (input.getItem() instanceof AloeVeraPulp) {
			CompoundTag compoundtag = input.hasTag() ? input.getTag().copy() : new CompoundTag();
			compoundtag.putInt(AloeVeraPulp.TAG_EXTRACT_LEVEL,  compoundtag.getInt(AloeVeraPulp.TAG_EXTRACT_LEVEL) + 1);
			outPutTrue.setTag(compoundtag);
		}
		return this.isInput(input) && this.isIngredient(ingredient) ? outPutTrue.copy() : ItemStack.EMPTY;
	}

	@Override
	public Ingredient getInput() {
		return this.input;
	}

	@Override
	public Ingredient getIngredient() {
		return this.ingredient;
	}

	@Override
	public ItemStack getOutput() {
		return this.output;
	}
}