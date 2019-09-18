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
    @Test
    public void transferToAccount3() {
        ItemStack itemStack = new ItemStack(Material.GOLD_INGOT, 2);
        ArrayList<ItemStack> removeThese = new ArrayList<>();
        int newAmount = TransferPhysicalToAccount.addStackToAmount(itemStack,
                9.1, 0, 9, removeThese);
        assertEquals(1, removeThese.size());
        assertEquals(18, newAmount);
    }
    @Test
    public void transferToAccount4() {
        ItemStack itemStack = new ItemStack(Material.GOLD_INGOT, 5);
        ArrayList<ItemStack> removeThese = new ArrayList<>();
        int newAmount = TransferPhysicalToAccount.addStackToAmount(itemStack,
                30.2, 10, 9, removeThese);
        assertEquals(0, removeThese.size());
        assertEquals(2, itemStack.getAmount());
        assertEquals(27, newAmount);
    }
}
