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

package com.basdxz.biomelabs.world.biome;

import com.basdxz.biomelabs.util.ChunkUtil;
import lombok.val;
import lombok.var;
import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.init.Blocks;
import net.minecraft.world.World;
import net.minecraft.world.biome.BiomeDecorator;
import net.minecraft.world.biome.BiomeGenBase;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public abstract class BiomeGenBaseSimple extends BiomeGenBase {
    protected final static int BEDROCK_THICKNESS = 5;
    protected final static Block DEFAULT_TOP_BLOCK = Blocks.grass;
    protected final static byte DEFAULT_TOP_BLOCK_META = 0;
    protected final static Block DEFAULT_FILLER_BLOCK = Blocks.dirt;
    protected final static byte DEFAULT_FILLER_BLOCK_META = 0;
    protected final static byte DEFAULT_MIN_TERRAIN_HEIGHT = 50;

    protected final List<BiomeBlockLayer> blockLayers;

    public BiomeGenBaseSimple(int biomeID) {
        this(biomeID, true);
    }

    public BiomeGenBaseSimple(int biomeID, boolean doRegister) {
        super(biomeID, doRegister);
        this.spawnableMonsterList.clear();
        this.spawnableCreatureList.clear();
        this.spawnableWaterCreatureList.clear();
        this.spawnableCaveCreatureList.clear();
        this.setHeight(new Height(0.125F, 0.0005F));
        setBiomeName(newBiomeName());
        blockLayers = newBlockLayers();
    }

    protected abstract String newBiomeName();

    protected List<BiomeBlockLayer> newBlockLayers() {
        val blockLayers = new ArrayList<BiomeBlockLayer>();
        blockLayers.add(new BiomeBlockLayer(topBlock(), topBlockMeta(), 1));
        blockLayers.add(new BiomeBlockLayer(fillerBlock(), fillerBlockMeta(), 3, 6));
        return blockLayers;
    }

    protected Block topBlock() {
        return DEFAULT_TOP_BLOCK;
    }

    protected byte topBlockMeta() {
        return DEFAULT_TOP_BLOCK_META;
    }

    protected Block fillerBlock() {
        return DEFAULT_FILLER_BLOCK;
    }

    protected byte fillerBlockMeta() {
        return DEFAULT_FILLER_BLOCK_META;
    }

    @Override
    public BiomeDecorator createBiomeDecorator() {
        return getModdedBiomeDecorator(new BiomeDecoratorSimple());
    }

    @Override
    public void genTerrainBlocks(World world, Random random, Block[] blocks, byte[] blockMetas,
                                 int blockPosX, int blockPosZ, double stoneNoise) {
        generateBedrock(random, blocks, blockPosX, blockPosZ);

        var blockPosY = firstNonAirBlock(blocks, blockPosX, blockPosZ);
        for (BiomeBlockLayer blockLayer : blockLayers) {
            if (minTerrainBlocksY() > blockPosY)
                return;

            val layerThickness = blockLayer.thickness(stoneNoise);
            for (int i = 0; i < layerThickness; i++) {
                if (minTerrainBlocksY() > blockPosY)
                    return;
                blocks[ChunkUtil.chunkBlockArrayIndex(blockPosX, blockPosY, blockPosZ)] = blockLayer.block();
                blockMetas[ChunkUtil.chunkBlockArrayIndex(blockPosX, blockPosY, blockPosZ)] = blockLayer.blockMeta();
                blockPosY--;
            }
        }
    }

    protected void generateBedrock(Random random, Block[] blocks, int blockPosX, int blockPosZ) {
        for (int blockPosY = 0; blockPosY < BEDROCK_THICKNESS; blockPosY++)
            if (blockPosY <= random.nextInt(BEDROCK_THICKNESS))
                blocks[ChunkUtil.chunkBlockArrayIndex(blockPosX, blockPosY, blockPosZ)] = Blocks.bedrock;
    }

    protected int firstNonAirBlock(Block[] blocks, int blockPosX, int blockPosZ) {
        for (var blockPosY = 255; blockPosY >= 0; blockPosY--)
            if (isNonBlockAir(blocks, blockPosX, blockPosY, blockPosZ))
                return blockPosY;
        return -1;
    }

    protected boolean isNonBlockAir(Block[] blocks, int blockPosX, int blockPosY, int blockPosZ) {
        val block = blocks[ChunkUtil.chunkBlockArrayIndex(blockPosX, blockPosY, blockPosZ)];
        return block != null && block.getMaterial() != Material.air;
    }

    protected int minTerrainBlocksY() {
        return DEFAULT_MIN_TERRAIN_HEIGHT;
    }
}
