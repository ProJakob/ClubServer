package me.devjakob.clubserver.util;

public class NibbleArray {

    private final byte[] data;

    public NibbleArray() {
        this.data = new byte[2048];
    }

    public NibbleArray(byte[] data) {
        this.data = data;

        if(data.length != 2048) {
            throw new IllegalArgumentException("NibbleArray data length must be 2048");
        }
    }

    public int get(int x, int y, int z) {
        return getIndex(getCoordinateIndex(x, y, z));
    }

    public void set(int x, int y, int z, int value) {
        setIndex(getCoordinateIndex(x, y, z), value);
    }

    private int getCoordinateIndex(int x, int y, int z) {
        return y << 8 | z << 4 | x;
    }

    public int getIndex(int index) {
        int i = getNibbleIndex(index);
        return isLowerNibble(index) ? data[i] & 15 : data[i] >> 4 & 15;
    }

    public void setIndex(int index, int value) {
        int i = getNibbleIndex(index);

        if(isLowerNibble(index)) {
            data[i] = (byte) (data[i] & 240 | value & 15);
        } else {
            data[i] = (byte) (data[i] & 15 | (value & 15) << 4);
        }
    }

    private int getNibbleIndex(int index) {
        return index >> 1;
    }

    private boolean isLowerNibble(int index) {
        return (index & 1) == 0;
    }

    public byte[] getData() {
        return data;
    }
}
