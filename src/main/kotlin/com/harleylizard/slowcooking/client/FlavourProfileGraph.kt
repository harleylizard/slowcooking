package com.harleylizard.slowcooking.client

import com.harleylizard.slowcooking.common.flavour.FlavourProfile
import com.mojang.blaze3d.systems.RenderSystem
import com.mojang.blaze3d.vertex.BufferUploader
import com.mojang.blaze3d.vertex.DefaultVertexFormat
import com.mojang.blaze3d.vertex.Tesselator
import com.mojang.blaze3d.vertex.VertexFormat
import net.minecraft.client.gui.Font
import net.minecraft.client.gui.GuiGraphics
import net.minecraft.client.gui.screens.inventory.tooltip.ClientTooltipComponent
import net.minecraft.client.renderer.GameRenderer

class FlavourProfileGraph(private val profile: FlavourProfile) : ClientTooltipComponent {
    override fun getHeight() = 30

    override fun getWidth(font: Font) = 30

    override fun renderImage(font: Font, i: Int, j: Int, guiGraphics: GuiGraphics) {
        val pose = guiGraphics.pose()
        pose.pushPose()
        pose.translate(i.toFloat(), j.toFloat(), 0.0f)
        RenderSystem.setShader(GameRenderer::getPositionColorShader)
        RenderSystem.setShaderColor(1.0f, 1.0f, 1.0f, 1.0f)
        val last = pose.last().pose()
        val buffer = Tesselator.getInstance().begin(VertexFormat.Mode.TRIANGLES, DefaultVertexFormat.POSITION_COLOR)

        val size = height.toFloat()
        val d = (0.15f / 1.0f) * size

        val t = 1.0f
        val r = 1.0f
        val g = 1.0f
        val b = 1.0f

        val min = size - 10.0f
        val k = (0.1f / 1.0f) * size
        val m = lerp(size - min, 0.0f, profile.spiciness)
        val c = lerp(size - min, 0.0f, profile.sourness)
        val l = lerp(min, size, profile.dryness)

        val x = lerp(min, size, profile.bitterness)
        val u = size - x + ((0.1f / 1.0f) * x)
        val y = lerp(min, size, profile.sweetness)
        val p = size - (size - y) - ((0.1f / 1.0f) * y)

        buffer.addVertex(last, size / 2.0f, m, 0.0f).setColor(r, g, b, t)
        buffer.addVertex(last, c, size / 2.25f, 0.0f).setColor(r, g, b, t)
        buffer.addVertex(last, l, size / 2.25f, 0.0f).setColor(r, g, b, t)

        buffer.addVertex(last, l, size / 2.25f, 0.0f).setColor(r, g, b, t)
        buffer.addVertex(last, u, x, 0.0f).setColor(r, g, b, t)
        buffer.addVertex(last, p, y, 0.0f).setColor(r, g, b, t)

        buffer.addVertex(last, l, size / 2.25f, 0.0f).setColor(r, g, b, t)
        buffer.addVertex(last, c, size / 2.25f, 0.0f).setColor(r, g, b, t)
        buffer.addVertex(last, u, x, 0.0f).setColor(r, g, b, t)

        BufferUploader.drawWithShader(buffer.buildOrThrow())

        pose.popPose()
    }

    fun lerp(start: Float, end: Float, t: Float): Float {
        return start + t * (end - start)
    }
}