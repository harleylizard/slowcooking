package com.harleylizard.slowcooking.client

import com.harleylizard.slowcooking.client.renderer.PotteryWheelBlockEntityRenderer
import com.harleylizard.slowcooking.client.renderer.PotteryWheelClay
import com.harleylizard.slowcooking.client.renderer.PotteryWheelDisc
import com.harleylizard.slowcooking.common.SlowcookingBlockEntities
import net.fabricmc.api.ClientModInitializer
import net.fabricmc.fabric.api.client.rendering.v1.EntityModelLayerRegistry
import net.minecraft.client.renderer.blockentity.BlockEntityRenderers

class SlowcookingClient : ClientModInitializer {
    override fun onInitializeClient() {
        BlockEntityRenderers.register(SlowcookingBlockEntities.potteryWheel) { PotteryWheelBlockEntityRenderer(it) }

        EntityModelLayerRegistry.registerModelLayer(SlowcookingModelLayers.potteryWheelDisc, PotteryWheelDisc::createModelLayer)
        EntityModelLayerRegistry.registerModelLayer(SlowcookingModelLayers.potteryWheelClay, PotteryWheelClay::createModelLayer)
    }
}