package com.jerotes.jerotesvillage.mixin;

import com.jerotes.jerotesvillage.init.JerotesVillageBuiltInLootTable;
import com.jerotes.jerotesvillage.init.JerotesVillageDimensions;
import net.minecraft.advancements.CriteriaTriggers;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.stats.Stats;
import net.minecraft.tags.ItemTags;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.ExperienceOrb;
import net.minecraft.world.entity.item.ItemEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.entity.projectile.FishingHook;
import net.minecraft.world.entity.projectile.Projectile;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.storage.loot.LootParams;
import net.minecraft.world.level.storage.loot.LootTable;
import net.minecraft.world.level.storage.loot.parameters.LootContextParamSets;
import net.minecraft.world.level.storage.loot.parameters.LootContextParams;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.event.entity.player.ItemFishedEvent;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import java.util.Collections;
import java.util.Iterator;
import java.util.List;

@Mixin(FishingHook.class)
public abstract class FishHookMixin extends Projectile {
    protected FishHookMixin(EntityType<? extends Projectile> entityType, Level level) {
        super(entityType, level);
    }

//    @Redirect(method = "retrieve", at = @At(value = "INVOKE", target = "Lnet/minecraft/world/level/storage/loot/LootDataManager;getLootTable(Lnet/minecraft/resources/ResourceLocation;)Lnet/minecraft/world/level/storage/loot/LootTable;"))
//    private LootTable modifyFishingLootTable(LootDataManager lootDataManager, ResourceLocation lootTableId) {
//        ServerLevel serverLevel = (ServerLevel) this.level();
//        if (lootTableId.equals(BuiltInLootTables.FISHING) && serverLevel.dimension() == JerotesVillageDimensions.SECOND_ROUND_WORLD) {
//            return lootDataManager.getLootTable(JerotesVillageBuiltInLootTable.FISHING);
//        }
//        return lootDataManager.getLootTable(lootTableId);
//    }


    @Shadow
    public abstract Player getPlayerOwner();
    
    @Shadow
    protected abstract boolean shouldStopFishing(Player player);

    @Shadow
    protected abstract void pullEntity(Entity entity);

    @Shadow
    private Entity hookedIn;
    @Shadow
    private int nibble;
    @Final
    @Shadow
    private int luck;

    @Inject(method = "retrieve", at = @At(value = "HEAD"))
    private void retrieve(ItemStack itemStack, CallbackInfoReturnable<Integer> cir) {
        ServerLevel serverLevel = (ServerLevel) this.level();
        if (serverLevel.dimension() == JerotesVillageDimensions.SECOND_ROUND_WORLD) {
            Player player = this.getPlayerOwner();
            if (!this.level().isClientSide && player != null && !this.shouldStopFishing(player)) {
                int i = 0;
                ItemFishedEvent event = null;
                if (this.hookedIn != null) {
                    this.pullEntity(this.hookedIn);
                    CriteriaTriggers.FISHING_ROD_HOOKED.trigger((ServerPlayer)player, itemStack, (FishingHook) (Object) this, Collections.emptyList());
                    this.level().broadcastEntityEvent(this, (byte)31);
                    i = this.hookedIn instanceof ItemEntity ? 3 : 5;
                } else if (this.nibble > 0) {
                    LootParams lootparams = (new LootParams.Builder((ServerLevel)this.level())).withParameter(LootContextParams.ORIGIN, this.position()).withParameter(LootContextParams.TOOL, itemStack).withParameter(LootContextParams.THIS_ENTITY, this).withParameter(LootContextParams.KILLER_ENTITY, this.getOwner()).withParameter(LootContextParams.THIS_ENTITY, this).withLuck((float)this.luck + player.getLuck()).create(LootContextParamSets.FISHING);
                    LootTable loottable = this.level().getServer().getLootData().getLootTable(JerotesVillageBuiltInLootTable.FISHING);
                    List<ItemStack> list = loottable.getRandomItems(lootparams);
                    event = new ItemFishedEvent(list, this.onGround() ? 2 : 1, (FishingHook) (Object) this);
                    MinecraftForge.EVENT_BUS.post(event);
                    if (event.isCanceled()) {
                        this.discard();
                        cir.setReturnValue(event.getRodDamage());
                    }

                    CriteriaTriggers.FISHING_ROD_HOOKED.trigger((ServerPlayer)player, itemStack, (FishingHook) (Object) this, list);
                    Iterator var8 = list.iterator();

                    while(var8.hasNext()) {
                        ItemStack itemstack = (ItemStack)var8.next();
                        ItemEntity itementity = new ItemEntity(this.level(), this.getX(), this.getY(), this.getZ(), itemstack);
                        double d0 = player.getX() - this.getX();
                        double d1 = player.getY() - this.getY();
                        double d2 = player.getZ() - this.getZ();
                        double d3 = 0.1;
                        itementity.setDeltaMovement(d0 * 0.1, d1 * 0.1 + Math.sqrt(Math.sqrt(d0 * d0 + d1 * d1 + d2 * d2)) * 0.08, d2 * 0.1);
                        this.level().addFreshEntity(itementity);
                        player.level().addFreshEntity(new ExperienceOrb(player.level(), player.getX(), player.getY() + 0.5, player.getZ() + 0.5, this.random.nextInt(6) + 1));
                        if (itemstack.is(ItemTags.FISHES)) {
                            player.awardStat(Stats.FISH_CAUGHT, 1);
                        }
                    }

                    i = 1;
                }

                if (this.onGround()) {
                    i = 2;
                }
                this.discard();
                cir.setReturnValue(event == null ? i : event.getRodDamage());
            } else {
                cir.setReturnValue(0);
            }
            cir.cancel();
        }
    }
}