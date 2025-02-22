package com.ilia_ip.mintindustry;

import com.ilia_ip.mintindustry.entities.EntityInit;
import com.ilia_ip.mintindustry.items.ItemInit;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;

@Mod(MintIndustry.MODID)
public class MintIndustry {
    public static final String MODID = "mintindustry";

    public MintIndustry() {
        IEventBus modEventBus = FMLJavaModLoadingContext.get().getModEventBus();

        ItemInit.ITEMS.register(modEventBus);

        ItemInit.CREATIVE_MODE_TABS.register(modEventBus);

        MinecraftForge.EVENT_BUS.register(this);

        EntityInit.ENTITIES.register(modEventBus);
    }
}
