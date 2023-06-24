package com.oyosite.ticon.cyberlib.client

import com.mojang.blaze3d.systems.RenderSystem
import com.oyosite.ticon.cyberlib.CyberLib.MODID
import net.minecraft.client.gui.screen.ingame.HandledScreen
import net.minecraft.client.render.GameRenderer
import net.minecraft.client.util.math.MatrixStack
import net.minecraft.entity.player.PlayerInventory
import net.minecraft.text.Text
import net.minecraft.util.Identifier

class CyberLayerScreen(handler: CyberLayerScreenHandler, inv: PlayerInventory, title: Text) : HandledScreen<CyberLayerScreenHandler>(handler, inv, title) {
    companion object {
        private val TEXTURE = Identifier(MODID, "textures/gui/container/cyberware.png")
        private val BASE_SLOT = Identifier(MODID, "textures/gui/container/generic_slot.png")
    }
    override fun drawBackground(matrices: MatrixStack, delta: Float, mouseX: Int, mouseY: Int) {
        RenderSystem.setShader { GameRenderer.getPositionTexProgram() }
        RenderSystem.setShaderColor(1.0f, 1.0f, 1.0f, 1.0f)
        RenderSystem.setShaderTexture(0, TEXTURE)
        val x = (width - backgroundWidth) / 2
        val y = (height - backgroundHeight) / 2
        drawTexture(matrices, x, y, 0, 0, backgroundWidth, backgroundHeight)
        with(handler.power) {
            slotPos.forEachIndexed { i, it ->
                RenderSystem.setShaderTexture(0, icons.getOrDefault(i, BASE_SLOT))
                drawTexture(matrices, x + it.first - 5, y + it.second - 5, 0F, 0F, 26, 26, 26, 26)
            }
        }
    }
    override fun render(matrices: MatrixStack, mouseX: Int, mouseY: Int, delta: Float) {
        renderBackground(matrices)
        super.render(matrices, mouseX, mouseY, delta)
        drawMouseoverTooltip(matrices, mouseX, mouseY)
    }
    override fun init() {
        super.init()
        titleX = (backgroundWidth - textRenderer.getWidth(title)) / 2
    }
}