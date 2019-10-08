package org.redcastlemedia.multitallented.physicalcurrency;

import java.util.HashMap;

import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.plugin.RegisteredServiceProvider;
import org.bukkit.plugin.java.JavaPlugin;
import org.redcastlemedia.multitallented.physicalcurrency.accounts.AccountManager;
import org.redcastlemedia.multitallented.physicalcurrency.commands.PCurrCommand;
import org.redcastlemedia.multitallented.physicalcurrency.orders.CreateCustomRecipe;
import org.redcastlemedia.multitallented.physicalcurrency.orders.RegisterCommands;
import org.redcastlemedia.multitallented.physicalcurrency.orders.RegisterEconomyService;
import org.redcastlemedia.multitallented.physicalcurrency.orders.RegisterListeners;
import org.redcastlemedia.multitallented.physicalcurrency.orders.StartSaveThread;

import net.milkbowl.vault.permission.Permission;

public class PhysicalCurrency extends JavaPlugin {
    private static PhysicalCurrency instance = null;
    private static HashMap<String, PCurrCommand> commands;
    public static Permission perm = null;

    // https://github.com/MilkBowl/Vault/blob/master/src/net/milkbowl/vault/Vault.java

    @Override
    public void onEnable() {
        instance = this;
        try {
            if (!getDataFolder().exists()) {
                getDataFolder().mkdir();
                saveDefaultConfig();
            }
            setupPermissions();
            RegisterEconomyService.execute();
            RegisterListeners.execute();
            commands = RegisterCommands.execute();
            CreateCustomRecipe.execute();
            StartSaveThread.execute();
        } catch (Exception e) {
            e.printStackTrace();
            getLogger().severe("Unable to start PCurrency");
            Bukkit.getPluginManager().disablePlugin(this);
        }
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

    private void setupPermissions() {
        RegisteredServiceProvider<Permission> permissionProvider = getServer().getServicesManager().getRegistration(Permission.class);
        if (permissionProvider != null) {
            perm = permissionProvider.getProvider();
        }
    }

    public static String getPrefix() {
        return "[PCurr] ";
    }

    public static PhysicalCurrency getInstance() {
        return instance;
    }
}
