package com.jerotes.jerotesvillage.entity.Monster.Resurrecter;

import com.jerotes.jerotes.spell.SpellTypeInterface;
import com.jerotes.jerotesvillage.init.JerotesVillageItems;
import com.jerotes.jerotesvillage.spell.OtherSpellType;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.util.RandomSource;
import net.minecraft.world.DifficultyInstance;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.MobSpawnType;
import net.minecraft.world.entity.SpawnGroupData;
import net.minecraft.world.entity.ai.attributes.AttributeSupplier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.ServerLevelAccessor;

import javax.annotation.Nullable;
import java.util.ArrayList;
import java.util.List;

public class SedimentSoulStabilizerEntity extends ResurrecterEntity {

	public SedimentSoulStabilizerEntity(EntityType type, Level world) {
		super(type, world);
		xpReward = 20;
	}

	@Override
	protected void populateDefaultEquipmentSlots(RandomSource randomSource, DifficultyInstance difficultyInstance) {
	}

	@Override
	public List<SpellTypeInterface> SelfMainSpellList() {
		List<SpellTypeInterface> spellList = new ArrayList<>();
		//技能列表-沉降飞弹
		spellList.add(OtherSpellType.JEROTESVILLAGE_SEDIMENT_MISSILE);
		return spellList;
	}
	@Override
	public List<SpellTypeInterface> SelfAddSpellList() {
		List<SpellTypeInterface> spellList = new ArrayList<>();
		return spellList;
	}

	public static AttributeSupplier.Builder createAttributes() {
		AttributeSupplier.Builder builder = Mob.createMobAttributes();
		builder = builder.add(Attributes.MOVEMENT_SPEED, 0.27);
		builder = builder.add(Attributes.MAX_HEALTH, 35);
		builder = builder.add(Attributes.ARMOR, 0);
		builder = builder.add(Attributes.ATTACK_DAMAGE, 5);
		builder = builder.add(Attributes.FOLLOW_RANGE, 35);
		return builder;
	}

	public int spellLevel = 3;
	@Override
	public int getSpellLevel() {
		return this.spellLevel;
	}
	@Override
	public boolean hurt(DamageSource damageSource, float amount) {
		if (isInvulnerableTo(damageSource)) {
			return super.hurt(damageSource, amount);
		}
		return super.hurt(damageSource, amount);
	}

	@Nullable
	@Override
	public SpawnGroupData finalizeSpawn(ServerLevelAccessor serverLevelAccessor, DifficultyInstance difficultyInstance, MobSpawnType mobSpawnType, @Nullable SpawnGroupData spawnGroupData, @Nullable CompoundTag compoundTag) {
		this.setCombatStyle(2);
		super.finalizeSpawn(serverLevelAccessor, difficultyInstance, mobSpawnType, spawnGroupData, compoundTag);
		return spawnGroupData;
	}
	@Override
	public ItemStack createSpawnWeapon(float weaponRandom) {
		return new ItemStack(JerotesVillageItems.ANCIENT_SECOND_ROUNDER_DAGGER.get());
	}
}
