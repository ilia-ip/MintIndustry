package com.ilia_ip.mintindustry.core;


import com.ilia_ip.mintindustry.blockentities.DroneStationEntity;

import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

public class ModBlockEntities {
    public static final DeferredRegister<BlockEntityType<?>> BLOCK_ENTITIES = DeferredRegister.create(ForgeRegistries.BLOCK_ENTITY_TYPES, MintIndustry.MODID);

    public static final RegistryObject<BlockEntityType<DroneStationEntity>> DRONE_STATION_ENTITY = BLOCK_ENTITIES.register("drone_station_entity", 
            () -> BlockEntityType.Builder.of(DroneStationEntity::new, ModBlocks.DRONE_STATION.get()).build(null));
}
