package org.redcastlemedia.multitallented.physicalcurrency;

import net.milkbowl.vault.economy.Economy;
import net.milkbowl.vault.economy.EconomyResponse;

import org.bukkit.Bukkit;
import org.bukkit.OfflinePlayer;
import org.redcastlemedia.multitallented.physicalcurrency.accounts.AccountManager;
import org.redcastlemedia.multitallented.physicalcurrency.orders.DepositPlayer;
import org.redcastlemedia.multitallented.physicalcurrency.orders.Format;
import org.redcastlemedia.multitallented.physicalcurrency.orders.HasAccount;
import org.redcastlemedia.multitallented.physicalcurrency.orders.WithdrawPlayer;

import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;
import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.List;

public class PCurrEconomy implements Economy {

    @Override
    public boolean isEnabled() {
        return PhysicalCurrency.getInstance() != null;
    }

    @Override
    public String getName() {
        return PhysicalCurrency.getInstance().getName();
    }

    @Override
    public boolean hasBankSupport() {
        return false;
    }

    @Override
    public int fractionalDigits() {
        return 2;
    }

    @Override
    public String format(double v) {
        return Format.execute(v);
    }

    @Override
    public String currencyNamePlural() {
        return ConfigManager.getInstance().getNamePlural();
    }

    @Override
    public String currencyNameSingular() {
        return ConfigManager.getInstance().getNameSingular();
    }

    @Override
    public boolean hasAccount(String s) {
        return HasAccount.execute(s);
    }

    @Override
    public boolean hasAccount(OfflinePlayer offlinePlayer) {
        return HasAccount.execute(offlinePlayer);
    }

    @Override
    public boolean hasAccount(String s, String worldName) {
        return HasAccount.execute(s);
    }

    @Override
    public boolean hasAccount(OfflinePlayer offlinePlayer, String worldName) {
        return HasAccount.execute(offlinePlayer);
    }

    @Override
    public double getBalance(String s) {
        OfflinePlayer offlinePlayer = Bukkit.getOfflinePlayer(s);
        return AccountManager.getInstance().getAccount(offlinePlayer.getUniqueId()).getAmount();
    }

    @Override
    public double getBalance(OfflinePlayer offlinePlayer) {
        return AccountManager.getInstance().getAccount(offlinePlayer.getUniqueId()).getAmount();
    }

    @Override
    public double getBalance(String s, String worldName) {
        return getBalance(s);
    }

    @Override
    public double getBalance(OfflinePlayer offlinePlayer, String worldName) {
        return getBalance(offlinePlayer);
    }

    @Override
    public boolean has(String s, double v) {
        OfflinePlayer offlinePlayer = Bukkit.getOfflinePlayer(s);
        return AccountManager.getInstance().getAccount(offlinePlayer.getUniqueId()).getAmount() >= v;
    }

    @Override
    public boolean has(OfflinePlayer offlinePlayer, double v) {
        return AccountManager.getInstance().getAccount(offlinePlayer.getUniqueId()).getAmount() >= v;
    }

    @Override
    public boolean has(String s, String worldName, double v) {
        return has(s, v);
    }

    @Override
    public boolean has(OfflinePlayer offlinePlayer, String s, double v) {
        return has(offlinePlayer, v);
    }

    @Override
    public EconomyResponse withdrawPlayer(String s, double v) {
        return WithdrawPlayer.execute(s, v);
    }

    @Override
    public EconomyResponse withdrawPlayer(OfflinePlayer offlinePlayer, double v) {
        return WithdrawPlayer.execute(offlinePlayer, v);
    }

    @Override
    public EconomyResponse withdrawPlayer(String s, String worldName, double v) {
        return WithdrawPlayer.execute(s, v);
    }

    @Override
    public EconomyResponse withdrawPlayer(OfflinePlayer offlinePlayer, String worldName, double v) {
        return WithdrawPlayer.execute(offlinePlayer, v);
    }

    @Override
    public EconomyResponse depositPlayer(String s, double v) {
        return DepositPlayer.execute(s, v);
    }

    @Override
    public EconomyResponse depositPlayer(OfflinePlayer offlinePlayer, double v) {
        return DepositPlayer.execute(offlinePlayer, v);
    }

    @Override
    public EconomyResponse depositPlayer(String s, String s1, double v) {
        return DepositPlayer.execute(s, v);
    }

    @Override
    public EconomyResponse depositPlayer(OfflinePlayer offlinePlayer, String s, double v) {
        return DepositPlayer.execute(offlinePlayer, v);
    }

    @Override
    public EconomyResponse createBank(String s, String s1) {
        return new EconomyResponse(0, 0, EconomyResponse.ResponseType.NOT_IMPLEMENTED,
                "Banks aren't implemented");
    }

    @Override
    public EconomyResponse createBank(String s, OfflinePlayer offlinePlayer) {
        return new EconomyResponse(0, 0, EconomyResponse.ResponseType.NOT_IMPLEMENTED,
                "Banks aren't implemented");
    }

    @Override
    public EconomyResponse deleteBank(String s) {
        return new EconomyResponse(0, 0, EconomyResponse.ResponseType.NOT_IMPLEMENTED,
                "Banks aren't implemented");
    }

    @Override
    public EconomyResponse bankBalance(String s) {
        return new EconomyResponse(0, 0, EconomyResponse.ResponseType.NOT_IMPLEMENTED,
                "Banks aren't implemented");
    }

    @Override
    public EconomyResponse bankHas(String s, double v) {
        return new EconomyResponse(0, 0, EconomyResponse.ResponseType.NOT_IMPLEMENTED,
                "Banks aren't implemented");
    }

    @Override
    public EconomyResponse bankWithdraw(String s, double v) {
        return new EconomyResponse(0, 0, EconomyResponse.ResponseType.NOT_IMPLEMENTED,
                "Banks aren't implemented");
    }

    @Override
    public EconomyResponse bankDeposit(String s, double v) {
        return new EconomyResponse(0, 0, EconomyResponse.ResponseType.NOT_IMPLEMENTED,
                "Banks aren't implemented");
    }

    @Override
    public EconomyResponse isBankOwner(String s, String s1) {
        return new EconomyResponse(0, 0, EconomyResponse.ResponseType.NOT_IMPLEMENTED,
                "Banks aren't implemented");
    }

    @Override
    public EconomyResponse isBankOwner(String s, OfflinePlayer offlinePlayer) {
        return new EconomyResponse(0, 0, EconomyResponse.ResponseType.NOT_IMPLEMENTED,
                "Banks aren't implemented");
    }

    @Override
    public EconomyResponse isBankMember(String s, String s1) {
        return new EconomyResponse(0, 0, EconomyResponse.ResponseType.NOT_IMPLEMENTED,
                "Banks aren't implemented");
    }

    @Override
    public EconomyResponse isBankMember(String s, OfflinePlayer offlinePlayer) {
        return new EconomyResponse(0, 0, EconomyResponse.ResponseType.NOT_IMPLEMENTED,
                "Banks aren't implemented");
    }

    @Override
    public List<String> getBanks() {
        return new ArrayList<>();
    }

    @Override
    public boolean createPlayerAccount(String s) {
        OfflinePlayer offlinePlayer = Bukkit.getOfflinePlayer(s);
        AccountManager.getInstance().getAccount(offlinePlayer.getUniqueId());
        return true;
    }

    @Override
    public boolean createPlayerAccount(OfflinePlayer offlinePlayer) {
        AccountManager.getInstance().getAccount(offlinePlayer.getUniqueId());
        return true;
    }

    @Override
    public boolean createPlayerAccount(String s, String worldName) {
        OfflinePlayer offlinePlayer = Bukkit.getOfflinePlayer(s);
        AccountManager.getInstance().getAccount(offlinePlayer.getUniqueId());
        return true;
    }

    @Override
    public boolean createPlayerAccount(OfflinePlayer offlinePlayer, String worldName) {
        AccountManager.getInstance().getAccount(offlinePlayer.getUniqueId());
        return true;
    }
}
