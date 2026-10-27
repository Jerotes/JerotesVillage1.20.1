package com.jerotes.jerotesvillage.item;

import com.jerotes.jerotes.item.Tool.ItemToolBaseTwoHandedHammer;
import com.jerotes.jerotes.util.AttackFind;
import com.jerotes.jerotes.util.Main;
import com.jerotes.jerotesvillage.init.JerotesVillageItems;
import com.mojang.blaze3d.vertex.PoseStack;
import net.minecraft.ChatFormatting;
import net.minecraft.client.Minecraft;
import net.minecraft.client.player.LocalPlayer;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.MutableComponent;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.HumanoidArm;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Rarity;
import net.minecraft.world.item.Tier;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.HitResult;
import net.minecraftforge.client.extensions.common.IClientItemExtensions;

import java.util.List;
import java.util.function.Consumer;

public class BitterColdHammer extends ItemToolBaseTwoHandedHammer {
	public BitterColdHammer() {
		super(new Tier() {
			public int getUses() {
				return 2000;
			}

			public float getSpeed() {
				return 4f;
			}

			public float getAttackDamageBonus() {
				return 16f;
			}

			public int getLevel() {
				return 4;
			}

			public int getEnchantmentValue() {
				return 14;
			}

			public Ingredient getRepairIngredient() {
				return Ingredient.of(new ItemStack(JerotesVillageItems.BITTER_COLD_FIRM_ICE.get()), new ItemStack(JerotesVillageItems.ICE_ROCK.get()));
			}
		}, -1f, 0.75f - 4f, new Properties().rarity(Rarity.EPIC));
	}

	@Override
	public void attackUse(Entity self, Entity attackTo) {
		if (!attackTo.level().isClientSide && attackTo instanceof LivingEntity livingEntity) {
			if (livingEntity.getTicksFrozen() < 280) {
				livingEntity.setTicksFrozen(280);
			}
		}
	}
	public boolean jerotesSpecialAttackNeed(LivingEntity livingEntity) {
		return super.jerotesSpecialAttackNeed(livingEntity) && !(livingEntity.fallDistance > 1.5f && !livingEntity.isFallFlying());
	}
	public boolean jerotesHasSpecialAttack(LivingEntity livingEntity) {
		return super.jerotesHasSpecialAttack(livingEntity) && !(livingEntity.fallDistance > 1.5f && !livingEntity.isFallFlying());
	}

	@Override
	public void appendHoverText(ItemStack itemStack, Level level, List<Component> list, TooltipFlag tooltipFlag) {
		super.appendHoverText(itemStack, level, list, tooltipFlag);
		list.add(this.getDisplayName().withStyle(ChatFormatting.GRAY));
	}

	public MutableComponent getDisplayName() {
		return Component.translatable(this.getDescriptionId() + ".desc");
	}



	@Override
	public void initializeClient(Consumer<IClientItemExtensions> consumer) {
		consumer.accept(new IClientItemExtensions() {
			private void applyItemArmTransform(PoseStack poseStack, HumanoidArm arm, float equipProcess) {
				int i = arm == HumanoidArm.RIGHT ? 1 : -1;
				poseStack.translate((double)((float)i * 0.56F), (double)(-0.52F + equipProcess * -0.6F), (double)-0.72F);
			}
			//第一人称
			@Override
			public boolean applyForgeHandTransform(PoseStack poseStack, LocalPlayer player, HumanoidArm arm,
												   ItemStack itemInHand, float partialTick, float equipProcess, float swingProcess) {
				int dir = arm == HumanoidArm.RIGHT ? 1 : -1;
				if (player.isUsingItem() && player.getUseItem() == itemInHand) {
					this.applyItemArmTransform(poseStack, arm, 0);
					return true;
				}
				else if (swingProcess > 0.0F) {
					HitResult hitResult = Minecraft.getInstance().hitResult;
					boolean miningBlock = hitResult != null && hitResult.getType() == HitResult.Type.BLOCK;
					if (getAttackType(itemInHand) == 0) {
					    setAttackType(itemInHand, miningBlock ? 4 : Main.randomReach(player.getRandom(), 1, 3));
					}
					if (swingProcess >= 0.98) {
						setAttackType(itemInHand, 0);
					}
					this.applyItemArmTransform(poseStack, arm, 0);
					firstPersonAttack(swingProcess, poseStack, dir, arm, getAttackType(itemInHand));
					return true;
				}
				this.applyItemArmTransform(poseStack, arm, equipProcess);
				return true;
			}
		});
	}
}
