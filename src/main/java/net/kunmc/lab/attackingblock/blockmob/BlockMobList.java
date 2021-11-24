package net.kunmc.lab.attackingblock.blockmob;

import org.bukkit.entity.Player;

import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

public class BlockMobList {
    private static List<BlockMob> list = new CopyOnWriteArrayList<>();

    public static void add(BlockMob blockMob) {
        list.add(blockMob);
    }

    public static void clearMob(Player player) {
        for (BlockMob blockMob : list) {
            if (blockMob.isTargetMatch(player)) {
                blockMob.kill();
                list.remove(blockMob);
            }
        }
    }

    public static void clearMob() {
        for (BlockMob blockMob : list) {
            blockMob.kill();
        }
    }

}
