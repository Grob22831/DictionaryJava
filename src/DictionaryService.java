import java.util.List;

public interface DictionaryService {
    List<DictionaryEntry> readAll();
    boolean deleteByKey(String key);
    DictionaryEntry findByKey(String key);
    boolean addEntry(DictionaryEntry entry);
    String getDictionaryName();
}