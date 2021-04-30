package cz.larkyy.lparkour2.inventories;

import cz.larkyy.lparkour2.LParkour2;
import cz.larkyy.lparkour2.handlers.ConfigHandler;
import cz.larkyy.lparkour2.objects.CheckpointObj;
import cz.larkyy.lparkour2.objects.EditingPlayer;
import cz.larkyy.lparkour2.objects.LevelObj;
import cz.larkyy.lparkour2.objects.PlayerObj;
import cz.larkyy.lparkour2.utils.Utils;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.InventoryHolder;
import org.bukkit.inventory.ItemStack;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class LocationCheckpointsMenu implements InventoryHolder {

    private Inventory gui;
    private int page;
    private final EditingPlayer player;

    public LocationCheckpointsMenu(int page, EditingPlayer player) {
        this.page = page;
        this.player = player;
    }

    @Override
    public @NotNull Inventory getInventory() {
        gui = Bukkit.createInventory(this,45,"&8Checkpoints List");
        solveItems();
        return gui;
    }
    private void solveItems() {
        String path = "inventories.locationCheckpointList";

        if (getInvCfg().getConfiguration().getConfigurationSection(path+".items")==null)
            return;
        for (String str : getInvCfg().getConfiguration().getConfigurationSection(path+".items").getKeys(false)) {

            String itempath = path+".items."+str;
            switch (str) {
                case "checkpoints":
                    loadCheckpointItems(itempath);
                    break;
                default:
                    loadItem(itempath,null);
                    break;
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

    private void loadCheckpointItems(String itempath) {
        List<ItemStack> cpItems = new ArrayList<>();

        List<CheckpointObj> cp = new ArrayList<>();

        if (player.getCheckpoints()!=null) {
            for (Map.Entry<Location, CheckpointObj> pair : player.getCheckpoints().entrySet()) {
                CheckpointObj checkpoint = pair.getValue();

                if (checkpoint.getType().equals(CheckpointObj.CheckType.LOCATION)) {
                    cp.add(checkpoint);
                }
            }
        }
        for (CheckpointObj checkpoint : cp) {

            Location loc = checkpoint.getLocation();

            cpItems.add(getUtils().mkItem(
                    //MATERIAL
                    Material.valueOf(getInvCfg().getConfiguration().getString(itempath+".material", "STONE")),
                    //NAME
                    Utils.format(getInvCfg().getConfiguration().getString(itempath+".name", "&eCheckpoint")),
                    //LOCALIZEDNAME
                    "Checkpoint " + loc.getWorld().getName()+ " "+loc.getX()+" "+loc.getY()+" "+loc.getZ()+" "+loc.getYaw()+" "+loc.getPitch(),
                    //LORE
                    getUtils().formatList(replaceLore(getInvCfg().getConfiguration().getStringList(itempath+".lore"),checkpoint)),
                    //TEXTURE
                    getInvCfg().getConfiguration().getString(itempath+".texture", null)
            ));
        }
        loadCheckpoints(itempath,cpItems);
    }

    private List<String> replaceLore(List<String> lore,CheckpointObj checkpoint) {
        List<String> newList = new ArrayList<>();
        Location loc = checkpoint.getLocation();
        for (String str : lore) {
            String loreStr = str
                    .replace("%world%",loc.getWorld().getName())
                    .replace("%x%",loc.getX()+"")
                    .replace("%y%",loc.getY()+"")
                    .replace("%z%",loc.getZ()+"")
                    .replace("%yaw%",loc.getYaw()+"")
                    .replace("%pitch%",loc.getPitch()+"");

            newList.add(loreStr);
        }
        return newList;
    }

    private void loadCheckpoints(String itempath,List<ItemStack> cpItems) {
        List<Integer> slots = getInvCfg().getConfiguration().getIntegerList(itempath+".slots");

        int first = slots.size()*page;
        for (int i : slots) {
            try {
                gui.setItem(i,cpItems.get(first));
            } catch (IndexOutOfBoundsException ex) {
                continue;
            }
            first++;
        }

    }


    private boolean isMoreSlots(String itemPath) {
        return GuiUtils.isMoreSlots(itemPath, getInvCfg()  );
    }

    private ItemStack mkItem(String itemPath, String localizedName) {
        return getUtils().mkItem(
                //MATERIAL
                Material.valueOf(getInvCfg().getConfiguration().getString(itemPath + ".material", "STONE")),
                //NAME
                Utils.format(getInvCfg().getConfiguration().getString(itemPath + ".name", null)),
                //LOCALIZEDNAME
                localizedName,
                //LORE
                getUtils().formatList(getInvCfg().getConfiguration().getStringList(itemPath + ".lore")),
                //TEXTURE
                getInvCfg().getConfiguration().getString(itemPath + ".texture", null));
    }

    private LParkour2 getMain() {
        return LParkour2.getMain();
    }

    private Utils getUtils() {
        return getMain().getUtils();
    }

    private ConfigHandler getInvCfg() {
        return getMain().getInvCfg();
    }
}
