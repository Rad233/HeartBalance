package com.lothrazar.heartbalance;

import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Hand;
import net.minecraft.world.World;

public class ItemTest extends Item {

  public ItemTest(Properties properties) {
    super(properties);
  }

  @Override
  public ActionResult<ItemStack> onItemRightClick(World worldIn, PlayerEntity playerIn, Hand handIn) {
    if (playerIn.shouldHeal()) {
      ItemStack itemstack = playerIn.getHeldItem(handIn);
      playerIn.heal(1F);
      itemstack.shrink(1);
      return ActionResult.resultConsume(itemstack);
    }
    return ActionResult.resultPass(playerIn.getHeldItem(handIn));
  }
}
