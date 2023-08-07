package at.spawn.commands;

import java.io.IOException;
import java.util.HashMap;
import java.util.Objects;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.player.PlayerMoveEvent;
import org.bukkit.plugin.Plugin;
import org.jetbrains.annotations.NotNull;

import static at.spawn.commands.BackCommand.Locs;
import static at.spawn.commands.BackCommand.Locscfg;

public class SpawnCommand implements CommandExecutor {
    private final Plugin plugin;

    private final HashMap<String, Long> cooldowns = new HashMap<>();

    @EventHandler
    private void Move(PlayerMoveEvent e) {
        var cancelMove = true;
    }

    public SpawnCommand(Plugin plugin) {
        this.plugin = plugin;
    }

    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command command, @NotNull String label, String[] args) {
        //Check IF Player
        if (!(sender instanceof Player)) {
            Bukkit.getConsoleSender().sendMessage(ChatColor.translateAlternateColorCodes('&', Objects.<String>requireNonNull(this.plugin.getConfig().getString("must-player", "§8[§eSpawn§8] §cYou must be a player!"))));
            return true;
        }
        //Check Permission
        Player player = (Player) sender;
        if (!player.hasPermission("spawnplugin.spawn")) {
            player.sendMessage(ChatColor.translateAlternateColorCodes('&', Objects.<String>requireNonNull(this.plugin.getConfig().getString("no-permission", "§8[§eSpawn§8] §cYou don't have permission to use this command!"))));
            return true;
        }
        //Cooldown
        int cooldownTime = this.plugin.getConfig().getInt("cooldown-time", 60);
        if (this.cooldowns.containsKey(player.getName())) {
            long secondsLeft = ((Long) this.cooldowns.get(player.getName())).longValue() / 1000L + cooldownTime - System.currentTimeMillis() / 1000L;
            if (secondsLeft > 0L) {
                player.sendMessage(ChatColor.translateAlternateColorCodes('&', Objects.<String>requireNonNull(this.plugin.getConfig().getString("cooldown-message", "§8[§eSpawn§8] §cSorry, you have to wait until the command has power again!"))));
                return true;
            }
        }


        this.cooldowns.put(player.getName(), Long.valueOf(System.currentTimeMillis()));
        int countdownTime = this.plugin.getConfig().getInt("countdown-time", 5);
        player.sendMessage(ChatColor.translateAlternateColorCodes('&', Objects.<String>requireNonNull(this.plugin.getConfig().getString("spawn-countdown", "§8[§eSpawn§8] §eTeleporting in..."))));
        for (int i = countdownTime; i > 0; i--) {
            player.sendMessage(ChatColor.translateAlternateColorCodes('&', Objects.<String>requireNonNull(this.plugin.getConfig().getString("cooldown-color", "§e") + i)));

            if (player.isSprinting() | player.isFlying() | player.isSneaking() | player.isGliding() | player.isJumping()) {
                player.sendMessage(ChatColor.translateAlternateColorCodes('&', Objects.<String>requireNonNull(this.plugin.getConfig().getString("player-moves", "§8[§eSpawn§8] §cAction canceled, you moved!"))));
                return true;
            }

            boolean cancelMove;

            cancelMove = false;

            if (player.isSprinting() | player.isFlying() | player.isSneaking() | player.isGliding() | player.isJumping()) {
                cancelMove = true;
            }

            if (cancelMove == true) {
                player.sendMessage(ChatColor.translateAlternateColorCodes('&', Objects.<String>requireNonNull(this.plugin.getConfig().getString("player-moves", "§8[§eSpawn§8] §cAction canceled, you moved!"))));
                return true;
            }

            cancelMove = false;

            try {
                Thread.sleep(1000L);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

        }

        //Location location = this.plugin.getConfig().getLocation("spawn");
        Location location = Locscfg.getLocation("spawnpoint");
        if (location == null) {
            player.sendMessage(ChatColor.translateAlternateColorCodes('&', Objects.<String>requireNonNull(this.plugin.getConfig().getString("no-spawnpoint", "§8[§eSpawn§8] §cNo spawnpoint has been set yet!"))));
            return true;
        }

        //Back Data 2
        Locscfg.set("x_" + player.getUniqueId(), player.getLocation().getX());
        Locscfg.set("y_" + player.getUniqueId(), player.getLocation().getY());
        Locscfg.set("z_" + player.getUniqueId(), player.getLocation().getZ());
        Locscfg.set("yaw_" + player.getUniqueId(), player.getLocation().getYaw());
        Locscfg.set("pitch_" + player.getUniqueId(), player.getLocation().getPitch());
        Locscfg.set("world_" + player.getUniqueId(), player.getLocation().getWorld().getName());
        try {
            Locscfg.save(Locs);
        } catch (IOException e) {
            e.printStackTrace();
        }

        player.teleport(location);
        player.sendMessage(ChatColor.translateAlternateColorCodes('&', Objects.<String>requireNonNull(this.plugin.getConfig().getString("spawn-arrival", "§8[§eSpawn§8] §eWelcome to the Spawn!"))));

        return false;
    }

}