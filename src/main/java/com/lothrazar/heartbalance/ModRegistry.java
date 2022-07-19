package com.lothrazar.heartbalance;

import com.lothrazar.heartbalance.item.ItemHeart;
import net.minecraft.entity.player.ServerPlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.network.play.server.SPlaySoundPacket;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.SoundCategory;
import net.minecraft.util.SoundEvent;
import net.minecraft.util.math.vector.Vector3d;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.registries.IForgeRegistry;
import net.minecraftforge.registries.ObjectHolder;

@Mod.EventBusSubscriber(bus = Mod.EventBusSubscriber.Bus.MOD)
public class ModRegistry {

  public static SoundEvent HEART_GET = make("heart_get");
  @ObjectHolder(ModMain.MODID + ":half_heart")
  public static Item HALF_HEART;

  @SubscribeEvent
  public static void onItemsRegistry(final RegistryEvent.Register<Item> event) {
    IForgeRegistry<Item> r = event.getRegistry();
    r.register(new ItemHeart(new Item.Properties(), 20).setRegistryName("refill_heart"));
    r.register(new ItemHeart(new Item.Properties(), 1).setRegistryName("half_heart"));
    r.register(new ItemHeart(new Item.Properties(), 2).setRegistryName("full_heart"));
  }

  @SubscribeEvent
  public void registerSounds(RegistryEvent.Register<SoundEvent> event) {
    IForgeRegistry<SoundEvent> r = event.getRegistry();
    r.register(HEART_GET);
  }

  private static SoundEvent make(String s) {
    SoundEvent se = new SoundEvent(new ResourceLocation(ModMain.MODID, s));
    se.setRegistryName(s);
    return se;
  }

  public static void playSoundFromServer(ServerPlayerEntity entityIn, SoundEvent soundIn, float p, float v) {
    if (soundIn == null || entityIn == null) {
      return;
    }
    entityIn.connection.send(new SPlaySoundPacket(soundIn.getLocation(), SoundCategory.BLOCKS,
            new Vector3d(entityIn.xOld, entityIn.yOld, entityIn.zOld), p, v));
  }
}
