package cz.larkyy.lparkour2.objects;

import cz.larkyy.lparkour2.LParkour2;
import org.bukkit.Location;

import java.util.HashMap;
import java.util.List;

public class LevelObj {

    private String name;
    private String difficulty;
    // LIST OF TIMES
    private Location start;
    private Location end;
    private List<String> creators;
    private HashMap<Location,CheckpointObj> checkpoints;

    private final LParkour2 main;

    public LevelObj(LParkour2 main, String name, String difficulty, List<String> creators, Location start, Location end, HashMap<Location,CheckpointObj> checkpoints) {
        this.main = main;
        this.name = name;
        this.difficulty = difficulty;
        this.creators = creators;
        this.start = start;
        this.end = end;
        this.checkpoints = checkpoints;
    }

    public HashMap<Location,CheckpointObj> getCheckpoints() {
        return checkpoints;
    }

    public void addCheckpoint(CheckpointObj checkpoint) {
        checkpoints.put(checkpoint.getLocation(),checkpoint);
    }

    public void setCheckpoints(HashMap<Location,CheckpointObj> checkpoints) {
        this.checkpoints = checkpoints;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setEnd(Location end) {
        this.end = end;
    }

    public void setDifficulty(String difficulty) {
        this.difficulty = difficulty;
    }

    public void setCreators(List<String> creators) {
        this.creators = creators;
    }

    public void setStart(Location start) {
        this.start = start;
    }

    public String getDifficulty() {
        return difficulty;
    }

    /*public HashMap<UUID, Long> getTimes() {
        return times;
    }*/

    public List<String> getCreators() {
        return creators;
    }

    public Location getEnd() {
        return end;
    }

    public Location getStart() {
        return start;
    }
}
