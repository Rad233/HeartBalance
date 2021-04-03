package com.lothrazar.heartbalance;

import com.electronwill.nightconfig.core.file.CommentedFileConfig;
import com.electronwill.nightconfig.core.io.WritingMode;
import net.minecraftforge.common.ForgeConfigSpec;
import net.minecraftforge.common.ForgeConfigSpec.DoubleValue;
import net.minecraftforge.common.ForgeConfigSpec.IntValue;
import net.minecraftforge.fml.loading.FMLPaths;

public class ConfigManager {

  private static final ForgeConfigSpec.Builder CFG = new ForgeConfigSpec.Builder();
  private static ForgeConfigSpec COMMON_CONFIG;
  public static IntValue INIT_HEARTS;
  public static DoubleValue CHANCE;
  static {
    initConfig();
  }

  private static void initConfig() {
    CFG.comment("General settings").push(ModMain.MODID);
    INIT_HEARTS = CFG.comment("Set the initial heart offset, it adds this to all players.  Zero means normal 10 hearts, -1 means players start with 9 hearts.").defineInRange("HeartOffset", -7, -10, 20);
    CHANCE = CFG.comment("Chance for a kill to drop a half heart refill.").defineInRange("HeartChances", 0.1, 0, 1);
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
