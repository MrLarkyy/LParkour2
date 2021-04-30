package cz.larkyy.lparkour2.listeners;

import cz.larkyy.lparkour2.LParkour2;
import cz.larkyy.lparkour2.handlers.PlayerHandler;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerDropItemEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.scheduler.BukkitRunnable;

public class PlayerDropListener implements Listener {

    @EventHandler
    public void onDrop(PlayerDropItemEvent e) {
        Player p = e.getPlayer();

        p.getLocation().getDirection();

        getMain().getListenerRegisters().addPlayerDrop(p);
        e.setCancelled(true);

        if (getPlayerHandler().isEditingPlayer(p)) {
            e.setCancelled(true);

            ItemStack is = e.getItemDrop().getItemStack();

            if (is.getItemMeta()==null) {
                return;
            }

            String str = is.getItemMeta().getLocalizedName();
            if (str.contains("checkpoint")) {
                e.getItemDrop().setItemStack(getMain().getContentsHandler().switchCpType(str));
            }
        }
    }

    private LParkour2 getMain() {
        return LParkour2.getMain();
    }

    private PlayerHandler getPlayerHandler() {
        return getMain().getPlayerHandler();
    }
}
