package org.deiverbum.app.util;

/*
 * The MIT License
 *
 * Copyright 2017 Leo Aso.
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in
 * all copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN
 * THE SOFTWARE.
 */

import java.util.Arrays;
import java.util.List;

/**
 * Utility for converting integers to English numerals or ordinals.
 *
 * @author Leo Aso
 */
public final class Numerals {
    private static final String[] HIGHER_NAMES = {"billion", "million", "thousand"};
    private static final int[] HIGHER_BASES = {1_000_000_000, 1_000_000, 1000};
    private static final List<String> ORDINALS = Arrays.asList("",
            "first", "second", "third", "fourth", "fifth", "sixth",
            "seventh", "eighth", "nineth", "tenth", "eleventh",
            "twelfth", "thirteenth", "fourteenth", "fifteenth",
            "sixteenth", "seventeenth", "eighteenth", "nineteenth",
            "twentieth", "thirtieth", "fortieth", "fiftieth",
            "sixtieth", "seventieth", "eightieth", "ninetieth"
    );
    private static final List<String> ORDINALS_ES = Arrays.asList("",
            "primera", "segunda", "tercera", "cuarta", "quinta", "sexta",
            "séptima", "octava", "novena", "décima", "undécima",
            "duodécima", "décimo tercera", "décimo cuarta", "décimo quinta",
            "décimo sexta", "décimo séptima", "décimo octava", "décimo novena",
            "vigésima", "trigésima", "cuadragésima", "quincuagésima",
            "sexagésima", "septuagésima", "octogésima", "nonagésima"
    );
    private static final List<String> NUMERALS = Arrays.asList("",
            "one", "two", "three", "four", "five", "six", "seven",
            "eight", "nine", "ten", "eleven", "twelve", "thirteen",
            "fourteen", "fifteen", "sixteen", "seventeen",
            "eighteen", "nineteen", "twenty", "thirty", "forty",
            "fifty", "sixty", "seventy", "eighty", "ninety"
    );
    private static final List<String> NUMERALS_ES = Arrays.asList("",
            "one", "two", "three", "four", "five", "six", "seven",
            "eight", "nine", "ten", "eleven", "twelve", "thirteen",
            "fourteen", "fifteen", "sixteen", "seventeen",
            "eighteen", "nineteen", "vigésimo", "trigésimo", "forty",
            "fifty", "sixty", "seventy", "eighty", "ninety"
    );
    private final boolean useAnd;
    private final boolean useCommas;
    private final boolean useDashes;

    /**
     * Creates a new instance. Equivalent to
     * {@code new Numerals(true, true, true)}.
     */
    public Numerals() {
        this(true, true, true);
    }

    /**
     * Creates a new numerals.
     *
     * @param useAnd    whether or not to print 'and' before tens and units
     *                  i.e. {@code one thousand and five} vs {@code one thousand five}.
     * @param useCommas whether or not to print 'commas' after
     *                  thousand, million, and billion.
     * @param useDashes whether or not to use dashes between tens and units
     *                  i.e. {@code forty-two} vs {@code forty two}.
     */
    public Numerals(boolean useAnd,
                    boolean useCommas,
                    boolean useDashes) {
        this.useAnd = useAnd;
        this.useCommas = useCommas;
        this.useDashes = useDashes;
    }

    /**
     * Converts the number to words in English.
     *
     * @param number the number to convert.
     * @return the generated numeral.
     */
    public String toWords(int number) {
        return toWords(number, false);
    }

    /**
     * Converts the number to words in English.
     *
     * @param number  the number to convert.
     * @param ordinal if true, generates an ordinal (first, forty-third);
     *                if false, generates a regular numeral (one, forty-two).
     * @return the generated numeral/ordinal.
     */
    public String toWords(int number, boolean ordinal) {
        if (number == 0) return ordinal ? "zeroth" : "zero";

        StringBuilder sb = new StringBuilder();

        for (int i = 0; i < HIGHER_BASES.length; i++) {
            int base = HIGHER_BASES[i];
            if (number < base) continue;

            sb.append(wordsBelow1000(number / base, false));
            sb.append(' ').append(HIGHER_NAMES[i]);
            number = number % base;
            if (number == 0) {
                if (ordinal) sb.append("th");
                return sb.toString();
            } else if (number >= 100) {
                sb.append(useCommas ? ", " : " ");
            } else {
                sb.append(useAnd ? " and " : " ");
                break;
            }
        }

        sb.append(wordsBelow1000(number, ordinal));
        return sb.toString();
    }

    private String wordsBelow1000(int number, boolean useOrdinals) {
        if (number == 0) return "";
        StringBuilder sb = new StringBuilder();

        if (number >= 100) {
            int h = number / 100 % 10;
            sb.append(NUMERALS_ES.get(h));
            sb.append(" hundred");
            number = number % 100;

            if (number == 0) {
                if (useOrdinals)
                    sb.append("th");
                return sb.toString();
            }

            if (useAnd) sb.append(" and ");
            else sb.append(" ");
        }

        List<String> words = useOrdinals ? ORDINALS_ES : NUMERALS_ES;
        if (number < 20) return sb.append(words.get(number)).toString();

        if (number % 10 == 0) {
            String word = words.get(18 + number / 10);
            return sb.append(word).toString();
        }

        sb.append(NUMERALS_ES.get(18 + number / 10));
        sb.append(useDashes ? '-' : ' ');
        sb.append(words.get(number % 10));
        return sb.toString();
    }
}