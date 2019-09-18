package org.redcastlemedia.multitallented.physicalcurrency.accounts;

import org.bukkit.OfflinePlayer;
import org.junit.Before;
import org.junit.Test;
import org.redcastlemedia.multitallented.physicalcurrency.commands.Set;
import org.redcastlemedia.multitallented.physicalcurrency.orders.DepositPlayer;
import org.redcastlemedia.multitallented.physicalcurrency.orders.WithdrawPlayer;

import java.util.UUID;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class AccountTransactionTest {
    private UUID uuid;
    private OfflinePlayer offlinePlayer;
    private Account account;

    @Before
    public void setup() {
        this.uuid = new UUID(1, 4);
        this.account = new Account(uuid, 10);
        AccountManager.accounts.put(uuid, account);
        this.offlinePlayer = mock(OfflinePlayer.class);
        when(this.offlinePlayer.getUniqueId()).thenReturn(this.uuid);
        when(this.offlinePlayer.isOnline()).thenReturn(false);
    }

    @Test
    public void withdrawShouldSetCorrectAmount() {
        this.account.setAmount(10);
        DepositPlayer.execute(this.offlinePlayer, 10);
        assertEquals(20, account.getAmount(), 0.1);
    }

    @Test
    public void depositShouldSetCorrectAmount() {
        this.account.setAmount(10);
        WithdrawPlayer.execute(this.offlinePlayer, 10);
        assertEquals(0, account.getAmount(), 0.1);
    }
}
