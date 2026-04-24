import java.util.List;
import java.util.Scanner;

public class ConsoleApp {
    private static final Scanner scanner = new Scanner(System.in);
    private static DictionaryService currentDictionary = null;

    public static void main(String[] args) {
        System.out.println("=== СИСТЕМА УПРАВЛЕНИЯ СЛОВАРЯМИ ===\n");

        while (true) {
            selectDictionary();

            while (currentDictionary != null) {
                showMainMenu();
                int choice = getIntInput("Выберите действие: ");

                switch (choice) {
                    case 1:
                        viewCurrentDictionary();
                        break;
                    case 2:
                        searchEntry();
                        break;
                    case 3:
                        addEntry();
                        break;
                    case 4:
                        deleteEntry();
                        break;
                    case 0:
                        currentDictionary = null;
                        System.out.println("\nВозврат к выбору словаря...\n");
                        break;
                    default:
                        System.out.println("Неверный выбор. Попробуйте снова.");
                }
            }
        }
    }

    private static void selectDictionary() {
        System.out.println("=== ВЫБОР СЛОВАРЯ ===");
        System.out.println("1. Латинский словарь (4 буквы)");
        System.out.println("2. Цифровой словарь (5 цифр)");
        System.out.println("0. Выход из программы");

        int dictChoice = getIntInput("Выберите словарь: ");

        if (dictChoice == 0) {
            System.out.println("До свидания!");
            System.exit(0);
        }

        currentDictionary = DictionaryFactory.loadDictionary(dictChoice);
        if (currentDictionary == null) {
            System.out.println("Неверный выбор! Загружается словарь по умолчанию.");
            currentDictionary = DictionaryFactory.loadDictionary(1);
        }

        System.out.println("\nЗагружен: " + currentDictionary.getDictionaryName() + "\n");
    }

    private static void showMainMenu() {
        System.out.println("\n--- РАБОТА СО СЛОВАРЕМ: " + currentDictionary.getDictionaryName() + " ---");
        System.out.println("1. Просмотреть все записи словаря");
        System.out.println("2. Поиск записи по ключу");
        System.out.println("3. Добавление записи");
        System.out.println("4. Удаление записи по ключу");
        System.out.println("0. Сменить словарь");
    }

    private static void viewCurrentDictionary() {
        System.out.println("\n=== СОДЕРЖИМОЕ СЛОВАРЯ ===\n");
        displayDictionary(currentDictionary);
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

    private static void searchEntry() {
        System.out.print("\nВведите ключ для поиска: ");
        String key = scanner.nextLine().trim();

        DictionaryEntry entry = currentDictionary.findByKey(key);
        if (entry != null) {
            System.out.println("Найдено: " + entry);
        } else {
            System.out.println("Запись с ключом '" + key + "' не найдена.");
        }
    }

    private static void addEntry() {
        System.out.print("\nВведите ключ: ");
        String key = scanner.nextLine().trim();

        System.out.print("Введите перевод на русский: ");
        String value = scanner.nextLine().trim();

        DictionaryEntry newEntry = new DictionaryEntry(key, value);
        if (currentDictionary.addEntry(newEntry)) {
            System.out.println("Запись успешно добавлена!");
        }
    }

    private static void deleteEntry() {
        System.out.print("\nВведите ключ для удаления: ");
        String key = scanner.nextLine().trim();

        if (currentDictionary.deleteByKey(key)) {
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