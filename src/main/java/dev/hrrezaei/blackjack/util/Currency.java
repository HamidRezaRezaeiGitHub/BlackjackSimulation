package dev.hrrezaei.blackjack.util;

import java.text.NumberFormat;
import java.util.Locale;

public class Currency {

    public static final NumberFormat currencyFormatter = NumberFormat.getNumberInstance(Locale.US);

    public static String format(long amount) {
        return currencyFormatter.format(amount) + "$";
    }

}
