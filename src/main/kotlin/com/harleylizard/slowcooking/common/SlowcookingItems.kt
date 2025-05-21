package com.harleylizard.slowcooking.common

import com.harleylizard.slowcooking.common.Slowcooking.Companion.resourceLocation
import net.minecraft.core.Registry
import net.minecraft.core.registries.BuiltInRegistries
import net.minecraft.world.item.BlockItem
import net.minecraft.world.item.Item
import net.minecraft.world.level.block.Block

object SlowcookingItems {
    private val Block.blockItem get() = BlockItem(this, Item.Properties())

    val potteryWheel = SlowcookingBlocks.potteryWheel.blockItem

    fun registerAll() {
        register("pottery_wheel", potteryWheel)
    }

    fun register(name: String, item: Item) {
        Registry.register(BuiltInRegistries.ITEM, name.resourceLocation, item)
    }

}