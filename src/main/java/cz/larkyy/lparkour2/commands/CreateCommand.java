package cz.larkyy.lparkour2.commands;

import cz.larkyy.lparkour2.LParkour2;
import cz.larkyy.lparkour2.handlers.MessageHandler;
import cz.larkyy.lparkour2.handlers.PermissionHandler;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class CreateCommand {

    public CreateCommand(CommandSender sender, String[] args) {

        if (!(sender instanceof Player)) {
            MessageHandler.ONLY_PLAYER.sendMsg(sender);
            return;
        }
        Player p = (Player) sender;

        if (!PermissionHandler.CREATE.has(p,true)) {
            return;
        }

        if (args.length < 2) {
            p.sendMessage(MessageHandler.SYNTAX.buildSyntaxStr("create","name"));
            return;
        }

        if (getMain().getLevelsHandler().isStringLevel(args[1])) {
            MessageHandler.ALREADY_LEVEL.sendMsg(p);
            return;
        }

        getMain().getPlayerHandler().addEditingPlayer(p,args[1]);
        MessageHandler.CREATING.sendMsg(p);

    }

    private LParkour2 getMain(){
        return LParkour2.getMain();
    }

}
