package cz.larkyy.lparkour2.inventories;

import cz.larkyy.lparkour2.LParkour2;
import cz.larkyy.lparkour2.handlers.ConfigHandler;
import cz.larkyy.lparkour2.objects.EditingPlayer;
import cz.larkyy.lparkour2.utils.Utils;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.InventoryHolder;
import org.bukkit.inventory.ItemStack;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;

public class MapCreatorsMenu implements InventoryHolder {

    private Inventory gui;
    private final EditingPlayer editingPlayer;

    public MapCreatorsMenu(EditingPlayer player) {
        this.editingPlayer = player;
    }

    @NotNull
    @Override
    public Inventory getInventory() {
        gui = Bukkit.createInventory(this,27,"Creators List");
        solveItems();
        return gui;
    }
    private void solveItems() {
        String path = "inventories.mapCreatorsList";
        if (getInvCfg().getConfiguration().getConfigurationSection(path+".items")==null)
            return;

        for (String str : getInvCfg().getConfiguration().getConfigurationSection(path+".items").getKeys(false)) {

            String itempath = path+".items."+str;

            switch (str) {
                case "creators":
                    loadCreators();
                    break;
                case "addCreator":
                    loadItem(itempath,"addCreator");
                    break;
                default:
                    loadItem(itempath,null);
                    break;
            }
        }
    }

    public void loadCreators() {
        String path = "inventories.mapCreatorsList.items.creators";
        List<ItemStack> creatorItems = new ArrayList<>();
        for (String str : editingPlayer.getCreators()) {

            creatorItems.add(getUtils().mkItem(
                    Material.PLAYER_HEAD,
                    getInvCfg().getConfiguration().getString(path+".name","&e%name%").replace("%name%",str),
                    "creator "+str,
                    getInvCfg().getConfiguration().getStringList(path+".lore"),
                    null
            ));
        }
        loadCreatorsToInv(creatorItems,path);
    }

    private void loadCreatorsToInv(List<ItemStack> items,String path) {
        List<Integer> slots = getMain().getInvCfg().getConfiguration().getIntegerList(path+".slots");
        int first = 0;
        for (int i : slots) {
            try {
                gui.setItem(i, items.get(first));
            } catch (IndexOutOfBoundsException ex) {
                continue;
            }
            first++;
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

    private boolean isMoreSlots(String itemPath) {
        return GuiUtils.isMoreSlots(itemPath,getInvCfg());
    }

    private LParkour2 getMain() {
        return LParkour2.getMain();
    }

    private ConfigHandler getInvCfg() {
        return getMain().getInvCfg();
    }

    private Utils getUtils() {
        return getMain().getUtils();
    }
}
