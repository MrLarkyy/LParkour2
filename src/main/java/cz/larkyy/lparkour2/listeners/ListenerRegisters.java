package cz.larkyy.lparkour2.listeners;

import cz.larkyy.lparkour2.LParkour2;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class ListenerRegisters {

    private List<UUID> playersDropped = new ArrayList<>();

    public ListenerRegisters(){

        getMain().getServer().getPluginManager().registerEvents(new PlayerInteractListener(),getMain());
        getMain().getServer().getPluginManager().registerEvents(new PlayerDropListener(),getMain());
        getMain().getServer().getPluginManager().registerEvents(new PlayerInventoryClickEvent(),getMain());
        getMain().getServer().getPluginManager().registerEvents(new PlayerChatListener(),getMain());
        getMain().getServer().getPluginManager().registerEvents(new PlayerJoinListener(),getMain());
        getMain().getServer().getPluginManager().registerEvents(new PlayerLeaveListener(),getMain());
        getMain().getServer().getPluginManager().registerEvents(new PlayerMoveListener(),getMain());

    }
    public void removePlayerDrop(Player p) {
        playersDropped.remove(p.getUniqueId());
    }
    public List<UUID> getPlayersDropped() {
        return playersDropped;
    }

    public void addPlayerDrop(Player p){
        playersDropped.add(p.getUniqueId());
        new BukkitRunnable() {
            @Override
            public void run() {
                getMain().getListenerRegisters().removePlayerDrop(p);
            }
        }.runTaskLater(getMain(),1);
    }

    private LParkour2 getMain() {
        return LParkour2.getMain();
    }
}
