package com.harleylizard.slowcooking.common.blockentity

import com.harleylizard.slowcooking.common.SlowcookingBlockEntities
import com.harleylizard.slowcooking.common.block.PotteryWheel
import it.unimi.dsi.fastutil.longs.LongArraySet
import it.unimi.dsi.fastutil.longs.LongSet
import net.minecraft.core.BlockPos
import net.minecraft.core.HolderLookup
import net.minecraft.nbt.CompoundTag
import net.minecraft.nbt.ListTag
import net.minecraft.nbt.LongTag
import net.minecraft.network.protocol.game.ClientboundBlockEntityDataPacket
import net.minecraft.world.item.Items
import net.minecraft.world.level.Level
import net.minecraft.world.level.block.Block
import net.minecraft.world.level.block.entity.BlockEntity
import net.minecraft.world.level.block.state.BlockState
import java.util.concurrent.TimeUnit
import kotlin.math.abs

class PotteryWheelBlockEntity(blockPos: BlockPos, blockState: BlockState) : BlockEntity(SlowcookingBlockEntities.potteryWheel, blockPos, blockState) {
    private val set: LongSet = LongArraySet()

    var previous = 0L

    val animation = PotteryWheelAnimation()

    override fun saveAdditional(compoundTag: CompoundTag, provider: HolderLookup.Provider) {
        super.saveAdditional(compoundTag, provider)
        animation.save(compoundTag)
        val list = ListTag()
        for (l in set.longStream()) {
            list.add(LongTag.valueOf(l))
        }
        compoundTag.put("Set", list)
        compoundTag.putLong("Previous", previous)
    }

    override fun loadAdditional(compoundTag: CompoundTag, provider: HolderLookup.Provider) {
        super.loadAdditional(compoundTag, provider)
        animation.load(compoundTag)
        set.clear()
        val list = compoundTag.getLongArray("Set")
        for (i in list) {
            set.add(i)
        }
        previous = compoundTag.getLong("Previous")
    }

    override fun getUpdatePacket() = ClientboundBlockEntityDataPacket.create(this)

    override fun getUpdateTag(provider: HolderLookup.Provider): CompoundTag {
        val tag = CompoundTag()
        animation.forUpdate(tag)
        return tag
    }

    fun use(timestamp: Long) {
        set.add(timestamp)
        val size = set.size
        if (size >= 10) {
            level?.let {
                it.setBlock(blockPos, blockState.setValue(PotteryWheel.hasClay, false), Block.UPDATE_ALL)
                var average = 0L
                var k = previous
                for (l in set.longStream()) {
                    average += abs(l - k)
                    k = l
                }
                average /= size
                val seconds = TimeUnit.MILLISECONDS.toSeconds(average)
                if (seconds >= 2) {
                    Block.popResource(it, blockPos, Items.CLAY.defaultInstance.copy())
                } else {
                    Block.popResource(it, blockPos, Items.DIRT.defaultInstance.copy())
                }
                set.clear()
            }
        }
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