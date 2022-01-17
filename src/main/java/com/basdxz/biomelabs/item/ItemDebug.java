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

package com.basdxz.biomelabs.item;

import cpw.mods.fml.common.registry.GameRegistry;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.item.ItemShears;
import net.minecraft.item.ItemStack;
import net.minecraft.util.MathHelper;
import net.minecraft.world.World;
import net.minecraft.world.WorldServer;


public class ItemDebug extends ItemShears {
    public ItemDebug() {
        GameRegistry.registerItem(this, getUnlocalizedName());
    }

    @Override
    public ItemStack onItemRightClick(ItemStack itemStack, World world, EntityPlayer entityPlayer) {
        if (world.isRemote)
            return super.onItemRightClick(itemStack, world, entityPlayer);

        // obviously make sure you are server side, then get the world server's default teleporter:
        ((EntityPlayerMP) entityPlayer).mcServer.getConfigurationManager().transferPlayerToDimension(
                (EntityPlayerMP) entityPlayer, 2, ((WorldServer) world).getDefaultTeleporter());

        // get the height value so you don't get stuck in solid blocks or worse, in the void
        double dy = entityPlayer.worldObj.getHeightValue(
                MathHelper.floor_double(entityPlayer.posX), MathHelper.floor_double(entityPlayer.posZ));

        // still seem to need to set the position, +1 so you don't get in the void
        entityPlayer.setPositionAndUpdate(entityPlayer.posX, dy + 1, entityPlayer.posZ);
        return super.onItemRightClick(itemStack, world, entityPlayer);
    }
}
