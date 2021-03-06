package net.kunmc.lab.attackingblock.blockmob;

import net.kunmc.lab.attackingblock.AttackingBlock;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.Particle;
import org.bukkit.block.Block;
import org.bukkit.block.data.BlockData;
import org.bukkit.craftbukkit.v1_16_R3.entity.CraftArmorStand;
import org.bukkit.entity.Entity;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.FallingBlock;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.scheduler.BukkitRunnable;
import org.bukkit.util.BoundingBox;
import org.bukkit.util.Vector;

import java.util.List;

public class BlockMob extends BukkitRunnable implements Listener {
    FallingBlock fallingBlock;
    Block block;
    Entity armorStand;
    Player target;
    int hitPoint;
    int invincibleCount;

    public BlockMob(Block block, Player target) {
        this.block = block;
        this.target = target;
        BlockData blockData = block.getBlockData();
        this.hitPoint = AttackingBlock.config.hitPoint.value();
        this.invincibleCount = 0;

        new BukkitRunnable() {
            public void run() {
                block.setType(Material.AIR);
                fallingBlock = block.getWorld().spawnFallingBlock(block.getLocation().add(0.5, 0, 0.5), blockData);
                fallingBlock.setGravity(false);

                armorStand = block.getWorld().spawnEntity(block.getLocation().add(0.5, 0, 0.5), EntityType.ARMOR_STAND);
                armorStand.setGravity(false);
                armorStand.addPassenger(fallingBlock);
                CraftArmorStand craftArmorStand = (CraftArmorStand) armorStand;
                craftArmorStand.setMarker(true);
                craftArmorStand.setInvisible(true);
            }
        }.runTask(AttackingBlock.plugin);

        Bukkit.getServer().getPluginManager().registerEvents(this, AttackingBlock.plugin);
        this.runTaskTimer(AttackingBlock.plugin, 0, 0);
    }

    public boolean isTargetMatch(Player player) {
        return player.getUniqueId().equals(target.getUniqueId());
    }

    public void kill() {
        fallingBlock.setTicksLived(1000);
        armorStand.remove();
        this.cancel();
    }

    @Override
    public void run() {
        this.fallingBlock.setTicksLived(1);

        // ??????????????????????????????
        if (this.invincibleCount > 0) {
            invincibleCount--;
        }

        // ???????????????
        List<Entity> entityList = this.fallingBlock.getNearbyEntities(1, 1, 1);
        BoundingBox blockBoundingBox = this.fallingBlock.getBoundingBox();
        for (Entity entity : entityList) {
            if (entity.getType() != EntityType.PLAYER) {
                continue;
            }

            Player player = (Player) entity;
            BoundingBox playerBoundingBox = player.getBoundingBox();

            if (!blockBoundingBox.overlaps(playerBoundingBox)) {
                continue;
            }

            if (player.equals(this.target)) {
                player.damage(AttackingBlock.config.damage.value());
            }
        }

        // ????????????
        /** ????????????????????? */
        Vector blockMobPoint = this.armorStand.getLocation().toVector();
        /** ???????????????????????? */
        Vector targetPoint = this.target.getEyeLocation().toVector();

        /** ??????????????????????????? */
        Vector differenceVector = targetPoint.subtract(blockMobPoint).normalize();

        teleport(this.armorStand, blockMobPoint.add(differenceVector.multiply(AttackingBlock.config.moveSpeed.value())).toLocation(armorStand.getWorld()));
    }

    private void teleport(Entity armorStand, Location to) {
        try {
            ((CraftArmorStand) armorStand).getHandle().teleportAndSync(to.getX(), to.getY(), to.getZ());
        } catch (Exception ignored) {
        }
    }

    @EventHandler()
    public void onPlayerInteractEntity(PlayerInteractEvent event) {
        if (this.armorStand == null) {
            return;
        }

        BoundingBox boundingBox = this.fallingBlock.getBoundingBox();
        Player player = event.getPlayer();


        if (event.getAction() != Action.LEFT_CLICK_AIR) {
            return;
        }


        Vector direction = player.getLocation().getDirection();
        if (!boundingBox.overlaps(player.getEyeLocation().toVector(), player.getEyeLocation().add(direction.multiply(3)).toVector())) {
            return;
        }

        if (this.invincibleCount > 0) {
            return;
        }

        this.hitPoint--;
        this.invincibleCount = 10;
        this.target = player;
        armorStand.getWorld().spawnParticle(Particle.DAMAGE_INDICATOR, armorStand.getLocation(), 4);
        armorStand.getWorld().playSound(armorStand.getLocation(),
                this.block.getSoundGroup().getBreakSound(),
                2,
                0);
        // HP????????????
        if (this.hitPoint <= 0) {
            this.kill();
            return;
        }
    }
}
