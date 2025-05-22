package com.harleylizard.slowcooking.client.renderer

import com.mojang.blaze3d.vertex.PoseStack
import net.minecraft.client.model.Model
import net.minecraft.client.renderer.MultiBufferSource
import net.minecraft.resources.ResourceLocation

object Render {

    fun renderModel(model: Model, pose: PoseStack, source: MultiBufferSource, texture: ResourceLocation, i: Int, j: Int, k: Int) {
        pose.pushPose()
        val offset = 0.5f
        pose.translate(offset, offset, offset)
        pose.scale(-1.0f, -1.0f, -1.0f)
        pose.translate(0.0f, -1.0f, 0.0f)
        val buffer = source.getBuffer(model.renderType(texture))
        model.renderToBuffer(pose, buffer, i, j, k)
        pose.popPose()
    }
}