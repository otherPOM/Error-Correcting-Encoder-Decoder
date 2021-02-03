package correcter;

import java.util.ArrayList;
import java.util.Random;

public class BitEncDec {
    private final Random rand = new Random();

    public byte[] encode(byte[] data) {
        var list = new ArrayList<Byte>();

        var shift = 7;
        byte n = 0;
        for (int j = 0; j < data.length; j++) {
            var b = data[j];
            for (int i = 7; i >= 0; i--) {
                var bit = getBit(b, i);
                n += bit << shift;
                n += bit << (shift - 1);
                shift -= 2;
                if (shift < 3 || (j == data.length - 1 && i == 0)) {
                    var parity = (n >> 7 & 1) ^ (n >> 5 & 1) ^ (n >> 3 & 1);
                    n += parity;
                    n += parity << 1;
                    shift = 7;
                    list.add(n);
                    n = 0;
                }
            }
        }
        var res = new byte[list.size()];
        for (int i = 0; i < list.size(); i++) {
            res[i] = list.get(i);
        }
        return res;
    }

    private int getBit(byte b, int pos) {
        return (b >> pos) & 1;
    }

    public byte[] simulateCorruption(byte[] data) {
        var res = new byte[data.length];
        for (int i = 0; i < data.length; i++) {
            res[i] = (byte) (data[i] ^ (1 << rand.nextInt(8)));
        }
        return res;
    }

    public byte[] decode(byte[] data) {
        var list = new ArrayList<Byte>();

        byte fixedByte = 0;
        var shift = 7;
        for (byte b : data) {
            var b1 = b >> 7 & 1;
            var b2 = b >> 6 & 1;
            var b3 = b >> 5 & 1;
            var b4 = b >> 4 & 1;
            var b5 = b >> 3 & 1;
            var b6 = b >> 2 & 1;
            var b7 = b >> 1 & 1;

            var bit1 = b1 == b2 ? b1 : b3 ^ b5 ^ b7;
            var bit2 = b3 == b4 ? b3 : b1 ^ b5 ^ b7;
            var bit3 = b5 == b6 ? b5 : b1 ^ b3 ^ b7;

            if (shift < 0) {
                list.add(fixedByte);
                fixedByte = 0;
                shift = 7;
            }
            fixedByte += bit1 << shift--;
            if (shift < 0) {
                list.add(fixedByte);
                fixedByte = 0;
                shift = 7;
            }
            fixedByte += bit2 << shift--;
            if (shift < 0) {
                list.add(fixedByte);
                fixedByte = 0;
                shift = 7;
            }
            fixedByte += bit3 << shift--;
        }
        if (fixedByte != 0) {
            list.add(fixedByte);
        }

        var res = new byte[list.size()];
        for (int i = 0; i < list.size(); i++) {
            res[i] = list.get(i);
        }
        return res;
    }
}
