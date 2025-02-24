package com.ilia_ip.mintindustry.blocks.entities;


import com.ilia_ip.mintindustry.MintIndustry;
import com.ilia_ip.mintindustry.blocks.ModBlocks;

import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

public class ModBlockEntities {
    public static final DeferredRegister<BlockEntityType<?>> BLOCK_ENTITIES = DeferredRegister.create(ForgeRegistries.BLOCK_ENTITY_TYPES, MintIndustry.MODID);

    public static final RegistryObject<BlockEntityType<DroneStationEntity>> DRONE_STATION_ENTITY = BLOCK_ENTITIES.register("drone_station", 
            () -> BlockEntityType.Builder.of(DroneStationEntity::new, ModBlocks.DRONE_STATION.get()).build(null));
}
