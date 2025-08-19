package com.artillexstudios.axholograms.config;

import com.artillexstudios.axapi.config.YamlConfiguration;
import com.artillexstudios.axapi.config.annotation.Comment;
import com.artillexstudios.axapi.config.annotation.ConfigurationPart;
import com.artillexstudios.axapi.config.annotation.Ignored;
import com.artillexstudios.axapi.config.annotation.Serializable;
import com.artillexstudios.axapi.libs.snakeyaml.DumperOptions;
import com.artillexstudios.axapi.utils.YamlUtils;
import com.artillexstudios.axapi.utils.logging.LogUtils;
import com.artillexstudios.axholograms.AxHologramsPlugin;
import com.artillexstudios.axholograms.utils.FileUtils;

import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;

public final class Language implements ConfigurationPart {
    private static final Path LANGUAGE_DIRECTORY = FileUtils.PLUGIN_DIRECTORY.resolve("language");
    private static final Language INSTANCE = new Language();
    public static String prefix = "<gradient:#FFAA55:#FFCC55><b>AxHolograms</b></gradient> ";

    public static Reload reload = new Reload();

    @Serializable
    public static class Reload {
        public String success = "<#00FF00>Successfully reloaded the configurations of the plugin in <white><time></white>ms!";
        public String fail = "<#FF0000>There were some issues while reloading file(s): <white><files></white>! Please check out the console for more information! <br>Reload done in: <white><time></white>ms!";
    }

    public static String invalidHologramType = "<white><type></white><#FF0000> is an invalid hologram type! Valid options are: <white><types></white>";
    public static String hologramAlreadyExists = "<#FF0000>A hologram named <white><name></white> already exists!";
    public static String hologramDoesntExist = "<#FF0000>No hologram named <white><name></white> exists!";
    public static String hologramNotLoaded = "<#FF0000>The hologram named <white><name></white> is not loaded! Please load the world it's in before trying to access it!";
    public static String successfullyCreatedHologram = "<#00FF00>You have successfully created a hologram named <white><name></white>!";
    public static String successfullyDeletedHologram = "<#00FF00>You have successfully deleted the hologram named <white><name></white>!";
    public static String successfullyTeleportedToHologram = "<#00FF00>You have been successfully teleported to the hologram named <white><name></white>!";
    public static String successfullyTeleportedHologram = "<#00FF00>Hologram named <white><name></white> has been teleported to you!";
    public static String successfullyCenteredHologram = "<#00FF00>Hologram named <white><name></white> has been centered!";


    @Comment("Do not touch!")
    public static int configVersion = 1;
    @Ignored
    public static String lastLanguage;
    private YamlConfiguration<?> config = null;

    public static boolean reload() {
        if (Config.debug) {
            LogUtils.debug("Reload called on language!");
        }
        FileUtils.copyFromResource("language");

        return INSTANCE.refreshConfig();
    }

    private boolean refreshConfig() {
        if (Config.debug) {
            LogUtils.debug("Refreshing language");
        }
        Path path = LANGUAGE_DIRECTORY.resolve(Config.language + ".yml");
        boolean shouldDefault = false;
        if (Files.exists(path)) {
            if (Config.debug) {
                LogUtils.debug("File exists");
            }
            if (!YamlUtils.suggest(path.toFile())) {
                return false;
            }
        } else {
            shouldDefault = true;
            path = LANGUAGE_DIRECTORY.resolve("en_US.yml");
            LogUtils.error("No language configuration was found with the name {}! Defaulting to en_US...", Config.language);
        }

        // The user might have changed the config
        if (this.config == null || (lastLanguage != null && lastLanguage.equalsIgnoreCase(Config.language))) {
            lastLanguage = shouldDefault ? "en_US" : Config.language;
            if (Config.debug) {
                LogUtils.debug("Set lastLanguage to {}", lastLanguage);
            }
            InputStream defaults = AxHologramsPlugin.getInstance().getResource("language/" + lastLanguage + ".yml");
            if (defaults == null) {
                if (Config.debug) {
                    LogUtils.debug("Defaults are null, defaulting to en_US.yml");
                }
                defaults = AxHologramsPlugin.getInstance().getResource("language/en_US.yml");
            }

            if (Config.debug) {
                LogUtils.debug("Loading config from file {} with defaults {}", path, defaults);
            }

            this.config = YamlConfiguration.of(path, Language.class)
                    .configVersion(1, "config-version")
                    .withDefaults(defaults)
                    .withDumperOptions(options -> {
                        options.setDefaultFlowStyle(DumperOptions.FlowStyle.BLOCK);
                        options.setSplitLines(false);
                    }).build();
        }

        this.config.load();
        return true;
    }
}
