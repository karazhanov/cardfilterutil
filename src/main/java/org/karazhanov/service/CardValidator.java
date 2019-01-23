package org.karazhanov.service;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * @author karazhanov on 23.01.19.
 */
public class CardValidator {

    private final Pattern pattern = Pattern.compile("((?:(?:\\d{4}[- ]){3}\\d{4}|\\d{16}))(?![\\d])");

    public boolean containValidCardNumber(String line) {
        Matcher matcher = pattern.matcher(line);
        if (matcher.find()) {
            return isNumberValid(matcher.group(0));
        }
        return false;
    }

    private boolean isNumberValid(String number) {
        String clearNumber = number.replaceAll("[^a-zA-Z0-9\\s]", "");
        int sum = 0;
        boolean alternate = false;
        for (int i = clearNumber.length() - 1; i >= 0; i--) {
            int n = Integer.parseInt(clearNumber.substring(i, i + 1));
            if (alternate) {
                n *= 2;
                if (n > 9) {
                    n = (n % 10) + 1;
                }
            }
            sum += n;
            alternate = !alternate;
        }
        return (sum % 10 == 0);
    }
}
