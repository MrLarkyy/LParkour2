package cz.larkyy.lparkour2.handlers;

import cz.larkyy.lparkour2.LParkour2;
import cz.larkyy.lparkour2.utils.Utils;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;

public class ContentsHandler {

    // SPAWN ITEMS
    private final ItemStack levels = getUtils().mkItem(
            Material.COMPASS,
            Utils.format("&eLevel Selector"),
            "levels",
            null,
            null
    );
    private final ItemStack checkpoint = getUtils().mkItem(
            Material.NETHER_STAR,
            Utils.format("&eTeleport to Checkpoint"),
            "checkpoint",
            null,
            null
    );

    // EDIT ITEMS
    private final ItemStack category = getUtils().mkItem(
            Material.BOOK,
            Utils.format("&eCategory"),
            "category",
            null,
            null
    );

    private final ItemStack start = getUtils().mkItem(
            Material.PLAYER_HEAD,
            Utils.format("&eStart Location"),
            "start",
            null,
            "eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvYWUwNDA0YTM4OWJjNzRiMTU4MjVhNzJmM2YxZmU1MTQzOWMzNzk0MjQ4OTRiMzMyZGY5NTJhODQ2NCJ9fX0="
    );

    private final ItemStack end = getUtils().mkItem(
            Material.PLAYER_HEAD,
            Utils.format("&eEnd Location"),
            "end",
            null,
            "eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvYjk0MzI2YjUzOGVkOTg5ODcxMGQ1OGU1NTI0NzI2ZjMxMzAzNzM0NDgzZDFlNTIzN2VlMzI1YThiYmU1MjE3In19fQ=="
    );

    private final ItemStack locCp = getUtils().mkItem(
            Material.LIGHT_WEIGHTED_PRESSURE_PLATE,
            Utils.format("&eLocation Checkpoint"),
            "checkpoint location",
            null,
            null
    );

    private final ItemStack signCp = getUtils().mkItem(
            Material.OAK_SIGN,
            Utils.format("&eSign Checkpoint"),
            "checkpoint sign",
            null,
            null
    );


    private final ItemStack creators = getUtils().mkItem(
            Material.PLAYER_HEAD,
            Utils.format("&eCreators"),
            "creators",
            null,
            null
    );

    private final ItemStack finish = getUtils().mkItem(
            Material.OAK_FENCE_GATE,
            Utils.format("&eFinish the Setup"),
            "finish",
            null,
            null
    );

    public void setSpawnItems(Player p) {
        Inventory inv = p.getInventory();
        inv.clear();
        inv.setItem(0,levels);
        inv.setItem(1,checkpoint);

    }

    public ItemStack switchCpType(String type){
        if (type.contains("location"))
            return signCp;
        return locCp;

    }

    public void setEditingItems(Player p){
        Inventory inv = p.getInventory();
        inv.clear();
        inv.setItem(0,category);
        inv.setItem(1,start);
        inv.setItem(2,end);
        inv.setItem(3,locCp);
        inv.setItem(4,creators);
        inv.setItem(8,finish);
    }

    private Utils getUtils() {
        return LParkour2.getMain().getUtils();
    }

}
