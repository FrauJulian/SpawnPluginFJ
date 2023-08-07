package at.spawn.commands;

import java.io.IOException;
import java.util.Objects;

import at.spawn.spawn;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

import static at.spawn.commands.BackCommand.Locs;
import static at.spawn.commands.BackCommand.Locscfg;

public class SetSpawnCommand implements CommandExecutor {
    private final spawn plugin;

    public SetSpawnCommand(spawn plugin) {
        this.plugin = plugin;
    }

    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command command, @NotNull String label, @NotNull String[] args) {
        if (sender instanceof Player) {
            Player player = (Player)sender;

            //Check Permission
            if (player.hasPermission("spawnplugin.setspawn")) {
                Location location = player.getLocation();
                Locscfg.set("spawnpoint", location);
                //this.plugin.getConfig().set("spawn", location);
                try {
                    Locscfg.set("spawnpoint", location);
                    //this.plugin.saveConfig();
                } catch (Exception e) {
                    e.printStackTrace();
                }
                player.sendMessage(ChatColor.translateAlternateColorCodes('&', Objects.<String>requireNonNull(this.plugin.getConfig().getString("spawn-set", "§8[§eSpawn§§8] §eYou have set the spawnpoint!"))));
            } else {
                player.sendMessage(ChatColor.translateAlternateColorCodes('&', Objects.<String>requireNonNull(this.plugin.getConfig().getString("no-permission", "§8[§eSpawn§8] §cYou don't have permission to use this command!"))));
            }
        } else {
            Bukkit.getConsoleSender().sendMessage(ChatColor.translateAlternateColorCodes('&', Objects.<String>requireNonNull(this.plugin.getConfig().getString("must-player", "§8[§eSpawn§8] §cYou must be a player!"))));
        }
        return true;
    }
}
