package dev.jdog.virtualshops;

import dev.jdog.virtualshops.commands.AddChestCommand;
import dev.jdog.virtualshops.commands.CreateShopCommand;
import dev.jdog.virtualshops.commands.ShopsCommand;
import dev.jdog.virtualshops.listeners.MenuListener;
import dev.jdog.virtualshops.menuManager.Menu;
import dev.jdog.virtualshops.menuManager.PlayerMenuUtility;
import dev.jdog.virtualshops.utils.ShopStorage;
import net.milkbowl.vault.economy.Economy;
import org.bukkit.entity.Player;
import org.bukkit.plugin.RegisteredServiceProvider;
import org.bukkit.plugin.java.JavaPlugin;

import java.io.IOException;
import java.util.HashMap;

public final class VirtualShops extends JavaPlugin {

    private static final HashMap<Player, PlayerMenuUtility> playerMenuUtilities = new HashMap<>();

    private static VirtualShops plugin;
    private static Economy econ = null;


    @Override
    public void onEnable() {
        // Plugin startup logic
        if (!setupEconomy() ) {
            getLogger().severe("Disabled due to no Vault dependency found!");
            getServer().getPluginManager().disablePlugin(this);
            return;
        }
        plugin = this;
        System.out.println("Starting up");
        getCommand("shops").setExecutor(new ShopsCommand());
        getCommand("createShop").setExecutor(new CreateShopCommand());
        getCommand("addShopChest").setExecutor(new AddChestCommand());
        getCommand("addShopChest").setTabCompleter(new AddChestCommand());

        getServer().getPluginManager().registerEvents(new MenuListener(), this);



        try {
            new ShopStorage().loadShops();
        } catch (IOException err) {
            err.printStackTrace();
        }

    }

    private boolean setupEconomy() {
        if (getServer().getPluginManager().getPlugin("Vault") == null) {
            return false;
        }
        RegisteredServiceProvider<Economy> rsp = getServer().getServicesManager().getRegistration(Economy.class);
        if (rsp == null) {
            return false;
        }
        econ = rsp.getProvider();
        return econ != null;
    }
    public static VirtualShops getPlugin() {
        return plugin;
    }

    public static Economy getEconomy() {
        return econ;
    }
    public static PlayerMenuUtility getPlayerUtiliity(Player p) {
        PlayerMenuUtility playerMenuUtility;
        if (!(playerMenuUtilities.containsKey(p))) { //See if the player has a playermenuutility "saved" for them

            //This player doesn't. Make one for them add add it to the hashmap
            playerMenuUtility = new PlayerMenuUtility(p);
            playerMenuUtilities.put(p, playerMenuUtility);

            return playerMenuUtility;
        } else {
            return playerMenuUtilities.get(p); //Return the object by using the provided player
        }

    }
}
