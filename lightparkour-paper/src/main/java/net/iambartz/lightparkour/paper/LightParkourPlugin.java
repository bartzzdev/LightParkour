package net.iambartz.lightparkour.paper;

import net.iambartz.lightparkour.paper.player.listener.PlayerJoinListener;
import org.bukkit.Bukkit;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.logging.Logger;

public class LightParkourPlugin extends JavaPlugin {

    @Override
    public void onEnable() {
        initialize();
    }

    private void initialize() {
        initializeListeners();
    }

    private void initializeListeners() {
        var api = LightParkourPaperApi.get();
        api.mapRepository();
        var logger = Logger.getLogger("LightParkour");
        logger.info("Initializing listeners");
        var pluginManager = Bukkit.getPluginManager();
        pluginManager.registerEvents(new PlayerJoinListener(), this);
    }
}
