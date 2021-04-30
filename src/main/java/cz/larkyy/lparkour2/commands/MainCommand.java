package cz.larkyy.lparkour2.commands;

import cz.larkyy.lparkour2.LParkour2;
import cz.larkyy.lparkour2.handlers.MessageHandler;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

public class MainCommand implements CommandExecutor {

    private final LParkour2 main;

    public MainCommand(LParkour2 main) {
        this.main = main;
    }

    @Override
    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command command, @NotNull String label, @NotNull String[] args) {

        if (args.length < 1) {
            MessageHandler.HELP_MESSAGE.sendMsg(sender);
            return false;
        }
        if (sender instanceof Player) {
            switch (args[0]) {
                case "create":
                    new CreateCommand(sender,args);
                    break;
                case "edit":
                    new EditCommand(sender,args);
                    break;
                case "reload":
                    MessageHandler.RELOAD.sendMsg(sender);
                    main.getPlayerHandler().resetEditingPlayers();
                    main.loadCfgs();
                    break;
                default:
                    MessageHandler.HELP_MESSAGE.sendMsg(sender);
                    break;
            }
        }
        return false;
    }

    public LParkour2 getMain() {
        return main;
    }
}
