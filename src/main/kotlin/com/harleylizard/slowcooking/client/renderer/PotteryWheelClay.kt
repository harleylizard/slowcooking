package com.harleylizard.slowcooking.client.renderer

import com.mojang.blaze3d.vertex.PoseStack
import com.mojang.blaze3d.vertex.VertexConsumer
import net.minecraft.client.model.Model
import net.minecraft.client.model.geom.ModelPart
import net.minecraft.client.model.geom.PartPose
import net.minecraft.client.model.geom.builders.CubeDeformation
import net.minecraft.client.model.geom.builders.CubeListBuilder
import net.minecraft.client.model.geom.builders.LayerDefinition
import net.minecraft.client.model.geom.builders.MeshDefinition
import net.minecraft.client.renderer.RenderType

class PotteryWheelClay(private val root: ModelPart) : Model(RenderType::entityCutoutNoCull) {

    override fun renderToBuffer(poseStack: PoseStack, vertexConsumer: VertexConsumer, i: Int, j: Int, k: Int) {
        root.render(poseStack, vertexConsumer, i, j, k)
    }

    companion object {

        fun createModelLayer(): LayerDefinition {
            val mesh = MeshDefinition()
            val root = mesh.root
            root.addOrReplaceChild("clay", CubeListBuilder.create().texOffs(0, 0).addBox(-4.0F, -6.0F, -2.0F, 6.0F, 6.0F, 6.0F, CubeDeformation(0.0F))
                .texOffs(44, 5).addBox(-5.0F, -3.0F, 1.0F, 4.0F, 3.0F, 4.0F, CubeDeformation(0.0F))
                .texOffs(24, 3).addBox(0.0F, -4.0F, -5.0F, 5.0F, 4.0F, 5.0F, CubeDeformation(0.0F)), PartPose.offset(0.0F, 24.0F, 0.0F));

            return LayerDefinition.create(mesh, 64, 32)
        }
    }
}