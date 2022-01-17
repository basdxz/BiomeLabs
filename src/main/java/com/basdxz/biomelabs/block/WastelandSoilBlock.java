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

package com.basdxz.biomelabs.block;

import com.basdxz.biomelabs.item.WastelandSoilItemBlock;
import cpw.mods.fml.common.registry.GameRegistry;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.IIcon;
import net.minecraftforge.common.util.ForgeDirection;

import java.text.MessageFormat;
import java.util.List;

public class WastelandSoilBlock extends Block {
    protected final String dimName;
    protected final String modid;

    protected IIcon soilTop;
    protected IIcon soilSide;
    protected IIcon dirt;
    protected IIcon stone;

    public WastelandSoilBlock(String modid, String dimName) {
        super(Material.ground);
        this.dimName = dimName.toLowerCase();
        this.modid = modid;
        setBlockName(String.format("%s_soil", this.dimName));
        GameRegistry.registerBlock(this, WastelandSoilItemBlock.class, getUnlocalizedName());
    }

    @SuppressWarnings("unchecked")
    @Override
    @SideOnly(Side.CLIENT)
    public void getSubBlocks(Item item, CreativeTabs creativeTabs, List itemList) {
        itemList.add(new ItemStack(item, 1, 0));
        itemList.add(new ItemStack(item, 1, 1));
        itemList.add(new ItemStack(item, 1, 2));
    }

    @Override
    public int damageDropped(int meta) {
        return meta;
    }

    @Override
    @SideOnly(Side.CLIENT)
    public void registerBlockIcons(IIconRegister iconRegister) {
        soilTop = registerIcon(iconRegister, "soil_top");
        soilSide = registerIcon(iconRegister, "soil_side");
        dirt = registerIcon(iconRegister, "dirt");
        stone = registerIcon(iconRegister, "stone");
    }

    protected IIcon registerIcon(IIconRegister iconRegister, String iconName) {
        return iconRegister.registerIcon(MessageFormat.format("{0}:{1}/{1}_{2}", modid, dimName, iconName));
    }

    @Override
    @SideOnly(Side.CLIENT)
    public IIcon getIcon(int side, int meta) {
        if (meta == 0) {
            if (side == ForgeDirection.UP.ordinal())
                return soilTop;
            if (side == ForgeDirection.DOWN.ordinal())
                return dirt;
            return soilSide;
        } else if (meta == 1) {
            return dirt;
        } else {
            return stone;
        }
    }
}
