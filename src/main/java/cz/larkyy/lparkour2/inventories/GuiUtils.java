package cz.larkyy.lparkour2.inventories;

import cz.larkyy.lparkour2.handlers.ConfigHandler;

public class GuiUtils {

    public static boolean isMoreSlots(String itemPath, ConfigHandler config) {
        return config.getConfiguration().getConfigurationSection(itemPath).getKeys(false).contains("slots");
    }

}
