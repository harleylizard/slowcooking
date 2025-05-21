package com.harleylizard.slowcooking.client

import com.harleylizard.slowcooking.common.Slowcooking.Companion.resourceLocation
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
import net.minecraft.util.Mth

class FlavourProfileTooltip(private val profile: FlavourProfile) : ClientTooltipComponent {
    override fun getHeight(): Int {
        return 100;
    }

    override fun getWidth(font: Font): Int {
        return 100;
    }

    override fun renderImage(font: Font, i: Int, j: Int, guiGraphics: GuiGraphics) {
        val pose = guiGraphics.pose()
        pose.pushPose()
        pose.translate(i.toFloat(), j.toFloat(), 0.0f)
        //RenderSystem.setShaderTexture(0, "e".resourceLocation)
        RenderSystem.enableBlend()
        RenderSystem.setShader(GameRenderer::getPositionTexShader)
        val last = pose.last().pose()
        val buffer = Tesselator.getInstance().begin(VertexFormat.Mode.TRIANGLES, DefaultVertexFormat.POSITION_COLOR)

        val size = 40.0f
        val big = size + 20.0f
        val d = (0.15f / 1.0f) * size
        val k = (0.1f / 1.0f) * size

        val t = 0.75f
        val r = 1.0f
        val g = 1.0f
        val b = 1.0f

        val f = profile.spiciness * big
        val h = profile.dryness * big
        val x = profile.sweetness * big
        val y = profile.bitterness * big
        val z = profile.sourness * big
        buffer.addVertex(last, size / 2.0f, 0.0f + d, 0.0f).setColor(r, g, b, t)
        buffer.addVertex(last, 0.0f, size / 2.25f, 0.0f).setColor(r, g, b, t)
        buffer.addVertex(last, size, size / 2.25f, 0.0f).setColor(r, g, b, t)

        buffer.addVertex(last, size, size / 2.25f, 0.0f).setColor(r, g, b, t)
        buffer.addVertex(last, d, size + k, 0.0f).setColor(r, g, b, t)
        buffer.addVertex(last, size - d, size + k, 0.0f).setColor(r, g, b, t)

        buffer.addVertex(last, size + 0, size / 2.25f, 0.0f).setColor(r, g, b, t)
        buffer.addVertex(last, 0.0f, size / 2.25f, 0.0f).setColor(r, g, b, t)
        buffer.addVertex(last, 0.0f + d, size + k, 0.0f).setColor(r, g, b, t)

        BufferUploader.drawWithShader(buffer.buildOrThrow())

        pose.popPose()
    }
}