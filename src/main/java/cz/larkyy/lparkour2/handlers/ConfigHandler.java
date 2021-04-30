package cz.larkyy.lparkour2.handlers;

import cz.larkyy.lparkour2.LParkour2;
import cz.larkyy.lparkour2.utils.Utils;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;

import java.io.File;
import java.io.IOException;
import java.util.List;

public class ConfigHandler {

    private final File file;
    private FileConfiguration config;

    private final LParkour2 main;
    private final Utils utils;

    public ConfigHandler(LParkour2 main, String path) {
        this.main = main;
        this.file = new File(main.getDataFolder(), path);
        this.utils = main.getUtils();
    }

    public void load() {
        if (!file.exists())
            try {
                main.saveResource(file.getName(), false);
            } catch (IllegalArgumentException e) {
                try {
                    file.createNewFile();
                } catch (IOException exception) {
                    exception.printStackTrace();
                }
            }


        this.config = YamlConfiguration.loadConfiguration(file);
    }

    public FileConfiguration getConfiguration() {
        if (config == null)
            load();

        return config;
    }

    public void save() throws IOException {
        config.save(file);
    }


    public String getString(String path, String defValue) {
        if (config.getString(path) != null) {
            return config.getString(path);
        } else {
            utils.sendConsoleMsg("&cPath " + path + " in " + file.getName() + " wasn't found! Using default value...");
            config.set(path,defValue);
            try {
                save();
            } catch (IOException ex) {
                ex.printStackTrace();
            }
            return defValue;
        }
    }

    public Integer getInt(String path, Integer defValue) {
        if (config.getString(path) != null) {
            return config.getInt(path);
        } else {
            utils.sendConsoleMsg("&cPath " + path + " in " + file.getName() + " wasn't found! Using default value...");
            return defValue;
        }
    }


    public List<String> getStringList(String path, List<String> defValue) {
        if (!config.getStringList(path).isEmpty()) {
            return config.getStringList(path);
        } else {
            utils.sendConsoleMsg("&cPath " + path + " in " + file.getName() + " wasn't found! Using default value...");
            return defValue;
        }
    }

}
