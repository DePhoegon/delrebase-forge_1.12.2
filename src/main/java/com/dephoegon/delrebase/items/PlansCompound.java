package com.dephoegon.delrebase.items;

import com.dephoegon.delrebase.DelReBase;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.Item;

public class PlansCompound extends Item {
    public PlansCompound(String name) {
        setTranslationKey(name);
        setRegistryName(DelReBase.MOD_ID, name);
        setCreativeTab(CreativeTabs.MISC);
        setMaxStackSize(64);
    }
}