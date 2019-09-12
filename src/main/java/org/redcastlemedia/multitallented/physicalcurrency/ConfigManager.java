package org.redcastlemedia.multitallented.physicalcurrency;

import org.bukkit.configuration.file.FileConfiguration;

import lombok.Getter;
import lombok.Setter;

public class ConfigManager {
    private static ConfigManager instance = null;

    @Getter @Setter
    protected String prefix = "$";
    @Getter @Setter
    protected String suffix = "";
    @Getter @Setter
    protected char separator = ',';
    @Getter @Setter
    protected char decimal = '.';
    @Getter @Setter
    protected int numberOfDecimalPlaces = 2;
    @Getter @Setter
    protected String nameSingular = "coin";
    @Getter @Setter
    protected String namePlural = "coins";

    public static ConfigManager getInstance() {
        if (instance == null) {
            new ConfigManager();
        }
        return instance;
    }

    public ConfigManager() {
        instance = this;
        loadConfig();
    }

    private void loadConfig() {
        if (PhysicalCurrency.getInstance() == null) {
            return;
        }
        FileConfiguration config = PhysicalCurrency.getInstance().getConfig();
        String format = config.getString("format", "$1,000.00");
        nameSingular = config.getString("name-singular", "coin");
        namePlural = config.getString("name-plural", "coins");
        parseFormat(format);
    }

    void parseFormat(String format) {
        prefix = format.split("1")[0];
        suffix = format.substring(format.lastIndexOf("0") + 1);
        separator = format.charAt(format.indexOf("1") + 1);
        int decimalIndex = format.indexOf("1" + separator + "000") + 5;
        int tenthIndex = decimalIndex + 1;
        decimal = format.charAt(decimalIndex);
        numberOfDecimalPlaces = format.substring(tenthIndex).replaceAll("\\" + separator,"")
                .replace(suffix, "").length();
    }
}
