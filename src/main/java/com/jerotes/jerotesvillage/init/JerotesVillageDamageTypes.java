package com.jerotes.jerotesvillage.init;

import com.jerotes.jerotesvillage.JerotesVillage;
import net.minecraft.core.Registry;
import net.minecraft.core.RegistryAccess;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.damagesource.DamageType;
import net.minecraft.world.entity.Entity;

import javax.annotation.Nullable;

public class JerotesVillageDamageTypes {
	private final Registry<DamageType> damageTypes;
	public static final ResourceKey<DamageType> BITTER_COLD_FALL_ATTACK = ResourceKey.create(Registries.DAMAGE_TYPE, new ResourceLocation(JerotesVillage.MODID, "bitter_cold_fall_attack"));
	public static final ResourceKey<DamageType> AX_CRAZY_ATTACK = ResourceKey.create(Registries.DAMAGE_TYPE, new ResourceLocation(JerotesVillage.MODID, "ax_crazy_attack"));
	public static final ResourceKey<DamageType> CELESTIAL_COILVINE_ATTACK = ResourceKey.create(Registries.DAMAGE_TYPE, new ResourceLocation(JerotesVillage.MODID, "celestial_coilvine_attack"));

	public JerotesVillageDamageTypes(RegistryAccess registryAccess) {
		this.damageTypes = registryAccess.registryOrThrow(Registries.DAMAGE_TYPE);
	}

	private DamageSource source(ResourceKey<DamageType> resourceKey, @Nullable Entity entity, @Nullable Entity entity2) {
		return new DamageSource(this.damageTypes.getHolderOrThrow(resourceKey), entity, entity2);
	}

	public DamageSource bitter_cold_fall_attack(Entity entity, @Nullable Entity entity2) {
		return this.source(JerotesVillageDamageTypes.BITTER_COLD_FALL_ATTACK, entity, entity2);
	}
	public DamageSource ax_crazy_attack(Entity entity, @Nullable Entity entity2) {
		return this.source(JerotesVillageDamageTypes.AX_CRAZY_ATTACK, entity, entity2);
	}
	public DamageSource celestial_coilvine_attack(Entity entity, @Nullable Entity entity2) {
		return this.source(JerotesVillageDamageTypes.CELESTIAL_COILVINE_ATTACK, entity, entity2);
	}
}