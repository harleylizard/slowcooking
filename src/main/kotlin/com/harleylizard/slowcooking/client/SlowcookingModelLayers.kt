package com.harleylizard.slowcooking.client

import com.harleylizard.slowcooking.common.Slowcooking.Companion.resourceLocation
import net.minecraft.client.model.geom.ModelLayerLocation

object SlowcookingModelLayers {
    private val String.modelLayerLocation: ModelLayerLocation get() = ModelLayerLocation(resourceLocation, "main")

    val potteryWheelDisc = "pottery_wheel_disc".modelLayerLocation
    val potteryWheelClay = "pottery_wheel_clay".modelLayerLocation

}