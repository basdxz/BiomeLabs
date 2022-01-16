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

package com.basdxz.biomelabs.worldgen.feature;

import com.basdxz.biomelabs.util.ChunkUtil;
import com.basdxz.biomelabs.util.RandomUtil;
import lombok.val;
import lombok.var;
import net.minecraft.init.Blocks;
import net.minecraft.world.ChunkCoordIntPair;
import net.minecraft.world.chunk.Chunk;

import java.util.Random;

public class CraterFeature implements IWorldFeature {
    protected final int blockPosX;
    protected final int blockPosY;
    protected final int blockPosZ;
    protected final int radius;
    protected final int depth;
    protected final int curve;

    protected Random currentRandom;
    protected int currentChunkBlockPosX;
    protected int currentChunkBlockPosZ;
    protected int currentDistance;
    protected int currentDepth;

    protected CraterFeature(int blockPosX, int blockPosY, int blockPosZ, int radius, int depth, int curve) {
        this.blockPosX = blockPosX;
        this.blockPosY = blockPosY;
        this.blockPosZ = blockPosZ;
        this.radius = radius;
        this.depth = depth;
        this.curve = curve;
    }

    // TODO: Add checks
    public static CraterFeature newCraterFeature(Random random, ChunkCoordIntPair chunkPos,
                                                 int minRadius, int maxRadius,
                                                 int minDepth, int maxDepth,
                                                 int minCurve, int maxCurve) {
        val blockPosX = ChunkUtil.chunkPosToBlockPos(chunkPos.chunkXPos) + RandomUtil.randChunkBlockPos(random);
        val blockPosZ = ChunkUtil.chunkPosToBlockPos(chunkPos.chunkZPos) + RandomUtil.randChunkBlockPos(random);
        val blockPosY = RandomUtil.randInt(random, 65, 70);
        val radius = RandomUtil.randInt(random, minRadius, maxRadius);
        val depth = RandomUtil.randInt(random, minDepth, maxDepth);
        val curve = RandomUtil.randInt(random, minCurve, maxCurve);

        return new CraterFeature(blockPosX, blockPosY, blockPosZ, radius, depth, curve);
    }

    @Override
    public boolean generate(Random random, Chunk currentChunk) {
        preGenerate(random, currentChunk);

        for (var x = 0; x <= 15; x++) {
            for (var z = 0; z <= 15; z++) {
                distance(x, z);
                if (!rangeCheck())
                    continue;

                depth();
                for (var y = ChunkUtil.getTopSolidOrLiquidBlock(currentChunk, x, z) - 1; y > currentDepth; y--)
                    currentChunk.func_150807_a(x, y, z, Blocks.air, 0);
            }
        }

        return true;
    }

    protected void preGenerate(Random random, Chunk currentChunk) {
        currentRandom = random;
        currentChunkBlockPosX = ChunkUtil.chunkPosToBlockPos(currentChunk.xPosition);
        currentChunkBlockPosZ = ChunkUtil.chunkPosToBlockPos(currentChunk.zPosition);
    }

    protected void distance(int posXOffset, int posZOffset) {
        currentDistance = (int) Math.round(Math.sqrt(Math.pow((currentChunkBlockPosX + posXOffset) - blockPosX, 2) +
                                Math.pow((currentChunkBlockPosZ + posZOffset) - blockPosZ, 2)));

        if (currentRandom.nextFloat() > 0.15F)
            currentDistance -= 1;
    }

    protected boolean rangeCheck() {
        return currentDistance <= radius;
    }

    protected void depth() {
        currentDepth = (int) (blockPosY - depth + Math.round(Math.pow((float) currentDistance / radius, 2 * curve) * depth));
    }
}
