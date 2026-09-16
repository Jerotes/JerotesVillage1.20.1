package com.jerotes.jerotesvillage.util;

import com.jerotes.jerotesvillage.JerotesVillage;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.tags.TagKey;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.core.NonNullList;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Random;

public class IngredientGet {
	private static final Random RANDOM = new Random();

	public static ItemStack getRandomItemFromIngredients(NonNullList<Ingredient> ingredients) {
		List<Ingredient> nonEmptyIngredients = ingredients.stream()
				.filter(ingredient -> !ingredient.isEmpty())
				.toList();

		if (nonEmptyIngredients.isEmpty()) {
			return ItemStack.EMPTY;
		}
		Ingredient selectedIngredient = nonEmptyIngredients.get(RANDOM.nextInt(nonEmptyIngredients.size()));
		ItemStack[] possibleStacks = selectedIngredient.getItems();
		if (possibleStacks.length == 0) {
			return ItemStack.EMPTY;
		}
		ItemStack stack = possibleStacks[RANDOM.nextInt(possibleStacks.length)].copy();
		stack.setCount(1);
		return stack;
	}

	public static List<ItemStack> getAllPossibleStacks(NonNullList<Ingredient> ingredients) {
		List<ItemStack> allStacks = new ArrayList<>();

		for (Ingredient ingredient : ingredients) {
			if (ingredient.isEmpty()) continue;
			ItemStack[] stacks = ingredient.getItems();
			if (stacks.length > 0) {
				allStacks.addAll(Arrays.asList(stacks));
			} else {
				try {
					java.lang.reflect.Field valuesField = Ingredient.class.getDeclaredField("values");
					valuesField.setAccessible(true);
					Ingredient.Value[] values = (Ingredient.Value[]) valuesField.get(ingredient);

					for (Ingredient.Value value : values) {
						if (value instanceof Ingredient.TagValue) {
							java.lang.reflect.Field tagField = Ingredient.TagValue.class.getDeclaredField("tag");
							tagField.setAccessible(true);
							TagKey<Item> tag = (TagKey<Item>) tagField.get(value);
							BuiltInRegistries.ITEM.getTag(tag).ifPresent(holders ->
									holders.forEach(holder ->
											allStacks.add(new ItemStack(holder.value()))
									));
						}
					}
				} catch (Exception e) {
					JerotesVillage.LOGGER.warn("Failed to get tag values from Ingredient", e);
				}
			}
		}

		return allStacks;
	}

	public static Item getRandomItem(NonNullList<Ingredient> ingredients) {
		List<ItemStack> allStacks = getAllPossibleStacks(ingredients);
		if (allStacks.isEmpty()) return null;

		ItemStack stack = allStacks.get(RANDOM.nextInt(allStacks.size()));
		return stack.getItem();
	}
}