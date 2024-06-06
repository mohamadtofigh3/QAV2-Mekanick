package ir.detiven.qav2mekanick;

import ir.detiven.qav2mekanick.commands.PickupVehiclesCommand;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.Objects;

public final class QAV2_Mekanick extends JavaPlugin {

    private static QAV2_Mekanick instance;
    public static QAV2_Mekanick getInstance() {
        return instance;
    }

    private long startTimerBaseSys;

    @Override
    public void onEnable() {
        startTimerBaseSys = System.currentTimeMillis();
        instance = this;

        saveDefaultConfig();
        reloadConfig();
        Config.init();

        if (getServer().getPluginManager().getPlugin("QualityArmoryVehicles2") == null) {
            getLogger().severe("plugin mashin shenahkte nashod");
            getServer().getPluginManager().disablePlugin(this);
            return;
        } else if (!getServer().getVersion().contains(Config.getSupportVersion)) {
            getLogger().severe("lotfan az version " + Config.getSupportVersion + " astefade konid");
            getServer().getPluginManager().disablePlugin(this);
            return;
        }

        init();
    }

    private void init() {
        Objects.requireNonNull(getCommand(Config.getCommandName)).setExecutor(new PickupVehiclesCommand());

        long enabledTimerMil = (startTimerBaseSys - System.currentTimeMillis());
        getLogger().info("enabled successfilly in " + enabledTimerMil + "ms");
    }

    @Override
    public void onDisable() {
        long endTimerMil = (startTimerBaseSys - System.currentTimeMillis());
        getLogger().info("plugin has disabled started " + endTimerMil + "ms ago");
        getLogger().info("plugin system is offline.");
    }
}