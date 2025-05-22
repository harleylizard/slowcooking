package com.harleylizard.slowcooking.client.renderer

import com.harleylizard.slowcooking.client.SlowcookingModelLayers
import com.harleylizard.slowcooking.common.Slowcooking.Companion.resourceLocation
import com.harleylizard.slowcooking.common.block.PotteryWheel.Companion.hasClay
import com.harleylizard.slowcooking.common.blockentity.PotteryWheelBlockEntity
import com.mojang.blaze3d.vertex.PoseStack
import com.mojang.math.Axis
import net.minecraft.client.renderer.MultiBufferSource
import net.minecraft.client.renderer.blockentity.BlockEntityRenderer
import net.minecraft.client.renderer.blockentity.BlockEntityRendererProvider

class PotteryWheelBlockEntityRenderer(context: BlockEntityRendererProvider.Context) : BlockEntityRenderer<PotteryWheelBlockEntity> {
    private val disc = PotteryWheelDisc(context.bakeLayer(SlowcookingModelLayers.potteryWheelDisc))
    private val clay = PotteryWheelClay(context.bakeLayer(SlowcookingModelLayers.potteryWheelClay))

    override fun render(blockEntity: PotteryWheelBlockEntity, f: Float, poseStack: PoseStack, multiBufferSource: MultiBufferSource, i: Int, j: Int) {
        poseStack.pushPose()
        val pivot = 0.5f
        poseStack.translate(pivot, pivot, pivot)
        poseStack.mulPose(Axis.YP.rotationDegrees(blockEntity.angle))
        poseStack.translate(-pivot, -pivot, -pivot)

        val colour = 0xFFFFFF
        Render.renderModel(disc, poseStack, multiBufferSource, discTexture, i, j, colour)

        val blockState = blockEntity.blockState
        if (blockState.hasClay) {
            poseStack.pushPose()
            poseStack.translate(0.0f, 1.0f, 0.0f)
            Render.renderModel(clay, poseStack, multiBufferSource, clayTexture, i, j, colour)
            poseStack.popPose()
        }
        poseStack.popPose()
    }

    companion object {
        private val discTexture = "textures/entity/pottery_wheel/disc.png".resourceLocation
        private val clayTexture = "textures/entity/pottery_wheel/clay.png".resourceLocation


    }
}