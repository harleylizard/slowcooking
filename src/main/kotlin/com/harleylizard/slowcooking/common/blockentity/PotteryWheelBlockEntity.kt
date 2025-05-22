package com.harleylizard.slowcooking.common.blockentity

import com.harleylizard.slowcooking.common.SlowcookingBlockEntities
import com.harleylizard.slowcooking.common.SlowcookingItems
import com.harleylizard.slowcooking.common.block.PotteryWheel
import it.unimi.dsi.fastutil.longs.LongArraySet
import it.unimi.dsi.fastutil.longs.LongIterable
import it.unimi.dsi.fastutil.longs.LongIterator
import it.unimi.dsi.fastutil.longs.LongSet
import net.minecraft.core.BlockPos
import net.minecraft.core.HolderLookup
import net.minecraft.nbt.CompoundTag
import net.minecraft.nbt.ListTag
import net.minecraft.nbt.LongTag
import net.minecraft.network.protocol.game.ClientboundBlockEntityDataPacket
import net.minecraft.world.level.Level
import net.minecraft.world.level.block.Block
import net.minecraft.world.level.block.entity.BlockEntity
import net.minecraft.world.level.block.state.BlockState
import java.util.concurrent.TimeUnit
import kotlin.math.abs

class PotteryWheelBlockEntity(blockPos: BlockPos, blockState: BlockState) : BlockEntity(SlowcookingBlockEntities.potteryWheel, blockPos, blockState) {
    private val times = Times()

    val animation = PotteryWheelAnimation()

    override fun saveAdditional(compoundTag: CompoundTag, provider: HolderLookup.Provider) {
        super.saveAdditional(compoundTag, provider)
        animation.save(compoundTag)
        times.save(compoundTag)
    }

    override fun loadAdditional(compoundTag: CompoundTag, provider: HolderLookup.Provider) {
        super.loadAdditional(compoundTag, provider)
        animation.load(compoundTag)
        times.load(compoundTag)
    }

    override fun getUpdatePacket() = ClientboundBlockEntityDataPacket.create(this)

    override fun getUpdateTag(provider: HolderLookup.Provider): CompoundTag {
        val tag = CompoundTag()
        animation.forUpdate(tag)
        times.save(tag)
        return tag
    }

    fun use() {
        times.add()
        val size = times.size
        if (size >= 10) {
            level?.let {
                it.setBlock(blockPos, blockState.setValue(PotteryWheel.hasClay, false), Block.UPDATE_ALL)
                var average = 0L
                var k = times.previous
                for (l in times.longIterator()) {
                    average += abs(l - k)
                    k = l
                }
                average /= size
                val seconds = TimeUnit.MILLISECONDS.toSeconds(average)
                if (seconds >= 2) {
                    Block.popResource(it, blockPos, SlowcookingItems.unfiredCeramicBowl.defaultInstance.copy())
                } else {
                    Block.popResource(it, blockPos, SlowcookingItems.unfiredCeramicPlate.defaultInstance.copy())
                }
                times.clean()
            }
        }
    }

    fun set() {
        times.set()
    }

    companion object {

        fun serverTick(level: Level, blockPos: BlockPos, blockState: BlockState, blockEntity: PotteryWheelBlockEntity) {
            blockEntity.animation.tick(blockState)
        }

        fun clientTick(level: Level, blockPos: BlockPos, blockState: BlockState, blockEntity: PotteryWheelBlockEntity) {
            blockEntity.animation.tick(blockState)
        }

        class Times : LongIterable {
            private val set: LongSet = LongArraySet()

            var previous = 0L; private set

            val size get() = set.size

            fun save(tag: CompoundTag) {
                val list = ListTag()
                for (l in set.longStream()) {
                    list.add(LongTag.valueOf(l))
                }
                tag.put("Set", list)
                tag.putLong("Previous", previous)
            }

            fun load(tag: CompoundTag) {
                clean()
                val list = tag.getLongArray("Set")
                for (i in list) {
                    set.add(i)
                }
                previous = tag.getLong("Previous")
            }

            fun set() {
                previous = System.currentTimeMillis()
            }

            fun add() {
                set.add(System.currentTimeMillis())
            }

            fun clean() {
                set.clear()
            }

            override fun iterator(): LongIterator = set.longIterator()
        }
    }
}