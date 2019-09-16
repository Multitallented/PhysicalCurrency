package org.redcastlemedia.multitallented.physicalcurrency;

import java.util.HashMap;

import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.plugin.java.JavaPlugin;
import org.redcastlemedia.multitallented.physicalcurrency.accounts.AccountManager;
import org.redcastlemedia.multitallented.physicalcurrency.commands.PCurrCommand;
import org.redcastlemedia.multitallented.physicalcurrency.orders.CreateCustomRecipe;
import org.redcastlemedia.multitallented.physicalcurrency.orders.RegisterCommands;
import org.redcastlemedia.multitallented.physicalcurrency.orders.RegisterEconomyService;
import org.redcastlemedia.multitallented.physicalcurrency.orders.RegisterListeners;

public class PhysicalCurrency extends JavaPlugin {
    private static PhysicalCurrency instance = null;
    private static HashMap<String, PCurrCommand> commands;

    // https://github.com/MilkBowl/Vault/blob/master/src/net/milkbowl/vault/Vault.java

    @Override
    public void onEnable() {
        instance = this;
        RegisterEconomyService.execute();
        RegisterListeners.execute();
        commands = RegisterCommands.execute();
        CreateCustomRecipe.execute();
        Bukkit.getScheduler().scheduleSyncRepeatingTask(this, new Runnable() {
            @Override
            public void run() {
                AccountManager.getInstance().cleanUp();
            }
        }, 80L, 80L);
        getLogger().info(getPrefix() + "Enabled!");
    }

    @Override
    public void onDisable() {
        getServer().getServicesManager().unregisterAll(this);
        Bukkit.getScheduler().cancelTasks(this);
        AccountManager.getInstance().unloadAllPlayers();
        instance = null;
        getLogger().info(getPrefix() + "Disabled!");
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (!commands.containsKey(label)) {
            return false;
        }
        return commands.get(label).execute(sender, args);
    }

    public static String getPrefix() {
        return "[PCurr] ";
    }

    public static PhysicalCurrency getInstance() {
        return instance;
    }
}
