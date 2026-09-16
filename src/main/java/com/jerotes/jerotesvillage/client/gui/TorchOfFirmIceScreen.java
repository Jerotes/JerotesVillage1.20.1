package com.jerotes.jerotesvillage.client.gui;

import com.jerotes.jerotes.JerotesWarehouse;
import com.jerotes.jerotes.client.gui.MobInventoryGUIScreen;
import com.jerotes.jerotes.init.JerotesMobEffects;
import com.jerotes.jerotes.util.Main;
import com.jerotes.jerotesvillage.JerotesVillage;
import com.jerotes.jerotesvillage.block.BitterCold.TorchOfFirmIce;
import com.jerotes.jerotesvillage.init.JerotesVillageEntityType;
import com.jerotes.jerotesvillage.init.JerotesVillageItems;
import com.jerotes.jerotesvillage.network.OtherPacketHandler;
import com.jerotes.jerotesvillage.network.TorchOfFirmIcePacket;
import com.jerotes.jerotesvillage.inventory.TorchOfFirmIceMenu;
import com.mojang.blaze3d.systems.RenderSystem;
import com.mojang.blaze3d.vertex.PoseStack;
import net.minecraft.ChatFormatting;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.components.ImageButton;
import net.minecraft.client.gui.screens.inventory.AbstractContainerScreen;
import net.minecraft.core.BlockPos;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.tags.FluidTags;
import net.minecraft.util.Mth;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.MobSpawnType;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.AABB;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class TorchOfFirmIceScreen extends AbstractContainerScreen<TorchOfFirmIceMenu> {
   private static final ResourceLocation LOCATION = new ResourceLocation(JerotesVillage.MODID, "textures/gui/container/torch_of_firm_ice.png");
   public static final ResourceLocation CAN_USE = new ResourceLocation(JerotesVillage.MODID, "textures/gui/sprites/container/torch_of_firm_ice/can_use.png");
   public static final ResourceLocation WILL_USE = new ResourceLocation(JerotesVillage.MODID, "textures/gui/sprites/container/torch_of_firm_ice/will_use.png");
   public static final ResourceLocation CAN_NOT_USE = new ResourceLocation(JerotesVillage.MODID, "textures/gui/sprites/container/torch_of_firm_ice/can_not_use.png");
   public static final ResourceLocation CHANGE = new ResourceLocation(JerotesVillage.MODID, "textures/gui/sprites/container/torch_of_firm_ice/change.png");
   public static final ResourceLocation NOT_CHANGE = new ResourceLocation(JerotesVillage.MODID, "textures/gui/sprites/container/torch_of_firm_ice/not_change.png");
   public static final ResourceLocation ICON = new ResourceLocation(JerotesVillage.MODID, "textures/gui/container/torch_of_firm_ice_icon.png");
   private final Level level;
   private final TorchOfFirmIceMenu container;

   public TorchOfFirmIceScreen(TorchOfFirmIceMenu container, Inventory inventory, Component text) {
      super(container, inventory, text);
      this.container = container;
      this.level = container.level;
      this.imageWidth = 176;
      this.imageHeight = 166;
   }

   @Override
   public void render(GuiGraphics guiGraphics, int mouseX, int mouseY, float partialTicks) {
      super.render(guiGraphics, mouseX, mouseY, partialTicks);
      ItemStack itemStack = this.container.getItems().get(0);
      int eliteType = level.getBlockState(menu.getBlockPos()).getBlock() instanceof TorchOfFirmIce torchOfFirmIce ? torchOfFirmIce.getElite(level.getBlockState(menu.getBlockPos())) : 0;


      //选择精英类型
      {
         //寒霜雪怪
         changeButton(guiGraphics, 9, 11, 9, 9, 1, mouseX, mouseY, eliteType);
         //泌火恶怪
         changeButton(guiGraphics, 19, 11, 9, 9, 2, mouseX, mouseY, eliteType);
         //闪电蠕虫
         changeButton(guiGraphics, 29, 11, 9, 9, 3, mouseX, mouseY, eliteType);
         //怒化骇浪
         changeButton(guiGraphics, 39, 11, 9, 9, 4, mouseX, mouseY, eliteType);
         //怒化旱火野火
         changeButton(guiGraphics, 49, 11, 9, 9, 5, mouseX, mouseY, eliteType);
         //元凶雪人
         changeButton(guiGraphics, 9, 25, 9, 9, 19, mouseX, mouseY, eliteType);
         //沙行雷虫
         changeButton(guiGraphics, 19, 25, 9, 9, 20, mouseX, mouseY, eliteType);
         //重置
         changeButton(guiGraphics, 85, 25, 9, 9, 0, mouseX, mouseY, eliteType);

         guiGraphics.blit(ICON, this.leftPos, this.topPos, 0, 0, 176, 166, 176, 166);

         //寒霜雪怪
         changeTooltip(guiGraphics, 9, 11, 9, 9, 1, mouseX, mouseY, eliteType);
         //泌火恶怪
         changeTooltip(guiGraphics, 19, 11, 9, 9, 2, mouseX, mouseY, eliteType);
         //闪电蠕虫
         changeTooltip(guiGraphics, 29, 11, 9, 9, 3, mouseX, mouseY, eliteType);
         //怒化骇浪
         changeTooltip(guiGraphics, 39, 11, 9, 9, 4, mouseX, mouseY, eliteType);
         //怒化旱火野火
         changeTooltip(guiGraphics, 49, 11, 9, 9, 5, mouseX, mouseY, eliteType);
         //元凶雪人
         changeTooltip(guiGraphics, 9, 25, 9, 9, 19, mouseX, mouseY, eliteType);
         //沙行雷虫
         changeTooltip(guiGraphics, 19, 25, 9, 9, 20, mouseX, mouseY, eliteType);
         //重置
         changeTooltip(guiGraphics, 85, 25, 9, 9, 0, mouseX, mouseY, eliteType);


         //要求介绍
         if (eliteType != 0) {
            canSummon(eliteType, itemStack, level, menu.getBlockPos(), false, guiGraphics);
         }
      }

      //使用
      {
         boolean canUse = canSummon(eliteType, itemStack, level, menu.getBlockPos(), true, guiGraphics);
         //按钮形式
         if (canUse) {
            int textX = this.leftPos + 82;
            int textY = this.topPos + 67;
            int textWidth = 11;
            int textHeight = 12;
            if (mouseX >= textX && mouseX <= textX + textWidth && mouseY >= textY && mouseY <= textY + textHeight) {
               guiGraphics.blit(WILL_USE, this.leftPos + 82, this.topPos + 67, 0, 0, 11, 12, 11, 12);
            } else {
               guiGraphics.blit(CAN_USE, this.leftPos + 82, this.topPos + 67, 0, 0, 11, 12, 11, 12);
            }
            //1.20.4↑//
//            this.addRenderableWidget(new ImageButton(this.leftPos + 82, this.topPos + 67, 11, 12, new WidgetSprites(new ResourceLocation(JerotesWarehouse.MODID, "container/null"), new ResourceLocation(JerotesWarehouse.MODID, "container/null")), button -> {
//               button.setPosition(this.leftPos + 82, this.topPos + 67);
//               if (level.isClientSide) {
//                  OtherPacketHandler.sendToServer(new TorchOfFirmIcePacket(eliteType, this.menu.getBlockPos(), true, itemNeedCount(eliteType)));
//               }
//            }));
            //1.20.1//
            this.addRenderableWidget(new ImageButton(this.leftPos + 82, this.topPos + 67, 11, 12, 0, 0, 11, new ResourceLocation(JerotesWarehouse.MODID, "container/null"), (button) -> {
               button.setPosition(this.leftPos + 82, this.topPos + 67);
               if (level.isClientSide) {
                  OtherPacketHandler.sendToServer(new TorchOfFirmIcePacket(eliteType, this.menu.getBlockPos(), true, itemNeedCount(eliteType)));
               }
            }));
         }
         else {
            guiGraphics.blit(CAN_NOT_USE, this.leftPos + 82, this.topPos + 67, 0, 0, 11, 12, 11, 12);
         }

      }

      this.renderTooltip(guiGraphics, mouseX, mouseY);
   }


   public void changeButton(GuiGraphics guiGraphics, int n, int n2, int n3, int n4, int elite, int mouseX, int mouseY, int nowElite) {
      int textX = this.leftPos + n;
      int textY = this.topPos + n2;
      //图标相关
      if (mouseX >= textX && mouseX <= textX + n3 && mouseY >= textY && mouseY <= textY + n4) {
         guiGraphics.blit(CHANGE, this.leftPos + n, this.topPos + n2, 0, 0, n3, n4, n3, n4);
      } else if (nowElite == elite && nowElite != 0) {
         guiGraphics.blit(CHANGE, this.leftPos + n, this.topPos + n2, 0, 0, n3, n4, n3, n4);
      } else {
         guiGraphics.blit(NOT_CHANGE, this.leftPos + n, this.topPos + n2, 0, 0, n3, n4, n3, n4);
      }
      //实体显示
      if (nowElite == elite && nowElite != 0) {
         LivingEntity livingEntity = getChangeEntity(elite, level);
         if (livingEntity != null) {
            //1.20.4↑//
//            InventoryScreen.renderEntityInInventoryFollowsMouse(guiGraphics, this.leftPos + 8, this.topPos + 46,
//                    this.leftPos + 59, this.topPos + 79, 17, 0.25f, mouseX, mouseY, livingEntity);
            //1.20.1//
            MobInventoryGUIScreen.renderEntityInInventoryFollowsMouse(guiGraphics, this.leftPos + 8, this.topPos + 46,
                    this.leftPos + 59, this.topPos + 79, 17, 0.25f, mouseX, mouseY, livingEntity);
         }
      }
      //按钮
      //1.20.4↑//
//      this.addRenderableWidget(new ImageButton(this.leftPos + n, this.topPos + n2, n3, n4, new WidgetSprites(new ResourceLocation(JerotesWarehouse.MODID, "container/null"), new ResourceLocation(JerotesWarehouse.MODID, "container/null")), button -> {
//         button.setPosition(this.leftPos + n, this.topPos + n2);
//         if (level.isClientSide) {
//            OtherPacketHandler.sendToServer(new TorchOfFirmIcePacket(elite, this.menu.getBlockPos(), false, 0));
//         }
//      }));
      //1.20.1//
      this.addRenderableWidget(new ImageButton(this.leftPos + n, this.topPos + n2, n3, n4, 0, 0, n3, new ResourceLocation(JerotesWarehouse.MODID, "container/null"), (button) -> {
         button.setPosition(this.leftPos + n, this.topPos + n2);
         if (level.isClientSide) {
            OtherPacketHandler.sendToServer(new TorchOfFirmIcePacket(elite, this.menu.getBlockPos(), false, 0));
         }
      }));
   }
   public void changeTooltip(GuiGraphics guiGraphics, int n, int n2, int n3, int n4, int elite, int mouseX, int mouseY, int nowElite) {
      int textX = this.leftPos + n;
      int textY = this.topPos + n2;
      //图标相关
      if (mouseX >= textX && mouseX <= textX + n3 && mouseY >= textY && mouseY <= textY + n4) {
         //介绍
         List<Component> tooltip = new ArrayList<>();
         if (changeEntityType(elite) != null) {
            tooltip.add(changeEntityType(elite).getDescription());
         }
         guiGraphics.renderTooltip(this.font, tooltip, Optional.empty(), mouseX, mouseY);
      }
   }

   public boolean canSummon(int eliteType, ItemStack itemStack, Level level, BlockPos blockPos, boolean summonUse, GuiGraphics guiGraphics) {
      Item item = Items.DIAMOND;
      int itemNeedCount = itemNeedCount(eliteType);
      int challengeDifficulty = 3;
      boolean water = false;
      int xzNeed = 25;
      int yNeed = 12;

      //寒霜雪怪
      if (eliteType == 1) {
         item = Items.SNOW_BLOCK;
      }
      //泌火恶怪
      if (eliteType == 2) {
         item = Items.BLAZE_ROD;
      }
      //闪电蠕虫
      if (eliteType == 3) {
         item = Items.COPPER_INGOT;
      }
      //怒化骇浪
      if (eliteType == 4) {
         water = true;
         xzNeed = 13;
         yNeed = 5;
         item = JerotesVillageItems.BULGE_ROD.get();
      }
      //怒化旱火野火
      if (eliteType == 5) {
         item = Items.BLAZE_ROD;
      }
      //元凶雪人
      if (eliteType == 19) {
         xzNeed = 33;
         yNeed = 16;
         item = JerotesVillageItems.GIANT_MONSTER_HAIR.get();
      }
      //沙行雷虫
      if (eliteType == 20) {
         xzNeed = 33;
         yNeed = 16;
         item = JerotesVillageItems.PINK_CACTUS_FLOWER.get();
      }

      int needCount = xzNeed * yNeed * xzNeed - 2;
      if (!summonUse) {
         //要求介绍
         if (eliteType != 0) {
            int leftPosAdd = 116;
            int topPos = 47;
            Component component;
            PoseStack poseStack = guiGraphics.pose();
            poseStack.pushPose();
            float scale = 0.6f;
            poseStack.scale(scale, scale, scale);
            //难度评级
            component = Component.translatable("message.jerotesvillage.torch_of_firm_ice.challenge_difficulty", challengeDifficulty);
            guiGraphics.drawString(font, component,
                    (int) ((this.leftPos + leftPosAdd) / scale), (int) ((this.topPos + topPos) / scale), 2500134, false);
            topPos += (int) (font.lineHeight * scale);
            //需要物品
            component = Component.translatable("message.jerotesvillage.torch_of_firm_ice.required_items", item.getDescription(), itemNeedCount);
            guiGraphics.drawString(font, component,
                    (int) ((this.leftPos + leftPosAdd) / scale), (int) ((this.topPos + topPos) / scale), 2500134, false);
            topPos += (int) (font.lineHeight * scale);
            //需要范围
            component = Component.translatable("message.jerotesvillage.torch_of_firm_ice.prerequisites");
            guiGraphics.drawString(font, component,
                    (int) ((this.leftPos + leftPosAdd) / scale), (int) ((this.topPos + topPos) / scale), 2500134, false);
            topPos += (int) (font.lineHeight * scale);
            component = Component.translatable("message.jerotesvillage.torch_of_firm_ice.prerequisites.add0", xzNeed, yNeed);
            guiGraphics.drawString(font, component,
                    (int) ((this.leftPos + leftPosAdd) / scale), (int) ((this.topPos + topPos) / scale), 2500134, false);
            topPos += (int) (font.lineHeight * scale);
            component = Component.translatable("message.jerotesvillage.torch_of_firm_ice.prerequisites.add1", water ? Component.translatable("message.jerotesvillage.torch_of_firm_ice.prerequisites.water") : Component.translatable("message.jerotesvillage.torch_of_firm_ice.prerequisites.air")
                    , needCount);
            guiGraphics.drawString(font, component,
                    (int) ((this.leftPos + leftPosAdd) / scale), (int) ((this.topPos + topPos) / scale), 2500134, false);
            topPos += (int) (font.lineHeight * scale);
            //y
            component = Component.translatable("message.jerotesvillage.torch_of_firm_ice.y");
            guiGraphics.drawString(font, component,
                    (int) ((this.leftPos + leftPosAdd) / scale), (int) ((this.topPos + topPos) / scale), 2500134, false);
            topPos += (int) (font.lineHeight * scale);
            poseStack.popPose();
         }
         return true;
      }
      //生成相关
      return eliteType != 0 && itemStack.getItem() == item && itemStack.getCount() >= itemNeedCount;
   }
   public static EntityType<? extends Mob> changeEntityType(int eliteType) {
      EntityType<? extends Mob> entityType = null;
      //寒霜雪怪
      if (eliteType == 1) {
         entityType = JerotesVillageEntityType.FROST_YETI.get();
      }
      //泌火恶怪
      if (eliteType == 2) {
         entityType = JerotesVillageEntityType.FIRE_SECRETOR.get();
      }
      //闪电蠕虫
      if (eliteType == 3) {
         entityType = JerotesVillageEntityType.LIGHTNING_WORM.get();
      }
      //怒化骇浪
      if (eliteType == 4) {
         entityType = JerotesVillageEntityType.FURY_SEAWAVE.get();
      }
      //怒化旱火野火
      if (eliteType == 5) {
         entityType = JerotesVillageEntityType.FURY_DROUGHT_WILDFIRE.get();
      }
      //元凶雪人
      if (eliteType == 19) {
         entityType = JerotesVillageEntityType.PRIMORDIER.get();
      }
      //沙行雷虫
      if (eliteType == 20) {
         entityType = JerotesVillageEntityType.DUNESHOCKLER.get();
      }
      return entityType;
   }
   public static LivingEntity changeEntity(int eliteType, Level level, BlockPos blockPos) {
      EntityType<? extends Mob>  entityType = changeEntityType(eliteType);
      if (entityType != null) {
         if (level instanceof ServerLevel serverLevel) {
            BlockPos summonPos = Main.findSpawnPositionNearFillOnBlock(level, blockPos, 3);
            Mob mob = entityType.spawn(serverLevel, BlockPos.containing(summonPos.getX(), summonPos.getY(), summonPos.getZ()), MobSpawnType.EVENT);
            if (mob != null) {
               if (!level.isClientSide()) {
                  mob.addEffect(new MobEffectInstance(JerotesMobEffects.PARALYZED.get(), 40, 0));
                  mob.addEffect(new MobEffectInstance(MobEffects.DAMAGE_RESISTANCE, 40, 4));
               }
            }
         }
      }
      return null;
   }
   public static LivingEntity getChangeEntity(int eliteType, Level level) {
      EntityType<? extends Mob>  entityType = changeEntityType(eliteType);
      if (entityType != null) {
         Mob mob = entityType.create(level);
         return mob;
      }
      return null;
   }
   public int itemNeedCount(int eliteType) {
      //寒霜雪怪
      if (eliteType == 1) {
         return 3;
      }
      //泌火恶怪
      if (eliteType == 2) {
         return 3;
      }
      //闪电蠕虫
      if (eliteType == 3) {
         return 3;
      }
      //元凶雪人
      if (eliteType == 19) {
         return 3;
      }
      //沙行雷虫
      if (eliteType == 20) {
         return 3;
      }
      return 1;
   }

   public static boolean canSummonFindBlockCount(int eliteType, ServerLevel level, BlockPos blockPos, Player player) {
      boolean water = false;
      int xzNeed = 25;
      int yNeed = 12;
      int count = 0;
      // 怒化骇浪
      if (eliteType == 4) {
         water = true;
         xzNeed = 13;
         yNeed = 6;
      }
      //元凶雪人
      if (eliteType == 19) {
         xzNeed = 33;
         yNeed = 16;
      }

      int halfXZ = (xzNeed - 1) / 2;
      int minX = blockPos.getX() - halfXZ;
      int minY = blockPos.getY();
      int minZ = blockPos.getZ() - halfXZ;
      int maxX = blockPos.getX() + halfXZ;
      int maxY = blockPos.getY() + yNeed - 1;
      int maxZ = blockPos.getZ() + halfXZ;

      AABB aabb = new AABB(minX, minY, minZ, maxX, maxY, maxZ);
      int needCount = xzNeed * yNeed * xzNeed - 2;
      for (BlockPos blockPosFind : BlockPos.betweenClosed(Mth.floor(aabb.minX), Mth.floor(aabb.minY), Mth.floor(aabb.minZ), Mth.floor(aabb.maxX), Mth.floor(aabb.maxY), Mth.floor(aabb.maxZ))) {
         if (water) {
            if (level.getFluidState(blockPosFind).is(FluidTags.WATER)) {
               count++;
            }
         } else {
            if (level.getBlockState(blockPosFind).isAir()) {
               count++;
            }
         }
      }
      if (count < needCount) {
         player.displayClientMessage(Component.translatable("message.jerotesvillage.torch_of_firm_ice_block_count", needCount, count).withStyle(ChatFormatting.DARK_AQUA), true);
         player.sendSystemMessage(Component.translatable("message.jerotesvillage.torch_of_firm_ice_block_count", needCount, count).withStyle(ChatFormatting.DARK_AQUA));
      }
      return count >= needCount;
   }

   @Override
   protected void renderBg(GuiGraphics guiGraphics, float partialTicks, int gx, int gy) {
      RenderSystem.setShaderColor(1, 1, 1, 1);
      RenderSystem.enableBlend();
      RenderSystem.defaultBlendFunc();
      guiGraphics.blit(LOCATION, this.leftPos, this.topPos, 0, 0, this.imageWidth, this.imageHeight, this.imageWidth, this.imageHeight);
      RenderSystem.disableBlend();
   }
   @Override
   public boolean keyPressed(int key, int b, int c) {
      if (key == 256) {
         this.minecraft.player.closeContainer();
         return true;
      }
      return super.keyPressed(key, b, c);
   }
   @Override
   public void containerTick() {
      super.containerTick();
   }
   @Override
   protected void renderLabels(GuiGraphics guiGraphics, int mouseX, int mouseY) {
   }
   @Override
   public void init() {
      super.init();
   }
}
