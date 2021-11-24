package net.kunmc.lab.attackingblock;

import net.kunmc.lab.configlib.config.BaseConfig;
import net.kunmc.lab.configlib.value.DoubleValue;
import net.kunmc.lab.configlib.value.IntegerValue;
import org.bukkit.plugin.Plugin;
import org.jetbrains.annotations.NotNull;

public class Config extends BaseConfig {
    public DoubleValue encounterProbability = new DoubleValue(10.0, 0.0, 100.0);
    public DoubleValue moveSpeed = new DoubleValue(0.15);
    public DoubleValue damage = new DoubleValue(6.0);
    public IntegerValue hitPoint = new IntegerValue(10);

    public Config(@NotNull Plugin plugin) {
        super(plugin, "config");
    }
}
