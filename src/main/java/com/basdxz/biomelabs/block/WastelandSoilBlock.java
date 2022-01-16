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

import java.text.MessageFormat;
import java.util.List;

public class WastelandSoilBlock extends Block {
    protected final String dimName;
    protected final String modid;

    protected IIcon topSoil;
    protected IIcon bottomSoil;

    public WastelandSoilBlock(String dimName, String modid) {
        super(Material.ground);
        this.dimName = dimName;
        this.modid = modid;
        setBlockName(String.format("%s_soil", dimName.toLowerCase()));
        GameRegistry.registerBlock(this, WastelandSoilItemBlock.class, getUnlocalizedName());
    }

    @SuppressWarnings("unchecked")
    @Override
    @SideOnly(Side.CLIENT)
    public void getSubBlocks(Item item, CreativeTabs creativeTabs, List itemList) {
        itemList.add(new ItemStack(item, 1, 0));
        itemList.add(new ItemStack(item, 1, 1));
    }

    @Override
    public int damageDropped(int meta) {
        return meta;
    }

    @Override
    @SideOnly(Side.CLIENT)
    public void registerBlockIcons(IIconRegister iconRegister) {
        bottomSoil = iconRegister.registerIcon(MessageFormat.format("{0}:{1}/{1}_bottomSoil", modid, dimName));
        topSoil = iconRegister.registerIcon(MessageFormat.format("{0}:{1}/{1}_topSoil", modid, dimName));
    }

    @Override
    public IIcon getIcon(int side, int meta) {
        return meta > 0 ? bottomSoil : topSoil;
    }
}
