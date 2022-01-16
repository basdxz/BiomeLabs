package com.basdxz.biomelabs.item;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.block.Block;
import net.minecraft.item.ItemBlock;
import net.minecraft.item.ItemStack;
import net.minecraft.util.IIcon;

public class WastelandSoilItemBlock extends ItemBlock {
    public WastelandSoilItemBlock(Block block) {
        super(block);
        this.setMaxDamage(0);
        this.setHasSubtypes(true);
    }

    @SideOnly(Side.CLIENT)
    @Override
    public IIcon getIconFromDamage(int damage) {
        return this.field_150939_a.getIcon(0, damage);
    }

    @Override
    public int getMetadata(int meta) {
        return meta;
    }

    public String getUnlocalizedName(ItemStack itemStack) {
        return String.format("%s_%d", field_150939_a.getUnlocalizedName(), itemStack.getItemDamage());
    }
}