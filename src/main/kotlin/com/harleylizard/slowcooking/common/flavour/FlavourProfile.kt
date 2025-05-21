package com.harleylizard.slowcooking.common.flavour

import com.harleylizard.slowcooking.common.Maps
import com.harleylizard.slowcooking.common.Maps.freeze
import com.harleylizard.slowcooking.common.Maps.object2IntMap
import com.harleylizard.slowcooking.common.SlowcookingItemTags
import com.mojang.serialization.Codec
import it.unimi.dsi.fastutil.objects.Object2IntArrayMap
import it.unimi.dsi.fastutil.objects.Object2IntMap
import it.unimi.dsi.fastutil.objects.Object2IntMaps
import net.minecraft.tags.TagKey
import net.minecraft.world.item.Item
import net.minecraft.world.item.ItemStack

class FlavourProfile private constructor(private val map: Object2IntMap<Flavour>) {

    companion object {
        private val amounts = Maps.object2IntMap<TagKey<Item>> {
            put(SlowcookingItemTags.spicyIngredient, 9)
            put(SlowcookingItemTags.dryIngredient, 9)
            put(SlowcookingItemTags.sweetIngredient, 9)
            put(SlowcookingItemTags.bitterIngredient, 9)
            put(SlowcookingItemTags.sourIngredient, 9)
            put(SlowcookingItemTags.verySpicyIngredient, 21)
            put(SlowcookingItemTags.veryDryIngredient, 21)
            put(SlowcookingItemTags.verySweetIngredient, 21)
            put(SlowcookingItemTags.veryBitterIngredient, 21)
            put(SlowcookingItemTags.verySourIngredient, 21)
        }

        val codec: Codec<FlavourProfile> = Flavour.codec.object2IntMap.xmap({ k -> FlavourProfile(k) }, { v -> v.map })

        val empty = FlavourProfile(Object2IntMaps.emptyMap())

        fun tags(stack: ItemStack): FlavourProfile {
            val map: Object2IntMap<Flavour> = Object2IntArrayMap()
            for (tag in stack.tags) {
                Flavour.map[tag]?.let {
                    val previous = map.getInt(it)
                    map.put(it, previous.coerceAtLeast(amounts.getInt(tag)))
                }
            }
            return FlavourProfile(map.freeze)
        }
    }
}