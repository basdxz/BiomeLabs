package com.basdxz.biomelabs.util;

import lombok.NoArgsConstructor;
import lombok.val;
import org.spongepowered.noise.module.Module;

import static lombok.AccessLevel.PRIVATE;

@NoArgsConstructor(access = PRIVATE)
public final class NoiseUtil {
    public static double[] chunkNoise(Module noiseGenerator, int blockPosX, int blockPosZ) {
        val chunkNoise = new double[ChunkUtil.CHUNK_AREA];
        ChunkUtil.iterateXZ((x, z) ->
                chunkNoise[ChunkUtil.chunkSliceArrayIndex(x, z)]
                        = noiseGenerator.getValue(blockPosX + x, 0, blockPosZ + z));
        return chunkNoise;
    }

    public static double contrast(final double value, double strength) {
        strength *= -1;
        if (strength >= 0) {
            strength++;
        } else {
            strength = 1 / (1 - strength);
        }
        return Math.signum(value - 0.5) * (Math.pow(Math.abs(value - 0.5), strength) / Math.pow(0.5, strength - 1)) + 0.5;
    }
}
