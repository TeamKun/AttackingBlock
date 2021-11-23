package net.kunmc.lab.attackingblock.blockmob;

import net.kunmc.lab.attackingblock.AttackingBlock;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.block.data.BlockData;
import org.bukkit.entity.Entity;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.FallingBlock;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;
import org.bukkit.util.Vector;

import java.util.List;

public class BlockMob extends BukkitRunnable {
    Player target;
    FallingBlock fallingBlock;

    public BlockMob(Block block, Player target) {
        this.target = target;
        BlockData blockData = block.getBlockData();

        new BukkitRunnable() {
            public void run() {
                block.setType(Material.AIR);
                fallingBlock = block.getWorld().spawnFallingBlock(block.getLocation().add(0.5, 0, 0.5), blockData);
                fallingBlock.setGravity(false);
            }
        }.runTask(AttackingBlock.plugin);

        this.runTaskTimer(AttackingBlock.plugin, 0, 0);
    }

    public boolean isTargetMatch(Player player) {
        return player.getUniqueId().equals(target.getUniqueId());
    }

    public void kill() {
        fallingBlock.setTicksLived(10000);
        this.cancel();
    }

    @Override
    public void run() {

        this.fallingBlock.setTicksLived(1);
        // 当たり判定
        List<Entity> entityList = this.fallingBlock.getNearbyEntities(1, 1, 1);
        for (Entity entity : entityList) {
            if (entity.getType() != EntityType.PLAYER) {
                return;
            }

            Player player = (Player) entity;

            if (player.equals(this.target)) {
                player.damage(10);
            }
        }

        // 追尾処理
        /** ブロックの座標 */
        Vector BlockMobPoint = this.fallingBlock.getLocation().toVector();
        /** ターゲットの座標 */
        Vector targetPoint = this.target.getEyeLocation().toVector();

        /** 差分の単位ベクトル */
        Vector differenceVector = targetPoint.subtract(BlockMobPoint).normalize();

        fallingBlock.setVelocity(differenceVector.multiply(0.15));

    }
}
