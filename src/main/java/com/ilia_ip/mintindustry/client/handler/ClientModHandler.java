package com.ilia_ip.mintindustry.client.handler;

import com.ilia_ip.mintindustry.core.MintIndustry;
import com.ilia_ip.mintindustry.core.ModKeybindings;

import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.client.event.RegisterKeyMappingsEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

@Mod.EventBusSubscriber(modid = MintIndustry.MODID, bus = Mod.EventBusSubscriber.Bus.MOD, value = Dist.CLIENT)
public class ClientModHandler {
    @SubscribeEvent
    public static void registerKeys(RegisterKeyMappingsEvent event) {
        event.register(ModKeybindings.INSTANCE.controllerMenuKey);
    }
}
