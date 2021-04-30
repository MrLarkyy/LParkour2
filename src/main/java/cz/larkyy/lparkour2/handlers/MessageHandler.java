package cz.larkyy.lparkour2.handlers;

import cz.larkyy.lparkour2.LParkour2;
import cz.larkyy.lparkour2.utils.Utils;
import org.bukkit.command.CommandSender;

import java.util.Arrays;
import java.util.List;

public enum MessageHandler {

    UNKNOWN_PLAYER("messages.unknownPlayer","&cUnknown Player!"),
    RELOAD("messages.reload","&6[lParkour] &fPlugin has been reloaded!"),
    CREATOR_REMOVED("messages.creatorRemoved","&6[lParkour] &fYou have removed creator &e%name%&f!"),
    CREATOR_ADDED("messages.creatorAdded","&6[lParkour] &fYou have added creator &e%name%&f!"),
    ALREADY_CREATOR("messages.alreadyCreator","&cThere is already a creator with this name!"),
    INVALID_NAME("messages.invalidName","&cInvalid creator name!"),
    HELP_MESSAGE("messages.help", Arrays.asList("Missing Message in the config!")),
    NO_PERMISSION("messages.noPermission","&cNo Permission!"),
    SYNTAX("messages.usage.syntax","&cInvalid Command Usage! &7Usage: &f/parkour %arguments%"),
    USAGE_ARG("messages.usage.argument","<%arg%>"),
    CREATING("messages.creating","&6[lParkour] &fYou are now creating a new level!"),
    EDITING("messages.editing","&6[lParkour] &fYou are now editing the level!"),
    CREATED("messages.created","&6[lParkour] &fThe level has been created!"),
    EDITED("messages.edited","&6[lParkour] &fThe level has been edited!"),
    ALREADY_LEVEL("messages.alreadyLevel","&cThere is already a level with this name!"),
    UNKNOWN_LEVEL("messages.unknownLevel","&cThere is no level with this name!"),
    SPAWN_SET("messages.spawnSet","&6[lParkour] &fYou have set a new spawn location!"),
    START_SET("messages.startSet","&6[lParkour] &fYou have set a new start location!"),
    END_SET("messages.endSet","&6[lParkour] &fYou have set a new end location!"),
    CATEGORY_SET("messages.categorySet","&6[lParkour] &fYou have set the category to %name%!"),
    CHECKPOINT_ADD("messages.checkpointAdd","&6[lParkour] &fYou have added a new checkpoint!"),
    CHECKPOINT_REMOVE("messages.checkpointRemove","&6[lParkour] &fYou have removed the checkpoint!"),
    MUST_SET_START_LOCATION("messages.noStartLocation","&cYou must set the start location!"),
    MUST_SET_END_LOCATION("messages.noEndLocation","&cYou must set the end location!"),
    MUST_SET_CATEGORY("messages.noCategory","&cYou must set the category!"),
    NO_CHECKPOINT("messages.noCheckpoint","&cThere is no checkpoint at this location!"),
    ALREADY_CHECKPOINT("messages.alreadyCheckpoint","&cThere is already a checkpoint at this location!"),
    NOT_SIGN("messages.notSign","&cYou must click at a sign!"),
    UNKNOWN_CATEGORY("messages.unknownCategory","&cUnknown category, please check configuration!"),
    ONLY_PLAYER("messages.onlyPlayer","&cOnly players can use this command!");

    private final String path;
    private String defValue = null;
    private List<String> defValueList = null;

    MessageHandler(String path,String defValue) {
        this.path = path;
        this.defValue = defValue;
    }
    MessageHandler(String path,List<String> defValue) {
        this.path = path;
        this.defValueList = defValue;
    }

    public String getStr() {
        return Utils.format(LParkour2.getMain().getMsgCfg().getString(path,defValue));
    }

    public List<String> getList() {
        return getUtils().formatList(LParkour2.getMain().getMsgCfg().getStringList(path,defValueList));
    }

    public String buildSyntaxStr(String subCmd, String arg) {
        return getStr().replace("%arguments%",subCmd+" "+USAGE_ARG.getStr())
                .replace("%arg%",arg);
    }

    public void sendMsg(CommandSender sender) {
        if (defValueList==null) {
            sender.sendMessage(getStr());
            return;
        }
        for (String str : getList()) {
            sender.sendMessage(str);
        }
    }

    private Utils getUtils() {
        return LParkour2.getMain().getUtils();
    }
}
