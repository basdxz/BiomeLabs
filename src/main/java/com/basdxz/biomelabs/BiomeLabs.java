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

package com.basdxz.biomelabs;

import com.basdxz.biomelabs.api.DimGenerator;
import com.basdxz.biomelabs.api.DimensionReference;
import com.basdxz.biomelabs.api.ExampleWorldProvider;
import com.basdxz.biomelabs.item.ItemDebug;
import cpw.mods.fml.common.Mod;
import cpw.mods.fml.common.event.FMLInitializationEvent;

import static com.basdxz.biomelabs.Tags.*;

@Mod(modid = MODID, version = VERSION, name = MODNAME, acceptedMinecraftVersions = "[1.7.10]")
public class BiomeLabs {
    public static DimensionReference woag;

    @Mod.EventHandler
    public void init(FMLInitializationEvent event) {
        woag = DimGenerator.createDesolateWasteland(MODID, new ExampleWorldProvider(), 2, 173);
        new ItemDebug();
    }
}
