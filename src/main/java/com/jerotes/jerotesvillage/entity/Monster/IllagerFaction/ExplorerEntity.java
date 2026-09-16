package com.jerotes.jerotesvillage.entity.Monster.IllagerFaction;

import com.google.common.collect.Maps;
import com.jerotes.jerotes.entity.Interface.*;
import com.jerotes.jerotes.entity.Mob.HumanEntity;
import com.jerotes.jerotes.goal.*;
import com.jerotes.jerotes.init.JerotesGameRules;
import com.jerotes.jerotes.init.JerotesMobEffects;
import com.jerotes.jerotes.init.JerotesSoundEvents;
import com.jerotes.jerotes.item.Tool.ItemToolBaseCrossbow;
import com.jerotes.jerotes.item.Tool.ItemToolBaseDagger;
import com.jerotes.jerotes.spell.MagicType;
import com.jerotes.jerotes.spell.SpellTypeInterface;
import com.jerotes.jerotes.util.AttackFind;
import com.jerotes.jerotes.util.Main;
import com.jerotes.jerotesvillage.init.JerotesVillageItems;
import com.jerotes.jerotesvillage.init.JerotesVillageParticleTypes;
import com.jerotes.jerotesvillage.init.JerotesVillageSoundEvents;
import net.minecraft.ChatFormatting;
import net.minecraft.core.BlockPos;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.chat.Component;
import net.minecraft.network.syncher.EntityDataAccessor;
import net.minecraft.network.syncher.EntityDataSerializers;
import net.minecraft.network.syncher.SynchedEntityData;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.tags.DamageTypeTags;
import net.minecraft.util.Mth;
import net.minecraft.util.RandomSource;
import net.minecraft.util.TimeUtil;
import net.minecraft.util.valueproviders.UniformInt;
import net.minecraft.world.Difficulty;
import net.minecraft.world.DifficultyInstance;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.*;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import net.minecraft.world.entity.ai.attributes.AttributeSupplier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.ai.goal.FloatGoal;
import net.minecraft.world.entity.ai.goal.LookAtPlayerGoal;
import net.minecraft.world.entity.ai.goal.RandomLookAroundGoal;
import net.minecraft.world.entity.ai.goal.RandomStrollGoal;
import net.minecraft.world.entity.ai.goal.target.HurtByTargetGoal;
import net.minecraft.world.entity.ai.goal.target.NearestAttackableTargetGoal;
import net.minecraft.world.entity.animal.IronGolem;
import net.minecraft.world.entity.monster.CrossbowAttackMob;
import net.minecraft.world.entity.monster.Monster;
import net.minecraft.world.entity.monster.RangedAttackMob;
import net.minecraft.world.entity.npc.AbstractVillager;
import net.minecraft.world.entity.npc.InventoryCarrier;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.entity.projectile.AbstractArrow;
import net.minecraft.world.entity.projectile.Projectile;
import net.minecraft.world.entity.projectile.ProjectileUtil;
import net.minecraft.world.entity.raid.Raid;
import net.minecraft.world.entity.raid.Raider;
import net.minecraft.world.item.*;
import net.minecraft.world.item.enchantment.EnchantmentHelper;
import net.minecraft.world.item.enchantment.Enchantments;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.Vec3;
import org.joml.Vector3f;

import javax.annotation.Nullable;
import java.util.*;
import java.util.function.Predicate;

public class ExplorerEntity extends MeleeIllagerEntity implements SpellUseEntity, ShiftKeyDownEntity, WizardEntity, UseDaggerEntity, UseThrowEntity, UseThrownJavelinEntity, InventoryCarrier, InventoryEntity, UseBowEntity, UseCrossbowEntity, UseShieldEntity, JerotesEntity, FactionEntity, CrossbowAttackMob, NeutralMob, RangedAttackMob {
    private static final EntityDataAccessor<Integer> SPELL_TICK = SynchedEntityData.defineId(ExplorerEntity.class, EntityDataSerializers.INT);
    private static final EntityDataAccessor<Integer> KILL_COUNT = SynchedEntityData.defineId(ExplorerEntity.class, EntityDataSerializers.INT);
    private static final EntityDataAccessor<Integer> JUMP_TICK = SynchedEntityData.defineId(ExplorerEntity.class, EntityDataSerializers.INT);
    private static final EntityDataAccessor<Integer> JUMP_USE_TICK = SynchedEntityData.defineId(ExplorerEntity.class, EntityDataSerializers.INT);
    private static final EntityDataAccessor<Integer> KILL_TICK = SynchedEntityData.defineId(ExplorerEntity.class, EntityDataSerializers.INT);
    private static final EntityDataAccessor<Integer> KILL_USE_TICK = SynchedEntityData.defineId(ExplorerEntity.class, EntityDataSerializers.INT);
    private static final EntityDataAccessor<Integer> AOE_TICK = SynchedEntityData.defineId(ExplorerEntity.class, EntityDataSerializers.INT);
    private static final EntityDataAccessor<Integer> AOE_USE_TICK = SynchedEntityData.defineId(ExplorerEntity.class, EntityDataSerializers.INT);
    private static final EntityDataAccessor<Boolean> IS_CHARGING_CROSSBOW = SynchedEntityData.defineId(ExplorerEntity.class, EntityDataSerializers.BOOLEAN);
    private static final UUID ATTACK_DAMAGE_MODIFIER_UUID = UUID.fromString("c41eb6f6-4040-4bf1-b1d3-366a71985f70");
    private static final UUID MAX_HEALTH_MODIFIER_UUID = UUID.fromString("534545d6-2a1b-4822-8a50-984d3939024c");
    private static final UUID MOVEMENT_SPEED_MODIFIER_UUID = UUID.fromString("912560ac-eba5-4eb2-b78d-0eab82b85df0");
    private static final UUID FOLLOW_RANGE_MODIFIER_UUID = UUID.fromString("c9a5be24-8aaa-4f56-b76f-703581b4e337");

    public ExplorerEntity(EntityType<? extends ExplorerEntity> entityType, Level level) {
        super(entityType, level);
        this.xpReward = 20;
    }

    @Override
    public void MobInventoryAddTooltip(List<Component> tooltip, LivingEntity livingEntity) {
        if (this.isKiller) {
            tooltip.add(Component.translatable("message.jerotes.kill_count", this.getKillCount()).
                    withStyle(ChatFormatting.AQUA));
        }
    }

    @Override
    public boolean asUseDagger() {
        return this.isKiller && InventoryEntity.isMeleeWeapon(this, this.getMainHandItem()) && this.tickCount % 120 < 20 && this.getTarget() != null && this.distanceTo(this.getTarget()) <= 2.5;
    }

    @Override
    public boolean baseShouldShiftKeyDown() {
        if (this.getTarget() != null && this.distanceTo(this.getTarget()) <= this.daggerShiftKeyDownReach()) {
            return (this.getMainHandItem().getItem() instanceof ItemToolBaseDagger itemToolBaseDagger && itemToolBaseDagger.canShiftKeyDownUse(this) ||
                    asUseDagger()) && this.onGround();
        }
        return false;
    }
    @Override
    protected void populateDefaultEquipmentSlots(RandomSource randomSource, DifficultyInstance difficultyInstance) {
        this.maybeWearArmor(EquipmentSlot.HEAD, new ItemStack(JerotesVillageItems.EXPLORER_HELMET.get()), randomSource);
        this.maybeWearArmor(EquipmentSlot.CHEST, new ItemStack(JerotesVillageItems.EXPLORER_CHESTPLATE.get()), randomSource);
        this.maybeWearArmor(EquipmentSlot.LEGS, new ItemStack(JerotesVillageItems.EXPLORER_LEGGINGS.get()), randomSource);
        this.maybeWearArmor(EquipmentSlot.FEET, new ItemStack(JerotesVillageItems.EXPLORER_BOOTS.get()), randomSource);
    }

    private void maybeWearArmor(EquipmentSlot equipmentSlot, ItemStack itemStack, RandomSource randomSource) {
        if (randomSource.nextFloat() < 0.3f) {
            this.setItemSlot(equipmentSlot, itemStack);
        }
    }

    @Override
    public IllagerArmPose getArmPose() {
        if (this.isChargingCrossbow()) {
            return IllagerArmPose.CROSSBOW_CHARGE;
        }
        if (this.isHolding(Items.CROSSBOW) || this.isAggressive() && InventoryEntity.isCrossbow(this.getMainHandItem())) {
            return IllagerArmPose.CROSSBOW_HOLD;
        }
        if (this.isAggressive() && InventoryEntity.isBow(this.getMainHandItem())) {
            return IllagerArmPose.BOW_AND_ARROW;
        }
        if (InventoryEntity.isCrossbow(this.getMainHandItem())) {
            return IllagerArmPose.NEUTRAL;
        }
        if (this.isAggressive()) {
            return IllagerArmPose.ATTACKING;
        }
        return super.getArmPose();
    }

    public static AttributeSupplier.Builder createAttributes() {
        AttributeSupplier.Builder builder = Monster.createMonsterAttributes();
        builder = builder.add(Attributes.MOVEMENT_SPEED, 0.35);
        builder = builder.add(Attributes.MAX_HEALTH, 24);
        builder = builder.add(Attributes.ARMOR, 3);
        builder = builder.add(Attributes.ATTACK_DAMAGE, 6);
        builder = builder.add(Attributes.FOLLOW_RANGE, 32);
        return builder;
    }

    @Override
    protected void registerGoals() {
        super.registerGoals();
        this.goalSelector.addGoal(0, new FloatGoal(this));
        this.goalSelector.addGoal(0, new JerotesShiftKeyDownGoal(this));
        this.goalSelector.addGoal(1, new JerotesMainSpellAttackGoal(this, this.getSpellLevel(), 60, 240, 0.5f));
        this.goalSelector.addGoal(1, new JerotesAddSpellAttackGoal(this, this.getSpellLevel(), 180, 240, 0.5f));
        this.goalSelector.addGoal(1, new JerotesCombatIMagicAttackGoal<>(this, 0.5, 15.0f, this.meleeOrRangeDistance()));
        this.goalSelector.addGoal(1, new JerotesCombatIIMagicAttackGoal<>(this, 0.2, true, this.meleeOrRangeDistance()));
        this.goalSelector.addGoal(1, new JerotesCombatIIIMagicAttackGoal<>(this, 0.5, 15.0f, this.meleeOrRangeDistance()));
        this.goalSelector.addGoal(1, new JerotesCombatIVMagicAttackGoal<>(this, 0.5, 15.0f, this.meleeOrRangeDistance()));
        this.goalSelector.addGoal(1, new JerotesRangedBowAttackGoal<ExplorerEntity>(this, 0.5, 20, 15.0f));
        this.goalSelector.addGoal(1, new JerotesRangedCrossbowAttackGoal<ExplorerEntity>(this, 1.0, 15.0f));
        this.goalSelector.addGoal(1, new JerotesRangedThrowAttackGoal<>(this, 0.4, 40, 12.0F));
        this.goalSelector.addGoal(1, new JerotesRangedJavelinAttackGoal<>(this, 1.0, 60, 12.0F, this.meleeOrRangeDistance()));
        this.goalSelector.addGoal(1, new JerotesSpearUseGoal<>(this,  1.0, 1.0, 10.0f, 2.0f));
        this.goalSelector.addGoal(1, new JerotesPikeUseGoal(this,  1.2, true));
        this.goalSelector.addGoal(2, new JerotesMeleeAttackGoal(this, 1.1, true));
        this.goalSelector.addGoal(2, new HoldGroundAttackGoal(this,  10.0f));
        this.goalSelector.addGoal(3, new RandomStrollGoal(this, 0.6));
        this.goalSelector.addGoal(4, new LookAtPlayerGoal(this, Player.class, 3.0f, 1.0f));
        this.goalSelector.addGoal(4, new LookAtPlayerGoal(this, HumanEntity.class, 3.0f, 1.0f));
        this.goalSelector.addGoal(4, new LookAtPlayerGoal(this, Mob.class, 8.0f));
        this.goalSelector.addGoal(5, new RandomLookAroundGoal(this));
        this.targetSelector.addGoal(1, new HurtByTargetGoal(this, Raider.class).setAlertOthers(new Class[0]));
        this.targetSelector.addGoal(2, new NearestAttackableTargetGoal<Player>(this, Player.class, true){
            @Override
            public boolean canUse() {
                if (ExplorerEntity.this.level().getDifficulty() == Difficulty.PEACEFUL) {
                    return false;
                }
                return super.canUse();
            }
        });
        this.targetSelector.addGoal(3, new NearestAttackableTargetGoal<AbstractVillager>(this, AbstractVillager.class, true));
        this.targetSelector.addGoal(3, new NearestAttackableTargetGoal<HumanEntity>(this, HumanEntity.class, true));
        this.targetSelector.addGoal(3, new NearestAttackableTargetGoal<IronGolem>(this, IronGolem.class, true));
        this.targetSelector.addGoal(4, new ExplorerKillerAttackGoal(this));
    }
    static class ExplorerKillerAttackGoal extends NearestAttackableTargetGoal<LivingEntity> {
        public ExplorerKillerAttackGoal(ExplorerEntity explorer) {
            super(explorer, LivingEntity.class, 0, true, true, LivingEntity::attackable);
        }

        @Override
        public boolean canUse() {
            if (((ExplorerEntity)this.mob).isKiller && super.canUse()) {
                return this.target != null && (!(Main.canSee(this.mob,this.target)) || this.mob.isInvisible());
            }
            return false;
        }

        @Override
        public void start() {
            super.start();
            this.mob.setNoActionTime(0);
        }
    }

    @Override
    public void performRangedAttack(LivingEntity livingEntity, float f) {
        ItemStack handItem = this.getMainHandItem();
        if ((this.getMainHandItem().isEmpty() || InventoryEntity.isMeleeWeapon(this.getMainHandItem())) && !this.getOffhandItem().isEmpty()) {
            handItem = this.getOffhandItem();
        }
        if (InventoryEntity.isRangeJavelin(handItem)) {
            useTridentShoot(this, livingEntity);
        }
        if (InventoryEntity.isBow(handItem)) {
            useBowShoot(this, livingEntity, f, getBowLevel(), 20);
        }
        if (InventoryEntity.isThrow(handItem)) {
            useThrowShoot(this, livingEntity);
            if (!this.isSilent()) {
                this.level().playSound(null, this.getX(), this.getY(), this.getZ(), JerotesSoundEvents.ITEM_THROW, this.getSoundSource(), 0.5f, 0.4f / (this.level().getRandom().nextFloat() * 0.4f + 0.8f));
            }
        }
        if (InventoryEntity.isCrossbow(handItem)) {
            useCrossbowShoot(this, 3.15F);
        }
        if (JerotesGameRules.JEROTES_RANGE_CAN_BREAK != null && this.level().getLevelData().getGameRules().getBoolean(JerotesGameRules.JEROTES_RANGE_CAN_BREAK)) {
            if (handItem.getItem() instanceof BowItem || handItem.getItem() instanceof TridentItem) {
                handItem.hurtAndBreak(1, this, player -> player.broadcastBreakEvent((this.getMainHandItem().isEmpty() || InventoryEntity.isMeleeWeapon(this.getMainHandItem())) && !this.getOffhandItem().isEmpty() ? EquipmentSlot.OFFHAND : EquipmentSlot.MAINHAND));
            }
        }
    }

    @Override
    public ItemStack getProjectile(ItemStack itemStack) {
        if (itemStack.getItem() instanceof ProjectileWeaponItem projectileWeaponItem) {
            Predicate<ItemStack> predicate = projectileWeaponItem.getSupportedHeldProjectiles();
            ItemStack itemStack2 = ProjectileWeaponItem.getHeldProjectile(this, predicate);
            if (predicate.test(this.getMainHandItem())) {
                itemStack2 = this.getMainHandItem();
            }
            else if (predicate.test(this.getOffhandItem())) {
                itemStack2 = this.getOffhandItem();
            }
            else {
                for (int n = 0; n < this.inventoryCount(); ++n) {
                    ItemStack finds = this.mobInventory().getItem(n);
                    if (predicate.test(finds)) {
                        itemStack2 = finds;
                        break;
                    }
                }
            }
            return itemStack2.isEmpty() ? new ItemStack(Items.ARROW) : itemStack2;
        }
        return ItemStack.EMPTY;
    }
    @Override
    public void shootCrossbowProjectile(LivingEntity livingEntity, ItemStack itemStack, Projectile projectile, float f) {
        this.shootCrossbowProjectile(this, livingEntity, projectile, f, itemStack.getItem() instanceof ItemToolBaseCrossbow itemToolBaseCrossbow ? itemToolBaseCrossbow.getShootingPower(itemStack) : 3.15f);
    }
    @Override
    public void shootCrossbowProjectile(LivingEntity livingEntity, LivingEntity livingEntity2, Projectile projectile, float f, float f2) {
        double d0 = livingEntity2.getX() - livingEntity.getX();
        double d1 = livingEntity2.getZ() - livingEntity.getZ();
        double d2 = Math.sqrt(d0 * d0 + d1 * d1);
        double d3 = livingEntity2.getY(0.3333333333333333) - projectile.getY() + d2 * 0.1;
        Vector3f vector3f = this.getProjectileShotVector(livingEntity, new Vec3(d0, d3, d1), f);
        projectile.shoot((double)vector3f.x(), (double)vector3f.y(), (double)vector3f.z(), f2, Math.max(0, 20 - getBowLevel()) / 5f);
        livingEntity.playSound(SoundEvents.CROSSBOW_SHOOT, 1.0F, 1.0F / (livingEntity.getRandom().nextFloat() * 0.4F + 0.8F));
    }

    @Override
    public void onCrossbowAttackPerformed() {
        this.noActionTime = 0;
    }
    @Override
    public AbstractArrow getCustomArrow(ItemStack itemStack, float f) {
        return ProjectileUtil.getMobArrow(this, itemStack, f);
    }

    @Override
    public boolean isChargingCrossbow() {
        return this.entityData.get(IS_CHARGING_CROSSBOW);
    }

    @Override
    public void setChargingCrossbow(boolean bl) {
        this.entityData.set(IS_CHARGING_CROSSBOW, bl);
    }

    @Override
    public boolean canFireProjectileWeapon(ProjectileWeaponItem projectileWeaponItem) {
        return projectileWeaponItem instanceof BowItem || projectileWeaponItem instanceof CrossbowItem || super.canFireProjectileWeapon(projectileWeaponItem);
    }

    @Override
    public boolean canUseCrossbow() {
        return true;
    }
    @Override
    public boolean canUseThrow() {
        return true;
    }
    @Override
    public boolean canUseRangeJavelin() {
        return true;
    }
    @Override
    public boolean canUseBow() {
        return true;
    }
    @Override
    protected SoundEvent getAmbientSound() {
        return JerotesVillageSoundEvents.EXPLORER_AMBIENT;
    }
    @Override
    protected SoundEvent getDeathSound() {
        return JerotesVillageSoundEvents.EXPLORER_DEATH;
    }
    @Override
    protected SoundEvent getHurtSound(DamageSource damageSource) {
        if (this.isDamageSourceBlocked(damageSource)) {
            return SoundEvents.SHIELD_BLOCK;
        }
        if (damageSource.is(DamageTypeTags.BYPASSES_SHIELD) && this.isBlocking()) {
            return SoundEvents.SHIELD_BLOCK;
        }
        return JerotesVillageSoundEvents.EXPLORER_HURT;
    }
    @Override
    public SoundEvent getCelebrateSound() {
        return JerotesVillageSoundEvents.EXPLORER_CHEER;
    }
    @Override
    public void applyRaidBuffs(int n, boolean bl) {
        boolean bl2;
        ItemStack itemStack = new ItemStack(Items.IRON_SWORD);
        Raid raid = this.getCurrentRaid();
        int n2 = 1;
        if (n > raid.getNumGroups(Difficulty.NORMAL)) {
            n2 = 2;
        }
        boolean bl3 = bl2 = this.random.nextFloat() <= raid.getEnchantOdds();
        if (bl2) {
            HashMap hashMap = Maps.newHashMap();
            hashMap.put(Enchantments.SHARPNESS, n2);
            EnchantmentHelper.setEnchantments(hashMap, itemStack);
        }
        this.setItemSlot(EquipmentSlot.MAINHAND, itemStack);
    }
    @Override
    public boolean isCustomNameVisible() {
        if (this.isKiller) {
            return true;
        }
        return super.isCustomNameVisible();
    }
    @Override
    public boolean isSilent() {
        if (this.isKiller && this.isInvisible()) {
            return true;
        }
        return super.isSilent();
    }
    @Override
    public void travel(Vec3 vec3) {
        super.travel(vec3);
        if (this.getKillUseTick() > 0 || this.getAoeUseTick() > 0) {
            this.setDeltaMovement(this.getDeltaMovement().multiply(0.05d, 1d, 0.05d));
        }
    }

    private static final String TAG_KILLER = "Killer";
    public boolean isKiller;
    public int getKillCount() {
        return this.getEntityData().get(KILL_COUNT);
    }
    public void setKillCount(int n) {
        this.getEntityData().set(KILL_COUNT, n);
    }
    public int getJumpTick() {
        return this.getEntityData().get(JUMP_TICK);
    }
    public void setJumpTick(int n) {
        this.getEntityData().set(JUMP_TICK, n);
    }
    public int getJumpUseTick() {
        return this.getEntityData().get(JUMP_USE_TICK);
    }
    public void setJumpUseTick(int n) {
        this.getEntityData().set(JUMP_USE_TICK, n);
    }
    public int getKillTick() {
        return this.getEntityData().get(KILL_TICK);
    }
    public void setKillTick(int n) {
        this.getEntityData().set(KILL_TICK, n);
    }
    public int getKillUseTick() {
        return this.getEntityData().get(KILL_USE_TICK);
    }
    public void setKillUseTick(int n) {
        this.getEntityData().set(KILL_USE_TICK, n);
    }
    public int getAoeTick() {
        return this.getEntityData().get(AOE_TICK);
    }
    public void setAoeTick(int n) {
        this.getEntityData().set(AOE_TICK, n);
    }
    public int getAoeUseTick() {
        return this.getEntityData().get(AOE_USE_TICK);
    }
    public void setAoeUseTick(int n) {
        this.getEntityData().set(AOE_USE_TICK, n);
    }
    //
    public boolean specialAction() {
        return this.getJumpUseTick() > 0
                || this.getKillUseTick() > 0
                || this.getAoeUseTick() > 0;
    }
    //
    @Override
    public void setCustomName(@Nullable Component component) {
        super.setCustomName(component);
        if (component != null && (component.getString().equals("Killer") || component.getString().equals("Assassin")) != isKiller) {
            if (!this.level().isClientSide()) {
                this.setKillCount(0);
            }
            this.getAttribute(Attributes.MAX_HEALTH).removeModifier(MAX_HEALTH_MODIFIER_UUID);
            this.getAttribute(Attributes.ATTACK_DAMAGE).removeModifier(ATTACK_DAMAGE_MODIFIER_UUID);
            this.getAttribute(Attributes.MOVEMENT_SPEED).removeModifier(MOVEMENT_SPEED_MODIFIER_UUID);
            this.getAttribute(Attributes.FOLLOW_RANGE).removeModifier(FOLLOW_RANGE_MODIFIER_UUID);
        }
        this.isKiller = component != null && component.getString().equals(TAG_KILLER);
    }
    @Override
    public void SpellUseAfterAttack(String string, MagicType magicType, MagicType magicType2) {
        if (!this.level().isClientSide()) {
            this.setSpellTick(10);
        }
    }
    @Override
    public List<SpellTypeInterface> SelfMainSpellList() {
        return new ArrayList<>();
    }
    @Override
    public List<SpellTypeInterface> SelfAddSpellList() {
        return new ArrayList<>();
    }
    public int spellLevel = 1;
    @Override
    public int getSpellLevel() {
        return this.spellLevel;
    }
    public void setSpellTick(int n){
        this.getEntityData().set(SPELL_TICK, n);
    }
    public int getSpellTick(){
        return this.getEntityData().get(SPELL_TICK);
    }
    public boolean isMagicUseStyle() {
        return this.getSpellTick() > 0;
    }
    @Override
    public boolean isSpellHumanoid() {
        return true;
    }
    @Override
    public void addAdditionalSaveData(CompoundTag compoundTag) {
        super.addAdditionalSaveData(compoundTag);
        if (this.isKiller) {
            compoundTag.putBoolean(TAG_KILLER, true);
        }
        compoundTag.putInt("SpellLevel", this.spellLevel);
        compoundTag.putInt("SpellTick", this.getSpellTick());
        compoundTag.putInt("KillCount", this.getKillCount());
        compoundTag.putInt("JumpTick", this.getJumpTick());
        compoundTag.putInt("JumpUseTick", this.getJumpUseTick());
        compoundTag.putInt("KillTick", this.getKillTick());
        compoundTag.putInt("KillUseTick", this.getKillUseTick());
        compoundTag.putInt("AoeTick", this.getAoeTick());
        compoundTag.putInt("AoeUseTick", this.getAoeUseTick());
        this.addPersistentAngerSaveData(compoundTag);
    }
    @Override
    public void readAdditionalSaveData(CompoundTag compoundTag) {
        this.setKillCount(compoundTag.getInt("KillCount"));
        this.addKillerAbout();
        super.readAdditionalSaveData(compoundTag);
        if (compoundTag.contains(TAG_KILLER, 99)) {
            this.isKiller = compoundTag.getBoolean(TAG_KILLER);
        }
        this.spellLevel = compoundTag.getInt("SpellLevel");
        this.setSpellTick(compoundTag.getInt("SpellTick"));
        this.setJumpTick(compoundTag.getInt("JumpTick"));
        this.setJumpUseTick(compoundTag.getInt("JumpUseTick"));
        this.setKillTick(compoundTag.getInt("KillTick"));
        this.setKillUseTick(compoundTag.getInt("KillUseTick"));
        this.setAoeTick(compoundTag.getInt("AoeTick"));
        this.setAoeUseTick(compoundTag.getInt("AoeUseTick"));
        this.readPersistentAngerSaveData((Level)this.level(), compoundTag);
    }
    @Override
    protected void defineSynchedData() {
        super.defineSynchedData();
        this.getEntityData().define(SPELL_TICK, 0);
        this.getEntityData().define(KILL_COUNT,0);
        this.getEntityData().define(JUMP_TICK, 0);
        this.getEntityData().define(JUMP_USE_TICK, 0);
        this.getEntityData().define(KILL_TICK, 0);
        this.getEntityData().define(KILL_USE_TICK, 0);
        this.getEntityData().define(AOE_TICK, 0);
        this.getEntityData().define(AOE_USE_TICK, 0);
        this.getEntityData().define(IS_CHARGING_CROSSBOW, false);
    }

    @Override
    protected InteractionResult mobInteract(Player player, InteractionHand interactionHand) {
        if (!this.isAggressive() && this.isKiller) {
            if (!this.level().isClientSide() && player instanceof ServerPlayer serverPlayer && interactionHand == InteractionHand.MAIN_HAND) {
                serverPlayer.sendSystemMessage(Component.literal("Kill Count " + this.getKillCount()).withStyle(ChatFormatting.RED));
            }
        }
        return super.mobInteract(player, interactionHand);
    }

    @Override
    public void aiStep() {
        super.aiStep();
        if (!this.level().isClientSide) {
            this.updatePersistentAnger((ServerLevel)this.level(), true);
        }
        //法术
        if (!this.level().isClientSide()) {
            this.setSpellTick(Math.max(0, this.getSpellTick() - 1));
        }
        //技能
        if (this.isKiller && this.isAlive()) {
            //跳跃
            {
                //开始
                if (this.getJumpTick() >= 240 && this.spellNeed(0, 32, 2)) {
                    if (!this.level().isClientSide()) {
                        this.setJumpUseTick(30);
                        this.setJumpTick(0);
                    }
                }
                if (this.getJumpUseTick() == 30) {
                    if (!this.isSilent()) {
                        this.playSound(JerotesVillageSoundEvents.EXPLORER_JUMP, 5.0f, 1.0f);
                    }
                    if (this.level() instanceof ServerLevel serverLevel) {
                        if (!this.isInvisible()) {
                            serverLevel.sendParticles(JerotesVillageParticleTypes.LEAPING_SLAYER_DISPLAY.get(), this.getX(), this.getBoundingBox().maxY + 0.5, this.getZ(), 0, 0.0, 0.0, 0.0, 0.0);
                        }
                    }
                }
                if (this.getJumpUseTick() >= 10) {
                    if (this.getTarget() != null) {
                        this.getLookControl().setLookAt(this.getTarget(), 360f, 360f);
                        this.lookAt(this.getTarget(), 360.0f, 360.0f);
                    }
                    this.getNavigation().stop();
                }
                if (this.getJumpUseTick() == 10) {
                    this.RushAttack();
                }
            }
            //突刺
            {
                //开始
                if (this.getKillTick() >= 360 && this.spellNeed(0, 16, 2)) {
                    if (!this.level().isClientSide()) {
                        this.setKillUseTick(40);
                        this.setKillTick(0);
                    }
                }
                if (this.getKillUseTick() == 40) {
                    if (!this.isSilent()) {
                        this.playSound(JerotesVillageSoundEvents.EXPLORER_KILL, 5.0f, 1.0f);
                    }
                    if (this.level() instanceof ServerLevel serverLevel) {
                        if (!this.isInvisible()) {
                            serverLevel.sendParticles(JerotesVillageParticleTypes.PIERCING_SLAYER_DISPLAY.get(), this.getX(), this.getBoundingBox().maxY + 0.5, this.getZ(), 0, 0.0, 0.0, 0.0, 0.0);
                        }
                    }
                }
                if (this.getKillUseTick() >= 40) {
                    if (this.getTarget() != null) {
                        this.getLookControl().setLookAt(this.getTarget(), 360f, 360f);
                        this.lookAt(this.getTarget(), 360.0f, 360.0f);
                    }
                    this.getNavigation().stop();
                }
                if (this.getKillUseTick() < 30 && this.getKillUseTick() >= 5) {
                    this.addEffect(new MobEffectInstance(JerotesMobEffects.TRUE_INVISIBLE.get(), 5, 0, false, false), this);
                }
                if (this.getKillUseTick() == 10) {
                    LivingEntity target = this.getTarget();
                    if (target != null && this.level() instanceof ServerLevel serverLevel) {
                        Vec3 targetPos = target.position();
                        Vec3 lookVec = this.getLookAngle().normalize();
                        Vec3 dashEndPos = targetPos.add(lookVec.scale(6.0));

                        Vec3 startPos = this.position();

                        drawDashPath(serverLevel, startPos, dashEndPos, true);

                        this.teleportTo(dashEndPos.x, dashEndPos.y, dashEndPos.z);
                        damageEntitiesAlongPath(serverLevel, startPos, dashEndPos);

                        if (!this.isSilent()) {
                            this.playSound(SoundEvents.PLAYER_ATTACK_STRONG, 1.0f, 0.8f);
                        }
                    }
                }
                if (this.getKillUseTick() == 2) {
                    if (!this.onGround() || !this.level().getBlockState(this.getOnPos().above()).isAir() || !this.level().getBlockState(this.getOnPos().above().above()).isAir()) {
                        BlockPos blockPos = Main.findSpawnPositionNearFillOnBlock(this, 3);
                        this.teleportTo(blockPos.getX(), blockPos.getY(), blockPos.getZ());
                    }
                }
            }
            //瞬杀
            {
                //开始
                if (this.getAoeTick() >= 360 && this.spellNeed(0, 16, 2)) {
                    if (!this.level().isClientSide()) {
                        this.setAoeUseTick(40);
                        this.setAoeTick(0);
                    }
                }
                if (this.getAoeUseTick() == 40) {
                    if (!this.isSilent()) {
                        this.playSound(JerotesVillageSoundEvents.EXPLORER_AOE, 5.0f, 1.0f);
                    }
                    if (this.level() instanceof ServerLevel serverLevel) {
                        if (!this.isInvisible()) {
                            serverLevel.sendParticles(JerotesVillageParticleTypes.INSTANT_SLAYER_DISPLAY.get(), this.getX(), this.getBoundingBox().maxY + 0.5, this.getZ(), 0, 0.0, 0.0, 0.0, 0.0);
                        }
                    }
                }
                if (this.getAoeUseTick() < 30 && this.getAoeUseTick() >= 5) {
                    this.addEffect(new MobEffectInstance(JerotesMobEffects.TRUE_INVISIBLE.get(), 5, 0, false, false), this);
                }
                if (this.getAoeUseTick() < 25 && this.getAoeUseTick() >= 10) {

                    for (int i2 = 0; i2 <= 256; i2++) {
                        Vec3 vec3 = new Vec3(this.getX() + (this.getRandom().nextFloat() - 0.5f) * 2f * 10,
                                this.getY() + 0.1,
                                this.getZ() + (this.getRandom().nextFloat() - 0.5f) * 2f * 10);
                        if (vec3.distanceTo(this.getPosition(0)) <= 10) {
                            if (this.level() instanceof ServerLevel serverLevel) {
                                serverLevel.sendParticles(
                                        ParticleTypes.ENCHANTED_HIT,
                                        vec3.x(),
                                        vec3.y(),
                                        vec3.z(),
                                        0,
                                        0, 0.0125, 0, 1
                                );
                                serverLevel.sendParticles(
                                        ParticleTypes.CRIT,
                                        vec3.x(),
                                        vec3.y(),
                                        vec3.z(),
                                        0,
                                        0, 0.0125, 0, 1
                                );
                            }
                        }
                    }
                }
                //记录攻击
                if (this.getAoeUseTick() == 16) {
                    List<LivingEntity> list = this.level().getEntitiesOfClass(LivingEntity.class, this.getAttackBoundingBox().deflate(9));
                    list.removeIf(livingEntity -> AttackFind.FindCanNotAttack(this, livingEntity));
                    aoeList = list;
                }
                if (this.getAoeUseTick() <= 15 && this.getAoeUseTick() > 5) {
                    if (aoeList != null && !aoeList.isEmpty() &&  aoeList.get(0) != null) {
                        LivingEntity hurt = aoeList.get(0);
                        AttackFind.attackBegin(this, hurt);
                        AttackFind.attackAfter(this, hurt, 1.5f, 1.0f, false, 0f);
                        aoeList.remove(hurt);
                        if (this.level() instanceof ServerLevel serverLevel) {
                            drawDashPath(serverLevel, this.getPosition(0), hurt.getPosition(0), false);
                        }
                        this.teleportTo(hurt.getX(), hurt.getY(), hurt.getZ());
                    }
                }
            }
            if (!this.level().isClientSide()) {
                if (this.isAggressive()) {
                    this.setJumpTick(Math.min(240, this.getJumpTick() + 1));
                }
                else {
                    this.setJumpTick(0);
                }
                this.setJumpUseTick(Math.max(0, this.getJumpUseTick() - 1));
                this.setKillTick(Math.min(360, this.getKillTick() + 1));
                this.setKillUseTick(Math.max(0, this.getKillUseTick() - 1));
                this.setAoeTick(Math.min(480, this.getAoeTick() + 1));
                this.setAoeUseTick(Math.max(0, this.getAoeUseTick() - 1));
            }
        }
    }
    //法术前提
    public boolean spellNeed(float min, float max, int time) {
        return this.getSpellTick() <= 0 && !this.specialAction()
                && this.isAlive() && !this.isNoAi() && this.getTarget() != null && this.getTarget().isAlive()
                && this.getTarget().distanceTo(this) > min && this.getTarget().distanceTo(this) < max
                && this.getRandom().nextInt(time * 20) == 1;
    }

    List<LivingEntity> aoeList;

    public boolean RushAttack() {
        float f = this.getYRot();
        float f2 = this.getXRot();
        float f3 = -Mth.sin(f * 0.017453292f) * Mth.cos(f2 * 0.017453292f);
        float f4 = -Mth.sin(f2 * 0.017453292f);
        float f5 = Mth.cos(f * 0.017453292f) * Mth.cos(f2 * 0.017453292f);
        float f6 = Mth.sqrt(f3 * f3 + f4 * f4 + f5 * f5);
        float f7 = 0.5f;
        this.setDeltaMovement(f3 *= f7 / f6 * 2, f4 *= f7 / f6 * 2 + 0.5f, f5 *= f7 / f6 * 2);
        return true;
    }

    // 绘制冲刺路径粒子效果
    private void drawDashPath(ServerLevel level, Vec3 start, Vec3 end, boolean type) {
        double distance = start.distanceTo(end);
        int particleCount = (int) (distance * 5);

        if (type) {
            for (int i = 0; i <= particleCount; i++) {
                for (int i2 = 0; i2 <= 64; i2++) {
                    double progress = (double) i / particleCount;
                    double x = Mth.lerp(progress, start.x, end.x);
                    double y = Mth.lerp(progress, start.y, end.y) + 1.25; // 提高0.5格避免埋入地面
                    double z = Mth.lerp(progress, start.z, end.z);

                    level.sendParticles(
                            JerotesVillageParticleTypes.BLOODY_SCREAM.get(),
                            x + (this.getRandom().nextFloat() - 0.5f) * 0.25f, y + (this.getRandom().nextFloat() - 0.5f) * 0.25f, z + (this.getRandom().nextFloat() - 0.5f) * 0.25f,
                            0,
                            (this.getRandom().nextFloat() - 0.5f) * 0.15f, (this.getRandom().nextFloat() - 0.5f) * 0.15f, (this.getRandom().nextFloat() - 0.5f) * 0.15f, 0.125
                    );
                }
            }
        }
        else {
            for (int i = 0; i <= particleCount; i++) {
                for (int i2 = 0; i2 <= 64; i2++) {
                    double progress = (double) i / particleCount;
                    double x = Mth.lerp(progress, start.x, end.x);
                    double y = Mth.lerp(progress, start.y, end.y) + 1.25; // 提高0.5格避免埋入地面
                    double z = Mth.lerp(progress, start.z, end.z);

                    level.sendParticles(
                            ParticleTypes.ENCHANTED_HIT,
                            x + (this.getRandom().nextFloat() - 0.5f) * 0.05f, y + (this.getRandom().nextFloat() - 0.5f) * 0.05f, z + (this.getRandom().nextFloat() - 0.5f) * 0.05f,
                            0,
                            (this.getRandom().nextFloat() - 0.5f) * 0.15f, (this.getRandom().nextFloat() - 0.5f) * 0.15f, (this.getRandom().nextFloat() - 0.5f) * 0.15f, 0.0125
                    );
                }
            }
            for (int i = 0; i <= particleCount; i++) {
                for (int i2 = 0; i2 <= 64; i2++) {
                    double progress = (double) i / particleCount;
                    double x = Mth.lerp(progress, start.x, end.x);
                    double y = Mth.lerp(progress, start.y, end.y) + 1.25; // 提高0.5格避免埋入地面
                    double z = Mth.lerp(progress, start.z, end.z);

                    level.sendParticles(
                            JerotesVillageParticleTypes.BLAMER_SOUL.get(),
                            x + (this.getRandom().nextFloat() - 0.5f) * 0.05f, y + (this.getRandom().nextFloat() - 0.5f) * 0.05f, z + (this.getRandom().nextFloat() - 0.5f) * 0.05f,
                            0,
                            (this.getRandom().nextFloat() - 0.5f) * 0.15f, (this.getRandom().nextFloat() - 0.5f) * 0.15f, (this.getRandom().nextFloat() - 0.5f) * 0.15f, 0.0125
                    );
                }
            }
        }
    }

    private void damageEntitiesAlongPath(ServerLevel level, Vec3 start, Vec3 end) {
        double radius = 1.5;
        AABB pathBox = new AABB(
                Math.min(start.x, end.x) - radius,
                Math.min(start.y, end.y) - radius,
                Math.min(start.z, end.z) - radius,
                Math.max(start.x, end.x) + radius,
                Math.max(start.y, end.y) + radius,
                Math.max(start.z, end.z) + radius
        );

        List<LivingEntity> list = this.level().getEntitiesOfClass(LivingEntity.class, pathBox);

        for (LivingEntity hurt : list) {
            if (hurt == null) continue;
            if (AttackFind.FindCanNotAttack(this, hurt)) continue;
            if (!this.hasLineOfSight(hurt)) continue;
            if (isEntityOnPath(hurt.position(), start, end, radius)) {
                AttackFind.attackBegin(this, hurt);
                boolean bl = AttackFind.attackAfter(this, hurt, 1.25f, 3.0f, false, 0f);
                if (bl) {
                    level.sendParticles(
                            ParticleTypes.CRIT,
                            hurt.getX(), hurt.getY() + hurt.getBbHeight() / 2, hurt.getZ(),
                            5,
                            this.getRandom().nextFloat() - 0.5f, 0.3, this.getRandom().nextFloat() - 0.5f,
                            0.1
                    );
                }
            }
        }
    }
    private boolean isEntityOnPath(Vec3 entityPos, Vec3 pathStart, Vec3 pathEnd, double radius) {
        Vec3 pathVec = pathEnd.subtract(pathStart);
        Vec3 entityToStart = entityPos.subtract(pathStart);

        double pathLength = pathVec.length();
        double dot = entityToStart.dot(pathVec) / (pathLength * pathLength);
        dot = Mth.clamp(dot, 0.0, 1.0);
        Vec3 closestPoint = pathStart.add(pathVec.scale(dot));
        return entityPos.distanceTo(closestPoint) <= radius;
    }

    @Override
    public boolean doHurtTarget(Entity entity) {
        boolean bl = super.doHurtTarget(entity);
        if (bl) {
            if (!this.isSilent()) {
                this.playSound(JerotesVillageSoundEvents.EXPLORER_ATTACK, 1.0f, 1.0f);
            }
            if (this.isKiller && entity instanceof LivingEntity livingEntity) {
                if (!livingEntity.level().isClientSide) {
                    livingEntity.addEffect(new MobEffectInstance(MobEffects.WITHER, 200, 0, false, false), this);
                    livingEntity.addEffect(new MobEffectInstance(MobEffects.DARKNESS, 200, 0, false, false), this);
                }
                if (!this.level().isClientSide) {
                    this.addEffect(new MobEffectInstance(MobEffects.DAMAGE_RESISTANCE, 72000, 1, false, false), this);
                    this.addEffect(new MobEffectInstance(MobEffects.DAMAGE_BOOST, 72000, 2, false, false), this);
                    this.addEffect(new MobEffectInstance(MobEffects.BAD_OMEN, 72000, 4, false, false), this);
                    this.addEffect(new MobEffectInstance(MobEffects.HEAL, 1, 1, false, false), this);
                }
                if (!this.level().isClientSide()) {
                    this.setJumpTick(0);
                }
            }
        }
        return bl;
    }

    @Override
    public boolean isInvulnerableTo(DamageSource damageSource) {
        if (this.isKiller && (damageSource.is(DamageTypeTags.IS_FALL)
        || AttackFind.isWitherDamage(damageSource)))
            return true;
        return super.isInvulnerableTo(damageSource);
    }
    @Override
    public boolean hurt(DamageSource damageSource, float amount) {
        if (isInvulnerableTo(damageSource)) {
            return super.hurt(damageSource, amount);
        }
        boolean bl = super.hurt(damageSource, amount);
        if (bl) {
            if (!this.level().isClientSide()) {
                this.setJumpTick(this.getJumpTick() + (int) amount);
            }
        }
        return bl;
    }

    @Override
    public boolean canBeAffected(MobEffectInstance mobEffectInstance) {
        if (this.isKiller) {
            if (mobEffectInstance.getEffect() == MobEffects.WITHER) {
                return false;
            }
            if (mobEffectInstance.getEffect() == MobEffects.DARKNESS) {
                return false;
            }
        }
        return super.canBeAffected(mobEffectInstance);
    }

    @Override
    public void awardKillScore(Entity entity, int score, DamageSource damageSource) {
        super.awardKillScore(entity, score, damageSource);
        if (this.isKiller) {
            if (!this.level().isClientSide()) {
                this.addEffect(new MobEffectInstance(MobEffects.ABSORPTION, 1200, 2, false, false), this);
                this.addEffect(new MobEffectInstance(MobEffects.HEALTH_BOOST, 1200, 3, false, false), this);
                this.addEffect(new MobEffectInstance(MobEffects.REGENERATION, 120, 2, false, false), this);
                this.addEffect(new MobEffectInstance(JerotesMobEffects.TRUE_INVISIBLE.get(), 240, 0, false, false), this);
                this.addEffect(new MobEffectInstance(MobEffects.MOVEMENT_SPEED, 1200, 1, false, false), this);
                this.setKillCount(this.getKillCount() + 1);
            }
            this.addKillerAbout();
            if (entity instanceof LivingEntity livingEntity) {
                this.heal(livingEntity.getMaxHealth() / 10);
                for (MobEffectInstance mobEffectInstance : livingEntity.getActiveEffects()) {
                    if (!mobEffectInstance.getEffect().isBeneficial()) continue;
                    if (!this.level().isClientSide()) {
                        this.addEffect(mobEffectInstance, this);
                    }
                }
            }
        }
    }

    public void addKillerAbout() {
        if (!(this.level()).isClientSide) {
            int n = Math.min(this.getKillCount(), 666);
            Objects.requireNonNull(this.getAttribute(Attributes.MAX_HEALTH)).removeModifier(MAX_HEALTH_MODIFIER_UUID);
            Objects.requireNonNull(this.getAttribute(Attributes.ATTACK_DAMAGE)).removeModifier(ATTACK_DAMAGE_MODIFIER_UUID);
            Objects.requireNonNull(this.getAttribute(Attributes.MOVEMENT_SPEED)).removeModifier(MOVEMENT_SPEED_MODIFIER_UUID);
            Objects.requireNonNull(this.getAttribute(Attributes.FOLLOW_RANGE)).removeModifier(FOLLOW_RANGE_MODIFIER_UUID);
            Objects.requireNonNull(this.getAttribute(Attributes.MAX_HEALTH)).addTransientModifier(new AttributeModifier(MAX_HEALTH_MODIFIER_UUID, "Killer max health", 0.5F * n, AttributeModifier.Operation.ADDITION));
            Objects.requireNonNull(this.getAttribute(Attributes.ATTACK_DAMAGE)).addTransientModifier(new AttributeModifier(ATTACK_DAMAGE_MODIFIER_UUID, "Killer attack damage", 0.05f * n, AttributeModifier.Operation.ADDITION));
            Objects.requireNonNull(this.getAttribute(Attributes.MOVEMENT_SPEED)).addTransientModifier(new AttributeModifier(MOVEMENT_SPEED_MODIFIER_UUID, "Killer movement speed", 0.0005f * n, AttributeModifier.Operation.ADDITION));
            Objects.requireNonNull(this.getAttribute(Attributes.FOLLOW_RANGE)).addTransientModifier(new AttributeModifier(FOLLOW_RANGE_MODIFIER_UUID, "Killer follow range", 0.1f * n, AttributeModifier.Operation.ADDITION));
        }
    }

    @Override
    public ItemStack createSpawnWeapon(float weaponRandom) {
        if (weaponRandom > 0.85f) {
            return new ItemStack(Items.CROSSBOW);
        }
        return new ItemStack(Items.IRON_SWORD);
    }

    private static final UniformInt PERSISTENT_ANGER_TIME = TimeUtil.rangeOfSeconds(20, 39);
    private int remainingPersistentAngerTime;
    private UUID persistentAngerTarget;

    @Override
    public void startPersistentAngerTimer() {
        this.setRemainingPersistentAngerTime(PERSISTENT_ANGER_TIME.sample(this.random));
    }

    @Override
    public void setRemainingPersistentAngerTime(int n) {
        this.remainingPersistentAngerTime = n;
    }

    @Override
    public int getRemainingPersistentAngerTime() {
        return this.remainingPersistentAngerTime;
    }

    @Override
    public void setPersistentAngerTarget(UUID uUID) {
        this.persistentAngerTarget = uUID;
    }

    @Override
    public UUID getPersistentAngerTarget() {
        return this.persistentAngerTarget;
    }
}
