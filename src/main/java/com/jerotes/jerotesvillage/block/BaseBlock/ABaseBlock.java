package com.jerotes.jerotesvillage.block.BaseBlock;

import com.jerotes.jerotesvillage.init.JerotesVillageBlockEntityType;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.core.particles.SimpleParticleType;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.util.RandomSource;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.AxeItem;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.*;
import net.minecraft.world.level.block.entity.*;
import net.minecraft.world.level.block.grower.OakTreeGrower;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.properties.BlockSetType;
import net.minecraft.world.level.block.state.properties.Property;
import net.minecraft.world.level.block.state.properties.WoodType;
import net.minecraft.world.level.gameevent.GameEvent;
import net.minecraft.world.level.material.MapColor;
import net.minecraft.world.level.material.PushReaction;
import net.minecraft.world.phys.BlockHitResult;
import org.jetbrains.annotations.NotNull;
//1.20.4↑
//import net.minecraft.util.ColorRGBA;
//import net.minecraft.world.level.block.grower.TreeGrower;

import javax.annotation.Nullable;

import static net.minecraft.advancements.CriteriaTriggers.ITEM_USED_ON_BLOCK;

public class ABaseBlock{
    public static class ABaseButtonBlock extends ButtonBlock {
        public ABaseButtonBlock(BlockSetType blockSetType, int n, Properties properties) {
            //1.20.4↑//
            //super(blockSetType, n, properties);
            //1.20.1//
            super(properties, blockSetType, n, false);
        }
    }

    public static class ABaseDoorBlock extends DoorBlock {
        public ABaseDoorBlock(BlockSetType blockSetType, Properties properties) {
            //1.20.4↑//
            //super(blockSetType, properties);
            //1.20.1//
            super(properties, blockSetType);
        }
    }

//1.20.4↑
//    public static class ABaseFallingBlock extends ColoredFallingBlock {
//        public ABaseFallingBlock(int colorRGBA, Properties properties) {
//            super(new ColorRGBA(colorRGBA), properties);
//        }
//    }
//1.20.1
   public static class ABaseFallingBlock extends SandBlock {
        public ABaseFallingBlock(int colorRGBA, Properties properties) {
            super(colorRGBA, properties);
        }
    }

    public static class ABaseBrushableBlock extends BrushableBlock {
        public ABaseBrushableBlock(Block block, SoundEvent soundEvent, SoundEvent soundEvent2, Properties properties) {
            //1.20.4↑//
            //super(block, soundEvent, soundEvent2, properties);
            //1.20.1//
            super(block, properties, soundEvent, soundEvent2);
        }

        public void tick(BlockState blockState, ServerLevel serverLevel, BlockPos blockPos, RandomSource randomSource) {
            BlockEntity var6 = serverLevel.getBlockEntity(blockPos);
            if (var6 instanceof ABaseBrushableBlockEntity brushableBlockEntity) {
                brushableBlockEntity.checkReset();
            }
            super.tick(blockState, serverLevel, blockPos, randomSource);
        }

        @Nullable
        public BlockEntity newBlockEntity(BlockPos blockPos, BlockState blockState) {
            return new ABaseBrushableBlockEntity(blockPos, blockState);
        }
    }

    public static class ABaseBrushableBlockEntity extends BrushableBlockEntity {
        public ABaseBrushableBlockEntity(BlockPos blockPos, BlockState blockState) {
            super(blockPos, blockState);
        }

        @Override
        public @NotNull BlockEntityType<? extends ABaseBrushableBlockEntity> getType(){
            return JerotesVillageBlockEntityType.ABASE_BRUSHABLE_BLOCK.get();
        }
    }

    public static class ABaseFence extends FenceBlock {
        public ABaseFence(Properties properties) {
            super(properties);
        }
    }

    public static class ABaseFenceCanFire extends ABaseFence {
        public ABaseFenceCanFire(Properties properties) {
            super(properties);
        }

        @Override
        public int getFlammability(BlockState state, BlockGetter world, BlockPos pos, Direction face) {
            return 5;
        }
        @Override
        public int getFireSpreadSpeed(BlockState state, BlockGetter world, BlockPos pos, Direction face) {
            return 20;
        }
    }

    public static class ABaseFenceGate extends FenceGateBlock {
        public ABaseFenceGate(WoodType woodType, Properties properties) {
            //1.20.4↑//
            //super(woodType, properties);
            //1.20.1//
            super(properties, woodType);
        }
    }

    public static class ABaseFenceGateCanFire extends ABaseFenceGate {
        public ABaseFenceGateCanFire(WoodType woodType, Properties properties) {
            super(woodType, properties);
        }

        @Override
        public int getFlammability(BlockState state, BlockGetter world, BlockPos pos, Direction face) {
            return 5;
        }
        @Override
        public int getFireSpreadSpeed(BlockState state, BlockGetter world, BlockPos pos, Direction face) {
            return 20;
        }
    }

    public static class ABaseLeavesBlock extends LeavesBlock {
        public ABaseLeavesBlock(Properties properties) {
            super(properties);
        }

        @Override
        public int getFlammability(BlockState state, BlockGetter world, BlockPos pos, Direction face) {
            return 30;
        }
        @Override
        public int getFireSpreadSpeed(BlockState state, BlockGetter world, BlockPos pos, Direction face) {
            return 60;
        }
    }

    public static class ABaseLogCanFire extends RotatedPillarBlock {
        public ABaseLogCanFire(Properties properties) {
            super(properties);
        }

        @Override
        public int getFlammability(BlockState state, BlockGetter world, BlockPos pos, Direction face) {
            return 5;
        }
        @Override
        public int getFireSpreadSpeed(BlockState state, BlockGetter world, BlockPos pos, Direction face) {
            return 5;
        }
    }

    public static class ABaseLogCanUseAxe extends ABaseLogCanFire {
        private final Block potted;

        public ABaseLogCanUseAxe(Block block, Properties properties) {
            super(properties);
            this.potted = block;
        }

        @Override
        public InteractionResult use(BlockState blockstate, Level level, BlockPos pos, Player player, InteractionHand hand, BlockHitResult hit) {
            if (player.getItemInHand(hand).getItem() instanceof AxeItem) {
                if (level.isClientSide) {
                    return InteractionResult.SUCCESS;
                }

                ItemStack itemStack = player.getItemInHand(hand);

                BlockState newState = potted.defaultBlockState();

                for (Property<?> property : blockstate.getProperties()) {
                    if (newState.hasProperty(property)) {
                        newState = copyProperty(blockstate, newState, property);
                    }
                }

                if (player instanceof ServerPlayer) {
                    ITEM_USED_ON_BLOCK.trigger((ServerPlayer) player, pos, itemStack);
                }

                level.playSound(player, pos, SoundEvents.AXE_STRIP, SoundSource.BLOCKS, 1.0f, 1.0f);
                level.setBlock(pos, newState, 11);
                level.gameEvent(GameEvent.BLOCK_CHANGE, pos, GameEvent.Context.of(player, newState));
                itemStack.hurtAndBreak(1, player, livingEntity -> livingEntity.broadcastBreakEvent(hand));

                return InteractionResult.CONSUME;
            }
            return super.use(blockstate, level, pos, player, hand, hit);
        }

        private static <T extends Comparable<T>> BlockState copyProperty(BlockState from, BlockState to, Property<T> property) {
            return to.setValue(property, from.getValue(property));
        }
    }

    public static class ABasePlankCanFire extends Block {
        public ABasePlankCanFire(Properties properties) {
            super(properties);
        }

        @Override
        public int getFlammability(BlockState state, BlockGetter world, BlockPos pos, Direction face) {
            return 5;
        }
        @Override
        public int getFireSpreadSpeed(BlockState state, BlockGetter world, BlockPos pos, Direction face) {
            return 20;
        }
    }

    public static class ABasePressurePlateBlock extends PressurePlateBlock {
        public ABasePressurePlateBlock(BlockSetType blockSetType, Properties properties) {
            //1.20.4↑//
            //super(blockSetType, properties);
            //1.20.1//
            super(PressurePlateBlock.Sensitivity.EVERYTHING, properties, blockSetType);
        }
    }

    public static class ABaseSapling extends SaplingBlock {
        public ABaseSapling() {
            //1.20.4↑//
            //super(TreeGrower.OAK, Properties.of().mapColor(MapColor.PLANT).noCollission().randomTicks().instabreak().sound(SoundType.GRASS).pushReaction(PushReaction.DESTROY));
            //1.20.1//
            super(new OakTreeGrower(), Properties.of().mapColor(MapColor.PLANT).noCollission().randomTicks().instabreak().sound(SoundType.GRASS).pushReaction(PushReaction.DESTROY));
        }
    }

    public static class ABaseSlab extends SlabBlock {
        public ABaseSlab(Properties properties) {
            super(properties);
        }
    }

    public static class ABaseSlabCanFire extends ABaseSlab {
        public ABaseSlabCanFire(Properties properties) {
            super(properties);
        }

        @Override
        public int getFlammability(BlockState state, BlockGetter world, BlockPos pos, Direction face) {
            return 5;
        }
        @Override
        public int getFireSpreadSpeed(BlockState state, BlockGetter world, BlockPos pos, Direction face) {
            return 20;
        }
    }

    public static class ABaseStair extends StairBlock {
        public ABaseStair(BlockState blockState, Properties properties) {
            super(blockState, properties);
        }
    }

    public static class ABaseStairCanFire extends ABaseStair {
        public ABaseStairCanFire(BlockState blockState, Properties properties) {
            super(blockState, properties);
        }

        @Override
        public int getFlammability(BlockState state, BlockGetter world, BlockPos pos, Direction face) {
            return 5;
        }
        @Override
        public int getFireSpreadSpeed(BlockState state, BlockGetter world, BlockPos pos, Direction face) {
            return 20;
        }
    }

    public static class ABaseTorch extends TorchBlock {
        public ABaseTorch(SimpleParticleType simpleParticleType, Properties properties) {
            //1.20.4↑//
            //super(simpleParticleType, properties);
            //1.20.1//
            super(properties, simpleParticleType);
        }
    }

    public static class ABaseTrapDoorBlock extends TrapDoorBlock {
        public ABaseTrapDoorBlock(BlockSetType blockSetType, Properties properties) {
            //1.20.4↑//
            //super(blockSetType, properties);
            //1.20.1//
            super(properties, blockSetType);
        }
    }

    public static class ABaseWallBlock extends WallBlock {
        public ABaseWallBlock(Properties properties) {
            super(properties);
        }
    }

    public static class ABaseWallTorch extends WallTorchBlock {
        public ABaseWallTorch(SimpleParticleType simpleParticleType, Properties properties) {
            //1.20.4↑//
            //super(simpleParticleType, properties);
            //1.20.1//
            super(properties, simpleParticleType);
        }
    }

    public static class ABaseCeilingHangingSign extends CeilingHangingSignBlock {
        public ABaseCeilingHangingSign(WoodType woodType, Properties properties) {
            //1.20.4↑//
            //super(woodType, properties);
            //1.20.1//
            super(properties, woodType);
        }

        @Override
        public BlockEntity newBlockEntity(BlockPos pos, BlockState state) {
            return new ABaseHangingSignEntity(pos, state);
        }
    }

    public static class ABaseHangingSignEntity extends HangingSignBlockEntity {
        public ABaseHangingSignEntity(BlockPos blockPos, BlockState blockState) {
            super(blockPos, blockState);
        }

        @Override
        public @NotNull BlockEntityType<? extends ABaseHangingSignEntity> getType(){
            return JerotesVillageBlockEntityType.ABASE_HANGING_SIGN.get();
        }
    }

    public static class ABaseSignEntity extends SignBlockEntity {
        public ABaseSignEntity(BlockPos blockPos, BlockState blockState) {
            super(blockPos, blockState);
        }

        @Override
        public @NotNull BlockEntityType<? extends ABaseSignEntity> getType(){
            return JerotesVillageBlockEntityType.ABASE_SIGN.get();
        }
    }

    public static class ABaseStandingSign extends StandingSignBlock {
        public ABaseStandingSign(WoodType woodType, Properties properties) {
            //1.20.4↑//
            //super(woodType, properties);
            //1.20.1//
            super(properties, woodType);
        }

        @Override
        public BlockEntity newBlockEntity(BlockPos pos, BlockState state) {
            return new ABaseSignEntity(pos, state);
        }
    }

    public static class ABaseWallHangingSign extends WallHangingSignBlock {
        public ABaseWallHangingSign(WoodType woodType, Properties properties) {
            //1.20.4↑//
            //super(woodType, properties);
            //1.20.1//
            super(properties, woodType);
        }

        @Override
        public BlockEntity newBlockEntity(BlockPos pos, BlockState state) {
            return new ABaseHangingSignEntity(pos, state);
        }
    }

    public static class ABaseWallSign extends WallSignBlock {
        public ABaseWallSign(WoodType woodType, Properties properties) {
            //1.20.4↑//
            //super(woodType, properties);
            //1.20.1//
            super(properties, woodType);
        }

        @Override
        public BlockEntity newBlockEntity(BlockPos pos, BlockState state) {
            return new ABaseSignEntity(pos, state);
        }
    }


    public static class ABaseSkullBlock extends SkullBlock {
        private final SoundEvent soundEvent;
        public ABaseSkullBlock(Type type, Properties properties, SoundEvent soundEvent) {
            super(type, properties);
            this.soundEvent = soundEvent;
        }

        @Nullable
        public BlockEntity newBlockEntity(BlockPos blockPos, BlockState blockState) {
            return new ABaseSkullBlockEntity(blockPos, blockState, soundEvent);
        }
    }
    public static class ABaseWallSkullBlock extends WallSkullBlock {
        public ABaseWallSkullBlock(SkullBlock.Type type, Properties properties) {
            super(type, properties);
        }

        @Nullable
        public BlockEntity newBlockEntity(BlockPos blockPos, BlockState blockState) {
            return new ABaseSkullBlockEntity(blockPos, blockState);
        }
    }

    public static class ABaseSkullBlockEntity extends SkullBlockEntity {
        private SoundEvent soundEvent;
        public ABaseSkullBlockEntity(BlockPos blockPos, BlockState blockState) {
            super(blockPos, blockState);
        }
        public ABaseSkullBlockEntity(BlockPos blockPos, BlockState blockState, SoundEvent soundEvent) {
            this(blockPos, blockState);
            this.soundEvent = soundEvent;
        }

        @Nullable
        public ResourceLocation getNoteBlockSound() {
            if (soundEvent != null) {
                return soundEvent.getLocation();
            }
            return super.getNoteBlockSound();
        }

        @Override
        public @NotNull BlockEntityType<? extends ABaseSkullBlockEntity> getType(){
            return JerotesVillageBlockEntityType.ABASE_SKULL_BLOCK.get();
        }
    }

    public static enum Types implements SkullBlock.Type {
        RESURRECTER("resurrecter"),
        SECOND_ROUNDER_ZOMBIE("second_rounder_zombie"),
        SECOND_ROUNDER_SKELETON("second_rounder_skeleton"),
        PURPLE_SAND_CREEPER("purple_sand_creeper"),
        BLAMER("blamer"),
        TANGLER("tangler"),
        SPIRVE("spirve");

        private final String name;

        private Types(String string) {
            this.name = string;
            //1.20.4↑//
            //TYPES.put(string, this);
        }

        public String getSerializedName() {
            return this.name;
        }
    }
}

