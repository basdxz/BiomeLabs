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

package com.basdxz.biomelabs.worldgen.chunk;

import com.basdxz.biomelabs.util.ChunkUtil;
import com.basdxz.biomelabs.util.MathUtils;
import com.basdxz.biomelabs.util.NoiseUtil;
import com.basdxz.biomelabs.util.RandomUtil;
import com.basdxz.biomelabs.worldgen.noise.Contrast;
import com.basdxz.biomelabs.worldgen.noise.INoiseGenerator;
import cpw.mods.fml.common.eventhandler.Event;
import lombok.val;
import net.minecraft.block.Block;
import net.minecraft.block.BlockFalling;
import net.minecraft.entity.EnumCreatureType;
import net.minecraft.init.Blocks;
import net.minecraft.util.IProgressUpdate;
import net.minecraft.world.ChunkPosition;
import net.minecraft.world.World;
import net.minecraft.world.biome.BiomeGenBase;
import net.minecraft.world.chunk.Chunk;
import net.minecraft.world.chunk.IChunkProvider;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.event.terraingen.ChunkProviderEvent;
import net.minecraftforge.event.terraingen.PopulateChunkEvent;
import org.spongepowered.noise.module.Module;
import org.spongepowered.noise.module.modifier.Range;
import org.spongepowered.noise.module.source.Perlin;

import java.util.Collections;
import java.util.List;
import java.util.Random;

public abstract class ChunkGeneratorBase implements IChunkProvider {
    public static final double DEFAULT_NOISE_FREQUENCY = ChunkUtil.CHUNK_LENGTH_WIDTH_INV * 0.1D;
    public static final double DEFAULT_NOISE_CONTRAST = 3D;
    public static final int DEFAULT_MIN_BLOCK_HEIGHT = 60;
    public static final int DEFAULT_MAX_BLOCK_HEIGHT = 80;
    public static final Block DEFAULT_STONE_BLOCK = Blocks.stone;
    public static final byte DEFAULT_STONE_BLOCK_META = 0;

    protected final World world;
    protected final Random random;
    protected final INoiseGenerator noiseGenerator;

    protected BiomeGenBase[] biomes;

    public ChunkGeneratorBase(World world) {
        this.world = world;
        random = new Random(0L);
        noiseGenerator = newNoiseGenerator();
    }

    public INoiseGenerator newNoiseGenerator() {
        val perlin = new Perlin();
        perlin.setSeed(RandomUtil.longSeedToIntSeed(RandomUtil.combinedSeed(world.getSeed(), seedOffset())));
        perlin.setFrequency(noiseFrequency());
        val range = new Range(perlin);
        range.setBounds(0, perlin.getMaxValue(), 0, 1);
        val contrast = new Contrast(range);
        contrast.setStrength(noiseContrast());
        return new INoiseGenerator() {
            private final Module noiseGenerator = contrast;

            @Override
            public double generate(double posX, double posZ) {
                return noiseGenerator.getValue(posX, 0, posZ);
            }
        };
    }

    public double noiseFrequency() {
        return DEFAULT_NOISE_FREQUENCY;
    }

    public double noiseContrast() {
        return DEFAULT_NOISE_CONTRAST;
    }

    @Override
    public Chunk loadChunk(int chunkPosX, int chunkPosZ) {
        return provideChunk(chunkPosX, chunkPosZ);
    }

    @Override
    public Chunk provideChunk(int chunkPosX, int chunkPosZ) {
        val blockPosX = ChunkUtil.chunkPosToBlockPos(chunkPosX);
        val blockPosZ = ChunkUtil.chunkPosToBlockPos(chunkPosZ);
        val blocks = ChunkUtil.newChunkBlockArray();
        val blockMetas = ChunkUtil.newChunkBlockMetasArray();
        //NOTE: This noise is scaled 0D to 1.0D instead of the vanilla -6.1D to 6.7D
        val stoneNoise = NoiseUtil.chunkNoise(noiseGenerator, blockPosX, blockPosZ);

        seedRandom(chunkPosX, chunkPosZ);
        fillStone(blocks, blockMetas, stoneNoise);
        replaceBlocksForBiome(chunkPosX, chunkPosZ, blockPosX, blockPosZ, blocks, blockMetas, stoneNoise);

        val chunk = new Chunk(world, blocks, blockMetas, chunkPosX, chunkPosZ);
        chunk.generateSkylightMap();
        return chunk;
    }

    public void seedRandom(int chunkPosX, int chunkPosZ) {
        random.setSeed(RandomUtil.combinedSeed(world.getSeed(), seedOffset(),
                RandomUtil.combinedPositionPair(chunkPosX, chunkPosZ)));
    }

    public abstract long seedOffset();

    public void fillStone(Block[] blocks, byte[] blockMetas, double[] stoneNoise) {
        ChunkUtil.iterateXZ((x, z) -> {
            val stoneHeight = stoneHeight(stoneNoise[ChunkUtil.chunkSliceArrayIndex(x, z)]);
            for (int y = 0; y <= stoneHeight; y++) {
                val arrayIndex = ChunkUtil.chunkBlockArrayIndex(x, y, z);
                blocks[arrayIndex] = stoneBlock();
                blockMetas[arrayIndex] = stoneBlockMeta();
            }
        });
    }

    public int stoneHeight(double noise) {
        return (int) Math.round(MathUtils.map(noise, 0, 1, minBlockHeight(), maxBlockHeight()));
    }

    public int minBlockHeight() {
        return DEFAULT_MIN_BLOCK_HEIGHT;
    }

    public int maxBlockHeight(){
        return DEFAULT_MAX_BLOCK_HEIGHT;
    }

    public Block stoneBlock(){
        return DEFAULT_STONE_BLOCK;
    }

    public byte stoneBlockMeta(){
        return DEFAULT_STONE_BLOCK_META;
    }

    public void replaceBlocksForBiome(int chunkPosX, int chunkPosZ, int blockPosX, int blockPosZ, Block[] blocks, byte[] blockMetas, double[] stoneNoise) {
        biomes = world.getWorldChunkManager().loadBlockGeneratorData(biomes, blockPosX, blockPosZ, ChunkUtil.CHUNK_LENGTH_WIDTH, ChunkUtil.CHUNK_LENGTH_WIDTH);
        val event = new ChunkProviderEvent.ReplaceBiomeBlocks(this, chunkPosX, chunkPosZ, blocks, blockMetas, biomes, world);
        MinecraftForge.EVENT_BUS.post(event);
        if (event.getResult() == Event.Result.DENY)
            return;

        ChunkUtil.iterateXZ((x, z) -> biomes[ChunkUtil.chunkSliceArrayIndex(x, z)].genTerrainBlocks(world, random, blocks, blockMetas, blockPosX + x, blockPosZ + z, stoneNoise[ChunkUtil.chunkSliceArrayIndex(x, z)]));
    }

    @Override
    public void populate(IChunkProvider chunkProvider, int chunkPosX, int chunkPosZ) {
        BlockFalling.fallInstantly = true;
        val blockPosX = ChunkUtil.chunkPosToBlockPos(chunkPosX);
        val blockPosZ = ChunkUtil.chunkPosToBlockPos(chunkPosZ);

        BiomeGenBase biomegenbase = world.getBiomeGenForCoords(blockPosX + 16, blockPosZ + 16);

        MinecraftForge.EVENT_BUS.post(new PopulateChunkEvent.Pre(chunkProvider, world, random, chunkPosX, chunkPosZ, false));

        biomegenbase.decorate(world, random, blockPosX, blockPosZ);

        MinecraftForge.EVENT_BUS.post(new PopulateChunkEvent.Post(chunkProvider, world, random, chunkPosX, chunkPosZ, false));
        BlockFalling.fallInstantly = false;
    }

    @Override
    public boolean chunkExists(int chunkPosX, int chunkPosZ) {
        return true;
    }

    @Override
    public boolean saveChunks(boolean allChunks, IProgressUpdate progressUpdate) {
        return true;
    }

    @Override
    public void saveExtraData() {
    }

    @Override
    public boolean unloadQueuedChunks() {
        return false;
    }

    @Override
    public boolean canSave() {
        return true;
    }

    // abstract
    @Override
    public String makeString() {
        return generatorName() + "LevelSource";
    }

    public abstract String generatorName();

    @SuppressWarnings("rawtypes")
    @Override
    public List getPossibleCreatures(EnumCreatureType creatureType, int blockPosX, int blockPosY, int blockPosZ) {
        return Collections.emptyList();
    }

    @Override
    public ChunkPosition func_147416_a(World world, String structureTypeString, int blockPosX, int blockPosY, int blockPosZ) {
        return getNearestStructure(world, structureTypeString, blockPosX, blockPosY, blockPosZ);
    }

    public ChunkPosition getNearestStructure(World world, String structureTypeString, int blockPosX, int blockPosY, int blockPosZ) {
        return null;
    }

    @Override
    public int getLoadedChunkCount() {
        return 0;
    }

    @Override
    public void recreateStructures(int chunkPosX, int chunkPosZ) {
    }
}
