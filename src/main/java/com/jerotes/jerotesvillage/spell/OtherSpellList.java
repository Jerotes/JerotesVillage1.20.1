package com.jerotes.jerotesvillage.spell;

import com.jerotes.jerotes.spell.MagicSpell;
import com.jerotes.jerotes.spell.MagicType;
import com.jerotes.jerotes.spell.SpellSchool;
import com.jerotes.jerotes.util.Main;
import com.jerotes.jerotesvillage.JerotesVillage;
import com.jerotes.jerotesvillage.entity.Boss.Biome.GemstoneMalignasaurEntity;
import com.jerotes.jerotesvillage.entity.MagicSummoned.Botanize.CoilvineBotanizeEntity;
import com.jerotes.jerotesvillage.entity.MagicSummoned.Bulge.BulgeEntity;
import com.jerotes.jerotesvillage.entity.MagicSummoned.Bulge.SeawaveEntity;
import com.jerotes.jerotesvillage.entity.MagicSummoned.Darisorder.DarisorderEntity;
import com.jerotes.jerotesvillage.entity.MagicSummoned.DroughtBlaze.DroughtBlazeEntity;
import com.jerotes.jerotesvillage.entity.MagicSummoned.DroughtBlaze.DroughtWildfireEntity;
import com.jerotes.jerotesvillage.entity.MagicSummoned.FlyingFireball.FlyingFireballEntity;
import com.jerotes.jerotesvillage.entity.MagicSummoned.IronTeeth.IronTeethEntity;
import com.jerotes.jerotesvillage.entity.MagicSummoned.VinePodBomb.VinePodBombEntity;
import com.jerotes.jerotesvillage.entity.Other.MagicAboutMob.*;
import com.jerotes.jerotesvillage.init.JerotesVillageParticleTypes;
import com.jerotes.jerotesvillage.init.JerotesVillageSoundEvents;
import com.jerotes.jerotesvillage.item.DroughtWildfireShield;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.decoration.ArmorStand;
import net.minecraft.world.entity.player.Player;

import java.util.List;

public class OtherSpellList {
	//蚀龙兽吐息
	public static MagicSpell CorrosiverBreath(int n, LivingEntity caster, Entity target) {
		return new MagicSpell(n, caster, target, MagicType.SHOOT, MagicType.MAIN, "corrosiver_breath", JerotesVillageParticleTypes.CORROSIVER_BREATH_DISPLAY.get(), JerotesVillageSoundEvents.MAGIC_CORROSIVER_BREATH){
			public boolean spellFindUse() {
				return OtherSpellFind.CorrosiverBreath(getCaster(), getTarget(), getSpellLevel(), getSpellLevel(), getSpellLevel(), getSpellLevel(), getSpellLevel(), getSpellAccuracy(), getSpellLevel(), 5, getCaster() instanceof Player, getSubcategory());
			}
			public String getSpellModId() {
				return JerotesVillage.MODID;
			}
			public int getBaseSpellLevel() {
				return 3;
			}
			public SpellSchool getSpellSchool() {
				return SpellSchool.EVOCATION;
			}
			public float getSpellDistanceBase() {
				return 27;
			}
			//
			public int getCustomSubcategoryReachAdd() {
				return 1;
			}
			public int usedByNonPlayerFindSubcategory(LivingEntity livingEntity) {
				return 0;
			}
			//
		};
	}
	//沉降飞弹
	public static MagicSpell SedimentMissile(int n, LivingEntity caster, Entity target) {
		return new MagicSpell(n, caster, target, MagicType.TARGET, MagicType.MAIN, "sediment_missile", JerotesVillageParticleTypes.SEDIMENT_MISSILE_DISPLAY.get(), JerotesVillageSoundEvents.MAGIC_SEDIMENT_MISSILE){
			public boolean spellFindUse() {
				return OtherSpellFind.SedimentMissile(getCaster(), getTarget(), getSpellLevel(), getSpellAccuracy(), 1, 1, 3 + getSpellLevel(), getCaster() instanceof Player);
			}
			public int getCastingTicks() {
				return (1 + getSpellLevel()) * 5 + 4;
			}
			public boolean CastingAboutBase(int n) {
				if (n % 5 == 0 && n != 0) {
					getCaster().swing(InteractionHand.MAIN_HAND, true);
					Entity entity = getTarget();
					if (getCaster() instanceof Player player && Main.getTargetedEntity(player, getSpellDistance()) != null && canUseToEntity(Main.getTargetedEntity(player, getSpellDistance()))) {
						entity = Main.getTargetedEntity(player, getSpellDistance());
					}
					if (!(entity != null && entity.isAlive())) {
						entity = getCaster();
					}
					OtherSpellFind.SedimentMissile(getCaster(), (entity instanceof LivingEntity livingEntity) ? livingEntity : getCaster(), getSpellLevel(), getSpellAccuracy(), 1, 1, 1, getCaster() instanceof Player);
					if (!getCaster().isSilent()) {
						getCaster().level().playSound(null, getCaster().getX(), getCaster().getY(), getCaster().getZ(), getSound(),
								getCaster().getSoundSource(), getSoundLevel(), 1.0F);
					}
				}
				return true;
			}
			public String getSpellModId() {
				return JerotesVillage.MODID;
			}
			public boolean canUseTargetNone() {
				return true;
			}
			public int getBaseSpellLevel() {
				return 1;
			}
			public float getSpellDistanceBase() {
				return 36;
			}
			public SpellSchool getSpellSchool() {
				return SpellSchool.EVOCATION;
			}
		};
	}
	//眼花缭乱
	public static MagicSpell VertigoDeluge(int n, LivingEntity caster, Entity target) {
		return new MagicSpell(n, caster, target, MagicType.TARGET, MagicType.MAIN, "vertigo_deluge", JerotesVillageParticleTypes.VERTIGO_DELUGE_DISPLAY.get(), JerotesVillageSoundEvents.MAGIC_VERTIGO_DELUGE){
			public boolean spellFindUse() {
				return OtherSpellFind.VertigoDeluge(getCaster(), getTarget(), getSpellLevel(), 6 + (getSpellLevel()) * 3, 1, getSpellAccuracy(), 1, 1, 1, getCaster() instanceof Player);
			}
			public int getCastingTicks() {
				return 19 * 2 + 1;
			}
			public boolean CastingAboutBase(int n) {
				if (n % 2 == 0 && n != 0) {
					getCaster().swing(InteractionHand.MAIN_HAND, true);
					Entity entity = getTarget();
					if (getCaster() instanceof Player player && Main.getTargetedEntity(player, getSpellDistance()) != null && canUseToEntity(Main.getTargetedEntity(player, getSpellDistance()))) {
						entity = Main.getTargetedEntity(player, getSpellDistance());
					}
					if (!(entity != null && entity.isAlive())) {
						entity = getCaster();
					}
					OtherSpellFind.VertigoDeluge(getCaster(), (entity instanceof LivingEntity livingEntity) ? livingEntity : getCaster(), getSpellLevel(), 6 + (getSpellLevel()) * 3, 1, getSpellAccuracy(), 1, 1, 1, getCaster() instanceof Player);
					if (!getCaster().isSilent()) {
						getCaster().level().playSound(null, getCaster().getX(), getCaster().getY(), getCaster().getZ(), getSound(),
								getCaster().getSoundSource(), getSoundLevel(), 1.0F);
					}
				}
				return true;
			}
			public String getSpellModId() {
				return JerotesVillage.MODID;
			}
			public boolean canUseTargetNone() {
				return true;
			}
			public int getBaseSpellLevel() {
				return 6;
			}
			public float getSpellDistanceBase() {
				return 60;
			}
			public SpellSchool getSpellSchool() {
				return SpellSchool.EVOCATION;
			}
		};
	}
	//洋流吐息
	public static MagicSpell OceanCurrentBreath(int n, LivingEntity caster, Entity target) {
		return new MagicSpell(n, caster, target, MagicType.SHOOT, MagicType.MAIN, "ocean_current_breath", JerotesVillageParticleTypes.OCEAN_CURRENT_BREATH_DISPLAY.get(), JerotesVillageSoundEvents.MAGIC_OCEAN_CURRENT_BREATH){
			public boolean spellFindUse() {
				return OtherSpellFind.OceanCurrentBreath(getCaster(), getTarget(), (getCaster() instanceof GemstoneMalignasaurEntity gemstoneMalignasaur && gemstoneMalignasaur.head1 != null) ? gemstoneMalignasaur.head1 : getCaster(), getSpellLevel(), getSpellLevel() * 3, getSpellLevel() - 1, getSpellAccuracy(), getSpellLevel(), 5, getCaster() instanceof Player, getSubcategory());
			}
			public String getSpellModId() {
				return JerotesVillage.MODID;
			}
			public int getBaseSpellLevel() {
				return 3;
			}
			public SpellSchool getSpellSchool() {
				return SpellSchool.EVOCATION;
			}
			//
			public int getCustomSubcategoryReachAdd() {
				return 1;
			}
			public int usedByNonPlayerFindSubcategory(LivingEntity livingEntity) {
				return 0;
			}
			//
		};
	}
	//血腥尖啸
	public static MagicSpell BloodyScream(int n, LivingEntity caster, Entity target) {
		return new MagicSpell(n, caster, target, MagicType.SHOOT, MagicType.MAIN, "bloody_scream", JerotesVillageParticleTypes.BLOODY_SCREAM_DISPLAY.get(), JerotesVillageSoundEvents.MAGIC_BLOODY_SCREAM){
			public boolean spellFindUse() {
				return OtherSpellFind.BloodyScream(getCaster(), getTarget(), getSpellLevel(), 6 + (getSpellLevel() - 1) * 3, (int)Math.ceil((getSpellLevel()) / 2.0), getSpellAccuracy(), getSpellLevel() + 1, 5, getCaster() instanceof Player, getSubcategory());
			}
			public String getSpellModId() {
				return JerotesVillage.MODID;
			}
			public int getBaseSpellLevel() {
				return 3;
			}
			public SpellSchool getSpellSchool() {
				return SpellSchool.NECROMANCY;
			}
			//
			public int getCustomSubcategoryReachAdd() {
				return 1;
			}
			public int usedByNonPlayerFindSubcategory(LivingEntity livingEntity) {
				return 0;
			}
			//
		};
	}
	//维落萨恩的杀戮之风
	public static MagicSpell VirosaansWindOfSlaughter(int n, LivingEntity caster, Entity target) {
		return new MagicSpell(n, caster, target, MagicType.SHOOT, MagicType.MAIN, "virosaans_wind_of_slaughter", JerotesVillageParticleTypes.VIROSAANS_WIND_OF_SLAUGHTER_DISPLAY.get(), JerotesVillageSoundEvents.MAGIC_VIROSAANS_WIND_OF_SLAUGHTER){
			public boolean spellFindUse() {
				return OtherSpellFind.VirosaansWindOfSlaughter(getCaster(), getTarget(), getSpellLevel(), getCaster() instanceof Player);
			}
			public String getSpellModId() {
				return JerotesVillage.MODID;
			}
			public int getBaseSpellLevel() {
				return 9;
			}
			public SpellSchool getSpellSchool() {
				return SpellSchool.NECROMANCY;
			}
			public float getSpellDistanceBase() {
				return 60;
			}
		};
	}
	//喷吐雷液
	public static MagicSpell LightningSpit(int n, LivingEntity caster, Entity target) {
		return new MagicSpell(n, caster, target, MagicType.SHOOT, MagicType.MAIN, "lightning_spit", JerotesVillageParticleTypes.LIGHTNING_SPIT_DISPLAY.get(), JerotesVillageSoundEvents.MAGIC_LIGHTNING_SPIT){
			public boolean spellFindUse() {
				return OtherSpellFind.LightningSpit(getCaster(), getTarget(), 0, getSpellLevel(), getSpellLevel(), getSpellLevel(), getSpellLevel(), getSpellLevel(), getSpellAccuracy(), 1, 5, getCaster() instanceof Player);
			}
			public String getSpellModId() {
				return JerotesVillage.MODID;
			}
			public int getBaseSpellLevel() {
				return 2;
			}
			public SpellSchool getSpellSchool() {
				return SpellSchool.EVOCATION;
			}
			public float getSpellDistanceBase() {
				return 18;
			}
		};
	}
	public static MagicSpell LightningSpitAdd(int n, float type, LivingEntity caster, Entity target) {
		return new MagicSpell(n, caster, target, MagicType.SHOOT, MagicType.MAIN, "lightning_spit", JerotesVillageParticleTypes.LIGHTNING_SPIT_DISPLAY.get(), JerotesVillageSoundEvents.MAGIC_LIGHTNING_SPIT){
			public boolean spellFindUse() {
				return OtherSpellFind.LightningSpit(getCaster(), getTarget(), type, getSpellLevel(), getSpellLevel(), getSpellLevel(), getSpellLevel(), getSpellLevel(), getSpellAccuracy(), 1, 5, getCaster() instanceof Player);
			}
			public String getSpellModId() {
				return JerotesVillage.MODID;
			}
			public int getBaseSpellLevel() {
				return 2;
			}
			public SpellSchool getSpellSchool() {
				return SpellSchool.EVOCATION;
			}
			public float getSpellDistanceBase() {
				return 18;
			}
		};
	}
	//云蒸雾集
	public static MagicSpell ToxicMistVolley(int n, LivingEntity caster, Entity target) {
		return new MagicSpell(n, caster, target, MagicType.SHOOT, MagicType.MAIN, "toxic_mist_volley", JerotesVillageParticleTypes.TOXIC_MIST_VOLLEY_DISPLAY.get(), JerotesVillageSoundEvents.MAGIC_TOXIC_MIST_VOLLEY){
			public boolean spellFindUse() {
				return OtherSpellFind.ToxicMistVolley(getCaster(), getTarget(), getSpellLevel(), 6 + (getSpellLevel() - 1) * 3, (int)Math.ceil((getSpellLevel()) / 2.0), getSpellAccuracy(), getSpellLevel() + 1, 5, getCaster() instanceof Player);
			}
			public String getSpellModId() {
				return JerotesVillage.MODID;
			}
			public int getBaseSpellLevel() {
				return 3;
			}
			public SpellSchool getSpellSchool() {
				return SpellSchool.EVOCATION;
			}
			public float getSpellDistanceBase() {
				return 36;
			}
		};
	}
	//离弦之箭
	public static MagicSpell Vinebolt(int n, LivingEntity caster, Entity target) {
		return new MagicSpell(n, caster, target, MagicType.SHOOT, MagicType.MAIN, "vinebolt", JerotesVillageParticleTypes.VINEBOLT_DISPLAY.get(), JerotesVillageSoundEvents.MAGIC_VINEBOLT){
			public boolean spellFindUse() {
				return OtherSpellFind.Vinebolt(getCaster(), getTarget(), getSpellLevel(), 2.25f, getSpellAccuracy(), 1 + (getSpellLevel() - 1) * 2, 5, getCaster() instanceof Player);
			}
			public String getSpellModId() {
				return JerotesVillage.MODID;
			}
			public int getBaseSpellLevel() {
				return 5;
			}
			public SpellSchool getSpellSchool() {
				return SpellSchool.CONJURATION;
			}
			public float getSpellDistanceBase() {
				return 60;
			}
		};
	}
	//紫沙幻影
	public static MagicSpell PurpleSandPhantom(int n, LivingEntity caster, Entity target) {
		return new MagicSpell(n, caster, target, MagicType.TARGET, MagicType.MAIN, "purple_sand_phantom", JerotesVillageParticleTypes.PURPLE_SAND_PHANTOM_DISPLAY.get(), JerotesVillageSoundEvents.MAGIC_PURPLE_SAND_PHANTOM){
			public boolean spellFindUse() {
				return OtherSpellFind.PurpleSandPhantom(getCaster(), getTarget(), getSpellLevel() * 2, getSpellLevel() * 3, 12, getSpellLevel());
			}
			public int getBaseSpellLevel() {
				return 4;
			}
			public float getSpellDistanceBase() {
				return 32;
			}
			public String getSpellModId() {
				return JerotesVillage.MODID;
			}
			public boolean canUse() {
				if (getCaster() != null) {
					List<PurpleSandPhantomEntity> list = getCaster().level().getEntitiesOfClass(PurpleSandPhantomEntity.class, getCaster().getBoundingBox().inflate(128, 128, 128));
					list.removeIf(purpleSandPhantomEntity -> purpleSandPhantomEntity.getOwner() != getCaster());
					return super.canUse() && list.size() <= getSpellLevel() * 4;
				}
				return super.canUse();
			}
			public boolean canUseTargetNone() {
				return true;
			}
			public SpellSchool getSpellSchool() {
				return SpellSchool.ILLUSION;
			}
		};
	}
	//漩涡
	public static MagicSpell Whirlpool(int n, LivingEntity caster, Entity target) {
		return new MagicSpell(n, caster, target, MagicType.SHOOT, MagicType.MAIN, "whirlpool", JerotesVillageParticleTypes.WHIRLPOOL_DISPLAY.get(), JerotesVillageSoundEvents.MAGIC_WHIRLPOOL){
			public boolean spellFindUse() {
				return OtherSpellFind.Whirlpool(getCaster(), getTarget(), (getCaster() instanceof GemstoneMalignasaurEntity gemstoneMalignasaur && gemstoneMalignasaur.head1 != null) ? gemstoneMalignasaur.head1 : getCaster(), getSpellLevel(), getSpellLevel(), getSpellAccuracy(), getSpellLevel(), 10, getCaster() instanceof Player);
			}
			public String getSpellModId() {
				return JerotesVillage.MODID;
			}
			public int getBaseSpellLevel() {
				return 3;
			}
			public SpellSchool getSpellSchool() {
				return SpellSchool.CONJURATION;
			}
		};
	}
	//二轮活化盔甲
	public static MagicSpell SecondRounderAnimatedArmor(int n, LivingEntity caster, Entity target) {
		return new MagicSpell(n, caster, target, MagicType.TARGET, MagicType.MAIN, "second_rounder_animated_armor", JerotesVillageParticleTypes.SECOND_ROUNDER_ANIMATED_ARMOR_DISPLAY.get(), JerotesVillageSoundEvents.MAGIC_SECOND_ROUNDER_ANIMATED_ARMOR){
			public boolean spellFindUse() {
				return OtherSpellFind.SecondRounderAnimatedArmor(getCaster(), (getTarget() instanceof LivingEntity livingEntity) ? livingEntity : null);
			}
			public String getSpellModId() {
				return JerotesVillage.MODID;
			}
			public int getBaseSpellLevel() {
				return 5;
			}
			public float getSpellDistanceBase() {
				return 6;
			}
			public boolean canUseToEntity(Entity entity) {
				boolean helmet = entity instanceof ArmorStand armorStand && armorStand.getItemBySlot(EquipmentSlot.HEAD).isEmpty();
				boolean chestplate = entity instanceof ArmorStand armorStand && armorStand.getItemBySlot(EquipmentSlot.CHEST).isEmpty();
				boolean leggings = entity instanceof ArmorStand armorStand && armorStand.getItemBySlot(EquipmentSlot.LEGS).isEmpty();
				boolean boots = entity instanceof ArmorStand armorStand && armorStand.getItemBySlot(EquipmentSlot.FEET).isEmpty();
				return entity instanceof ArmorStand && !(helmet && chestplate && leggings && boots);
			}
			public SpellSchool getSpellSchool() {
				return SpellSchool.TRANSMUTATION;
			}
		};
	}
	//苦寒冰锥
	public static MagicSpell BitterColdIceSpike(int n, LivingEntity caster, Entity target) {
		return new MagicSpell(n, caster, target, MagicType.SHOOT, MagicType.MAIN, "bitter_cold_ice_spike", JerotesVillageParticleTypes.BITTER_COLD_ICE_SPIKE_DISPLAY.get(), JerotesVillageSoundEvents.MAGIC_BITTER_COLD_ICE_SPIKE){
			public boolean spellFindUse() {
				return OtherSpellFind.BitterColdIceSpike(getCaster(), getTarget(), getSpellLevel(), 6 + (getSpellLevel() - 1) * 3, 2.25f, getSpellAccuracy(), 1, 5, getCaster() instanceof Player);
			}
			public String getSpellModId() {
				return JerotesVillage.MODID;
			}
			public int getBaseSpellLevel() {
				return 2;
			}
			public SpellSchool getSpellSchool() {
				return SpellSchool.EVOCATION;
			}
		};
	}
	//腐蚀冰锥
	public static MagicSpell CorrosiveIceSpike(int n, LivingEntity caster, Entity target) {
		return new MagicSpell(n, caster, target, MagicType.SHOOT, MagicType.MAIN, "corrosive_ice_spike", JerotesVillageParticleTypes.CORROSIVE_ICE_SPIKE_DISPLAY.get(), JerotesVillageSoundEvents.MAGIC_CORROSIVE_ICE_SPIKE){
			public boolean spellFindUse() {
				return OtherSpellFind.CorrosiveIceSpike(getCaster(), getTarget(), getSpellLevel(), 6 + (getSpellLevel() - 1) * 3, 1.5f, getSpellAccuracy(), getSpellLevel(), 5, getCaster() instanceof Player);
			}
			public String getSpellModId() {
				return JerotesVillage.MODID;
			}
			public int getBaseSpellLevel() {
				return 3;
			}
			public SpellSchool getSpellSchool() {
				return SpellSchool.EVOCATION;
			}
		};
	}
	//蛇龙火球
	public static MagicSpell SerponFireball(int n, LivingEntity caster, Entity target) {
		return new MagicSpell(n, caster, target, MagicType.SHOOT, MagicType.MAIN, "serpon_fireball", JerotesVillageParticleTypes.SERPON_FIREBALL_DISPLAY.get(), JerotesVillageSoundEvents.MAGIC_SERPON_FIREBALL){
			public boolean spellFindUse() {
				return OtherSpellFind.SerponFireball(getCaster(), getTarget(), getSpellLevel(), 6 + (getSpellLevel() - 1) * 3, 0.5f + this.getSpellLevel() * 0.25f, getSpellAccuracy(), 1, 15, getCaster() instanceof Player);
			}
			public String getSpellModId() {
				return JerotesVillage.MODID;
			}
			public int getBaseSpellLevel() {
				return 3;
			}
			public SpellSchool getSpellSchool() {
				return SpellSchool.EVOCATION;
			}
			public float getSpellDistanceBase() {
				return 36;
			}
		};
	}
	//弹力光球
	public static MagicSpell ElasticLightBall(int n, LivingEntity caster, Entity target) {
		return new MagicSpell(n, caster, target, MagicType.SHOOT, MagicType.MAIN, "elastic_light_ball", JerotesVillageParticleTypes.ELASTIC_LIGHT_BALL_DISPLAY.get(), JerotesVillageSoundEvents.MAGIC_ELASTIC_LIGHT_BALL){
			public boolean spellFindUse() {
				return OtherSpellFind.ElasticLightBall(getCaster(), getTarget(), getSpellLevel(), 6 + (getSpellLevel() - 1) * 3, 1, 1.6f, getSpellAccuracy(), getSpellLevel(), 5, getCaster() instanceof Player);
			}
			public String getSpellModId() {
				return JerotesVillage.MODID;
			}
			public int getBaseSpellLevel() {
				return 3;
			}
			public SpellSchool getSpellSchool() {
				return SpellSchool.EVOCATION;
			}
		};
	}
	//泌火油弹
	public static MagicSpell SecreteFireOilBomb(int n, LivingEntity caster, Entity target) {
		return new MagicSpell(n, caster, target, MagicType.SHOOT, MagicType.MAIN, "secrete_fire_oil_bomb", JerotesVillageParticleTypes.SECRETE_FIRE_OIL_BOMB_DISPLAY.get(), JerotesVillageSoundEvents.MAGIC_SECRETE_FIRE_OIL_BOMB){
			public boolean spellFindUse() {
				return OtherSpellFind.SecreteFireOilBomb(getCaster(), getTarget(), getSpellLevel(), 6 + (getSpellLevel() - 1) * 3, 0.5f + this.getSpellLevel() * 0.25f, getSpellAccuracy(), this.getSpellLevel(), 5, getCaster() instanceof Player);
			}
			public String getSpellModId() {
				return JerotesVillage.MODID;
			}
			public int getBaseSpellLevel() {
				return 3;
			}
			public ResourceLocation getDisplayResourceLocation() {
				return new ResourceLocation(this.getSpellModId(), "textures/particle/secrete_fire_oil_bomb_display_by_shenmimoenengyuanti.png");
			}
			public SpellSchool getSpellSchool() {
				return SpellSchool.EVOCATION;
			}
			public float getSpellDistanceBase() {
				return 60;
			}
		};
	}
	//捕食齿花喷吐
	public static MagicSpell PredationToothedFlowerShoot(int n, LivingEntity caster, Entity target) {
		return new MagicSpell(n, caster, target, MagicType.SHOOT, MagicType.MAIN, "predation_toothed_flower_shoot", JerotesVillageParticleTypes.PREDATION_TOOTHED_FLOWER_SHOOT_DISPLAY.get(), JerotesVillageSoundEvents.MAGIC_PREDATION_TOOTHED_FLOWER_SHOOT){
			public boolean spellFindUse() {
				return OtherSpellFind.PredationToothedFlowerShoot(getCaster(), getTarget(), getSpellLevel(), getSpellAccuracy(), getSpellLevel(), 5, getCaster() instanceof Player);
			}
			public String getSpellModId() {
				return JerotesVillage.MODID;
			}
			public SpellSchool getSpellSchool() {
				return SpellSchool.EVOCATION;
			}
			public float getSpellDistanceBase() {
				return 90;
			}
		};
	}
	//浓烟飞弹
	public static MagicSpell SmokeMissile(int n, LivingEntity caster, Entity target) {
		return new MagicSpell(n, caster, target, MagicType.SHOOT, MagicType.MAIN, "smoke_missile", JerotesVillageParticleTypes.SMOKE_MISSILE_DISPLAY.get(), JerotesVillageSoundEvents.MAGIC_SMOKE_MISSILE){
			public boolean spellFindUse() {
				return OtherSpellFind.SmokeMissile(getCaster(), getTarget(), getSpellLevel(), getSpellLevel() * 3, 1, getSpellLevel() * 3, getSpellLevel(), getSpellLevel(), getSpellAccuracy(), 1, 5, getCaster() instanceof Player);
			}
			public String getSpellModId() {
				return JerotesVillage.MODID;
			}
			public int getBaseSpellLevel() {
				return 2;
			}
			public SpellSchool getSpellSchool() {
				return SpellSchool.EVOCATION;
			}
			public float getSpellDistanceBase() {
				return 30;
			}
		};
	}
	//波涛射弹
	public static MagicSpell BulgeBullet(int n, LivingEntity caster, Entity target) {
		return new MagicSpell(n, caster, target, MagicType.SHOOT, MagicType.MAIN, "bulge_bullet", JerotesVillageParticleTypes.BULGE_BULLET_DISPLAY.get(), JerotesVillageSoundEvents.MAGIC_BULGE_BULLET){
			public boolean spellFindUse() {
				return OtherSpellFind.BulgeBullet(getCaster(), getTarget(), getSpellLevel(), getSpellAccuracy(), 1, 5, this.getSpellLevel() * 3, getCaster() instanceof Player);
			}
			public int getCastingTicks() {
				return (this.getSpellLevel() * 3 - 1) * 5 + 4;
			}
			public boolean CastingAboutBase(int n) {
				if (n % 5 == 0 && n != 0) {
					getCaster().swing(InteractionHand.MAIN_HAND, true);
					OtherSpellFind.BulgeBullet(getCaster(), getTarget(), getSpellLevel(), getSpellAccuracy(), 1, 5, 1, getCaster() instanceof Player);
					if (!getCaster().isSilent()) {
						getCaster().level().playSound(null, getCaster().getX(), getCaster().getY(), getCaster().getZ(), getSound(),
								getCaster().getSoundSource(), getSoundLevel(), 1.0F);
					}
				}
				return true;
			}
			public String getSpellModId() {
				return JerotesVillage.MODID;
			}
			public SpellSchool getSpellSchool() {
				return SpellSchool.EVOCATION;
			}
			public float getSpellDistanceBase() {
				return 90;
			}
		};
	}
	//植究射弹
	public static MagicSpell BotanizeBullet(int n, LivingEntity caster, Entity target) {
		return new MagicSpell(n, caster, target, MagicType.SHOOT, MagicType.MAIN, "botanize_bullet", JerotesVillageParticleTypes.BOTANIZE_BULLET_DISPLAY.get(), JerotesVillageSoundEvents.MAGIC_BOTANIZE_BULLET){
			public boolean spellFindUse() {
				return OtherSpellFind.BotanizeBullet(getCaster(), getTarget(), getSpellLevel(), getSpellAccuracy(), 1, 5, this.getSpellLevel() * 3, getCaster() instanceof Player);
			}
			public int getCastingTicks() {
				return (this.getSpellLevel() * 3 - 1) * 5 + 4;
			}
			public boolean CastingAboutBase(int n) {
				if (n % 5 == 0 && n != 0) {
					getCaster().swing(InteractionHand.MAIN_HAND, true);
					OtherSpellFind.BotanizeBullet(getCaster(), getTarget(), getSpellLevel(), getSpellAccuracy(), 1, 5, 1, getCaster() instanceof Player);
					if (!getCaster().isSilent()) {
						getCaster().level().playSound(null, getCaster().getX(), getCaster().getY(), getCaster().getZ(), getSound(),
								getCaster().getSoundSource(), getSoundLevel(), 1.0F);
					}
				}
				return true;
			}
			public String getSpellModId() {
				return JerotesVillage.MODID;
			}
			public SpellSchool getSpellSchool() {
				return SpellSchool.EVOCATION;
			}
			public float getSpellDistanceBase() {
				return 90;
			}
		};
	}
	//旱火火球
	public static MagicSpell DroughtFireball(int n, LivingEntity caster, Entity target) {
		return new MagicSpell(n, caster, target, MagicType.SHOOT, MagicType.MAIN, "drought_fireball", JerotesVillageParticleTypes.DROUGHT_FIREBALL_DISPLAY.get(), JerotesVillageSoundEvents.MAGIC_DROUGHT_FIREBALL){
			public boolean spellFindUse() {
				return OtherSpellFind.DroughtFireball(getCaster(), getTarget(), getSpellLevel(), 6 + (getSpellLevel() - 1) * 3, getSpellAccuracy(), 1, 5, this.getSpellLevel() * 3, getCaster() instanceof Player);
			}
			public int getCastingTicks() {
				return (this.getSpellLevel() * 3 - 1) * 5 + 4;
			}
			public boolean CastingAboutBase(int n) {
				if (n % 5 == 0 && n != 0) {
					getCaster().swing(InteractionHand.MAIN_HAND, true);
					OtherSpellFind.DroughtFireball(getCaster(), getTarget(), getSpellLevel(), 6 + (getSpellLevel() - 1) * 3, getSpellAccuracy(), 1, 5, 1, getCaster() instanceof Player);
					if (!getCaster().isSilent()) {
						getCaster().level().playSound(null, getCaster().getX(), getCaster().getY(), getCaster().getZ(), getSound(),
								getCaster().getSoundSource(), getSoundLevel(), 1.0F);
					}
				}
				return true;
			}
			public String getSpellModId() {
				return JerotesVillage.MODID;
			}
			public SpellSchool getSpellSchool() {
				return SpellSchool.EVOCATION;
			}
			public float getSpellDistanceBase() {
				return 90;
			}
		};
	}
	//遗迹风弹
	public static MagicSpell RuinsWindCharge(int n, LivingEntity caster, Entity target) {
		return new MagicSpell(n, caster, target, MagicType.SHOOT, MagicType.MAIN, "ruins_wind_charge", JerotesVillageParticleTypes.RUINS_WIND_CHARGE_DISPLAY.get(), JerotesVillageSoundEvents.MAGIC_RUINS_WIND_CHARGE){
			public boolean spellFindUse() {
				return OtherSpellFind.RuinsWindCharge(getCaster(), getTarget(), getSpellLevel(), getSpellAccuracy(), 1, 5, this.getSpellLevel() * 3, getCaster() instanceof Player);
			}
			public int getCastingTicks() {
				return (this.getSpellLevel() * 3 - 1) * 5 + 4;
			}
			public boolean CastingAboutBase(int n) {
				if (n % 5 == 0 && n != 0) {
					getCaster().swing(InteractionHand.MAIN_HAND, true);
					OtherSpellFind.RuinsWindCharge(getCaster(), getTarget(), getSpellLevel(), getSpellAccuracy(), 1, 5, 1, getCaster() instanceof Player);
					if (!getCaster().isSilent()) {
						getCaster().level().playSound(null, getCaster().getX(), getCaster().getY(), getCaster().getZ(), getSound(),
								getCaster().getSoundSource(), getSoundLevel(), 1.0F);
					}
				}
				return true;
			}
			public String getSpellModId() {
				return JerotesVillage.MODID;
			}
			public SpellSchool getSpellSchool() {
				return SpellSchool.EVOCATION;
			}
			public float getSpellDistanceBase() {
				return 90;
			}
		};
	}
	//光芒爆弹
	public static MagicSpell RadiantBomb(int n, LivingEntity caster, Entity target) {
		return new MagicSpell(n, caster, target, MagicType.SHOOT, MagicType.MAIN, "radiant_bomb", JerotesVillageParticleTypes.RADIANT_BOMB_DISPLAY.get(), JerotesVillageSoundEvents.MAGIC_RADIANT_BOMB){
			public boolean spellFindUse() {
				return OtherSpellFind.RadiantBomb(getCaster(), getTarget(), getSpellLevel(), 1.1f + getSpellLevel() * 0.1f, spellLevel * 6, 1, getSpellAccuracy(), 1, 5, getCaster() instanceof Player);
			}
			public String getSpellModId() {
				return JerotesVillage.MODID;
			}
			public int getBaseSpellLevel() {
				return 2;
			}
			public SpellSchool getSpellSchool() {
				return SpellSchool.EVOCATION;
			}
			public float getSpellDistanceBase() {
				return 90;
			}
		};
	}
	public static MagicSpell SnapBolt(int n, LivingEntity caster, Entity target) {
		return new MagicSpell(n, caster, target, MagicType.SHOOT, MagicType.MAIN, "snap_bolt", JerotesVillageParticleTypes.SNAP_BOLT_DISPLAY.get(), JerotesVillageSoundEvents.MAGIC_SNAP_BOLT){
			public boolean spellFindUse() {
				return OtherSpellFind.SnapBolt(getCaster(), getTarget(), getSpellLevel(), getSpellAccuracy(), 3, 5, getCaster() instanceof Player);
			}
			public String getSpellModId() {
				return JerotesVillage.MODID;
			}
			public int getBaseSpellLevel() {
				return 4;
			}
			public SpellSchool getSpellSchool() {
				return SpellSchool.EVOCATION;
			}
			public float getSpellDistanceBase() {
				return 90;
			}
		};
	}
	//奥术光点
	public static MagicSpell ArcaneLightSpot(int n, LivingEntity caster, Entity target) {
		return new MagicSpell(n, caster, target, MagicType.TARGET, MagicType.MAIN, "arcane_light_spot", JerotesVillageParticleTypes.ARCANE_LIGHT_SPOT_DISPLAY.get(), JerotesVillageSoundEvents.MAGIC_ARCANE_LIGHT_SPOT){
			public boolean spellFindUse() {
				return OtherSpellFind.ArcaneLightSpot(getCaster(), (getTarget() instanceof LivingEntity livingEntity) ? livingEntity : getCaster(), getSpellLevel(), 6 + (getSpellLevel()) * 3, 1, getSpellAccuracy(), 1, 1, 3 + getSpellLevel(), getCaster() instanceof Player);
			}
			public int getCastingTicks() {
				return (1 + getSpellLevel()) * 5 + 4;
			}
			public boolean CastingAboutBase(int n) {
				if (n % 5 == 0 && n != 0) {
					getCaster().swing(InteractionHand.MAIN_HAND, true);
					Entity entity = getTarget();
					if (getCaster() instanceof Player player && Main.getTargetedEntity(player, getSpellDistance()) != null && canUseToEntity(Main.getTargetedEntity(player, getSpellDistance()))) {
						entity = Main.getTargetedEntity(player, getSpellDistance());
					}
					if (!(entity != null && entity.isAlive())) {
						entity = getCaster();
					}
					OtherSpellFind.ArcaneLightSpot(getCaster(), (entity instanceof LivingEntity livingEntity) ? livingEntity : getCaster(), getSpellLevel(), 6 + (getSpellLevel() - 1) * 3, 1, getSpellAccuracy(), 1, 1, 1, getCaster() instanceof Player);
					if (!getCaster().isSilent()) {
						getCaster().level().playSound(null, getCaster().getX(), getCaster().getY(), getCaster().getZ(), getSound(),
								getCaster().getSoundSource(), getSoundLevel(), 1.0F);
					}
				}
				return true;
			}
			public String getSpellModId() {
				return JerotesVillage.MODID;
			}
			public boolean canUseTargetNone() {
				return true;
			}
			public int getBaseSpellLevel() {
				return 1;
			}
			public float getSpellDistanceBase() {
				return 36;
			}
			public SpellSchool getSpellSchool() {
				return SpellSchool.EVOCATION;
			}
		};
	}
	//蛇龙热风术
	public static MagicSpell SerponSirocco(int n, LivingEntity caster, Entity target) {
		return new MagicSpell(n, caster, target, MagicType.SHOOT, MagicType.MAIN, "serpon_sirocco", JerotesVillageParticleTypes.SERPON_SIROCCO_DISPLAY.get(), JerotesVillageSoundEvents.MAGIC_SERPON_SIROCCO){
			public boolean spellFindUse() {
				return OtherSpellFind.SerponSirocco(getCaster(), getTarget(), getSpellLevel(), getSpellLevel() * 2, getSpellLevel(), getSpellAccuracy(), getSpellLevel() - 2, 12, getCaster() instanceof Player);
			}
			public String getSpellModId() {
				return JerotesVillage.MODID;
			}
			public int getBaseSpellLevel() {
				return 3;
			}
			public SpellSchool getSpellSchool() {
				return SpellSchool.EVOCATION;
			}
			public float getSpellDistanceBase() {
				return 40;
			}
		};
	}
	//眼盘射线
	public static MagicSpell EyeDiscShoot(int n, LivingEntity caster, Entity target) {
		return new MagicSpell(n, caster, target, MagicType.SHOOT, MagicType.MAIN, "eye_disc_shoot", JerotesVillageParticleTypes.EYE_DISC_SHOOT_DISPLAY.get(), JerotesVillageSoundEvents.MAGIC_EYE_DISC_SHOOT){
			public boolean spellFindUse() {
				return OtherSpellFind.EyeDiscShoot(getCaster(), getTarget(), getSpellLevel(), getSpellAccuracy(), 1, 5, 20 + this.getSpellLevel() * 5, getCaster() instanceof Player);
			}
			public int getCastingTicks() {
				return (19 + this.getSpellLevel() * 5) * 2 + 1;
			}
			public boolean CastingAboutBase(int n) {
				if (n % 2 == 0 && n != 0) {
					getCaster().swing(InteractionHand.MAIN_HAND, true);
					OtherSpellFind.EyeDiscShoot(getCaster(), getTarget(), getSpellLevel(), getSpellAccuracy(), 1, 5, 1, getCaster() instanceof Player);
					if (!getCaster().isSilent()) {
						getCaster().level().playSound(null, getCaster().getX(), getCaster().getY(), getCaster().getZ(), getSound(),
								getCaster().getSoundSource(), getSoundLevel(), 1.0F);
					}
				}
				return true;
			}
			public String getSpellModId() {
				return JerotesVillage.MODID;
			}
			public int getBaseSpellLevel() {
				return 2;
			}
			public SpellSchool getSpellSchool() {
				return SpellSchool.EVOCATION;
			}
			public float getSpellDistanceBase() {
				return 45;
			}
		};
	}
	//投鞭断流
	public static MagicSpell WhipTheTide(int n, LivingEntity caster, Entity target) {
		return new MagicSpell(n, caster, target, MagicType.SHOOT, MagicType.MAIN, "whip_the_tide", JerotesVillageParticleTypes.WHIP_THE_TIDE_DISPLAY.get(), JerotesVillageSoundEvents.MAGIC_WHIP_THE_TIDE){
			public boolean spellFindUse() {
				return OtherSpellFind.WhipTheTide(getCaster(), getTarget(), getSpellLevel(), 1.6f, getSpellAccuracy(), 20, 5, getCaster() instanceof Player);
			}
			public String getSpellModId() {
				return JerotesVillage.MODID;
			}
			public int getBaseSpellLevel() {
				return 5;
			}
			public SpellSchool getSpellSchool() {
				return SpellSchool.EVOCATION;
			}
			public float getSpellDistanceBase() {
				return 60;
			}
		};
	}
	//冰岩投掷
	public static MagicSpell IceRockThrow(int n, LivingEntity caster, Entity target) {
		return new MagicSpell(n, caster, target, MagicType.SHOOT, MagicType.MAIN, "ice_rock_throw", JerotesVillageParticleTypes.ICE_ROCK_THROW_DISPLAY.get(), JerotesVillageSoundEvents.MAGIC_ICE_ROCK_THROW){
			public boolean spellFindUse() {
				return OtherSpellFind.IceRockThrow(getCaster(), getTarget(), getSpellLevel(), 6 + (getSpellLevel() - 1) * 3, 1.6f, getSpellAccuracy(), 1, 5, getCaster() instanceof Player);
			}
			public String getSpellModId() {
				return JerotesVillage.MODID;
			}
			public int getBaseSpellLevel() {
				return 3;
			}
			public SpellSchool getSpellSchool() {
				return SpellSchool.EVOCATION;
			}
			public float getSpellDistanceBase() {
				return 60;
			}
		};
	}
	//苦寒霜噬术
	public static MagicSpell BitterColdFrostbite(int n, LivingEntity caster, Entity target) {
		return new MagicSpell(n, caster, target, MagicType.SHOOT, MagicType.MAIN, "bitter_cold_frostbite", JerotesVillageParticleTypes.BITTER_COLD_FROSTBITE_DISPLAY.get(), JerotesVillageSoundEvents.MAGIC_BITTER_COLD_FROSTBITE){
			public boolean spellFindUse() {
				return OtherSpellFind.BitterColdFrostbite(getCaster(), getTarget(), getSpellLevel(), 6 + (getSpellLevel() - 1) * 3, getSpellAccuracy(), getCaster() instanceof Player);
			}
			public int getCastingTicks() {
				return 59;
			}
			public boolean CastingAboutBase(int n) {
				if (n % 2 == 0 && n != 0) {
					getCaster().swing(InteractionHand.MAIN_HAND, true);
					OtherSpellFind.BitterColdFrostbite(getCaster(), getTarget(), getSpellLevel(), 6 + (getSpellLevel() - 1) * 3, getSpellAccuracy(), getCaster() instanceof Player);
					if (!getCaster().isSilent()) {
						getCaster().level().playSound(null, getCaster().getX(), getCaster().getY(), getCaster().getZ(), getSound(),
								getCaster().getSoundSource(), getSoundLevel(), 1.0F);
					}
				}
				return true;
			}
			public String getSpellModId() {
				return JerotesVillage.MODID;
			}
			public int getBaseSpellLevel() {
				return 2;
			}
			public SpellSchool getSpellSchool() {
				return SpellSchool.EVOCATION;
			}
			public float getSpellDistanceBase() {
				return 9;
			}
		};
	}
	//蛇龙火舌术
	public static MagicSpell SerponFlames(int n, LivingEntity caster, Entity target) {
		return new MagicSpell(n, caster, target, MagicType.SHOOT, MagicType.MAIN, "serpon_flames", JerotesVillageParticleTypes.SERPON_FLAMES_DISPLAY.get(), JerotesVillageSoundEvents.MAGIC_SERPON_FLAMES){
			public boolean spellFindUse() {
				return OtherSpellFind.SerponFlames(getCaster(), getTarget(), getSpellLevel(), 6 + (getSpellLevel() - 1) * 3, getSpellAccuracy(), getCaster() instanceof Player);
			}
			public int getCastingTicks() {
				return 59;
			}
			public boolean CastingAboutBase(int n) {
				if (n % 2 == 0 && n != 0) {
					if (!(getCaster().getUseItem().getItem() instanceof DroughtWildfireShield)) {
						getCaster().swing(InteractionHand.MAIN_HAND, true);
					}
					OtherSpellFind.SerponFlames(getCaster(), getTarget(), getSpellLevel(), 6 + (getSpellLevel() - 1) * 3, getSpellAccuracy(), getCaster() instanceof Player);
					if (!getCaster().isSilent()) {
						getCaster().level().playSound(null, getCaster().getX(), getCaster().getY(), getCaster().getZ(), getSound(),
								getCaster().getSoundSource(), getSoundLevel(), 1.0F);
					}
				}
				return true;
			}
			public String getSpellModId() {
				return JerotesVillage.MODID;
			}
			public int getBaseSpellLevel() {
				return 3;
			}
			public SpellSchool getSpellSchool() {
				return SpellSchool.EVOCATION;
			}
			public float getSpellDistanceBase() {
				return 9;
			}
		};
	}
	//不祥火舌术
	public static MagicSpell OminousFlames(int n, LivingEntity caster, Entity target) {
		return new MagicSpell(n, caster, target, MagicType.SHOOT, MagicType.MAIN, "ominous_flames", JerotesVillageParticleTypes.OMINOUS_FLAMES_DISPLAY.get(), JerotesVillageSoundEvents.MAGIC_OMINOUS_FLAMES){
			public boolean spellFindUse() {
				return OtherSpellFind.OminousFlames(getCaster(), getTarget(), getSpellLevel(), 6 + (getSpellLevel() - 1) * 3, getSpellAccuracy(), getCaster() instanceof Player);
			}
			public int getCastingTicks() {
				return 59;
			}
			public boolean CastingAboutBase(int n) {
				if (n % 2 == 0 && n != 0) {
					getCaster().swing(InteractionHand.MAIN_HAND, true);
					OtherSpellFind.OminousFlames(getCaster(), getTarget(), getSpellLevel(), 6 + (getSpellLevel() - 1) * 3, getSpellAccuracy(), getCaster() instanceof Player);
					if (!getCaster().isSilent()) {
						getCaster().level().playSound(null, getCaster().getX(), getCaster().getY(), getCaster().getZ(), getSound(),
								getCaster().getSoundSource(), getSoundLevel(), 1.0F);
					}
				}
				return true;
			}
			public String getSpellModId() {
				return JerotesVillage.MODID;
			}
			public int getBaseSpellLevel() {
				return 2;
			}
			public SpellSchool getSpellSchool() {
				return SpellSchool.EVOCATION;
			}
			public float getSpellDistanceBase() {
				return 9;
			}
		};
	}
	//蠕虫雷暴
	public static MagicSpell WormThunderstorm(int n, LivingEntity caster, Entity target) {
		return new MagicSpell(n, caster, target, MagicType.SHOOT, MagicType.MAIN, "worm_thunderstorm", JerotesVillageParticleTypes.WORM_THUNDERSTORM_DISPLAY.get(), JerotesVillageSoundEvents.MAGIC_WORM_THUNDERSTORM){
			public boolean spellFindUse() {
				return OtherSpellFind.WormThunderstorm(getCaster(), getTarget(), getSpellLevel(), getSpellAccuracy(), 1, 5, getCaster() instanceof Player, getSubcategory());
			}
			public String getSpellModId() {
				return JerotesVillage.MODID;
			}
			public int getBaseSpellLevel() {
				return 3;
			}
			public float getSpellDistanceBase() {
				return 30;
			}
			public SpellSchool getSpellSchool() {
				return SpellSchool.EVOCATION;
			}
			//
			public int getCustomSubcategoryReachAdd() {
				return 1;
			}
			public int usedByNonPlayerFindSubcategory(LivingEntity livingEntity) {
				return 0;
			}
			//
		};
	}
	//推动力场
	public static MagicSpell PushForce(int n, LivingEntity caster, Entity target) {
		return new MagicSpell(n, caster, target, MagicType.SHOOT, MagicType.MAIN, "push_force", JerotesVillageParticleTypes.PUSH_FORCE_DISPLAY.get(), JerotesVillageSoundEvents.MAGIC_PUSH_FORCE){
			public boolean spellFindUse() {
				return OtherSpellFind.PushForce(getCaster(), getTarget(), getSpellLevel(), getSpellLevel(), 1.0f + getSpellLevel() * 0.2f, getSpellLevel(), 1.0f + getSpellLevel() * 0.2f, getSpellAccuracy(), 1, 5, getCaster() instanceof Player);
			}
			public String getSpellModId() {
				return JerotesVillage.MODID;
			}
			public int getBaseSpellLevel() {
				return 3;
			}
			public float getSpellDistanceBase() {
				return 18;
			}
			public SpellSchool getSpellSchool() {
				return SpellSchool.EVOCATION;
			}
		};
	}
	//忐忑不定
	public static MagicSpell UneasyUncertainty(int n, LivingEntity caster, Entity target) {
		return new MagicSpell(n, caster, target, MagicType.SHOOT, MagicType.MAIN, "uneasy_uncertainty", JerotesVillageParticleTypes.UNEASY_UNCERTAINTY_DISPLAY.get(), JerotesVillageSoundEvents.MAGIC_UNEASY_UNCERTAINTY){
			public boolean spellFindUse() {
				return OtherSpellFind.UneasyUncertainty(getCaster(), getTarget(), getSpellLevel(), getSpellAccuracy(), 1, 5, getCaster() instanceof Player);
			}
			public String getSpellModId() {
				return JerotesVillage.MODID;
			}
			public int getBaseSpellLevel() {
				return 4;
			}
			public float getSpellDistanceBase() {
				return 36;
			}
			public SpellSchool getSpellSchool() {
				return SpellSchool.EVOCATION;
			}
		};
	}
	//窒息控制
	public static MagicSpell AsphyxiaControl(int n, LivingEntity caster, Entity target) {
		return new MagicSpell(n, caster, target, MagicType.SELF, MagicType.MAIN, "asphyxia_control", JerotesVillageParticleTypes.ASPHYXIA_CONTROL_DISPLAY.get(), JerotesVillageSoundEvents.MAGIC_ASPHYXIA_CONTROL){
			public boolean spellFindUse() {
				return OtherSpellFind.AsphyxiaControl(getCaster(), 6 + (getSpellLevel() - 1) * 3, (int)Math.ceil((getSpellLevel()) / 2.0), getSpellLevel() * 8);
			}
			public String getSpellModId() {
				return JerotesVillage.MODID;
			}
			public int getBaseSpellLevel() {
				return 3;
			}
			public float getSpellDistanceBase() {
				return getSpellLevel() * 8;
			}
			public SpellSchool getSpellSchool() {
				return SpellSchool.NECROMANCY;
			}
		};
	}
	//海龙威慑
	public static MagicSpell OceanDragonDeterrence(int n, LivingEntity caster, Entity target) {
		return new MagicSpell(n, caster, target, MagicType.SELF, MagicType.MAIN, "ocean_dragon_deterrence", JerotesVillageParticleTypes.OCEAN_DRAGON_DETERRENCE_DISPLAY.get(), JerotesVillageSoundEvents.MAGIC_OCEAN_DRAGON_DETERRENCE){
			public boolean spellFindUse() {
				return OtherSpellFind.OceanDragonDeterrence(getCaster(), getSpellLevel() * 2,getSpellLevel() / 2 + 1, getSpellLevel() * 2, getSpellLevel() / 2 + 1, getSpellLevel() * 8);
			}
			public String getSpellModId() {
				return JerotesVillage.MODID;
			}
			public int getBaseSpellLevel() {
				return 5;
			}
			public float getSpellDistanceBase() {
				return getSpellLevel() * 8;
			}
			public SpellSchool getSpellSchool() {
				return SpellSchool.ENCHANTMENT;
			}
		};
	}
	//宝石海浪
	public static MagicSpell GemstoneWaves(int n, LivingEntity caster, Entity target) {
		return new MagicSpell(n, caster, target, MagicType.SHOOT, MagicType.MAIN, "gemstone_waves", JerotesVillageParticleTypes.GEMSTONE_WAVES_DISPLAY.get(), JerotesVillageSoundEvents.MAGIC_GEMSTONE_WAVES){
			public boolean spellFindUse() {
				return OtherSpellFind.GemstoneWaves(getCaster(), getSpellLevel(), getSpellLevel() * 3, getSpellLevel(), 8, 30, this.getSpellLevel() * 5);
			}
			public String getSpellModId() {
				return JerotesVillage.MODID;
			}
			public int getBaseSpellLevel() {
				return 3;
			}
			public float getSpellDistanceBase() {
				return 8;
			}
			public SpellSchool getSpellSchool() {
				return SpellSchool.CONJURATION;
			}
		};
	}
	//迅电流光
	public static MagicSpell Electroflash(int n, LivingEntity caster, Entity target) {
		return new MagicSpell(n, caster, target, MagicType.SHOOT, MagicType.MAIN, "electroflash", JerotesVillageParticleTypes.ELECTROFLASH_DISPLAY.get(), JerotesVillageSoundEvents.MAGIC_ELECTROFLASH){
			public boolean spellFindUse() {
				return OtherSpellFind.Electroflash(getCaster(), getSpellLevel());
			}
			public int getCastingTicks() {
				return 240;
			}
			public boolean isCastingCanNotStop() {
				return true;
			}
			public String getSpellModId() {
				return JerotesVillage.MODID;
			}
			public int getBaseSpellLevel() {
				return 3;
			}
			public float getSpellDistanceBase() {
				return 64;
			}
			public SpellSchool getSpellSchool() {
				return SpellSchool.EVOCATION;
			}
		};
	}
	//不屈意志
	public static MagicSpell IndomitableWill(int n, LivingEntity caster, Entity target) {
		return new MagicSpell(n, caster, target, MagicType.SELF, MagicType.MAIN, "indomitable_will", JerotesVillageParticleTypes.INDOMITABLE_WILL_DISPLAY.get(), JerotesVillageSoundEvents.MAGIC_INDOMITABLE_WILL){
			public boolean spellFindUse() {
				return OtherSpellFind.IndomitableWill(getCaster(), getSpellLevel(), getSpellLevel() * 4, getSpellLevel() * 5);
			}
			public String getSpellModId() {
				return JerotesVillage.MODID;
			}
			public int getBaseSpellLevel() {
				return 6;
			}
			public float getSpellDistanceBase() {
				return getSpellLevel() * 4;
			}
			public SpellSchool getSpellSchool() {
				return SpellSchool.CONJURATION;
			}
		};
	}
	//漂浮力场
	public static MagicSpell FloatingForce(int n, LivingEntity caster, Entity target) {
		return new MagicSpell(n, caster, target, MagicType.SELF, MagicType.MAIN, "floating_force", JerotesVillageParticleTypes.FLOATING_FORCE_DISPLAY.get(), JerotesVillageSoundEvents.MAGIC_FLOATING_FORCE){
			public boolean spellFindUse() {
				return OtherSpellFind.FloatingForce(getCaster(), getSpellLevel(), getSpellLevel() * 8, getSpellLevel() * 5);
			}
			public String getSpellModId() {
				return JerotesVillage.MODID;
			}
			public int getBaseSpellLevel() {
				return 3;
			}
			public float getSpellDistanceBase() {
				return getSpellLevel() * 8;
			}
			public SpellSchool getSpellSchool() {
				return SpellSchool.EVOCATION;
			}
		};
	}
	//地吸力场
	public static MagicSpell GravityForce(int n, LivingEntity caster, Entity target) {
		return new MagicSpell(n, caster, target, MagicType.SELF, MagicType.MAIN, "gravity_force", JerotesVillageParticleTypes.GRAVITY_FORCE_DISPLAY.get(), JerotesVillageSoundEvents.MAGIC_GRAVITY_FORCE){
			public boolean spellFindUse() {
				return OtherSpellFind.GravityForce(getCaster(), getSpellLevel(), getSpellLevel() * 8, getSpellLevel() * 5);
			}
			public String getSpellModId() {
				return JerotesVillage.MODID;
			}
			public int getBaseSpellLevel() {
				return 3;
			}
			public float getSpellDistanceBase() {
				return getSpellLevel() * 8;
			}
			public SpellSchool getSpellSchool() {
				return SpellSchool.EVOCATION;
			}
		};
	}
	//腐蚀雪云
	public static MagicSpell CorrosiveSnowCloud(int n, LivingEntity caster, Entity target) {
		return new MagicSpell(n, caster, target, MagicType.TARGET, MagicType.MAIN, "corrosive_snow_cloud", JerotesVillageParticleTypes.CORROSIVE_SNOW_CLOUD_DISPLAY.get(), JerotesVillageSoundEvents.MAGIC_CORROSIVE_SNOW_CLOUD){
			public boolean spellFindUse() {
				return OtherSpellFind.CorrosiveSnowCloud(getCaster(), (getTarget() instanceof LivingEntity livingEntity) ? livingEntity : getCaster(), 6 + (getSpellLevel() - 1) * 3, 6 + (getSpellLevel() - 1) * 3, (int)Math.ceil((getSpellLevel()) / 2.0), 1, Main.mobHeight(getTarget()) + 2);
			}
			public String getSpellModId() {
				return JerotesVillage.MODID;
			}
			public float getSpellDistanceBase() {
				return 36;
			}
			public boolean canUseTargetNone() {
				return true;
			}
			public int getBaseSpellLevel() {
				return 3;
			}
			public SpellSchool getSpellSchool() {
				return SpellSchool.CONJURATION;
			}
		};
	}
	//不洁血雨
	public static MagicSpell UncleanBloodRain(int n, LivingEntity caster, Entity target) {
		return new MagicSpell(n, caster, target, MagicType.TARGET, MagicType.MAIN, "unclean_blood_rain", JerotesVillageParticleTypes.UNCLEAN_BLOOD_RAIN_DISPLAY.get(), JerotesVillageSoundEvents.MAGIC_UNCLEAN_BLOOD_RAIN){
			public boolean spellFindUse() {
				return OtherSpellFind.UncleanBloodRain(getCaster(), (getTarget() instanceof LivingEntity livingEntity) ? livingEntity : getCaster(), 6 + (getSpellLevel() - 1) * 3, 6 + (getSpellLevel() - 1) * 3, (int)Math.ceil((getSpellLevel()) / 2.0), 1, Main.mobHeight(getTarget()) + 2);
			}
			public String getSpellModId() {
				return JerotesVillage.MODID;
			}
			public float getSpellDistanceBase() {
				return 36;
			}
			public boolean canUseTargetNone() {
				return true;
			}
			public int getBaseSpellLevel() {
				return 3;
			}
			public SpellSchool getSpellSchool() {
				return SpellSchool.NECROMANCY;
			}
		};
	}
	//不洁血雾
	public static MagicSpell UncleanBloodFog(int n, LivingEntity caster, Entity target) {
		return new MagicSpell(n, caster, target, MagicType.TARGET, MagicType.MAIN, "unclean_blood_fog", JerotesVillageParticleTypes.UNCLEAN_BLOOD_FOG_DISPLAY.get(), JerotesVillageSoundEvents.MAGIC_UNCLEAN_BLOOD_FOG){
			public boolean spellFindUse() {
				return OtherSpellFind.UncleanBloodFog(getCaster(), (getTarget() instanceof LivingEntity livingEntity) ? livingEntity : getCaster(), 6 + (getSpellLevel() - 1) * 3, 6 + (getSpellLevel() - 1) * 3, (int)Math.ceil((getSpellLevel()) / 2.0), 6 + (getSpellLevel() - 1) * 3, (int)Math.ceil((getSpellLevel()) / 2.0),  5 + getSpellLevel());
			}
			public String getSpellModId() {
				return JerotesVillage.MODID;
			}
			public float getSpellDistanceBase() {
				return 36;
			}
			public boolean canUseTargetNone() {
				return true;
			}
			public int getBaseSpellLevel() {
				return 3;
			}
			public SpellSchool getSpellSchool() {
				return SpellSchool.NECROMANCY;
			}
		};
	}
	//神汉低语
	public static MagicSpell WarlockWhisper(int n, LivingEntity caster, Entity target) {
		return new MagicSpell(n, caster, target, MagicType.SHOOT, MagicType.MAIN, "warlock_whisper", JerotesVillageParticleTypes.WARLOCK_WHISPER_DISPLAY.get(), JerotesVillageSoundEvents.MAGIC_WARLOCK_WHISPER){
			public boolean spellFindUse() {
				return OtherSpellFind.WarlockWhisper(getCaster(), getSpellLevel() + 1, getSpellLevel() * 15, getSpellLevel() - 1, getSpellLevel() * 15, 1, 16 + getSpellLevel() * 2, 12 + getSpellLevel() * 2, 1.0d + getSpellLevel() * 0.1f, 32);
			}
			public String getSpellModId() {
				return JerotesVillage.MODID;
			}
			public int getBaseSpellLevel() {
				return 4;
			}
			public float getSpellDistanceBase() {
				return 32;
			}
			public SpellSchool getSpellSchool() {
				return SpellSchool.ENCHANTMENT;
			}
		};
	}
	//雪怪恐吓
	public static MagicSpell YetiDeter(int n, LivingEntity caster, Entity target) {
		return new MagicSpell(n, caster, target, MagicType.TARGET, MagicType.MAIN, "yeti_deter", JerotesVillageParticleTypes.YETI_DETER_DISPLAY.get(), JerotesVillageSoundEvents.MAGIC_YETI_DETER){
			public boolean spellFindUse() {
				return OtherSpellFind.YetiDeter(getCaster(), (getTarget() instanceof LivingEntity livingEntity) ? livingEntity : getCaster(), getSpellLevel(), getSpellLevel(), 1, getSpellLevel() * 6);
			}
			public String getSpellModId() {
				return JerotesVillage.MODID;
			}
			public int getBaseSpellLevel() {
				return 2;
			}
			public float getSpellDistanceBase() {
				return getSpellLevel() * 6;
			}
			public boolean canUseTargetNone() {
				return true;
			}
			public SpellSchool getSpellSchool() {
				return SpellSchool.ENCHANTMENT;
			}
		};
	}
	//瑟潘珀斯陨石术
	public static MagicSpell SerpenperthMeteor(int n, LivingEntity caster, Entity target) {
		return new MagicSpell(n, caster, target, MagicType.TARGET, MagicType.MAIN, "serpenperth_meteor", JerotesVillageParticleTypes.SERPENPERTH_METEOR_DISPLAY.get(), JerotesVillageSoundEvents.MAGIC_SERPENPERTH_METEOR){
			public boolean spellFindUse() {
				return OtherSpellFind.SerpenperthMeteor(getCaster(), (getTarget() instanceof LivingEntity livingEntity) ? livingEntity : getCaster(), getSpellLevel(), 1.0f + getSpellLevel() * 0.5f, Math.max(3, getSpellLevel()), Math.max(6, getSpellLevel() * 2), 6, Main.mobHeight(getTarget()) + 15, getSubcategory());
			}
			public String getSpellModId() {
				return JerotesVillage.MODID;
			}
			public int getBaseSpellLevel() {
				return 5;
			}
			public boolean canUseTargetNone() {
				return true;
			}
			public float getSpellDistanceBase() {
				return 45;
			}
			public SpellSchool getSpellSchool() {
				return SpellSchool.EVOCATION;
			}
			//
			public int getCustomSubcategoryReachAdd() {
				return 1;
			}
			public int usedByNonPlayerFindSubcategory(LivingEntity livingEntity) {
				return 0;
			}
			//
		};
	}
	//不祥齿轮
	public static MagicSpell OminousGear(int n, LivingEntity caster, Entity target) {
		return new MagicSpell(n, caster, target, MagicType.SELF, MagicType.MAIN, "ominous_gear", JerotesVillageParticleTypes.OMINOUS_GEAR_DISPLAY.get(), JerotesVillageSoundEvents.MAGIC_OMINOUS_GEAR){
			public boolean spellFindUse() {
				return OtherSpellFind.OminousGear(getCaster(), (getTarget() instanceof LivingEntity livingEntity) ? livingEntity : getCaster(), getSpellLevel(), 9, getSpellLevel() * 3);
			}
			public String getSpellModId() {
				return JerotesVillage.MODID;
			}
			public int getBaseSpellLevel() {
				return 3;
			}
			public float getSpellDistanceBase() {
				return 9;
			}
			public SpellSchool getSpellSchool() {
				return SpellSchool.CONJURATION;
			}
			public boolean canUseTargetNone() {
				return true;
			}
		};
	}
	//邪祟召唤
	public static MagicSpell EvilSummoning(int n, LivingEntity caster, Entity target) {
		return new MagicSpell(n, caster, target, MagicType.TARGET, MagicType.MAIN, "evil_summoning", JerotesVillageParticleTypes.EVIL_SUMMONING_DISPLAY.get(), JerotesVillageSoundEvents.MAGIC_EVIL_SUMMONING){
			public boolean spellFindUse() {
				return OtherSpellFind.EvilSummoning(getCaster(), (getTarget() instanceof LivingEntity livingEntity) ? livingEntity : getCaster(), getSpellLevel(), getSpellLevel() * 2, getSpellLevel() * 4, 4);
			}
			public String getSpellModId() {
				return JerotesVillage.MODID;
			}
			public int getBaseSpellLevel() {
				return 4;
			}
			public float getSpellDistanceBase() {
				return 32;
			}
			public boolean canUseTargetNone() {
				return true;
			}
			public boolean canUse() {
				if (getCaster() != null) {
					List<UncleanTentacleEntity> list = getCaster().level().getEntitiesOfClass(UncleanTentacleEntity.class, getCaster().getBoundingBox().inflate(128, 128, 128));
					list.removeIf(summon -> summon.getOwner() != getCaster());
					return super.canUse() && list.size() <= getSpellLevel() * 2;
				}
				return super.canUse();
			}
			public SpellSchool getSpellSchool() {
				return SpellSchool.CONJURATION;
			}
		};
	}
	//喝茅成剑
	public static MagicSpell CogonSword(int n, LivingEntity caster, Entity target) {
		return new MagicSpell(n, caster, target, MagicType.TARGET, MagicType.MAIN, "cogon_sword", JerotesVillageParticleTypes.COGON_SWORD_DISPLAY.get(), JerotesVillageSoundEvents.MAGIC_COGON_SWORD){
			public boolean spellFindUse() {
				return OtherSpellFind.CogonSword(getCaster(), (getTarget() instanceof LivingEntity livingEntity) ? livingEntity : getCaster(), getSpellLevel(), getSpellLevel() * 2, getSpellLevel() * 3, 16);
			}
			public String getSpellModId() {
				return JerotesVillage.MODID;
			}
			public int getBaseSpellLevel() {
				return 4;
			}
			public float getSpellDistanceBase() {
				return 32;
			}
			public boolean canUseTargetNone() {
				return true;
			}
			public boolean canUse() {
				if (getCaster() != null) {
					List<CogonSwordEntity> list = getCaster().level().getEntitiesOfClass(CogonSwordEntity.class, getCaster().getBoundingBox().inflate(128, 128, 128));
					list.removeIf(summon -> summon.getOwner() != getCaster());
					return super.canUse() && list.size() <= getSpellLevel() * 4;
				}
				return super.canUse();
			}
			public SpellSchool getSpellSchool() {
				return SpellSchool.CONJURATION;
			}
		};
	}
	//盘根错节
	public static MagicSpell ImprisonVine(int n, LivingEntity caster, Entity target) {
		return new MagicSpell(n, caster, target, MagicType.TARGET, MagicType.MAIN, "imprison_vine", JerotesVillageParticleTypes.IMPRISON_VINE_DISPLAY.get(), JerotesVillageSoundEvents.MAGIC_IMPRISON_VINE){
			public boolean spellFindUse() {
				return OtherSpellFind.ImprisonVine(getCaster(), (getTarget() instanceof LivingEntity livingEntity) ? livingEntity : getCaster(), getSpellLevel(), getSpellLevel() * 2, getSpellLevel() * 3);
			}
			public String getSpellModId() {
				return JerotesVillage.MODID;
			}
			public int getBaseSpellLevel() {
				return 5;
			}
			public float getSpellDistanceBase() {
				return 32;
			}
			public boolean canUseTargetNone() {
				return true;
			}
			public boolean canUse() {
				if (getCaster() != null) {
					List<CogonSwordEntity> list = getCaster().level().getEntitiesOfClass(CogonSwordEntity.class, getCaster().getBoundingBox().inflate(128, 128, 128));
					list.removeIf(summon -> summon.getOwner() != getCaster());
					return super.canUse() && list.size() <= getSpellLevel() * 4;
				}
				return super.canUse();
			}
			public SpellSchool getSpellSchool() {
				return SpellSchool.CONJURATION;
			}
		};
	}
	//荆棘之刺
	public static MagicSpell ThornSpike(int n, LivingEntity caster, Entity target) {
		return new MagicSpell(n, caster, target, MagicType.TARGET, MagicType.MAIN, "thorn_spike", JerotesVillageParticleTypes.THORN_SPIKE_DISPLAY.get(), JerotesVillageSoundEvents.MAGIC_THORN_SPIKE) {
			public boolean spellFindUse() {
				return OtherSpellFind.ThornSpike(getCaster(), (getTarget() instanceof LivingEntity livingEntity) ? livingEntity : getCaster(), getSpellLevel(), getSpellLevel() * 4, 2f);
			}
			public String getSpellModId() {
				return JerotesVillage.MODID;
			}
			public int getBaseSpellLevel() {
				return 4;
			}
			public float getSpellDistanceBase() {
				return 32;
			}
			public boolean canUseTargetNone() {
				return true;
			}
			public boolean canUse() {
				if (getCaster() != null) {
					List<ThornSpikeEntity> list = getCaster().level().getEntitiesOfClass(ThornSpikeEntity.class, getCaster().getBoundingBox().inflate(128, 128, 128));
					list.removeIf(summon -> summon.getOwner() != getCaster());
					return super.canUse() && list.size() <= getSpellLevel() * 4;
				}
				return super.canUse();
			}
			public SpellSchool getSpellSchool() {
				return SpellSchool.CONJURATION;
			}
		};
	}
	//苦寒祭坛
	public static MagicSpell BitterColdAltar(int n, LivingEntity caster, Entity target) {
		return new MagicSpell(n, caster, target, MagicType.SELF, MagicType.MAIN, "bitter_cold_altar", JerotesVillageParticleTypes.BITTER_COLD_ALTAR_DISPLAY.get(), JerotesVillageSoundEvents.MAGIC_BITTER_COLD_ALTAR){
			public boolean spellFindUse() {
				return OtherSpellFind.BitterColdAltar(getCaster(), getSpellLevel(), 1, 1, 18);
			}
			public String getSpellModId() {
				return JerotesVillage.MODID;
			}
			public float getSpellDistanceBase() {
				return 18;
			}
			public boolean canUse() {
				if (getCaster() != null) {
					List<BitterColdAltarEntity> list = getCaster().level().getEntitiesOfClass(BitterColdAltarEntity.class, getCaster().getBoundingBox().inflate(128, 128, 128));
					list.removeIf(summon -> summon.getOwner() != getCaster());
					return super.canUse() && list.isEmpty();
				}
				return super.canUse();
			}
			public int getBaseSpellLevel() {
				return 4;
			}
			public SpellSchool getSpellSchool() {
				return SpellSchool.CONJURATION;
			}
		};
	}
	//撒豆成兵
	public static MagicSpell ConjureCoilvineBotanizes(int n, LivingEntity caster, Entity target) {
		return new MagicSpell(n, caster, target, MagicType.SELF, MagicType.MAIN, "conjure_coilvine_botanizes", JerotesVillageParticleTypes.CONJURE_COILVINE_BOTANIZES_DISPLAY.get(), JerotesVillageSoundEvents.MAGIC_CONJURE_COILVINE_BOTANIZES){
			public boolean spellFindUse() {
				List<CoilvineBotanizeEntity> list = getCaster().level().getEntitiesOfClass(CoilvineBotanizeEntity.class, getCaster().getBoundingBox().inflate(128, 128, 128));
				list.removeIf(summon -> summon.getOwner() != getCaster());
				list.removeIf(summon -> !summon.isNotPersistent());
				return OtherSpellFind.ConjureCoilvineBotanizes(getCaster(), getSpellLevel() - list.size(), getSpellLevel() - list.size(), 9);
			}
			public String getSpellModId() {
				return JerotesVillage.MODID;
			}
			public int getBaseSpellLevel() {
				return 3;
			}
			public float getSpellDistanceBase() {
				return 9;
			}
			public boolean canUse() {
				if (getCaster() != null) {
					List<CoilvineBotanizeEntity> list = getCaster().level().getEntitiesOfClass(CoilvineBotanizeEntity.class, getCaster().getBoundingBox().inflate(128, 128, 128));
					list.removeIf(summon -> summon.getOwner() != getCaster());
					list.removeIf(summon -> !summon.isNotPersistent());
					return super.canUse() && list.size() < getSpellLevel();
				}
				return super.canUse();
			}
			public SpellSchool getSpellSchool() {
				return SpellSchool.CONJURATION;
			}
		};
	}
	//天女散花
	public static MagicSpell VinePodBomb(int n, LivingEntity caster, Entity target) {
		return new MagicSpell(n, caster, target, MagicType.SELF, MagicType.MAIN, "vine_pod_bomb", JerotesVillageParticleTypes.VINE_POD_BOMB_DISPLAY.get(), JerotesVillageSoundEvents.MAGIC_VINE_POD_BOMB){
			public boolean spellFindUse() {
				List<VinePodBombEntity> list = getCaster().level().getEntitiesOfClass(VinePodBombEntity.class, getCaster().getBoundingBox().inflate(128, 128, 128));
				list.removeIf(summon -> summon.getOwner() != getCaster());
				list.removeIf(summon -> !summon.isNotPersistent());
				return OtherSpellFind.VinePodBomb(getCaster(), getSpellLevel() - list.size(), getSpellLevel() - list.size(), 9);
			}
			public String getSpellModId() {
				return JerotesVillage.MODID;
			}
			public int getBaseSpellLevel() {
				return 5;
			}
			public float getSpellDistanceBase() {
				return 9;
			}
			public boolean canUse() {
				if (getCaster() != null) {
					List<VinePodBombEntity> list = getCaster().level().getEntitiesOfClass(VinePodBombEntity.class, getCaster().getBoundingBox().inflate(128, 128, 128));
					list.removeIf(summon -> summon.getOwner() != getCaster());
					list.removeIf(summon -> !summon.isNotPersistent());
					return super.canUse() && list.size() < getSpellLevel();
				}
				return super.canUse();
			}
			public SpellSchool getSpellSchool() {
				return SpellSchool.CONJURATION;
			}
		};
	}
	//八面威风
	public static MagicSpell VineheartBomb(int n, LivingEntity caster, Entity target) {
		return new MagicSpell(n, caster, target, MagicType.TARGET, MagicType.MAIN, "vineheart_bomb", JerotesVillageParticleTypes.VINEHEART_BOMB_DISPLAY.get(), JerotesVillageSoundEvents.MAGIC_VINEHEART_BOMB) {
			public boolean spellFindUse() {
				return OtherSpellFind.VineheartBomb(getCaster(), (getTarget() instanceof LivingEntity livingEntity) ? livingEntity : getCaster(), getSpellLevel());
			}
			public String getSpellModId() {
				return JerotesVillage.MODID;
			}
			public int getBaseSpellLevel() {
				return 7;
			}
			public float getSpellDistanceBase() {
				return 32;
			}
			public boolean canUseTargetNone() {
				return false;
			}
			public boolean canUse() {
				if (getCaster() != null) {
					List<VineheartBombEntity> list = getCaster().level().getEntitiesOfClass(VineheartBombEntity.class, getCaster().getBoundingBox().inflate(128, 128, 128));
					list.removeIf(summon -> summon.getOwner() != getCaster());
					return super.canUse() && list.size() <= getSpellLevel() * 4;
				}
				return super.canUse();
			}
			public SpellSchool getSpellSchool() {
				return SpellSchool.CONJURATION;
			}
		};
	}
	//召唤波涛人
	public static MagicSpell ConjureBulges(int n, LivingEntity caster, Entity target) {
		return new MagicSpell(n, caster, target, MagicType.SELF, MagicType.MAIN, "conjure_bulges", JerotesVillageParticleTypes.CONJURE_BULGES_DISPLAY.get(), JerotesVillageSoundEvents.MAGIC_CONJURE_BULGES){
			public boolean spellFindUse() {
				List<BulgeEntity> list = getCaster().level().getEntitiesOfClass(BulgeEntity.class, getCaster().getBoundingBox().inflate(128, 128, 128));
				list.removeIf(summon -> summon.getOwner() != getCaster());
				list.removeIf(summon -> !summon.isNotPersistent());
				return OtherSpellFind.ConjureBulges(getCaster(), getSpellLevel() - list.size(), getSpellLevel() - list.size(), 9);
			}
			public String getSpellModId() {
				return JerotesVillage.MODID;
			}
			public int getBaseSpellLevel() {
				return 3;
			}
			public float getSpellDistanceBase() {
				return 9;
			}
			public boolean canUse() {
				if (getCaster() != null) {
					List<BulgeEntity> list = getCaster().level().getEntitiesOfClass(BulgeEntity.class, getCaster().getBoundingBox().inflate(128, 128, 128));
					list.removeIf(summon -> summon.getOwner() != getCaster());
					list.removeIf(summon -> !summon.isNotPersistent());
					return super.canUse() && list.size() < getSpellLevel();
				}
				return super.canUse();
			}
			public SpellSchool getSpellSchool() {
				return SpellSchool.CONJURATION;
			}
		};
	}
	//召唤骇浪
	public static MagicSpell ConjureSeawave(int n, LivingEntity caster, Entity target) {
		return new MagicSpell(n, caster, target, MagicType.TARGET, MagicType.MAIN, "conjure_seawave", JerotesVillageParticleTypes.CONJURE_SEAWAVE_DISPLAY.get(), JerotesVillageSoundEvents.MAGIC_CONJURE_SEAWAVE){
			public boolean spellFindUse() {
				return OtherSpellFind.ConjureSeawave(getCaster(), 1, 1, 18);
			}
			public String getSpellModId() {
				return JerotesVillage.MODID;
			}
			public int getBaseSpellLevel() {
				return 5;
			}
			public float getSpellDistanceBase() {
				return 18;
			}
			public boolean canUseTargetNone() {
				return true;
			}
			public boolean canUse() {
				if (getCaster() != null) {
					List<SeawaveEntity> list = getCaster().level().getEntitiesOfClass(SeawaveEntity.class, getCaster().getBoundingBox().inflate(128.0, 128.0, 128.0));
					list.removeIf(summon -> summon.getOwner() != getCaster());
					list.removeIf(summon -> !summon.isNotPersistent());
					return super.canUse() && list.size() < 2;
				}
				return super.canUse();
			}
			public boolean isConcentration() {
				return true;
			}
			public SpellSchool getSpellSchool() {
				return SpellSchool.CONJURATION;
			}
		};
	}
	//召唤旱火烈焰人
	public static MagicSpell ConjureDroughtBlazes(int n, LivingEntity caster, Entity target) {
		return new MagicSpell(n, caster, target, MagicType.SELF, MagicType.MAIN, "conjure_drought_blazes", JerotesVillageParticleTypes.CONJURE_DROUGHT_BLAZES_DISPLAY.get(), JerotesVillageSoundEvents.MAGIC_CONJURE_DROUGHT_BLAZES){
			public boolean spellFindUse() {
				List<DroughtBlazeEntity> list = getCaster().level().getEntitiesOfClass(DroughtBlazeEntity.class, getCaster().getBoundingBox().inflate(128, 128, 128));
				list.removeIf(summon -> summon.getOwner() != getCaster());
				list.removeIf(summon -> !summon.isNotPersistent());
				return OtherSpellFind.ConjureDroughtBlazes(getCaster(), getSpellLevel() - list.size(), getSpellLevel() - list.size(), 9);
			}
			public String getSpellModId() {
				return JerotesVillage.MODID;
			}
			public int getBaseSpellLevel() {
				return 3;
			}
			public float getSpellDistanceBase() {
				return 9;
			}
			public boolean canUse() {
				if (getCaster() != null) {
					List<DroughtBlazeEntity> list = getCaster().level().getEntitiesOfClass(DroughtBlazeEntity.class, getCaster().getBoundingBox().inflate(128, 128, 128));
					list.removeIf(summon -> summon.getOwner() != getCaster());
					list.removeIf(summon -> !summon.isNotPersistent());
					return super.canUse() && list.size() < getSpellLevel();
				}
				return super.canUse();
			}
			public SpellSchool getSpellSchool() {
				return SpellSchool.CONJURATION;
			}
		};
	}
	//召唤旱火野火
	public static MagicSpell ConjureDroughtWildfire(int n, LivingEntity caster, Entity target) {
		return new MagicSpell(n, caster, target, MagicType.TARGET, MagicType.MAIN, "conjure_drought_wildfire", JerotesVillageParticleTypes.CONJURE_DROUGHT_WILDFIRE_DISPLAY.get(), JerotesVillageSoundEvents.MAGIC_CONJURE_DROUGHT_WILDFIRE){
			public boolean spellFindUse() {
				return OtherSpellFind.ConjureDroughtWildfire(getCaster(), 1, 1, 18);
			}
			public String getSpellModId() {
				return JerotesVillage.MODID;
			}
			public int getBaseSpellLevel() {
				return 5;
			}
			public float getSpellDistanceBase() {
				return 18;
			}
			public boolean canUseTargetNone() {
				return true;
			}
			public boolean canUse() {
				if (getCaster() != null) {
					List<DroughtWildfireEntity> list = getCaster().level().getEntitiesOfClass(DroughtWildfireEntity.class, getCaster().getBoundingBox().inflate(128, 128, 128));
					list.removeIf(summon -> summon.getOwner() != getCaster());
					list.removeIf(summon -> !summon.isNotPersistent());
					return super.canUse() && list.size() < 2;
				}
				return super.canUse();
			}
			public boolean isConcentration() {
				return true;
			}
			public SpellSchool getSpellSchool() {
				return SpellSchool.CONJURATION;
			}
		};
	}
	//呼唤暗乱
	public static MagicSpell CallTheDarisorder(int n, LivingEntity caster, Entity target) {
		return new MagicSpell(n, caster, target, MagicType.TARGET, MagicType.MAIN, "call_the_darisorder", JerotesVillageParticleTypes.CALL_THE_DARISORDER_DISPLAY.get(), JerotesVillageSoundEvents.MAGIC_CALL_THE_DARISORDER){
			public boolean spellFindUse() {
				return OtherSpellFind.CallTheDarisorder(getCaster(), 1, 1, 18);
			}
			public String getSpellModId() {
				return JerotesVillage.MODID;
			}
			public int getBaseSpellLevel() {
				return 7;
			}
			public float getSpellDistanceBase() {
				return 18;
			}
			public boolean canUseTargetNone() {
				return true;
			}
			public boolean canUse() {
				if (getCaster() != null) {
					List<DarisorderEntity> list = getCaster().level().getEntitiesOfClass(DarisorderEntity.class, getCaster().getBoundingBox().inflate(128, 128, 128));
					list.removeIf(summon -> summon.getOwner() != getCaster());
					list.removeIf(summon -> !summon.isNotPersistent());
					return super.canUse() && list.size() < 2;
				}
				return super.canUse();
			}
			public boolean isConcentration() {
				return true;
			}
			public SpellSchool getSpellSchool() {
				return SpellSchool.CONJURATION;
			}
		};
	}
	//恶鳞炸弹
	public static MagicSpell MalignasaurBomb(int n, LivingEntity caster, Entity target) {
		return new MagicSpell(n, caster, target, MagicType.SELF, MagicType.MAIN, "malignasaur_bomb", JerotesVillageParticleTypes.MALIGNASAUR_BOMB_DISPLAY.get(), JerotesVillageSoundEvents.MAGIC_MALIGNASAUR_BOMB){
			public boolean spellFindUse() {
				return OtherSpellFind.MalignasaurBomb(getCaster(), 48, 64, 12, getSpellLevel(), 2 + getSpellLevel() * 0.25f);
			}
			public String getSpellModId() {
				return JerotesVillage.MODID;
			}
			public int getBaseSpellLevel() {
				return 4;
			}
			public float getSpellDistanceBase() {
				return 12;
			}
			public SpellSchool getSpellSchool() {
				return SpellSchool.CONJURATION;
			}
		};
	}
	//错乱放电
	public static MagicSpell ChaoticDischarge(int n, LivingEntity caster, Entity target) {
		return new MagicSpell(n, caster, target, MagicType.TARGET, MagicType.MAIN, "chaotic_discharge", JerotesVillageParticleTypes.CHAOTIC_DISCHARGE_DISPLAY.get(), JerotesVillageSoundEvents.MAGIC_CHAOTIC_DISCHARGE){
			public boolean spellFindUse() {
				return OtherSpellFind.ChaoticDischarge(getCaster(), (getTarget() instanceof LivingEntity livingEntity) ? livingEntity : getCaster(), getSpellLevel(), 12 + ((getSpellLevel() - 3) * 2.25f), getSpellLevel() * 2);
			}
			public String getSpellModId() {
				return JerotesVillage.MODID;
			}
			public int getBaseSpellLevel() {
				return 3;
			}
			public float getSpellDistanceBase() {
				return 12 + ((getSpellLevel() - 3) * 2.25f);
			}
			public boolean canUseTargetNone() {
				return true;
			}
			public SpellSchool getSpellSchool() {
				return SpellSchool.EVOCATION;
			}
		};
	}
	//目光如炬
	public static MagicSpell TorchGaze(int n, LivingEntity caster, Entity target) {
		return new MagicSpell(n, caster, target, MagicType.TARGET, MagicType.MAIN, "torch_gaze", JerotesVillageParticleTypes.TORCH_GAZE_DISPLAY.get(), JerotesVillageSoundEvents.MAGIC_TORCH_GAZE){
			public boolean spellFindUse() {
				return OtherSpellFind.TorchGaze(getCaster(), (getTarget() instanceof LivingEntity livingEntity) ? livingEntity : getCaster(), getSpellLevel(), 36, 9);
			}
			public String getSpellModId() {
				return JerotesVillage.MODID;
			}
			public int getBaseSpellLevel() {
				return 5;
			}
			public float getSpellDistanceBase() {
				return 36;
			}
			public boolean canUseTargetNone() {
				return true;
			}
			public SpellSchool getSpellSchool() {
				return SpellSchool.EVOCATION;
			}
		};
	}
	//土崩瓦解
	public static MagicSpell CollapseZone(int n, LivingEntity caster, Entity target) {
		return new MagicSpell(n, caster, target, MagicType.TARGET, MagicType.MAIN, "collapse_zone", JerotesVillageParticleTypes.COLLAPSE_ZONE_DISPLAY.get(), JerotesVillageSoundEvents.MAGIC_COLLAPSE_ZONE){
			public boolean spellFindUse() {
				return OtherSpellFind.CollapseZone(getCaster(), (getTarget() instanceof LivingEntity livingEntity) ? livingEntity : getCaster(), getSpellLevel(), 36, 9 + (getSpellLevel() - 6) * 3);
			}
			public String getSpellModId() {
				return JerotesVillage.MODID;
			}
			public int getBaseSpellLevel() {
				return 6;
			}
			public float getSpellDistanceBase() {
				return 36;
			}
			public boolean canUseTargetNone() {
				return true;
			}
			public SpellSchool getSpellSchool() {
				return SpellSchool.EVOCATION;
			}
		};
	}
	//召唤飞行火团
	public static MagicSpell ConjureFlyingFireball(int n, LivingEntity caster, Entity target) {
		return new MagicSpell(n, caster, target, MagicType.TARGET, MagicType.MAIN, "conjure_flying_fireball", JerotesVillageParticleTypes.CONJURE_FLYING_FIREBALL_DISPLAY.get(), JerotesVillageSoundEvents.MAGIC_CONJURE_FLYING_FIREBALL){
			public boolean spellFindUse() {
				return OtherSpellFind.ConjureFlyingFireball(getCaster(), (getTarget() instanceof LivingEntity livingEntity) ? livingEntity : getCaster(), 1.5f + ((getSpellLevel() - 2f) * 0.35f), 1, 1, 36);
			}
			public String getSpellModId() {
				return JerotesVillage.MODID;
			}
			public boolean canUseTargetNone() {
				return true;
			}
			public float getSpellDistanceBase() {
				return 36;
			}
			public boolean canUse() {
				if (getCaster() != null) {
					List<FlyingFireballEntity> list = getCaster().level().getEntitiesOfClass(FlyingFireballEntity.class, getCaster().getBoundingBox().inflate(128, 128, 128));
					list.removeIf(summon -> summon.getOwner() != getCaster());
					list.removeIf(summon -> !summon.isNotPersistent());
					return super.canUse() && list.size() < 2;
				}
				return super.canUse();
			}
			public boolean isConcentration() {
				return true;
			}
			public int getBaseSpellLevel() {
				return 2;
			}
			public SpellSchool getSpellSchool() {
				return SpellSchool.CONJURATION;
			}
		};
	}
	//召唤铁齿
	public static MagicSpell ConjureIronTeeth(int n, LivingEntity caster, Entity target) {
		return new MagicSpell(n, caster, target, MagicType.TARGET, MagicType.MAIN, "conjure_iron_teeth", JerotesVillageParticleTypes.CONJURE_IRON_TEETH_DISPLAY.get(), JerotesVillageSoundEvents.MAGIC_CONJURE_IRON_TEETH){
			public boolean spellFindUse() {
				return OtherSpellFind.ConjureIronTeeth(getCaster(), (getTarget() instanceof LivingEntity livingEntity) ? livingEntity : getCaster(), 1, 1, 18);
			}
			public String getSpellModId() {
				return JerotesVillage.MODID;
			}
			public float getSpellDistanceBase() {
				return 18;
			}
			public boolean canUse() {
				if (getCaster() != null) {
					List<IronTeethEntity> list = getCaster().level().getEntitiesOfClass(IronTeethEntity.class, getCaster().getBoundingBox().inflate(128, 128, 128));
					list.removeIf(summon -> summon.getOwner() != getCaster());
					list.removeIf(summon -> !summon.isNotPersistent());
					return super.canUse() && list.size() < getSpellLevel();
				}
				return super.canUse();
			}
			public boolean canUseTargetNone() {
				return true;
			}
			public int getBaseSpellLevel() {
				return 2;
			}
			public SpellSchool getSpellSchool() {
				return SpellSchool.CONJURATION;
			}
		};
	}
	//目眩神驰
	public static MagicSpell DazzlingLightFog(int n, LivingEntity caster, Entity target) {
		return new MagicSpell(n, caster, target, MagicType.TARGET, MagicType.MAIN, "dazzling_light_fog", JerotesVillageParticleTypes.DAZZLING_LIGHT_FOG_DISPLAY.get(), JerotesVillageSoundEvents.MAGIC_DAZZLING_LIGHT_FOG){
			public boolean spellFindUse() {
				return OtherSpellFind.DazzlingLightFog(getCaster(), (getTarget() instanceof LivingEntity livingEntity) ? livingEntity : getCaster(), 6 + (getSpellLevel() - 1) * 3, 6 + (getSpellLevel() - 1) * 3, (int)Math.ceil((getSpellLevel()) / 2.0), 6 + (getSpellLevel() - 1) * 3, (int)Math.ceil((getSpellLevel()) / 2.0),  5 + getSpellLevel());
			}
			public String getSpellModId() {
				return JerotesVillage.MODID;
			}
			public float getSpellDistanceBase() {
				return 36;
			}
			public boolean canUseTargetNone() {
				return true;
			}
			public int getBaseSpellLevel() {
				return 5;
			}
			public boolean canUse() {
				boolean bl = false;
				if (getTarget() != null && !getTarget().onGround() && !(getCaster() instanceof Player)) {
					return false;
				}
				return true;
			}
			public SpellSchool getSpellSchool() {
				return SpellSchool.ILLUSION;
			}
		};
	}
	//驱鬼禳灾
	public static MagicSpell UndeadBanishing(int n, LivingEntity caster, Entity target) {
		return new MagicSpell(n, caster, target, MagicType.TARGET, MagicType.MAIN, "undead_banishing", JerotesVillageParticleTypes.UNDEAD_BANISHING_DISPLAY.get(), JerotesVillageSoundEvents.MAGIC_UNDEAD_BANISHING){
			public boolean spellFindUse() {
				return OtherSpellFind.UndeadBanishing(getCaster(), getSpellLevel(), getSpellLevel(), getSpellLevel(), 24);
			}
			public String getSpellModId() {
				return JerotesVillage.MODID;
			}
			public boolean canUseTargetNone() {
				return true;
			}
			public int getBaseSpellLevel() {
				return 5;
			}
			public float getSpellDistanceBase() {
				return 24;
			}
			public SpellSchool getSpellSchool() {
				return SpellSchool.ABJURATION;
			}
		};
	}
	//枯木逢春
	public static MagicSpell SpringRejuvenation(int n, LivingEntity caster, Entity target) {
		return new MagicSpell(n, caster, target, MagicType.SELF, MagicType.MAIN, "spring_rejuvenation", JerotesVillageParticleTypes.SPRING_REJUVENATION_DISPLAY.get(), JerotesVillageSoundEvents.MAGIC_SPRING_REJUVENATION){
			public boolean spellFindUse() {
				return OtherSpellFind.SpringRejuvenation(getCaster(), getSpellLevel(), getSpellLevel(), 12);
			}
			public String getSpellModId() {
				return JerotesVillage.MODID;
			}
			public int getBaseSpellLevel() {
				return 4;
			}
			public float getSpellDistanceBase() {
				return 12;
			}
			public SpellSchool getSpellSchool() {
				return SpellSchool.CONJURATION;
			}
		};
	}
	//摧枯拉朽
	public static MagicSpell WiltMissile(int n, LivingEntity caster, Entity target) {
		return new MagicSpell(n, caster, target, MagicType.TARGET, MagicType.MAIN, "wilt_missile", JerotesVillageParticleTypes.WILT_MISSILE_DISPLAY.get(), JerotesVillageSoundEvents.MAGIC_WILT_MISSILE){
			public boolean spellFindUse() {
				return OtherSpellFind.WiltMissile(getCaster(), (getTarget() instanceof LivingEntity livingEntity) ? livingEntity : getCaster(), getSpellLevel(), 6 + (getSpellLevel() - 1) * 3, 1, getSpellAccuracy(), 1, 1, getSpellLevel(), getCaster() instanceof Player);
			}
			public int getCastingTicks() {
				return (getSpellLevel() - 1) * 5 + 4;
			}
			public boolean CastingAboutBase(int n) {
				if (n % 5 == 0 && n != 0) {
					getCaster().swing(InteractionHand.MAIN_HAND, true);
					Entity entity = getTarget();
					if (getCaster() instanceof Player player && Main.getTargetedEntity(player, getSpellDistance()) != null && canUseToEntity(Main.getTargetedEntity(player, getSpellDistance()))) {
						entity = Main.getTargetedEntity(player, getSpellDistance());
					}
					if (!(entity != null && entity.isAlive())) {
						entity = getCaster();
					}
					OtherSpellFind.WiltMissile(getCaster(), (entity instanceof LivingEntity livingEntity) ? livingEntity : getCaster(), getSpellLevel(), 6 + (getSpellLevel() - 1) * 3, 1, getSpellAccuracy(), 1, 1, 1, getCaster() instanceof Player);
					if (!getCaster().isSilent()) {
						getCaster().level().playSound(null, getCaster().getX(), getCaster().getY(), getCaster().getZ(), getSound(),
								getCaster().getSoundSource(), getSoundLevel(), 1.0F);
					}
				}
				return true;
			}
			public String getSpellModId() {
				return JerotesVillage.MODID;
			}
			public boolean canUseTargetNone() {
				return true;
			}
			public int getBaseSpellLevel() {
				return 7;
			}
			public float getSpellDistanceBase() {
				return 36;
			}
			public SpellSchool getSpellSchool() {
				return SpellSchool.NECROMANCY;
			}
		};
	}
	//维落萨恩的魔能冲爆
	public static MagicSpell VirosaansManaSurge(int n, LivingEntity caster, Entity target) {
		return new MagicSpell(n, caster, target, MagicType.SHOOT, MagicType.MAIN, "virosaans_mana_surge", JerotesVillageParticleTypes.VIROSAANS_MANA_SURGE_DISPLAY.get(), JerotesVillageSoundEvents.MAGIC_VIROSAANS_MANA_SURGE){
			public boolean spellFindUse() {
				return OtherSpellFind.VirosaansManaSurge(getCaster(), getTarget(), getSpellLevel(), getSpellAccuracy(), 1, 5, getCaster() instanceof Player);
			}
			public int getCastingTicks() {
				return 30;
			}
			public boolean CastingAboutBase(int n) {
				if (n == 26
						|| n == 22
						|| n == 18
						|| n == 14
						|| n == 11
						|| n == 8
						|| n == 5
						|| n == 3
						|| n == 1) {
					getCaster().swing(InteractionHand.MAIN_HAND, true);
					OtherSpellFind.VirosaansManaSurge(getCaster(), getTarget(), getSpellLevel(), getSpellAccuracy(), 1, 5, getCaster() instanceof Player);
					if (!getCaster().isSilent()) {
						getCaster().level().playSound(null, getCaster().getX(), getCaster().getY(), getCaster().getZ(), getSound(),
								getCaster().getSoundSource(), getSoundLevel(), 1.0F);
					}
				}
				return true;
			}
			public String getSpellModId() {
				return JerotesVillage.MODID;
			}
			public int getBaseSpellLevel() {
				return 6;
			}
			public float getSpellDistanceBase() {
				return 36;
			}
			public SpellSchool getSpellSchool() {
				return SpellSchool.EVOCATION;
			}
		};
	}
	//维落萨恩的力场球爆
	public static MagicSpell VirosaansForceBurstBall(int n, LivingEntity caster, Entity target) {
		return new MagicSpell(n, caster, target, MagicType.TARGET, MagicType.MAIN, "virosaans_force_burst_ball", JerotesVillageParticleTypes.VIROSAANS_FORCE_BURST_BALL_DISPLAY.get(), JerotesVillageSoundEvents.MAGIC_VIROSAANS_FORCE_BURST_BALL){
			public boolean spellFindUse() {
				return OtherSpellFind.VirosaansForceBurstBall(getCaster(), (getTarget() instanceof LivingEntity livingEntity) ? livingEntity : getCaster(), getSpellLevel());
			}
			public int getBaseSpellLevel() {
				return 7;
			}
			public String getSpellModId() {
				return JerotesVillage.MODID;
			}
			public float getSpellDistanceBase() {
				return 45;
			}
			public boolean canUseTargetNone() {
				return true;
			}
			public SpellSchool getSpellSchool() {
				return SpellSchool.EVOCATION;
			}
		};
	}
	//落地生根
	public static MagicSpell TakeRoot(int n, LivingEntity caster, Entity target) {
		return new MagicSpell(n, caster, target, MagicType.TARGET, MagicType.MAIN, "take_root", JerotesVillageParticleTypes.TAKE_ROOT_DISPLAY.get(), JerotesVillageSoundEvents.MAGIC_TAKE_ROOT){
			public boolean spellFindUse() {
				return OtherSpellFind.TakeRoot(getCaster(), (getTarget() instanceof LivingEntity livingEntity) ? livingEntity : getCaster(), getSpellLevel());
			}
			public int getBaseSpellLevel() {
				return 5;
			}
			public String getSpellModId() {
				return JerotesVillage.MODID;
			}
			public float getSpellDistanceBase() {
				return 45;
			}
			public boolean canUseTargetNone() {
				return true;
			}
			public SpellSchool getSpellSchool() {
				return SpellSchool.EVOCATION;
			}
		};
	}
	public static MagicSpell DeadCenter(int n, LivingEntity caster, Entity target) {
		return new MagicSpell(n, caster, target, MagicType.TARGET, MagicType.MAIN, "dead_center", JerotesVillageParticleTypes.DEAD_CENTER_DISPLAY.get(), JerotesVillageSoundEvents.MAGIC_DEAD_CENTER){
			public boolean spellFindUse() {
				return OtherSpellFind.DeadCenter(getCaster(), (getTarget() instanceof LivingEntity livingEntity) ? livingEntity : null, getSpellLevel(), getSpellDistance());
			}
			public String getSpellModId() {
				return JerotesVillage.MODID;
			}
			public int getBaseSpellLevel() {
				return 5;
			}
			public float getSpellDistanceBase() {
				return 45;
			}
			public SpellSchool getSpellSchool() {
				return SpellSchool.EVOCATION;
			}
		};
	}
}