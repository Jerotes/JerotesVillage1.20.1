package com.jerotes.jerotesvillage.item;

import com.jerotes.jerotesvillage.init.JerotesVillageFoods;
import net.minecraft.ChatFormatting;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.MutableComponent;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.*;
import net.minecraft.world.level.Level;

import javax.annotation.Nullable;
import java.util.List;

public class AloeVeraPulp extends Item {
	public static final String TAG_EXTRACT_LEVEL = "ExtractLevel";

	public AloeVeraPulp() {
		super(new Properties().stacksTo(1).rarity(Rarity.UNCOMMON).food(JerotesVillageFoods.ALOE_VERA_PULP));
	}


	@Override
	public Rarity getRarity(ItemStack itemStack) {
		if (!itemStack.isEnchanted()) {
			return this.TrueRarity(itemStack);
		} else {
            return switch (this.TrueRarity(itemStack)) {
                case COMMON, UNCOMMON -> Rarity.RARE;
                case RARE -> Rarity.EPIC;
                default -> this.TrueRarity(itemStack);
            };
		}
	}

	public Rarity TrueRarity(ItemStack itemStack) {
		if (itemStack.getItem() instanceof AloeVeraPulp aloeVeraPulp && aloeVeraPulp.getExtractLevel(itemStack) > 3) {
			if (aloeVeraPulp.getExtractLevel(itemStack) >= 6) {
                return switch (super.getRarity(itemStack)) {
                    case COMMON -> Rarity.UNCOMMON;
                    case UNCOMMON -> Rarity.RARE;
                    case RARE -> Rarity.EPIC;
                    default -> super.getRarity(itemStack);
                };
			}
            return switch (super.getRarity(itemStack)) {
                case COMMON -> Rarity.UNCOMMON;
                case UNCOMMON -> Rarity.RARE;
                case RARE -> Rarity.EPIC;
                default -> super.getRarity(itemStack);
            };
		}
		return super.getRarity(itemStack);
	}

	@Override
	public int getUseDuration(ItemStack itemStack) {
		return 20;
	}

	@Override
	public UseAnim getUseAnimation(ItemStack itemStack) {
		return UseAnim.DRINK;
	}

	@Override
	public SoundEvent getDrinkingSound() {
		return SoundEvents.GENERIC_DRINK;
	}

	@Override
	public SoundEvent getEatingSound() {
		return SoundEvents.GENERIC_DRINK;
	}

	@Override
	public InteractionResultHolder<ItemStack> use(Level level, Player player, InteractionHand interactionHand) {
		return ItemUtils.startUsingInstantly(level, player, interactionHand);
	}

	@Override
	public ItemStack finishUsingItem(ItemStack itemStack, Level level, LivingEntity livingEntity) {
		int n = (int) (this.getExtractLevel(itemStack) / 3f);
		int n2 = (this.getExtractLevel(itemStack) * 3 + 4) * 20;
		if (!level.isClientSide()) {
			livingEntity.addEffect(new MobEffectInstance(MobEffects.REGENERATION, n2, n), livingEntity);
		}
		Player player;
		super.finishUsingItem(itemStack, level, livingEntity);
		if (itemStack.isEmpty()) {
			return new ItemStack(Items.GLASS_BOTTLE);
		}
		if (livingEntity instanceof Player) {
			player = (Player)livingEntity;
			if (!player.getAbilities().instabuild) {
				ItemStack itemStack2 = new ItemStack(Items.GLASS_BOTTLE);
				if (!player.getInventory().add(itemStack2)) {
					player.drop(itemStack2, false);
				}
			}
		}
		return itemStack;
	}

	public boolean isFoil(ItemStack itemStack) {
		return this.getExtractLevel(itemStack) >= 5 || super.isFoil(itemStack);
	}


	public int getExtractLevel(ItemStack itemStack) {
		CompoundTag compoundtag = itemStack.hasTag() ? itemStack.getTag().copy() : new CompoundTag();
		return compoundtag.getInt(TAG_EXTRACT_LEVEL) + 1;
	}

	public void addExtractLevel(ItemStack itemStack) {
		CompoundTag compoundtag = itemStack.hasTag() ? itemStack.getTag().copy() : new CompoundTag();
		compoundtag.putInt(TAG_EXTRACT_LEVEL,  compoundtag.getInt(TAG_EXTRACT_LEVEL) + 1);
		itemStack.setTag(compoundtag);
	}

	@Override
	public String getDescriptionId(ItemStack itemStack) {
		if (itemStack.getItem() instanceof AloeVeraPulp aloeVeraPulp) {
			if (aloeVeraPulp.getExtractLevel(itemStack) == 1) {
				return "item.jerotesvillage.aloe_vera_pulp";
			}
			else if (aloeVeraPulp.getExtractLevel(itemStack) == 2) {
				return "item.jerotesvillage.aloe_vera_pulp_2";
			}
			else if (aloeVeraPulp.getExtractLevel(itemStack) == 3) {
				return "item.jerotesvillage.aloe_vera_pulp_3";
			}
			else if (aloeVeraPulp.getExtractLevel(itemStack) == 4) {
				return "item.jerotesvillage.aloe_vera_pulp_4";
			}
			else if (aloeVeraPulp.getExtractLevel(itemStack) == 5) {
				return "item.jerotesvillage.aloe_vera_pulp_5";
			}
			else if (aloeVeraPulp.getExtractLevel(itemStack) == 6) {
				return "item.jerotesvillage.aloe_vera_pulp_6";
			}
		}
		return super.getDescriptionId(itemStack);
	}

	@Override
	public void appendHoverText(ItemStack itemStack, @Nullable Level level, List<Component> list, TooltipFlag tooltipFlag) {
		super.appendHoverText(itemStack, level, list, tooltipFlag);
		list.add(this.getDisplayName(itemStack).withStyle(ChatFormatting.GRAY));
	}

	public MutableComponent getDisplayName(ItemStack itemStack) {
		return Component.translatable(this.getDescriptionId() + ".desc", this.getExtractLevel(itemStack));
	}
}
