package com.jerotes.jerotesvillage.block.PurpleDesert;

import com.jerotes.jerotesvillage.config.OtherMainConfig;
import com.jerotes.jerotesvillage.entity.Animal.PurpleSandRabbitEntity;
import com.jerotes.jerotesvillage.entity.Boss.Biome.PurpleSandHagEntity;
import com.jerotes.jerotesvillage.init.JerotesVillageBlocks;
import com.jerotes.jerotesvillage.init.JerotesVillageEntityType;
import com.jerotes.jerotesvillage.init.JerotesVillagePotions;
import com.jerotes.jerotesvillage.util.SpawnRules;
import net.minecraft.core.BlockPos;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.util.RandomSource;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.MobSpawnType;
import net.minecraft.world.entity.projectile.ThrownPotion;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.alchemy.Potion;
import net.minecraft.world.item.alchemy.PotionUtils;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.AbstractCauldronBlock;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.SoundType;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.material.PushReaction;
import net.minecraft.world.phys.shapes.BooleanOp;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.Shapes;
import net.minecraft.world.phys.shapes.VoxelShape;

public class UnstableHagsCauldron extends Block {
	public UnstableHagsCauldron() {
		super(Properties.of().sound(SoundType.METAL).strength(-1f, 3600000f).lightLevel(s -> 10).pushReaction(PushReaction.BLOCK).noOcclusion());
	}
	private static final VoxelShape INSIDE = AbstractCauldronBlock.box(2.0, 4.0, 2.0, 14.0, 16.0, 14.0);
	protected static final VoxelShape SHAPE = Shapes.join(Shapes.block(), Shapes.or(AbstractCauldronBlock.box(0.0, 0.0, 4.0, 16.0, 3.0, 12.0), AbstractCauldronBlock.box(4.0, 0.0, 0.0, 12.0, 3.0, 16.0), AbstractCauldronBlock.box(2.0, 0.0, 2.0, 14.0, 3.0, 14.0), INSIDE), BooleanOp.ONLY_FIRST);

	@Override
	public VoxelShape getShape(BlockState blockState, BlockGetter blockGetter, BlockPos blockPos, CollisionContext collisionContext) {
		return SHAPE;
	}

	@Override
	public VoxelShape getInteractionShape(BlockState blockState, BlockGetter blockGetter, BlockPos blockPos) {
		return INSIDE;
	}
	@Override
	public void onPlace(BlockState blockstate, Level world, BlockPos pos, BlockState oldState, boolean moving) {
		super.onPlace(blockstate, world, pos, oldState, moving);
		world.scheduleTick(pos, this, 1);
	}

	@Override
	public void randomTick(BlockState blockState, ServerLevel serverLevel, BlockPos pos, RandomSource randomSource) {
		super.randomTick(blockState, serverLevel, pos, randomSource);
		serverLevel.scheduleTick(pos, this, 1);
	}

	private int tran = 0;

	@Override
	public void tick(BlockState blockstate, ServerLevel serverLevel, BlockPos pos, RandomSource random) {
		super.tick(blockstate, serverLevel, pos, random);
		int x = pos.getX();
		int y = pos.getY();
		int z = pos.getZ();

		if (this.tran < 160){
			this.tran += 1;
			serverLevel.sendParticles(ParticleTypes.BUBBLE, x+0.5, y+1, z+0.5, 5, 0.2, 0.1, 0.2, 0.1);
			serverLevel.sendParticles(ParticleTypes.ENTITY_EFFECT, x+0.5, y+1.5, z+0.5, 5, 0.4, 0.7, 0.4, 0.3);
			if (!serverLevel.isClientSide()) {
				serverLevel.playSound(null, BlockPos.containing(x, y, z), SoundEvents.BUBBLE_COLUMN_BUBBLE_POP, SoundSource.BLOCKS, 5, 1);
			}
		}
		if (this.tran < 100){
			Potion potion = JerotesVillagePotions.LOVES_SHAME.get();
			double randoms = Math.random();
			if (randoms > 0.95f) potion = JerotesVillagePotions.WITCH_ROT.get();
			else if (randoms > 0.9f) potion = JerotesVillagePotions.UNEASY_RABBIT.get();
			else if (randoms > 0.85f) potion = JerotesVillagePotions.WITHER_SLEEP.get();
			else if (randoms > 0.8f) potion = JerotesVillagePotions.VOMIT_INDUCER.get();
			else if (randoms > 0.75f) potion = JerotesVillagePotions.LICH_CURSE.get();
			else if (randoms > 0.7f) potion = JerotesVillagePotions.LAST_STAND.get();
			else if (randoms > 0.65f) potion = JerotesVillagePotions.TROLL_TONGUE.get();
			else if (randoms > 0.6f) potion = JerotesVillagePotions.HAUNTING_IMP.get();
			else if (randoms > 0.55f) potion = JerotesVillagePotions.PURPLE_SANDS_SECRET.get();
			else if (randoms > 0.5f) potion = JerotesVillagePotions.SERPENT_SCALE.get();
			else if (randoms > 0.45f) potion = JerotesVillagePotions.DESPICABLE_MEROR.get();
			else if (randoms > 0.4f) potion = JerotesVillagePotions.SEA_MONSTER_WORSHIP.get();
			else if (randoms > 0.35f) potion = JerotesVillagePotions.DARK_HEART.get();
			else if (randoms > 0.3f) potion = JerotesVillagePotions.BINDING_PACT.get();
			else if (randoms > 0.25f) potion = JerotesVillagePotions.BIG_WITCH_HURT.get();
			else if (randoms > 0.2f) potion = JerotesVillagePotions.BIG_WITCH_HURT.get();
			else if (randoms > 0.15f) potion = JerotesVillagePotions.BIG_WITCH_HEAL.get();
			else if (randoms > 0.1f) potion = JerotesVillagePotions.BIG_WITCH_HEAL.get();
			else if (randoms > 0.05f) potion = JerotesVillagePotions.BIG_WITCH_HEAL.get();
			ThrownPotion thrownPotion = new ThrownPotion(EntityType.POTION, serverLevel);
			thrownPotion.setItem(PotionUtils.setPotion(new ItemStack(Items.SPLASH_POTION), potion));
			thrownPotion.setPos(pos.getX() + 0.5, pos.getY() + 0.5, pos.getZ() + 0.5);
			thrownPotion.shoot((Math.random() - 0.5)/8, (Math.random())/4, (Math.random() - 0.5)/8, (float) (0.80f + Math.random() / 8f), 2.0f);
			serverLevel.addFreshEntity(thrownPotion);
		}
		if (this.tran >= 160) {
			this.tran = 0;
			if (!serverLevel.isClientSide()) {
				serverLevel.explode(null, x + 0.5, y + 0.5, z + 0.5, 4, Level.ExplosionInteraction.NONE);
			}
			serverLevel.setBlock(pos, JerotesVillageBlocks.DIRTY_HAGS_CAULDRON.get().defaultBlockState(), 2);
			PurpleSandRabbitEntity rabbit = JerotesVillageEntityType.PURPLE_SAND_RABBIT.get().spawn(serverLevel, BlockPos.containing(x, y, z), MobSpawnType.MOB_SUMMONED);
			if (rabbit != null) {
				rabbit.teleportTo(rabbit.getX(), rabbit.getY() + 2, rabbit.getZ());
				for (int i = 0; i < OtherMainConfig.PurpleSandHagSpellNormalLevel - 1; ++i) {
					PurpleSandRabbitEntity rabbit2 = JerotesVillageEntityType.PURPLE_SAND_RABBIT.get().spawn(serverLevel, BlockPos.containing(x, y, z), MobSpawnType.MOB_SUMMONED);
					if (rabbit2 != null) {
						rabbit2.teleportTo(rabbit2.getX(), rabbit2.getY() + 2, rabbit2.getZ());
					}
				}
				if (SpawnRules.BossSpawnFind(JerotesVillageEntityType.PURPLE_SAND_HAG.get(), rabbit)) {
					PurpleSandHagEntity hag = JerotesVillageEntityType.PURPLE_SAND_HAG.get().spawn(serverLevel, BlockPos.containing(x, y, z), MobSpawnType.MOB_SUMMONED);
					if (hag != null) {
						hag.setDeltaMovement(0, 0.5, 0);
					}
				}
			}
		}
		serverLevel.scheduleTick(pos, this, 1);
	}
}
