package com.harleylizard.slowcooking.common.flavour

import com.harleylizard.slowcooking.common.SlowcookingItemTags
import com.mojang.serialization.Codec
import net.minecraft.network.chat.Component
import net.minecraft.world.item.ItemStack

enum class Flavour {
    SPICY,
    DRY,
    SWEET,
    BITTER,
    SOUR;

    private val translatable: Component get() = when (this) {
        SPICY -> Component.translatable("slowcooking.flavour.spicy")
        DRY -> Component.translatable("slowcooking.flavour.dry")
        SWEET -> Component.translatable("slowcooking.flavour.sweet")
        BITTER -> Component.translatable("slowcooking.flavour.bitter")
        SOUR -> Component.translatable("slowcooking.flavour.sour")
    }

    companion object {
        val codec: Codec<Flavour> = Codec.STRING.xmap({ k -> k.flavour }, { v -> v.name.lowercase() })

        private val String.flavour get() = when (this) {
            "spicy" -> SPICY
            "dry" -> DRY
            "sweet" -> SWEET
            "bitter" -> BITTER
            "sour" -> SOUR
            else -> throw UnsupportedOperationException()
        }

        fun tooltipOf(stack: ItemStack): Component {
            val list = stack.tags.toList().mapNotNull { SlowcookingItemTags.map[it] }.distinct().map { it.translatable }
            return when (val size = list.size) {
                1 -> list.first()
                2 -> Component.translatable("slowcooking.tooltip.flavour.and", "${list[0].string} ", list[1].string)
                else -> {
                    val last = list.last()
                    val builder = StringBuilder()
                    for (i in 0 until size - 1) {
                        builder.append(Component.translatable("slowcooking.tooltip.flavour.comma", list[i].string).string)
                    }
                    Component.translatable("slowcooking.tooltip.flavour.and", builder.toString(), last.string)
                }
            }
        }
    }
}