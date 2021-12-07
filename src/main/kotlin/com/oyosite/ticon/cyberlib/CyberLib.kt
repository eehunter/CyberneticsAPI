package com.oyosite.ticon.cyberlib

import com.oyosite.ticon.cyberlib.block.Registry.registerBlocks
import com.oyosite.ticon.cyberlib.item.Registry.registerItems
import com.oyosite.ticon.cyberlib.client.CyberLayerScreenHandler
import com.oyosite.ticon.cyberlib.power.CyberwareLayerPower
import io.github.apace100.apoli.power.factory.PowerFactory
import io.github.apace100.apoli.registry.ApoliRegistries
import net.fabricmc.api.ModInitializer
import net.fabricmc.fabric.api.screenhandler.v1.ScreenHandlerRegistry
import net.minecraft.screen.ScreenHandlerType
import net.minecraft.util.Identifier
import net.minecraft.util.registry.Registry

object CyberLib : ModInitializer {
    const val MODID = "cyberlib"
    val CYBER_LAYER_SCREEN_HANDLER: ScreenHandlerType<CyberLayerScreenHandler> = ScreenHandlerRegistry.registerExtended(Identifier(MODID, "cyber_layer"), ::CyberLayerScreenHandler)
    override fun onInitialize() {
        registerBlocks()
        registerItems()
        registerPowerFactory(CyberwareLayerPower.createFactory())
    }
    private fun registerPowerFactory(factory: PowerFactory<*>): PowerFactory<*> = Registry.register(ApoliRegistries.POWER_FACTORY, factory.serializerId, factory)
}