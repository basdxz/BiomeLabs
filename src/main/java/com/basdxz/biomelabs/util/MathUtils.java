package com.basdxz.biomelabs.util;

import lombok.NoArgsConstructor;

import static lombok.AccessLevel.PRIVATE;

@NoArgsConstructor(access = PRIVATE)
public final class MathUtils {
    public static double map(double input, double inMin, double inMax, double outMin, double outMax) {
        return (input - inMin) * (outMax - outMin) / (inMax - inMin) + outMin;
    }

    public static double clamp(double input, double min, double max) {
        return Math.max(min, Math.min(max, input));
    }
}
