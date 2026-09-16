package com.jerotes.jerotesvillage.item;

import com.jerotes.jerotesvillage.init.JerotesVillageItems;
import com.jerotes.jerotesvillage.init.JerotesVillageVillagerProfessions;
import net.minecraft.ChatFormatting;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.MutableComponent;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.village.ReputationEventType;
import net.minecraft.world.entity.item.ItemEntity;
import net.minecraft.world.entity.npc.AbstractVillager;
import net.minecraft.world.entity.npc.Villager;
import net.minecraft.world.entity.npc.VillagerDataHolder;
import net.minecraft.world.entity.npc.VillagerProfession;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Rarity;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.level.Level;

import java.util.List;

public class VillagerMetalBase extends Item {
	public VillagerMetalBase() {
		super(new Properties().stacksTo(64).rarity(Rarity.COMMON));
	}

	public InteractionResult interactLivingEntity(ItemStack itemStack, Player player, LivingEntity livingEntity, InteractionHand interactionHand) {
		if (livingEntity instanceof AbstractVillager abstractVillager &&
				livingEntity instanceof VillagerDataHolder villager &&
				abstractVillager.isAlive()) {
			VillagerProfession armorer = VillagerProfession.ARMORER;
			VillagerProfession toolSmith = VillagerProfession.TOOLSMITH;
			VillagerProfession weaponSmith = VillagerProfession.WEAPONSMITH;
			VillagerProfession forger = JerotesVillageVillagerProfessions.VILLAGER_FORGER.get();
			boolean profession = villager.getVillagerData().getProfession() == armorer ||
					villager.getVillagerData().getProfession() == toolSmith ||
					villager.getVillagerData().getProfession() == weaponSmith || villager.getVillagerData().getProfession() == forger;

			if (!abstractVillager.isBaby() && profession) {
				if (!player.getCooldowns().isOnCooldown(itemStack.getItem())) {
					player.swing(InteractionHand.MAIN_HAND);
					if (!abstractVillager.level().isClientSide()) {
						abstractVillager.playSound(SoundEvents.SMITHING_TABLE_USE, 1.0f, abstractVillager.getVoicePitch());
					}
					boolean ingot = false;
					float baseDropProfession = 0.5f;
					float baseDropLevel = 0f + villager.getVillagerData().getLevel() * 0.1f;
					if (villager.getVillagerData().getProfession() == forger) {
						baseDropProfession = 0.75f;
					}
					if (abstractVillager instanceof Villager villagers) {
						villagers.setVillagerXp(villagers.getVillagerXp() + 3);
					}
					if (player.getRandom().nextFloat() < baseDropProfession + baseDropLevel) {
						ingot = true;
					}
					ItemStack itemDrop;
					if (!ingot) {
						itemDrop = new ItemStack(JerotesVillageItems.SCRAP_VILLAGER_METAL.get(), 1);
					} else {
						itemDrop = new ItemStack(JerotesVillageItems.VILLAGER_METAL_INGOT.get(), 1);
						if (!abstractVillager.level().isClientSide()) {
							abstractVillager.playSound(SoundEvents.VILLAGER_TRADE, 1.0f, abstractVillager.getVoicePitch());
						}
						if (abstractVillager instanceof Villager villager1) {
							if (abstractVillager.level() instanceof ServerLevel serverLevel) {
								serverLevel.onReputationEvent(ReputationEventType.TRADE, player, villager1);
							}
						}
						for (int i = 0; i < 5; ++i) {
							double d = abstractVillager.getRandom().nextGaussian() * 0.02;
							double d2 = abstractVillager.getRandom().nextGaussian() * 0.02;
							double d3 = abstractVillager.getRandom().nextGaussian() * 0.02;
							abstractVillager.level().addParticle(ParticleTypes.HAPPY_VILLAGER, abstractVillager.getRandomX(1.0), abstractVillager.getRandomY() + 1.0, abstractVillager.getRandomZ(1.0), d, d2, d3);
						}
					}
					if (!player.getAbilities().instabuild) {
						itemStack.shrink(1);
					}
					ItemEntity itemEntity = abstractVillager.spawnAtLocation(itemDrop, 1);
					if (itemEntity != null) {
						itemEntity.setDeltaMovement(itemEntity.getDeltaMovement().add((abstractVillager.getRandom().nextFloat() - abstractVillager.getRandom().nextFloat()) * 0.1f, abstractVillager.getRandom().nextFloat() * 0.2f, (abstractVillager.getRandom().nextFloat() - abstractVillager.getRandom().nextFloat()) * 0.1f));
					}
					player.getCooldowns().addCooldown(itemStack.getItem(), 20);
				}
				return InteractionResult.sidedSuccess(player.level().isClientSide());
			}

		}
		return InteractionResult.PASS;
	}

	@Override
	public void appendHoverText(ItemStack itemStack, Level level, List<Component> list, TooltipFlag tooltipFlag) {
		super.appendHoverText(itemStack, level, list, tooltipFlag);
		list.add(this.getDisplayName_0().withStyle(ChatFormatting.GRAY));
		list.add(this.getDisplayName_1().withStyle(ChatFormatting.GRAY));
		list.add(this.getDisplayName_2().withStyle(ChatFormatting.GRAY));
	}

	public MutableComponent getDisplayName_0() {
		return Component.translatable(this.getDescriptionId() + ".desc_0");
	}
	public MutableComponent getDisplayName_1() {
		return Component.translatable(this.getDescriptionId() + ".desc_1");
	}
	public MutableComponent getDisplayName_2() {
		return Component.translatable(this.getDescriptionId() + ".desc_2");
	}
}
