package dev.jdog.virtualshops.listeners;

import dev.jdog.virtualshops.VirtualShops;
import org.bukkit.NamespacedKey;
import org.bukkit.block.Block;
import org.bukkit.block.BlockState;
import org.bukkit.block.TileState;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityExplodeEvent;
import org.bukkit.persistence.PersistentDataContainer;
import org.bukkit.persistence.PersistentDataType;

import java.util.ArrayList;

public class EntityExplodeListener implements Listener {
    @EventHandler
    public void onEntityExplode(EntityExplodeEvent e) {
        for (Block block: e.blockList()) {
            BlockState blockState = block.getState();

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
                    e.setCancelled(true);
                }

            }

        }
    }
}
