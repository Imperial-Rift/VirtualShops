package dev.jdog.virtualshops.menuManager;

import org.bukkit.Material;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Player;

import java.util.HashMap;
import java.util.Map;

public class PlayerMenuUtility {

    private Player owner;
    private Integer price = 1;
    private Integer amount = 1;
    private Material item;

    private Map<Enchantment, Integer> enchantments;

    private String shopId;

    public PlayerMenuUtility(Player p) {
        this.owner = p;
    }

    public Player getOwner() {
        return owner;
    }

    public Integer getPrice() {
        return price;
    }

    public Integer getAmount() {
        return amount;
    }

    public Material getItem() {
        return item;
    }

    public Map<Enchantment, Integer> getEnchantments() {
        return enchantments;
    }

    public String getShopId() {
        return shopId;
    }

    public void increasePrice() {
        this.price++;
    }

    public void increaseAmount() {
        this.amount++;
    }

    public void decreasePrice() {
        this.price--;
    }

    public void decreaseAmount() {
        this.amount--;
    }

    public void setItem(Material item) {
        this.item = item;
    }

    public void setEnchantments(Map<Enchantment, Integer> enchantments) {
        this.enchantments = enchantments;
    }

    public void setShopId(String shopId) {
        this.shopId = shopId;
    }
}
