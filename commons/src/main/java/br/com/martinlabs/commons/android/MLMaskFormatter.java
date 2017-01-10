package br.com.martinlabs.commons.android;

import java.util.ArrayList;

/**
 * Created by gil on 3/17/16.
 */
public class MLMaskFormatter {

    //////////////////////////////////////////////////////////////////////////////////////
    // PRIVATE CONSTANTS
    //////////////////////////////////////////////////////////////////////////////////////

    private static final char DIGIT_KEY = '#';
    private static final char LITERAL_KEY = '\'';
    private static final char UPPERCASE_KEY = 'U';
    private static final char LOWERCASE_KEY = 'L';
    private static final char ALPHA_NUMERIC_KEY = 'A';
    private static final char CHARACTER_KEY = '?';
    private static final char ANYTHING_KEY = '*';
    private static final char HEX_KEY = 'H';

    private static final MaskCharacter[] EMPTY_MASK_CHARS = new MaskCharacter[0];

    //////////////////////////////////////////////////////////////////////////////////////
    // PRIVATE MEMBERS
    //////////////////////////////////////////////////////////////////////////////////////

    private String mMask;
    private transient MaskCharacter[] mMaskChars;

    private String mValidCharacters;
    private String mInvalidCharacters;
    private String mPlaceholderString;
    private char mPlaceholder;

    //////////////////////////////////////////////////////////////////////////////////////
    // CONSTRUCTORS
    //////////////////////////////////////////////////////////////////////////////////////

    public MLMaskFormatter() {
        mMaskChars = EMPTY_MASK_CHARS;
        mPlaceholder = ' ';
    }

    public MLMaskFormatter(String mask) {
        this();
        setMask(mask);
    }

    //////////////////////////////////////////////////////////////////////////////////////
    // PUBLIC METHODS
    //////////////////////////////////////////////////////////////////////////////////////

    public String getMask() {
        return mMask;
    }

    public void setMask(String mask) {
        mMask = mask;
        updateInternalMask();
    }

    public String getValidCharacters() {
        return mValidCharacters;
    }

    public void setValidCharacters(String validCharacters) {
        mValidCharacters = validCharacters;
    }

    public String getInvalidCharacters() {
        return mInvalidCharacters;
    }

    public void setInvalidCharacters(String invalidCharacters) {
        mInvalidCharacters = invalidCharacters;
    }

    public String getPlaceholder() {
        return mPlaceholderString;
    }

    public void setPlaceholder(String placeholder) {
        mPlaceholderString = placeholder;
    }

    public char getPlaceholderCharacter() {
        return mPlaceholder;
    }

    public void setPlaceholderCharacter(char placeholder) {
        mPlaceholder = placeholder;
    }

    public String valueToString(Object value) {
        String stringValue = (value == null) ? "" : value.toString();
        StringBuffer result = new StringBuffer();
        String placeholder = getPlaceholder();
        int[] valueCounter = { 0 };

        append(result, stringValue, valueCounter, placeholder, mMaskChars);
        return result.toString();
    }

    //////////////////////////////////////////////////////////////////////////////////////
    // PRIVATE METHODS
    //////////////////////////////////////////////////////////////////////////////////////

    private void updateInternalMask() {
        ArrayList<MaskCharacter> fixed = new ArrayList<MaskCharacter>();
        ArrayList<MaskCharacter> temp = fixed;

        String mask = getMask();
        if (mask != null) {
            for (int counter = 0, maxCounter = mask.length(); counter < maxCounter; counter++) {
                char maskChar = mask.charAt(counter);

                switch (maskChar) {
                    case DIGIT_KEY: {
                        temp.add(new DigitMaskCharacter());
                        break;
                    }
                    case LITERAL_KEY: {
                        if (++counter < maxCounter) {
                            maskChar = mask.charAt(counter);
                            temp.add(new LiteralCharacter(maskChar));
                        }
                        break;
                    }
                    case UPPERCASE_KEY: {
                        temp.add(new UpperCaseCharacter());
                        break;
                    }
                    case LOWERCASE_KEY: {
                        temp.add(new LowerCaseCharacter());
                        break;
                    }
                    case ALPHA_NUMERIC_KEY: {
                        temp.add(new AlphaNumericCharacter());
                        break;
                    }
                    case CHARACTER_KEY: {
                        temp.add(new CharCharacter());
                        break;
                    }
                    case ANYTHING_KEY: {
                        temp.add(new MaskCharacter());
                        break;
                    }
                    case HEX_KEY: {
                        temp.add(new HexCharacter());
                        break;
                    }
                    default: {
                        temp.add(new LiteralCharacter(maskChar));
                        break;
                    }
                }
            }
        }
        if (fixed.size() == 0) {
            mMaskChars = EMPTY_MASK_CHARS;
        }
        else {
            mMaskChars = new MaskCharacter[fixed.size()];
            fixed.toArray(mMaskChars);
        }
    }

    private void append(StringBuffer result, String value, int[] index, String placeholder, MaskCharacter[] mask) {
        for (int i = 0; i < mask.length; i++) {
            if (!mask[i].append(result, value, index, placeholder)) {
                return;
            }
        }
    }

    //////////////////////////////////////////////////////////////////////////////////////
    // PRIVATE CLASSES
    //////////////////////////////////////////////////////////////////////////////////////

    private class MaskCharacter {
        public boolean isLiteral() {
            return false;
        }

        public boolean isValidCharacter(char character) {
            if (isLiteral()) {
                return getChar(character) == character;
            }

            character = getChar(character);

            String filter = getValidCharacters();
            if (filter != null && filter.indexOf(character) == -1) {
                return false;
            }

            filter = getInvalidCharacters();
            if (filter != null && filter.indexOf(character) != -1) {
                return false;
            }

            return true;
        }

        public char getChar(char character) {
            return character;
        }

        public boolean append(StringBuffer buffer, String formatting, int[] index, String placeholder) {
            boolean inString = index[0] < formatting.length();
            char character = inString ? formatting.charAt(index[0]) : 0;

            if (!inString) {
                return false;
            }

            if (isLiteral()) {
                buffer.append(getChar(character));

                if (inString && character == getChar(character)) {
                    index[0] = index[0] + 1;
                }
            }
            else if (index[0] >= formatting.length()) {
                if (placeholder != null && index[0] < placeholder.length()) {
                    buffer.append(placeholder.charAt(index[0]));
                }
                else {
                    buffer.append(getPlaceholderCharacter());
                }

                index[0] = index[0] + 1;
            }
            else if (isValidCharacter(character)) {
                buffer.append(getChar(character));
                index[0] = index[0] + 1;
            }
            else {
                return false;
            }

            return true;
        }
    }

    private class LiteralCharacter extends MaskCharacter {
        private char mLiteralCharacter;

        public LiteralCharacter(char character) {
            mLiteralCharacter = character;
        }

        public boolean isLiteral() {
            return true;
        }

        public char getChar(char aChar) {
            return mLiteralCharacter;
        }
    }

    private class DigitMaskCharacter extends MaskCharacter {
        public boolean isValidCharacter(char character) {
            return Character.isDigit(character) && super.isValidCharacter(character);
        }
    }

    private class UpperCaseCharacter extends MaskCharacter {
        public boolean isValidCharacter(char character) {
            return Character.isLetter(character) && super.isValidCharacter(character);
        }

        public char getChar(char character) {
            return Character.toUpperCase(character);
        }
    }

    private class LowerCaseCharacter extends MaskCharacter {
        public boolean isValidCharacter(char character) {
            return Character.isLetter(character) && super.isValidCharacter(character);
        }

        public char getChar(char character) {
            return Character.toLowerCase(character);
        }
    }

    private class AlphaNumericCharacter extends MaskCharacter {
        public boolean isValidCharacter(char character) {
            return Character.isLetterOrDigit(character) && super.isValidCharacter(character);
        }
    }

    private class CharCharacter extends MaskCharacter {
        public boolean isValidCharacter(char character) {
            return Character.isLetter(character) && super.isValidCharacter(character);
        }
    }

    private class HexCharacter extends MaskCharacter {
        private static final String HEX_CHARS = "0123456789abcedfABCDEF";

        public boolean isValidCharacter(char character) {
            return HEX_CHARS.indexOf(character) != -1 && super.isValidCharacter(character);
        }

        public char getChar(char character) {
            if (Character.isDigit(character)) {
                return character;
            }

            return Character.toUpperCase(character);
        }
    }
}
