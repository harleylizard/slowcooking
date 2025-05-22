package com.harleylizard.slowcooking.common.blockentity

import com.harleylizard.slowcooking.common.block.PotteryWheel.Companion.hasClay
import com.harleylizard.slowcooking.common.block.PotteryWheel.Companion.powered
import com.harleylizard.slowcooking.common.payload.BlockEntityToClientPayload.Companion.sync
import net.minecraft.nbt.CompoundTag
import net.minecraft.world.level.block.entity.BlockEntity
import net.minecraft.world.level.block.state.BlockState

class PotteryWheelAnimation {
    private var time = 0
    private var ticks = 0

    var speed = 0.0f; private set
    var angle = 0.0f

    fun tick(blockState: BlockState) {
        ticks++
        if (time > 0) {
            if (ticks % 20 == 0) {
                time--
            }

            val step = 0.5f
            val axis = if (blockState.powered && blockState.hasClay) step else -step
            speed += axis
            speed = Math.min(Math.max(speed, 0.0f), 8.0f)
        }
    }

    fun save(tag: CompoundTag) {
        tag.putInt("Time", time)
        tag.putInt("Ticks", ticks)
        tag.putFloat("Speed", speed)
    }

    fun load(tag: CompoundTag) {
        time = tag.getInt("Time")
        ticks = tag.getInt("Ticks")
        speed = tag.getFloat("Speed")
    }

    fun forUpdate(tag: CompoundTag) {
        save(tag)
    }

    fun animate(blockEntity: BlockEntity) {
        if (time == 0) {
            time = 100
            blockEntity.sync()
        }
    }

}