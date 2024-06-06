package ir.detiven.qav2mekanick.commands;

import ir.detiven.qav2mekanick.Config;
import me.zombie_striker.qav.Main;
import me.zombie_striker.qav.VehicleEntity;
import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.command.ConsoleCommandSender;
import org.bukkit.entity.Player;

import java.util.List;

public class PickupVehiclesCommand implements CommandExecutor {
    private final List<VehicleEntity> list = Main.vehicles;

    @Override
    public boolean onCommand(CommandSender sender, Command command, String s, String[] strings) {
        if (!(sender instanceof Player)) {
            return RESULT.FAILED;
        }
        return hasPlayer((Player) sender);
    }

    private boolean hasPlayer(Player player) {
        boolean isFind = false;
        VehicleEntity car = null;

        for (VehicleEntity var : list) {
            double range = player.getLocation().distance(var.getCenter());
            if (range > 3.0D) continue;

            if (var.getType().getName().equalsIgnoreCase(Config.getAllowCar)) {
                if (!var.isInvalid())
                    if (Config.isEventCancel) {
                        player.sendMessage(Config.getPlayerInCar);
                        continue;
                    }

                isFind = true;
                car = var;
            }
        }

        if (isFind) {
            Player owner = Bukkit.getPlayer(car.getOwner());
            ConsoleCommandSender sender = Bukkit.getConsoleSender();

            if (owner == null){
                player.sendMessage(Config.getOfflineCarOwner);
                return RESULT.FAILED;
            } else {
                if (!Config.getGiveCommand.contains("nullable")) {
                    if (Config.getGiveCommand.contains("player:")){
                        owner.performCommand(Config.getGiveCommand.replace("player:", ""));
                    } else if (Config.getGiveCommand.contains("console:")) {
                        Bukkit.dispatchCommand(sender, Config.getGiveCommand.replace("console:", "").replace("%player%", owner.getName()));
                    } else {
                        new IllegalArgumentException("az action dorost estefade konid! config.yml(givecommand)");
                        return RESULT.FAILED;
                    }
                }
                if (!Config.getGiveCommand.contains("nullable")) {
                    if (Config.getUserCommand.contains("player:")){
                        owner.performCommand(Config.getUserCommand.replace("player:", ""));
                    } else if (Config.getUserCommand.contains("console:")) {
                        Bukkit.dispatchCommand(sender, Config.getUserCommand.replace("console:", "").replace("%player%", owner.getName()));
                    } else {
                        new IllegalArgumentException("az action dorost estefade konid! config.yml(usercommand)");
                        return RESULT.FAILED;
                    }
                }
            }
            return RESULT.SUCCESS;
        } else {
            player.sendMessage(Config.getNotCar);
        }
        return RESULT.FAILED;
    }

    private static class RESULT {
        private static final boolean SUCCESS = true;
        private static final boolean FAILED = false;
    }
}