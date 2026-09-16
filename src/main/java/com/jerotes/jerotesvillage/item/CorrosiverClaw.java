package com.jerotes.jerotesvillage.item;

import com.jerotes.jerotes.init.JerotesGameRules;
import com.jerotes.jerotes.init.JerotesMobEffects;
import com.jerotes.jerotes.item.FlawlessMagicScroll;
import com.jerotes.jerotes.item.Interface.MagicItem;
import com.jerotes.jerotes.item.Tool.ItemToolBaseDagger;
import com.jerotes.jerotes.spell.MagicSpell;
import com.jerotes.jerotes.spell.SpellTypeInterface;
import com.jerotes.jerotes.util.Main;
import com.jerotes.jerotesvillage.init.JerotesVillageItems;
import com.jerotes.jerotesvillage.init.JerotesVillageMobEffects;
import com.jerotes.jerotesvillage.spell.OtherSpellList;
import com.jerotes.jerotesvillage.spell.OtherSpellType;
import net.minecraft.ChatFormatting;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.MutableComponent;
import net.minecraft.stats.Stats;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.*;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.level.Level;

import java.util.ArrayList;
import java.util.List;

public class CorrosiverClaw extends ItemToolBaseDagger implements MagicItem {
	public static final String SPELL_SUBCATEGORY = "SpellSubcategory";
	public CorrosiverClaw() {
		super(new Tier() {
			public int getUses() {
				return 2000;
			}

			public float getSpeed() {
				return 4f;
			}

			public float getAttackDamageBonus() {
				return 6f;
			}

			public int getLevel() {
				return 4;
			}

			public int getEnchantmentValue() {
				return 12;
			}

			public Ingredient getRepairIngredient() {
				return Ingredient.of(new ItemStack(JerotesVillageItems.ICE_ROCK.get()));
			}
		}, -1, 3.5f - 4f, new Properties().rarity(Rarity.EPIC));
	}

	@Override
	public void inventoryTick(ItemStack itemstack, Level world, Entity entity, int slot, boolean selected) {
		super.inventoryTick(itemstack, world, entity, slot, selected);
		if (!entity.level().isClientSide) {
			if (entity instanceof LivingEntity livingEntity) {
				livingEntity.addEffect(new MobEffectInstance(JerotesVillageMobEffects.BITTER_COLD_PROTECTION.get(), 205, 0, false, false));
			}
		}
	}


	@Override
	public void attackUse(Entity self, Entity attackTo) {
		if (!attackTo.level().isClientSide && attackTo instanceof LivingEntity livingEntity) {
			livingEntity.addEffect(new MobEffectInstance(JerotesMobEffects.CORROSIVE.get(), 170, 0));
			if (livingEntity.getTicksFrozen() < 280) {
				livingEntity.setTicksFrozen(280);
			}
		}
	}

	@Override
	public InteractionResultHolder<ItemStack> use(Level level, Player player, InteractionHand interactionHand) {
		ItemStack itemStack = player.getItemInHand(interactionHand);
		if (this.usePotion(level, player, interactionHand))
			return InteractionResultHolder.sidedSuccess(itemStack, level.isClientSide());
		if (itemStack.getDamageValue() >= itemStack.getMaxDamage() - 1) {
			return InteractionResultHolder.fail(itemStack);
		}
		player.startUsingItem(interactionHand);
		return InteractionResultHolder.consume(itemStack);
	}

	@Override
	public void releaseUsing(ItemStack itemStack, Level level, LivingEntity livingEntity, int n) {
		int n2 = this.getUseDuration(itemStack) - n;
		if (n2 < 20) {
			return;
		}
		if (Main.getJerotesPersistentData(livingEntity).getDouble("jerotes_spell_cooldown") > 0) {
			return;
		}
		livingEntity.swing(InteractionHand.MAIN_HAND, true);
		//法术列表-蚀龙兽吐息
		boolean spellUse = OtherSpellList.CorrosiverBreath(3, livingEntity, livingEntity).spellUse(getSubcategoryMagicItem(itemStack));
		if (spellUse) {
			damageMagicItem(livingEntity, itemStack);
			if (livingEntity instanceof Player player)
				player.awardStat(Stats.ITEM_USED.get(this));
		}
	}

	public boolean hasSpellMagicItem() {
		return true;
	}
	public String getSpellIdMagicItem(ItemStack itemStack) {
		return "jerotesvillage_corrosiver_breath";
	}
	public boolean canChangeSubcategoryMagicItem(ItemStack itemStack) {
		return true;
	}
	@Override
	public int getSubcategoryMagicItem(ItemStack itemStack) {
		CompoundTag tag = itemStack.getTag();
		return tag != null && tag.get(SPELL_SUBCATEGORY) != null ? tag.getInt(SPELL_SUBCATEGORY) : 0;
	}
	@Override
	public ItemStack setSubcategoryMagicItem(ItemStack itemStack, int n) {
		ItemStack stack = itemStack;
		CompoundTag compoundTag = new CompoundTag();
		compoundTag.putInt(FlawlessMagicScroll.SPELL_SUBCATEGORY, n);
		stack.setTag(compoundTag);
		return stack;
	}
	@Override
	public UseAnim getUseAnimation(ItemStack itemStack) {
		return UseAnim.BOW;
	}

	@Override
	public int getUseDuration(ItemStack itemStack) {
		return 72000;
	}

	@Override
	public void appendHoverText(ItemStack itemStack, Level level, List<Component> list, TooltipFlag tooltipFlag) {
		super.appendHoverText(itemStack, level, list, tooltipFlag);
		MagicSpell.MagicTooltip(list, OtherSpellList.CorrosiverBreath(3, null, null), trueLevel(itemStack), null, itemStack);
		list.add(this.getDisplayName_0().withStyle(ChatFormatting.GRAY));
		list.add(this.getDisplayName_1().withStyle(ChatFormatting.GRAY));
	}

	public MutableComponent getDisplayName_0() {
		return Component.translatable(this.getDescriptionId() + ".desc_0");
	}
	public MutableComponent getDisplayName_1() {
		return Component.translatable(this.getDescriptionId() + ".desc_1");
	}

	public int trueLevel(ItemStack itemStack) {
		return OtherSpellList.CorrosiverBreath(3, null, null).getSpellLevel();
	}

	@Override
	public List<SpellTypeInterface> getMainSpellTypeMagicItem(ItemStack itemStack) {
		List<SpellTypeInterface> spellList = new ArrayList<>();
		spellList.add(OtherSpellType.JEROTESVILLAGE_CORROSIVER_BREATH);
		return spellList;
	}
	@Override
	public List<SpellTypeInterface> getAddSpellTypeMagicItem(ItemStack itemStack) {
		return new ArrayList<>();
	}

	@Override
	public int getSpellLevelMagicItem(ItemStack itemStack) {
		return 3;
	}

	@Override
	public boolean isMeleeMagicItem(ItemStack itemStack) {
		return true;
	}
	@Override
	public boolean isHelpMagicItem(ItemStack itemStack) {
		return false;
	}

	@Override
	public void damageMagicItem(LivingEntity livingEntity, ItemStack itemStack) {
		if (livingEntity instanceof Player player) {
			itemStack.hurtAndBreak(1, player, player2 -> player2.broadcastBreakEvent(livingEntity.getUsedItemHand()));
		}
		else if (JerotesGameRules.JEROTES_MAGIC_CAN_BREAK != null && livingEntity.level().getLevelData().getGameRules().getBoolean(JerotesGameRules.JEROTES_MAGIC_CAN_BREAK)) {
			itemStack.hurtAndBreak(1, livingEntity, player2 -> player2.broadcastBreakEvent(EquipmentSlot.MAINHAND));
		}
	}

	@Override
	public float getSpellDistanceMagicItem(ItemStack itemStack) {
		return OtherSpellList.CorrosiverBreath(3, null, null).getSpellDistance();
	}
}
