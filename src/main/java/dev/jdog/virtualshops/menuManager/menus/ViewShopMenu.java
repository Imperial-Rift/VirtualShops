package dev.jdog.virtualshops.menuManager.menus;

import com.sun.tools.javac.jvm.Items;
import dev.jdog.virtualshops.VirtualShops;
import dev.jdog.virtualshops.menuManager.Menu;
import dev.jdog.virtualshops.menuManager.PlayerMenuUtility;
import dev.jdog.virtualshops.models.Shop;
import dev.jdog.virtualshops.utils.ShopStorage;
import net.milkbowl.vault.economy.Economy;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.OfflinePlayer;
import org.bukkit.block.Block;
import org.bukkit.block.Chest;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import javax.swing.text.View;
import java.util.ArrayList;
import java.util.HashMap;

public class ViewShopMenu extends Menu {
    public ViewShopMenu(PlayerMenuUtility playerMenuUtility) {
        super(playerMenuUtility);
    }

    @Override
    public String getMenuName() {
        return "Virtual Shop";
    }

    @Override
    public int getSlots() {
        return 54;
    }

    @Override
    public void handleMenu(InventoryClickEvent e) {
        Shop shop = new ShopStorage().findShopById(playerMenuUtility.getShopId());
        Economy economy = VirtualShops.getEconomy();


        switch (e.getCurrentItem().getType()) {
            case GOLD_INGOT:
                HashMap<Integer, ItemStack> notAdded = player.getInventory().addItem(new ItemStack(shop.getItem()));

                if (!notAdded.isEmpty()) {
                    player.sendMessage(ChatColor.DARK_RED + "Inventory full.");
                    break;
                }

                Block block = Bukkit.getServer().getWorld(shop.getChestWorld()).getBlockAt(shop.getChest());
                if (block.getState() instanceof Chest) {
                    Chest chest = (Chest) block.getState();
                    Inventory chestInv = chest.getInventory();
                    ItemStack[] items = chestInv.getContents();

                    Integer itemsNeeded = shop.getAmount();
                    for (ItemStack is : items) {
                        if (is != null) {
                            if (is.getType() == shop.getItem()) {
                                if (itemsNeeded > 0) {
                                    if (itemsNeeded > is.getAmount()) {
                                        is.setAmount(0);
                                        itemsNeeded -= is.getAmount();
                                    } else {
                                        Integer leftover = is.getAmount() - itemsNeeded;

                                        is.setAmount(leftover);

                                        break;
                                    }
                                }
                            }
                        }
                    }

                    chestInv.setContents(items);

                    economy.withdrawPlayer(player, shop.getPrice());
                    economy.depositPlayer(VirtualShops.getPlugin().getServer().getPlayer(shop.getOwner()), shop.getPrice());

                    player.sendMessage(ChatColor.GOLD + "Successfully bought " + ChatColor.WHITE + shop.getAmount() + " " +  shop.getItem().name().replace("_", " ") + ChatColor.GOLD + " from " + shop.getOwner());

                    super.open();
                }

            case ARROW:
                ShopsMenu menu = new ShopsMenu(playerMenuUtility);
                menu.open();
        }

    }

    @Override
    public void setMenuItems() {
        Shop shop = new ShopStorage().findShopById(playerMenuUtility.getShopId());
        Economy economy = VirtualShops.getEconomy();


        ItemStack display = new ItemStack(shop.getItem());
//        display.addEnchantment(Enchantment.KNOCKBACK, 1);

        ItemStack buy = new ItemStack(Material.GOLD_INGOT);
        ItemMeta buyMeta = buy.getItemMeta();
        buyMeta.setDisplayName(ChatColor.GREEN + "Buy");
        ArrayList<String> buyLore = new ArrayList<>();
        buyLore.add(ChatColor.WHITE + "$"+shop.getPrice() + " for " + shop.getAmount());
        buyLore.add("Left click to buy");
        buyMeta.setLore(buyLore);
        buy.setItemMeta(buyMeta);

        // Error items
        ItemStack out = new ItemStack(Material.BARRIER);
        ItemMeta outMeta = out.getItemMeta();
        outMeta.setDisplayName(ChatColor.RED + "Out of Stock");
        out.setItemMeta(outMeta);

        ItemStack noFunds = new ItemStack(Material.BARRIER);
        ItemMeta noFundsMeta = noFunds.getItemMeta();
        noFundsMeta.setDisplayName(ChatColor.RED + "Insufficient Funds");
        ArrayList<String> noFundsLore = new ArrayList<>();
        noFundsLore.add(ChatColor.WHITE + "Price: " + "$"+shop.getPrice());
        noFundsMeta.setLore(noFundsLore);
        noFunds.setItemMeta(noFundsMeta);

        if (!new ShopStorage().isInStock(shop.getId())) {
            buy = out;
        } else if (economy.getBalance(player) < shop.getPrice()) {
            buy = noFunds;
        }




        ItemStack bal = new ItemStack(Material.PAPER);
        ItemMeta balMeta = bal.getItemMeta();
        balMeta.setDisplayName(ChatColor.GREEN + "Your Balance");
        ArrayList<String> balLore = new ArrayList<>();
        balLore.add(ChatColor.WHITE + economy.format(economy.getBalance(player)));
        balMeta.setLore(balLore);
        bal.setItemMeta(balMeta);

        ItemStack back = new ItemStack(Material.ARROW);
        ItemMeta backMeta = back.getItemMeta();
        backMeta.setDisplayName(ChatColor.GREEN + "Back");
        back.setItemMeta(backMeta);


        ItemStack[] items = {null, null ,null, null, null, null, null, null ,null, null, null ,null, null, null, null, null, null ,null, null, null ,display, null, buy, null, bal, null ,null, null, null ,null, null, null, null, null, null ,null, null, null ,null, null, null, null, null, null ,null, back, null ,null, null, null, null, null, null ,null};
//        Shop[] shops = new ShopStorage().getShops();
//        ArrayList<ItemStack> itemList = new ArrayList<ItemStack>();
//        for (Shop shop : shops) {
//            ItemStack item = new ItemStack(shop.getItem());
//            itemList.add(item);
//        }
//        ItemStack[] items = itemList.toArray(new ItemStack[0]);
//
        inventory.setContents(items);

        setFillerGlass();

    }
}
