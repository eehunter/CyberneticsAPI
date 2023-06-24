package com.oyosite.ticon.cyberlib.client

import com.oyosite.ticon.cyberlib.CyberLib.MODID
import net.minecraft.client.gui.screen.ingame.ForgingScreen
import net.minecraft.client.util.math.MatrixStack
import net.minecraft.entity.player.PlayerInventory
import net.minecraft.text.Text
import net.minecraft.util.Identifier

class CyberForgeScreen(handler: CyberForgeScreenHandler, inventory: PlayerInventory, title: Text) : ForgingScreen<CyberForgeScreenHandler>(handler, inventory, title, TEXTURE) {
    init{
        titleX = 60
        titleY = 18
    }

    companion object {
        private val TEXTURE: Identifier = Identifier(MODID, "textures/gui/container/cyber_forge.png")
    }

    val hasInvalidRecipe: Boolean get() = handler.getSlot(0).hasStack() && handler.getSlot(1).hasStack() && !handler.getSlot(handler.resultSlotIndex).hasStack()

    override fun drawInvalidRecipeArrow(matrices: MatrixStack?, x: Int, y: Int) {
        if(hasInvalidRecipe) drawTexture(matrices, x + 99, y + 45, backgroundWidth, 0, 28, 21)
    }
}