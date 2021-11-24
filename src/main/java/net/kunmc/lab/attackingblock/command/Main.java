package net.kunmc.lab.attackingblock.command;

import dev.kotx.flylib.command.Command;
import net.kunmc.lab.configlib.command.ConfigCommand;
import org.jetbrains.annotations.NotNull;

public class Main extends Command {
    public Main(@NotNull String name, ConfigCommand configCommand) {
        super(name);
        children(configCommand, new Start(), new Stop());
    }
}
