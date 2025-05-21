package com.harleylizard.slowcooking.common

import com.harleylizard.slowcooking.common.Slowcooking.Companion.resourceLocation
import net.minecraft.core.registries.Registries
import net.minecraft.tags.TagKey
import net.minecraft.world.item.Item

object SlowcookingItemTags {
    private val String.tag: TagKey<Item> get() = TagKey.create(Registries.ITEM, resourceLocation)

    val spicyIngredient = "spicy_ingredient".tag
    val dryIngredient = "dry_ingredient".tag
    val sweetIngredient = "sweet_ingredient".tag
    val bitterIngredient = "bitter_ingredient".tag
    val sourIngredient = "sour_ingredient".tag
}