package cz.larkyy.lparkour2.inventories;

import cz.larkyy.lparkour2.LParkour2;
import cz.larkyy.lparkour2.handlers.ConfigHandler;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.InventoryHolder;
import org.bukkit.inventory.ItemStack;
import org.jetbrains.annotations.NotNull;

public class MapCategorySelectionMenu implements InventoryHolder {

    private Inventory gui;

    @Override
    public @NotNull Inventory getInventory() {
        gui = Bukkit.createInventory(this,27,"Category Selection");
        solveItems();
        return gui;
    }

    private void solveItems() {
        String path = "inventories.mapCategorySelection";
        if (getInvCfg().getConfiguration().getConfigurationSection(path+".items")==null)
            return;

        for (String str : getInvCfg().getConfiguration().getConfigurationSection(path+".items").getKeys(false)) {

            String itempath = path+".items."+str;

            if (getMain().getLevelsHandler().getCategories().contains(str)) {
                loadItem(itempath,"category "+str);
            } else {
                loadItem(itempath,null);
            }
        }

    }
    private void loadItem(String itemPath, String localizedName) {
        if (!isMoreSlots(itemPath)) {
            int slot = getInvCfg().getConfiguration().getInt(itemPath + ".slot");
            if (slot != -1) {
                gui.setItem(slot, mkItem(itemPath, localizedName));
            }

        } else {
            ItemStack is = mkItem(itemPath, localizedName);
            for (int i : getInvCfg().getConfiguration().getIntegerList(itemPath + ".slots")) {
                gui.setItem(i, is);
            }
        }
    }

    private boolean isMoreSlots(String itemPath) {
        return GuiUtils.isMoreSlots(itemPath,getInvCfg());
    }

    private ItemStack mkItem(String path,String lname) {
        return getMain().getUtils().mkItem(
                //MATERIAL
                Material.valueOf(getInvCfg().getConfiguration().getString(path + ".material", "STONE")),
                //NAME
                getInvCfg().getConfiguration().getString(path + ".name", null),
                //LOCALIZEDNAME
                lname,
                //LORE
                getInvCfg().getConfiguration().getStringList(path + ".lore"),
                //TEXTURE
                getInvCfg().getConfiguration().getString(path + ".texture", null));
    }

    private LParkour2 getMain() {
        return LParkour2.getMain();
    }

    private ConfigHandler getInvCfg() {
        return getMain().getInvCfg();
    }
}
