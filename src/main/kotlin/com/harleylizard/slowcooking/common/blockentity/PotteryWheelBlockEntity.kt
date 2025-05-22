package com.harleylizard.slowcooking.common.blockentity

import com.harleylizard.slowcooking.common.SlowcookingBlockEntities
import net.minecraft.core.BlockPos
import net.minecraft.world.level.block.entity.BlockEntity
import net.minecraft.world.level.block.state.BlockState

class PotteryWheelBlockEntity(blockPos: BlockPos, blockState: BlockState) : BlockEntity(SlowcookingBlockEntities.potteryWheel, blockPos, blockState) {
}