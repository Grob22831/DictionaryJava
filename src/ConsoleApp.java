import java.util.List;
import java.util.Scanner;

public class ConsoleApp {
    private static final Scanner scanner = new Scanner(System.in);

    public static void main(String[] args) {
        System.out.println("=== СИСТЕМА УПРАВЛЕНИЯ СЛОВАРЯМИ ===\n");

        while (true) {
            showMainMenu();
            int choice = getIntInput("Выберите действие: ");

            switch (choice) {
                case 1:
                    viewAllDictionaries();
                    break;
                case 2:
                    manageDictionary();
                    break;
                case 0:
                    System.out.println("До свидания!");
                    return;
                default:
                    System.out.println("Неверный выбор. Попробуйте снова.");
            }
        }
    }

    private static void showMainMenu() {
        System.out.println("\n--- ГЛАВНОЕ МЕНЮ ---");
        System.out.println("1. Просмотреть содержимое обоих словарей");
        System.out.println("2. Работа со словарем (удаление/поиск/добавление)");
        System.out.println("0. Выход");
    }

    private static void viewAllDictionaries() {
        System.out.println("\n=== ПРОСМОТР СЛОВАРЕЙ ===\n");

        DictionaryService latin = DictionaryFactory.getLatinDictionary();
        DictionaryService digit = DictionaryFactory.getDigitDictionary();

        displayDictionary(latin);
        System.out.println();
        displayDictionary(digit);
    }

    private static void displayDictionary(DictionaryService dictionary) {
        System.out.println("--- " + dictionary.getDictionaryName() + " ---");
        List<DictionaryEntry> entries = dictionary.readAll();

        if (entries.isEmpty()) {
            System.out.println("  (словарь пуст)");
        } else {
            for (DictionaryEntry entry : entries) {
                System.out.println("  " + entry);
            }
        }
    }

    private static void manageDictionary() {
        System.out.println("\n=== ВЫБОР СЛОВАРЯ ===");
        System.out.println("1. " + DictionaryFactory.getLatinDictionary().getDictionaryName());
        System.out.println("2. " + DictionaryFactory.getDigitDictionary().getDictionaryName());
        System.out.println("0. Назад");

        int dictChoice = getIntInput("Выберите словарь: ");
        if (dictChoice == 0) return;

        DictionaryService dictionary = DictionaryFactory.getDictionary(dictChoice);
        if (dictionary == null) {
            System.out.println("Неверный выбор словаря!");
            return;
        }

        manageDictionaryOperations(dictionary);
    }

    private static void manageDictionaryOperations(DictionaryService dictionary) {
        while (true) {
            System.out.println("\n--- Работа со словарем: " + dictionary.getDictionaryName() + " ---");
            System.out.println("1. Поиск записи по ключу");
            System.out.println("2. Добавление записи");
            System.out.println("3. Удаление записи по ключу");
            System.out.println("4. Показать все записи этого словаря");
            System.out.println("0. Назад к выбору словаря");

            int choice = getIntInput("Выберите операцию: ");

            switch (choice) {
                case 1:
                    searchEntry(dictionary);
                    break;
                case 2:
                    addEntry(dictionary);
                    break;
                case 3:
                    deleteEntry(dictionary);
                    break;
                case 4:
                    displayDictionary(dictionary);
                    break;
                case 0:
                    return;
                default:
                    System.out.println("Неверный выбор.");
            }
        }
    }

    private static void searchEntry(DictionaryService dictionary) {
        System.out.print("Введите ключ для поиска: ");
        String key = scanner.nextLine().trim();

        DictionaryEntry entry = dictionary.findByKey(key);
        if (entry != null) {
            System.out.println("Найдено: " + entry);
        } else {
            System.out.println("Запись с ключом '" + key + "' не найдена.");
        }
    }

    private static void addEntry(DictionaryService dictionary) {
        System.out.print("Введите ключ: ");
        String key = scanner.nextLine().trim();

        System.out.print("Введите перевод на русский: ");
        String value = scanner.nextLine().trim();

        DictionaryEntry newEntry = new DictionaryEntry(key, value);
        if (dictionary.addEntry(newEntry)) {
            System.out.println("Запись успешно добавлена!");
        }
    }

    private static void deleteEntry(DictionaryService dictionary) {
        System.out.print("Введите ключ для удаления: ");
        String key = scanner.nextLine().trim();

        if (dictionary.deleteByKey(key)) {
            System.out.println("Запись с ключом '" + key + "' удалена.");
        } else {
            System.out.println("Запись с ключом '" + key + "' не найдена.");
        }
    }

    private static int getIntInput(String prompt) {
        System.out.print(prompt);
        while (!scanner.hasNextInt()) {
            System.out.print("Ошибка! Введите число: ");
            scanner.next();
        }
        int result = scanner.nextInt();
        scanner.nextLine();
        return result;
    }
}