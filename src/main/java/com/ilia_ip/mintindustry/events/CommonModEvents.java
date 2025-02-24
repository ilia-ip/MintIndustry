package com.ilia_ip.mintindustry.events;

import com.ilia_ip.mintindustry.MintIndustry;
import com.ilia_ip.mintindustry.entities.ModEntities;
import com.ilia_ip.mintindustry.entities.drone.DroneEntity;

import net.minecraftforge.event.entity.EntityAttributeCreationEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

@Mod.EventBusSubscriber(modid = MintIndustry.MODID, bus = Mod.EventBusSubscriber.Bus.MOD)
public class CommonModEvents {
    @SubscribeEvent
    public static void entityAttributes(EntityAttributeCreationEvent event) {
        event.put(ModEntities.DRONE_ENTITY.get(), DroneEntity.createMobAttributes().build());
    }

    /*
     * @SubscribeEvent
     * public static void registerSpawnPlacements(SpawnPlacementRegisterEvent event)
     * {
     * event.register(EntityInit.DRONE_ENTITY.get(), SpawnPlacements.Type.ON_GROUND,
     * Heightmap.Types.WORLD_SURFACE,
     * DroneEntity::canSpawn, SpawnPlacementRegisterEvent.Operation.OR);
     * }
     */
}
