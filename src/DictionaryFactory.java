public class DictionaryFactory {
    private static final String LATIN_DICT_FILE = "latin_dictionary.txt";
    private static final String DIGIT_DICT_FILE = "digit_dictionary.txt";

    private static DictionaryService latinDictionary;
    private static DictionaryService digitDictionary;

    public static DictionaryService getLatinDictionary() {
        if (latinDictionary == null) {
            latinDictionary = new FileDictionaryService(
                    LATIN_DICT_FILE,
                    new Latin4Validator(),
                    "Словарь (латиница, 4 символа)"
            );
        }
        return latinDictionary;
    }

    public static DictionaryService getDigitDictionary() {
        if (digitDictionary == null) {
            digitDictionary = new FileDictionaryService(
                    DIGIT_DICT_FILE,
                    new Digit5Validator(),
                    "Словарь (цифровой, 5 символов)"
            );
        }
        return digitDictionary;
    }

    public static DictionaryService getDictionary(int choice) {
        if (choice == 1) {
            return getLatinDictionary();
        } else if (choice == 2) {
            return getDigitDictionary();
        }
        return null;
    }
}