package com.lothrazar.heartbalance;

import com.lothrazar.heartbalance.item.ItemHeart;
import net.minecraft.network.protocol.game.ClientboundSoundPacket;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.item.Item;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

@Mod.EventBusSubscriber(bus = Mod.EventBusSubscriber.Bus.MOD)
public class ModRegistry {

  public static SoundEvent HEART_GET = make("heart_get");
  public static final DeferredRegister<Item> ITEMS = DeferredRegister.create(ForgeRegistries.ITEMS, ModMain.MODID);
  public static final RegistryObject<Item> REFILL_HEART = ITEMS.register("refill_heart", () -> new ItemHeart(new Item.Properties(), 20));
  public static final RegistryObject<Item> HALF_HEART = ITEMS.register("half_heart", () -> new ItemHeart(new Item.Properties(), 1));
  public static final RegistryObject<Item> FULL_HEART = ITEMS.register("full_heart", () -> new ItemHeart(new Item.Properties(), 2));

  private static SoundEvent make(String s) {
    SoundEvent se = new SoundEvent(new ResourceLocation(ModMain.MODID, s));
    //    se.setRegistryName(s);
    return se;
  }

  public static void playSoundFromServer(ServerPlayer entityIn, SoundEvent soundIn, float p, float v) {
    if (soundIn == null || entityIn == null) {
      return;
    }
    entityIn.connection.send(new ClientboundSoundPacket(soundIn, SoundSource.BLOCKS,
        entityIn.xOld, entityIn.yOld, entityIn.zOld, p, v, 0));
  }
}
