package com.harleylizard.slowcooking.common.blockentity

import com.harleylizard.slowcooking.common.SlowcookingBlockEntities
import net.minecraft.core.BlockPos
import net.minecraft.world.level.Level
import net.minecraft.world.level.block.entity.BlockEntity
import net.minecraft.world.level.block.state.BlockState

class PotteryWheelBlockEntity(blockPos: BlockPos, blockState: BlockState) : BlockEntity(SlowcookingBlockEntities.potteryWheel, blockPos, blockState) {

    companion object {

        fun serverTick(level: Level, blockPos: BlockPos, blockState: BlockState, blockEntity: PotteryWheelBlockEntity) {
        }

        fun clientTick(level: Level, blockPos: BlockPos, blockState: BlockState, blockEntity: PotteryWheelBlockEntity) {

        }
    }
}