package cz.larkyy.lparkour2.commands;

import cz.larkyy.lparkour2.LParkour2;
import cz.larkyy.lparkour2.handlers.MessageHandler;
import cz.larkyy.lparkour2.handlers.PermissionHandler;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class SetSpawnCommand {

    public SetSpawnCommand(CommandSender sender, String[] args) {

        if (!(sender instanceof Player)) {
            MessageHandler.ONLY_PLAYER.sendMsg(sender);
            return;
        }
        Player p = (Player) sender;

        if (!PermissionHandler.SETSPAWN.has(p,true)) {
            return;
        }

        getMain().getPlayerHandler().setSpawn(p.getLocation());
        MessageHandler.SPAWN_SET.sendMsg(p);

    }

    private LParkour2 getMain(){
        return LParkour2.getMain();
    }
}
