package dev.jdog.virtualshops.models;

import com.sun.org.apache.xpath.internal.operations.Bool;
import dev.jdog.virtualshops.VirtualShops;
import org.bukkit.*;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.enchantments.EnchantmentWrapper;

import javax.swing.event.DocumentEvent;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

public class Shop {
    private Material item;
    private HashMap<String, Integer> enchantments;
    private String owner;

    private Boolean isServerShop;
    private Integer price;
    private Integer amount;
    private String id;
    private Integer chestX;
    private Integer chestY;
    private Integer chestZ;
    private String chestWorld;

    public Shop(Material item, Map<Enchantment, Integer> enchantments, String owner, Boolean isServerShop, Integer price, Integer amount) {
        this.item = item;

        HashMap<String, Integer> newEnchants = new HashMap<>();

        for (Map.Entry<Enchantment, Integer> entry: enchantments.entrySet()) {
            String name = entry.getKey().getKey().toString();
            Integer level = entry.getValue();

            newEnchants.put(name, level);
        }
        this.enchantments = newEnchants;
        this.owner = owner;
        this.isServerShop = isServerShop;
        this.price = price;
        this.amount = amount;
        this.id = UUID.randomUUID().toString();
    }

    public Material getItem() {
        return item;
    }

    public Map<Enchantment, Integer> getEnchantments() {
        Map<Enchantment, Integer> newEnchants = new HashMap<>();

        for (Map.Entry<String, Integer> entry: enchantments.entrySet()) {
            String name = entry.getKey();
            Integer level = entry.getValue();

            Enchantment enchantment = EnchantmentWrapper.getByKey(NamespacedKey.fromString(name));


            newEnchants.put(enchantment, level);
        }
        return newEnchants;
    }

    public String getOwner() {
        return owner;
    }

    public Boolean getIsServerShop() {
        return isServerShop;
    }

    public Integer getPrice() {
        return price;
    }

    public Integer getAmount() {
        return amount;
    }

    public String getId() {
        return id;
    }

    public void setChest(Location location) {
        this.chestX = location.getBlockX();
        this.chestY = location.getBlockY();
        this.chestZ = location.getBlockZ();
        this.chestWorld = location.getWorld().getName();
    }

    public Location getChest() {
        return new Location(Bukkit.getWorld(this.chestWorld), this.chestX, this.chestY, this.chestZ);
    }

    public String getChestWorld() {
        return chestWorld;
    }
}
