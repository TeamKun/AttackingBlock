package net.kunmc.lab.attackingblock.command;

import dev.kotx.flylib.command.Command;
import dev.kotx.flylib.command.CommandContext;
import net.kunmc.lab.attackingblock.AttackingBlock;
import net.kunmc.lab.attackingblock.blockmob.BlockMobList;

public class Stop extends Command {
    public Stop() {
        super("stop");
    }

    @Override
    public void execute(CommandContext ctx) {
        if (AttackingBlock.isRunning) {
            AttackingBlock.isRunning = false;
            BlockMobList.clearMob();
            ctx.success("停止しました");
        } else {
            ctx.fail("起動していません");
        }
    }
}
