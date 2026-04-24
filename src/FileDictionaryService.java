import java.io.*;
import java.util.*;

public class FileDictionaryService implements DictionaryService {
    private final String filePath;
    private final Validator validator;
    private final String dictionaryName;

    public FileDictionaryService(String filePath, Validator validator, String dictionaryName) {
        this.filePath = filePath;
        this.validator = validator;
        this.dictionaryName = dictionaryName;
        initFile();
    }

    private void initFile() {
        File file = new File(filePath);
        try {
            if (!file.exists()) {
                file.createNewFile();
            }
        } catch (IOException e) {
            System.err.println("Ошибка при создании файла: " + e.getMessage());
        }
    }

    @Override
    public List<DictionaryEntry> readAll() {
        List<DictionaryEntry> entries = new ArrayList<>();
        try (BufferedReader reader = new BufferedReader(new FileReader(filePath))) {
            String line;
            while ((line = reader.readLine()) != null) {
                String[] parts = line.split(":", 2);
                if (parts.length == 2) {
                    DictionaryEntry entry = new DictionaryEntry(parts[0], parts[1]);
                    if (validator.validateKey(entry.getKey())) {
                        entries.add(entry);
                    }
                }
            }
        } catch (IOException e) {
            System.err.println("Ошибка чтения файла: " + e.getMessage());
        }
        return entries;
    }

    @Override
    public boolean deleteByKey(String key) {
        if (!validator.validateKey(key)) {
            System.err.println("Ошибка: ключ '" + key + "' не соответствует правилам словаря '" + dictionaryName + "'");
            return false;
        }

        List<DictionaryEntry> allEntries = readAllFromFile();
        boolean removed = allEntries.removeIf(entry -> entry.getKey().equals(key));

        if (removed) {
            saveAll(allEntries);
        }
        return removed;
    }

    @Override
    public DictionaryEntry findByKey(String key) {
        if (!validator.validateKey(key)) {
            return null;
        }
        return readAllFromFile().stream()
                .filter(entry -> entry.getKey().equals(key))
                .findFirst()
                .orElse(null);
    }

    @Override
    public boolean addEntry(DictionaryEntry entry) {
        if (!validator.validateKey(entry.getKey())) {
            System.err.println("Ошибка: ключ '" + entry.getKey() + "' не соответствует правилам словаря '" + dictionaryName + "'");
            System.err.println("Правила: " + validator.getLanguageName());
            return false;
        }

        List<DictionaryEntry> allEntries = readAllFromFile();

        if (allEntries.stream().anyMatch(e -> e.getKey().equals(entry.getKey()))) {
            System.err.println("Ошибка: ключ '" + entry.getKey() + "' уже существует");
            return false;
        }

        allEntries.add(entry);
        saveAll(allEntries);
        return true;
    }

    private List<DictionaryEntry> readAllFromFile() {
        List<DictionaryEntry> entries = new ArrayList<>();
        try (BufferedReader reader = new BufferedReader(new FileReader(filePath))) {
            String line;
            while ((line = reader.readLine()) != null) {
                String[] parts = line.split(":", 2);
                if (parts.length == 2) {
                    entries.add(new DictionaryEntry(parts[0], parts[1]));
                }
            }
        } catch (IOException e) {
            System.err.println("Ошибка чтения файла: " + e.getMessage());
        }
        return entries;
    }

    private void saveAll(List<DictionaryEntry> entries) {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(filePath))) {
            for (DictionaryEntry entry : entries) {
                writer.write(entry.getKey() + ":" + entry.getValue());
                writer.newLine();
            }
        } catch (IOException e) {
            System.err.println("Ошибка сохранения файла: " + e.getMessage());
        }
    }

    @Override
    public String getDictionaryName() {
        return dictionaryName;
    }
}