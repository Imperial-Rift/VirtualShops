package dev.jdog.virtualshops.commands;

import dev.jdog.virtualshops.VirtualShops;
import dev.jdog.virtualshops.menuManager.menus.CreateShopMenu;
import dev.jdog.virtualshops.utils.ShopStorage;
import dev.jdog.virtualshops.models.Shop;
import org.bukkit.Material;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class CreateShopCommand implements CommandExecutor {
    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        Player p = (Player) sender;
        new CreateShopMenu(VirtualShops.getPlayerUtiliity(p)).open();
        return true;
    }
}