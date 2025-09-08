package com.dephoegon.delrebase.init;

import com.dephoegon.delrebase.items.PlansCompound;
import com.dephoegon.delrebase.items.PlansItem;
import net.minecraft.item.Item;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

@Mod.EventBusSubscriber
public class ModItems {
    public static final Item WALL_PLANS = new PlansItem("wall_plans");
    public static final Item SLAB_PLANS = new PlansItem("slab_plans");
    public static final Item FENCE_PLANS = new PlansItem("fence_plans");
    public static final Item FENCE_GATE_PLANS = new PlansItem("fence_gate_plans");
    public static final Item STAIR_PLANS = new PlansItem("stair_plans");
    public static final Item ARMOR_COMPOUND = new PlansCompound("armor_compound");

    public static final Item [] PLAN_ITEMS = {
            WALL_PLANS,
            SLAB_PLANS,
            FENCE_PLANS,
            FENCE_GATE_PLANS,
            STAIR_PLANS,
            ARMOR_COMPOUND
    };
    @SubscribeEvent
    public static void registerItems(net.minecraftforge.event.RegistryEvent.Register<Item> event) {
        for (Item item : PLAN_ITEMS) {
            event.getRegistry().register(item);
        }
    }
}