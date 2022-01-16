package com.basdxz.biomelabs;

import cpw.mods.fml.common.Mod;
import cpw.mods.fml.common.event.FMLInitializationEvent;

@Mod(modid = Tags.MODID, version = Tags.VERSION, name = Tags.MODNAME, acceptedMinecraftVersions = "[1.7.10]")
public class BiomeLabs {
    @Mod.EventHandler
    public void init(FMLInitializationEvent event) {
        System.out.println("BiomeLabs Loaded!");
    }
}
