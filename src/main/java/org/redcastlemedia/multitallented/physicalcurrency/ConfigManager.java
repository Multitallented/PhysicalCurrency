package org.redcastlemedia.multitallented.physicalcurrency;

import java.util.List;

import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.inventory.ItemStack;
import org.redcastlemedia.multitallented.physicalcurrency.util.ItemUtil;

import lombok.Getter;

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
    @Getter
    private String singleMaterialString;
    @Getter
    private String singleName;
    @Getter
    private List<String> singleLore;
    @Getter
    private String nineMaterialString;
    @Getter
    private String nineName;
    @Getter
    private List<String> nineLore;
    @Getter
    private String eightyOneMaterialString;
    @Getter
    private String eightyOneName;
    @Getter
    private List<String> eightyOneLore;
    @Getter
    private boolean preventInteract;
    @Getter
    private boolean preventPlacing;

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

    public void loadConfig() {
        if (PhysicalCurrency.getInstance() == null) {
            return;
        }
        FileConfiguration config = PhysicalCurrency.getInstance().getConfig();
        String format = config.getString("format", "$1,000.00");
        nameSingular = config.getString("name-singular", "coin");
        namePlural = config.getString("name-plural", "coins");
        preventInteract = config.getBoolean("prevent-interaction", false);
        preventPlacing = config.getBoolean("prevent-placing", false);

        singleMaterialString = config.getString("single-currency.material", "GOLD_NUGGET");
        singleName = config.getString("single-currency.name", null);
        singleLore = config.getStringList("single-currency.lore");
        singleItem = ItemUtil.processItem(singleMaterialString, singleName, singleLore);

        nineMaterialString = config.getString("nine-currency.material", "GOLD_INGOT");
        nineName = config.getString("nine-currency.name", null);
        nineLore = config.getStringList("nine-currency.lore");
        nineItem = ItemUtil.processItem(nineMaterialString, nineName, nineLore);

        eightyOneMaterialString = config.getString("eighty-one-currency.material", "GOLD_BLOCK");
        eightyOneName = config.getString("eighty-one-currency.name", null);
        eightyOneLore = config.getStringList("eighty-one-currency.lore");
        eightyOneItem = ItemUtil.processItem(eightyOneMaterialString, eightyOneName, eightyOneLore);
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
