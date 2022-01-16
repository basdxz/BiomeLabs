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

import net.minecraft.world.biome.BiomeDecorator;
import net.minecraft.world.biome.BiomeGenBase;

public class BiomeDecoratorSimple extends BiomeDecorator {
    // Disables all the existing generation
    public BiomeDecoratorSimple() {
        this.clayGen = null;
        this.sandGen = null;
        this.gravelAsSandGen = null;
        this.dirtGen = null;
        this.gravelGen = null;
        this.coalGen = null;
        this.ironGen = null;
        this.goldGen = null;
        this.redstoneGen = null;
        this.diamondGen = null;
        this.lapisGen = null;
        this.yellowFlowerGen = null;
        this.mushroomBrownGen = null;
        this.mushroomRedGen = null;
        this.bigMushroomGen = null;
        this.reedGen = null;
        this.cactusGen = null;
        this.waterlilyGen = null;
        this.flowersPerChunk = 0;
        this.grassPerChunk = 0;
        this.sandPerChunk = 0;
        this.sandPerChunk2 = 0;
        this.clayPerChunk = 0;
        this.generateLakes = false;
    }

    // TODO: Allow for external decorations from forge bus
    @Override
    protected void genDecorations(BiomeGenBase biomeGenBase) {
    }

    // TODO: Fix Oregen in dims?
    @Override
    protected void generateOres() {
    }
}
