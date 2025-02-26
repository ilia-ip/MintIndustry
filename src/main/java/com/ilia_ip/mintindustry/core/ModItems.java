package com.ilia_ip.mintindustry.core;

import com.ilia_ip.mintindustry.items.DroneController;

import net.minecraft.core.registries.Registries;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.Item;
import net.minecraftforge.common.ForgeSpawnEggItem;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

public class ModItems {
        public static final DeferredRegister<Item> ITEMS = DeferredRegister.create(ForgeRegistries.ITEMS,
                        MintIndustry.MODID);

        public static final DeferredRegister<CreativeModeTab> CREATIVE_MODE_TABS = DeferredRegister
                        .create(Registries.CREATIVE_MODE_TAB, MintIndustry.MODID);

        public static final RegistryObject<Item> DRONE_ITEM = ITEMS.register("drone_item",
                        () -> new ForgeSpawnEggItem(ModEntities.DRONE_ENTITY, 0, 0,
                                        new Item.Properties().fireResistant()));

        public static final RegistryObject<Item> DRONE_CONTROLLER = ITEMS.register("drone_controller",
                        () -> new DroneController());

        public static final RegistryObject<BlockItem> DRONE_STATION_ITEM = ITEMS.register("drone_station", () -> new BlockItem(ModBlocks.DRONE_STATION.get(), new Item.Properties()));

        public static final RegistryObject<CreativeModeTab> CREATIVE_TAB = CREATIVE_MODE_TABS.register("mintidustry",
                        () -> CreativeModeTab.builder()
                                        .icon(() -> DRONE_ITEM.get().getDefaultInstance())
                                        .displayItems((parameters, output) -> {
                                                output.accept(DRONE_ITEM.get());
                                                output.accept(DRONE_CONTROLLER.get());
                                                output.accept(DRONE_STATION_ITEM.get());
                                        }).build());
}