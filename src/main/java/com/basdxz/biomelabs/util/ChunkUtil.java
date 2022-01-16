package com.basdxz.biomelabs.util;

import lombok.NoArgsConstructor;
import lombok.val;
import lombok.var;
import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.init.Blocks;
import net.minecraft.world.chunk.Chunk;

import java.util.Arrays;
import java.util.function.BiConsumer;

import static lombok.AccessLevel.PRIVATE;

@NoArgsConstructor(access = PRIVATE)
public final class ChunkUtil {
    public final static int CHUNK_LENGTH_WIDTH = 16;
    public final static float CHUNK_LENGTH_WIDTH_INV = 1F / CHUNK_LENGTH_WIDTH;
    public final static int CHUNK_HEIGHT = 256;
    public final static int CHUNK_AREA = CHUNK_LENGTH_WIDTH * CHUNK_LENGTH_WIDTH;
    public final static int CHUNK_VOLUME = CHUNK_AREA * CHUNK_HEIGHT;

    public static Block[] newChunkBlockArray() {
        val blocks = new Block[CHUNK_VOLUME];
        Arrays.fill(blocks, Blocks.air);
        return blocks;
    }

    public static byte[] newChunkBlockMetasArray() {
        return new byte[CHUNK_VOLUME];
    }

    public static int chunkBlockArrayIndex(int blockPosX, int blockPosY, int blockPosZ) {
        return (Math.abs(ChunkUtil.blockPosToBlockInChunkPos(blockPosX)) * ChunkUtil.CHUNK_LENGTH_WIDTH +
                Math.abs(ChunkUtil.blockPosToBlockInChunkPos(blockPosZ))) * CHUNK_HEIGHT + blockPosY;
    }

    public static int chunkSliceArrayIndex(int blockPosX, int blockPosZ) {
        return Math.abs(blockPosZ) * CHUNK_LENGTH_WIDTH + Math.abs(blockPosX);
    }

    public static void iterateXZ(BiConsumer<Integer, Integer> consumer) {
        for (int x = 0; x < CHUNK_LENGTH_WIDTH; x++)
            for (int y = 0; y < CHUNK_LENGTH_WIDTH; y++)
                consumer.accept(x, y);
    }

    public static int blockPosToBlockInChunkPos(int blockPos) {
        return blockPos % CHUNK_LENGTH_WIDTH;
    }

    public static int blockPosToChunkPos(int blockPos) {
        if (blockPos >= 0) {
            return blockPos / CHUNK_LENGTH_WIDTH;
        } else {
            return (blockPos + 1) / CHUNK_LENGTH_WIDTH - 1;
        }
    }

    public static int chunkPosToBlockPos(int chunkPos) {
        return chunkPosToBlockPos(chunkPos, 0);
    }

    public static int chunkPosToBlockPos(int chunkPos, int blockPosOffset) {
        return (chunkPos * CHUNK_LENGTH_WIDTH) + blockPosOffset;
    }

    public static int getTopSolidOrLiquidBlock(Chunk chunk, int blockPosX, int blockPosZ) {
        blockPosX &= 15;
        blockPosZ &= 15;
        for (var chunkPosY = chunk.getTopFilledSegment() + 15; chunkPosY > 0; --chunkPosY) {
            val block = chunk.getBlock(blockPosX, chunkPosY, blockPosZ);
            if (block.getMaterial().blocksMovement() && block.getMaterial() != Material.leaves &&
                    !block.isFoliage(chunk.worldObj, blockPosX, chunkPosY, blockPosZ))
                return chunkPosY + 1;
        }

        return -1;
    }
}
