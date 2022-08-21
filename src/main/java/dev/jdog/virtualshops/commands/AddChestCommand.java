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
import org.bukkit.command.TabExecutor;
import org.bukkit.entity.Player;
import org.bukkit.persistence.PersistentDataContainer;
import org.bukkit.persistence.PersistentDataType;
import sun.tools.jconsole.Tab;

import java.util.ArrayList;
import java.util.List;

public class AddChestCommand implements TabExecutor {
    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        Player p = (Player) sender;
        Block block = p.getTargetBlock(null, 5);


        if (block.getType().equals(Material.CHEST)) {
            BlockState blockState = block.getState();

            if (blockState instanceof TileState) {
                TileState tileState = (TileState) blockState;

                PersistentDataContainer container = tileState.getPersistentDataContainer();

                if (container.has(new NamespacedKey(VirtualShops.getPlugin(), "shop"), PersistentDataType.STRING)) {
                    p.sendMessage(ChatColor.DARK_RED + "This chest is already assigned to a shop!");
                } else {

                    container.set(new NamespacedKey(VirtualShops.getPlugin(), "shop"), PersistentDataType.STRING, args[0]);
                    tileState.update();

                    Location location = block.getLocation();
                    System.out.println(new ShopStorage().addShopChest(location, args[0]));
                    p.sendMessage(ChatColor.GOLD + "Shop chest added.");
                }
            }
        }
        return true;
    }

    @Override
    public List<String> onTabComplete(CommandSender sender, Command command, String label, String[] args) {
        if (args.length == 1) {
            ArrayList<Shop> shops = new ShopStorage().getShopsByPlayer(sender.getName());

            ArrayList<String> ids = new ArrayList<>();

            for (Shop shop: shops) {
                ids.add(shop.getId());
            }

            return ids;
        }
        return null;
    }
}
