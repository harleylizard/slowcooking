package com.harleylizard.slowcooking.common.drop

import com.cobblemon.mod.common.api.pokemon.PokemonSpecies
import com.cobblemon.mod.common.pokemon.Pokemon
import com.cobblemon.mod.common.pokemon.Species
import com.google.gson.GsonBuilder
import net.fabricmc.loader.api.FabricLoader
import net.minecraft.server.packs.PackType
import java.io.BufferedReader
import java.io.InputStreamReader
import java.util.concurrent.ConcurrentHashMap

class SpeciesDrop  {
    private val map: MutableMap<Species, DropSet> = ConcurrentHashMap()

    fun get(pokemon: Pokemon): DropSet? = map[pokemon.species]

    fun load(species: PokemonSpecies) {
        map.clear()
        val gson = GsonBuilder()
            .registerTypeAdapter(Drop::class.java, Drop.deserializer)
            .registerTypeAdapter(DropSet::class.java, DropSet.deserializer)
            .create()
        for (mod in FabricLoader.getInstance().allMods) {
            val path = "${PackType.SERVER_DATA.directory}/${mod.metadata.id}/ingredients_to_drop"
            for (type in species.species)  {
                val file = "$path/${type.resourceIdentifier.path}.json"
                SpeciesDrop::class.java.classLoader.getResource(file)?.openStream()?.let {
                    BufferedReader(InputStreamReader(it))
                }?.use {
                    type.drops.entries.add(gson.fromJson(it, DropSet::class.java))
                }
            }
        }
    }

    companion object {
        val speciesDrop = SpeciesDrop()

    }
}