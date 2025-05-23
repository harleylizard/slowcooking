package com.harleylizard.slowcooking.common

import com.harleylizard.slowcooking.common.Slowcooking.Companion.resourceLocation
import com.harleylizard.slowcooking.common.Util.freeze
import com.harleylizard.slowcooking.common.flavour.Flavour

import net.minecraft.core.registries.Registries
import net.minecraft.tags.TagKey
import net.minecraft.world.item.Item
import net.minecraft.world.item.ItemStack

object SlowcookingItemTags {
    private val String.tag: TagKey<Item> get() = TagKey.create(Registries.ITEM, resourceLocation)

    val spicyIngredient = "spicy_ingredient".tag
    val dryIngredient = "dry_ingredient".tag
    val sweetIngredient = "sweet_ingredient".tag
    val bitterIngredient = "bitter_ingredient".tag
    val sourIngredient = "sour_ingredient".tag

    val verySpicyIngredient = "very_spicy_ingredient".tag
    val veryDryIngredient = "very_dry_ingredient".tag
    val verySweetIngredient = "very_sweet_ingredient".tag
    val veryBitterIngredient = "very_bitter_ingredient".tag
    val verySourIngredient = "very_sour_ingredient".tag

    val map = mapOf(
        spicyIngredient to Flavour.SPICY,
        dryIngredient to Flavour.DRY,
        sweetIngredient to Flavour.SWEET,
        bitterIngredient to Flavour.BITTER,
        sourIngredient to Flavour.SOUR,
        verySpicyIngredient to Flavour.SPICY,
        veryDryIngredient to Flavour.DRY,
        verySweetIngredient to Flavour.SWEET,
        veryBitterIngredient to Flavour.BITTER,
        verySourIngredient to Flavour.SOUR
    ).freeze

    val ItemStack.isIngredient get() = tags.anyMatch { tag ->
        tag == spicyIngredient ||
        tag == dryIngredient ||
        tag == sweetIngredient ||
        tag == bitterIngredient ||
        tag == sourIngredient ||
        tag == verySpicyIngredient ||
        tag == veryDryIngredient ||
        tag == verySweetIngredient ||
        tag == veryBitterIngredient ||
        tag == verySourIngredient}
}