package ir.detiven.qav2mekanick;

import org.bukkit.ChatColor;
import org.bukkit.configuration.file.FileConfiguration;

import java.util.Objects;

public class Config {
    public static String getSupportVersion;
    public static String getCommandName;
    public static Boolean isEventCancel;
    public static String getUserCommand;
    public static String getGiveCommand;
    public static String getAllowCar;
    public static String getPrefix;
    public static String getPickupCar;
    public static String getPlayerInCar;
    public static String getOfflineCarOwner;
    public static String getNotCar;

    public static void init() {
        FileConfiguration config = QAV2_Mekanick.getInstance().getConfig();

        getSupportVersion = config.getString("ServerVersion");
        getCommandName = Objects.requireNonNull(config.getString("Command")).replace("/", "");
        isEventCancel = config.getBoolean("EventCancelIfPlayerInCar");
        getUserCommand = config.getString("UserCommand");
        getGiveCommand = config.getString("GiveCommand");
        getAllowCar = config.getString("Car");
        getPrefix = applyColor(config.getString("Language.Prefix"));
        getPlayerInCar = applyColor(config.getString("Language.PlayerInCar"));
        getPickupCar = applyColor(config.getString("Language.PickupedCar"));
        getOfflineCarOwner = applyColor(config.getString("Language.OfflineCarOwner"));
        getNotCar = applyColor(config.getString("Language.NotCar"));

        getPlayerInCar = getPrefix + getPlayerInCar;
        getPickupCar = getPrefix + getPickupCar;
        getOfflineCarOwner = getPrefix + getOfflineCarOwner;
        getNotCar = getPrefix + getNotCar;
    }

    public static String applyColor(String str) {
        return ChatColor.translateAlternateColorCodes('&', str);
    }
}
