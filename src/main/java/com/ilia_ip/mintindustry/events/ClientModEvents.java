package com.ilia_ip.mintindustry.events;

import com.ilia_ip.mintindustry.MintIndustry;
import com.ilia_ip.mintindustry.client.model.DroneEntityModel;
import com.ilia_ip.mintindustry.client.renderer.DroneEntityRenderer;
import com.ilia_ip.mintindustry.entities.EntityInit;

import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.client.event.EntityRenderersEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

@Mod.EventBusSubscriber(modid = MintIndustry.MODID, bus = Mod.EventBusSubscriber.Bus.MOD, value = Dist.CLIENT)
public class ClientModEvents {
    @SubscribeEvent
    public static void registerRenderers(EntityRenderersEvent.RegisterRenderers event) {
        event.registerEntityRenderer(EntityInit.DRONE_ENTITY.get(), DroneEntityRenderer::new);
    }

    @SubscribeEvent
    public static void registerLayerDefenitions(EntityRenderersEvent.RegisterLayerDefinitions event) {
        event.registerLayerDefinition(DroneEntityModel.LAYER_LOCATION, DroneEntityModel::createBodyLayer);
    }
}
