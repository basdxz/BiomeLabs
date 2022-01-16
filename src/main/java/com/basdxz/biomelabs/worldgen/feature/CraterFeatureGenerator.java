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

public class CraterFeatureGenerator extends ChunkFeatureGenerator {
    protected final int minRadius;
    protected final int maxRadius;

    public CraterFeatureGenerator(float generationChance, int minRadius, int maxRadius) {
        super(random -> random.nextFloat() <= generationChance);
        this.minRadius = minRadius;
        this.maxRadius = maxRadius;
    }

    @Override
    protected int maxChunkRadius() {
        return ChunkUtil.blockPosToChunkPos(maxRadius) + 1;
    }

    @Override
    protected IWorldFeature newWorldFeature() {
        return CraterFeature.newCraterFeature(
                random, offsetChunkPos, minRadius, maxRadius, 5, 10, 1, 5);
    }
}
