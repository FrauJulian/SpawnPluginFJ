package at.spawn.commands;

import at.spawn.spawn;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.plugin.Plugin;

import java.io.File;
import java.io.IOException;
import java.util.Objects;

public class BackCommand implements CommandExecutor {
    public static File Locs = new File("plugins/SpawnPluginFJ", "data.yml");
    public static YamlConfiguration Locscfg = YamlConfiguration.loadConfiguration(Locs);

    private final spawn plugin;

    public BackCommand(Plugin plugin) {
        this.plugin = (spawn) plugin;
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        Player player = (Player)sender;

        if(player.hasPermission("spawnplugin.back")) {
            if(!Locs.exists()){
                try {
                    Locs.createNewFile();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            if (args.length == 0) {
                Location location = player.getLocation();
                location.setX(Locscfg.getDouble("x_" + player.getUniqueId()));
                location.setY(Locscfg.getDouble("y_" + player.getUniqueId()));
                location.setZ(Locscfg.getDouble("z_" + player.getUniqueId()));
                location.setYaw(Locscfg.getLong("yaw_" + player.getUniqueId()));
                location.setPitch(Locscfg.getLong("pitch_" + player.getUniqueId()));
                location.setWorld(Bukkit.getWorld(Locscfg.getString("world_" + player.getUniqueId())));
                player.teleport(location);
                player.sendMessage(ChatColor.translateAlternateColorCodes('&', Objects.<String>requireNonNull(this.plugin.getConfig().getString("back-arrival", "&8[&eSpawn&8] &eYou have been teleported!"))));
            } else {
                player.sendMessage(ChatColor.translateAlternateColorCodes('&', Objects.<String>requireNonNull(this.plugin.getConfig().getString("no-back-data", "&8[&eSpawn&8] &cNo data found for this command!"))));
            }
        } else {
            player.sendMessage(ChatColor.translateAlternateColorCodes('&', Objects.<String>requireNonNull(this.plugin.getConfig().getString("no-permission", "§8[§eSpawn§8] §cYou don't have permission to use this command!"))));
        }
        return false;
    }
}
