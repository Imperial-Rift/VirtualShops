package dev.jdog.virtualshops.commands;

import dev.jdog.virtualshops.VirtualShops;
import dev.jdog.virtualshops.models.Shop;
import dev.jdog.virtualshops.utils.ShopStorage;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.NamespacedKey;
import org.bukkit.block.Block;
import org.bukkit.block.BlockState;
import org.bukkit.block.TileState;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.persistence.PersistentDataContainer;
import org.bukkit.persistence.PersistentDataType;

public class DeleteShopCommand implements CommandExecutor {

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        Player p = (Player) sender;
        Block block = p.getTargetBlock(null, 7);

        if (block.getType().equals(Material.CHEST)) {
            BlockState blockState = block.getState();

            if (blockState instanceof TileState) {
                TileState tileState = (TileState) blockState;

                PersistentDataContainer container = tileState.getPersistentDataContainer();

                if (container.has(new NamespacedKey(VirtualShops.getPlugin(), "shop"), PersistentDataType.STRING)) {
                    String id = container.get(new NamespacedKey(VirtualShops.getPlugin(), "shop"), PersistentDataType.STRING);
//                    Remove shop
                    container.remove(new NamespacedKey(VirtualShops.getPlugin(), "shop"));
                    tileState.update();

                    new ShopStorage().deleteShop(id);
                    p.sendMessage(ChatColor.GOLD + "Shop deleted.");

                } else {
                    p.sendMessage(ChatColor.RED + "That is not a shop!");
                }
            }
        }

        return true;
    }
}
