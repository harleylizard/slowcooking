package com.harleylizard.slowcooking.common

import com.mojang.serialization.Codec
import it.unimi.dsi.fastutil.objects.Object2IntArrayMap
import it.unimi.dsi.fastutil.objects.Object2IntMap
import it.unimi.dsi.fastutil.objects.Object2IntMaps
import net.minecraft.core.BlockPos
import net.minecraft.world.entity.player.Player
import net.minecraft.world.item.Item
import net.minecraft.world.item.ItemStack
import net.minecraft.world.level.block.Block
import java.util.*

object Util {
    val <K, V> Map<K, V>.freeze: Map<K, V> get() = Collections.unmodifiableMap(this)

    val <T> Object2IntMap<T>.freeze: Object2IntMap<T> get() = Object2IntMaps.unmodifiable(this)

    private val <T> Map<T, Int>.object2Int: Object2IntMap<T> get() = Object2IntArrayMap<T>().also {
        forEach { (key, value) -> it[key] = value }
    }

    private val <T> Object2IntMap<T>.map: Map<T, Int> get() = mutableMapOf<T, Int>().apply {
        for ((key, value) in object2IntEntrySet()) {
            this[key] = value
        }
    }

    val <T> Codec<T>.object2IntMap: Codec<Object2IntMap<T>> get() = Codec.unboundedMap(this, Codec.INT).xmap({ it.object2Int }) { it.map }

    fun <T> object2IntMap(unit: Object2IntMap<T>.() -> Unit) = Object2IntArrayMap<T>().also(unit).freeze

    fun takeBack(player: Player, item: Item, blockPos: BlockPos) {
        takeBack(player, item.defaultInstance.copy(), blockPos)
    }

    private fun takeBack(player: Player, itemStack: ItemStack, blockPos: BlockPos) {
        if (!player.addItem(itemStack)) {
            Block.popResource(player.level(), blockPos, itemStack)
        }
    }
}