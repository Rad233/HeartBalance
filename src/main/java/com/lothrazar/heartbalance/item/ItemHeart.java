package com.lothrazar.heartbalance.item;

import com.lothrazar.heartbalance.ConfigManager;
import com.lothrazar.heartbalance.ModRegistry;
import java.util.List;
import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemGroup;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Hand;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.TextFormatting;
import net.minecraft.util.text.TranslationTextComponent;
import net.minecraft.world.World;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

public class ItemHeart extends Item {

  final int healAmt;

  public ItemHeart(Properties properties, int value) {
    super(properties.tab(ItemGroup.TAB_COMBAT));
    healAmt = value;
  }

  public int getHealing() {
    return this.healAmt;
  }

  @Override
  @OnlyIn(Dist.CLIENT)
  public void appendHoverText(ItemStack stack, World worldIn, List<ITextComponent> tooltip, ITooltipFlag flagIn) {
    TranslationTextComponent t = new TranslationTextComponent(getDescriptionId() + ".tooltip");
    t.withStyle(TextFormatting.GRAY);
    tooltip.add(t);
  }

  @Override
  public ActionResult<ItemStack> use(World world, PlayerEntity player, Hand handIn) {
    ItemStack itemstack = player.getItemInHand(handIn);
    if (player.isHurt() && !player.getCooldowns().isOnCooldown(itemstack.getItem())) {
      player.heal(getHealing());
      player.getCooldowns().addCooldown(itemstack.getItem(), 20);
      itemstack.shrink(1);
      player.swing(handIn);
      if (world.isClientSide && ConfigManager.DO_SOUND_USE.get()) {
        player.playSound(ModRegistry.HEART_GET, 0.2F, 0.95F);
      }
      return ActionResult.success(itemstack);
    }
    return ActionResult.pass(player.getItemInHand(handIn));
  }
}
