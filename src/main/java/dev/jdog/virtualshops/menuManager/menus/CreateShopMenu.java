package dev.jdog.virtualshops.menuManager.menus;

import dev.jdog.virtualshops.VirtualShops;
import dev.jdog.virtualshops.menuManager.Menu;
import dev.jdog.virtualshops.menuManager.PlayerMenuUtility;
import dev.jdog.virtualshops.models.Shop;
import dev.jdog.virtualshops.utils.ShopStorage;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.ClickType;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.ArrayList;

public class CreateShopMenu extends Menu {
    public CreateShopMenu(PlayerMenuUtility playerMenuUtility) {
        super(playerMenuUtility);
    }

    @Override
    public String getMenuName() {
        return "Create Shop";
    }

    @Override
    public int getSlots() {
        return 27;
    }

    @Override
    public void handleMenu(InventoryClickEvent e) {
        Player p = (Player) e.getWhoClicked();
        PlayerMenuUtility playerMenuUtility = VirtualShops.getPlayerUtiliity(p);
        CreateShopMenu menu = new CreateShopMenu(playerMenuUtility);

        switch (e.getCurrentItem().getItemMeta().getLocalizedName()) {
            case "price-btn":
                if (e.getClick().equals(ClickType.LEFT)) {
                    playerMenuUtility.increasePrice();
                    menu.open();
                } else if (e.getClick().equals(ClickType.RIGHT)) {
                    if (playerMenuUtility.getPrice() > 1) {
                        playerMenuUtility.decreasePrice();
                        menu.open();
                    }
                }

                break;
            case "amount-btn":
                if (e.getClick().equals(ClickType.LEFT)) {
                    if (playerMenuUtility.getAmount() < 64) {
                        playerMenuUtility.increaseAmount();
                        menu.open();
                    }
                } else if (e.getClick().equals(ClickType.RIGHT)) {
                    if (playerMenuUtility.getPrice() > 1) {
                        playerMenuUtility.decreasePrice();
                        menu.open();
                    }
                }
                break;
            case "cancel-btn":
                p.closeInventory();
                break;
            case "confirm-btn":
                new ShopStorage().createShop(new Shop(playerMenuUtility.getItem(), p.getName(), playerMenuUtility.getPrice(), playerMenuUtility.getAmount()));
                p.sendMessage(ChatColor.GOLD + "Shop successfully created! Use /addshopchest <shop> while looking at a chest to add a shop chest.");
                p.closeInventory();

                break;
            default:
                Material item = e.getCurrentItem().getType();
                playerMenuUtility.setItem(item);
                menu.open();
                break;
        }
    }

    @Override
    public void setMenuItems() {
        PlayerMenuUtility playerMenuUtility = VirtualShops.getPlayerUtiliity(getPlayer());


        ItemStack item = new ItemStack(Material.BARRIER);
        if (playerMenuUtility.getItem() != null) {
            item = new ItemStack(playerMenuUtility.getItem());
        }
        ItemMeta itemMeta = item.getItemMeta();
        itemMeta.setLocalizedName("item-display");
        item.setItemMeta(itemMeta);

        ItemStack price = new ItemStack(Material.GOLD_INGOT);
        ItemMeta priceMeta = price.getItemMeta();
        priceMeta.setDisplayName(ChatColor.GREEN + "Price");
        ArrayList<String> priceLore = new ArrayList<String>();
        priceLore.add("$"+playerMenuUtility.getPrice());
        priceLore.add(ChatColor.DARK_PURPLE + "Left click to increase");
        priceLore.add(ChatColor.DARK_PURPLE + "Right click to decrease");
        priceMeta.setLore(priceLore);
        priceMeta.setLocalizedName("price-btn");
        price.setItemMeta(priceMeta);

        ItemStack amount = new ItemStack(Material.IRON_INGOT);
        ItemMeta amountMeta = amount.getItemMeta();
        amountMeta.setDisplayName(ChatColor.GREEN + "Amount");
        ArrayList<String> amountLore = new ArrayList<String>();
        amountLore.add(playerMenuUtility.getAmount().toString());
        amountLore.add(ChatColor.DARK_PURPLE + "Left click to increase");
        amountLore.add(ChatColor.DARK_PURPLE + "Right click to decrease");
        amountMeta.setLore(amountLore);
        amountMeta.setLocalizedName("amount-btn");
        amount.setItemMeta(amountMeta);

        ItemStack cancel = new ItemStack(Material.RED_CONCRETE);
        ItemMeta cancelMeta = cancel.getItemMeta();
        cancelMeta.setDisplayName(ChatColor.RED + "Cancel");
        cancelMeta.setLocalizedName("cancel-btn");
        cancel.setItemMeta(cancelMeta);

        ItemStack confirm = new ItemStack(Material.GREEN_CONCRETE);
        ItemMeta confirmMeta = confirm.getItemMeta();
        confirmMeta.setDisplayName(ChatColor.GREEN + "Confirm");
        confirmMeta.setLocalizedName("confirm-btn");
        confirm.setItemMeta(confirmMeta);



        ItemStack[] items = {null, null, null, item, price, amount, null, null, null, null, null, null, null, null, null, null, null, null, cancel, null, null, null, null, null, null, null, confirm};

        inventory.setContents(items);
    }
}
