package org.redcastlemedia.multitallented.physicalcurrency.listeners;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockPlaceEvent;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryOpenEvent;
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
}
