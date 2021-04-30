package cz.larkyy.lparkour2.handlers;

import cz.larkyy.lparkour2.LParkour2;
import org.bukkit.entity.Player;

public enum PermissionHandler {

    CREATE("create","lparkour.create"),
    EDIT("edit","lparkour.edit"),
    SETSPAWN("setspawn","lparkour.setspawn");

    private final String path;
    private final String defValue;

    PermissionHandler(String path,String defValue) {
        this.defValue = defValue;
        this.path = "settings.permissions."+path;
    }

    public boolean has(Player p) {
        return (p.hasPermission(LParkour2.getMain().getCfg().getString(path,defValue)));
    }

    public boolean has(Player p,boolean msg) {
        final boolean bool = p.hasPermission(LParkour2.getMain().getCfg().getString(path,defValue));
        if (msg && !bool)
            MessageHandler.NO_PERMISSION.sendMsg(p);
        return bool;
    }

}
