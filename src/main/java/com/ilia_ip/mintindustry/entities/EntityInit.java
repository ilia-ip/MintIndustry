package com.ilia_ip.mintindustry.entities;

import com.ilia_ip.mintindustry.MintIndustry;

import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.MobCategory;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

public class EntityInit {
        public static final DeferredRegister<EntityType<?>> ENTITIES = DeferredRegister.create(
                        ForgeRegistries.ENTITY_TYPES,
                        MintIndustry.MODID);

        public static final RegistryObject<EntityType<DroneEntity>> DRONE_ENTITY = ENTITIES.register("drone_entity",
                        () -> EntityType.Builder.of(DroneEntity::new, MobCategory.CREATURE).fireImmune()
                                        .sized(1.0f, 0.5f)
                                        .build(new ResourceLocation(MintIndustry.MODID, "drone_entity").toString()));
}
