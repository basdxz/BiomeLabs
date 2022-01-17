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
import lombok.val;
import lombok.var;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.world.Teleporter;
import net.minecraft.world.World;
import net.minecraft.world.WorldServer;

public class DimensionTeleportItem extends Item {
    protected final int targetDimID;
    protected final String targetDimName;

    public DimensionTeleportItem(String modid, int targetDimID, String targetDimName) {
        this.targetDimID = targetDimID;
        this.targetDimName = targetDimName.toLowerCase();
        setUnlocalizedName(String.format("dim_teleporter_%s", this.targetDimName));
        setTextureName(String.format("%s:%s/%s", modid, this.targetDimName, getUnlocalizedName().substring(5)));
        GameRegistry.registerItem(this, getUnlocalizedName(), modid);
    }

    @Override
    public ItemStack onItemRightClick(ItemStack itemStack, World world, EntityPlayer entityPlayer) {
        if (!world.isRemote && entityPlayer instanceof EntityPlayerMP)
            teleport((EntityPlayerMP) entityPlayer);
        return super.onItemRightClick(itemStack, world, entityPlayer);
    }

    protected void teleport(EntityPlayerMP player) {
        int gotoDimID = 0;
        if (player.dimension == 0) {
            gotoDimID = targetDimID;
        } else if (player.dimension != targetDimID) {
            return;
        }

        player.mcServer.getConfigurationManager().transferPlayerToDimension(player, gotoDimID,
                new LocalTeleporter(player.mcServer.worldServerForDimension(gotoDimID)));
    }

    protected static class LocalTeleporter extends Teleporter {
        protected final WorldServer worldServerInstance;

        public LocalTeleporter(WorldServer world) {
            super(world);
            this.worldServerInstance = world;
        }

        @Override
        public void placeInPortal(Entity entity, double d, double d1, double d2, float d3) {
            val posX = (int) Math.round(entity.posX);
            var posY = (int) Math.round(entity.posY);
            val posZ = (int) Math.round(entity.posZ);

            worldServerInstance.getBlock(posX, posY, posZ); // Used to generate chunk
            posY = worldServerInstance.getHeightValue(posX, posZ) + 1;
            entity.setPosition(entity.posX, posY, entity.posZ);
        }
    }
}
