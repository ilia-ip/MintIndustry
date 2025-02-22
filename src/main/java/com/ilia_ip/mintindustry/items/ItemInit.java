package com.ilia_ip.mintindustry.items;

import com.ilia_ip.mintindustry.MintIndustry;
import com.ilia_ip.mintindustry.entities.EntityInit;

import net.minecraft.core.registries.Registries;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.Item;
import net.minecraftforge.common.ForgeSpawnEggItem;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

public class ItemInit {
        public static final DeferredRegister<Item> ITEMS = DeferredRegister.create(ForgeRegistries.ITEMS,
                        MintIndustry.MODID);

        public static final DeferredRegister<CreativeModeTab> CREATIVE_MODE_TABS = DeferredRegister
                        .create(Registries.CREATIVE_MODE_TAB, MintIndustry.MODID);

        public static final RegistryObject<Item> DRONE_ITEM = ITEMS.register("drone_item",
                        () -> new ForgeSpawnEggItem(EntityInit.DRONE_ENTITY, 0, 0, new Item.Properties()));

        public static final RegistryObject<CreativeModeTab> CREATIVE_TAB = CREATIVE_MODE_TABS.register("mintidustry",
                        () -> CreativeModeTab.builder()
                                        .icon(() -> DRONE_ITEM.get().getDefaultInstance())
                                        .displayItems((parameters, output) -> {
                                                output.accept(DRONE_ITEM.get());
                                        }).build());

}
