package de.example.core;

import java.awt.GraphicsEnvironment;

/** This type allows checking the installed fonts. */
public class FontChecker {

    //: SECTION: - ATTRIBUTES

    public static final String SF_MONO_FONT_NAME = "SF Mono";
    public static final String COURIER_NEW_FONT_NAME = "Courier New";

    //: SECTION: - METHODS

    /**
     * This method checks whether the font with the given name is available on the system.
     *
     * @param fontName The font to be checked
     * @return {@code true} if the font is available, otherwise {@code false}
     */
    public boolean isFontAvailable(String fontName) {
        GraphicsEnvironment ge = GraphicsEnvironment.getLocalGraphicsEnvironment();
        String[] availableFonts = ge.getAvailableFontFamilyNames();

        for (String font : availableFonts)
            if (font.equalsIgnoreCase(fontName))
                return true;

        return false;
    }
}
