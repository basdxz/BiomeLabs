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

package com.basdxz.biomelabs.worldgen.biome;

import com.basdxz.biomelabs.util.MathUtils;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.experimental.Accessors;
import net.minecraft.block.Block;

@Data
@Accessors(fluent = true)
@AllArgsConstructor
public class BlockLayer {
    protected final Block block;
    protected final byte blockMeta;
    protected final int minThickness;
    protected final int maxThickness;

    public BlockLayer(Block block, byte blockMeta, int thickness) {
        this.block = block;
        this.blockMeta = blockMeta;
        this.minThickness = thickness;
        this.maxThickness = thickness;
    }

    // TODO: Make layers have a little extra variation
    // Expects noise 0.0 to 1.0
    public int thickness(double noise) {
        if (minThickness == maxThickness)
            return minThickness;
        return (int) Math.round(MathUtils.map(noise, 0, 1, minThickness, maxThickness));
    }
}
