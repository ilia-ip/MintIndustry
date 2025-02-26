package com.ilia_ip.mintindustry.core;

import com.ilia_ip.mintindustry.blocks.DroneStation;

import net.minecraft.world.level.block.Block;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

public class ModBlocks {
    public static final DeferredRegister<Block> BLOCKS = DeferredRegister.create(ForgeRegistries.BLOCKS, MintIndustry.MODID);

    public static final RegistryObject<Block> DRONE_STATION = BLOCKS.register("drone_station", () -> new DroneStation());
}
