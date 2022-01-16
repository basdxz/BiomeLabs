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

package com.basdxz.biomelabs.util;

import com.basdxz.biomelabs.world.noise.INoiseGenerator;
import lombok.NoArgsConstructor;
import lombok.val;

import static lombok.AccessLevel.PRIVATE;

@NoArgsConstructor(access = PRIVATE)
public final class NoiseUtil {
    public static double[] chunkNoise(INoiseGenerator noiseGenerator, int blockPosX, int blockPosZ) {
        val chunkNoise = new double[ChunkUtil.CHUNK_AREA];
        ChunkUtil.iterateXZ((x, z) ->
                chunkNoise[ChunkUtil.chunkSliceArrayIndex(x, z)]
                        = noiseGenerator.generate(blockPosX + x, blockPosZ + z));
        return chunkNoise;
    }

    /*
        Tec wrote this masterpiece of a contrast function
     */
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
