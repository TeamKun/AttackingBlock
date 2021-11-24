package net.kunmc.lab.attackingblock.command;

import dev.kotx.flylib.command.Command;
import dev.kotx.flylib.command.CommandContext;
import net.kunmc.lab.attackingblock.AttackingBlock;

public class Start extends Command {
    public Start() {
        super("start");
    }

    @Override
    public void execute(CommandContext ctx) {
        if (!AttackingBlock.isRunning) {
            AttackingBlock.isRunning = true;
            ctx.success("起動しました");
        } else {
            ctx.fail("すでに起動してます");
        }
    }
}
