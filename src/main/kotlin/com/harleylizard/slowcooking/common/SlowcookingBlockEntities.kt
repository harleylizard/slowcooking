package com.harleylizard.slowcooking.common

import com.harleylizard.slowcooking.common.Slowcooking.Companion.resourceLocation
import com.harleylizard.slowcooking.common.blockentity.PotteryWheelBlockEntity
import net.minecraft.core.Registry
import net.minecraft.core.registries.BuiltInRegistries
import net.minecraft.world.level.block.entity.BlockEntity
import net.minecraft.world.level.block.entity.BlockEntityType

object SlowcookingBlockEntities {
    val potteryWheel: BlockEntityType<PotteryWheelBlockEntity> = BlockEntityType.Builder.of(::PotteryWheelBlockEntity, SlowcookingBlocks.potteryWheel).build()

    fun registerAll() {
        register("pottery_wheel", potteryWheel)
    }

    private fun register(name: String, type: BlockEntityType<out BlockEntity>) {
        Registry.register(BuiltInRegistries.BLOCK_ENTITY_TYPE, name.resourceLocation, type)
    }
}