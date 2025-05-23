package com.harleylizard.slowcooking.common.flavour

import com.harleylizard.slowcooking.common.SlowcookingComponents
import com.harleylizard.slowcooking.common.SlowcookingItemTags
import com.harleylizard.slowcooking.common.SlowcookingItemTags.isIngredient
import com.harleylizard.slowcooking.common.Util.freeze
import com.harleylizard.slowcooking.common.Util.object2IntMap
import com.mojang.serialization.Codec
import it.unimi.dsi.fastutil.objects.Object2IntArrayMap
import it.unimi.dsi.fastutil.objects.Object2IntMap
import it.unimi.dsi.fastutil.objects.Object2IntMaps
import net.minecraft.tags.TagKey
import net.minecraft.world.inventory.tooltip.TooltipComponent
import net.minecraft.world.item.Item
import net.minecraft.world.item.ItemStack

class FlavourProfile private constructor(private val map: Object2IntMap<Flavour>) : TooltipComponent {
    val spiciness = float(Flavour.SPICY)
    val dryness = float(Flavour.DRY)
    val sweetness = float(Flavour.SWEET)
    val bitterness = float(Flavour.BITTER)
    val sourness = float(Flavour.SOUR)
    private fun float(flavour: Flavour) = map.getInt(flavour).toFloat() / MAXIMUM_FLAVOUR

    override fun equals(other: Any?) = if (other is FlavourProfile) other.map == map else super.equals(other)

    override fun hashCode() = map.hashCode()

    companion object {
        private const val MAXIMUM_FLAVOUR = 32.0f

        private val amounts = object2IntMap<TagKey<Item>> {
            put(SlowcookingItemTags.spicyIngredient, 15)
            put(SlowcookingItemTags.dryIngredient, 15)
            put(SlowcookingItemTags.sweetIngredient, 15)
            put(SlowcookingItemTags.bitterIngredient, 15)
            put(SlowcookingItemTags.sourIngredient, 15)
            put(SlowcookingItemTags.verySpicyIngredient, 23)
            put(SlowcookingItemTags.veryDryIngredient, 23)
            put(SlowcookingItemTags.verySweetIngredient, 23)
            put(SlowcookingItemTags.veryBitterIngredient, 23)
            put(SlowcookingItemTags.verySourIngredient, 23)
        }

        val codec: Codec<FlavourProfile> = Flavour.codec.object2IntMap.xmap({ k -> FlavourProfile(k) }, { v -> v.map })

        val empty = FlavourProfile(Object2IntMaps.emptyMap())

        private fun tags(stack: ItemStack): FlavourProfile {
            val map: Object2IntMap<Flavour> = Object2IntArrayMap()
            for (tag in stack.tags) {
                SlowcookingItemTags.map[tag]?.let {
                    val previous = map.getInt(it)
                    map.put(it, previous.coerceAtLeast(amounts.getInt(tag)))
                }
            }
            return FlavourProfile(map.freeze)
        }

        fun apply(stack: ItemStack): ItemStack {
            if (stack.isIngredient) {
                val component = SlowcookingComponents.flavourProfile
                if (!stack.has(component)) {
                    stack.set(component, tags(stack))
                }
            }
            return stack
        }
    }
}