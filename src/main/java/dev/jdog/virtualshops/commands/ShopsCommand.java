package dev.jdog.virtualshops.commands;

import dev.jdog.virtualshops.VirtualShops;
import dev.jdog.virtualshops.menuManager.menus.ShopsMenu;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class ShopsCommand implements CommandExecutor {
    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        Player p = (Player) sender;
        new ShopsMenu(VirtualShops.getPlayerUtiliity(p)).open();
        return true;
    }
}
