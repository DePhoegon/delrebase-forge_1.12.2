package com.dephoegon.delrebase.items;

import com.dephoegon.delrebase.DelReBase;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.Item;

public class PlansItem extends Item {

    public PlansItem(String name) {
        setTranslationKey(name);
        setRegistryName(DelReBase.MOD_ID, name);
        setCreativeTab(CreativeTabs.MISC);
        setMaxStackSize(1);
    }
}
