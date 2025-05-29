package me.devjakob.clubserver.util;

/***
 * This part of the project contains code from Minecraft 1.8.8
 */
public class MathHelper {

	public static int floor_double(double value) {
		int i = (int)value;
		return value < (double)i ? i - 1 : i;
	}

	public static int roundUpToPowerOfTwo(int value) {
		int i = value - 1;
		i = i | i >> 1;
		i = i | i >> 2;
		i = i | i >> 4;
		i = i | i >> 8;
		i = i | i >> 16;
		return i + 1;
	}

	private static int calculateLogBaseTwoDeBruijn(int value) {
		value = isPowerOfTwo(value) ? value : roundUpToPowerOfTwo(value);
		return multiplyDeBruijnBitPosition[(int)((long)value * 125613361L >> 27) & 31];
	}

	private static boolean isPowerOfTwo(int value) {
		return value != 0 && (value & value - 1) == 0;
	}

	public static int calculateLogBaseTwo(int value) {
		return calculateLogBaseTwoDeBruijn(value) - (isPowerOfTwo(value) ? 0 : 1);
	}

	private static final int[] multiplyDeBruijnBitPosition = new int[] {0, 1, 28, 2, 29, 14, 24, 3, 30, 22, 20, 15, 25, 17, 4, 8, 31, 27, 13, 23, 21, 19, 16, 7, 26, 12, 18, 6, 11, 5, 10, 9};

}
