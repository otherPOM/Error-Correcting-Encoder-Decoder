package correcter;

import java.util.Random;
import java.util.Scanner;

public class Main {
    private static final Scanner scan = new Scanner(System.in);

    public static void main(String[] args) {
        var line = scan.nextLine();
        var res = new StringBuilder();
        var rand = new Random();

        for (int i = 0; i < line.length(); i += 3) {
            var sub = line.substring(i, Math.min(i + 3, line.length())).toCharArray();
            sub[rand.nextInt(Math.min(3, sub.length))] = (char) (rand.nextInt(27) + 'a' - 1);
            res.append(sub);
        }

        System.out.println(res);
    }
}
