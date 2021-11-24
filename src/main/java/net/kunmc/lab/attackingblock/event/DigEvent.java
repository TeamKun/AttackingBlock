package net.kunmc.lab.attackingblock.event;

import com.comphenix.protocol.PacketType;
import com.comphenix.protocol.ProtocolLibrary;
import com.comphenix.protocol.events.PacketAdapter;
import com.comphenix.protocol.events.PacketEvent;
import net.kunmc.lab.attackingblock.AttackingBlock;
import net.kunmc.lab.attackingblock.blockmob.BlockMob;
import net.kunmc.lab.attackingblock.blockmob.BlockMobList;
import net.kunmc.lab.attackingblock.util.RandomCalculator;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.metadata.FixedMetadataValue;

public class DigEvent {

    public static void addPacketListener() {
        ProtocolLibrary.getProtocolManager().addPacketListener(new PacketAdapter(AttackingBlock.plugin, PacketType.Play.Client.BLOCK_DIG) {
            @Override
            public void onPacketReceiving(PacketEvent event) {
                try {
                    if (!AttackingBlock.isRunning) {
                        return;
                    }

                    String digName = event.getPacket().getPlayerDigTypes().getValues().get(0).name();
                    if (!digName.equals("START_DESTROY_BLOCK")) {
                        return;
                    }

                    Block targetBlock = event.getPlayer().rayTraceBlocks(4).getHitBlock();

                    if (targetBlock.getType() == Material.AIR) {
                        return;
                    }
                    // すでにメタデータが入っているか確認
                    if (targetBlock.hasMetadata(Const.META_DATA_KEY)) {
                        return;
                    }

                    targetBlock.setMetadata(Const.META_DATA_KEY, new FixedMetadataValue(AttackingBlock.plugin, null));

                    // 抽選
                    if (!RandomCalculator.lottery(AttackingBlock.config.encounterProbability.value())) {
                        return;
                    }

                    // エンティティを生み出す
                    targetBlock.removeMetadata(Const.META_DATA_KEY, AttackingBlock.plugin);
                    BlockMob blockMob = new BlockMob(targetBlock, event.getPlayer());
                    BlockMobList.add(blockMob);

                } catch (Exception ignored) {

                }
            }
        });
    }
}
