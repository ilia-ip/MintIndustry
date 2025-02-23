package com.ilia_ip.mintindustry.items;

import net.minecraft.client.renderer.item.ItemProperties;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Rarity;
import net.minecraftforge.common.capabilities.ICapabilityProvider;
import top.theillusivec4.curios.api.CuriosApi;
import top.theillusivec4.curios.api.SlotContext;
import top.theillusivec4.curios.api.type.capability.ICurio;
import top.theillusivec4.curios.api.type.capability.ICurioItem;

public class DroneController extends Item implements ICurioItem {
    public DroneController() {
        super(new Item.Properties().fireResistant().stacksTo(1).rarity(Rarity.EPIC));
    }

    @Override
    public void curioTick(SlotContext slotContext, ItemStack stack) {

    }

    /*
     * public ICapabilityProvider initCapabilities(ItemStack stack, CompoundTag
     * unused) {
     * return CuriosApi.createCurioProvider(new ICurio() {
     * 
     * @Override
     * public ItemStack getStack() {
     * return stack;
     * }
     * 
     * @Override
     * public void curioTick(SlotContext slotContext) {
     * 
     * }
     * });
     * }
     */
}
