package cz.larkyy.lparkour2.listeners;

import cz.larkyy.lparkour2.LParkour2;
import cz.larkyy.lparkour2.handlers.PlayerHandler;
import cz.larkyy.lparkour2.objects.EditingPlayer;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerQuitEvent;

public class PlayerLeaveListener implements Listener {

    @EventHandler
    public void onLeave(PlayerQuitEvent e) {
        Player p = e.getPlayer();

        if (getPlayerHandler().isEditingPlayer(p)) {
            EditingPlayer editingPlayer = getPlayerHandler().getEditingPlayer(p);

            p.getInventory().setContents(editingPlayer.getInventory());
            getPlayerHandler().removeEditingPlayer(editingPlayer);
        }
    }

    private LParkour2 getMain() {
        return LParkour2.getMain();
    }

    private PlayerHandler getPlayerHandler() {
        return getMain().getPlayerHandler();
    }
}
