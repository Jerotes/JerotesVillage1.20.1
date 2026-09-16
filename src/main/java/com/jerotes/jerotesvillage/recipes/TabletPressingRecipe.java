//1.20.4↑//
// package com.jerotes.jerotesvillage.recipes;
//
//import com.jerotes.jerotesvillage.init.JerotesVillageItems;
//import com.jerotes.jerotesvillage.init.JerotesVillageRecipeSerializers;
//import com.jerotes.jerotesvillage.init.JerotesVillageRecipeType;
//import com.mojang.serialization.Codec;
//import com.mojang.serialization.DataResult;
//import com.mojang.serialization.codecs.RecordCodecBuilder;
//import net.minecraft.core.NonNullList;
//import net.minecraft.core.RegistryAccess;
//import net.minecraft.network.FriendlyByteBuf;
//import net.minecraft.world.Container;
//import net.minecraft.world.item.ItemStack;
//import net.minecraft.world.item.crafting.Ingredient;
//import net.minecraft.world.item.crafting.Recipe;
//import net.minecraft.world.item.crafting.RecipeSerializer;
//import net.minecraft.world.item.crafting.RecipeType;
//import net.minecraft.world.level.Level;
//
//import javax.annotation.Nullable;
//
//public class TabletPressingRecipe implements Recipe<Container> {
//	private final NonNullList<Ingredient> base;
//	private final NonNullList<Ingredient> addition;
//	private final ItemStack result;
//	private final TabletPressingCraftingBookCategory category;
//
//	public TabletPressingRecipe(NonNullList<Ingredient> base, NonNullList<Ingredient> addition, ItemStack result, TabletPressingCraftingBookCategory category) {
//		this.base = base;
//		this.addition = addition;
//		this.result = result;
//		this.category = category;
//	}
//	public NonNullList<Ingredient> getAddition() {
//		return addition;
//	}
//	public NonNullList<Ingredient> getBase() {
//		return base;
//	}
//
//	@Override
//	public NonNullList<Ingredient> getIngredients() {
//		NonNullList<Ingredient> combined = NonNullList.create();
//		combined.addAll(base);
//		combined.addAll(addition);
//		return combined;
//	}
//
//	public ItemStack getResult() {
//		return result;
//	}
//
//	@Override
//	public boolean matches(Container container, Level level) {
//		return false;
//	}
//
//	@Override
//	public ItemStack assemble(Container container, RegistryAccess registryAccess) {
//		return result.copy();
//	}
//
//	@Override
//	public boolean canCraftInDimensions(int width, int height) {
//		return true;
//	}
//
//	@Override
//	public ItemStack getResultItem(RegistryAccess registryAccess) {
//		return result.copy();
//	}
//
//	@Override
//	public RecipeType<?> getType() {
//		return JerotesVillageRecipeType.TABLET_PRESSING.get();
//	}
//
//	@Nullable
//	public TabletPressingCraftingBookCategory category() {
//		return this.category;
//	}
//
//	@Override
//	public ItemStack getToastSymbol() {
//		return new ItemStack(JerotesVillageItems.VILLAGER_BLOCK_PHYSICIAN.get());
//	}
//
//	@Override
//	public RecipeSerializer<?> getSerializer() {
//		return JerotesVillageRecipeSerializers.TABLET_PRESSING.get();
//	}
//
//	public static class TabletPressingSerializer implements RecipeSerializer<TabletPressingRecipe> {
//		private final Codec<TabletPressingRecipe> codec;
//
//		public TabletPressingSerializer() {
//			this.codec = RecordCodecBuilder.create(instance ->
//					instance.group(
//							Ingredient.CODEC_NONEMPTY.listOf().fieldOf("base").flatXmap((ingredients) -> {
//								Ingredient[] aingredient = ingredients.stream().filter((ingredient) -> !ingredient.isEmpty()).toArray(Ingredient[]::new);
//								if (aingredient.length == 0) {
//									return DataResult.error(() -> "No ingredients for recipe");
//								} else {
//									return DataResult.success(NonNullList.of(Ingredient.EMPTY, aingredient));
//								}
//							}, DataResult::success).forGetter(TabletPressingRecipe::getBase),
//							Ingredient.CODEC_NONEMPTY.listOf().fieldOf("addition").flatXmap((ingredients) -> {
//								Ingredient[] aingredient = ingredients.stream().filter((ingredient) -> !ingredient.isEmpty()).toArray(Ingredient[]::new);
//								if (aingredient.length == 0) {
//									return DataResult.error(() -> "No ingredients for recipe");
//								} else {
//									return DataResult.success(NonNullList.of(Ingredient.EMPTY, aingredient));
//								}
//							}, DataResult::success).forGetter(TabletPressingRecipe::getAddition),
//							ItemStack.ITEM_WITH_COUNT_CODEC.fieldOf("result").forGetter(TabletPressingRecipe::getResult),
//							TabletPressingCraftingBookCategory.CODEC.fieldOf("category").orElse(TabletPressingCraftingBookCategory.MISC).forGetter(recipe -> recipe.category)
//					).apply(instance, TabletPressingRecipe::new)
//			);
//		}
//		@Override
//		public Codec<TabletPressingRecipe> codec() {
//			return this.codec;
//		}
//
//		@Override
//		public TabletPressingRecipe fromNetwork(FriendlyByteBuf friendlyByteBuf) {
//			int baseSize = friendlyByteBuf.readVarInt();
//			NonNullList<Ingredient> base = NonNullList.withSize(baseSize, Ingredient.EMPTY);
//			for (int j = 0; j < baseSize; ++j) {
//				base.set(j, Ingredient.fromNetwork(friendlyByteBuf));
//			}
//
//			int additionSize = friendlyByteBuf.readVarInt();
//			NonNullList<Ingredient> addition = NonNullList.withSize(additionSize, Ingredient.EMPTY);
//			for (int j = 0; j < additionSize; ++j) {
//				addition.set(j, Ingredient.fromNetwork(friendlyByteBuf));
//			}
//			ItemStack result = friendlyByteBuf.readItem();
//			TabletPressingCraftingBookCategory category = TabletPressingCraftingBookCategory.valueOf(friendlyByteBuf.readUtf());
//			return new TabletPressingRecipe(base, addition, result, category);
//		}
//
//		@Override
//		public void toNetwork(FriendlyByteBuf friendlyByteBuf, TabletPressingRecipe recipe) {
//			for (Ingredient ingredient : recipe.base) {
//				ingredient.toNetwork(friendlyByteBuf);
//			}
//			for (Ingredient ingredient : recipe.addition) {
//				ingredient.toNetwork(friendlyByteBuf);
//			}
//			friendlyByteBuf.writeItem(recipe.result);
//			friendlyByteBuf.writeUtf(recipe.category.name());
//		}
//	}
//}

//1.20.1//
package com.jerotes.jerotesvillage.recipes;

import com.google.gson.JsonArray;
import com.jerotes.jerotesvillage.init.JerotesVillageItems;
import com.jerotes.jerotesvillage.init.JerotesVillageRecipeSerializers;
import com.jerotes.jerotesvillage.init.JerotesVillageRecipeType;
import net.minecraft.core.NonNullList;
import net.minecraft.core.RegistryAccess;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.GsonHelper;
import net.minecraft.world.Container;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.*;
		import net.minecraft.world.level.Level;

import javax.annotation.Nullable;

public class TabletPressingRecipe implements Recipe<Container> {
	private final ResourceLocation id;
	@Override
	public ResourceLocation getId() {
		return id;
	}
	private final NonNullList<Ingredient> base;
	private final NonNullList<Ingredient> addition;
	private final ItemStack result;
	private final TabletPressingCraftingBookCategory category;

	public TabletPressingRecipe(ResourceLocation id, NonNullList<Ingredient> base, NonNullList<Ingredient> addition, ItemStack result, TabletPressingCraftingBookCategory category) {
		this.id = id;
		this.base = base;
		this.addition = addition;
		this.result = result;
		this.category = category;
	}

	public NonNullList<Ingredient> getAddition() {
		return addition;
	}
	public NonNullList<Ingredient> getBase() {
		return base;
	}

	@Override
	public NonNullList<Ingredient> getIngredients() {
		NonNullList<Ingredient> combined = NonNullList.create();
		combined.addAll(base);
		combined.addAll(addition);
		return combined;
	}

	public ItemStack getResult() {
		return result;
	}

	@Override
	public boolean matches(Container container, Level level) {
		return false;
	}

	@Override
	public ItemStack assemble(Container container, RegistryAccess registryAccess) {
		return result.copy();
	}

	@Override
	public boolean canCraftInDimensions(int width, int height) {
		return true;
	}

	@Override
	public ItemStack getResultItem(RegistryAccess registryAccess) {
		return result.copy();
	}

	@Override
	public RecipeType<?> getType() {
		return JerotesVillageRecipeType.TABLET_PRESSING.get();
	}

	@Nullable
	public TabletPressingCraftingBookCategory category() {
		return this.category;
	}

	@Override
	public ItemStack getToastSymbol() {
		return new ItemStack(JerotesVillageItems.VILLAGER_BLOCK_PHYSICIAN.get());
	}

	@Override
	public RecipeSerializer<?> getSerializer() {
		return JerotesVillageRecipeSerializers.TABLET_PRESSING.get();
	}
	public static class TabletPressingSerializer implements RecipeSerializer<TabletPressingRecipe> {

		public TabletPressingSerializer() {

		}

		@Override
		public TabletPressingRecipe fromJson(ResourceLocation resourceLocation, com.google.gson.JsonObject jsonObject) {
			final NonNullList<Ingredient> baseIn = readIngredients(GsonHelper.getAsJsonArray(jsonObject, "base"));
			final NonNullList<Ingredient> additionIn = readIngredients(GsonHelper.getAsJsonArray(jsonObject, "addition"));
			ItemStack itemstack = ShapedRecipe.itemStackFromJson(GsonHelper.getAsJsonObject(jsonObject, "result"));
			TabletPressingCraftingBookCategory tabletPressingCraftingBookCategory = TabletPressingCraftingBookCategory.CODEC.byName(GsonHelper.getAsString(jsonObject, "category", (String)null), TabletPressingCraftingBookCategory.MISC);
			return new TabletPressingRecipe(resourceLocation, baseIn, additionIn, itemstack, tabletPressingCraftingBookCategory);
		}

		private static NonNullList<Ingredient> readIngredients(JsonArray ingredientArray) {
			NonNullList<Ingredient> nonnulllist = NonNullList.create();
			for (int i = 0; i < ingredientArray.size(); ++i) {
				Ingredient ingredient = Ingredient.fromJson(ingredientArray.get(i));
				if (!ingredient.isEmpty()) {
					nonnulllist.add(ingredient);
				}
			}
			return nonnulllist;
		}

		@Override
		public TabletPressingRecipe fromNetwork(ResourceLocation resourceLocation, FriendlyByteBuf friendlyByteBuf) {
			int baseSize = friendlyByteBuf.readVarInt();
			NonNullList<Ingredient> base = NonNullList.withSize(baseSize, Ingredient.EMPTY);
			for (int j = 0; j < baseSize; ++j) {
				base.set(j, Ingredient.fromNetwork(friendlyByteBuf));
			}

			int additionSize = friendlyByteBuf.readVarInt();
			NonNullList<Ingredient> addition = NonNullList.withSize(additionSize, Ingredient.EMPTY);
			for (int j = 0; j < additionSize; ++j) {
				addition.set(j, Ingredient.fromNetwork(friendlyByteBuf));
			}

			ItemStack result = friendlyByteBuf.readItem();
			TabletPressingCraftingBookCategory category = TabletPressingCraftingBookCategory.valueOf(friendlyByteBuf.readUtf());
			return new TabletPressingRecipe(resourceLocation, base, addition, result, category);
		}

		@Override
		public void toNetwork(FriendlyByteBuf friendlyByteBuf, TabletPressingRecipe recipe) {
			friendlyByteBuf.writeVarInt(recipe.base.size());
			for (Ingredient ingredient : recipe.base) {
				ingredient.toNetwork(friendlyByteBuf);
			}

			friendlyByteBuf.writeVarInt(recipe.addition.size());
			for (Ingredient ingredient : recipe.addition) {
				ingredient.toNetwork(friendlyByteBuf);
			}

			friendlyByteBuf.writeItem(recipe.result);
			friendlyByteBuf.writeUtf(recipe.category.name());
		}
	}
}