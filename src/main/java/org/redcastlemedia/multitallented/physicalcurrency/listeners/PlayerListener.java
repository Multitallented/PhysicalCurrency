package org.redcastlemedia.multitallented.physicalcurrency.listeners;

import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerQuitEvent;
import org.redcastlemedia.multitallented.physicalcurrency.accounts.AccountManager;

public class PlayerListener implements Listener {

    @EventHandler
    public void onPlayerJoin(PlayerJoinEvent event) {
        AccountManager.getInstance().hasAccount(event.getPlayer().getUniqueId());
    }

    @EventHandler
    public void onPlayerQuit(PlayerQuitEvent event) {
        AccountManager.getInstance().unloadPlayer(event.getPlayer().getUniqueId(), true);
    }
}
