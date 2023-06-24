package com.oyosite.ticon.cyberlib

import com.oyosite.ticon.cyberlib.client.CyberForgeScreen
import com.oyosite.ticon.cyberlib.client.CyberLayerScreen
import net.fabricmc.api.ClientModInitializer
import net.fabricmc.api.EnvType
import net.fabricmc.api.Environment
import net.fabricmc.fabric.api.client.screenhandler.v1.ScreenRegistry
import net.minecraft.client.gui.screen.ingame.HandledScreens

@Environment(EnvType.CLIENT)
object CyberLibClient : ClientModInitializer{
    override fun onInitializeClient() {
        HandledScreens.register(CyberLib.CYBER_LAYER_SCREEN_HANDLER, ::CyberLayerScreen)
        HandledScreens.register(CyberLib.CYBER_FORGE_SCREEN_HANDLER, ::CyberForgeScreen)
    }
}