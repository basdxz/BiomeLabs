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

package com.basdxz.biomelabs.world.provider;

import com.basdxz.biomelabs.render.DarkSky;
import com.basdxz.biomelabs.render.EmptyRenderHandler;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.util.Vec3;
import net.minecraft.world.WorldProvider;
import net.minecraft.world.biome.WorldChunkManager;
import net.minecraftforge.client.IRenderHandler;

public abstract class WorldProviderBase extends WorldProvider {
    @Override
    public void registerWorldChunkManager() {
        worldChunkMgr = newWorldChunkManager();
        dimensionId = newDimensionId();
    }

    protected abstract WorldChunkManager newWorldChunkManager();

    protected abstract int newDimensionId();

    @Override
    public IRenderHandler getSkyRenderer() {
        return DarkSky.INSTANCE();
    }

    @Override
    public IRenderHandler getCloudRenderer() {
        return EmptyRenderHandler.INSTANCE();
    }

    @Override
    public IRenderHandler getWeatherRenderer() {
        return EmptyRenderHandler.INSTANCE();
    }

    @SideOnly(Side.CLIENT)
    public Vec3 getFogColor(float f, float f0) {
        return Vec3.createVectorHelper(0, 0, 0);
    }
}
