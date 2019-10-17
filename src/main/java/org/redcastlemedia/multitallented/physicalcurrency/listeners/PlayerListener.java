package org.redcastlemedia.multitallented.physicalcurrency.listeners;

import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockPlaceEvent;
import org.bukkit.event.inventory.*;
import org.bukkit.event.player.PlayerDropItemEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerQuitEvent;
import org.bukkit.inventory.CraftingInventory;
import org.bukkit.inventory.ItemStack;
import org.redcastlemedia.multitallented.physicalcurrency.ConfigManager;
import org.redcastlemedia.multitallented.physicalcurrency.accounts.AccountManager;
import org.redcastlemedia.multitallented.physicalcurrency.orders.TransferAccountToPhysical;
import org.redcastlemedia.multitallented.physicalcurrency.util.ItemUtil;

public class PlayerListener implements Listener {

    @EventHandler
    public void onPlayerJoin(PlayerJoinEvent event) {
        AccountManager.getInstance().hasAccount(event.getPlayer().getUniqueId());
    }

    @EventHandler
    public void onPlayerQuit(PlayerQuitEvent event) {
        AccountManager.getInstance().unloadPlayer(event.getPlayer().getUniqueId(), true);
    }

    @EventHandler(ignoreCancelled = true)
    public void onPlayerDropItem(PlayerDropItemEvent event) {
        TransferAccountToPhysical.execute(event.getPlayer());
    }

    @EventHandler(ignoreCancelled = true)
    public void onPlayerItemClick(InventoryClickEvent event) {
        TransferAccountToPhysical.execute((Player) event.getWhoClicked());
    }

    @EventHandler(ignoreCancelled = true)
    public void onPlayerOpenInventory(InventoryOpenEvent event) {
        TransferAccountToPhysical.execute((Player) event.getPlayer());
    }

    @EventHandler(ignoreCancelled = true)
    public void onPlayerInteract(PlayerInteractEvent event) {
        ItemStack itemStack = event.getItem();
        if (itemStack == null || !ConfigManager.getInstance().isPreventInteract()) {
            return;
        }
        if (ItemUtil.isEquivalentItem(itemStack, ConfigManager.getInstance().getSingleItem()) ||
                ItemUtil.isEquivalentItem(itemStack, ConfigManager.getInstance().getNineItem()) ||
                ItemUtil.isEquivalentItem(itemStack, ConfigManager.getInstance().getEightyOneItem())) {
            event.setCancelled(true);
        }
    }

    @EventHandler(ignoreCancelled = true)
    public void onPlayerPlaceBlock(BlockPlaceEvent event) {
        if (!ConfigManager.getInstance().isPreventInteract()) {
            return;
        }
        ItemStack itemStack = event.getItemInHand();
        if (ItemUtil.isEquivalentItem(itemStack, ConfigManager.getInstance().getSingleItem()) ||
                ItemUtil.isEquivalentItem(itemStack, ConfigManager.getInstance().getNineItem()) ||
                ItemUtil.isEquivalentItem(itemStack, ConfigManager.getInstance().getEightyOneItem())) {
            event.setCancelled(true);
        }
    }

    @EventHandler
    public void onPlayerCraftItem(PrepareItemCraftEvent event) {
        ItemStack singleItem = ConfigManager.getInstance().getSingleItem();
        ItemStack nineItem = ConfigManager.getInstance().getNineItem();
        ItemStack eightyOneItem = ConfigManager.getInstance().getEightyOneItem();
        if (event.getInventory().getResult() == null) {
            int singleCount = 0;
            int nineCount = 0;
            int eightyOneCount = 0;
            for (ItemStack itemStack : event.getInventory().getMatrix()) {
                if (itemStack == null || itemStack.getType() == Material.AIR) {
                    continue;
                }
                if (ItemUtil.isEquivalentItem(itemStack, singleItem)) {
                    singleCount++;
                } else if (ItemUtil.isEquivalentItem(itemStack, nineItem)) {
                    nineCount++;
                } else if (ItemUtil.isEquivalentItem(itemStack, eightyOneItem)) {
                    eightyOneCount++;
                }
            }
            if (singleCount == 9) {
                ItemStack itemStack = new ItemStack(nineItem);
                itemStack.setAmount(1);
                event.getInventory().setResult(itemStack);
            } else if (nineCount == 1) {
                ItemStack itemStack = new ItemStack(singleItem);
                itemStack.setAmount(9);
                event.getInventory().setResult(itemStack);
            } else if (nineCount == 9) {
                ItemStack itemStack = new ItemStack(eightyOneItem);
                itemStack.setAmount(1);
                event.getInventory().setResult(itemStack);
            } else if (eightyOneCount == 1) {
                ItemStack itemStack = new ItemStack(nineItem);
                itemStack.setAmount(9);
                event.getInventory().setResult(itemStack);
            }
        } else {
            ItemStack resultStack = event.getInventory().getResult();
            if (ItemUtil.isEquivalentItem(resultStack, singleItem)) {
                if (hasMissingIngredients(event, nineItem)) {
                    event.getInventory().setResult(null);
                }
            } else if (ItemUtil.isEquivalentItem(resultStack, nineItem)) {
                if (hasMissingIngredients(event, singleItem) && hasMissingIngredients(event, eightyOneItem)) {
                    event.getInventory().setResult(null);
                }
            } else if (ItemUtil.isEquivalentItem(resultStack, eightyOneItem)) {
                if (hasMissingIngredients(event, nineItem)) {
                    event.getInventory().setResult(null);
                }
            }
        }
    }

    @EventHandler
    public void onPlayerCraftEvent(CraftItemEvent event) {
        System.out.println("craft event");
        ItemStack resultStack = event.getCurrentItem();
        System.out.println(event.getCurrentItem() == null);
        System.out.println(event.getCursor() == null);
        if (resultStack == null) {
            System.out.println("null result stack");
            return;
        }
        ItemStack singleItem = ConfigManager.getInstance().getSingleItem();
        ItemStack nineItem = ConfigManager.getInstance().getNineItem();
        ItemStack eightyOneItem = ConfigManager.getInstance().getEightyOneItem();
        if (!ItemUtil.isEquivalentItem(resultStack, singleItem) &&
                !ItemUtil.isEquivalentItem(resultStack, singleItem) &&
                !ItemUtil.isEquivalentItem(resultStack, singleItem)) {
            System.out.println("result stack no match");
            return;
        }
        event.setCancelled(true);
        event.setCurrentItem(resultStack);
        ItemStack[] newStack = new ItemStack[9];
        int i = 0;
        CraftingInventory craftingInventory = event.getInventory();
        for (ItemStack itemStack : craftingInventory.getMatrix()) {
            if (itemStack == null) {
                newStack[i] = null;
            } else {
                if (itemStack.getAmount() < 2) {
                    newStack[i] = null;
                } else {
                    ItemStack itemStack1 = new ItemStack(itemStack);
                    itemStack1.setAmount(itemStack1.getAmount() - 1);
                    newStack[i] = itemStack1;
                }
            }
            i++;
        }
        craftingInventory.setMatrix(newStack);
    }

    private boolean hasMissingIngredients(PrepareItemCraftEvent event, ItemStack ingredient) {
        for (ItemStack itemStack : event.getInventory().getMatrix()) {
            if (itemStack == null || itemStack.getType() == Material.AIR) {
                continue;
            }
            if (!ItemUtil.isEquivalentItem(itemStack, ingredient)) {
                return true;
            }
        }
        return false;
    }
}
