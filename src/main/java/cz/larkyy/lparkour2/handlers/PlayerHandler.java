package cz.larkyy.lparkour2.handlers;

import cz.larkyy.lparkour2.LParkour2;
import cz.larkyy.lparkour2.objects.EditingPlayer;
import cz.larkyy.lparkour2.objects.LevelObj;
import cz.larkyy.lparkour2.objects.PlayerObj;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

public class PlayerHandler {

    private HashMap<UUID, EditingPlayer> editingPlayers = new HashMap<>();

    private HashMap<UUID, PlayerObj> players = new HashMap<>();

    private Location spawn = null;

    public Location getSpawn() {
        return spawn;
    }

    public void addPlayer(UUID uuid) {
        PlayerObj player = new PlayerObj(uuid,new ArrayList<>(),new ArrayList<>(),null);
        players.put(uuid,player);
    }

    public PlayerObj getPlayer(UUID uuid) {
        if (!players.containsKey(uuid))
            addPlayer(uuid);
        return players.get(uuid);
    }

    public void setSpawn(Location spawn) {
        this.spawn = spawn;
    }

    public void addEditingPlayer(Player p, String name) {
        EditingPlayer editingPlayer = new EditingPlayer(p,name);
        this.editingPlayers.put(p.getUniqueId(),editingPlayer);
        getMain().getContentsHandler().setEditingItems(p);
    }
    public void addEditingPlayer(Player p, LevelObj level) {
        EditingPlayer editingPlayer = new EditingPlayer(p,level);
        this.editingPlayers.put(p.getUniqueId(),editingPlayer);
        getMain().getContentsHandler().setEditingItems(p);
    }

    private LParkour2 getMain() {
        return LParkour2.getMain();
    }

    public void resetEditingPlayers() {
        for (Map.Entry<UUID,EditingPlayer> pair : editingPlayers.entrySet()) {
            Player p = Bukkit.getPlayer(pair.getKey());
            if (p!=null) {

                p.getInventory().setContents(pair.getValue().getInventory());
                getMain().getContentsHandler().setSpawnItems(p);
                if (spawn != null)
                    p.teleport(spawn);
            }
        }
        editingPlayers = new HashMap<>();
    }

    public boolean isEditingPlayer(Player p) {
        return editingPlayers.containsKey(p.getUniqueId());
    }

    public EditingPlayer getEditingPlayer(Player p){
        return editingPlayers.get(p.getUniqueId());
    }

    public void removeEditingPlayer(EditingPlayer player) {
        this.editingPlayers.remove(player.getUuid());
    }
}
