package org.redcastlemedia.multitallented.physicalcurrency.orders;

import org.bukkit.Bukkit;
import org.redcastlemedia.multitallented.physicalcurrency.PhysicalCurrency;
import org.redcastlemedia.multitallented.physicalcurrency.accounts.AccountManager;

public final class StartSaveThread {
    private static int threadId = -1;
    private StartSaveThread() {

    }

    public static void execute() {
        if (PhysicalCurrency.getInstance() == null) {
            return;
        }
        threadId = Bukkit.getScheduler().scheduleSyncRepeatingTask(PhysicalCurrency.getInstance(), new Runnable() {
            @Override
            public void run() {
                try {
                    AccountManager.getInstance().cleanUp();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }, 80L, 80L);
    }

    public static void checkThread() {
        if (threadId == -1 || !Bukkit.getScheduler().isCurrentlyRunning(threadId)) {
            StartSaveThread.execute();
        }
    }
}
