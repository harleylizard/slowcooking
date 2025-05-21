package com.harleylizard.slowcooking.common

import net.fabricmc.api.ModInitializer
import net.minecraft.resources.ResourceLocation

class Slowcooking : ModInitializer {
    override fun onInitialize() {
        SlowcookingBlocks.registerAll()
        SlowcookingItems.registerAll()
    }

    companion object {
        private const val MOD_ID = "slowcooking"

        val String.resourceLocation: ResourceLocation get() = ResourceLocation.fromNamespaceAndPath(MOD_ID, this)

    }
}