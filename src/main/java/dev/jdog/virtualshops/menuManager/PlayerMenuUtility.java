package dev.jdog.virtualshops.menuManager;

import org.bukkit.Material;
import org.bukkit.entity.Player;

public class PlayerMenuUtility {

    private Player owner;
    private Integer price = 0;
    private Integer amount = 0;
    private Material item;

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

    public void setShopId(String shopId) {
        this.shopId = shopId;
    }
}
