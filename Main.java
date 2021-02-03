package correcter;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Locale;
import java.util.Scanner;

public class Main {
    private static final Scanner scan = new Scanner(System.in);

    public static void main(String[] args) throws IOException {
        System.out.print("Write a mode: ");
        var mode = scan.nextLine();
        System.out.println();

        var encoder = new BitEncDec();
        switch (mode) {
            case "encode":
                var dataToEncode = Files.readAllBytes(Path.of("send.txt"));

                System.out.println("send.txt");
                System.out.println("text view: " + new String(dataToEncode));
                System.out.println("hex view: " + hexString(dataToEncode));
                System.out.println("bin view: " + binString(dataToEncode));
                System.out.println();

                var encodedData = encoder.encode(dataToEncode);

                System.out.println("encoded.txt:");
                System.out.println("expand: " + expandString(encodedData));
                System.out.println("parity: " + binString(encodedData));
                System.out.println("hex view: " + hexString(encodedData));
                System.out.println();

                Files.write(Path.of("encoded.txt"), encodedData);
                break;
            case "send":
                var dataToSend = Files.readAllBytes(Path.of("encoded.txt"));

                System.out.println("encoded.txt");
                System.out.println("hex view: " + hexString(dataToSend));
                System.out.println("bin view: " + binString(dataToSend));
                System.out.println();

                var corruptedData = encoder.simulateCorruption(dataToSend);

                System.out.println("received.txt");
                System.out.println("bin view: " + binString(corruptedData));
                System.out.println("hex view: " + hexString(corruptedData));
                System.out.println();

                Files.write(Path.of("received.txt"), corruptedData);
                break;
            case "decode":
                var dataToDecode = Files.readAllBytes(Path.of("received.txt"));

                System.out.println("received.txt:");
                System.out.println("hex view: " + hexString(dataToDecode));
                System.out.println("bin view: " + binString(dataToDecode));
                System.out.println();

                var fixedData = encoder.decode(dataToDecode);
                System.out.println("decoded.txt:");
                System.out.println("decode: " + binString(fixedData));
                System.out.println("hex view: " + hexString(fixedData));
                System.out.println("text view: " + new String(fixedData));

                Files.write(Path.of("decoded.txt"), fixedData);

                break;
        }
    }

    private static String hexString(byte[] data) {
        var sb = new StringBuilder();
        for (byte b : data) {
            var hex = Integer.toHexString(b);
            hex = hex.length() > 2 ? hex.substring(hex.length() - 2) : hex;
            hex = hex.length() < 2 ? "0" + hex : hex;
            sb.append(hex.toUpperCase(Locale.ROOT)).append(" ");
        }
        return sb.toString();
    }

    private static String binString(byte[] data) {
        var sb = new StringBuilder();
        for (byte b : data) {
            var binary = toBinaryString(b);
            sb.append(binary).append(" ");
        }
        return sb.toString();
    }

    private static String expandString(byte[] data) {
        var sb = new StringBuilder();
        for (byte b : data) {
            var binary = toBinaryString(b);
            sb.append(binary, 0, 6)
                    .append("..")
                    .append(" ");
        }
        return sb.toString();
    }

    private static String toBinaryString(byte b) {
        var sb = new StringBuilder();
        for (int i = 7; i >= 0 ; i--) {
            sb.append((b >> i) & 1);
        }
        return sb.toString();
    }

    private static void stringStages() {
        var encoder = new CharEncDec();
        var line = scan.nextLine();
        var encoded = encoder.encode(line);
        var corrupted = encoder.simulateCorruption(encoded);
        var decoded = encoder.decode(corrupted);
        System.out.println(line);
        System.out.println(encoded);
        System.out.println(corrupted);
        System.out.println(decoded);
    }
}
