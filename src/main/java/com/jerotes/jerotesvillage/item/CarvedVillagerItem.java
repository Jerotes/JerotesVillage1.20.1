package com.jerotes.jerotesvillage.item;

import com.jerotes.jerotes.init.JerotesSoundEvents;
import com.jerotes.jerotes.util.Main;
import com.jerotes.jerotesvillage.entity.Humanoid.CarvedVillagerEntity;
import com.jerotes.jerotesvillage.entity.Monster.IllagerFaction.DefectorEntity;
import com.jerotes.jerotesvillage.event.RelationshipEvent;
import com.jerotes.jerotesvillage.init.JerotesVillageEntityType;
import com.jerotes.jerotesvillage.init.JerotesVillageItems;
import net.minecraft.ChatFormatting;
import net.minecraft.core.BlockPos;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.MutableComponent;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.stats.Stats;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.SimpleContainer;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.MobSpawnType;
import net.minecraft.world.entity.npc.Villager;
import net.minecraft.world.entity.npc.VillagerProfession;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.*;
import net.minecraft.world.level.Level;

import javax.annotation.Nullable;
import java.util.List;

public class CarvedVillagerItem extends Item {
	public CarvedVillagerItem() {
		super(new Properties().stacksTo(1).rarity(Rarity.UNCOMMON));
	}

	@Override
	public UseAnim getUseAnimation(ItemStack itemStack) {
		return UseAnim.BRUSH;
	}

	@Override
	public int getUseDuration(ItemStack itemStack) {
		return 72000;
	}

	@Override
	public InteractionResultHolder<ItemStack> use(Level level, Player player, InteractionHand interactionHand) {
		ItemStack itemStack = player.getItemInHand(interactionHand);
		player.startUsingItem(interactionHand);
		return InteractionResultHolder.consume(itemStack);
	}

	public ItemStack createSpawnWeaponTo(float weaponRandom) {
		if (weaponRandom > 0.6) {
			return new ItemStack(JerotesVillageItems.VILLAGER_METAL_JAVELIN.get());
		}
		if (weaponRandom > 0.5) {
			return new ItemStack(JerotesVillageItems.VILLAGER_METAL_AXE.get());
		}
		if (weaponRandom > 0.1) {
			return new ItemStack(JerotesVillageItems.VILLAGER_METAL_SWORD.get());
		}
		return new ItemStack(JerotesVillageItems.VILLAGER_METAL_BOW.get());
	}

	@Override
	public void releaseUsing(ItemStack itemStack, Level level, LivingEntity livingEntity, int n) {
		if (!(livingEntity instanceof Player player)) {
			return;
		}
		int n2 = this.getUseDuration(itemStack) - n;
		if (n2 < 20) {
			return;
		}
		if (level instanceof ServerLevel serverLevel) {
			BlockPos blockPos = Main.findSpawnPositionNearFillOnBlock(player, 8);
			CarvedVillagerEntity villager;
			if (blockPos != null) {
				villager = JerotesVillageEntityType.CARVED_VILLAGER.get().spawn(serverLevel, blockPos, MobSpawnType.EVENT);
				if (villager != null) {
					villager.setItemSlot(EquipmentSlot.HEAD, new ItemStack(JerotesVillageItems.VILLAGER_METAL_HELMET.get()));
					villager.setItemSlot(EquipmentSlot.CHEST, new ItemStack(JerotesVillageItems.VILLAGER_METAL_CHESTPLATE.get()));
					villager.setItemSlot(EquipmentSlot.LEGS, new ItemStack(JerotesVillageItems.VILLAGER_METAL_LEGGINGS.get()));
					villager.setItemSlot(EquipmentSlot.FEET, new ItemStack(JerotesVillageItems.VILLAGER_METAL_BOOTS.get()));
					villager.setDeltaMovement(0, 0, 0);
					player.getCooldowns().addCooldown(this, 120);
					for (int i = 0; i < 60; ++i) {
						serverLevel.sendParticles(ParticleTypes.HAPPY_VILLAGER, villager.getRandomX(1.5), villager.getRandomY(), villager.getRandomZ(1.5), 0, 0.0, 0.0, 0.0, 0.0);
					}
					if (!villager.isSilent()) {
						serverLevel.playSound(null, villager.getX(), villager.getY(), villager.getZ(), JerotesSoundEvents.TELEPORT, villager.getSoundSource(), 5.0f, 1.0F);
					}
					if (!player.getAbilities().instabuild) {
						itemStack.shrink(1);
					}
					RelationshipEvent.AddCopperCarvedCompanyRelationship(player, 5);
				}
			}
		}
		player.awardStat(Stats.ITEM_USED.get(this));
	}


	public InteractionResult interactLivingEntity(ItemStack itemStack, Player player, LivingEntity livingEntity, InteractionHand interactionHand) {
		if (itemStack.getItem() instanceof CarvedVillagerItem carvedVillagerItem) {
			if (!player.getCooldowns().isOnCooldown(carvedVillagerItem)) {
				//转化村民
				if (livingEntity.getType() == EntityType.VILLAGER && livingEntity instanceof Villager villager && villager.isAlive() && RelationshipEvent.MoreCopperCarvedCompanyRelationship(player, 700)) {
					boolean profession = villager.getVillagerData().getProfession() == VillagerProfession.NONE;
					if (!villager.isBaby() && profession) {
						if (player.level() instanceof ServerLevel serverLevel) {
							player.swing(InteractionHand.MAIN_HAND);
							SimpleContainer simpleContainer = villager.getInventory();
							CarvedVillagerEntity carvedVillager = villager.convertTo(JerotesVillageEntityType.CARVED_VILLAGER.get(), true);
							if (carvedVillager != null) {
								carvedVillager.setPiglinRace(false);
								carvedVillager.setIllagerRace(false);
								carvedVillager.setEndermanRace(false);
								carvedVillager.mobInventory().clearContent();
								if (carvedVillager.getItemBySlot(EquipmentSlot.HEAD).isEmpty()) {
									carvedVillager.setItemSlot(EquipmentSlot.HEAD, new ItemStack(JerotesVillageItems.VILLAGER_METAL_HELMET.get()));
								} else {
									carvedVillager.mobInventory().addItem(carvedVillager.getItemBySlot(EquipmentSlot.HEAD));
									carvedVillager.setItemSlot(EquipmentSlot.HEAD, new ItemStack(JerotesVillageItems.VILLAGER_METAL_HELMET.get()));
								}
								if (carvedVillager.getItemBySlot(EquipmentSlot.CHEST).isEmpty()) {
									carvedVillager.setItemSlot(EquipmentSlot.CHEST, new ItemStack(JerotesVillageItems.VILLAGER_METAL_CHESTPLATE.get()));
								} else {
									carvedVillager.mobInventory().addItem(carvedVillager.getItemBySlot(EquipmentSlot.CHEST));
									carvedVillager.setItemSlot(EquipmentSlot.CHEST, new ItemStack(JerotesVillageItems.VILLAGER_METAL_CHESTPLATE.get()));
								}
								if (carvedVillager.getItemBySlot(EquipmentSlot.LEGS).isEmpty()) {
									carvedVillager.setItemSlot(EquipmentSlot.LEGS, new ItemStack(JerotesVillageItems.VILLAGER_METAL_LEGGINGS.get()));
								} else {
									carvedVillager.mobInventory().addItem(carvedVillager.getItemBySlot(EquipmentSlot.LEGS));
									carvedVillager.setItemSlot(EquipmentSlot.LEGS, new ItemStack(JerotesVillageItems.VILLAGER_METAL_LEGGINGS.get()));
								}
								if (carvedVillager.getItemBySlot(EquipmentSlot.FEET).isEmpty()) {
									carvedVillager.setItemSlot(EquipmentSlot.FEET, new ItemStack(JerotesVillageItems.VILLAGER_METAL_BOOTS.get()));
								} else {
									carvedVillager.mobInventory().addItem(carvedVillager.getItemBySlot(EquipmentSlot.FEET));
									carvedVillager.setItemSlot(EquipmentSlot.FEET, new ItemStack(JerotesVillageItems.VILLAGER_METAL_BOOTS.get()));
								}
								if (carvedVillager.getItemBySlot(EquipmentSlot.MAINHAND).isEmpty()) {
									float weaponRandom = carvedVillager.getRandom().nextFloat();
									carvedVillager.setItemSlot(EquipmentSlot.MAINHAND, carvedVillagerItem.createSpawnWeaponTo(weaponRandom));
								} else {
									carvedVillager.mobInventory().addItem(carvedVillager.getItemBySlot(EquipmentSlot.MAINHAND));
									float weaponRandom = carvedVillager.getRandom().nextFloat();
									carvedVillager.setItemSlot(EquipmentSlot.MAINHAND, carvedVillagerItem.createSpawnWeaponTo(weaponRandom));
								}
								if (!carvedVillager.getItemBySlot(EquipmentSlot.OFFHAND).isEmpty()) {
									carvedVillager.mobInventory().addItem(carvedVillager.getItemBySlot(EquipmentSlot.OFFHAND));
								}
								for (int i = 0; i < simpleContainer.getMaxStackSize(); ++i) {
									carvedVillager.mobInventory().addItem(simpleContainer.getItem(i));
								}
								carvedVillager.setDeltaMovement(0, 0, 0);
								for (int i = 0; i < 60; ++i) {
									serverLevel.sendParticles(ParticleTypes.HAPPY_VILLAGER, carvedVillager.getRandomX(1.5), carvedVillager.getRandomY(), carvedVillager.getRandomZ(1.5), 0, 0.0, 0.0, 0.0, 0.0);
								}
								if (!carvedVillager.isSilent()) {
									serverLevel.playSound(null, carvedVillager.getX(), carvedVillager.getY(), carvedVillager.getZ(), JerotesSoundEvents.TELEPORT, carvedVillager.getSoundSource(), 5.0f, 1.0F);
								}
								net.minecraftforge.event.ForgeEventFactory.onLivingConvert(villager, carvedVillager);
								if (!player.getAbilities().instabuild) {
									itemStack.shrink(1);
								}
								RelationshipEvent.AddCopperCarvedCompanyRelationship(player, 20);
								carvedVillager.trustsBy(player);
							}
						}
						player.getCooldowns().addCooldown(carvedVillagerItem, 20);
						return InteractionResult.sidedSuccess(player.level().isClientSide());
					}
				}
				//转化背叛者
				if (livingEntity.getType() == JerotesVillageEntityType.DEFECTOR.get() && livingEntity instanceof DefectorEntity defector && defector.isAlive() && RelationshipEvent.MoreCopperCarvedCompanyRelationship(player, 0)) {
					if (player.level() instanceof ServerLevel serverLevel) {
						player.swing(InteractionHand.MAIN_HAND);
						SimpleContainer simpleContainer = defector.getInventory();
						CarvedVillagerEntity carvedVillager = defector.convertTo(JerotesVillageEntityType.CARVED_VILLAGER.get(), true);
						if (carvedVillager != null) {
							carvedVillager.setPiglinRace(false);
							carvedVillager.setIllagerRace(true);
							carvedVillager.setEndermanRace(false);
							carvedVillager.mobInventory().clearContent();
							if (carvedVillager.getItemBySlot(EquipmentSlot.HEAD).isEmpty()) {
								carvedVillager.setItemSlot(EquipmentSlot.HEAD, new ItemStack(JerotesVillageItems.VILLAGER_METAL_HELMET.get()));
							} else {
								carvedVillager.mobInventory().addItem(carvedVillager.getItemBySlot(EquipmentSlot.HEAD));
								carvedVillager.setItemSlot(EquipmentSlot.HEAD, new ItemStack(JerotesVillageItems.VILLAGER_METAL_HELMET.get()));
							}
							if (carvedVillager.getItemBySlot(EquipmentSlot.CHEST).isEmpty()) {
								carvedVillager.setItemSlot(EquipmentSlot.CHEST, new ItemStack(JerotesVillageItems.VILLAGER_METAL_CHESTPLATE.get()));
							} else {
								carvedVillager.mobInventory().addItem(carvedVillager.getItemBySlot(EquipmentSlot.CHEST));
								carvedVillager.setItemSlot(EquipmentSlot.CHEST, new ItemStack(JerotesVillageItems.VILLAGER_METAL_CHESTPLATE.get()));
							}
							if (carvedVillager.getItemBySlot(EquipmentSlot.LEGS).isEmpty()) {
								carvedVillager.setItemSlot(EquipmentSlot.LEGS, new ItemStack(JerotesVillageItems.VILLAGER_METAL_LEGGINGS.get()));
							} else {
								carvedVillager.mobInventory().addItem(carvedVillager.getItemBySlot(EquipmentSlot.LEGS));
								carvedVillager.setItemSlot(EquipmentSlot.LEGS, new ItemStack(JerotesVillageItems.VILLAGER_METAL_LEGGINGS.get()));
							}
							if (carvedVillager.getItemBySlot(EquipmentSlot.FEET).isEmpty()) {
								carvedVillager.setItemSlot(EquipmentSlot.FEET, new ItemStack(JerotesVillageItems.VILLAGER_METAL_BOOTS.get()));
							} else {
								carvedVillager.mobInventory().addItem(carvedVillager.getItemBySlot(EquipmentSlot.FEET));
								carvedVillager.setItemSlot(EquipmentSlot.FEET, new ItemStack(JerotesVillageItems.VILLAGER_METAL_BOOTS.get()));
							}
							if (carvedVillager.getItemBySlot(EquipmentSlot.MAINHAND).isEmpty()) {
								float weaponRandom = carvedVillager.getRandom().nextFloat();
								carvedVillager.setItemSlot(EquipmentSlot.MAINHAND, carvedVillagerItem.createSpawnWeaponTo(weaponRandom));
							} else {
								carvedVillager.mobInventory().addItem(carvedVillager.getItemBySlot(EquipmentSlot.MAINHAND));
								float weaponRandom = carvedVillager.getRandom().nextFloat();
								carvedVillager.setItemSlot(EquipmentSlot.MAINHAND, carvedVillagerItem.createSpawnWeaponTo(weaponRandom));
							}
							if (!carvedVillager.getItemBySlot(EquipmentSlot.OFFHAND).isEmpty()) {
								carvedVillager.mobInventory().addItem(carvedVillager.getItemBySlot(EquipmentSlot.OFFHAND));
							}
							for (int i = 0; i < simpleContainer.getMaxStackSize(); ++i) {
								carvedVillager.mobInventory().addItem(simpleContainer.getItem(i));
							}
							carvedVillager.setDeltaMovement(0, 0, 0);
							player.getCooldowns().addCooldown(carvedVillagerItem, 60);
							for (int i = 0; i < 60; ++i) {
								serverLevel.sendParticles(ParticleTypes.HAPPY_VILLAGER, carvedVillager.getRandomX(1.5), carvedVillager.getRandomY(), carvedVillager.getRandomZ(1.5), 0, 0.0, 0.0, 0.0, 0.0);
							}
							if (!carvedVillager.isSilent()) {
								serverLevel.playSound(null, carvedVillager.getX(), carvedVillager.getY(), carvedVillager.getZ(), JerotesSoundEvents.TELEPORT, carvedVillager.getSoundSource(), 5.0f, 1.0F);
							}
							net.minecraftforge.event.ForgeEventFactory.onLivingConvert(defector, carvedVillager);
							if (!player.getAbilities().instabuild) {
								itemStack.shrink(1);
							}
							RelationshipEvent.AddCopperCarvedCompanyRelationship(player, 40);
							carvedVillager.trustsBy(player);
						}
					}
					player.getCooldowns().addCooldown(carvedVillagerItem, 20);
					return InteractionResult.sidedSuccess(player.level().isClientSide());
				}

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
