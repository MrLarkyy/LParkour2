package cz.larkyy.lparkour2.listeners;

import cz.larkyy.lparkour2.LParkour2;
import cz.larkyy.lparkour2.objects.CheckpointObj;
import cz.larkyy.lparkour2.objects.LevelObj;
import cz.larkyy.lparkour2.objects.PlayerObj;
import org.bukkit.Location;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerMoveEvent;

import java.util.HashMap;
import java.util.Map;

public class PlayerMoveListener implements Listener {

    @EventHandler
    public void onMove(PlayerMoveEvent e) {
        Player p = e.getPlayer();

        PlayerObj player = getMain().getPlayerHandler().getPlayer(p.getUniqueId());

        if (!player.isInLevel())
            return;

        LevelObj level = player.getActualLevel();

        Location loc1 = e.getFrom().getBlock().getLocation().clone();
        Location loc2 = e.getTo().getBlock().getLocation().clone();

        Location end = player.getActualLevel().getEnd();

        if (loc1.equals(loc2))
            return;

        if (loc2.distance(end)<=0.5) {
            p.sendMessage("Reached Finish!");
            player.addLevel(level);
            player.setActualLevel(null);
            player.setCheckpoint(null);
            return;
        }

        HashMap<Location,CheckpointObj> checkpoints = level.getCheckpoints();
        for (Map.Entry<Location,CheckpointObj> pair : checkpoints.entrySet()) {
            if (pair.getValue().getType().equals(CheckpointObj.CheckType.LOCATION)) {
                if (pair.getKey().distance(loc2)<=0.65) {
                    if (player.getCheckpoint()==null || player.getCheckpoint()!=null && !player.getCheckpoint().equals(pair.getValue())) {
                        p.sendMessage("You have reached the checkpoint!");
                        player.setCheckpoint(pair.getValue());
                        player.setCploc(pair.getKey());
                    } else {
                        p.sendMessage("You have already reached this checkpoint!");
                    }
                    return;
                }
            }
        }
    }

    private LParkour2 getMain() {
        return LParkour2.getMain();
    }
}
