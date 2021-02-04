package correcter;

import java.util.Random;

public class BitEncDec {
    private final Random rand = new Random();

    public byte[] encode(byte[] data) {
        var res = new byte[data.length * 2];

        int ind = 0;
        for (byte b : data) {
            var bytes = hammingEncode(b);
            res[ind++] = bytes[0];
            res[ind++] = bytes[1];
        }
        return res;
    }

    private byte[] hammingEncode(byte b) {
        var encoded = new byte[2];

        var ind = 7;
        for (int i = 0; i < 2; i++) {
            byte encodedByte = 0;

            encodedByte += (b >> ind-- & 1) << 5;
            encodedByte += (b >> ind-- & 1) << 3;
            encodedByte += (b >> ind-- & 1) << 2;
            encodedByte += (b >> ind-- & 1) << 1;

            encodedByte = parity(encodedByte);
            encoded[i] = encodedByte;
        }
        return encoded;
    }

    private byte parity(byte b) {
        var p1 = (b >> 5 & 1) ^ (b >> 3 & 1) ^ (b >> 1 & 1);
        var p2 = (b >> 5 & 1) ^ (b >> 2 & 1) ^ (b >> 1 & 1);
        var p4 = (b >> 3 & 1) ^ (b >> 2 & 1) ^ (b >> 1 & 1);
        b += (p1 << 7) + (p2 << 6) + (p4 << 4);
        return b;
    }

    public byte[] simulateCorruption(byte[] data) {
        var res = new byte[data.length];
        for (int i = 0; i < data.length; i++) {
            res[i] = (byte) (data[i] ^ (1 << rand.nextInt(8)));
        }
        return res;
    }

    public byte[] decode(byte[] data) {
        var res = new byte[data.length / 2];
        for (int i = 0; i < data.length; i += 2) {
            var b1 = fixBit(data[i]);
            var b2 = fixBit(data[i + 1]);
            byte decodedByte = 0;

            decodedByte += (b1 >> 5 & 1) << 7;
            decodedByte += (b1 >> 3 & 1) << 6;
            decodedByte += (b1 >> 2 & 1) << 5;
            decodedByte += (b1 >> 1 & 1) << 4;
            decodedByte += (b2 >> 5 & 1) << 3;
            decodedByte += (b2 >> 3 & 1) << 2;
            decodedByte += (b2 >> 2 & 1) << 1;
            decodedByte += (b2 >> 1 & 1);
            res[i / 2] = decodedByte;
        }
        return res;
    }

    private static byte fixBit(byte b) {
        var errorIndex = 0;
        var p1 = b >> 7 & 1;
        var p2 = b >> 6 & 1;
        var p4 = b >> 4 & 1;

        if (((b >> 5 & 1) ^ (b >> 3 & 1) ^ (b >> 1 & 1)) != p1) {
            errorIndex += 1;
        }
        if (((b >> 5 & 1) ^ (b >> 2 & 1) ^ (b >> 1 & 1)) != p2) {
            errorIndex += 2;
        }
        if (((b >> 3 & 1) ^ (b >> 2 & 1) ^ (b >> 1 & 1)) != p4) {
            errorIndex += 4;
        }
        errorIndex = 8 - errorIndex;
        b ^= 1 << errorIndex;
        return b;
    }
}
