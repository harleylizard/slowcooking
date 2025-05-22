package com.harleylizard.slowcooking.common.blockentity

import com.harleylizard.slowcooking.common.SlowcookingBlockEntities
import com.harleylizard.slowcooking.common.block.PotteryWheel.Companion.hasClay
import com.harleylizard.slowcooking.common.block.PotteryWheel.Companion.powered
import com.harleylizard.slowcooking.common.payload.BlockEntityToClientPayload.Companion.sync
import net.minecraft.core.BlockPos
import net.minecraft.core.HolderLookup
import net.minecraft.nbt.CompoundTag
import net.minecraft.network.protocol.game.ClientboundBlockEntityDataPacket
import net.minecraft.util.Mth
import net.minecraft.world.level.Level
import net.minecraft.world.level.block.entity.BlockEntity
import net.minecraft.world.level.block.state.BlockState

class PotteryWheelBlockEntity(blockPos: BlockPos, blockState: BlockState) : BlockEntity(SlowcookingBlockEntities.potteryWheel, blockPos, blockState) {
    var animateTime = 0
    var ticks = 0
    var angle = 0.0f
    var speed = 0.0f

    override fun saveAdditional(compoundTag: CompoundTag, provider: HolderLookup.Provider) {
        super.saveAdditional(compoundTag, provider)
        compoundTag.putInt("AnimateTime", animateTime)
        compoundTag.putInt("Ticks", ticks)
        compoundTag.putFloat("Angle", angle)
        compoundTag.putFloat("Speed", speed)

    }

    override fun loadAdditional(compoundTag: CompoundTag, provider: HolderLookup.Provider) {
        super.loadAdditional(compoundTag, provider)
        animateTime = compoundTag.getInt("AnimateTime")
        ticks = compoundTag.getInt("Ticks")
        if (level?.isClientSide == false) {
            angle = compoundTag.getFloat("Angle")
            speed = compoundTag.getFloat("Speed")
        }
    }

    fun animate() {
        if (animateTime == 0) {
            animateTime = 100
            sync()
        }
    }

    override fun getUpdatePacket() = ClientboundBlockEntityDataPacket.create(this)

    override fun getUpdateTag(provider: HolderLookup.Provider) = CompoundTag().also {
        it.putInt("AnimateTime", animateTime)
    }

    companion object {

        fun serverTick(level: Level, blockPos: BlockPos, blockState: BlockState, blockEntity: PotteryWheelBlockEntity) {
            blockEntity.ticks++
        }

        fun clientTick(level: Level, blockPos: BlockPos, blockState: BlockState, blockEntity: PotteryWheelBlockEntity) {
            blockEntity.ticks++;
            if (blockEntity.animateTime > 0) {
                if (blockEntity.ticks % 20 == 0) {
                    blockEntity.animateTime--
                }

                val f = 0.4f
                val amount = (if (blockState.powered && blockState.hasClay) f else -f)
                blockEntity.speed += amount
                blockEntity.speed = Mth.clamp(blockEntity.speed, 0.0f, 8.0f)
            }

            val speed = blockEntity.speed
            if (speed != 0.0f) {
                blockEntity.angle += speed
            }

        }
    }
}