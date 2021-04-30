package cz.larkyy.lparkour2.inventories;

import cz.larkyy.lparkour2.LParkour2;
import cz.larkyy.lparkour2.handlers.ConfigHandler;
import cz.larkyy.lparkour2.objects.LevelObj;
import cz.larkyy.lparkour2.utils.Utils;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.InventoryHolder;
import org.bukkit.inventory.ItemStack;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class LevelsMenu implements InventoryHolder {

    private Inventory gui;
    private String category;
    private int page;

    public LevelsMenu(String category, int page) {
        this.category = category;
        this.page = page;
    }

    @Override
    public @NotNull Inventory getInventory() {
        gui = Bukkit.createInventory(this,45,"&8Levels Menu");
        solveItems();
        return gui;
    }
    private void solveItems() {
        String path = "inventories.categoryMenus."+category;

        if (getInvCfg().getConfiguration().getConfigurationSection(path+".items")==null)
            return;
        for (String str : getInvCfg().getConfiguration().getConfigurationSection(path+".items").getKeys(false)) {

            String itempath = path+".items."+str;
            switch (str) {
                case "levels":
                    loadLevelItems(itempath);
                    break;
                case "back":
                    loadItem(itempath,"back");
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

    private void loadLevelItems(String itempath) {
        List<ItemStack> levelItems = new ArrayList<>();

        List<LevelObj> levels = new ArrayList<>();
        for (Map.Entry<String,LevelObj> pair : getMain().getLevelsHandler().getLevels().entrySet()) {
            LevelObj level = pair.getValue();
            String levelCategory = level.getDifficulty();

            if (category.equals(levelCategory)) {
                levels.add(level);
            }
        }
        Bukkit.broadcastMessage("Levels: "+levels.size());

        for (LevelObj level : levels) {
            Bukkit.broadcastMessage("Loading level "+level.getName());
            levelItems.add(getUtils().mkItem(
                    //MATERIAL
                    Material.valueOf(getInvCfg().getConfiguration().getString(itempath+".material", "STONE")),
                    //NAME
                    Utils.format(getInvCfg().getConfiguration().getString(itempath+".name", "&eLevel %name%").replace("%name%", level.getName())),
                    //LOCALIZEDNAME
                    "Level " + level.getName(),
                    //LORE
                    getUtils().formatList(replaceLore(getInvCfg().getConfiguration().getStringList(itempath+".lore"),level)),
                    //TEXTURE
                    getInvCfg().getConfiguration().getString(itempath+".texture", null)
            ));
        }
        loadLevels(itempath,levelItems);
    }

    private List<String> replaceLore(List<String> lore,LevelObj level) {
        List<String> newLore = new ArrayList<>();
        List<String> creators = level.getCreators();
        if (creators==null)
            creators = new ArrayList<>();
        for (String str : lore) {
            if (str.contains("%creator")) {
                for (int i = 1; i < 10; i++) {
                    if (str.contains("%creator-" + i + "%")) {
                        if (creators.size() >= i) {
                            String newLine = str.replace("%creator-" + i + "%", creators.get(i - 1));
                            newLore.add(newLine);
                        }
                    }
                }
            } else {
                newLore.add(str);
            }
        }

        return newLore;
    }

    private void loadLevels(String itempath,List<ItemStack> levelItems) {
        List<Integer> slots = getInvCfg().getConfiguration().getIntegerList(itempath+".slots");

        int first = slots.size()*page;
        for (int i : slots) {
            try {
                gui.setItem(i,levelItems.get(first));
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
