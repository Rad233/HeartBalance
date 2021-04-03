package com.lothrazar.heartbalance;

import net.minecraft.entity.player.ServerPlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.network.play.server.SPlaySoundEffectPacket;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.SoundCategory;
import net.minecraft.util.SoundEvent;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.registries.IForgeRegistry;
import net.minecraftforge.registries.ObjectHolder;

@Mod.EventBusSubscriber(bus = Mod.EventBusSubscriber.Bus.MOD)
public class ModRegistry {

  //change Object to your Block/Item/whatever 
  @ObjectHolder(ModMain.MODID + ":half_heart")
  public static Item HALF_HEART;

  @SubscribeEvent
  public static void onItemsRegistry(final RegistryEvent.Register<Item> event) {
    IForgeRegistry<Item> r = event.getRegistry();
    r.register(new ItemHealing(new Item.Properties(), 1).setRegistryName("half_heart"));
    r.register(new ItemHealing(new Item.Properties(), 2).setRegistryName("full_heart"));
  }

  @SubscribeEvent
  public void registerSounds(RegistryEvent.Register<SoundEvent> event) {
    IForgeRegistry<SoundEvent> r = event.getRegistry();
    r.register(heart_get);
  }

  public static SoundEvent heart_get = make("heart_get");

  private static SoundEvent make(String s) {
    SoundEvent se = new SoundEvent(new ResourceLocation(ModMain.MODID, s));
    se.setRegistryName(s);
    return se;
  }

  public static void playSoundFromServer(ServerPlayerEntity entityIn, SoundEvent soundIn,
      float p, float v) {
    if (soundIn == null || entityIn == null) {
      return;
    }
    entityIn.connection.sendPacket(new SPlaySoundEffectPacket(
        soundIn,
        SoundCategory.BLOCKS,
        entityIn.lastTickPosX, entityIn.lastTickPosY, entityIn.lastTickPosZ,
        p, v));
  }
}
