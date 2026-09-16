package com.jerotes.jerotesvillage.item;

import com.jerotes.jerotes.entity.Interface.FireAbsorptionEntity;
import com.jerotes.jerotes.init.JerotesGameRules;
import com.jerotes.jerotes.init.JerotesMobEffects;
import com.jerotes.jerotes.item.Interface.MagicItem;
import com.jerotes.jerotes.item.Tool.ItemToolBaseParryShield;
import com.jerotes.jerotes.spell.MagicSpell;
import com.jerotes.jerotes.spell.SpellType;
import com.jerotes.jerotes.spell.SpellTypeInterface;
import com.jerotes.jerotes.util.AttackFind;
import com.jerotes.jerotes.util.Main;
import com.jerotes.jerotesvillage.spell.OtherSpellList;
import com.jerotes.jerotesvillage.spell.OtherSpellType;
import net.minecraft.ChatFormatting;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.MutableComponent;
import net.minecraft.stats.Stats;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.Rarity;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.level.Level;

import java.util.ArrayList;
import java.util.List;

public class DroughtWildfireShield extends ItemToolBaseParryShield implements MagicItem {
	public DroughtWildfireShield() {
		super(new Properties().stacksTo(1).durability(800 * 3).fireResistant().rarity(Rarity.UNCOMMON), 4f, 1.2f, 15, Ingredient.of(Items.BLAZE_ROD)	, 40, 4, 1.3f, 5.5f, 1.1f);
	}
	public boolean isMeleeWeapon() {
		return true;
	}

	@Override
	public void onUseTick(Level level, LivingEntity livingEntity, ItemStack itemStack, int count) {
		if ((float)(itemStack.getUseDuration() - count) == 10) {
			//法术列表-蛇龙火舌术
			boolean spellUse = OtherSpellList.SerponFlames(3, livingEntity, livingEntity).spellUse();
			if (spellUse) {
				damageMagicItem(livingEntity, itemStack);
				if (livingEntity instanceof Player player)
					player.awardStat(Stats.ITEM_USED.get(this));
			}
		}
	}
	@Override
	public void releaseUsing(ItemStack itemStack, Level level, LivingEntity livingEntity, int n) {
		int n2 = this.getUseDuration(itemStack) - n;
		if (n2 < 10) {
			return;
		}
		if (Main.getJerotesPersistentData(livingEntity).getDouble("jerotes_spell_cooldown") > 0) {
			return;
		}
		livingEntity.swing(InteractionHand.MAIN_HAND, true);
		if (SpellType.getCastingTickJerotes(livingEntity) > -1 && SpellType.getLastSpellTypeJerotes(livingEntity).equals(getSpellIdMagicItem(itemStack))) {
			SpellType.stopCasting(livingEntity, 999, false);
		}
	}
	@Override
	public InteractionResultHolder<ItemStack> use(Level level, Player player, InteractionHand interactionHand) {
		ItemStack itemStack = player.getItemInHand(interactionHand);
		if (itemStack.getDamageValue() >= itemStack.getMaxDamage() - 1) {
			return InteractionResultHolder.fail(itemStack);
		}
		player.startUsingItem(interactionHand);
		return InteractionResultHolder.consume(itemStack);
	}
	@Override
	public void attackUse(Entity self, Entity attackTo) {
		attackTo.setSecondsOnFire(12);
	}
	public void blockUse(Entity self, Entity attackBy, DamageSource damageSource) {
		if (attackBy instanceof LivingEntity livingEntity &&
				AttackFind.isMeleeDamage(damageSource) &&
				!(attackBy instanceof FireAbsorptionEntity) &&
				!(livingEntity.hasEffect(JerotesMobEffects.FIRE_ABSORPTION.get()))) {
			attackBy.setSecondsOnFire(12);
		}
	}

	public boolean hasSpellMagicItem() {
		return true;
	}
	public String getSpellIdMagicItem(ItemStack itemStack) {
		return "jerotesvillage_serpon_flames";
	}

	@Override
	public void appendHoverText(ItemStack itemStack, Level level, List<Component> list, TooltipFlag tooltipFlag) {
		super.appendHoverText(itemStack, level, list, tooltipFlag);
		MagicSpell.MagicTooltip(list, OtherSpellList.SerponFlames(3, null, null), trueLevel(itemStack), null, itemStack);
		list.add(this.getDisplayName().withStyle(ChatFormatting.GRAY));
	}

	public MutableComponent getDisplayName() {
		return Component.translatable(this.getDescriptionId() + ".desc");
	}

	@Override
	public int getEnchantmentValue() {
		return 15;
	}

	public int trueLevel(ItemStack itemStack) {
		return OtherSpellList.SerponFlames(3, null, null).getSpellLevel();
	}

	@Override
	public List<SpellTypeInterface> getMainSpellTypeMagicItem(ItemStack itemStack) {
		List<SpellTypeInterface> spellList = new ArrayList<>();
		spellList.add(OtherSpellType.JEROTESVILLAGE_SERPON_FLAMES);
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
		return OtherSpellList.SerponFlames(3, null, null).getSpellDistance();
	}
}
