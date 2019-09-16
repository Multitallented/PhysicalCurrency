package org.redcastlemedia.multitallented.physicalcurrency;

import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.inventory.ItemStack;
import org.redcastlemedia.multitallented.physicalcurrency.util.ItemUtil;

import lombok.Getter;
import lombok.Setter;

public class ConfigManager {
    private static ConfigManager instance = null;

    @Getter
    protected String prefix = "$";
    @Getter
    protected String suffix = "";
    @Getter
    protected char separator = ',';
    @Getter
    protected char decimal = '.';
    @Getter
    protected int numberOfDecimalPlaces = 2;
    @Getter
    protected String nameSingular = "coin";
    @Getter
    protected String namePlural = "coins";
    @Getter
    private ItemStack singleItem;
    @Getter
    private ItemStack nineItem;
    @Getter
    private ItemStack eightyOneItem;

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
        singleItem = ItemUtil.processItem(config.getString("single-currency.material", "GOLD_NUGGET"),
                config.getString("single-currency.name", null),
                config.getStringList("single-currency.lore"));
        nineItem = ItemUtil.processItem(config.getString("nine-currency.material", "GOLD_INGOT"),
                config.getString("nine-currency.name", null),
                config.getStringList("nine-currency.lore"));
        eightyOneItem = ItemUtil.processItem(config.getString("eighty-one-currency.material", "GOLD_BLOCK"),
                config.getString("eighty-one-currency.name", null),
                config.getStringList("eighty-one-currency.lore"));
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
