package main.java.org.redcastlemedia.multitallented.physicalcurrency;

import net.milkbowl.vault.economy.Economy;
import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.plugin.Plugin;
import org.bukkit.plugin.ServicePriority;
import org.bukkit.plugin.java.JavaPlugin;

public class PhysicalCurrency extends JavaPlugin {
    private static PhysicalCurrency instance = null;

    // https://github.com/MilkBowl/Vault/blob/master/src/net/milkbowl/vault/Vault.java

    @Override
    public void onEnable() {
        instance = this;
        if (!Bukkit.getPluginManager().isPluginEnabled("Vault")) {
            Bukkit.getPluginManager().disablePlugin(this);
            getLogger().severe(getPrefix() + "Unable to hook Vault. Disabling...");
            return;
        }
        Plugin vault = Bukkit.getPluginManager().getPlugin("Vault");
        if (vault == null) {
            Bukkit.getPluginManager().disablePlugin(this);
            getLogger().severe(getPrefix() + "Unable to hook Vault. Disabling...");
            return;
        }
        PCurrEconomy pCurrEconomy = new PCurrEconomy();
        Bukkit.getServicesManager().register(Economy.class, pCurrEconomy, vault, ServicePriority.Normal);
    }

    @Override
    public void onDisable() {
        getServer().getServicesManager().unregisterAll(this);
        Bukkit.getScheduler().cancelTasks(this);
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {

        return true;
    }

    public static String getPrefix() {
        return "[PCurr] ";
    }

    public static PhysicalCurrency getInstance() {
        return instance;
    }
}
