package com.jerotes.jerotesvillage.item;

import com.jerotes.jerotes.entity.Shoot.Arrow.ThrownSimpleJavelinEntity;
import com.jerotes.jerotes.init.JerotesGameRules;
import com.jerotes.jerotes.item.Interface.MagicItem;
import com.jerotes.jerotes.item.Tool.ItemToolBaseJavelin;
import com.jerotes.jerotes.spell.MagicSpell;
import com.jerotes.jerotes.spell.SpellTypeInterface;
import com.jerotes.jerotes.util.Main;
import com.jerotes.jerotesvillage.init.JerotesVillageItems;
import com.jerotes.jerotesvillage.spell.OtherSpellList;
import com.jerotes.jerotesvillage.spell.OtherSpellType;
import net.minecraft.ChatFormatting;
import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.MutableComponent;
import net.minecraft.stats.Stats;
import net.minecraft.util.Mth;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.entity.projectile.Projectile;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Rarity;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.item.UseAnim;
import net.minecraft.world.level.Level;

import java.util.ArrayList;
import java.util.List;

public class SeawaveTrident extends ItemToolBaseJavelin implements MagicItem {
	public SeawaveTrident() {
		super(new Properties().stacksTo(1).durability(800).rarity(Rarity.UNCOMMON), 10f, 1.0f);
	}

	@Override
	public Projectile JerotesThrownJavelin(LivingEntity livingEntity, ItemStack itemStack) {
		return new ThrownSimpleJavelinEntity(livingEntity.level(), livingEntity, itemStack);
	}
	public float getThrowingDamage() {
		return 0.0f;
	}
	public boolean JerotesThrownJavelinCanRange() {
		return false;
	}

	@Override
	public void JerotesNormalThrownJavelinAnim(ModelPart main, ModelPart off, ModelPart add, boolean bl) {
		main.xRot = main.xRot * 0.5f - 0.9424779f + Mth.clamp(add.xRot, -1.3962634f, 0.43633232f);
		main.yRot = (bl ? -30.0f : 30.0f) * 0.017453292f + Mth.clamp(add.yRot, -0.5235988f, 0.5235988f);
	}
	public boolean JerotesUseSpecialThrownJavelinAnim() {
		return true;
	}

	@Override
	public UseAnim getUseAnimation(ItemStack itemStack) {
		return UseAnim.BLOCK;
	}

	@Override
	public boolean isValidRepairItem(ItemStack itemStack, ItemStack itemStack2) {
		return itemStack2.is(JerotesVillageItems.BULGE_ROD.get()) || super.isValidRepairItem(itemStack, itemStack2);
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
	public void releaseUsing(ItemStack itemStack, Level level, LivingEntity livingEntity, int n) {
		int n2 = this.getUseDuration(itemStack) - n;
		if (n2 < 10) {
			return;
		}
		if (Main.getJerotesPersistentData(livingEntity).getDouble("jerotes_spell_cooldown") > 0) {
			return;
		}
		livingEntity.swing(InteractionHand.MAIN_HAND, true);
		//法术列表-宝石海浪
		boolean spellUse = OtherSpellList.GemstoneWaves(3, livingEntity, livingEntity).spellUse();
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
		return "jerotesvillage_gemstone_waves";
	}

	@Override
	public void appendHoverText(ItemStack itemStack, Level level, List<Component> list, TooltipFlag tooltipFlag) {
		super.appendHoverText(itemStack, level, list, tooltipFlag);
		MagicSpell.MagicTooltip(list, OtherSpellList.GemstoneWaves(3, null, null), trueLevel(itemStack), null, itemStack);
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
		return OtherSpellList.GemstoneWaves(3, null, null).getSpellLevel();
	}

	@Override
	public List<SpellTypeInterface> getMainSpellTypeMagicItem(ItemStack itemStack) {
		List<SpellTypeInterface> spellList = new ArrayList<>();
		spellList.add(OtherSpellType.JEROTESVILLAGE_GEMSTONE_WAVES);
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
		return OtherSpellList.GemstoneWaves(3, null, null).getSpellDistance();
	}
}


