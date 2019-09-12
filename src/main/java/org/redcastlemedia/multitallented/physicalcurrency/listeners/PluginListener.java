package org.redcastlemedia.multitallented.physicalcurrency.listeners;

import org.bukkit.Bukkit;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.server.PluginDisableEvent;
import org.bukkit.event.server.PluginEnableEvent;
import org.redcastlemedia.multitallented.physicalcurrency.PhysicalCurrency;
import org.redcastlemedia.multitallented.physicalcurrency.orders.RegisterEconomyService;

public class PluginListener implements Listener {
    @EventHandler
    public void onPluginEnable(PluginEnableEvent event) {
        if (!event.getPlugin().getName().equals("Vault")) {
            return;
        }
        RegisterEconomyService.execute();
    }
    @EventHandler
    public void onPluginDisable(PluginDisableEvent event) {
        if (!event.getPlugin().getName().equals("Vault")) {
            return;
        }
        Bukkit.getPluginManager().disablePlugin(PhysicalCurrency.getInstance());
    }
}
