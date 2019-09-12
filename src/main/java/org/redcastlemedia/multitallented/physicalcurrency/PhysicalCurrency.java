package org.redcastlemedia.multitallented.physicalcurrency;

import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.plugin.java.JavaPlugin;
import org.redcastlemedia.multitallented.physicalcurrency.orders.RegisterEconomyService;
import org.redcastlemedia.multitallented.physicalcurrency.orders.RegisterListeners;

public class PhysicalCurrency extends JavaPlugin {
    private static PhysicalCurrency instance = null;

    // https://github.com/MilkBowl/Vault/blob/master/src/net/milkbowl/vault/Vault.java

    @Override
    public void onEnable() {
        instance = this;
        RegisterEconomyService.execute();
        RegisterListeners.execute();
        getLogger().info(getPrefix() + "Enabled!");
    }

    @Override
    public void onDisable() {
        getServer().getServicesManager().unregisterAll(this);
        Bukkit.getScheduler().cancelTasks(this);
        getLogger().info(getPrefix() + "Disabled!");
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        // TODO pay
        // TODO admin
        return true;
    }

    public static String getPrefix() {
        return "[PCurr] ";
    }

    public static PhysicalCurrency getInstance() {
        return instance;
    }
}
