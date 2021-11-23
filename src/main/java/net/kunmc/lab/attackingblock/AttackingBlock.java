package net.kunmc.lab.attackingblock;

import net.kunmc.lab.attackingblock.event.PlayerEvent;
import net.kunmc.lab.attackingblock.event.DigEvent;
import org.bukkit.plugin.java.JavaPlugin;

public final class AttackingBlock extends JavaPlugin {

    public static AttackingBlock plugin;
    @Override
    public void onEnable() {
        // Plugin startup logic
        plugin = this;

        // Event
        getServer().getPluginManager().registerEvents(new PlayerEvent(), this);
        DigEvent.addPacketListener();
    }

    @Override
    public void onDisable() {
        // Plugin shutdown logic
    }
}
