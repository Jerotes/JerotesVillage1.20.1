package com.jerotes.jerotesvillage.item;

import com.google.common.collect.Lists;
import com.jerotes.jerotes.config.MainConfig;
import com.jerotes.jerotes.entity.Interface.UseBowEntity;
import com.jerotes.jerotes.entity.Interface.UseCrossbowEntity;
import com.jerotes.jerotes.item.Interface.NotNormalCrossbow;
import com.jerotes.jerotes.item.Tool.ItemToolBaseCrossbow;
import com.jerotes.jerotesvillage.entity.Shoot.Other.MerorBulletEntity;
import com.jerotes.jerotesvillage.init.JerotesVillageEntityType;
import com.jerotes.jerotesvillage.init.JerotesVillageItems;
import net.minecraft.ChatFormatting;
import net.minecraft.advancements.CriteriaTriggers;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.ListTag;
import net.minecraft.network.chat.CommonComponents;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.MutableComponent;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.stats.Stats;
import net.minecraft.util.RandomSource;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.monster.CrossbowAttackMob;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.entity.projectile.Projectile;
import net.minecraft.world.item.*;
import net.minecraft.world.item.enchantment.EnchantmentHelper;
import net.minecraft.world.item.enchantment.Enchantments;
import net.minecraft.world.level.Level;
import net.minecraftforge.event.ForgeEventFactory;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Predicate;

public class MerorShotgun extends ItemToolBaseCrossbow implements NotNormalCrossbow {
	private static final String TAG_CHARGED = "Charged";
	private static final String TAG_CHARGED_PROJECTILES = "ChargedProjectiles";
	private boolean startSoundPlayed = false;
	private boolean midLoadSoundPlayed = false;

	public MerorShotgun() {
		super(new Properties().stacksTo(1).fireResistant().rarity(Rarity.EPIC).durability(18800 * 3));
	}

	public int getEnchantmentValue() {
		return 28;
	}

	public static final Predicate<ItemStack> MEROR_BULLET = itemStack -> itemStack.is(JerotesVillageItems.MEROR_BULLET.get());
	@Override
	public Predicate<ItemStack> getSupportedHeldProjectiles() {
		return MEROR_BULLET;
	}

	@Override
	public Predicate<ItemStack> getAllSupportedProjectiles() {
		return MEROR_BULLET;
	}

	@Override
	public boolean useCustomShoot() {
		return true;
	}
	public int mobUseAddReach() {
		return 12;
	}
	@Override
	public void customShoot(LivingEntity livingEntity) {
		if (livingEntity instanceof Mob mob && mob.getTarget() != null) {
			float f = 5;
			if (mob instanceof UseBowEntity useBowEntity) {
				f = 20 - useBowEntity.getBowLevel();
			}
			LivingEntity target = mob.getTarget();
			Projectile shoot = new MerorBulletEntity(JerotesVillageEntityType.MEROR_BULLET.get(),mob.level());
			shoot.setOwner(mob);
			shoot.setPos(mob.getX(), (mob.getEyeY() + (mob.getEyeY() + mob.getY()) / 2) / 2, mob.getZ());
			shoot.shoot(target.getX() - mob.getX(), target.getY(0.7) - mob.getY(0.7), target.getZ() - mob.getZ(), 4f, Math.max(0, f) / 5f);
			mob.level().addFreshEntity(shoot);
			if (!mob.isSilent()) {
				mob.level().playSound(null, mob.getX(), mob.getY(), mob.getZ(), SoundEvents.GENERIC_EXPLODE, mob.getSoundSource(), 1.0f, 5f);
			}
		}
	}

	@Override
	public InteractionResultHolder<ItemStack> use(Level level, Player player, InteractionHand interactionHand) {
		ItemStack itemStack = player.getItemInHand(interactionHand);
		if (MerorShotgun.isCharged(itemStack)) {
			MerorShotgun.performShooting(level, player, interactionHand, itemStack, getShootingPower(itemStack), 1.0f);

			if (getBullet(itemStack) > 1) {
				MerorShotgun.setBullet(itemStack, getBullet(itemStack) - 1);
			}
			else {
				MerorShotgun.setBullet(itemStack, 0);
				MerorShotgun.setCharged(itemStack, false);
				MerorShotgun.clearChargedProjectiles(itemStack);
			}
			return InteractionResultHolder.consume(itemStack);
		}
		if (!player.getProjectile(itemStack).isEmpty()) {
			if (!MerorShotgun.isCharged(itemStack)) {
				this.startSoundPlayed = false;
				this.midLoadSoundPlayed = false;
				player.startUsingItem(interactionHand);
			}
			return InteractionResultHolder.consume(itemStack);
		}
		return InteractionResultHolder.fail(itemStack);
	}
	public float getShootingPower(ItemStack itemStack) {
		return 5f;
	}

	@Override
	public void releaseUsing(ItemStack itemStack, Level level, LivingEntity livingEntity, int n) {
		int n2 = this.getUseDuration(itemStack) - n;
		float f = MerorShotgun.getPowerForTime(n2, itemStack);
		if (f >= 1.0f && !MerorShotgun.isCharged(itemStack) && MerorShotgun.tryLoadProjectiles(livingEntity, itemStack)) {
			MerorShotgun.setCharged(itemStack, true);
			SoundSource soundSource = livingEntity instanceof Player ? SoundSource.PLAYERS : SoundSource.HOSTILE;
			level.playSound(null, livingEntity.getX(), livingEntity.getY(), livingEntity.getZ(), SoundEvents.TNT_PRIMED, soundSource, 1.0f, 1.0f / (level.getRandom().nextFloat() * 0.5f + 1.0f) + 0.2f);
		}
	}

	private static boolean tryLoadProjectiles(LivingEntity livingEntity, ItemStack itemStack) {
		MerorShotgun.clearChargedProjectiles(itemStack);
		int n = EnchantmentHelper.getItemEnchantmentLevel(Enchantments.MULTISHOT, itemStack);
		int n2 = n == 0 ? 1 : 3;
		boolean bl = livingEntity instanceof Player && ((Player)livingEntity).getAbilities().instabuild;
		ItemStack itemStack2 = livingEntity.getProjectile(itemStack);
		ItemStack itemStack3 = itemStack2.copy();
		for (int i = 0; i < n2; ++i) {
			if (i > 0) {
				itemStack2 = itemStack3.copy();
			}
			if (itemStack2.isEmpty() && bl) {
				itemStack2 = new ItemStack(JerotesVillageItems.MEROR_BULLET.get());
				itemStack3 = itemStack2.copy();
			}
			if (MerorShotgun.loadProjectile(livingEntity, itemStack, itemStack2, i > 0, bl)) continue;
			return false;
		}
		return true;
	}

	public int maxBullet() {
		return 8;
	}
	private static boolean loadProjectile(LivingEntity livingEntity, ItemStack itemStack, ItemStack itemStack2, boolean bl, boolean bl2) {
		ItemStack itemStack3;
		boolean bl3;
		if (itemStack2.isEmpty()) {
			return false;
		}
		boolean bl4 = bl3 = bl2 && itemStack2.getItem() == JerotesVillageItems.MEROR_BULLET.get();
		if (!(bl3 || bl2 || bl)) {
			if (livingEntity instanceof UseCrossbowEntity && !MainConfig.MobUseCrossbowShrinkArrow) {
				int i = Math.min(1, itemStack2.getCount());
				itemStack3 = itemStack2.copyWithCount(i);
			}
			else {
				itemStack3 = itemStack2.split(1);
			}
			if (itemStack2.isEmpty() && livingEntity instanceof Player) {
				((Player)livingEntity).getInventory().removeItem(itemStack2);
			}
		} else {
			itemStack3 = itemStack2.copy();
		}
		MerorShotgun.setBullet(itemStack,itemStack.getItem() instanceof ItemToolBaseCrossbow itemToolBaseCrossbow ? itemToolBaseCrossbow.maxBullet() : 1);
		MerorShotgun.addChargedProjectile(itemStack, itemStack3);
		return true;
	}

	public static boolean isCharged(ItemStack itemStack) {
		CompoundTag compoundTag = itemStack.getTag();
		return compoundTag != null && compoundTag.getBoolean(TAG_CHARGED);
	}

	public static void setCharged(ItemStack itemStack, boolean bl) {
		CompoundTag compoundTag = itemStack.getOrCreateTag();
		compoundTag.putBoolean(TAG_CHARGED, bl);
	}

	private static void addChargedProjectile(ItemStack itemStack, ItemStack itemStack2) {
		CompoundTag compoundTag = itemStack.getOrCreateTag();
		ListTag listTag = compoundTag.contains(TAG_CHARGED_PROJECTILES, 9) ? compoundTag.getList(TAG_CHARGED_PROJECTILES, 10) : new ListTag();
		CompoundTag compoundTag2 = new CompoundTag();
		itemStack2.save(compoundTag2);
		listTag.add(compoundTag2);
		compoundTag.put(TAG_CHARGED_PROJECTILES, listTag);
	}

	public static List<ItemStack> getChargedProjectiles(ItemStack itemStack) {
		ListTag listTag;
		ArrayList arrayList = Lists.newArrayList();
		CompoundTag compoundTag = itemStack.getTag();
		if (compoundTag != null && compoundTag.contains(TAG_CHARGED_PROJECTILES, 9) && (listTag = compoundTag.getList(TAG_CHARGED_PROJECTILES, 10)) != null) {
			for (int i = 0; i < listTag.size(); ++i) {
				CompoundTag compoundTag2 = listTag.getCompound(i);
				arrayList.add(ItemStack.of(compoundTag2));
			}
		}
		return arrayList;
	}

	public static void clearChargedProjectiles(ItemStack itemStack) {
		CompoundTag compoundTag = itemStack.getTag();
		if (compoundTag != null) {
			ListTag listTag = compoundTag.getList(TAG_CHARGED_PROJECTILES, 9);
			listTag.clear();
			compoundTag.put(TAG_CHARGED_PROJECTILES, listTag);
		}
	}

	public static boolean containsChargedProjectile(ItemStack itemStack2, Item item) {
		return MerorShotgun.getChargedProjectiles(itemStack2).stream().anyMatch(itemStack -> itemStack.is(item));
	}

	public static void shootProjectile(Level level, LivingEntity livingEntity2, InteractionHand interactionHand, ItemStack itemStack, ItemStack itemStack2, float f, boolean bl, float f2, float f3, float f4) {
		Projectile projectile = new MerorBulletEntity(JerotesVillageEntityType.MEROR_BULLET.get(), level);
		if (level.isClientSide) {
			return;
		}
		if (livingEntity2 instanceof CrossbowAttackMob) {
			CrossbowAttackMob crossbowAttackMob = (CrossbowAttackMob)((Object)livingEntity2);
			crossbowAttackMob.shootCrossbowProjectile(crossbowAttackMob.getTarget(), itemStack, projectile, f4);
		}
		else {
			projectile = new MerorBulletEntity(JerotesVillageEntityType.MEROR_BULLET.get(), level);
			projectile.setOwner(livingEntity2);
			projectile.setPos(livingEntity2.getX(), (3 * livingEntity2.getEyeY() + (livingEntity2.getEyeY() + livingEntity2.getY()) / 2) / 4, livingEntity2.getZ());
			projectile.shootFromRotation(livingEntity2, livingEntity2.getXRot(), livingEntity2.getYRot(), 0.0f, 4f, 0.0f);
			level.addFreshEntity(projectile);
		}
		itemStack.hurtAndBreak(1, livingEntity2, livingEntity -> livingEntity.broadcastBreakEvent(interactionHand));
		level.playSound(null, livingEntity2.getX(), livingEntity2.getY(), livingEntity2.getZ(), SoundEvents.GENERIC_EXPLODE, SoundSource.PLAYERS, 1.0f, f);
	}

	public static void performShooting(Level level, LivingEntity livingEntity, InteractionHand interactionHand, ItemStack itemStack, float f, float f2) {
		if (livingEntity instanceof Player player) {
			if (ForgeEventFactory.onArrowLoose(itemStack, livingEntity.level(), player, 1, true) < 0) {
				return;
			}
		}
		List<ItemStack> list = MerorShotgun.getChargedProjectiles(itemStack);
		float[] arrf = MerorShotgun.getShotPitches(livingEntity.getRandom());
		for (int i = 0; i < list.size(); ++i) {
			boolean bl;
			ItemStack itemStack2 = list.get(i);
			boolean bl2 = bl = livingEntity instanceof Player && ((Player)livingEntity).getAbilities().instabuild;
			if (itemStack2.isEmpty()) continue;
			if (i == 0) {
				MerorShotgun.shootProjectile(level, livingEntity, interactionHand, itemStack, itemStack2, arrf[i], bl, f, f2, 0.0f);
				continue;
			}
			if (i == 1) {
				MerorShotgun.shootProjectile(level, livingEntity, interactionHand, itemStack, itemStack2, arrf[i], bl, f, f2, -4.0f);
				continue;
			}
			if (i != 2) continue;
			MerorShotgun.shootProjectile(level, livingEntity, interactionHand, itemStack, itemStack2, arrf[i], bl, f, f2, 4.0f);
		}
		MerorShotgun.onCrossbowShot(level, livingEntity, itemStack);
	}

	public static float[] getShotPitches(RandomSource randomSource) {
		boolean bl = randomSource.nextBoolean();
		return new float[]{1.0f, MerorShotgun.getRandomShotPitch(bl, randomSource), MerorShotgun.getRandomShotPitch(!bl, randomSource)};
	}

	public static float getRandomShotPitch(boolean bl, RandomSource randomSource) {
		float f = bl ? 0.63f : 0.43f;
		return 1.0f / (randomSource.nextFloat() * 0.5f + 1.8f) + f;
	}

	public static void onCrossbowShot(Level level, LivingEntity livingEntity, ItemStack itemStack) {
		if (livingEntity instanceof ServerPlayer) {
			ServerPlayer serverPlayer = (ServerPlayer)livingEntity;
			if (!level.isClientSide) {
				CriteriaTriggers.SHOT_CROSSBOW.trigger(serverPlayer, itemStack);
			}
			serverPlayer.awardStat(Stats.ITEM_USED.get(itemStack.getItem()));
		}
		if (getBullet(itemStack) <= 0) {
			MerorShotgun.clearChargedProjectiles(itemStack);
		}
	}

	@Override
	public void onUseTick(Level level, LivingEntity livingEntity, ItemStack itemStack, int n) {
		if (!level.isClientSide) {
			int n2 = EnchantmentHelper.getItemEnchantmentLevel(Enchantments.QUICK_CHARGE, itemStack);
			SoundEvent soundEvent = this.getStartSound(n2);
			SoundEvent soundEvent2 = n2 == 0 ? SoundEvents.TNT_PRIMED : null;
			float f = (float)(itemStack.getUseDuration() - n) / (float) MerorShotgun.getChargeDuration(itemStack);
			if (f < 0.2f) {
				this.startSoundPlayed = false;
				this.midLoadSoundPlayed = false;
			}
			if (f >= 0.2f && !this.startSoundPlayed) {
				this.startSoundPlayed = true;
				level.playSound(null, livingEntity.getX(), livingEntity.getY(), livingEntity.getZ(), soundEvent, SoundSource.PLAYERS, 0.5f, 1.0f);
			}
			if (f >= 0.5f && soundEvent2 != null && !this.midLoadSoundPlayed) {
				this.midLoadSoundPlayed = true;
				level.playSound(null, livingEntity.getX(), livingEntity.getY(), livingEntity.getZ(), soundEvent2, SoundSource.PLAYERS, 0.5f, 1.0f);
			}
		}
	}

	@Override
	public int getUseDuration(ItemStack itemStack) {
		return MerorShotgun.getChargeDuration(itemStack) + 3;
	}

	public static int getChargeDuration(ItemStack itemStack) {
		int n = EnchantmentHelper.getItemEnchantmentLevel(Enchantments.QUICK_CHARGE, itemStack);
		return n == 0 ? 25 : 25 - 5 * n;
	}
	@Override
	public UseAnim getUseAnimation(ItemStack itemStack) {
		return UseAnim.CROSSBOW;
	}

	private SoundEvent getStartSound(int n) {
		return SoundEvents.TNT_PRIMED;
	}

	private static float getPowerForTime(int n, ItemStack itemStack) {
		float f = (float)n / (float) MerorShotgun.getChargeDuration(itemStack);
		if (f > 1.0f) {
			f = 1.0f;
		}
		return f;
	}

	@Override
	public void appendHoverText(ItemStack itemStack,Level level, List<Component> list, TooltipFlag tooltipFlag) {
		super.appendHoverText(itemStack, level, list, tooltipFlag);
		List<ItemStack> list2 = MerorShotgun.getChargedProjectiles(itemStack);
		if (!MerorShotgun.isCharged(itemStack) || list2.isEmpty()) {
			return;
		}
		ItemStack itemStack2 = list2.get(0);
		if (itemStack2.getItem() == Items.ARROW) {
			itemStack2 = new ItemStack(Items.COPPER_INGOT.asItem());;
		}
		list.add(Component.translatable("item.minecraft.crossbow.projectile").append(CommonComponents.SPACE).append(itemStack2.getDisplayName()));
		if (getBullet(itemStack) != 0) {
			list.add(Component.translatable(String.valueOf(getBullet(itemStack))));
		}
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

	@Override
	public boolean useOnRelease(ItemStack itemStack) {
		return itemStack.is(this);
	}

	@Override
	public int getDefaultProjectileRange() {
		return 16;
	}

}

