package com.harleylizard.slowcooking.common

import com.harleylizard.slowcooking.common.payload.BlockEntityToClientPayload
import net.fabricmc.api.ModInitializer
import net.fabricmc.fabric.api.itemgroup.v1.FabricItemGroup
import net.fabricmc.fabric.api.networking.v1.PayloadTypeRegistry
import net.minecraft.core.Registry
import net.minecraft.core.registries.BuiltInRegistries
import net.minecraft.network.chat.Component
import net.minecraft.resources.ResourceLocation
import net.minecraft.world.item.CreativeModeTab

class Slowcooking : ModInitializer {
    override fun onInitialize() {
        SlowcookingBlocks.registerAll()
        SlowcookingItems.registerAll()
        SlowcookingComponents.registerAll()
        SlowcookingBlockEntities.registerAll()

        PayloadTypeRegistry.playS2C().register(BlockEntityToClientPayload.type, BlockEntityToClientPayload.codec)

        Registry.register(BuiltInRegistries.CREATIVE_MODE_TAB, MOD_ID.resourceLocation, itemGroup)
    }

    companion object {
        private const val MOD_ID = "slowcooking"

        val String.resourceLocation: ResourceLocation get() = ResourceLocation.fromNamespaceAndPath(MOD_ID, this)

        val itemGroup: CreativeModeTab = FabricItemGroup.builder()
            .title(Component.translatable("slowcooking.itemGroup.name"))
            .icon { SlowcookingItems.potteryWheel.defaultInstance }
            .displayItems { _, output ->
                output.accept(SlowcookingItems.potteryWheel)
                output.accept(SlowcookingItems.unfiredCeramicPlate)
                output.accept(SlowcookingItems.unfiredCeramicBowl)
                output.accept(SlowcookingItems.ceramicPlate)
                output.accept(SlowcookingItems.ceramicBowl)

                output.accept(SlowcookingItems.spicyHam)
                output.accept(SlowcookingItems.dryWing)
                output.accept(SlowcookingItems.sweetTenderloin)
                output.accept(SlowcookingItems.bitterNectar)
                output.accept(SlowcookingItems.sourTooth)
                output.accept(SlowcookingItems.freshHoney)
            }
            .build()

    }
}