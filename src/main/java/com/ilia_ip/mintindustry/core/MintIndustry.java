package com.ilia_ip.mintindustry.core;

import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;

@Mod(MintIndustry.MODID)
public class MintIndustry {
    public static final String MODID = "mintindustry";

    public MintIndustry() {
        @SuppressWarnings("all")
        IEventBus modEventBus = FMLJavaModLoadingContext.get().getModEventBus();

        MinecraftForge.EVENT_BUS.register(this);

        ModItems.ITEMS.register(modEventBus);

        ModItems.CREATIVE_MODE_TABS.register(modEventBus);

        ModSounds.SOUNDS.register(modEventBus);

        ModBlockEntities.BLOCK_ENTITIES.register(modEventBus);

        ModBlocks.BLOCKS.register(modEventBus);

        ModEntities.ENTITIES.register(modEventBus);
    }
}
