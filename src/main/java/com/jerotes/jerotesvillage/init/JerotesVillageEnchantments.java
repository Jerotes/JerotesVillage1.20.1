package com.jerotes.jerotesvillage.init;

import com.jerotes.jerotesvillage.JerotesVillage;
import com.jerotes.jerotesvillage.enchantment.ElasticityEnchantment;
import com.jerotes.jerotesvillage.item.Tool.ItemToolBaseThrowingBall;
import com.jerotes.jerotesvillage.item.Tool.ItemToolBaseThrowingBallGlove;
import net.minecraft.world.item.enchantment.Enchantment;
import net.minecraft.world.item.enchantment.EnchantmentCategory;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

public class JerotesVillageEnchantments {
	public static final EnchantmentCategory THROWING_BALL_ABOUT = EnchantmentCategory.create("throwing_ball_about", item -> item instanceof ItemToolBaseThrowingBall || item instanceof ItemToolBaseThrowingBallGlove);

	public static final DeferredRegister<Enchantment> REGISTRY = DeferredRegister.create(ForgeRegistries.ENCHANTMENTS, JerotesVillage.MODID);
	public static final RegistryObject<Enchantment> ELASTICITY = REGISTRY.register("elasticity", () -> new ElasticityEnchantment());
}