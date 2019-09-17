package org.redcastlemedia.multitallented.physicalcurrency.orders;

import org.redcastlemedia.multitallented.physicalcurrency.ConfigManager;

import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;
import java.text.NumberFormat;

public final class Format {
    private Format() {

    }
    public static String execute(double v) {
        StringBuilder stringBuilder = new StringBuilder(ConfigManager.getInstance().getPrefix());
        NumberFormat nf = NumberFormat.getNumberInstance();
        DecimalFormat numberFormat = (DecimalFormat) nf;
        numberFormat.setMinimumFractionDigits(ConfigManager.getInstance().getNumberOfDecimalPlaces());
        DecimalFormatSymbols decimalFormatSymbols = new DecimalFormatSymbols();
        decimalFormatSymbols.setDecimalSeparator(ConfigManager.getInstance().getDecimal());
        decimalFormatSymbols.setGroupingSeparator(ConfigManager.getInstance().getSeparator());
        numberFormat.setDecimalFormatSymbols(decimalFormatSymbols);
        stringBuilder.append(numberFormat.format(v));
        stringBuilder.append(ConfigManager.getInstance().getSuffix());
        return stringBuilder.toString();
    }
}
