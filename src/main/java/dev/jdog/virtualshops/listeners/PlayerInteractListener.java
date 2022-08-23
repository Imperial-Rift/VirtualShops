package dev.jdog.virtualshops.listeners;

import dev.jdog.virtualshops.VirtualShops;
import dev.jdog.virtualshops.models.Shop;
import dev.jdog.virtualshops.utils.ShopStorage;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.NamespacedKey;
import org.bukkit.block.BlockState;
import org.bukkit.block.TileState;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.persistence.PersistentDataContainer;
import org.bukkit.persistence.PersistentDataType;

public class PlayerInteractListener implements Listener {
    @EventHandler
    public void onPlayerInteract(PlayerInteractEvent e) {
        if (e.getClickedBlock() != null) {
            if (e.getClickedBlock().getType() == Material.CHEST) {
                BlockState blockState = e.getClickedBlock().getState();

                if (blockState instanceof TileState) {
                    TileState tileState = (TileState) blockState;

                    PersistentDataContainer container = tileState.getPersistentDataContainer();


                    if (container.has(new NamespacedKey(VirtualShops.getPlugin(), "shop"), PersistentDataType.STRING)) {
                        String id = container.get(new NamespacedKey(VirtualShops.getPlugin(), "shop"), PersistentDataType.STRING);
                        Shop shop = new ShopStorage().findShopById(id);

                        if (!shop.getOwner().equals(e.getPlayer().getName())) {
                            e.setCancelled(true);
                            e.getPlayer().sendMessage(ChatColor.DARK_RED + "You aren't allowed to do that!");
                        }
                    }

                }

            }
        }
    }
}
