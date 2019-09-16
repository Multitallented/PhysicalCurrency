package org.redcastlemedia.multitallented.physicalcurrency.accounts;

import java.util.UUID;

import lombok.Getter;

public class Account {
    @Getter
    private UUID uuid;

    @Getter
    private double amount;

    public Account(UUID uuid, double amount) {
        this.uuid = uuid;
        this.amount = amount;
    }

    public void setAmount(double amount) {
        setAmount(amount, true);
    }

    public void setAmount(double amount, boolean lazy) {
        this.amount = amount;
        if (lazy) {
            AccountManager.getInstance().setNeedsSaving(this.uuid);
        } else {
            AccountManager.getInstance().save(this);
        }
    }
}
