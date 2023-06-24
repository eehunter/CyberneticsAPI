package com.oyosite.ticon.cyberlib

import com.oyosite.ticon.cyberlib.action.ActionRegistry
import com.oyosite.ticon.cyberlib.block.Registry.registerBlocks
import com.oyosite.ticon.cyberlib.client.CyberForgeScreenHandler
import com.oyosite.ticon.cyberlib.client.CyberLayerScreenHandler
import com.oyosite.ticon.cyberlib.condition.ConditionRegistry
import com.oyosite.ticon.cyberlib.data.CyberForgeRecipe
import com.oyosite.ticon.cyberlib.data.UpgradeResourceReloadListener
import com.oyosite.ticon.cyberlib.item.Registry.registerItems
import com.oyosite.ticon.cyberlib.power.CyberwareLayerPower
import com.oyosite.ticon.cyberlib.registry.CyberlibRegistries
import io.github.apace100.apoli.power.factory.PowerFactory
import io.github.apace100.apoli.registry.ApoliRegistries
import net.fabricmc.api.ModInitializer
import net.fabricmc.fabric.api.resource.ResourceManagerHelper
import net.fabricmc.fabric.api.resource.conditions.v1.ResourceConditions
import net.fabricmc.fabric.api.screenhandler.v1.ScreenHandlerRegistry
import net.minecraft.resource.ResourceType
import net.minecraft.screen.ScreenHandlerType
import net.minecraft.util.Identifier
import net.minecraft.registry.Registry

object CyberLib : ModInitializer {
    const val MODID = "cyberlib"
    val CYBER_LAYER_SCREEN_HANDLER: ScreenHandlerType<CyberLayerScreenHandler> = ScreenHandlerRegistry.registerExtended(Identifier(MODID, "cyber_layer"), ::CyberLayerScreenHandler)
    val CYBER_FORGE_SCREEN_HANDLER: ScreenHandlerType<CyberForgeScreenHandler> = ScreenHandlerRegistry.registerSimple  (Identifier(MODID, "cyber_forge"), ::CyberForgeScreenHandler)
    override fun onInitialize() {
        registerBlocks()
        registerItems()
        registerPowerFactory(CyberwareLayerPower.createFactory())
        CyberForgeRecipe.SERIALIZER
        ActionRegistry.register()
        ConditionRegistry.register()
        //CyberlibRegistries.UPGRADE
        ResourceManagerHelper.get(ResourceType.SERVER_DATA).registerReloadListener(UpgradeResourceReloadListener)

    }
    private fun registerPowerFactory(factory: PowerFactory<*>): PowerFactory<*> = Registry.register(ApoliRegistries.POWER_FACTORY, factory.serializerId, factory)
}