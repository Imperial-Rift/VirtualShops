package dev.jdog.virtualshops.menuManager.menus;

import com.sun.tools.javac.jvm.Items;
import dev.jdog.virtualshops.VirtualShops;
import dev.jdog.virtualshops.menuManager.Menu;
import dev.jdog.virtualshops.menuManager.PaginatedMenu;
import dev.jdog.virtualshops.menuManager.PlayerMenuUtility;
import dev.jdog.virtualshops.models.Shop;
import dev.jdog.virtualshops.utils.ShopStorage;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.NamespacedKey;
import org.bukkit.entity.Item;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.persistence.PersistentDataType;

import java.util.ArrayList;

public class ShopsMenu extends PaginatedMenu {
    public ShopsMenu(PlayerMenuUtility playerMenuUtility) {
        super(playerMenuUtility);
    }

    @Override
    public String getMenuName() {
        return "Shops";
    }

    @Override
    public int getSlots() {
        return 54;
    }

    @Override
    public void handleMenu(InventoryClickEvent e) {
//        System.out.println(e.getCurrentItem().getItemMeta().getLocalizedName());
        if (!e.getCurrentItem().getItemMeta().getLocalizedName().equals("")) {
            ViewShopMenu menu = new ViewShopMenu(playerMenuUtility);
            playerMenuUtility.setShopId(e.getCurrentItem().getItemMeta().getLocalizedName());

            menu.open();
        }

        // Page controls
        if (ChatColor.stripColor(e.getCurrentItem().getItemMeta().getDisplayName()).equalsIgnoreCase("Right")) {
            if (!((index + 1) >= new ShopStorage().getShops().size())) {
                page++;
                super.open();
            }
        } else if (ChatColor.stripColor(e.getCurrentItem().getItemMeta().getDisplayName()).equalsIgnoreCase("Left")) {
            if (page != 0) {
                page--;
                super.open();
            }
        } else if (ChatColor.stripColor(e.getCurrentItem().getItemMeta().getDisplayName()).equalsIgnoreCase("Close")) {
            e.getWhoClicked().closeInventory();
        }



    }

    @Override
    public void setMenuItems() {
        ArrayList<Shop> shops = new ShopStorage().getShops();
//        ArrayList<ItemStack> itemList = new ArrayList<ItemStack>();
//        for (Shop shop : shops) {
//            ItemStack item = new ItemStack(shop.getItem());
//            ItemMeta itemMeta = item.getItemMeta();
//            itemMeta.setLocalizedName("shop");
//            item.setItemMeta(itemMeta);
//            itemList.add(item);
//        }
//        ItemStack[] items = itemList.toArray(new ItemStack[0]);
//
//        inventory.setContents(items);
        addMenuBorder();

//        ArrayList<Player> players = new ArrayList<Player>(VirtualShops.getPlugin().getServer().getOnlinePlayers());

        ///////////////////////////////////// Pagination loop template
        if(shops != null && !shops.isEmpty()) {
            for(int i = 0; i < getMaxItemsPerPage(); i++) {
                index = getMaxItemsPerPage() * page + i;
                if(index >= shops.size()) break;
                if (shops.get(index) != null) {
                    ///////////////////////////

                    //Create an item from our collection and place it into the inventory
//                    ItemStack playerItem = new ItemStack(Material.PLAYER_HEAD, 1);
//                    ItemMeta playerMeta = playerItem.getItemMeta();
//                    playerMeta.setDisplayName("i" + i);
////
////                    playerMeta.getPersistentDataContainer().set(new NamespacedKey(VirtualShops.getPlugin(), "uuid"), PersistentDataType.STRING, players.get(index).getUniqueId().toString());
//                    playerItem.setItemMeta(playerMeta);
                    if (shops.get(index).getChestWorld() != null || shops.get(index).getIsServerShop()) {
                        ItemStack item = new ItemStack(shops.get(index).getItem());

                        item.addEnchantments(shops.get(index).getEnchantments());
                        ItemMeta itemMeta = item.getItemMeta();
                        itemMeta.setDisplayName(ChatColor.RESET + "" + shops.get(index).getAmount() + " " + item.getType().name().replace("_", " "));
                        itemMeta.setLocalizedName(shops.get(index).getId());
                        ArrayList<String> lore = new ArrayList<String>();
                        lore.add(ChatColor.WHITE + shops.get(index).getOwner());
                        lore.add(ChatColor.GREEN + "$" + shops.get(index).getPrice().toString());
                        lore.add("Click to view");

                        itemMeta.setLore(lore);
                        itemMeta.addItemFlags(ItemFlag.HIDE_ATTRIBUTES);

                        item.setItemMeta(itemMeta);

                        inventory.addItem(item);

                        ////////////////////////
                    }
                }
            }
        }

    }
}
