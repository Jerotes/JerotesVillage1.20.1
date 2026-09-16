package com.jerotes.jerotesvillage.item;

import com.jerotes.jerotesvillage.entity.Neutral.CarvedIronGolemEntity;
import com.jerotes.jerotesvillage.event.RelationshipEvent;
import com.jerotes.jerotesvillage.init.JerotesVillageEntityType;
import net.minecraft.ChatFormatting;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.MutableComponent;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.animal.IronGolem;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Rarity;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.level.Level;

import javax.annotation.Nullable;
import java.util.List;

public class CarvedIronGolemCore extends Item {
	public CarvedIronGolemCore() {
		super(new Properties().stacksTo(1).rarity(Rarity.UNCOMMON));
	}


	public InteractionResult interactLivingEntity(ItemStack itemStack, Player player, LivingEntity livingEntity, InteractionHand interactionHand) {
		if (!player.getCooldowns().isOnCooldown(itemStack.getItem())) {
			if (livingEntity.getType() == EntityType.IRON_GOLEM && livingEntity instanceof IronGolem ironGolem && !(livingEntity instanceof CarvedIronGolemEntity) && livingEntity.isAlive()) {
				player.swing(InteractionHand.MAIN_HAND);
				if (!player.getAbilities().instabuild) {
					itemStack.shrink(1);
				}
				boolean playerCreated = ironGolem.isPlayerCreated();
				CarvedIronGolemEntity carvedIronGolem = ironGolem.convertTo(JerotesVillageEntityType.CARVED_IRON_GOLEM.get(), true);
				if (carvedIronGolem != null) {
					float f1 = 1.0f + (carvedIronGolem.getRandom().nextFloat() - carvedIronGolem.getRandom().nextFloat()) * 0.2f;
					player.getCooldowns().addCooldown(itemStack.getItem(), 120);
					if (player.level() instanceof ServerLevel serverLevel) {
						for (int i = 0; i < 60; ++i) {
							serverLevel.sendParticles(ParticleTypes.HAPPY_VILLAGER, carvedIronGolem.getRandomX(1.5), carvedIronGolem.getRandomY(), carvedIronGolem.getRandomZ(1.5), 0, 0.0, 0.0, 0.0, 0.0);
						}
					}
					if (!carvedIronGolem.isSilent()) {
						carvedIronGolem.playSound(SoundEvents.IRON_GOLEM_REPAIR, 1.0f, f1);
					}
					net.minecraftforge.event.ForgeEventFactory.onLivingConvert(ironGolem, carvedIronGolem);
					carvedIronGolem.setPlayerCreated(playerCreated);
					RelationshipEvent.AddCopperCarvedCompanyRelationship(player, 5);
				}
				return InteractionResult.sidedSuccess(player.level().isClientSide());
			}
		}
		return InteractionResult.PASS;
	}

	@Override
	public void appendHoverText(ItemStack itemStack, @Nullable Level level, List<Component> list, TooltipFlag tooltipFlag) {
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
