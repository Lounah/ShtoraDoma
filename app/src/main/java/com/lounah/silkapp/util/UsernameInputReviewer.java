package com.lounah.silkapp.util;

import android.support.annotation.NonNull;

import java.util.Arrays;
import java.util.List;

public class UsernameInputReviewer {

    private static final List<CharSequence> bannedChars =
            Arrays.asList("!", "@", "#", "№", "$", "%", ":", "^",
                    ",", "&", ".", "*", ";", "(", ")", "_",
                    "-", "+", "=", "±", "§", ">", "<", "/",
                    "?", "'", "|");

    private UsernameInputReviewer() {}

    public static boolean review(@NonNull final CharSequence input) {
        return (input.length() > 1) && (!textContainsAtLeastOneOf(input, bannedChars));
    }

    private static boolean textContainsAtLeastOneOf(CharSequence text, List<CharSequence> bannedChars) {
        boolean result = false;
        for (CharSequence bannedChar : bannedChars) {
            if (text.toString().contains(bannedChar)) result = true;
        }
        return result;
    }
}
