package org.redcastlemedia.multitallented.physicalcurrency.orders;

import org.bukkit.Bukkit;
import org.bukkit.NamespacedKey;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.ShapedRecipe;
import org.redcastlemedia.multitallented.physicalcurrency.ConfigManager;

public final class CreateCustomRecipe {
    private static final String SINGLE_CURRENCY_KEY = "PCURR_SINGLE_CURR";
    private static final String NINE_CURRENCY_KEY = "PCURR_NINE_CURR";
    private static final String EIGHTY_ONE_CURRENCY_KEY = "PCURR_EIGHTY_ONE_CURR";
    private CreateCustomRecipe() {

    }
    public static void execute() {
        ItemStack singleItem = ConfigManager.getInstance().getSingleItem();

        ItemStack nineItem = ConfigManager.getInstance().getNineItem();
        ShapedRecipe nineCurrency = new ShapedRecipe(NamespacedKey.minecraft(NINE_CURRENCY_KEY), nineItem);

        ItemStack eightyOneItem = ConfigManager.getInstance().getEightyOneItem();
        ShapedRecipe eightyOneCurrency = new ShapedRecipe(NamespacedKey.minecraft(EIGHTY_ONE_CURRENCY_KEY), eightyOneItem);

        nineCurrency.shape("***", "***", "***");
        eightyOneCurrency.shape("***", "***", "***");

        nineCurrency.setIngredient('*', singleItem.getData());
        nineCurrency.setIngredient('*', nineItem.getData());

        addRecipe(singleItem, nineItem, nineCurrency);
        addRecipe(nineItem, eightyOneItem, eightyOneCurrency);
    }
    private static void addRecipe(ItemStack stack1, ItemStack stack2, ShapedRecipe recipe) {
        if (stack1.getItemMeta() != null &&
                stack1.getItemMeta().getLore() != null &&
                !stack1.getItemMeta().getLore().isEmpty() &&
                stack2.getItemMeta() != null &&
                stack2.getItemMeta().getLore() != null &&
                !stack2.getItemMeta().getLore().isEmpty() &&
                Bukkit.getServer().getRecipesFor(stack2).isEmpty()) {
            Bukkit.getServer().addRecipe(recipe);
        }
    }
}
