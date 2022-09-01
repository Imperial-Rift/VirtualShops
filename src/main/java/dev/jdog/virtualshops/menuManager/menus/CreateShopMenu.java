package dev.jdog.virtualshops.menuManager.menus;

import dev.jdog.virtualshops.VirtualShops;
import dev.jdog.virtualshops.menuManager.Menu;
import dev.jdog.virtualshops.menuManager.PlayerMenuUtility;
import dev.jdog.virtualshops.models.Shop;
import dev.jdog.virtualshops.utils.ShopStorage;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.NamespacedKey;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.ClickType;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.checkerframework.checker.units.qual.C;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

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
                    if (playerMenuUtility.getPrice() - playerMenuUtility.getRate() > 0) {
                        playerMenuUtility.decreasePrice();
                        menu.open();
                    }
                }

                break;
            case "amount-btn":
                if (e.getClick().equals(ClickType.LEFT)) {
                    if (playerMenuUtility.getAmount() + playerMenuUtility.getRate() < 65) {
                        playerMenuUtility.increaseAmount();
                        menu.open();
                    }
                } else if (e.getClick().equals(ClickType.RIGHT)) {
                    if (playerMenuUtility.getAmount() - playerMenuUtility.getRate() > 0) {
                        playerMenuUtility.decreaseAmount();
                        menu.open();
                    }
                }
                break;
            case "rate-btn":
                if (e.getClick().equals(ClickType.LEFT)) {
                    if (playerMenuUtility.getRate() < 100) {
                        playerMenuUtility.increaseRate();
                        menu.open();
                    }
                } else if (e.getClick().equals(ClickType.RIGHT)) {
                    if (playerMenuUtility.getRate() > 1) {
                        playerMenuUtility.decreaseRate();
                        menu.open();
                    }
                }
                break;
            case "servershop-btn":
                playerMenuUtility.setIsServerShop(!playerMenuUtility.getIsServerShop());
                menu.open();
                break;
            case "cancel-btn":
                p.closeInventory();
                break;
            case "confirm-btn":
                if (playerMenuUtility.getItem() != null) {
                    if (playerMenuUtility.getItem() == Material.BARRIER) {
                        p.sendMessage(ChatColor.DARK_RED + "You must select an item to sell!");
                        break;
                    }
                } else {
                    p.sendMessage(ChatColor.DARK_RED + "You must select an item to sell!");
                    break;
                }

                String owner = p.getName();
                if (playerMenuUtility.getIsServerShop()) {
                    owner = "Server";
                }
                Shop newShop = new Shop(playerMenuUtility.getItem(), playerMenuUtility.getEnchantments(), owner, playerMenuUtility.getIsServerShop(), playerMenuUtility.getPrice(), playerMenuUtility.getAmount());
                new ShopStorage().createShop(newShop);
                if (newShop.getIsServerShop()) {
                    p.sendMessage(ChatColor.GOLD + "Server shop successfully created");
                } else {
                    p.sendMessage(ChatColor.GOLD + "Shop successfully created! Use /addshopchest <shop> while looking at a chest to add a shop chest.");
                    p.sendMessage("Shop id: " + newShop.getId());
                }

                p.closeInventory();

                break;
            default:
                Material item = e.getCurrentItem().getType();
                Map<Enchantment, Integer> enchantments = e.getCurrentItem().getEnchantments();

                playerMenuUtility.setItem(item);
                playerMenuUtility.setEnchantments(enchantments);
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
            item.addEnchantments(playerMenuUtility.getEnchantments());

        }
        ItemMeta itemMeta = item.getItemMeta();
        itemMeta.setLocalizedName("item-display");
        item.setItemMeta(itemMeta);

        ItemStack price = new ItemStack(Material.LIME_CONCRETE);
        ItemMeta priceMeta = price.getItemMeta();
        priceMeta.setDisplayName(ChatColor.GREEN + "Price");
        ArrayList<String> priceLore = new ArrayList<String>();
        priceLore.add("$"+playerMenuUtility.getPrice());
        priceLore.add(ChatColor.DARK_PURPLE + "Left click to increase");
        priceLore.add(ChatColor.DARK_PURPLE + "Right click to decrease");
        priceMeta.setLore(priceLore);
        priceMeta.setLocalizedName("price-btn");
        price.setItemMeta(priceMeta);

        ItemStack amount = new ItemStack(Material.BLUE_CONCRETE);
        ItemMeta amountMeta = amount.getItemMeta();
        amountMeta.setDisplayName(ChatColor.GREEN + "Amount");
        ArrayList<String> amountLore = new ArrayList<String>();
        amountLore.add(playerMenuUtility.getAmount().toString());
        amountLore.add(ChatColor.DARK_PURPLE + "Left click to increase");
        amountLore.add(ChatColor.DARK_PURPLE + "Right click to decrease");
        amountMeta.setLore(amountLore);
        amountMeta.setLocalizedName("amount-btn");
        amount.setItemMeta(amountMeta);

        ItemStack rate = new ItemStack(Material.YELLOW_CONCRETE);
        ItemMeta rateMeta = amount.getItemMeta();
        rateMeta.setDisplayName(ChatColor.GREEN + "Rate");
        ArrayList<String> rateLore = new ArrayList<String>();
        rateLore.add(playerMenuUtility.getRate().toString());
        rateLore.add(ChatColor.DARK_PURPLE + "Left click to increase");
        rateLore.add(ChatColor.DARK_PURPLE + "Right click to decrease");
        rateMeta.setLore(rateLore);
        rateMeta.setLocalizedName("rate-btn");
        rate.setItemMeta(rateMeta);


        ItemStack serverShop = new ItemStack(Material.EMERALD);
        ItemMeta serverShopItemMeta = serverShop.getItemMeta();
        serverShopItemMeta.setDisplayName(ChatColor.GREEN + "Server Shop");
        ArrayList<String> serverShopLore = new ArrayList<String>();
        serverShopLore.add(playerMenuUtility.getIsServerShop().toString());
        serverShopLore.add(ChatColor.DARK_PURPLE + "Left click to change");
        serverShopItemMeta.setLore(serverShopLore);
        serverShopItemMeta.setLocalizedName("servershop-btn");
        serverShop.setItemMeta(serverShopItemMeta);


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



        ItemStack[] items = {null, null, item, price, amount, rate, null, null, null, null, null, null, null, null, null, null, null, null, cancel, null, null, null, null, null, null, null, confirm};
        if (player.isOp()) {
            items[6] = serverShop;
        }

        inventory.setContents(items);
    }
}
