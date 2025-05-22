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

class PotteryWheelDisc(private val root: ModelPart) : Model(RenderType::entityCutoutNoCull) {

    override fun renderToBuffer(poseStack: PoseStack, vertexConsumer: VertexConsumer, i: Int, j: Int, k: Int) {
        root.render(poseStack, vertexConsumer, i, j, k)
    }

    companion object {

        fun createModelLayer(): LayerDefinition {
            val mesh = MeshDefinition()
            val root = mesh.root
            root.addOrReplaceChild("spinning", CubeListBuilder.create().texOffs(0, 0)
                    .addBox(-5.0f, -8.0f, -5.0f, 10.0f, 2.0f, 10.0f, CubeDeformation(0.0f))
                    .texOffs(0, 12).addBox(-5.0f, 5.0f, -5.0f, 10.0f, 2.0f, 10.0f, CubeDeformation(0.0f))
                    .texOffs(40, 0).addBox(-1.0f, -6.0f, -1.0f, 2.0f, 8.0f, 2.0f, CubeDeformation(0.0f))
                    .texOffs(48, 0).addBox(-1.0f, 0.0f, 1.0f, 2.0f, 5.0f, 2.0f, CubeDeformation(0.0f))
                    .texOffs(56, 0).addBox(-1.0f, 4.0f, -1.0f, 2.0f, 1.0f, 2.0f, CubeDeformation(0.0f)),
                PartPose.offset(0.0f, 16.0f, 0.0f)
            )
            return LayerDefinition.create(mesh, 64, 32)
        }
    }
}