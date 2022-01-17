/*
 * MIT License
 *
 * Copyright (c) 2022 basdxz
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in all
 * copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
 * SOFTWARE.
 *
 */

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
            for (int z = 0; z < CHUNK_LENGTH_WIDTH; z++)
                consumer.accept(x, z);
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
