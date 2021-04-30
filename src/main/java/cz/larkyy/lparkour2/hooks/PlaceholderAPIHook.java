package cz.larkyy.lparkour2.hooks;

import cz.larkyy.lparkour2.LParkour2;
import me.clip.placeholderapi.PlaceholderAPI;
import me.clip.placeholderapi.expansion.PlaceholderExpansion;
import org.bukkit.OfflinePlayer;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

public class PlaceholderAPIHook extends PlaceholderExpansion {

    public String replaceMsg(Player p, String msg) {
        return PlaceholderAPI.setPlaceholders(p, msg);
    }

    @Override
    public @NotNull String getIdentifier() {
        return "lparkour";
    }

    @Override
    public @NotNull String getAuthor() {
        return "MrLarkyy_";
    }

    @Override
    public @NotNull String getVersion() {
        return "1.0.0";
    }

    @Override
    public boolean canRegister() {
        return true;
    }

    @Override
    public String onRequest(OfflinePlayer player, @NotNull String params) {

        if (params.equalsIgnoreCase("totalplayed"))
            return getMain().getPlayerHandler().getPlayer(player.getUniqueId()).getTotal() + "";

        if (params.equalsIgnoreCase("totalcategories"))
            return getMain().getLevelsHandler().getCategories().size() + "";

        if (params.equalsIgnoreCase("played"))
            return getMain().getPlayerHandler().getPlayer(player.getUniqueId()).getLevels().size() + "";

        /*if (params.equalsIgnoreCase("total"))
            return getMain().storageUtils.getEggs().size() + "";
*/
        return null;
    }

    @Override
    public boolean register() {
        return super.register();
    }

    private LParkour2 getMain() {
        return LParkour2.getMain();
    }
}

