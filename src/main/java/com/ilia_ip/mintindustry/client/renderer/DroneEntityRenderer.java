package com.ilia_ip.mintindustry.client.renderer;

import com.ilia_ip.mintindustry.MintIndustry;
import com.ilia_ip.mintindustry.client.model.DroneEntityModel;
import com.ilia_ip.mintindustry.entities.DroneEntity;

import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.entity.MobRenderer;
import net.minecraft.resources.ResourceLocation;

public class DroneEntityRenderer extends MobRenderer<DroneEntity, DroneEntityModel<DroneEntity>> {

    public static final ResourceLocation TEXTURE = new ResourceLocation(MintIndustry.MODID,
            "textures/entity/drone_entity.png");

    public DroneEntityRenderer(EntityRendererProvider.Context context) {
        super(context, new DroneEntityModel<>(context.bakeLayer(DroneEntityModel.LAYER_LOCATION)), 0.0f);
    }

    @Override
    public ResourceLocation getTextureLocation(DroneEntity entity) {
        return TEXTURE;
    }
}