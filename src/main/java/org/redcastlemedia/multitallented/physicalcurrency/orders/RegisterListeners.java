package org.redcastlemedia.multitallented.physicalcurrency.orders;

import org.bukkit.Bukkit;
import org.redcastlemedia.multitallented.physicalcurrency.PhysicalCurrency;
import org.redcastlemedia.multitallented.physicalcurrency.listeners.PluginListener;

public class RegisterListeners {
    public static void execute() {
        PluginListener pluginListener = new PluginListener();
        Bukkit.getPluginManager().registerEvents(pluginListener, PhysicalCurrency.getInstance());
    }
}
