package com.jerotes.jerotesvillage.spell;

import com.jerotes.jerotes.spell.MagicSpell;
import com.jerotes.jerotes.spell.SpellTypeInterface;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;

public enum OtherSpellType implements SpellTypeInterface {
	JEROTESVILLAGE_CORROSIVER_BREATH("jerotesvillage_corrosiver_breath"),
	JEROTESVILLAGE_SEDIMENT_MISSILE("jerotesvillage_sediment_missile"),
	JEROTESVILLAGE_VERTIGO_DELUGE("jerotesvillage_vertigo_deluge"),
	JEROTESVILLAGE_OCEAN_CURRENT_BREATH("jerotesvillage_ocean_current_breath"),
	JEROTESVILLAGE_BLOODY_SCREAM("jerotesvillage_bloody_scream"),
	JEROTESVILLAGE_VIROSAANS_WIND_OF_SLAUGHTER("jerotesvillage_virosaans_wind_of_slaughter"),
	JEROTESVILLAGE_LIGHTNING_SPIT("jerotesvillage_lightning_spit"),
	JEROTESVILLAGE_TOXIC_MIST_VOLLEY("jerotesvillage_toxic_mist_volley"),
	JEROTESVILLAGE_VINEBOLT("jerotesvillage_vinebolt"),
	JEROTESVILLAGE_PURPLE_SAND_PHANTOM("jerotesvillage_purple_sand_phantom"),
	JEROTESVILLAGE_WHIRLPOOL("jerotesvillage_whirlpool"),
	JEROTESVILLAGE_BITTER_COLD_ICE_SPIKE("jerotesvillage_bitter_cold_ice_spike"),
	JEROTESVILLAGE_CORROSIVE_ICE_SPIKE("jerotesvillage_corrosive_ice_spike"),
	JEROTESVILLAGE_SECOND_ROUNDER_ANIMATED_ARMOR("jerotesvillage_second_rounder_animated_armor"),
	JEROTESVILLAGE_SERPON_FIREBALL("jerotesvillage_serpon_fireball"),
	JEROTESVILLAGE_ELASTIC_LIGHT_BALL("jerotesvillage_elastic_light_ball"),
	JEROTESVILLAGE_SECRETE_FIRE_OIL_BOMB("jerotesvillage_secrete_fire_oil_bomb"),
	JEROTESVILLAGE_PREDATION_TOOTHED_FLOWER_SHOOT("jerotesvillage_predation_toothed_flower_shoot"),
	JEROTESVILLAGE_SMOKE_MISSILE("jerotesvillage_smoke_missile"),
	JEROTESVILLAGE_BULGE_BULLET("jerotesvillage_bulge_bullet"),
	JEROTESVILLAGE_BOTANIZE_BULLET("jerotesvillage_botanize_bullet"),
	JEROTESVILLAGE_DROUGHT_FIREBALL("jerotesvillage_drought_fireball"),
	JEROTESVILLAGE_RUINS_WIND_CHARGE("jerotesvillage_ruins_wind_charge"),
	JEROTESVILLAGE_RADIANT_BOMB("jerotesvillage_radiant_bomb"),
	JEROTESVILLAGE_ARCANE_LIGHT_SPOT("jerotesvillage_arcane_light_spot"),
	JEROTESVILLAGE_SERPON_SIROCCO("jerotesvillage_serpon_sirocco"),
	JEROTESVILLAGE_EYE_DISC_SHOOT("jerotesvillage_eye_disc_shoot"),
	JEROTESVILLAGE_ICE_ROCK_THROW("jerotesvillage_ice_rock_throw"),
	JEROTESVILLAGE_ASTRAL_JAVELIN("jerotesvillage_astral_javelin"),
	JEROTESVILLAGE_BITTER_COLD_FROSTBITE("jerotesvillage_bitter_cold_frostbite"),
	JEROTESVILLAGE_SERPON_FLAMES("jerotesvillage_serpon_flames"),
	JEROTESVILLAGE_VIRTUAL_CAVE_SURGE_SIPHON("jerotesvillage_virtual_cave_surge_siphon"),
	JEROTESVILLAGE_OMINOUS_FLAMES("jerotesvillage_ominous_flames"),
	JEROTESVILLAGE_WORM_THUNDERSTORM("jerotesvillage_worm_thunderstorm"),
	JEROTESVILLAGE_PUSH_FORCE("jerotesvillage_push_force"),
	JEROTESVILLAGE_UNEASY_UNCERTAINTY("jerotesvillage_uneasy_uncertainty"),
	JEROTESVILLAGE_ASPHYXIA_CONTROL("jerotesvillage_asphyxia_control"),
	JEROTESVILLAGE_OCEAN_DRAGON_DETERRENCE("jerotesvillage_ocean_dragon_deterrence"),
	JEROTESVILLAGE_GEMSTONE_WAVES("jerotesvillage_gemstone_waves"),
	JEROTESVILLAGE_ELECTROFLASH("jerotesvillage_electroflash"),
	JEROTESVILLAGE_SKYLANCE_DRAGON_BEAM("jerotesvillage_skylance_dragon_beam"),
	JEROTESVILLAGE_INDOMITABLE_WILL("jerotesvillage_indomitable_will"),
	JEROTESVILLAGE_FLOATING_FORCE("jerotesvillage_floating_force"),
	JEROTESVILLAGE_GRAVITY_FORCE("jerotesvillage_gravity_force"),
	JEROTESVILLAGE_CORROSIVE_SNOW_CLOUD("jerotesvillage_corrosive_snow_cloud"),
	JEROTESVILLAGE_UNCLEAN_BLOOD_RAIN("jerotesvillage_unclean_blood_rain"),
	JEROTESVILLAGE_UNCLEAN_BLOOD_FOG("jerotesvillage_unclean_blood_fog"),
	JEROTESVILLAGE_WARLOCK_WHISPER("jerotesvillage_warlock_whisper"),
	JEROTESVILLAGE_YETI_DETER("jerotesvillage_yeti_deter"),
	JEROTESVILLAGE_SERPENPERTH_METEOR("jerotesvillage_serpenperth_meteor"),
	JEROTESVILLAGE_OMINOUS_GEAR("jerotesvillage_ominous_gear"),
	JEROTESVILLAGE_EVIL_SUMMONING("jerotesvillage_evil_summoning"),
	JEROTESVILLAGE_COGON_SWORD("jerotesvillage_cogon_sword"),
	JEROTESVILLAGE_IMPRISON_VINE("jerotesvillage_imprison_vine"),
	JEROTESVILLAGE_THORN_SPIKE("jerotesvillage_thorn_spike"),
	JEROTESVILLAGE_BITTER_COLD_ALTAR("jerotesvillage_bitter_cold_altar"),
	JEROTESVILLAGE_CONJURE_COILVINE_BOTANIZES("jerotesvillage_conjure_coilvine_botanizes"),
	JEROTESVILLAGE_VINE_POD_BOMB("jerotesvillage_vine_pod_bomb"),
	JEROTESVILLAGE_MALIGNASAUR_BOMB("jerotesvillage_malignasaur_bomb"),
	JEROTESVILLAGE_CHAOTIC_DISCHARGE("jerotesvillage_chaotic_discharge"),
	JEROTESVILLAGE_TORCH_GAZE("jerotesvillage_torch_gaze"),
	JEROTESVILLAGE_COLLAPSE_ZONE("jerotesvillage_collapse_zone"),
	JEROTESVILLAGE_CONJURE_IRON_TEETH("jerotesvillage_conjure_iron_teeth"),
	JEROTESVILLAGE_CONJURE_FLYING_FIREBALL("jerotesvillage_conjure_flying_fireball"),
	JEROTESVILLAGE_DAZZLING_LIGHT_FOG("jerotesvillage_dazzling_light_fog"),
	JEROTESVILLAGE_UNDEAD_BANISHING("jerotesvillage_undead_banishing"),
	JEROTESVILLAGE_SPRING_REJUVENATION("jerotesvillage_spring_rejuvenation"),
	JEROTESVILLAGE_VINEHEART_BOMB("jerotesvillage_vineheart_bomb"),
	JEROTESVILLAGE_WILT_MISSILE("jerotesvillage_wilt_missile"),
	JEROTESVILLAGE_CONJURE_BULGES("jerotesvillage_conjure_bulges"),
	JEROTESVILLAGE_CONJURE_SEAWAVE("jerotesvillage_conjure_seawave"),
	JEROTESVILLAGE_CONJURE_DROUGHT_BLAZES("jerotesvillage_conjure_drought_blazes"),
	JEROTESVILLAGE_CONJURE_DROUGHT_WILDFIRE("jerotesvillage_conjure_drought_wildfire"),
	JEROTESVILLAGE_CALL_THE_DARISORDER("jerotesvillage_call_the_darisorder"),
	JEROTESVILLAGE_VIROSAANS_MANA_SURGE("jerotesvillage_virosaans_mana_surge"),
	JEROTESVILLAGE_VIROSAANS_FORCE_BURST_BALL("jerotesvillage_virosaans_force_burst_ball"),
	JEROTESVILLAGE_TAKE_ROOT("jerotesvillage_take_root"),
	JEROTESVILLAGE_DEAD_CENTER("jerotesvillage_dead_center"),
	JEROTESVILLAGE_SNAP_BOLT("jerotesvillage_snap_bolt"),
	JEROTESVILLAGE_VIROSAANS_MAGIC_MISSILE("jerotesvillage_virosaans_magic_missile"),
	JEROTESVILLAGE_STARBURST_MARKER("jerotesvillage_starburst_marker"),
	JEROTESVILLAGE_WHIP_THE_TIDE("jerotesvillage_whip_the_tide");

	private final String id;

	private OtherSpellType(String id) {
		this.id = id;
	}

	public String getId() {
		return this.id;
	}

	public MagicSpell magicSpellGet(int level, LivingEntity caster, Entity target) {
		return switch (this) {
			case JEROTESVILLAGE_CORROSIVER_BREATH -> OtherSpellList.CorrosiverBreath(level, caster, target);
			case JEROTESVILLAGE_SEDIMENT_MISSILE -> OtherSpellList.SedimentMissile(level, caster, target);
			case JEROTESVILLAGE_VERTIGO_DELUGE -> OtherSpellList.VertigoDeluge(level, caster, target);
			case JEROTESVILLAGE_OCEAN_CURRENT_BREATH -> OtherSpellList.OceanCurrentBreath(level, caster, target);
			case JEROTESVILLAGE_BLOODY_SCREAM -> OtherSpellList.BloodyScream(level, caster, target);
			case JEROTESVILLAGE_VIROSAANS_WIND_OF_SLAUGHTER -> OtherSpellList.VirosaansWindOfSlaughter(level, caster, target);
			case JEROTESVILLAGE_LIGHTNING_SPIT -> OtherSpellList.LightningSpit(level, caster, target);
			case JEROTESVILLAGE_TOXIC_MIST_VOLLEY -> OtherSpellList.ToxicMistVolley(level, caster, target);
			case JEROTESVILLAGE_VINEBOLT -> OtherSpellList.Vinebolt(level, caster, target);
			case JEROTESVILLAGE_PURPLE_SAND_PHANTOM -> OtherSpellList.PurpleSandPhantom(level, caster, target);
			case JEROTESVILLAGE_WHIRLPOOL -> OtherSpellList.Whirlpool(level, caster, target);
			case JEROTESVILLAGE_BITTER_COLD_ICE_SPIKE -> OtherSpellList.BitterColdIceSpike(level, caster, target);
			case JEROTESVILLAGE_CORROSIVE_ICE_SPIKE -> OtherSpellList.CorrosiveIceSpike(level, caster, target);
			case JEROTESVILLAGE_SECOND_ROUNDER_ANIMATED_ARMOR -> OtherSpellList.SecondRounderAnimatedArmor(level, caster, target);
			case JEROTESVILLAGE_SERPON_FIREBALL -> OtherSpellList.SerponFireball(level, caster, target);
			case JEROTESVILLAGE_ELASTIC_LIGHT_BALL -> OtherSpellList.ElasticLightBall(level, caster, target);
			case JEROTESVILLAGE_SECRETE_FIRE_OIL_BOMB -> OtherSpellList.SecreteFireOilBomb(level, caster, target);
			case JEROTESVILLAGE_PREDATION_TOOTHED_FLOWER_SHOOT -> OtherSpellList.PredationToothedFlowerShoot(level, caster, target);
			case JEROTESVILLAGE_SMOKE_MISSILE -> OtherSpellList.SmokeMissile(level, caster, target);
			case JEROTESVILLAGE_BULGE_BULLET -> OtherSpellList.BulgeBullet(level, caster, target);
			case JEROTESVILLAGE_BOTANIZE_BULLET -> OtherSpellList.BotanizeBullet(level, caster, target);
			case JEROTESVILLAGE_DROUGHT_FIREBALL -> OtherSpellList.DroughtFireball(level, caster, target);
			case JEROTESVILLAGE_RUINS_WIND_CHARGE -> OtherSpellList.RuinsWindCharge(level, caster, target);
			case JEROTESVILLAGE_RADIANT_BOMB -> OtherSpellList.RadiantBomb(level, caster, target);
			case JEROTESVILLAGE_ARCANE_LIGHT_SPOT -> OtherSpellList.ArcaneLightSpot(level, caster, target);
			case JEROTESVILLAGE_SERPON_SIROCCO -> OtherSpellList.SerponSirocco(level, caster, target);
			case JEROTESVILLAGE_EYE_DISC_SHOOT -> OtherSpellList.EyeDiscShoot(level, caster, target);
			case JEROTESVILLAGE_ICE_ROCK_THROW -> OtherSpellList.IceRockThrow(level, caster, target);
			case JEROTESVILLAGE_ASTRAL_JAVELIN -> OtherSpellList.AstralJavelin(level, caster, target);
			case JEROTESVILLAGE_BITTER_COLD_FROSTBITE -> OtherSpellList.BitterColdFrostbite(level, caster, target);
			case JEROTESVILLAGE_SERPON_FLAMES -> OtherSpellList.SerponFlames(level, caster, target);
			case JEROTESVILLAGE_VIRTUAL_CAVE_SURGE_SIPHON -> OtherSpellList.VirtualCaveSurgeSiphon(level, caster, target);
			case JEROTESVILLAGE_OMINOUS_FLAMES -> OtherSpellList.OminousFlames(level, caster, target);
			case JEROTESVILLAGE_WORM_THUNDERSTORM -> OtherSpellList.WormThunderstorm(level, caster, target);
			case JEROTESVILLAGE_PUSH_FORCE -> OtherSpellList.PushForce(level, caster, target);
			case JEROTESVILLAGE_UNEASY_UNCERTAINTY -> OtherSpellList.UneasyUncertainty(level, caster, target);
			case JEROTESVILLAGE_ASPHYXIA_CONTROL -> OtherSpellList.AsphyxiaControl(level, caster, target);
			case JEROTESVILLAGE_OCEAN_DRAGON_DETERRENCE -> OtherSpellList.OceanDragonDeterrence(level, caster, target);
			case JEROTESVILLAGE_GEMSTONE_WAVES -> OtherSpellList.GemstoneWaves(level, caster, target);
			case JEROTESVILLAGE_ELECTROFLASH -> OtherSpellList.Electroflash(level, caster, target);
			case JEROTESVILLAGE_SKYLANCE_DRAGON_BEAM -> OtherSpellList.SkylanceDragonBeam(level, caster, target);
			case JEROTESVILLAGE_INDOMITABLE_WILL -> OtherSpellList.IndomitableWill(level, caster, target);
			case JEROTESVILLAGE_FLOATING_FORCE -> OtherSpellList.FloatingForce(level, caster, target);
			case JEROTESVILLAGE_GRAVITY_FORCE -> OtherSpellList.GravityForce(level, caster, target);
			case JEROTESVILLAGE_CORROSIVE_SNOW_CLOUD -> OtherSpellList.CorrosiveSnowCloud(level, caster, target);
			case JEROTESVILLAGE_UNCLEAN_BLOOD_RAIN -> OtherSpellList.UncleanBloodRain(level, caster, target);
			case JEROTESVILLAGE_UNCLEAN_BLOOD_FOG -> OtherSpellList.UncleanBloodFog(level, caster, target);
			case JEROTESVILLAGE_WARLOCK_WHISPER -> OtherSpellList.WarlockWhisper(level, caster, target);
			case JEROTESVILLAGE_YETI_DETER -> OtherSpellList.YetiDeter(level, caster, target);
			case JEROTESVILLAGE_SERPENPERTH_METEOR -> OtherSpellList.SerpenperthMeteor(level, caster, target);
			case JEROTESVILLAGE_OMINOUS_GEAR -> OtherSpellList.OminousGear(level, caster, target);
			case JEROTESVILLAGE_EVIL_SUMMONING -> OtherSpellList.EvilSummoning(level, caster, target);
			case JEROTESVILLAGE_COGON_SWORD -> OtherSpellList.CogonSword(level, caster, target);
			case JEROTESVILLAGE_IMPRISON_VINE -> OtherSpellList.ImprisonVine(level, caster, target);
			case JEROTESVILLAGE_THORN_SPIKE -> OtherSpellList.ThornSpike(level, caster, target);
			case JEROTESVILLAGE_BITTER_COLD_ALTAR -> OtherSpellList.BitterColdAltar(level, caster, target);
			case JEROTESVILLAGE_CONJURE_COILVINE_BOTANIZES -> OtherSpellList.ConjureCoilvineBotanizes(level, caster, target);
			case JEROTESVILLAGE_VINE_POD_BOMB -> OtherSpellList.VinePodBomb(level, caster, target);
			case JEROTESVILLAGE_MALIGNASAUR_BOMB -> OtherSpellList.MalignasaurBomb(level, caster, target);
			case JEROTESVILLAGE_CHAOTIC_DISCHARGE -> OtherSpellList.ChaoticDischarge(level, caster, target);
			case JEROTESVILLAGE_TORCH_GAZE -> OtherSpellList.TorchGaze(level, caster, target);
			case JEROTESVILLAGE_COLLAPSE_ZONE -> OtherSpellList.CollapseZone(level, caster, target);
			case JEROTESVILLAGE_CONJURE_IRON_TEETH -> OtherSpellList.ConjureIronTeeth(level, caster, target);
			case JEROTESVILLAGE_CONJURE_FLYING_FIREBALL -> OtherSpellList.ConjureFlyingFireball(level, caster, target);
			case JEROTESVILLAGE_DAZZLING_LIGHT_FOG -> OtherSpellList.DazzlingLightFog(level, caster, target);
			case JEROTESVILLAGE_UNDEAD_BANISHING -> OtherSpellList.UndeadBanishing(level, caster, target);
			case JEROTESVILLAGE_SPRING_REJUVENATION -> OtherSpellList.SpringRejuvenation(level, caster, target);
			case JEROTESVILLAGE_WILT_MISSILE -> OtherSpellList.WiltMissile(level, caster, target);
			case JEROTESVILLAGE_VINEHEART_BOMB -> OtherSpellList.VineheartBomb(level, caster, target);
			case JEROTESVILLAGE_CONJURE_BULGES -> OtherSpellList.ConjureBulges(level, caster, target);
			case JEROTESVILLAGE_CONJURE_SEAWAVE -> OtherSpellList.ConjureSeawave(level, caster, target);
			case JEROTESVILLAGE_CONJURE_DROUGHT_BLAZES -> OtherSpellList.ConjureDroughtBlazes(level, caster, target);
			case JEROTESVILLAGE_CONJURE_DROUGHT_WILDFIRE -> OtherSpellList.ConjureDroughtWildfire(level, caster, target);
			case JEROTESVILLAGE_CALL_THE_DARISORDER -> OtherSpellList.CallTheDarisorder(level, caster, target);
			case JEROTESVILLAGE_VIROSAANS_MANA_SURGE -> OtherSpellList.VirosaansManaSurge(level, caster, target);
			case JEROTESVILLAGE_VIROSAANS_FORCE_BURST_BALL -> OtherSpellList.VirosaansForceBurstBall(level, caster, target);
			case JEROTESVILLAGE_TAKE_ROOT -> OtherSpellList.TakeRoot(level, caster, target);
			case JEROTESVILLAGE_DEAD_CENTER -> OtherSpellList.DeadCenter(level, caster, target);
			case JEROTESVILLAGE_SNAP_BOLT -> OtherSpellList.SnapBolt(level, caster, target);
			case JEROTESVILLAGE_VIROSAANS_MAGIC_MISSILE -> OtherSpellList.VirosaansMagicMissile(level, caster, target);
			case JEROTESVILLAGE_STARBURST_MARKER -> OtherSpellList.StarburstMarker(level, caster, target);
			case JEROTESVILLAGE_WHIP_THE_TIDE -> OtherSpellList.WhipTheTide(level, caster, target);
		};
	}

	public void stop(LivingEntity caster, int level, boolean must) {
		stops(caster, level, must);
	}
	public static void stops(LivingEntity caster, int level, boolean must) {
	}
}