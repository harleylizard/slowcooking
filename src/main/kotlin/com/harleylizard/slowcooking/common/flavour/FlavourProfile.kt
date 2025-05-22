package com.harleylizard.slowcooking.common.flavour

import com.harleylizard.slowcooking.common.Maps.freeze
import com.harleylizard.slowcooking.common.Maps.object2IntMap
import com.harleylizard.slowcooking.common.SlowcookingComponents
import com.harleylizard.slowcooking.common.SlowcookingItemTags
import com.harleylizard.slowcooking.common.SlowcookingItemTags.isIngredient
import com.mojang.serialization.Codec
import it.unimi.dsi.fastutil.objects.Object2IntArrayMap
import it.unimi.dsi.fastutil.objects.Object2IntMap
import it.unimi.dsi.fastutil.objects.Object2IntMaps
import net.minecraft.tags.TagKey
import net.minecraft.world.inventory.tooltip.TooltipComponent
import net.minecraft.world.item.Item
import net.minecraft.world.item.ItemStack

class FlavourProfile private constructor(private val map: Object2IntMap<Flavour>) : TooltipComponent {
    val spiciness = float(Flavour.SPICY) / MAXIMUM_FLAVOUR
    val dryness = float(Flavour.DRY) / MAXIMUM_FLAVOUR
    val sweetness = float(Flavour.SWEET) / MAXIMUM_FLAVOUR
    val bitterness = float(Flavour.BITTER) / MAXIMUM_FLAVOUR
    val sourness = float(Flavour.SOUR) / MAXIMUM_FLAVOUR

    private fun float(flavour: Flavour) = map.getInt(flavour).toFloat()

    override fun equals(other: Any?): Boolean {
        if (other is FlavourProfile) {
            return other.map == map
        }
        return super.equals(other)
    }

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
                Flavour.map[tag]?.let {
                    val previous = map.getInt(it)
                    map.put(it, previous.coerceAtLeast(amounts.getInt(tag)))
                }
            }
            return FlavourProfile(map.freeze)
        }

        fun apply(stack: ItemStack): ItemStack {
            if (stack.isIngredient) {
                stack.set(SlowcookingComponents.flavourProfile, tags(stack))
            }
            return stack
        }
    }
}