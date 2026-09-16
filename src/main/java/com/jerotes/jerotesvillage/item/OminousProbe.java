package com.jerotes.jerotesvillage.item;

import com.jerotes.jerotes.init.JerotesSoundEvents;
import com.jerotes.jerotes.util.EntityFactionFind;
import com.jerotes.jerotes.util.Main;
import com.jerotes.jerotesvillage.entity.Monster.IllagerFaction.MeleeIllagerEntity;
import com.jerotes.jerotesvillage.entity.Monster.IllagerFaction.SpellIllagerEntity;
import com.jerotes.jerotesvillage.init.JerotesVillageEntityType;
import com.jerotes.jerotesvillage.init.JerotesVillageItems;
import com.jerotes.jerotesvillage.init.JerotesVillageMobEffects;
import com.jerotes.jerotesvillage.init.JerotesVillageParticleTypes;
import net.minecraft.ChatFormatting;
import net.minecraft.core.BlockPos;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.network.chat.Component;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.*;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.*;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.gameevent.GameEvent;

import javax.annotation.Nullable;
import java.util.List;
import java.util.Objects;

public class OminousProbe extends Item implements Vanishable {
	public OminousProbe() {
		super(new Properties().stacksTo(1).rarity(Rarity.EPIC).durability(200));
	}

	@Override
	public boolean isValidRepairItem(ItemStack itemStack, ItemStack itemStack2) {
		return itemStack2.is(JerotesVillageItems.ILLAGER_SIGNAL_LIGHT.get()) || super.isValidRepairItem(itemStack, itemStack2);
	}


	@Override
	public InteractionResultHolder<ItemStack> use(Level level, Player player, InteractionHand interactionHand) {
		ItemStack itemStack = player.getItemInHand(interactionHand);
		if (itemStack.getDamageValue() >= itemStack.getMaxDamage() - 100) {
			return InteractionResultHolder.fail(itemStack);
		}
		if (Main.getTargetedEntity(player, 6) != null &&
				Main.getTargetedEntity(player, 6) instanceof LivingEntity target) {
			if (!EntityFactionFind.isRaider(target)) {
				target.hurt(player.damageSources().sting(player), 1);
				if (level instanceof ServerLevel serverLevel) {
					for (int i = 0; i < 12; ++i) {
						serverLevel.sendParticles(JerotesVillageParticleTypes.ABACK.get(), target.getRandomX(0.5), target.getRandomY(), target.getRandomZ(0.5), 0, 0.0, 0.02, 0.0, 1);
					}
				}
				summonEntity(player, target, JerotesVillageEntityType.TELEPORTER.get(), 1, 6);
				summonEntity(player, target, JerotesVillageEntityType.EXECUTIONER.get(), 1, 6);
				summonEntity(player, target, JerotesVillageEntityType.BANNER_BEARER.get(), 1, 6);
				summonEntity(player, target, JerotesVillageEntityType.TRUMPETER.get(), 1, 6);
				summonEntity(player, target, JerotesVillageEntityType.EXPLORER.get(), Main.randomReach(player.getRandom(), 1, 3), 6);
				summonEntity(player, target, JerotesVillageEntityType.JAVELIN_THROWER.get(), Main.randomReach(player.getRandom(), 1, 3), 6);

				player.level().playSound(null, player.getX(), player.getY(), player.getZ(), JerotesSoundEvents.TELEPORT, player.getSoundSource(), 1.0f, 1.0f);
				if (!player.getAbilities().instabuild) {
					player.getCooldowns().addCooldown(this, 3600);
				}
				if (!player.getAbilities().instabuild) {
					itemStack.hurtAndBreak(100, player, e -> e.broadcastBreakEvent(player.getUsedItemHand()));
				}
			}
			else if (level instanceof ServerLevel) {
				player.sendSystemMessage(Component.literal("???").withStyle(ChatFormatting.RED));
			}
		}
		return InteractionResultHolder.consume(itemStack);
	}


	public static void summonEntity(LivingEntity caster, LivingEntity target, EntityType entityType, int count, int summonDistance) {
		if (caster.level() instanceof ServerLevel serverLevel) {
			if (entityType == null) {
				return;
			}
			for (int i = 0; i < count; ++i) {
				BlockPos summonPos = Main.findSpawnPositionNearFillOnBlock(target, summonDistance);
				if (summonPos.getY() < caster.getY()) {
					summonPos = caster.getOnPos().above();
				}
				Entity entity = entityType.spawn(serverLevel, BlockPos.containing(summonPos.getX(), summonPos.getY(), summonPos.getZ()), MobSpawnType.MOB_SUMMONED);
				if (entity != null) {
					for (int n2 = 0; n2 < 32; ++n2) {
						serverLevel.sendParticles(ParticleTypes.PORTAL,
								entity.getRandomX(0.5), entity.getRandomY(), entity.getRandomZ(0.5),
								0, 0.0, 0.0, 0.0,
								0);
					}
					if (entity instanceof Mob mob) {
						mob.setTarget(target);
						//去掉赏金
						if (Main.getJerotesPersistentData(caster).getDouble("jerotesvillage_meror_wanted_order") > 0) {
							Main.getJerotesPersistentData(mob).putDouble("jerotesvillage_meror_wanted_order", Main.getJerotesPersistentData(caster).getDouble("jerotesvillage_meror_wanted_order"));
							Main.getJerotesPersistentData(caster).putDouble("jerotesvillage_meror_wanted_order", 0);
						}
						if (caster.hasEffect(JerotesVillageMobEffects.MEROR_WANTED_ORDER.get())) {
							caster.removeEffect(JerotesVillageMobEffects.MEROR_WANTED_ORDER.get());
						}
						if (caster.hasEffect(MobEffects.BAD_OMEN)) {
							target.addEffect(Objects.requireNonNull(caster.getEffect(MobEffects.BAD_OMEN)));
							caster.removeEffect(MobEffects.BAD_OMEN);
						}
					}
					if (entity instanceof MeleeIllagerEntity mob) {
						mob.teleportCooldown = 480;
					}
					if (entity instanceof SpellIllagerEntity mob) {
						mob.teleportCooldown = 480;
					}
				}
			}
			serverLevel.gameEvent(GameEvent.ENTITY_PLACE, new BlockPos((int) caster.getX(), (int) caster.getY(), (int) caster.getZ()), GameEvent.Context.of(caster));
		}
	}

	@Override
	public UseAnim getUseAnimation(ItemStack itemStack) {
		return UseAnim.BLOCK;
	}

	@Override
	public int getUseDuration(ItemStack itemStack) {
		return 72000;
	}

	@Override
	public void appendHoverText(ItemStack itemStack, @Nullable Level level, List<Component> list, TooltipFlag tooltipFlag) {
		super.appendHoverText(itemStack, level, list, tooltipFlag);
		list.add(Component.translatable(this.getDescriptionId() + ".desc_0").withStyle(ChatFormatting.GRAY));
		list.add(Component.translatable(this.getDescriptionId() + ".desc_1").withStyle(ChatFormatting.GRAY));
	}
}
