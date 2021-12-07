package com.oyosite.ticon.cyberlib

import com.oyosite.ticon.cyberlib.client.CyberLayerScreen
import net.fabricmc.api.ClientModInitializer
import net.fabricmc.api.EnvType
import net.fabricmc.api.Environment
import net.fabricmc.fabric.api.client.screenhandler.v1.ScreenRegistry

@Environment(EnvType.CLIENT)
object CyberLibClient : ClientModInitializer{
    override fun onInitializeClient() {
        ScreenRegistry.register(CyberLib.CYBER_LAYER_SCREEN_HANDLER, ::CyberLayerScreen)

    }
}