package com.oyosite.ticon.cyberlib.client

import com.oyosite.ticon.cyberlib.CyberLib.MODID
import net.minecraft.client.gui.screen.ingame.ForgingScreen
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
}