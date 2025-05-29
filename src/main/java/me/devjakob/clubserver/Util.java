package me.devjakob.clubserver;

public class Util {

    public static int calculateSize(int numSections, boolean hasSky, boolean groundUpContinuous) {
        int blockBytes = numSections * 2 * 16 * 16 * 16;
        int blockLightBytes = numSections * 16 * 16 * 16 / 2;
        int skyLightBytes = hasSky ? numSections * 16 * 16 * 16 / 2 : 0;
        int biomeBytes = groundUpContinuous ? 256 : 0;
        return blockBytes + blockLightBytes + skyLightBytes + biomeBytes;
    }

    public static int copyDataWithOffset(byte[] src, byte[] dest, int destPos) {
        System.arraycopy(src, 0, dest, destPos, src.length);
        return destPos + src.length;
    }


}
