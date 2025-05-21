package com.harleylizard.slowcooking.common

import com.harleylizard.slowcooking.common.Slowcooking.Companion.resourceLocation
import com.harleylizard.slowcooking.common.block.PotteryWheel
import net.minecraft.core.Registry
import net.minecraft.core.registries.BuiltInRegistries
import net.minecraft.world.level.block.Block
import net.minecraft.world.level.block.state.BlockBehaviour

object SlowcookingBlocks {
    val potteryWheel = PotteryWheel(BlockBehaviour.Properties.of())

    fun registerAll() {
        register("pottery_wheel", potteryWheel)
    }

    fun register(name: String, block: Block) {
        Registry.register(BuiltInRegistries.BLOCK, name.resourceLocation, block)
    }
}