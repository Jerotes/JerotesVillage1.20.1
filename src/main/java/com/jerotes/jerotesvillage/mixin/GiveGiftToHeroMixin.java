package com.jerotes.jerotesvillage.mixin;

import com.jerotes.jerotesvillage.config.OtherMainConfig;
import com.jerotes.jerotesvillage.init.JerotesVillageBuiltInLootTable;
import com.jerotes.jerotesvillage.init.JerotesVillageVillagerProfessions;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.ai.behavior.GiveGiftToHero;
import net.minecraft.world.entity.npc.VillagerProfession;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import java.util.Map;

@Mixin(GiveGiftToHero.class)
public abstract class GiveGiftToHeroMixin {
    @Shadow
    @Final
    private static Map<VillagerProfession, ResourceLocation> GIFTS;

//    private static final Map<VillagerProfession, ResourceLocation> JEROTESVILLAGE_GIFTS = Util.make(Maps.newHashMap(), hashMap -> {
//        hashMap.put(JerotesVillageVillagerProfessions.VILLAGER_BARTENDER.get(), JerotesVillageBuiltInLootTable.BARTENDER_GIFT);
//        hashMap.put(JerotesVillageVillagerProfessions.VILLAGER_BEEKEEPER.get(), JerotesVillageBuiltInLootTable.BEEKEEPER_GIFT);
//        hashMap.put(JerotesVillageVillagerProfessions.VILLAGER_BREEDER.get(), JerotesVillageBuiltInLootTable.BREEDER_GIFT);
//        hashMap.put(JerotesVillageVillagerProfessions.VILLAGER_BUILDER.get(), JerotesVillageBuiltInLootTable.BUILDER_GIFT);
//        hashMap.put(JerotesVillageVillagerProfessions.VILLAGER_CAPTAIN.get(), JerotesVillageBuiltInLootTable.CAPTAIN_GIFT);
//        hashMap.put(JerotesVillageVillagerProfessions.VILLAGER_CHEF.get(), JerotesVillageBuiltInLootTable.CHEF_GIFT);
//        hashMap.put(JerotesVillageVillagerProfessions.VILLAGER_COACHMAN.get(), JerotesVillageBuiltInLootTable.COACHMAN_GIFT);
//        hashMap.put(JerotesVillageVillagerProfessions.VILLAGER_CONSCRIPTION.get(), JerotesVillageBuiltInLootTable.CONSCRIPTION_GIFT);
//        hashMap.put(JerotesVillageVillagerProfessions.VILLAGER_FORGER.get(), JerotesVillageBuiltInLootTable.FORGER_GIFT);
//        hashMap.put(JerotesVillageVillagerProfessions.VILLAGER_HAWKER.get(), JerotesVillageBuiltInLootTable.HAWKER_GIFT);
//        hashMap.put(JerotesVillageVillagerProfessions.VILLAGER_HORSEMAN.get(), JerotesVillageBuiltInLootTable.HORSEMAN_GIFT);
//        hashMap.put(JerotesVillageVillagerProfessions.VILLAGER_HUNTER.get(), JerotesVillageBuiltInLootTable.HUNTER_GIFT);
//        hashMap.put(JerotesVillageVillagerProfessions.VILLAGER_ILLAGER.get(), JerotesVillageBuiltInLootTable.ILLAGER_GIFT);
//        hashMap.put(JerotesVillageVillagerProfessions.VILLAGER_JAILER.get(), JerotesVillageBuiltInLootTable.JAILER_GIFT);
//        hashMap.put(JerotesVillageVillagerProfessions.VILLAGER_LAMPLIGHTER.get(), JerotesVillageBuiltInLootTable.LAMPLIGHTER_GIFT);
//        hashMap.put(JerotesVillageVillagerProfessions.VILLAGER_MAGICIAN.get(), JerotesVillageBuiltInLootTable.MAGICIAN_GIFT);
//        hashMap.put(JerotesVillageVillagerProfessions.VILLAGER_DYE_MERCHANT.get(), JerotesVillageBuiltInLootTable.DYE_MERCHANT_GIFT);
//        hashMap.put(JerotesVillageVillagerProfessions.VILLAGER_MESSENGER.get(), JerotesVillageBuiltInLootTable.MESSENGER_GIFT);
//        hashMap.put(JerotesVillageVillagerProfessions.VILLAGER_OBSERVER.get(), JerotesVillageBuiltInLootTable.OBSERVER_GIFT);
//        hashMap.put(JerotesVillageVillagerProfessions.VILLAGER_PHYSICIAN.get(), JerotesVillageBuiltInLootTable.PHYSICIAN_GIFT);
//        hashMap.put(JerotesVillageVillagerProfessions.VILLAGER_PIGLIN.get(), JerotesVillageBuiltInLootTable.PIGLIN_GIFT);
//        hashMap.put(JerotesVillageVillagerProfessions.VILLAGER_PISCATOR.get(), JerotesVillageBuiltInLootTable.PISCATOR_GIFT);
//        hashMap.put(JerotesVillageVillagerProfessions.VILLAGER_SHOPKEEPER.get(), JerotesVillageBuiltInLootTable.SHOPKEEPER_GIFT);
//        hashMap.put(JerotesVillageVillagerProfessions.VILLAGER_VILLAGEHEAD.get(), JerotesVillageBuiltInLootTable.VILLAGEHEAD_GIFT);
//        hashMap.put(JerotesVillageVillagerProfessions.VILLAGER_WITCH.get(), JerotesVillageBuiltInLootTable.WITCH_GIFT);
//    });

    @Inject(method = "<clinit>", at = @At("TAIL"))
    private static void addToShapeIngredient(CallbackInfo ci) {
        if (OtherMainConfig.NewProfessionVillagerGiveCustomGift) {
            GIFTS.put(JerotesVillageVillagerProfessions.VILLAGER_BARTENDER.get(), JerotesVillageBuiltInLootTable.BARTENDER_GIFT);
            GIFTS.put(JerotesVillageVillagerProfessions.VILLAGER_BEEKEEPER.get(), JerotesVillageBuiltInLootTable.BEEKEEPER_GIFT);
            GIFTS.put(JerotesVillageVillagerProfessions.VILLAGER_BREEDER.get(), JerotesVillageBuiltInLootTable.BREEDER_GIFT);
            GIFTS.put(JerotesVillageVillagerProfessions.VILLAGER_BUILDER.get(), JerotesVillageBuiltInLootTable.BUILDER_GIFT);
            GIFTS.put(JerotesVillageVillagerProfessions.VILLAGER_CAPTAIN.get(), JerotesVillageBuiltInLootTable.CAPTAIN_GIFT);
            GIFTS.put(JerotesVillageVillagerProfessions.VILLAGER_CHEF.get(), JerotesVillageBuiltInLootTable.CHEF_GIFT);
            GIFTS.put(JerotesVillageVillagerProfessions.VILLAGER_COACHMAN.get(), JerotesVillageBuiltInLootTable.COACHMAN_GIFT);
            GIFTS.put(JerotesVillageVillagerProfessions.VILLAGER_CONSCRIPTION.get(), JerotesVillageBuiltInLootTable.CONSCRIPTION_GIFT);
            GIFTS.put(JerotesVillageVillagerProfessions.VILLAGER_FORGER.get(), JerotesVillageBuiltInLootTable.FORGER_GIFT);
            GIFTS.put(JerotesVillageVillagerProfessions.VILLAGER_HAWKER.get(), JerotesVillageBuiltInLootTable.HAWKER_GIFT);
            GIFTS.put(JerotesVillageVillagerProfessions.VILLAGER_HORSEMAN.get(), JerotesVillageBuiltInLootTable.HORSEMAN_GIFT);
            GIFTS.put(JerotesVillageVillagerProfessions.VILLAGER_HUNTER.get(), JerotesVillageBuiltInLootTable.HUNTER_GIFT);
            GIFTS.put(JerotesVillageVillagerProfessions.VILLAGER_ILLAGER.get(), JerotesVillageBuiltInLootTable.ILLAGER_GIFT);
            GIFTS.put(JerotesVillageVillagerProfessions.VILLAGER_JAILER.get(), JerotesVillageBuiltInLootTable.JAILER_GIFT);
            GIFTS.put(JerotesVillageVillagerProfessions.VILLAGER_LAMPLIGHTER.get(), JerotesVillageBuiltInLootTable.LAMPLIGHTER_GIFT);
            GIFTS.put(JerotesVillageVillagerProfessions.VILLAGER_MAGICIAN.get(), JerotesVillageBuiltInLootTable.MAGICIAN_GIFT);
            GIFTS.put(JerotesVillageVillagerProfessions.VILLAGER_DYE_MERCHANT.get(), JerotesVillageBuiltInLootTable.DYE_MERCHANT_GIFT);
            GIFTS.put(JerotesVillageVillagerProfessions.VILLAGER_MESSENGER.get(), JerotesVillageBuiltInLootTable.MESSENGER_GIFT);
            GIFTS.put(JerotesVillageVillagerProfessions.VILLAGER_OBSERVER.get(), JerotesVillageBuiltInLootTable.OBSERVER_GIFT);
            GIFTS.put(JerotesVillageVillagerProfessions.VILLAGER_PHYSICIAN.get(), JerotesVillageBuiltInLootTable.PHYSICIAN_GIFT);
            GIFTS.put(JerotesVillageVillagerProfessions.VILLAGER_PIGLIN.get(), JerotesVillageBuiltInLootTable.PIGLIN_GIFT);
            GIFTS.put(JerotesVillageVillagerProfessions.VILLAGER_PISCATOR.get(), JerotesVillageBuiltInLootTable.PISCATOR_GIFT);
            GIFTS.put(JerotesVillageVillagerProfessions.VILLAGER_SHOPKEEPER.get(), JerotesVillageBuiltInLootTable.SHOPKEEPER_GIFT);
            GIFTS.put(JerotesVillageVillagerProfessions.VILLAGER_VILLAGEHEAD.get(), JerotesVillageBuiltInLootTable.VILLAGEHEAD_GIFT);
            GIFTS.put(JerotesVillageVillagerProfessions.VILLAGER_WITCH.get(), JerotesVillageBuiltInLootTable.WITCH_GIFT);
        }
    }

//    @Inject(method = "getItemToThrow", at = @At("HEAD"), cancellable = true)
//    public void getItemToThrow(@NotNull Villager villager, CallbackInfoReturnable<List<ItemStack>> cir) {
//        if (!villager.isBaby()) {
//            VillagerProfession villagerprofession = villager.getVillagerData().getProfession();
//            if (JEROTESVILLAGE_GIFTS.containsKey(villagerprofession)) {
//                LootTable loottable = Objects.requireNonNull(villager.level().getServer()).getLootData().getLootTable(JEROTESVILLAGE_GIFTS.get(villagerprofession));
//                LootParams lootparams = (new LootParams.Builder((ServerLevel) villager.level())).withParameter(LootContextParams.ORIGIN, villager.position()).withParameter(LootContextParams.THIS_ENTITY, villager).create(LootContextParamSets.GIFT);
//                cir.setReturnValue(loottable.getRandomItems(lootparams));
//            }
//        }
//    }
}