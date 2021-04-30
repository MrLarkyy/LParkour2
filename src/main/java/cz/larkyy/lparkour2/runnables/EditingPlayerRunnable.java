package cz.larkyy.lparkour2.runnables;

import cz.larkyy.lparkour2.LParkour2;
import cz.larkyy.lparkour2.objects.EditingPlayer;
import org.bukkit.Bukkit;
import org.bukkit.Color;
import org.bukkit.Particle;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;

public class EditingPlayerRunnable extends BukkitRunnable {

    @Override
    public void run() {
        if (!Bukkit.getOnlinePlayers().isEmpty()) {
            for (Player p : Bukkit.getOnlinePlayers()) {
                if (getMain().getPlayerHandler().isEditingPlayer(p)) {
                    EditingPlayer editingPlayer = getMain().getPlayerHandler().getEditingPlayer(p);

                    if (editingPlayer.getStart() != null) {
                        p.spawnParticle(Particle.VILLAGER_HAPPY,editingPlayer.getStart(),7,0.3,0.3,0.3);
                    }

                    if (editingPlayer.getEnd() != null) {
                        Particle.DustOptions dust = new Particle.DustOptions(
                                Color.fromRGB(255, 0, 0), 1);

                        p.spawnParticle(Particle.REDSTONE,editingPlayer.getEnd().clone().add(0.5,0.5,0.5),7,0.3,0.3,0.3,dust);
                    }
                }
            }
        }
    }

    private LParkour2 getMain() {
        return LParkour2.getMain();
    }

}
