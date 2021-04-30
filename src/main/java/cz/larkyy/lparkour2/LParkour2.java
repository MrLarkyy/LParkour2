package cz.larkyy.lparkour2;

import cz.larkyy.lparkour2.commands.MainCommand;
import cz.larkyy.lparkour2.handlers.ConfigHandler;
import cz.larkyy.lparkour2.handlers.ContentsHandler;
import cz.larkyy.lparkour2.handlers.LevelsHandler;
import cz.larkyy.lparkour2.handlers.PlayerHandler;
import cz.larkyy.lparkour2.hooks.PlaceholderAPIHook;
import cz.larkyy.lparkour2.listeners.ListenerRegisters;
import cz.larkyy.lparkour2.runnables.EditingPlayerRunnable;
import cz.larkyy.lparkour2.utils.Utils;
import org.bukkit.Bukkit;
import org.bukkit.plugin.java.JavaPlugin;

public final class LParkour2 extends JavaPlugin {

    private Utils utils;
    private static LParkour2 main;
    private ConfigHandler cfg;
    private ConfigHandler invCfg;
    private ConfigHandler msgCfg;
    private PlayerHandler playerHandler;
    private LevelsHandler levelsHandler;
    private ContentsHandler contentsHandler;
    private PlaceholderAPIHook placeholderAPIHook;
    private ListenerRegisters listenerRegisters;

    @Override
    public void onEnable() {

        main = this;

        if (!getDataFolder().exists())
            getDataFolder().mkdir();

        utils = new Utils(this);
        cfg = new ConfigHandler(this,"config.yml");
        msgCfg = new ConfigHandler(this,"messages.yml");
        invCfg = new ConfigHandler(this,"inventories.yml");
        levelsHandler = new LevelsHandler();
        playerHandler = new PlayerHandler();
        contentsHandler = new ContentsHandler();
        listenerRegisters = new ListenerRegisters();

        getCommand("lparkour").setExecutor(new MainCommand(this));

        loadCfgs();
        levelsHandler.loadCategories();

        if (Bukkit.getPluginManager().getPlugin("PlaceholderAPI") != null) {
            utils.sendConsoleMsg("&d[EasterEggs] &fLoading &ePlaceholderAPI&f hook...");
            placeholderAPIHook = new PlaceholderAPIHook();
            placeholderAPIHook.register();
        }

        new EditingPlayerRunnable().runTaskTimerAsynchronously(this,50,10);

    }

    @Override
    public void onDisable() {
        // Plugin shutdown logic
    }

    public ConfigHandler getMsgCfg() {
        return msgCfg;
    }
    public ConfigHandler getInvCfg() { return invCfg; }
    public Utils getUtils() {
        return utils;
    }
    public ConfigHandler getCfg() {
        return cfg;
    }
    public void loadCfgs() {
        cfg.load();
        msgCfg.load();
        invCfg.load();
        levelsHandler.loadCategories();
    }

    public ListenerRegisters getListenerRegisters() {
        return listenerRegisters;
    }

    public static LParkour2 getMain() {
        return main;
    }

    public LevelsHandler getLevelsHandler() {
        return levelsHandler;
    }

    public ContentsHandler getContentsHandler() {
        return contentsHandler;
    }

    public PlayerHandler getPlayerHandler(){
        return playerHandler;
    }
}
