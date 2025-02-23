package com.ilia_ip.mintindustry.sounds;

import org.apache.commons.io.comparator.DefaultFileComparator;

import com.ilia_ip.mintindustry.MintIndustry;

import net.minecraft.resources.ResourceLocation;
import net.minecraft.sounds.SoundEvent;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

public class SoundInit {
        public static final DeferredRegister<SoundEvent> SOUNDS = DeferredRegister.create(ForgeRegistries.SOUND_EVENTS,
                        MintIndustry.MODID);

        public static final RegistryObject<SoundEvent> DRONE_ENGINE_LOOP = SOUNDS.register("drone_engine_loop",
                        () -> SoundEvent.createVariableRangeEvent(
                                        new ResourceLocation(MintIndustry.MODID, "drone_engine_loop")));
}
