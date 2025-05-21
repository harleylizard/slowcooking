package com.harleylizard.slowcooking.common

import net.fabricmc.api.ModInitializer
import net.fabricmc.fabric.api.itemgroup.v1.FabricItemGroup
import net.minecraft.core.Registry
import net.minecraft.core.registries.BuiltInRegistries
import net.minecraft.network.chat.Component
import net.minecraft.resources.ResourceLocation

class Slowcooking : ModInitializer {
    override fun onInitialize() {
        SlowcookingBlocks.registerAll()
        SlowcookingItems.registerAll()
        SlowcookingComponents.registerAll()

        Registry.register(BuiltInRegistries.CREATIVE_MODE_TAB, MOD_ID.resourceLocation, itemGroup)
    }

    companion object {
        private const val MOD_ID = "slowcooking"

        val String.resourceLocation: ResourceLocation get() = ResourceLocation.fromNamespaceAndPath(MOD_ID, this)

        val itemGroup = FabricItemGroup.builder()
            .title(Component.translatable("slowcooking.itemGroup.name"))
            .icon { SlowcookingItems.potteryWheel.defaultInstance }
            .displayItems { itemDisplayParameters, output ->  }
            .build()

    }
}