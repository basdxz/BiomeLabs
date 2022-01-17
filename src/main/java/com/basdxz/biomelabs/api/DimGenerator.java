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
import com.basdxz.biomelabs.item.DimensionTeleportItem;
import com.basdxz.biomelabs.world.biome.BiomeGenBaseSimple;
import com.basdxz.biomelabs.world.chunk.MonoBiomeWorldChunkManager;
import lombok.Builder;
import lombok.NoArgsConstructor;
import lombok.NonNull;
import lombok.val;
import net.minecraft.block.Block;
import net.minecraftforge.common.BiomeDictionary;
import net.minecraftforge.common.BiomeManager;
import net.minecraftforge.common.DimensionManager;

import static lombok.AccessLevel.PRIVATE;

@NoArgsConstructor(access = PRIVATE)
public final class DimGenerator {
    @Builder
    public static DimensionReference createDesolateWasteland(@NonNull String modid,
                                                              @NonNull DesolateWastelandWorldProvider worldProvider,
                                                              int dimID, int biomeID) {
        val topSoilBlock = new WastelandSoilBlock(modid, worldProvider.getDimensionName());
        val topSoilBlockMeta = (byte) 0;
        val fillerSoilBlock = topSoilBlock;
        val fillerSoilBlockMeta = (byte) 1;
        val stoneBlock = topSoilBlock;
        val stonelBlockMeta = (byte) 2;

        val biome = new BiomeGenBaseSimple(biomeID) {
            @Override
            protected String newBiomeName() {
                return worldProvider.getDimensionName();
            }

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

        BiomeDictionary.registerBiomeType(biome);
        BiomeManager.addSpawnBiome(biome);

        worldProvider.stoneBlock(stoneBlock);
        worldProvider.stoneBlockMeta(stonelBlockMeta);
        worldProvider.dimID(dimID);
        worldProvider.chunkManager(new MonoBiomeWorldChunkManager(biome));

        DimensionManager.registerProviderType(dimID, worldProvider.getClass(), false);
        DimensionManager.registerDimension(dimID, dimID);

        val teleportItem = new DimensionTeleportItem(modid, dimID, worldProvider.getDimensionName());

        return DimensionReference.builder()
                .dimName(worldProvider.getDimensionName())
                .dimID(dimID)
                .biome(biome)
                .topSoilBlock(topSoilBlock)
                .topSoilBlockMeta(topSoilBlockMeta)
                .fillerSoilBlock(fillerSoilBlock)
                .fillerSoilBlockMeta(fillerSoilBlockMeta)
                .teleportItem(teleportItem)
                .build();
    }
}
