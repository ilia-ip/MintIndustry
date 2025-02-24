package com.ilia_ip.mintindustry.client.model;
// Made with Blockbench 4.11.2

// Exported for Minecraft version 1.17 or later with Mojang mappings
// Paste this class into your mod and generate all required imports

import com.ilia_ip.mintindustry.MintIndustry;
import com.ilia_ip.mintindustry.entities.drone.DroneEntity;
import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;

import net.minecraft.client.model.EntityModel;
import net.minecraft.client.model.geom.ModelLayerLocation;
import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.client.model.geom.PartPose;
import net.minecraft.client.model.geom.builders.CubeDeformation;
import net.minecraft.client.model.geom.builders.CubeListBuilder;
import net.minecraft.client.model.geom.builders.LayerDefinition;
import net.minecraft.client.model.geom.builders.MeshDefinition;
import net.minecraft.client.model.geom.builders.PartDefinition;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.Entity;

public class DroneEntityModel<T extends Entity> extends EntityModel<T> {
	// This layer location should be baked with EntityRendererProvider.Context in
	// the entity renderer and passed into this model's constructor
	public static final ModelLayerLocation LAYER_LOCATION = new ModelLayerLocation(
			new ResourceLocation(MintIndustry.MODID, "drone_entity"), "main");
	private final ModelPart bone;
	private final ModelPart bone2;
	private final ModelPart bone3;
	private final ModelPart bone4;
	private final ModelPart bb_main;

	public DroneEntityModel(ModelPart root) {
		this.bone = root.getChild("bone");
		this.bone2 = root.getChild("bone2");
		this.bone3 = root.getChild("bone3");
		this.bone4 = root.getChild("bone4");
		this.bb_main = root.getChild("bb_main");
	}

	public static LayerDefinition createBodyLayer() {
		MeshDefinition meshdefinition = new MeshDefinition();
		PartDefinition partdefinition = meshdefinition.getRoot();

		PartDefinition bone = partdefinition.addOrReplaceChild("bone", CubeListBuilder.create(),
				PartPose.offset(4.0245F, 14.25F, 5.9941F));

		PartDefinition cube_r1 = bone
				.addOrReplaceChild("cube_r1",
						CubeListBuilder.create().texOffs(0, 16).addBox(-4.5F, 0.0F, -0.5F, 9.0F, 0.0F, 1.0F,
								new CubeDeformation(0.0F)),
						PartPose.offsetAndRotation(0.0F, 0.0F, 0.0F, 0.0F, -2.3562F, 0.0F));

		PartDefinition bone2 = partdefinition.addOrReplaceChild("bone2", CubeListBuilder.create(),
				PartPose.offset(4.0105F, 14.25F, -5.9919F));

		PartDefinition cube_r2 = bone2
				.addOrReplaceChild("cube_r2",
						CubeListBuilder.create().texOffs(16, 12).addBox(-4.5F, 0.0F, -0.5F, 9.0F, 0.0F, 1.0F,
								new CubeDeformation(0.0F)),
						PartPose.offsetAndRotation(0.0F, 0.0F, 0.0F, 0.0F, -1.9635F, 0.0F));

		PartDefinition bone3 = partdefinition.addOrReplaceChild("bone3", CubeListBuilder.create(),
				PartPose.offset(-3.9751F, 14.25F, 5.9946F));

		PartDefinition cube_r3 = bone3
				.addOrReplaceChild("cube_r3",
						CubeListBuilder.create().texOffs(16, 11).addBox(-4.5F, 0.0F, -0.5F, 9.0F, 0.0F, 1.0F,
								new CubeDeformation(0.0F)),
						PartPose.offsetAndRotation(0.0F, 0.0F, 0.0F, 0.0F, -2.7925F, 0.0F));

		PartDefinition bone4 = partdefinition.addOrReplaceChild("bone4", CubeListBuilder.create(),
				PartPose.offset(-3.9673F, 14.25F, -5.99F));

		PartDefinition cube_r4 = bone4
				.addOrReplaceChild("cube_r4",
						CubeListBuilder.create().texOffs(0, 10).addBox(-4.5F, 0.0F, -0.5F, 9.0F, 0.0F, 1.0F,
								new CubeDeformation(0.0F)),
						PartPose.offsetAndRotation(0.0F, 0.0F, 0.0F, 0.0F, -0.4363F, 0.0F));

		PartDefinition bb_main = partdefinition.addOrReplaceChild("bb_main",
				CubeListBuilder.create().texOffs(0, 0)
						.addBox(-2.0F, -9.25F, -4.0F, 4.0F, 2.0F, 8.0F, new CubeDeformation(0.0F))
						.texOffs(0, 17).addBox(-1.5F, -8.5F, -4.5F, 3.0F, 2.0F, 3.0F, new CubeDeformation(0.0F))
						.texOffs(0, 11).addBox(-2.5F, -8.75F, 0.0F, 5.0F, 2.0F, 3.0F, new CubeDeformation(0.0F)),
				PartPose.offset(0.0F, 24.0F, 0.0F));

		PartDefinition cube_r5 = bb_main.addOrReplaceChild("cube_r5",
				CubeListBuilder.create().texOffs(24, 3).addBox(-1.0F, -0.5F, -1.0F, 2.0F, 1.0F, 2.0F,
						new CubeDeformation(0.0F)),
				PartPose.offsetAndRotation(-3.9846F, -9.3F, 5.9978F, 0.0F, -0.4363F, 0.0F));

		PartDefinition cube_r6 = bb_main.addOrReplaceChild("cube_r6",
				CubeListBuilder.create().texOffs(24, 0).addBox(-1.0F, -0.5F, -1.0F, 2.0F, 1.0F, 2.0F,
						new CubeDeformation(0.0F)),
				PartPose.offsetAndRotation(4.0154F, -9.3F, 5.9978F, 0.0F, 0.3927F, 0.0F));

		PartDefinition cube_r7 = bb_main.addOrReplaceChild("cube_r7",
				CubeListBuilder.create().texOffs(20, 22).addBox(-1.0F, -0.5F, -1.0F, 2.0F, 1.0F, 2.0F,
						new CubeDeformation(0.0F)),
				PartPose.offsetAndRotation(-3.9846F, -9.3F, -6.0022F, 0.0F, -0.4363F, 0.0F));

		PartDefinition cube_r8 = bb_main.addOrReplaceChild("cube_r8",
				CubeListBuilder.create().texOffs(22, 18).addBox(-1.0F, -0.5F, -1.0F, 2.0F, 1.0F, 2.0F,
						new CubeDeformation(0.0F)),
				PartPose.offsetAndRotation(4.0154F, -9.3F, -6.0022F, 0.0F, 0.3927F, 0.0F));

		PartDefinition cube_r9 = bb_main.addOrReplaceChild("cube_r9",
				CubeListBuilder.create().texOffs(22, 13).addBox(-2.4806F, -0.129F, -3.9088F, 1.0F, 1.0F, 4.0F,
						new CubeDeformation(0.0F)),
				PartPose.offsetAndRotation(0.0106F, -8.8908F, 4.9465F, -0.192F, -2.3562F, 0.0F));

		PartDefinition cube_r10 = bb_main.addOrReplaceChild("cube_r10",
				CubeListBuilder.create().texOffs(10, 22).addBox(1.4445F, -0.1221F, -3.9442F, 1.0F, 1.0F, 4.0F,
						new CubeDeformation(0.0F)),
				PartPose.offsetAndRotation(0.0106F, -8.8908F, 4.9465F, -0.192F, 2.3562F, 0.0F));

		PartDefinition cube_r11 = bb_main.addOrReplaceChild("cube_r11",
				CubeListBuilder.create().texOffs(0, 22).addBox(-2.4806F, -0.129F, -3.9088F, 1.0F, 1.0F, 4.0F,
						new CubeDeformation(0.0F)),
				PartPose.offsetAndRotation(0.0106F, -8.8908F, -4.8035F, -0.192F, 0.7854F, 0.0F));

		PartDefinition cube_r12 = bb_main.addOrReplaceChild("cube_r12",
				CubeListBuilder.create().texOffs(12, 17).addBox(1.4445F, -0.1221F, -3.9442F, 1.0F, 1.0F, 4.0F,
						new CubeDeformation(0.0F)),
				PartPose.offsetAndRotation(0.0106F, -8.8908F, -4.8035F, -0.192F, -0.7854F, 0.0F));

		return LayerDefinition.create(meshdefinition, 64, 64);
	}

	@Override
	public void setupAnim(Entity entity, float limbSwing, float limbSwingAmount, float ageInTicks, float netHeadYaw,
			float headPitch) {
		if (entity instanceof DroneEntity) {
			DroneEntity drone = (DroneEntity) entity;

			bone.yRot += drone.propellerSpeed;
			bone2.yRot += drone.propellerSpeed;
			bone3.yRot += drone.propellerSpeed;
			bone4.yRot += drone.propellerSpeed;
		}
	}

	@Override
	public void renderToBuffer(PoseStack poseStack, VertexConsumer vertexConsumer, int packedLight, int packedOverlay,
			float red, float green, float blue, float alpha) {
		bone.render(poseStack, vertexConsumer, packedLight, packedOverlay, red, green, blue, alpha);
		bone2.render(poseStack, vertexConsumer, packedLight, packedOverlay, red, green, blue, alpha);
		bone3.render(poseStack, vertexConsumer, packedLight, packedOverlay, red, green, blue, alpha);
		bone4.render(poseStack, vertexConsumer, packedLight, packedOverlay, red, green, blue, alpha);
		bb_main.render(poseStack, vertexConsumer, packedLight, packedOverlay, red, green, blue, alpha);
	}
}