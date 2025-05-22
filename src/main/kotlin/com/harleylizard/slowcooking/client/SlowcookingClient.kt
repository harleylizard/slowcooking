package com.harleylizard.slowcooking.client

import com.harleylizard.slowcooking.client.renderer.PotteryWheelBlockEntityRenderer
import com.harleylizard.slowcooking.client.renderer.PotteryWheelClay
import com.harleylizard.slowcooking.client.renderer.PotteryWheelDisc
import com.harleylizard.slowcooking.common.SlowcookingBlockEntities
import com.harleylizard.slowcooking.common.payload.BlockEntityToClientPayload
import net.fabricmc.api.ClientModInitializer
import net.fabricmc.fabric.api.client.networking.v1.ClientPlayNetworking
import net.fabricmc.fabric.api.client.rendering.v1.EntityModelLayerRegistry
import net.minecraft.client.renderer.blockentity.BlockEntityRenderers

class SlowcookingClient : ClientModInitializer {
    override fun onInitializeClient() {
        BlockEntityRenderers.register(SlowcookingBlockEntities.potteryWheel) { PotteryWheelBlockEntityRenderer(it) }

        EntityModelLayerRegistry.registerModelLayer(SlowcookingModelLayers.potteryWheelDisc, PotteryWheelDisc::createModelLayer)
        EntityModelLayerRegistry.registerModelLayer(SlowcookingModelLayers.potteryWheelClay, PotteryWheelClay::createModelLayer)

        ClientPlayNetworking.registerGlobalReceiver(BlockEntityToClientPayload.type) { payload, context ->
            val minecraft = context.client()
            minecraft.execute {
                minecraft.level?.let {
                    it.getBlockEntity(payload.blockPos, payload.type).ifPresent { blockEntity ->
                        blockEntity.loadCustomOnly(payload.tag, it.registryAccess())
                    }
                }
            }
        }
    }
}