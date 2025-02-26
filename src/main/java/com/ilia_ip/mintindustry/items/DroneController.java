package com.ilia_ip.mintindustry.items;

import java.util.List;

import com.ilia_ip.mintindustry.blocks.DroneStation;
import com.ilia_ip.mintindustry.core.MintIndustry;
import com.ilia_ip.mintindustry.core.ModKeybindings;
import com.ilia_ip.mintindustry.entities.drone.DroneEntity;
import com.ilia_ip.mintindustry.entities.drone.DroneTask;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.chat.Component;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Rarity;
import net.minecraft.world.level.entity.EntityTypeTest;
import net.minecraft.world.level.levelgen.structure.BoundingBox;
import net.minecraft.world.phys.AABB;
import net.minecraftforge.common.capabilities.ICapabilityProvider;
import top.theillusivec4.curios.api.CuriosApi;
import top.theillusivec4.curios.api.SlotContext;
import top.theillusivec4.curios.api.type.capability.ICurio;
import top.theillusivec4.curios.api.type.capability.ICurioItem;

public class DroneController extends Item implements ICurioItem {
    List<DroneEntity> drones;
    List<DroneStation> stations;

    public DroneController() {
        super(new Item.Properties().fireResistant().stacksTo(1).rarity(Rarity.EPIC));
    }

    @Override
    public ICapabilityProvider initCapabilities(ItemStack stack, CompoundTag unused) {
        return CuriosApi.createCurioProvider(new ICurio() {

            @Override
            public ItemStack getStack() {
                return stack;
            }

            @Override
            public void curioTick(SlotContext slotContext) {
                if (stations == null) {
                }
                if (drones == null) {
                    List<DroneEntity> allDrones = slotContext.entity().level().getEntities(
                            EntityTypeTest.forClass(DroneEntity.class),
                            AABB.of(BoundingBox.infinite()), null);
                    for (DroneEntity drone : allDrones) {
                        if (drone.owner.getUUID() == slotContext.entity().getUUID()) {
                            drones.add(drone);
                        }
                    }
                }
                if (drones == null) {
                    return;
                }
                for (DroneEntity drone : drones) {
                    drone.setTask(DroneTask.FOLLOWING_PLAYER);
                }

                if (ModKeybindings.INSTANCE.controllerMenuKey.isDown()) {
                    ModKeybindings.INSTANCE.controllerMenuKey.consumeClick();

                    Minecraft.getInstance().setScreen(new DroneControllerScreen());

                }
                slotContext.entity().addEffect(new MobEffectInstance(MobEffects.BAD_OMEN));
                
                
                
            }
        });
    }
}

class DroneControllerScreen extends Screen {
    public static final Component TITLE = Component.translatable("gui." + MintIndustry.MODID + ".controllerMenu");

    public DroneControllerScreen() {
        super(TITLE);
    }


}