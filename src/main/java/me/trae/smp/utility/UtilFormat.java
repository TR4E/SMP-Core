package me.trae.smp.utility;

import org.apache.commons.lang.WordUtils;

public class UtilFormat {

    public static String cleanString(final String string) {
        return WordUtils.capitalizeFully(string.replaceAll("_", " ")).replaceAll("_", " ");
    }
}