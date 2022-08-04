package com.lothrazar.heartbalance;

import com.electronwill.nightconfig.core.file.CommentedFileConfig;
import com.electronwill.nightconfig.core.io.WritingMode;
import net.minecraftforge.common.ForgeConfigSpec;
import net.minecraftforge.common.ForgeConfigSpec.BooleanValue;
import net.minecraftforge.common.ForgeConfigSpec.DoubleValue;
import net.minecraftforge.common.ForgeConfigSpec.IntValue;
import net.minecraftforge.fml.loading.FMLPaths;

public class ConfigManager {

  private static final ForgeConfigSpec.Builder CFG = new ForgeConfigSpec.Builder();
  private static ForgeConfigSpec COMMON_CONFIG;
  public static IntValue INIT_HEARTS;
  public static DoubleValue CHANCE;
  public static BooleanValue DO_SOUND_USE;
  public static BooleanValue DO_SOUND_PICKUP;
  public static BooleanValue DO_PICKUP;
  static {
    initConfig();
  }

  private static void initConfig() {
    CFG.comment("General settings").push(ModMain.MODID);
    DO_SOUND_USE = CFG.comment("\r\nPlay sounds on heart pickup").define("soundOnUse", true);
    DO_SOUND_PICKUP = CFG.comment("\r\nPlay sounds on heart pickup").define("soundOnPickup", true);
    DO_PICKUP = CFG.comment("\r\nIf true, then a player with full health walking into a heart will get it as an item (false will vanish)").define("allowPickup", true);
    INIT_HEARTS = CFG.comment("\r\nEdit players maximum hearts, for all players as an offset: "
        + "Zero means normal 10 hearts no changes; -7 means players start with 3 hearts; 5 means you spawn with 15 hearts")
        .defineInRange("spawnHeartOffset", 0, -9, 90);
    CHANCE = CFG.comment("\r\nChance for a kill to drop a half heart refill")
        .defineInRange("lootHeartChances", 0.1, 0, 1);
    CFG.pop(); // one pop for every push
    COMMON_CONFIG = CFG.build();
  }

  public static void setup() {
    final CommentedFileConfig configData = CommentedFileConfig.builder(FMLPaths.CONFIGDIR.get().resolve(ModMain.MODID + ".toml"))
        .sync()
        .autosave()
        .writingMode(WritingMode.REPLACE)
        .build();
    configData.load();
    COMMON_CONFIG.setConfig(configData);
  }
}
