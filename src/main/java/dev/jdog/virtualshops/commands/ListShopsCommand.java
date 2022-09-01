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

import java.util.ArrayList;

public class ListShopsCommand implements CommandExecutor {
    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        Player p = (Player) sender;
        ArrayList<Shop> shops = new ShopStorage().getShopsByPlayer(p.getName());
        p.sendMessage(ChatColor.GOLD + "-----------------------------------------");

        for (Shop shop: shops) {
            p.sendMessage(" ");
            if (shop.getChestWorld() != null) {
                p.sendMessage(ChatColor.GREEN + "ACTIVE");
            } else {
                p.sendMessage(ChatColor.RED + "NO CHEST");
            }
            p.sendMessage(shop.getAmount() + " " + shop.getItem() + " for $" + shop.getPrice());
            p.sendMessage("ID: " + shop.getId());
        }

        p.sendMessage(ChatColor.GOLD + "-----------------------------------------");

        return true;
    }
}
