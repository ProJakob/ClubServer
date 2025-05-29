package me.devjakob.clubserver.util;

import java.util.HashMap;
import java.util.Map;

public class World {

    private final Map<Long, Chunk> idToChunkMap = new HashMap<>();

    public boolean chunkExists(int x, int z) {
        return idToChunkMap.containsKey(chunkXZToId(x, z));
    }

    public Chunk loadChunk(int x, int z) {
        long id = chunkXZToId(x, z);
        Chunk chunk = idToChunkMap.get(id);

        if(chunk == null) {
            System.out.println("Generated a chunk!");
            chunk = new Chunk(true);
            for (int i = 0; i < 16; i++) {
                for (int j = 0; j < 16; j++) {
                    chunk.set(i, 0, j, (char) (5 << 4));
                }
            }
            idToChunkMap.put(id, chunk);
        }

        return chunk;
    }

    public void set(int x, int y, int z, char blockState) {
        int chunkX = x >> 4;
        int chunkZ = z >> 4;

        loadChunk(chunkX, chunkZ).set(x, y, z, blockState);
    }

    public static long chunkXZToId(int x, int z) {
        return ((long)x & 0xFFFFFFFFL) | (((long)z & 0xFFFFFFFFL) << 32);
    }

}
