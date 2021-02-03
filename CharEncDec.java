package correcter;

import java.util.Locale;
import java.util.Random;

public class CharEncDec {
    private static final Random rand = new Random();
    private static final char[] symbolsNumbersSpace;

    static {
        var s = "abcdefghijklmnopqrstuvwxyz";
        symbolsNumbersSpace = (s + s.toUpperCase(Locale.ROOT) + "0123456789 ").toCharArray();
    }

    public String simulateCorruption(String data) {
        var res = new StringBuilder();

        for (int i = 0; i < data.length(); i += 3) {
            var sub = data.substring(i, Math.min(i + 3, data.length())).toCharArray();
            var corruption = symbolsNumbersSpace[rand.nextInt(symbolsNumbersSpace.length)];
            sub[rand.nextInt(Math.min(3, sub.length))] = corruption;
            res.append(sub);
        }

        return res.toString();
    }

    public String encode(String data) {
        var sb = new StringBuilder();
        for (char c : data.toCharArray()) {
            sb.append(c).append(c).append(c);
        }
        return sb.toString();
    }

    public String decode(String data) {
        var sb = new StringBuilder();
        for (int i = 0; i < data.length(); i += 3) {
            var c1 = data.charAt(i);
            var c2 = data.charAt(i + 1);
            var c3 = data.charAt(i + 2);
            sb.append(c1 == c2 ? c1 : c1 == c3 ? c1 : c2);
        }
        return sb.toString();
    }
}