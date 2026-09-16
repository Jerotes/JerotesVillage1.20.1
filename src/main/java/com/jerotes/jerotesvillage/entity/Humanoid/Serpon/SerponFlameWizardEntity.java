package com.jerotes.jerotesvillage.entity.Humanoid.Serpon;

import com.jerotes.jerotes.entity.Interface.SpellUseEntity;
import com.jerotes.jerotes.spell.MagicType;
import com.jerotes.jerotes.spell.SpellType;
import com.jerotes.jerotes.spell.SpellTypeInterface;
import com.jerotes.jerotesvillage.init.JerotesVillageItems;
import com.jerotes.jerotesvillage.spell.OtherSpellType;
import net.minecraft.ChatFormatting;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.util.RandomSource;
import net.minecraft.world.DifficultyInstance;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.*;
import net.minecraft.world.entity.ai.attributes.AttributeSupplier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.ServerLevelAccessor;

import javax.annotation.Nullable;
import java.util.ArrayList;
import java.util.List;


public class SerponFlameWizardEntity extends SerponEntity implements SpellUseEntity {
	public SerponFlameWizardEntity(EntityType<? extends SerponFlameWizardEntity> entityType, Level level) {
		super(entityType, level);
		this.xpReward = 30;
	}

	@Override
	public boolean wantsToPickUp(ItemStack itemStack) {
		if (itemStack.canEquip(EquipmentSlot.HEAD,this)) {
			return false;
		}
		if (itemStack.canEquip(EquipmentSlot.CHEST,this)) {
			return false;
		}
		if (itemStack.canEquip(EquipmentSlot.LEGS,this)) {
			return false;
		}
		if (itemStack.canEquip(EquipmentSlot.FEET,this)) {
			return false;
		}
		if (itemStack.canEquip(EquipmentSlot.MAINHAND,this)) {
			return false;
		}
		if (itemStack.canEquip(EquipmentSlot.OFFHAND,this)) {
			return false;
		}
		return super.wantsToPickUp(itemStack);
	}

	@Override
	protected void populateDefaultEquipmentSlots(RandomSource randomSource, DifficultyInstance difficultyInstance) {
		this.maybeWearArmor(EquipmentSlot.HEAD, new ItemStack(JerotesVillageItems.SERPON_WIZARD_HELMET.get()), randomSource);
		this.maybeWearArmor(EquipmentSlot.CHEST, new ItemStack(JerotesVillageItems.SERPON_WIZARD_CHESTPLATE.get()), randomSource);
		this.maybeWearArmor(EquipmentSlot.LEGS, new ItemStack(JerotesVillageItems.SERPON_WIZARD_LEGGINGS.get()), randomSource);
		this.maybeWearArmor(EquipmentSlot.FEET, new ItemStack(JerotesVillageItems.SERPON_WIZARD_BOOTS.get()), randomSource);
	}

	private void maybeWearArmor(EquipmentSlot equipmentSlot, ItemStack itemStack, RandomSource randomSource) {
		this.setItemSlot(equipmentSlot, itemStack);
	}

	public static AttributeSupplier.Builder createAttributes() {
		AttributeSupplier.Builder builder = Mob.createMobAttributes();
		builder = builder.add(Attributes.MOVEMENT_SPEED, 0.4);
		builder = builder.add(Attributes.FLYING_SPEED, 0.5);
		builder = builder.add(Attributes.MAX_HEALTH, 40);
		builder = builder.add(Attributes.ATTACK_KNOCKBACK, 0.1);
		builder = builder.add(Attributes.ATTACK_DAMAGE, 5);
		builder = builder.add(Attributes.FOLLOW_RANGE, 48);
		return builder;
	}

	@Override
	public List<SpellTypeInterface> SelfMainSpellList() {
		List<SpellTypeInterface> spellList = new ArrayList<>();

		String string = ChatFormatting.stripFormatting(this.getName().getString());
		if ("Fireball".equals(string)) {
			spellList.add(SpellType.JEROTES_FIREBALL);
			spellList.add(SpellType.JEROTES_FIREBALL);
			return spellList;
		}
		else {
			if (!(this.getTarget() != null && this.distanceTo(this.getTarget()) > 8)) {
				//技能列表-蛇龙火舌术
				spellList.add(OtherSpellType.JEROTESVILLAGE_SERPON_FLAMES);
			}
			else {
				//技能列表-蛇龙火球
				spellList.add(OtherSpellType.JEROTESVILLAGE_SERPON_FIREBALL);
			}
			//技能列表-蛇龙火球
			spellList.add(OtherSpellType.JEROTESVILLAGE_SERPON_FIREBALL);
			return spellList;
		}
	}

	@Override
	public void SpellUseAfterAttack(String string, MagicType magicType, MagicType magicType2) {
		if (string.equals("jerotesvillage_serpon_flames")) {
			if (!this.level().isClientSide()) {
				this.setSpellTick(20);
			}
		}
		else {
			super.SpellUseAfterAttack(string, magicType, magicType2);
		}
	}

	public int spellLevel = 4;
	@Override
	public int getSpellLevel() {
		return this.spellLevel;
	}
	@Override
	public boolean isSpellHumanoidTwoHanded() {
		return true;
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
		this.setCombatStyle(4);
		super.finalizeSpawn(serverLevelAccessor, difficultyInstance, mobSpawnType, spawnGroupData, compoundTag);
		return spawnGroupData;
	}
}

