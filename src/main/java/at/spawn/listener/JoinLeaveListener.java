package at.spawn.listener;

import at.spawn.spawn;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerQuitEvent;

import java.io.IOException;
import java.util.Objects;

import static at.spawn.commands.BackCommand.Locs;
import static at.spawn.commands.BackCommand.Locscfg;

public class JoinLeaveListener implements Listener {
    private final spawn plugin;

    public JoinLeaveListener(spawn plugin) {
        this.plugin = plugin;
    }

    //Join Message
    @EventHandler
    public void onPlayerJoin(PlayerJoinEvent event) {
        String joinMessage = ChatColor.translateAlternateColorCodes('&', this.plugin.getConfig().getString("join-message", "§8[§eSpawn§8] §aWelcome %player%!"));
        event.setJoinMessage(joinMessage.replace("%player%", event.getPlayer().getDisplayName()));

        //Join = Teleport to Spawn
        Location location = Locscfg.getLocation("spawnpoint");
        Player player = event.getPlayer();
        if (location != null) {
            player.teleport(location);
        } else {
            player.sendMessage(ChatColor.translateAlternateColorCodes('&', Objects.<String>requireNonNull(this.plugin.getConfig().getString("no-spawnpoint"))));
        }

        //Back Data 1
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
    }

    //Leave Message
    @EventHandler
    public void onPlayerQuit(PlayerQuitEvent event) {
        String quitMessage = ChatColor.translateAlternateColorCodes('&', this.plugin.getConfig().getString("leave-message", "§8[§eSpawn§8] §cBye %player%!"));
        event.setQuitMessage(quitMessage.replace("%player%", event.getPlayer().getDisplayName()));
    }
}
