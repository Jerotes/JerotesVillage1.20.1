package com.jerotes.jerotesvillage.event;

import com.jerotes.jerotes.init.JerotesEnchantments;
import com.jerotes.jerotes.util.Main;
import com.jerotes.jerotesvillage.entity.Humanoid.CarvedVillagerEntity;
import com.jerotes.jerotesvillage.entity.Neutral.MerorLocatorEntity;
import com.jerotes.jerotesvillage.init.JerotesVillageEntityType;
import com.jerotes.jerotesvillage.init.JerotesVillageItems;
import com.jerotes.jerotesvillage.init.JerotesVillageMobEffects;
import com.jerotes.jerotesvillage.network.JerotesVillageLevelData;
import com.jerotes.jerotesvillage.util.CopperCarvedCompany;
import com.mojang.brigadier.arguments.DoubleArgumentType;
import com.mojang.brigadier.arguments.IntegerArgumentType;
import net.minecraft.ChatFormatting;
import net.minecraft.commands.Commands;
import net.minecraft.commands.arguments.EntityArgument;
import net.minecraft.commands.arguments.coordinates.BlockPosArgument;
import net.minecraft.core.BlockPos;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.ListTag;
import net.minecraft.nbt.StringTag;
import net.minecraft.nbt.Tag;
import net.minecraft.network.chat.Component;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.MobSpawnType;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.alchemy.PotionUtils;
import net.minecraft.world.item.alchemy.Potions;
import net.minecraft.world.item.enchantment.Enchantments;
import net.minecraft.world.level.Level;
import net.minecraftforge.event.RegisterCommandsEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

import java.util.Collection;
import java.util.List;
import java.util.Objects;

@Mod.EventBusSubscriber
public class CommandEvent {

	@SubscribeEvent
	public static void registerCommand(RegisterCommandsEvent event) {
		event.getDispatcher().register(Commands.literal("jerotes").requires(s -> s.hasPermission(4))
				//阵营关系
				.then(Commands.literal("relationship").then(Commands.argument("player", EntityArgument.player())
								.then(Commands.literal("add")
										.then(Commands.literal("carved")
												.then(Commands.argument("number", DoubleArgumentType.doubleArg(-2000)).executes(arguments -> {
																	Entity entity = arguments.getSource().getEntity();
																	Player playerTo = EntityArgument.getPlayer(arguments, "player");
																	if (entity instanceof Player player) {
																		double n = DoubleArgumentType.getDouble(arguments, "number");
																		RelationshipEvent.AddCopperCarvedCompanyRelationship(playerTo, (int) n);
																		player.sendSystemMessage(Component.literal(String.valueOf((RelationshipEvent.GetCopperCarvedCompanyRelationship(playerTo)))).withStyle(ChatFormatting.GOLD));
																	}
																	return 0;
																}
														)
												)
										)
								)
								.then(Commands.literal("set")
										.then(Commands.literal("carved")
												.then(Commands.argument("number", DoubleArgumentType.doubleArg(-1000)).executes(arguments -> {
																	Entity entity = arguments.getSource().getEntity();
																	Player playerTo = EntityArgument.getPlayer(arguments, "player");
																	if (entity instanceof Player player) {
																		double n = DoubleArgumentType.getDouble(arguments, "number");
																		RelationshipEvent.SetCopperCarvedCompanyRelationship(playerTo, (int) n);
																		player.sendSystemMessage(Component.literal(String.valueOf((RelationshipEvent.GetCopperCarvedCompanyRelationship(playerTo)))).withStyle(ChatFormatting.GOLD));
																	}
																	return 0;
																}
														)
												)
										)
								)
								.then(Commands.literal("get")
										.then(Commands.literal("carved").executes(arguments -> {
															Entity entity = arguments.getSource().getEntity();
															Player playerTo = EntityArgument.getPlayer(arguments, "player");
															if (entity instanceof Player player) {
																player.sendSystemMessage(Component.literal(String.valueOf((RelationshipEvent.GetCopperCarvedCompanyRelationship(playerTo)))).withStyle(ChatFormatting.GOLD));
															}
															return 0;
														}
												)
										)
								)
						)
				)
				//事件
				.then(Commands.literal("event")
						.then(Commands.literal("copper_carved_company_spawn")
								.then(Commands.argument("pos", BlockPosArgument.blockPos())
										.then(Commands.argument("number", IntegerArgumentType.integer(0))
												.executes(arguments ->
														{
															Level level = arguments.getSource().getLevel();
															int n = IntegerArgumentType.getInteger(arguments, "number");
															BlockPos blockPos = BlockPosArgument.getBlockPos(arguments, "pos");
															CopperCarvedCompany.CopperCarvedCompanySpawn(level, blockPos, n);
															return 0;
														}
												)
										)
								)
						)
						.then(Commands.literal("meror_bounty_hunter")
								.then(Commands.argument("pos", BlockPosArgument.blockPos())
										.executes(arguments ->
												{
													Level level = arguments.getSource().getLevel();
													if (level instanceof ServerLevel serverLevel) {
														BlockPos blockPos = BlockPosArgument.getBlockPos(arguments, "pos");
														MerorLocatorEntity locator = JerotesVillageEntityType.MEROR_LOCATOR.get().spawn(serverLevel,
																new BlockPos(blockPos.getX(), blockPos.getY(), blockPos.getZ()), MobSpawnType.EVENT);
													}
													return 0;
												}
										)
								)
						)
						.then(Commands.literal("wicked_ridicule_night")
								.then(Commands.literal("true").executes(arguments -> {
													Entity entity = arguments.getSource().getEntity();
													Level level = arguments.getSource().getLevel();
													float times = level.getDayTime() % 24000;
													if (WorldEvent.WickedRidiculeNightAbout(level)) {
														if (!(level.isDay() && times > 1000 && times < 6000)) {
															WorldEvent.WickedRidiculeNightTrueOrFalse(level, true);
															if (!level.isClientSide() && JerotesVillageLevelData.get(level) != null && JerotesVillageLevelData.get(level).WickedRidiculeNight) {
                                                                    List<? extends Player> list = level.players();
                                                                    list.removeIf(player -> player.level() != level);
                                                                    for (Player player : list) {
                                                                        player.addEffect(new MobEffectInstance(JerotesVillageMobEffects.WICKED_RIDICULE_NIGHT_START.get(), 20, 1));
                                                                        player.sendSystemMessage(Component.translatable("message.jerotesvillage.wicked_ridicule_night_start").withStyle(ChatFormatting.DARK_RED));
                                                                    }
                                                                }
                                                            }
													}
													else {
														if (entity instanceof Player player) {
															player.sendSystemMessage(Component.translatable("message.jerotesvillage.zsiein_affinity_not_second_round_world").withStyle(ChatFormatting.WHITE));
														}
													}
													return 0;
												}
										)
								)
								.then(Commands.literal("false").executes(arguments -> {
													Entity entity = arguments.getSource().getEntity();
													Level level = arguments.getSource().getLevel();
													if (JerotesVillageLevelData.get(level) != null && WorldEvent.WickedRidiculeNightAbout(level)) {
														WorldEvent.WickedRidiculeNightTrueOrFalse(level, false);
														if (!level.isClientSide() && !JerotesVillageLevelData.get(level).WickedRidiculeNight) {
															List<? extends Player> list = level.players();
															list.removeIf(player -> player.level() != level);
															for (Player player : list) {
																player.addEffect(new MobEffectInstance(JerotesVillageMobEffects.WICKED_RIDICULE_NIGHT_START.get(), 20, 1));
																player.sendSystemMessage(Component.translatable("message.jerotesvillage.wicked_ridicule_night_stop").withStyle(ChatFormatting.DARK_RED));
															}
														}
													}
													else {
														if (entity instanceof Player player) {
															player.sendSystemMessage(Component.translatable("message.jerotesvillage.zsiein_affinity_not_second_round_world").withStyle(ChatFormatting.WHITE));
														}
													}
													return 0;
												}
										)
								)
						)
				)
				//数值
				.then(Commands.literal("number")
						.then(Commands.literal("add")
								.then(Commands.literal("zsiein_affinity")
										.then(Commands.argument("number", DoubleArgumentType.doubleArg(0)).executes(arguments -> {
															Entity entity = arguments.getSource().getEntity();
															Level level = arguments.getSource().getLevel();
															if (JerotesVillageLevelData.get(level) != null && WorldEvent.ZsieinLatentInfectedAbove(level)) {
																double d = DoubleArgumentType.getDouble(arguments, "number");
																WorldEvent.ZsieinAffinityAdd(level, d);
																if (entity instanceof Player player) {
																	player.sendSystemMessage(Component.translatable("message.jerotesvillage.zsiein_affinity", JerotesVillageLevelData.get(level).ZsieinAffinity).withStyle(ChatFormatting.WHITE));
																}
															}
															else {
																if (entity instanceof Player player) {
																	player.sendSystemMessage(Component.translatable("message.jerotesvillage.zsiein_affinity_not_second_round_world").withStyle(ChatFormatting.WHITE));
																}
															}
															return 0;
														}
												)
										)
								)
						)
						.then(Commands.literal("set")
								.then(Commands.literal("zsiein_affinity")
										.then(Commands.argument("number", DoubleArgumentType.doubleArg(0)).executes(arguments -> {
															Entity entity = arguments.getSource().getEntity();
															Level level = arguments.getSource().getLevel();
															if (JerotesVillageLevelData.get(level) != null && WorldEvent.ZsieinLatentInfectedAbove(level)) {
																double d = DoubleArgumentType.getDouble(arguments, "number");
																WorldEvent.ZsieinAffinitySet(level, d);
																if (entity instanceof Player player) {
																	player.sendSystemMessage(Component.translatable("message.jerotesvillage.zsiein_affinity", JerotesVillageLevelData.get(level).ZsieinAffinity).withStyle(ChatFormatting.WHITE));
																}
															}
															else {
																if (entity instanceof Player player) {
																	player.sendSystemMessage(Component.translatable("message.jerotesvillage.zsiein_affinity_not_second_round_world").withStyle(ChatFormatting.WHITE));
																}
															}
															return 0;
														}
												)
										)
								)
						)
						.then(Commands.literal("get")
								.then(Commands.literal("zsiein_affinity").executes(arguments -> {
													Entity entity = arguments.getSource().getEntity();
													Level level = arguments.getSource().getLevel();
											if (JerotesVillageLevelData.get(level) != null && WorldEvent.ZsieinLatentInfectedAbove(level)) {
														if (entity instanceof Player player) {
															player.sendSystemMessage(Component.translatable("message.jerotesvillage.zsiein_affinity", JerotesVillageLevelData.get(level).ZsieinAffinity).withStyle(ChatFormatting.WHITE));
														}
													}
													else {
														if (entity instanceof Player player) {
															player.sendSystemMessage(Component.translatable("message.jerotesvillage.zsiein_affinity_not_second_round_world").withStyle(ChatFormatting.WHITE));
														}
													}
													return 0;
												}
										)
								)
						)
				)
				//修改
				.then(Commands.literal("entity_change").then(Commands.argument("entities", EntityArgument.entities())
								.then(Commands.literal("meror_wanted_order")
										.then(Commands.argument("number", DoubleArgumentType.doubleArg(0)).executes(arguments -> {
															Collection<? extends Entity> entities = EntityArgument.getEntities(arguments, "entities");
															double d = DoubleArgumentType.getDouble(arguments, "number");
															for (Entity entity : entities) {
																Main.getJerotesPersistentData(entity).putDouble("jerotesvillage_meror_wanted_order", d);
																if (entity instanceof LivingEntity living) {
																	living.removeEffect(JerotesVillageMobEffects.MEROR_WANTED_ORDER.get());
																	living.addEffect(new MobEffectInstance(JerotesVillageMobEffects.MEROR_WANTED_ORDER.get(), (int) d, 0, false, false));
																}
															}
															return 0;
														}
												)
										)
								)
								.then(Commands.literal("variant_zsiein_discard")
										.then(Commands.argument("number", DoubleArgumentType.doubleArg(0)).executes(arguments -> {
															Collection<? extends Entity> entities = EntityArgument.getEntities(arguments, "entities");
															double d = DoubleArgumentType.getDouble(arguments, "number");
															for (Entity entity : entities) {
																Main.getJerotesPersistentData(entity).putDouble("jerotesvillage_variant_zsiein_discard", d);
															}
															return 0;
														}
												)
										)
								)
						)
				)
				//角色
				.then(Commands.literal("character")
								.then(Commands.literal("Jerotes").executes(arguments ->
												{
													CarvedVillagerEntity carvedVillager = JerotesVillageEntityType.CARVED_VILLAGER.get().spawn(arguments.getSource().getLevel(), BlockPos.containing(arguments.getSource().getPosition().x,arguments.getSource().getPosition().y, arguments.getSource().getPosition().z), MobSpawnType.MOB_SUMMONED);
													if (carvedVillager != null) {
														carvedVillager.setCustomName(Component.literal("Jerotes"));
														carvedVillager.setSkin(true);
														carvedVillager.setFemale(false);
														carvedVillager.setEndermanRace(false);
														carvedVillager.setPiglinRace(false);
														carvedVillager.setIllagerRace(false);
														carvedVillager.setCanChangeInventory(true);
														carvedVillager.setCanChangeMeleeOrRange(true);
														carvedVillager.setCanPickUpLoot(false);
														carvedVillager.setChangeInventoryCooldownTick(10);
														Objects.requireNonNull(carvedVillager.getAttribute(Attributes.MAX_HEALTH)).setBaseValue(26);
														carvedVillager.setHealth(carvedVillager.getMaxHealth());
														Objects.requireNonNull(carvedVillager.getAttribute(Attributes.FOLLOW_RANGE)).setBaseValue(128);
														carvedVillager.setItemSlot(EquipmentSlot.MAINHAND, new ItemStack(Items.AIR));
														carvedVillager.setItemSlot(EquipmentSlot.OFFHAND, new ItemStack(Items.AIR));
														carvedVillager.setItemSlot(EquipmentSlot.HEAD, new ItemStack(Items.AIR));
														carvedVillager.setItemSlot(EquipmentSlot.CHEST, new ItemStack(Items.AIR));
														carvedVillager.setItemSlot(EquipmentSlot.LEGS, new ItemStack(Items.AIR));
														carvedVillager.setItemSlot(EquipmentSlot.FEET, new ItemStack(Items.AIR));
														carvedVillager.mobInventory().addItem(new ItemStack(JerotesVillageItems.VILLAGER_METAL_SWORD.get()));
														carvedVillager.mobInventory().addItem(new ItemStack(JerotesVillageItems.VILLAGER_METAL_SHILED.get()));
														carvedVillager.mobInventory().addItem(new ItemStack(JerotesVillageItems.VILLAGER_METAL_HELMET.get()));
														carvedVillager.mobInventory().addItem(new ItemStack(JerotesVillageItems.VILLAGER_METAL_CHESTPLATE.get()));
														carvedVillager.mobInventory().addItem(new ItemStack(JerotesVillageItems.VILLAGER_METAL_LEGGINGS.get()));
														carvedVillager.mobInventory().addItem(new ItemStack(JerotesVillageItems.VILLAGER_METAL_BOOTS.get()));
														carvedVillager.mobInventory().addItem(new ItemStack(JerotesVillageItems.VILLAGER_METAL_CROSSBOW.get()));
													}
													return 0;
												}
										)
								)
								.then(Commands.literal("Maid").executes(arguments ->
												{
													CarvedVillagerEntity carvedVillager = JerotesVillageEntityType.CARVED_VILLAGER.get().spawn(arguments.getSource().getLevel(), BlockPos.containing(arguments.getSource().getPosition().x,arguments.getSource().getPosition().y, arguments.getSource().getPosition().z), MobSpawnType.MOB_SUMMONED);
													if (carvedVillager != null) {
														carvedVillager.setCustomName(Component.literal("Maid"));
														carvedVillager.setSkin(true);
														carvedVillager.setFemale(false);
														carvedVillager.setEndermanRace(false);
														carvedVillager.setPiglinRace(false);
														carvedVillager.setIllagerRace(false);
														carvedVillager.setCanChangeInventory(true);
														carvedVillager.setCanChangeMeleeOrRange(true);
														carvedVillager.setCanPickUpLoot(false);
														carvedVillager.setChangeInventoryCooldownTick(10);
														Objects.requireNonNull(carvedVillager.getAttribute(Attributes.MAX_HEALTH)).setBaseValue(32);
														carvedVillager.setHealth(carvedVillager.getMaxHealth());
														Objects.requireNonNull(carvedVillager.getAttribute(Attributes.FOLLOW_RANGE)).setBaseValue(128);
														carvedVillager.setBowLevel(18);

														carvedVillager.spellLevel = 3;
														CompoundTag data = Main.getJerotesPersistentData(carvedVillager);
														ListTag spellList = data.getList("jerotes_main_spell_string_list", Tag.TAG_STRING);
														spellList.add(StringTag.valueOf("jerotes_magic_missile"));
														spellList.add(StringTag.valueOf("jerotes_fire_bolt"));
														spellList.add(StringTag.valueOf("jerotes_ray_of_frost"));
														spellList.add(StringTag.valueOf("jerotes_shocking_grasp"));
														spellList.add(StringTag.valueOf("jerotes_life_mend"));
														spellList.add(StringTag.valueOf("jerotes_witch_bolt"));
														data.put("jerotes_main_spell_string_list", spellList);
														ListTag spellList2 = data.getList("jerotes_add_spell_string_list", Tag.TAG_STRING);
														spellList2.add(StringTag.valueOf("jerotes_misty_step"));
														spellList2.add(StringTag.valueOf("jerotes_counterspell"));
														data.put("jerotes_add_spell_string_list", spellList2);

														carvedVillager.setItemSlot(EquipmentSlot.MAINHAND, new ItemStack(Items.AIR));
														carvedVillager.setItemSlot(EquipmentSlot.OFFHAND, new ItemStack(Items.AIR));
														carvedVillager.setItemSlot(EquipmentSlot.HEAD, new ItemStack(Items.AIR));
														carvedVillager.setItemSlot(EquipmentSlot.CHEST, new ItemStack(Items.AIR));
														carvedVillager.setItemSlot(EquipmentSlot.LEGS, new ItemStack(Items.AIR));
														carvedVillager.setItemSlot(EquipmentSlot.FEET, new ItemStack(Items.AIR));
														carvedVillager.mobInventory().addItem(new ItemStack(JerotesVillageItems.VILLAGER_METAL_BOW.get()));
														carvedVillager.mobInventory().addItem(new ItemStack(JerotesVillageItems.VILLAGER_METAL_HELMET.get()));
														carvedVillager.mobInventory().addItem(new ItemStack(JerotesVillageItems.VILLAGER_METAL_CHESTPLATE.get()));
														carvedVillager.mobInventory().addItem(new ItemStack(JerotesVillageItems.VILLAGER_METAL_LEGGINGS.get()));
														carvedVillager.mobInventory().addItem(new ItemStack(JerotesVillageItems.VILLAGER_METAL_BOOTS.get()));
													}
													return 0;
												}
										)
								)
								.then(Commands.literal("Sentnes").executes(arguments ->
												{
													CarvedVillagerEntity carvedVillager = JerotesVillageEntityType.CARVED_VILLAGER.get().spawn(arguments.getSource().getLevel(), BlockPos.containing(arguments.getSource().getPosition().x,arguments.getSource().getPosition().y, arguments.getSource().getPosition().z), MobSpawnType.MOB_SUMMONED);
													if (carvedVillager != null) {
														carvedVillager.setCustomName(Component.literal("Sentnes"));
														carvedVillager.setSkin(true);
														carvedVillager.setFemale(true);
														carvedVillager.setEndermanRace(false);
														carvedVillager.setPiglinRace(false);
														carvedVillager.setIllagerRace(false);
														carvedVillager.setCanChangeInventory(true);
														carvedVillager.setCanChangeMeleeOrRange(true);
														carvedVillager.setCanPickUpLoot(false);
														carvedVillager.setChangeInventoryCooldownTick(5);
														carvedVillager.setBowLevel(20);
														carvedVillager.setShieldLevel(3);
														Objects.requireNonNull(carvedVillager.getAttribute(Attributes.MAX_HEALTH)).setBaseValue(40);
														carvedVillager.setHealth(carvedVillager.getMaxHealth());
														Objects.requireNonNull(carvedVillager.getAttribute(Attributes.ATTACK_DAMAGE)).setBaseValue(5);
														Objects.requireNonNull(carvedVillager.getAttribute(Attributes.MOVEMENT_SPEED)).setBaseValue(0.33);
														Objects.requireNonNull(carvedVillager.getAttribute(Attributes.FOLLOW_RANGE)).setBaseValue(128);
														if (!carvedVillager.level().isClientSide) {
															carvedVillager.addEffect(new MobEffectInstance(MobEffects.DAMAGE_RESISTANCE, -1, 2, false, false), carvedVillager);
															carvedVillager.addEffect(new MobEffectInstance(MobEffects.WATER_BREATHING, -1, 0, false, false), carvedVillager);
															carvedVillager.addEffect(new MobEffectInstance(MobEffects.FIRE_RESISTANCE, -1, 0, false, false), carvedVillager);
														}
														carvedVillager.setItemSlot(EquipmentSlot.MAINHAND, new ItemStack(Items.AIR));
														carvedVillager.setItemSlot(EquipmentSlot.OFFHAND, new ItemStack(Items.AIR));
														carvedVillager.setItemSlot(EquipmentSlot.HEAD, new ItemStack(Items.AIR));
														carvedVillager.setItemSlot(EquipmentSlot.CHEST, new ItemStack(Items.AIR));
														carvedVillager.setItemSlot(EquipmentSlot.LEGS, new ItemStack(Items.AIR));
														carvedVillager.setItemSlot(EquipmentSlot.FEET, new ItemStack(Items.AIR));

														//剑
														ItemStack sword = new ItemStack(Items.NETHERITE_SWORD);
														sword.enchant(JerotesEnchantments.BANE_OF_HUMANOIDS.get(), 4);
														sword.enchant(Enchantments.KNOCKBACK, 2);
														sword.enchant(Enchantments.MOB_LOOTING, 3);
														sword.enchant(Enchantments.SWEEPING_EDGE, 3);
														sword.enchant(Enchantments.UNBREAKING, 3);
														//弓
														ItemStack bow = new ItemStack(JerotesVillageItems.VILLAGER_METAL_BOW.get());
														bow.enchant(Enchantments.POWER_ARROWS, 5);
														bow.enchant(Enchantments.INFINITY_ARROWS, 1);
														bow.enchant(Enchantments.PUNCH_ARROWS, 2);
														bow.enchant(Enchantments.UNBREAKING, 3);
														//盔甲
														ItemStack helmet = new ItemStack(JerotesVillageItems.CARVED_LEADER_HELMET.get());
														ItemStack chestplate = new ItemStack(JerotesVillageItems.CARVED_LEADER_CHESTPLATE.get());
														ItemStack leggings = new ItemStack(JerotesVillageItems.CARVED_LEADER_LEGGINGS.get());
														ItemStack boots = new ItemStack(JerotesVillageItems.CARVED_LEADER_BOOTS.get());
														helmet.enchant(Enchantments.ALL_DAMAGE_PROTECTION, 4);
														chestplate.enchant(Enchantments.ALL_DAMAGE_PROTECTION, 4);
														leggings.enchant(Enchantments.ALL_DAMAGE_PROTECTION, 4);
														boots.enchant(Enchantments.ALL_DAMAGE_PROTECTION, 4);
														helmet.enchant(Enchantments.UNBREAKING, 3);
														helmet.enchant(Enchantments.THORNS, 3);
														chestplate.enchant(Enchantments.UNBREAKING, 3);
														chestplate.enchant(Enchantments.THORNS, 3);
														leggings.enchant(Enchantments.UNBREAKING, 3);
														leggings.enchant(Enchantments.THORNS, 3);
														boots.enchant(Enchantments.THORNS, 3);
														boots.enchant(Enchantments.UNBREAKING, 3);
														boots.enchant(Enchantments.FALL_PROTECTION, 4);
														boots.enchant(Enchantments.THORNS, 3);
														//
														carvedVillager.mobInventory().addItem(sword);
														carvedVillager.mobInventory().addItem(helmet);
														carvedVillager.mobInventory().addItem(chestplate);
														carvedVillager.mobInventory().addItem(leggings);
														carvedVillager.mobInventory().addItem(boots);
														carvedVillager.mobInventory().addItem(bow);
														ItemStack arrow = new ItemStack(Items.TIPPED_ARROW);
														PotionUtils.setPotion(arrow, Potions.LONG_WEAKNESS);
														carvedVillager.mobInventory().addItem(arrow);
													}
													return 0;
												}
										)
								)
						)
		);
	}
}