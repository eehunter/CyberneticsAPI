@file:Suppress("Unused")

package com.oyosite.ticon.cyberlib.item

import com.oyosite.ticon.cyberlib.item.CyberItem.Companion.register
import net.fabricmc.fabric.api.item.v1.FabricItemSettings
import net.minecraft.registry.Registries
import net.minecraft.registry.Registry

object Registry {
    val ITEMS = mutableListOf<CyberItem>()

    //val AUGMENT = CyberItem("augment", FabricItemSettings().maxCount(1)).register()

    fun registerItems() = ITEMS.forEach { Registry.register(Registries.ITEM, it.id, it) }
}