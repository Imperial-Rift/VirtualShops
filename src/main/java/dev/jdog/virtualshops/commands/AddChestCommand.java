package dev.jdog.virtualshops.commands;

import dev.jdog.virtualshops.models.Shop;
import dev.jdog.virtualshops.utils.ShopStorage;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabExecutor;
import org.bukkit.entity.Player;
import sun.tools.jconsole.Tab;

import java.util.ArrayList;
import java.util.List;

public class AddChestCommand implements TabExecutor {
    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        Player p = (Player) sender;
        Block block = p.getTargetBlock(null, 5);
        if (block.getType().equals(Material.CHEST)) {
            Location location = block.getLocation();
            System.out.println(new ShopStorage().addShopChest(location, args[0]));
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
