package at.spawn;

import at.spawn.commands.BackCommand;
import at.spawn.commands.SetSpawnCommand;
import at.spawn.commands.SpawnCommand;
import at.spawn.listener.JoinLeaveListener;
import at.spawn.utils.Metrics;
import at.spawn.utils.UpdateChecker;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.event.Listener;
import org.bukkit.plugin.Plugin;
import org.bukkit.plugin.java.JavaPlugin;

import java.io.File;

public final class spawn extends JavaPlugin {
    private FileConfiguration config;

    @Override
    public void onEnable() {

        //Plugin load - Console
        Bukkit.getConsoleSender().sendMessage(ChatColor.GREEN + "Spawn Plugin loaded!");
        System.out.println();
        System.out.println("   _____                              _____  _             _       ");
        System.out.println("  / ____|                            |  __ \\| |           (_)      ");
        System.out.println(" | (___  _ __   __ ___      ___ __   | |__) | |_   _  __ _ _ _ __  ");
        System.out.println("  \\___ \\| '_ \\ / _` \\ \\ /\\ / / '_ \\  |  ___/| | | | |/ _` | | '_ \\ ");
        System.out.println("  ____) | |_) | (_| |\\ V  V /| | | | | |    | | |_| | (_| | | | | |");
        System.out.println(" |_____/| .__/ \\__,_| \\_/\\_/ |_| |_| |_|    |_|\\__,_|\\__, |_|_| |_|");
        System.out.println("        | |                                           __/ |        ");
        System.out.println("        |_|                                          |___/         ");
        System.out.println();
        System.out.println("by FrauJulian");
        System.out.println();

        //Metrics (bStats)
        int pluginId = 19370;
        Metrics metrics = new Metrics(this, pluginId);

        //Update Checker
        new UpdateChecker(this, 111820).getLatestVersion(version -> {
            if(this.getDescription().getVersion().equalsIgnoreCase(version)) {
                Bukkit.getConsoleSender().sendMessage(ChatColor.GREEN + "Spawn plugin is up to date!");
            } else {
                System.out.println();
                System.out.println();
                System.out.println();
                Bukkit.getConsoleSender().sendMessage(ChatColor.RED + "Spawn plugin has an update!");
                System.out.println();
                System.out.println();
                System.out.println();
            }
        });

        //Register Commands
        getCommand("setspawn").setExecutor(new SetSpawnCommand(this));
        getCommand("spawn").setExecutor(new SpawnCommand(this));
        getCommand("back").setExecutor(new BackCommand(this));

        //Register Listeners
        getServer().getPluginManager().registerEvents((Listener) new JoinLeaveListener(this), (Plugin)this);

        //Config Arguments
        loadConfig();
        registerListener();
        registerCommand();
        loadMessages();

    }

    //Command Register for config.yml / Unused - Future Update
    private void registerCommand() {
    }

    //Listener Register for config.yml / Unused - Future Update
    private void registerListener() {
    }

    //Message Loader for config.yml / Unused - Future Update
    private void loadMessages() {
    }

    //Create Config
    private void loadConfig() {

        //Create Folder
        if (!getDataFolder().exists()) {
            getDataFolder().mkdir();
        }

        //Check Config File
        File configFile = new File(getDataFolder(), "config.yml");
        if (!configFile.exists()) {
            saveResource("config.yml", false);
        }

        //Load Config and Location File
        config = YamlConfiguration.loadConfiguration(configFile);
    }

    //onDisable Event
    @Override
    public void onDisable() {

        //Plugin unload - Console
        Bukkit.getConsoleSender().sendMessage(ChatColor.RED + "Spawn Plugin unloaded!");
        System.out.println();
        System.out.println("   _____                              _____  _             _       ");
        System.out.println("  / ____|                            |  __ \\| |           (_)      ");
        System.out.println(" | (___  _ __   __ ___      ___ __   | |__) | |_   _  __ _ _ _ __  ");
        System.out.println("  \\___ \\| '_ \\ / _` \\ \\ /\\ / / '_ \\  |  ___/| | | | |/ _` | | '_ \\ ");
        System.out.println("  ____) | |_) | (_| |\\ V  V /| | | | | |    | | |_| | (_| | | | | |");
        System.out.println(" |_____/| .__/ \\__,_| \\_/\\_/ |_| |_| |_|    |_|\\__,_|\\__, |_|_| |_|");
        System.out.println("        | |                                           __/ |        ");
        System.out.println("        |_|                                          |___/         ");
        System.out.println();
        System.out.println("by FrauJulian");
        System.out.println();
    }
}
