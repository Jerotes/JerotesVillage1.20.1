package com.jerotes.jerotesvillage.item;

import com.jerotes.jerotes.JerotesWarehouse;
import com.jerotes.jerotes.entity.Interface.InventoryEntity;
import com.jerotes.jerotes.item.Interface.CanGetOffHand;
import com.jerotes.jerotes.item.Tool.ItemToolBaseCrossbow;
import com.jerotes.jerotesvillage.JerotesVillage;
import net.minecraft.ChatFormatting;
import net.minecraft.core.registries.Registries;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.MutableComponent;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.tags.TagKey;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.monster.CrossbowAttackMob;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.entity.projectile.AbstractArrow;
import net.minecraft.world.entity.projectile.FireworkRocketEntity;
import net.minecraft.world.entity.projectile.Projectile;
import net.minecraft.world.item.*;
import net.minecraft.world.item.enchantment.EnchantmentHelper;
import net.minecraft.world.item.enchantment.Enchantments;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.Vec3;
import org.joml.Quaternionf;
import org.joml.Quaternionfc;
import org.joml.Vector3f;

import java.util.List;
import java.util.Objects;

public class ShrapnelLauncher extends ItemToolBaseCrossbow implements CanGetOffHand {
	public ShrapnelLauncher() {
		super(new Properties().stacksTo(1).rarity(Rarity.UNCOMMON).durability(450 * 3));
	}
	public int getEnchantmentValue() {
		return 18;
	}

	public boolean getOffHandItem(LivingEntity livingEntity) {
		return livingEntity instanceof InventoryEntity inventoryEntity &&
				inventoryEntity.canUseCrossbow() &&
				livingEntity.getItemInHand(InteractionHand.OFF_HAND).is(TagKey.create(Registries.ITEM, new ResourceLocation(JerotesWarehouse.MODID, "shrapnel_launcher_need")));
	}

	@Override
	public float getShootingPower(ItemStack itemStack) {
		return containsChargedProjectile(itemStack, Items.FIREWORK_ROCKET) ? 5F : 3.3F;
	}
	public float getShootingPowerTwo(ItemStack itemStack, ItemStack itemStack2) {
		if (containsChargedProjectile(itemStack, Items.FIREWORK_ROCKET)) {
			return 5F;
		}
		if (itemStack2.is(TagKey.create(Registries.ITEM, new ResourceLocation(JerotesVillage.MODID, "shrapnel_launcher_need")))) {
			return 4.25F;
		}
		return 3.3f;
	}
	@Override
	public void releaseUsing(ItemStack itemStack, Level level, LivingEntity livingEntity, int n) {
		int n2 = this.getUseDuration(itemStack) - n;
		float f = getPowerForTime(n2, itemStack);
		if (f >= 1.0f && !isCharged(itemStack)) {
			SoundSource soundSource = livingEntity instanceof Player ? SoundSource.PLAYERS : SoundSource.HOSTILE;
			level.playSound(null, livingEntity.getX(), livingEntity.getY(), livingEntity.getZ(), SoundEvents.TNT_PRIMED, soundSource, 1.0f, 1.0f / (level.getRandom().nextFloat() * 0.5f + 1.0f) + 0.2f);
		}
		super.releaseUsing(itemStack, level, livingEntity, n);
	}
	private static float getPowerForTime(int n, ItemStack itemStack) {
		float f = (float)n / (float)getChargeDuration(itemStack);
		if (f > 1.0f) {
			f = 1.0f;
		}
		return f;
	}

	private static AbstractArrow jerotes_villager_$getArrow(Level level, LivingEntity livingEntity, ItemStack itemStack, ItemStack itemStack2) {
		ArrowItem arrowitem = (ArrowItem) (itemStack2.getItem() instanceof ArrowItem ? itemStack2.getItem() : Items.ARROW);
		AbstractArrow abstractarrow = arrowitem.createArrow(level, itemStack2, livingEntity);
		if (livingEntity instanceof Player) {
			abstractarrow.setCritArrow(true);
		}

		abstractarrow.setSoundEvent(SoundEvents.CROSSBOW_HIT);
		abstractarrow.setShotFromCrossbow(true);
		int i = EnchantmentHelper.getItemEnchantmentLevel(Enchantments.PIERCING, itemStack);
		if (i > 0) {
			abstractarrow.setPierceLevel((byte) i);
		}

		return abstractarrow;
	}
	public void customShootProjectile(Level level, LivingEntity livingEntity, InteractionHand interactionHand, ItemStack itemStack, ItemStack itemStack2, float f, boolean bl, float f2, float f3, float f4) {
		Projectile projectile1;
		Projectile projectile2;
		boolean bl2 = itemStack2.is(Items.FIREWORK_ROCKET);
		if (bl2) {
			projectile1 = new FireworkRocketEntity(level, itemStack2, livingEntity, livingEntity.getX(), livingEntity.getEyeY() - 0.15000000596046448, livingEntity.getZ(), true);
			projectile2 = new FireworkRocketEntity(level, itemStack2, livingEntity, livingEntity.getX(), livingEntity.getEyeY() - 0.15000000596046448, livingEntity.getZ(), true);
		}
		else {
			projectile1 = jerotes_villager_$getArrow(level, livingEntity, itemStack, itemStack2);
			projectile2 = jerotes_villager_$getArrow(level, livingEntity, itemStack, itemStack2);
			((AbstractArrow)projectile1).pickup = AbstractArrow.Pickup.CREATIVE_ONLY;
			((AbstractArrow)projectile2).pickup = AbstractArrow.Pickup.CREATIVE_ONLY;
		}

		if (livingEntity instanceof CrossbowAttackMob) {
			CrossbowAttackMob crossbowAttackMob = (CrossbowAttackMob)((Object)livingEntity);
			crossbowAttackMob.shootCrossbowProjectile(Objects.requireNonNull(crossbowAttackMob.getTarget()), itemStack, projectile1, f4);
			crossbowAttackMob.shootCrossbowProjectile(Objects.requireNonNull(crossbowAttackMob.getTarget()), itemStack, projectile2, f4);
		} else {
			Vec3 vec3 = livingEntity.getUpVector(1.0f);
			Quaternionf quaternionf = new Quaternionf().setAngleAxis((double)(f4 * 0.017453292f), vec3.x, vec3.y, vec3.z);
			Vec3 vec32 = livingEntity.getViewVector(1.0f);
			Vector3f vector3f = vec32.toVector3f().rotate((Quaternionfc)quaternionf);
			projectile1.shoot(vector3f.x(), vector3f.y(), vector3f.z(), f2, f3+5);
			projectile2.shoot(vector3f.x(), vector3f.y(), vector3f.z(), f2, f3-5);
		}

		ItemStack itemstackgun1 = livingEntity.getOffhandItem();
		if (itemstackgun1.getItem() instanceof ShrapnelLauncher) {
			ItemStack itemstackgun = livingEntity.getMainHandItem();
			boolean gun = itemstackgun.is(TagKey.create(Registries.ITEM, new ResourceLocation(JerotesWarehouse.MODID, "shrapnel_launcher_need")));
			if (gun) {
				if (livingEntity instanceof Player player && !player.getAbilities().instabuild) {
					itemstackgun.shrink(1);
				}
				level.addFreshEntity(livingEntity);
				level.addFreshEntity(projectile1);
				level.addFreshEntity(projectile2);
				level.playSound(null, livingEntity.getX(), livingEntity.getY(), livingEntity.getZ(), SoundEvents.GENERIC_EXPLODE, SoundSource.PLAYERS, 1.0f, f);
			}
		}
		else{
			ItemStack itemstackgun = livingEntity.getOffhandItem();
			boolean gun = itemstackgun.is(TagKey.create(Registries.ITEM, new ResourceLocation(JerotesWarehouse.MODID, "shrapnel_launcher_need")));
			if (gun) {
				if (livingEntity instanceof Player player && !player.getAbilities().instabuild) {
					itemstackgun.shrink(1);
				}
				level.addFreshEntity(projectile1);
				level.addFreshEntity(projectile2);
				level.playSound(null, livingEntity.getX(), livingEntity.getY(), livingEntity.getZ(), SoundEvents.GENERIC_EXPLODE, SoundSource.PLAYERS, 1.0f, f);
			}
		}
	}
	public boolean useCustomUse() {
		return true;
	}
	public InteractionResultHolder<ItemStack> customUse(Level level, Player player, InteractionHand interactionHand) {
		ItemStack itemStack = player.getItemInHand(interactionHand);
		if (itemStack.getItem() instanceof ShrapnelLauncher shrapnelLauncher) {
			InteractionHand interactionHand2 = InteractionHand.MAIN_HAND;
			if (interactionHand == InteractionHand.MAIN_HAND) {
				interactionHand2 = InteractionHand.OFF_HAND;
			}
			ItemStack itemStack2 = player.getItemInHand(interactionHand2);
			if (CrossbowItem.isCharged(itemStack)) {
				CrossbowItem.performShooting(level, player, interactionHand, itemStack, shrapnelLauncher.getShootingPowerTwo(itemStack, itemStack2), 1.0f);
				CrossbowItem.setCharged(itemStack, false);
				return InteractionResultHolder.consume(itemStack);
			}
			if (!player.getProjectile(itemStack).isEmpty()) {
				if (!CrossbowItem.isCharged(itemStack)) {
					player.startUsingItem(interactionHand);
				}
				return InteractionResultHolder.consume(itemStack);
			}
		}
		return InteractionResultHolder.fail(itemStack);
	}

	//

	@Override
	public void appendHoverText(ItemStack itemStack,Level level, List<Component> list, TooltipFlag tooltipFlag) {
		super.appendHoverText(itemStack, level, list, tooltipFlag);
		list.add(this.getDisplayName_0().withStyle(ChatFormatting.GRAY));
		list.add(this.getDisplayName_1().withStyle(ChatFormatting.GRAY));
	}

	public MutableComponent getDisplayName_0() {
		return Component.translatable(this.getDescriptionId() + ".desc_0");
	}
	public MutableComponent getDisplayName_1() {
		return Component.translatable(this.getDescriptionId() + ".desc_1");
	}

	@Override
	public int getDefaultProjectileRange() {
		return 16;
	}
}

