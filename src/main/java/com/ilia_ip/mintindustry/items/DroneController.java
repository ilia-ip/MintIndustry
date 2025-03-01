package com.ilia_ip.mintindustry.items;

import java.util.List;

import com.ilia_ip.mintindustry.blocks.DroneStation;
import com.ilia_ip.mintindustry.core.MintIndustry;
import com.ilia_ip.mintindustry.core.ModKeybindings;
import com.ilia_ip.mintindustry.entities.drone.DroneEntity;
import com.ilia_ip.mintindustry.entities.drone.DroneTasks;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.core.Vec3i;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.chat.Component;
import net.minecraft.world.entity.ai.targeting.TargetingConditions;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Rarity;
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

            /*
             * asdasdssa i hate BouningBox.infinite()
             */
            @Override
            public void curioTick(SlotContext slotContext) {
                drones = slotContext.entity().level().getNearbyEntities(DroneEntity.class, TargetingConditions.DEFAULT, slotContext.entity(), AABB.of(BoundingBox.fromCorners(
                            new Vec3i((int)slotContext.entity().getX()-100,(int)slotContext.entity().getY()-100,(int)slotContext.entity().getZ()-100), 
                            new Vec3i((int)slotContext.entity().getX()+100, (int)slotContext.entity().getY()+100, (int)slotContext.entity().getZ()+100))));
                 
                for (DroneEntity drone : drones) {
                    drone.setTask(DroneTasks.FOLLOWING_PLAYER);
                    if (true) {
                        drone.setTask(DroneTasks.RECHARGING);
                    }
                }

                // TO_BE_REMOVED
                if (ModKeybindings.INSTANCE.controllerMenuKey.isDown()) {
                    ModKeybindings.INSTANCE.controllerMenuKey.consumeClick();

                    Minecraft.getInstance().setScreen(new DroneControllerScreen());

                } 
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