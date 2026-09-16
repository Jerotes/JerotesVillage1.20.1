package com.jerotes.jerotesvillage.alchemy;

import com.jerotes.jerotes.alchemy.effect.*;
import com.jerotes.jerotes.alchemy.forge.JerotesAlchemyMaterialEffectEvent;
import com.jerotes.jerotesvillage.JerotesVillage;
import com.jerotes.jerotesvillage.alchemy.effect.FragranceAlchemyEffect;
import com.jerotes.jerotesvillage.alchemy.effect.WildernessGiantPoisonAlchemyEffect;
import com.jerotes.jerotesvillage.init.JerotesVillageItems;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

@Mod.EventBusSubscriber(modid = JerotesVillage.MODID)
public class MaterialEvent {

	@SubscribeEvent
	public static void Material(JerotesAlchemyMaterialEffectEvent event) {
		ItemStack itemStack = event.getMaterial();
		if (itemStack == null)
			return;
		//锈蚀步甲颚
		if (itemStack.is(JerotesVillageItems.RUST_BEETLE_MANDIBLE.get())) {
			event.setEffectCount(3);
			event.setEffect1(new PoisonAlchemyEffect(1, 2));
			event.setEffect2(new CorrosiveAlchemyEffect(2, 2));
			event.setEffect3(new MiningFatigueAlchemyEffect(1, 2));
		}
		//褐毒蛞蝓
		if (itemStack.is(JerotesVillageItems.BROWN_VENOMOUS_SLUG.get())) {
			event.setEffectCount(3);
			event.setEffect1(new PoisonAlchemyEffect(2, 2));
			event.setEffect2(new DeadlyPoisonAlchemyEffect(1, 2));
			event.setEffect3(new WildernessGiantPoisonAlchemyEffect(2, 2));
		}
		//无味瓜
		if (itemStack.is(JerotesVillageItems.FLAVORLESS_MELON.get())) {
			event.setEffectCount(3);
			event.setEffect1(new RegenerationAlchemyEffect(1, 1));
			event.setEffect2(new FogAlchemyEffect(1, 2));
			event.setEffect3(new WildernessGiantPoisonAlchemyEffect(1, 1));
		}
		//迷林花 袋装迷林花瓣 迷林香料
		if (itemStack.is(JerotesVillageItems.MYSTEREST_FLOWER.get()) || itemStack.is(JerotesVillageItems.BAGGED_MYSTEREST_PETALS.get()) || itemStack.is(JerotesVillageItems.MYSTEREST_PERFUME.get())) {
			event.setEffectCount(3);
			event.setEffect1(new RegenerationAlchemyEffect(1, 1));
			event.setEffect2(new FragranceAlchemyEffect(1, 2));
			event.setEffect3(new WildernessGiantPoisonAlchemyEffect(1, 1));
		}
		//明路脆瓜
		if (itemStack.is(JerotesVillageItems.BRIGHT_MELON.get())) {
			event.setEffectCount(3);
			event.setEffect1(new MagicAbsorptionAlchemyEffect(1, 1));
			event.setEffect2(new SaturationAlchemyEffect(1, 1));
			event.setEffect3(new SpeedAlchemyEffect(1, 1));
		}
		//金明路脆瓜
		if (itemStack.is(JerotesVillageItems.GOLDEN_BRIGHT_MELON.get())) {
			event.setEffectCount(3);
			event.setEffect1(new MagicAbsorptionAlchemyEffect(2, 2));
			event.setEffect2(new SaturationAlchemyEffect(2, 1));
			event.setEffect3(new AbsorptionAlchemyEffect(1, 1));
		}
		//二轮芦荟
		if (itemStack.is(JerotesVillageItems.SECOND_ROUND_ALOE_LEAF.get())) {
			event.setEffectCount(3);
			event.setEffect1(new LuckAlchemyEffect(1, 1));
			event.setEffect2(new RegenerationAlchemyEffect(1, 2));
			event.setEffect3(new InstantHealthAlchemyEffect(2, 2));
		}
		//紫沙守宫尾
		if (itemStack.is(JerotesVillageItems.PURPLE_SAND_GECKO_TAIL.get())) {
			event.setEffectCount(3);
			event.setEffect1(new LuckAlchemyEffect(2, 2));
			event.setEffect2(new UnluckAlchemyEffect(2, 2));
			event.setEffect3(new SpeedAlchemyEffect(1, 2));
		}
		//二轮龙葵
		if (itemStack.is(JerotesVillageItems.SECOND_ROUND_NIGRUM.get())) {
			event.setEffectCount(3);
			event.setEffect1(new PoisonAlchemyEffect(1, 1));
			event.setEffect2(new BlindnessAlchemyEffect(1, 2));
			event.setEffect3(new HasteAlchemyEffect(2, 1));
		}
		//冰岩
		if (itemStack.is(JerotesVillageItems.ICE_ROCK.get())) {
			event.setEffectCount(3);
			event.setEffect1(new SlownessAlchemyEffect(2, 1));
			event.setEffect2(new ColdAbsorptionAlchemyEffect(2, 1));
			event.setEffect3(new MiningFatigueAlchemyEffect(2, 2));
		}
		//荒野荆棘
		if (itemStack.is(JerotesVillageItems.WILDERNESS_THORNS.get())) {
			event.setEffectCount(3);
			event.setEffect1(new UnluckAlchemyEffect(1, 1));
			event.setEffect2(new BleedingAlchemyEffect(2, 1));
			event.setEffect3(new NauseaAlchemyEffect(1, 1));
		}
		//鱼类
		if (itemStack.is(JerotesVillageItems.JEWELROAMER.get()) ||
				itemStack.is(JerotesVillageItems.GREYSCALE.get()) ||
				itemStack.is(JerotesVillageItems.STARBRITE.get()) ||
				itemStack.is(JerotesVillageItems.BITTER_COD.get()) ||
				itemStack.is(JerotesVillageItems.LAKE_LOACH.get()) ||
				itemStack.is(JerotesVillageItems.BRIGHT_GRASS_CARP.get()) ||
				itemStack.is(JerotesVillageItems.CHROMASNAP_BASS.get()) ||
				itemStack.is(JerotesVillageItems.ABYSSALER.get()) ||
				itemStack.is(JerotesVillageItems.SOLISH_MEAT.get())) {
			event.setEffectCount(3);
			event.setEffect1(new DolphinsGraceAlchemyEffect(1, 1));
			event.setEffect2(new WaterBreathingAlchemyEffect(1, 1));
			event.setEffect3(new HasteAlchemyEffect(1, 1));
		}
		//熟鱼类
		if (itemStack.is(JerotesVillageItems.COOKED_JEWELROAMER.get()) ||
				itemStack.is(JerotesVillageItems.COOKED_GREYSCALE.get()) ||
				itemStack.is(JerotesVillageItems.COOKED_STARBRITE.get()) ||
				itemStack.is(JerotesVillageItems.COOKED_BITTER_COD.get()) ||
				itemStack.is(JerotesVillageItems.COOKED_LAKE_LOACH.get()) ||
				itemStack.is(JerotesVillageItems.COOKED_BRIGHT_GRASS_CARP.get()) ||
				itemStack.is(JerotesVillageItems.COOKED_CHROMASNAP_BASS.get()) ||
				itemStack.is(JerotesVillageItems.COOKED_ABYSSALER.get()) ||
				itemStack.is(JerotesVillageItems.COOKED_SOLISH_MEAT.get())) {
			event.setEffectCount(3);
			event.setEffect1(new SaturationAlchemyEffect(1, 1));
			event.setEffect2(new WaterBreathingAlchemyEffect(1, 2));
			event.setEffect3(new HasteAlchemyEffect(1, 2));
		}
		//肉类
		if (itemStack.is(JerotesVillageItems.RAMBLER_MEAT.get()) ||
				itemStack.is(JerotesVillageItems.CLOUD_FUR_VENISON.get()) ||
				itemStack.is(JerotesVillageItems.MEGANOSE_SNOW_LIZARD_MEAT.get())) {
			event.setEffectCount(3);
			event.setEffect1(new SpeedAlchemyEffect(1, 1));
			event.setEffect2(new WeaknessAlchemyEffect(1, 1));
			event.setEffect3(new HungerAlchemyEffect(1, 1));
		}
		//熟肉类
		if (itemStack.is(JerotesVillageItems.COOKED_RAMBLER_MEAT.get()) ||
				itemStack.is(JerotesVillageItems.COOKED_CLOUD_FUR_VENISON.get()) ||
				itemStack.is(JerotesVillageItems.COOKED_MEGANOSE_SNOW_LIZARD_MEAT.get())) {
			event.setEffectCount(3);
			event.setEffect1(new SaturationAlchemyEffect(1, 1));
			event.setEffect2(new SpeedAlchemyEffect(1, 2));
			event.setEffect3(new WeaknessAlchemyEffect(1, 2));
		}
		//彩尾鸡蛋
		if (itemStack.is(JerotesVillageItems.IRISHTAIL_CHICKEN_EGG.get())) {
			event.setEffectCount(3);
			event.setEffect1(new MagicAbsorptionAlchemyEffect(1, 1));
			event.setEffect2(new UnluckAlchemyEffect(1, 2));
			event.setEffect3(new JumpBoostAlchemyEffect(2, 1));
		}
		//捕食齿花
		if (itemStack.is(JerotesVillageItems.PREDATION_TOOTHED_FLOWER_BLOCK.get())) {
			event.setEffectCount(3);
			event.setEffect1(new DeadlyPoisonAlchemyEffect(2, 1));
			event.setEffect2(new SlownessAlchemyEffect(1, 2));
			event.setEffect3(new MiningFatigueAlchemyEffect(1, 1));
		}
		//二轮蒲公英
		if (itemStack.is(JerotesVillageItems.SECOND_ROUND_DANDELION.get())) {
			event.setEffectCount(3);
			event.setEffect1(new HealthBoostAlchemyEffect(2, 1));
			event.setEffect2(new RegenerationAlchemyEffect(1, 1));
			event.setEffect3(new SaturationAlchemyEffect(1, 1));
		}
		//白风堇
		if (itemStack.is(JerotesVillageItems.WHITE_BREEZE_VIOLET.get())) {
			event.setEffectCount(3);
			event.setEffect1(new SaturationAlchemyEffect(2, 1));
			event.setEffect2(new LevitationAlchemyEffect(1, 1));
			event.setEffect3(new DarknessAlchemyEffect(1, 1));
		}
		//紫婆挪
		if (itemStack.is(JerotesVillageItems.PURPLE_PONUO.get())) {
			event.setEffectCount(3);
			event.setEffect1(new HasteAlchemyEffect(2, 1));
			event.setEffect2(new JumpBoostAlchemyEffect(1, 1));
			event.setEffect3(new SlowFallingAlchemyEffect(1, 1));
		}
		//闪络草
		if (itemStack.is(JerotesVillageItems.FLASHING_GRASS.get())) {
			event.setEffectCount(3);
			event.setEffect1(new GlowingAlchemyEffect(1, 2));
			event.setEffect2(new ResistanceAlchemyEffect(1, 1));
			event.setEffect3(new WeaknessAlchemyEffect(1, 1));
		}
		//香坪草
		if (itemStack.is(JerotesVillageItems.FRAGRANT_LAWN.get())) {
			event.setEffectCount(3);
			event.setEffect1(new LuckAlchemyEffect(2, 1));
			event.setEffect2(new TruesightAlchemyEffect(1, 1));
			event.setEffect3(new FragranceAlchemyEffect(1, 1));
		}
		//青蓝根
		if (itemStack.is(JerotesVillageItems.CYAN_ROOT.get())) {
			event.setEffectCount(3);
			event.setEffect1(new JumpBoostAlchemyEffect(2, 1));
			event.setEffect2(new CorrosiveAlchemyEffect(1, 1));
			event.setEffect3(new SaturationAlchemyEffect(1, 1));
		}
		//苦寒雪花
		if (itemStack.is(JerotesVillageItems.BITTER_COLD_SNOW_FLOWER.get())) {
			event.setEffectCount(3);
			event.setEffect1(new SlownessAlchemyEffect(2, 1));
			event.setEffect2(new ColdAbsorptionAlchemyEffect(1, 1));
			event.setEffect3(new ResistanceAlchemyEffect(1, 1));
		}
		//闻草
		if (itemStack.is(JerotesVillageItems.HEARSAY_GRASS.get())) {
			event.setEffectCount(3);
			event.setEffect1(new PoisonAlchemyEffect(2, 1));
			event.setEffect2(new DeadlyPoisonAlchemyEffect(1, 1));
			event.setEffect3(new JumpBoostAlchemyEffect(1, 1));
		}
		//仙境花
		if (itemStack.is(JerotesVillageItems.WONDERLAND_FLOWER.get())) {
			event.setEffectCount(3);
			event.setEffect1(new RegenerationAlchemyEffect(2, 1));
			event.setEffect2(new SlownessAlchemyEffect(1, 1));
			event.setEffect3(new WeaknessAlchemyEffect(1, 1));
		}
		//红盎然
		if (itemStack.is(JerotesVillageItems.RED_LUSH.get())) {
			event.setEffectCount(3);
			event.setEffect1(new WeaknessAlchemyEffect(2, 1));
			event.setEffect2(new TruesightAlchemyEffect(1, 1));
			event.setEffect3(new MagicAbsorptionAlchemyEffect(1, 1));
		}
		//品红菊
		if (itemStack.is(JerotesVillageItems.MAGENTA_MUMS.get())) {
			event.setEffectCount(3);
			event.setEffect1(new NightVisionAlchemyEffect(1, 2));
			event.setEffect2(new TruesightAlchemyEffect(1, 1));
			event.setEffect3(new HungerAlchemyEffect(1, 1));
		}
		//芒药
		if (itemStack.is(JerotesVillageItems.AWN_HERB.get())) {
			event.setEffectCount(3);
			event.setEffect1(new InstantHealthAlchemyEffect(2, 1));
			event.setEffect2(new ResistanceAlchemyEffect(2, 1));
			event.setEffect3(new HealthBoostAlchemyEffect(1, 2));
		}
		//谐梦铃
		if (itemStack.is(JerotesVillageItems.HARMONIOUS_DREAM_BELL.get())) {
			event.setEffectCount(3);
			event.setEffect1(new RegenerationAlchemyEffect(1, 2));
			event.setEffect2(new TruesightAlchemyEffect(1, 1));
			event.setEffect3(new DarknessAlchemyEffect(1, 1));
		}
		//阳橙子
		if (itemStack.is(JerotesVillageItems.SUN_ORANGE_FLOWER.get())) {
			event.setEffectCount(3);
			event.setEffect1(new FireAbsorptionAlchemyEffect(2, 1));
			event.setEffect2(new FireResistanceAlchemyEffect(1, 1));
			event.setEffect3(new SlowFallingAlchemyEffect(1, 1));
		}
		//艼花
		if (itemStack.is(JerotesVillageItems.TINBUSH.get())) {
			event.setEffectCount(3);
			event.setEffect1(new RegenerationAlchemyEffect(2, 1));
			event.setEffect2(new BleedingAlchemyEffect(1, 1));
			event.setEffect3(new HealthBoostAlchemyEffect(1, 1));
		}
		//褐泥刺
		if (itemStack.is(JerotesVillageItems.BROWN_MUD_THORNS.get())) {
			event.setEffectCount(3);
			event.setEffect1(new DarknessAlchemyEffect(1, 2));
			event.setEffect2(new BleedingAlchemyEffect(1, 1));
			event.setEffect3(new PoisonAlchemyEffect(1, 1));
		}
		//绿人杆
		if (itemStack.is(JerotesVillageItems.GREEN_HUMANOID_CANE.get()) || itemStack.is(JerotesVillageItems.GREEN_HUMANOID_CANE_STEMS.get())) {
			event.setEffectCount(3);
			event.setEffect1(new AnesthetizedAlchemyEffect(2, 2));
			event.setEffect2(new SlownessAlchemyEffect(2, 1));
			event.setEffect3(new ResistanceAlchemyEffect(1, 1));
		}
		if (itemStack.is(JerotesVillageItems.GREEN_HUMANOID_CANE_STEMS_BUNDLE.get())) {
			event.setEffectCount(3);
			event.setEffect1(new AnesthetizedAlchemyEffect(2, 2));
			event.setEffect2(new SlownessAlchemyEffect(2, 2));
			event.setEffect3(new ResistanceAlchemyEffect(2, 1));
		}
		//麻尘花
		if (itemStack.is(JerotesVillageItems.FIBRE_DUST_FLOWER.get())) {
			event.setEffectCount(3);
			event.setEffect1(new WeaknessAlchemyEffect(2, 1));
			event.setEffect2(new FogAlchemyEffect(1, 1));
			event.setEffect3(new ResistanceAlchemyEffect(1, 1));
		}
		//黑丽灌
		if (itemStack.is(JerotesVillageItems.BLACK_PRETTY_BUSH.get())) {
			event.setEffectCount(3);
			event.setEffect1(new DarknessAlchemyEffect(1, 2));
			event.setEffect2(new StrengthAlchemyEffect(1, 1));
			event.setEffect3(new SaturationAlchemyEffect(1, 1));
		}
		//石叶蕨
		if (itemStack.is(JerotesVillageItems.STONE_LEAVES_FERN.get()) || itemStack.is(JerotesVillageItems.LARGE_STONE_LEAVES_FERN.get())) {
			event.setEffectCount(3);
			event.setEffect1(new ResistanceAlchemyEffect(1, 2));
			event.setEffect2(new FragranceAlchemyEffect(1, 1));
			event.setEffect3(new FogAlchemyEffect(1, 1));
		}
		//宝石海葵
		if (itemStack.is(JerotesVillageItems.GEMSTONE_ANEMONE.get())) {
			event.setEffectCount(3);
			event.setEffect1(new WaterBreathingAlchemyEffect(2, 1));
			event.setEffect2(new DolphinsGraceAlchemyEffect(1, 1));
			event.setEffect3(new PoisonAlchemyEffect(1, 1));
		}
		//粉红仙人掌
		if (itemStack.is(JerotesVillageItems.PINK_CACTUS.get()) || itemStack.is(JerotesVillageItems.PINK_CACTUS_FLOWER.get())) {
			event.setEffectCount(3);
			event.setEffect1(new AnesthetizedAlchemyEffect(1, 1));
			event.setEffect2(new SaturationAlchemyEffect(1, 1));
			event.setEffect3(new BleedingAlchemyEffect(2, 1));
		}
		//明路草
		if (itemStack.is(JerotesVillageItems.BRIGHT_GRASS.get()) || itemStack.is(JerotesVillageItems.TALL_BRIGHT_GRASS.get())) {
			event.setEffectCount(3);
			event.setEffect1(new GlowingAlchemyEffect(1, 3));
			event.setEffect2(new SpeedAlchemyEffect(1, 1));
			event.setEffect3(new JumpBoostAlchemyEffect(1, 1));
		}
		//紫黑死灌
		if (itemStack.is(JerotesVillageItems.PURPLE_BLACK_DEAD_BUSH.get())) {
			event.setEffectCount(3);
			event.setEffect1(new WitherAlchemyEffect(1, 3));
			event.setEffect2(new InstantDamageAlchemyEffect(1, 1));
			event.setEffect3(new InstantDamageAlchemyEffect(1, 1));
		}
		//紫沙枯草从
		if (itemStack.is(JerotesVillageItems.PURPLE_SAND_SHORT_DRY_GRASS.get()) || itemStack.is(JerotesVillageItems.PURPLE_SAND_TALL_DRY_GRASS.get())) {
			event.setEffectCount(3);
			event.setEffect1(new WitherAlchemyEffect(2, 1));
			event.setEffect2(new GlowingAlchemyEffect(1, 1));
			event.setEffect3(new HungerAlchemyEffect(1, 1));
		}
		//明路草
		if (itemStack.is(JerotesVillageItems.PREDATION_TOOTHED_FLOWER_BLOCK.get())) {
			event.setEffectCount(3);
			event.setEffect1(new DeadlyPoisonAlchemyEffect(1, 1));
			event.setEffect2(new SlownessAlchemyEffect(1, 1));
			event.setEffect3(new MiningFatigueAlchemyEffect(1, 1));
		}
		//恶怨魂
		if (itemStack.is(JerotesVillageItems.BLAMER_SOUL.get())) {
			event.setEffectCount(3);
			event.setEffect1(new CloakingAlchemyEffect(1, 2));
			event.setEffect2(new DarknessAlchemyEffect(1, 1));
			event.setEffect3(new SpeedAlchemyEffect(1, 1));
		}
		//粉黏液球
		if (itemStack.is(JerotesVillageItems.PINK_SLIME_BALL.get())) {
			event.setEffectCount(3);
			event.setEffect1(new SlownessAlchemyEffect(1, 2));
			event.setEffect2(new CorrosiveAlchemyEffect(1, 1));
			event.setEffect3(new PruritusAlchemyEffect(2, 1));
		}
		//鳞熊毛 巨怪毛 巨怪角
		if (itemStack.is(JerotesVillageItems.SCALY_BEAR_HAIR.get()) || itemStack.is(JerotesVillageItems.GIANT_MONSTER_HAIR.get()) || itemStack.is(JerotesVillageItems.GIANT_MONSTER_HORN.get())) {
			event.setEffectCount(3);
			event.setEffect1(new ColdAbsorptionAlchemyEffect(1, 2));
			event.setEffect2(new WeaknessAlchemyEffect(2, 2));
			event.setEffect3(new NauseaAlchemyEffect(1, 1));
		}
		//鳞熊鳞片
		if (itemStack.is(JerotesVillageItems.SCALY_BEAR_SCALE.get())) {
			event.setEffectCount(3);
			event.setEffect1(new UnluckAlchemyEffect(2, 1));
			event.setEffect2(new ResistanceAlchemyEffect(1, 3));
			event.setEffect3(new ResistanceAlchemyEffect(2, 1));
		}
		//滑行者黏液
		if (itemStack.is(JerotesVillageItems.SLIDER_MUCUS.get())) {
			event.setEffectCount(3);
			event.setEffect1(new SlownessAlchemyEffect(1, 2));
			event.setEffect2(new CorrosiveAlchemyEffect(1, 1));
			event.setEffect3(new GlowingAlchemyEffect(1, 2));
		}
		//苦寒油
		if (itemStack.is(JerotesVillageItems.BITTER_COLD_FAT.get())) {
			event.setEffectCount(3);
			event.setEffect1(new ColdAbsorptionAlchemyEffect(1, 2));
			event.setEffect2(new HasteAlchemyEffect(1, 1));
			event.setEffect3(new JumpBoostAlchemyEffect(1, 1));
		}
		//寒霜雪怪毛发
		if (itemStack.is(JerotesVillageItems.FROST_YETI_HAIR.get())) {
			event.setEffectCount(3);
			event.setEffect1(new ColdAbsorptionAlchemyEffect(3, 2));
			event.setEffect2(new DarknessAlchemyEffect(1, 1));
			event.setEffect3(new StrengthAlchemyEffect(1, 1));
		}
		//泌火恶怪黏胶
		if (itemStack.is(JerotesVillageItems.FIRE_SECRETOR_ADHESIVE.get())) {
			event.setEffectCount(3);
			event.setEffect1(new FireResistanceAlchemyEffect(3, 2));
			event.setEffect2(new DarknessAlchemyEffect(1, 1));
			event.setEffect3(new ExplosionAlchemyEffect(1, 1));
		}
		//闪电蠕虫甲壳
		if (itemStack.is(JerotesVillageItems.LIGHTNING_WORM_CHITIN.get())) {
			event.setEffectCount(3);
			event.setEffect1(new LightningAbsorptionAlchemyEffect(3, 2));
			event.setEffect2(new DarknessAlchemyEffect(1, 1));
			event.setEffect3(new SpeedAlchemyEffect(1, 1));
		}
		//海洋宝石 海洋宝石碎片
		if (itemStack.is(JerotesVillageItems.OCEAN_GEMSTONE.get()) || itemStack.is(JerotesVillageItems.OCEAN_GEMSTONE_FRAGMENTS.get())) {
			event.setEffectCount(3);
			event.setEffect1(new WaterBreathingAlchemyEffect(1, 2));
			event.setEffect2(new ResistanceAlchemyEffect(1, 2));
			event.setEffect3(new PruritusAlchemyEffect(1, 2));
		}
		//恶鳞鳞片 恶鳞牙齿
		if (itemStack.is(JerotesVillageItems.MARINE_REPTILES_FINE_SCALE.get()) || itemStack.is(JerotesVillageItems.MALIGNASAUR_TEETH.get())) {
			event.setEffectCount(3);
			event.setEffect1(new WaterBreathingAlchemyEffect(1, 2));
			event.setEffect2(new DolphinsGraceAlchemyEffect(2, 1));
			event.setEffect3(new SpeedAlchemyEffect(1, 2));
		}
	}
}