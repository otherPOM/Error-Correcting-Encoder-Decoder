package correcter;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Locale;
import java.util.Random;
import java.util.Scanner;

public class Main {
    private static final Scanner scan = new Scanner(System.in);
    private static final Random rand = new Random();
    private static final char[] symbolsNumbersSpace;

    static {
        var s = "abcdefghijklmnopqrstuvwxyz";
        symbolsNumbersSpace = (s + s.toUpperCase(Locale.ROOT) + "0123456789 ").toCharArray();
    }

    public static void main(String[] args) throws IOException {
        try (var bis = new BufferedInputStream(
                Files.newInputStream(
                        Path.of("send.txt")));
        var bos = new BufferedOutputStream(
                Files.newOutputStream(
                        Path.of("received.txt")))) {

            while (bis.available() > 0) {
                var b = bis.read();
                b ^= (1 << rand.nextInt(8));
                bos.write(b);
            }
        }
//        var line = scan.nextLine();
//        var encoded = encode(line);
//        var corrupted = simulateCorruption(encoded);
//        var decoded = decode(corrupted);
//        System.out.println(line);
//        System.out.println(encoded);
//        System.out.println(corrupted);
//        System.out.println(decoded);
    }

    private static String simulateCorruption(String data) {
        var res = new StringBuilder();

        for (int i = 0; i < data.length(); i += 3) {
            var sub = data.substring(i, Math.min(i + 3, data.length())).toCharArray();
            var corruption = symbolsNumbersSpace[rand.nextInt(symbolsNumbersSpace.length)];
            sub[rand.nextInt(Math.min(3, sub.length))] = corruption;
            res.append(sub);
        }

        return res.toString();
    }

    private static String encode(String data) {
        var sb = new StringBuilder();
        for (char c : data.toCharArray()) {
            sb.append(c).append(c).append(c);
        }
        return sb.toString();
    }

    private static String decode(String data) {
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
