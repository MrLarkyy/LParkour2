package cz.larkyy.lparkour2.objects;

import org.bukkit.Location;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class PlayerObj {

    private List<LevelObj> levels;

    private List<String> total;

    private final UUID uuid;

    private LevelObj actualLevel;

    private CheckpointObj checkpoint = null;
    private Location cpLoc = null;

    public PlayerObj(UUID uuid, List<LevelObj> levels, List<String> total, LevelObj actualLevel) {
        this.levels = levels;
        this.uuid = uuid;
        this.total = total;
        this.actualLevel = actualLevel;
    }

    public UUID getUuid() {
        return uuid;
    }

    public List<String> getTotal() {
        return total;
    }

    public void addTotal(String category) {
        total.add(category);
    }

    public CheckpointObj getCheckpoint() {
        return checkpoint;
    }

    public void setCheckpoint(CheckpointObj checkpoint) {
        this.checkpoint = checkpoint;
    }

    public LevelObj getActualLevel() {
        return actualLevel;
    }

    public List<LevelObj> getLevels() {
        return levels;
    }

    public void addLevel(LevelObj level) {
        addTotal(level.getDifficulty());
        if (!levels.contains(level))
            this.levels.add(level);
    }

    public void setActualLevel(LevelObj level) {
        this.actualLevel = level;
    }

    public Location getCpLoc() {
        return cpLoc;
    }

    public void setCploc(Location cploc) {
        this.cpLoc = cploc;
    }

    public boolean isInLevel() {
        return (actualLevel!=null);
    }

}
