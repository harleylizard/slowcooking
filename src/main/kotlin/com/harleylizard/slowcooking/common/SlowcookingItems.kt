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
    val unfiredCeramicPlate = Item(Item.Properties())
    val unfiredCeramicBowl = Item(Item.Properties())
    val ceramicPlate = Item(Item.Properties())
    val ceramicBowl = Item(Item.Properties())

    val spicyHam = Item(Item.Properties())
    val dryWing = Item(Item.Properties())
    val sweetTenderloin = Item(Item.Properties())
    val bitterNectar = Item(Item.Properties())
    val sourTooth = Item(Item.Properties())
    val freshHoney = Item(Item.Properties())

    fun registerAll() {
        register("pottery_wheel", potteryWheel)
        register("unfired_ceramic_plate", unfiredCeramicPlate)
        register("unfired_ceramic_bowl", unfiredCeramicBowl)
        register("ceramic_plate", ceramicPlate)
        register("ceramic_bowl", ceramicBowl)

        register("spicy_ham", spicyHam)
        register("dry_wing", dryWing)
        register("sweet_tenderloin", sweetTenderloin)
        register("bitter_nectar", bitterNectar)
        register("sour_tooth", sourTooth)
        register("fresh_honey", freshHoney)
    }

    fun register(name: String, item: Item) {
        Registry.register(BuiltInRegistries.ITEM, name.resourceLocation, item)
    }

}