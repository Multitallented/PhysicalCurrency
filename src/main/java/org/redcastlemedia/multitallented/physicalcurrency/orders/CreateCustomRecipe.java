package org.redcastlemedia.multitallented.physicalcurrency.orders;

import org.bukkit.Bukkit;
import org.bukkit.NamespacedKey;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.ShapedRecipe;
import org.redcastlemedia.multitallented.physicalcurrency.ConfigManager;
import org.redcastlemedia.multitallented.physicalcurrency.PhysicalCurrency;

public final class CreateCustomRecipe {
    private static final String SINGLE_CURRENCY_KEY = "pcurrsinglecurr";
    private static final String NINE_CURRENCY_KEY = "pcurrninecurr";
    private static final String NINE_CURRENCY_BREAK_KEY = "pcurrninecurr";
    private static final String EIGHTY_ONE_CURRENCY_KEY = "pcurreightyonecurr";
    private CreateCustomRecipe() {

    }
    public static void execute() {
        ItemStack singleItem = ConfigManager.getInstance().getSingleItem();
        ItemStack nineItem = ConfigManager.getInstance().getNineItem();
        ItemStack eightyOneItem = ConfigManager.getInstance().getEightyOneItem();


        ShapedRecipe nineCurrency = new ShapedRecipe(NamespacedKey.minecraft(NINE_CURRENCY_KEY), nineItem);
        nineCurrency.shape("EEE", "EEE", "EEE");
        nineCurrency.setIngredient('E', singleItem.getType());
        addRecipe(singleItem, nineItem, nineCurrency);

        ShapedRecipe eightyOneCurrency = new ShapedRecipe(NamespacedKey.minecraft(EIGHTY_ONE_CURRENCY_KEY), eightyOneItem);
        eightyOneCurrency.shape("EEE", "EEE", "EEE");
        eightyOneCurrency.setIngredient('E', nineItem.getType());
        addRecipe(nineItem, eightyOneItem, eightyOneCurrency);

        ItemStack nineItemBreak = new ItemStack(nineItem);
        nineItemBreak.setAmount(9);
        ShapedRecipe nineCurrencyBreak = new ShapedRecipe(NamespacedKey.minecraft(NINE_CURRENCY_BREAK_KEY), nineItemBreak);
        nineCurrencyBreak.shape("E");
        nineCurrencyBreak.setIngredient('E', eightyOneItem.getType());
        addRecipe(nineItem, eightyOneItem, nineCurrencyBreak);

        ItemStack singleItemBreak = new ItemStack(singleItem);
        singleItemBreak.setAmount(9);
        ShapedRecipe singleCurrencyBreak = new ShapedRecipe(NamespacedKey.minecraft(SINGLE_CURRENCY_KEY), nineItemBreak);
        singleCurrencyBreak.shape("E");
        singleCurrencyBreak.setIngredient('E', nineItem.getType());
        addRecipe(singleItem, nineItem, singleCurrencyBreak);
    }
    private static void addRecipe(ItemStack stack1, ItemStack stack2, ShapedRecipe recipe) {
        if (stack1.getItemMeta() != null &&
                stack1.getItemMeta().getLore() != null &&
                !stack1.getItemMeta().getLore().isEmpty() &&
                stack2.getItemMeta() != null &&
                stack2.getItemMeta().getLore() != null &&
                !stack2.getItemMeta().getLore().isEmpty() &&
                Bukkit.getServer().getRecipesFor(stack2).isEmpty()) {
            try {
                Bukkit.getServer().addRecipe(recipe);
            } catch (Exception npe) {
                npe.printStackTrace();
                PhysicalCurrency.getInstance().getLogger().severe(PhysicalCurrency.getPrefix() +
                        "Unable to add custom recipe!");
            }
        }
    }
}
