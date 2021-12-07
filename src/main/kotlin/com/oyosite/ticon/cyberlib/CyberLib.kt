package com.oyosite.ticon.cyberlib

import com.oyosite.ticon.cyberlib.block.registerBlocks
import com.oyosite.ticon.cyberlib.client.CyberLayerScreen
import com.oyosite.ticon.cyberlib.client.CyberLayerScreenHandler
import com.oyosite.ticon.cyberlib.item.registerItems
import net.fabricmc.api.ModInitializer
import net.fabricmc.fabric.api.client.screenhandler.v1.ScreenRegistry
import net.fabricmc.fabric.api.screenhandler.v1.ScreenHandlerRegistry
import net.minecraft.screen.ScreenHandlerType
import net.minecraft.util.Identifier

object CyberLib : ModInitializer {
    const val MODID = "cyberlib"
    val CYBER_LAYER_SCREEN_HANDLER: ScreenHandlerType<CyberLayerScreenHandler> = ScreenHandlerRegistry.registerExtended(Identifier(MODID, "cyber_layer"), ::CyberLayerScreenHandler)
    override fun onInitialize() {
        registerBlocks()
        registerItems()
        ScreenRegistry.register(CYBER_LAYER_SCREEN_HANDLER, ::CyberLayerScreen)
    }
}