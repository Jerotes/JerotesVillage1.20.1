package com.jerotes.jerotesvillage.util;

import com.jerotes.jerotes.init.JerotesItems;
import com.jerotes.jerotesvillage.block.Other.VillagerBlock;
import com.jerotes.jerotesvillage.config.OtherMainConfig;
import com.jerotes.jerotesvillage.event.WorldEvent;
import net.minecraft.core.RegistryAccess;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.tags.TagKey;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.item.*;
import net.minecraft.world.item.crafting.*;
import net.minecraft.world.level.Level;

import java.util.List;

public class OtherEntityAndItemFind {
	//是否泽林变体免疫
	public static boolean isZsieinVariantImmune(Entity entity) {
		return !WorldEvent.ZsieinExtremelyInfectedAbove(entity.level()) && entity.getType().is(TagKey.create(Registries.ENTITY_TYPE, new ResourceLocation("jerotesvillage:zsiein_variant_immune")));
	}
	//是否泽林影响免疫
	public static boolean isZsieinAboutImmune(Entity entity) {
		return !WorldEvent.ZsieinExtremelyInfectedAbove(entity.level()) && entity.getType().is(TagKey.create(Registries.ENTITY_TYPE, new ResourceLocation("jerotesvillage:zsiein_about_immune")));
	}

	//是否铁制品
	public static boolean targetIron(ItemStack metal, Level level) {
		return  isItemCraftedWithIron(metal.getItem(), level, Items.IRON_INGOT)
				|| isItemCraftedWithIron(metal.getItem(), level, Items.IRON_NUGGET)
				|| isItemCraftedWithIron(metal.getItem(), level, Items.IRON_BLOCK)
				|| metal.getItem().isValidRepairItem(metal, new ItemStack(Items.IRON_INGOT))
				|| metal.getItem().isValidRepairItem(metal, new ItemStack(Items.IRON_NUGGET))
				|| metal.getItem().isValidRepairItem(metal, new ItemStack(Items.IRON_BLOCK))
				//铁锭
				|| metal.getItem() == Items.IRON_INGOT
				//铁粒
				|| metal.getItem() == Items.IRON_NUGGET
				//铁块
				|| metal.getItem() == Items.IRON_BLOCK
				//铁栏杆
				|| metal.getItem() == Items.IRON_BARS
				//铁门
				|| metal.getItem() == Items.IRON_DOOR
				//铁马铠
				|| metal.getItem() == Items.IRON_HORSE_ARMOR
				//铁战兽铠
				|| metal.getItem() == JerotesItems.IRON_WAR_BEAST_ARMOR.get()
				//铁巨兽铠
				|| metal.getItem() == JerotesItems.IRON_GIANT_BEAST_ARMOR.get()
				//铁矿石
				|| metal.getItem() == Items.IRON_ORE
				//铁活板门
				|| metal.getItem() == Items.IRON_TRAPDOOR
				//粗铁
				|| metal.getItem() == Items.RAW_IRON
				//深层铁矿石
				|| metal.getItem() == Items.DEEPSLATE_IRON_ORE
				//粗铁块
				|| metal.getItem() == Items.RAW_IRON_BLOCK
				//铁砧
				|| metal.getItem() == Items.ANVIL
				|| metal.getItem() == Items.CHIPPED_ANVIL
				|| metal.getItem() == Items.DAMAGED_ANVIL
				//锁链
				|| metal.getItem() == Items.CHAIN
				//矿车
				|| metal.getItem() instanceof MinecartItem
				//铁轨
				|| metal.getItem() == Items.RAIL
				|| metal.getItem() == Items.ACTIVATOR_RAIL
				|| metal.getItem() == Items.DETECTOR_RAIL
				//轻质压力板
				|| metal.getItem() == Items.LIGHT_WEIGHTED_PRESSURE_PLATE
				//炼药锅
				|| metal.getItem() == Items.CAULDRON
				//漏斗
				|| metal.getItem() == Items.HOPPER
				//打火石
				|| metal.getItem() == Items.FLINT_AND_STEEL
				//剪刀
				|| metal.getItem() == Items.SHEARS
				//铁桶
				|| metal.getItem() instanceof BucketItem
				//轻质压力板
				|| metal.getItem() == Items.COMPASS
				//村民工作台
				|| (metal.getItem() instanceof BlockItem blockItem && blockItem.getBlock() instanceof VillagerBlock)
				;
	}

	//1.20.4↑//
//	public static boolean isItemCraftedWithIron(Item item, Level level, Item item2) {
//		if (level.isClientSide) return false;
//		if (!OtherMainConfig.RustBeetleFindIronAboutIsItemCraftedWithIron) return false;
//
//		RecipeManager recipeManager = ((ServerLevel) level).getRecipeManager();
//		List<RecipeHolder<CraftingRecipe>> recipes = recipeManager.getAllRecipesFor(RecipeType.CRAFTING);
//		for (RecipeHolder<CraftingRecipe> holder : recipes) {
//			CraftingRecipe recipe = holder.value();
//
//			ItemStack result = recipe.getResultItem(RegistryAccess.EMPTY);
//			if (result.getItem() == item) {
//				for (Ingredient ingredient : recipe.getIngredients()) {
//					for (ItemStack stack : ingredient.getItems()) {
//						if (stack.getItem() == item2) {
//							return true;
//						}
//					}
//				}
//			}
//		}
//		return false;
//	}
	//1.20.1//
   public static boolean isItemCraftedWithIron(Item item, Level level, Item item2) {
        if (level.isClientSide) return false;
        if (!OtherMainConfig.RustBeetleFindIronAboutIsItemCraftedWithIron) return false;
        RecipeManager recipeManager = ((ServerLevel) level).getRecipeManager();
        List<CraftingRecipe> recipes = recipeManager.getAllRecipesFor(RecipeType.CRAFTING);
        for (CraftingRecipe recipe : recipes) {
            ItemStack result = recipe.getResultItem(RegistryAccess.EMPTY);
            if (result.getItem() == item) {
                for (Ingredient ingredient : recipe.getIngredients()) {
                    for (ItemStack stack : ingredient.getItems()) {
                        if (stack.getItem() == item2) {
                            return true;
                        }
                    }
                }
            }
        }
        return false;
    }

}