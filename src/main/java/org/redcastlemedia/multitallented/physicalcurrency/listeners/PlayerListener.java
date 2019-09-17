package org.redcastlemedia.multitallented.physicalcurrency.listeners;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryOpenEvent;
import org.bukkit.event.player.PlayerDropItemEvent;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerQuitEvent;
import org.redcastlemedia.multitallented.physicalcurrency.accounts.AccountManager;
import org.redcastlemedia.multitallented.physicalcurrency.orders.TransferAccountToPhysical;

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
}
