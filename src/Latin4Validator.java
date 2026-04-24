public class Latin4Validator implements Validator {
    @Override
    public boolean validateKey(String key) {
        if (key == null || key.length() != 4) {
            return false;
        }
        return key.matches("^[a-zA-Z]+$");
    }

    @Override
    public String getLanguageName() {
        return "Латиница (4 буквы)";
    }
}