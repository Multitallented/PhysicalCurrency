package org.redcastlemedia.multitallented.physicalcurrency.accounts;

import java.io.File;
import java.util.HashMap;
import java.util.HashSet;
import java.util.UUID;
import java.util.concurrent.ScheduledFuture;
import java.util.concurrent.ScheduledThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.redcastlemedia.multitallented.physicalcurrency.PhysicalCurrency;

public class AccountManager {
    private static AccountManager instance = null;
    private static HashMap<UUID, Account> accounts = new HashMap<>();
    private static HashSet<UUID> needSaving = new HashSet<>();
    private static HashSet<ScheduledFuture<?>> futureSaves = new HashSet<>();
    private static HashSet<ScheduledFuture<?>> futureLoads = new HashSet<>();

    public AccountManager() {
        instance = this;
    }

    public static AccountManager getInstance() {
        if (instance == null) {
            new AccountManager();
        }
        return instance;
    }

    public boolean hasAccount(UUID uuid) {
        if (accounts.containsKey(uuid)) {
            return true;
        }
        File playerDataFolder = new File(PhysicalCurrency.getInstance().getDataFolder(), "data");
        if (!playerDataFolder.exists()) {
            return false;
        }
        File playerFile = new File(playerDataFolder, uuid.toString() + ".yml");
        if (!playerFile.exists()) {
            return false;
        }

        loadAccount(uuid, playerFile, true);
        return true;
    }

    public void unloadPlayer(final UUID uuid, boolean lazy) {
        if (!accounts.containsKey(uuid)) {
            return;
        }
        if (needSaving.contains(uuid)) {
            if (lazy) {
                saveLater(uuid);
            } else {
                save(accounts.get(uuid));
            }
        }
        accounts.remove(uuid);
    }

    public void unloadAllPlayers() {
        for (ScheduledFuture future : futureSaves) {
            future.cancel(false);
        }
        for (ScheduledFuture future : futureLoads) {
            future.cancel(false);
        }
        for (UUID uuid : new HashSet<>(accounts.keySet())) {
            unloadPlayer(uuid, false);
        }
    }

    public void setNeedsSaving(UUID uuid) {
        needSaving.add(uuid);
    }

    public void save(Account account) {
        needSaving.remove(account.getUuid());
        File playerDataFolder = new File(PhysicalCurrency.getInstance().getDataFolder(), "data");
        if (!playerDataFolder.exists()) {
            if (!playerDataFolder.mkdir()) {
                PhysicalCurrency.getInstance().getLogger().severe(PhysicalCurrency.getPrefix() +
                        "Unable to create data folder to save!");
                return;
            }
        }
        File playerFile = new File(playerDataFolder, account.getUuid().toString() + ".yml");
        if (!playerFile.exists()) {
            try {
                if (!playerFile.createNewFile()) {
                    PhysicalCurrency.getInstance().getLogger().severe(PhysicalCurrency.getPrefix() +
                            "Unable to create data file " + account.getUuid() + ".yml!");
                    return;
                }
            } catch (Exception e) {
                PhysicalCurrency.getInstance().getLogger().severe(PhysicalCurrency.getPrefix() +
                        "Unable to create data file " + account.getUuid() + ".yml!");
                return;
            }
        }
        FileConfiguration config = new YamlConfiguration();
        config.set("amount", account.getAmount());
        try {
            config.save(playerFile);
        } catch (Exception e) {
            PhysicalCurrency.getInstance().getLogger().severe(PhysicalCurrency.getPrefix() +
                    "Unable to save to file " + account.getUuid() + ".yml!");
            return;
        }
    }

    private void loadAccount(UUID uuid, boolean lazy) {
        loadAccount(uuid, null, lazy);
    }

    private void loadAccount(UUID uuid, final File playerFile, boolean lazy) {
        if (lazy) {
            ScheduledThreadPoolExecutor executor = new ScheduledThreadPoolExecutor(1);
            futureLoads.add(executor.schedule(new Runnable() {
                @Override
                public void run() {
                    if (accounts.containsKey(uuid)) {
                        return;
                    }
                    if (playerFile == null) {

                    }
                    load(playerFile, uuid);
                }
            }, 10, TimeUnit.MILLISECONDS));
        } else {
            File newPlayerFile = playerFile;
            if (playerFile == null) {
                File playerDataFolder = new File(PhysicalCurrency.getInstance().getDataFolder(), "data");
                if (!playerDataFolder.exists()) {
                    return;
                }
                newPlayerFile = new File(playerDataFolder, uuid.toString() + ".yml");
                if (!playerFile.exists()) {
                    return;
                }
            }
            load(newPlayerFile, uuid);
        }
    }

    private void load(File playerFile, UUID uuid) {
        FileConfiguration config = new YamlConfiguration();
        try {
            config.load(playerFile);
            if (accounts.containsKey(uuid)) {
                return;
            }
            accounts.put(uuid, new Account(
                    uuid, config.getDouble("amount", 0)
            ));
        } catch (Exception e) {
            PhysicalCurrency.getInstance().getLogger().severe(PhysicalCurrency.getPrefix() +
                    "Unable to load existing file " + playerFile.getName());
            return;
        }
    }

    private void saveLater(UUID uuid) {
        final Account ACCOUNT = accounts.get(uuid);
        ScheduledThreadPoolExecutor executor = new ScheduledThreadPoolExecutor(1);
        futureSaves.add(executor.schedule(new Runnable() {
            @Override
            public void run() {
                save(ACCOUNT);
                needSaving.remove(uuid);
            }
        }, 10, TimeUnit.MILLISECONDS));
    }

    public void cleanUp() {
        for (ScheduledFuture future : new HashSet<>(futureLoads)) {
            if (future.isDone()) {
                futureLoads.remove(future);
            }
        }
        for (ScheduledFuture future : new HashSet<>(futureSaves)) {
            if (future.isDone()) {
                futureSaves.remove(future);
            }
        }
        for (UUID uuid : new HashSet<>(needSaving)) {
            if (!accounts.containsKey(uuid)) {
                needSaving.remove(uuid);
            } else {
                saveLater(uuid);
                return;
            }
        }
    }

    public Account getAccount(UUID uuid) {
        if (accounts.containsKey(uuid)) {
            loadAccount(uuid, false);
        }
        if (accounts.get(uuid) == null) {
            accounts.put(uuid, new Account(uuid, 0));
            needSaving.add(uuid);
        }
        return accounts.get(uuid);
    }
}
