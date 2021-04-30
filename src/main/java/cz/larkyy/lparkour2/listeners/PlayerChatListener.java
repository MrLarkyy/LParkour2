package cz.larkyy.lparkour2.listeners;

import cz.larkyy.lparkour2.LParkour2;
import cz.larkyy.lparkour2.handlers.MessageHandler;
import cz.larkyy.lparkour2.inventories.MapCreatorsMenu;
import cz.larkyy.lparkour2.objects.EditingPlayer;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.AsyncPlayerChatEvent;
import org.bukkit.scheduler.BukkitRunnable;

public class PlayerChatListener implements Listener {

    @EventHandler
    public void onChat(AsyncPlayerChatEvent e) {
        Player p = e.getPlayer();
        if (!getMain().getPlayerHandler().isEditingPlayer(p)) {
            return;
        }
        EditingPlayer editingPlayer = getMain().getPlayerHandler().getEditingPlayer(p);

        if (editingPlayer.isAddingCreators()) {
            e.setCancelled(true);

            String str = e.getMessage();

            if (str.equalsIgnoreCase("cancel")) {
                editingPlayer.setAddingCreators(false);
                openCreatorsInv(p,editingPlayer);
                p.sendTitle("","",0,0,0);
                return;
            }

            if (str.split(" ").length > 1) {
                MessageHandler.INVALID_NAME.sendMsg(p);
                return;
            }

            if (editingPlayer.getCreators().contains(str)) {
                MessageHandler.ALREADY_CREATOR.sendMsg(p);
                return;
            }

            editingPlayer.addCreator(str);
            openCreatorsInv(p,editingPlayer);
            p.sendMessage(MessageHandler.CREATOR_ADDED.getStr().replace("%name%",str));
            p.sendTitle("","",0,0,0);
            return;
        }

        if (e.getMessage().equalsIgnoreCase("cancel")) {

            e.setCancelled(true);

            p.getInventory().setContents(editingPlayer.getInventory());
            getMain().getPlayerHandler().removeEditingPlayer(editingPlayer);
            p.sendMessage("Level creating cancelled!");
        }

    }

    private LParkour2 getMain() {
        return LParkour2.getMain();
    }

    private void openCreatorsInv(Player p,EditingPlayer editingPlayer) {
        new BukkitRunnable() {
            @Override
            public void run() {
                p.openInventory(new MapCreatorsMenu(editingPlayer).getInventory());
            }
        }.runTask(getMain());
    }
}
