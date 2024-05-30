package hw.utils;

public class StringUtils {

    /**
     * Первая буква заглавная
     *
     * @param str
     * @return
     */
    public static String capitalizeFirstLetter(String str) {
        return str.substring(0, 1).toUpperCase() + str.substring(1);
    }
}
