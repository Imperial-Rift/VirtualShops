package dev.jdog.virtualshops.models;

import dev.jdog.virtualshops.VirtualShops;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.World;

import java.util.UUID;

public class Shop {
    private Material item;
    private String owner;
    private Integer price;
    private Integer amount;
    private String id;
    private Integer chestX;
    private Integer chestY;
    private Integer chestZ;
    private String chestWorld;

    public Shop(Material item, String owner, Integer price, Integer amount) {
        this.item = item;
        this.owner = owner;
        this.price = price;
        this.amount = amount;
        this.id = UUID.randomUUID().toString();
    }

    public Material getItem() {
        return item;
    }

    public String getOwner() {
        return owner;
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
