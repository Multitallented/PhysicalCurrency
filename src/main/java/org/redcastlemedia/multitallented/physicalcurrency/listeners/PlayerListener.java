package org.redcastlemedia.multitallented.physicalcurrency.listeners;

import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockPlaceEvent;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryOpenEvent;
import org.bukkit.event.inventory.PrepareItemCraftEvent;
import org.bukkit.event.player.PlayerDropItemEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerQuitEvent;
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
        if (event.getInventory().getResult() == null) {
            return;
        }
        ItemStack resultStack = event.getInventory().getResult();
        ItemStack singleItem = ConfigManager.getInstance().getSingleItem();
        ItemStack nineItem = ConfigManager.getInstance().getNineItem();
        ItemStack eightyOneItem = ConfigManager.getInstance().getEightyOneItem();
        if (ItemUtil.isEquivalentItem(resultStack, singleItem)) {
            if (hasMissingIngredients(event, nineItem)) {
                event.getInventory().setResult(null);
                return;
            }
        } else if (ItemUtil.isEquivalentItem(resultStack, nineItem)) {
            if (hasMissingIngredients(event, singleItem) && hasMissingIngredients(event, eightyOneItem)) {
                event.getInventory().setResult(null);
                return;
            }
        } else if (ItemUtil.isEquivalentItem(resultStack, eightyOneItem)) {
            if (hasMissingIngredients(event, nineItem)) {
                event.getInventory().setResult(null);
                return;
            }
        }
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
