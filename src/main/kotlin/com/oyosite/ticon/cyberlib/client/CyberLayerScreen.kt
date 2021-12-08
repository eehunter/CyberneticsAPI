package com.oyosite.ticon.cyberlib.client

import com.mojang.blaze3d.systems.RenderSystem
import com.oyosite.ticon.cyberlib.CyberLib.MODID
import net.minecraft.client.gui.screen.ingame.HandledScreen
import net.minecraft.client.render.GameRenderer
import net.minecraft.client.util.math.MatrixStack
import net.minecraft.entity.player.PlayerInventory
import net.minecraft.text.Text
import net.minecraft.util.Identifier


class CyberLayerScreen(private val handler: CyberLayerScreenHandler, inv: PlayerInventory, title: Text) : HandledScreen<CyberLayerScreenHandler>(handler, inv, title) {
    val TEXTURE = Identifier( "textures/gui/container/dispenser.png")
    override fun drawBackground(matrices: MatrixStack, delta: Float, mouseX: Int, mouseY: Int) {
        RenderSystem.setShader { GameRenderer.getPositionTexShader() }
        RenderSystem.setShaderColor(1.0f, 1.0f, 1.0f, 1.0f)
        RenderSystem.setShaderTexture(0, TEXTURE)
        val x = (width - backgroundWidth) / 2
        val y = (height - backgroundHeight) / 2
        drawTexture(matrices, x, y, 0, 0, backgroundWidth, backgroundHeight)
        handler.power.slotPos.forEach { drawTexture(matrices, x+it.first-1, y+it.second-1, backgroundWidth/2-9, backgroundHeight/2, 18, 18) }
    }

}