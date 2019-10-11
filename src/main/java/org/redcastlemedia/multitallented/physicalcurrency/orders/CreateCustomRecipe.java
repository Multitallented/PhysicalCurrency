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
    private static final String NINE_CURRENCY_BREAK_KEY = "pcurrninecurrbreak";
    private static final String EIGHTY_ONE_CURRENCY_KEY = "pcurreightyonecurr";
    private CreateCustomRecipe() {

    }
    public static void execute() {
        ItemStack singleItem = ConfigManager.getInstance().getSingleItem();
        ItemStack nineItem = ConfigManager.getInstance().getNineItem();
        ItemStack eightyOneItem = ConfigManager.getInstance().getEightyOneItem();

        ShapedRecipe nineRecipe = new ShapedRecipe(new NamespacedKey(PhysicalCurrency.getInstance(), NINE_CURRENCY_KEY), nineItem);
        nineRecipe.shape("EEE", "EEE", "EEE");
        nineRecipe.setIngredient('E', singleItem.getType());
        addRecipe(singleItem, nineItem, nineRecipe);

        ShapedRecipe eightyOneRecipe = new ShapedRecipe(new NamespacedKey(PhysicalCurrency.getInstance(), EIGHTY_ONE_CURRENCY_KEY), eightyOneItem);
        eightyOneRecipe.shape("EEE", "EEE", "EEE");
        eightyOneRecipe.setIngredient('E', nineItem.getType());
        addRecipe(nineItem, eightyOneItem, eightyOneRecipe);

        ItemStack nineBreakItem = new ItemStack(nineItem);
        nineBreakItem.setAmount(9);
        ShapedRecipe nineCurrencyBreak = new ShapedRecipe(new NamespacedKey(PhysicalCurrency.getInstance(), NINE_CURRENCY_BREAK_KEY), nineBreakItem);
        nineCurrencyBreak.shape("E");
        nineCurrencyBreak.setIngredient('E', eightyOneItem.getType());
        addRecipe(nineItem, eightyOneItem, nineCurrencyBreak);

        ItemStack singleBreakItem = new ItemStack(singleItem);
        singleBreakItem.setAmount(9);
        ShapedRecipe singleCurrencyBreak = new ShapedRecipe(new NamespacedKey(PhysicalCurrency.getInstance(), SINGLE_CURRENCY_KEY), singleBreakItem);
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
