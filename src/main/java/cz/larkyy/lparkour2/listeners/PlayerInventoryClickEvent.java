package cz.larkyy.lparkour2.listeners;

import cz.larkyy.lparkour2.LParkour2;
import cz.larkyy.lparkour2.handlers.MessageHandler;
import cz.larkyy.lparkour2.inventories.*;
import cz.larkyy.lparkour2.objects.CheckpointObj;
import cz.larkyy.lparkour2.objects.EditingPlayer;
import cz.larkyy.lparkour2.objects.LevelObj;
import cz.larkyy.lparkour2.objects.PlayerObj;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.InventoryHolder;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.Map;

public class PlayerInventoryClickEvent implements Listener {

    @EventHandler
    public void onInvClick(InventoryClickEvent e) {

        if (!(e.getWhoClicked() instanceof Player))
            return;

        Player p = (Player) e.getWhoClicked();
        InventoryHolder holder = e.getInventory().getHolder();

        getMain().getListenerRegisters().addPlayerDrop(p);

        if (e.getCurrentItem()==null || e.getCurrentItem().getType().equals(Material.AIR))
            return;
        if (e.getCurrentItem().getItemMeta()==null)
            return;
        ItemMeta im = e.getCurrentItem().getItemMeta();

        if (holder instanceof MapCategorySelectionMenu) {
            e.setCancelled(true);

            if (!getMain().getPlayerHandler().isEditingPlayer(p)) {
                return;
            }

            EditingPlayer editingPlayer = getMain().getPlayerHandler().getEditingPlayer(p);

            if (im.getLocalizedName().contains("category")) {
                String category = im.getLocalizedName().split(" ")[1];
                if (!getMain().getLevelsHandler().getCategories().contains(category)) {
                    MessageHandler.UNKNOWN_CATEGORY.sendMsg(p);
                    return;
                }

                editingPlayer.setDifficulty(category);
                p.closeInventory();
                p.sendMessage(MessageHandler.CATEGORY_SET.getStr().replace("%name%",category));
            }
            return;
        }
        if (holder instanceof MapCreatorsMenu) {
            e.setCancelled(true);

            if (!getMain().getPlayerHandler().isEditingPlayer(p)) {
                return;
            }

            EditingPlayer editingPlayer = getMain().getPlayerHandler().getEditingPlayer(p);

            if (im.getLocalizedName().equals("addCreator")) {
                getMain().getUtils().sendTitleMsg(p,"&a&lTYPE PLAYER NAME","&7To continue, please type a name.",0,Integer.MAX_VALUE,0);
                editingPlayer.setAddingCreators(true);
                p.closeInventory();
            }
            if (im.getLocalizedName().contains("creator ")) {
                String creator = im.getLocalizedName().split(" ")[1];
                editingPlayer.removeCreator(creator);
                p.openInventory(new MapCreatorsMenu(editingPlayer).getInventory());
                p.sendMessage(MessageHandler.CREATOR_REMOVED.getStr().replace("%name%",creator));
            }

        }

        if (holder instanceof CategoryMenu) {
            e.setCancelled(true);

            if (im.getLocalizedName().contains("category")) {
                String category = im.getLocalizedName().split(" ")[1];
                if (!getMain().getLevelsHandler().getCategories().contains(category)) {
                    p.sendMessage("Unknown category!");
                    return;
                }
                p.openInventory(new LevelsMenu(category,0).getInventory());
            }
        }
        if (holder instanceof LevelsMenu) {
            e.setCancelled(true);

            if (im.getLocalizedName().contains("Level")) {
                String levelString = im.getLocalizedName().split(" ")[1];
                LevelObj level = getMain().getLevelsHandler().getLevelByName(levelString);

                PlayerObj player = getMain().getPlayerHandler().getPlayer(p.getUniqueId());
                p.teleport(level.getStart());
                player.setCploc(level.getStart());
                player.setActualLevel(level);

            }
        }
        if (holder instanceof LocationCheckpointsMenu) {
            e.setCancelled(true);
            if (im.getLocalizedName().contains("Checkpoint")) {
                String[] locString = im.getLocalizedName().split(" ");
                Location loc = new Location(
                        Bukkit.getWorld(locString[1]),
                        Double.parseDouble(locString[2]),
                        Double.parseDouble(locString[3]),
                        Double.parseDouble(locString[4]),
                        Float.parseFloat(locString[5]),
                        Float.parseFloat(locString[6])
                );
                if (e.isLeftClick()) {
                    p.closeInventory();
                    p.teleport(loc);
                }
                else {
                    EditingPlayer player = getMain().getPlayerHandler().getEditingPlayer(p);
                    if (player.getCheckpoints()==null) {
                        p.closeInventory();
                        return;
                    }
                    if (player.getCheckpoints().isEmpty()) {
                        p.closeInventory();
                        return;
                    }
                    for (Map.Entry<Location, CheckpointObj> pair: player.getCheckpoints().entrySet()) {
                        if (pair.getKey().equals(loc)) {
                            player.removeCheckpoint(pair.getValue());
                            p.sendMessage("Checkpoint removed!");
                            p.closeInventory();
                            return;
                        }
                    }
                }
            }
        }

        if (im.getLocalizedName().equals("levels")) {
            e.setCancelled(true);
        }
        if (im.getLocalizedName().equals("checkpoint")) {
            e.setCancelled(true);
        }

    }

    private LParkour2 getMain() {
        return LParkour2.getMain();
    }
}
