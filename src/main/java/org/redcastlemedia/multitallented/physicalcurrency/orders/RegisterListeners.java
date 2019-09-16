package org.redcastlemedia.multitallented.physicalcurrency.orders;

import org.bukkit.Bukkit;
import org.redcastlemedia.multitallented.physicalcurrency.PhysicalCurrency;
import org.redcastlemedia.multitallented.physicalcurrency.listeners.PlayerListener;
import org.redcastlemedia.multitallented.physicalcurrency.listeners.PluginListener;

public final class RegisterListeners {
    private RegisterListeners() {

    }
    public static void execute() {
        Bukkit.getPluginManager().registerEvents(new PluginListener(), PhysicalCurrency.getInstance());
        Bukkit.getPluginManager().registerEvents(new PlayerListener(), PhysicalCurrency.getInstance());
    }
}
