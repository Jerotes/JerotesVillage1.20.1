package com.jerotes.jerotesvillage.item;

import com.jerotes.jerotesvillage.entity.Other.MagicAboutMob.AnimatedChainEntity;
import com.jerotes.jerotesvillage.init.JerotesVillageEntityType;
import com.jerotes.jerotes.item.Tool.*;
import com.jerotes.jerotes.util.Main;
import net.minecraft.ChatFormatting;
import net.minecraft.core.BlockPos;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.MutableComponent;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.MobSpawnType;
import net.minecraft.world.item.*;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.gameevent.GameEvent;
import net.minecraft.world.scores.PlayerTeam;

import java.util.List;

public class SlaverySupervisorHammerWhip extends ItemToolBaseFlail {
	public SlaverySupervisorHammerWhip() {
		super(new Tier() {
			public int getUses() {
				return 2031;
			}

			public float getSpeed() {
				return 9.0f;
			}

			public float getAttackDamageBonus() {
				return 12f;
			}

			public int getLevel() {
				return 4;
			}

			public int getEnchantmentValue() {
				return 15;
			}

			public Ingredient getRepairIngredient() {
				return Ingredient.of(new ItemStack(Items.NETHERITE_INGOT));
			}
		}, -1, 0.9f - 4f, new Properties().rarity(Rarity.UNCOMMON).fireResistant());
	}

	public void afterUseAttack(ItemStack itemStack, Level level, LivingEntity self, LivingEntity hurt, List<LivingEntity> list) {
		PlayerTeam teams = (PlayerTeam) self.getTeam();
		if (level instanceof ServerLevel serverLevel) {
			if (self.getRandom().nextFloat() > 0.4f) return;
			if (hurt.getHealth() > self.getHealth() * 2) return;
			if (!(Main.mobSizeSmall(hurt) || Main.mobSizeMedium(hurt) || Main.mobSizeLarge(hurt))) return;
			if (hurt instanceof AnimatedChainEntity) return;
			AnimatedChainEntity chain = JerotesVillageEntityType.ANIMATED_CHAIN.get().spawn(serverLevel, BlockPos.containing(hurt.getX(), hurt.getY(), hurt.getZ()), MobSpawnType.MOB_SUMMONED);
			if (chain != null) {
				chain.setOwner(self);
				chain.setPrisoner(hurt);
				chain.setSize(Main.mobWidth(hurt));
				if (teams != null) {
					serverLevel.getScoreboard().addPlayerToTeam(chain.getStringUUID(), teams);
				}
				chain.setTarget(hurt);
			}
			serverLevel.gameEvent(GameEvent.ENTITY_PLACE, new BlockPos((int) self.getX(), (int) self.getY(), (int) self.getZ()), GameEvent.Context.of(self));
		}
	}

	@Override
	public void appendHoverText(ItemStack itemStack, Level level, List<Component> list, TooltipFlag tooltipFlag) {
		super.appendHoverText(itemStack, level, list, tooltipFlag);
		list.add(this.getDisplayName().withStyle(ChatFormatting.GRAY));
	}

	public MutableComponent getDisplayName() {
		return Component.translatable(this.getDescriptionId() + ".desc");
	}
}
