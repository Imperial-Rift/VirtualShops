package dev.jdog.virtualshops.menuManager;

import org.bukkit.Material;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Player;

import java.util.HashMap;
import java.util.Map;

public class PlayerMenuUtility {

    private Player owner;
    private Boolean isServerShop = false;
    private Integer price = 1;
    private Integer amount = 1;

    private Integer rate = 1;
    private Material item;

    private Map<Enchantment, Integer> enchantments;

    private String shopId;

    public PlayerMenuUtility(Player p) {
        this.owner = p;
    }

    public Player getOwner() {
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

    public Integer getRate() {
        return rate;
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

    public void setIsServerShop(Boolean serverShop) {
        isServerShop = serverShop;
    }

    public void increasePrice() {
        this.price+=rate;
    }

    public void increaseAmount() {
        this.amount+=rate;
    }

    public void decreasePrice() {
        this.price-=rate;
    }

    public void decreaseAmount() {
        this.amount-=rate;
    }

    public void decreaseRate() {
        this.rate--;
    }

    public void increaseRate() {
        this.rate++;
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
