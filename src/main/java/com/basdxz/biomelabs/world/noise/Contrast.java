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

package com.basdxz.biomelabs.world.noise;

import com.basdxz.biomelabs.util.NoiseUtil;
import lombok.val;
import org.spongepowered.noise.exception.NoModuleException;
import org.spongepowered.noise.module.Module;

/**
 * Contrast module that with positive
 *
 * @sourceModules 1
 */
public class Contrast extends Module {
    public static final double DEFAULT_STRENGTH = 1D;

    protected double strength = Contrast.DEFAULT_STRENGTH;

    public Contrast() {
        super(1);
    }

    /**
     * Create a new Contrast module with the source modules pre-configured.
     *
     * @param source the input module
     */
    public Contrast(final Module source) {
        this();
        this.setSourceModule(0, source);
    }

    /**
     * Get the strength factor for this noise module.
     *
     * @return the strength factor for this noise module.
     */
    public double getStrength() {
        return this.strength;
    }

    /**
     * Set the strength factor for this noise module.
     *
     * For best results, use values between -10 and 10.
     *
     * Although higher values are possible, going under -1000 or over 1000 may produce weird results.
     *
     * @param strength the strength factor for this noise module.
     */
    public void setStrength(final double strength) {
        this.strength = strength;
    }

    //TODO replace that 0D with 'close enough to 0D'
    @Override
    public double getValue(final double x, final double y, final double z) {
        if (this.sourceModule[0] == null)
            throw new NoModuleException(0);
        val value = this.sourceModule[0].getValue(x, y, z);
        return strength == 0D ? value : NoiseUtil.contrast(value, strength);
    }
}
