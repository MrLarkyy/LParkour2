package cz.larkyy.lparkour2.commands;

import cz.larkyy.lparkour2.LParkour2;
import cz.larkyy.lparkour2.handlers.MessageHandler;
import cz.larkyy.lparkour2.handlers.PermissionHandler;
import cz.larkyy.lparkour2.objects.LevelObj;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class EditCommand {
    public EditCommand(CommandSender sender, String[] args) {

        if (!(sender instanceof Player)) {
            MessageHandler.ONLY_PLAYER.sendMsg(sender);
            return;
        }

        Player p = (Player) sender;

        if (!PermissionHandler.EDIT.has(p,true)) {
            return;
        }

        if (args.length < 2) {
            p.sendMessage(MessageHandler.SYNTAX.buildSyntaxStr("edit","name"));
            return;
        }

        if (!getMain().getLevelsHandler().isStringLevel(args[1])) {
            MessageHandler.UNKNOWN_LEVEL.sendMsg(p);
            return;
        }

        LevelObj level = getMain().getLevelsHandler().getLevelByName(args[1]);

        getMain().getPlayerHandler().addEditingPlayer(p,level);
        MessageHandler.EDITING.sendMsg(p);
    }
    private LParkour2 getMain(){
        return LParkour2.getMain();
    }
}
