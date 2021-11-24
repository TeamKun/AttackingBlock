package net.kunmc.lab.attackingblock.event;

import net.kunmc.lab.attackingblock.AttackingBlock;
import net.kunmc.lab.attackingblock.blockmob.BlockMobList;
import org.bukkit.block.Block;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.PlayerDeathEvent;

public class PlayerEvent implements Listener {
    @EventHandler(ignoreCancelled = true)
    public void onBlockBreak(org.bukkit.event.block.BlockBreakEvent event) {
        Block targetBlock = event.getBlock();

        if (!targetBlock.hasMetadata(Const.META_DATA_KEY)) {
            return;
        }

        targetBlock.removeMetadata(Const.META_DATA_KEY, AttackingBlock.plugin);
    }

    @EventHandler(ignoreCancelled = true)
    public void onPlayerDeath(PlayerDeathEvent event) {
        BlockMobList.clearMob(event.getEntity());

    }
}
