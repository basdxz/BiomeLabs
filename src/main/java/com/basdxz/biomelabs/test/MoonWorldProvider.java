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

package com.basdxz.biomelabs.test;

import com.basdxz.biomelabs.api.DesolateWastelandWorldProvider;
import com.basdxz.biomelabs.world.chunk.ChunkGeneratorBase;
import net.minecraft.block.Block;
import net.minecraft.world.biome.WorldChunkManager;
import net.minecraft.world.chunk.IChunkProvider;

@SuppressWarnings("AccessStaticViaInstance")
public final class MoonWorldProvider extends DesolateWastelandWorldProvider {
    private static final String DIMENSION_NAME = "Moon";

    private static Block stoneBlock;
    private static byte stoneBlockMeta;
    private static int dimID;
    private static WorldChunkManager chunkManager;

    @Override
    public String getDimensionName() {
        return DIMENSION_NAME;
    }

    @Override
    public void stoneBlock(Block block) {
        this.stoneBlock = block;
    }

    @Override
    public void stoneBlockMeta(byte stoneBlockMeta) {
        this.stoneBlockMeta = stoneBlockMeta;
    }

    @Override
    public void dimID(int dimID) {
        this.dimID = dimID;
    }

    @Override
    public void chunkManager(WorldChunkManager chunkManager) {
        this.chunkManager = chunkManager;
    }

    @Override
    protected int dimensionId() {
        return dimID;
    }

    @Override
    protected WorldChunkManager worldChunkManager() {
        return chunkManager;
    }

    @Override
    public IChunkProvider createChunkGenerator() {
        return new ChunkGeneratorBase(worldObj) {
            @Override
            public long seedOffset() {
                return generatorName().hashCode();
            }

            @Override
            public String generatorName() {
                return getDimensionName();
            }

            @Override
            public Block stoneBlock() {
                return stoneBlock;
            }

            @Override
            public byte stoneBlockMeta() {
                return stoneBlockMeta;
            }
        };
    }
}
