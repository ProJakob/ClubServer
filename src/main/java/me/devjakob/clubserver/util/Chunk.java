package me.devjakob.clubserver.util;

import me.devjakob.clubserver.Util;

import java.util.ArrayList;
import java.util.List;

public class Chunk {

    public static class NetworkChunk {

        public final int sectionMask;
        public final byte[] data;

        public NetworkChunk(int sectionMask, byte[] data) {
            this.sectionMask = sectionMask;
            this.data = data;
        }
    }

    public static class Section {

        private final char[] data;
        private final NibbleArray blockLight;
        private NibbleArray skyLight;
        private int blockRefCount;

        public Section(boolean hasSky) {
            this.data = new char[4096];
            this.blockLight = new NibbleArray();

            if(hasSky) {
                this.skyLight = new NibbleArray();
            }
        }

        public char get(int x, int y, int z) {
            return data[y << 8 | z << 4 | x];
        }

        public void set(int x, int y, int z, char blockState) {
            char oldState = get(x, y, z);

            if(oldState != 0) {
                --this.blockRefCount;
            }

            if(blockState != 0) {
                ++this.blockRefCount;
            }

            data[y << 8 | z << 4 | x] = blockState;
        }

        public char[] getData() {
            return data;
        }

        public NibbleArray getBlockLight() {
            return blockLight;
        }

        public NibbleArray getSkyLight() {
            return skyLight;
        }

        public boolean isEmpty() {
            return blockRefCount == 0;
        }

    }

    private final boolean hasSky;

    private final Section[] sections = new Section[16];
    private final byte[] biomes = new byte[256];

    public Chunk(boolean hasSky) {
        this.hasSky = hasSky;
    }

    public char get(int x, int y, int z) {
        int chunkIndex = y >> 4;
        Section section = sections[chunkIndex];
        if (section == null) return 0;
        return section.get(x & 15, y & 15, z & 15);
    }

    public void set(int x, int y, int z, char blockState) {
        int chunkIndex = y >> 4;
        Section section = sections[chunkIndex];
        if (section == null) {
            section = new Section(hasSky);
            sections[chunkIndex] = section;
        }
        section.set(x & 15, y & 15, z & 15, blockState);
    }

    public Section[] getSections() {
        return sections;
    }

    public byte[] getBiomes() {
        return biomes;
    }

    public NetworkChunk toNetwork(boolean groundUpContinuous, boolean hasSky, int sections) {
        int sectionMask = 0;

        List<Section> sendingSections = new ArrayList<>();

        for (int i = 0; i < this.sections.length; i++) {
            Section section = this.sections[i];

            if(section != null && (!groundUpContinuous || !section.isEmpty()) && (sections & 1 << i) != 0) {
                sectionMask |= 1 << i;
                sendingSections.add(section);
            }
        }

        byte[] result = new byte[Util.calculateSize(Integer.bitCount(sectionMask), hasSky, groundUpContinuous)];

        int index = 0;

        // Write blocks
        for (Section section : sendingSections) {
            char[] blocks = section.getData();

            for (char block : blocks) {
                result[index++] = (byte) (block & 255);
                result[index++] = (byte) ((block >> 8) & 255);
            }
        }

        // Write Block Light
        for (Section section : sendingSections) {
            index = Util.copyDataWithOffset(section.getBlockLight().getData(), result, index);
        }

        // Write Skylight if hasSky
        if(hasSky) {
            for (Section section : sendingSections) {
                index = Util.copyDataWithOffset(section.getSkyLight().getData(), result, index);
            }
        }

        if(groundUpContinuous) {
            Util.copyDataWithOffset(getBiomes(), result, index);
        }

        return new NetworkChunk(sectionMask, result);
    }

}
