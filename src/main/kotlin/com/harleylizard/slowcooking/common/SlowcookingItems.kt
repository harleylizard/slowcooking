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
    val spicyHam = Item(Item.Properties())
    val dryWing = Item(Item.Properties())
    val sweetTenderloin = Item(Item.Properties())
    val bitterNectar = Item(Item.Properties())
    val sourTooth = Item(Item.Properties())

    fun registerAll() {
        register("pottery_wheel", potteryWheel)
        register("spicy_ham", spicyHam)
        register("dry_wing", dryWing)
        register("sweet_tenderloin", sweetTenderloin)
        register("bitter_nectar", bitterNectar)
        register("sour_tooth", sourTooth)
    }

    fun register(name: String, item: Item) {
        Registry.register(BuiltInRegistries.ITEM, name.resourceLocation, item)
    }

}