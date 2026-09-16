package com.jerotes.jerotesvillage.init;

import com.jerotes.jerotesvillage.JerotesVillage;
import com.jerotes.jerotesvillage.effect.*;
import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraftforge.common.ForgeMod;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

public class JerotesVillageMobEffects {
	public static final DeferredRegister<MobEffect> REGISTRY = DeferredRegister.create(ForgeRegistries.MOB_EFFECTS, JerotesVillage.MODID);
	public static final RegistryObject<MobEffect> WILDERNESS_GIANT_POISON = REGISTRY.register("wilderness_giant_poison", () -> new WildernessGiantPoisonMobEffect());
	public static final RegistryObject<MobEffect> CARVED_VILLAGER_ARROW = REGISTRY.register("carved_villager_arrow", () -> new CarvedVillagerArrowMobEffect());
	public static final RegistryObject<MobEffect> UNCLEAN_BODY = REGISTRY.register("unclean_body", () -> new UncleanBodyMobEffect()
			.addAttributeModifier(Attributes.MOVEMENT_SPEED, "04f832e7-5274-43c4-8301-784e3198b2ca", -0.1, AttributeModifier.Operation.MULTIPLY_TOTAL)
			.addAttributeModifier(Attributes.ATTACK_DAMAGE, "4f71afdc-7499-494e-93ac-b1e7a5e3192f", -3.0, AttributeModifier.Operation.ADDITION));
	public static final RegistryObject<MobEffect> WICKED_RIDICULE_NIGHT = REGISTRY.register("wicked_ridicule_night", () -> new WickedRidiculeNightMobEffect());
	public static final RegistryObject<MobEffect> WICKED_RIDICULE_NIGHT_START = REGISTRY.register("wicked_ridicule_night_start", () -> new WickedRidiculeNightStartMobEffect());
	public static final RegistryObject<MobEffect> THROWING_STUBBORN_STONE = REGISTRY.register("throwing_stubborn_stone", () -> new ThrowingStubbornStoneMobEffect()
			.addAttributeModifier(Attributes.MOVEMENT_SPEED, "86FC816D-3A26-9DA2-F0AF-230BAB60CA54", 0.2, AttributeModifier.Operation.MULTIPLY_TOTAL));
	public static final RegistryObject<MobEffect> FISHING_NET_CONFINEMET = REGISTRY.register("fishing_net_confinement", () -> new FishingNetConfinementMobEffect()
			.addAttributeModifier(Attributes.MOVEMENT_SPEED, "A761BC36-3DF4-A54A-86E9-EA736E25228B", -0.15, AttributeModifier.Operation.MULTIPLY_TOTAL)
			.addAttributeModifier(Attributes.ATTACK_DAMAGE, "F28C4746-4D14-00E7-E7D6-FFCF02CBB9C4", -3, AttributeModifier.Operation.ADDITION)
			.addAttributeModifier(Attributes.ATTACK_SPEED, "9C0622BD-6BA2-D097-E59E-B18DC8633AD1", -0.2, AttributeModifier.Operation.ADDITION));
	public static final RegistryObject<MobEffect> SERPON_CREDENTIAL = REGISTRY.register("serpon_credential", () -> new SerponCredentialMobEffect());
	public static final RegistryObject<MobEffect> GLOW_SPARROW_BLESSING = REGISTRY.register("glow_sparrow_blessing", () -> new GlowSparrowBlessingEffect());
	public static final RegistryObject<MobEffect> MALIGNASAUR_ASPHYXIA = REGISTRY.register("malignasaur_asphyxia", () -> new MalignasaurAsphyxiaMobEffect());
	public static final RegistryObject<MobEffect> OCEAN_DRAGON_DETERRENCE = REGISTRY.register("ocean_dragon_deterrence", () -> new OceanDragonDeterrenceMobEffect());
	public static final RegistryObject<MobEffect> FAIRNESS = REGISTRY.register("fairness", () -> new FairnessMobEffect());
	public static final RegistryObject<MobEffect> MEROR_STIMULIN = REGISTRY.register("meror_stimulin", () -> new MerorStimulinEffect()
			.addAttributeModifier(Attributes.MOVEMENT_SPEED, "3833DD1A-6D95-7B23-0BA2-F039960B90B8", 0.2, AttributeModifier.Operation.MULTIPLY_TOTAL)
			.addAttributeModifier(Attributes.ATTACK_DAMAGE, "F2DF9D77-78EA-EC1F-26A9-28710C376641", 3.0, AttributeModifier.Operation.ADDITION)
			.addAttributeModifier(Attributes.ATTACK_SPEED, "ECB297BB-456A-60A5-5141-02D01F006671", 0.2, AttributeModifier.Operation.MULTIPLY_TOTAL));
	public static final RegistryObject<MobEffect> MEROR_DAGGER = REGISTRY.register("meror_dagger", () -> new MerorDaggerEffect()
			.addAttributeModifier(Attributes.ATTACK_SPEED, "714F90F8-DA1E-19ED-D16C-1BC91233D667", 2.0, AttributeModifier.Operation.ADDITION));
	public static final RegistryObject<MobEffect> BITTER_COLD = REGISTRY.register("bitter_cold", () -> new BitterColdMobEffect()
			.addAttributeModifier(Attributes.KNOCKBACK_RESISTANCE, "F9FD3A4A-2A84-900D-EE6A-5F96D029DFC3", -0.20, AttributeModifier.Operation.MULTIPLY_TOTAL)
			.addAttributeModifier(Attributes.MOVEMENT_SPEED, "801C34ED-0DF5-E4FB-9E08-EB82AF21A21B", -0.30, AttributeModifier.Operation.MULTIPLY_TOTAL));
	public static final RegistryObject<MobEffect> DEEP_BLESSING = REGISTRY.register("deep_blessing", () -> new DeepBlessingMobEffect()
			.addAttributeModifier(Attributes.ATTACK_DAMAGE, "4B723228-1610-6525-B482-F8EAC791F30C", 2.0, AttributeModifier.Operation.ADDITION));
	public static final RegistryObject<MobEffect> RAMPANT = REGISTRY.register("rampant", () -> new RampantMobEffect()
			.addAttributeModifier(Attributes.MOVEMENT_SPEED, "0251C21C-A83D-5055-EADE-968E382FCADA", 0.1, AttributeModifier.Operation.MULTIPLY_TOTAL)
			.addAttributeModifier(Attributes.ATTACK_DAMAGE, "77800DA2-F0E6-7992-C1AD-47D1989DA844", 6.0, AttributeModifier.Operation.ADDITION));
	public static final RegistryObject<MobEffect> BITTER_COLD_PROTECTION = REGISTRY.register("bitter_cold_protection", () -> new BitterColdProtectionMobEffect()
			.addAttributeModifier(ForgeMod.ENTITY_REACH.get(), "c9d34bee-6b56-4e22-a812-725d704a1d46", 1.0, AttributeModifier.Operation.ADDITION)
			.addAttributeModifier(ForgeMod.BLOCK_REACH.get(), "0ce2bb53-13c0-46a3-8db4-2c7702dc0edb", 1.0, AttributeModifier.Operation.ADDITION));
	public static final RegistryObject<MobEffect> FRAGRANCE = REGISTRY.register("fragrance", () -> new FragranceMobEffect());
	public static final RegistryObject<MobEffect> MEROR_WANTED_ORDER = REGISTRY.register("meror_wanted_order", () -> new MerorWantedOrderMobEffect());
	public static final RegistryObject<MobEffect> ABUNDANT_COURAGE = REGISTRY.register("abundant_courage", () -> new AbundantCourageMobEffect()
			.addAttributeModifier(Attributes.ATTACK_DAMAGE, "71972839-F515-6CC9-4988-6A9D96B99093", 3.0, AttributeModifier.Operation.ADDITION));
	public static final RegistryObject<MobEffect> BRIGHT_LAND_HEART = REGISTRY.register("bright_land_heart", () -> new BrightLandHeartMobEffect()
			.addAttributeModifier(Attributes.MAX_HEALTH, "0B63B1A4-52C9-ACD0-D808-103485145768", 10.0, AttributeModifier.Operation.ADDITION));
	public static final RegistryObject<MobEffect> LAST_DITCH = REGISTRY.register("last_ditch", () -> new LastDitchMobEffect()
			.addAttributeModifier(Attributes.MAX_HEALTH, "2893EDE1-D03A-3499-77F6-74806DDE82A7", 2.0, AttributeModifier.Operation.MULTIPLY_TOTAL));
	public static final RegistryObject<MobEffect> VIROSAANS_WIND_OF_SLAUGHTER = REGISTRY.register("virosaans_wind_of_slaughter", () -> new VirosaansWindOfSlaughterMobEffect()
			.addAttributeModifier(Attributes.MAX_HEALTH, "c309083f-b7b0-4811-8372-05d187f9fbc2", -0.4, AttributeModifier.Operation.MULTIPLY_TOTAL));
	public static final RegistryObject<MobEffect> DAZZLING_LIGHT = REGISTRY.register("dazzling_light", () -> new DazzlingLightMobEffect()
			.addAttributeModifier(ForgeMod.ENTITY_GRAVITY.get(), "5990b01d-f0dd-4a03-ab10-becd2bc76348", 0.2, AttributeModifier.Operation.ADDITION));
	public static final RegistryObject<MobEffect> ZSIEIN_FOG = REGISTRY.register("zsiein_fog", () -> new ZsieinFogMobEffect());
	public static final RegistryObject<MobEffect> ZSIEIN_PLAGUE = REGISTRY.register("zsiein_plague", () -> new ZsieinPlagueMobEffect());
}