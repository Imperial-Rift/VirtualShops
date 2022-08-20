package dev.jdog.virtualshops.utils;

import com.google.gson.Gson;
import dev.jdog.virtualshops.VirtualShops;
import dev.jdog.virtualshops.models.Shop;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.block.Block;
import org.bukkit.block.Chest;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;

import java.io.*;
import java.util.ArrayList;
import java.util.Arrays;

public class ShopStorage {
    private static ArrayList<Shop> shops = new ArrayList<>();

    public void createShop(Shop shop) {
        shops.add(shop);
//        System.out.println(shops);
        try {
            save();
        } catch (IOException err) {
            err.printStackTrace();
        }
    }

    public ArrayList<Shop> getShops() {
        return shops;
    }

    public ArrayList<Shop> getShopsByPlayer(String player) {
        ArrayList<Shop> playersShops = new ArrayList<>();
        for (Shop shop: shops) {
            if (shop.getOwner().equals(player)) {
                playersShops.add(shop);
            }
        }
        if (!playersShops.isEmpty()) {
            return playersShops;
        }
        return null;
    }

    public Shop findShopById(String id) {
        for (Shop shop: shops) {
            if (shop.getId().equals(id)) {
                return shop;
            }
        }
        return null;
    }

    public boolean addShopChest (Location location, String id) {
        for (Shop shop: shops) {
            if (shop.getId().equals(id)) {
                shop.setChest(location);

                try {
                    save();
                } catch (IOException err) {
                    err.printStackTrace();
                }

                return true;
            }
        }

        return false;
    }

    public boolean isInStock(String id) {
        Shop shop = findShopById(id);
        Block block = Bukkit.getServer().getWorld(shop.getChestWorld()).getBlockAt(shop.getChest());
        if (block.getState() instanceof Chest) {
            Chest chest = (Chest) block.getState();
            Inventory chestInv = chest.getInventory();
            ItemStack[] items = chestInv.getContents();

            Integer itemsNeeded = shop.getAmount();
            for (int i = 0; i < items.length; i++) {
                ItemStack is = items[i];
                if(is != null) {
                    if (is.getType() == shop.getItem()) {
                        if (itemsNeeded > is.getAmount()) {
                            if (i == items.length-1) {
                                return false;
                            }

                            itemsNeeded -= is.getAmount();
                        } else {
                            Integer leftover = is.getAmount() - itemsNeeded;

//                                is.setAmount(leftover);

                            break;
                        }
                    }
                } else {

                    if (i == items.length - 1) {
                        return false;
                    }
                }
            }
        }
        return true;
    }

    public void loadShops() throws IOException {
        Gson gson = new Gson();
        System.out.println(VirtualShops.getPlugin().getDataFolder());
        File file = new File(VirtualShops.getPlugin().getDataFolder().getAbsolutePath() + "/shops.json");
        if (file.exists()){
            Reader reader = new FileReader(file);
            Shop[] s = gson.fromJson(reader, Shop[].class);
            shops = new ArrayList<>(Arrays.asList(s));
            VirtualShops.getPlugin().getLogger().info("Shops Loaded");
        }
    }



    public void save() throws IOException {
        Gson gson = new Gson();
        File file = new File(VirtualShops.getPlugin().getDataFolder().getAbsolutePath() + "/shops.json");

        file.getParentFile().mkdir();
        file.createNewFile();
        Writer writer = new FileWriter(file, false);
        gson.toJson(shops, writer);
        writer.flush();
        writer.close();
        System.out.println(shops);
    }
}
