package com.ilia_ip.mintindustry;

import com.ilia_ip.mintindustry.entities.EntityInit;
import com.ilia_ip.mintindustry.items.DroneController;
import com.ilia_ip.mintindustry.items.ItemInit;
import com.ilia_ip.mintindustry.sounds.SoundInit;

import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import top.theillusivec4.curios.api.CuriosApi;
import top.theillusivec4.curios.api.type.capability.ICurio.SoundInfo;

@Mod(MintIndustry.MODID)
public class MintIndustry {
    public static final String MODID = "mintindustry";

    public MintIndustry() {
        IEventBus modEventBus = FMLJavaModLoadingContext.get().getModEventBus();

        ItemInit.ITEMS.register(modEventBus);

        ItemInit.CREATIVE_MODE_TABS.register(modEventBus);

        SoundInit.SOUNDS.register(modEventBus);

        MinecraftForge.EVENT_BUS.register(this);

        EntityInit.ENTITIES.register(modEventBus);

        // CuriosApi.registerCurio(ItemInit.DRONE_CONTROLLER.get(),
        // new DroneController());
    }
}
