package com.harleylizard.slowcooking.common.drop

import com.google.gson.JsonDeserializer
import net.minecraft.core.registries.BuiltInRegistries
import net.minecraft.resources.ResourceLocation
import net.minecraft.util.RandomSource
import net.minecraft.world.item.Item
import net.minecraft.world.item.ItemStack

class Drop private constructor(
    private val item: Item,
    private val min: Int,
    private val max: Int) {

    fun itemStack(random: RandomSource): ItemStack = item.defaultInstance.copyWithCount(random.nextInt(min, max + 1))

    override fun hashCode() = item.hashCode()

    override fun equals(other: Any?) = if (other is Drop) other.item == item else super.equals(other)

    companion object {
        val deserializer = JsonDeserializer { json, typeOfT, context ->
            val jsonObject = json.asJsonObject
            val id = ResourceLocation.parse(jsonObject.getAsJsonPrimitive("item").asString)
            val item = BuiltInRegistries.ITEM.get(id)
            val min = jsonObject.getAsJsonPrimitive("min").asInt
            val max = jsonObject.getAsJsonPrimitive("max").asInt

            Drop(item,
                min.coerceAtMost(max),
                min.coerceAtLeast(max))
        }

    }
}