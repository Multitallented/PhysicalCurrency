package org.redcastlemedia.multitallented.physicalcurrency.orders;

import org.bukkit.Bukkit;
import org.bukkit.plugin.Plugin;
import org.bukkit.plugin.ServicePriority;
import org.redcastlemedia.multitallented.physicalcurrency.PCurrEconomy;
import org.redcastlemedia.multitallented.physicalcurrency.PhysicalCurrency;

import net.milkbowl.vault.economy.Economy;

public final class RegisterEconomyService {
    private RegisterEconomyService() {

    }
    public static void execute() {
        if (!Bukkit.getPluginManager().isPluginEnabled("Vault")) {
            Bukkit.getPluginManager().disablePlugin(PhysicalCurrency.getInstance());
            PhysicalCurrency.getInstance().getLogger().severe(PhysicalCurrency.getPrefix() +
                    "Unable to hook Vault. Disabling...");
            return;
        }
        Plugin vault = Bukkit.getPluginManager().getPlugin("Vault");
        if (vault == null) {
            Bukkit.getPluginManager().disablePlugin(PhysicalCurrency.getInstance());
            PhysicalCurrency.getInstance().getLogger().severe(PhysicalCurrency.getPrefix() +
                    "Unable to hook Vault. Disabling...");
            return;
        }
        PCurrEconomy pCurrEconomy = new PCurrEconomy();
        Bukkit.getServicesManager().register(Economy.class, pCurrEconomy, vault, ServicePriority.Normal);
    }
}
