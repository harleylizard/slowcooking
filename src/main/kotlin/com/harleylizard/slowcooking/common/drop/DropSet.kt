package com.harleylizard.slowcooking.common.drop

import com.cobblemon.mod.common.api.drop.DropEntry
import com.google.gson.JsonDeserializer
import com.harleylizard.slowcooking.common.SlowcookingItemTags.isIngredient
import net.minecraft.server.level.ServerLevel
import net.minecraft.server.level.ServerPlayer
import net.minecraft.world.entity.LivingEntity
import net.minecraft.world.phys.Vec3
import java.util.*

class DropSet(private val set: Set<Drop>) : DropEntry {

    override val maxSelectableTimes = 1

    override val percentage = 100.0f

    override val quantity = 1

    override fun drop(entity: LivingEntity?, world: ServerLevel, pos: Vec3, player: ServerPlayer?) {
        for (drop in set) {
            drop.itemStack(world.random).takeIf { it.isIngredient }?.let {
                entity?.spawnAtLocation(it)
            }
        }
    }

    companion object {
        val deserializer = JsonDeserializer { json, typeOfT, context ->
            val items = json.asJsonObject.getAsJsonArray("items")
            val set: MutableSet<Drop> = HashSet<Drop>(items.size())
            for (entry in items) {
                set.add(context.deserialize(entry, Drop::class.java))
            }
            DropSet(Collections.unmodifiableSet(set))
        }

    }
}