package com.lothrazar.heartbalance;

import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Hand;
import net.minecraft.world.World;

public class ItemHealing extends Item {

  final int healAmt;

  public ItemHealing(Properties properties, int value) {
    super(properties);
    healAmt = value;
  }

  public int getHealing() {
    return this.healAmt;
  }

  @Override
  public ActionResult<ItemStack> onItemRightClick(World world, PlayerEntity player, Hand handIn) {
    if (player.shouldHeal()) {
      ItemStack itemstack = player.getHeldItem(handIn);
      player.heal(getHealing());
      itemstack.shrink(1);
      player.swingArm(handIn);
      if (world.isRemote && ConfigManager.DO_SOUND_USE.get()) {
        player.playSound(ModRegistry.HEART_GET, 0.2F, 0.95F);
      }
      return ActionResult.resultSuccess(itemstack);
    }
    return ActionResult.resultPass(player.getHeldItem(handIn));
  }
}
