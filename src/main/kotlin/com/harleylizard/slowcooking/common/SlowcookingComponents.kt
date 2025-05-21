package com.harleylizard.slowcooking.common

import com.harleylizard.slowcooking.common.Slowcooking.Companion.resourceLocation
import com.harleylizard.slowcooking.common.flavour.FlavourProfile
import net.minecraft.core.Registry
import net.minecraft.core.component.DataComponentType
import net.minecraft.core.registries.BuiltInRegistries

object SlowcookingComponents {
    val flavourProfile: DataComponentType<FlavourProfile> = DataComponentType.builder<FlavourProfile>()
        .persistent(FlavourProfile.codec)
        .build()

    fun registerAll() {
        register("flavour_profile", flavourProfile)
    }

    private fun <T> register(name: String, component: DataComponentType<T>) {
        Registry.register(BuiltInRegistries.DATA_COMPONENT_TYPE, name.resourceLocation, component)
    }
}