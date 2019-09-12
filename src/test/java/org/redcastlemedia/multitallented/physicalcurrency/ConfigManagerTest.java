package org.redcastlemedia.multitallented.physicalcurrency;

import static org.junit.Assert.assertEquals;

import org.junit.Test;

public class ConfigManagerTest {

    @Test
    public void formatShouldWorkProperly() {
        ConfigManager.getInstance().parseFormat("#1.000,000.00$");
        assertEquals("#", ConfigManager.getInstance().prefix);
        assertEquals("$", ConfigManager.getInstance().suffix);
        assertEquals('.', ConfigManager.getInstance().separator);
        assertEquals(',', ConfigManager.getInstance().decimal);
        assertEquals(5, ConfigManager.getInstance().numberOfDecimalPlaces);

        PCurrEconomy pCurrEconomy = new PCurrEconomy();
        assertEquals("#5,00000$", pCurrEconomy.format(5));
        assertEquals("#5.000,00000$", pCurrEconomy.format(5000));
        ConfigManager.getInstance().parseFormat("$1,000.00");
    }
}
