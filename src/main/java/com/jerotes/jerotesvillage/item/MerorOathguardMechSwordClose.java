package com.jerotes.jerotesvillage.item;

import com.jerotes.jerotes.item.Tool.ItemToolBaseHammer;
import com.jerotes.jerotesvillage.init.JerotesVillageItems;
import com.jerotes.jerotesvillage.init.JerotesVillageSoundEvents;
import net.minecraft.ChatFormatting;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.MutableComponent;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.*;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.level.Level;

import javax.annotation.Nullable;
import java.util.List;

public class MerorOathguardMechSwordClose extends ItemToolBaseHammer {
    public MerorOathguardMechSwordClose() {
        super(new Tier() {
            public int getUses() {
                return 12800;
            }

            public float getSpeed() {
                return 18f;
            }

            public float getAttackDamageBonus() {
                return 9f;
            }

            public int getLevel() {
                return 5;
            }

            public int getEnchantmentValue() {
                return 28;
            }

            public Ingredient getRepairIngredient() {
                return Ingredient.of(new ItemStack(JerotesVillageItems.REFINE_MEROR_METAL_INGOT.get()));
            }
        }, -1, 2.6f - 4f, new Properties().fireResistant().rarity(Rarity.EPIC));
    }

    public Item getOtherMode() {
        return JerotesVillageItems.MEROR_OATHGUARD_MECH_SWORD.get();
    }

    @Override
    public InteractionResultHolder<ItemStack> use(Level level, Player player, InteractionHand interactionHand) {
        ItemStack itemStack = player.getItemInHand(interactionHand);
        if (player.isShiftKeyDown() && getOtherMode() != null) {
            ItemStack itemStack1 = new ItemStack(getOtherMode());
            itemStack1.deserializeNBT(itemStack.getOrCreateTag());
            itemStack1.setTag(itemStack.getOrCreateTag());
            itemStack1.setDamageValue(itemStack.getDamageValue());
            player.setItemInHand(interactionHand, itemStack1);
            player.getCooldowns().addCooldown(itemStack.getItem(), 20);
            player.getCooldowns().addCooldown(itemStack1.getItem(), 20);
            if (!player.isSilent()) {
                player.level().playSound(null, player.getX(), player.getY(), player.getZ(), JerotesVillageSoundEvents.MEROR_TOOL_USE, player.getSoundSource(), 1.0f, 1.0f);
            }
            return InteractionResultHolder.success(itemStack);
        }
        return super.use(level, player, interactionHand);
    }

    @Override
    public void appendHoverText(ItemStack itemStack, @Nullable Level level, List<Component> list, TooltipFlag tooltipFlag) {
        super.appendHoverText(itemStack, level, list, tooltipFlag);
        list.add(this.getDisplayName().withStyle(ChatFormatting.GRAY));
    }
    public MutableComponent getDisplayName() {
        return Component.translatable(this.getDescriptionId() + ".desc");
    }
}

