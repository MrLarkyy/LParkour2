package cz.larkyy.lparkour2.handlers;

import cz.larkyy.lparkour2.LParkour2;
import cz.larkyy.lparkour2.objects.EditingPlayer;
import cz.larkyy.lparkour2.objects.LevelObj;
import org.bukkit.Location;
import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class LevelsHandler {

    private HashMap<String, LevelObj> levels = new HashMap<>();

    private List<String> categories = new ArrayList<>();

    public HashMap<String, LevelObj> getLevels() {
        return levels;
    }
    public LevelObj getLevelByName(String name) {
        return levels.get(name);
    }

    public void remLevel(LevelObj level) {
        levels.remove(level.getName());
    }

    public void addLevel(LevelObj level) {
        levels.put(level.getName(),level);
    }

    public boolean isStringLevel(String str) {
        return (levels.containsKey(str));
    }

    public List<String> getCategories() {
        return categories;
    }

    public void loadCategories() {
        this.categories = getMain().getCfg().getStringList("categories",new ArrayList<>());
    }

    private LParkour2 getMain() {
        return LParkour2.getMain();
    }

    public void editLevelByPlayer(Player p, EditingPlayer editingPlayer) {
        LevelObj level = editingPlayer.getLevel();

        level.setName(editingPlayer.getName());
        level.setDifficulty(editingPlayer.getDifficulty());
        level.setStart(editingPlayer.getStart());
        level.setCreators(editingPlayer.getCreators());
        level.setEnd(editingPlayer.getEnd());
        level.setCreators(editingPlayer.getCreators());
        level.setCheckpoints(editingPlayer.getCheckpoints());

        MessageHandler.EDITED.sendMsg(p);
        p.getInventory().setContents(editingPlayer.getInventory());
        getMain().getPlayerHandler().removeEditingPlayer(editingPlayer);

    }

    public void addLevelByPlayer(Player p, EditingPlayer editingPlayer) {

        Location end = editingPlayer.getEnd().clone();
        end.setX(Math.floor(end.getX()));
        end.setY(Math.floor(end.getY()));
        end.setZ(Math.floor(end.getZ()));
        end.setYaw(0);
        end.setPitch(0);

        LevelObj level = new LevelObj(
                getMain(),
                editingPlayer.getName(),
                editingPlayer.getDifficulty(),
                editingPlayer.getCreators(),
                editingPlayer.getStart(),
                end,
                new HashMap<>()
        );

        level.setCheckpoints(editingPlayer.getCheckpoints());

        getMain().getLevelsHandler().addLevel(level);

        MessageHandler.CREATED.sendMsg(p);
        p.getInventory().setContents(editingPlayer.getInventory());
        getMain().getPlayerHandler().removeEditingPlayer(editingPlayer);

    }

}
