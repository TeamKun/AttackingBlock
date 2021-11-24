package net.kunmc.lab.attackingblock;

import dev.kotx.flylib.FlyLib;
import net.kunmc.lab.attackingblock.command.Main;
import net.kunmc.lab.attackingblock.event.DigEvent;
import net.kunmc.lab.attackingblock.event.PlayerEvent;
import net.kunmc.lab.configlib.command.ConfigCommand;
import net.kunmc.lab.configlib.command.ConfigCommandBuilder;
import org.bukkit.plugin.java.JavaPlugin;

public final class AttackingBlock extends JavaPlugin {

    public static AttackingBlock plugin;
    public static Config config;
    public static boolean isRunning;

    @Override
    public void onEnable() {
        // Plugin startup logic
        plugin = this;

        // command
        config = new Config(this);
        config.saveConfig();
        config.loadConfig();

        ConfigCommand configCommand = new ConfigCommandBuilder(config).build();

        FlyLib.create(this, builder -> {
            builder.command(new Main("attackingblock", configCommand));
        });

        // Event
        getServer().getPluginManager().registerEvents(new PlayerEvent(), this);
        DigEvent.addPacketListener();
    }

    @Override
    public void onDisable() {
        // Plugin shutdown logic
    }
}
