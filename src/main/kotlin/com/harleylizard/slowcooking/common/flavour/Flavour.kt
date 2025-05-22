package com.harleylizard.slowcooking.common.flavour

import com.harleylizard.slowcooking.common.SlowcookingItemTags
import com.harleylizard.slowcooking.common.Util.freeze
import com.mojang.serialization.Codec
import net.minecraft.network.chat.Component
import net.minecraft.world.item.ItemStack

enum class Flavour(val tooltip: Component) {
    SPICY(Component.translatable("slowcooking.tooltip.flavour.spicy")),
    DRY(Component.translatable("slowcooking.tooltip.flavour.dry")),
    SWEET(Component.translatable("slowcooking.tooltip.flavour.sweet")),
    BITTER(Component.translatable("slowcooking.tooltip.flavour.bitter")),
    SOUR(Component.translatable("slowcooking.tooltip.flavour.sour"));

    companion object {
        private val String.flavour get() = when (this) {
            "spicy" -> SPICY
            "dry" -> DRY
            "sweet" -> SWEET
            "bitter" -> BITTER
            "sour" -> SOUR
            else -> throw UnsupportedOperationException()
        }

        val codec: Codec<Flavour> = Codec.STRING.xmap({ k -> k.flavour }, { v -> v.name.lowercase() })

        val map = mapOf(
            SlowcookingItemTags.spicyIngredient to SPICY,
            SlowcookingItemTags.dryIngredient to DRY,
            SlowcookingItemTags.sweetIngredient to SWEET,
            SlowcookingItemTags.bitterIngredient to BITTER,
            SlowcookingItemTags.sourIngredient to SOUR,
            SlowcookingItemTags.verySpicyIngredient to SPICY,
            SlowcookingItemTags.veryDryIngredient to DRY,
            SlowcookingItemTags.verySweetIngredient to SWEET,
            SlowcookingItemTags.veryBitterIngredient to BITTER,
            SlowcookingItemTags.verySourIngredient to SOUR
        ).freeze

        fun tooltip(stack: ItemStack): Component {
            val list = stack.tags.toList().mapNotNull { map[it] }.distinct().map { it.tooltip }
            return when (val size = list.size) {
                1 -> list.first()
                2 -> Component.translatable("slowcooking.tooltip.flavour.and", "${list[0].string} ", list[1].string)
                else -> {
                    val last = list.last()
                    val builder = StringBuilder()
                    for (i in 0 until size - 1) {
                        builder.append(Component.translatable("slowcooking.tooltip.flavour.following", list[i].string).string)
                    }
                    Component.translatable("slowcooking.tooltip.flavour.and", builder.toString(), last.string)
                }
            }
        }
    }

}