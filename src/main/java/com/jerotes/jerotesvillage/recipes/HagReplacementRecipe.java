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

public class HagReplacementRecipe implements Recipe<Container> {
	private final ResourceLocation id;
	@Override
	public ResourceLocation getId() {
		return id;
	}
	private final NonNullList<Ingredient> base;
	private final NonNullList<Ingredient> result;

	public HagReplacementRecipe(ResourceLocation id, NonNullList<Ingredient> base, NonNullList<Ingredient> result) {
		this.id = id;
		this.base = base;
		this.result = result;
	}

	public NonNullList<Ingredient> getBase() {
		return base;
	}
	public NonNullList<Ingredient> getResult() {
		return result;
	}

	@Override
	public boolean matches(Container container, Level level) {
		return false;
	}

	@Override
	public ItemStack assemble(Container container, RegistryAccess registryAccess) {
		return result.get(0).getItems()[0];
	}

	@Override
	public boolean canCraftInDimensions(int width, int height) {
		return true;
	}

	@Override
	public ItemStack getResultItem(RegistryAccess registryAccess) {
		return result.get(0).getItems()[0];
	}

	@Override
	public RecipeSerializer<?> getSerializer() {
		return JerotesVillageRecipeSerializers.HAG_REPLACEMENT.get();
	}

	@Override
	public RecipeType<?> getType() {
		return JerotesVillageRecipeType.HAG_REPLACEMENT.get();
	}

	@Override
	public ItemStack getToastSymbol() {
		return new ItemStack(JerotesVillageItems.NEW_HAGS_CAULDRON.get());
	}

	public static class HagReplacementSerializer implements RecipeSerializer<HagReplacementRecipe> {

		public HagReplacementSerializer() {

		}

		@Override
		public HagReplacementRecipe fromJson(ResourceLocation resourceLocation, com.google.gson.JsonObject jsonObject) {
			final NonNullList<Ingredient> baseIn = readIngredients(GsonHelper.getAsJsonArray(jsonObject, "base"));
			final NonNullList<Ingredient> resultIn = readIngredients(GsonHelper.getAsJsonArray(jsonObject, "result"));
			return new HagReplacementRecipe(resourceLocation, baseIn, resultIn);
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

		// 修复网络序列化/反序列化
		@Override
		public HagReplacementRecipe fromNetwork(ResourceLocation resourceLocation, FriendlyByteBuf friendlyByteBuf) {
			// 读取base列表
			int baseSize = friendlyByteBuf.readVarInt();
			NonNullList<Ingredient> base = NonNullList.withSize(baseSize, Ingredient.EMPTY);
			for (int j = 0; j < baseSize; ++j) {
				base.set(j, Ingredient.fromNetwork(friendlyByteBuf));
			}

			// 读取result列表
			int resultSize = friendlyByteBuf.readVarInt();
			NonNullList<Ingredient> result = NonNullList.withSize(resultSize, Ingredient.EMPTY);
			for (int j = 0; j < resultSize; ++j) {
				result.set(j, Ingredient.fromNetwork(friendlyByteBuf));
			}
			return new HagReplacementRecipe(resourceLocation, base, result);
		}

		@Override
		public void toNetwork(FriendlyByteBuf friendlyByteBuf, HagReplacementRecipe recipe) {
			// 写入base列表大小和内容
			friendlyByteBuf.writeVarInt(recipe.base.size());
			for (Ingredient ingredient : recipe.base) {
				ingredient.toNetwork(friendlyByteBuf);
			}

			// 写入result列表大小和内容
			friendlyByteBuf.writeVarInt(recipe.result.size());
			for (Ingredient ingredient : recipe.result) {
				ingredient.toNetwork(friendlyByteBuf);
			}
		}
	}
}