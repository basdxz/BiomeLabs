package com.basdxz.biomelabs.render;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import lombok.Getter;
import lombok.NoArgsConstructor;
import net.minecraft.client.Minecraft;
import net.minecraft.client.multiplayer.WorldClient;
import net.minecraft.client.renderer.OpenGlHelper;
import net.minecraft.client.renderer.RenderHelper;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.client.IRenderHandler;
import org.lwjgl.opengl.GL11;

import static lombok.AccessLevel.PRIVATE;

@NoArgsConstructor(access = PRIVATE)
public final class DarkSky extends IRenderHandler {
    @Getter
    private static final IRenderHandler INSTANCE = new DarkSky();
    private static final ResourceLocation locationEndSkyPng = new ResourceLocation("textures/environment/end_sky.png");

    @Override
    @SideOnly(Side.CLIENT)
    public void render(float partialTicks, WorldClient world, Minecraft minecraft) {
        GL11.glDisable(GL11.GL_FOG);
        GL11.glDisable(GL11.GL_ALPHA_TEST);
        GL11.glEnable(GL11.GL_BLEND);
        OpenGlHelper.glBlendFunc(770, 771, 1, 0);
        RenderHelper.disableStandardItemLighting();
        GL11.glDepthMask(false);
        minecraft.getTextureManager().bindTexture(locationEndSkyPng);

        for (int i = 0; i < 6; ++i) {
            GL11.glPushMatrix();

            if (i == 1) {
                GL11.glRotatef(90.0F, 1.0F, 0.0F, 0.0F);
            }

            if (i == 2) {
                GL11.glRotatef(-90.0F, 1.0F, 0.0F, 0.0F);
            }

            if (i == 3) {
                GL11.glRotatef(180.0F, 1.0F, 0.0F, 0.0F);
            }

            if (i == 4) {
                GL11.glRotatef(90.0F, 0.0F, 0.0F, 1.0F);
            }

            if (i == 5) {
                GL11.glRotatef(-90.0F, 0.0F, 0.0F, 1.0F);
            }

            Tessellator.instance.startDrawingQuads();
            Tessellator.instance.setColorOpaque_I(0x050505);
            Tessellator.instance.addVertexWithUV(-100.0D, -100.0D, -100.0D, 0.0D, 0.0D);
            Tessellator.instance.addVertexWithUV(-100.0D, -100.0D, 100.0D, 0.0D, 16.0D);
            Tessellator.instance.addVertexWithUV(100.0D, -100.0D, 100.0D, 16.0D, 16.0D);
            Tessellator.instance.addVertexWithUV(100.0D, -100.0D, -100.0D, 16.0D, 0.0D);
            Tessellator.instance.draw();
            GL11.glPopMatrix();
        }

        GL11.glDepthMask(true);
        GL11.glEnable(GL11.GL_TEXTURE_2D);
        GL11.glEnable(GL11.GL_ALPHA_TEST);
    }
}