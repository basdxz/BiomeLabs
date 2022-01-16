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
import lombok.val;
import lombok.var;
import net.minecraft.world.ChunkCoordIntPair;
import net.minecraft.world.World;
import net.minecraft.world.gen.feature.WorldGenerator;

import java.util.HashMap;
import java.util.Map;
import java.util.Random;
import java.util.function.Function;

public abstract class ChunkFeatureGenerator extends WorldGenerator {
    protected final Function<Random, Boolean> doGenerateFunction;
    protected final Random random = new Random();
    protected final Map<ChunkCoordIntPair, IWorldFeature> chunkFeatures = new HashMap<>();

    protected World currentWorld;
    protected int currentBlockPosX;
    protected int currentBlockPosY;
    protected int currentBlockPosZ;
    protected ChunkCoordIntPair currentChunkPos;
    protected ChunkCoordIntPair offsetChunkPos;

    public ChunkFeatureGenerator(Function<Random, Boolean> doGenerateFunction) {
        this.doGenerateFunction = doGenerateFunction;
    }

    @Override
    public boolean generate(World world, Random ignoredRandom, int blockPosX, int blockPosY, int blockPosZ) {
        val radius = maxChunkRadius();
        if (radius < 0)
            throw new RuntimeException("Feature radius must be more than or equal to zero.");

        preGenInit(world, blockPosX, blockPosY, blockPosZ);

        var generated = false;
        for (var chunkPosOffsetX = -radius; chunkPosOffsetX <= radius; chunkPosOffsetX++) {
            for (var chunkPosOffsetZ = -radius; chunkPosOffsetZ <= radius; chunkPosOffsetZ++) {
                initOffsetChunkPos(chunkPosOffsetX, chunkPosOffsetZ);
                seedRandom();
                generated |= feature();
            }
        }
        return generated;
    }

    protected void preGenInit(World world, int blockPosX, int blockPosY, int blockPosZ) {
        currentWorld = world;
        currentBlockPosX = blockPosX;
        currentBlockPosY = blockPosY;
        currentBlockPosZ = blockPosZ;
        currentChunkPos = new ChunkCoordIntPair(
                ChunkUtil.blockPosToChunkPos(blockPosX),
                ChunkUtil.blockPosToChunkPos(blockPosZ));
    }

    protected void initOffsetChunkPos(int chunkPosOffsetX, int chunkPosOffsetZ) {
        offsetChunkPos = new ChunkCoordIntPair(
                currentChunkPos.chunkXPos + chunkPosOffsetX,
                currentChunkPos.chunkZPos + chunkPosOffsetZ);
    }

    protected abstract int maxChunkRadius();

    protected void seedRandom() {
        random.setSeed(coordinateSeed(offsetChunkPos.chunkXPos, offsetChunkPos.chunkZPos, currentWorld.getSeed()));
    }

    //TODO: Implement the better seed combiner
    protected long coordinateSeed(int chunkPosX, int chunkPosZ, long seed) {
        var ret = (chunkPosX * 1554056719730314711L) ^ (chunkPosZ * -5577154291444130341L) ^ seed;
        return (ret * ret * -6976197817209773279L) + (ret * 2280518506167463948L);
    }

    protected boolean feature() {
        return chunkFeatures.computeIfAbsent(offsetChunkPos, (this::computeFeature))
                .generate(random,
                        currentWorld.getChunkFromChunkCoords(currentChunkPos.chunkXPos, currentChunkPos.chunkZPos));
    }

    protected IWorldFeature computeFeature(ChunkCoordIntPair chunkPos) {
        if (doGenerateFunction.apply(random))
            return newWorldFeature();
        return EmptyWorldFeature.INSTANCE();
    }

    protected abstract IWorldFeature newWorldFeature();
}
