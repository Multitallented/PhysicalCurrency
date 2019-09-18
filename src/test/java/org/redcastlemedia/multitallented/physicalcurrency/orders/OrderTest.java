package org.redcastlemedia.multitallented.physicalcurrency.orders;

import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;
import org.junit.Test;

import java.util.ArrayList;

import static org.junit.Assert.assertEquals;

public class OrderTest {
    @Test
    public void transferToAccount() {
        ItemStack itemStack = new ItemStack(Material.GOLD_INGOT, 1);
        ArrayList<ItemStack> removeThese = new ArrayList<>();
        int newAmount = TransferPhysicalToAccount.addStackToAmount(itemStack,
                9, 0, 9, removeThese);
        assertEquals(1, removeThese.size());
        assertEquals(9, newAmount);
    }
    @Test
    public void transferToAccount2() {
        ItemStack itemStack = new ItemStack(Material.GOLD_INGOT, 2);
        ArrayList<ItemStack> removeThese = new ArrayList<>();
        int newAmount = TransferPhysicalToAccount.addStackToAmount(itemStack,
                9, 0, 9, removeThese);
        assertEquals(0, removeThese.size());
        assertEquals(1, itemStack.getAmount());
        assertEquals(9, newAmount);
    }
}
