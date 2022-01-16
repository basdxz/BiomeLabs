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

import com.basdxz.biomelabs.block.WastelandSoilBlock;
import com.basdxz.biomelabs.test.DesolateWastelandWorldProvider;
import com.basdxz.biomelabs.world.biome.BiomeGenBaseSimple;
import com.basdxz.biomelabs.world.chunk.MonoBiomeWorldChunkManager;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.NoArgsConstructor;
import lombok.val;
import net.minecraft.block.Block;
import net.minecraftforge.common.DimensionManager;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public final class DimGenerator {
    @Builder
    public static IDimensionReference createDesolateWasteland(String modid,
                                                              DesolateWastelandWorldProvider worldProvider,
                                                              int dimID, int biomeID) {
        val biome = new BiomeGenBaseSimple(biomeID) {
            private Block topSoilBlock = new WastelandSoilBlock(modid, worldProvider.getDimensionName());
            private byte topSoilBlockMeta = (byte) 0;
            private Block fillerSoilBlock = topSoilBlock;
            private byte fillerSoilBlockMeta = (byte) 1;

            protected Block topBlock() {
                return topSoilBlock;
            }

            protected byte topBlockMeta() {
                return topSoilBlockMeta;
            }

            protected Block fillerBlock() {
                return fillerSoilBlock;
            }

            protected byte fillerBlockMeta() {
                return fillerSoilBlockMeta;
            }
        };

        worldProvider.dimID(dimID);
        worldProvider.chunkManager(new MonoBiomeWorldChunkManager(biome));

        DimensionManager.registerProviderType(dimID, worldProvider.getClass(), false);
        DimensionManager.registerDimension(dimID, dimID);

        return new IDimensionReference() {
            @Override
            public String dimName() {
                return worldProvider.getDimensionName();
            }

            @Override
            public int dimID() {
                return dimID;
            }

            @Override
            public int[] biomeIDs() {
                return new int[]{biomeID};
            }
        };
    }
}
