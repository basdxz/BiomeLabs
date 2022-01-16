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
