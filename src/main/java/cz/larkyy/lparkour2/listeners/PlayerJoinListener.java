package cz.larkyy.lparkour2.listeners;

import cz.larkyy.lparkour2.LParkour2;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;

public class PlayerJoinListener implements Listener {

    @EventHandler
    public void onJoin(PlayerJoinEvent e) {
        Player p = e.getPlayer();

        getMain().getContentsHandler().setSpawnItems(p);

    }

    private LParkour2 getMain() {
        return LParkour2.getMain();
    }
}
