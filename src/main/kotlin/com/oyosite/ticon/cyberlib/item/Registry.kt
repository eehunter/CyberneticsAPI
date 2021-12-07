@file:Suppress("Unused")

package com.oyosite.ticon.cyberlib.item

import com.oyosite.ticon.cyberlib.item.CyberItem.Companion.register
import net.fabricmc.fabric.api.item.v1.FabricItemSettings
import net.minecraft.util.registry.Registry

object Registry {
    val ITEMS = mutableListOf<CyberItem>()

    val AUGMENT = CyberItem("augment", FabricItemSettings().maxCount(1)).register()

    fun registerItems() = ITEMS.forEach { Registry.register(Registry.ITEM, it.id, it) }
}