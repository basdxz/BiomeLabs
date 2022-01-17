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

import com.basdxz.biomelabs.world.chunk.ChunkGeneratorBase;
import net.minecraft.world.biome.WorldChunkManager;
import net.minecraft.world.chunk.IChunkProvider;

public class MoonWorldProvider extends DesolateWastelandWorldProvider {
    protected static int dimID;
    protected static WorldChunkManager chunkManager;

    @Override
    public void dimID(int dimID) {
        MoonWorldProvider.dimID = dimID;
    }

    @Override
    public void chunkManager(WorldChunkManager chunkManager) {
        MoonWorldProvider.chunkManager = chunkManager;
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
    public String getDimensionName() {
        return "Moon";
    }

    @Override
    public IChunkProvider createChunkGenerator() {
        return new ChunkGeneratorBase(worldObj) {
            @Override
            public long seedOffset() {
                return 65561565646556561L;
            }

            @Override
            public String generatorName() {
                return getDimensionName();
            }
        };
    }
}
