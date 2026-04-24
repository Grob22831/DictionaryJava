public class Digit5Validator implements Validator {
    @Override
    public boolean validateKey(String key) {
        if (key == null || key.length() != 5) {
            return false;
        }
        return key.matches("^\\d+$");
    }

    @Override
    public String getLanguageName() {
        return "Цифровой (5 цифр)";
    }
}