package utils;

public class Utils {
    /**
     * Convert input integer value to an array of bytes.
     * @param value Value to convert.
     * @return Byte array associated at param value.
     */
    public static byte[] intToByteArray (int value) {
        return new byte[] {
                (byte)(value >>> 24),
                (byte)(value >>> 16),
                (byte)(value >>> 8),
                (byte)value};
    }

    /**
     * Convert input byte array to an integer value.
     * @param bytes Byte array to convert.
     * @return Integer value associated at param value.
     */
    public static int byteArrayToInt (byte[] bytes) {
        return ((0xFF & bytes[0]) << 24) | ((0xFF & bytes[1]) << 16) |
                ((0xFF & bytes[2]) << 8) | (0xFF & bytes[3]);
    }
}
