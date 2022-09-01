package dev.jdog.virtualshops.listeners;

import dev.jdog.virtualshops.VirtualShops;
import org.bukkit.Material;
import org.bukkit.NamespacedKey;
import org.bukkit.block.BlockState;
import org.bukkit.block.TileState;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.persistence.PersistentDataContainer;
import org.bukkit.persistence.PersistentDataType;

public class BreakBlockListener implements Listener {
    @EventHandler
    public void onBreakBlock(BlockBreakEvent e) {
        BlockState blockState = e.getBlock().getState();

        if (blockState instanceof TileState) {
            TileState tileState = (TileState) blockState;

            PersistentDataContainer container = tileState.getPersistentDataContainer();

//                if (container.has(new NamespacedKey(VirtualShops.getPlugin(), "shop"), PersistentDataType.STRING)) {
//
//                } else {

//            container.set(new NamespacedKey(VirtualShops.getPlugin(), "shop"), PersistentDataType.STRING, "hi");

//                }

//            tileState.update();



            if (container.has(new NamespacedKey(VirtualShops.getPlugin(), "shop"), PersistentDataType.STRING)) {
//                Player p = (Player) e.getPlayer();
//                System.out.println(p.);
                e.setCancelled(true);
            }

        }

    }
}
