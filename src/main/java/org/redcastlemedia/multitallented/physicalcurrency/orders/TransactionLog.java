package org.redcastlemedia.multitallented.physicalcurrency.orders;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.UUID;

import org.bukkit.OfflinePlayer;
import org.redcastlemedia.multitallented.physicalcurrency.PhysicalCurrency;
import org.redcastlemedia.multitallented.physicalcurrency.accounts.AccountManager;

public final class TransactionLog {
    private TransactionLog() {

    }
    public static void execute(OfflinePlayer offlinePlayer, double amount, boolean withdraw) {
        File pCurrFolder = PhysicalCurrency.getInstance().getDataFolder();
        if (!pCurrFolder.exists()) {
            if (!pCurrFolder.mkdir()) {
                PhysicalCurrency.getInstance().getLogger().severe("Unable to create PhysicalCurrency folder");
                return;
            }
        }
        File dataFolder = new File(pCurrFolder, "data");
        if (!dataFolder.exists()) {
            if (!dataFolder.mkdir()) {
                PhysicalCurrency.getInstance().getLogger().severe("Unable to create data folder");
                return;
            }
        }
        File playerFile = new File(dataFolder, offlinePlayer.getUniqueId().toString() + ".yml");
        if (!playerFile.exists()) {
            try {
                if (!playerFile.createNewFile()) {
                    PhysicalCurrency.getInstance().getLogger().severe("Unable to create file " + playerFile.getName());
                    return;
                }
            } catch (Exception e) {
                PhysicalCurrency.getInstance().getLogger().severe("Unable to create file " + playerFile.getName());
                return;
            }
        }
        try {
            BufferedWriter bufferedWriter = new BufferedWriter(new FileWriter(playerFile));
            Date date = new Date();
            DateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
            String line = "[" + dateFormat.format(date) + "] ";
            if (withdraw) {
                line += "Withdrew " + Format.execute(amount) + " from";
            } else {
                line += "Deposited " + Format.execute(amount) + " to";
            }
            line += " player. New balance: " + Format.execute(GetBalance.execute(offlinePlayer));
            bufferedWriter.write(line);
            bufferedWriter.close();
        } catch (Exception e) {
            e.printStackTrace();
            PhysicalCurrency.getInstance().getLogger().severe("Unable to save player file " + playerFile.getName());
            return;
        }
    }
}
