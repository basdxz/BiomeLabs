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

package com.basdxz.biomelabs.api;

import com.basdxz.biomelabs.world.provider.WorldProviderBase;
import lombok.experimental.Accessors;
import net.minecraft.block.Block;
import net.minecraft.world.biome.WorldChunkManager;


@Accessors(fluent = true)
public abstract class DesolateWastelandWorldProvider extends WorldProviderBase {
    /**
     * Must set a static value inside implementing classes.
     *
     * @param block The Block to store
     */
    public abstract void stoneBlock(Block block);

    /**
     * Must set a static value inside implementing classes.
     *
     * @param stoneBlockMeta The block meta to store
     */
    public abstract void stoneBlockMeta(byte stoneBlockMeta);

    /**
     * Must set a static value inside implementing classes.
     *
     * @param dimID The Dimension ID to store
     */
    public abstract void dimID(int dimID);

    /**
     * Must set a static value inside implementing classes.
     *
     * @param chunkManager The WorldChunkManager to store
     */
    public abstract void chunkManager(WorldChunkManager chunkManager);
}
