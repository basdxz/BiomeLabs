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

import lombok.NoArgsConstructor;

import java.util.Arrays;
import java.util.Random;

import static lombok.AccessLevel.PRIVATE;

@NoArgsConstructor(access = PRIVATE)
public final class RandomUtil {
    public static int randChunkBlockPos(Random random) {
        return randInt(random, 0, 15);
    }

    public static int randInt(Random random, int min, int max) {
        return random.nextInt((max - min) + 1) + min;
    }

    public static long combinedPositionPair(int posX, int posY) {
        return (long) posX << 32 | posY & 0xFFFFFFFFL;
    }

    public static long combinedSeed(long... seeds) {
        return Arrays.stream(seeds).reduce((a, b) -> 31 * a + b).orElse(0);
    }

    public static int longSeedToIntSeed(long seed) {
        return 31 * (int) (seed >> 32) + (int) seed;
    }
}
