package cz.larkyy.lparkour2.objects;

import org.bukkit.Location;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

import java.util.*;

public class EditingPlayer {

    private final UUID uuid;

    private final String name;

    private String difficulty;

    private Location start;

    private Location end;

    private List<String> creators;

    private LevelObj level = null;

    private final ItemStack[] inventory;

    private HashMap<Location,CheckpointObj> checkpoints = new HashMap<>();

    private boolean addingCreators = false;

    public EditingPlayer(Player p, String name) {
        this.name = name;
        this.uuid = p.getUniqueId();
        this.inventory = p.getInventory().getContents();
        this.creators = new ArrayList<>();
    }

    public EditingPlayer(Player p, LevelObj level) {
        this.level = level;
        this.name = level.getName();
        this.difficulty = level.getDifficulty();
        this.start = level.getStart();
        this.end = level.getEnd();
        this.creators = level.getCreators();
        this.uuid = p.getUniqueId();
        this.inventory = p.getInventory().getContents();
        this.checkpoints = level.getCheckpoints();
    }

    public boolean isAddingCreators() {
        return addingCreators;
    }

    public void setAddingCreators(boolean addingCreators) {
        this.addingCreators = addingCreators;
    }

    public HashMap<Location,CheckpointObj> getCheckpoints() {
        return checkpoints;
    }

    public boolean isCheckPoint(CheckpointObj.CheckType type, Location loc) {
        for (Map.Entry<Location,CheckpointObj> pair : checkpoints.entrySet()) {
            if (pair.getValue().getType().equals(type)) {
                Location location = pair.getKey().clone();
                location.setYaw(0);
                location.setPitch(0);

                Location location2 = loc.clone();
                location2.setYaw(0);
                location2.setPitch(0);

                if (location2.equals(location))
                    return true;
            }
        }
        return false;
    }

    public void addCheckpoint(CheckpointObj checkpoint) {
        checkpoints.put(checkpoint.getLocation(), checkpoint);
    }

    public void removeCheckpoint(CheckpointObj checkpoint) {
        checkpoints.remove(checkpoint.getLocation());
    }

    public Location getStart() {
        return start;
    }

    public Location getEnd() {
        return end;
    }

    public List<String> getCreators() {
        return creators;
    }

    public String getDifficulty() {
        return difficulty;
    }

    public UUID getUuid() {
        return uuid;
    }

    public String getName() {
        return name;
    }

    public void setCreators(List<String> creators) {
        this.creators = creators;
    }

    public void addCreator(String name) {
        this.creators.add(name);
        setAddingCreators(false);
    }
    public void removeCreator(String name) {
        this.creators.remove(name);
    }

    public void setDifficulty(String difficulty) {
        this.difficulty = difficulty;
    }

    public void setEnd(Location end) {
        this.end = end;
    }

    public void setStart(Location start) {
        this.start = start;
    }

    public ItemStack[] getInventory() {
        return inventory;
    }

    public LevelObj getLevel() {
        return level;
    }

}
