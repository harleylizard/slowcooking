package com.harleylizard.slowcooking.common.blockentity

import com.harleylizard.slowcooking.common.SlowcookingBlockEntities
import net.minecraft.core.BlockPos
import net.minecraft.core.HolderLookup
import net.minecraft.nbt.CompoundTag
import net.minecraft.network.protocol.game.ClientboundBlockEntityDataPacket
import net.minecraft.world.level.Level
import net.minecraft.world.level.block.entity.BlockEntity
import net.minecraft.world.level.block.state.BlockState

class PotteryWheelBlockEntity(blockPos: BlockPos, blockState: BlockState) : BlockEntity(SlowcookingBlockEntities.potteryWheel, blockPos, blockState) {
    val animation = PotteryWheelAnimation()

    override fun saveAdditional(compoundTag: CompoundTag, provider: HolderLookup.Provider) {
        super.saveAdditional(compoundTag, provider)
        animation.save(compoundTag)
    }

    override fun loadAdditional(compoundTag: CompoundTag, provider: HolderLookup.Provider) {
        super.loadAdditional(compoundTag, provider)
        animation.load(compoundTag)
    }

    override fun getUpdatePacket() = ClientboundBlockEntityDataPacket.create(this)

    override fun getUpdateTag(provider: HolderLookup.Provider): CompoundTag {
        val tag = CompoundTag()
        animation.forUpdate(tag)
        return tag
    }

    companion object {

        fun serverTick(level: Level, blockPos: BlockPos, blockState: BlockState, blockEntity: PotteryWheelBlockEntity) {
            blockEntity.animation.tick(blockState)
        }

        fun clientTick(level: Level, blockPos: BlockPos, blockState: BlockState, blockEntity: PotteryWheelBlockEntity) {
            blockEntity.animation.tick(blockState)
        }
    }
}