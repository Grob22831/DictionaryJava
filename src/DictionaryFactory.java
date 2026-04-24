import java.util.Scanner;

public class DictionaryFactory {
    private static final String LATIN_DICT_FILE = "latin_dictionary.txt";
    private static final String DIGIT_DICT_FILE = "digit_dictionary.txt";
    private static final Scanner scanner = new Scanner(System.in);

    public static DictionaryService loadDictionary(int choice) {
        if (choice == 1) {
            return loadLatinDictionary();
        } else if (choice == 2) {
            return loadDigitDictionary();
        }
        return null;
    }

    private static DictionaryService loadLatinDictionary() {
        System.out.print("Загрузить имеющийся? (да/нет): ");
        String answer = scanner.nextLine().trim();

        if (answer.toLowerCase().equals("да")) {
            return new FileDictionaryService(
                    LATIN_DICT_FILE,
                    new Latin4Validator(),
                    "Словарь (латиница, 4 символа)"
            );
        } else {
            System.out.print("Введите расположение латинского словаря: ");
            String path = scanner.nextLine().trim();
            return new FileDictionaryService(
                    path,
                    new Latin4Validator(),
                    "Словарь (латиница, 4 символа)"
            );
        }
    }

    private static DictionaryService loadDigitDictionary() {
        System.out.print("Загрузить имеющийся? (да/нет): ");
        String answer = scanner.nextLine().trim();

        if (answer.toLowerCase().equals("да")) {
            return new FileDictionaryService(
                    DIGIT_DICT_FILE,
                    new Digit5Validator(),
                    "Словарь (цифровой, 5 символов)"
            );
        } else {
            System.out.print("Введите расположение цифрового словаря: ");
            String path = scanner.nextLine().trim();
            return new FileDictionaryService(
                    path,
                    new Digit5Validator(),
                    "Словарь (цифровой, 5 символов)"
            );
        }
    }

    public static DictionaryService getLatinDictionary() {
        return new FileDictionaryService(
                LATIN_DICT_FILE,
                new Latin4Validator(),
                "Словарь (латиница, 4 символа)"
        );
    }

    public static DictionaryService getDigitDictionary() {
        return new FileDictionaryService(
                DIGIT_DICT_FILE,
                new Digit5Validator(),
                "Словарь (цифровой, 5 символов)"
        );
    }
}