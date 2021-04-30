package cz.larkyy.lparkour2.objects;

import org.bukkit.Location;

public class CheckpointObj {

    private Location location;
    private CheckType type;

    public enum CheckType {
        LOCATION,SIGN
    }

    public CheckpointObj(CheckType type, Location location) {
        this.location = location;
        this.type = type;
    }

    public Location getLocation() {
        return location;
    }

    public CheckType getType() {
        return type;
    }
}
